/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.microblogging.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.socialpack.microblogging.dao.Mblog2tagDao;
import com.lgcns.ikep4.socialpack.microblogging.model.Mblog2tag;


/**
 * 
 * Mblog2tagDao 구현 클래스
 *
 * @author 최성우(csw9737@hanmail.net)
 * @version $Id: Mblog2tagDaoImpl.java 16246 2011-08-18 04:48:28Z giljae $
 */
@Repository
public class Mblog2tagDaoImpl extends GenericDaoSqlmap<Mblog2tag, Mblog2tag> implements Mblog2tagDao {
	private static final String NAMESPACE = "socialpack.microblogging.dao.mblog2tag.";

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public Mblog2tag create(Mblog2tag mblog2tag) {
		return (Mblog2tag) sqlInsert(NAMESPACE + "insert", mblog2tag);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable
	 * )
	 */
	public void remove(Mblog2tag mblog2tag) {
		sqlDelete(NAMESPACE + "delete", mblog2tag);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable
	 * )
	 */
	public boolean exists(Mblog2tag mblog2tag) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public Mblog2tag get(Mblog2tag mblog2tag) {
		return (Mblog2tag) sqlSelectForObject(NAMESPACE + "select", mblog2tag);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(Mblog2tag mblog2tag) {
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.dao.Mblog2tagDao#selectListByMblogId(java.lang.String)
	 */
	public List<Mblog2tag> selectListByMblogId(String mblogId) {
		return sqlSelectForList(NAMESPACE + "listByMblogId", mblogId);
	}

}
