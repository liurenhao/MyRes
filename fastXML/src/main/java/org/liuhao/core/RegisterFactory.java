package org.liuhao.core;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.io.SAXReader;
import org.liuhao.annotation.Doc;
import org.liuhao.config.Config;
import org.liuhao.config.Document;

public class RegisterFactory implements Register {
	
	Map<String, Class<?>> pathMap = new HashMap<>();
	
	SAXReader reader;
	
	Config config;
	
	@Override
	public void registerHandler() {
		//ɨ��ָ�����µ�pojo��
		Document document = config.getDocument();
		List<String> packageNames = document.getPojo().getPackageName();
		packageNames.forEach(pname -> {
			pname = pname.replace(".", "/");
			try {
				registerDocument(pname);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
	}

	public void registerDocument(String packageName) throws ClassNotFoundException {
		URL url = this.getClass().getClassLoader().getResource("");
		// ���ݰ�·�����ļ���
		String path1 = url.toString().replace("file:/", "") + packageName;
		File file = new File(path1);
//		System.out.println(path1);
		// ���ΪĿ¼���������ļ������ļ���
		if (file.isDirectory()) {
			String[] fileList = file.list();
			for (int i = 0; i < fileList.length; i++) {
				// �ݹ���Ұ�������class�ļ�
				registerDocument(packageName + "/" + fileList[i]);
			}
		} else if (file.getName().endsWith(".class")) {
			Class<?> cls = Class.forName(packageName.replace(".class", "").replace("/", "."));
			if (cls.isAnnotationPresent(Doc.class)) {
				Doc doc = cls.getAnnotation(Doc.class);
				//ʵ����
				String path = doc.path();
				if(pathMap.get(path)!=null){
					throw new RuntimeException("·����"+path+"���ѱ��ࡿ"+cls+"��ʹ�ã������");
				}
				pathMap.put(path, cls);
				System.out.println(path+":"+cls);
				reader.addHandler(path, new ConsElementHandler(cls,config));
			}
		} else {
			return;
		}

	}

	public RegisterFactory(SAXReader reader, Config config) {
		this.reader = reader;
		this.config = config;
	}
	
}
