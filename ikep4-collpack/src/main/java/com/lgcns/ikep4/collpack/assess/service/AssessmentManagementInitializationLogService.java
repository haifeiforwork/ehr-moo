/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.assess.service;

import java.util.List;

import com.lgcns.ikep4.collpack.assess.model.AssessmentManagementInitializationLog;
import com.lgcns.ikep4.collpack.assess.model.AssessmentManagementInitializationLogPK;
import com.lgcns.ikep4.framework.core.service.GenericService;

/**
 * Assessment Management AssessmentManagementInitializationLogService interface
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: AssessmentManagementInitializationLogService.java 16236 2011-08-18 02:48:22Z giljae $
 */
public interface AssessmentManagementInitializationLogService extends GenericService<AssessmentManagementInitializationLog, AssessmentManagementInitializationLogPK> {

	/**
	 * portalId별 초기화이력 조회
	 * @param portalId - 포털ID
	 * @return List<AssessmentManagementInitializationLog>
	 */
	List<AssessmentManagementInitializationLog> listByPortalId(String portalId);

	/**
	 * 평가점수 초기화
	 * @param portalId - 포털ID
	 * @param registerId - 작업자ID
	 */
	void scoreInitialization(String portalId, String registerId);
}
