/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.security.acl.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.support.security.acl.model.ACLGroupPermission;
import com.lgcns.ikep4.support.security.acl.model.ACLResourcePermission;
import com.lgcns.ikep4.support.user.group.model.Group;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * Access Control List(ACL) Dao
 * 
 * @author 주길재(giljae@gmail.com)
 * @version $Id: ACLDao.java 17346 2012-02-28 08:43:22Z yruyo $
 */
public interface ACLDao extends GenericDao<Object, String> {
	/**
	 * 접근 리소스에 대한 퍼미션 아이디 조회
	 * 
	 * @param className - 권한 자원의 종류
	 * @param operationNames - 권한 자원에 대한 멀티 오퍼레이션
	 * @param resourceName - 권한 자원의 이름
	 * @return
	 */
	public List<Map<String, Object>> listResourcePermission(String className, String[] operationNames,
			String resourceName);
	
	/**
	 * 접근 리소스에 대한 어드민 권한을 갖고 있는 유저 리스트
	 * @param permissionIdList
	 * @return
	 */
	public List<User> listSystemAdminAsUser(List<String> permissionIdList);
	
	/**
	 * 접근 리소스에 대한 어드민 권한을 갖고 있는 그룹 리스트
	 * @param permissionIdList
	 * @return
	 */
	public List<Group> listSystemAdminAsGroup(List<String> permissionIdList);
	
	/**
	 * 정적 (System) 접근 리소스의 관리 권한이 있는 사용자 + 그룹(유저아이디를 읽어와서)의 사용자 리스트를 리턴
	 * @param permissionIdList
	 * @return
	 */
	public List<User> listSystemAdminAsAll(List<String> permissionIdList);

	/**
	 * 사용자가 소속된 그룹/역할/사용자 별 권한 조회
	 * 
	 * @param permissionIds
	 * @param userId
	 */
	public List<Object> listAllPermission(String[] permissionIds, String userId);
	
	/**
	 * 사용자 별 예외 권한 조회
	 * 
	 * @param permissionIds
	 * @param userId
	 */
	public List<Object> listExpPermission(String[] permissionIds, String userId);

	/**
	 * 리소스 생성
	 * 
	 * @param aclResourcePermission
	 * @param resourceId
	 */
	public void createResource(ACLResourcePermission aclResourcePermission, String resourceId);

	/**
	 * 퍼미션 생성
	 * 
	 * @param aclPermissionList
	 * @param resourceId
	 * @param permissionId
	 */
	public void createBasePermission(ACLResourcePermission aclResourcePermission, String resourceId, String permissionId);
	
	/**
	 * 퍼미션 수정
	 * 
	 * @param aclPermissionList
	 * @param resourceId
	 * @param permissionId
	 */
	public void updateBasePermission(ACLResourcePermission aclResourcePermission, String permissionId);

	/**
	 * 지정된 사용자에게 권한 부여
	 * 
	 * @param aclResourcePermission
	 * @param permissionId
	 */
	public void createUserPermission(ACLResourcePermission aclResourcePermission, String permissionId);

	/**
	 * 사용자가 소속된 그룹에게 권한 부여
	 * 
	 * @param aclResourcePermission
	 * @param permissionId
	 */
	public void createGroupPermission(ACLResourcePermission aclResourcePermission, String permissionId);

	/**
	 * 역할별 권한 부여
	 * 
	 * @param aclResourcePermission
	 * @param permissionId
	 */
	public void createRolePermission(ACLResourcePermission aclResourcePermission, String permissionId);

	/**
	 * 고용 형태별 권한 부여
	 * 
	 * @param aclResourcePermission
	 * @param permissionId
	 */
	public void createJobClassPermission(ACLResourcePermission aclResourcePermission, String permissionId);

	/**
	 * 직책별 권한 부여
	 * 
	 * @param aclResourcePermission
	 * @param permissionid
	 */
	public void createJobDutyPermission(ACLResourcePermission aclResourcePermission, String permissionid);
	
	/**
	 * 지정된 사용자에게 예외 권한 부여
	 * 
	 * @param aclResourcePermission
	 * @param permissionId
	 */
	public void createExpUserPermission(ACLResourcePermission aclResourcePermission, String permissionId);

