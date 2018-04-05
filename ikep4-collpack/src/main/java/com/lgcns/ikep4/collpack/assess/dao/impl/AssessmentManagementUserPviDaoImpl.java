/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.assess.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.collpack.assess.dao.AssessmentManagementUserPviDao;
import com.lgcns.ikep4.collpack.assess.model.AssessmentManagementUserPvi;
import com.lgcns.ikep4.collpack.assess.model.AssessmentManagementUserPviPK;
import com.lgcns.ikep4.collpack.assess.search.AssessmentManagementBlockPageCondition;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;

/**
 * Assessment Management AssessmentManagementUserPviDao implementation
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: AssessmentManagementUserPviDaoImpl.java 16457 2011-08-30 04:20:17Z giljae $
 */
@Repository
public class AssessmentManagementUserPviDaoImpl extends GenericDaoSqlmap<AssessmentManagementUserPvi, AssessmentManagementUserPviPK> implements AssessmentManagementUserPviDao {
	private static final String NAMESPACE = "collpack.assess.dao.AssessmentManagementUserPvi.";

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	@Deprecated
	public AssessmentManagementUserPvi get(AssessmentManagementUserPviPK id) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable)
	 */
	@Deprecated
	public boolean exists(AssessmentManagementUserPviPK id) {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	@Deprecated
	public AssessmentManagementUserPviPK create(AssessmentManagementUserPvi object) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	@Deprecated
	public void update(AssessmentManagementUserPvi object) {
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable)
	 */
	@Deprecated
	public void remove(AssessmentManagementUserPviPK id) {
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.assess.dao.AssessmentManagementUserPviDao#countByPortalIdPage(com.lgcns.ikep4.collpack.assess.search.AssessmentManagementBlockPageCondition)
	 */
	public int countByPortalIdPage(AssessmentManagementBlockPageCondition assessmentManagementBlockPageCondition) {
		return (Integer)sqlSelectForObject(NAMESPACE + "countByPortalIdPage", assessmentManagementBlockPageCondition);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.assess.dao.AssessmentManagementUserPviDao#listByPortalIdPage(com.lgcns.ikep4.collpack.assess.search.AssessmentManagementBlockPageCondition)
	 */
	public List<AssessmentManagementUserPvi> listByPortalIdPage(
			AssessmentManagementBlockPageCondition assessmentManagementBlockPageCondition) {
		return sqlSelectForList(NAMESPACE + "listByPortalIdPage", assessmentManagementBlockPageCondition);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.assess.dao.AssessmentManagementUserPviDao#getByUserId(java.lang.String)
	 */
	public AssessmentManagementUserPvi getByUserId(String userId) {
		return (AssessmentManagementUserPvi)sqlSelectForObject(NAMESPACE + "getByUserId", userId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.assess.dao.AssessmentManagementUserPviDao#countByGroupIdPage(com.lgcns.ikep4.collpack.assess.search.AssessmentManagementBlockPageCondition)
	 */
	public int countByGroupIdPage(AssessmentManagementBlockPageCondition assessmentManagementBlockPageCondition) {
		return (Integer)sqlSelectForObject(NAMESPACE + "countByGroupIdPage", assessmentManagementBlockPageCondition);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.assess.dao.AssessmentManagementUserPviDao#listByGroupIdPage(com.lgcns.ikep4.collpack.assess.search.AssessmentManagementBlockPageCondition)
	 */
	public List<AssessmentManagementUserPvi> listByGroupIdPage(
			AssessmentManagementBlockPageCondition assessmentManagementBlockPageCondition) {
		return sqlSelectForList(NAMESPACE + "listByGroupIdPage", assessmentManagementBlockPageCondition);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.assess.dao.AssessmentManagementUserPviDao#initScoreByPortalId(java.lang.String, java.lang.String)
	 */
	public void initScoreByPortalId(String portalId, String registerId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("portalId", portalId);
		map.put("registerId", registerId);

		sqlUpdate(NAMESPACE + "initScoreByPortalId", map);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.assess.dao.AssessmentManagementUserPviDao#getMaxPviByPortalId(java.lang.String)
	 */
	public int getMaxPviByPortalId(String portalId) {
		return (Integer)sqlSelectForObject(NAMESPACE + "getMaxPviByPortalId", portalId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.assess.dao.AssessmentManagementUserPviDao#truncateUserActivities()
	 */
	public void truncateUserActivities() {
		getSqlMapClientTemplate().queryForObject(NAMESPACE + "truncateUserActivities");
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.assess.dao.AssessmentManagementUserPviDao#batchGatherRegistScore()
	 */
	public void batchGatherRegistScore() {
		sqlInsert(NAMESPACE + "batchGatherRegistScore");
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.assess.dao.AssessmentManagementUserPviDao#batchGatherAnswerScore()
	 */
	public void batchGatherAnswerScore() {
		sqlInsert(NAMESPACE + "batchGatherAnswerScore");
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.assess.dao.AssessmentManagementUserPviDao#batchGatherLinereplyScore()
	 */
	public void batchGatherLinereplyScore() {
		sqlInsert(NAMESPACE + "batchGatherLinereplyScore");
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.assess.dao.AssessmentManagementUserPviDao#batchGatherRecommendScore()
	 */
	public void batchGatherRecommendScore() {
		sqlInsert(NAMESPACE + "batchGatherRecommendScore");
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.assess.dao.AssessmentManagementUserPviDao#batchGatherHitScore()
	 */
	public void batchGatherHitScore() {
		sqlInsert(NAMESPACE + "batchGatherHitScore");
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.assess.dao.AssessmentManagementUserPviDao#batchGatherSearchScore()
	 */
	public void batchGatherSearchScore() {
		sqlInsert(NAMESPACE + "batchGatherSearchScore");
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.assess.dao.AssessmentManagementUserPviDao#batchGatherFollowScore()
	 */
	public void batchGatherFollowScore() {
		sqlInsert(NAMESPACE + "batchGatherFollowScore");
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.assess.dao.AssessmentManagementUserPviDao#batchGatherFollowingScore()
	 */
	public void batchGatherFollowingScore() {
		sqlInsert(NAMESPACE + "batchGatherFollowingScore");
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.assess.dao.AssessmentManagementUserPviDao#batchGatherCrossFollowingScore()
	 */
	public void batchGatherCrossFollowingScore() {
		sqlInsert(NAMESPACE + "batchGatherCrossFollowingScore");
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.assess.dao.AssessmentManagementUserPviDao#batchGatherMblogGroupScore()
	 */
	public void batchGatherMblogGroupScore() {
		sqlInsert(NAMESPACE + "batchGatherMblogGroupScore");
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.assess.dao.AssessmentManagementUserPviDao#batchGatherGuestbookItemScore()
	 */
	public void batchGatherGuestbookItemScore() {
		sqlInsert(NAMESPACE + "batchGatherGuestbookItemScore");
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.assess.dao.AssessmentManagementUserPviDao#batchGatherTweetScore()
	 */
	public void batchGatherTweetScore() {
		sqlInsert(NAMESPACE + "batchGatherTweetScore");
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.assess.dao.AssessmentManagementUserPviDao#batchGatherRetweetScore()
	 */
	public void batchGatherRetweetScore() {
		sqlInsert(NAMESPACE + "batchGatherRetweetScore");
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.assess.dao.AssessmentManagementUserPviDao#batchGatherProfileVisitScore()
	 */
	public void batchGatherProfileVisitScore() {
		sqlInsert(NAMESPACE + "batchGatherProfileVisitScore");
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.assess.dao.AssessmentManagementUserPviDao#batchRemoveCviPoint()
	 */
	public void batchRemoveUserPviPoint() {
		sqlDelete(NAMESPACE + "batchRemoveUserPviPoint");
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.assess.dao.AssessmentManagementUserPviDao#batchCalcUserPvi()
	 */
	public void batchCalcUserPvi() {
		sqlInsert(NAMESPACE + "batchCalcUserPvi");
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.assess.dao.AssessmentManagementUserPviDao#batchRemoveGroupPviPoint()
	 */
	public void batchRemoveGroupPviPoint() {
		sqlDelete(NAMESPACE + "batchRemoveGroupPviPoint");
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.assess.dao.AssessmentManagementUserPviDao#batchCalcGroupPvi()
	 */
	public void batchAppendLeafGroupPvi() {
		sqlInsert(NAMESPACE + "batchAppendLeafGroupPvi");
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.assess.dao.AssessmentManagementUserPviDao#batchAppendNoLeafGroupPvi()
	 */
	public void batchAppendNoLeafGroupPvi() {
		sqlInsert(NAMESPACE + "batchAppendNoLeafGroupPvi");
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.assess.dao.AssessmentManagementUserPviDao#batchGetMaxDepth()
	 */
	public int batchGetMaxDepth() {
		return (Integer)sqlSelectForObject(NAMESPACE + "batchGetMaxDepth");
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.assess.dao.AssessmentManagementUserPviDao#batchCalcGroupPvi(int)
	 */
	public void batchCalcGroupPvi(int levelDepth) {
		sqlUpdate(NAMESPACE + "batchCalcGroupPvi", levelDepth);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.assess.dao.AssessmentManagementUserPviDao#getWithSymbolByUserId(java.lang.String)
	 */
	public AssessmentManagementUserPvi getWithSymbolByUserId(String userId) {
		return (AssessmentManagementUserPvi)sqlSelectForObject(NAMESPACE + "getWithSymbolByUserId", userId);
	}

}
