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
import com.lgcns.ikep4.lightpack.cafe.cafe.dao.CafeLayoutDao;
import com.lgcns.ikep4.lightpack.cafe.cafe.model.CafeLayout;


/**
 * 포틀릿_레이아웃 DAO Impl Class
 * 
 * @author 이형운
 * @version $Id: CafeLayoutDaoImpl.java 16297 2011-08-19 07:52:43Z giljae $
 */
@Repository("cafeLayoutDao")
public class CafeLayoutDaoImpl extends GenericDaoSqlmap<CafeLayout, String> implements CafeLayoutDao {

	private static final String NAMESPACE = "lightpack.cafe.cafe.dao.CafeLayout.";

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public CafeLayout get(String layoutId) {
		return (CafeLayout) sqlSelectForObject(NAMESPACE + "get", layoutId);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable
	 * )
	 */
	public boolean exists(String layoutId) {
		Integer count = (Integer) sqlSelectForObject(NAMESPACE + "exists", layoutId);
		return count > 0;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public String create(CafeLayout cafeLayout) {
		sqlInsert(NAMESPACE + "create", cafeLayout);
		return cafeLayout.getLayoutId();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(CafeLayout cafeLayout) {
		sqlUpdate(NAMESPACE + "update", cafeLayout);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#physicalDelete(java.io.
	 * Serializable)
	 */
	public void physicalDelete(String layoutId) {
		sqlDelete(NAMESPACE + "physicalDelete", layoutId);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.lightpack.cafe.cafe.dao.CafeLayoutDao#listCafeLayout()
	 */
	public List<CafeLayout> listCafeLayout() {
		return sqlSelectForList(NAMESPACE + "listCafeLayout");
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.lightpack.cafe.cafe.dao.CafeLayoutDao#countCafeLayout()
	 */
	public Integer countCafeLayout() {
		return (Integer) sqlSelectForObject(NAMESPACE + "countCafeLayout");
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.lightpack.cafe.cafe.dao.CafeLayoutDao#getDefaultLayout ()
	 */
	public CafeLayout getDefaultLayout() {
		return (CafeLayout) sqlSelectForObject(NAMESPACE + "getDefaultLayout");
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.lightpack.cafe.cafe.dao.CafeLayoutDao#isDefaultLayout
	 * (java.lang.String)
	 */
	public boolean isDefaultLayout(String layoutId) {
		Integer count = (Integer) sqlSelectForObject(NAMESPACE + "isDefaultLayout");
		return count > 0;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable
	 * )
	 */
	@Deprecated
	public void remove(String id) {}

}
