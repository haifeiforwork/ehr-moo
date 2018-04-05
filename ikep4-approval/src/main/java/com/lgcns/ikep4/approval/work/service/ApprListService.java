/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.work.service;

import com.lgcns.ikep4.approval.work.model.ApprList;
import com.lgcns.ikep4.approval.work.search.ApprListSearchCondition;
import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;

/**
 * 기안암 목록
 *
 * @author jeehye
 * @version $Id: ApprEntrustService.java 16234 2011-08-18 02:44:36Z giljae $
 */
public interface ApprListService extends GenericService<ApprList, String> {
	
	/**
	 *기안한 문서
	 * @param searchCondition 모델
	 * @return  SearchResult<ApprList>
	 */
	public SearchResult<ApprList> listByMyRequest(ApprListSearchCondition searchCondition);
	
	/**
	 * 결제함 목록 
	 * @param searchCondition 모델
	 * @return  SearchResult<ApprList>
	 */
	public SearchResult<ApprList> listBySearchCondition(ApprListSearchCondition searchCondition);
	
	/**
	 * 결제함 목록 갯수
	 * @param searchCondition 모델
	 * @return  SearchResult<ApprList>
	 */
	public Integer countBySearchCondition(ApprListSearchCondition searchCondition);
	
	/**
	 * 이름가져오기
	 * @param userId
	 * @return  String
	 */
	public String getUserName(String userId);
	
	/**
	 * 참조문서
	 * @param searchCondition 모델
	 * @return  SearchResult<ApprList>
	 */
	public SearchResult<ApprList> listBySearchConditionRef(ApprListSearchCondition searchCondition);
	
	/**
	 * 열람문서
	 * @param searchCondition 모델
	 * @return  SearchResult<ApprList>
	 */
	public SearchResult<ApprList> listBySearchConditionRead(ApprListSearchCondition searchCondition);
	
	
	/**
	 * 통합결재함
	 * @param searchCondition 모델
	 * @return  SearchResult<ApprList>
	 */
	public SearchResult<ApprList> listBySearchConditionIntegrate(ApprListSearchCondition searchCondition);
	
	/**
	 * 부서수신함/부서결재함
	 * @param searchCondition 모델
	 * @return  SearchResult<ApprList>
	 */
	public SearchResult<ApprList> listBySearchConditionDeptRec(ApprListSearchCondition searchCondition);
	
	/**
	 * 부서수신함 목록 갯수
	 * @param searchCondition 모델
	 * @return  SearchResult<ApprList>
	 */
	public Integer countBySearchConditionDeptRec(ApprListSearchCondition searchCondition);

	
	/**
	 * 검토요청함
	 * @param searchCondition 모델
	 * @return  SearchResult<ApprList>
	 */
	public SearchResult<ApprList> listBySearchConditionExam(ApprListSearchCondition searchCondition);
	
	/**
	 * 검토요청함 목록 갯수
	 * @param searchCondition 모델
	 * @return  SearchResult<ApprList>
	 */
	public Integer countBySearchConditionExam(ApprListSearchCondition searchCondition);
	
	/**
	 * 위임문서
	 * @param searchCondition 모델
	 * @return  SearchResult<ApprList>
	 */
	public SearchResult<ApprList> listBySearchConditionLineEntrust(ApprListSearchCondition searchCondition);
	
	/**
	 * 개인보관함
	 * @param searchCondition 모델
	 * @return  SearchResult<ApprList>
	 */
	public SearchResult<ApprList> listBySearchConditionUserDoc(ApprListSearchCondition searchCondition);
	
	
	/**
	 * 보안설정 및 해제
	 * @param apprId
	 * @param isHidden
	 * @return  String
	 */
	void updateSetSecurity(String apprId, String isHidden);

}
