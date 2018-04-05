/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.knowledgemonitor.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.collpack.knowledgemonitor.dao.KnowledgeMonitorStatisticsDao;
import com.lgcns.ikep4.collpack.knowledgemonitor.model.KnowledgeMonitorAccumulation;
import com.lgcns.ikep4.collpack.knowledgemonitor.model.KnowledgeMonitorAccumulationChart;
import com.lgcns.ikep4.collpack.knowledgemonitor.model.KnowledgeMonitorStatistics;
import com.lgcns.ikep4.collpack.knowledgemonitor.model.KnowledgeMonitorStatisticsPK;
import com.lgcns.ikep4.collpack.knowledgemonitor.search.KnowledgeAccumulationSearchCondition;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;

/**
 * Knowledge Monitor KnowledgeMonitorStatisticsDao implementation
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMonitorStatisticsDaoImpl.java 16295 2011-08-19 07:49:49Z giljae $
 */
@Repository
public class KnowledgeMonitorStatisticsDaoImpl extends GenericDaoSqlmap<KnowledgeMonitorStatistics, KnowledgeMonitorStatisticsPK> implements KnowledgeMonitorStatisticsDao {
	private static final String NAMESPACE = "collpack.knowledgemonitor.dao.KnowledgeMonitorStatistics.";

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	@Deprecated
	public KnowledgeMonitorStatistics get(KnowledgeMonitorStatisticsPK id) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable)
	 */
	@Deprecated
	public boolean exists(KnowledgeMonitorStatisticsPK id) {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	@Deprecated
	public KnowledgeMonitorStatisticsPK create(KnowledgeMonitorStatistics object) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	@Deprecated
	public void update(KnowledgeMonitorStatistics object) {
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable)
	 */
	@Deprecated
	public void remove(KnowledgeMonitorStatisticsPK id) {
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgemonitor.dao.KnowledgeMonitorStatisticsDao#listByPortalId(com.lgcns.ikep4.collpack.knowledgemonitor.search.AccumulationSearchCondition)
	 */
	@SuppressWarnings("unchecked")
	public List<KnowledgeMonitorAccumulationChart> listChartBySearchCondition(
			KnowledgeAccumulationSearchCondition accumulationSearchCondition) {
		List<KnowledgeMonitorAccumulationChart> knowledgeMonitorAccumulationChart = getSqlMapClientTemplate().queryForList(NAMESPACE + "listChartBySearchCondition", accumulationSearchCondition);

		return knowledgeMonitorAccumulationChart;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgemonitor.dao.KnowledgeMonitorStatisticsDao#listBySearchCondition(com.lgcns.ikep4.collpack.knowledgemonitor.search.AccumulationSearchCondition)
	 */
	@SuppressWarnings("unchecked")
	public List<KnowledgeMonitorAccumulation> listBySearchCondition(
			KnowledgeAccumulationSearchCondition accumulationSearchCondition) {
		List<KnowledgeMonitorAccumulation> knowledgeMonitorAccumulation = getSqlMapClientTemplate().queryForList(NAMESPACE + "listBySearchCondition", accumulationSearchCondition);

		return knowledgeMonitorAccumulation;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgemonitor.dao.KnowledgeMonitorStatisticsDao#batchGatherBBS()
	 */
	public void batchGatherBBS() {
		sqlInsert(NAMESPACE + "batchGatherBBS");
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgemonitor.dao.KnowledgeMonitorStatisticsDao#batchGatherWorkSpace()
	 */
	public void batchGatherWorkSpace() {
		sqlInsert(NAMESPACE + "batchGatherWorkSpace");
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgemonitor.dao.KnowledgeMonitorStatisticsDao#batchGatherCafe()
	 */
	public void batchGatherCafe() {
		sqlInsert(NAMESPACE + "batchGatherCafe");
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgemonitor.dao.KnowledgeMonitorStatisticsDao#batchGatherWorkManual()
	 */
	public void batchGatherWorkManual() {
		sqlInsert(NAMESPACE + "batchGatherWorkManual");
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgemonitor.dao.KnowledgeMonitorStatisticsDao#batchGatherSocialBlog()
	 */
	public void batchGatherSocialBlog() {
		sqlInsert(NAMESPACE + "batchGatherSocialBlog");
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgemonitor.dao.KnowledgeMonitorStatisticsDao#batchGatherQnA()
	 */
	public void batchGatherQnA() {
		sqlInsert(NAMESPACE + "batchGatherQnA");
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgemonitor.dao.KnowledgeMonitorStatisticsDao#batchGatherForum()
	 */
	public void batchGatherForum() {
		sqlInsert(NAMESPACE + "batchGatherForum");
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgemonitor.dao.KnowledgeMonitorStatisticsDao#batchGatherIdeation()
	 */
	public void batchGatherIdeation() {
		sqlInsert(NAMESPACE + "batchGatherIdeation");
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgemonitor.dao.KnowledgeMonitorStatisticsDao#batchGatherCorVoca()
	 */
	public void batchGatherCorVoca() {
		sqlInsert(NAMESPACE + "batchGatherCorVoca");
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgemonitor.dao.KnowledgeMonitorStatisticsDao#listChartBySearchConditionPortlet(com.lgcns.ikep4.collpack.knowledgemonitor.search.KnowledgeAccumulationSearchCondition)
	 */
	@SuppressWarnings("unchecked")
	public List<KnowledgeMonitorAccumulationChart> listChartBySearchConditionPortlet(
			KnowledgeAccumulationSearchCondition accumulationSearchCondition) {
		List<KnowledgeMonitorAccumulationChart> knowledgeMonitorAccumulationChart = getSqlMapClientTemplate().queryForList(NAMESPACE + "listChartBySearchConditionPortlet", accumulationSearchCondition);

		return knowledgeMonitorAccumulationChart;
	}

}
