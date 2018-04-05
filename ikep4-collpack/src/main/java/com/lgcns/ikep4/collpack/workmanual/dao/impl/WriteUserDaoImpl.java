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

import com.lgcns.ikep4.collpack.workmanual.dao.WriteUserDao;
import com.lgcns.ikep4.collpack.workmanual.model.WriteUser;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;


/**
 * DAO 구현 클래스
 * 
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: WriteUserDaoImpl.java 16295 2011-08-19 07:49:49Z giljae $
 */
@Repository
public class WriteUserDaoImpl extends GenericDaoSqlmap<WriteUser, WriteUser> implements WriteUserDao {
	private static final String NAMESPACE = "collpack.workmanual.dao.writeUser."; 
	
	public WriteUser create(WriteUser writeUser) {
		sqlInsert(NAMESPACE + "create", writeUser);
		return writeUser;
	}

	public boolean exists(WriteUser writeUser) {
		Integer count = (Integer) sqlSelectForObject(NAMESPACE + "count", writeUser);
		if (count > 0) {
			return true;
		}
		return false;
	}

	@Deprecated
	public WriteUser get(WriteUser writeUser) {
		return (WriteUser) sqlSelectForObject(NAMESPACE + "get", writeUser);
	}

	@Deprecated
	public void remove(WriteUser writeUser) {
		sqlDelete(NAMESPACE + "remove", writeUser);
	}

	@Deprecated
	public void update(WriteUser writeUser) {
		sqlUpdate(NAMESPACE + "update", writeUser);
	}
	////////////////////////////////////
	
	//문서담당자 정보
	public List<WriteUser> listWriteUser(String categoryId) {
		return sqlSelectForList(NAMESPACE + "listWriteUser", categoryId);
	}
	//문서 담당자 여부
	public String writeUserYn(String writeUserId) {
		return (String) sqlSelectForObject(NAMESPACE + "writeUserYn", writeUserId);
	}
	//카테고리 아이디로 삭제
	public void removeByCategoryId(String categoryId) {
		sqlDelete(NAMESPACE + "removeByCategoryId", categoryId);
	}
}
