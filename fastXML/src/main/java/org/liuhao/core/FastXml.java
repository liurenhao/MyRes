package org.liuhao.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.junit.Test;
import org.liuhao.config.Config;
import org.liuhao.constant.ObjectMap;
import org.liuhao.queen.Queen;

public class FastXml {
	public static void readXml(String filename,Class<? extends ConsumerThread> consumerClass) throws FileNotFoundException, DocumentException, InterruptedException, InstantiationException, IllegalAccessException {
		long start = System.currentTimeMillis();
		SAXReader reader = new SAXReader();
		Config config = new Config();
		//��ȡ�����ļ�
		config.readProperties();
		Register registerFactory = new RegisterFactory(reader, config);
		//ע��reader��handler�¼�
		registerFactory.registerHandler();
		
		//���߳̽���XML�ĵ�
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					long start = System.currentTimeMillis();
					reader.read(new FileInputStream(new File(filename)));
					long end = System.currentTimeMillis();
					System.out.println("��ʱ��" + (end - start) + "ms");
					System.out.println("�������ݸ�����" + Queen.productNum);
					Object take = Queen.queue.add("byebye");
				} catch (Exception e) {
					new RuntimeException(e);
				}
			}
		}).start();


		
		
		//�̳߳ش����������
//		ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(config.getDocument().getThread().getNum());
		ExecutorService threadPool = new ThreadPoolExecutor(10, 100, 10, TimeUnit.SECONDS, 
				new ArrayBlockingQueue<>(10000), new ThreadPoolExecutor.CallerRunsPolicy());
		while (true) {
			Object take = Queen.queue.take();
			if("byebye".equals(take)){
				break;
			}
			ConsumerThread newInstance = consumerClass.newInstance();
			try {
				Field field = consumerClass.getSuperclass().getDeclaredField("obj");
				field.setAccessible(true);
				field.set(newInstance, take);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			threadPool.submit(newInstance);
		}
		
		threadPool.shutdown();
		
		while(!threadPool.isTerminated()) {
			
		}
		

		//������֤��
		ObjectMap.threadMap.entrySet().forEach(entry -> {
			System.out.println(entry.getKey() + "��ɸ���:" + entry.getValue());
		});
		
		long end = System.currentTimeMillis();
		System.out.println("��ʱ��" + (end - start) + "ms");

	}
	
	public static void main(String[] args) throws Exception{
		FastXml.readXml("e:\\test11.xml", ConsumerMyThread.class);
	}
}
