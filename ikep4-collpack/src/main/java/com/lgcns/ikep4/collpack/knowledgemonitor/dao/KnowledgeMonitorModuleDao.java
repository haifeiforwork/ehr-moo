/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.knowledgemonitor.dao;

import java.util.List;

import com.lgcns.ikep4.collpack.knowledgemonitor.model.KnowledgeMonitorModule;
import com.lgcns.ikep4.collpack.knowledgemonitor.model.KnowledgeMonitorModulePK;
import com.lgcns.ikep4.framework.core.dao.GenericDao;

/**
 * Knowledge Monitor KnowledgeMonitorModuleDao interface
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMonitorModuleDao.java 16236 2011-08-18 02:48:22Z giljae $
 */
public interface KnowledgeMonitorModuleDao extends GenericDao<KnowledgeMonitorModule, KnowledgeMonitorModulePK> {

	/**
	 * portalId별 Module 조회
	 * @param portalId
	 * @return List<KnowledgeMonitorModule>
	 */
	List<KnowledgeMonitorModule> listByPortalId(String portalId);

	/**
	 * portalId별 사용가능한 Module 조회
	 * @param portalId
	 * @return List<KnowledgeMonitorModule>
	 */
	List<KnowledgeMonitorModule> listUsableByPortalId(String portalId);

	/**
	 * portalId별  사용불가로 변경
	 * @param knowledgeMonitorModule
	 */
	void updateUnusableByPortalId(KnowledgeMonitorModule knowledgeMonitorModule);

	/**
	 * 모듈별 사용가능으로 변경
	 * @param knowledgeMonitorModule
	 */
	void updateUsableByPortalIdModuleCode(KnowledgeMonitorModule knowledgeMonitorModule);

	/**
	 * 변경된 모듈 내용을 평가관리 모듈에 반영
	 * @param portalId
	 */
	void updateAssessmentManagementModuleCode(String portalId);

	/**
	 * portalId별 CVI 정책 조회
	 * @param portalId
	 * @return List<KnowledgeMonitorModule>
	 */
	List<KnowledgeMonitorModule> listContentPolicyByPortalId(String portalId);

	/**
	 * CVI 정책 수정
	 * @param knowledgeMonitorModule
	 */
	void updateContentPolicyByPortalIdModuleCode(KnowledgeMonitorModule knowledgeMonitorModule);

	/**
	 * portalId별 Comment 정책 조회
	 * @param portalId
	 * @return List<KnowledgeMonitorModule>
	 */
	List<KnowledgeMonitorModule> listCommentPolicyByPortalId(String portalId);

	/**
	 * Comment 정책 수정
	 * @param knowledgeMonitorModule
	 */
	void updateCommentPolicyByPortalIdModuleCode(KnowledgeMonitorModule knowledgeMonitorModule);
	
}
