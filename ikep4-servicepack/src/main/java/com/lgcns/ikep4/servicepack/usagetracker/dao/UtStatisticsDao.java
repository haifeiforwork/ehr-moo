/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.servicepack.usagetracker.dao;

import java.util.Date;
import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.servicepack.usagetracker.model.UtStatistics;
import com.lgcns.ikep4.servicepack.usagetracker.search.UtSearchCondition;
import com.lgcns.ikep4.servicepack.usagetracker.util.SearchUtil;

/**
 * 현황 통계
 *
 * @author ihko11
 * @version $Id: UtStatisticsDao.java 16244 2011-08-18 04:11:42Z giljae $
 */
public interface UtStatisticsDao extends GenericDao<UtStatistics,String> {
	/**
	 * 배치 유저 로그인 통계 읽기
	 * @return
	 */
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
	 * 사용자통계 이동
	 *
	 */
	public void moveToUserBatchItems();
	/**
	 * 메뉴통계이통
	 *
	 */
	public void moveToMenuBatchItems() ;
	/**
	 * 포틀릿 통계 이동
	 *
	 */
	public void moveToPortletBatchItems();
	/**
	 * 사용자통계 삭제
	 *
	 */
	public void removeUserBatchItems();
	/**
	 * 메뉴통계 삭제
	 *
	 */
	public void removeMenuBatchItems();
	/**
	 * 포틀릿 통계 삭제
	 *
	 */
	public void removePortletBatchItems();
	
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
	 * 메인 로그 메뉴별
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
	 * 통계 메뉴구성 리스트
	 * @param searchCondition
	 * @return
	 */
	List<UtStatistics> listBySearchCondition(UtSearchCondition searchCondition);
	/**
	 * 통계 메뉴 카운트
	 * @param searchCondition
	 * @return
	 */
	Integer countBySearchCondition(UtSearchCondition searchCondition); 
	
	/**
	 * total sum
	 * @param searchCondition
	 * @return
	 */
	Integer sumBySearchCondition(UtSearchCondition searchCondition); 
	
	/**
	 * 최소값 구하기
	 * @param searchCondition
	 * @return
	 */
	public String minBySearchCondition(UtSearchCondition searchCondition);
	
	/**
	 * 현재 포틀릿 통계 리스트 카운트
	 * @param searchCondition
	 * @return
	 */
	public Integer countTodayPortlet(UtSearchCondition searchCondition); 
	
	/**
	 * 현재 포틀릿 통계 리스트
	 * @param searchCondition
	 * @return
	 */
	public List<UtStatistics> listTodayPortlet(UtSearchCondition searchCondition);
	
	/**
	 * 현재 포틀릿 통계 전체 리스트
	 * @return
	 */
	public List<UtStatistics> listTodayPortletTop10();
	
	/**
	 * 현재 포틀릿 통계 전체 카운트
	 * @return
	 */
	public Integer sumTodayPortlet();
	
	/**
	 * 현재 포틀릿 사용량 제일 작은 포틀릿 
	 * @return
	 */
	public String minUseageTodayPortlet();
	
	/**
	 * 배치 데이터 같은 날짜 있으면 지우기
	 *@param moduleDate
	 * @return
	 */
	public void removeBatchStatisticsByDate(Date moduleDate);
	
}
