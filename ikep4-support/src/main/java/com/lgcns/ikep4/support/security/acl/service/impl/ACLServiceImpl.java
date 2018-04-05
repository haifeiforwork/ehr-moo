/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.security.acl.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.support.security.acl.dao.ACLCommonDao;
import com.lgcns.ikep4.support.security.acl.dao.ACLDao;
import com.lgcns.ikep4.support.security.acl.model.ACLGroupPermission;
import com.lgcns.ikep4.support.security.acl.model.ACLResourcePermission;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.group.model.Group;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * Access Control List(ACL) Service 구현체
 * 
 * @author 주길재(giljae@gmail.com)
 * @version $Id: AccessControlListServiceImpl.java 1137 2011-02-23 06:57:18Z
 *          giljae $
 */
@Service(value = "aclService")
public class ACLServiceImpl implements ACLService {
	private final static String MANAGE = "MANAGE";

	@Autowired
	@Qualifier("aclSystemDao")
	private ACLDao aclSystemDao;

	@Autowired
	@Qualifier("aclContentDao")
	private ACLDao aclContentDao;

	@Autowired
	private ACLCommonDao aclCommonDao;

	@Autowired
	private IdgenService idgenService;

	/**
	 * 정적(System) 접근 리소스에 대한 권한 체크 (단일 Operation)
	 * 
	 * @param className - 권한 자원의 종류
	 * @param operationName - 권한 자원에 대한 오퍼레이션
	 * @param resourceName - 권한 자원의 이름
	 * @param userId - 로그인한 유저아이디
	 * @return - 접근 권한이 있으면 true, 접근 권한이 없으면 false를 리턴
	 */
	public boolean hasSystemPermission(String className, String operationName, String resourceName, String userId) {
		String[] operationNames = { operationName };
		return hasSystemPermission(className, operationNames, resourceName, userId);
	}

	/**
	 * 정적 (System) 접근 리소스의 관리 권한이 있는 사용자 리스트를 리턴
	 * 
	 * @param sysName
	 * @return
	 */
	public List<User> listSystemAdminAsUser(String sysName) {
		String[] operationNames = { MANAGE };
		List<User> userList = null;

		// resource의 접근 권한 조회
		List<Map<String, Object>> permissionList = this.aclSystemDao.listResourcePermission(sysName, operationNames,
				sysName);

		// 결과 데이터가 없을 경우에는 null을 리턴
		if (permissionList.size() < 1) {
			return userList;
		}

		List<String> permissionIdList = new ArrayList<String>();
		Map<String, Object> permissionMap = null;

		for (int index = 0; index < permissionList.size(); index++) {
			permissionMap = permissionList.get(index);
			permissionIdList.add(permissionMap.get("permissionId").toString());
		}

		userList = this.aclSystemDao.listSystemAdminAsUser(permissionIdList);

		return userList;
	}

	/**
	 * 정적 (System) 접근 리소스의 관리 권한이 있는 그룹 리스트를 리턴
	 * 
	 * @param sysName
	 * @return
	 */
	public List<Group> listSystemAdminAsGroup(String sysName) {
		String[] operationNames = { MANAGE };
		List<Group> groupList = null;

		// resource의 접근 권한 조회
		List<Map<String, Object>> permissionList = this.aclSystemDao.listResourcePermission(sysName, operationNames,
				sysName);

		// 결과 데이터가 없을 경우에는 null을 리턴
		if (permissionList.size() < 1) {
			return groupList;
		}

		List<String> permissionIdList = new ArrayList<String>();
		Map<String, Object> permissionMap = null;

		for (int index = 0; index < permissionList.size(); index++) {
			permissionMap = permissionList.get(index);
			permissionIdList.add(permissionMap.get("permissionId").toString());
		}

		groupList = this.aclSystemDao.listSystemAdminAsGroup(permissionIdList);

		return groupList;
	}

	/**
	 * 정적 (System) 접근 리소스의 관리 권한이 있는 유저+그룹(리스트에 속해 있는 유저)을 리턴
	 * 
	 * @param sysName
	 * @return
	 */
	public List<User> listSystemAdminAsAll(String sysName) {
		String[] operationNames = { MANAGE };
		List<User> userList = null;

		// resource의 접근 권한 조회
		List<Map<String, Object>> permissionList = this.aclSystemDao.listResourcePermission(sysName, operationNames,
				sysName);

		// 결과 데이터가 없을 경우에는 null을 리턴
		if (permissionList.size() < 1) {
			return userList;
		}

		List<String> permissionIdList = new ArrayList<String>();
		Map<String, Object> permissionMap = null;

		for (int index = 0; index < permissionList.size(); index++) {
			permissionMap = permissionList.get(index);
			permissionIdList.add(permissionMap.get("permissionId").toString());
		}

		userList = this.aclSystemDao.listSystemAdminAsAll(permissionIdList);

		return userList;
	}

