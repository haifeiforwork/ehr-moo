/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.servicepack.usagetracker.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.servicepack.usagetracker.model.UtStatistics;
import com.lgcns.ikep4.servicepack.usagetracker.search.UtSearchCondition;
import com.lgcns.ikep4.servicepack.usagetracker.util.SearchUtil;

/**
 * 현황통계
 *
 * @author ihko11
 * @version $Id: UtStatisticsService.java 16244 2011-08-18 04:11:42Z giljae $
 */
@Transactional
public interface UtStatisticsService extends GenericService<UtStatistics, String> {
	public List<UtStatistics> selectUserBatchReadItems();

	/**
	 * 메뉴별
	 * @return
	 */
	public List<UtStatistics> selectMenuBatchReadItems();
	
	/**
	 * 브라우즈별
	 * @return
	 */
	public List<UtStatistics> selectBrowserBatchReadItems();
	
	/**
	 * 포틀릿별
	 * @return
	 */
	public List<UtStatistics> selectPortletBatchReadItems();
	/**
	 * 사용자 통계 저장 backup
	 *
	 */
	public void userStaticsSave();
	
	/**
	 * 사용량 통계 backup후 원 데이터 삭제
	 *
	 */
	public void moveToAndRemove();
	
	
	/**
	 * 로그인 메인 로그 분석
	 * @param utSearchUtil
	 * @return
	 */
	public List<UtStatistics> loginMainStastic(SearchUtil utSearchUtil);
	
	/**
	 * 메인 로그 브라우즈별
	 * @param utSearchUtil
	 * @return
	 */
	public List<UtStatistics> browseMainStastic(SearchUtil utSearchUtil);
	
	
	/**
	 * 메인 로그 포틀릿
	 * @param utSearchUtil
	 * @return
	 */
	public List<UtStatistics> portletMainStastic(SearchUtil utSearchUtil);
	
	
	/**
	 * 메뉴별
	 * @param utSearchUtil
	 * @return
	 */
	public List<UtStatistics> menuMainStastic(SearchUtil utSearchUtil);
	
	
	/**
	 * 메뉴및 로그인
	 * @param utSearchUtil
	 * @return
	 */
	public List<UtStatistics> publicStastic(SearchUtil utSearchUtil);
	
	/**
	 *  브라우즈별
	 * @param utSearchUtil
	 * @return
	 */
	public List<UtStatistics> browseStastic(SearchUtil utSearchUtil);
	
	/**
	 * 메뉴 포틀릿 리스트
	 * @param searchCondition
	 * @return
	 */
	public SearchResult<UtStatistics> listBySearchCondition(UtSearchCondition searchCondition) ;
	
	/**
	 * total sum
	 * @param searchCondition
	 * @return
	 */
	Integer sumBySearchCondition(UtSearchCondition searchCondition); 
	
	/**
	 * 최소값
	 * @param searchCondition
	 * @return
	 */
	public String minBySearchCondition(UtSearchCondition searchCondition);
	
	
	public List<UtStatistics> excelListBySearchCondition(UtSearchCondition searchCondition) ;
	
	/**
	 * 사용량 통계 배치
	 */
	public void utStatisticsBatch();
	
	/**
	 * 현재 포틀릿 사용량 통계 리스트
	 * @param searchCondition
	 * @return
	 */
	public SearchResult<UtStatistics> listTodayPortlet(UtSearchCondition searchCondition);
	
	/**
	 * 현재 포틀릿 사용량 통계 엑셀 리스트
	 * @param searchCondition
	 * @return
	 */
	public SearchResult<UtStatistics> excelListTodayPortlet(UtSearchCondition searchCondition);
	
	/**
	 * 현재 포틀릿 사용량 통계 전체 리스트
	 * @return
	 */
	public List<UtStatistics> listTodayPortletTop10();
	
	/**
	 * 현재 포틀릿 사용량 제일 작은 포틀릿 
	 * @return
	 */
	public String minUseageTodayPortlet();
}
