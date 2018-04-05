/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.assess.dao;

import java.util.List;

import com.lgcns.ikep4.collpack.assess.model.AssessmentManagementUserPvi;
import com.lgcns.ikep4.collpack.assess.model.AssessmentManagementUserPviPK;
import com.lgcns.ikep4.collpack.assess.search.AssessmentManagementBlockPageCondition;
import com.lgcns.ikep4.framework.core.dao.GenericDao;

/**
 * Assessment Management AssessmentManagementUserPviDao interface
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: AssessmentManagementUserPviDao.java 16236 2011-08-18 02:48:22Z giljae $
 */
public interface AssessmentManagementUserPviDao extends GenericDao<AssessmentManagementUserPvi, AssessmentManagementUserPviPK> {

	/**
	 * portalId별 건수 (페이징)
	 * @param assessmentManagementBlockPageCondition
	 * @return int
	 */
	int countByPortalIdPage(AssessmentManagementBlockPageCondition assessmentManagementBlockPageCondition);

	/**
	 * portalId별 조회 (페이징)
	 * @param assessmentManagementBlockPageCondition
	 * @return List<AssessmentManagementUserPvi>
	 */
	List<AssessmentManagementUserPvi> listByPortalIdPage(AssessmentManagementBlockPageCondition assessmentManagementBlockPageCondition);

	/**
	 * userId별 조회
	 * @param userId
	 * @return AssessmentManagementUserPvi
	 */
	AssessmentManagementUserPvi getByUserId(String userId);

	/**
	 * groupId별 건수 (페이징)
	 * @param assessmentManagementBlockPageCondition
	 * @return int - 레코드 개수
	 */
	int countByGroupIdPage(AssessmentManagementBlockPageCondition assessmentManagementBlockPageCondition);

	/**
	 * groupId별 조회 (페이징)
	 * @param assessmentManagementBlockPageCondition
	 * @return List<AssessmentManagementUserPvi>
	 */
	List<AssessmentManagementUserPvi> listByGroupIdPage(AssessmentManagementBlockPageCondition assessmentManagementBlockPageCondition);

	/**
	 * portal별 점수 초기화
	 * @param portalId - 포털ID
	 * @param registerId
	 */
	void initScoreByPortalId(String portalId, String registerId);

	/**
	 * portal별 최고 점수 조회
	 * @param portalId - 포털ID
	 * @return int 최고 PVI점수
	 */
	int getMaxPviByPortalId(String portalId);

	/**
	 * userId별 사용자점수 및 Symbol 조회
	 * @param userId
	 * @return AssessmentManagementUserPvi
	 */
	AssessmentManagementUserPvi getWithSymbolByUserId(String userId);

	/**
	 * 배치작업<br/>
	 * 임시집계테이블(IKEP4_AM_USER_ACTIVITIES) Truncate
	 */
	void truncateUserActivities();

	/**
	 * 배치작업<br/>
	 * 자료등록/우수자료 (COUNT_TYPE : 'Regist')
	 */
	void batchGatherRegistScore();

	/**
	 * 배치작업<br/>
	 * 답변수 / 우수답변수 (COUNT_TYPE : 'Answer')
	 */
	void batchGatherAnswerScore();

	/**
	 * 배치작업<br/>
	 * Comment (COUNT_TYPE : 'Linereply')
	 */
	void batchGatherLinereplyScore();

	/**
	 * 배치작업<br/>
	 * 추천수 (COUNT_TYPE : 'Recommend')
	 */
	void batchGatherRecommendScore();

	/**
	 * 배치작업<br/>
	 * 조회수 (COUNT_TYPE : 'Hit')
	 */
	void batchGatherHitScore();

	/**
	 * 배치작업<br/>
	 * 검색수 (내가 검색을 한 수) (COUNT_TYPE : 'Search')
	 */
	void batchGatherSearchScore();

	/**
	 * 배치작업<br/>
	 * Follower수 (나를 Follow 한 사람의 수) (COUNT_TYPE : 'Follow')
	 */
	void batchGatherFollowScore();

	/**
	 * 배치작업<br/>
	 * Following수 (내가 Follow 한 사람의 수) (COUNT_TYPE : 'Following')
	 */
	void batchGatherFollowingScore();

	/**
	 * 배치작업<br/>
	 * Mutual Following수 (COUNT_TYPE : 'CrossFollowing')
	 */
	void batchGatherCrossFollowingScore();

	/**
	 * 배치작업<br/>
	 * 가입 MicroBlogGroup수 (COUNT_TYPE : 'MblogGroup')
	 */
	void batchGatherMblogGroupScore();

	/**
	 * 배치작업<br/>
	 * 방명록 방명록글수 (COUNT_TYPE : 'GuestbookItem')
	 */
	void batchGatherGuestbookItemScore();

	/**
	 * 배치작업<br/>
	 * 트윗수 (COUNT_TYPE : 'Tweet')
	 */
	void batchGatherTweetScore();

	/**
	 * 배치작업<br/>
	 * Retweet수 (COUNT_TYPE : 'Retweet')
	 */
	void batchGatherRetweetScore();

	/**
	 * 배치작업<br/>
	 * Profile 방문수 (COUNT_TYPE : 'ProfileVisit')
	 */
	void batchGatherProfileVisitScore();

	/**
	 * 배치작업<br/>
	 * 개인별 평가정보 삭제(IKEP4_AM_USER_PVI)
	 */
	void batchRemoveUserPviPoint();

	/**
	 * 배치작업<br/>
	 * 개인별 평가정보 계산
	 */
	void batchCalcUserPvi();

	/**
	 * 배치작업<br/>
	 * 조직별 평가정보 삭제(IKEP4_AM_GROUP_PVI)
	 */
	void batchRemoveGroupPviPoint();

	/**
	 * 배치작업<br/>
	 * 사용자별 점수로 최하위 조직만 입력(자식이 없는 조직만)
	 */
	void batchAppendLeafGroupPvi();

	/**
	 * 배치작업<br/>
	 * 조직 입력(자식이 있는 조직만)
	 */
	void batchAppendNoLeafGroupPvi();

	/**
	 * 배치작업<br/>
	 * 조직 최대 차수
	 */
	int batchGetMaxDepth();

	/**
	 * 배치작업<br/>
	 * 조직별 평가정보 계산
	 */
	void batchCalcGroupPvi(int levelDepth);

}
