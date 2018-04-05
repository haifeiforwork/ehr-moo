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
import com.lgcns.ikep4.support.security.acl.dao.ACLDao;
import com.lgcns.ikep4.support.security.acl.model.ACLGroupPermission;
import com.lgcns.ikep4.support.security.acl.model.ACLResourcePermission;
import com.lgcns.ikep4.support.user.group.model.Group;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * Access Control List(ACL) Dao 구현체 (동적 Content)
 * 
 * @author 주길재
 * @version $Id: ACLContentDaoImpl.java 17346 2012-02-28 08:43:22Z yruyo $
 */
@Repository(value = "aclContentDao")
public class ACLContentDaoImpl extends GenericDaoSqlmap<Object, String> implements ACLDao {
	// 상수 정의
	interface Constants {
		final String CLASS_NAME = "className";

		final String OPERATION_NAMES = "operationNames";

		final String OPERATION_NAME = "operationName";

		final String RESOURCE_NAME = "resourceName";

		final String RESOURCE_ID = "resourceId";

		final String USER_ID = "userId";

		final String PERMISSION_ID = "permissionId";

		final String PERMISSION_IDS = "permissionIds";

		final String ASSIGN_USER_ID = "assignUserId";
		
		final String EXCEPT_USER_ID = "exceptUserId";

		final String GROUP_ID = "groupId";

		final String ROLE_ID = "roleId";

		final String JOB_CLASS_CODE = "jobClassCode";

		final String JOB_DUTY_CODE = "jobDutyCode";

		final String HIERARCHY_PERMISSION = "hierarchyPermission";

		final String ACL_RESOURCE_PERMISSION = "aclResourcePermission";

		final String NAMESPACE = "support.user.security.acl.dao.ACLContent.";
	}

	/**
	 * 접근 리소스에 대한 퍼미션 아이디 조회
	 * 
	 * @param className - 권한 자원의 종류
	 * @param operationNames - 권한 자원에 대한 멀티 오퍼레이션
	 * @param resourceName - 권한 자원의 이름
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> listResourcePermission(String className, String[] operationNames,
			String resourceName) {
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put(Constants.CLASS_NAME, className);
		parameterMap.put(Constants.OPERATION_NAMES, operationNames);
		parameterMap.put(Constants.RESOURCE_NAME, resourceName);

		List<Map<String, Object>> resourcePermissionList = null;

		try {
			resourcePermissionList = getSqlMapClientTemplate().getSqlMapClient().queryForList(
					Constants.NAMESPACE + "listResourcePermission", parameterMap);
		} catch (SQLException sqlException) {
			// TODO : Exception 작성 필요
		}

		return resourcePermissionList;
	}

	/**
	 * 사용자가 소속된 그룹권한/역할권한/사용자권한 조회
	 * 
	 * @param permissionIds
	 * @param userId
	 */
	public List<Object> listAllPermission(String[] permissionIds, String userId) {
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put(Constants.PERMISSION_IDS, permissionIds);
		parameterMap.put(Constants.USER_ID, userId);

		return (List<Object>) sqlSelectForList(Constants.NAMESPACE + "listAllPermission", parameterMap);
	}
	
	/**
	 * 사용자별 예외 권한 조회
	 * 
	 * @param permissionIds
	 * @param userId
	 */
	public List<Object> listExpPermission(String[] permissionIds, String userId) {
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put(Constants.PERMISSION_IDS, permissionIds);
		parameterMap.put(Constants.USER_ID, userId);

		return (List<Object>) sqlSelectForList(Constants.NAMESPACE + "listExpPermission", parameterMap);
	}

	/**
	 * 리소스 아이디 가져오기
	 * 
	 * @param className
	 * @param resourceName
	 */
	public String getResourceId(String className, String resourceName) {
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put(Constants.CLASS_NAME, className);
		parameterMap.put(Constants.RESOURCE_NAME, resourceName);

		String resourceId = (String) sqlSelectForObject(Constants.NAMESPACE + "getResourceId", parameterMap);

		return resourceId;
	}

