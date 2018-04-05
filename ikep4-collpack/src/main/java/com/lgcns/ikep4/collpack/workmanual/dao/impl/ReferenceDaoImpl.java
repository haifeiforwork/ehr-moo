/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.workmanual.dao.impl;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.collpack.workmanual.dao.ReferenceDao;
import com.lgcns.ikep4.collpack.workmanual.model.Reference;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;


/**
 * DAO 구현 클래스
 * 
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: ReferenceDaoImpl.java 16295 2011-08-19 07:49:49Z giljae $
 */
@Repository
public class ReferenceDaoImpl extends GenericDaoSqlmap<Reference, Reference> implements ReferenceDao {
	private static final String NAMESPACE = "collpack.workmanual.dao.reference.";
	
	public Reference create(Reference reference) {
		sqlInsert(NAMESPACE + "create", reference);
		return reference;
	}

	public boolean exists(Reference reference) {
		Integer count = (Integer) sqlSelectForObject(NAMESPACE + "count", reference);
		if (count > 0) {
			return true;
		}
		return false;
	}

	public Reference get(Reference reference) {
		return (Reference) sqlSelectForObject(NAMESPACE + "get", reference);
	}

	public void remove(Reference reference) {
		sqlDelete(NAMESPACE + "remove", reference);
	}

	public void update(Reference reference) {
		sqlUpdate(NAMESPACE + "update", reference);
	}
	////////////////////////////////////

	//금일 카운드 여부
	public Integer countShowToday(Reference reference) {
		return (Integer) sqlSelectForObject(NAMESPACE + "countShowToday", reference);
	}
	//매뉴얼아이디로 모두 삭제
	public void removeByManualId(String manualId) {
		sqlDelete(NAMESPACE + "removeByManualId", manualId);
	}
}
