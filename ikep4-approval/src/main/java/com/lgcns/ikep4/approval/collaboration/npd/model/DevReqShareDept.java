package com.lgcns.ikep4.approval.collaboration.npd.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;

public class DevReqShareDept extends BaseObject {
	/**
	 * 개발 검토 의뢰서의 관리번호
	 */
	private String mgntNo;
	
	/**
	 * 회람부서코드
	 */
	private String shareDeptId;
	
	/**
	 * 회람부서명
	 */
	private String shareDeptNm;

	/**
	 * @return the mgntNo
	 */
	public String getMgntNo() {
		return mgntNo;
	}

	/**
	 * @param mgntNo the mgntNo to set
	 */
	public void setMgntNo(String mgntNo) {
		this.mgntNo = mgntNo;
	}

	/**
	 * @return the shareDeptId
	 */
	public String getShareDeptId() {
		return shareDeptId;
	}

	/**
	 * @param shareDeptId the shareDeptId to set
	 */
	public void setShareDeptId(String shareDeptId) {
		this.shareDeptId = shareDeptId;
	}

	/**
	 * @return the shareDeptNm
	 */
	public String getShareDeptNm() {
		return shareDeptNm;
	}

	/**
	 * @param shareDeptNm the shareDeptNm to set
	 */
	public void setShareDeptNm(String shareDeptNm) {
		this.shareDeptNm = shareDeptNm;
	}
}
