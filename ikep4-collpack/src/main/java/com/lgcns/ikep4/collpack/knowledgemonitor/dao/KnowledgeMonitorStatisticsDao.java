/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.knowledgemonitor.dao;

import java.util.List;

import com.lgcns.ikep4.collpack.knowledgemonitor.model.KnowledgeMonitorAccumulation;
import com.lgcns.ikep4.collpack.knowledgemonitor.model.KnowledgeMonitorAccumulationChart;
import com.lgcns.ikep4.collpack.knowledgemonitor.model.KnowledgeMonitorStatistics;
import com.lgcns.ikep4.collpack.knowledgemonitor.model.KnowledgeMonitorStatisticsPK;
import com.lgcns.ikep4.collpack.knowledgemonitor.search.KnowledgeAccumulationSearchCondition;
import com.lgcns.ikep4.framework.core.dao.GenericDao;

/**
 * Knowledge Monitor KnowledgeMonitorStatisticsDao interface
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMonitorStatisticsDao.java 16236 2011-08-18 02:48:22Z giljae $
 */
public interface KnowledgeMonitorStatisticsDao extends GenericDao<KnowledgeMonitorStatistics, KnowledgeMonitorStatisticsPK> {

	/**
	 * portalId별 차트 데이터 조회
	 * @param accumulationSearchCondition
	 * @return List<KnowledgeMonitorModule>
	 */
	List<KnowledgeMonitorAccumulationChart> listChartBySearchCondition(KnowledgeAccumulationSearchCondition accumulationSearchCondition);

	/**
	 * portalId별 데이터 조회
	 * @param accumulationSearchCondition
	 * @return List<KnowledgeMonitorModule>
	 */
	List<KnowledgeMonitorAccumulation> listBySearchCondition(KnowledgeAccumulationSearchCondition accumulationSearchCondition);

	/**
	 * 배치작업<br/>
	 * 일별 지식등록건수 집계 (WorkSpace)
	 */
	void batchGatherWorkSpace();

	/**
	 * 배치작업<br/>
	 * 일별 지식등록건수 집계 (Cafe)
	 */
	void batchGatherCafe();

	/**
	 * 배치작업<br/>
	 * 일별 지식등록건수 집계 (WorkManual)
	 */
	void batchGatherWorkManual();

	/**
	 * 배치작업<br/>
	 * 일별 지식등록건수 집계 (SocialBlog)
	 */
	void batchGatherSocialBlog();

	/**
	 * 배치작업<br/>
	 * 일별 지식등록건수 집계 (Q&A)
	 */
	void batchGatherQnA();

	/**
	 * 배치작업<br/>
	 * 일별 지식등록건수 집계 (Forum)
	 */
	void batchGatherForum();

	/**
	 * 배치작업<br/>
	 * 일별 지식등록건수 집계 (Ideation)
	 */
	void batchGatherIdeation();

	/**
	 * 배치작업<br/>
	 * 일별 지식등록건수 집계 (CoporateVoca)
	 */
	void batchGatherCorVoca();

	/**
	 * 배치작업<br/>
	 * 일별 지식등록건수 집계 (BBS)
	 */
	void batchGatherBBS();

	/**
	 * portalId별 차트 데이터 조회 (포틀릿용)
	 * @param accumulationSearchCondition
	 * @return List<KnowledgeMonitorModule>
	 */
	List<KnowledgeMonitorAccumulationChart> listChartBySearchConditionPortlet(KnowledgeAccumulationSearchCondition accumulationSearchCondition);
	
}
