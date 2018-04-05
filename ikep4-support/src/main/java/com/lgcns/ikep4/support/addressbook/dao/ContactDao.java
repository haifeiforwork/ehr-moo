/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.addressbook.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.support.addressbook.model.Contact;
import com.lgcns.ikep4.support.addressbook.search.ContactSearchCondition;


/**
 * Contact 정보용 Dao Interface (사용 안함)
 * 
 * @author 이형운
 * @version $Id: ContactDao.java 16258 2011-08-18 05:37:22Z giljae $
 */
public interface ContactDao extends GenericDao<Contact, String> {

	/**
	 * Contact 정보 조회
	 * @param contact Contact 정보 객체
	 * @return Contact 정보
	 */
	public Contact get(Contact contact);
	
	/**
	 * Contact 정보 존재 유무
	 * @param contact Contact 정보 객체
	 * @return Contact 정보 존재 유무
	 */
	public boolean exists(Contact contact);

	/**
	 * Contact 정보 삭제
	 * @param contact Contact 정보 객체
	 */
	public void remove(Contact contact);
	
	/**
	 * Contact 전체 정보 조회
	 * 
	 * @param contactSearch Contact 정보 조회용 객체
	 * @return Contact 정보 리스트
	 */
	public List<Contact> selectAll(ContactSearchCondition contactSearch); 
	
}
