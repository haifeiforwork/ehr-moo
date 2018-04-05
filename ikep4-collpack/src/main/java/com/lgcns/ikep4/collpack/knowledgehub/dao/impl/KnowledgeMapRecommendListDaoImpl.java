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

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.collpack.knowledgehub.dao.KnowledgeMapRecommendListDao;
import com.lgcns.ikep4.collpack.knowledgehub.model.KnowledgeMapList;
import com.lgcns.ikep4.collpack.knowledgehub.model.KnowledgeMapListPK;
import com.lgcns.ikep4.support.tagging.model.Tag;


/**
 * Knowledge Map KnowledgeRecommendListDao implementation
 * 
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMapRecommendListDaoImpl.java 15142 2011-06-17
 *          04:19:35Z jins02 $
 */
@Repository
public class KnowledgeMapRecommendListDaoImpl extends GenericDaoSqlmap<KnowledgeMapList, KnowledgeMapListPK> implements
		KnowledgeMapRecommendListDao {
	private static final String NAMESPACE = "collpack.knowledgehub.dao.KnowledgeMapRecommendList.";

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	@Deprecated
	public KnowledgeMapList get(KnowledgeMapListPK id) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable
	 * )
	 */
	@Deprecated
	public boolean exists(KnowledgeMapListPK id) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	@Deprecated
	public KnowledgeMapListPK create(KnowledgeMapList object) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	@Deprecated
	public void update(KnowledgeMapList object) {}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable
	 * )
	 */
	@Deprecated
	public void remove(KnowledgeMapListPK id) {}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgehub.dao.KnowledgeMapRecommendListDao#
	 * listByUserIdPage(int)
	 */
	public List<KnowledgeMapList> listByUserIdPage(Tag tag) {
		return (List<KnowledgeMapList>) sqlSelectForList(NAMESPACE + "listByUserIdPage", tag);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgehub.dao.KnowledgeMapRecommendListDao#
	 * listByUserIdPageSimple(int)
	 */
	public List<KnowledgeMapList> listByUserIdPageSimple(Tag tag) {
		return (List<KnowledgeMapList>) sqlSelectForList(NAMESPACE + "listByUserIdPageSimple", tag);
	}

}
