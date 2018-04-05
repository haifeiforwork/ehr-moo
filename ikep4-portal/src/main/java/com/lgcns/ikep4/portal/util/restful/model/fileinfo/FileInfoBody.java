/* 
 * Copyright (C) 2013 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.util.restful.model.fileinfo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * FileInfoReturnData Body
 * 
 * @Useror jmjeon
 * @since 2013. 2. 4. 오후 6:51:30
 * @version 1.0.0 
 */
@XmlRootElement(name="body")
public class FileInfoBody {
	
	@XmlElement(name="FileInfo")
	public FileInfoReturnData fileInfoReturnData;
	
	public FileInfoBody() {}

	public FileInfoBody(FileInfoReturnData fileInfoReturnData) {

		// set FileInfoReturnData
		if(fileInfoReturnData != null) {
			this.fileInfoReturnData = fileInfoReturnData;
		}
		else {
			this.fileInfoReturnData = new FileInfoReturnData();
		}
	} 
	
}
