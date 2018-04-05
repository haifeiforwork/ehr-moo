/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.wfapproval.admin.dao.impl;

import org.springframework.stereotype.Repository;

import com.googlecode.ehcache.annotations.TriggersRemove;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.wfapproval.admin.dao.ApFormTplDao;
import com.lgcns.ikep4.wfapproval.admin.model.ApForm;
import com.lgcns.ikep4.wfapproval.admin.model.ApFormTpl;

/**
 * 결재 양식관리 Template DAO 구현
 *
 * @author 박희진(neoheejin@naver.com)
 * @version $Id: ApFormTplDaoImpl.java 16234 2011-08-18 02:44:36Z giljae $
 */
@Repository("apFormTplDao")
public class ApFormTplDaoImpl extends GenericDaoSqlmap<ApFormTpl, String> implements ApFormTplDao {
	
	private static final String NAMESPACE = "com.lgcns.ikep4.wfapproval.admin.dao.ApFormTpl.";

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public ApFormTpl get(String formId) {
		return (ApFormTpl) sqlSelectForObject(NAMESPACE+"select", formId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable)
	 */
	public boolean exists(String formId) {
		Integer count = (Integer) sqlSelectForObject(NAMESPACE+"selectCount", formId);
		
		if (count > 0)
			return true;

		return false;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public String create(ApFormTpl apformtpl) {
		return (String) sqlInsert(NAMESPACE+"create", apformtpl);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(ApFormTpl apformtpl) {
		sqlUpdate(NAMESPACE+"update", apformtpl);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable)
	 */
	public void remove(String formId) {
		sqlDelete(NAMESPACE+"delete", formId);
	}

}
