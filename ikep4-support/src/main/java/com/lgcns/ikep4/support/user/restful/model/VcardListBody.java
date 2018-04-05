package com.lgcns.ikep4.support.user.restful.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.lgcns.ikep4.support.user.restful.model.VcardListReturnData;

@XmlRootElement(name="body")
public class VcardListBody {
	
	@XmlElement(name="VcardUserList")
	public List<VcardListReturnData> vcardListReturnData;
	
	public VcardListBody() {}
	
	public VcardListBody(List<VcardListReturnData> vcardListReturnData) {
		this.vcardListReturnData = vcardListReturnData;
	}
}