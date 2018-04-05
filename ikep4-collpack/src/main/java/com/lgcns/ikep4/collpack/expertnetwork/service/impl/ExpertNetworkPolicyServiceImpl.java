/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.expertnetwork.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.collpack.expertnetwork.dao.ExpertNetworkPolicyDao;
import com.lgcns.ikep4.collpack.expertnetwork.model.ExpertNetworkPolicy;
import com.lgcns.ikep4.collpack.expertnetwork.model.ExpertNetworkPolicyPK;
import com.lgcns.ikep4.collpack.expertnetwork.service.ExpertNetworkPolicyService;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;

/**
 * Expert Network ExpertPolicyService implementation
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: ExpertNetworkPolicyServiceImpl.java 16236 2011-08-18 02:48:22Z giljae $
 */
@Service
@Transactional
public class ExpertNetworkPolicyServiceImpl extends GenericServiceImpl<ExpertNetworkPolicy, ExpertNetworkPolicyPK> implements ExpertNetworkPolicyService {

	private ExpertNetworkPolicyDao expertNetworkPolicyDao;

	@Autowired
	public ExpertNetworkPolicyServiceImpl(ExpertNetworkPolicyDao dao) {
		super(dao);
		this.expertNetworkPolicyDao = dao;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.expertnetwork.service.ExpertNetworkPolicyService#getByPortalId(java.lang.String)
	 */
	public ExpertNetworkPolicy getByPortalId(String portalId) {
		return expertNetworkPolicyDao.getByPortalId(portalId);
	}

}