	/**
	 * 리소스 아이디에 대한 권한 아이디 리스트 가져오기
	 */
	@SuppressWarnings("unchecked")
	public List<String> listPermissionId(String resourceId) {
		List<String> permissionIdList = null;
		try {
			permissionIdList = getSqlMapClientTemplate().getSqlMapClient().queryForList(
					Constants.NAMESPACE + "listPermissionId", resourceId);
		} catch (SQLException sqlException) {
			// TODO : Exception 작성 필요
		}
		return permissionIdList;
	}

	/**
	 * 베이스 퍼미션 정보 가져오기
	 * 
	 * @param className
	 * @param resourceName
	 * @param operationName
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getBasePermission(String className, String resourceName, String operationName) {
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put(Constants.CLASS_NAME, className);
		parameterMap.put(Constants.RESOURCE_NAME, resourceName);
		parameterMap.put(Constants.OPERATION_NAME, operationName);

		return (Map<String, Object>) sqlSelectForObject(Constants.NAMESPACE + "getBasePermission", parameterMap);
	}

	/**
	 * 리소스를 생성한다.
	 * 
	 * @param aclResourcePermission
	 * @param resourceId
	 * @return
	 */
	public void createResource(ACLResourcePermission aclResourcePermission, String resourceId) {
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put(Constants.ACL_RESOURCE_PERMISSION, aclResourcePermission);
		parameterMap.put(Constants.RESOURCE_ID, resourceId);

		// 리소스를 생성한다.
		sqlInsert(Constants.NAMESPACE + "createResource", parameterMap);
	}

	/**
	 * 퍼미션을 생성한다.
	 * 
	 * @param aclResourcePermission
	 * @param resourceId
	 * @param permissionId
	 * @return
	 */
	public void createBasePermission(ACLResourcePermission aclResourcePermission, String resourceId, String permissionId) {
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put(Constants.RESOURCE_ID, resourceId);
		parameterMap.put(Constants.PERMISSION_ID, permissionId);
		parameterMap.put(Constants.ACL_RESOURCE_PERMISSION, aclResourcePermission);

		sqlInsert(Constants.NAMESPACE + "createBasePermission", parameterMap);
	}

	/**
	 * 리소스에 대한 사용자 권한 생성
	 * 
	 * @param aclResourcePermission
	 * @param permissionId
	 */
	public void createUserPermission(ACLResourcePermission aclResourcePermission, String permissionId) {
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put(Constants.ACL_RESOURCE_PERMISSION, aclResourcePermission);
		parameterMap.put(Constants.PERMISSION_ID, permissionId);

		try {
			// Batch 처리
			getSqlMapClientTemplate().getSqlMapClient().startBatch();

			for (String assignUserId : aclResourcePermission.getAssignUserIdList()) {
				parameterMap.put(Constants.ASSIGN_USER_ID, assignUserId);
				sqlInsert(Constants.NAMESPACE + "createUserPermission", parameterMap);
			}
			getSqlMapClientTemplate().getSqlMapClient().executeBatch();
		} catch (SQLException sqlException) {
			// TODO : Exception 처리 필요
		}
	}

	/**
	 * 사용자가 소속된 그룹에게 리소스에 대한 사용자 권한 생성
	 * 
	 * @param aclResourcePermission
	 * @param permissionId
	 */
	public void createGroupPermission(ACLResourcePermission aclResourcePermission, String permissionId) {
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put(Constants.ACL_RESOURCE_PERMISSION, aclResourcePermission);
		parameterMap.put(Constants.PERMISSION_ID, permissionId);

		try {
			// Batch 처리
			getSqlMapClientTemplate().getSqlMapClient().startBatch();

			for (ACLGroupPermission aclGroupPermission : aclResourcePermission.getGroupPermissionList()) {
				parameterMap.put(Constants.GROUP_ID, aclGroupPermission.getGroupId());
				parameterMap.put(Constants.HIERARCHY_PERMISSION, aclGroupPermission.getHierarchyPermission());
				sqlInsert(Constants.NAMESPACE + "createGroupPermission", parameterMap);
			}
			getSqlMapClientTemplate().getSqlMapClient().executeBatch();
		} catch (SQLException sqlException) {
			// TODO : Exception 처리 필요
		}
	}

