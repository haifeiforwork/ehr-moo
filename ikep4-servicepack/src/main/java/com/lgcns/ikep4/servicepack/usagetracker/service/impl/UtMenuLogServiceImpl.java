/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.servicepack.usagetracker.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.servicepack.usagetracker.dao.UtMenuLogDao;
import com.lgcns.ikep4.servicepack.usagetracker.model.UtMenuLog;
import com.lgcns.ikep4.servicepack.usagetracker.search.UtSearchCondition;
import com.lgcns.ikep4.servicepack.usagetracker.service.UtMenuLogService;

/**
 * 메뉴현황통계
 *
 * @author ihko11
 * @version $Id: UtMenuLogServiceImpl.java 16244 2011-08-18 04:11:42Z giljae $
 */
@Service
@Transactional
public class UtMenuLogServiceImpl extends GenericServiceImpl<UtMenuLog, String> implements UtMenuLogService {
	protected final Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private UtMenuLogDao utMenuLogDao;
	
	
	@Autowired
	public UtMenuLogServiceImpl(UtMenuLogDao dao) {
		super(dao);
		this.utMenuLogDao = dao;
	}
	
	/**
	 * 로그인리스트
	 */
	public SearchResult<UtMenuLog> listBySearchCondition(UtSearchCondition searchCondition) {
		SearchResult<UtMenuLog> searchResult = null;

		Integer count = this.utMenuLogDao.countBySearchCondition(searchCondition);
		searchCondition.terminateSearchCondition(count);

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<UtMenuLog>(searchCondition);
		} else {
			List<UtMenuLog> itemList = utMenuLogDao.listBySearchCondition(searchCondition);
			searchResult = new SearchResult<UtMenuLog>(itemList, searchCondition);
		}

		return searchResult;
	}
	
	public SearchResult<UtMenuLog> mobileListBySearchCondition(UtSearchCondition searchCondition) {
		SearchResult<UtMenuLog> searchResult = null;

		Integer count = this.utMenuLogDao.mobileCountBySearchCondition(searchCondition);
		searchCondition.terminateSearchCondition(count);

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<UtMenuLog>(searchCondition);
		} else {
			List<UtMenuLog> itemList = utMenuLogDao.mobileListBySearchCondition(searchCondition);
			searchResult = new SearchResult<UtMenuLog>(itemList, searchCondition);
		}

		return searchResult;
	}
	
	
	
	
	
	/**
	 * excel 통계 메뉴구성 리스트
	 * @param searchCondition
	 * @return
	 */
	public List<UtMenuLog> excelMenuListBySearchCondition(UtSearchCondition searchCondition){
		return utMenuLogDao.excelMenuListBySearchCondition(searchCondition);
	}
	
	public List<UtMenuLog> excelMobileMenuListBySearchCondition(UtSearchCondition searchCondition){
		return utMenuLogDao.excelMobileMenuListBySearchCondition(searchCondition);
	}
}
