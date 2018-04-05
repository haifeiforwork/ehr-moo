package com.lgcns.ikep4.portal.login.restful.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="body")
public class UserInfoBody {
	
	@XmlElement(name="UserInfo")
	public UserInfoReturnData userInfoReturnData;
	
	@XmlElement(name="MailSettingInfo")
	public MailSettingInfoReturnData mailSettingInfoReturnData;
	
	public UserInfoBody() {}
	
	public UserInfoBody(UserInfoReturnData userInfoReturnData, MailSettingInfoReturnData mailSettingInfoReturnData) {
		this.userInfoReturnData = userInfoReturnData;
		this.mailSettingInfoReturnData = mailSettingInfoReturnData;
	}
}