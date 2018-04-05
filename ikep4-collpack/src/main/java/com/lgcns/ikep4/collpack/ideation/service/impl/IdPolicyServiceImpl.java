/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.ideation.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.collpack.ideation.dao.IdPolicyDao;
import com.lgcns.ikep4.collpack.ideation.model.IdPolicy;
import com.lgcns.ikep4.collpack.ideation.service.IdPolicyService;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


/**
 * BoardService 구현 클래스
 * 
 * @author 이주열(jooyoul.lee@lgcns.com)
 * @version $Id: IdPolicyServiceImpl.java 12460 2011-05-20 09:48:52Z loverfairy $
 */
@Service("idPolicyService")
public class IdPolicyServiceImpl extends GenericServiceImpl<IdPolicy, String> implements IdPolicyService {


	@Autowired
	private IdPolicyDao idPolicyDao;
	
	@Autowired
	private IdgenService idgenService;
	

	public String create(IdPolicy idPolicy) {
		
		String id = idgenService.getNextId();
		
		idPolicy.setPolicyId(id);
		
		idPolicyDao.create(idPolicy);
		
		return id;
	}
	
	public IdPolicy get(String policyType, String portalId) {
		return idPolicyDao.get(policyType, portalId);
	}

	@Override
	public void update(IdPolicy idPolicy) {
		idPolicyDao.update(idPolicy);
	}

	public void remove(String policyId) {
		
		idPolicyDao.remove(policyId);
		
	}
	

}
