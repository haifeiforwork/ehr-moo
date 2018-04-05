package com.lgcns.ikep4.support.abusereporting.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.abusereporting.dao.ArModuleDao;
import com.lgcns.ikep4.support.abusereporting.model.ArModule;


/**
 * 
 * ArModuleDao 구현 클래스
 *
 * @author 최성우
 * @version $Id: ArModuleDaoImpl.java 16316 2011-08-22 00:26:53Z giljae $
 */
@Repository
public class ArModuleDaoImpl extends GenericDaoSqlmap<ArModule, String> implements ArModuleDao {

	private static final String NAMESPACE = "support.abusereporting.dao.arModule.";

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public String create(ArModule arModule) {
		return (String) sqlInsert(NAMESPACE + "insert", arModule);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public ArModule get(String arModule) {
		return (ArModule) sqlSelectForObject(NAMESPACE + "select", arModule);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable)
	 */
	public boolean exists(String id) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(ArModule object) {

	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable)
	 */
	public void remove(String id) {
		sqlDelete(NAMESPACE + "delete", id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.abusereporting.dao.ArModuleDao#list()
	 */
	public List<ArModule> list() {
		return sqlSelectForList(NAMESPACE + "list");
	}

}
