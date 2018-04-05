/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.socialanalyzer.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.socialpack.socialanalyzer.dao.RelationDao;
import com.lgcns.ikep4.socialpack.socialanalyzer.model.Relation;


/**
 * DAO 구현 클래스
 * 
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: RelationDaoImpl.java 16246 2011-08-18 04:48:28Z giljae $
 */
@Repository
public class RelationDaoImpl extends GenericDaoSqlmap<Relation, String> implements RelationDao {
	private static final String NAMESPACE = "socialpack.socialanalyzer.dao.relation.";

	@Deprecated
	public String create(Relation relation) {
		return null;
	}

	@Deprecated
	public boolean exists(String id) {
		return false;
	}

	@Deprecated
	public Relation get(String id) {
		return null;
	}

	@Deprecated
	public void remove(String id) {
	}

	@Deprecated
	public void update(Relation relation) {
	}
	////////////////////////////////////

	//소셜 네트웍 조회-개인별
	public List<Relation> listPerson(Map<String, String> map) {
		return sqlSelectForList(NAMESPACE + "listPerson", map);
	}
	//소셜 네트웍 조회-그룹별
	public List<Relation> listGroup(Map<String, String> map) {
		return sqlSelectForList(NAMESPACE + "listGroup", map);
	}
	//배치
	public void batchRelation() {
		sqlInsert(NAMESPACE + "batchRelation");
	}
}
