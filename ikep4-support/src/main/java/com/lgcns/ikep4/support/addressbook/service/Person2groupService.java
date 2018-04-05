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
import com.lgcns.ikep4.support.addressbook.model.Person2group;


/**
 * 주소록 등록 사용자와 주소록 그룹간 매핑 정보 Service interface
 * 
 * @author 이형운
 * @version $Id: Person2groupService.java 16258 2011-08-18 05:37:22Z giljae $
 */
@Transactional
public interface Person2groupService extends GenericService<Person2group, String> {

	/**
	 * 주소록 등록 사용자와 주소록 그룹간 매핑 정보 조회
	 * @param person2group 주소록 등록 사용자와 주소록 그룹간 매핑 정보 객체
	 * @return 주소록 등록 사용자와 주소록 그룹간 매핑 정보 객체
	 */
	public Person2group read(Person2group person2group);

	/**
	 * 주소록 등록 사용자와 주소록 그룹간 매핑 정보 존재 유무
	 * @param person2group 주소록 등록 사용자와 주소록 그룹간 매핑 정보 객체
	 * @return 주소록 등록 사용자와 주소록 그룹간 매핑 정보 객체 존재 유무
	 */
	public boolean exists(Person2group person2group);

	/**
	 * 주소록 등록 사용자와 주소록 그룹간 매핑 정보 삭제
	 * @param person2group 주소록 등록 사용자와 주소록 그룹간 매핑 정보 객체
	 */
	public void delete(Person2group person2group);
	
	public void copyTeamAddr(Person2group person2group);
	
}
