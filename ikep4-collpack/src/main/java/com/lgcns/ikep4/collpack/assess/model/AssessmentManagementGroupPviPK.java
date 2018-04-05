/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.assess.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * Assessment Management AssessmentManagementGroupPviPK model
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: AssessmentManagementGroupPviPK.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class AssessmentManagementGroupPviPK extends BaseObject { 

	/**
	 *
	 */
	private static final long serialVersionUID = -8636413290015414386L;

	/**
	 * 조직 그룹 ID
	 */
	private String groupId;

	/**
	 * 생성자
	 */
	public AssessmentManagementGroupPviPK() {
	}

	/**
	 * 생성자
	 * @param groupId - 그룹ID
	 */
	public AssessmentManagementGroupPviPK(String groupId) {
		this.groupId = groupId;
	}

	/**
	 * @return the groupId
	 */
	public String getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId the groupId to set
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

}
