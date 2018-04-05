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
import com.lgcns.ikep4.servicepack.usagetracker.dao.UtLoginLogDao;
import com.lgcns.ikep4.servicepack.usagetracker.model.UtLoginLog;
import com.lgcns.ikep4.servicepack.usagetracker.search.UtSearchCondition;
import com.lgcns.ikep4.servicepack.usagetracker.service.UtLoginLogService;

/**
 * 사용자현황통계
 *
 * @author ihko11
 * @version $Id: UtLoginLogServiceImpl.java 16244 2011-08-18 04:11:42Z giljae $
 */
@Service
@Transactional
public class UtLoginLogServiceImpl extends GenericServiceImpl<UtLoginLog, String> implements UtLoginLogService {
	protected final Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private UtLoginLogDao utLoginLogDao;
	
	
	@Autowired
	public UtLoginLogServiceImpl(UtLoginLogDao dao) {
		super(dao);
		this.utLoginLogDao = dao;
	}
	
	/**
	 * 로그인리스트
	 */
	public SearchResult<UtLoginLog> listBySearchCondition(UtSearchCondition searchCondition) {
		SearchResult<UtLoginLog> searchResult = null;

		Integer count = this.utLoginLogDao.countBySearchCondition(searchCondition);
		searchCondition.terminateSearchCondition(count);

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<UtLoginLog>(searchCondition);
		} else {
			List<UtLoginLog> itemList = utLoginLogDao.listBySearchCondition(searchCondition);
			searchResult = new SearchResult<UtLoginLog>(itemList, searchCondition);
		}

		return searchResult;
	}
	
	public List<UtLoginLog> smsUseListBySearchCondition(UtSearchCondition searchCondition) {

			List<UtLoginLog> itemList = utLoginLogDao.smsUseListBySearchCondition(searchCondition);

		return itemList;
	}
	
	/**
	 * excel 통계 메뉴구성 리스트
	 * @param searchCondition
	 * @return
	 */
	public List<UtLoginLog> excelLoginListBySearchCondition(UtSearchCondition searchCondition){
		return utLoginLogDao.excelLoginListBySearchCondition(searchCondition);
	}
	
	public List<UtLoginLog> excelSmsListBySearchCondition(UtSearchCondition searchCondition){
		return utLoginLogDao.excelSmsListBySearchCondition(searchCondition);
	}
	
}