	/**
	 * 리소스 삭제
	 * 
	 * @param resourceId
	 */
	public void removeReource(String resourceId);

	/**
	 * 퍼미션 삭제
	 * 
	 * @param permissionIdList
	 */
	public void removeBasePermission(List<String> permissionIdList);

	/**
	 * 그룹 권한 삭제
	 * 
	 * @param permissionIdList
	 */
	public void removeGroupPermission(List<String> permissionIdList);
	
	/**
	 * 그룹 권한 삭제 (with portalId)
	 * @param permissionId
	 * @param portalId
	 */
	public void removeGroupPermission(String permissionId, String portalId);

	/**
	 * 역할 권한 삭제
	 * 
	 * @param permissionIdList
	 */
	public void removeRolePermission(List<String> permissionIdList);
	
	/**
	 * 역할 권한 삭제 (with portalId)
	 * 
	 * @param permissionId
	 * @param portalId
	 */
	public void removeRolePermission(String permissionId, String portalId);

	/**
	 * 사용자 권한 삭제
	 * 
	 * @param permissionIdList
	 */
	public void removeUserPermission(List<String> permissionIdList);

	/**
	 * 사용자 권한 삭제 (with portalId)
	 * @param permissionId
	 * @param portalId
	 */
	public void removeUserPermission(String permissionId, String portalId);
	
	/**
	 * 고용형태 권한 삭제
	 * 
	 * @param permissionIdList
	 */
	public void removeJobClassPermission(List<String> permissionIdList);
	
	/**
	 * 고용형태 권한 삭제 (with portalId)
	 * 
	 * @param permissionIdList
	 */
	public void removeJobClassPermission(String permissionId, String portalId);

	/**
	 * 직책 권한 삭제
	 * 
	 * @param permissionIdList
	 */
	public void removeJobDutyPermission(List<String> permissionIdList);
	
	/**
	 * 직책 권한 삭제 (with portalId)
	 * 
	 * @param permissionId
	 * @param portalId
	 */
	public void removeJobDutyPermission(String permissionId, String portalId);
	
	/**
	 * 사용자 예외 권한 삭제
	 * 
	 * @param permissionIdList
	 */
	public void removeExpUserPermission(List<String> permissionIdList);

	/**
	 * 사용자 예외 권한 삭제 (with portalId)
	 * @param permissionId
	 * @param portalId
	 */
	public void removeExpUserPermission(String permissionId, String portalId);

	/**
	 * 리소스 아이디 가져오기
	 * 
	 * @param className
	 * @param resourceName
	 * @return String
	 */
	public String getResourceId(String className, String resourceName);

	/**
	 * 퍼미션 아이디 리스트 가져오기
	 * 
	 * @param resourceId
	 * @return
	 */
	public List<String> listPermissionId(String resourceId);

	/**
	 * 퍼미션 정보 가져오기
	 * 
	 * @param className
	 * @param resourceName
	 * @param operationName
	 * @return
	 */
	public Map<String, Object> getBasePermission(String className, String resourceName, String operationName);

	/**
	 * 그룹 권한 정보 가져오기
	 * 
	 * @param permissionId
	 * @return List<ACLGroupPermission>
	 */
	public List<ACLGroupPermission> listGroupPermission(String permissionId);

	/**
	 * 역할 권한 정보 가져오기
	 * 
	 * @param permissionId
	 * @return List<String>
	 */
	public List<String> listRolePermission(String permissionId);

	/**
	 * 사용자 권한 정보 가져오기
	 * 
	 * @param permissionId
	 * @return List<Object>
	 */
	public List<String> listUserPermission(String permissionId);

	/**
	 * 고용 형태 권한 정보 가져오기
	 * 
	 * @param permissionId
	 * @return List<Object>
	 */
	public List<String> listJobClassPermission(String permissionId);

	/**
	 * 직책 권한 정보 가져오기
	 * 
	 * @param permissionId
	 * @return
	 */
	public List<String> listJobDutyPermission(String permissionId);
	
	/**
	 * 사용자 예외 권한 정보 가져오기
	 * 
	 * @param permissionId
	 * @return List<Object>
	 */
	public List<String> listExpUserPermission(String permissionId);
}
