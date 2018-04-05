/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.assess.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.collpack.assess.dao.AssessmentManagementTargetDao;
import com.lgcns.ikep4.collpack.assess.model.AssessmentManagementTarget;
import com.lgcns.ikep4.collpack.assess.model.AssessmentManagementTargetPK;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;

/**
 * Assessment Management AssessmentManagementTargetDao implementation
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: AssessmentManagementTargetDaoImpl.java 16457 2011-08-30 04:20:17Z giljae $
 */
@Repository
public class AssessmentManagementTargetDaoImpl extends GenericDaoSqlmap<AssessmentManagementTarget, AssessmentManagementTargetPK> implements AssessmentManagementTargetDao {
	private static final String NAMESPACE = "collpack.assess.dao.AssessmentManagementTarget.";

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	@Deprecated
	public AssessmentManagementTarget get(AssessmentManagementTargetPK id) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable)
	 */
	@Deprecated
	public boolean exists(AssessmentManagementTargetPK id) {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public AssessmentManagementTargetPK create(AssessmentManagementTarget object) {
		sqlInsert(NAMESPACE + "create", object);
		return object;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	@Deprecated
	public void update(AssessmentManagementTarget object) {
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable)
	 */
	@Deprecated
	public void remove(AssessmentManagementTargetPK id) {
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.assess.dao.AssessmentManagementTargetDao#listRequiredByPortalId(java.lang.String)
	 */
	public List<AssessmentManagementTarget> listRequiredByPortalId(String portalId) {
		return sqlSelectForList(NAMESPACE + "listRequiredByPortalId", portalId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.assess.dao.AssessmentManagementTargetDao#listUnrequiredAvailableByPortalId(java.lang.String)
	 */
	public List<AssessmentManagementTarget> listUnrequiredAvailableByPortalId(String portalId) {
		return sqlSelectForList(NAMESPACE + "listUnrequiredAvailableByPortalId", portalId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.assess.dao.AssessmentManagementTargetDao#listUnrequiredUnavailableByPortalId(java.lang.String)
	 */
	public List<AssessmentManagementTarget> listUnrequiredUnavailableByPortalId(String portalId) {
		return sqlSelectForList(NAMESPACE + "listUnrequiredUnavailableByPortalId", portalId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.assess.dao.AssessmentManagementTargetDao#updateUnusableByPortalId(com.lgcns.ikep4.collpack.assess.model.AssessmentManagementTarget)
	 */
	public void updateUnusableByPortalId(AssessmentManagementTarget assessmentManagementTarget) {
		sqlUpdate(NAMESPACE + "updateUnusableByPortalId", assessmentManagementTarget);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.assess.dao.AssessmentManagementTargetDao#updateUsableByPortalIdModuleCode(com.lgcns.ikep4.collpack.assess.model.AssessmentManagementTarget)
	 */
	public void updateUsableByPortalIdModuleCode(AssessmentManagementTarget assessmentManagementTarget) {
		sqlUpdate(NAMESPACE + "updateUsableByPortalIdModuleCode", assessmentManagementTarget);
	}

}
