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

import com.lgcns.ikep4.approval.work.dao.ApprEntrustDao;
import com.lgcns.ikep4.approval.work.model.ApprEntrust;
import com.lgcns.ikep4.approval.work.search.ApprListSearchCondition;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;


/**
 * 위임 관리 DAO 구현
 * 
 * @author jeehye
 * @version $Id: ApprEntrustDaoImpl.java 16234 2011-08-18 02:44:36Z giljae $
 */
@Repository("apprEntrustDao")
public class ApprEntrustDaoImpl extends GenericDaoSqlmap<ApprEntrust, String> implements ApprEntrustDao {

	private static final String NAMESPACE = "com.lgcns.ikep4.approval.work.dao.ApprEntrust.";

	public String create(ApprEntrust arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean exists(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public ApprEntrust get(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public void remove(String arg0) {
		// TODO Auto-generated method stub

	}

	public void update(ApprEntrust arg0) {
		// TODO Auto-generated method stub

	}

	public void entrustCreate(ApprEntrust apprEntrust) {
		this.sqlInsert(NAMESPACE + "entrustCreate", apprEntrust);
	}

	public void entrustUpdate(ApprEntrust apprEntrust) {
		this.sqlInsert(NAMESPACE + "entrustUpdate", apprEntrust);
	}

	public void entrustUpdateUsage(ApprEntrust apprEntrust) {
		this.sqlUpdate(NAMESPACE + "entrustUpdateUsage", apprEntrust);
	}

	public ApprEntrust entrustDetail(ApprEntrust apprEntrust) {
		return (ApprEntrust) this.sqlSelectForObject(NAMESPACE + "entrustDetail", apprEntrust);
	}

	public List<ApprEntrust> entrustList(ApprEntrust apprEntrust) {
		return this.sqlSelectForList(NAMESPACE + "entrustDetail", apprEntrust);
	}

	public List<ApprEntrust> listBySearchCondition(ApprListSearchCondition searchCondition) {
		return this.sqlSelectForList(NAMESPACE + "listBySearchCondition", searchCondition);
	}

	public Integer countBySearchCondition(ApprListSearchCondition searchCondition) {
		Integer count = (Integer) this.sqlSelectForObject(NAMESPACE + "countBySearchCondition", searchCondition);
		return count;
	}

	public boolean hasSignUser(Map map) {
		Integer count = (Integer) this.sqlSelectForObject(NAMESPACE + "hasSignUser", map);
		return count > 0;
	}

}
