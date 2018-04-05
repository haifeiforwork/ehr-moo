package com.lgcns.ikep4.support.fileupload.service;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.support.fileupload.model.FileCheck;


/**
 * 파일권한체크 처리 서비스
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: FileCheckService.java 16273 2011-08-18 07:07:47Z giljae $
 */
@Transactional
public interface FileCheckService extends GenericService<FileCheck, String> {

	/**
	 * 파일권한체크 처리
	 * 
	 * @param fileId
	 * @param itemId
	 * @param itemTypeCode
	 * @param userId
	 * @return
	 */
	public boolean checkFile(String fileId, String itemId, String itemTypeCode, String userId);

}
