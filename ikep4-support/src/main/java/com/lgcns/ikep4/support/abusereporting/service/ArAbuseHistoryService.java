/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.abusereporting.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.abusereporting.model.ArAbuseHistory;
import com.lgcns.ikep4.support.abusereporting.model.ArAbuseQueryReturn;
import com.lgcns.ikep4.support.abusereporting.search.ArAbuseSearchCondition;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 
 * 금지어 모니터링 히스토리  관련 서비스
 *
 * @author 최성우
 * @version $Id: ArAbuseHistoryService.java 16271 2011-08-18 07:06:14Z giljae $
 */
@Transactional
public interface ArAbuseHistoryService extends GenericService<ArAbuseHistory, ArAbuseHistory> {

	/**
	 * 금지어가 있거나, 외부연동 모듈의 경우  history에 쌓는다.
	 * 
	 * @param arAbuseHistory moduleCode, itemId, contents 등이 담긴 ArAbuseHistory객체
	 * @param user 모듈 사용자 정보
	 */
	public void checkAndSaveProhibitWord		(ArAbuseHistory arAbuseHistory, User user);
	
	/**
	 * 모듈에 해당하는 금지어 리스트를 조회하여 컨텐츠와 비교하여 금지어를 걸러서 리턴한다.
	 * 
	 * @param arAbuseHistory moduleCode, itemId, contents 등이 담긴 ArAbuseHistory객체
	 * @return 금지어리스트 ('|'로 구분자 사용)
	 */
	public String getCheckProhibitWordList		(ArAbuseHistory arAbuseHistory);

	/**
	 * 검색조건에 해당하는 ArAbuse 리포트용 리스트 조회
	 * 
	 * @param moduleCode, startDate, endDate, keyword 를 담은 ArAbuseSearchCondition 객체
	 * @return 검색결과 모델 클래스
	 */
	public SearchResult<ArAbuseHistory>			listBySearchconditionForReport		(ArAbuseSearchCondition arAbuseSearchCondition);
	
	/**
	 * 검색조건에 해당하는 ArAbuse 통계용 리스트 조회
	 * 
	 * @param moduleCode, startDate, endDate 를 담은 ArAbuseSearchCondition 객체
	 * @return   ArAbuse 통계용 리스트
	 */
	public List<ArAbuseQueryReturn> 			listBySearchconditionForStatistics	(ArAbuseSearchCondition arAbuseSearchCondition);
}
