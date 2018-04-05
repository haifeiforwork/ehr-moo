/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.wfapproval.admin.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.wfapproval.admin.dao.ApCodeDao;
import com.lgcns.ikep4.wfapproval.admin.model.ApCode;


/**
 * 결재 코드관리 DAO 구현
 * 
 * @author 박희진(neoheejin@naver.com)
 * @version $Id: ApCodeDaoImpl.java 16234 2011-08-18 02:44:36Z giljae $
 */
@Repository("apCodeDao")
public class ApCodeDaoImpl extends GenericDaoSqlmap<ApCode, String> implements ApCodeDao {

	private static final String NAMESPACE = "com.lgcns.ikep4.wfapproval.admin.dao.ApCode.";

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public ApCode get(String codeId) {
		return (ApCode)sqlSelectForObject(NAMESPACE + "select", codeId);
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
	public String create(ApCode apCode) {
		return (String) sqlInsert(NAMESPACE + "create", apCode);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(ApCode apCode) {
		sqlUpdate(NAMESPACE + "update", apCode);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.wfapproval.admin.dao.ApCodeDao#updateApCodeOrder(com.lgcns.ikep4.wfapproval.admin.model.ApCode)
	 */
	public void updateApCodeOrder(ApCode apCode) {
		sqlUpdate(NAMESPACE + "updateApCodeOrder", apCode);
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
	 * @see com.lgcns.ikep4.wfapproval.admin.dao.ApCodeDao#listRootApCode(int)
	 */
	public List<ApCode> listRootApCode(String sLevel) {
		return sqlSelectForList(NAMESPACE + "listRootApCode", sLevel);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.wfapproval.admin.dao.ApCodeDao#listGroupApCode(java.lang
	 * .String)
	 */
	public List<ApCode> listGroupApCode(String codeId) {
		return sqlSelectForList(NAMESPACE + "listGroupApCode", codeId);
	}
	
	public List<ApCode> listGroupApCodeByValueKr(String codeValue) {
		return sqlSelectForList(NAMESPACE + "listGroupApCodeByValueKr", codeValue);
	}
	
	public List<ApCode> listGroupApCodeByValueEn(String codeValue) {
		return sqlSelectForList(NAMESPACE + "listGroupApCodeByValueEn", codeValue);
	}
}
