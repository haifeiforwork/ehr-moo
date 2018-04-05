/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.servicepack.usagetracker.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.servicepack.usagetracker.dao.UtStatisticsDao;
import com.lgcns.ikep4.servicepack.usagetracker.model.UtStatistics;
import com.lgcns.ikep4.servicepack.usagetracker.search.UtSearchCondition;
import com.lgcns.ikep4.servicepack.usagetracker.util.MagicNumUtils;
import com.lgcns.ikep4.servicepack.usagetracker.util.SearchUtil;

/**
 * 사용자현황통계
 *
 * @author ihko11
 * @version $Id: UtStatisticsDaoImpl.java 16244 2011-08-18 04:11:42Z giljae $
 */
@Repository
public class UtStatisticsDaoImpl extends GenericDaoSqlmap<UtStatistics,String> implements UtStatisticsDao {
	
	private static final String NAMESPACE = "com.lgcns.ikep4.servicepack.usagetracker.model.utStatistics.";
	
	/**
	 * 읽기
	 */
	public UtStatistics get(String id) {
		return (UtStatistics) sqlSelectForObject(NAMESPACE+"get", id);
	}

	/**
	 * 존재여부
	 */
	public boolean exists(String id) {
		Integer count = (Integer)sqlSelectForObject(NAMESPACE+"exists", id);
		return count > 0;
	}
	
	/**
	 * 생성
	 */
	public String create(UtStatistics utStatistics) {
		sqlInsert(NAMESPACE+"create", utStatistics);
		
		return utStatistics.getId();
	}

	/**
	 * 수정
	 */
	public void update(UtStatistics UtStatistics) {
		sqlUpdate(NAMESPACE+"update", UtStatistics);
	}

	/**
	 * 삭제
	 */
	public void remove(String id) {
		sqlDelete(NAMESPACE+"remove", id);
	}
	
	/**
	 * 배치 사용자 통계 읽기
	 */
	public List<UtStatistics> selectUserBatchReadItems(){
		return sqlSelectForList(NAMESPACE+"selectUserBatchReadItems");
	}
	
	/**
	 * 배치 메뉴통계 읽기
	 */
	public List<UtStatistics> selectMenuBatchReadItems(){
		return sqlSelectForList(NAMESPACE+"selectMenuBatchReadItems");
	}
	
	/**
	 * 브라우즈 통계읽기
	 */
	public List<UtStatistics> selectBrowserBatchReadItems(){
		return sqlSelectForList(NAMESPACE+"selectBrowserBatchReadItems");
	}
	
	/**
	 * 포틀릿통계 읽기
	 */
	public List<UtStatistics> selectPortletBatchReadItems(){
		return sqlSelectForList(NAMESPACE+"selectPortletBatchReadItems");
	}
	
	public void moveToUserBatchItems() {
		sqlDelete(NAMESPACE+"moveToUserBatchItems");
	}
	public void moveToMenuBatchItems() {
		sqlDelete(NAMESPACE+"moveToMenuBatchItems");
	}
	public void moveToPortletBatchItems() {
		sqlDelete(NAMESPACE+"moveToPortletBatchItems");
	}
	public void removeUserBatchItems() {
		sqlDelete(NAMESPACE+"removeUserBatchItems");
	}
	public void removeMenuBatchItems() {
		sqlDelete(NAMESPACE+"removeMenuBatchItems");
	}
	public void removePortletBatchItems() {
		sqlDelete(NAMESPACE+"removePortletBatchItems");
	}
	
	
	/**
	 * 로그인 메인 로그 분석
	 * @param utSearchUtil
	 * @return
	 */
	public List<UtStatistics> loginMainStastic(SearchUtil utSearchUtil){
		return sqlSelectForList(NAMESPACE+"loginMainStastic",utSearchUtil,0,MagicNumUtils.ARRAY_SIZE_10);
	}

