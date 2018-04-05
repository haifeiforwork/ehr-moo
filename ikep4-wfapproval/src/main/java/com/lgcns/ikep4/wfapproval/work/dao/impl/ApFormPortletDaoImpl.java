/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.wfapproval.work.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.wfapproval.work.dao.ApFormPortletDao;
import com.lgcns.ikep4.wfapproval.work.model.ApFormPortlet;


/**
 * 양식 포틀릿정보 DAO 구현
 * 
 * @author 박희진(neoheejin@naver.com)
 * @version $Id: ApFormPortletDaoImpl.java 16234 2011-08-18 02:44:36Z giljae $
 */
@Repository("apFormPortletDao")
public class ApFormPortletDaoImpl extends GenericDaoSqlmap<ApFormPortlet, String> implements ApFormPortletDao {

	private static final String NAMESPACE = "com.lgcns.ikep4.wfapproval.work.dao.ApFormPortlet.";
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.wfapproval.work.dao.ApFormPortletDao#selectList(com.lgcns.ikep4.wfapproval.work.model.ApFormPortlet)
	 */
	public List<ApFormPortlet> listApFormPortlet(ApFormPortlet apFormPortlet) {
		List<ApFormPortlet> apFormPortletList = (List<ApFormPortlet>) this.sqlSelectForList(NAMESPACE + "ListApFormPortlet", apFormPortlet);

		return apFormPortletList;
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.wfapproval.work.dao.ApFormPortletDao#exists(java.lang.String, java.lang.String)
	 */
	public boolean exists(ApFormPortlet apFormPortlet) {
		Integer count = (Integer) sqlSelectForObject(NAMESPACE + "selectCount", apFormPortlet);

		if (count > 0)
			return true;

		return false;
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public String create(ApFormPortlet apFormPortlet) {
		return (String) sqlInsert(NAMESPACE + "create", apFormPortlet);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(ApFormPortlet apFormPortlet) {
		sqlUpdate(NAMESPACE + "update", apFormPortlet);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public ApFormPortlet get(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable)
	 */
	public boolean exists(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable)
	 */
	public void remove(String id) {
		// TODO Auto-generated method stub
		
	}
	
	
}
