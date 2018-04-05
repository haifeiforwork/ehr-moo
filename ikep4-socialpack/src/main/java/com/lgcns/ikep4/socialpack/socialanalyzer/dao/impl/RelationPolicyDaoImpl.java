/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.socialanalyzer.dao.impl;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.socialpack.socialanalyzer.dao.RelationPolicyDao;
import com.lgcns.ikep4.socialpack.socialanalyzer.model.RelationPolicy;


/**
 * DAO 구현 클래스
 * 
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: RelationPolicyDaoImpl.java 16246 2011-08-18 04:48:28Z giljae $
 */
@Repository
public class RelationPolicyDaoImpl extends GenericDaoSqlmap<RelationPolicy, String> implements RelationPolicyDao {
	private static final String NAMESPACE = "socialpack.socialanalyzer.dao.relationPolicy.";
	
	public String create(RelationPolicy relationPolicy) {
		sqlInsert(NAMESPACE + "create", relationPolicy);
		return relationPolicy.getPortalId();
	}

	public boolean exists(String portalId) {
		Integer count = (Integer) (Integer) sqlSelectForObject(NAMESPACE + "count", portalId);
		if (count > 0) {
			return true;
		}
		return false;
	}

	public RelationPolicy get(String portalId) {
		return (RelationPolicy) sqlSelectForObject(NAMESPACE + "get", portalId);
	}

	public void remove(String portalId) {
		sqlDelete(NAMESPACE + "remove", portalId);
	}

	public void update(RelationPolicy relationPolicy) {
		sqlUpdate(NAMESPACE + "update", relationPolicy);
	}
	////////////////////////////////////
	
}
