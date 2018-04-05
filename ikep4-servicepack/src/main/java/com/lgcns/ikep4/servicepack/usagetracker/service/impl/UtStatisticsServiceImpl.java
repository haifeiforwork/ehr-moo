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
import com.lgcns.ikep4.servicepack.usagetracker.dao.UtStatisticsDao;
import com.lgcns.ikep4.servicepack.usagetracker.model.UtLoginRank;
import com.lgcns.ikep4.servicepack.usagetracker.model.UtStatistics;
import com.lgcns.ikep4.servicepack.usagetracker.search.UtSearchCondition;
import com.lgcns.ikep4.servicepack.usagetracker.service.UtLoginRankService;
import com.lgcns.ikep4.servicepack.usagetracker.service.UtStatisticsService;
import com.lgcns.ikep4.servicepack.usagetracker.util.NumberUtils;
import com.lgcns.ikep4.servicepack.usagetracker.util.SearchUtil;
import com.lgcns.ikep4.util.idgen.service.IdgenService;

/**
 * 사용자현황통계
 *
 * @author ihko11
 * @version $Id: UtStatisticsServiceImpl.java 16244 2011-08-18 04:11:42Z giljae $
 */
@Service
@Transactional
public class UtStatisticsServiceImpl extends GenericServiceImpl<UtStatistics, String> implements UtStatisticsService {
	protected final Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private IdgenService idgenService;
	
	@Autowired
	private UtLoginRankService utLoginRankService;
	
	@Autowired
	private UtStatisticsDao utStatisticsDao;
	
	@Autowired
	public UtStatisticsServiceImpl(UtStatisticsDao dao) {
		super(dao);
		this.utStatisticsDao = dao;
	}
	
	/**
	 * 삭제
	 */
	@Override
	public void delete(String id) {
		utStatisticsDao.remove(id);
	}

	
	/**
	 * 상세
	 */
	@Override
	public UtStatistics read(String id) {
		return utStatisticsDao.get(id);
	}

	/**
	 * 생성
	 */
	@Override
	public String create(UtStatistics utLoginRank) {
		return utStatisticsDao.create(utLoginRank);
	}

	/**
	 * 수정
	 */
	@Override
	public void update(UtStatistics utLoginRank) {
		utStatisticsDao.update(utLoginRank);
	}
	
	/**
	 * 사용자별
	 */
	public List<UtStatistics> selectUserBatchReadItems(){
		return utStatisticsDao.selectUserBatchReadItems();
	}
	

	/**
	 * 메뉴별
	 * @return
	 */
	public List<UtStatistics> selectMenuBatchReadItems(){
		return utStatisticsDao.selectMenuBatchReadItems();
	}
	/**
	 * 브라우즈별
	 * @return
	 */
	public List<UtStatistics> selectBrowserBatchReadItems(){
		return utStatisticsDao.selectBrowserBatchReadItems();
	}
	/**
	 * 포틀릿별
	 * @return
	 */
	public List<UtStatistics> selectPortletBatchReadItems(){
		return utStatisticsDao.selectPortletBatchReadItems();
	}
	
