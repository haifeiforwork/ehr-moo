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
import com.lgcns.ikep4.support.abusereporting.model.ArKeyword;
import com.lgcns.ikep4.support.abusereporting.search.ArAbuseSearchCondition;

/**
 * 
 * 모니터링 키워드 관련 서비스
 *
 * @author 최성우
 * @version $Id: ArKeywordService.java 16258 2011-08-18 05:37:22Z giljae $
 */
@Transactional
public interface ArKeywordService extends GenericService<ArKeyword, ArKeyword> {

	/**
	 * 검색조건에 해당하는 금지어조회.
	 * 
	 * @param arAbuseSearchCondition moduleCode, searchWord 등이 담긴 ArAbuseSearchCondition 객체
	 * @param 금지어 목록
	 */
	public List<String> list	(ArAbuseSearchCondition arAbuseSearchCondition);
}
