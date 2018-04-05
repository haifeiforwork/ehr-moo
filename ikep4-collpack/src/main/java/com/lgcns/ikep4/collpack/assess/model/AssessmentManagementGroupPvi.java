/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.assess.model;



/**
 * Assessment Management AssessmentManagementGroupPvi model
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: AssessmentManagementGroupPvi.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class AssessmentManagementGroupPvi extends AssessmentManagementGroupPviBody {

	/**
	 *
	 */
	private static final long serialVersionUID = 3489721297893930136L; 

	/**
	 * 그룹명 (Locale 관련)
	 */
	private String groupName;

	/**
	 * 그룹 영문명 (Locale 관련)
	 */
	private String groupEnglishName;

	/**
	 * 생성자
	 */
	public AssessmentManagementGroupPvi() {
		super();
	}

	/**
	 * 생성자
	 * @param groupId - 그룹ID
	 */
	public AssessmentManagementGroupPvi(String groupId) {
		super(groupId);
	}

	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * @param groupName the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	/**
	 * @return the groupEnglishName
	 */
	public String getGroupEnglishName() {
		return groupEnglishName;
	}

	/**
	 * @param groupEnglishName the groupEnglishName to set
	 */
	public void setGroupEnglishName(String groupEnglishName) {
		this.groupEnglishName = groupEnglishName;
	}

}
