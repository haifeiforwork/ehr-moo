package com.lgcns.ikep4.support.fileupload.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.fileupload.dao.FileDao;
import com.lgcns.ikep4.support.fileupload.model.FileData;


/**
 * 파일업로드 저장 DAO
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: FileDaoImpl.java 16258 2011-08-18 05:37:22Z giljae $
 */
@Repository
public class FileDaoImpl extends GenericDaoSqlmap<FileData, String> implements FileDao {

	/**
	 * 파일 쿼리 네임스페이스
	 */
	private static final String NAMESPACE = "support.fileupload.file.";

	/**
	 * 파일 등록
	 */
	public String create(FileData file) {
		return (String) sqlInsert(NAMESPACE + "insert", file);
	}
	
	public String createEcmFile(FileData file) {
		return (String) sqlInsert(NAMESPACE + "insertEcmFile", file);
	}

	/**
	 * 파일 삭제
	 */
	public void remove(String fileId) {
		sqlDelete(NAMESPACE + "delete", fileId);
	}

	/**
	 * 파일 조회
	 */
	public FileData get(String fileId) {
		return (FileData) sqlSelectForObject(NAMESPACE + "select", fileId);
	}

	/**
	 * 프로파일 파일 조회
	 */
	public FileData selectProfileImage(String userId) {
		return (FileData) sqlSelectForObject(NAMESPACE + "selectProfileImage", userId);
	}

	public boolean exists(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	public void update(FileData object) {
		// TODO Auto-generated method stub

	}

	/**
	 * 아이템에 연결된 파일 조회
	 */
	public List<FileData> getItemFile(String itemId, String editorYn) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("itemId", itemId);
		if (editorYn.equals("Y")) {
			map.put("editorYn", "1");
		} else if (editorYn.equals("N")) {
			map.put("editorYn", "0");
		} else {
			map.put("editorYn", "");
		}
		return (List<FileData>) sqlSelectForList(NAMESPACE + "selectItemFile", map);
	}
	
	public List<FileData> getItemFileEcm(String itemId, String editorYn) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("itemId", itemId);
		if (editorYn.equals("Y")) {
			map.put("editorYn", "1");
		} else if (editorYn.equals("N")) {
			map.put("editorYn", "0");
		} else {
			map.put("editorYn", "");
		}
		return (List<FileData>) sqlSelectForList(NAMESPACE + "selectItemFileEcm", map);
	}
	
	public List<FileData> getItemFileList(String itemId, String itemType, String editorYn) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("itemId", itemId);
		map.put("itemType", itemType);
		if (editorYn.equals("Y")) {
			map.put("editorYn", "1");
		} else if (editorYn.equals("N")) {
			map.put("editorYn", "0");
		} else {
			map.put("editorYn", "");
		}
		return (List<FileData>) sqlSelectForList(NAMESPACE + "selectItemFileList", map);
	}

	/**
	 * 섬네일변환 배치처리를 위한 조회
	 */
	public List<FileData> getAllForThumbnailBatch() {
		return (List<FileData>) sqlSelectForList(NAMESPACE + "selectAllForThumbnailBatch", "");
	}

	/**
	 * 파일삭제 배치처리를 위한 조회
	 */
	public List<FileData> getAllForFileDeleteBatch() {
		return (List<FileData>) sqlSelectForList(NAMESPACE + "selectAllForFileDeleteBatch", "");
	}

	/**
	 * 다운로드 히스토리 등록
	 */
	public String createDownloadHistroy(FileData file) {
		return (String) sqlInsert(NAMESPACE + "insertDownloadHistory", file);
	}

	public void updateProfileImage(FileData file) {
		sqlUpdate(NAMESPACE + "updateProfileImage", file);
	}

	public FileData getFileInfo(String fileId) {
		return (FileData) sqlSelectForObject(NAMESPACE + "getFileInfo", fileId);
	}
	
	public void updateEcmFileName(FileData file) {
		sqlUpdate(NAMESPACE + "updateEcmFileName", file);
	}
	
	public void updateFileName(FileData file) {
		sqlUpdate(NAMESPACE + "updateFileName", file);
	}

}
