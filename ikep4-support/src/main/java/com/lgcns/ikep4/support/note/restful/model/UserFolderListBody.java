package com.lgcns.ikep4.support.note.restful.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.lgcns.ikep4.support.note.restful.model.UserFolderListReturnData;

@XmlRootElement(name="body")
public class UserFolderListBody {
	
	@XmlElement(name="UserFolderList")
	public List<UserFolderListReturnData> userFolderListReturnData;
	
	public UserFolderListBody() {}
	
	public UserFolderListBody(List<UserFolderListReturnData> userFolderListReturnData) {
		this.userFolderListReturnData = userFolderListReturnData;
	}
}