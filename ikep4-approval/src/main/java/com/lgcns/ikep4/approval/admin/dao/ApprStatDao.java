/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.admin.dao;

import java.util.List;

import com.lgcns.ikep4.approval.admin.model.ApprStat;
import com.lgcns.ikep4.approval.work.search.ApprListSearchCondition;
import com.lgcns.ikep4.framework.core.dao.GenericDao;


/**
 * 통계 DAO
 * 
 * @author 유승목
 * @version $Id$
 */
public interface ApprStatDao extends GenericDao<ApprStat, String> {

	/**
	 * 리드타임 통계 생성
	 */
	void generateTimeStat(ApprStat apprStat);

	/**
	 * 사용자별 통계 생성
	 */
	void generateUserStat(ApprStat apprStat);

	/**
	 * 양식별 통계 생성
	 */
	void generateFormStat(ApprStat apprStat);

	/**
	 * 리드타임 통계 리스트
	 * 
	 * @return
	 */
	public List<ApprStat> getTimeStatList(ApprListSearchCondition searchCondition);

	/**
	 * 리드타임 통계 카운트
	 * 
	 * @return
	 */
	public Integer getTimeStatCount(ApprListSearchCondition searchCondition);

	/**
	 * 사용자별 통계 리스트
	 * 
	 * @return
	 */
	public List<ApprStat> getUserStatList(ApprListSearchCondition searchCondition);

	/**
	 * 사용자별 통계 카운트
	 * 
	 * @return
	 */
	public Integer getUserStatCount(ApprListSearchCondition searchCondition);

	/**
	 * 양식별 통계 리스트
	 * 
	 * @return
	 */
	public List<ApprStat> getFormStatList(ApprListSearchCondition searchCondition);

}
