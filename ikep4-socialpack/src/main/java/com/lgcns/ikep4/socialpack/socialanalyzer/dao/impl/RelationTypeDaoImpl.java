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
import com.lgcns.ikep4.socialpack.socialanalyzer.dao.RelationTypeDao;
import com.lgcns.ikep4.socialpack.socialanalyzer.model.RelationType;
import com.lgcns.ikep4.socialpack.socialanalyzer.model.RelationTypePk;


/**
 * DAO 구현 클래스
 * 
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: RelationTypeDaoImpl.java 16246 2011-08-18 04:48:28Z giljae $
 */
@Repository
public class RelationTypeDaoImpl extends GenericDaoSqlmap<RelationType, RelationTypePk> implements RelationTypeDao {
	private static final String NAMESPACE = "socialpack.socialanalyzer.dao.relationType.";
	
	public RelationTypePk create(RelationType relationType) {
		sqlInsert(NAMESPACE + "create", relationType);
		return (RelationTypePk) relationType;
	}

	public boolean exists(RelationTypePk relationTypePk) {
		Integer count = (Integer) sqlSelectForObject(NAMESPACE + "count", relationTypePk);
		if (count > 0) {
			return true;
		}
		return false;
	}

	public RelationType get(RelationTypePk relationTypePk) {
		return (RelationType) sqlSelectForObject(NAMESPACE + "get", relationTypePk);
	}

	public void remove(RelationTypePk relationTypePk) {
		sqlDelete(NAMESPACE + "remove", relationTypePk);
	}

	public void update(RelationType relationType) {
		sqlUpdate(NAMESPACE + "update", relationType);
	}
	////////////////////////////////////
	
}
