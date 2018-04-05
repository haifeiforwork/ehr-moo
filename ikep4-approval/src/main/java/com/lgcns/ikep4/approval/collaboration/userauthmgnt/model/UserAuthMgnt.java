/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.collaboration.userauthmgnt.model;

import java.util.Arrays;
import java.util.List;

import com.lgcns.ikep4.approval.admin.model.Code;
import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * 사용자 권한 관리 VO
 * 
 * @author pjh
 * @version $Id$
 */
public class UserAuthMgnt extends BaseObject {
	
	private static final List<Code<String>> YN_LIST = Arrays.asList(
			new Code<String>("Y", "Y"), new Code<String>("N", "N")); 
	
	/**
	 * rowNum
	 */
	private String rNum;
	
	/**
	 * 업무구분코드
	 */
	private String workGbnCd;
	
	/**
	 * 부서코드
	 */
	private String deptId;
	
	/**
	 * 사원번호
	 */
	private String empNo;
	
	/**
	 * 결과파일 읽기 권한 여부
	 */
	private String rsltFileReadYn;
	
	/**
	 * 승인자여부
	 */
	private String apprYn;
	
	/**
	 * 사용여부
	 */
	private String useYn;
	
	/**
	 * 직원명
	 */
	private String userName;
	
	/**
	 * 부서명
	 */
	private String groupName;
	
	/**
	 * 메일주소
	 */
	private String mail;
	
	/**
	 * 저장버튼 활성화
	 */
	private Boolean saveBntActive;
	
	/**
	 * @return the rNum
	 */
	public String getrNum() {
		return rNum;
	}

	/**
	 * @param rNum the rNum to set
	 */
	public void setrNum(String rNum) {
		this.rNum = rNum;
	}

	/**
	 * @return the workGbnCd
	 */
	public String getWorkGbnCd() {
		return workGbnCd;
	}

	/**
	 * @param workGbnCd the workGbnCd to set
	 */
	public void setWorkGbnCd(String workGbnCd) {
		this.workGbnCd = workGbnCd;
	}

	/**
	 * @return the deptId
	 */
	public String getDeptId() {
		return deptId;
	}

	/**
	 * @param deptId the deptId to set
	 */
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	/**
	 * @return the empNo
	 */
	public String getEmpNo() {
		return empNo;
	}

	/**
	 * @param empNo the empNo to set
	 */
	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}

	/**
	 * @return the rsltFileReadYn
	 */
	public String getRsltFileReadYn() {
		return rsltFileReadYn;
	}

	/**
	 * @param rsltFileReadYn the rsltFileReadYn to set
	 */
	public void setRsltFileReadYn(String rsltFileReadYn) {
		this.rsltFileReadYn = rsltFileReadYn;
	}

	/**
	 * @return the apprYn
	 */
	public String getApprYn() {
		return apprYn;
	}

	/**
	 * @param apprYn the apprYn to set
	 */
	public void setApprYn(String apprYn) {
		this.apprYn = apprYn;
	}

	/**
	 * @return the useYn
	 */
	public String getUseYn() {
		return useYn;
	}

	/**
	 * @param useYn the useYn to set
	 */
	public void setUseYn(String useYn) {
		this.useYn = useYn;
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
	 * @return the ynList
	 */
	public static List<Code<String>> getYnList() {
		return YN_LIST;
	}

	/**
	 * @return the mail
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * @param mail the mail to set
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}

	/**
	 * @return the saveBntActive
	 */
	public Boolean getSaveBntActive() {
		return saveBntActive;
	}

	/**
	 * @param saveBntActive the saveBntActive to set
	 */
	public void setSaveBntActive(Boolean saveBntActive) {
		this.saveBntActive = saveBntActive;
	}
}
