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

import com.lgcns.ikep4.collpack.assess.dao.AssessmentManagementPviSymbolDao;
import com.lgcns.ikep4.collpack.assess.model.AssessmentManagementPviSymbol;
import com.lgcns.ikep4.collpack.assess.model.AssessmentManagementPviSymbolPK;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;

/**
 * Assessment Management AssessmentManagementPviSymbolDao implementation
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: AssessmentManagementPviSymbolDaoImpl.java 16457 2011-08-30 04:20:17Z giljae $
 */
@Repository
public class AssessmentManagementPviSymbolDaoImpl extends GenericDaoSqlmap<AssessmentManagementPviSymbol, AssessmentManagementPviSymbolPK> implements AssessmentManagementPviSymbolDao {
	private static final String NAMESPACE = "collpack.assess.dao.AssessmentManagementPviSymbol.";

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	@Deprecated
	public AssessmentManagementPviSymbol get(AssessmentManagementPviSymbolPK id) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable)
	 */
	@Deprecated
	public boolean exists(AssessmentManagementPviSymbolPK id) {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public AssessmentManagementPviSymbolPK create(AssessmentManagementPviSymbol object) {
		sqlInsert(NAMESPACE + "create", object);
		return object;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(AssessmentManagementPviSymbol object) {
		sqlUpdate(NAMESPACE + "update", object);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable)
	 */
	@Deprecated
	public void remove(AssessmentManagementPviSymbolPK id) {
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.assess.dao.AssessmentManagementPviSymbolDao#listByPortalId(java.lang.String)
	 */
	public List<AssessmentManagementPviSymbol> listByPortalId(String portalId) {
		return sqlSelectForList(NAMESPACE + "listByPortalId", portalId);
	}

}
