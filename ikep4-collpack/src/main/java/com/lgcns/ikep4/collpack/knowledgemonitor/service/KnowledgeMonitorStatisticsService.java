/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.knowledgemonitor.service;

import java.util.List;

import com.lgcns.ikep4.collpack.knowledgemonitor.model.KnowledgeMonitorAccumulation;
import com.lgcns.ikep4.collpack.knowledgemonitor.model.KnowledgeMonitorAccumulationChart;
import com.lgcns.ikep4.collpack.knowledgemonitor.model.KnowledgeMonitorStatistics;
import com.lgcns.ikep4.collpack.knowledgemonitor.model.KnowledgeMonitorStatisticsPK;
import com.lgcns.ikep4.collpack.knowledgemonitor.search.KnowledgeAccumulationSearchCondition;
import com.lgcns.ikep4.framework.core.service.GenericService;

/**
 * Knowledge Map KnowledgeMonitorStatisticsService interface
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMonitorStatisticsService.java 16236 2011-08-18 02:48:22Z giljae $
 */
public interface KnowledgeMonitorStatisticsService extends GenericService<KnowledgeMonitorStatistics, KnowledgeMonitorStatisticsPK> {

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
	 * 일별 지식등록건수 집계<br/>
	 * 매일 자정을 지나고 실행되기때문에 입력조건 없이 (sysdate - 1) 을 기준으로 한다.
	 */
	void batchGatherKnowledge();

	/**
	 * portalId별 차트 데이터 조회 (포틀릿용)
	 * @param accumulationSearchCondition
	 * @return List<KnowledgeMonitorModule>
	 */
	List<KnowledgeMonitorAccumulationChart> listChartBySearchConditionPortlet(KnowledgeAccumulationSearchCondition accumulationSearchCondition);

}
