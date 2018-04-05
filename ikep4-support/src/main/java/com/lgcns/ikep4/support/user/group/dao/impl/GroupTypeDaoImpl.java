/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.user.group.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;
import com.lgcns.ikep4.support.user.group.dao.GroupTypeDao;
import com.lgcns.ikep4.support.user.group.model.GroupType;


/**
 * TODO Javadoc주석작성
 * 
 * @author 양새로훈(yang.sae@gmail.com)
 * @version $Id: GroupTypeDaoImpl.java 16276 2011-08-18 07:09:07Z giljae $
 */
@Repository("groupTypeDao")
public class GroupTypeDaoImpl extends GenericDaoSqlmap<GroupType, String> implements GroupTypeDao {

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.group.dao.GroupTypeDao#selectAll(com.lgcns.ikep4.support.user.code.model.AdminSearchCondition)
	 */
	public List<GroupType> selectAll(AdminSearchCondition searchCondition) {

		return sqlSelectForList("support.user.group.dao.GroupType.selectAll", searchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.group.dao.GroupTypeDao#selectCount(com.lgcns.ikep4.support.user.code.model.AdminSearchCondition)
	 */
	public Integer selectCount(AdminSearchCondition searchCondition) {

		return (Integer) sqlSelectForObject("support.user.group.dao.GroupType.selectCount", searchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public String create(GroupType groupType) {

		return (String) sqlInsert("support.user.group.dao.GroupType.insert", groupType);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable)
	 */
	public boolean exists(String id) {

		String count = (String) sqlSelectForObject("support.user.group.dao.GroupType.checkId", id);

		return !count.equals("0");
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(GroupType groupType) {

		sqlUpdate("support.user.group.dao.GroupType.update", groupType);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable)
	 */
	public void remove(String id) {

		sqlDelete("support.user.group.dao.GroupType.delete", id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.group.dao.GroupTypeDao#selectForList(java.lang.String)
	 */
	public List<GroupType> selectForList() {

		return (List<GroupType>) sqlSelectForList("support.user.group.dao.GroupType.selectForList");
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public GroupType get(String id) {
		
		return (GroupType) sqlSelectForObject("support.user.group.dao.GroupType.select", id);
	}

}
