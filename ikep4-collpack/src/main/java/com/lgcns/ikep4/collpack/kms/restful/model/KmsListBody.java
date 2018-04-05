package com.lgcns.ikep4.collpack.kms.restful.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="body")
public class KmsListBody {
	
	@XmlElement(name="KmsList")
	public List<KmsListReturnData> kmsListReturnData;

	public KmsListBody() {}
	
	public KmsListBody(List<KmsListReturnData> kmsListReturnData) {
		this.kmsListReturnData = kmsListReturnData;
	} 
	
}