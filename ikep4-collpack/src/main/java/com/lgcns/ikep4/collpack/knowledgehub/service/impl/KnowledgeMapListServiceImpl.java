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

import com.lgcns.ikep4.collpack.knowledgehub.dao.KnowledgeMapListDao;
import com.lgcns.ikep4.collpack.knowledgehub.model.KnowledgeMapList;
import com.lgcns.ikep4.collpack.knowledgehub.model.KnowledgeMapListPK;
import com.lgcns.ikep4.collpack.knowledgehub.search.KnowledgeMapBlockPageCondition;
import com.lgcns.ikep4.collpack.knowledgehub.service.KnowledgeMapListService;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;

/**
 * Knowledge Map KnowledgeListService implementation
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMapListServiceImpl.java 16236 2011-08-18 02:48:22Z giljae $
 */
@Service
@Transactional
public class KnowledgeMapListServiceImpl extends GenericServiceImpl<KnowledgeMapList, KnowledgeMapListPK> implements KnowledgeMapListService {

	private KnowledgeMapListDao knowledgeMapListDao;

	@Autowired
	public KnowledgeMapListServiceImpl(KnowledgeMapListDao dao) {
		super(dao);
		this.knowledgeMapListDao = dao;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgehub.service.KnowledgeMapListService#countByCategoryId(java.lang.String)
	 */
	public int countByCategoryId(String categoryId) {
		return knowledgeMapListDao.countByCategoryId(categoryId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgehub.service.KnowledgeMapListService#listByCategoryIdPage(com.lgcns.ikep4.collpack.knowledgehub.search.KnowledgeMapBlockPageCondition)
	 */
	public List<KnowledgeMapList> listByCategoryIdPage(KnowledgeMapBlockPageCondition pageCondition) {
		return knowledgeMapListDao.listByCategoryIdPage(pageCondition);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgehub.service.KnowledgeMapListService#batchGatherKnowledge()
	 */
	public void batchGatherKnowledge() {
		knowledgeMapListDao.removeAll();
		knowledgeMapListDao.batchGatherKnowledge(IKepConstant.ITEM_TYPE_CODE_BBS);
		knowledgeMapListDao.batchGatherKnowledge(IKepConstant.ITEM_TYPE_CODE_CAFE);
		knowledgeMapListDao.batchGatherKnowledge(IKepConstant.ITEM_TYPE_CODE_VOCABULARY);
		knowledgeMapListDao.batchGatherKnowledge(IKepConstant.ITEM_TYPE_CODE_FORUM);
		knowledgeMapListDao.batchGatherKnowledge(IKepConstant.ITEM_TYPE_CODE_IDEATION);
		knowledgeMapListDao.batchGatherKnowledge(IKepConstant.ITEM_TYPE_CODE_QA);
		knowledgeMapListDao.batchGatherKnowledge(IKepConstant.ITEM_TYPE_CODE_SOCIAL_BLOG);
		knowledgeMapListDao.batchGatherKnowledge(IKepConstant.ITEM_TYPE_CODE_WORK_MANUAL);
		knowledgeMapListDao.batchGatherKnowledge(IKepConstant.ITEM_TYPE_CODE_WORK_SPACE);
	}

}
