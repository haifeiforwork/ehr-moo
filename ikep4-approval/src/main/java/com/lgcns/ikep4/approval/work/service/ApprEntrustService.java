/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.work.service;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.approval.work.model.ApprEntrust;
import com.lgcns.ikep4.approval.work.search.ApprListSearchCondition;
import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;


/**
 * 위임 관리 서비스
 * 
 * @author jeehye
 * @version $Id: ApprEntrustService.java 16234 2011-08-18 02:44:36Z giljae $
 */
public interface ApprEntrustService extends GenericService<ApprEntrust, String> {

	/**
	 * 위임정보 저장
	 * 
	 * @param apprEntrust 모델
	 * @return ID
	 */
	public void entrustCreate(ApprEntrust apprEntrust);

	/**
	 * 위임정보 저장
	 * 
	 * @param apprEntrust 모델
	 * @return ID
	 */
	public void entrustUpdate(ApprEntrust apprEntrust);

	/**
	 * 위임 상태만 변경
	 * 
	 * @param apprEntrust 모델
	 * @return ID
	 */
	public void entrustUpdateUsage(ApprEntrust apprEntrust, String[] entrustIds);

	/**
	 * 위임정보 조회
	 * 
	 * @param apprEntrust 모델
	 * @return ID
	 */
	public ApprEntrust entrustDetail(ApprEntrust apprEntrust);

	/**
	 * 위임정보리스트 조회
	 * 
	 * @param apprEntrust
	 * @return
	 */
	public List<ApprEntrust> entrustList(ApprEntrust apprEntrust);

	/**
	 * 위임정보리스트 조회(페이징)
	 * 
	 * @param apprEntrust
	 * @return
	 */
	public SearchResult<ApprEntrust> listBySearchCondition(ApprListSearchCondition searchCondition);

	/**
	 * 위임 여부 체크
	 * 
	 * @param entrustUserId
	 * @param signUserId
	 * @return
	 */
	public boolean isEntrust(String entrustUserId, String signUserId);
	
	/**
	 * 수임 여부 체크
	 * 
	 * @param entrustUserId
	 * @param signUserId
	 * @return
	 */
	public boolean isSignUser(String entrustId);
	
	/**
	 * 수임 여부 체크
	 * 
	 * @param entrustUserId
	 * @param signUserId
	 * @return
	 */
	public boolean hasSignUser(Map map);

}
