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

import com.lgcns.ikep4.collpack.knowledgemonitor.dao.KnowledgeMonitorModuleDao;
import com.lgcns.ikep4.collpack.knowledgemonitor.model.KnowledgeMonitorModule;
import com.lgcns.ikep4.collpack.knowledgemonitor.model.KnowledgeMonitorModulePK;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;

/**
 * Knowledge Monitor KnowledgeMonitorModuleDao implementation
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMonitorModuleDaoImpl.java 16295 2011-08-19 07:49:49Z giljae $
 */
@Repository
public class KnowledgeMonitorModuleDaoImpl extends GenericDaoSqlmap<KnowledgeMonitorModule, KnowledgeMonitorModulePK> implements KnowledgeMonitorModuleDao {
	private static final String NAMESPACE = "collpack.knowledgemonitor.dao.KnowledgeMonitorModule.";

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	@Deprecated
	public KnowledgeMonitorModule get(KnowledgeMonitorModulePK id) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable)
	 */
	@Deprecated
	public boolean exists(KnowledgeMonitorModulePK id) {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public KnowledgeMonitorModulePK create(KnowledgeMonitorModule object) {
		sqlInsert(NAMESPACE + "create", object);
		return object;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	@Deprecated
	public void update(KnowledgeMonitorModule object) {
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable)
	 */
	@Deprecated
	public void remove(KnowledgeMonitorModulePK id) {
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgemonitor.dao.KnowledgeMonitorModuleDao#listByPortalId(java.lang.String)
	 */
	public List<KnowledgeMonitorModule> listByPortalId(String portalId) {
		return sqlSelectForList(NAMESPACE + "listByPortalId", portalId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgemonitor.dao.KnowledgeMonitorModuleDao#updateUnusableByPortalId(com.lgcns.ikep4.collpack.knowledgemonitor.model.KnowledgeMonitorModule)
	 */
	public void updateUnusableByPortalId(KnowledgeMonitorModule knowledgeMonitorModule) {
		sqlUpdate(NAMESPACE + "updateUnusableByPortalId", knowledgeMonitorModule);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgemonitor.dao.KnowledgeMonitorModuleDao#updateUsable(com.lgcns.ikep4.collpack.knowledgemonitor.model.KnowledgeMonitorModule)
	 */
	public void updateUsableByPortalIdModuleCode(KnowledgeMonitorModule knowledgeMonitorModule) {
		sqlUpdate(NAMESPACE + "updateUsableByPortalIdModuleCode", knowledgeMonitorModule);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgemonitor.dao.KnowledgeMonitorModuleDao#listUsableByPortalId(java.lang.String)
	 */
	public List<KnowledgeMonitorModule> listUsableByPortalId(String portalId) {
		return sqlSelectForList(NAMESPACE + "listUsableByPortalId", portalId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgemonitor.dao.KnowledgeMonitorModuleDao#listContentPolicyByPortalId(java.lang.String)
	 */
	public List<KnowledgeMonitorModule> listContentPolicyByPortalId(String portalId) {
		return sqlSelectForList(NAMESPACE + "listContentPolicyByPortalId", portalId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgemonitor.dao.KnowledgeMonitorModuleDao#updateContentPolicyByPortalIdModuleCode(com.lgcns.ikep4.collpack.knowledgemonitor.model.KnowledgeMonitorModule)
	 */
	public void updateContentPolicyByPortalIdModuleCode(KnowledgeMonitorModule knowledgeMonitorModule) {
		sqlUpdate(NAMESPACE + "updateContentPolicyByPortalIdModuleCode", knowledgeMonitorModule);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgemonitor.dao.KnowledgeMonitorModuleDao#listCommentPolicyByPortalId(java.lang.String)
	 */
	public List<KnowledgeMonitorModule> listCommentPolicyByPortalId(String portalId) {
		return sqlSelectForList(NAMESPACE + "listCommentPolicyByPortalId", portalId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgemonitor.dao.KnowledgeMonitorModuleDao#updateCommentPolicyByPortalIdModuleCode(com.lgcns.ikep4.collpack.knowledgemonitor.model.KnowledgeMonitorModule)
	 */
	public void updateCommentPolicyByPortalIdModuleCode(KnowledgeMonitorModule knowledgeMonitorModule) {
		sqlUpdate(NAMESPACE + "updateCommentPolicyByPortalIdModuleCode", knowledgeMonitorModule);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgemonitor.dao.KnowledgeMonitorModuleDao#updateAssessmentManagementModuleCode(java.lang.String)
	 */
	public void updateAssessmentManagementModuleCode(String portalId) {
		sqlUpdate(NAMESPACE + "updateAssessmentManagementModuleCode", portalId);
	}

}
