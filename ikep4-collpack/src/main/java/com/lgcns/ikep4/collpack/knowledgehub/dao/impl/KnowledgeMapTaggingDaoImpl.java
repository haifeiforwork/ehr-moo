/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.knowledgehub.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.collpack.knowledgehub.dao.KnowledgeMapTaggingDao;
import com.lgcns.ikep4.collpack.knowledgehub.model.KnowledgeMapTagging;
import com.lgcns.ikep4.collpack.knowledgehub.model.KnowledgeMapTaggingPK;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;

/**
 * Knowledge Map KnowledgeTaggingDao implementation
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMapTaggingDaoImpl.java 16457 2011-08-30 04:20:17Z giljae $
 */
@Repository
public class KnowledgeMapTaggingDaoImpl extends GenericDaoSqlmap<KnowledgeMapTagging, KnowledgeMapTaggingPK> implements KnowledgeMapTaggingDao {
	private static final String NAMESPACE = "collpack.knowledgehub.dao.KnowledgeMapTagging.";

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public KnowledgeMapTagging get(KnowledgeMapTaggingPK id) {
		return (KnowledgeMapTagging)sqlSelectForObject(NAMESPACE + "get", id);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable)
	 */
	public boolean exists(KnowledgeMapTaggingPK id) {
		KnowledgeMapTagging tagging = get(id);
		if (null == tagging) {
			return false;
		}
		return tagging.getCategoryId().equals(id.getCategoryId());
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public KnowledgeMapTaggingPK create(KnowledgeMapTagging object) {
		sqlInsert(NAMESPACE + "create", object);
		return (KnowledgeMapTaggingPK)object;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(KnowledgeMapTagging object) {
		sqlUpdate(NAMESPACE + "update", object);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable)
	 */
	public void remove(KnowledgeMapTaggingPK id) {
		sqlDelete(NAMESPACE + "remove", id);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgehub.dao.KnowledgeMapTaggingDao#listByCategoryId(java.lang.String)
	 */
	public List<KnowledgeMapTagging> listByCategoryId(String categoryId) {
		return sqlSelectForList(NAMESPACE + "listByCategoryId", categoryId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgehub.dao.KnowledgeMapTaggingDao#removeByCategoryId(java.lang.String)
	 */
	public int removeByCategoryId(String categoryId) {
		return sqlDelete(NAMESPACE + "removeByCategoryId", categoryId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgehub.dao.KnowledgeMapTaggingDao#removeByCategoryIdHierarchy(java.lang.String)
	 */
	public int removeByCategoryIdHierarchy(String categoryId) {
		return sqlDelete(NAMESPACE + "removeByCategoryIdHierarchy", categoryId);
	}


}
