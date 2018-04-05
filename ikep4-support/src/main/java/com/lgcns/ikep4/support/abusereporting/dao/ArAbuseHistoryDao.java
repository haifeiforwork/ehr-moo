/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.abusereporting.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.support.abusereporting.model.ArAbuseHistory;
import com.lgcns.ikep4.support.abusereporting.model.ArAbuseQueryReturn;
import com.lgcns.ikep4.support.abusereporting.search.ArAbuseSearchCondition;


/**
 * 
 * ArAbuseHistory 관련 처리 DAO
 *
 * @author 최성우
 * @version $Id: ArAbuseHistoryDao.java 16258 2011-08-18 05:37:22Z giljae $
 */
public interface ArAbuseHistoryDao extends GenericDao<ArAbuseHistory, ArAbuseHistory> {

	/**
	 * 검색조건에 해당하는 ArAbuse 리포트용 리스트 조회
	 * 
	 * @param moduleCode, startDate, endDate, keyword 를 담은 ArAbuseSearchCondition 객체
	 * @return ArAbuseHistory List
	 */
	public List<ArAbuseHistory> listBySearchconditionForReport(ArAbuseSearchCondition arAbuseSearchCondition);

	/**
	 * 검색조건에 해당하는 ArAbuse 리포트용 리스트 갯수 조회
	 * 
	 * @param moduleCode, startDate, endDate, keyword 를 담은 ArAbuseSearchCondition 객체
	 * @return  ArAbuse 리포트용 리스트 갯수
	 */
	Integer countBySearchconditionForReport(ArAbuseSearchCondition arAbuseSearchCondition);

	/**
	 * 검색조건에 해당하는 ArAbuse 통계용 리스트 조회
	 * 
	 * @param moduleCode, startDate, endDate 를 담은 ArAbuseSearchCondition 객체
	 * @return   ArAbuse 통계용 리스트
	 */
	public List<ArAbuseQueryReturn> listBySearchconditionForStatistics(ArAbuseSearchCondition arAbuseSearchCondition);

}
