package org.liuhao.config;

public class Document {
	Pojo pojo = new Pojo();
	Thread thread = new Thread();

	/**
	 * @return the pojo
	 */
	public Pojo getPojo() {
		return pojo;
	}

	/**
	 * @param pojo the pojo to set
	 */
	public void setPojo(Pojo pojo) {
		this.pojo = pojo;
	}

	/**
	 * @return the thread
	 */
	public Thread getThread() {
		return thread;
	}

	/**
	 * @param thread the thread to set
	 */
	public void setThread(Thread thread) {
		this.thread = thread;
	}
	
}
