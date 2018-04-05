/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.ideation.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.collpack.ideation.dao.IdPolicyDao;
import com.lgcns.ikep4.collpack.ideation.model.IdPolicy;

/**
 * TODO Javadoc주석작성updateOrderInit
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: IdPolicyDaoImpl.java 12461 2011-05-20 09:49:00Z loverfairy $
 */
@Repository("idPolicyDao")
public class IdPolicyDaoImpl extends GenericDaoSqlmap<IdPolicy, String> implements IdPolicyDao {
	

	public String create(IdPolicy obj) {
		return (String) sqlInsert("collpack.ideation.dao.IdPolicy.create", obj);
	}

	@Deprecated
	public boolean exists(String id) {
		return false;
	}
	
	
	@Deprecated
	public IdPolicy get(String id) {
		return null;
	}

	public IdPolicy get(String policyType, String portalId) {
		
		IdPolicy idPolicy = new IdPolicy();
		idPolicy.setPolicyType(policyType);
		idPolicy.setPortalId(portalId);
		
		return (IdPolicy) sqlSelectForObject("collpack.ideation.dao.IdPolicy.get", idPolicy);
	}

	

	public List<IdPolicy> list(String policyType) {
		return sqlSelectForList("collpack.ideation.dao.IdPolicy.list", policyType);
	}

	public void update(IdPolicy obj) {
		sqlUpdate("collpack.ideation.dao.IdPolicy.update", obj);
	}
	
	
	public void remove(String id) {
		sqlDelete("collpack.ideation.dao.IdPolicy.remove", id);
	}
	
	
}
