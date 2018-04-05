/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.cafe.cafe.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.lightpack.cafe.cafe.dao.CafePortletDao;
import com.lgcns.ikep4.lightpack.cafe.cafe.model.CafePortlet;


/**
 * 개별 Cafe 포틀릿 Dao
 * 
 * @author 김종철
 * @version $Id: CafePortletDaoImpl.java 16297 2011-08-19 07:52:43Z giljae $
 */
@Repository("cafePortletDao")
public class CafePortletDaoImpl extends GenericDaoSqlmap<CafePortlet, String> implements CafePortletDao {

	private static final String NAMESPACE = "lightpack.cafe.cafe.dao.CafePortlet.";

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public CafePortlet get(String portletId) {
		return (CafePortlet) sqlSelectForObject(NAMESPACE + "get", portletId);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable
	 * )
	 */
	public boolean exists(String portletId) {
		Integer count = (Integer) sqlSelectForObject(NAMESPACE + "exists", portletId);
		return count > 0;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public String create(CafePortlet object) {
		sqlInsert(NAMESPACE + "create", object);
		return object.getPortletId();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(CafePortlet object) {
		sqlUpdate(NAMESPACE + "update", object);

	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable
	 * )
	 */
	@Deprecated
	public void remove(String id) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.lightpack.cafe.cafe.dao.CafePortletDao#listAllPortlet
	 * (com.lgcns.ikep4.lightpack.cafe.cafe.model.CafePortlet)
	 */
	public List<CafePortlet> listAllPortlet(CafePortlet cafePortlet) {
		return sqlSelectForList(NAMESPACE + "listAllPortlet", cafePortlet);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.lightpack.cafe.cafe.dao.CafePortletDao#countAllPortlet
	 * (com.lgcns.ikep4.lightpack.cafe.cafe.model.CafePortlet)
	 */
	public Integer countAllPortlet(CafePortlet cafePortlet) {
		return (Integer) sqlSelectForObject(NAMESPACE + "countAllPortlet", cafePortlet);
	}

	public List<CafePortlet> listAllCafePortlet(String cafeId) {
		return sqlSelectForList(NAMESPACE + "listAllCafePortlet", cafeId);
	}

	public Integer countAllCafePortlet(String cafeId) {
		return (Integer) sqlSelectForObject(NAMESPACE + "countAllCafePortlet", cafeId);
	}

}
