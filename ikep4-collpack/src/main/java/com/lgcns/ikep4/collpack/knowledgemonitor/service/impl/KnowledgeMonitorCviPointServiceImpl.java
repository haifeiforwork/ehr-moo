/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.knowledgemonitor.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.collpack.knowledgemonitor.dao.KnowledgeMonitorCviPointDao;
import com.lgcns.ikep4.collpack.knowledgemonitor.model.KnowledgeMonitorCviPoint;
import com.lgcns.ikep4.collpack.knowledgemonitor.model.KnowledgeMonitorCviPointPK;
import com.lgcns.ikep4.collpack.knowledgemonitor.portlet.model.KnowledgeMonitorPortletSearchCondition;
import com.lgcns.ikep4.collpack.knowledgemonitor.search.KnowledgeRankBlockPageCondition;
import com.lgcns.ikep4.collpack.knowledgemonitor.service.KnowledgeMonitorCviPointService;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;

/**
 * Knowledge Map KnowledgeMonitorCviPointService implementation
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMonitorCviPointServiceImpl.java 19576 2012-07-02 05:35:19Z malboru80 $
 */
@Service
@Transactional
public class KnowledgeMonitorCviPointServiceImpl extends GenericServiceImpl<KnowledgeMonitorCviPoint, KnowledgeMonitorCviPointPK> implements KnowledgeMonitorCviPointService {

	private KnowledgeMonitorCviPointDao knowledgeMonitorCviPointDao;

	@Autowired
	public KnowledgeMonitorCviPointServiceImpl(KnowledgeMonitorCviPointDao dao) {
		super(dao);
		this.knowledgeMonitorCviPointDao = dao;
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgemonitor.service.KnowledgeMonitorCviPointService#countByPortalIdModuleCode(com.lgcns.ikep4.collpack.knowledgemonitor.search.KnowledgeRankBlockPageCondition)
	 */
	public int countByPortalIdModuleCode(KnowledgeRankBlockPageCondition pageCondition) {
		return knowledgeMonitorCviPointDao.countByPortalIdModuleCode(pageCondition);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgemonitor.service.KnowledgeMonitorCviPointService#listByPortalIdModuleCodePage(com.lgcns.ikep4.collpack.knowledgemonitor.search.KnowledgeRankBlockPageCondition)
	 */
	public List<KnowledgeMonitorCviPoint> listByPortalIdModuleCodePage(KnowledgeRankBlockPageCondition pageCondition) {
		return knowledgeMonitorCviPointDao.listByPortalIdModuleCodePage(pageCondition);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgemonitor.service.KnowledgeMonitorCviPointService#batchGatherKnowledge()
	 */
	public void batchGatherKnowledge() {
		knowledgeMonitorCviPointDao.batchRemove();

		knowledgeMonitorCviPointDao.batchGatherBBS();
		knowledgeMonitorCviPointDao.batchGatherCafe();
		knowledgeMonitorCviPointDao.batchGatherCorVoca();
		knowledgeMonitorCviPointDao.batchGatherForum();
		knowledgeMonitorCviPointDao.batchGatherIdeation();
		knowledgeMonitorCviPointDao.batchGatherQnA();
		knowledgeMonitorCviPointDao.batchGatherSocialBlog();
		knowledgeMonitorCviPointDao.batchGatherWorkManual();
		knowledgeMonitorCviPointDao.batchGatherWorkSpace();
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgemonitor.service.KnowledgeMonitorCviPointService#listByPortalIdPortelet(com.lgcns.ikep4.collpack.knowledgemonitor.portlet.model.KnowledgeMonitorPortletSearchCondition)
	 */
	public List<KnowledgeMonitorCviPoint> listByPortalIdPortelet(KnowledgeMonitorPortletSearchCondition SearchCondition) {
		return knowledgeMonitorCviPointDao.listByPortalIdPortelet(SearchCondition);
	}

}
