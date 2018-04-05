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
import com.lgcns.ikep4.support.user.code.model.Timezone;


/**
 * 타임존 코드 관리 DAO 정의
 * 
 * @author 양새로훈(yang.sae@gmail.com)
 * @version $Id: TimezoneDao.java 16258 2011-08-18 05:37:22Z giljae $
 */
public interface TimezoneDao extends GenericDao<Timezone, String> {

	/**
	 * 타임존 조회
	 * 
	 * @param searchCondition 검색 조건
	 * @return
	 */
	public List<Timezone> selectAll(AdminSearchCondition searchCondition);

	/**
	 * 타임존 조회 카운트
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
	 * 해당 타임존의 정렬순서를 목록에서 한단계 위로 올림
	 * 
	 * @param map 이동 조건
	 * @return
	 */
	public Timezone goUp(Map<String,String> map);
	
	/**
	 * 해당 타임존의 정렬순서를 목록에서 한단계 아래로 내림
	 * 
	 * @param map 이동 조건
	 * @return
	 */
	public Timezone goDown(Map<String,String> map);
	
	/**
	 * 타임존을 위/아래로 이동 후 정렬 순서를 업데이트 함
	 * 
	 * @param timezone 업데이트할 로케일 객체
	 */
	public void updateSortOrder(Timezone timezone);
	
	/**
	 * 타임존 목록 가져오기
	 * 
	 * @return
	 */
	public List<Timezone> list(Map<String,String> param);
}
