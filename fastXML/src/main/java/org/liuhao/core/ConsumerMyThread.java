package org.liuhao.core;

import java.io.File;

import org.liuhao.utils.FileUtils;

import com.thoughtworks.xstream.XStream;

public class ConsumerMyThread extends ConsumerThread {

	@Override
	protected void handle(Object obj) {
		try {
//			XStream xStream = new XStream();
//			String xml = xStream.toXML(obj);
//			FileUtils.appendFileText(xml, new File("e:\\test3.xml"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