	/**
	 * 정적(System) 접근 리소스의 관리 권한이 있는 사용자인지 체크
	 * 
	 * @param sysName
	 * @param userId
	 * @return
	 */
	public boolean isSystemAdmin(String sysName, String userId) {
		String[] operationNames = { MANAGE };
		// resource의 접근 권한 조회
		List<Map<String, Object>> permissionList = this.aclSystemDao.listResourcePermission(sysName, operationNames,
				sysName);

		// 결과 데이터가 없을 경우에는 접근 권한이 없음
		if (permissionList.size() < 1) {
			return Boolean.FALSE;
		}

		Map<String, Object> permissionMap = null;
		String[] permisionIds = new String[permissionList.size()];

		// Get the PermissionIds
		for (int index = 0; index < permissionList.size(); index++) {
			permissionMap = permissionList.get(index);

			if (permissionMap.get("owner").toString().equals(userId)) {
				return Boolean.TRUE;
			}

			permisionIds[index] = permissionMap.get("permissionId").toString();
		}

		List<Object> allPermissionList = this.aclSystemDao.listAllPermission(permisionIds, userId);
		
		//속도 개선을 위해 로직을 분리
		// 결과 데이터가 없을 경우에는 접근 권한이 없음
		if (allPermissionList.size() < 1) {
			return Boolean.FALSE;
		}		
		
		List<Object> exceptPermissionList = this.aclSystemDao.listExpPermission(permisionIds, userId);

		// userPermissionCount > 0 면, 사용자권한/역할권한/그룹권한 중에 하나 이상의 접근 권한이 존재
		//return allPermissionList.size() > 0;
		
		if(!(exceptPermissionList.size() > 0)) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	/**
	 * 정적(System) 접근 리소스에 대한 권한 체크 (멀티 Operation)
	 * 
	 * @param className - 권한 자원의 종류
	 * @param operationNames - 권한 자원에 대한 멀티 오퍼레이션
	 * @param resourceName - 권한 자원의 이름
	 * @param userId - 로그인한 유저아이디
	 * @return - 접근 권한이 있으면 true, 접근 권한이 없으면 false를 리턴
	 */
	public boolean hasSystemPermission(String className, String[] operationNames, String resourceName, String userId) {
		// resource의 접근 권한 조회
		List<Map<String, Object>> permissionList = this.aclSystemDao.listResourcePermission(className, operationNames,
				resourceName);

		// 결과 데이터가 없을 경우에는 접근 권한이 없음
		if (permissionList.size() < 1) {
			return Boolean.FALSE;
		}

 		Map<String, Object> permissionMap = null;
		String[] permisionIds = new String[permissionList.size()];

		// resource의 오픈 여부가 1 이거나, Resource 소유자일 경우
		for (int index = 0; index < permissionList.size(); index++) {
			permissionMap = permissionList.get(index);

			if ((Integer) permissionMap.get("open") == 1 || permissionMap.get("owner").toString().equals(userId)) {
				return Boolean.TRUE;
			}

			permisionIds[index] = permissionMap.get("permissionId").toString();
		}

		List<Object> allPermissionList = this.aclSystemDao.listAllPermission(permisionIds, userId);
		
		List<Object> exceptPermissionList = this.aclSystemDao.listExpPermission(permisionIds, userId);

		// userPermissionCount > 0 면, 사용자권한/역할권한/그룹권한 중에 하나 이상의 접근 권한이 존재
		//return allPermissionList.size() > 0;
		
		if(allPermissionList.size() > 0 && !(exceptPermissionList.size() > 0)) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	/**
	 * 정적(System) 접근 권한 생성 - 베이스
	 * 
	 * @param aclResourceWithPermission
	 */
	public void createSystemPermission(ACLResourcePermission aclResourcePermission) {
		// Resource Name으로 resourceId를 찾기
		String className = aclResourcePermission.getClassName();
		String resourceName = aclResourcePermission.getResourceName();

		String resourceId = aclSystemDao.getResourceId(className, resourceName);

		if (resourceId == null || "".equals(resourceId)) {
			// Get the resource id.
			resourceId = idgenService.getNextId();
			// 리소스 생성
			aclSystemDao.createResource(aclResourcePermission, resourceId);
		}

		// Get the permission id.
		String permissionId = idgenService.getNextId();

		// 퍼미션 생성
		createSystemBasePermission(aclResourcePermission, resourceId, permissionId);

		if (aclResourcePermission.getOpen() == 0) {
			// 지정된 사용자에게 퍼미션 부여
			createSystemUserPermission(aclResourcePermission, permissionId);

			// 사용자가 소속된 그룹에게 퍼미션 부여
			createSystemGroupPermission(aclResourcePermission, permissionId);

			// 역할 권한 부여
			createSystemRolePermission(aclResourcePermission, permissionId);

			// 고용 형태 권한 부여
			createSystemJobClassPermission(aclResourcePermission, permissionId);

			// 직책 권한 부여
			createSystemJobDutyPermission(aclResourcePermission, permissionId);
			
			// 예외 사용자 권한 부여
			createSystemExpUserPermission(aclResourcePermission, permissionId);
		}
	}

	/**
	 * 정적(System) 접근 권한 삭제
	 * 
	 * @param className
	 * @param resourceName
	 */
	public void deleteSystemPermission(String className, String resourceName) {
		// 클래스 이름과 리소스 이름에 매칭되는 리소스 아이디를 가져온다.
		String resourceId = aclSystemDao.getResourceId(className, resourceName);

		// 리소스 아이디에 해당하는 권한 아이디 리스트를 가져온다.
		List<String> permissionIdList = aclSystemDao.listPermissionId(resourceId);

		// 권한 삭제 및 리소스 삭제 루틴
		if (permissionIdList != null && permissionIdList.size() > 0) {
			// 그룹 권한 삭제
			aclSystemDao.removeGroupPermission(permissionIdList);

			// 사용자 권한 삭제
			aclSystemDao.removeUserPermission(permissionIdList);

			// 역할 권한 삭제
			aclSystemDao.removeRolePermission(permissionIdList);

			// 고용형태 권한 삭제
			aclSystemDao.removeJobClassPermission(permissionIdList);

			// 직책 권한 삭제
			aclSystemDao.removeJobDutyPermission(permissionIdList);
			
			// 직책 권한 삭제
			aclSystemDao.removeExpUserPermission(permissionIdList);
			
			// 사용자 예외 권한 삭제
			aclSystemDao.removeExpUserPermission(permissionIdList);

			// 베이스 권한 삭제
			aclSystemDao.removeBasePermission(permissionIdList);
		}

		// 리소스 삭제
		aclSystemDao.removeReource(resourceId);
	}

	/**
	 * 정적(System) 리소스에 대한 퍼미션 정보를 가져온다.
	 * 
	 * @param className
	 * @param resourceName
	 * @param operationName
	 */
	public ACLResourcePermission getSystemPermission(String className, String resourceName, String operationName) {
		// Get the base permission
		Map<String, Object> resultMap = aclSystemDao.getBasePermission(className, resourceName, operationName);

		// 리소스에 대한 권한 정보가 없을 경우에는 null을 리턴한다.
		if (resultMap == null) {
			return null;
		}

		int open = Integer.parseInt(resultMap.get("open").toString());
		String permissionName = resultMap.get("permissionName").toString();
		String permissionDescription = resultMap.get("permissionDescription").toString();
		String userId = resultMap.get("userId").toString();
		String userName = resultMap.get("userName").toString();

		// 권한 아이디
		String permissionId = resultMap.get("permissionId").toString();

		// 그룹 권한 정보
		List<ACLGroupPermission> groupPermissionList = aclSystemDao.listGroupPermission(permissionId);

		// 롤 권한 정보
		List<String> roleIdList = aclSystemDao.listRolePermission(permissionId);

		// 사용자 권한 정보
		List<String> assignUserIdList = aclSystemDao.listUserPermission(permissionId);

		// 고용형태 권한 정보
		List<String> jobClassCodeList = aclSystemDao.listJobClassPermission(permissionId);

		// 직책 권한 정보
		List<String> jobDutyCodeList = aclSystemDao.listJobDutyPermission(permissionId);
		
		// 사용자 예외 권한 정보
		List<String> exceptUserIdList = aclSystemDao.listExpUserPermission(permissionId);

		// 모델 세팅
		ACLResourcePermission aclResourcePermission = new ACLResourcePermission();

		// 베이스 퍼미션
		aclResourcePermission.setClassName(className);
		aclResourcePermission.setResourceName(resourceName);
		aclResourcePermission.setUserId(userId);
		aclResourcePermission.setUserName(userName);
		aclResourcePermission.setOpen(open);
		aclResourcePermission.setPermissionName(permissionName);
		aclResourcePermission.setPermissionDescription(permissionDescription);
		aclResourcePermission.setOperationName(operationName);

		// 그룹 권한 정보 세팅
		aclResourcePermission.setGroupPermissionList(groupPermissionList);
		// 롤 권한 정보 세팅
		aclResourcePermission.setRoleIdList(roleIdList);
		// 사용자 권한 정보 세팅
		aclResourcePermission.setAssignUserIdList(assignUserIdList);
		// 고용 형태 권한 정보 세팅
		aclResourcePermission.setJobClassCodeList(jobClassCodeList);
		// 직책 권한 정보 세팅
		aclResourcePermission.setJobDutyCodeList(jobDutyCodeList);
		// 사용자 예외 권한 정보 세팅
		aclResourcePermission.setExceptUserIdList(exceptUserIdList);

		return aclResourcePermission;
	}

	/**
	 * 정적(System) 리소스에 대한 권한 정보를 수정한다. (리소스 삭제후 등록)
	 * 
	 * @param aclResourcePermission
	 */
	public void updateSystemPermissionAndResource(ACLResourcePermission aclResourcePermission) {
		String className = aclResourcePermission.getClassName();
		String resourceName = aclResourcePermission.getResourceName();
		String operationName = aclResourcePermission.getOperationName();

		// 리소스 아이디에 해당하는 정보를 가져온다.
		Map<String, Object> permissionMap = aclSystemDao.getBasePermission(className, resourceName, operationName);
		String permissionId = permissionMap.get("permissionId").toString();
		String resourceId = permissionMap.get("resourceId").toString();

		List<String> permissionIdList = new ArrayList<String>();

		permissionIdList.add(permissionId);

		// 권한 삭제 루틴
		deleteSystemPermission(permissionIdList);

		// 권한 생성 루틴
		// 새로 생성할 권한 아이디를 발급 받는다.
		permissionId = idgenService.getNextId();

		// 기본 권한 정보를 생성한다.
		createSystemBasePermission(aclResourcePermission, resourceId, permissionId);

		if (aclResourcePermission.getOpen() == 0) {
			// 지정된 사용자에게 퍼미션 부여
			createSystemUserPermission(aclResourcePermission, permissionId);

			// 사용자가 소속된 그룹에게 퍼미션 부여
			createSystemGroupPermission(aclResourcePermission, permissionId);

			// 역할 권한 부여
			createSystemRolePermission(aclResourcePermission, permissionId);

			// 고용 형태 권한 부여
			createSystemJobClassPermission(aclResourcePermission, permissionId);

			// 직책 권한 부여
			createSystemJobDutyPermission(aclResourcePermission, permissionId);
			
			// 지정된 사용자에게 예외 퍼미션 부여
			createSystemExpUserPermission(aclResourcePermission, permissionId);
		}
	}

	/**
	 * 정적(System) 리소스에 대한 권한 정보를 수정한다. (리소스 삭제X, 관련 유저만 업데이트)
	 * 
	 * @param aclResourcePermission
	 */
	public void updateSystemPermission(ACLResourcePermission aclResourcePermission, String portalId) {
		String className = aclResourcePermission.getClassName();
		String resourceName = aclResourcePermission.getResourceName();
		String operationName = aclResourcePermission.getOperationName();

		// 리소스 아이디에 해당하는 정보를 가져온다.
		Map<String, Object> permissionMap = aclSystemDao.getBasePermission(className, resourceName, operationName);
		System.out.println("2777777777777777777777777777");
		String permissionId = permissionMap.get("permissionId").toString();

		// 권한 삭제 루틴
		deleteSystemPermissionByPortalId(permissionId, portalId);
		System.out.println("288888888888888888888888888888888");
		// 베이스 퍼미션 정보 업데이트
		updateSystemBasePermission(aclResourcePermission, permissionId);
		System.out.println("29999999999999999999999999999");

		if (aclResourcePermission.getOpen() == 0) {System.out.println("3111111111111111111111111111111");
			// 지정된 사용자에게 퍼미션 부여
			createSystemUserPermission(aclResourcePermission, permissionId);
			System.out.println("3222222222222222222222222222222");
			// 사용자가 소속된 그룹에게 퍼미션 부여
			createSystemGroupPermission(aclResourcePermission, permissionId);
			System.out.println("3444444444444444444444444444");
			// 역할 권한 부여
			createSystemRolePermission(aclResourcePermission, permissionId);
			System.out.println("35555555555555555555555");
			// 고용 형태 권한 부여
			createSystemJobClassPermission(aclResourcePermission, permissionId);
			System.out.println("3666666666666666666666666666");
			// 직책 권한 부여
			createSystemJobDutyPermission(aclResourcePermission, permissionId);
			System.out.println("3777777777777777777777777777777");
			// 지정된 사용자에게 예외 퍼미션 부여
			createSystemExpUserPermission(aclResourcePermission, permissionId);
			System.out.println("38888888888888888888888888");
		}
	}

	/**
	 * 정적(System) 리소스에 대한 퍼미션을 생성한다.
	 * 
	 * @param aclResourcePermission
	 * @param resourceId
	 */
	private void createSystemBasePermission(ACLResourcePermission aclResourcePermission, String resourceId,
			String permissionId) {
		boolean isPermissionNameEmpty = (aclResourcePermission.getPermissionName() == null)
				|| ("".equals(aclResourcePermission.getPermissionName()));
		boolean isPermissionDescriptionEmpty = (aclResourcePermission.getPermissionDescription() == null)
				|| ("".equals(aclResourcePermission.getPermissionDescription()));

		// permissionName에 값이 없을 경우, resourceName으로 대체
		if (isPermissionNameEmpty) {
			aclResourcePermission.setPermissionName(aclResourcePermission.getResourceName());
		}
		// permissionDescription에 값이 없을 경우, resourceDescription으로 대체
		if (isPermissionDescriptionEmpty) {
			aclResourcePermission.setPermissionDescription(aclResourcePermission.getResourceDescription());
		}

		aclSystemDao.createBasePermission(aclResourcePermission, resourceId, permissionId);
	}

	/**
	 * 정적(System) 리소스에 대한 퍼미션 정보를 수정한다.
	 * 
	 * @param aclResourcePermission
	 * @param resourceId
	 * @param permissionId
	 */
	private void updateSystemBasePermission(ACLResourcePermission aclResourcePermission, String permissionId) {
		boolean isPermissionNameEmpty = (aclResourcePermission.getPermissionName() == null)
				|| ("".equals(aclResourcePermission.getPermissionName()));
		boolean isPermissionDescriptionEmpty = (aclResourcePermission.getPermissionDescription() == null)
				|| ("".equals(aclResourcePermission.getPermissionDescription()));

		// permissionName에 값이 없을 경우, resourceName으로 대체
		if (isPermissionNameEmpty) {
			aclResourcePermission.setPermissionName(aclResourcePermission.getResourceName());
		}
		// permissionDescription에 값이 없을 경우, resourceDescription으로 대체
		if (isPermissionDescriptionEmpty) {
			aclResourcePermission.setPermissionDescription(aclResourcePermission.getResourceDescription());
		}

		aclSystemDao.updateBasePermission(aclResourcePermission, permissionId);
	}

	/**
	 * 지정된 사용자에게 퍼미션을 부여한다.- 정적(System)
	 * 
	 * @param aclResourcePermission
	 * @param permissionId
	 */
	private void createSystemUserPermission(ACLResourcePermission aclResourcePermission, String permissionId) {
		boolean isEmpty = (aclResourcePermission.getAssignUserIdList() == null)
				|| (aclResourcePermission.getAssignUserIdList().size() == 0);

		if (isEmpty) {
			return; // assign user가 없을 경우
		}

		aclSystemDao.createUserPermission(aclResourcePermission, permissionId);
	}

	/**
	 * 지정된 역할에게 퍼미션을 부여한다. - 정적(System)
	 * 
	 * @param aclResourcePermission
	 * @param permissionId
	 */
	private void createSystemGroupPermission(ACLResourcePermission aclResourcePermission, String permissionId) {
		boolean isEmpty = (aclResourcePermission.getGroupPermissionList() == null)
				|| (aclResourcePermission.getGroupPermissionList().size() == 0);

		if (isEmpty) {
			return; // 그룹 퍼미션이 없을 경우
		}

		aclSystemDao.createGroupPermission(aclResourcePermission, permissionId);
	}

	/**
	 * 지정된 롤에게 퍼미션을 부여한다. - 정적(System)
	 * 
	 * @param aclResourcePermission
	 * @param permissionId
	 */
	private void createSystemRolePermission(ACLResourcePermission aclResourcePermission, String permissionId) {
		boolean isEmpty = (aclResourcePermission.getRoleIdList() == null)
				|| (aclResourcePermission.getRoleIdList().size() == 0);

		if (isEmpty) {
			return; // 역할 퍼미션이 없을 경우
		}

		aclSystemDao.createRolePermission(aclResourcePermission, permissionId);
	}

	/**
	 * 고용 형태별 권한 부여 - 정적(System)
	 * 
	 * @param aclResourcePermission
	 * @param permissionId
	 */
	private void createSystemJobClassPermission(ACLResourcePermission aclResourcePermission, String permissionId) {
		boolean isEmpty = (aclResourcePermission.getJobClassCodeList() == null)
				|| (aclResourcePermission.getJobClassCodeList().size() == 0);

		if (isEmpty) {
			return; // 역할 퍼미션이 없을 경우
		}

		aclSystemDao.createJobClassPermission(aclResourcePermission, permissionId);
	}

	/**
	 * 직책별 권한 부여 - 정적(System)
	 * 
	 * @param aclResourcePermission
	 * @param permissionId
	 */
	private void createSystemJobDutyPermission(ACLResourcePermission aclResourcePermission, String permissionId) {
		boolean isEmpty = (aclResourcePermission.getJobDutyCodeList() == null)
				|| (aclResourcePermission.getJobDutyCodeList().size() == 0);

		if (isEmpty) {
			return; // 역할 퍼미션이 없을 경우
		}

		aclSystemDao.createJobDutyPermission(aclResourcePermission, permissionId);
	}
	
	/**
	 * 예외 사용자에게 퍼미션을 부여한다.- 정적(System)
	 * 
	 * @param aclResourcePermission
	 * @param permissionId
	 */
	private void createSystemExpUserPermission(ACLResourcePermission aclResourcePermission, String permissionId) {
		boolean isEmpty = (aclResourcePermission.getExceptUserIdList() == null)
				|| (aclResourcePermission.getExceptUserIdList().size() == 0);

		if (isEmpty) {
			return; // assign user가 없을 경우
		}

		aclSystemDao.createExpUserPermission(aclResourcePermission, permissionId);
	}

	/**
	 * 권한 정보를 삭제한다. - 정적(System)
	 * 
	 * @param permissionIdList
	 */
	private void deleteSystemPermission(List<String> permissionIdList) {
		// 권한 삭제 루틴
		if (permissionIdList != null && permissionIdList.size() > 0) {
			// 그룹 권한 삭제
			aclSystemDao.removeGroupPermission(permissionIdList);

			// 사용자 권한 삭제
			aclSystemDao.removeUserPermission(permissionIdList);

			// 역할 권한 삭제
			aclSystemDao.removeRolePermission(permissionIdList);

			// 고용형태 권한 삭제
			aclSystemDao.removeJobClassPermission(permissionIdList);

			// 직책 권한 삭제
			aclSystemDao.removeJobDutyPermission(permissionIdList);
			
			// 사용자 예외 권한 삭제
			aclSystemDao.removeExpUserPermission(permissionIdList);

			// 베이스 권한 삭제
			aclSystemDao.removeBasePermission(permissionIdList);
		}
	}

	/**
	 * 정적 권한 정보에 매핑된 사용자 정보를 삭제한다.
	 * 
	 * @param permissionIdList
	 * @param portalId
	 */
	private void deleteSystemPermissionByPortalId(String permissionId, String portalId) {
		// 권한 삭제 루틴
		if (permissionId != null) {
			// 그룹 권한 삭제
			aclSystemDao.removeGroupPermission(permissionId, portalId);

			// 사용자 권한 삭제
			aclSystemDao.removeUserPermission(permissionId, portalId);

			// 역할 권한 삭제
			aclSystemDao.removeRolePermission(permissionId, portalId);

			// 고용형태 권한 삭제
			aclSystemDao.removeJobClassPermission(permissionId, portalId);

			// 직책 권한 삭제
			aclSystemDao.removeJobDutyPermission(permissionId, portalId);
			
			// 사용자 권한 삭제
			aclSystemDao.removeExpUserPermission(permissionId, portalId);			
		}
	}

	/**
	 * 동적(Content) 접근 리소스에 대한 권한 체크 (단일 Operation)
	 * 
	 * @param className - 권한 자원의 종류
	 * @param operationName - 권한 자원에 대한 오퍼레이션
	 * @param resourceName - 권한 자원의 이름
	 * @param userId - 로그인한 유저아이디
	 * @return - 접근 권한이 있으면 true, 접근 권한이 없으면 false를 리턴
	 */
	public boolean hasContentPermission(String className, String operationName, String resourceName, String userId) {
		String[] operationNames = { operationName };
		return hasContentPermission(className, operationNames, resourceName, userId);
	}

	/**
	 * 동적(Content) 접근 리소스에 대한 권한 체크 (멀티 Operation)
	 * 
	 * @param className - 권한 자원의 종류
	 * @param operationNames - 권한 자원에 대한 멀티 오퍼레이션
	 * @param resourceName - 권한 자원의 이름
	 * @param userId - 로그인한 유저아이디
	 * @return - 접근 권한이 있으면 true, 접근 권한이 없으면 false를 리턴
	 */
	public boolean hasContentPermission(String className, String[] operationNames, String resourceName, String userId) {
		// resource의 접근 권한 조회
		List<Map<String, Object>> permissionList = this.aclContentDao.listResourcePermission(className, operationNames,
				resourceName);

		// 결과 데이터가 없을 경우에는 접근 권한이 없음
		if (permissionList.size() < 1) {
			return Boolean.FALSE;
		}

		Map<String, Object> permissionMap = null;
		String[] permisionIds = new String[permissionList.size()];

		// resource의 오픈 여부가 1 이거나, Resource 소유자일 경우
		for (int index = 0; index < permissionList.size(); index++) {
			permissionMap = permissionList.get(index);

			if ((Integer) permissionMap.get("open") == 1 || permissionMap.get("owner").toString().equals(userId)) {
				return Boolean.TRUE;
			}

			permisionIds[index] = permissionMap.get("permissionId").toString();
		}

		List<Object> allPermissionList = this.aclContentDao.listAllPermission(permisionIds, userId);
		
		List<Object> exceptPermissionList = this.aclContentDao.listExpPermission(permisionIds, userId);

		// userPermissionCount > 0 면, 사용자권한/역할권한/그룹권한 중에 하나 이상의 접근 권한이 존재
		//return allPermissionList.size() > 0;
		
		if(allPermissionList.size() > 0 && !(exceptPermissionList.size() > 0)) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	/**
	 * 동적(Content) 접근 권한 생성 - 베이스
	 * 
	 * @param aclResourceWithPermission
	 */
	public void createContentPermission(ACLResourcePermission aclResourcePermission) {
		// Resource Name으로 resourceId를 찾기
		String className = aclResourcePermission.getClassName();
		String resourceName = aclResourcePermission.getResourceName();

		String resourceId = aclContentDao.getResourceId(className, resourceName);

		if (resourceId == null || "".equals(resourceId)) {
			// Get the resource id.
			resourceId = idgenService.getNextId();
			// 리소스 생성
			aclContentDao.createResource(aclResourcePermission, resourceId);
		}

		// Get the permission id.
		String permissionId = idgenService.getNextId();

		// 퍼미션 생성
		createContentBasePermission(aclResourcePermission, resourceId, permissionId);

		if (aclResourcePermission.getOpen() == 0) {
			// 지정된 사용자에게 퍼미션 부여
			createContentUserPermission(aclResourcePermission, permissionId);

			// 사용자가 소속된 그룹에게 퍼미션 부여
			createContentGroupPermission(aclResourcePermission, permissionId);

			// 역할 권한 부여
			createContentRolePermission(aclResourcePermission, permissionId);

			// 고용 형태 권한 부여
			createContentJobClassPermission(aclResourcePermission, permissionId);

			// 직책 권한 부여
			createContentJobDutyPermission(aclResourcePermission, permissionId);
			
			// 예외 사용자 권한 부여
			createContentExpUserPermission(aclResourcePermission, permissionId);
		}
	}

	/**
	 * 동적(Content) 접근 권한 삭제
	 * 
	 * @param className
	 * @param resourceName
	 */
	public void deleteContentPermission(String className, String resourceName) {
		// 클래스 이름과 리소스 이름에 매칭되는 리소스 아이디를 가져온다.
		String resourceId = aclContentDao.getResourceId(className, resourceName);

		// 리소스 아이디에 해당하는 권한 아이디 리스트를 가져온다.
		List<String> permissionIdList = aclContentDao.listPermissionId(resourceId);

		// 권한 삭제 및 리소스 삭제 루틴
		if (permissionIdList != null && permissionIdList.size() > 0) {
			// 그룹 권한 삭제
			aclContentDao.removeGroupPermission(permissionIdList);

			// 사용자 권한 삭제
			aclContentDao.removeUserPermission(permissionIdList);

			// 역할 권한 삭제
			aclContentDao.removeRolePermission(permissionIdList);

			// 고용형태 권한 삭제
			aclContentDao.removeJobClassPermission(permissionIdList);

			// 직책 권한 삭제
			aclContentDao.removeJobDutyPermission(permissionIdList);
			
			// 사용자 예외 권한 삭제
			aclContentDao.removeExpUserPermission(permissionIdList);

			// 베이스 권한 삭제
			aclContentDao.removeBasePermission(permissionIdList);
		}

		// 리소스 삭제
		aclContentDao.removeReource(resourceId);
	}

	/**
	 * 동적(Content) 리소스에 대한 퍼미션 정보를 가져온다.
	 * 
	 * @param className
	 * @param resourceName
	 * @param operationName
	 */
	public ACLResourcePermission getContentPermission(String className, String resourceName, String operationName) {
		// Get the base permission
		Map<String, Object> resultMap = aclContentDao.getBasePermission(className, resourceName, operationName);

		// 리소스에 대한 권한 정보가 없을 경우에는 null을 리턴한다.
		if (resultMap == null) {
			return null;
		}

		int open = Integer.parseInt(resultMap.get("open").toString());
		String permissionName = resultMap.get("permissionName").toString();
		String permissionDescription = resultMap.get("permissionDescription").toString();
		String userId = resultMap.get("userId").toString();
		String userName = resultMap.get("userName").toString();

		// 권한 아이디
		String permissionId = resultMap.get("permissionId").toString();

		// 그룹 권한 정보
		List<ACLGroupPermission> groupPermissionList = aclContentDao.listGroupPermission(permissionId);

		// 롤 권한 정보
		List<String> roleIdList = aclContentDao.listRolePermission(permissionId);

		// 사용자 권한 정보
		List<String> assignUserIdList = aclContentDao.listUserPermission(permissionId);

		// 고용형태 권한 정보
		List<String> jobClassCodeList = aclContentDao.listJobClassPermission(permissionId);

		// 직책 권한 정보
		List<String> jobDutyCodeList = aclContentDao.listJobDutyPermission(permissionId);
		
		// 사용자 권한 정보
		List<String> exceptUserIdList = aclContentDao.listExpUserPermission(permissionId);

		// 모델 세팅
		ACLResourcePermission aclResourcePermission = new ACLResourcePermission();

		// 베이스 퍼미션
		aclResourcePermission.setClassName(className);
		aclResourcePermission.setResourceName(resourceName);
		aclResourcePermission.setUserId(userId);
		aclResourcePermission.setUserName(userName);
		aclResourcePermission.setOpen(open);
		aclResourcePermission.setPermissionName(permissionName);
		aclResourcePermission.setPermissionDescription(permissionDescription);
		aclResourcePermission.setOperationName(operationName);

		// 그룹 권한 정보 세팅
		aclResourcePermission.setGroupPermissionList(groupPermissionList);
		// 롤 권한 정보 세팅
		aclResourcePermission.setRoleIdList(roleIdList);
		// 사용자 권한 정보 세팅
		aclResourcePermission.setAssignUserIdList(assignUserIdList);
		// 고용 형태 권한 정보 세팅
		aclResourcePermission.setJobClassCodeList(jobClassCodeList);
		// 직책 권한 정보 세팅
		aclResourcePermission.setJobDutyCodeList(jobDutyCodeList);
		// 사용자 예외 권한 정보 세팅
		aclResourcePermission.setExceptUserIdList(exceptUserIdList);

		return aclResourcePermission;
	}

	/**
	 * 동적(Content) 리소스에 대한 권한 정보를 수정한다.
	 * 
	 * @param aclResourcePermission
	 */
	public void updateContentPermission(ACLResourcePermission aclResourcePermission) {
		String className = aclResourcePermission.getClassName();
		String resourceName = aclResourcePermission.getResourceName();
		String operationName = aclResourcePermission.getOperationName();

		// 리소스 아이디에 해당하는 정보를 가져온다.
		Map<String, Object> permissionMap = aclContentDao.getBasePermission(className, resourceName, operationName);
		String permissionId = permissionMap.get("permissionId").toString();
		String resourceId = permissionMap.get("resourceId").toString();

		List<String> permissionIdList = new ArrayList<String>();

		permissionIdList.add(permissionId);

		// 권한 삭제 루틴
		deleteContentPermission(permissionIdList);

		// 권한 생성 루틴
		// 새로 생성할 권한 아이디를 발급 받는다.
		permissionId = idgenService.getNextId();

		// 기본 권한 정보를 생성한다.
		createContentBasePermission(aclResourcePermission, resourceId, permissionId);

		if (aclResourcePermission.getOpen() == 0) {
			// 지정된 사용자에게 퍼미션 부여
			createContentUserPermission(aclResourcePermission, permissionId);

			// 사용자가 소속된 그룹에게 퍼미션 부여
			createContentGroupPermission(aclResourcePermission, permissionId);

			// 역할 권한 부여
			createContentRolePermission(aclResourcePermission, permissionId);

			// 고용 형태 권한 부여
			createContentJobClassPermission(aclResourcePermission, permissionId);

			// 직책 권한 부여
			createContentJobDutyPermission(aclResourcePermission, permissionId);
			
			// 지정된 사용자에게 예외 퍼미션 부여
			createContentExpUserPermission(aclResourcePermission, permissionId);
		}
	}

	/**
	 * 동적(Content) 리소스에 대한 퍼미션을 생성한다.
	 * 
	 * @param aclResourcePermission
	 * @param resourceId
	 */
	private void createContentBasePermission(ACLResourcePermission aclResourcePermission, String resourceId,
			String permissionId) {
		boolean isPermissionNameEmpty = (aclResourcePermission.getPermissionName() == null)
				|| ("".equals(aclResourcePermission.getPermissionName()));
		boolean isPermissionDescriptionEmpty = (aclResourcePermission.getPermissionDescription() == null)
				|| ("".equals(aclResourcePermission.getPermissionDescription()));

		// permissionName에 값이 없을 경우, resourceName으로 대체
		if (isPermissionNameEmpty) {
			aclResourcePermission.setPermissionName(aclResourcePermission.getResourceName());
		}
		// permissionDescription에 값이 없을 경우, resourceDescription으로 대체
		if (isPermissionDescriptionEmpty) {
			aclResourcePermission.setPermissionDescription(aclResourcePermission.getResourceDescription());
		}

		aclContentDao.createBasePermission(aclResourcePermission, resourceId, permissionId);
	}

	/**
	 * 지정된 사용자에게 퍼미션을 부여한다.- 동적(Content)
	 * 
	 * @param aclResourcePermission
	 * @param permissionId
	 */
	private void createContentExpUserPermission(ACLResourcePermission aclResourcePermission, String permissionId) {
		boolean isEmpty = (aclResourcePermission.getExceptUserIdList() == null)
				|| (aclResourcePermission.getExceptUserIdList().size() == 0);

		if (isEmpty) {
			return; // assign user가 없을 경우
		}

		aclContentDao.createExpUserPermission(aclResourcePermission, permissionId);
	}

	/**
	 * 지정된 역할에게 퍼미션을 부여한다. - 동적(Content)
	 * 
	 * @param aclResourcePermission
	 * @param permissionId
	 */
	private void createContentGroupPermission(ACLResourcePermission aclResourcePermission, String permissionId) {
		boolean isEmpty = (aclResourcePermission.getGroupPermissionList() == null)
				|| (aclResourcePermission.getGroupPermissionList().size() == 0);

		if (isEmpty) {
			return; // 그룹 퍼미션이 없을 경우
		}

		aclContentDao.createGroupPermission(aclResourcePermission, permissionId);
	}

	/**
	 * 지정된 롤에게 퍼미션을 부여한다. - 동적(Content)
	 * 
	 * @param aclResourcePermission
	 * @param permissionId
	 */
	private void createContentRolePermission(ACLResourcePermission aclResourcePermission, String permissionId) {
		boolean isEmpty = (aclResourcePermission.getRoleIdList() == null)
				|| (aclResourcePermission.getRoleIdList().size() == 0);

		if (isEmpty) {
			return; // 역할 퍼미션이 없을 경우
		}

		aclContentDao.createRolePermission(aclResourcePermission, permissionId);
	}

	/**
	 * 고용 형태별 권한 부여 - 동적(Content)
	 * 
	 * @param aclResourcePermission
	 * @param permissionId
	 */
	private void createContentJobClassPermission(ACLResourcePermission aclResourcePermission, String permissionId) {
		boolean isEmpty = (aclResourcePermission.getJobClassCodeList() == null)
				|| (aclResourcePermission.getJobClassCodeList().size() == 0);

		if (isEmpty) {
			return; // 역할 퍼미션이 없을 경우
		}

		aclContentDao.createJobClassPermission(aclResourcePermission, permissionId);
	}

	/**
	 * 직책별 권한 부여 - 동적(Content)
	 * 
	 * @param aclResourcePermission
	 * @param permissionId
	 */
	private void createContentJobDutyPermission(ACLResourcePermission aclResourcePermission, String permissionId) {
		boolean isEmpty = (aclResourcePermission.getJobDutyCodeList() == null)
				|| (aclResourcePermission.getJobDutyCodeList().size() == 0);

		if (isEmpty) {
			return; // 역할 퍼미션이 없을 경우
		}

		aclContentDao.createJobDutyPermission(aclResourcePermission, permissionId);
	}
	
	/**
	 * 지정된 사용자에게 예외 퍼미션을 부여한다.- 동적(Content)
	 * 
	 * @param aclResourcePermission
	 * @param permissionId
	 */
	private void createContentUserPermission(ACLResourcePermission aclResourcePermission, String permissionId) {
		boolean isEmpty = (aclResourcePermission.getAssignUserIdList() == null)
				|| (aclResourcePermission.getAssignUserIdList().size() == 0);

		if (isEmpty) {
			return; // assign user가 없을 경우
		}

		aclContentDao.createUserPermission(aclResourcePermission, permissionId);
	}

	/**
	 * 권한 정보를 삭제한다. - 동적(Content)
	 * 
	 * @param permissionIdList
	 */
	private void deleteContentPermission(List<String> permissionIdList) {
		// 권한 삭제 루틴
		if (permissionIdList != null && permissionIdList.size() > 0) {
			// 그룹 권한 삭제
			aclContentDao.removeGroupPermission(permissionIdList);

			// 사용자 권한 삭제
			aclContentDao.removeUserPermission(permissionIdList);

			// 역할 권한 삭제
			aclContentDao.removeRolePermission(permissionIdList);

			// 고용형태 권한 삭제
			aclContentDao.removeJobClassPermission(permissionIdList);

			// 직책 권한 삭제
			aclContentDao.removeJobDutyPermission(permissionIdList);
			
			// 사용자 예외 권한 삭제
			aclContentDao.removeExpUserPermission(permissionIdList);

			// 베이스 권한 삭제
			aclContentDao.removeBasePermission(permissionIdList);
		}
	}

	/**
	 * 권한 상세 정보를 조회
	 * 
	 * @param aclResourcePermission
	 */
	public ACLResourcePermission listDetailPermission(ACLResourcePermission aclResourcePermission) {

		String portalId = (String) getRequestAttribute("ikep.portalId");
		User sessionuser = (User) this.getRequestAttribute("ikep.user");

		// 사용자 권한 상세조회
		if (aclResourcePermission.getAssignUserIdList() != null
				&& aclResourcePermission.getAssignUserIdList().size() > 0) {

			List<User> assignUserDetailList = aclCommonDao.listUserDetailPermission(
					aclResourcePermission.getAssignUserIdList(), portalId);

			if (assignUserDetailList != null) {
				if (!sessionuser.getLocaleCode().equals("ko")) {
					for (User user : assignUserDetailList) {
						user.setUserName(user.getUserEnglishName());
						user.setTeamName(user.getTeamEnglishName());
						user.setJobTitleName(user.getJobTitleEnglishName());
					}
				}
			}

			aclResourcePermission.setAssignUserDetailList(assignUserDetailList);
		}

		// 그룹 권한 상세조회
		List<Group> groupDetailList = getGroupDetailList(aclResourcePermission, portalId);
		if (groupDetailList != null) {
			if (!sessionuser.getLocaleCode().equals("ko")) {
				for (Group group : groupDetailList) {
					group.setGroupName(group.getGroupEnglishName());
				}
			}
		}

		aclResourcePermission.setGroupDetailList(groupDetailList);

		// 역할 권한 상세조회
		if (aclResourcePermission.getRoleIdList() != null && aclResourcePermission.getRoleIdList().size() > 0) {
			aclResourcePermission.setRoleDetailList(aclCommonDao.listRoleDetailPermission(
					aclResourcePermission.getRoleIdList(), portalId));
		}

		// 고용형태 권한 상세조회
		if (aclResourcePermission.getJobClassCodeList() != null
				&& aclResourcePermission.getJobClassCodeList().size() > 0) {
			aclResourcePermission.setJobClassDetailList(aclCommonDao.listJobClassDetailPermission(
					aclResourcePermission.getJobClassCodeList(), portalId));
		}

		// 직책 권한 상세조회
		if (aclResourcePermission.getJobDutyCodeList() != null && aclResourcePermission.getJobDutyCodeList().size() > 0) {
			aclResourcePermission.setJobDutyDetailList(aclCommonDao.listJobDutyDetailPermission(
					aclResourcePermission.getJobDutyCodeList(), portalId));
		}
		
		// 사용자 예외 권한 상세조회
		if (aclResourcePermission.getExceptUserIdList() != null
				&& aclResourcePermission.getExceptUserIdList().size() > 0) {

			List<User> exceptUserDetailList = aclCommonDao.listUserDetailPermission(
					aclResourcePermission.getExceptUserIdList(), portalId);

			if (exceptUserDetailList != null) {
				if (!sessionuser.getLocaleCode().equals("ko")) {
					for (User user : exceptUserDetailList) {
						user.setUserName(user.getUserEnglishName());
						user.setTeamName(user.getTeamEnglishName());
						user.setJobTitleName(user.getJobTitleEnglishName());
					}
				}
			}

			aclResourcePermission.setExceptUserDetailList(exceptUserDetailList);
		}

		return aclResourcePermission;
	}

	private List<Group> getGroupDetailList(ACLResourcePermission aclResourcePermission, String portalId) {
		List<Group> groupDetailList = null;

		if (aclResourcePermission.getGroupPermissionList() != null
				&& aclResourcePermission.getGroupPermissionList().size() > 0) {
			List<String> groupIdPermissionList = new ArrayList<String>();

			for (int i = 0; i < aclResourcePermission.getGroupPermissionList().size(); i++) {
				groupIdPermissionList.add(aclResourcePermission.getGroupPermissionList().get(i).getGroupId());
			}

			groupDetailList = aclCommonDao.listGroupDetailPermission(groupIdPermissionList, portalId);
		}

		return groupDetailList;
	}

	/**
	 * 세션 정보 얻기
	 * 
	 * @param name
	 * @return
	 */
	private Object getRequestAttribute(String name) {
		return RequestContextHolder.currentRequestAttributes().getAttribute(name, RequestAttributes.SCOPE_SESSION);
	}
}
