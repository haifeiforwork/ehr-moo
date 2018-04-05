package com.lgcns.ikep4.collpack.kms.restful.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="body")
public class KmsPermissionBody {
	
	@XmlElement(name="PermitInfo")
	public KmsPermissionReturnData kmsPermissionReturnData;

	public KmsPermissionBody() {}
	
	public KmsPermissionBody(KmsPermissionReturnData kmsPermissionReturnData) {
		this.kmsPermissionReturnData = kmsPermissionReturnData;
	} 
	
}