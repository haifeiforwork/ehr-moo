package com.lgcns.ikep4.support.user.restful.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.lgcns.ikep4.support.user.restful.model.ProfileInfoReturnData;

@XmlRootElement(name="body")
public class ProfileInfoBody {
	
	@XmlElement(name="AddrInfo")
	public ProfileInfoReturnData profileInfoReturnData;
	
	public ProfileInfoBody() {}
	
	public ProfileInfoBody(ProfileInfoReturnData profileInfoReturnData) {
		this.profileInfoReturnData = profileInfoReturnData;
	}
}