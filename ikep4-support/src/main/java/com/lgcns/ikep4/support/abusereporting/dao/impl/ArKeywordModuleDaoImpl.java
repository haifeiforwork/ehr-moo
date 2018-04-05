package com.lgcns.ikep4.support.abusereporting.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.abusereporting.dao.ArKeywordModuleDao;
import com.lgcns.ikep4.support.abusereporting.model.ArKeywordModule;


/**
 * 
 * ArKeywordModuleDao 구현 클래스
 *
 * @author 최성우
 * @version $Id: ArKeywordModuleDaoImpl.java 17315 2012-02-08 04:56:13Z yruyo $
 */
@Repository
public class ArKeywordModuleDaoImpl extends GenericDaoSqlmap<ArKeywordModule, ArKeywordModule> implements ArKeywordModuleDao {

	private static final String NAMESPACE = "support.abusereporting.dao.arKeywordModule.";

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public ArKeywordModule create(ArKeywordModule arKeywordModule) {
		return (ArKeywordModule) sqlInsert(NAMESPACE + "insert", arKeywordModule);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public ArKeywordModule get(ArKeywordModule arKeywordModule) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable)
	 */
	public boolean exists(ArKeywordModule arKeywordModule) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(ArKeywordModule object) {

	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable)
	 */
	public void remove(ArKeywordModule arKeywordModule) {
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.abusereporting.dao.ArKeywordModuleDao#deleteByKeyword(com.lgcns.ikep4.support.abusereporting.model.ArKeywordModule)
	 */
	public void deleteByKeyword(ArKeywordModule arKeywordModule) {
		sqlDelete(NAMESPACE + "deleteByKeyword", arKeywordModule);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.abusereporting.dao.ArKeywordModuleDao#listByKeyword(com.lgcns.ikep4.support.abusereporting.model.ArKeywordModule)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List listByKeyword(ArKeywordModule arKeywordModule) {
		return sqlSelectForList(NAMESPACE + "listByKeyword", arKeywordModule);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.abusereporting.dao.ArKeywordModuleDao#listByModuleCode(com.lgcns.ikep4.support.abusereporting.model.ArKeywordModule)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List listByModuleCode(ArKeywordModule arKeywordModule) {
		return  sqlSelectForListOfObject(NAMESPACE + "listByModuleCode", arKeywordModule);
	}


}
