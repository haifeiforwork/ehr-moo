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

import com.lgcns.ikep4.collpack.knowledgehub.dao.KnowledgeMapPopularListDao;
import com.lgcns.ikep4.collpack.knowledgehub.model.KnowledgeMapList;
import com.lgcns.ikep4.collpack.knowledgehub.model.KnowledgeMapListPK;
import com.lgcns.ikep4.collpack.knowledgehub.search.KnowledgeMapPopularPageCondition;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;

/**
 * Knowledge Map KnowledgePopularListDao implementation
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMapPopularListDaoImpl.java 16457 2011-08-30 04:20:17Z giljae $
 */
@Repository
public class KnowledgeMapPopularListDaoImpl extends GenericDaoSqlmap<KnowledgeMapList, KnowledgeMapListPK> implements KnowledgeMapPopularListDao {
	private static final String NAMESPACE = "collpack.knowledgehub.dao.KnowledgeMapPopularList.";

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
	 * @see com.lgcns.ikep4.collpack.knowledgehub.dao.KnowledgeMapPopularListDao#countByPortalId(java.lang.String)
	 */
	public int countByPortalId(String portalId) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countByPortalId", portalId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgehub.dao.KnowledgeMapPopularListDao#listByPortalIdPage(com.lgcns.ikep4.collpack.knowledgehub.search.KnowledgeMapPopularPageCondition)
	 */
	public List<KnowledgeMapList> listByPortalIdPage(KnowledgeMapPopularPageCondition pageCondition) {
		return (List<KnowledgeMapList>)sqlSelectForList(NAMESPACE + "listByPortalIdPage", pageCondition);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgehub.dao.KnowledgeMapPopularListDao#removeAll()
	 */
	public void removeAll() {
		sqlDelete(NAMESPACE + "removeAll");
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgehub.dao.KnowledgeMapPopularListDao#countByPortalIdPage(com.lgcns.ikep4.collpack.knowledgehub.search.KnowledgeMapPopularPageCondition)
	 */
	public int countByPortalIdPage(KnowledgeMapPopularPageCondition pageCondition) {
		return (Integer)sqlSelectForObject(NAMESPACE + "countByPortalIdPage", pageCondition);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgehub.dao.KnowledgeMapPopularListDao#batchGatherBBS(java.lang.String)
	 */
	public void batchGatherBBS(String itemType) {
		sqlInsert(NAMESPACE + "batchGatherBBS", itemType);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgehub.dao.KnowledgeMapPopularListDao#batchGatherWorkSpace(java.lang.String)
	 */
	public void batchGatherWorkSpace(String itemType) {
		sqlInsert(NAMESPACE + "batchGatherWorkSpace", itemType);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgehub.dao.KnowledgeMapPopularListDao#batchGatherCafe(java.lang.String)
	 */
	public void batchGatherCafe(String itemType) {
		sqlInsert(NAMESPACE + "batchGatherCafe", itemType);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgehub.dao.KnowledgeMapPopularListDao#batchGatherWorkManual(java.lang.String)
	 */
	public void batchGatherWorkManual(String itemType) {
		sqlInsert(NAMESPACE + "batchGatherWorkManual", itemType);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgehub.dao.KnowledgeMapPopularListDao#batchGatherSocialBlog(java.lang.String)
	 */
	public void batchGatherSocialBlog(String itemType) {
		sqlInsert(NAMESPACE + "batchGatherSocialBlog", itemType);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgehub.dao.KnowledgeMapPopularListDao#batchGatherQnA(java.lang.String)
	 */
	public void batchGatherQnA(String itemType) {
		sqlInsert(NAMESPACE + "batchGatherQnA", itemType);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgehub.dao.KnowledgeMapPopularListDao#batchGatherForum(java.lang.String)
	 */
	public void batchGatherForum(String itemType) {
		sqlInsert(NAMESPACE + "batchGatherForum", itemType);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgehub.dao.KnowledgeMapPopularListDao#batchGatherIdeation(java.lang.String)
	 */
	public void batchGatherIdeation(String itemType) {
		sqlInsert(NAMESPACE + "batchGatherIdeation", itemType);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgehub.dao.KnowledgeMapPopularListDao#batchGatherCorVoca(java.lang.String)
	 */
	public void batchGatherCorVoca(String itemType) {
		sqlInsert(NAMESPACE + "batchGatherCorVoca", itemType);
	}

}
