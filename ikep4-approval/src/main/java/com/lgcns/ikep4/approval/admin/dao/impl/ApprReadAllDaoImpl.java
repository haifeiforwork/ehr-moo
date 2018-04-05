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

import com.lgcns.ikep4.approval.admin.dao.ApprReadAllDao;
import com.lgcns.ikep4.approval.admin.model.ApprAdminConfig;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 알람 관리 DAO 구현
 * 
 * @author jeehye
 * @version $Id: ApprNoticeDaoImpl.java 16234 2011-08-18 02:44:36Z giljae $
 */
@Repository("apprReadAllDao")
public class ApprReadAllDaoImpl extends GenericDaoSqlmap<User, String>
		implements ApprReadAllDao {

	private static final String NAMESPACE = "com.lgcns.ikep4.approval.admin.dao.ApprReadAll.";

	public void readAllCreate(ApprAdminConfig apprAdminConfig) {
		this.sqlInsert(NAMESPACE + "readAllCreate", apprAdminConfig);
	}

	public void readAllDelete(String portalId) {
		this.sqlInsert(NAMESPACE + "readAllDelete", portalId);
	}

	public List<User> readAllList(String portalId) {
		return this.sqlSelectForList(NAMESPACE + "readAllList", portalId);
	}

	public boolean existReadAllAuth(String userId) {
		Integer count = (Integer) this.sqlSelectForObject(NAMESPACE + "existReadAllAuth", userId);
		return count>0;
	}
	
	public String create(User arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean exists(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public User get(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public void remove(String arg0) {
		// TODO Auto-generated method stub

	}

	public void update(User arg0) {
		// TODO Auto-generated method stub

	}
}
