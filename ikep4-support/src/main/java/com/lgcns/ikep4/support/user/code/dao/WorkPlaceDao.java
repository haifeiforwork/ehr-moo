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
import com.lgcns.ikep4.support.user.code.model.WorkPlace;


/**
 * 사업장 코드 관리 DAO 정의
 * 
 * @author 양새로훈(yang.sae@gmail.com)
 * @version $Id: WorkPlaceDao.java 19023 2012-05-31 06:36:51Z malboru80 $
 */
public interface WorkPlaceDao extends GenericDao<WorkPlace, String> {

	/**
	 * 사업장 코드 조회
	 * 
	 * @param searchCondition 검색 조건
	 * @return
	 */
	public List<WorkPlace> selectAll(AdminSearchCondition searchCondition);
	
	/**
	 * 검색 조건없이 목록 가져오기
	 * 
	 * @return
	 */
	public List<WorkPlace> getAll(String localeCode);

	/**
	 * 사업장 코드 조회 카운트
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
	 * 해당 사업장코드의 정렬순서를 목록에서 한단계 위로 올림
	 * 
	 * @param map 이동 조건
	 * @return
	 */
	public WorkPlace goUp(Map<String, String> map);

	/**
	 * 해당 사업장코드의 정렬순서를 목록에서 한단계 아래로 내림
	 * 
	 * @param map 이동 조건
	 * @return
	 */
	public WorkPlace goDown(Map<String, String> map);

	/**
	 * 사업장코드를 위/아래로 이동 후 정렬 순서를 업데이트 함
	 * 
	 * @param workPlace 업데이트할 사업장 객체
	 */
	public void updateSortOrder(WorkPlace workPlace);
	
	/**
	 * 사업장 코드 목록 조회
	 * 
	 * @param portalId 포탈 ID
	 * @return
	 */
	public List<WorkPlace> listWorkPlaceAll(String portalId);
	
}