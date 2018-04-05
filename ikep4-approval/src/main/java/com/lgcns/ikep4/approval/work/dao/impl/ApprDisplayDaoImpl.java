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

import com.lgcns.ikep4.approval.work.dao.ApprDisplayDao;
import com.lgcns.ikep4.approval.work.model.ApprDisplay;
import com.lgcns.ikep4.approval.work.search.ApprDisplaySearchCondition;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;


/**
 * 공람지정 DAO 구현
 * 
 * @author jeehye
 * @version $Id: ApprDisplayDaoImpl.java 16234 2011-08-18 02:44:36Z giljae $
 */
@Repository("apprDisplayDao")
public class ApprDisplayDaoImpl extends GenericDaoSqlmap<ApprDisplay, String> implements ApprDisplayDao {

	private static final String NAMESPACE = "com.lgcns.ikep4.approval.work.dao.ApprDisplay.";

	public String create(ApprDisplay arg0) { 
		// TODO Auto-generated method stub
		return null;
	}

	public boolean exists(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public ApprDisplay get(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public void remove(String arg0) {
		// TODO Auto-generated method stub
		
	}

	public void update(ApprDisplay arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void createApprDisplay(ApprDisplay apprDisplay){
		this.sqlInsert(NAMESPACE + "createApprDisplay", apprDisplay);
	}
	
	public void createApprDisplaySub(ApprDisplay apprDisplay){
		this.sqlInsert(NAMESPACE + "createApprDisplaySub", apprDisplay);
	}
	
	public void creatDisplayUserIdForGroup(Map map){
		
		this.sqlInsert(NAMESPACE + "creatDisplayUserIdForGroup", map);
	}
	
	public List<ApprDisplay> getUserIdByGroup(Map map) {
		return this.sqlSelectForList(NAMESPACE + "getUserIdByGroup", map);
	}

	public List<ApprDisplay> getUserIdBySubGroupCntList(Map map) {
		return this.sqlSelectForList(NAMESPACE + "getUserIdBySubGroupCntList", map);
	}

	public List<ApprDisplay> getUserIdByParentGroup(Map map) {
		return this.sqlSelectForList(NAMESPACE + "getUserIdByParentGroup", map);
	}
	
	/**
	 * 공람지정 목록
	 */
	public List<ApprDisplay> listBySearchCondition(ApprDisplaySearchCondition searchCondition) {
		return this.sqlSelectForList(NAMESPACE + "listBySearchCondition", searchCondition);
	}

	/**
	 * 공람지정 목록 갯수
	 */
	public Integer countBySearchCondition(ApprDisplaySearchCondition searchCondition) {
		Integer count = (Integer) this.sqlSelectForObject(NAMESPACE + "countBySearchCondition", searchCondition);
		return count;
	}
	
	public void deleteApprDisplaySub(Map map){
		this.sqlDelete(NAMESPACE + "deleteApprDisplaySub", map);
	}
	
	public void deleteApprDisplay(Map map){
		this.sqlDelete(NAMESPACE + "deleteApprDisplay", map);
	}
	
	public Integer getApprDisplaySubCount(Map map) {
		Integer count = (Integer) this.sqlSelectForObject(NAMESPACE + "getApprDisplaySubCount", map);
		return count;
	}
	
	/**
	 * 공람지정 목록
	 */
	public List<ApprDisplay> listByDisplaySearchCondition(ApprDisplaySearchCondition searchCondition) {
		return this.sqlSelectForList(NAMESPACE + "listByDisplaySearchCondition", searchCondition);
	}

	/**
	 * 공람지정 목록 갯수
	 */
	public Integer countByDisplaySearchCondition(ApprDisplaySearchCondition searchCondition) {
		Integer count = (Integer) this.sqlSelectForObject(NAMESPACE + "countByDisplaySearchCondition", searchCondition);
		return count;
	}
	
	public String getApprDisplayId(Map map) {
		String displayId = (String) this.sqlSelectForObject(NAMESPACE + "getApprDisplayId", map);
		return displayId;
	}
	
	public void updateApprDisplaySub(Map map){
		this.sqlUpdate(NAMESPACE + "updateApprDisplaySub", map);
	}
	
	public Integer countByDisplayUserStatus(String apprId) {
		Integer count = (Integer) this.sqlSelectForObject(NAMESPACE + "countByDisplayUserStatus", apprId);
		return count;
	}
	
	public boolean existDisplayDoc(Map map) {
		Integer count = (Integer) this.sqlSelectForObject(NAMESPACE + "existDisplayDoc", map);
		return count>0;
	}
	
	public boolean existDisplayUser(Map map) {
		Integer count = (Integer) this.sqlSelectForObject(NAMESPACE + "existDisplayUser", map);
		return count>0;
	}
}
