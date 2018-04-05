/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.forum.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.collpack.forum.dao.FrPolicyDao;
import com.lgcns.ikep4.collpack.forum.model.FrPolicy;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;

/**
 * TODO Javadoc주석작성updateOrderInit
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: FrPolicyDaoImpl.java 16295 2011-08-19 07:49:49Z giljae $
 */
@Repository("frPolicyDao")
public class FrPolicyDaoImpl extends GenericDaoSqlmap<FrPolicy, String> implements FrPolicyDao {
	

	public String create(FrPolicy obj) {
		return (String) sqlInsert("collpack.fourm.dao.FrPolicy.create", obj);
	}

	@Deprecated
	public boolean exists(String id) {
		return false;
	}
	
	
	@Deprecated
	public FrPolicy get(String id) {
		return null;
	}

	public FrPolicy get(String policyType, String portalId) {
		
		FrPolicy frPolicy = new FrPolicy();
		frPolicy.setPolicyType(policyType);
		frPolicy.setPortalId(portalId);
		
		return (FrPolicy) sqlSelectForObject("collpack.fourm.dao.FrPolicy.get", frPolicy);
	}

	

	public List<FrPolicy> list(String policyType) {
		return sqlSelectForList("collpack.fourm.dao.FrPolicy.list", policyType);
	}

	public void update(FrPolicy obj) {
		sqlUpdate("collpack.fourm.dao.FrPolicy.update", obj);
	}
	
	
	public void remove(String id) {
		sqlDelete("collpack.fourm.dao.FrPolicy.remove", id);
	}
	
	
}
