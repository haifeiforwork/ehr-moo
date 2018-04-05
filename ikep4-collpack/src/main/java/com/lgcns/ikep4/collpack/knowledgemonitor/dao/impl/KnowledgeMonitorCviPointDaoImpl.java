/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.knowledgemonitor.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.collpack.knowledgemonitor.dao.KnowledgeMonitorCviPointDao;
import com.lgcns.ikep4.collpack.knowledgemonitor.model.KnowledgeMonitorCviPoint;
import com.lgcns.ikep4.collpack.knowledgemonitor.model.KnowledgeMonitorCviPointPK;
import com.lgcns.ikep4.collpack.knowledgemonitor.portlet.model.KnowledgeMonitorPortletSearchCondition;
import com.lgcns.ikep4.collpack.knowledgemonitor.search.KnowledgeRankBlockPageCondition;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;

/**
 * Knowledge Monitor KnowledgeMonitorCviPointDao implementation
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMonitorCviPointDaoImpl.java 16295 2011-08-19 07:49:49Z giljae $
 */
@Repository
public class KnowledgeMonitorCviPointDaoImpl extends GenericDaoSqlmap<KnowledgeMonitorCviPoint, KnowledgeMonitorCviPointPK> implements KnowledgeMonitorCviPointDao {
	private static final String NAMESPACE = "collpack.knowledgemonitor.dao.KnowledgeMonitorCviPoint.";

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	@Deprecated
	public KnowledgeMonitorCviPoint get(KnowledgeMonitorCviPointPK id) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable)
	 */
	@Deprecated
	public boolean exists(KnowledgeMonitorCviPointPK id) {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	@Deprecated
	public KnowledgeMonitorCviPointPK create(KnowledgeMonitorCviPoint object) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	@Deprecated
	public void update(KnowledgeMonitorCviPoint object) {
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable)
	 */
	@Deprecated
	public void remove(KnowledgeMonitorCviPointPK id) {
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgemonitor.dao.KnowledgeMonitorCviPointDao#countByPortalIdModuleCode(com.lgcns.ikep4.collpack.knowledgemonitor.search.KnowledgeRankBlockPageCondition)
	 */
	public int countByPortalIdModuleCode(KnowledgeRankBlockPageCondition pageCondition) {
		return (Integer)sqlSelectForObject(NAMESPACE + "countByPortalIdModuleCode", pageCondition);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgemonitor.dao.KnowledgeMonitorCviPointDao#listByPortalIdModuleCodePage(com.lgcns.ikep4.collpack.knowledgemonitor.search.KnowledgeRankBlockPageCondition)
	 */
	public List<KnowledgeMonitorCviPoint> listByPortalIdModuleCodePage(KnowledgeRankBlockPageCondition pageCondition) {
		return sqlSelectForList(NAMESPACE + "listByPortalIdModuleCodePage", pageCondition);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgemonitor.dao.KnowledgeMonitorCviPointDao#batchGatherBBS()
	 */
	public void batchGatherBBS() {
		sqlInsert(NAMESPACE + "batchGatherBBS");
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgemonitor.dao.KnowledgeMonitorCviPointDao#batchGatherWorkSpace()
	 */
	public void batchGatherWorkSpace() {
		sqlInsert(NAMESPACE + "batchGatherWorkSpace");
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgemonitor.dao.KnowledgeMonitorCviPointDao#batchGatherCafe()
	 */
	public void batchGatherCafe() {
		sqlInsert(NAMESPACE + "batchGatherCafe");
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgemonitor.dao.KnowledgeMonitorCviPointDao#batchGatherWorkManual()
	 */
	public void batchGatherWorkManual() {
		sqlInsert(NAMESPACE + "batchGatherWorkManual");
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgemonitor.dao.KnowledgeMonitorCviPointDao#batchGatherSocialBlog()
	 */
	public void batchGatherSocialBlog() {
		sqlInsert(NAMESPACE + "batchGatherSocialBlog");
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgemonitor.dao.KnowledgeMonitorCviPointDao#batchGatherQnA()
	 */
	public void batchGatherQnA() {
		sqlInsert(NAMESPACE + "batchGatherQnA");
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgemonitor.dao.KnowledgeMonitorCviPointDao#batchGatherForum()
	 */
	public void batchGatherForum() {
		sqlInsert(NAMESPACE + "batchGatherForum");
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgemonitor.dao.KnowledgeMonitorCviPointDao#batchGatherIdeation()
	 */
	public void batchGatherIdeation() {
		sqlInsert(NAMESPACE + "batchGatherIdeation");
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgemonitor.dao.KnowledgeMonitorCviPointDao#batchGatherCorVoca()
	 */
	public void batchGatherCorVoca() {
		sqlInsert(NAMESPACE + "batchGatherCorVoca");
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgemonitor.dao.KnowledgeMonitorCviPointDao#listByPortalIdPortelet(com.lgcns.ikep4.collpack.knowledgemonitor.portlet.model.KnowledgeMonitorPortletSearchCondition)
	 */
	public List<KnowledgeMonitorCviPoint> listByPortalIdPortelet(KnowledgeMonitorPortletSearchCondition SearchCondition) {
		return sqlSelectForList(NAMESPACE + "listByPortalIdPortelet", SearchCondition);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgemonitor.dao.KnowledgeMonitorCviPointDao#batchRemove()
	 */
	public void batchRemove() {
		sqlDelete(NAMESPACE + "batchRemove");
	}

}
