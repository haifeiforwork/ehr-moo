/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.work.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.approval.work.model.ApprEntrust;
import com.lgcns.ikep4.approval.work.search.ApprListSearchCondition;
import com.lgcns.ikep4.framework.core.dao.GenericDao;


/**
 * TODO Javadoc주석작성
 * 
 * @author 서지혜
 * @version $Id$
 */

public interface ApprEntrustDao extends GenericDao<ApprEntrust, String> {

	/**
	 * 입력
	 * 
	 * @param apprEntrust
	 */
	void entrustCreate(ApprEntrust apprEntrust);

	/**
	 * 수정
	 * 
	 * @param apprEntrust
	 */
	void entrustUpdate(ApprEntrust apprEntrust);

	/**
	 * 사용여부 수정
	 * 
	 * @param apprEntrust
	 */
	void entrustUpdateUsage(ApprEntrust apprEntrust);

	/**
	 * 상세 정보
	 * 
	 * @param apprEntrust
	 * @return
	 */
	ApprEntrust entrustDetail(ApprEntrust apprEntrust);

	/**
	 * 리스트 검색
	 * 
	 * @param apprEntrust
	 * @return
	 */
	List<ApprEntrust> entrustList(ApprEntrust apprEntrust);

	/**
	 * 리스트 검색
	 * 
	 * @param searchCondition
	 * @return
	 */
	public List<ApprEntrust> listBySearchCondition(ApprListSearchCondition searchCondition);

	/**
	 * 리스트 카운트
	 * 
	 * @param searchCondition
	 * @return
	 */
	public Integer countBySearchCondition(ApprListSearchCondition searchCondition);

	/**
	 * 위임자 여부
	 * 
	 * @param map
	 * @return
	 */
	boolean hasSignUser(Map map);

}
