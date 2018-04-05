/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.servicepack.usagetracker.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.servicepack.usagetracker.model.UtLoginLog;
import com.lgcns.ikep4.servicepack.usagetracker.search.UtSearchCondition;

/**
 * 
 * 사용량 통계 사용자 로그인 히스토리 service
 *
 * @author 고인호(ihko11@daum.net)
 * @version $Id: UtLoginLogService.java 16244 2011-08-18 04:11:42Z giljae $
 */
@Transactional
public interface UtLoginLogService extends GenericService<UtLoginLog,String> {
	/**
	 * 로그인 리스트
	 * @param searchCondition
	 * @return
	 */
	public SearchResult<UtLoginLog> listBySearchCondition(UtSearchCondition searchCondition) ;
	
	public List<UtLoginLog> smsUseListBySearchCondition(UtSearchCondition searchCondition) ;
	
	/**
	 * excel 통계 메뉴구성 리스트
	 * @param searchCondition
	 * @return
	 */
	public List<UtLoginLog> excelLoginListBySearchCondition(UtSearchCondition searchCondition);
	
	public List<UtLoginLog> excelSmsListBySearchCondition(UtSearchCondition searchCondition);
	
}