	/**
	 * 메인 로그 브라우즈별
	 * @param utSearchUtil
	 * @return
	 */
	public List<UtStatistics> browseMainStastic(SearchUtil utSearchUtil){
		return sqlSelectForList(NAMESPACE+"browseMainStastic",utSearchUtil,0,MagicNumUtils.ARRAY_SIZE_10);
	}
	
	
	/**
	 * 메인 로그 포틀릿
	 * @param utSearchUtil
	 * @return
	 */
	public List<UtStatistics> portletMainStastic(SearchUtil utSearchUtil){
		return sqlSelectForList(NAMESPACE+"portletMainStastic",utSearchUtil,0,MagicNumUtils.ARRAY_SIZE_10);
	}
	
	
	/**
	 * 메인 로그 메뉴별
	 * @param utSearchUtil
	 * @return
	 */
	public List<UtStatistics> menuMainStastic(SearchUtil utSearchUtil){
		return sqlSelectForList(NAMESPACE+"menuMainStastic",utSearchUtil,0,MagicNumUtils.ARRAY_SIZE_10);
	}
	
	/**
	 * 메뉴및 로그인
	 */
	public List<UtStatistics> publicStastic(SearchUtil utSearchUtil){
		return sqlSelectForList(NAMESPACE+"publicStastic"+utSearchUtil.getViewOption(),utSearchUtil);
	}
	
	/**
	 * 브라우즈별
	 */
	public List<UtStatistics> browseStastic(SearchUtil utSearchUtil){
		return sqlSelectForList(NAMESPACE+"browseStastic",utSearchUtil);
	}
	
	/**
	 * 통계 메뉴리스트
	 */
	public List<UtStatistics> listBySearchCondition(UtSearchCondition searchCondition) { 
		return (List<UtStatistics>)this.sqlSelectForList(NAMESPACE + "listBySearchCondition"+searchCondition.getProcess(), searchCondition);
	}

	/**
	 * 통계 메뉴카운트
	 */
	public Integer countBySearchCondition(UtSearchCondition searchCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countBySearchCondition"+searchCondition.getProcess(), searchCondition);
	} 

	/**
	 * total sum
	 */
	public Integer sumBySearchCondition(UtSearchCondition searchCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "sumBySearchCondition"+searchCondition.getProcess(), searchCondition);
	}
	
	
	/**
	 * total sum
	 */
	public String minBySearchCondition(UtSearchCondition searchCondition) {
		return (String)this.sqlSelectForObject(NAMESPACE + "minBySearchCondition"+searchCondition.getProcess(), searchCondition);
	}
	
    /**
	 * 현재 포틀릿 통계 리스트 카운트
	 * @param searchCondition
	 * @return
	 */
	public Integer countTodayPortlet(UtSearchCondition searchCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countTodayPortlet", searchCondition);
	}


    /**
	 * 현재 포틀릿 통계 리스트
	 * @param searchCondition
	 * @return
	*/
    public List<UtStatistics> listTodayPortlet(UtSearchCondition searchCondition) {
		return (List<UtStatistics>)this.sqlSelectForList(NAMESPACE + "listTodayPortlet", searchCondition);
	}
	
    /**
	 * 현재 포틀릿 통계 전체 리스트
	 * @return
	 */
	public List<UtStatistics> listTodayPortletTop10() {
		return (List<UtStatistics>)this.sqlSelectForList(NAMESPACE + "listTodayPortletTop10");
	}

	/**
	 * 현재 포틀릿 통계 전체 카운트
	 * @return
	 */
	public Integer sumTodayPortlet() {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "sumTodayPortlet");
	}

    /**
	 * 현재 포틀릿 사용량 제일 작은 포틀릿 
	 * @return
	 */
	public String minUseageTodayPortlet() {
		return (String)this.sqlSelectForObject(NAMESPACE + "minUseageTodayPortlet");
	}

	
	public void removeBatchStatisticsByDate(Date moduleDate) {
		sqlDelete(NAMESPACE+"removeBatchStatisticsByDate", moduleDate);
	}
}
