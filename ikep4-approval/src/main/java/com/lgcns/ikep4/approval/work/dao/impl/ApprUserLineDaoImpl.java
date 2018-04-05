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

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.approval.work.dao.ApprUserLineDao;
import com.lgcns.ikep4.approval.work.model.ApprUserLine;

/**
 * 결재선 Dao 구현
 *
 * @author 
 * @version $Id$
 */
@Repository("apprUserLineDao")
public class ApprUserLineDaoImpl extends GenericDaoSqlmap<ApprUserLine, String> implements ApprUserLineDao {

	private static final String NAMESPACE = "approval.work.dao.ApprUserLine.";

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public String create(ApprUserLine object) {
		sqlInsert(NAMESPACE + "create", object);
		return object.getUserLineId();
	}
	
	/*public String createDefLineInfo(IntDefLine object) {
		sqlInsert(NAMESPACE + "createDefLineInfo", object);
		return object.getInfoId();
	}*/
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable)
	 */
	public boolean exists(String userLineId) {
		Integer count = (Integer) sqlSelectForObject(NAMESPACE + "exists", userLineId);
		return count > 0;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public ApprUserLine get(String userLineId) {
		return (ApprUserLine) sqlSelectForObject(NAMESPACE + "get", userLineId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable)
	 */
	public void remove(String userLineId) {
		sqlDelete(NAMESPACE + "delete", userLineId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(ApprUserLine object) {
		sqlUpdate(NAMESPACE + "update", object);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void updateUsage(ApprUserLine object) {
		sqlUpdate(NAMESPACE + "updateUsage", object);
	}	


	public List<ApprUserLine> listApprUserLineAll(String userId) {
		return this.sqlSelectForList(NAMESPACE + "listApprUserLineAll",userId);
	}

	public List<ApprUserLine> listApprUserLine(Map<String, String> map) {
		return this.sqlSelectForList(NAMESPACE + "listApprUserLine",map);
	}

}
