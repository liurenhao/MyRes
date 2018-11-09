package org.liuhao.pojo;

import org.liuhao.annotation.Doc;

@Doc(path="/gen/Root/PolicyList/Policy")
public class Policy {
	String proposalno;
	String premium;
	String policyType;
	String amount;
	String insuredName;
	
	Person person;
	
	/**
	 * @return the proposalno
	 */
	public String getProposalno() {
		return proposalno;
	}
	/**
	 * @return the premium
	 */
	public String getPremium() {
		return premium;
	}
	/**
	 * @return the policyType
	 */
	public String getPolicyType() {
		return policyType;
	}
	/**
	 * @return the amount
	 */
	public String getAmount() {
		return amount;
	}
	/**
	 * @return the insuredName
	 */
	public String getInsuredName() {
		return insuredName;
	}
	/**
	 * @param proposalno the proposalno to set
	 */
	public void setProposalno(String proposalno) {
		this.proposalno = proposalno;
	}
	/**
	 * @param premium the premium to set
	 */
	public void setPremium(String premium) {
		this.premium = premium;
	}
	/**
	 * @param policyType the policyType to set
	 */
	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}
	/**
	 * @param insuredName the insuredName to set
	 */
	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}
	/**
	 * @return the person
	 */
	public Person getPerson() {
		return person;
	}
	/**
	 * @param person the person to set
	 */
	public void setPerson(Person person) {
		this.person = person;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Policy [proposalno=" + proposalno + ", premium=" + premium + ", policyType=" + policyType + ", amount="
				+ amount + ", insuredName=" + insuredName + ", person=" + person + "]";
	}
	
}
