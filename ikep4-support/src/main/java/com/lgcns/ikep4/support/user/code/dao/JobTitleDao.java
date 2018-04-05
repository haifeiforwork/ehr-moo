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
import com.lgcns.ikep4.support.user.code.model.JobTitle;


/**
 * 호칭 코드 관리 DAO 정의
 * 
 * @author 양새로훈(yang.sae@gmail.com)
 * @version $Id: JobTitleDao.java 19023 2012-05-31 06:36:51Z malboru80 $
 */
public interface JobTitleDao extends GenericDao<JobTitle, String> {

	/**
	 * 호칭 코드 조회
	 * 
	 * @param searchCondition 검색 조건
	 * @return
	 */
	public List<JobTitle> selectAll(AdminSearchCondition searchCondition);

	/**
	 * 호칭 코드 조회
	 * 
	 * @param searchCondition 검색 조건
	 * @return
	 */
	public List<JobTitle> selectJobTitleForTree(String portalId);
	
	
	/**
	 * 호칭 코드 조회 카운트
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
	 * 해당 호칭코드의 정렬순서를 목록에서 한단계 위로 올림
	 * 
	 * @param map 이동 조건
	 * @return
	 */
	public JobTitle goUp(Map<String, String> map);

	/**
	 * 해당 호칭코드의 정렬순서를 목록에서 한단계 아래로 내림
	 * 
	 * @param map 이동 조건
	 * @return
	 */
	public JobTitle goDown(Map<String, String> map);

	/**
	 * 호칭코드를 위/아래로 이동 후 정렬 순서를 업데이트 함
	 * 
	 * @param jobTitle 업데이트할 호칭 객체
	 */
	public void updateSortOrder(JobTitle jobTitle);
	
	/**
	 * 호칭 코드 목록 조회
	 * 
	 * @param portalId 포탈ID
	 * @return
	 */
	public List<JobTitle> listJobTitleAll(String portalId);

}