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
		//读取配置文件
		config.readProperties();
		Register registerFactory = new RegisterFactory(reader, config);
		//注册reader的handler事件
		registerFactory.registerHandler();
		
		//单线程解析XML文档
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					long start = System.currentTimeMillis();
					reader.read(new FileInputStream(new File(filename)));
					long end = System.currentTimeMillis();
					System.out.println("耗时：" + (end - start) + "ms");
					System.out.println("生产数据个数：" + Queen.productNum);
					Object take = Queen.queue.add("byebye");
				} catch (Exception e) {
					new RuntimeException(e);
				}
			}
		}).start();


		
		
		//线程池处理解析数据
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
		

		//工作量证明
		ObjectMap.threadMap.entrySet().forEach(entry -> {
			System.out.println(entry.getKey() + "完成个数:" + entry.getValue());
		});
		
		long end = System.currentTimeMillis();
		System.out.println("耗时：" + (end - start) + "ms");

	}
	
	public static void main(String[] args) throws Exception{
		FastXml.readXml("e:\\test11.xml", ConsumerMyThread.class);
	}
}
