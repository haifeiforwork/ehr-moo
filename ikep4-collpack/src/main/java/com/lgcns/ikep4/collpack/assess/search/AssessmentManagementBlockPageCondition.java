/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.assess.search;

import com.lgcns.ikep4.support.base.pagecondition.BlockPageCondition;

/**
 * Assessment Management 페이징
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: AssessmentManagementBlockPageCondition.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class AssessmentManagementBlockPageCondition extends BlockPageCondition {

	/**
	 *
	 */
	private static final long serialVersionUID = -8206437288252606814L;

	/**
	 * 포털 ID
	 */
	private String portalId;

	/**
	 * 사용자명 조회에 사용
	 */
	private String userName;

	/**
	 * PVI점수 정렬방식
	 */
	private int pviOrder;

	/**
	 * Excel Download 여부
	 */
	private boolean excel;

	/**
	 * 그룹 ID
	 */
	private String groupId;

	/**
	 * 생성자
	 */
	public AssessmentManagementBlockPageCondition() {
		super();

		pviOrder = DESC;
		excel = false;
	}

	/**
	 * @return the portalId
	 */
	public String getPortalId() {
		return portalId;
	}

	/**
	 * @param portalId the portalId to set
	 */
	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the pviOrder
	 */
	public int getPviOrder() {
		return pviOrder;
	}

	/**
	 * @param pviOrder the pviOrder to set
	 */
	public void setPviOrder(int pviOrder) {
		this.pviOrder = pviOrder;
	}

	/**
	 * @return the excel
	 */
	public boolean isExcel() {
		return excel;
	}

	/**
	 * @param excel the excel to set
	 */
	public void setExcel(boolean excel) {
		this.excel = excel;
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
