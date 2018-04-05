/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.admin.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.framework.web.SearchCondition;
import com.lgcns.ikep4.approval.admin.dao.ApprAdminFormDao;
import com.lgcns.ikep4.approval.admin.model.ApprForm;
import com.lgcns.ikep4.approval.admin.search.ApprFormSearchCondition;


/**
 * TODO Javadoc주석작성
 *
 * @author wonchu
 * @version $Id: FormListDaoImpl.java 16245 2011-08-18 04:28:59Z giljae $
 */
@Repository
public class ApprAdminFormDaoImpl extends GenericDaoSqlmap<ApprForm, String> implements ApprAdminFormDao{
	
	private static final String NAMESPACE = "approval.admin.dao.ApprAdminForm.";
	
	/**
	 * apprForm 목록
	 * @param 	ApprFormSearchCondition
	 * @return 	SearchResult
	 */
	public List<ApprForm> listBySearchCondition(ApprFormSearchCondition searchCondition) {
		return sqlSelectForList(NAMESPACE + "listBySearchCondition", searchCondition);
	}
	
	/**
	 * apprForm count
	 * @param 	searchCondition 검색조건 모델 클래스
	 * @return 	Integer
	 */
	public Integer countBySearchCondition(ApprFormSearchCondition searchCondition) {
		Integer count = (Integer) this.sqlSelectForObject(NAMESPACE + "countBySearchCondition", searchCondition);

		return count;
	}
	
	/**
	 * apprForm 결제정보 생성
	 * @param 	ApprForm
	 * @return 	String
	 */
	public String create(ApprForm apprForm){
		sqlInsert(NAMESPACE + "create", apprForm);
		return apprForm.getFormId();
	}
	
	/**
	 * apprForm 문서정보 생성
	 * @param 	ApprForm
	 * @return 	void
	 */
	public void createApprContent(ApprForm apprForm){
		sqlInsert(NAMESPACE + "createApprContent", apprForm);
	}
	
	/**
	 * apprForm 결재정보 수정
	 * @param 	ApprForm
	 * @return 	void
	 */
	public void update(ApprForm apprForm){
		sqlUpdate(NAMESPACE + "update", apprForm);
	}
	
	/**
	 * apprForm 문서정보 수정
	 * @param 	ApprForm
	 * @return 	void
	 */
	public void updateApprContent(ApprForm apprForm){
		sqlUpdate(NAMESPACE + "updateApprContent", apprForm);
	}
	
	/**
	 * apprForm 폼상세
	 * @param 	formId
	 * @return 	ApprForm
	 */
	public ApprForm get(String formId) {
		return (ApprForm) sqlSelectForObject(NAMESPACE + "get", formId);
	}
	
	/**
	 * apprForm 참조자 생성
	 * @param 	Map
	 * @return 	nothing
	 */
	public void createApprReference(Map<String, String> map){
		sqlInsert(NAMESPACE + "createApprReference", map);
	}
	
	/**
	 * apprForm 참조자 삭제
	 * @param 	formId
	 * @return 	void
	 */
	public void deleteApprReference(String formId){
		sqlDelete(NAMESPACE + "deleteApprReference", formId);
	}
	
	/**
	 * apprForm 수신참조 리스트
	 * @param 	Map
	 * @return 	List<ApprForm>
	 */
	public List<ApprForm> getApprReferenceList(Map<String, String> map) {
		return sqlSelectForList(NAMESPACE + "getApprReferenceList", map);
	}
	
	/**
	 * apprDoc 열람권한 리스트
	 * @param 	formId
	 * @return 	List<ApprForm>
	 */
	public List<ApprForm> getApprFormHistoryList(String formId) {
		return sqlSelectForList(NAMESPACE + "getApprFormHistoryList", formId);
	}
	
	/**
	 * apprForm 수신참조 리스트
	 * @param 	Map
	 * @return 	List<ApprForm>
	 */
	public List<ApprForm> getApprSystem(Map<String, String> map) {
		return sqlSelectForList(NAMESPACE + "getApprSystem", map);
	}
	
	/**
	 * apprForm 즐겨찾기 추가
	 * @param 	ApprForm
	 * @return 	void
	 */
	public void createApprFavorite(ApprForm apprForm){
		sqlDelete(NAMESPACE + "createApprFavorite", apprForm);
	}
	
	/**
	 * apprForm 즐겨찾기 삭제
	 * @param 	ApprForm
	 * @return 	void
	 */
	public void deleteApprFavorite(ApprForm apprForm){
		sqlDelete(NAMESPACE + "deleteApprFavorite", apprForm);
	}
	
	/**
	 * apprForm 즐겨찾기 목록
	 * @param 	Map
	 * @return 	List<ApprForm>
	 */
	public List<ApprForm> getApprFavoriteList(Map<String, Object> map) {
		return sqlSelectForList(NAMESPACE + "getApprFavoriteList", map);
	}
	
	/**
	 * apprForm 최근 기안 문서 목록
	 * @param 	Map
	 * @return 	List<ApprForm>
	 */
	public List<ApprForm> getApprRecentList(Map<String, Object> map) {
		return sqlSelectForList(NAMESPACE + "getApprRecentList", map);
	}
	
	public boolean exists(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	public void remove(String id) {
		// TODO Auto-generated method stub
		
	}
}