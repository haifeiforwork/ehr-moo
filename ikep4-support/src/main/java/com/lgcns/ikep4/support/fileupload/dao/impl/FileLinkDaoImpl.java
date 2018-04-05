package com.lgcns.ikep4.support.fileupload.dao.impl;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.fileupload.dao.FileLinkDao;
import com.lgcns.ikep4.support.fileupload.model.FileLink;


/**
 * 파일업로드링크 저장 DAO
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: FileLinkDaoImpl.java 16258 2011-08-18 05:37:22Z giljae $
 */
@Repository
public class FileLinkDaoImpl extends GenericDaoSqlmap<FileLink, String> implements FileLinkDao {

	/**
	 * 파일링크 쿼리 네임스페이스
	 */
	private static final String NAMESPACE = "support.fileupload.filelink.";

	/**
	 * 파일링크 등록
	 */
	public String create(FileLink fileLink) {
		return (String) sqlInsert(NAMESPACE + "insert", fileLink);

	}
	
	public String createEcmFileLink(FileLink fileLink) {
		return (String) sqlInsert(NAMESPACE + "insertEcmFileLink", fileLink);

	}

	/**
	 * 파일링크 삭제
	 */
	public void remove(String fileId) {
		sqlDelete(NAMESPACE + "delete", fileId);
	}
	
	public void removeEcmFileLink(FileLink fileLink) {
		sqlDelete(NAMESPACE + "deleteEcmFileLink", fileLink);
	}
	
	public void removeFileLink(FileLink fileLink) {
		sqlDelete(NAMESPACE + "deleteFileLink", fileLink);
	}

	/**
	 * 파일링크 조회
	 */
	public FileLink get(String fileId) {
		return (FileLink) sqlSelectForObject(NAMESPACE + "select", fileId);
	}

	public boolean exists(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	public void update(FileLink object) {
		// TODO Auto-generated method stub

	}

}
