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
import com.lgcns.ikep4.approval.admin.dao.ApprDefLineInfoDao;
import com.lgcns.ikep4.approval.admin.model.ApprDefLineInfo;

/**
 * 결재선 Flow Dao 구현
 *
 * @author 
 * @version $Id$
 */
@Repository("apprDefLineInfoDao")
public class ApprDefLineInfoDaoImpl extends GenericDaoSqlmap<ApprDefLineInfo, String> implements ApprDefLineInfoDao {

	
	private static final String NAMESPACE = "approval.admin.dao.ApprDefLineInfo.";
	
	/* 
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public String create(ApprDefLineInfo object) {
		sqlInsert(NAMESPACE + "create", object);
		return object.getInfoId();
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.uapproval.admin.dao.IntDefLineInfoDao#getDefLineList(com.lgcns.ikep4.uapproval.admin.search.IntDefLineSearchCondition)
	 */
	public List<ApprDefLineInfo> getDefLineInfoList(String defLineId) {
		return sqlSelectForList(NAMESPACE + "getDefLineInfoList",defLineId);
	}
	
	/* 
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable)
	 */
	public boolean exists(String infoId) {
		Integer count = (Integer) sqlSelectForObject(NAMESPACE + "exists", infoId);
		return count > 0;
	}

	/* 
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public ApprDefLineInfo get(String infoId) {
		return (ApprDefLineInfo) sqlSelectForObject(NAMESPACE + "get", infoId);
	}

	/* 
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(ApprDefLineInfo object) {
		//sqlUpdate(NAMESPACE + "update", object);
	}

	/* 
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable)
	 */
	public void remove(String infoId) {
		sqlDelete(NAMESPACE + "delete", infoId);

	}

}
