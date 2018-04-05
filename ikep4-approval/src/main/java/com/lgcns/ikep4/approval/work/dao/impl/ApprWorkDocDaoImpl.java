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

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.framework.web.SearchCondition;
import com.lgcns.ikep4.approval.admin.model.ApprDoc;
import com.lgcns.ikep4.approval.admin.search.ApprFormSearchCondition;
import com.lgcns.ikep4.approval.work.dao.ApprWorkDocDao;


/**
 * TODO Javadoc주석작성
 *
 * @author wonchu
 * @version $Id: FormListDaoImpl.java 16245 2011-08-18 04:28:59Z giljae $
 */
@Repository
public class ApprWorkDocDaoImpl extends GenericDaoSqlmap<ApprDoc, String> implements ApprWorkDocDao{
	
	private static final String NAMESPACE = "approval.admin.dao.ApprWorkDoc.";
	
	/**
	 * 기안문 생성
	 * @param 	ApprDoc
	 * @return 	String
	 */
	public String create(ApprDoc apprDoc){
		sqlInsert(NAMESPACE + "create", apprDoc);
		return apprDoc.getFormId();
	}
	
	/**
	 * 기안문 복사
	 * @param 	Map
	 * @return 	void
	 */
	public void duplicateApprDoc(Map<String, String> map){
		sqlInsert(NAMESPACE + "duplicateApprDoc", map);
	}
	
	/**
	 * apprDoc 기안문 상세
	 * @param 	Map
	 * @return 	ApprDoc
	 */
	public ApprDoc readApprDoc(Map<String, String> map) {
		return (ApprDoc) sqlSelectForObject(NAMESPACE + "readApprDoc", map);
	}
	/**
	 * apprDoc Sub기안문 상세
	 * @param 	Map
	 * @return 	ApprDoc
	 */
	public ApprDoc readApprDocSub(Map<String, String> map) {
		return (ApprDoc) sqlSelectForObject(NAMESPACE + "readApprDocSub", map);
	}
		
	/**
	 * 기안문 이력 조회
	 * @param 	apprId
	 * @return 	List<ApprDoc>
	 */
	public List<ApprDoc> getApprDocHistoryList(String apprId) {
		return sqlSelectForList(NAMESPACE + "getApprDocHistoryList", apprId);
	}
	
	/**
	 * 기안문 수정
	 * @param 	ApprDocApprDoc
	 * @return 	void
	 */
	public void update(ApprDoc apprDoc){
		sqlUpdate(NAMESPACE + "update", apprDoc);
	}
	
	/**
	 * 기안문 삭제
	 * @param 	void
	 * @return 	void
	 */
	public void remove(String apprId) {
		sqlDelete(NAMESPACE + "remove", apprId);
	}
	
	/**
	 * 기안문 상세 생성
	 * @param 	ApprDoc
	 * @return 	void
	 */
	public void createApprDocContent(ApprDoc apprDoc){
		sqlInsert(NAMESPACE + "createApprDocContent", apprDoc);
	}
	
	/**
	 * 기안문 상세 복사
	 * @param 	Map
	 * @return 	void
	 */
	public void duplicateApprDocContent(Map<String, String> map){
		sqlInsert(NAMESPACE + "duplicateApprDocContent", map);
	}
	
	/**
	 * 기안문 상세 수정
	 * @param 	ApprDoc
	 * @return 	void
	 */
	public void updateApprDocContent(ApprDoc apprDoc){
		sqlUpdate(NAMESPACE + "updateApprDocContent", apprDoc);
	}
	
	/**
	 * 기안문 상세 삭제
	 * @param 	apprId
	 * @return 	void
	 */
	public void deleteApprDocContent(String apprId){
		sqlDelete(NAMESPACE + "deleteApprDocContent", apprId);
	}

	/**
	 * 참조자 생성
	 * @param 	Map
	 * @return 	void
	 */
	public void createApprReference(Map<String, String> map){
		sqlInsert(NAMESPACE + "createApprReference", map);
	}
	
