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

import com.lgcns.ikep4.approval.admin.model.ApprSystem;
import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;



/**
 * 결재 시스템 관리 서비스
 * 
 * @author 
 * @version $Id: ApprSystemService.java 16276 2011-08-18 07:09:07Z giljae $
 */
@Transactional
public interface ApprSystemService extends GenericService<ApprSystem, String> {

	/**
	 * 결재 시스템 조회
	 * 
	 * @param searchCondition 검색 조건
	 * @return
	 */
	public SearchResult<ApprSystem> list(AdminSearchCondition searchCondition);

	/**
	 * 결재 시스템 select Box용 조회
	 * @param portalId
	 * @return
	 */
	public List<ApprSystem> selectList(String portalId);
	
	/**
	 * 정렬순서(Sort order) 최대값 가져오기
	 * 
	 * @return
	 */
	public Integer getMaxSystemOrder(String portalId);

	/**
	 * 해당 결재 시스템의 정렬순서를 목록에서 한단계 위로 올림
	 * 
	 * @param map 이동 조건
	 * @return
	 */
	public void goUp(Map<String, String> map);

	/**
	 * 해당 결재 시스템의 정렬순서를 목록에서 한단계 아래로 내림
	 * 
	 * @param map 이동 조건
	 * @return
	 */
	public void goDown(Map<String, String> map);
	
}