/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.expertnetwork.dao.impl;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.collpack.expertnetwork.dao.ExpertNetworkPolicyDao;
import com.lgcns.ikep4.collpack.expertnetwork.model.ExpertNetworkPolicy;
import com.lgcns.ikep4.collpack.expertnetwork.model.ExpertNetworkPolicyPK;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;

/**
 * Expert Network ExpertPolicyDao implementation
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: ExpertNetworkPolicyDaoImpl.java 16295 2011-08-19 07:49:49Z giljae $
 */
@Repository
public class ExpertNetworkPolicyDaoImpl extends GenericDaoSqlmap<ExpertNetworkPolicy, ExpertNetworkPolicyPK> implements ExpertNetworkPolicyDao {
	private static final String NAMESPACE = "collpack.expertnetwork.dao.ExpertNetworkPolicy.";

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	@Deprecated
	public ExpertNetworkPolicy get(ExpertNetworkPolicyPK id) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable)
	 */
	@Deprecated
	public boolean exists(ExpertNetworkPolicyPK id) {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public ExpertNetworkPolicyPK create(ExpertNetworkPolicy object) {
		sqlInsert(NAMESPACE + "create", object);
		return (ExpertNetworkPolicyPK)object;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(ExpertNetworkPolicy object) {
		this.sqlUpdate(NAMESPACE + "update", object);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable)
	 */
	@Deprecated
	public void remove(ExpertNetworkPolicyPK id) {
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.expertnetwork.dao.ExpertNetworkPolicyDao#getByPortalId(java.lang.String)
	 */
	public ExpertNetworkPolicy getByPortalId(String portalId) {
		return (ExpertNetworkPolicy)sqlSelectForObject(NAMESPACE + "getByPortalId", portalId);
	}

}
