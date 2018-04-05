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
import com.lgcns.ikep4.support.user.code.dao.ClassCodeDao;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;
import com.lgcns.ikep4.support.user.code.model.ClassCode;


/**
 * 클래스 코드 관련 Dao 구현체
 * 
 * @author 양새로훈(yang.sae@gmail.com)
 * @version $Id: ClassCodeDaoImpl.java 16258 2011-08-18 05:37:22Z giljae $
 */
@Repository("classDao")
public class ClassCodeDaoImpl extends GenericDaoSqlmap<ClassCode, String> implements ClassCodeDao {

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.code.dao.ClassCodeDao#selectAll(com.lgcns
	 * .ikep4.support.user.code.model.AdminSearchCondition)
	 */
	public List<ClassCode> selectAll(AdminSearchCondition searchCondition) {

		return sqlSelectForList("support.user.code.dao.ClassCode.selectAll", searchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.code.dao.ClassCodeDao#selectCount(com.lgcns
	 * .ikep4.support.user.code.model.AdminSearchCondition)
	 */
	public Integer selectCount(AdminSearchCondition searchCondition) {

		return (Integer) sqlSelectForObject("support.user.code.dao.ClassCode.selectCount", searchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public String create(ClassCode classCode) {

		return (String) sqlInsert("support.user.code.dao.ClassCode.insert", classCode);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable
	 * )
	 */
	public boolean exists(String code) {

		String count = (String) sqlSelectForObject("support.user.code.dao.ClassCode.checkId", code);

		return !count.equals("0");
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(ClassCode classCode) {

		sqlUpdate("support.user.code.dao.ClassCode.update", classCode);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable
	 * )
	 */
	public void remove(String id) {

		sqlDelete("support.user.code.dao.ClassCode.delete", id);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public ClassCode get(String id) {

		return (ClassCode) sqlSelectForObject("support.user.code.dao.ClassCode.select", id);
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable
	 * )
	 */
	public boolean checkName(String className) {

		String count = (String) sqlSelectForObject("support.user.code.dao.ClassCode.checkName", className);

		return !count.equals("0");
	}
}
