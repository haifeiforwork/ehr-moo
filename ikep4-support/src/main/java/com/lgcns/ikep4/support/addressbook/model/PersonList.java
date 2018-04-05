/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.addressbook.model;

import java.util.List;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * Person 주소록 등록된 사용자 VO
 *
 * @author 이형운
 * @version $Id: PersonList.java 16271 2011-08-18 07:06:14Z giljae $
 */
public class PersonList extends BaseObject {
	
	/**
	 *
	 */
	private static final long serialVersionUID = -8597293785591768501L;

	/**
	 * 일괄 저장 용도의 personList
	 */
	private List<Person> personList;
	
	/**
	 * 일괄 저장 용도의 personList 의 사이즈
	 */
	private Integer personListSize;
	
	/**
	 * 일괄 저장 용도 personList의 개별 Person 
	 */
	private Person person;
	
	/**
	 * 일괄 저장 용도 저장시 저장해야 할 그룹 ID 값
	 */
	private String addrgroupId;

	/**
	 * @return the personList
	 */
	public List<Person> getPersonList() {
		return personList;
	}

	/**
	 * @param personList the personList to set
	 */
	public void setPersonList(List<Person> personList) {
		this.personList = personList;
	}

	/**
	 * @return the personListSize
	 */
	public Integer getPersonListSize() {
		return personListSize;
	}

	/**
	 * @param personListSize the personListSize to set
	 */
	public void setPersonListSize(Integer personListSize) {
		this.personListSize = personListSize;
	}

	/**
	 * @return the person
	 */
	public Person getPerson(Integer idx) {
		return personList.get(idx);
	}

	/**
	 * @param person the person to set
	 */
	public void setPerson(Person person) {
		this.personList.add(person);
	}

	/**
	 * @return the addrgroupId
	 */
	public String getAddrgroupId() {
		return addrgroupId;
	}

	/**
	 * @param addrgroupId the addrgroupId to set
	 */
	public void setAddrgroupId(String addrgroupId) {
		this.addrgroupId = addrgroupId;
	}

	/**
	 * @return the person
	 */
	public Person getPerson() {
		return person;
	}
	
	
}

