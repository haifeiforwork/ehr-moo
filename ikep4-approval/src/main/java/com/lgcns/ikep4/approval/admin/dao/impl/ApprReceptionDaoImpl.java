/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.admin.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.approval.admin.dao.ApprReceptionDao;
import com.lgcns.ikep4.approval.admin.model.ApprReception;
import com.lgcns.ikep4.approval.work.model.ApprExam;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;


/**
 * 접수 담당자 DAO 구현
 * 
 * @author jeehye
 * @version $Id: ApprReceptionDaoImpl.java $
 */
@Repository("apprReceptionDao")
public class ApprReceptionDaoImpl extends GenericDaoSqlmap<ApprReception, String> implements ApprReceptionDao {

	private static final String NAMESPACE = "com.lgcns.ikep4.approval.admin.dao.ApprReception.";

	public String create(ApprReception arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean exists(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public ApprReception get(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public void remove(String arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void update(ApprReception arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void createApprReceptionSave(ApprReception apprReception){
		this.sqlInsert(NAMESPACE + "createApprReceptionSave", apprReception);
	}
	
	public List<ApprReception> listByReception(String groupId) {
		return this.sqlSelectForList(NAMESPACE + "listByReception",groupId);
	}

	public void deleteApprReception(String groupId){
		this.sqlDelete(NAMESPACE + "deleteApprReception", groupId);
	}
	
	public boolean existReceptionUser(Map<String, String> map) {
		Integer count = (Integer) this.sqlSelectForObject(NAMESPACE + "existReceptionUser", map);
		return count > 0;
	}
}
