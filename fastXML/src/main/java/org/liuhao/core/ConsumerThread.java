package org.liuhao.core;

import org.liuhao.queen.Queen;

public abstract class ConsumerThread implements Runnable {
	
	int count;
	
	@Override
	public void run() {
		boolean flag = true;
		try {
			while (true) {
//				Object take = Queen.queue.poll();
				Object take = Queen.queue.take();
				if(take==null){
					if(flag){
//						System.out.println(count);
						Thread.sleep(1000);
						flag = false;
						continue;
					}
					break;
				}
				flag = true;
				handle(take);
				take = null;
				count++;
			}
			System.out.println("数据处理完成，处理个数"+count);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
	
	protected abstract void handle(Object obj);
	
}	
