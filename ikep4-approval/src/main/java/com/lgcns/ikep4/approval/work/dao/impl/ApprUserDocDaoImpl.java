/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.work.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.approval.work.dao.ApprUserDocDao;
import com.lgcns.ikep4.approval.work.model.ApprUserDocFolder;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;


/**
 * 개인보관함 DAO 구현
 * 
 * @author jeehye
 * @version $Id: ApprDisplayDaoImpl.java 16234 2011-08-18 02:44:36Z giljae $
 */
@Repository("apprUserDocDao")
public class ApprUserDocDaoImpl extends GenericDaoSqlmap<ApprUserDocFolder, String> implements ApprUserDocDao {

	private static final String NAMESPACE = "com.lgcns.ikep4.approval.work.dao.ApprUserDoc.";

	public String create(ApprUserDocFolder arg0) { 
		// TODO Auto-generated method stub
		return null;
	}

	public boolean exists(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public ApprUserDocFolder get(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public void remove(String arg0) {
		// TODO Auto-generated method stub
		
	}

	public void update(ApprUserDocFolder arg0) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 하위 시스템 개수
	 * @param param Map(systemCode:부모 시스템 코드, portalId:포탈 아이디)
	 * @return int 하위 시스템 개수
	 */
	public int getChildCount(Map<String, String> param) {
		
		int count = 0;

		count = (Integer) sqlSelectForObject(NAMESPACE + "getChildCount", param);
		
		return count;
		
	}
	
	/**
	 * 포탈 시스템 별 관리 트리 목록
	 * @param param Map(portalId:포탈 아이디, 사용자 아이디)
	 * @return List<ApprUserDocFolder> 포탈 시스템 별 관리 트리 목록
	 */
	public List<ApprUserDocFolder> treeList(Map<String, String> param) {
		
		List<ApprUserDocFolder> list = (List<ApprUserDocFolder>) this.sqlSelectForList(NAMESPACE + "treeList", param);
		
		return list;
		
	}
	
	/**
	 * 포탈 시스템 별 관리 트리 목록
	 * @param param Map(portalId:포탈 아이디, 사용자 아이디)
	 * @return List<ApprUserDocFolder> 포탈 시스템 별 관리 트리 목록
	 */
	public List<ApprUserDocFolder> treeChildrenList(Map<String, String> param) {
		
		List<ApprUserDocFolder> list = (List<ApprUserDocFolder>) this.sqlSelectForList(NAMESPACE + "treeChildrenList", param);
		
		return list;
		
	}
	
	/**
	 * 폴더저장
	 * @param ApprUserDocFolder
	 */
	public void createFolder(ApprUserDocFolder apprUserDocFolder){
		this.sqlInsert(NAMESPACE + "createFolder", apprUserDocFolder);
	}
	
	/**
	 * 폴더수정
	 * @param ApprUserDocFolder
	 */
	public void updateFolder(ApprUserDocFolder apprUserDocFolder){
		this.sqlInsert(NAMESPACE + "updateFolder", apprUserDocFolder);
	}
	
	/**
	 * 폴더정보 
	 * @param folderId
	 * @return ApprUserDocFolder
	 */
	public ApprUserDocFolder getFolderInfo(String folderId) {
		
		return (ApprUserDocFolder) this.sqlSelectForObject(NAMESPACE + "getFolderInfo", folderId);
		
	}
	
	/**
	 * 폴더에 문서  저장
	 * @param ApprUserDocFolder
	 */
	public void createApprUserDoc(ApprUserDocFolder apprUserDocFolder){
		this.sqlInsert(NAMESPACE + "createApprUserDoc", apprUserDocFolder);
	}
	
	/**
	 * 폴더에 문서 삭제
	 * @param 	Map
	 * @return 	void
	 */
	public void deleteApprUserDoc(Map<String, String> param){
		sqlDelete(NAMESPACE + "deleteApprUserDoc", param);
	}
	
	/**
	 * 폴더에 삭제
	 * @param 	Map
	 * @return 	void
	 */
	public void deleteApprDocFolder(String folderId){
		sqlDelete(NAMESPACE + "deleteApprDocFolder", folderId);
	}
	
	/**
	 * 폴더 이동
	 */
	public void updateMove(ApprUserDocFolder apprUserDocFolder) {
		sqlUpdate(NAMESPACE + "updateMove", apprUserDocFolder);
	}

	/**
	 * 폴더 순서 수정
	 */
	public void updateSortOderIncrease(ApprUserDocFolder apprUserDocFolder) {
		sqlUpdate(NAMESPACE + "updateSortOderIncrease", apprUserDocFolder);

	}

	/**
	 * 폴더 순서 수정
	 */
	public void updateSortOderDecrease(ApprUserDocFolder apprUserDocFolder) {
		sqlUpdate(NAMESPACE + "updateSortOderDecrease", apprUserDocFolder);

	}
	
	/**
	 * 개인보관함에 존재하는 체크
	 */
	public boolean checkExist(Map<String, String> param) {
		Integer count = (Integer) sqlSelectForObject(NAMESPACE + "checkExist", param);
		return count > 0;
	}
	
	/**
	 * 폴더명 존재하는 체크
	 */
	public boolean existFolderName(Map<String, String> param) {
		Integer count = (Integer) sqlSelectForObject(NAMESPACE + "existFolderName", param);
		return count > 0;
	}
	
	
}
