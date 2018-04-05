/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.work.dao.impl;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.approval.work.dao.ApprUserLineSubDao;
import com.lgcns.ikep4.approval.work.model.ApprUserLineSub;

/**
 * 결재선 Flow Dao 구현
 *
 * @author 
 * @version $Id$
 */
@Repository("apprUserLineSubDao")
public class ApprUserLineSubDaoImpl extends GenericDaoSqlmap<ApprUserLineSub, String> implements ApprUserLineSubDao {

	
	private static final String NAMESPACE = "approval.work.dao.ApprUserLineSub.";

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public String create(ApprUserLineSub object) {
		sqlInsert(NAMESPACE + "create", object);
		return object.getUserLineId();
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable)
	 */
	public boolean exists(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public ApprUserLineSub get(String arg0) {
		// TODO Auto-generated method stub
		return null;
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
	public void update(ApprUserLineSub arg0) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.approval.work.dao.ApprUserLineSubDao#listApprUserLineSub(java.lang.String)
	 */
	public List<ApprUserLineSub> listApprUserLineSub(String userLineId) {
		return this.sqlSelectForList(NAMESPACE + "listApprUserLineSub",userLineId);
	}


}
