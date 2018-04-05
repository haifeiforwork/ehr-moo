/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.addressbook.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.addressbook.dao.PersonDao;
import com.lgcns.ikep4.support.addressbook.model.Person;
import com.lgcns.ikep4.support.addressbook.search.PersonSearchCondition;


/**
 * 주소록 등록 사용자 정보 Dao Imol
 * 
 * @author 이형운
 * @version $Id: PersonDaoImpl.java 16316 2011-08-22 00:26:53Z giljae $
 */
@Repository("personDao")
public class PersonDaoImpl extends GenericDaoSqlmap<Person, String> implements PersonDao {

	
	/**
	 * ADDRESSBOOK_NAME_SPACE
	 */
	private static final String ADDRESSBOOK_NAME_SPACE = "support.addressbook.dao.Addressbook.";
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.addressbook.model.Person#get(com.lgcns.ikep4.support.addressbook.model.Person)
	 */
	public Person get(Person person) {
		return (Person) sqlSelectForObject(ADDRESSBOOK_NAME_SPACE + "selectPerson", person);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.addressbook.model.Person#exists(com.lgcns.ikep4.support.addressbook.model.Person)
	 */
	public boolean exists(Person person) {
		Integer count = (Integer) sqlSelectForObject(ADDRESSBOOK_NAME_SPACE + "selectCountPerson", person);
		return count > 0;
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(com.lgcns.ikep4.support.addressbook.model.Person)
	 */
	public String create(Person person) {
		return (String) sqlInsert(ADDRESSBOOK_NAME_SPACE + "insertPerson", person);
	}
	
	public String copyMyPerson(Person person) {
		return (String) sqlInsert(ADDRESSBOOK_NAME_SPACE + "copyMyPerson", person);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(com.lgcns.ikep4.support.addressbook.model.Person)
	 */
	public void update(Person person) {
		sqlUpdate(ADDRESSBOOK_NAME_SPACE + "updatePerson", person);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.addressbook.model.Person#remove(com.lgcns.ikep4.support.addressbook.model.Person)
	 */
	public void remove(Person person) {
		sqlDelete(ADDRESSBOOK_NAME_SPACE + "deletePerson", person);
	}
	
	public void removePerson(Person person) {
		sqlUpdate(ADDRESSBOOK_NAME_SPACE + "updatePersonFlg", person);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.addressbook.model.Person#selectAllPerPage(com .lgcns.ikep4.support.addressbook.model.PersonSearch)
	 */
	public List<Person> selectAllPerPage(PersonSearchCondition personSearch) {
		return sqlSelectForList(ADDRESSBOOK_NAME_SPACE + "selectPersonPerPage", personSearch);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.addressbook.model.Person#selectAll(com .lgcns.ikep4.support.addressbook.model.PersonSearch)
	 */
	public List<Person> selectAll(PersonSearchCondition personSearch) {
		return sqlSelectForList(ADDRESSBOOK_NAME_SPACE + "selectPersonAll", personSearch);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.addressbook.model.Person#selectAllPopUp(com .lgcns.ikep4.support.addressbook.model.PersonSearch)
	 */
	public List<Person> selectAllPopUp(PersonSearchCondition personSearch) {
		return sqlSelectForList(ADDRESSBOOK_NAME_SPACE + "selectPersonPopup", personSearch);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.addressbook.model.Person#selectTotalCntPerPage(com .lgcns.ikep4.support.addressbook.model.PersonSearch)
	 */
	public Integer selectTotalCntPerPage(PersonSearchCondition personSearch) {
		return (Integer) sqlSelectForObject(ADDRESSBOOK_NAME_SPACE + "selectTotalCntPersonPerPage", personSearch);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.addressbook.model.Person#selectTotalCountAll(com .lgcns.ikep4.support.addressbook.model.PersonSearch)
	 */
	public Integer selectTotalCountAll(PersonSearchCondition personSearch) {
		return (Integer) sqlSelectForObject(ADDRESSBOOK_NAME_SPACE + "selectTotalCntPersonAll", personSearch);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.addressbook.model.Person#selectTotalCountAllPopUp(com .lgcns.ikep4.support.addressbook.model.PersonSearch)
	 */
	public Integer selectTotalCountAllPopUp(PersonSearchCondition personSearch) {
		return (Integer) sqlSelectForObject(ADDRESSBOOK_NAME_SPACE + "selectTotalCntPersonPopup", personSearch);
	}
	
	public Person selectPersonByMail(PersonSearchCondition personSearch)
	{
		return (Person) sqlSelectForObject(ADDRESSBOOK_NAME_SPACE + "selectPersonByMail", personSearch);
	}
	
	@Deprecated
	public Person get(String id) {
		return null;
	}

	@Deprecated
	public boolean exists(String id) {
		return false;
	}

	@Deprecated
	public void remove(String id) {}



}
