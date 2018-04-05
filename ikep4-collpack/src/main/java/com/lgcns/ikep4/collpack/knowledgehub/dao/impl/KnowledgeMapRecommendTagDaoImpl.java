/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.knowledgehub.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.collpack.knowledgehub.dao.KnowledgeMapRecommendTagDao;
import com.lgcns.ikep4.collpack.knowledgehub.model.KnowledgeMapRecommendTag;
import com.lgcns.ikep4.collpack.knowledgehub.model.KnowledgeMapRecommendTagPK;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;

/**
 * Knowledge Map KnowledgeRecommendTagDao implementation
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMapRecommendTagDaoImpl.java 16457 2011-08-30 04:20:17Z giljae $
 */
@Repository
public class KnowledgeMapRecommendTagDaoImpl extends GenericDaoSqlmap<KnowledgeMapRecommendTag, KnowledgeMapRecommendTagPK> implements KnowledgeMapRecommendTagDao {
	private static final String NAMESPACE = "collpack.knowledgehub.dao.KnowledgeMapRecommendTag.";

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	@Deprecated
	public KnowledgeMapRecommendTag get(KnowledgeMapRecommendTagPK id) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable)
	 */
	@Deprecated
	public boolean exists(KnowledgeMapRecommendTagPK id) {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public KnowledgeMapRecommendTagPK create(KnowledgeMapRecommendTag object) {
		sqlInsert(NAMESPACE + "create", object);
		return object;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	@Deprecated
	public void update(KnowledgeMapRecommendTag object) {
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable)
	 */
	@Deprecated
	public void remove(KnowledgeMapRecommendTagPK id) {
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgehub.dao.KnowledgeMapRecommendTagDao#listByUserId(java.lang.String, int)
	 */
	public List<KnowledgeMapRecommendTag> listByUserId(String userId, int rowCount) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("rowCount", rowCount + 1); // SQL 에서 "<" 연산을 하므로 1 크게

		return (List<KnowledgeMapRecommendTag>)sqlSelectForList(NAMESPACE + "listByUserId", map);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgehub.dao.KnowledgeMapRecommendTagDao#batchGatherTags()
	 */
	public void batchGatherTags() {
		sqlInsert(NAMESPACE + "batchGatherTags");
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgehub.dao.KnowledgeMapRecommendTagDao#removeAll()
	 */
	public void removeAll() {
		sqlDelete(NAMESPACE + "removeAll");
	}

}
