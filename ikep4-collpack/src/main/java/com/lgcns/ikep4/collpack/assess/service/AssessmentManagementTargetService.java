/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.assess.service;

import java.util.List;

import com.lgcns.ikep4.collpack.assess.model.AssessmentManagementTarget;
import com.lgcns.ikep4.collpack.assess.model.AssessmentManagementTargetPK;
import com.lgcns.ikep4.framework.core.service.GenericService;

/**
 * Assessment Management AssessmentManagementTargetService interface
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: AssessmentManagementTargetService.java 16236 2011-08-18 02:48:22Z giljae $
 */
public interface AssessmentManagementTargetService extends GenericService<AssessmentManagementTarget, AssessmentManagementTargetPK> {

	/**
	 * portalId별 Target 조회 (관리대상 필수인것)
	 * @param portalId - 포털ID
	 * @return List<AssessmentManagementTarget>
	 */
	List<AssessmentManagementTarget> listRequiredByPortalId(String portalId);

	/**
	 * portalId별 Target 조회 (관리대상 필수가 아닌것중 변경이 가능한것)
	 * @param portalId - 포털ID
	 * @return List<AssessmentManagementTarget>
	 */
	List<AssessmentManagementTarget> listUnrequiredAvailableByPortalId(String portalId);

	/**
	 * portalId별 Target 조회 (관리대상 필수가 아닌것중 변경이 불가능한것)
	 * @param portalId - 포털ID
	 * @return List<AssessmentManagementTarget>
	 */
	List<AssessmentManagementTarget> listUnrequiredUnavailableByPortalId(String portalId);

	/**
	 * 사용자에의해 변경된 내용을 일괄저장
	 * @param knowledgeMonitorModule
	 */
	void saveModules(AssessmentManagementTarget assessmentManagementTarget);

}
