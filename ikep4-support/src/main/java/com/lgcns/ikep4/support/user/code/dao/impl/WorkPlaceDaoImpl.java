/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.user.code.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.user.code.dao.WorkPlaceDao;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;
import com.lgcns.ikep4.support.user.code.model.WorkPlace;


/**
 * 사업장 코드 관련 Dao 구현체
 * 
 * @author 양새로훈(yang.sae@gmail.com)
 * @version $Id: WorkPlaceDaoImpl.java 19023 2012-05-31 06:36:51Z malboru80 $
 */
@Repository("workPlaceDao")
public class WorkPlaceDaoImpl extends GenericDaoSqlmap<WorkPlace, String> implements WorkPlaceDao {

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.code.dao.WorkPlaceDao#selectAll(com.lgcns
	 * .ikep4.support.user.code.model.AdminSearchCondition)
	 */
	public List<WorkPlace> selectAll(AdminSearchCondition searchCondition) {

		return sqlSelectForList("support.user.code.dao.WorkPlace.selectAll", searchCondition);
	}
	
	public List<WorkPlace> getAll(String localeCode) {
		
		return sqlSelectForList("support.user.code.dao.WorkPlace.getAll", localeCode);
	}
	

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.code.dao.WorkPlaceDao#selectCount(com.
	 * lgcns .ikep4.support.user.code.model.AdminSearchCondition)
	 */
	public Integer selectCount(AdminSearchCondition searchCondition) {

		return (Integer) sqlSelectForObject("support.user.code.dao.WorkPlace.selectCount", searchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public String create(WorkPlace workPlace) {

		return (String) sqlInsert("support.user.code.dao.WorkPlace.insert", workPlace);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable
	 * )
	 */
	public boolean exists(String workPlaceCode) {

		String count = (String) sqlSelectForObject("support.user.code.dao.WorkPlace.checkCode", workPlaceCode);

		return !count.equals("0");
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public WorkPlace get(String workPlaceCode) {

		return (WorkPlace) sqlSelectForObject("support.user.code.dao.WorkPlace.select", workPlaceCode);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(WorkPlace workPlace) {

		sqlUpdate("support.user.code.dao.WorkPlace.update", workPlace);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable
	 * )
	 */
	public void remove(String workPlaceCode) {

		sqlDelete("support.user.code.dao.WorkPlace.delete", workPlaceCode);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.code.dao.WorkPlaceDao#getMaxSortOrder()
	 */
	public String getMaxSortOrder() {

		return (String) sqlSelectForObject("support.user.code.dao.WorkPlace.getMaxSortOrder");
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.code.dao.WorkPlaceDao#goUp(java.util.Map)
	 */
	public WorkPlace goUp(Map<String, String> map) {

		return (WorkPlace) sqlSelectForObject("support.user.code.dao.WorkPlace.selectGoUp", map);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.code.dao.WorkPlaceDao#goDown(java.util
	 * .Map)
	 */
	public WorkPlace goDown(Map<String, String> map) {

		return (WorkPlace) sqlSelectForObject("support.user.code.dao.WorkPlace.selectGoDown", map);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.code.dao.WorkPlaceDao#updateSortOrder(com
	 * .lgcns.ikep4.support.user.code.model.WorkPlace)
	 */
	public void updateSortOrder(WorkPlace workPlace) {

		sqlUpdate("support.user.code.dao.WorkPlace.updateSortOrder", workPlace);
	}

	public List<WorkPlace> listWorkPlaceAll(String portalId) {
		return sqlSelectForList("support.user.code.dao.WorkPlace.listWorkPlaceAll", portalId);
	}

}