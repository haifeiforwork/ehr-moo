package com.lgcns.ikep4.collpack.kms.restful.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="returnData")
public class KmsPermissionReturnData {
	
	private String isAdminPermit = "";
	
	private String isReadPermit = "";
	
	public String getIsAdminPermit() {
		return isAdminPermit;
	}
	public void setIsAdminPermit(String isAdminPermit) {
		this.isAdminPermit = isAdminPermit;
	}
	public String getIsReadPermit() {
		return isReadPermit;
	}
	public void setIsReadPermit(String isReadPermit) {
		this.isReadPermit = isReadPermit;
	}
	
}