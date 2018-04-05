/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.admin.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.framework.web.SearchCondition;
import com.lgcns.ikep4.approval.admin.model.ApprForm;
import com.lgcns.ikep4.approval.admin.search.ApprFormSearchCondition;



/**
 * TODO Javadoc주석작성
 *
 * @author wonchu
 * @version $Id: FormListDao.java 16245 2011-08-18 04:28:59Z giljae $
 */
public interface ApprAdminFormDao extends GenericDao<ApprForm, String> {
	
	
	/**
	 * apprForm 목록
	 * @param 	ApprFormSearchCondition
	 * @return	 SearchResult
	 */
	public List<ApprForm> listBySearchCondition(ApprFormSearchCondition searchCondition);
	
	/**
	 * apprForm count
	 * @param 	ApprFormSearchCondition
	 * @return 	Integer
	 */
	public Integer countBySearchCondition(ApprFormSearchCondition searchCondition);
	
	/**
	 * apprForm 문서정보 생성
	 * @param 	ApprForm
	 * @return 	void
	 */
	public void createApprContent(ApprForm apprForm);
	
	/**
	 * apprForm 문서정보 수정
	 * @param 	ApprForm
	 * @return 	void
	 */
	public void updateApprContent(ApprForm apprForm);
	
	/**
	 * apprForm 수신참조 생성
	 * @param 	Map
	 * @return 	nothing
	 */
	public void createApprReference(Map<String, String> map);
	
	/**
	 * apprForm 수신참조 삭제
	 * @param 	formId
	 * @return 	void
	 */
	public void deleteApprReference(String formId);
	
	/**
	 * apprForm 수신참조
	 * @param 	Map
	 * @return  List<ApprForm>
	 */
	public List<ApprForm> getApprReferenceList(Map<String, String> map);
	
	/**
	 * apprForm 열람권한
	 * @param 	formId
	 * @return  List<ApprForm>
	 */
	public List<ApprForm> getApprFormHistoryList(String formId);
	
	/**
	 * apprForm 수신참조
	 * @param 	Map
	 * @return  List<ApprForm>
	 */
	public List<ApprForm> getApprSystem(Map<String, String> map);
	
	/**
	 * apprForm 즐겨찾기 추가
	 * @param 	ApprForm
	 * @return 	void
	 */
	public void createApprFavorite(ApprForm apprForm);
	
	/**
	 * apprForm 즐겨찾기 삭제
	 * @param 	ApprForm
	 * @return 	void
	 */
	public void deleteApprFavorite(ApprForm apprForm);
	
	/**
	 * apprForm 즐겨찾기 목록
	 * @param 	Map
	 * @return 	List<ApprForm>
	 */
	public List<ApprForm> getApprFavoriteList(Map<String, Object> map);
	
	/**
	 * apprForm 최근 기안문서 목록
	 * @param 	Map
	 * @return 	List<ApprForm>
	 */
	public List<ApprForm> getApprRecentList(Map<String, Object> map);
}