	/**
	 * 참조자 삭제
	 * @param 	Map
	 * @return 	void
	 */
	public void deleteApprReference(String apprId){
		sqlDelete(NAMESPACE + "deleteApprReference", apprId);
	}
	
	/**
	 * 열람권한 생성
	 * @param 	Map
	 * @return 	void
	 */
	public void createApprRead(Map<String, String> map){
		sqlInsert(NAMESPACE + "createApprRead", map);
	}

	/**
	 * 열람권한 삭제
	 * @param 	Map
	 * @return 	void
	 */
	public void deleteApprRead(String apprId){
		sqlDelete(NAMESPACE + "deleteApprRead", apprId);
	}
	/**
	 * 기결재 생성
	 * @param 	Map
	 * @return 	void
	 */
	public void createApprRelations(Map<String, String> map){
		sqlInsert(NAMESPACE + "createApprRelations", map);
	}

	/**
	 * 기결재 삭제
	 * @param 	Map
	 * @return 	void
	 */
	public void deleteApprRelations(String apprId){
		sqlDelete(NAMESPACE + "deleteApprRelations", apprId);
	}
	
	/**
	 * apprDoc 열람권한 리스트
	 * @param 	Map
	 * @return 	List<ApprDoc>
	 */
	public List<ApprDoc> getApprReadList(Map<String, String> map) {
		return sqlSelectForList(NAMESPACE + "getApprReadList", map);
	}
	
	/**
	 * apprDoc 참조자 리스트
	 * @param 	Map
	 * @return 	List<ApprDoc>
	 */
	public List<ApprDoc> getApprReferenceList(Map<String, String> map) {
		return sqlSelectForList(NAMESPACE + "getApprReferenceList", map);
	}
	
	/**
	 * apprDoc 기결재 조회
	 * @param 	String
	 * @return 	List<ApprDoc>
	 */
	public List<ApprDoc> getApprRelationsList(String apprId) {
		return sqlSelectForList(NAMESPACE + "getApprRelationsList", apprId);
	}
	
	/**
	 * 상태값 변경
	 * @param 	map
	 * @return 	void
	 */
	public void updateApprDocStatus(ApprDoc apprDoc){
		sqlUpdate(NAMESPACE + "updateApprDocStatus", apprDoc);
	}

	/**
	 * 부서 수신 문서 접수자 처리(전결,내부결재진행)
	 */
	public void updateReceiverApprDocStatus(ApprDoc apprDoc){
		sqlUpdate(NAMESPACE + "updateReceiverApprDocStatus", apprDoc);
	}
	
	
	public ApprDoc get(String id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean exists(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	public List<ApprDoc> subList(Map<String, String> map) {
		return sqlSelectForList(NAMESPACE + "subList", map);
	}
	/**
	 * 기안문 원본 + Sub 문서 목록
	 * @param map
	 * @return
	 */
	public List<ApprDoc> listApprDoc(Map<String, String> map) {
		return sqlSelectForList(NAMESPACE + "listApprDoc", map);
	}	
	
	/**
	 * 참조권한
	 * @param map
	 * @return
	 */	
	public boolean getApprReferenceCount(Map<String, String> map){
		Integer count = (Integer) this.sqlSelectForObject(NAMESPACE + "getApprReferenceCount", map);
		return count>0;
	}
	
	/**
	 * 열람권한
	 * @param map
	 * @return
	 */	
	public boolean getApprReadCount(Map<String, String> map){
		Integer count = (Integer) this.sqlSelectForObject(NAMESPACE + "getApprReadCount", map);
		return count>0;
	}
	
	/**
	 * 해당 조회조건 문서의 Max 값 구하기
	 * @param map
	 * @return
	 */	
	public int getApprDocMaxNo(String searchDocNo){
		Integer count = (Integer) this.sqlSelectForObject(NAMESPACE + "getApprDocMaxNo", searchDocNo);
		if(count==null)
			return 0;
		else 
			return count;
	}
}