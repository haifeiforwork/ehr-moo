package com.lgcns.ikep4.support.note.restful.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.lgcns.ikep4.support.note.restful.model.ShareFolderListReturnData;

@XmlRootElement(name="body")
public class ShareFolderListBody {
	
	@XmlElement(name="ShareFolderList")
	public List<ShareFolderListReturnData> shareFolderListReturnData;
	
	public ShareFolderListBody() {}
	
	public ShareFolderListBody(List<ShareFolderListReturnData> shareFolderListReturnData) {
		this.shareFolderListReturnData = shareFolderListReturnData;
	}
}