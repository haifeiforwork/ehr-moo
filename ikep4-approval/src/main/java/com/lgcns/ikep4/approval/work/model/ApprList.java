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

import com.lgcns.ikep4.approval.admin.model.Code;
import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * 기안함 목록
 * 
 * @author jeehye(jjang2g79@naver.com)
 * @version $Id: ApprEntrust.java 16234 2011-08-18 02:44:36Z giljae $
 */
public class ApprList extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8900711193377442735L;

	/**
	 * 양식 ID
	 */
	private String formId;

	/**
	 * 양식명
	 */
	private String formName;

	/**
	 * 결재문서 ID
	 */
	private String apprId;

	/**
	 * 부모 결재문서 ID
	 */
	private String parentApprId;

	/**
	 * 결재문서 유형 (0:내부결재,1:협조공문-수신처가 있는 결재문서)
	 */
	private String apprDocType;

	/**
	 * 결재문서 제목
	 */
	private String apprTitle;

	/**
	 * 기안일자
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
	private Date apprReqDate;

	/**
	 * 문서상태 (0:임시저장, 1:진행중, 2:완료, 3:반려, 4:회수, 5:오류..)
	 */
	private String apprDocStatus;

	/**
	 * 결재문서 기안자 ID
	 */
	private String registerId;

	/**
	 * 등록일자
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
	private Date registDate;

	/**
	 * 분류
	 */
	private String codeName;

	/**
	 * TEAM
	 */
	private String teamName;

	/**
	 * 기안자 이름
	 */
	private String registerName;

	/**
	 * 접수자 
	 */
	private String recRegisterId;

	/**
	 * 접수자 이름
	 */
	private String recRegisterName;

	/**
	 * 완료일
	 */
	private Date apprEndDate;

	/**
	 * 완료일
	 */
	private Date examDate;

	/**
	 * 위임자
	 */
	private String entrustUserId;

	private String entrustId;

	private String apprGroupName;

	private String apprDocNo;

	/**
	 * 결재타입 (0:결재, 1:합의(필수), 2:합의(선택), 3:수신)
	 */
	private String apprType;

	private String approvalId;

	private String apprStatus;

	private String examReqId;

	private String examReqName;

	private Date examReqDate;

	private String isHidden;

	private String entrustUserName;

	private String mListType;

	private String apprGroupId;

	private String listSortType;
	
	private String folderId;
	
	private String folderName;
	
	private String folderParentId;

	@SuppressWarnings("unchecked")
	private static final List<Code<String>> SORT_TYPE_LIST = Arrays.asList(new Code<String>("",
			"ui.approval.work.apprlist.display.all"), new Code<String>("AL",
			"ui.approval.common.searchCondition.apprAl"), new Code<String>("AA",
			"ui.approval.common.searchCondition.apprAl2"), new Code<String>("AR",
			"ui.approval.common.searchCondition.apprAr"));

	@SuppressWarnings("unchecked")
	private static final List<Code<String>> TYPE_LIST = Arrays.asList(new Code<String>("",
			"ui.approval.work.apprlist.display.all"), new Code<String>("appr_ad",
			"ui.approval.common.searchCondition.apprAd"), new Code<String>("appr_al",
			"ui.approval.common.searchCondition.apprAl"), new Code<String>("appr_al2",
			"ui.approval.common.searchCondition.apprAl2"), new Code<String>("appr_ar",
			"ui.approval.common.searchCondition.apprAr"), new Code<String>("appr_are",
			"ui.approval.common.searchCondition.apprAre"), new Code<String>("appr_dept",
			"ui.approval.common.searchCondition.apprDept"));

	@SuppressWarnings("unchecked")
	private static final List<Code<String>> DOC_SECURITY_LIST = Arrays.asList(new Code<String>("",
			"ui.approval.work.apprlist.display.all"), new Code<String>("0",
			"ui.approval.work.apprlist.display.clearSecurity"), new Code<String>("1",
			"ui.approval.work.apprlist.display.setSecurity"));

	@SuppressWarnings("unchecked")
	private static final List<Code<String>> DOC_STATUS_LIST = Arrays.asList(new Code<String>("",
			"ui.approval.work.apprlist.display.all"), new Code<String>("1",
			"ui.approval.work.apprlist.display.progress"), new Code<String>("2",
			"ui.approval.work.apprlist.display.complete"), new Code<String>("3",
			"ui.approval.work.apprlist.display.reject"), new Code<String>("4",
			"ui.approval.work.apprlist.display.return"));

	@SuppressWarnings("unchecked")
	private static final List<Code<String>> DOC_STATUS_ALL_LIST = Arrays.asList(new Code<String>("",
			"ui.approval.work.apprlist.display.all"), new Code<String>("0", "ui.approval.work.apprlist.display.temp"),
			new Code<String>("1", "ui.approval.work.apprlist.display.progress"), new Code<String>("2",
					"ui.approval.work.apprlist.display.complete"), new Code<String>("3",
					"ui.approval.work.apprlist.display.reject"), new Code<String>("4",
					"ui.approval.work.apprlist.display.return"), new Code<String>("6",
					"ui.approval.work.apprlist.display.wait"), new Code<String>("7",
					"ui.approval.work.apprlist.display.return1"));

	@SuppressWarnings("unchecked")
	private static final List<Code<String>> DOC_TYPE_LIST = Arrays.asList(new Code<String>("",
			"ui.approval.work.apprlist.display.all"), new Code<String>("0",
			"ui.approval.common.searchCondition.apprDocType0"), new Code<String>("1",
			"ui.approval.common.searchCondition.apprDocType1"));

	@SuppressWarnings("unchecked")
	private static final List<Code<String>> DOC_STATUS_REJECT_LIST = Arrays.asList(new Code<String>("",
			"ui.approval.work.apprlist.display.all"),
			new Code<String>("3", "ui.approval.work.apprlist.display.reject"), new Code<String>("4",
					"ui.approval.work.apprlist.display.return"));

	@SuppressWarnings("unchecked")
	private static final List<Code<Integer>> PAGE_NUM_LIST = Arrays.asList(new Code<Integer>(10, "10"),
			new Code<Integer>(15, "15"), new Code<Integer>(20, "20"), new Code<Integer>(30, "30"), new Code<Integer>(
					40, "40"), new Code<Integer>(50, "50"));

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

	public String getApprId() {
		return apprId;
	}

	public void setApprId(String apprId) {
		this.apprId = apprId;
	}

	public String getParentApprId() {
		return parentApprId;
	}

	public void setParentApprId(String parentApprId) {
		this.parentApprId = parentApprId;
	}

	public String getApprDocType() {
		return apprDocType;
	}

	public void setApprDocType(String apprDocType) {
		this.apprDocType = apprDocType;
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

	public static List<Code<String>> getDocStatusList() {
		return DOC_STATUS_LIST;
	}

	public String getRegisterId() {
		return registerId;
	}

	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public Date getRegistDate() {
		return registDate;
	}

	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}

	public static List<Code<String>> getDocStatusRejectList() {
		return DOC_STATUS_REJECT_LIST;
	}

	public static List<Code<Integer>> getPageNumList() {
		return PAGE_NUM_LIST;
	}

	public String getEntrustUserId() {
		return entrustUserId;
	}

	public void setEntrustUserId(String entrustUserId) {
		this.entrustUserId = entrustUserId;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getRegisterName() {
		return registerName;
	}

	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}

	public Date getApprEndDate() {
		return apprEndDate;
	}

	public void setApprEndDate(Date apprEndDate) {
		this.apprEndDate = apprEndDate;
	}

	public String getApprType() {
		return apprType;
	}

	public void setApprType(String apprType) {
		this.apprType = apprType;
	}

	public String getApprGroupName() {
		return apprGroupName;
	}

	public void setApprGroupName(String apprGroupName) {
		this.apprGroupName = apprGroupName;
	}

	public String getApprDocNo() {
		return apprDocNo;
	}

	public void setApprDocNo(String apprDocNo) {
		this.apprDocNo = apprDocNo;
	}

	public String getApprovalId() {
		return approvalId;
	}

	public void setApprovalId(String approvalId) {
		this.approvalId = approvalId;
	}

	public String getApprStatus() {
		return apprStatus;
	}

	public void setApprStatus(String apprStatus) {
		this.apprStatus = apprStatus;
	}

	public String getExamReqId() {
		return examReqId;
	}

	public void setExamReqId(String examReqId) {
		this.examReqId = examReqId;
	}

	public String getExamReqName() {
		return examReqName;
	}

	public void setExamReqName(String examReqName) {
		this.examReqName = examReqName;
	}

	public Date getExamReqDate() {
		return examReqDate;
	}

	public void setExamReqDate(Date examReqDate) {
		this.examReqDate = examReqDate;
	}

	public static List<Code<String>> getDocStatusAllList() {
		return DOC_STATUS_ALL_LIST;
	}

	public static List<Code<String>> getDocTypeList() {
		return DOC_TYPE_LIST;
	}

	public String getIsHidden() {
		return isHidden;
	}

	public void setIsHidden(String isHidden) {
		this.isHidden = isHidden;
	}

	public String getEntrustUserName() {
		return entrustUserName;
	}

	public void setEntrustUserName(String entrustUserName) {
		this.entrustUserName = entrustUserName;
	}

	public static List<Code<String>> getDocSecurityList() {
		return DOC_SECURITY_LIST;
	}

	public Date getExamDate() {
		return examDate;
	}

	public void setExamDate(Date examDate) {
		this.examDate = examDate;
	}

	public String getmListType() {
		return mListType;
	}

	public void setmListType(String mListType) {
		this.mListType = mListType;
	}

	public static List<Code<String>> getTypeList() {
		return TYPE_LIST;
	}

	public String getApprGroupId() {
		return apprGroupId;
	}

	public void setApprGroupId(String apprGroupId) {
		this.apprGroupId = apprGroupId;
	}

	public String getEntrustId() {
		return entrustId;
	}

	public void setEntrustId(String entrustId) {
		this.entrustId = entrustId;
	}

	public String getRecRegisterName() {
		return recRegisterName;
	}

	public void setRecRegisterName(String recRegisterName) {
		this.recRegisterName = recRegisterName;
	}

	public String getListSortType() {
		return listSortType;
	}

	public void setListSortType(String listSortType) {
		this.listSortType = listSortType;
	}

	public static List<Code<String>> getSortTypeList() {
		return SORT_TYPE_LIST;
	}

	public String getRecRegisterId() {
		return recRegisterId;
	}

	public void setRecRegisterId(String recRegisterId) {
		this.recRegisterId = recRegisterId;
	}

	public String getFolderId() {
		return folderId;
	}

	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	public String getFolderParentId() {
		return folderParentId;
	}

	public void setFolderParentId(String folderParentId) {
		this.folderParentId = folderParentId;
	}

}
