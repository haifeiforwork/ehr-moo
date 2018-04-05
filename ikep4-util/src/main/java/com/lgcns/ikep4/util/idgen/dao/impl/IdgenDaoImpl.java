package com.lgcns.ikep4.util.idgen.dao.impl;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.util.idgen.dao.IdgenDao;


/**
 * ID 생성 DAO
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: IdgenDaoImpl.java 16489 2011-09-06 01:41:09Z giljae $
 */
@Repository
public class IdgenDaoImpl extends GenericDaoSqlmap<String, String> implements IdgenDao {

	/**
	 * 아이디생성 쿼리 네임스페이스
	 */
	private static final String NAMESPACE = "util.idgen.";

	public String get(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean exists(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	public String create(String object) {
		// TODO Auto-generated method stub
		return null;
	}

	public void update(String object) {
		// TODO Auto-generated method stub

	}

	public void remove(String id) {
		// TODO Auto-generated method stub

	}

	/**
	 * 아이디 생성
	 */
	public String getNextId() {
		return (String) sqlSelectForObject(NAMESPACE + "getNextId", "");
	}

}
