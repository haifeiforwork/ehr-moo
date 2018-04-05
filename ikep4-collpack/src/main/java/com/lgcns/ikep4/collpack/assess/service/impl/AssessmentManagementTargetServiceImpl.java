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

import com.lgcns.ikep4.collpack.assess.dao.AssessmentManagementTargetDao;
import com.lgcns.ikep4.collpack.assess.model.AssessmentManagementTarget;
import com.lgcns.ikep4.collpack.assess.model.AssessmentManagementTargetPK;
import com.lgcns.ikep4.collpack.assess.service.AssessmentManagementTargetService;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;

/**
 * Assessment Management AssessmentManagementTargetService implementation
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: AssessmentManagementTargetServiceImpl.java 16236 2011-08-18 02:48:22Z giljae $
 */
@Service
@Transactional
public class AssessmentManagementTargetServiceImpl extends GenericServiceImpl<AssessmentManagementTarget, AssessmentManagementTargetPK> implements AssessmentManagementTargetService {

	private AssessmentManagementTargetDao assessmentManagementTargetDao;

	@Autowired
	public AssessmentManagementTargetServiceImpl(AssessmentManagementTargetDao dao) {
		super(dao);
		this.assessmentManagementTargetDao = dao;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.assess.service.AssessmentManagementTargetService#listRequiredByPortalId(java.lang.String)
	 */
	public List<AssessmentManagementTarget> listRequiredByPortalId(String portalId) {
		return assessmentManagementTargetDao.listRequiredByPortalId(portalId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.assess.service.AssessmentManagementTargetService#listUnrequiredAvailableByPortalId(java.lang.String)
	 */
	public List<AssessmentManagementTarget> listUnrequiredAvailableByPortalId(String portalId) {
		return assessmentManagementTargetDao.listUnrequiredAvailableByPortalId(portalId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.assess.service.AssessmentManagementTargetService#listUnrequiredUnavailableByPortalId(java.lang.String)
	 */
	public List<AssessmentManagementTarget> listUnrequiredUnavailableByPortalId(String portalId) {
		return assessmentManagementTargetDao.listUnrequiredUnavailableByPortalId(portalId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.assess.service.AssessmentManagementTargetService#saveModules(com.lgcns.ikep4.collpack.assess.model.AssessmentManagementTarget)
	 */
	public void saveModules(AssessmentManagementTarget assessmentManagementTarget) {
		// 관리대상 필수 항목이 아닌 모듈만 모두 사용 불가로 변경한다.
		assessmentManagementTargetDao.updateUnusableByPortalId(assessmentManagementTarget);

		// 요청받은 moduleId((,)로 분리된) 를 사용가능으로 변경한다.
		assessmentManagementTargetDao.updateUsableByPortalIdModuleCode(assessmentManagementTarget);
	}

}
