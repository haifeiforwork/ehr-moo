/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.searchpreprocessor.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.support.searchpreprocessor.util.SearchUtil;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 검색 동료 상세
 *
 * @author ihko11
 * @version $Id: SpSnRelationService.java 16276 2011-08-18 07:09:07Z giljae $
 */
@Transactional
public interface SpSnRelationService extends GenericService<User, String> {
	/**
	 * 검색어 동료 상세 리스트
	 * @param searchUtil
	 * @return
	 */
	public List<User> getSnRelationList(SearchUtil searchUtil);
	/**
	 * 검색어 동료 상세 리스트
	 * @param searchUtil
	 * @return
	 */
	public List<User> getSnRelationDetailList(SearchUtil searchUtil);
	/**
	 * 검색어 동료 상세 리스트 카운트
	 * @param searchUtil
	 * @return
	 */
	public Integer countSnRelationDetailList(SearchUtil searchUtil);
}