	/**
	 * 역할별 권한 부여
	 * 
	 * @param aclResourcePermission
	 * @param permissionId
	 */
	public void createRolePermission(ACLResourcePermission aclResourcePermission, String permissionId) {
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put(Constants.ACL_RESOURCE_PERMISSION, aclResourcePermission);
		parameterMap.put(Constants.PERMISSION_ID, permissionId);

		try {
			// Batch 처리
			getSqlMapClientTemplate().getSqlMapClient().startBatch();

			for (String roleId : aclResourcePermission.getRoleIdList()) {
				parameterMap.put(Constants.ROLE_ID, roleId);
				sqlInsert(Constants.NAMESPACE + "createRolePermission", parameterMap);
			}
			getSqlMapClientTemplate().getSqlMapClient().executeBatch();
		} catch (SQLException sqlException) {
			// TODO : Exception 처리 필요
		}
	}

	/**
	 * 고용 형태별 권한 부여
	 * 
	 * @param aclResourcePermission
	 * @param permissionId
	 */
	public void createJobClassPermission(ACLResourcePermission aclResourcePermission, String permissionId) {
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put(Constants.ACL_RESOURCE_PERMISSION, aclResourcePermission);
		parameterMap.put(Constants.PERMISSION_ID, permissionId);

		try {
			// Batch 처리
			getSqlMapClientTemplate().getSqlMapClient().startBatch();

			for (String jobClassCode : aclResourcePermission.getJobClassCodeList()) {
				parameterMap.put(Constants.JOB_CLASS_CODE, jobClassCode);
				sqlInsert(Constants.NAMESPACE + "createJobClassPermission", parameterMap);
			}
			getSqlMapClientTemplate().getSqlMapClient().executeBatch();
		} catch (SQLException sqlException) {
			// TODO : Exception 처리 필요
		}
	}

	/**
	 * 직책별 권한 부여
	 * 
	 * @param aclResourcePermission
	 * @param permissionId
	 */
	public void createJobDutyPermission(ACLResourcePermission aclResourcePermission, String permissionId) {
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put(Constants.ACL_RESOURCE_PERMISSION, aclResourcePermission);
		parameterMap.put(Constants.PERMISSION_ID, permissionId);

		try {
			// Batch 처리
			getSqlMapClientTemplate().getSqlMapClient().startBatch();

			for (String jobDutyCode : aclResourcePermission.getJobDutyCodeList()) {
				parameterMap.put(Constants.JOB_DUTY_CODE, jobDutyCode);
				sqlInsert(Constants.NAMESPACE + "createJobDutyPermission", parameterMap);
			}
			getSqlMapClientTemplate().getSqlMapClient().executeBatch();
		} catch (SQLException sqlException) {
			// TODO : Exception 처리 필요
		}
	}
	
	/**
	 * 리소스에 대한 사용자 예외 권한 생성
	 * 
	 * @param aclResourcePermission
	 * @param permissionId
	 */
	public void createExpUserPermission(ACLResourcePermission aclResourcePermission, String permissionId) {
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put(Constants.ACL_RESOURCE_PERMISSION, aclResourcePermission);
		parameterMap.put(Constants.PERMISSION_ID, permissionId);

		try {
			// Batch 처리
			getSqlMapClientTemplate().getSqlMapClient().startBatch();

			for (String exceptUserId : aclResourcePermission.getExceptUserIdList()) {
				// 사용자 예외 권한 생성 시 권한을 생성하는 자가 예외가 되지 않도록 한다.
				if(aclResourcePermission.getUserId() != null && !aclResourcePermission.getUserId().equals(exceptUserId)) {
					parameterMap.put(Constants.EXCEPT_USER_ID, exceptUserId);
					sqlInsert(Constants.NAMESPACE + "createExpUserPermission", parameterMap);
				}
			}
			getSqlMapClientTemplate().getSqlMapClient().executeBatch();
		} catch (SQLException sqlException) {
			// TODO : Exception 처리 필요
		}
	}

