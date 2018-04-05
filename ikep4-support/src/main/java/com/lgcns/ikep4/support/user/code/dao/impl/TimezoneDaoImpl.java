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
import com.lgcns.ikep4.support.user.code.dao.TimezoneDao;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;
import com.lgcns.ikep4.support.user.code.model.Timezone;


/**
 * TIMEZONE 코드 관련 Dao 구현체
 * 
 * @author 양새로훈(yang.sae@gmail.com)
 * @version $Id: TimezoneDaoImpl.java 16258 2011-08-18 05:37:22Z giljae $
 */
@Repository("timezoneDao")
public class TimezoneDaoImpl extends GenericDaoSqlmap<Timezone, String> implements TimezoneDao {

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.code.dao.TimezoneDao#selectAll(com.lgcns.ikep4.support.user.code.model.AdminSearchCondition)
	 */
	public List<Timezone> selectAll(AdminSearchCondition searchCondition) {

		return sqlSelectForList("support.user.code.dao.Timezone.selectAll", searchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.code.dao.TimezoneDao#selectCount(com.lgcns.ikep4.support.user.code.model.AdminSearchCondition)
	 */
	public Integer selectCount(AdminSearchCondition searchCondition) {

		return (Integer) sqlSelectForObject("support.user.code.dao.Timezone.selectCount", searchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public String create(Timezone timezone) {

		return (String) sqlInsert("support.user.code.dao.Timezone.insert", timezone);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable)
	 */
	public boolean exists(String id) {

		String count = (String) sqlSelectForObject("support.user.code.dao.Timezone.checkId", id);

		return !count.equals("0");
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.code.dao.TimezoneDao#checkId(java.lang.String)
	 */
	public String checkId(String id) {

		return (String) sqlSelectForObject("support.user.code.dao.Timezone.checkId", id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(Timezone timezone) {

		sqlUpdate("support.user.code.dao.Timezone.update", timezone);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable)
	 */
	public void remove(String id) {

		sqlDelete("support.user.code.dao.Timezone.delete", id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.code.dao.TimezoneDao#getMaxSortOrder()
	 */
	public String getMaxSortOrder() {

		return (String) sqlSelectForObject("support.user.code.dao.Timezone.getMaxSortOrder");
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.code.dao.TimezoneDao#goUp(java.util.Map)
	 */
	public Timezone goUp(Map<String,String> map) {

		return (Timezone) sqlSelectForObject("support.user.code.dao.Timezone.selectGoUp", map);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.code.dao.TimezoneDao#goDown(java.util.Map)
	 */
	public Timezone goDown(Map<String,String> map) {

		return (Timezone) sqlSelectForObject("support.user.code.dao.Timezone.selectGoDown", map);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.code.dao.TimezoneDao#updateSortOrder(com.lgcns.ikep4.support.user.code.model.Timezone)
	 */
	public void updateSortOrder(Timezone timezone) {

		sqlUpdate("support.user.code.dao.Timezone.updateSortOrder", timezone);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public Timezone get(String id) {
		
		return (Timezone) sqlSelectForObject("support.user.code.dao.Timezone.select", id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.code.dao.TimezoneDao#list(java.util.Map)
	 */
	public List<Timezone> list(Map<String,String> param) {

		return sqlSelectForList("support.user.code.dao.Timezone.selectNoPage", param);
	}

}
