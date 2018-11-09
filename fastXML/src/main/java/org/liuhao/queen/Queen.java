package org.liuhao.queen;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Queen{
	
	final static double FACTOR = 0.75;
	
	final static int CAPACITY = 2000;
	
	public final static BlockingQueue<Object> queue = new ArrayBlockingQueue<>(CAPACITY);
	
	public static int productNum = 0;
	
}
