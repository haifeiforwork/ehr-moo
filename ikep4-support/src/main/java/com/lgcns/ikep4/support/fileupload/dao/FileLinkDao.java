/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.fileupload.dao;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.support.fileupload.model.FileLink;


/**
 * 파일 링크 Dao
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: FileLinkDao.java 16258 2011-08-18 05:37:22Z giljae $
 */
public interface FileLinkDao extends GenericDao<FileLink, String> {

	public String createEcmFileLink(FileLink fileLink); 
	
	public void removeEcmFileLink(FileLink fileLink);
	
	public void removeFileLink(FileLink fileLink);
}

