package org.liuhao.pojo;

import java.util.ArrayList;
import java.util.List;

public class PolicyList {
	List<Policy> policy = new ArrayList<>();

	/**
	 * @return the policy
	 */
	public List<Policy> getPolicy() {
		return policy;
	}

	/**
	 * @param policy the policy to set
	 */
	public void setPolicy(List<Policy> policy) {
		this.policy = policy;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PolicyList [policy=" + policy + "]";
	}
	
}
