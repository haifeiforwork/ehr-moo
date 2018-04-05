/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.knowledgehub.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.collpack.knowledgehub.dao.KnowledgeMapPopularListDao;
import com.lgcns.ikep4.collpack.knowledgehub.model.KnowledgeMapList;
import com.lgcns.ikep4.collpack.knowledgehub.model.KnowledgeMapListPK;
import com.lgcns.ikep4.collpack.knowledgehub.search.KnowledgeMapPopularPageCondition;
import com.lgcns.ikep4.collpack.knowledgehub.service.KnowledgeMapPopularListService;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;

/**
 * Knowledge Map KnowledgeMapPopularListService implementation
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMapPopularListServiceImpl.java 16236 2011-08-18 02:48:22Z giljae $
 */
@Service
@Transactional
public class KnowledgeMapPopularListServiceImpl extends GenericServiceImpl<KnowledgeMapList, KnowledgeMapListPK> implements KnowledgeMapPopularListService {

	private KnowledgeMapPopularListDao knowledgeMapPopularListDao;

	@Autowired
	public KnowledgeMapPopularListServiceImpl(KnowledgeMapPopularListDao dao) {
		super(dao);
		this.knowledgeMapPopularListDao = dao;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgehub.service.KnowledgeMapPopularListService#countByPortalId(java.lang.String)
	 */
	public int countByPortalId(String portalId) {
		return knowledgeMapPopularListDao.countByPortalId(portalId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgehub.service.KnowledgeMapPopularListService#listByPortalIdPage(com.lgcns.ikep4.collpack.knowledgehub.search.KnowledgeMapPopularPageCondition)
	 */
	public List<KnowledgeMapList> listByPortalIdPage(KnowledgeMapPopularPageCondition pageCondition) {
		return knowledgeMapPopularListDao.listByPortalIdPage(pageCondition);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgehub.service.KnowledgeMapPopularListService#countByPortalIdPage(com.lgcns.ikep4.collpack.knowledgehub.search.KnowledgeMapPopularPageCondition)
	 */
	public int countByPortalIdPage(KnowledgeMapPopularPageCondition pageCondition) {
		return knowledgeMapPopularListDao.countByPortalIdPage(pageCondition);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgehub.service.KnowledgeMapPopularListService#batchGatherKnowledge()
	 */
	public void batchGatherKnowledge() {
		knowledgeMapPopularListDao.removeAll();
		knowledgeMapPopularListDao.batchGatherBBS(IKepConstant.ITEM_TYPE_CODE_BBS);
		knowledgeMapPopularListDao.batchGatherCafe(IKepConstant.ITEM_TYPE_CODE_CAFE);
		knowledgeMapPopularListDao.batchGatherCorVoca(IKepConstant.ITEM_TYPE_CODE_VOCABULARY);
		knowledgeMapPopularListDao.batchGatherForum(IKepConstant.ITEM_TYPE_CODE_FORUM);
		knowledgeMapPopularListDao.batchGatherIdeation(IKepConstant.ITEM_TYPE_CODE_IDEATION);
		knowledgeMapPopularListDao.batchGatherQnA(IKepConstant.ITEM_TYPE_CODE_QA);
		knowledgeMapPopularListDao.batchGatherSocialBlog(IKepConstant.ITEM_TYPE_CODE_SOCIAL_BLOG);
		knowledgeMapPopularListDao.batchGatherWorkManual(IKepConstant.ITEM_TYPE_CODE_WORK_MANUAL);
		knowledgeMapPopularListDao.batchGatherWorkSpace(IKepConstant.ITEM_TYPE_CODE_WORK_SPACE);
	}

}
