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
import com.lgcns.ikep4.support.searchpreprocessor.model.Rank;

/**
 * 검색 순위
 *
 * @author ihko11
 * @version $Id: RankService.java 16276 2011-08-18 07:09:07Z giljae $
 */
@Transactional
public interface RankService extends GenericService<Rank, String> {
	/**
	 * 검색어 순위 리스트
	 * @return
	 */
	public List<Rank> getRankList();
	/**
	 * 검색어 순위 삭제
	 *
	 */
	public void removeAll();
}
