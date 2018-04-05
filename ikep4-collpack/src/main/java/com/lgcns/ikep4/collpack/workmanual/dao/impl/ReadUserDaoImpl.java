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

import com.lgcns.ikep4.collpack.workmanual.dao.ReadUserDao;
import com.lgcns.ikep4.collpack.workmanual.model.ReadUser;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;


/**
 * DAO 구현 클래스
 * 
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: ReadUserDaoImpl.java 16295 2011-08-19 07:49:49Z giljae $
 */
@Repository
public class ReadUserDaoImpl extends GenericDaoSqlmap<ReadUser, ReadUser> implements ReadUserDao {
	private static final String NAMESPACE = "collpack.workmanual.dao.readUser.";
	
	public ReadUser create(ReadUser readUser) {
		sqlInsert(NAMESPACE + "create", readUser);
		return readUser;
	}

	public boolean exists(ReadUser readUser) {
		Integer count = (Integer) sqlSelectForObject(NAMESPACE + "count", readUser);
		if (count > 0) {
			return true;
		}
		return false;
	}

	@Deprecated
	public ReadUser get(ReadUser readUser) {
		return (ReadUser) sqlSelectForObject(NAMESPACE + "get", readUser);
	}

	@Deprecated
	public void remove(ReadUser readUser) {
		sqlDelete(NAMESPACE + "remove", readUser);
	}

	@Deprecated
	public void update(ReadUser readUser) {
		sqlUpdate(NAMESPACE + "update", readUser);
	}
	////////////////////////////////////
	
	//문서 조회 담당자 정보
	public List<ReadUser> listReadUser(String categoryId) {
		return sqlSelectForList(NAMESPACE + "listReadUser", categoryId);
	}
	//카테고리 아이디로 삭제
	public void removeByCategoryId(String categoryId) {
		sqlDelete(NAMESPACE + "removeByCategoryId", categoryId);
	}
}
