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

import com.lgcns.ikep4.approval.admin.dao.ApprCodeDao;
import com.lgcns.ikep4.approval.admin.model.ApprCode;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;


/**
 * 결재 코드관리 DAO 구현
 * 
 * @author 박희진(neoheejin@naver.com)
 * @version $Id: ApprCodeDaoImpl.java 16234 2011-08-18 02:44:36Z giljae $
 */
@Repository("apprCodeDao")
public class ApprCodeDaoImpl extends GenericDaoSqlmap<ApprCode, String> implements ApprCodeDao {

	private static final String NAMESPACE = "com.lgcns.ikep4.approval.admin.dao.ApprCode.";

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public ApprCode get(String codeId) {
		return (ApprCode)sqlSelectForObject(NAMESPACE + "select", codeId);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable
	 * )
	 */
	public boolean exists(String codeId) {
		Integer count = (Integer) sqlSelectForObject(NAMESPACE + "selectCount", codeId);

		if (count > 0)
			return true;

		return false;
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable
	 * )
	 */
	public boolean existsChild(String parentCodeId) {
		Integer count = (Integer) sqlSelectForObject(NAMESPACE + "selectChildCount", parentCodeId);

		if (count > 0)
			return true;

		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public String create(ApprCode apprCode) {
		return (String) sqlInsert(NAMESPACE + "create", apprCode);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(ApprCode apprCode) {
		sqlUpdate(NAMESPACE + "update", apprCode);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.approval.admin.dao.ApprCodeDao#updateApprCodeOrder(com.lgcns.ikep4.approval.admin.model.ApprCode)
	 */
	public void updateApprCodeOrder(ApprCode apprCode) {
		sqlUpdate(NAMESPACE + "updateApprCodeOrder", apprCode);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable
	 * )
	 */
	public void remove(String codeId) {
		sqlDelete(NAMESPACE + "delete", codeId);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.approval.admin.dao.ApprCodeDao#listRootApprCode(int)
	 */
	public List<ApprCode> listRootApprCode(Map<String, String> map) {
		return sqlSelectForList(NAMESPACE + "listRootApprCode", map);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.approval.admin.dao.ApprCodeDao#listGroupApprCode(java.lang
	 * .String)
	 */
	
	public List<ApprCode> listGroupApprCode(String codeId) {
		return sqlSelectForList(NAMESPACE + "listGroupApprCode", codeId);
	}
	
	public List<ApprCode> listGroupApprCodeByValueKr(String codeValue) {
		return sqlSelectForList(NAMESPACE + "listGroupApprCodeByValueKr", codeValue);
	}
	
	public List<ApprCode> listGroupApprCodeByValueEn(String codeValue) {
		return sqlSelectForList(NAMESPACE + "listGroupApprCodeByValueEn", codeValue);
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable
	 * )
	 */
	public String getCodeIdByCodeValue(Map<String, Object> map) {
		return  (String) sqlSelectForObject(NAMESPACE + "getCodeIdByCodeValue", map);
	}
	
	public List<ApprCode> getApprCodeList(Map<String, Object> map) {
		return sqlSelectForList(NAMESPACE + "getApprCodeList", map);
	}
}
