package org.liuhao.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.liuhao.queen.Queen;

public class XmlUtils {

	public static final int THREAD_NUM = 50;

	static Set<String> paths = new HashSet<String>();

	static {
		Properties properties = new Properties();
		// ʹ��ClassLoader����properties�����ļ����ɶ�Ӧ��������
		InputStream in = XmlUtils.class.getClassLoader().getResourceAsStream("config/path.properties");
		// ʹ��properties�������������
		try {
			properties.load(in);
		} catch (IOException e) {
			throw new RuntimeException("�����ļ���ȡ����");
		}
		properties.stringPropertyNames().forEach(key -> paths.add(properties.getProperty(key)));
	}

	public static void readXml(String filename,Class<? extends ConsumerThread> consumerClass) throws FileNotFoundException, DocumentException, InterruptedException, InstantiationException, IllegalAccessException {
		long start = System.currentTimeMillis();
		SAXReader reader = new SAXReader();
		paths.forEach(path -> {
			Class<?> cls;
			try {
				String className = path.substring(path.lastIndexOf("/") + 1);
				className = className.substring(0, 1).toUpperCase() + className.substring(1);
				System.out.println(className);
				cls = Class.forName("org.liuhao.pojo." + className);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			reader.addHandler(path, new ConsElementHandler(cls));
		});
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					long start = System.currentTimeMillis();
					reader.read(new FileInputStream(new File(filename)));
					long end = System.currentTimeMillis();
					System.out.println("��ʱ��" + (end - start) + "ms");
					System.out.println("�������ݸ�����" + Queen.productNum);
				} catch (Exception e) {
					new RuntimeException(e);
				}
			}
		}).start();

		while (true) {
			if (Queen.queue.size() > 0)
				break;
		}

		ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(THREAD_NUM);
		for (int i = 0; i < THREAD_NUM; i++) {
			newFixedThreadPool.submit(consumerClass.newInstance());
		}
		newFixedThreadPool.shutdown();
		while (!newFixedThreadPool.isTerminated()) {

		}
		long end = System.currentTimeMillis();
		System.out.println("��ʱ��" + (end - start) + "ms");

	}

	public static void main(String[] args) throws FileNotFoundException, DocumentException, InterruptedException, InstantiationException, IllegalAccessException {
		XmlUtils.readXml("e:\\test11.xml", ConsumerMyThread.class);
	}
}
