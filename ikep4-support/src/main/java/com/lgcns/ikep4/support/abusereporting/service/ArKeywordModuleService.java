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
import com.lgcns.ikep4.support.abusereporting.model.ArKeywordModule;
import com.lgcns.ikep4.support.abusereporting.model.ArModule;

/**
 * 
 * 모니터링 키워드 관련 서비스
 *
 * @author 최성우
 * @version $Id: ArKeywordModuleService.java 16271 2011-08-18 07:06:14Z giljae $
 */
@Transactional
public interface ArKeywordModuleService extends GenericService<ArKeywordModule, ArKeywordModule> {

	/**
	 * keyword에 해당하는 모듈 리스트 조회
	 * 
	 * @param keyword, portalId 가 있는 ArKeywordModule 객체
	 * @return ArModule List
	 */
	public List<ArModule> listByKeyword	(ArKeywordModule arKeywordModule);
	
	/**
	 * moduleCode 에 해당하는 keyword 리스트 조회
	 * 
	 * @param keyword, portalId 가 있는 ArKeywordModule 객체
	 * @return keyword 리스트
	 */
	public List<String> listByModuleCode(ArKeywordModule arKeywordModule);

	/**
	 * 모듈별 금지어 리스트 조회후 캐쉬에 등록한다.
	 * 금지어와 모듈 연결이 변경되는 등록, 수정, 삭제 작업이 있으면 관련 cache를 삭제 후 reload한다.
	 * 
	 * @param portalId 포탈 ID
	 */
	public void prohibitWordCacheReload(String portalId);

	/**
	 * moduleCode 에 해당하는 keyword 리스트 조회한다.
	 * 처음 prohibitWordCache를 호출하거나, 시간이 지나서 그 안의 Element가 없어졌을 경우 다시 조회해서 cache에 저장한다.
	 * 
	 * @param moduleCode, portalId 가 있는 ArKeywordModule 객체
	 * @return keyword 리스트
	 */
	public String getProhibitWordCacheByModuleCode(ArKeywordModule arKeywordModule);
}
