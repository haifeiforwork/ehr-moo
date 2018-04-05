/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.admin.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.approval.admin.dao.ApprDefLineDao;
import com.lgcns.ikep4.approval.admin.model.ApprDefLine;
import com.lgcns.ikep4.approval.admin.search.ApprDefLineSearchCondition;

/**
 * 결재선 Dao 구현
 *
 * @author 
 * @version $Id$
 */
@Repository("apprDefLineDao")
public class ApprDefLineDaoImpl extends GenericDaoSqlmap<ApprDefLine, String> implements ApprDefLineDao {

	private static final String NAMESPACE = "approval.admin.dao.ApprDefLine.";

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public String create(ApprDefLine object) {
		sqlInsert(NAMESPACE + "create", object);
		return object.getDefLineId();
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.approval.admin.dao.ApprDefLineDao#listBySearchCondition(com.lgcns.ikep4.approval.admin.search.ApprDefLineSearchCondition)
	 */
	public List<ApprDefLine> listBySearchCondition(ApprDefLineSearchCondition searchCondition) {
		return this.sqlSelectForList(NAMESPACE + "listBySearchCondition", searchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.approval.admin.dao.ApprDefLineDao#countBySearchCondition(com.lgcns.ikep4.approval.admin.search.ApprDefLineSearchCondition)
	 */
	public Integer countBySearchCondition(ApprDefLineSearchCondition searchCondition) {
		return (Integer) this.sqlSelectForObject(NAMESPACE + "countBySearchCondition", searchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.approval.admin.dao.ApprDefLineDao#listApprDefLine()
	 */
	public List<ApprDefLine> listApprDefLine() {
		return this.sqlSelectForList(NAMESPACE + "listApprDefLine");
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.approval.admin.dao.ApprDefLineDao#listApprDefLine(java.lang.String)
	 */
	public List<ApprDefLine> listApprDefLineType(ApprDefLine apprDefLine) {
		return this.sqlSelectForList(NAMESPACE + "listApprDefLineType",apprDefLine);
	}

	/* 
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable)
	 */
	public boolean exists(String defLineId) {
		Integer count = (Integer) sqlSelectForObject(NAMESPACE + "exists", defLineId);
		return count > 0;
	}

	/* 
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public ApprDefLine get(String defLineId) {
		return (ApprDefLine) sqlSelectForObject(NAMESPACE + "get", defLineId);
	}


	/* 
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(ApprDefLine object) {
		sqlUpdate(NAMESPACE + "update", object);
	}

	/* 
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable)
	 */
	public void remove(String defLineId) {
		sqlDelete(NAMESPACE + "delete", defLineId);
	}

}
