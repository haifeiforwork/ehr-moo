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
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.searchpreprocessor.dao.BatchLogDAO;
import com.lgcns.ikep4.support.searchpreprocessor.model.BatchLog;
import com.lgcns.ikep4.support.searchpreprocessor.search.SpSearchCondition;
import com.lgcns.ikep4.support.searchpreprocessor.service.BatchLogService;

/**
 * 배치로그 서비스
 *
 * @author ihko11
 * @version $Id: BatchLogServiceImpl.java 16276 2011-08-18 07:09:07Z giljae $
 */
@Service
@Transactional
public class BatchLogServiceImpl extends GenericServiceImpl<BatchLog, String> implements BatchLogService {
	protected final Log log = LogFactory.getLog(getClass());

	BatchLogDAO batchLogDao;
	
	@Autowired
	public BatchLogServiceImpl(BatchLogDAO dao) {
		super(dao);
		this.batchLogDao = dao;
	}
	
	/**
	 * 배치로그 리스트
	 */
	public SearchResult<BatchLog> listBySearchCondition(SpSearchCondition searchCondition) {
			SearchResult<BatchLog> searchResult = null;

			Integer count = this.batchLogDao.countBySearchCondition(searchCondition);
			searchCondition.terminateSearchCondition(count);
			
			
			if( searchCondition.isEmptyRecord() ){
				searchResult = new SearchResult<BatchLog>(searchCondition); 
			}else{
				List<BatchLog> roleItemList = batchLogDao.listBySearchCondition(searchCondition);
				searchResult = new SearchResult<BatchLog>(roleItemList, searchCondition); 
			}
		
			return searchResult;
	}
	
	
}
