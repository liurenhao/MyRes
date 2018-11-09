package org.liuhao.pojo;

import org.liuhao.annotation.Doc;

@Doc(path="/gen/Root")
public class Root {
	String rootName;
	PolicyList policyList;
	/**
	 * @return the rootName
	 */
	public String getRootName() {
		return rootName;
	}
	/**
	 * @param rootName the rootName to set
	 */
	public void setRootName(String rootName) {
		this.rootName = rootName;
	}
	/**
	 * @return the policyList
	 */
	public PolicyList getPolicyList() {
		return policyList;
	}
	/**
	 * @param policyList the policyList to set
	 */
	public void setPolicyList(PolicyList policyList) {
		this.policyList = policyList;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Root [rootName=" + rootName + ", policyList=" + policyList + "]";
	}
	
}
