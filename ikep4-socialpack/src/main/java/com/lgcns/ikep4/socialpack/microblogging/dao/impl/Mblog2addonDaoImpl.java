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
import com.lgcns.ikep4.socialpack.microblogging.dao.Mblog2addonDao;
import com.lgcns.ikep4.socialpack.microblogging.model.Mblog2addon;


/**
 * 
 * Mblog2addonDao 구현 클래스
 *
 * @author 최성우
 * @version $Id: Mblog2addonDaoImpl.java 16246 2011-08-18 04:48:28Z giljae $
 */
@Repository
public class Mblog2addonDaoImpl extends GenericDaoSqlmap<Mblog2addon, Mblog2addon> implements Mblog2addonDao {
	private static final String NAMESPACE = "socialpack.microblogging.dao.mblog2addon.";

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public Mblog2addon create(Mblog2addon mblog2addon) {
		return (Mblog2addon) sqlInsert(NAMESPACE + "insert", mblog2addon);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable
	 * )
	 */
	public void remove(Mblog2addon mblog2addon) {
		sqlDelete(NAMESPACE + "delete", mblog2addon);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable
	 * )
	 */
	public boolean exists(Mblog2addon mblog2addon) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public Mblog2addon get(Mblog2addon mblog2addon) {
		return null ;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(Mblog2addon mblog2addon) {
		
	}

}
