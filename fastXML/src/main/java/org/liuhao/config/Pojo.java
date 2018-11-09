package org.liuhao.config;

import java.util.ArrayList;
import java.util.List;

import org.liuhao.constant.Constant;

public class Pojo {
	List<String> packageName = new ArrayList<>();
	
	String root = Constant.ROOT;

	public List<String> getPackageName() {
		return packageName;
	}

	public void setPackageName(List<String> packageName) {
		this.packageName = packageName;
	}

	/**
	 * @return the root
	 */
	public String getRoot() {
		return root;
	}

	/**
	 * @param root the root to set
	 */
	public void setRoot(String root) {
		this.root = root;
	}
	
	
}
