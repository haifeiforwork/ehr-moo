/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.knowledgemonitor.service;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.collpack.knowledgemonitor.model.KnowledgeMonitorModule;
import com.lgcns.ikep4.collpack.knowledgemonitor.model.KnowledgeMonitorModulePK;
import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * Knowledge Map KnowledgeMonitorModuleService interface
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMonitorModuleService.java 16236 2011-08-18 02:48:22Z giljae $
 */
public interface KnowledgeMonitorModuleService extends GenericService<KnowledgeMonitorModule, KnowledgeMonitorModulePK> {

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
	 * 사용자에의해 변경된 내용을 일괄저장
	 * @param knowledgeMonitorModule
	 */
	void saveModules(KnowledgeMonitorModule knowledgeMonitorModule);

	/**
	 * portalId별 CVI 정책 조회
	 * @param portalId
	 * @return List<KnowledgeMonitorModule>
	 */
	List<KnowledgeMonitorModule> listContentPolicyByPortalId(String portalId);

	/**
	 * CVI 정책 저장
	 * @param policyMap Map<String, Integer> 정책
	 * @param user 등록자 정보 Object
	 */
	void saveContentCriteria(Map<String, String> policyMap, User user);

	/**
	 * portalId별 Comment 정책 조회
	 * @param portalId
	 * @return List<KnowledgeMonitorModule>
	 */
	List<KnowledgeMonitorModule> listCommentPolicyByPortalId(String portalId);

	/**
	 * Comment 정책 저장
	 * @param policyMap Map<String, Integer> 정책
	 * @param user 등록자 정보 Object
	 */
	void saveCommentCriteria(Map<String, String> policyMap, User user);

}
