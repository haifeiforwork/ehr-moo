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
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.addressbook.base.Constant;
import com.lgcns.ikep4.support.addressbook.dao.PersonDao;
import com.lgcns.ikep4.support.addressbook.model.Person;
import com.lgcns.ikep4.support.addressbook.model.Person2group;
import com.lgcns.ikep4.support.addressbook.model.PersonList;
import com.lgcns.ikep4.support.addressbook.search.PersonSearchCondition;
import com.lgcns.ikep4.support.addressbook.service.Person2groupService;
import com.lgcns.ikep4.support.addressbook.service.PersonService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.StringUtil;


/**
 * 주소록 등록 사용자 정보 Sevice Impl
 * 
 * @author 이형운
 * @version $Id: PersonServiceImpl.java 16316 2011-08-22 00:26:53Z giljae $
 */
@Service("personService")
public class PersonServiceImpl extends GenericServiceImpl<Person, String> implements PersonService {

	/**
	 * 주소록 Person 정보 컨트롤용 Dao
	 */
	@Autowired
	private PersonDao personDao;

	/**
	 * 키값 생성을 위한 정보 컨트롤용 Service
	 */
	@Autowired
	private IdgenService idgenService;

	/**
	 * 주소록 Person 과 주소록 그룹간의 매핑 정보 컨트롤용 Service
	 */
	@Autowired
	private Person2groupService person2groupService;

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.addressbook.service.PersonService#read
	 * (com.lgcns.ikep4.support.addressbook.model.Person)
	 */
	public Person read(Person person) {
		User user = (User) this.getRequestAttribute("ikep.user");
		Person result = personDao.get(person);

		if (!user.getLocaleCode().equals("ko")) {
			result.setPersonName(result.getPersonEnglishName());
			result.setTeamName(result.getTeamEnglishName());
			result.setJobRankName(result.getJobRankEnglishName());
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.addressbook.service.PersonService#exists
	 * (com.lgcns.ikep4.support.addressbook.model.Person)
	 */
	public boolean exists(Person person) {
		return personDao.exists(person);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.GenericService#create(com.lgcns
	 * .ikep4.support.addressbook.model.Person)
	 */
	public String create(Person person) {
		return personDao.create(person);
	}
	
	public String copyMyPerson(Person person) {
		return personDao.copyMyPerson(person);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.GenericService#update(com.lgcns
	 * .ikep4.support.addressbook.model.Person)
	 */
	public void update(Person person) {
		personDao.update(person);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.addressbook.service.PersonService#delete
	 * (com.lgcns.ikep4.support.addressbook.model.Person)
	 */
	public void delete(Person person) {
		personDao.remove(person);
	}
	
	public void deletePerson(Person person) {
		personDao.removePerson(person);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.addressbook.service.PersonService#list
	 * (com.lgcns.ikep4.support.addressbook.model.PersonSearch)
	 */
	public SearchResult<Person> list(PersonSearchCondition personSearch) {

		User user = (User) this.getRequestAttribute("ikep.user");

		Integer count = this.personDao.selectTotalCntPerPage(personSearch);

		personSearch.terminateSearchCondition(count);

		SearchResult<Person> searchResult = null;

		if (personSearch.isEmptyRecord()) {
			searchResult = new SearchResult<Person>(personSearch);

		} else {

			List<Person> list = this.personDao.selectAllPerPage(personSearch);

			for (Person person : list) {
				if (!user.getLocaleCode().equals("ko")) {
					person.setPersonName(person.getPersonEnglishName());
					person.setTeamName(person.getTeamEnglishName());
					person.setJobRankName(person.getJobRankEnglishName());
				}
			}
			searchResult = new SearchResult<Person>(list, personSearch);
		}

		return searchResult;

	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.addressbook.service.PersonService#listCount
	 * (com.lgcns.ikep4.support.addressbook.model.PersonSearch)
	 */
	public Integer listCount(PersonSearchCondition personSearch) {
		return personDao.selectTotalCntPerPage(personSearch);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.addressbook.service.PersonService#listAll
	 * (com.lgcns.ikep4.support.addressbook.model.PersonSearch)
	 */
	public SearchResult<Person> listAll(PersonSearchCondition personSearch) {

		String portalId = (String) getRequestAttribute("ikep.portalId");
		personSearch.setPortalId(portalId);

		Integer count = this.personDao.selectTotalCountAll(personSearch);

		personSearch.terminateSearchCondition(count);

		SearchResult<Person> searchResult = null;

		if (personSearch.isEmptyRecord()) {
			searchResult = new SearchResult<Person>(personSearch);

		} else {

			List<Person> list = this.personDao.selectAll(personSearch);
			searchResult = new SearchResult<Person>(list, personSearch);
		}

		return searchResult;
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.addressbook.service.PersonService#
	 * listAllWithoutCount
	 * (com.lgcns.ikep4.support.addressbook.search.PersonSearchCondition)
	 */
	public SearchResult<Person> listAllWithoutCount(PersonSearchCondition personSearch) {

		String portalId = (String) getRequestAttribute("ikep.portalId");

		SearchResult<Person> searchResult = null;

		personSearch.setStartRowIndex(0);
		personSearch.setEndRowIndex(Constant.DEFAULT_ADDRGROUP_PERSON_FETCH_SIZE);
		personSearch.setPortalId(portalId);

		List<Person> list = personDao.selectAll(personSearch);
		searchResult = new SearchResult<Person>(list, personSearch);

		return searchResult;

	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.addressbook.service.PersonService#listAllCount
	 * (com.lgcns.ikep4.support.addressbook.search.PersonSearchCondition)
	 */
	public Integer listAllCount(PersonSearchCondition personSearch) {
		return personDao.selectTotalCountAll(personSearch);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.addressbook.service.PersonService#deleteList
	 * (java.lang.String)
	 */
	public void deleteList(List<String> personList) {

		for (int i = 0; i < personList.size(); i++) {
			Person person = new Person();
			person.setPersonId(personList.get(i));
			personDao.remove(person);
		}

	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.addressbook.service.PersonService#listAllPopUp
	 * (com.lgcns.ikep4.support.addressbook.search.PersonSearchCondition)
	 */
	public SearchResult<Person> listAllPopUp(PersonSearchCondition personSearch) {

		Integer count = this.personDao.selectTotalCountAllPopUp(personSearch);

		personSearch.terminateSearchCondition(count);

		SearchResult<Person> searchResult = null;

		if (personSearch.isEmptyRecord()) {
			searchResult = new SearchResult<Person>(personSearch);

		} else {

			List<Person> list = this.personDao.selectAllPopUp(personSearch);
			searchResult = new SearchResult<Person>(list, personSearch);
		}

		return searchResult;
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.addressbook.service.PersonService#
	 * listAllPopUpCount
	 * (com.lgcns.ikep4.support.addressbook.search.PersonSearchCondition)
	 */
	public Integer listAllPopUpCount(PersonSearchCondition personSearch) {
		return personDao.selectTotalCountAllPopUp(personSearch);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.addressbook.service.PersonService#savePerson
	 * (
	 * com.lgcns.ikep4.support.addressbook.model.Person,com.lgcns.ikep4.support.
	 * user.member.model.User)
	 */
	public void savePerson(Person person, User user) {
		// 주소록 저장
		if (!(StringUtil.isEmpty(person.getPersonId()))) {
			//외부 사용자인경우에만 정보 업데이트
			if("O".equals(person.getUserType()))
				this.update(person);
			//사용자 업데이트 후 그룹정보도 같이 업데이트.
			Person2group person2group = new Person2group();
			person2group.setPersonId(person.getPersonId());
			person2group.setUserType(person.getUserType());
			person2group.setRegisterId(user.getUserId());
			person2group.setRegisterName(user.getUserName());
			person2group.setUpdaterId(user.getUserId());
			person2group.setUpdaterName(user.getUserName());		
			person2groupService.delete(person2group);
			if(person.getAddrgroupIdList() != null)
			{
			    List<String> addrgroupIds = person.getAddrgroupIdList();
			    for (int i = 0; i < addrgroupIds.size(); i++) {
				     if (!(StringUtil.isEmpty(addrgroupIds.get(i)))) {
					      person2group.setAddrgroupId(addrgroupIds.get(i));					
					      person2groupService.create(person2group);
				     }
			     }
			}
		} else {

			String newPersonId = idgenService.getNextId();
			person.setPersonId(newPersonId);
			person.setRegisterId(user.getUserId());
			person.setRegisterName(user.getUserName());
			person.setUserType("O");

			this.create(person);

			List<String> addrgroupIds = person.getAddrgroupIdList();
			for (int i = 0; i < addrgroupIds.size(); i++) {
				if (!(StringUtil.isEmpty(addrgroupIds.get(i)))) {
					Person2group person2group = new Person2group();
					person2group.setAddrgroupId(addrgroupIds.get(i));
					person2group.setPersonId(newPersonId);
					person2group.setUserType(person.getUserType());
					person2group.setRegisterId(user.getUserId());
					person2group.setRegisterName(user.getUserName());
					person2group.setUpdaterId(user.getUserId());
					person2group.setUpdaterName(user.getUserName());
					person2groupService.create(person2group);
				}

			}
		}

	}
	
	public void copyMyAddr(Person person, User user) {

			person.setOrgPersonId(person.getPersonId());
			String newPersonId = idgenService.getNextId();
			person.setPersonId(newPersonId);
			person.setRegisterId(user.getUserId());
			person.setRegisterName(user.getUserName());

			this.copyMyPerson(person);

	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.addressbook.service.PersonService#
	 * saveAddrressbookFile
	 * (com.lgcns.ikep4.support.addressbook.model.Person,com.
	 * lgcns.ikep4.support.user.member.model.User)
	 */
	public void saveAddrressbookFile(PersonList personList, User user, String pgroupTp) {

		List<Person> persons = personList.getPersonList();
		for (Person person : persons) {

			if (!(StringUtil.isEmpty(person.getCheckFlag())) && person.getCheckFlag().equals("true")) {
				
				if("G".equals(pgroupTp)){
					System.out.println("1111111111111111111111111111");
					System.out.println(person.getPersonId());
					person.setRegisterId(user.getUserId());
					person.setRegisterName(user.getUserName());
					person.setUserType("I");
	
					if (!(StringUtil.isEmpty(personList.getAddrgroupId()))) {
						Person2group person2group = new Person2group();
						person2group.setAddrgroupId(personList.getAddrgroupId());
						person2group.setPersonId(person.getPersonId());
						person2group.setUserType(person.getUserType());
						person2group.setRegisterId(user.getUserId());
						person2group.setRegisterName(user.getUserName());
						person2group.setUpdaterId(user.getUserId());
						person2group.setUpdaterName(user.getUserName());
						person2groupService.create(person2group);
					}
				}else{
					System.out.println("22222222222222222222222222");
					String newPersonId = idgenService.getNextId();
					person.setPersonId(newPersonId);
					person.setRegisterId(user.getUserId());
					person.setRegisterName(user.getUserName());
					person.setUserType("O");
	
					this.create(person);
	
					if (!(StringUtil.isEmpty(personList.getAddrgroupId()))) {
						Person2group person2group = new Person2group();
						person2group.setAddrgroupId(personList.getAddrgroupId());
						person2group.setPersonId(newPersonId);
						person2group.setUserType(person.getUserType());
						person2group.setRegisterId(user.getUserId());
						person2group.setRegisterName(user.getUserName());
						person2group.setUpdaterId(user.getUserId());
						person2group.setUpdaterName(user.getUserName());
						person2groupService.create(person2group);
					}
				}

			}

		}

	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.addressbook.service.PersonService#
	 * removePersonByType(java.lang.Object,java.lang.Object)
	 */
	public void removePersonByType(String personId, String personType) {

		if (personType.equals("O")) {

			Person person = new Person();
			person.setPersonId(personId);
			//this.delete(person);
			this.deletePerson(person);

			Person2group person2group = new Person2group();
			person2group.setPersonId(personId);
			person2groupService.delete(person2group);

		} else {

			Person2group person2group = new Person2group();
			person2group.setPersonId(personId);
			person2groupService.delete(person2group);

		}

	}
	
	public void copyTeamByType(Person2group person2group) {
		person2groupService.copyTeamAddr(person2group);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.addressbook.service.PersonService#
	 * removePersonFromGroup(java.util.List<E>,java.lang.Object)
	 */
	public void removePersonFromGroup(List<String> personIdList, String addrgroupId) {

		for (int i = 0; i < personIdList.size(); i++) {

			List<String> paramList = StringUtil.getTokens(personIdList.get(i), "|");

			Person2group person2group = new Person2group();
			person2group.setPersonId(paramList.get(0));
			person2group.setAddrgroupId(addrgroupId);
			person2groupService.delete(person2group);
		}

	}

	/**
	 * 세션 정보 얻기
	 * 
	 * @param name
	 * @return
	 */
	public Object getRequestAttribute(String name) {
		return RequestContextHolder.currentRequestAttributes().getAttribute(name, RequestAttributes.SCOPE_SESSION);
	}
	
	public Person selectPersonByMail(PersonSearchCondition personSearch)
	{
		return personDao.selectPersonByMail(personSearch);
		
	}

}
