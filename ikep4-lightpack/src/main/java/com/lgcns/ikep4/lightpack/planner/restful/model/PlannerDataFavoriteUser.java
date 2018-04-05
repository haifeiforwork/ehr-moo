package com.lgcns.ikep4.lightpack.planner.restful.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="favoriteUser")
public class PlannerDataFavoriteUser {

	private String userId;
	private String userName;
	private String userDept;
	private String userTitle;
	
	public PlannerDataFavoriteUser(String userId, String userName, String userDept, String userTitle) {
		super();
		
		this.userId = userId;
		this.userName = userName;
		this.userDept = userDept;
		this.userTitle = userTitle;
	}

	public String getUserId() {
		return userId;
	}
	
	public String getUserName() {
		return userName;
	}

	public String getUserDept() {
		return userDept;
	}
	
	public String getUserTitle() {
		return userTitle;
	}
	
	

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setUserDept(String userDept) {
		this.userDept = userDept;
	}
	
	public void setUserTitle(String userTitle) {
		this.userTitle = userTitle;
	}

}