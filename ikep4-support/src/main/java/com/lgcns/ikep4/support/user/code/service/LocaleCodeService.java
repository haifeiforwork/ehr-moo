/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.user.code.service;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;
import com.lgcns.ikep4.support.user.code.model.LocaleCode;


/**
 * 로케일 코드 관리 서비스
 * 
 * @author 양새로훈(yang.sae@gmail.com)
 * @version $Id: LocaleCodeService.java 17313 2012-02-08 00:17:29Z yruyo $
 */
public interface LocaleCodeService extends GenericService<LocaleCode, String> {

	/**
	 * 로케일 코드 조회
	 * 
	 * @param searchCondition 검색 조건
	 * @return
	 */
	public SearchResult<LocaleCode> list(AdminSearchCondition searchCondition);

	/**
	 * 정렬순서(Sort order) 최대값 가져오기
	 * 
	 * @return
	 */
	public String getMaxSortOrder();

	/**
	 * 해당 로케일 코드의 정렬순서를 목록에서 한단계 위로 올림
	 * 
	 * @param map 이동 조건
	 * @return
	 */
	public void goUp(Map<String, String> map);

	/**
	 * 해당 로케일코드의 정렬순서를 목록에서 한단계 아래로 내림
	 * 
	 * @param map 이동 조건
	 * @return
	 */
	public void goDown(Map<String, String> map);

	/**
	 * 검색 조건없이 목록 가져오기
	 * 
	 * @return
	 */
	public List<LocaleCode> list(String localeCode);

	/**
	 * 로케일 코드 삭제하기
	 * 
	 * @param code
	 */
	public void remove(String code);
	
	/**
	 * 로케일 정보 참조
	 * @param locale 
	 * @return
	 */
	public LocaleCode localeInfo(Map<String, String> locale);
}
