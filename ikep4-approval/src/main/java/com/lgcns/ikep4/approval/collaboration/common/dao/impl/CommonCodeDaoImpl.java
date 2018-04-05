package com.lgcns.ikep4.approval.collaboration.common.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.approval.collaboration.common.dao.CommonCodeDao;
import com.lgcns.ikep4.approval.collaboration.common.model.CommonCode;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;

/**
 * 공통코드 CommonCodeDao 구현체 클래스
 * 
 * @author pjh
 * @version $Id$
 */
@Repository
public class CommonCodeDaoImpl extends GenericDaoSqlmap<CommonCode, String> implements CommonCodeDao{
	
	/** The Constant NAMESPACE. */
	private static final String NAMESPACE = "com.lgcns.ikep4.approval.collaboration.common.dao.CommonCode.";
	
	/**
	 * 공통코드 목록 조회
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<CommonCode> getCommonCodeList( Map<String, String> paramMap) throws Exception {
		
		return sqlSelectForList( NAMESPACE + "getCommonCodeList" , paramMap);
	}
	
	/**
	 * 해당 사원 팀장 조회
	 * @param string
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getTeamLeaderInfo( String empNo) throws Exception {
		return (Map<String, Object>) sqlSelectForObject ( NAMESPACE + "getTeamLeaderInfo" , empNo);
	}


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
}
