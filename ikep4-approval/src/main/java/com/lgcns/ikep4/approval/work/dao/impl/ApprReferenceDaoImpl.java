/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.work.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.approval.work.dao.ApprReferenceDao;
import com.lgcns.ikep4.approval.work.model.ApprReference;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;


/**
 * 참조자 의견 DAO 구현
 * 
 * @author 
 * @version $Id: ApprReferenceDaoImpl.java 16234 2011-08-18 02:44:36Z giljae $
 */
@Repository("apprReferenceDao")
public class ApprReferenceDaoImpl extends GenericDaoSqlmap<ApprReference, String> implements ApprReferenceDao {

	private static final String NAMESPACE = "com.lgcns.ikep4.approval.work.dao.ApprReference.";

	public String create(ApprReference arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean exists(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public ApprReference get(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	public ApprReference get(Map<String, String> map) {
		return (ApprReference) sqlSelectForObject(NAMESPACE + "get", map);		
	}	
	
	public void remove(String arg0) {
		// TODO Auto-generated method stub
		
	}

	public void update(ApprReference object) {
		sqlUpdate(NAMESPACE + "update", object);		
	}
	
	public void updateRead(Map<String, String> map) {
		sqlUpdate(NAMESPACE + "updateRead", map);		
	}	
	
	public void remove(Map<String, String> map) {
		sqlDelete(NAMESPACE + "delete", map);		
	}
	
	public List<ApprReference>	list(String	apprId) {
		return this.sqlSelectForList(NAMESPACE + "list",apprId);
	}

	public boolean exists(Map<String, String> map) {
		Integer count = (Integer) sqlSelectForObject(NAMESPACE + "exists", map);
		return count > 0;
	}
}