	/**
	 * 사용량통계 전체 log 분석 후 사용량 분석테이블에 입력
	 */
	public void userStaticsSave(){
		
		List<UtStatistics> selectUserBatchReadItems = utStatisticsDao.selectUserBatchReadItems();
		
		//배치 넣는 날짜 data 있으면 삭제
		if(selectUserBatchReadItems.size() > 0){
			Date moduleDate = selectUserBatchReadItems.get(0).getModuleDate();
			utStatisticsDao.removeBatchStatisticsByDate(moduleDate);
		}
		
		for (UtStatistics utLoginRankLog : selectUserBatchReadItems) {
			utLoginRankLog.setId( idgenService.getNextId() );
			utLoginRankLog.setRegistDate(new Date() );
			utStatisticsDao.create(utLoginRankLog);
		}
		
		List<UtStatistics> selectMenuBatchReadItems = utStatisticsDao.selectMenuBatchReadItems();
		
		for (UtStatistics utLoginRankLog : selectMenuBatchReadItems) {
			utLoginRankLog.setId( idgenService.getNextId() );
			utLoginRankLog.setRegistDate(new Date() );
			utStatisticsDao.create(utLoginRankLog);
		}
		
		List<UtStatistics> selectBrowserBatchReadItems = utStatisticsDao.selectBrowserBatchReadItems();
		
		for (UtStatistics utLoginRankLog : selectBrowserBatchReadItems) {
			utLoginRankLog.setId( idgenService.getNextId() );
			utLoginRankLog.setRegistDate(new Date() );
			utStatisticsDao.create(utLoginRankLog);
		}
		
		List<UtStatistics> selectPortletBatchReadItems = utStatisticsDao.selectPortletBatchReadItems();
		
		for (UtStatistics utLoginRankLog : selectPortletBatchReadItems) {
			utLoginRankLog.setId( idgenService.getNextId() );
			utLoginRankLog.setRegistDate(new Date() );
			utStatisticsDao.create(utLoginRankLog);
		}
		
	}
	
	/**
	 * 사용량 통계 log 를 backup
	 */
	public void moveToAndRemove() {
		utStatisticsDao.moveToUserBatchItems();
		utStatisticsDao.moveToMenuBatchItems();
		utStatisticsDao.moveToPortletBatchItems();
		utStatisticsDao.removeUserBatchItems();
		utStatisticsDao.removeMenuBatchItems();
		utStatisticsDao.removePortletBatchItems();
	}
	
	
	/**
	 * 로그인 메인 로그 분석
	 * @param utSearchUtil
	 * @return
	 */
	public List<UtStatistics> loginMainStastic(SearchUtil utSearchUtil){
		return utStatisticsDao.loginMainStastic(utSearchUtil);
	}
	

	
	/**
	 * 메인 로그 브라우즈별
	 * @param utSearchUtil
	 * @return
	 */
	public List<UtStatistics> browseMainStastic(SearchUtil utSearchUtil){
		return utStatisticsDao.browseMainStastic(utSearchUtil);
	}
	
	
	/**
	 * 메인 로그 포틀릿
	 * @param utSearchUtil
	 * @return
	 */
	public List<UtStatistics> portletMainStastic(SearchUtil utSearchUtil){
		return utStatisticsDao.portletMainStastic(utSearchUtil);
	}
	
	
	/**
	 * 메인 로그 메뉴별
	 * @param utSearchUtil
	 * @return
	 */
	public List<UtStatistics> menuMainStastic(SearchUtil utSearchUtil){
		return utStatisticsDao.menuMainStastic(utSearchUtil);
	}
	
	
	
	/**
	 * 브라우즈별 로그
	 * @param utSearchUtil
	 * @return
	 */
	public List<UtStatistics> browseStastic(SearchUtil utSearchUtil){
		return utStatisticsDao.browseStastic(utSearchUtil);
	}
	
	
	/**
	 * 로그인 및 메뉴로그
	 * @param utSearchUtil
	 * @return
	 */
	public List<UtStatistics> publicStastic(SearchUtil utSearchUtil){
		return utStatisticsDao.publicStastic(utSearchUtil);
	}
	
	
	/**
	 * 메뉴리스트
	 */
	public SearchResult<UtStatistics> listBySearchCondition(UtSearchCondition searchCondition) {
		SearchResult<UtStatistics> searchResult = null;

		Integer count = this.utStatisticsDao.countBySearchCondition(searchCondition);
		searchCondition.terminateSearchCondition(count);

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<UtStatistics>(searchCondition);
		} else {
			List<UtStatistics> itemList = utStatisticsDao.listBySearchCondition(searchCondition);
			int totalSum = utStatisticsDao.sumBySearchCondition(searchCondition);
			
			for (UtStatistics utStatistics : itemList) {
				utStatistics.setUsageAvg( NumberUtils.round(utStatistics.getUsageCount(), totalSum) );
			}
			
			searchResult = new SearchResult<UtStatistics>(itemList, searchCondition);
		}

