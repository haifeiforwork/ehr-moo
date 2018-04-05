package com.lgcns.ikep4.portal.login.restful.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="returnData")
public class UserAuthReturnData {

	private String isSuccessAuth;

	public String getIsSuccessAuth() {
		return isSuccessAuth;
	}
	public void setIsSuccessAuth(String isSuccessAuth) {
		this.isSuccessAuth = isSuccessAuth;
	}
	
}