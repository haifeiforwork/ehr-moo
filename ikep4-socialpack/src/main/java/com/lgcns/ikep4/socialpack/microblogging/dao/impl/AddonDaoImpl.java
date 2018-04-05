/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.microblogging.dao.impl;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.socialpack.microblogging.dao.AddonDao;
import com.lgcns.ikep4.socialpack.microblogging.model.Addon;


/**
 * 
 * addonDao 구현 클래스
 *
 * @author 최성우(csw9737@hanmail.net)
 * @version $Id: AddonDaoImpl.java 16246 2011-08-18 04:48:28Z giljae $
 */
@Repository
public class AddonDaoImpl extends GenericDaoSqlmap<Addon, String> implements AddonDao {
	private static final String NAMESPACE = "socialpack.microblogging.dao.addon.";

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public String create(Addon addon) {
		return (String) sqlInsert(NAMESPACE + "insert", addon);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable
	 * )
	 */
	public boolean exists(String id) {

		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public Addon get(String id) {
		return (Addon) sqlSelectForObject(NAMESPACE + "select", id);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable
	 * )
	 */
	public void remove(String id) {
		sqlDelete(NAMESPACE + "delete", id);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(Addon addon) {
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.dao.AddonDao#getSeq()
	 */
	public int getSeq() {
		return (Integer)sqlSelectForObject(NAMESPACE + "getSeq");
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.dao.AddonDao#selectBySourceLink(java.lang.String)
	 */
	public Addon selectBySourceLink(String sourceLink) {
		return (Addon) sqlSelectForObject(NAMESPACE + "selectBySourceLink", sourceLink);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.dao.AddonDao#selectByDisplayCode(java.lang.String)
	 */
	public Addon selectByDisplayCode(String displayCode) {
		return (Addon) sqlSelectForObject(NAMESPACE + "selectByDisplayCode", displayCode);
	}

}
