/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.addressbook.service;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.addressbook.model.Contact;
import com.lgcns.ikep4.support.addressbook.search.ContactSearchCondition;


/**
 * Contact 정보용 Service Interface (사용안함)
 * 
 * @author 이형운
 * @version $Id: ContactService.java 16271 2011-08-18 07:06:14Z giljae $
 */
@Transactional
public interface ContactService extends GenericService<Contact, String> {

	/**
	 * Contact 정보 조회
	 * @param contact Contact 정보 객체
	 * @return Contact 정보 객체
	 */
	public Contact read(Contact contact);
	
	/**
	 * Contact 정보 존재 유무
	 * @param contact Contact 정보 객체
	 * @return Contact 정보 객체 존재 유무
	 */
	public boolean exists(Contact contact);

	/**
	 * Contact 정보 삭제
	 * @param contact Contact 정보 객체
	 */
	public void delete(Contact contact);
	
	/**
	 * Contact 전체 정보 조회
	 * 
	 * @param addrgroupSearch Contact 정보 조회용 객체
	 * @return Contact 정보 리스트
	 */
	public SearchResult<Contact> list(ContactSearchCondition contactSearch);
	
}
