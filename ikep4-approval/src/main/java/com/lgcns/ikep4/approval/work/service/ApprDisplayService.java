/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.work.service;

import java.util.Map;

import com.lgcns.ikep4.approval.work.model.ApprDisplay;
import com.lgcns.ikep4.approval.work.search.ApprDisplaySearchCondition;
import com.lgcns.ikep4.approval.work.search.ApprListSearchCondition;
import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;

/**
 * 공람지정 서비스
 *
 * @author jeehye
 * @version $Id: ApprDisplayService.java 16234 2011-08-18 02:44:36Z jeehye $
 */
public interface ApprDisplayService extends GenericService<ApprDisplay, String> {
	
	/**
	 * 공람지정 저장
	 * @param ApprDisplay 모델
	 * @return  void
	 */
	public void createApprDisplay(ApprDisplay apprDisplay);
	
	/**
	 * 공람지정 목록
	 * @param searchCondition
	 * @return
	 */
	public SearchResult<ApprDisplay> listBySearchCondition(ApprDisplaySearchCondition searchCondition);
	
	/**
	 * 공람지정 목록
	 * @param searchCondition
	 * @return
	 */
	public SearchResult<ApprDisplay> listByDisplaySearchCondition(ApprDisplaySearchCondition searchCondition);
	
	/**
	 * 부서수신함 목록 갯수
	 * @param searchCondition 모델
	 * @return  SearchResult<ApprList>
	 */
	public Integer countByDisplaySearchCondition(ApprDisplaySearchCondition searchCondition);
	
	/**
	 * 공람지정 회수
	 * @param 	displayId
	 * @param 	apprId
	 * @return 	void
	 */
	public void deleteApprDisplay(String userId,String apprId);
	
	/**
	 * 공람확인 여부
	 * @param 	userId
	 * @param 	apprId
	 * @return 	void
	 */
	public String getApprDisplayId(String userId,String apprId);
	
	/**
	 * 공람 확인 
	 * @param displayId
	 * @param userId
	 * @return  void
	 */
	public void updateApprDisplaySub(String displayId, String userId);
	
	/**
	 * 공람 버튼 
	 * @param apprId
	 * @return  void
	 */
	public Integer countByDisplayUserStatus(String apprId);
	
	/**
	 * 공람지정 문서여부
	 * @param apprId
	 * @return  void
	 */
	public boolean existDisplayDoc (Map map);
	
	/**
	 * 공람지정 대상자
	 * @param apprId
	 * @return  void
	 */
	public boolean existDisplayUser (Map map);
	
}
