/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.work.model;


import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.lgcns.ikep4.framework.core.model.BaseObject;
import com.lgcns.ikep4.approval.admin.model.Code;

/**
 * 공람
 *
 * @author jeehye(jjang2g79@naver.com)
 * @version $Id: ApprEntrust.java 16234 2011-08-18 02:44:36Z giljae $
 */
public class ApprDisplay extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8900711193377442735L;

	/**
	 * 공람 ID
	 */
	private String displayId;
	
	/**
	 * 결재문서 ID
	 */
	private String apprId;
	
	/**
	 * 공람상태 (0:공람지정, 1:공람진행, 2:공람완료)
	 */
	private String displayStatus;
	
	/**
	 * 공람 완료시간
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd hh:mm")
	private Date completeDate;
	
	/**
	 * 등록자 ID
	 */
	private String registerId;
	
	/**
	 * 등록자 이름
	 */
	private String registerName;
	
	/**
	 * 등록일자
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd hh:mm")
	private Date registDate;
	
	/**
	 * 사용자 권한 배열
	 */
	private String[] addrUserList;
	
	/**
	 * 그룹 권한 배열
	 */
	private String[] addrGroupTypeList;
	
	/**
	 * 사용자 권한
	 */
	private String addrUserListAll;
	
	/**
	 * 그룹 권한
	 */
	private String addrGroupTypeListAll;
	
	/**
	 * 공람 받은 사용자 ID
	 */
	private String userId;
	
	/**
	 * 공람 받은 그룹 ID
	 */
	private String groupId;
	
	/**
	 * sub group
	 */
	private int childGroupCount;
	
	/**
	 * 공람여부 (0:미확인, 1:확인)
	 */
	private String userStatus;
	
	/**
	 * 공람 확인일자
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd hh:mm")
	private Date confirmDate;
	
	/**
	 * 공람자 이름
	 */
	private String userName;
	
	/**
	 * 공람자 부서
	 */
	private String teamName;
	
	/**
	 * 공람자 직급
	 */
	private String jobTitleName;
	
	private String formId;
	
	private String formName;
	
	private String apprDocType;
	
	private String codeName;
	
	private String apprTitle;
	
	@DateTimeFormat(pattern="yyyy-MM-dd hh:mm")
	private Date apprReqDate;
	
	private String apprDocStatus;
	
	private Date dRegistDate;
	
	private Date apprEndDate;
	
	
	@SuppressWarnings("unchecked")
	private static final List<Code<Integer>> PAGE_NUM_LIST = Arrays.asList(		
			new Code<Integer>(10, "10"),
			new Code<Integer>(15, "15"),
			new Code<Integer>(20, "20"),
			new Code<Integer>(30, "30"),
			new Code<Integer>(40, "40"),
			new Code<Integer>(50, "50")
	);
	
	@SuppressWarnings("unchecked")
	private static final List<Code<String>> USER_STATUS_LIST = Arrays.asList(	
			new Code<String>("",  "ui.approval.work.apprDisplay.confirm.all"),
			new Code<String>("0",  "ui.approval.work.apprDisplay.confirm.no"),
			new Code<String>("1",  "ui.approval.work.apprDisplay.confirm.yes")
	);

	public String getDisplayId() {
		return displayId;
	}

	public void setDisplayId(String displayId) {
		this.displayId = displayId;
	}

	public String getApprId() {
		return apprId;
	}

	public void setApprId(String apprId) {
		this.apprId = apprId;
	}

	public String getDisplayStatus() {
		return displayStatus;
	}

	public void setDisplayStatus(String displayStatus) {
		this.displayStatus = displayStatus;
	}

	public Date getCompleteDate() {
		return completeDate;
	}

	public void setCompleteDate(Date completeDate) {
		this.completeDate = completeDate;
	}

	public String getRegisterId() {
		return registerId;
	}

	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}

	public String getRegisterName() {
		return registerName;
	}

	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}

	public Date getRegistDate() {
		return registDate;
	}

	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}

	public String[] getAddrUserList() {
		return addrUserList;
	}

	public void setAddrUserList(String[] addrUserList) {
		this.addrUserList = addrUserList;
	}

	public String[] getAddrGroupTypeList() {
		return addrGroupTypeList;
	}

	public void setAddrGroupTypeList(String[] addrGroupTypeList) {
		this.addrGroupTypeList = addrGroupTypeList;
	}

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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public Date getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public int getChildGroupCount() {
		return childGroupCount;
	}

	public void setChildGroupCount(int childGroupCount) {
		this.childGroupCount = childGroupCount;
	}

	public static List<Code<Integer>> getPageNumList() {
		return PAGE_NUM_LIST;
	}

	public static List<Code<String>> getUserStatusList() {
		return USER_STATUS_LIST;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getJobTitleName() {
		return jobTitleName;
	}

	public void setJobTitleName(String jobTitleName) {
		this.jobTitleName = jobTitleName;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public String getApprDocType() {
		return apprDocType;
	}

	public void setApprDocType(String apprDocType) {
		this.apprDocType = apprDocType;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public String getApprTitle() {
		return apprTitle;
	}

	public void setApprTitle(String apprTitle) {
		this.apprTitle = apprTitle;
	}

	public Date getApprReqDate() {
		return apprReqDate;
	}

	public void setApprReqDate(Date apprReqDate) {
		this.apprReqDate = apprReqDate;
	}

	public String getApprDocStatus() {
		return apprDocStatus;
	}

	public void setApprDocStatus(String apprDocStatus) {
		this.apprDocStatus = apprDocStatus;
	}

	public Date getdRegistDate() {
		return dRegistDate;
	}

	public void setdRegistDate(Date dRegistDate) {
		this.dRegistDate = dRegistDate;
	}

	public Date getApprEndDate() {
		return apprEndDate;
	}

	public void setApprEndDate(Date apprEndDate) {
		this.apprEndDate = apprEndDate;
	}
	
}
