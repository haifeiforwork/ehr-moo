/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.admin.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.approval.admin.dao.ApprReadGroupDao;
import com.lgcns.ikep4.approval.admin.model.ApprReadGroup;
import com.lgcns.ikep4.approval.admin.search.ApprReadGroupSearchCondition;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;

/**
 * 결재선 Dao 구현
 *
 * @author 
 * @version $Id$
 */
@Repository("apprReadGroupDao")
public class ApprReadGroupDaoImpl extends GenericDaoSqlmap<ApprReadGroup, String> implements ApprReadGroupDao {

	private static final String NAMESPACE = "approval.admin.dao.ApprReadGroup.";

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public String create(ApprReadGroup object) {
		sqlInsert(NAMESPACE + "create", object);
		return object.getUserId();
	}
	
	/*public String createDefLineInfo(IntDefLine object) {
		sqlInsert(NAMESPACE + "createDefLineInfo", object);
		return object.getInfoId();
	}*/
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable)
	 */
	public boolean exists(String defLineId) {
		Integer count = (Integer) sqlSelectForObject(NAMESPACE + "exists", defLineId);
		return count > 0;
	}
	
	public void remove(String arg0) {
		// TODO Auto-generated method stub
		
	}

	public void update(ApprReadGroup arg0) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public ApprReadGroup get(String userId) {
		return (ApprReadGroup) sqlSelectForObject(NAMESPACE + "get", userId);
	}

	/**
	 * 검색조건에 의한 Default 부서 결재함 열람권한 목록
	 */
	public List<ApprReadGroup> listBySearchCondition(ApprReadGroupSearchCondition searchCondition) {
		return this.sqlSelectForList(NAMESPACE + "listBySearchCondition", searchCondition);
	}

	/**
	 * 검색조건에 의한 Default 부서 결재함 열람권한 목록 갯수
	 */
	public Integer countBySearchCondition(ApprReadGroupSearchCondition searchCondition) {
		return (Integer) this.sqlSelectForObject(NAMESPACE + "countBySearchCondition", searchCondition);
	}
	
	/**
	 * 부서 결재함 열람권한 생성
	 * @param 	Map
	 * @return 	nothing
	 */
	public void createApprReadGroup(ApprReadGroup apprReadGroup){
		sqlInsert(NAMESPACE + "createApprReadGroup", apprReadGroup);
	}
	
	/**
	 * 부서 목록
	 * @param 	userId
	 * @return 	List<ApprReadGroup>
	 */
	public List<ApprReadGroup> getGroupList(Map map){
		return this.sqlSelectForList(NAMESPACE + "getGroupList", map);
	}
	
	/**
	 * 부서 결재함 열람권한 정보
	 * @param 	userId
	 * @return 	ApprReadGroup
	 */
	public ApprReadGroup getBasicInfo(Map map){
		return (ApprReadGroup) this.sqlSelectForObject(NAMESPACE + "getBasicInfo", map);
	}
	
	/**
	 * 부서 결재함 열람권한 정보 삭제
	 * @param 	userId
	 * @return 	ApprReadGroup
	 */
	public void deleteApprReadGroup(Map map){
		sqlDelete(NAMESPACE + "deleteApprReadGroup", map);
	}
	
	/**
	 * 부서 결재함 열람권한 목록에서 삭제
	 * @param 	userId
	 * @return 	ApprReadGroup
	 */
	public void deleteApprReadGroupAjax(Map map){
		sqlDelete(NAMESPACE + "deleteApprReadGroupAjax", map);
	}

}
