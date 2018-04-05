/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.security.acl.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * 그룹 권한 모델
 * 
 * @author 주길재
 * @version $Id: ACLGroupPermission.java 16276 2011-08-18 07:09:07Z giljae $
 */
public class ACLGroupPermission extends BaseObject {
	/**
	 * Use serialVersionUID science iKEP 4.0.0
	 */
	private static final long serialVersionUID = 8140651603975674236L;

	private String groupId;

	private int hierarchyPermission;

	public String getGroupId() {
		return groupId;
	}

	/**
	 * 권한을 부여할 그룹 아이디
	 * 
	 * @param groupId
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public int getHierarchyPermission() {
		return hierarchyPermission;
	}

	/**
	 * 계층 권한 여부<br/>
	 * 계층 권한일 경우 = 1<br/>
	 * 계층 권한이 아닐 경우 = 0
	 * 
	 * @param hierarchyPermission
	 */
	public void setHierarchyPermission(int hierarchyPermission) {
		this.hierarchyPermission = hierarchyPermission;
	}

}
