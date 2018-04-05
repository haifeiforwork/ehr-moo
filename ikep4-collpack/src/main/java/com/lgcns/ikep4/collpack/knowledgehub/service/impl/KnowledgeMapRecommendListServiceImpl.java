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

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.collpack.knowledgehub.dao.KnowledgeMapRecommendListDao;
import com.lgcns.ikep4.collpack.knowledgehub.model.KnowledgeMapList;
import com.lgcns.ikep4.collpack.knowledgehub.model.KnowledgeMapListPK;
import com.lgcns.ikep4.collpack.knowledgehub.service.KnowledgeMapRecommendListService;
import com.lgcns.ikep4.support.tagging.model.Tag;
import com.lgcns.ikep4.support.tagging.service.TagService;

/**
 * Knowledge Map KnowledgeRecommendListService implementation
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMapRecommendListServiceImpl.java 16487 2011-09-06 01:34:40Z giljae $
 */
@Service
@Transactional
public class KnowledgeMapRecommendListServiceImpl extends GenericServiceImpl<KnowledgeMapList, KnowledgeMapListPK> implements KnowledgeMapRecommendListService {
	
	@Autowired
	private KnowledgeMapRecommendListDao knowledgeMapRecommendListDao;
	
	@Autowired
	private TagService tagService;

	@Autowired
	public KnowledgeMapRecommendListServiceImpl(KnowledgeMapRecommendListDao dao) {
		super(dao);
		this.knowledgeMapRecommendListDao = dao;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgehub.service.KnowledgeMapRecommendListService#listByUserIdPage(int)
	 */
	public List<KnowledgeMapList> listByUserIdPage(Tag tag) {
		return knowledgeMapRecommendListDao.listByUserIdPage(tagService.itemTypes(tag));
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgehub.service.KnowledgeMapRecommendListService#listByUserIdPageSimple(int)
	 */
	public List<KnowledgeMapList> listByUserIdPageSimple(Tag tag) {
		return knowledgeMapRecommendListDao.listByUserIdPageSimple(tagService.itemTypes(tag));
	}

}
