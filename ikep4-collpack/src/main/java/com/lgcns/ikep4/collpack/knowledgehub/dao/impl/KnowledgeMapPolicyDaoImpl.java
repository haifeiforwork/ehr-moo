/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.knowledgehub.dao.impl;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.collpack.knowledgehub.dao.KnowledgeMapPolicyDao;
import com.lgcns.ikep4.collpack.knowledgehub.model.KnowledgeMapPolicy;
import com.lgcns.ikep4.collpack.knowledgehub.model.KnowledgeMapPolicyPK;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;

/**
 * Knowledge Map KnowledgeMapPolicyDao implementation
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMapPolicyDaoImpl.java 16457 2011-08-30 04:20:17Z giljae $
 */
@Repository
public class KnowledgeMapPolicyDaoImpl extends GenericDaoSqlmap<KnowledgeMapPolicy, KnowledgeMapPolicyPK> implements KnowledgeMapPolicyDao {
	private static final String NAMESPACE = "collpack.knowledgehub.dao.KnowledgeMapPolicy.";

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	@Deprecated
	public KnowledgeMapPolicy get(KnowledgeMapPolicyPK id) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable)
	 */
	@Deprecated
	public boolean exists(KnowledgeMapPolicyPK id) {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public KnowledgeMapPolicyPK create(KnowledgeMapPolicy object) {
		sqlInsert(NAMESPACE + "create", object);
		return object;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(KnowledgeMapPolicy object) {
		sqlUpdate(NAMESPACE + "update", object);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable)
	 */
	@Deprecated
	public void remove(KnowledgeMapPolicyPK id) {
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgehub.dao.KnowledgeMapPolicyDao#getByPortalId(java.lang.String)
	 */
	public KnowledgeMapPolicy getByPortalId(String portalId) {
		return (KnowledgeMapPolicy)sqlSelectForObject(NAMESPACE + "getByPortalId", portalId);
	}

}
