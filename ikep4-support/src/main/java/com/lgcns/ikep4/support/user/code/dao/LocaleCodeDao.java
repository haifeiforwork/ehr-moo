/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.user.code.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;
import com.lgcns.ikep4.support.user.code.model.LocaleCode;


/**
 * 로케일 코드 관리 DAO 정의
 * 
 * @author 양새로훈(yang.sae@gmail.com)
 * @version $Id: LocaleCodeDao.java 17313 2012-02-08 00:17:29Z yruyo $
 */
public interface LocaleCodeDao extends GenericDao<LocaleCode, String> {

	/**
	 * 로케일 코드 조회
	 * 
	 * @param searchCondition 검색 조건
	 * @return
	 */
	public List<LocaleCode> selectAll(AdminSearchCondition searchCondition);
	
	/**
	 * 검색 조건없이 목록 가져오기
	 * 
	 * @return
	 */
	public List<LocaleCode> getAll(String localeCode);

	/**
	 * 로케일 코드 조회 카운트
	 * 
	 * @param searchCondition 검색 조건
	 * @return
	 */
	Integer selectCount(AdminSearchCondition searchCondition);
	
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
	public LocaleCode goUp(Map<String,String> map);
	
	/**
	 * 해당 로케일코드의 정렬순서를 목록에서 한단계 아래로 내림
	 * 
	 * @param map 이동 조건
	 * @return
	 */
	public LocaleCode goDown(Map<String,String> map);
	
	/**
	 * 로케일코드를 위/아래로 이동 후 정렬 순서를 업데이트 함
	 * 
	 * @param localeCode 업데이트할 로케일 객체
	 */
	public void updateSortOrder(LocaleCode localeCode);
	
	/**
	 * 로케일 코드 정보
	 * 
	 * @param locale 요청하는 로케일 코드와 사용자의 로케일 코드
	 */
	public LocaleCode localeInfo(Map<String, String> locale);
}