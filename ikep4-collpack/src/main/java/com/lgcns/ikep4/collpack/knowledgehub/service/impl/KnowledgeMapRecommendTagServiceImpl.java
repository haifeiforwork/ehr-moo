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

import com.lgcns.ikep4.collpack.knowledgehub.dao.KnowledgeMapRecommendTagDao;
import com.lgcns.ikep4.collpack.knowledgehub.model.KnowledgeMapRecommendTag;
import com.lgcns.ikep4.collpack.knowledgehub.model.KnowledgeMapRecommendTagPK;
import com.lgcns.ikep4.collpack.knowledgehub.service.KnowledgeMapRecommendTagService;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;

/**
 * Knowledge Map KnowledgeRecommendTagService implementation
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMapRecommendTagServiceImpl.java 16236 2011-08-18 02:48:22Z giljae $
 */
@Service
@Transactional
public class KnowledgeMapRecommendTagServiceImpl extends GenericServiceImpl<KnowledgeMapRecommendTag, KnowledgeMapRecommendTagPK> implements KnowledgeMapRecommendTagService {

	private KnowledgeMapRecommendTagDao knowledgeMapRecommendTagDao;

	@Autowired
	public KnowledgeMapRecommendTagServiceImpl(KnowledgeMapRecommendTagDao dao) {
		super(dao);
		this.knowledgeMapRecommendTagDao = dao;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgehub.service.KnowledgeMapRecommendTagService#listByUserId(java.lang.String, int)
	 */
	public List<KnowledgeMapRecommendTag> listByUserId(String userId, int rowCount) {
		return knowledgeMapRecommendTagDao.listByUserId(userId, rowCount);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgehub.service.KnowledgeMapRecommendTagService#batchGatherTags()
	 */
	public void batchGatherTags() {
		//기존 자료 모두 삭제
		knowledgeMapRecommendTagDao.removeAll();

		// 신규자료 수집
		knowledgeMapRecommendTagDao.batchGatherTags();
	}

}
