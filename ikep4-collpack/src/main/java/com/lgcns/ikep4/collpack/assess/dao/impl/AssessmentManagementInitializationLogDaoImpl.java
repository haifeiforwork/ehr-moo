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

import com.lgcns.ikep4.collpack.assess.dao.AssessmentManagementInitializationLogDao;
import com.lgcns.ikep4.collpack.assess.model.AssessmentManagementInitializationLog;
import com.lgcns.ikep4.collpack.assess.model.AssessmentManagementInitializationLogPK;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;

/**
 * Assessment Management AssessmentManagementInitializationLogDao implementation
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: AssessmentManagementInitializationLogDaoImpl.java 16457 2011-08-30 04:20:17Z giljae $
 */
@Repository
public class AssessmentManagementInitializationLogDaoImpl extends GenericDaoSqlmap<AssessmentManagementInitializationLog, AssessmentManagementInitializationLogPK> implements AssessmentManagementInitializationLogDao {
	private static final String NAMESPACE = "collpack.assess.dao.AssessmentManagementInitializationLog.";

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	@Deprecated
	public AssessmentManagementInitializationLog get(AssessmentManagementInitializationLogPK id) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable)
	 */
	@Deprecated
	public boolean exists(AssessmentManagementInitializationLogPK id) {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public AssessmentManagementInitializationLogPK create(AssessmentManagementInitializationLog object) {
		sqlInsert(NAMESPACE + "create", object);
		return object;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	@Deprecated
	public void update(AssessmentManagementInitializationLog object) {
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable)
	 */
	@Deprecated
	public void remove(AssessmentManagementInitializationLogPK id) {
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.assess.dao.AssessmentManagementInitializationLogDao#listRequiredByPortalId(java.lang.String)
	 */
	public List<AssessmentManagementInitializationLog> listByPortalId(String portalId) {
		return sqlSelectForList(NAMESPACE + "listByPortalId", portalId);
	}

}
