package com.lgcns.ikep4.approval.collaboration.npd.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.approval.collaboration.npd.dao.DevReqShareDeptDao;
import com.lgcns.ikep4.approval.collaboration.npd.model.DevReqShareDept;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;

/**
 * 신제품 개발 NewProductDevDao 구현체 클래스
 * 
 * @author pjh
 * @version $Id$
 */
@Repository
public class DevReqShareDeptDaoImpl extends GenericDaoSqlmap<DevReqShareDept, String> implements DevReqShareDeptDao{
	
	/** The Constant NAMESPACE. */
	private static final String NAMESPACE = "com.lgcns.ikep4.approval.collaboration.npd.dao.DevReqShareDept.";
	

	public String create(DevReqShareDept arg0) {
		// TODO Auto-generated method stub
		return null;
	}


	public boolean exists(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}


	public DevReqShareDept get(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}


	public void remove(String arg0) {
		// TODO Auto-generated method stub
		
	}


	public void update(DevReqShareDept arg0) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 참조부서 목록조회
	 * @param DevReqShareDept
	 * @return
	 * @throws Exception
	 */
	public List<DevReqShareDept> getDevReqShareDept(  String mgntNo) throws Exception {
		
		return sqlSelectForList( NAMESPACE + "getDevReqShareDept", mgntNo);
	}

	/**
	 * 참조부서 등록
	 * @param DevReqShareDept
	 * @throws Exception
	 */
	public void insertDevReqShareDept( DevReqShareDept DevReqShareDept) throws Exception {
		
		sqlInsert( NAMESPACE + "insertDevReqShareDept", DevReqShareDept);
	}

	/**
	 * 참조부서 삭제
	 * @param DevReqShareDept
	 * @throws Exception
	 */
	public void deleteDevReqShareDept( String mgntNo) throws Exception {
		
		sqlDelete( NAMESPACE + "deleteDevReqShareDept", mgntNo);
	}
	
}
