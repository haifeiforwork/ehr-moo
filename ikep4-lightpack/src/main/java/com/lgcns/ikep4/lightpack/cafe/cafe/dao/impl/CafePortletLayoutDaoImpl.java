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
import com.lgcns.ikep4.lightpack.cafe.cafe.dao.CafePortletLayoutDao;
import com.lgcns.ikep4.lightpack.cafe.cafe.model.CafePortletLayout;


/**
 * 개별 Cafe 레이아웃 Dao
 * 
 * @author 김종철
 * @version $Id: CafePortletLayoutDaoImpl.java 14298 2011-06-03 02:29:54Z jghong
 *          $
 */
@Repository("cafePortletLayoutDao")
public class CafePortletLayoutDaoImpl extends GenericDaoSqlmap<CafePortletLayout, String> implements
		CafePortletLayoutDao {

	private static final String NAMESPACE = "lightpack.cafe.cafe.dao.CafePortletLayout.";

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public CafePortletLayout get(String portletLayoutId) {
		return (CafePortletLayout) sqlSelectForObject(NAMESPACE + "get", portletLayoutId);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable
	 * )
	 */
	public boolean exists(String portletLayoutId) {
		Integer count = (Integer) sqlSelectForObject(NAMESPACE + "exists", portletLayoutId);
		return count > 0;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public String create(CafePortletLayout object) {
		sqlInsert(NAMESPACE + "create", object);
		return object.getPortletLayoutId();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(CafePortletLayout object) {
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
	 * com.lgcns.ikep4.lightpack.cafe.cafe.dao.CafePortletLayoutDao#listByCafe
	 * (java.lang.String)
	 */
	public List<CafePortletLayout> listByCafe(String cafeId) {
		return sqlSelectForList(NAMESPACE + "listBycafe", cafeId);
	}

	public List<CafePortletLayout> listLayoutByCafe(String cafeId) {
		return sqlSelectForList(NAMESPACE + "listLayoutByCafe", cafeId);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.lightpack.cafe.cafe.dao.CafePortletLayoutDao#countByCafe
	 * (java.lang.String)
	 */
	public Integer countByCafe(String cafeId) {
		return (Integer) sqlSelectForObject(NAMESPACE + "countBycafe", cafeId);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.lightpack.cafe.cafe.dao.CafePortletLayoutDao#physicalDelete
	 * (java.lang.String)
	 */
	public void physicalDelete(String portletLayoutId) {
		sqlDelete(NAMESPACE + "physicalDelete", portletLayoutId);

	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.cafe.cafe.dao.CafePortletLayoutDao#
	 * physicalDeleteThreadByCafe(java.lang.String)
	 */
	public void physicalDeleteByCafeId(String cafeId) {
		sqlDelete(NAMESPACE + "physicalDeleteByCafeId", cafeId);

	}

}
