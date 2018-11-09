package org.liuhao.core;

import org.liuhao.constant.ObjectMap;

public class ConsumerMyThread extends ConsumerThread {

	public final static ThreadLocal<Integer> count = new ThreadLocal<>();
	
	@Override
	protected void handle(Object obj) {
		try {
			// XStream xStream = new XStream();
			// String xml = xStream.toXML(obj);
			// FileUtils.appendFileText(xml, new File("e:\\test3.xml"));
			
			Thread.sleep(200);
			
			if(count.get()==null){
				count.set(0);
			}
			count.set(count.get() + 1);
			ObjectMap.threadMap.put(Thread.currentThread().getName(), count.get());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
