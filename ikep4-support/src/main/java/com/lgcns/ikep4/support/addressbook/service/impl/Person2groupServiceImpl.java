/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.addressbook.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.support.addressbook.dao.Person2groupDao;
import com.lgcns.ikep4.support.addressbook.model.Person2group;
import com.lgcns.ikep4.support.addressbook.service.Person2groupService;


/**
 * 주소록 등록 사용자와 주소록 그룹간 매핑 정보 Service Impl
 * 
 * @author 이형운
 * @version $Id: Person2groupServiceImpl.java 16258 2011-08-18 05:37:22Z giljae $
 */
@Service("person2groupService")
public class Person2groupServiceImpl extends GenericServiceImpl<Person2group, String> implements Person2groupService {

	@Autowired
	private Person2groupDao person2groupDao;

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.addressbook.service.Person2groupService#read(com.lgcns.ikep4.support.addressbook.model.Person2group)
	 */
	public Person2group read(Person2group person2group) {
		return person2groupDao.get(person2group);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.addressbook.service.Person2groupService#exists(com.lgcns.ikep4.support.addressbook.model.Person2group)
	 */
	public boolean exists(Person2group person2group) {
		return person2groupDao.exists(person2group);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#create(com.lgcns.ikep4.support.addressbook.model.Person2group)
	 */
	public String create(Person2group person2group) {
		return person2groupDao.create(person2group);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#update(com.lgcns.ikep4.support.addressbook.model.Person2group)
	 */
	public void update(Person2group person2group) {
		person2groupDao.update(person2group);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.addressbook.service.Person2groupService#delete(com.lgcns.ikep4.support.addressbook.model.Person2group)
	 */
	public void delete(Person2group person2group) {
		person2groupDao.remove(person2group);
	}
	
	public void copyTeamAddr(Person2group person2group) {
		person2groupDao.copyTeamAddr(person2group);
	}
	
}
