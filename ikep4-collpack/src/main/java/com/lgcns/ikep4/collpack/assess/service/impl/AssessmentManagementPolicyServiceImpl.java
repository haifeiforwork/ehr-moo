/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.assess.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.collpack.assess.dao.AssessmentManagementPolicyDao;
import com.lgcns.ikep4.collpack.assess.model.AssessmentManagementPolicy;
import com.lgcns.ikep4.collpack.assess.model.AssessmentManagementPolicyPK;
import com.lgcns.ikep4.collpack.assess.service.AssessmentManagementPolicyService;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;

/**
 * Assessment Management AssessmentManagementPolicyService implementation
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: AssessmentManagementPolicyServiceImpl.java 16236 2011-08-18 02:48:22Z giljae $
 */
@Service
@Transactional
public class AssessmentManagementPolicyServiceImpl extends GenericServiceImpl<AssessmentManagementPolicy, AssessmentManagementPolicyPK> implements AssessmentManagementPolicyService {

	private AssessmentManagementPolicyDao assessmentManagementPolicyDao;

	@Autowired
	public AssessmentManagementPolicyServiceImpl(AssessmentManagementPolicyDao dao) {
		super(dao);
		this.assessmentManagementPolicyDao = dao;
	}

	protected void doNotUse() {
		assessmentManagementPolicyDao.toString();
	}
}
