/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.user.code.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.user.code.dao.LocaleCodeDao;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;
import com.lgcns.ikep4.support.user.code.model.LocaleCode;


/**
 * 로케일 코드 관련 Dao 구현체
 * 
 * @author 양새로훈(yang.sae@gmail.com)
 * @version $Id: LocaleCodeDaoImpl.java 17313 2012-02-08 00:17:29Z yruyo $
 */
@Repository("localeDao")
public class LocaleCodeDaoImpl extends GenericDaoSqlmap<LocaleCode, String> implements LocaleCodeDao {

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.code.dao.LocaleCodeDao#selectAll(com.lgcns.ikep4.support.user.code.model.AdminSearchCondition)
	 */
	public List<LocaleCode> selectAll(AdminSearchCondition searchCondition) {

		return sqlSelectForList("support.user.code.dao.LocaleCode.selectAll", searchCondition);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.code.dao.LocaleCodeDao#selectCount(com.lgcns.ikep4.support.user.code.model.AdminSearchCondition)
	 */
	public Integer selectCount(AdminSearchCondition searchCondition) {
		
		return (Integer) sqlSelectForObject("support.user.code.dao.LocaleCode.selectCount", searchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public String create(LocaleCode localeCode) {

		return (String) sqlInsert("support.user.code.dao.LocaleCode.insert", localeCode);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable)
	 */
	public boolean exists(String code) {

		String count = (String) sqlSelectForObject("support.user.code.dao.LocaleCode.checkCode", code);

		return !count.equals("0");
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.code.dao.LocaleCodeDao#checkCode(java.lang.String)
	 */
	public String checkCode(String code) {

		return (String) sqlSelectForObject("support.user.code.dao.LocaleCode.checkCode", code);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(LocaleCode localeCode) {

		sqlUpdate("support.user.code.dao.LocaleCode.update", localeCode);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable)
	 */
	public void remove(String code) {

		sqlDelete("support.user.code.dao.LocaleCode.delete", code);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.code.dao.LocaleCodeDao#getMaxSortOrder()
	 */
	public String getMaxSortOrder() {

		return (String) sqlSelectForObject("support.user.code.dao.LocaleCode.getMaxSortOrder");
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.code.dao.LocaleCodeDao#goUp(java.util.Map)
	 */
	public LocaleCode goUp(Map<String,String> map) {

		return (LocaleCode) sqlSelectForObject("support.user.code.dao.LocaleCode.selectGoUp", map);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.code.dao.LocaleCodeDao#goDown(java.util.Map)
	 */
	public LocaleCode goDown(Map<String,String> map) {

		return (LocaleCode) sqlSelectForObject("support.user.code.dao.LocaleCode.selectGoDown", map);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.code.dao.LocaleCodeDao#updateSortOrder(com.lgcns.ikep4.support.user.code.model.LocaleCode)
	 */
	public void updateSortOrder(LocaleCode localeCode) {

		sqlUpdate("support.user.code.dao.LocaleCode.updateSortOrder", localeCode);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public LocaleCode get(String id) {

		return (LocaleCode) sqlSelectForObject("support.user.code.dao.LocaleCode.select", id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.code.dao.LocaleCodeDao#getAll(java.lang.String)
	 */
	public List<LocaleCode> getAll(String localeCode) {
		
		return sqlSelectForList("support.user.code.dao.LocaleCode.getAll", localeCode);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public LocaleCode localeInfo(Map<String, String> locale) {
		return (LocaleCode) sqlSelectForObject("support.user.code.dao.LocaleCode.localeInfo", locale);
	}
}
