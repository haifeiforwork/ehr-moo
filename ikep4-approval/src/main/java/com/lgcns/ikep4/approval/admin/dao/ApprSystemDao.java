/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.admin.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.approval.admin.model.ApprSystem;
import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;



/**
 * 결재 시스템 관리 DAO 정의
 * 
 * @author
 * @version $Id: ApprSystemDao.java 16276 2011-08-18 07:09:07Z giljae $
 */
public interface ApprSystemDao extends GenericDao<ApprSystem, String> {

	/**
	 * 결재 시스템 조회
	 * 
	 * @param searchCondition 검색 조건
	 * @return
	 */
	public List<ApprSystem> selectAll(AdminSearchCondition searchCondition);

	/**
	 * 결재 시스템 select Box용 조회
	 * 
	 * @param portal
	 * @return
	 */
	public List<ApprSystem> selectList(String portalId);

	/**
	 * 결재 시스템 조회 카운트
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
	public int getMaxSystemOrder(String portalId);

	/**
	 * 해당 결재 시스템의 정렬순서를 목록에서 한단계 위로 올림
	 * 
	 * @param map 이동 조건
	 * @return
	 */
	public ApprSystem goUp(Map<String, String> map);

	/**
	 * 해당 결재 시스템의 정렬순서를 목록에서 한단계 아래로 내림
	 * 
	 * @param map 이동 조건
	 * @return
	 */
	public ApprSystem goDown(Map<String, String> map);

	/**
	 * 결재 시스템을 위/아래로 이동 후 정렬 순서를 업데이트 함
	 * 
	 * @param jobDuty 업데이트할 직책 객체
	 */
	public void updateSystemOrder(ApprSystem system);

}