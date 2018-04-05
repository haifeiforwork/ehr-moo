/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.assess.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.collpack.assess.dao.AssessmentManagementUserPviDao;
import com.lgcns.ikep4.collpack.assess.model.AssessmentManagementUserPvi;
import com.lgcns.ikep4.collpack.assess.model.AssessmentManagementUserPviPK;
import com.lgcns.ikep4.collpack.assess.search.AssessmentManagementBlockPageCondition;
import com.lgcns.ikep4.collpack.assess.service.AssessmentManagementUserPviService;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;

/**
 * Assessment Management AssessmentManagementUserPviService implementation
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: AssessmentManagementUserPviServiceImpl.java 19576 2012-07-02 05:35:19Z malboru80 $
 */
@Service
@Transactional
public class AssessmentManagementUserPviServiceImpl extends GenericServiceImpl<AssessmentManagementUserPvi, AssessmentManagementUserPviPK> implements AssessmentManagementUserPviService {

	private AssessmentManagementUserPviDao assessmentManagementUserPviDao;
	
	@Autowired
	public AssessmentManagementUserPviServiceImpl(AssessmentManagementUserPviDao dao) {
		super(dao);
		this.assessmentManagementUserPviDao = dao;
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.assess.service.AssessmentManagementUserPviService#countByPortalIdPage(com.lgcns.ikep4.collpack.assess.search.AssessmentManagementBlockPageCondition)
	 */
	public int countByPortalIdPage(AssessmentManagementBlockPageCondition assessmentManagementBlockPageCondition) {
		return assessmentManagementUserPviDao.countByPortalIdPage(assessmentManagementBlockPageCondition);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.assess.service.AssessmentManagementUserPviService#listByPortalIdPage(com.lgcns.ikep4.collpack.assess.search.AssessmentManagementBlockPageCondition)
	 */
	public List<AssessmentManagementUserPvi> listByPortalIdPage(
			AssessmentManagementBlockPageCondition assessmentManagementBlockPageCondition) {
		return assessmentManagementUserPviDao.listByPortalIdPage(assessmentManagementBlockPageCondition);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.assess.service.AssessmentManagementUserPviService#listPowerUsers(java.lang.String, int)
	 */
	public List<AssessmentManagementUserPvi> listPowerUsers(String portalId, int listCount) {
		AssessmentManagementBlockPageCondition assessmentManagementBlockPageCondition = new AssessmentManagementBlockPageCondition();
		assessmentManagementBlockPageCondition.setPortalId(portalId);
		assessmentManagementBlockPageCondition.setCountOfPage(listCount);
		assessmentManagementBlockPageCondition.setTotalCount(listCount);
		assessmentManagementBlockPageCondition.calculate();

		return assessmentManagementUserPviDao.listByPortalIdPage(assessmentManagementBlockPageCondition);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.assess.service.AssessmentManagementUserPviService#getByUserId(java.lang.String)
	 */
	public AssessmentManagementUserPvi getByUserId(String userId) {
		return assessmentManagementUserPviDao.getByUserId(userId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.assess.service.AssessmentManagementUserPviService#countByGroupIdPage(com.lgcns.ikep4.collpack.assess.search.AssessmentManagementBlockPageCondition)
	 */
	public int countByGroupIdPage(AssessmentManagementBlockPageCondition assessmentManagementBlockPageCondition) {
		return assessmentManagementUserPviDao.countByGroupIdPage(assessmentManagementBlockPageCondition);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.assess.service.AssessmentManagementUserPviService#listByGroupIdPage(com.lgcns.ikep4.collpack.assess.search.AssessmentManagementBlockPageCondition)
	 */
	public List<AssessmentManagementUserPvi> listByGroupIdPage(
			AssessmentManagementBlockPageCondition assessmentManagementBlockPageCondition) {
		return assessmentManagementUserPviDao.listByGroupIdPage(assessmentManagementBlockPageCondition);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.assess.service.AssessmentManagementUserPviService#getMaxPviByPortalId(java.lang.String)
	 */
	public int getMaxPviByPortalId(String portalId) {
		return assessmentManagementUserPviDao.getMaxPviByPortalId(portalId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.assess.service.AssessmentManagementUserPviService#truncateUserActivities()
	 */
	public void batchGatherAccessment() {
		// 중간집계 임시테이블 삭제
		assessmentManagementUserPviDao.truncateUserActivities();

		// 자료 수집
		assessmentManagementUserPviDao.batchGatherRegistScore();
		assessmentManagementUserPviDao.batchGatherAnswerScore();
		assessmentManagementUserPviDao.batchGatherLinereplyScore();
		assessmentManagementUserPviDao.batchGatherRecommendScore();
		assessmentManagementUserPviDao.batchGatherHitScore();
		assessmentManagementUserPviDao.batchGatherSearchScore();
		assessmentManagementUserPviDao.batchGatherFollowScore();
		assessmentManagementUserPviDao.batchGatherFollowingScore();
		assessmentManagementUserPviDao.batchGatherCrossFollowingScore();
		assessmentManagementUserPviDao.batchGatherMblogGroupScore();
		assessmentManagementUserPviDao.batchGatherGuestbookItemScore();
		assessmentManagementUserPviDao.batchGatherTweetScore();
		assessmentManagementUserPviDao.batchGatherRetweetScore();
		assessmentManagementUserPviDao.batchGatherProfileVisitScore();

		// 개인별 평가자료 집계
		assessmentManagementUserPviDao.batchRemoveUserPviPoint();
		assessmentManagementUserPviDao.batchCalcUserPvi();

		// 조직별 평가자료 집계
		assessmentManagementUserPviDao.batchRemoveGroupPviPoint();
		assessmentManagementUserPviDao.batchAppendLeafGroupPvi();
		assessmentManagementUserPviDao.batchAppendNoLeafGroupPvi();

		// 조직 최대차수-1 부터 순차적으로 집계
		int maxLevel = assessmentManagementUserPviDao.batchGetMaxDepth();
		int levelDepth = maxLevel - 1;

		while (levelDepth > 0) {
			assessmentManagementUserPviDao.batchCalcGroupPvi(levelDepth);
			levelDepth--;
		}
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.assess.service.AssessmentManagementUserPviService#getWithSymbolByUserId(java.lang.String)
	 */
	public AssessmentManagementUserPvi getWithSymbolByUserId(String userId) {
		return assessmentManagementUserPviDao.getWithSymbolByUserId(userId);
	}

}