	/**
	 * 리소스 삭제
	 * 
	 * @param resourceId
	 */
	public void removeReource(String resourceId) {
		sqlDelete(Constants.NAMESPACE + "removeResource", resourceId);
	}

	/**
	 * 퍼미션 삭제
	 * 
	 * @param permissionIdList
	 */
	public void removeBasePermission(List<String> permissionIdList) {
		sqlDelete(Constants.NAMESPACE + "removeBasePermission", permissionIdList);
	}

	/**
	 * 그룹 권한 삭제
	 * 
	 * @param permissionIdList
	 */
	public void removeGroupPermission(List<String> permissionIdList) {
		sqlDelete(Constants.NAMESPACE + "removeGroupPermission", permissionIdList);
	}
	
	/**
	 * 그룹 권한 삭제 (with portalId)
	 * 
	 * @param permissionId
	 * @param portalId
	 */
	@Deprecated
	public void removeGroupPermission(String permissionId, String portalId) {
	}

	/**
	 * 역할 권한 삭제
	 * 
	 * @param permissionIdList
	 */
	public void removeRolePermission(List<String> permissionIdList) {
		sqlDelete(Constants.NAMESPACE + "removeRolePermission", permissionIdList);
	}
	
	/**
	 * 역할 권한 삭제 (with portalId)
	 * 
	 * @param permissionId
	 * @param portalId
	 */
	@Deprecated
	public void removeRolePermission(String permissionId, String portalId) {
	}

	/**
	 * 사용자 권한 삭제
	 * 
	 * @param permissionIdList
	 */
	public void removeUserPermission(List<String> permissionIdList) {
		sqlDelete(Constants.NAMESPACE + "removeUserPermission", permissionIdList);
	}
	
	/**
	 * 사용자 권한 삭제 (with portalId)
	 * 
	 * @param permissionId
	 * @param portalId
	 */
	@Deprecated
	public void removeUserPermission(String permissionId, String portalId) {
	}
	
	@Deprecated
	public void updateBasePermission(ACLResourcePermission aclResourcePermission, String permissionId) {
		
	}

	/**
	 * 고용형태 권한 삭제
	 * 
	 * @param permissionIdList
	 */
	public void removeJobClassPermission(List<String> permissionIdList) {
		sqlDelete(Constants.NAMESPACE + "removeJobClassPermission", permissionIdList);
	}
	
	/**
	 * 고용형태 권한 삭제 (with portalId)
	 * 
	 * @param permissionId
	 * @param portalId
	 */
	@Deprecated
	public void removeJobClassPermission(String permissionId, String portalId) {
	}

	/**
	 * 직책 권한 삭제
	 * 
	 * @param permissionIdList
	 */
	public void removeJobDutyPermission(List<String> permissionIdList) {
		sqlDelete(Constants.NAMESPACE + "removeJobDutyPermission", permissionIdList);
	}
	
	/**
	 * 직책 권한 삭제 (with portalId)
	 * 
	 * @param permissionId
	 * @param portalId
	 */
	@Deprecated
	public void removeJobDutyPermission(String permissionId, String portalId) {
	}
	
	/**
	 * 사용자 예외 권한 삭제
	 * 
	 * @param permissionIdList
	 */
	public void removeExpUserPermission(List<String> permissionIdList) {
		sqlDelete(Constants.NAMESPACE + "removeExpUserPermission", permissionIdList);
	}
	
