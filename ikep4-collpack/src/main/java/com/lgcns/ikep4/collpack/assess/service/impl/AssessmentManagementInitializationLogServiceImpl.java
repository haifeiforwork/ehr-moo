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

import com.lgcns.ikep4.collpack.assess.dao.AssessmentManagementGroupPviDao;
import com.lgcns.ikep4.collpack.assess.dao.AssessmentManagementInitializationLogDao;
import com.lgcns.ikep4.collpack.assess.dao.AssessmentManagementPolicyDao;
import com.lgcns.ikep4.collpack.assess.dao.AssessmentManagementUserPviDao;
import com.lgcns.ikep4.collpack.assess.model.AssessmentManagementInitializationLog;
import com.lgcns.ikep4.collpack.assess.model.AssessmentManagementInitializationLogPK;
import com.lgcns.ikep4.collpack.assess.service.AssessmentManagementInitializationLogService;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.util.idgen.service.IdgenService;

/**
 * Assessment Management AssessmentManagementInitializationLogService implementation
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: AssessmentManagementInitializationLogServiceImpl.java 16236 2011-08-18 02:48:22Z giljae $
 */
@Service
@Transactional
public class AssessmentManagementInitializationLogServiceImpl extends GenericServiceImpl<AssessmentManagementInitializationLog, AssessmentManagementInitializationLogPK> implements AssessmentManagementInitializationLogService {

	private AssessmentManagementInitializationLogDao assessmentManagementInitializationLogDao;

	@Autowired
	public AssessmentManagementInitializationLogServiceImpl(AssessmentManagementInitializationLogDao dao) {
		super(dao);
		this.assessmentManagementInitializationLogDao = dao;
	}

	@Autowired
	private IdgenService idgenService;

	@Autowired
	private AssessmentManagementUserPviDao assessmentManagementUserPviDao;
	@Autowired
	private AssessmentManagementGroupPviDao assessmentManagementGroupPviDao;
	@Autowired
	private AssessmentManagementPolicyDao assessmentManagementPolicyDao;

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#create(java.lang.Object)
	 */
	@Override
	public AssessmentManagementInitializationLogPK create(AssessmentManagementInitializationLog object) {
		object.setHistoryId(idgenService.getNextId());
		return assessmentManagementInitializationLogDao.create(object);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.assess.service.AssessmentManagementInitializationLogService#listByPortalId(java.lang.String)
	 */
	public List<AssessmentManagementInitializationLog> listByPortalId(String portalId) {
		return assessmentManagementInitializationLogDao.listByPortalId(portalId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.assess.service.AssessmentManagementInitializationLogService#scoreInitialization(java.lang.String, java.lang.String)
	 */
	public void scoreInitialization(String portalId, String registerId) {
		// 사용자 평가점수 초기화
		assessmentManagementUserPviDao.initScoreByPortalId(portalId, registerId);
		// 조직별 평가점수 초기화
		assessmentManagementGroupPviDao.initScoreByPortalId(portalId, registerId);
		// 평가점수 산정개시일 변경
		assessmentManagementPolicyDao.updateEvaluationStartDateByPortalId(portalId, registerId);
	}

}
