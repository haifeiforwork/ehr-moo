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
import com.lgcns.ikep4.socialpack.microblogging.dao.SearchDao;
import com.lgcns.ikep4.socialpack.microblogging.model.Search;
import com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition;


/**
 * 
 * searchDao 구현 클래스
 *
 * @author 최성우(csw9737@hanmail.net)
 * @version $Id: SearchDaoImpl.java 16246 2011-08-18 04:48:28Z giljae $
 */
@Repository
public class SearchDaoImpl extends GenericDaoSqlmap<Search, String> implements SearchDao {
	private static final String NAMESPACE = "socialpack.microblogging.dao.search.";

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public String create(Search search) {
		return (String) sqlInsert(NAMESPACE + "insert", search);
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
	public Search get(String id) {
		return (Search) sqlSelectForObject(NAMESPACE + "select", id);
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
	public void update(Search search) {

		this.sqlUpdate(NAMESPACE + "update", search); 
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.dao.SearchDao#selectCount(com.lgcns.ikep4.socialpack.microblogging.model.Search)
	 */
	public int selectCount(Search search) {
		return  (Integer) sqlSelectForObject(NAMESPACE + "selectCount", search);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.dao.SearchDao#selectListBySearchUserId(com.lgcns.ikep4.socialpack.microblogging.model.Search)
	 */
	public List selectListBySearchUserId(Search search) {
		return  sqlSelectForListOfObject(NAMESPACE + "selectListBySearchUserId", search);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.dao.SearchDao#selectTrendList(java.lang.String)
	 */
	public List selectTrendList(MblogSearchCondition mblogSearchCondition) {
		return  sqlSelectForListOfObject(NAMESPACE + "selectTrendList", mblogSearchCondition);
	}

}