		return searchResult;
	}
	
	
	/**
	 * 메뉴리스트
	 */
	public List<UtStatistics> excelListBySearchCondition(UtSearchCondition searchCondition) {

			List<UtStatistics> itemList = utStatisticsDao.listBySearchCondition(searchCondition);
			int totalSum = utStatisticsDao.sumBySearchCondition(searchCondition);
			
			for (UtStatistics utStatistics : itemList) {
				utStatistics.setUsageAvg( NumberUtils.round(utStatistics.getUsageCount(), totalSum) );
			}
			
			return itemList;
	}
	
	
	/**
	 * total sum
	 * @param searchCondition
	 * @return
	 */
	public Integer sumBySearchCondition(UtSearchCondition searchCondition){
		return utStatisticsDao.sumBySearchCondition(searchCondition);
	}
	
	/**
	 * 최소값
	 */
	public String minBySearchCondition(UtSearchCondition searchCondition){
		return utStatisticsDao.minBySearchCondition(searchCondition);
	}

	/**
	 * 사용량 통계 배치
	 */
	public void utStatisticsBatch() {
		/**
		 * 사용자 3개월 전데이터 이동
		 */
		moveToAndRemove();
		
		/**
		 * 사용자 통계 로그 생성 - 로그 랭킹 업데이트
		 */
		List<UtLoginRank> utBatchLogList = utLoginRankService.selectUserBatchReadItems();
		utLoginRankService.saveOrUpdate(utBatchLogList);
		
		/**
		 * 통합통계생성
		 */
		userStaticsSave();
	}

	/**
	 * 현재 포틀릿 사용량 통계 리스트
	 * @param searchCondition
	 * @return
	 */
	public SearchResult<UtStatistics> listTodayPortlet(UtSearchCondition searchCondition) {
		SearchResult<UtStatistics> searchResult = null;

		Integer count = this.utStatisticsDao.countTodayPortlet(searchCondition);
		searchCondition.terminateSearchCondition(count);

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<UtStatistics>(searchCondition);
		} else {
			List<UtStatistics> itemList = utStatisticsDao.listTodayPortlet(searchCondition);
			
			int totalSum = utStatisticsDao.sumTodayPortlet();
			
			for (UtStatistics utStatistics : itemList) {
				utStatistics.setUsageAvg( NumberUtils.round(utStatistics.getUsageCount(), totalSum) );
			}
			
			searchResult = new SearchResult<UtStatistics>(itemList, searchCondition);
		}

		return searchResult;
	}
	
	/**
	 * 현재 포틀릿 사용량 통계 엑셀 리스트
	 * @param searchCondition
	 * @return
	 */
	public SearchResult<UtStatistics> excelListTodayPortlet(UtSearchCondition searchCondition) {
		SearchResult<UtStatistics> searchResult = null;

		List<UtStatistics> itemList = utStatisticsDao.listTodayPortlet(searchCondition);
		
		int totalSum = utStatisticsDao.sumTodayPortlet();
		
		for (UtStatistics utStatistics : itemList) {
			utStatistics.setUsageAvg( NumberUtils.round(utStatistics.getUsageCount(), totalSum) );
		}
		
		searchResult = new SearchResult<UtStatistics>(itemList, searchCondition);

		return searchResult;
	}

	/**
	 * 현재 포틀릿 사용량 통계 전체 리스트
	 * @return
	 */
	public List<UtStatistics> listTodayPortletTop10() {
		return utStatisticsDao.listTodayPortletTop10();
	}

	/**
	 * 현재 포틀릿 사용량 제일 작은 포틀릿 
	 * @return
	 */
	public String minUseageTodayPortlet() {
		return utStatisticsDao.minUseageTodayPortlet();
	}
}
