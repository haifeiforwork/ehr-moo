/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.addressbook.dao;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.support.addressbook.model.Person2group;


/**
 * 주소록 등록 사용자와 주소록 그룹간 매핑 정보 VO interface
 * 
 * @author 이형운
 * @version $Id: Person2groupDao.java 16271 2011-08-18 07:06:14Z giljae $
 */
public interface Person2groupDao extends GenericDao<Person2group, String> {

	/**
	 * 주소록 등록 사용자와 주소록 그룹간 매핑 정보 조회
	 * @param person2group 사용자 그룹간 매핑정보 객체
	 * @return 사용자 그룹간 매핑정보 객체
	 */
	public Person2group get(Person2group person2group);

	/**
	 * 주소록 등록 사용자와 주소록 그룹간 매핑 정보 존재 유무
	 * @param person2group 사용자 그룹간 매핑정보 객체
	 * @return 사용자 그룹간 매핑정보 존재 유무
	 */
	public boolean exists(Person2group person2group);

	/**
	 * 주소록 등록 사용자와 주소록 그룹간 매핑 정보 삭제
	 * @param person2group 사용자 그룹간 매핑정보 객체
	 */
	public void remove(Person2group person2group);
	
	public void copyTeamAddr(Person2group person2group);
	
	
}
