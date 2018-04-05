/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.expertnetwork.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.collpack.expertnetwork.dao.ExpertNetworkPopularDao;
import com.lgcns.ikep4.collpack.expertnetwork.model.ExpertNetworkPopular;
import com.lgcns.ikep4.collpack.expertnetwork.model.ExpertNetworkPopularPK;
import com.lgcns.ikep4.collpack.expertnetwork.service.ExpertNetworkPopularService;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;

/**
 * Expert Network ExpertPopularService implementation
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: ExpertNetworkPopularServiceImpl.java 16236 2011-08-18 02:48:22Z giljae $
 */
@Service
@Transactional
public class ExpertNetworkPopularServiceImpl extends GenericServiceImpl<ExpertNetworkPopular, ExpertNetworkPopularPK> implements ExpertNetworkPopularService {

	private ExpertNetworkPopularDao expertNetworkPopularDao;

	@Autowired
	public ExpertNetworkPopularServiceImpl(ExpertNetworkPopularDao dao) {
		super(dao);
		this.expertNetworkPopularDao = dao;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.expertnetwork.service.ExpertNetworkPopularService#listByPortalId(java.lang.String)
	 */
	public List<ExpertNetworkPopular> listByPortalId(String portalId) {
		return expertNetworkPopularDao.listByPortalId(portalId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.expertnetwork.service.ExpertNetworkPopularService#batchGatherPopular(int)
	 */
	public void batchGatherPopular(int gatherCount) {
		// 기존자료 모두 삭제
		expertNetworkPopularDao.removeAll();

		expertNetworkPopularDao.batchGatherPopular(gatherCount);
	}

}