	/**
	 * 사용자 예외 권한 삭제 (with portalId)
	 * 
	 * @param permissionId
	 * @param portalId
	 */
	@Deprecated
	public void removeExpUserPermission(String permissionId, String portalId) {
	}
	
	/**
	 * 그룹 권한 정보 가져오기
	 * 
	 * @param permissionId
	 * @return List<ACLGroupPermission>
	 */
	@SuppressWarnings("unchecked")
	public List<ACLGroupPermission> listGroupPermission(String permissionId) {
		List<ACLGroupPermission> groupPermissionList = null;
		try {
			groupPermissionList = getSqlMapClientTemplate().getSqlMapClient().queryForList(
					Constants.NAMESPACE + "listGroupPermission", permissionId);
		} catch (SQLException sqlException) {
			// TODO: Exception 작성 필요
		}

		return groupPermissionList;
	}

	/**
	 * 역할 권한 정보 가져오기
	 * 
	 * @param permissionId
	 * @return List<String>
	 */
	@SuppressWarnings("unchecked")
	public List<String> listRolePermission(String permissionId) {
		List<String> rolePermissionList = null;
		try {
			rolePermissionList = getSqlMapClientTemplate().getSqlMapClient().queryForList(
					Constants.NAMESPACE + "listRolePermission", permissionId);
		} catch (SQLException sqlException) {
			// TODO: Exception 작성 필요
		}
		return rolePermissionList;
	}

	/**
	 * 사용자 권한 정보 가져오기
	 * 
	 * @param permissionId
	 * @return List<Object>
	 */
	@SuppressWarnings("unchecked")
	public List<String> listUserPermission(String permissionId) {
		List<String> userPermissionList = null;
		try {
			userPermissionList = getSqlMapClientTemplate().getSqlMapClient().queryForList(
					Constants.NAMESPACE + "listUserPermission", permissionId);
		} catch (SQLException sqlException) {
			// TODO: Exception 작성 필요
		}
		return userPermissionList;
	}

	/**
	 * 고용 형태 권한 정보 가져오기
	 * 
	 * @param permissionId
	 * @return List<Object>
	 */
	@SuppressWarnings("unchecked")
	public List<String> listJobClassPermission(String permissionId) {
		List<String> jobClassPermissionList = null;
		try {
			jobClassPermissionList = getSqlMapClientTemplate().getSqlMapClient().queryForList(
					Constants.NAMESPACE + "listJobClassPermission", permissionId);
		} catch (SQLException sqlException) {
			// TODO: Exception 작성 필요
		}
		return jobClassPermissionList;
	}

	/**
	 * 직책 권한 정보 가져오기
	 * 
	 * @param permissionId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> listJobDutyPermission(String permissionId) {
		List<String> jobDutyPermission = null;

		try {
			jobDutyPermission = getSqlMapClientTemplate().getSqlMapClient().queryForList(
					Constants.NAMESPACE + "listJobDutyPermission", permissionId);
		} catch (SQLException sqlException) {
			// TODO: Exception 작성 필요
		}
		return jobDutyPermission;
	}
	
	/**
	 * 사용자 예외 권한 정보 가져오기
	 * 
	 * @param permissionId
	 * @return List<Object>
	 */
	@SuppressWarnings("unchecked")
	public List<String> listExpUserPermission(String permissionId) {
		List<String> userExpPermissionList = null;
		try {
			userExpPermissionList = getSqlMapClientTemplate().getSqlMapClient().queryForList(
					Constants.NAMESPACE + "listExpUserPermission", permissionId);
		} catch (SQLException sqlException) {
			// TODO: Exception 작성 필요
		}
		return userExpPermissionList;
	}
	
	@Deprecated
	public List<User> listSystemAdminAsUser(List<String> permissionIdList) {
		return null;
	}
	
	@Deprecated
	public List<Group> listSystemAdminAsGroup(List<String> permissionIdList) {
		return null;
	}
	
	@Deprecated
	public List<User> listSystemAdminAsAll(List<String> permissionIdList) {
		return null;
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
