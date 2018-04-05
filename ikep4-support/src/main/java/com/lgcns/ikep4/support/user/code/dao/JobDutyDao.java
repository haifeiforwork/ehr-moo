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
import com.lgcns.ikep4.support.user.code.model.JobDuty;


/**
 * 직책 코드 관리 DAO 정의
 * 
 * @author 양새로훈(yang.sae@gmail.com)
 * @version $Id: JobDutyDao.java 19023 2012-05-31 06:36:51Z malboru80 $
 */
public interface JobDutyDao extends GenericDao<JobDuty, String> {

	/**
	 * 직책 코드 조회
	 * 
	 * @param searchCondition 검색 조건
	 * @return
	 */
	public List<JobDuty> selectAll(AdminSearchCondition searchCondition);

	/**
	 * 직책 코드 조회
	 * 
	 * @param searchCondition 검색 조건
	 * @return
	 */
	public List<JobDuty> selectJobDutyForTree(String portalId);

	
	/**
	 * 직책 코드 조회 카운트
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
	 * 해당 직책코드의 정렬순서를 목록에서 한단계 위로 올림
	 * 
	 * @param map 이동 조건
	 * @return
	 */
	public JobDuty goUp(Map<String, String> map);

	/**
	 * 해당 직책코드의 정렬순서를 목록에서 한단계 아래로 내림
	 * 
	 * @param map 이동 조건
	 * @return
	 */
	public JobDuty goDown(Map<String, String> map);

	/**
	 * 직책코드를 위/아래로 이동 후 정렬 순서를 업데이트 함
	 * 
	 * @param jobDuty 업데이트할 직책 객체
	 */
	public void updateSortOrder(JobDuty jobDuty);
	
	/**
	 * 직책 코드 목록 조회
	 * 
	 * @param portalId 포탈 ID
	 * @return
	 */
	public List<JobDuty> listJobDutyAll(String portalId);
}