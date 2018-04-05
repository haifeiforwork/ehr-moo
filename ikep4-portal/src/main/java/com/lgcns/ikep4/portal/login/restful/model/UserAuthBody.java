package com.lgcns.ikep4.portal.login.restful.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="body")
public class UserAuthBody {
	
	@XmlElement(name="AuthInfo")
	public UserAuthReturnData userAuthReturnData;
	
	public UserAuthBody() {}
	
	public UserAuthBody(UserAuthReturnData userAuthReturnData) {
		this.userAuthReturnData = userAuthReturnData;
	}
}