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
import com.lgcns.ikep4.servicepack.usagetracker.model.UtMenuLog;
import com.lgcns.ikep4.servicepack.usagetracker.search.UtSearchCondition;

/**
 * 
 * 사용량 통계 사용자 메뉴 히스토리 service
 *
 * @author 고인호(ihko11@daum.net)
 * @version $Id: UtMenuLogService.java 16244 2011-08-18 04:11:42Z giljae $
 */
@Transactional
public interface UtMenuLogService extends GenericService<UtMenuLog,String> {
	/**
	 * 로그인 리스트
	 * @param searchCondition
	 * @return
	 */
	public SearchResult<UtMenuLog> listBySearchCondition(UtSearchCondition searchCondition) ;
	
	public SearchResult<UtMenuLog> mobileListBySearchCondition(UtSearchCondition searchCondition) ;
	
	
	/**
	 * excel 통계 메뉴구성 리스트
	 * @param searchCondition
	 * @return
	 */
	List<UtMenuLog> excelMenuListBySearchCondition(UtSearchCondition searchCondition);
	
	List<UtMenuLog> excelMobileMenuListBySearchCondition(UtSearchCondition searchCondition);
}
