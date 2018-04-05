/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.knowledgemonitor.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.collpack.knowledgemonitor.dao.KnowledgeMonitorModuleDao;
import com.lgcns.ikep4.collpack.knowledgemonitor.model.KnowledgeMonitorModule;
import com.lgcns.ikep4.collpack.knowledgemonitor.model.KnowledgeMonitorModulePK;
import com.lgcns.ikep4.collpack.knowledgemonitor.service.KnowledgeMonitorModuleService;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.support.base.constant.CommonConstant;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.lang.StringUtil;

/**
 * Knowledge Map KnowledgeMonitorModuleService implementation
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMonitorModuleServiceImpl.java 16236 2011-08-18 02:48:22Z giljae $
 */
@Service
@Transactional
public class KnowledgeMonitorModuleServiceImpl extends GenericServiceImpl<KnowledgeMonitorModule, KnowledgeMonitorModulePK> implements KnowledgeMonitorModuleService {

	private KnowledgeMonitorModuleDao knowledgeMonitorModuleDao;

	@Autowired
	public KnowledgeMonitorModuleServiceImpl(KnowledgeMonitorModuleDao dao) {
		super(dao);
		this.knowledgeMonitorModuleDao = dao;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgemonitor.service.KnowledgeMonitorModuleService#listByPortalId(java.lang.String)
	 */
	public List<KnowledgeMonitorModule> listByPortalId(String portalId) {
		return knowledgeMonitorModuleDao.listByPortalId(portalId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgemonitor.service.KnowledgeMonitorModuleService#listUsableByPortalId(java.lang.String)
	 */
	public List<KnowledgeMonitorModule> listUsableByPortalId(String portalId) {
		return knowledgeMonitorModuleDao.listUsableByPortalId(portalId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgemonitor.service.KnowledgeMonitorModuleService#saveModules(com.lgcns.ikep4.collpack.knowledgemonitor.model.KnowledgeMonitorModule)
	 */
	public void saveModules(KnowledgeMonitorModule knowledgeMonitorModule) {

		// 모두 사용 불가로 변경한다.
		knowledgeMonitorModuleDao.updateUnusableByPortalId(knowledgeMonitorModule);

		// 요청받은 moduleCode((,)로 분리된) 를 사용가능으로 변경한다.
		knowledgeMonitorModuleDao.updateUsableByPortalIdModuleCode(knowledgeMonitorModule);

		// 평가관리모듈 반영
		knowledgeMonitorModuleDao.updateAssessmentManagementModuleCode(knowledgeMonitorModule.getPortalId());
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgemonitor.service.KnowledgeMonitorModuleService#listContentPolicyByPortalId(java.lang.String)
	 */
	public List<KnowledgeMonitorModule> listContentPolicyByPortalId(String portalId) {
		return knowledgeMonitorModuleDao.listContentPolicyByPortalId(portalId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgemonitor.service.KnowledgeMonitorModuleService#saveContentCriteria(java.util.Map, com.lgcns.ikep4.support.user.member.model.User)
	 */
	public void saveContentCriteria(Map<String, String> policyMap, User user) {

		// 모듈 리스트
		String moduleCodeCommaString = policyMap.get("moduleCodeCommaString");
		// 평가기간
		String evaluationTerm = policyMap.get("evaluationTerm");

		// 정책 저장
		KnowledgeMonitorModule knowledgeMonitorModule = new KnowledgeMonitorModule();
		knowledgeMonitorModule.setPortalId(user.getPortalId());
		knowledgeMonitorModule.setUpdaterId(user.getUserId());
		knowledgeMonitorModule.setUpdaterName(user.getUserName());
		knowledgeMonitorModule.setCviEvaluationTerm(Integer.parseInt(evaluationTerm));

		String[] moduleCodes = moduleCodeCommaString.split(CommonConstant.COMMA_SEPARATER);

		for (String moduleCode : moduleCodes) {
			knowledgeMonitorModule.setModuleCode(moduleCode);
			knowledgeMonitorModule.setHitWeight(Integer.parseInt(StringUtil.nvl(policyMap.get(moduleCode + "Hit"), "0")));
			knowledgeMonitorModule.setRecommendWeight(Integer.parseInt(StringUtil.nvl(policyMap.get(moduleCode + "Recommend"), "0")));
			knowledgeMonitorModule.setLinereplyWeight(Integer.parseInt(StringUtil.nvl(policyMap.get(moduleCode + "Linereply"), "0")));
			knowledgeMonitorModule.setFavoriteWeight(Integer.parseInt(StringUtil.nvl(policyMap.get(moduleCode + "Favorite"), "0")));
			knowledgeMonitorModule.setDistributeWeight(Integer.parseInt(StringUtil.nvl(policyMap.get(moduleCode + "Distribute"), "0")));
			knowledgeMonitorModule.setGoodDocCutline(Integer.parseInt(StringUtil.nvl(policyMap.get(moduleCode + "Cutline"), "0")));

			knowledgeMonitorModuleDao.updateContentPolicyByPortalIdModuleCode(knowledgeMonitorModule);
		}
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgemonitor.service.KnowledgeMonitorModuleService#listCommentPolicyByPortalId(java.lang.String)
	 */
	public List<KnowledgeMonitorModule> listCommentPolicyByPortalId(String portalId) {
		return knowledgeMonitorModuleDao.listCommentPolicyByPortalId(portalId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgemonitor.service.KnowledgeMonitorModuleService#saveCommentCriteria(java.util.Map, com.lgcns.ikep4.support.user.member.model.User)
	 */
	public void saveCommentCriteria(Map<String, String> policyMap, User user) {
		// 정책 저장
		KnowledgeMonitorModule knowledgeMonitorModule = new KnowledgeMonitorModule();
		knowledgeMonitorModule.setPortalId(user.getPortalId());
		knowledgeMonitorModule.setUpdaterId(user.getUserId());
		knowledgeMonitorModule.setUpdaterName(user.getUserName());

		Iterator<String> keyIterator = policyMap.keySet().iterator();

		while (keyIterator.hasNext()) {
			String moduleCode = keyIterator.next();
			knowledgeMonitorModule.setModuleCode(moduleCode);
			knowledgeMonitorModule.setGoodLinereplyCutline(Integer.parseInt(policyMap.get(moduleCode)));

			knowledgeMonitorModuleDao.updateCommentPolicyByPortalIdModuleCode(knowledgeMonitorModule);
		}
	}

}
