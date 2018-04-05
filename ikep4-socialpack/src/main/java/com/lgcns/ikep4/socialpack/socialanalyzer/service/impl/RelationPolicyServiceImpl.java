/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.socialanalyzer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.socialpack.socialanalyzer.dao.RelationPolicyDao;
import com.lgcns.ikep4.socialpack.socialanalyzer.model.RelationPolicy;
import com.lgcns.ikep4.socialpack.socialanalyzer.service.RelationPolicyService;


/**
 * Service 구현 클래스
 * 
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: RelationPolicyServiceImpl.java 16246 2011-08-18 04:48:28Z giljae $
 */
@Service 
@Transactional
public class RelationPolicyServiceImpl extends GenericServiceImpl<RelationPolicy, String> implements RelationPolicyService {

	@Autowired
	private RelationPolicyDao relationPolicyDao;
	
	public String create(RelationPolicy relationPolicy) {
		return relationPolicyDao.create(relationPolicy);
	}

	public boolean exists(String portalId) {
		return relationPolicyDao.exists(portalId);
	}

	public RelationPolicy read(String portalId) {
		return relationPolicyDao.get(portalId);
	}

	public void delete(String portalId) {
		relationPolicyDao.remove(portalId);
	}

	public void update(RelationPolicy relationPolicy) {
		relationPolicyDao.update(relationPolicy);
	}
	////////////////////////////////////
}
