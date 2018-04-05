/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.expertnetwork.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.collpack.expertnetwork.dao.ExpertNetworkPopularDao;
import com.lgcns.ikep4.collpack.expertnetwork.model.ExpertNetworkPopular;
import com.lgcns.ikep4.collpack.expertnetwork.model.ExpertNetworkPopularPK;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;

/**
 * Expert Network ExpertPopularDao implementation
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: ExpertNetworkPopularDaoImpl.java 16295 2011-08-19 07:49:49Z giljae $
 */
@Repository
public class ExpertNetworkPopularDaoImpl extends GenericDaoSqlmap<ExpertNetworkPopular, ExpertNetworkPopularPK> implements ExpertNetworkPopularDao {
	private static final String NAMESPACE = "collpack.expertnetwork.dao.ExpertNetworkPopular.";

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	@Deprecated
	public ExpertNetworkPopular get(ExpertNetworkPopularPK id) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable)
	 */
	@Deprecated
	public boolean exists(ExpertNetworkPopularPK id) {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public ExpertNetworkPopularPK create(ExpertNetworkPopular object) {
		sqlInsert(NAMESPACE + "create", object);
		return (ExpertNetworkPopularPK)object;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	@Deprecated
	public void update(ExpertNetworkPopular object) {
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable)
	 */
	@Deprecated
	public void remove(ExpertNetworkPopularPK id) {
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.expertnetwork.dao.ExpertNetworkPopularDao#listByPortalId(java.lang.String)
	 */
	public List<ExpertNetworkPopular> listByPortalId(String portalId) {
		return this.sqlSelectForList(NAMESPACE + "listByPortalId", portalId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.expertnetwork.dao.ExpertNetworkPopularDao#removeAll()
	 */
	public void removeAll() {
		sqlDelete(NAMESPACE + "removeAll");
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.expertnetwork.dao.ExpertNetworkPopularDao#batchGatherPopular(int)
	 */
	public void batchGatherPopular(int gatherCount) {
		sqlInsert(NAMESPACE + "batchGatherPopular", gatherCount);
	}

}
