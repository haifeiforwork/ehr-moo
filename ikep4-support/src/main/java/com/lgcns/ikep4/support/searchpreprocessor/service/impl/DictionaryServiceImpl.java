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
import com.lgcns.ikep4.support.searchpreprocessor.dao.DictionaryDAO;
import com.lgcns.ikep4.support.searchpreprocessor.model.Dictionary;
import com.lgcns.ikep4.support.searchpreprocessor.service.DictionaryService;

/**
 * 검색어사전검색
 *
 * @author ihko11
 * @version $Id: DictionaryServiceImpl.java 16258 2011-08-18 05:37:22Z giljae $
 */
@Service
@Transactional
public class DictionaryServiceImpl extends GenericServiceImpl<Dictionary, String> implements DictionaryService {
	protected final Log log = LogFactory.getLog(getClass());

	DictionaryDAO dictionaryDao;
	
	@Autowired
	public DictionaryServiceImpl(DictionaryDAO dao) {
		super(dao);
		this.dictionaryDao = dao;
	}
	
	/**
	 * 검색어사전리스트
	 */
	public List<Dictionary> getList(Dictionary dictionary){
		return dictionaryDao.getList(dictionary);
	}
	
	/**
	 * 검색어 순위리스트
	 */
	public List<Dictionary> getRankList(Dictionary dictionary){
		return dictionaryDao.getList(dictionary);
	}

	public List<String> getPortalIdList(){
		return dictionaryDao.getPortalIdList();
	}
	
	
	
}
