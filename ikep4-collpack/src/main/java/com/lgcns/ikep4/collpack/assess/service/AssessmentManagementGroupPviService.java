/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.assess.service;

import java.util.List;

import com.lgcns.ikep4.collpack.assess.model.AssessmentManagementGroupPvi;
import com.lgcns.ikep4.collpack.assess.model.AssessmentManagementGroupPviPK;
import com.lgcns.ikep4.collpack.assess.search.AssessmentManagementBlockPageCondition;
import com.lgcns.ikep4.framework.core.service.GenericService;

/**
 * Assessment Management AssessmentManagementGroupPviService interface
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: AssessmentManagementGroupPviService.java 16236 2011-08-18 02:48:22Z giljae $
 */
public interface AssessmentManagementGroupPviService extends GenericService<AssessmentManagementGroupPvi, AssessmentManagementGroupPviPK> {

	/**
	 * portalId별 PVI 점수 조회
	 * @param groupId - 사용자가 속한 최상위 조직
	 * @return int - PVI 점수
	 */
	int getCompanyPvi(String groupId);

	/**
	 * portalId별 최상위 조직 구하기
	 * @param portalId - 포털ID
	 * @return List<AssessmentManagementGroupPvi>
	 */
	List<AssessmentManagementGroupPvi> listRootGroupByPortalId(String portalId);

	/**
	 * 그룹별 건수 (페이징)
	 * @param groupId - 그룹ID
	 * @return int - 하위그룹 개수
	 */
	int countByGroupIdPage(String groupId);

	/**
	 * 그룹별 조회 (페이징)
	 * @param assessmentManagementBlockPageCondition
	 * @return List<AssessmentManagementGroupPvi>
	 */
	List<AssessmentManagementGroupPvi> listByGroupIdPage(AssessmentManagementBlockPageCondition assessmentManagementBlockPageCondition);

	/**
	 * 조직의 계층 구조 구하기
	 * @param groupId - 그룹ID
	 * @return List<AssessmentManagementGroupPvi>
	 */
	List<AssessmentManagementGroupPvi> listGroupHierarchy(String groupId);

}
