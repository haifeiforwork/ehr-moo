/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.workmanual.model;


/**
 * TODO Javadoc주석작성
 *
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: ApprovalUser.java 16236 2011-08-18 02:48:22Z giljae $
 */

public class ApprovalUser extends ApprovalUserPk {
	private static final long serialVersionUID = -1373942940234681080L;
 
	/**
	 * 결재자ID
	 */
	private String approvalUserId;

	/**
	 * 결재자명
	 */
	private String approvalUserName;

	/**
	 * 결재자부서
	 */
	private String approvalUserTeamName;

	/**
	 * 결재자직급
	 */
	private String approvalUserJobTitleName;

	/**
	 * 결재자명(영어)
	 */
	private String approvalUserEnglishName;

	/**
	 * 결재자부서(영어)
	 */
	private String approvalUserTeamEnglishName;

	/**
	 * 결재자직급(영어)
	 */
	private String approvalUserJobTitleEnglishName;

	/**
	 * @return the approvalUserName
	 */
	public String getApprovalUserName() {
		return approvalUserName;
	}
	/**
	 * @param approvalUserName the approvalUserName to set
	 */
	public void setApprovalUserName(String approvalUserName) {
		this.approvalUserName = approvalUserName;
	}
	/**
	 * @return the approvalUserTeamName
	 */
	public String getApprovalUserTeamName() {
		return approvalUserTeamName;
	}
	/**
	 * @param approvalUserTeamName the approvalUserTeamName to set
	 */
	public void setApprovalUserTeamName(String approvalUserTeamName) {
		this.approvalUserTeamName = approvalUserTeamName;
	}
	/**
	 * @return the approvalUserJobTitleName
	 */
	public String getApprovalUserJobTitleName() {
		return approvalUserJobTitleName;
	}
	/**
	 * @param approvalUserJobTitleName the approvalUserJobTitleName to set
	 */
	public void setApprovalUserJobTitleName(String approvalUserJobTitleName) {
		this.approvalUserJobTitleName = approvalUserJobTitleName;
	}
	/**
	 * @return the approvalUserId
	 */
	public String getApprovalUserId() {
		return approvalUserId;
	}
	/**
	 * @param approvalUserId the approvalUserId to set
	 */
	public void setApprovalUserId(String approvalUserId) {
		this.approvalUserId = approvalUserId;
	}
	/**
	 * @return the approvalUserEnglishName
	 */
	public String getApprovalUserEnglishName() {
		return approvalUserEnglishName;
	}
	/**
	 * @param approvalUserEnglishName the approvalUserEnglishName to set
	 */
	public void setApprovalUserEnglishName(String approvalUserEnglishName) {
		this.approvalUserEnglishName = approvalUserEnglishName;
	}
	/**
	 * @return the approvalUserTeamEnglishName
	 */
	public String getApprovalUserTeamEnglishName() {
		return approvalUserTeamEnglishName;
	}
	/**
	 * @param approvalUserTeamEnglishName the approvalUserTeamEnglishName to set
	 */
	public void setApprovalUserTeamEnglishName(String approvalUserTeamEnglishName) {
		this.approvalUserTeamEnglishName = approvalUserTeamEnglishName;
	}
	/**
	 * @return the approvalUserJobTitleEnglishName
	 */
	public String getApprovalUserJobTitleEnglishName() {
		return approvalUserJobTitleEnglishName;
	}
	/**
	 * @param approvalUserJobTitleEnglishName the approvalUserJobTitleEnglishName to set
	 */
	public void setApprovalUserJobTitleEnglishName(String approvalUserJobTitleEnglishName) {
		this.approvalUserJobTitleEnglishName = approvalUserJobTitleEnglishName;
	}


}
