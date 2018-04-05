/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.security.acl.dao.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.security.acl.dao.ACLCommonDao;
import com.lgcns.ikep4.support.user.code.model.JobClass;
import com.lgcns.ikep4.support.user.code.model.JobDuty;
import com.lgcns.ikep4.support.user.group.model.Group;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.role.model.Role;


/**
 * 권한별 상세정보
 * 
 * @author 주길재
 * @version $Id: ACLCommonDaoImpl.java 16258 2011-08-18 05:37:22Z giljae $
 */
@Repository(value = "aclCommonDao")
public class ACLCommonDaoImpl extends GenericDaoSqlmap<Object, String> implements ACLCommonDao {
	// 상수 정의
	interface Constants {
		final String NAMESPACE = "support.user.security.acl.dao.ACLCommon.";
	}

	/**
	 * 유저권한 상세정보 가져오기
	 * 
	 * @param assignUserIdList
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<User> listUserDetailPermission(List<String> assignUserIdList, String portalId) {
		List<User> userDetailPermission = null;

		Map<String, Object> map = new HashMap<String, Object>(); 
		map.put("assignUserIdList", assignUserIdList);
		map.put("portalId", portalId);
		
		try {
			userDetailPermission = getSqlMapClientTemplate().getSqlMapClient().queryForList(
					Constants.NAMESPACE + "listUserDetailPermission", map);
		} catch (SQLException sqlException) {
			// TODO: Exception 작성 필요
		}
		return userDetailPermission;
	}

	/**
	 * 그룹권한 상세정보 가져오기
	 * 
	 * @param assignUserIdList
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Group> listGroupDetailPermission(List<String> groupIdPermissionList, String portalId) {
		List<Group> groupDetailPermission = null;
		
		Map<String, Object> map = new HashMap<String, Object>(); 
		map.put("groupIdPermissionList", groupIdPermissionList);
		map.put("portalId", portalId);

		try {
			groupDetailPermission = getSqlMapClientTemplate().getSqlMapClient().queryForList(
					Constants.NAMESPACE + "listGroupDetailPermission", map);
		} catch (SQLException sqlException) {
			// TODO: Exception 작성 필요
		}
		return groupDetailPermission;
	}

	/**
	 * 역할권한 상세정보 가져오기
	 * 
	 * @param roleIdList
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Role> listRoleDetailPermission(List<String> roleIdList, String portalId) {
		List<Role> roleDetailPermission = null;

		Map<String, Object> map = new HashMap<String, Object>(); 
		map.put("roleIdList", roleIdList);
		map.put("portalId", portalId);
		
		try {
			roleDetailPermission = getSqlMapClientTemplate().getSqlMapClient().queryForList(
					Constants.NAMESPACE + "listRoleDetailPermission", map);
		} catch (SQLException sqlException) {
			// TODO: Exception 작성 필요
		}
		return roleDetailPermission;
	}

	/**
	 * 고용형태 권한 상세정보 가져오기
	 * 
	 * @param assignUserIdList
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<JobClass> listJobClassDetailPermission(List<String> jobClassCodeList, String portalId) {
		List<JobClass> jobClassDetailPermission = null;

		Map<String, Object> map = new HashMap<String, Object>(); 
		map.put("jobClassCodeList", jobClassCodeList);
		map.put("portalId", portalId);
		
		try {
			jobClassDetailPermission = getSqlMapClientTemplate().getSqlMapClient().queryForList(
					Constants.NAMESPACE + "listJobClassDetailPermission", map);
		} catch (SQLException sqlException) {
			// TODO: Exception 작성 필요
		}
		return jobClassDetailPermission;
	}

	/**
	 * 직책 권한 상세정보 가져오기
	 * 
	 * @param assignUserIdList
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<JobDuty> listJobDutyDetailPermission(List<String> jobDutyCodeList, String portalId) {
		List<JobDuty> jobDutyDetailPermission = null;

		Map<String, Object> map = new HashMap<String, Object>(); 
		map.put("jobDutyCodeList", jobDutyCodeList);
		map.put("portalId", portalId);
		
		try {
			jobDutyDetailPermission = getSqlMapClientTemplate().getSqlMapClient().queryForList(
					Constants.NAMESPACE + "listJobDutyDetailPermission", map);
		} catch (SQLException sqlException) {
			// TODO: Exception 작성 필요
		}
		return jobDutyDetailPermission;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	@Deprecated
	public String create(Object object) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable
	 * )
	 */
	@Deprecated
	public boolean exists(String username) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	@Deprecated
	public Object get(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable
	 * )
	 */
	@Deprecated
	public void remove(String username) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	@Deprecated
	public void update(Object object) {
		// TODO Auto-generated method stub

	}
}
