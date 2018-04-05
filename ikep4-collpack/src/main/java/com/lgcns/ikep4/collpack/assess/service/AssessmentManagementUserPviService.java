/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.assess.service;

import java.util.List;

import com.lgcns.ikep4.collpack.assess.model.AssessmentManagementUserPvi;
import com.lgcns.ikep4.collpack.assess.model.AssessmentManagementUserPviPK;
import com.lgcns.ikep4.collpack.assess.search.AssessmentManagementBlockPageCondition;
import com.lgcns.ikep4.framework.core.service.GenericService;

/**
 * Assessment Management AssessmentManagementUserPviService interface
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: AssessmentManagementUserPviService.java 16236 2011-08-18 02:48:22Z giljae $
 */
public interface AssessmentManagementUserPviService extends GenericService<AssessmentManagementUserPvi, AssessmentManagementUserPviPK> {

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
	 * portalId별 상위 리스트 조회
	 * @param portalId - 포털ID
	 * @param listCount - 결과리스트 개수
	 * @return List<AssessmentManagementUserPvi>
	 */
	List<AssessmentManagementUserPvi> listPowerUsers(String portalId, int listCount);

	/**
	 * userId별 조회
	 * @param userId
	 * @return AssessmentManagementUserPvi
	 */
	AssessmentManagementUserPvi getByUserId(String userId);

	/**
	 * groupId별 건수 (페이징)
	 * @param assessmentManagementBlockPageCondition
	 * @return int
	 */
	int countByGroupIdPage(AssessmentManagementBlockPageCondition assessmentManagementBlockPageCondition);

	/**
	 * groupId별 조회 (페이징)
	 * @param assessmentManagementBlockPageCondition
	 * @return List<AssessmentManagementUserPvi>
	 */
	List<AssessmentManagementUserPvi> listByGroupIdPage(AssessmentManagementBlockPageCondition assessmentManagementBlockPageCondition);

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
	 * 일별 평가자료 개인별/조직별 집계<br/>
	 */
	void batchGatherAccessment();

}
