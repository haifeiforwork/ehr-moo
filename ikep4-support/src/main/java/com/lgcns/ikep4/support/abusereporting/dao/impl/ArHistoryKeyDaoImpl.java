package com.lgcns.ikep4.support.abusereporting.dao.impl;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.abusereporting.dao.ArHistoryKeyDao;
import com.lgcns.ikep4.support.abusereporting.model.ArHistoryKey;


/**
 * 
 * ArHistoryKeyDao 구현 클래스
 *
 * @author 최성우
 * @version $Id: ArHistoryKeyDaoImpl.java 16316 2011-08-22 00:26:53Z giljae $
 */
@Repository
public class ArHistoryKeyDaoImpl extends GenericDaoSqlmap<ArHistoryKey, String> implements ArHistoryKeyDao {

	private static final String NAMESPACE = "support.abusereporting.dao.arHistoryKey.";

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public String create(ArHistoryKey arHistoryKey) {
		return (String) sqlInsert(NAMESPACE + "insert", arHistoryKey);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public ArHistoryKey get(String arHistoryKey) {
		return null;
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
	public void update(ArHistoryKey object) {

	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable)
	 */
	public void remove(String id) {

	}

}
