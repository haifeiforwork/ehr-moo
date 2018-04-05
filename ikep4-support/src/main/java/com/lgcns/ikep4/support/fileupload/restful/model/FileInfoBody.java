package com.lgcns.ikep4.support.fileupload.restful.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="body")
public class FileInfoBody {
	
	@XmlElement(name="FileInfo")
	public FileInfoReturnData fileInfoReturnData;

	public FileInfoBody() {}
	
	public FileInfoBody(FileInfoReturnData fileInfoReturnData) {
		this.fileInfoReturnData = fileInfoReturnData;
	}
}