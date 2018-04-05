/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.assess.dao;

import java.util.List;

import com.lgcns.ikep4.collpack.assess.model.AssessmentManagementGroupPvi;
import com.lgcns.ikep4.collpack.assess.model.AssessmentManagementGroupPviPK;
import com.lgcns.ikep4.collpack.assess.search.AssessmentManagementBlockPageCondition;
import com.lgcns.ikep4.framework.core.dao.GenericDao;

/**
 * Assessment Management AssessmentManagementGroupPviDao interface
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: AssessmentManagementGroupPviDao.java 16236 2011-08-18 02:48:22Z giljae $
 */
public interface AssessmentManagementGroupPviDao extends GenericDao<AssessmentManagementGroupPvi, AssessmentManagementGroupPviPK> {

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

	/**
	 * portal별 점수 초기화
	 * @param portalId
	 * @param registerId
	 */
	void initScoreByPortalId(String portalId, String registerId);
}
