/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.addressbook.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.addressbook.model.Person;
import com.lgcns.ikep4.support.addressbook.model.Person2group;
import com.lgcns.ikep4.support.addressbook.model.PersonList;
import com.lgcns.ikep4.support.addressbook.search.PersonSearchCondition;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * 주소록 등록 사용자 정보 Sevice Interface
 * 
 * @author 이형운
 * @version $Id: PersonService.java 16258 2011-08-18 05:37:22Z giljae $
 */
@Transactional
public interface PersonService extends GenericService<Person, String> {

	/**
	 * 모든 주소록 그룹 사용자 조회 (페이지별)
	 * 
	 * @param addrgroupSearch 주소록 그룹 정보 조회용 객체
	 * @return 주소록 그룹별 등록된 Person
	 */
	public SearchResult<Person> list(PersonSearchCondition personSearch);
	
	/**
	 * 모든 주소록 그룹 사용자 조회 수
	 * @param personSearch 주소록 Person 정보 조회용 객체
	 * @return 주소록 그룹별 등록된 Person 수
	 */
	public Integer listCount(PersonSearchCondition personSearch);
	
	/**
	 * 특정 그룹에 속한 그룹내의 모든 주소록 그룹 사용자 조회 
	 * @param personSearch 주소록 Person 정보 조회용 객체
	 * @return 주소록 그룹별 등록된 Person
	 */
	public SearchResult<Person> listAll(PersonSearchCondition personSearch);
	
	/**
	 * 특정 그룹에 속한 그룹내의 모든 주소록 그룹 사용자 조회  (Paging 건수 체크 로직 없이 )
	 * @param personSearch 주소록 Person 정보 조회용 객체
	 * @return 주소록 그룹별 등록된 Person
	 */
	public SearchResult<Person> listAllWithoutCount(PersonSearchCondition personSearch);
	
	
	/**
	 * 특정 그룹에 속한 그룹내의 모든 주소록 그룹 사용자 조회 수 리턴
	 * @param personSearch 주소록 Person 정보 조회용 객체
	 * @return 특정 그룹에 등록된 Person 수
	 */
	public Integer listAllCount(PersonSearchCondition personSearch);
	
	/**
	 * 내 모든 주소록 그룹 사용자 조회 (연락처 조회용)
	 * @param personSearch 주소록 Person 정보 조회용 객체
	 * @return 주소록 등록된 Person 리스트 
	 */
	public SearchResult<Person> listAllPopUp(PersonSearchCondition personSearch);
	
	/**
	 * 내 모든 주소록 그룹 사용자 조회 (연락처 조회용) 수 리턴
	 * @param personSearch  주소록 Person 정보 조회용 객체
	 * @return 주소록 등록된 Person 수
	 */
	public Integer listAllPopUpCount(PersonSearchCondition personSearch);
	
	/**
	 * 주소록 등록 사용자 정보 조회
	 * @param person 주소록 Person 정보 객체
	 * @return 주소록 Person 정보 객체
	 */
	public Person read(Person person);

	/**
	 * 주소록 등록 사용자 정보 존재 유무
	 * @param person 주소록 Person 정보 객체
	 * @return 주소록 Person 정보 객체 존재 유무
	 */
	public boolean exists(Person person);

	/**
	 * 주소록 등록 사용자 정보 삭제
	 * @param person 주소록 Person 정보 객체
	 */
	public void delete(Person person);
	
	/**
	 * 복수의 주소록 등록 사용자 정보 삭제
	 * @param personList
	 */
	public void deleteList(List<String> personList);

	/**
	 * 주소록 사용자 정보를 저장 하면서 Person2Group 까지 함께 생성 하는 서비스
	 * @param person 주소록 Person 정보 객체
	 * @param user 등록하는 사용자 User 정보
	 */
	public void savePerson(Person person, User user);
	
	public void copyMyAddr(Person person, User user);
	
	
	/**
	 * import 받은 주소록 사용자 정보를 저장 하면서 Person2Group 까지 함께 생성 하는 서비스
	 * @param personList 주소록 Person 정보 객체
	 * @param user 등록하는 사용자 User 정보
	 */
	public void saveAddrressbookFile(PersonList personList, User user, String pgroupTp);
	
	/**
	 * public 또는 개인 그룹의 타입별로 Person 데이타를 삭제하면서 Person2Group 까지 함께 삭제 하는 서비스
	 * @param personId 주소록 Person ID
	 * @param personType 개인 그룹의 타입
	 */
	public void removePersonByType(String personId, String personType);
	
	public void copyTeamByType(Person2group person2group);
	
	
	/**
	 * 그룹에서 일괄 제거를 위해 Person 데이타를 삭제하면서 Person2Group 까지 함께 삭제 하는 서비스
	 * @param personIdList 주소록 Person 정보 리스트
	 * @param addrgroupId 일괄 제거할 주소록 그룹 ID
	 */
	public void removePersonFromGroup(List<String> personIdList, String addrgroupId); 
	
	/**
	 *이메일 주소를 이용한 주소록 등록 외부 사용자 정보 조회
	 * @param person
	 * @return
	 */
	public Person selectPersonByMail(PersonSearchCondition personSearch);

}
