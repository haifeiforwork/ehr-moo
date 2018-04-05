/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.workmanual.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.collpack.workmanual.dao.ReadGroupDao;
import com.lgcns.ikep4.collpack.workmanual.model.ReadGroup;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;


/**
 * DAO 구현 클래스
 * 
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: ReadGroupDaoImpl.java 16295 2011-08-19 07:49:49Z giljae $
 */
@Repository
public class ReadGroupDaoImpl extends GenericDaoSqlmap<ReadGroup, ReadGroup> implements ReadGroupDao {
	private static final String NAMESPACE = "collpack.workmanual.dao.readGroup."; 
	
	public ReadGroup create(ReadGroup readGroup) {
		sqlInsert(NAMESPACE + "create", readGroup);
		return readGroup;
	}

	public boolean exists(ReadGroup readGroup) {
		Integer count = (Integer) sqlSelectForObject(NAMESPACE + "count", readGroup);
		if (count > 0) {
			return true;
		}
		return false;
	}

	@Deprecated
	public ReadGroup get(ReadGroup readGroup) {
		return (ReadGroup) sqlSelectForObject(NAMESPACE + "get", readGroup);
	}

	@Deprecated
	public void remove(ReadGroup readGroup) {
		sqlDelete(NAMESPACE + "remove", readGroup);
	}

	@Deprecated
	public void update(ReadGroup readGroup) {
		sqlUpdate(NAMESPACE + "update", readGroup);
	}
	////////////////////////////////////

	//문서 조회 조직 정보
	public List<ReadGroup> listReadGroup(ReadGroup readGroup) {
		return sqlSelectForList(NAMESPACE + "listReadGroup", readGroup);
	}
	//카테고리 아이디로 삭제
	public void removeByCategoryId(String categoryId) {
		sqlDelete(NAMESPACE + "removeByCategoryId", categoryId);
	}
}
