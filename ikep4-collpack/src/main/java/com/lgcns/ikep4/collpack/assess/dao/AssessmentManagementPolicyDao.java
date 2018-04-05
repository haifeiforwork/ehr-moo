/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.assess.dao;

import com.lgcns.ikep4.collpack.assess.model.AssessmentManagementPolicy;
import com.lgcns.ikep4.collpack.assess.model.AssessmentManagementPolicyPK;
import com.lgcns.ikep4.framework.core.dao.GenericDao;

/**
 * Assessment Management AssessmentManagementPolicyDao interface
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: AssessmentManagementPolicyDao.java 16236 2011-08-18 02:48:22Z giljae $
 */
public interface AssessmentManagementPolicyDao extends GenericDao<AssessmentManagementPolicy, AssessmentManagementPolicyPK> {

	/**
	 * 평가점수 산정개시일 변경
	 * @param portalId - 포털ID
	 * @param registerId - 작업자ID
	 */
	void updateEvaluationStartDateByPortalId(String portalId, String registerId);
}
