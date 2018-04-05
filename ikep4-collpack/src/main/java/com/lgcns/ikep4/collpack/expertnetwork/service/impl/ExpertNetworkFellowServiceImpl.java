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

import com.lgcns.ikep4.collpack.expertnetwork.dao.ExpertNetworkFellowDao;
import com.lgcns.ikep4.collpack.expertnetwork.model.ExpertNetworkFellow;
import com.lgcns.ikep4.collpack.expertnetwork.model.ExpertNetworkFellowPK;
import com.lgcns.ikep4.collpack.expertnetwork.search.ExpertNetworkPageCondition;
import com.lgcns.ikep4.collpack.expertnetwork.service.ExpertNetworkFellowService;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;

/**
 * Expert Network ExpertFellowService implementation
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: ExpertNetworkFellowServiceImpl.java 16236 2011-08-18 02:48:22Z giljae $
 */
@Service
@Transactional
public class ExpertNetworkFellowServiceImpl extends GenericServiceImpl<ExpertNetworkFellow, ExpertNetworkFellowPK> implements ExpertNetworkFellowService {

	private ExpertNetworkFellowDao expertNetworkFellowDao;

	@Autowired
	public ExpertNetworkFellowServiceImpl(ExpertNetworkFellowDao dao) {
		super(dao);
		this.expertNetworkFellowDao = dao;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.expertnetwork.service.ExpertNetworkFellowService#countByUserId(java.lang.String)
	 */
	public int countByUserId(String userId) {
		return expertNetworkFellowDao.countByUserId(userId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.expertnetwork.service.ExpertNetworkFellowService#listByUserIdPage(com.lgcns.ikep4.collpack.expertnetwork.search.ExpertNetworkPageCondition)
	 */
	public List<ExpertNetworkFellow> listByUserIdPage(ExpertNetworkPageCondition pageCondition) {
		return expertNetworkFellowDao.listByUserIdPage(pageCondition);
	}

}
