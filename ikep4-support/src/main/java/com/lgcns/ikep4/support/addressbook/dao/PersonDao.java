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
import com.lgcns.ikep4.support.addressbook.model.Person;
import com.lgcns.ikep4.support.addressbook.search.PersonSearchCondition;


/**
 * 주소록 등록 사용자 정보 Dao Interface
 * 
 * @author 이형운
 * @version $Id: PersonDao.java 16271 2011-08-18 07:06:14Z giljae $
 */
public interface PersonDao extends GenericDao<Person, String> {

	/**
	 * 모든 주소록 그룹 사용자 조회 (페이지별로)
	 * 
	 * @param addrgroupSearch 주소록 그룹 정보 조회용 객체
	 * @return 주소록 Person 리스트
	 */
	public List<Person> selectAllPerPage(PersonSearchCondition personSearch);
	
	/**
	 * 모든 주소록 그룹 사용자 조회시 총 게시물 수 리턴
	 * @param personSearch 주소록 그룹 정보 조회용 객체
	 * @return 주소록 Person 수
	 */
	public Integer selectTotalCntPerPage(PersonSearchCondition personSearch);
	
	/**
	 * 해당 그룹에 포함되는 모든 주소록 그룹 사용자 조회 (주소록 다운로드용)
	 * @param personSearch 주소록 그룹 정보 조회용 객체
	 * @return 주소록 전체 Person 리스트
	 */
	public List<Person> selectAll(PersonSearchCondition personSearch);
	
	/**
	 * 해당 그룹에 포함되는 모든 주소록 그룹 사용자 조회  수
	 * @param personSearch 주소록 Person 정보 조회용 객체
	 * @return  주소록 전체 Person 수
	 */
	public Integer selectTotalCountAll(PersonSearchCondition personSearch);
	
	
	/**
	 * 내 모든 주소록 그룹 사용자 조회 (연락처 조회용)
	 * @param personSearch 주소록 Person 조회용 정보 객체
	 * @return 주소록 Person 리스트
	 */
	public List<Person> selectAllPopUp(PersonSearchCondition personSearch);
	
	/**
	 * 내 모든 주소록 그룹 사용자 조회 (연락처 조회용) 조회  수
	 * @param personSearch 주소록 Person 정보 조회용 객체
	 * @return 주소록 Person 수
	 */
	public Integer selectTotalCountAllPopUp(PersonSearchCondition personSearch);
	
	/**
	 * 주소록 등록 사용자 정보 조회
	 * @param person 주소록 Person 정보 객체
	 * @return 주소록 Person 정보 객체
	 */
	public Person get(Person person);
	
	/**
	 *  이메일 주소를 이용한 주소록 등록 외부 사용자 정보 조회
	 * @param personSearch
	 * @return
	 */
	public Person selectPersonByMail(PersonSearchCondition personSearch);
	

	/**
	 * 주소록 등록 사용자 정보 존재 유무
	 * @param person 주소록 Person 정보 객체
	 * @return 주소록 Person 정보 존재 유무
	 */
	public boolean exists(Person person);

	/**
	 * 주소록 등록 사용자 정보 삭제
	 * @param person 주소록 Person 정보 객체
	 */
	public void remove(Person person);
	
	public String copyMyPerson(Person person);
	
	public void removePerson(Person person);
}
