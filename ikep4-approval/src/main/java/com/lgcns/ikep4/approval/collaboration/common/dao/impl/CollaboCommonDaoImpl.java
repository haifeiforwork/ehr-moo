package com.lgcns.ikep4.approval.collaboration.common.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.approval.collaboration.common.dao.CollaboCommonDao;
import com.lgcns.ikep4.approval.collaboration.common.dao.CommonCodeDao;
import com.lgcns.ikep4.approval.collaboration.common.model.CommonCode;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;

/**
 * 공통코드 ColaboCommonDao 구현체 클래스
 * 
 * @author pjh
 * @version $Id$
 */
@Repository
public class CollaboCommonDaoImpl extends GenericDaoSqlmap<CommonCode, String> implements CollaboCommonDao{
	
	/** The Constant NAMESPACE. */
	private static final String NAMESPACE = "com.lgcns.ikep4.approval.collaboration.common.dao.CollaboCommonDao.";
	

	public String create(CommonCode arg0) {
		// TODO Auto-generated method stub
		return null;
	}


	public boolean exists(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}


	public CommonCode get(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}


	public void remove(String arg0) {
		// TODO Auto-generated method stub
		
	}


	public void update(CommonCode arg0) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 메일,이름 조회
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<?> getUserMailAddrList(Map<String, Object> paramMap) throws Exception {
		
		return sqlSelectForList( NAMESPACE + "getUserMailAddrList" , paramMap);
	}
	
	/**
	 * 메일주소 조회
	 * @param empNO
	 * @return
	 * @throws Exception
	 */
	public String getUserMailAddr(String empNO) throws Exception{
		
		return (String) sqlSelectForObject( NAMESPACE + "getUserMailAddr" , empNO);
	}
}
