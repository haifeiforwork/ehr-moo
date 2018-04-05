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
import com.lgcns.ikep4.socialpack.socialanalyzer.dao.SocialityPolicyDao;
import com.lgcns.ikep4.socialpack.socialanalyzer.model.SocialityPolicy;
import com.lgcns.ikep4.socialpack.socialanalyzer.service.SocialityPolicyService;


/**
 * Service 구현 클래스
 * 
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: SocialityPolicyServiceImpl.java 16246 2011-08-18 04:48:28Z giljae $
 */
@Service 
@Transactional
public class SocialityPolicyServiceImpl extends GenericServiceImpl<SocialityPolicy, String> implements SocialityPolicyService {

	@Autowired
	private SocialityPolicyDao socialityPolicyDao;
	
	public String create(SocialityPolicy socialityPolicy) {
		return socialityPolicyDao.create(socialityPolicy);
	}

	public boolean exists(String portalId) {
		return socialityPolicyDao.exists(portalId);
	}

	public SocialityPolicy read(String portalId) {
		return socialityPolicyDao.get(portalId);
	}

	public void delete(String portalId) {
		socialityPolicyDao.remove(portalId);
	}

	public void update(SocialityPolicy socialityPolicy) {
		socialityPolicyDao.update(socialityPolicy);
	}
	////////////////////////////////////
}
