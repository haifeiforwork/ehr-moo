/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.knowledgemonitor.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.collpack.knowledgemonitor.dao.KnowledgeMonitorStatisticsDao;
import com.lgcns.ikep4.collpack.knowledgemonitor.model.KnowledgeMonitorAccumulation;
import com.lgcns.ikep4.collpack.knowledgemonitor.model.KnowledgeMonitorAccumulationChart;
import com.lgcns.ikep4.collpack.knowledgemonitor.model.KnowledgeMonitorStatistics;
import com.lgcns.ikep4.collpack.knowledgemonitor.model.KnowledgeMonitorStatisticsPK;
import com.lgcns.ikep4.collpack.knowledgemonitor.search.KnowledgeAccumulationSearchCondition;
import com.lgcns.ikep4.collpack.knowledgemonitor.service.KnowledgeMonitorStatisticsService;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;

/**
 * Knowledge Map KnowledgeMonitorStatisticsService implementation
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMonitorStatisticsServiceImpl.java 19576 2012-07-02 05:35:19Z malboru80 $
 */
@Service
@Transactional
public class KnowledgeMonitorStatisticsServiceImpl extends GenericServiceImpl<KnowledgeMonitorStatistics, KnowledgeMonitorStatisticsPK> implements KnowledgeMonitorStatisticsService {

	private KnowledgeMonitorStatisticsDao knowledgeMonitorStatisticsDao;

	@Autowired
	public KnowledgeMonitorStatisticsServiceImpl(KnowledgeMonitorStatisticsDao dao) {
		super(dao);
		this.knowledgeMonitorStatisticsDao = dao;
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgemonitor.service.KnowledgeMonitorStatisticsService#listChartBySearchCondition(com.lgcns.ikep4.collpack.knowledgemonitor.search.AccumulationSearchCondition)
	 */
	public List<KnowledgeMonitorAccumulationChart> listChartBySearchCondition(
			KnowledgeAccumulationSearchCondition accumulationSearchCondition) {
		return knowledgeMonitorStatisticsDao.listChartBySearchCondition(accumulationSearchCondition);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgemonitor.service.KnowledgeMonitorStatisticsService#listBySearchCondition(com.lgcns.ikep4.collpack.knowledgemonitor.search.AccumulationSearchCondition)
	 */
	public List<KnowledgeMonitorAccumulation> listBySearchCondition(
			KnowledgeAccumulationSearchCondition accumulationSearchCondition) {
		return knowledgeMonitorStatisticsDao.listBySearchCondition(accumulationSearchCondition);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgemonitor.service.KnowledgeMonitorStatisticsService#batchGatherKnowledge()
	 */
	public void batchGatherKnowledge() {
		knowledgeMonitorStatisticsDao.batchGatherBBS();
		knowledgeMonitorStatisticsDao.batchGatherCafe();
		knowledgeMonitorStatisticsDao.batchGatherCorVoca();
		knowledgeMonitorStatisticsDao.batchGatherForum();
		knowledgeMonitorStatisticsDao.batchGatherIdeation();
		knowledgeMonitorStatisticsDao.batchGatherQnA();
		knowledgeMonitorStatisticsDao.batchGatherSocialBlog();
		knowledgeMonitorStatisticsDao.batchGatherWorkManual();
		knowledgeMonitorStatisticsDao.batchGatherWorkSpace();
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgemonitor.service.KnowledgeMonitorStatisticsService#listChartBySearchConditionPortlet(com.lgcns.ikep4.collpack.knowledgemonitor.search.KnowledgeAccumulationSearchCondition)
	 */
	public List<KnowledgeMonitorAccumulationChart> listChartBySearchConditionPortlet(
			KnowledgeAccumulationSearchCondition accumulationSearchCondition) {
		return knowledgeMonitorStatisticsDao.listChartBySearchConditionPortlet(accumulationSearchCondition);
	}

}
