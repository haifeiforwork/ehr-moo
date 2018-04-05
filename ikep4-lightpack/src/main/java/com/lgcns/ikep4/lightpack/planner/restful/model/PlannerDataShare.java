package com.lgcns.ikep4.lightpack.planner.restful.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="share")
public class PlannerDataShare {

	private String shareUserId;
	private String shareUserName;
	private String shareUserDept;
	private String shareUserTitle;
	
	public PlannerDataShare(String shareUserId, String shareUserName, String shareUserDept, String shareUserTitle) {
		super();
		
		this.shareUserId = shareUserId;
		this.shareUserName = shareUserName;
		this.shareUserDept = shareUserDept;
		this.shareUserTitle = shareUserTitle;
	}

	public String getShareUserId() {
		return shareUserId;
	}
	
	public String getShareUserName() {
		return shareUserName;
	}

	public String getShareUserDept() {
		return shareUserDept;
	}
	
	public String getShareUserTitle() {
		return shareUserTitle;
	}
	
	

	public void setShareUserId(String shareUserId) {
		this.shareUserId = shareUserId;
	}

	public void setShareUserName(String shareUserName) {
		this.shareUserName = shareUserName;
	}

	public void setShareUserDept(String shareUserDept) {
		this.shareUserDept = shareUserDept;
	}
	
	public void setShareUserTitle(String shareUserTitle) {
		this.shareUserTitle = shareUserTitle;
	}

}