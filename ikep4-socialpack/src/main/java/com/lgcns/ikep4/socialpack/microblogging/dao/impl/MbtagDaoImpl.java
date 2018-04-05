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
import com.lgcns.ikep4.socialpack.microblogging.dao.MbtagDao;
import com.lgcns.ikep4.socialpack.microblogging.model.Mbtag;


/**
 * 
 * mbtagDao 구현 클래스
 *
 * @author 최성우(csw9737@hanmail.net)
 * @version $Id: MbtagDaoImpl.java 16246 2011-08-18 04:48:28Z giljae $
 */
@Repository
public class MbtagDaoImpl extends GenericDaoSqlmap<Mbtag, Mbtag> implements MbtagDao {
	private static final String NAMESPACE = "socialpack.microblogging.dao.mbtag.";

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public Mbtag create(Mbtag mbtag) {
		return (Mbtag) sqlInsert(NAMESPACE + "insert", mbtag);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable
	 * )
	 */
	public boolean exists(Mbtag mbtag) {

		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public Mbtag get(Mbtag mbtag) {
		return (Mbtag) sqlSelectForObject(NAMESPACE + "select", mbtag);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable
	 * )
	 */
	public void remove(Mbtag mbtag) {
		sqlDelete(NAMESPACE + "delete", mbtag);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(Mbtag mbtag) {
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.dao.MbtagDao#selectTagIdByTagFullName(com.lgcns.ikep4.socialpack.microblogging.model.Mbtag)
	 */
	public String selectTagIdByTagFullName(Mbtag mbtag) {
		return (String) sqlSelectForObject(NAMESPACE + "selectTagIdByTagFullName", mbtag);
	}

}
