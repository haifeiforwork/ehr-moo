/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.fileupload.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.support.fileupload.model.FileData;


/**
 * 파일 Dao
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: FileDao.java 16258 2011-08-18 05:37:22Z giljae $
 */
public interface FileDao extends GenericDao<FileData, String> {

	/**
	 * ItemId에 연결된 파일 리스트 조회
	 * 
	 * @param itemId
	 * @return
	 */
	public List<FileData> getItemFile(String itemId, String editorYn);
	
	public List<FileData> getItemFileEcm(String itemId, String editorYn);
	
	public List<FileData> getItemFileList(String itemId, String itemType, String editorYn);
	
	public String createEcmFile(FileData file);

	/**
	 * 썸네일 변환 배치처리를 위한 리스트 조회
	 * 
	 * @param itemId
	 * @return
	 */
	public List<FileData> getAllForThumbnailBatch();

	/**
	 * 파일 삭제 배치처리를 위한 리스트 조회
	 * 
	 * @param itemId
	 * @return
	 */
	public List<FileData> getAllForFileDeleteBatch();

	/**
	 * 다운로드 히스토리 저장
	 * 
	 * @param file
	 * @return
	 */
	public String createDownloadHistroy(FileData file);

	/**
	 * 프로파일 이미지 파일 검색
	 * 
	 * @param userId
	 * @return
	 */
	public FileData selectProfileImage(String userId);

	/**
	 * 프로파일 이미지 경로 저장
	 * 
	 * @param file
	 */
	public void updateProfileImage(FileData file);
	
	public FileData getFileInfo(String fileId);
	
	public void updateEcmFileName(FileData file);
	
	public void updateFileName(FileData file);

}
