/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.admin.screen.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;
import com.lgcns.ikep4.support.security.acl.model.ACLResourcePermission;

/**
 * TODO 시큐리티 VO
 *
 * @author 한승환
 * @version $Id: PortalSecurity.java 17346 2012-02-28 08:43:22Z yruyo $
 */
public class PortalSecurity extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = -6461884533884471953L;
	/**
	 * read 퍼미션
	 */
	private ACLResourcePermission aclResourcePermissionRead;
	
	/**
	 * 클래스 명
	 */
	private String className;
	
	/**
	 * 리소스 명
	 */
	private String resourceName;
	
	/**
	 * 오퍼레이션 명
	 */
	private String operationName;
	
	/**
	 * 리소스 설명
	 */
	private String resourceDescription;
	
	/**
	 * 퍼미션 명
	 */
	private String permissionName;
	
	/**
	 * 퍼미션 설명
	 */
	private String permissionDescription;
	
	/**
	 * 사용자 권한 배열
	 */
	private String[] addrUserList;
	
	/**
	 * 그룹 권한 배열
	 */
	private String[] addrGroupTypeList;
	
	/**
	 * 역할 권한 배열
	 */
	private String[] addrRoleList;
	
	/**
	 * 고용형태 권한 배열
	 */
	private String[] addrJobClassList;
	
	/**
	 * 직책 권한 배열
	 */
	private String[] addrJobDutyList;
	
	/**
	 * 예외 사용자 권한 배열
	 */
	private String[] addrExpUserList;
	
	
	/**
	 * 사용자 권한
	 */
	private String addrUserListAll;
	
	/**
	 * 그룹 권한
	 */
	private String addrGroupTypeListAll;
	
	/**
	 * 역할 권한
	 */
	private String addrRoleListAll;
	
	/**
	 * 고용형태 권한 
	 */
	private String addrJobClassListAll;
	
	/**
	 * 직책 권한
	 */
	private String addrJobDutyListAll;
	
	/**
	 * 예외 사용자 권한
	 */
	private String addrExpUserListAll;
	
	
	/**
	 * 사용자 아이디
	 */
	private String ownerId;
	
	/**
	 * 사용자명 
	 */
	private String ownerName;
	
	
	public String getAddrUserListAll() {
		return addrUserListAll;
	}

	public void setAddrUserListAll(String addrUserListAll) {
		this.addrUserListAll = addrUserListAll;
	}

	public String getAddrGroupTypeListAll() {
		return addrGroupTypeListAll;
	}

	public void setAddrGroupTypeListAll(String addrGroupTypeListAll) {
		this.addrGroupTypeListAll = addrGroupTypeListAll;
	}

	public String getAddrRoleListAll() {
		return addrRoleListAll;
	}

	public void setAddrRoleListAll(String addrRoleListAll) {
		this.addrRoleListAll = addrRoleListAll;
	}

	public String getAddrJobClassListAll() {
		return addrJobClassListAll;
	}

	public void setAddrJobClassListAll(String addrJobClassListAll) {
		this.addrJobClassListAll = addrJobClassListAll;
	}

	public String getAddrJobDutyListAll() {
		return addrJobDutyListAll;
	}

	public void setAddrJobDutyListAll(String addrJobDutyListAll) {
		this.addrJobDutyListAll = addrJobDutyListAll;
	}

	public String getAddrExpUserListAll() {
		return addrExpUserListAll;
	}

	public void setAddrExpUserListAll(String addrExpUserListAll) {
		this.addrExpUserListAll = addrExpUserListAll;
	}

	public String[] getAddrUserList() {
		return addrUserList;
	}

	public void setAddrUserList(String[] addrUserList) {
		this.addrUserList = new String[addrUserList.length];
		for(int i = 0; i< addrUserList.length; i++)
		{
			this.addrUserList[i] = addrUserList[i];
		}
		
	}

	public String[] getAddrGroupTypeList() {
		return addrGroupTypeList;
	}

	public void setAddrGroupTypeList(String[] addrGroupTypeList) {
		this.addrGroupTypeList = new String[addrGroupTypeList.length];
		for(int i = 0; i< addrGroupTypeList.length; i++)
		{
			this.addrGroupTypeList[i] = addrGroupTypeList[i];
		}
	}

	public String[] getAddrRoleList() {
		return addrRoleList;
	}

	public void setAddrRoleList(String[] addrRoleList) {
		this.addrRoleList = new String[addrRoleList.length];
		for(int i = 0; i< addrRoleList.length; i++)
		{
			this.addrRoleList[i] = addrRoleList[i];
		}
	}

	public String[] getAddrJobClassList() {
		return addrJobClassList;
	}

	public void setAddrJobClassList(String[] addrJobClassList) {
		this.addrJobClassList = new String[addrJobClassList.length];
		for(int i = 0; i< addrJobClassList.length; i++)
		{
			this.addrJobClassList[i] = addrJobClassList[i];
		}
	}

	public String[] getAddrJobDutyList() {
		return addrJobDutyList;
	}

	public void setAddrJobDutyList(String[] addrJobDutyList) {
		this.addrJobDutyList = new String[addrJobDutyList.length];
		for(int i = 0; i< addrJobDutyList.length; i++)
		{
			this.addrJobDutyList[i] = addrJobDutyList[i];
		}
	}

	public String[] getAddrExpUserList() {
		return addrExpUserList;
	}

	public void setAddrExpUserList(String[] addrExpUserList) {
		this.addrExpUserList = new String[addrExpUserList.length];
		for(int i = 0; i< addrExpUserList.length; i++)
		{
			this.addrExpUserList[i] = addrExpUserList[i];
		}
	}

	/**
	 * 그룹 권한
	 */
	private String addrGroupType;
	
	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public String getResourceDescription() {
		return resourceDescription;
	}

	public void setResourceDescription(String resourceDescription) {
		this.resourceDescription = resourceDescription;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	public String getPermissionDescription() {
		return permissionDescription;
	}

	public void setPermissionDescription(String permissionDescription) {
		this.permissionDescription = permissionDescription;
	}

	/**
	 * 그룹 권한 수
	 */
	private int groupTypeCount;

	public ACLResourcePermission getAclResourcePermissionRead() {
		return aclResourcePermissionRead;
	}

	public void setAclResourcePermissionRead(ACLResourcePermission aclResourcePermissionRead) {
		this.aclResourcePermissionRead = aclResourcePermissionRead;
	}

	

	public String getAddrGroupType() {
		return addrGroupType;
	}

	public void setAddrGroupType(String addrGroupType) {
		this.addrGroupType = addrGroupType;
	}

	public int getGroupTypeCount() {
		return groupTypeCount;
	}

	public void setGroupTypeCount(int groupTypeCount) {
		this.groupTypeCount = groupTypeCount;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
}
