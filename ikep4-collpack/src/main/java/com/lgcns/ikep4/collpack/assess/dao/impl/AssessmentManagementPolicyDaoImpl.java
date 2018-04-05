/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.assess.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.collpack.assess.dao.AssessmentManagementPolicyDao;
import com.lgcns.ikep4.collpack.assess.model.AssessmentManagementPolicy;
import com.lgcns.ikep4.collpack.assess.model.AssessmentManagementPolicyPK;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;

/**
 * Assessment Management AssessmentManagementPolicyDao implementation
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: AssessmentManagementPolicyDaoImpl.java 16457 2011-08-30 04:20:17Z giljae $
 */
@Repository
public class AssessmentManagementPolicyDaoImpl extends GenericDaoSqlmap<AssessmentManagementPolicy, AssessmentManagementPolicyPK> implements AssessmentManagementPolicyDao {
	private static final String NAMESPACE = "collpack.assess.dao.AssessmentManagementPolicy.";

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public AssessmentManagementPolicy get(AssessmentManagementPolicyPK id) {
		return (AssessmentManagementPolicy)sqlSelectForObject(NAMESPACE + "get", id);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable)
	 */
	@Deprecated
	public boolean exists(AssessmentManagementPolicyPK id) {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public AssessmentManagementPolicyPK create(AssessmentManagementPolicy object) {
		sqlInsert(NAMESPACE + "create", object);
		return object;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(AssessmentManagementPolicy object) {
		sqlUpdate(NAMESPACE + "update", object);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable)
	 */
	@Deprecated
	public void remove(AssessmentManagementPolicyPK id) {
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.assess.dao.AssessmentManagementPolicyDao#updateEvaluationStartDateByPortalId(java.lang.String, java.lang.String)
	 */
	public void updateEvaluationStartDateByPortalId(String portalId, String registerId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("portalId", portalId);
		map.put("registerId", registerId);

		sqlUpdate(NAMESPACE + "updateEvaluationStartDateByPortalId", map);
	}

}
