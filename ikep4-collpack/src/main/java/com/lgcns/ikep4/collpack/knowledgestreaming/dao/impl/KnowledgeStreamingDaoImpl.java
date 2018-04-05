/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.knowledgestreaming.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.collpack.knowledgestreaming.dao.KnowledgeStreamingDao;
import com.lgcns.ikep4.collpack.knowledgestreaming.model.KnowledgeUse;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;

/**
 * Knowledge Monitor KnowledgeStreamingDao implementation
 * 
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeStreamingDaoImpl.java 16295 2011-08-19 07:49:49Z
 *          giljae $
 */
@Repository
public class KnowledgeStreamingDaoImpl extends
		GenericDaoSqlmap<KnowledgeUse, String> implements KnowledgeStreamingDao {

	private static final String NAMESPACE = "collpack.knowledgestreaming.";

	public KnowledgeUse get(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean exists(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	public String create(KnowledgeUse object) {
		return (String) sqlInsert(NAMESPACE + "insert", object);
	}

	public void update(KnowledgeUse object) {
		// TODO Auto-generated method stub

	}

	public void remove(String id) {
		// TODO Auto-generated method stub

	}

	public List<KnowledgeUse> selectWeekly(Map searchMap) {
		return (List<KnowledgeUse>) sqlSelectForList(
				NAMESPACE + "selectWeekly", searchMap);
	}

	public void batchInsert() {
		sqlInsert(NAMESPACE + "batchInsert");
	}

	public void batchDelete() {
		sqlDelete(NAMESPACE + "batchDelete");
	}

}
