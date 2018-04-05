package com.lgcns.ikep4.portal.login.restful.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="body")
public class UserInfoMobileBody {
	
	@XmlElement(name="UserInfo")
	public UserInfoMobileReturnData userInfoMobileReturnData;
	
	public UserInfoMobileBody() {}
	
	public UserInfoMobileBody(UserInfoMobileReturnData userInfoMobileReturnData) {
		this.userInfoMobileReturnData = userInfoMobileReturnData;
	}
}