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
import com.lgcns.ikep4.support.addressbook.dao.ContactDao;
import com.lgcns.ikep4.support.addressbook.model.Contact;
import com.lgcns.ikep4.support.addressbook.search.ContactSearchCondition;


/**
 * Contact 정보용 Dao Interface (사용 안함)
 * 
 * @author 이형운
 * @version $Id: ContactDaoImpl.java 16258 2011-08-18 05:37:22Z giljae $
 */
@Repository("contactDao")
public class ContactDaoImpl extends GenericDaoSqlmap<Contact, String> implements ContactDao {

	
	/**
	 * ADDRESSBOOK_NAME_SPACE
	 */
	private static final String ADDRESSBOOK_NAME_SPACE = "support.addressbook.dao.Addressbook.";
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.addressbook.dao.ContactDao#get(com.lgcns.ikep4.support.addressbook.model.Contact)
	 */
	public Contact get(Contact contact) {
		return (Contact) sqlSelectForObject(ADDRESSBOOK_NAME_SPACE + "selectContact", contact);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.addressbook.dao.ContactDao#exists(com.lgcns.ikep4.support.addressbook.model.Contact)
	 */
	public boolean exists(Contact contact) {
		Integer count = (Integer) sqlSelectForObject(ADDRESSBOOK_NAME_SPACE + "selectCountContact", contact);
		return count > 0;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#create(com.lgcns.ikep4.support.addressbook.model.Contact)
	 */
	public String create(Contact contact) {
		return (String) sqlInsert(ADDRESSBOOK_NAME_SPACE + "insertContact", contact);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#update(com.lgcns.ikep4.support.addressbook.model.Contact)
	 */
	public void update(Contact contact) {
		sqlUpdate(ADDRESSBOOK_NAME_SPACE + "updateContact", contact);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.addressbook.dao.ContactDao#remove(com.lgcns.ikep4.support.addressbook.model.Contact)
	 */
	public void remove(Contact contact) {
		sqlDelete(ADDRESSBOOK_NAME_SPACE + "deleteContact", contact);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.addressbook.dao.ContactDao#selectAll(com.lgcns.ikep4.support.addressbook.search.ContactSearchCondition)
	 */
	public List<Contact> selectAll(ContactSearchCondition contactSearch) {
		return sqlSelectForList(ADDRESSBOOK_NAME_SPACE + "selectContactAll", contactSearch);
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.lang.Object)
	 */
	@Deprecated
	public Contact get(String id) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.lang.Object)
	 */
	@Deprecated
	public boolean exists(String id) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.lang.Object)
	 */
	@Deprecated
	public void remove(String id) {}



}
