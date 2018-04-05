package com.lgcns.ikep4.lightpack.planner.restful.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="reference")
public class PlannerDataReference {

	private String referUserId;
	private String referUserName;
	private String referUserDept;
	private String referUserTitle;
	
	public PlannerDataReference(String referUserId, String referUserName, String referUserDept, String referUserTitle) {
		super();
		
		this.referUserId = referUserId;
		this.referUserName = referUserName;
		this.referUserDept = referUserDept;
		this.referUserTitle = referUserTitle;
	}

	public String getReferUserId() {
		return referUserId;
	}
	
	public String getReferUserName() {
		return referUserName;
	}

	public String getReferUserDept() {
		return referUserDept;
	}
	
	public String getReferUserTitle() {
		return referUserTitle;
	}
	
	

	public void setReferUserId(String referUserId) {
		this.referUserId = referUserId;
	}

	public void setReferUserName(String referUserName) {
		this.referUserName = referUserName;
	}

	public void setReferUserDept(String referUserDept) {
		this.referUserDept = referUserDept;
	}
	
	public void setReferUserTitle(String referUserTitle) {
		this.referUserTitle = referUserTitle;
	}

}