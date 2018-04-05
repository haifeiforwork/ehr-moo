/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.addressbook.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.addressbook.dao.AddrgroupDao;
import com.lgcns.ikep4.support.addressbook.model.Addrgroup;
import com.lgcns.ikep4.support.addressbook.model.Person;
import com.lgcns.ikep4.support.addressbook.search.AddrgroupSearchCondition;


/**
 * 주소록 그룹 사용자 Dao Impl
 * 
 * @author 이형운
 * @version $Id: AddrgroupDaoImpl.java 16271 2011-08-18 07:06:14Z giljae $
 */
@Repository("addrgroupDao")
public class AddrgroupDaoImpl extends GenericDaoSqlmap<Addrgroup, String> implements AddrgroupDao {


	/**
	 * ADDRESSBOOK_NAME_SPACE
	 */
	private static final String ADDRESSBOOK_NAME_SPACE = "support.addressbook.dao.Addressbook.";
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.addressbook.dao.AddrgroupDao#get(com.lgcns.ikep4.support.addressbook.model.Addrgroup)
	 */
	public Addrgroup get(Addrgroup addrgroup) {
		return (Addrgroup) sqlSelectForObject( ADDRESSBOOK_NAME_SPACE + "selectAddrgroup", addrgroup);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.addressbook.dao.AddrgroupDao#exists(com.lgcns.ikep4.support.addressbook.model.Addrgroup)
	 */
	public boolean exists(Addrgroup addrgroup) {
		Integer count = (Integer) sqlSelectForObject(ADDRESSBOOK_NAME_SPACE + "selectCountAddrgroup", addrgroup);
		return count > 0;
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(com.lgcns.ikep4.support.addressbook.model.Addrgroup)
	 */
	public String create(Addrgroup addrgroup) {
		return (String) sqlInsert(ADDRESSBOOK_NAME_SPACE + "insertAddrgroup", addrgroup);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(com.lgcns.ikep4.support.addressbook.model.Addrgroup)
	 */
	public void update(Addrgroup addrgroup) {
		sqlUpdate(ADDRESSBOOK_NAME_SPACE + "updateAddrgroup", addrgroup);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(com.lgcns.ikep4.support.addressbook.model.Addrgroup)
	 */
	public void remove(Addrgroup addrgroup) {
		sqlDelete(ADDRESSBOOK_NAME_SPACE + "deleteAddrgroup", addrgroup);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#selectAll(com.lgcns.ikep4.support.addressbook.model.Addrgroup)
	 */
	public List<Addrgroup> selectAll(AddrgroupSearchCondition addrgroupSearch) {
		return sqlSelectForList(ADDRESSBOOK_NAME_SPACE + "selectAddrgroupPerPage", addrgroupSearch);
	}
	
	public List<Person> personList(String[] personNames){
		return getSqlMapClientTemplate().queryForList(ADDRESSBOOK_NAME_SPACE + "getPersonList", personNames);
	}
	
	public List<Addrgroup> selectPubAll(AddrgroupSearchCondition addrgroupSearch) {
		if(addrgroupSearch.getAddrgroupId()==""){
			return sqlSelectForList(ADDRESSBOOK_NAME_SPACE + "selectAddrgroupPub1", addrgroupSearch);
		}else{
			return sqlSelectForList(ADDRESSBOOK_NAME_SPACE + "selectAddrgroupPub2", addrgroupSearch);
		}
		
	}


	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#selectAllTotalCount(com.lgcns.ikep4.support.addressbook.model.Addrgroup)
	 */
	public Integer selectAllTotalCount(AddrgroupSearchCondition addrgroupSearch) {
		return (Integer) sqlSelectForObject(ADDRESSBOOK_NAME_SPACE + "selectTotalCntAddrgroupPerPage", addrgroupSearch);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.lang.Object)
	 */
	@Deprecated
	public Addrgroup get(String id) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.lang.Object)
	 */
	@Deprecated
	public boolean exists(String id) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.lang.Object)
	 */
	@Deprecated
	public void remove(String id) {}

}
