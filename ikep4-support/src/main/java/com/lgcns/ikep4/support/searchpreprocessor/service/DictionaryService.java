/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.searchpreprocessor.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.support.searchpreprocessor.model.Dictionary;

/**
 * 검색어 사전
 *
 * @author ihko11
 * @version $Id: DictionaryService.java 16258 2011-08-18 05:37:22Z giljae $
 */
@Transactional
public interface DictionaryService extends GenericService<Dictionary, String> {
/**
 * 검색어 사전 리스트
 * @param dictionary
 * @return
 */
	public List<Dictionary> getList(Dictionary dictionary);
	/**
	 * 검색어 사전 순위 리스트
	 * @param dictionary
	 * @return
	 */
	public List<Dictionary> getRankList(Dictionary dictionary);
	
	/**
	 * portal id list
	 * @return
	 */
	public List<String> getPortalIdList();
	
	
}
