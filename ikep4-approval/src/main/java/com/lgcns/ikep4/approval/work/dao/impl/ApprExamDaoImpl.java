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

import com.lgcns.ikep4.approval.work.dao.ApprExamDao;
import com.lgcns.ikep4.approval.work.model.ApprExam;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;


/**
 * 검토요청 DAO 구현
 * 
 * @author jeehye
 * @version $Id: ApprExamDaoImpl.java 16234 2011-08-18 02:44:36Z giljae $
 */
@Repository("apprExamDao")
public class ApprExamDaoImpl extends GenericDaoSqlmap<ApprExam, String> implements ApprExamDao {

	private static final String NAMESPACE = "com.lgcns.ikep4.approval.work.dao.ApprExam.";

	public String create(ApprExam arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean exists(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public ApprExam get(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public void remove(String arg0) {
		// TODO Auto-generated method stub
		
	}

	public void update(ApprExam arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void apprExamCreate(ApprExam apprExam){
		this.sqlInsert(NAMESPACE + "entrustCreate", apprExam);
	}
	
	public List<ApprExam> listApprExamInfo(ApprExam apprExam) {
		return this.sqlSelectForList(NAMESPACE + "listApprExamInfo",apprExam);
	}
	
	public String examFirstReqId(String apprId) {
		String examFirstReqId = (String)this.sqlSelectForObject(NAMESPACE + "examFirstReqId",apprId);
		return examFirstReqId;
	}
	
	public void updateApprExamInfoSave(ApprExam apprExam){
		this.sqlInsert(NAMESPACE + "updateApprExamInfoSave", apprExam);
	}
	
	public boolean existExamUser(Map map) {
		Integer count = (Integer) this.sqlSelectForObject(NAMESPACE + "existExamUser", map);
		return count > 0;
	}
	
	public boolean existExamIsRequest(Map map) {
		Integer count = (Integer) this.sqlSelectForObject(NAMESPACE + "existExamIsRequest", map);
		return count > 0;
	}
	
	public boolean existExamStatus(Map map) {
		Integer count = (Integer) this.sqlSelectForObject(NAMESPACE + "existExamStatus", map);
		return count > 0;
	}
	public void remove(Map<String, String> map){
		sqlDelete(NAMESPACE + "delete", map);
	}
	
}
