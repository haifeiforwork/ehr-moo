package com.lgcns.ikep4.support.user.restful.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.lgcns.ikep4.support.user.restful.model.VcardFolderListReturnData;

@XmlRootElement(name="body")
public class VcardFolderListBody {
	
	@XmlElement(name="VcardGroupList")
	public List<VcardFolderListReturnData> vcardFolderListReturnData;
	
	public VcardFolderListBody() {}
	
	public VcardFolderListBody(List<VcardFolderListReturnData> vcardFolderListReturnData) {
		this.vcardFolderListReturnData = vcardFolderListReturnData;
	}
}