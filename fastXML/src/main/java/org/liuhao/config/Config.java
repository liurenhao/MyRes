package org.liuhao.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junit.Test;
import org.liuhao.reflect.ReflectUtils;

public class Config {
	
	public final static String DEFAULT_CONFIG_PATH = "config/config.properties";
	
	Document document = new Document();
	
	@Test
	public void readProperties(){
		Properties properties = new Properties();
		// 使用ClassLoader加载properties配置文件生成对应的输入流
		InputStream in = this.getClass().getClassLoader().getResourceAsStream(DEFAULT_CONFIG_PATH);
		// 使用properties对象加载输入流
		try {
			properties.load(in);
		} catch (IOException e) {
			throw new RuntimeException("配置文件读取有误！");
		}
		document = parse(properties);
		
	}
	
	protected Document parse(Properties properties){
		Document document = new Document();
		
		properties.entrySet().forEach(entry->{
			String path = (String) entry.getKey();
			String value = (String)entry.getValue();
			try {
				ReflectUtils.setValue(document, path, value);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
		
		return document;
	}

	/**
	 * @return the document
	 */
	public Document getDocument() {
		return document;
	}

	/**
	 * @param document the document to set
	 */
	public void setDocument(Document document) {
		this.document = document;
	}
	
	
}
