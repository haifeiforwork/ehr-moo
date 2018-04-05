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
import com.lgcns.ikep4.support.searchpreprocessor.dao.SpSnRelationDAO;
import com.lgcns.ikep4.support.searchpreprocessor.service.SpSnRelationService;
import com.lgcns.ikep4.support.searchpreprocessor.util.SearchUtil;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 검색어관련 동료검색
 *
 * @author ihko11
 * @version $Id: SpSnRelationServiceImpl.java 16258 2011-08-18 05:37:22Z giljae $
 */
@Service
@Transactional
public class SpSnRelationServiceImpl extends GenericServiceImpl<User, String> implements SpSnRelationService {
	protected final Log log = LogFactory.getLog(getClass());

	SpSnRelationDAO spSnRelationDao;
	
	@Autowired
	public SpSnRelationServiceImpl(SpSnRelationDAO dao) {
		super(dao);
		this.spSnRelationDao = dao;
	}
	
	/**
	 * 동료검색리스트
	 */
	public List<User> getSnRelationList(SearchUtil searchUtil){
		return spSnRelationDao.getSnRelationList(searchUtil);
	}
	
	/**
	 * 동료검색상세리스트
	 */
	public List<User> getSnRelationDetailList(SearchUtil searchUtil){
		return spSnRelationDao.getSnRelationDetailList(searchUtil);
	}
	
	/**
	 * 동료검색카운트
	 */
	public Integer countSnRelationDetailList(SearchUtil searchUtil){
		return spSnRelationDao.countSnRelationDetailList(searchUtil);
	}
}
