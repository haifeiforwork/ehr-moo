/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.addressbook.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.addressbook.dao.ContactDao;
import com.lgcns.ikep4.support.addressbook.model.Contact;
import com.lgcns.ikep4.support.addressbook.search.ContactSearchCondition;
import com.lgcns.ikep4.support.addressbook.service.ContactService;


/**
 * Contact 정보용 Service Impl
 * 
 * @author 이형운
 * @version $Id: ContactServiceImpl.java 16258 2011-08-18 05:37:22Z giljae $
 */
@Service("contactService")
public class ContactServiceImpl extends GenericServiceImpl<Contact, String> implements ContactService {

	@Autowired
	private ContactDao contactDao;

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.addressbook.service.ContactService#read(com.lgcns.ikep4.support.addressbook.model.Contact)
	 */
	public Contact read(Contact contact) {
		return contactDao.get(contact);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.addressbook.service.ContactService#exists(com.lgcns.ikep4.support.addressbook.model.Contact)
	 */
	public boolean exists(Contact contact) {
		return contactDao.exists(contact);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#create(com.lgcns.ikep4.support.addressbook.model.Contact)
	 */
	public String create(Contact contact) {
		return contactDao.create(contact);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#update(com.lgcns.ikep4.support.addressbook.model.Contact)
	 */
	public void update(Contact contact) {
		contactDao.update(contact);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.addressbook.service.ContactService#delete(com.lgcns.ikep4.support.addressbook.model.Contact)
	 */
	public void delete(Contact contact) {
		contactDao.remove(contact);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.addressbook.service.ContactService#list(com.lgcns.ikep4.support.addressbook.search.ContactSearchCondition)
	 */
	public SearchResult<Contact> list(ContactSearchCondition contactSearch) {
		
		List<Contact> contactList = contactDao.selectAll(contactSearch);
		contactSearch.terminateSearchCondition(contactList.size());
		SearchResult<Contact> searchResult = new SearchResult<Contact>(contactList, contactSearch);
		
		return searchResult;
	}

}
