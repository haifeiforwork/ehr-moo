/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.admin.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * Default 결재선 Flow 정보
 *
 * @author 
 * @version $Id$
 */
public class ApprDefLineInfo extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * 결재선 정보 ID
	 */
	private	String	infoId;
	
	
	/**
	 * Default 결재선 ID
	 */
	private	String	defLineId;
	
	/**
	 * 결재타입 (0:결재, 1:합의(필수), 2:합의(선택), 3:수신)
	 */
	private	int		apprType;
	
	/**
	 * 결재자 선택 방식 (직접:0, 간접(role선택):1)
	 */
	private	int		approverWay;
	
	/**
	 * 결재자 타입 (사용자:0, 부서:1, role:2)
	 */
	private	int		approverType;
	
	/**
	 * 결재자 ID (결재선 타입이 사용자이면 USER_ID, 부서면 GROUP_ID, 역할이면 ROLE_ID)
	 */
	private	String	approverId;
	
	/**
	 * 결재순서
	 */
	private	int		apprOrder;
	
	/**
	 * 결재자 직위 OR 직책 여부 (직위:0, 직책:1)
	 */
	private int		jobType;
	/**
	 * 결재자 직위/직책 코드
	 */
	private	String	approverJobTitle;
	
	/**
	 * 결재자 직위/직책 명
	 */
	private	String	approverJobTitleName;	
	
	/**
	 * 결재자 직위/직책 영문명
	 */	
	private	String	approverJobTitleEnglishName;
	
	/**
	 * 결재선 변경 권한 여부 (권한없음:0, 권한있음:1)
	 */
	private	int		lineModifyAuth;
	
	/**
	 * 결재문서 변경 권한 여부 (권한없음:0, 권한있음:1)
	 */
	private	int		docModifyAuth;
	
	/**
	 * 참조자,열람권자 변경 권한 여부 (권한없음:0, 권한있음:1)
	 */
	private	int		readModifyAuth;
	
	/**
	 * 결재자명
	 */
	private String userName;
	
	/**
	 * 결재자 영문명
	 */
	private String userEnglishName;	
	/**
	 * 결재자 호칭코드
	 */
	private String jobTitleCode;
	
	/**
	 * 결재자 호칭 명
	 */
	private	String jobTitleName;
	private	String jobTitleEnglishName;	
	/**
	 * 결재자 직위명
	 */
	//private	String jobTitleName;	
	/**
	 * 결재자 부서명
	 */
	private String teamName;
	private String teamEnglishName;	
	/**
	 * 결재부서 부서명
	 */
	private String groupName;
	
	private	String groupEnglishName;
	/**
	 * 결재자 직책코드
	 */
	private String jobDutyCode;
	/**
	 * Role 직책명
	 */
	private String jobDutyName;
	private String jobDutyEnglishName;
	/**
	 * @return the infoId
	 */
	public String getInfoId() {
		return infoId;
	}

	/**
	 * @param infoId the infoId to set
	 */
	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

	/**
	 * @return the defLineId
	 */
	public String getDefLineId() {
		return defLineId;
	}

	/**
	 * @param defLineId the defLineId to set
	 */
	public void setDefLineId(String defLineId) {
		this.defLineId = defLineId;
	}

	/**
	 * @return the apprType
	 */
	public int getApprType() {
		return apprType;
	}

	/**
	 * @param apprType the apprType to set
	 */
	public void setApprType(int apprType) {
		this.apprType = apprType;
	}

	/**
	 * @return the approverWay
	 */
	public int getApproverWay() {
		return approverWay;
	}

	/**
	 * @param approverWay the approverWay to set
	 */
	public void setApproverWay(int approverWay) {
		this.approverWay = approverWay;
	}

	/**
	 * @return the approverType
	 */
	public int getApproverType() {
		return approverType;
	}

	/**
	 * @param approverType the approverType to set
	 */
	public void setApproverType(int approverType) {
		this.approverType = approverType;
	}

	/**
	 * @return the approverId
	 */
	public String getApproverId() {
		return approverId;
	}

	/**
	 * @param approverId the approverId to set
	 */
	public void setApproverId(String approverId) {
		this.approverId = approverId;
	}

	/**
	 * @return the apprOrder
	 */
	public int getApprOrder() {
		return apprOrder;
	}

	/**
	 * @param apprOrder the apprOrder to set
	 */
	public void setApprOrder(int apprOrder) {
		this.apprOrder = apprOrder;
	}

	/**
	 * @return the approverJobTitle
	 */
	public String getApproverJobTitle() {
		return approverJobTitle;
	}

	/**
	 * @param approverJobTitle the approverJobTitle to set
	 */
	public void setApproverJobTitle(String approverJobTitle) {
		this.approverJobTitle = approverJobTitle;
	}

	/**
	 * @return the lineModifyAuth
	 */
	public int getLineModifyAuth() {
		return lineModifyAuth;
	}

	/**
	 * @param lineModifyAuth the lineModifyAuth to set
	 */
	public void setLineModifyAuth(int lineModifyAuth) {
		this.lineModifyAuth = lineModifyAuth;
	}

	/**
	 * @return the docModifyAuth
	 */
	public int getDocModifyAuth() {
		return docModifyAuth;
	}

	/**
	 * @param docModifyAuth the docModifyAuth to set
	 */
	public void setDocModifyAuth(int docModifyAuth) {
		this.docModifyAuth = docModifyAuth;
	}

	/**
	 * @return the readModifyAuth
	 */
	public int getReadModifyAuth() {
		return readModifyAuth;
	}

	/**
	 * @param readModifyAuth the readModifyAuth to set
	 */
	public void setReadModifyAuth(int readModifyAuth) {
		this.readModifyAuth = readModifyAuth;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getJobDutyName() {
		return jobDutyName;
	}

	public void setJobDutyName(String jobDutyName) {
		this.jobDutyName = jobDutyName;
	}
 
	public String getApproverJobTitleName() {
		return approverJobTitleName;
	}

	public void setApproverJobTitleName(String approverJobTitleName) {
		this.approverJobTitleName = approverJobTitleName;
	}

	public int getJobType() {
		return jobType;
	}

	public void setJobType(int jobType) {
		this.jobType = jobType;
	}

 

	public String getJobDutyCode() {
		return jobDutyCode;
	}

	public void setJobDutyCode(String jobDutyCode) {
		this.jobDutyCode = jobDutyCode;
	}

	/**
	 * @return the jobTitleCode
	 */
	public String getJobTitleCode() {
		return jobTitleCode;
	}

	/**
	 * @param jobTitleCode the jobTitleCode to set
	 */
	public void setJobTitleCode(String jobTitleCode) {
		this.jobTitleCode = jobTitleCode;
	}

	/**
	 * @return the jobTitleName
	 */
	public String getJobTitleName() {
		return jobTitleName;
	}

	/**
	 * @param jobTitleName the jobTitleName to set
	 */
	public void setJobTitleName(String jobTitleName) {
		this.jobTitleName = jobTitleName;
	}

	public String getApproverJobTitleEnglishName() {
		return approverJobTitleEnglishName;
	}

	public void setApproverJobTitleEnglishName(String approverJobTitleEnglishName) {
		this.approverJobTitleEnglishName = approverJobTitleEnglishName;
	}

	public String getUserEnglishName() {
		return userEnglishName;
	}

	public void setUserEnglishName(String userEnglishName) {
		this.userEnglishName = userEnglishName;
	}

	public String getJobTitleEnglishName() {
		return jobTitleEnglishName;
	}

	public void setJobTitleEnglishName(String jobTitleEnglishName) {
		this.jobTitleEnglishName = jobTitleEnglishName;
	}

	public String getTeamEnglishName() {
		return teamEnglishName;
	}

	public void setTeamEnglishName(String teamEnglishName) {
		this.teamEnglishName = teamEnglishName;
	}

	public String getJobDutyEnglishName() {
		return jobDutyEnglishName;
	}

	public void setJobDutyEnglishName(String jobDutyEnglishName) {
		this.jobDutyEnglishName = jobDutyEnglishName;
	}

	public String getGroupEnglishName() {
		return groupEnglishName;
	}

	public void setGroupEnglishName(String groupEnglishName) {
		this.groupEnglishName = groupEnglishName;
	}


}
