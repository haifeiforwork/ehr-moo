/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.admin.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.group.model.Group;
import com.lgcns.ikep4.approval.admin.model.ApprForm;
import com.lgcns.ikep4.approval.admin.search.ApprFormSearchCondition;

/**
 * WorkPlace List 서비스
 *
 * @author wonchu
 * @version $Id: ApprAdminFormListService.java 16245 2011-08-18 04:28:59Z giljae $
 */
@Transactional
public interface ApprAdminFormService extends GenericService<ApprForm, String> {

	/**
	 * apprForm 목록
	 * @param 	apprFormSearchCondition
	 * @return 	SearchResult
	 */
	public SearchResult<ApprForm> listBySearchCondition(ApprFormSearchCondition apprFormSearchCondition);
	
	/**
	 * apprForm 생성
	 * @param 	ApprForm
	 * @return 	String
	 */
	public String createApprForm(ApprForm apprForm);
	
	/**
	 * apprForm 결재정보 수정
	 * @param 	ApprForm
	 * @return 	void
	 */
	public void updateApprInfoForm(ApprForm apprForm);
	
	/**
	 * apprForm 문서정보 수정
	 * @param 	apprForm, user
	 * @return 	void
	 */
	public void updateApprContentForm(ApprForm apprForm, User user);
	
	/**
	 * apprForm 폼상세
	 * @param 	formId
	 * @return 	ApprForm
	 */
	public ApprForm readApprForm(String formId);
	
	/**
	 * apprForm 폼상세
	 * @param 	Map
	 * @return 	List<ApprForm>
	 */
	public List<ApprForm> getApprSystem(Map<String, String> map);
	
	/**
	 * apprForm 수신참조자 조회
	 * @param 	formId, locale
	 * @return 	List<User>
	 */
	public List<User> getApprReferenceList(String formId, String localeCode);
	
	/**
	 * apprDoc 열람권한 그룹 조회
	 * @param 	formId
	 * @return 	List<ApprForm>
	 */
	public List<ApprForm> getApprFormHistoryList(String formId);
	
	
	/**
	 * apprForm 그룹 조회
	 * @param 	code, localeCode
	 * @return 	Group
	 */
	public Group getGroup(String code, String localeCode);
	
	/**
	 * apprForm 사용자 조회
	 * @param 	id, localeCode
	 * @return 	User
	 */
	public User getUser(String id, String localeCode);
	
	/**
	 * apprForm 즐겨찾기 
	 * @param 	Map
	 * @return 	void
	 */
	public void setApprFavorite(ApprForm apprForm);
	
	/**
	 * apprForm 즐겨찾기 목록
	 * @param 	Map
	 * @return 	List<ApprForm>
	 */
	public List<ApprForm> getApprFavoriteList(Map<String, Object> map);
	
	/**
	 * apprForm 최근 기안 문서 목록
	 * @param 	Map
	 * @return 	List<ApprForm>
	 */
	public List<ApprForm> getApprRecentList(Map<String, Object> map);
}