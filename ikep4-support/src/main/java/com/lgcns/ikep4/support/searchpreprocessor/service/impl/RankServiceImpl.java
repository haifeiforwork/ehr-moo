/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.searchpreprocessor.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.support.searchpreprocessor.dao.RankDAO;
import com.lgcns.ikep4.support.searchpreprocessor.model.Rank;
import com.lgcns.ikep4.support.searchpreprocessor.service.RankService;

/**
 * 검색어 랭킹
 *
 * @author ihko11
 * @version $Id: RankServiceImpl.java 16258 2011-08-18 05:37:22Z giljae $
 */
@Service
@Transactional
public class RankServiceImpl extends GenericServiceImpl<Rank, String> implements RankService {
	protected final Log log = LogFactory.getLog(getClass());

	RankDAO rankDao;
	
	@Autowired
	public RankServiceImpl(RankDAO dao) {
		super(dao);
		this.rankDao = dao;
	}
	
	/**
	 * 검색어 랭킹 리스트
	 */
	public List<Rank> getRankList(){
		return rankDao.getRankList();
	}
	
	/**
	 * 검색어 모두삭제 
	 */
	public void removeAll(){
		rankDao.removeAll();
	}
}
