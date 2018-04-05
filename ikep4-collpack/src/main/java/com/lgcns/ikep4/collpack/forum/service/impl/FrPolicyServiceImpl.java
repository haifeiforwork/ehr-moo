/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.forum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.collpack.forum.dao.FrPolicyDao;
import com.lgcns.ikep4.collpack.forum.model.FrPolicy;
import com.lgcns.ikep4.collpack.forum.service.FrPolicyService;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


/**
 * BoardService 구현 클래스
 * 
 * @author 이주열(jooyoul.lee@lgcns.com)
 * @version $Id: FrPolicyServiceImpl.java 16236 2011-08-18 02:48:22Z giljae $
 */
@Service("frPolicyService")
public class FrPolicyServiceImpl extends GenericServiceImpl<FrPolicy, String> implements FrPolicyService {


	@Autowired
	private FrPolicyDao frPolicyDao;
	
	@Autowired
	private IdgenService idgenService;
	

	public String create(FrPolicy frPolicy) {
		
		String id = idgenService.getNextId();
		
		frPolicy.setPolicyId(id);
		
		frPolicyDao.create(frPolicy);
		
		return id;
	}
	
	public FrPolicy get(String policyType, String portalId) {
		return frPolicyDao.get(policyType, portalId);
	}

	@Override
	public void update(FrPolicy frPolicy) {
		frPolicyDao.update(frPolicy);
	}

	public void remove(String policyId) {
		
		frPolicyDao.remove(policyId);
		
	}
	

}
