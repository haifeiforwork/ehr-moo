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

import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.lgcns.ikep4.framework.core.model.BaseObject;
import com.lgcns.ikep4.approval.admin.model.Code;

/**
 * 검토 요청
 *
 * @author jeehye(jjang2g79@naver.com)
 * @version $Id: ApprEntrust.java 16234 2011-08-18 02:44:36Z giljae $
 */
public class ApprExam extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8900711193377442735L;

	/**
	 * 결재문서 ID
	 */
	private String apprId;
	/**
	 * 검토순서
	 */
	private String examOrder;
	
	/**
	 * 검토 요청자 ID
	 */
	private String examReqId;
	
	/**
	 * 검토 요청일
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd hh:mm")
	private Date examReqDate;
	
	/**
	 * 검토 요청내용
	 */
	@Size(min=0, max=2000)
	private String examReqContent;
	
	/**
	 * 검토 ID
	 */
	private String examUserId;
	
	/**
	 * 검토 ID
	 */
	private String examUserName;
	
	/**
	 * 검토 ID
	 */
	private String examUserSet;
	
	/**
	 * 검토일
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd hh:mm")
	private Date examDate;
	
	/**
	 * 검토 내용
	 */
	@Size(min=0, max=2000)
	private String examContent;
	
	/**
	 * 검토 진행 상태 (0:검토중, 1:검토완료)
	 */
	private String examStatus;
	
	/**
	 * 검토 공개여부 (0:공개, 1:검토자/요청자만 조회)
	 */
	private String isOpen;
	
	/**
	 * 검토 요청 권한 부여 (0:권한 미부여, 1:검토자로 지정된 사람이 타인에게 검토요청을 할 수 있는 권한부여)
	 */
	private String isRequest;
	
	/**
	 * 최종 수정자 ID
	 */
	private String lastUpdaterId;
	
	/**
	 * 최종 수정일시
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd hh:mm")
	private Date lastUpdate;
	
	/**
	 * 로그인ID
	 */
	private String userId;
	
	/**
	 * 요청자이름 및 직급
	 */
	private String examReqName;
	
	/**
	 * 검토자이름 및 직급
	 */
	private String examName;
	
	/**
	 * 요청자 팀
	 */
	private String examReqTeamName;
	
	/**
	 * 검토자 팀
	 */
	private String examTeamName;
	
	private String examFirstReqId;
	
	private String examId;
	
	@SuppressWarnings("unchecked")
	private static final List<Code<String>> IS_OPEN_LIST = Arrays.asList(
			new Code<String>("0",  "ui.approval.work.exam.display.open"),
			new Code<String>("1",  "ui.approval.work.exam.display.close")
	);
	
	@SuppressWarnings("unchecked")
	private static final List<Code<String>> IS_REQUEST_LIST = Arrays.asList(
			new Code<String>("1",  "ui.approval.work.exam.display.setting"),
			new Code<String>("0",  "ui.approval.work.exam.display.unsetting")
	);

	public String getApprId() {
		return apprId;
	}

	public void setApprId(String apprId) {
		this.apprId = apprId;
	}

	public String getExamOrder() {
		return examOrder;
	}

	public void setExamOrder(String examOrder) {
		this.examOrder = examOrder;
	}

	public String getExamReqId() {
		return examReqId;
	}

	public void setExamReqId(String examReqId) {
		this.examReqId = examReqId;
	}

	public Date getExamReqDate() {
		return examReqDate;
	}

	public void setExamReqDate(Date examReqDate) {
		this.examReqDate = examReqDate;
	}

	public String getExamReqContent() {
		return examReqContent;
	}

	public void setExamReqContent(String examReqContent) {
		this.examReqContent = examReqContent;
	}

	public String getExamUserId() {
		return examUserId;
	}

	public void setExamUserId(String examUserId) {
		this.examUserId = examUserId;
	}

	public Date getExamDate() {
		return examDate;
	}

	public void setExamDate(Date examDate) {
		this.examDate = examDate;
	}

	public String getExamContent() {
		return examContent;
	}

	public void setExamContent(String examContent) {
		this.examContent = examContent;
	}

	public String getExamStatus() {
		return examStatus;
	}

	public void setExamStatus(String examStatus) {
		this.examStatus = examStatus;
	}

	public String getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(String isOpen) {
		this.isOpen = isOpen;
	}

	public String getIsRequest() {
		return isRequest;
	}

	public void setIsRequest(String isRequest) {
		this.isRequest = isRequest;
	}
	public static List<Code<String>> getIsOpenList() {
		return IS_OPEN_LIST;
	}

	public static List<Code<String>> getIsRequestList() {
		return IS_REQUEST_LIST;
	}

	public String getExamUserName() {
		return examUserName;
	}

	public void setExamUserName(String examUserName) {
		this.examUserName = examUserName;
	}

	public String getExamUserSet() {
		return examUserSet;
	}

	public void setExamUserSet(String examUserSet) {
		this.examUserSet = examUserSet;
	}

	public String getLastUpdaterId() {
		return lastUpdaterId;
	}

	public void setLastUpdaterId(String lastUpdaterId) {
		this.lastUpdaterId = lastUpdaterId;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getExamReqName() {
		return examReqName;
	}

	public void setExamReqName(String examReqName) {
		this.examReqName = examReqName;
	}

	public String getExamName() {
		return examName;
	}

	public void setExamName(String examName) {
		this.examName = examName;
	}

	public String getExamReqTeamName() {
		return examReqTeamName;
	}

	public void setExamReqTeamName(String examReqTeamName) {
		this.examReqTeamName = examReqTeamName;
	}

	public String getExamTeamName() {
		return examTeamName;
	}

	public void setExamTeamName(String examTeamName) {
		this.examTeamName = examTeamName;
	}

	public String getExamFirstReqId() {
		return examFirstReqId;
	}

	public void setExamFirstReqId(String examFirstReqId) {
		this.examFirstReqId = examFirstReqId;
	}

	public String getExamId() {
		return examId;
	}

	public void setExamId(String examId) {
		this.examId = examId; 
	}
	
}
