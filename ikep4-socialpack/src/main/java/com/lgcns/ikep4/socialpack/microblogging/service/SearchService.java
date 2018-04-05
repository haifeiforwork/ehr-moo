/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.microblogging.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.socialpack.microblogging.model.Search;
import com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition;

/**
 * 
 * 검색 HISTORY 및 검색어 저장 정보 관련 서비스
 *
 * @author 최성우(csw9737@hanmail.net)
 * @version $Id: SearchService.java 16246 2011-08-18 04:48:28Z giljae $
 */
@Transactional
public interface SearchService extends GenericService<Search, String> {

	/**
	 * 사용자가 특정 검색어를 기존에 저장상태로 등록한 게 몇개인지 조회해서 반환
	 * 
	 * @param searchWord, searchUserId를 담은 Search객체
	 * @return 저장된 수
	 */
	public int count(Search search);

	/**
	 * Saved search list 반환
	 * 
	 * @param searchUserId를 담은 Search객체
	 * @return 저장된 검색어 리스트
	 */
	public List<Search> listBySearchUserId(Search search);

	/**
	 * 최근 한달간 
	 * 가장 많이 등록된 해쉬태그 또는 멘션 5개 +
	 * 가장 많이 검색된 해쉬태그 또는 멘션 5개 조회
	 * 
	 * @return 검색어 리스트
	 */
	public List<String> trendList(MblogSearchCondition mblogSearchCondition);
}
