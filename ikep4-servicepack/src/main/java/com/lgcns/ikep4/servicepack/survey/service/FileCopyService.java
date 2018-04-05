package com.lgcns.ikep4.servicepack.survey.service;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.framework.core.service.GenericService;


/**
 * 
 * file copy
 *
 * @author 고인호(ihko11@daum.net)
 * @version $Id: FileCopyService.java 16244 2011-08-18 04:11:42Z giljae $
 */
@Transactional
public interface FileCopyService extends GenericService<FileData, String> {

	/**
	 * 파일 업로드 처리
	 * 
	 * @param fileList
	 * @param itemId
	 * @param type
	 * @return @
	 */

	/**
	 * 파일 연결 정보 저장 및 삭제
	 * 
	 * @param fileList
	 */
	public FileData clone(String fileId);

}
