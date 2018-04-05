/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.peopleconnection.dao.impl;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.socialpack.peopleconnection.dao.PeopleConnectionDao;
import com.lgcns.ikep4.socialpack.peopleconnection.model.ExternalExpert;

/**
 * 
 * PeopleConnectionDao 구현 클래스
 *
 * @author 최성우
 * @version $Id: PeopleConnectionDaoImpl.java 16246 2011-08-18 04:48:28Z giljae $
 */
@Repository("peopleConnectionDao")
public class PeopleConnectionDaoImpl extends GenericDaoSqlmap<ExternalExpert, String> implements PeopleConnectionDao {

	private static final String NAMESPACE = "socialpack.peopleconnection.dao.peopleConnection.";
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public ExternalExpert get(String profileId) {
		return (ExternalExpert) sqlSelectForObject(NAMESPACE + "select", profileId);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable)
	 */
	public boolean exists(String id) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public String create(ExternalExpert object) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(ExternalExpert object) {
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable)
	 */
	public void remove(String id) {
		
	}

}
