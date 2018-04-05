/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.security.acl.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.support.security.acl.model.ACLResourcePermission;
import com.lgcns.ikep4.support.user.group.model.Group;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * Access Control List(ACL) Service
 * 
 * @author 주길재(giljae@gmail.com)
 * @version $Id: AccessControlListService.java 1137 2011-02-23 06:57:18Z giljae
 *          $
 */
@Transactional
public interface ACLService {
	/**
	 * 정적(System) 접근 리소스에 대한 권한 체크 (단일 Operation)
	 * 
	 * @param className - 권한 자원의 타입
	 * @param operationName - 권한 자원에 대한 오퍼레이션
	 * @param resourceName - 권한 자원의 이름
	 * @param userId - 로그인한 유저아이디
	 * @return - 접근 권한이 있으면 true, 접근 권한이 없으면 false를 리턴
	 */
	public boolean hasSystemPermission(String className, String operationName, String resourceName, String userId);

	/**
	 * 정적(System) 접근 리소스에 대한 권한 체크 (멀티 Operation)
	 * 
	 * @param className - 권한 자원의 타입
	 * @param operationNames - 권한 자원에 대한 멀티 오퍼레이션
	 * @param resourceName - 권한 자원의 이름
	 * @param userId - 로그인한 유저아이디
	 * @return - 접근 권한이 있으면 true, 접근 권한이 없으면 false를 리턴
	 */
	public boolean hasSystemPermission(String className, String[] operationName, String resourceName, String userId);

	/**
	 * 정적 (System) 접근 리소스의 관리 권한이 있는 사용자 리스트를 리턴
	 * @param sysName
	 * @return
	 */
	public List<User> listSystemAdminAsUser(String sysName);
	
	/**
	 * 정적 (System) 접근 리소스의 관리 권한이 있는 그룹 리스트를 리턴
	 * @param sysName
	 * @return
	 */
	public List<Group> listSystemAdminAsGroup(String sysName);
	
	/**
	 * 정적 (System) 접근 리소스의 관리 권한이 있는 유저+그룹(리스트에 속해 있는 유저)을 리턴 
	 * @param sysName
	 * @return
	 */
	public List<User> listSystemAdminAsAll(String sysName);
	
	/**
	 * 정적(System) 접근 리소스의 관리 권한이 있는 사용자인지 체크
	 * 
	 * @param sysName
	 * @param userId
	 * @return
	 */
	public boolean isSystemAdmin(String sysName, String userId);

	/**
	 * 정적(System) 접근 권한 생성
	 * 
	 * @param aclResourcePermission
	 */
	public void createSystemPermission(ACLResourcePermission aclResourcePermission);

	/**
	 * 정적(System) 접근 권한 삭제<br/>
	 * 리소스와 연관된 권한 정보를 삭제한다.<br/>
	 * <b>유의사항: 리소스가 삭제될 경우에만 사용한다.</b><br/>
	 * 
	 * @param className
	 * @param resourceName
	 */
	public void deleteSystemPermission(String className, String resourceName);

	/**
	 * 정적(System) 리소스에 대한 권한 정보를 가져온다.
	 * 
	 * @param className
	 * @param resourceName
	 * @param operationName
	 * @return
	 */
	public ACLResourcePermission getSystemPermission(String className, String resourceName, String operationName);

	/**
	 * 정적(System) 리소스에 대한 권한 정보를 수정한다. (리소스 삭제후 등록)
	 * 
	 * @param aclResourcePermission
	 */
	public void updateSystemPermissionAndResource(ACLResourcePermission aclResourcePermission);

	/**
	 * 정적(System) 리소스에 대한 권한 정보를 수정한다. (리소스 삭제X, 관련 유저만 업데이트)
	 * 
	 * @param aclResourcePermission
	 * @param portalId
	 */
	public void updateSystemPermission(ACLResourcePermission aclResourcePermission, String portalId);

	/**
	 * 동적(Content) 접근 리소스에 대한 권한 체크 (단일 Operation)
	 * 
	 * @param className - 권한 자원의 타입
	 * @param operationName - 권한 자원에 대한 오퍼레이션
	 * @param resourceName - 권한 자원의 이름
	 * @param userId - 로그인한 유저아이디
	 * @return - 접근 권한이 있으면 true, 접근 권한이 없으면 false를 리턴
	 */
	public boolean hasContentPermission(String className, String operationName, String resourceName, String userId);

	/**
	 * 동적(Content) 접근 리소스에 대한 권한 체크 (멀티 Operation)
	 * 
	 * @param className - 권한 자원의 타입
	 * @param operationNames - 권한 자원에 대한 멀티 오퍼레이션
	 * @param resourceName - 권한 자원의 이름
	 * @param userId - 로그인한 유저아이디
	 * @return - 접근 권한이 있으면 true, 접근 권한이 없으면 false를 리턴
	 */
	public boolean hasContentPermission(String className, String[] operationName, String resourceName, String userId);

	/**
	 * 동적(Content) 접근 권한 생성
	 * 
	 * @param aclResourcePermission
	 */
	public void createContentPermission(ACLResourcePermission aclResourcePermission);

	/**
	 * 동적(Content) 접근 권한 삭제<br/>
	 * 리소스와 연관된 권한 정보를 삭제한다.<br/>
	 * <b>유의사항: 리소스가 삭제될 경우에만 사용한다.</b><br/>
	 * 
	 * @param className
	 * @param resourceName
	 */
	public void deleteContentPermission(String className, String resourceName);

	/**
	 * 동적(Content) 리소스에 대한 권한 정보를 가져온다.
	 * 
	 * @param className
	 * @param resourceName
	 * @param operationName
	 * @return
	 */
	public ACLResourcePermission getContentPermission(String className, String resourceName, String operationName);

	/**
	 * 동적(Content) 리소스에 대한 권한 정보를 수정한다.
	 * 
	 * @param aclResourcePermission
	 */
	public void updateContentPermission(ACLResourcePermission aclResourcePermission);

	/**
	 * 권한 상세 정보를 조회
	 * 
	 * @param aclResourcePermission
	 */
	public ACLResourcePermission listDetailPermission(ACLResourcePermission aclResourcePermission);
}
