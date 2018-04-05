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

import com.lgcns.ikep4.collpack.expertnetwork.dao.ExpertNetworkTaggingDao;
import com.lgcns.ikep4.collpack.expertnetwork.model.ExpertNetworkTagging;
import com.lgcns.ikep4.collpack.expertnetwork.model.ExpertNetworkTaggingPK;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;

/**
 * Expert Network ExpertTaggingDao implementation
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: ExpertNetworkTaggingDaoImpl.java 16295 2011-08-19 07:49:49Z giljae $
 */
@Repository
public class ExpertNetworkTaggingDaoImpl extends GenericDaoSqlmap<ExpertNetworkTagging, ExpertNetworkTaggingPK> implements ExpertNetworkTaggingDao {
	private static final String NAMESPACE = "collpack.expertnetwork.dao.ExpertNetworkTagging.";

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public ExpertNetworkTagging get(ExpertNetworkTaggingPK id) {
		return (ExpertNetworkTagging)sqlSelectForObject(NAMESPACE + "get", id);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable)
	 */
	public boolean exists(ExpertNetworkTaggingPK id) {
		ExpertNetworkTagging tagging = get(id);
		if (null == tagging) {
			return false;
		}
		return tagging.getCategoryId().equals(id.getCategoryId());
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public ExpertNetworkTaggingPK create(ExpertNetworkTagging object) {
		sqlInsert(NAMESPACE + "create", object);
		return (ExpertNetworkTaggingPK)object;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(ExpertNetworkTagging object) {
		sqlUpdate(NAMESPACE + "update", object);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable)
	 */
	public void remove(ExpertNetworkTaggingPK id) {
		sqlDelete(NAMESPACE + "remove", id);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.expertnetwork.dao.ExpertNetworkTaggingDao#listByCategoryId(java.lang.String)
	 */
	public List<ExpertNetworkTagging> listByCategoryId(String categoryId) {
		return sqlSelectForList(NAMESPACE + "listByCategoryId", categoryId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.expertnetwork.dao.ExpertNetworkTaggingDao#removeByCategoryId(java.lang.String)
	 */
	public int removeByCategoryId(String categoryId) {
		return sqlDelete(NAMESPACE + "removeByCategoryId", categoryId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.expertnetwork.dao.ExpertNetworkTaggingDao#removeByCategoryIdHierarchy(java.lang.String)
	 */
	public int removeByCategoryIdHierarchy(String categoryId) {
		return sqlDelete(NAMESPACE + "removeByCategoryIdHierarchy", categoryId);
	}


}
