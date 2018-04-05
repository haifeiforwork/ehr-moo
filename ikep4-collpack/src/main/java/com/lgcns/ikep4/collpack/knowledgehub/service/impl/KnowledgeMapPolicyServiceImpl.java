/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.knowledgehub.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.collpack.knowledgehub.dao.KnowledgeMapPolicyDao;
import com.lgcns.ikep4.collpack.knowledgehub.model.KnowledgeMapPolicy;
import com.lgcns.ikep4.collpack.knowledgehub.model.KnowledgeMapPolicyPK;
import com.lgcns.ikep4.collpack.knowledgehub.service.KnowledgeMapPolicyService;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;

/**
 * Knowledge Map KnowledgeMapPolicyService implementation
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMapPolicyServiceImpl.java 16236 2011-08-18 02:48:22Z giljae $
 */
@Service
@Transactional
public class KnowledgeMapPolicyServiceImpl extends GenericServiceImpl<KnowledgeMapPolicy, KnowledgeMapPolicyPK> implements KnowledgeMapPolicyService {

	private KnowledgeMapPolicyDao knowledgeMapPolicyDao;

	@Autowired
	public KnowledgeMapPolicyServiceImpl(KnowledgeMapPolicyDao dao) {
		super(dao);
		this.knowledgeMapPolicyDao = dao;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgehub.service.KnowledgeMapPolicyService#getByPortalId(java.lang.String)
	 */
	public KnowledgeMapPolicy getByPortalId(String portalId) {
		return knowledgeMapPolicyDao.getByPortalId(portalId);
	}

}
