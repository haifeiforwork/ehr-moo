/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.addressbook.dao.impl;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.addressbook.dao.Person2groupDao;
import com.lgcns.ikep4.support.addressbook.model.Person2group;


/**
 * 주소록 등록 사용자와 주소록 그룹간 매핑 정보 VO impl
 * 
 * @author 이형운
 * @version $Id: Person2groupDaoImpl.java 16258 2011-08-18 05:37:22Z giljae $
 */
@Repository("person2groupDao")
public class Person2groupDaoImpl extends GenericDaoSqlmap<Person2group, String> implements Person2groupDao {

	
	/**
	 * ADDRESSBOOK_NAME_SPACE
	 */
	private static final String ADDRESSBOOK_NAME_SPACE = "support.addressbook.dao.Addressbook.";
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.addressbook.dao.Person2groupDao#get(com.lgcns.ikep4.support.addressbook.model.Person2group)
	 */
	public Person2group get(Person2group person2group) {
		return (Person2group) sqlSelectForObject(ADDRESSBOOK_NAME_SPACE + "selectPerson2group", person2group);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.addressbook.dao.Person2groupDao#exists(com.lgcns.ikep4.support.addressbook.model.Person2group)
	 */
	public boolean exists(Person2group person2group) {
		Integer count = (Integer) sqlSelectForObject(ADDRESSBOOK_NAME_SPACE + "selectCountPerson2group", person2group);
		return count > 0;
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(com.lgcns.ikep4.support.addressbook.model.Person2group)
	 */
	public String create(Person2group person2group) {
		return (String) sqlInsert(ADDRESSBOOK_NAME_SPACE + "insertPerson2group", person2group);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(com.lgcns.ikep4.support.addressbook.model.Person2group)
	 */
	public void update(Person2group person2group) {
		sqlUpdate(ADDRESSBOOK_NAME_SPACE + "updatePerson2group", person2group);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.addressbook.dao.Person2groupDao#remove(com.lgcns.ikep4.support.addressbook.model.Person2group)
	 */
	public void remove(Person2group person2group) {
		sqlDelete(ADDRESSBOOK_NAME_SPACE + "deletePerson2group", person2group);
	}
	
	public void copyTeamAddr(Person2group person2group) {
		sqlDelete(ADDRESSBOOK_NAME_SPACE + "copyTeamAddr", person2group);
	}
	

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.lang.Object)
	 */
	@Deprecated
	public Person2group get(String id) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.lang.Object)
	 */
	@Deprecated
	public boolean exists(String id) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.lang.Object)
	 */
	@Deprecated
	public void remove(String id) {

	}

}
