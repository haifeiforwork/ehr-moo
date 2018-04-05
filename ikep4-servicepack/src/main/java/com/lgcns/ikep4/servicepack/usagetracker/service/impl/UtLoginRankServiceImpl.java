/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.servicepack.usagetracker.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.servicepack.usagetracker.dao.UtLoginRankDao;
import com.lgcns.ikep4.servicepack.usagetracker.model.UtLoginRank;
import com.lgcns.ikep4.servicepack.usagetracker.model.UtStatistics;
import com.lgcns.ikep4.servicepack.usagetracker.search.UtSearchCondition;
import com.lgcns.ikep4.servicepack.usagetracker.service.UtLoginRankService;

/**
 * 사용자현황통계
 *
 * @author ihko11
 * @version $Id: UtLoginRankServiceImpl.java 16244 2011-08-18 04:11:42Z giljae $
 */
@Service
@Transactional
public class UtLoginRankServiceImpl extends GenericServiceImpl<UtLoginRank, String> implements UtLoginRankService {
	protected final Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private UtLoginRankDao utLoginRankDao;
	
	//@Autowired
	//private UtStatisticsDao utStatisticsDao;
	
	@Autowired
	public UtLoginRankServiceImpl(UtLoginRankDao dao) {
		super(dao);
		this.utLoginRankDao = dao;
	}
	
	/**
	 * 삭제
	 */
	@Override
	public void delete(String id) {
		utLoginRankDao.remove(id);
	}

	
	/**
	 * 상세
	 */
	@Override
	public UtLoginRank read(String id) {
		return utLoginRankDao.get(id);
	}

	/**
	 * 생성
	 */
	@Override
	public String create(UtLoginRank utLoginRank) {
		return utLoginRankDao.create(utLoginRank);
	}

	/**
	 * 수정
	 */
	@Override
	public void update(UtLoginRank utLoginRank) {
		utLoginRankDao.update(utLoginRank);
	}
	
	public List<UtLoginRank> selectUserBatchReadItems(){
		return utLoginRankDao.selectUserBatchReadItems();
	}
	
	/**
	 * 사용량 상요자 현황생성
	 */
	public void saveOrUpdate(List<UtLoginRank> batchLogList){
		
		for (UtLoginRank utLoginRankLog : batchLogList) {

			UtLoginRank utLoginRank = utLoginRankDao.get(utLoginRankLog.getUserId());	
			
			if( utLoginRank == null )
			{
				utLoginRank = new UtLoginRank();
				utLoginRank.setUserId( utLoginRankLog.getUserId() );
				utLoginRank.setMonth1(utLoginRankLog.getMonth1() );
				utLoginRank.setMonth2(utLoginRankLog.getMonth2());
				utLoginRank.setMonth3(utLoginRankLog.getMonth3());
				utLoginRank.setMonth4(utLoginRankLog.getMonth4());
				utLoginRank.setMonth5(utLoginRankLog.getMonth5());
				utLoginRank.setMonth6(utLoginRankLog.getMonth6());
				utLoginRank.setMonth7(utLoginRankLog.getMonth7());
				utLoginRank.setMonth8(utLoginRankLog.getMonth8());
				utLoginRank.setMonth9(utLoginRankLog.getMonth9());
				utLoginRank.setMonth10(utLoginRankLog.getMonth10());
				utLoginRank.setMonth11(utLoginRankLog.getMonth11());
				utLoginRank.setMonth12(utLoginRankLog.getMonth12());
				utLoginRank.setPortalId(utLoginRankLog.getPortalId());
				utLoginRank.setRegistDate( new Date() );
				utLoginRankDao.create(utLoginRank);
			}
			else
			{
				utLoginRank.setMonth1(utLoginRankLog.getMonth1() );
				utLoginRank.setMonth2(utLoginRankLog.getMonth2());
				utLoginRank.setMonth3(utLoginRankLog.getMonth3());
				utLoginRank.setMonth4(utLoginRankLog.getMonth4());
				utLoginRank.setMonth5(utLoginRankLog.getMonth5());
				utLoginRank.setMonth6(utLoginRankLog.getMonth6());
				utLoginRank.setMonth7(utLoginRankLog.getMonth7());
				utLoginRank.setMonth8(utLoginRankLog.getMonth8());
				utLoginRank.setMonth9(utLoginRankLog.getMonth9());
				utLoginRank.setMonth10(utLoginRankLog.getMonth10());
				utLoginRank.setMonth11(utLoginRankLog.getMonth11());
				utLoginRank.setMonth12(utLoginRankLog.getMonth12());
				utLoginRank.setPortalId(utLoginRankLog.getPortalId());
				utLoginRankDao.update(utLoginRank);
			}
			
		}
	}
	
	/**
	 * 로그인리스트
	 */
	public SearchResult<UtStatistics> listBySearchCondition(UtSearchCondition searchCondition) {
		SearchResult<UtStatistics> searchResult = null;

		Integer count = this.utLoginRankDao.countBySearchCondition(searchCondition);
		searchCondition.terminateSearchCondition(count);

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<UtStatistics>(searchCondition);
		} else {
			List<UtStatistics> itemList = utLoginRankDao.listBySearchCondition(searchCondition);
			searchResult = new SearchResult<UtStatistics>(itemList, searchCondition);
		}

		return searchResult;
	}
	
	
	public List<UtStatistics> excelListBySearchCondition(UtSearchCondition searchCondition) {
		  return utLoginRankDao.listBySearchCondition(searchCondition);
	}
}
