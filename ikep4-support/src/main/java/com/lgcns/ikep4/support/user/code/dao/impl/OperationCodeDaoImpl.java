/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.user.code.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.user.code.dao.OperationCodeDao;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;
import com.lgcns.ikep4.support.user.code.model.OperationCode;


/**
 * OPERATION 코드 관련 Dao 구현체
 * 
 * @author 양새로훈(yang.sae@gmail.com)
 * @version $Id: OperationCodeDaoImpl.java 16258 2011-08-18 05:37:22Z giljae $
 */
@Repository("operationDao")
public class OperationCodeDaoImpl extends GenericDaoSqlmap<OperationCode, String> implements OperationCodeDao {

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.code.dao.OperationCodeDao#selectAll(com.lgcns.ikep4.support.user.code.model.AdminSearchCondition)
	 */
	public List<OperationCode> selectAll(AdminSearchCondition searchCondition) {

		return sqlSelectForList("support.user.code.dao.OperationCode.selectAll", searchCondition);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.code.dao.OperationCodeDao#selectCount(com.lgcns.ikep4.support.user.code.model.AdminSearchCondition)
	 */
	public Integer selectCount(AdminSearchCondition searchCondition) {
		
		return (Integer) sqlSelectForObject("support.user.code.dao.OperationCode.selectCount", searchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public String create(OperationCode operation) {

		return (String) sqlInsert("support.user.code.dao.OperationCode.insert", operation);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable)
	 */
	public boolean exists(String code) {

		String count = (String) sqlSelectForObject("support.user.code.dao.OperationCode.checkId", code);

		return !count.equals("0");
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.code.dao.OperationCodeDao#checkId(java.lang.String)
	 */
	public String checkId(String id) {

		return (String) sqlSelectForObject("support.user.code.dao.OperationCode.checkId", id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(OperationCode operation) {

		sqlUpdate("support.user.code.dao.OperationCode.update", operation);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable)
	 */
	public void remove(String id) {

		sqlDelete("support.user.code.dao.OperationCode.delete", id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public OperationCode get(String id) {

		return (OperationCode) sqlSelectForObject("support.user.code.dao.OperationCode.select", id);
	}
}
