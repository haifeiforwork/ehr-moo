/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.admin.dao.impl;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.approval.admin.dao.ApprAdminConfigDao;
import com.lgcns.ikep4.approval.admin.model.ApprAdminConfig;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;


/**
 * 알람 관리 DAO 구현
 * 
 * @author jeehye
 * @version $Id: ApprNoticeDaoImpl.java 16234 2011-08-18 02:44:36Z giljae $
 */
@Repository("apprAdminConfigDao")
public class ApprAdminConfigDaoImpl extends GenericDaoSqlmap<ApprAdminConfig, String> implements ApprAdminConfigDao {

	private static final String NAMESPACE = "com.lgcns.ikep4.approval.admin.dao.ApprAdminConfig.";

	public String create(ApprAdminConfig arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean exists(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public ApprAdminConfig get(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public void remove(String arg0) {
		// TODO Auto-generated method stub

	}

	public void update(ApprAdminConfig arg0) {
		// TODO Auto-generated method stub

	}

	public void adminConfigCreate(ApprAdminConfig apprAdminConfig) {
		this.sqlInsert(NAMESPACE + "adminConfigCreate", apprAdminConfig);
	}

	public void adminConfigUpdate(ApprAdminConfig apprAdminConfig) {
		this.sqlInsert(NAMESPACE + "adminConfigUpdate", apprAdminConfig);
	}

	public ApprAdminConfig adminConfigDetail(String portalId) {
		return (ApprAdminConfig) this.sqlSelectForObject(NAMESPACE + "adminConfigDetail", portalId);
	}
}
