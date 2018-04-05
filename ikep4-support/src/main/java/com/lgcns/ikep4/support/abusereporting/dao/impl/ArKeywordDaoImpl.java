package com.lgcns.ikep4.support.abusereporting.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.abusereporting.dao.ArKeywordDao;
import com.lgcns.ikep4.support.abusereporting.model.ArKeyword;
import com.lgcns.ikep4.support.abusereporting.search.ArAbuseSearchCondition;


/**
 * 
 * ArKeywordDao 구현 클래스
 *
 * @author 최성우
 * @version $Id: ArKeywordDaoImpl.java 17315 2012-02-08 04:56:13Z yruyo $
 */
@Repository
public class ArKeywordDaoImpl extends GenericDaoSqlmap<ArKeyword, ArKeyword> implements ArKeywordDao {

	private static final String NAMESPACE = "support.abusereporting.dao.arKeyword.";

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public ArKeyword create(ArKeyword arKeyword) {
		return (ArKeyword) sqlInsert(NAMESPACE + "insert", arKeyword);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public ArKeyword get(ArKeyword arKeyword) {
		return (ArKeyword) sqlSelectForObject(NAMESPACE + "select", arKeyword);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable)
	 */
	public boolean exists(ArKeyword arKeyword) {
		Integer count = (Integer) sqlSelectForObject(NAMESPACE + "selectCount", arKeyword);
		if (count > 0){
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(ArKeyword arKeyword) {

	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable)
	 */
	public void remove(ArKeyword arKeyword) {
		sqlDelete(NAMESPACE + "delete", arKeyword);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.abusereporting.dao.ArKeywordDao#list(com.lgcns.ikep4.support.abusereporting.search.ArAbuseSearchCondition)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List list(ArAbuseSearchCondition arAbuseSearchCondition) {
		return sqlSelectForListOfObject(NAMESPACE + "list", arAbuseSearchCondition);
	}

}
