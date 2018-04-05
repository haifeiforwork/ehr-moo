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
import com.lgcns.ikep4.lightpack.cafe.cafe.dao.CafeLayoutColumnDao;
import com.lgcns.ikep4.lightpack.cafe.cafe.model.CafeLayoutColumn;


/**
 * 레이아웃 컬럼 정보 DAO Impl Class
 * 
 * @author 이형운
 * @version $Id: CafeLayoutColumnDaoImpl.java 14767 2011-06-10 08:21:23Z
 *          handul32 $
 */
@Repository("cafeLayoutColumnDao")
public class CafeLayoutColumnDaoImpl extends GenericDaoSqlmap<CafeLayoutColumn, String> implements CafeLayoutColumnDao {

	private static final String NAMESPACE = "lightpack.cafe.cafe.dao.CafeLayoutColumn.";

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.lightpack.cafe.cafe.dao.CafeLayoutColumnDao#get(com.lgcns
	 * .ikep4.socialpack.socialblog.model.CafeLayoutColumn)
	 */
	public CafeLayoutColumn get(CafeLayoutColumn cafeLayoutColumn) {
		return (CafeLayoutColumn) sqlSelectForObject(NAMESPACE + "get", cafeLayoutColumn);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.lightpack.cafe.cafe.dao.CafeLayoutColumnDao#exists(com
	 * .lgcns.ikep4.socialpack.socialblog.model.CafeLayoutColumn)
	 */
	public boolean exists(CafeLayoutColumn CafeLayoutColumn) {
		Integer count = (Integer) sqlSelectForObject(NAMESPACE + "exists", CafeLayoutColumn);
		return count > 0;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public String create(CafeLayoutColumn cafeLayoutColumn) {
		sqlInsert(NAMESPACE + "create", cafeLayoutColumn);
		return cafeLayoutColumn.getLayoutId();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(CafeLayoutColumn cafeLayoutColumn) {
		sqlUpdate(NAMESPACE + "update", cafeLayoutColumn);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.lightpack.cafe.cafe.dao.CafeLayoutColumnDao#listByLayoutId
	 * (java.lang.String)
	 */
	public List<CafeLayoutColumn> listByLayoutId(String layoutId) {
		return sqlSelectForList(NAMESPACE + "listByLayoutId", layoutId);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.lightpack.cafe.cafe.dao.CafeLayoutColumnDao#countByLayoutId
	 * (java.lang.String)
	 */
	public Integer countByLayoutId(String layoutId) {
		return (Integer) sqlSelectForObject(NAMESPACE + "countByLayoutId", layoutId);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.cafe.cafe.dao.CafeLayoutColumnDao#
	 * getFixedLayoutColumn(java.lang.String)
	 */
	public Integer getFixedLayoutColumn(String layoutId) {
		return (Integer) sqlSelectForObject(NAMESPACE + "getFixedLayoutColumn", layoutId);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.lightpack.cafe.cafe.dao.CafeLayoutColumnDao#physicalDelete
	 * (com.lgcns.ikep4.socialpack.socialblog.model.CafeLayoutColumn)
	 */
	public void physicalDelete(CafeLayoutColumn CafeLayoutColumn) {
		sqlDelete(NAMESPACE + "physicalDelete", CafeLayoutColumn);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	@Deprecated
	public CafeLayoutColumn get(String id) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable
	 * )
	 */
	@Deprecated
	public boolean exists(String id) {
		return false;
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
