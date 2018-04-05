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

import com.lgcns.ikep4.collpack.knowledgehub.dao.KnowledgeMapListDao;
import com.lgcns.ikep4.collpack.knowledgehub.model.KnowledgeMapList;
import com.lgcns.ikep4.collpack.knowledgehub.model.KnowledgeMapListPK;
import com.lgcns.ikep4.collpack.knowledgehub.search.KnowledgeMapBlockPageCondition;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;

/**
 * Knowledge Map KnowledgeListDao implementation
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMapListDaoImpl.java 16457 2011-08-30 04:20:17Z giljae $
 */
@Repository
public class KnowledgeMapListDaoImpl extends GenericDaoSqlmap<KnowledgeMapList, KnowledgeMapListPK> implements KnowledgeMapListDao {
	private static final String NAMESPACE = "collpack.knowledgehub.dao.KnowledgeMapList.";

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	@Deprecated
	public KnowledgeMapList get(KnowledgeMapListPK id) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable)
	 */
	@Deprecated
	public boolean exists(KnowledgeMapListPK id) {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	@Deprecated
	public KnowledgeMapListPK create(KnowledgeMapList object) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	@Deprecated
	public void update(KnowledgeMapList object) {
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable)
	 */
	@Deprecated
	public void remove(KnowledgeMapListPK id) {
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgehub.dao.KnowledgeMapListDao#countByCategoryId(java.lang.String)
	 */
	public int countByCategoryId(String categoryId) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countByCategoryId", categoryId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgehub.dao.KnowledgeMapListDao#listByCategoryIdPage(com.lgcns.ikep4.collpack.knowledgehub.search.KnowledgeMapBlockPageCondition)
	 */
	public List<KnowledgeMapList> listByCategoryIdPage(KnowledgeMapBlockPageCondition pageCondition) {
		return (List<KnowledgeMapList>)sqlSelectForList(NAMESPACE + "listByCategoryIdPage", pageCondition);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgehub.dao.KnowledgeMapListDao#removeAll()
	 */
	public void removeAll() {
		sqlDelete(NAMESPACE + "removeAll");
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgehub.dao.KnowledgeMapListDao#batchGatherKnowledge(java.lang.String)
	 */
	public void batchGatherKnowledge(String itemType) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("itemType", itemType);
		sqlInsert(NAMESPACE + "batchGatherKnowledge", map);
	}

}
