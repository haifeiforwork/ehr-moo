package com.lgcns.ikep4.collpack.kms.main.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.collpack.kms.main.dao.KmsDao;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;

@Repository("kmsDao")
public class KmsDaoImpl extends GenericDaoSqlmap<Object, String> implements KmsDao {

	
	private static final String NAMESPACE = "collpack.kms.main.dao.Kms.";
	
	
	
	/*
	 * public String create(HashMap<String, String> paramMap) {
		return sqlInsert(NAMESPACE + "insertKeyword", paramMap);
	}
	 * 
	 * */

	public boolean exists(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public Object get(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public void remove(String arg0) {
		// TODO Auto-generated method stub
		
	}

	public void update(Object arg0) {
		// TODO Auto-generated method stub
		
	}

	public List listKeyword(String userId) {
		return sqlSelectForList(NAMESPACE + "listKeyword", userId);
	}
	
	public List listKeywordAll() {
		return sqlSelectForList(NAMESPACE + "listKeywordAll");
	}

	public String create(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public void insertKeyword(Map<String, String> paramMap) {
		sqlInsert(NAMESPACE + "insertKeyword", paramMap);
		
	}

	public void deleteKeyword(Map<String, String> paramMap) {
		sqlInsert(NAMESPACE + "deleteKeyword", paramMap);		
	}

}
