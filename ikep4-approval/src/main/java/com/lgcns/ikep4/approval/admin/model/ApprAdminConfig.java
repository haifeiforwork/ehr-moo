/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.admin.model;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.lgcns.ikep4.framework.core.model.BaseObject;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 
 * TODO Javadoc주석작성
 * 
 * @author 서지혜
 * @version $Id$
 */

public class ApprAdminConfig extends BaseObject {

	private static final long serialVersionUID = 8900711193377442735L;

	/**
	 * 포탈 ID
	 */
	private String portalId;

	/**
	 * 문서결재 설정 ID
	 */
	private String configId;

	/**
	 * 참조 사용 여부 (0:미사용, 1:사용)
	 */
	private String isReference;

	/**
	 * 보류 사용 여부 (0:미사용, 1:사용)
	 */
	private String isReservation;

	/**
	 * 전결 사용 여부 (0:미사용, 1:사용)
	 */
	private String isArbitraryDecision;

	/**
	 * 결재 비밀번호 사용 여부 (0:미사용, 1:사용)
	 */
	private String isPassword;

	/**
	 * 일괄결재 기능 사용 여부 (0:미사용, 1:사용)
	 */
	private String isOverall;

	/**
	 * 검토 기능 사용 여부 (0:미사용, 1:사용)
	 */
	private String isExam;

	/**
	 * 참조자 의견 입력 사용 여부 (0:미사용, 1:사용)
	 */
	private String isRefMsg;

	/**
	 * 수신처(부서) 사용 여부 (0:미사용, 1:사용)
	 */
	private String isReceive;

	/**
	 * 결재 서명이미지 사용 여부 (0:미사용, 1:사용)
	 */
	private String isUseSign;

	/**
	 * 결재선 수정권한 사용 여부 (0:미사용, 1:사용)
	 */
	private String isUpdateLine;

	/**
	 * 결재 문서 수정권한 사용 여부 (0:미사용, 1:사용)
	 */
	private String isUpdateDoc;

	/**
	 * 결재 참조, 열람권자 수정 사용 여부 (0:미사용, 1:사용)
	 */
	private String isUpdateRead;

	/**
	 * 결재 취소 사용 여부 (0:미사용, 1:사용)
	 */
	private String isCancel;

	/**
	 * 수신문서 접수자 지정방식 (0:접수담당자 접수, 1:수신부서 부서원 전체 접수)RECEPTION_TYPE
	 */
	private String receptionType;

	/**
	 * 등록자 ID
	 */
	private String registerId;

	/**
	 * 등록자명
	 */
	private String registerName;

	/**
	 * 등록일자
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
	private Date registDate;

	/**
	 * 수정자 ID
	 */
	private String updaterId;

	/**
	 * 수정자명
	 */
	private String updaterName;

	/**
	 * 수정일자
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
	private Date updateDate;

	/**
	 * 후결 사용 여부 (0:미사용, 1:사용)
	 */
	private String isPostApproval;

	/**
	 * 결재전 결재문서 조회 가능 여부- APPR_LINE STATUS가 0(대기) 이어도 진행함에 표시 (0:미사용, 1:사용)
	 */
	private String isPreView;

	/**
	 * 협조공문일 경우 기안부서와 수신처에서 서로의 의견을 공개할지 설정(0:공개안함, 1:공개)
	 */
	private String isMessageOpen;

	/**
	 * 결재라인 표시 방식 (0:가로, 1:세로) 세로는 인장이미지 표시 안됨
	 */
	private String lineViewType;
	
	/**
	 * 문서번호 채번방식(0:전체공통 일련번호 채번, 1:부서별 일련번호 채번, 2:양식별 일련번호 채번)
	 */
	private String docNoMethod;

	/**
	 * 완료문서 전체조회(0:미사용, 1:사용)
	 */
	private String isReadAll;
	
	/**
	 * 사용자 ID
	 */
	private String userId;

	/**
	 * 사용자 ID 목록
	 */
	private List<String> userIdList;

	/**
	 * 사용자 목록
	 */
	private List<User> userList;

	@SuppressWarnings("unchecked")
	private static final List<Code<String>> MESSAGE_OPEN_LIST = Arrays.asList(
			new Code<String>("1", "ui.approval.admin.config.form.open"),
			new Code<String>("0", "ui.approval.admin.config.form.close"));

	@SuppressWarnings("unchecked")
	private static final List<Code<String>> DOC_NO_METHOD_LIST = Arrays.asList(
			new Code<String>("0", "ui.approval.admin.config.form.docNo1"),
			new Code<String>("1", "ui.approval.admin.config.form.docNo2"),
			new Code<String>("2", "ui.approval.admin.config.form.docNo3"));

	@SuppressWarnings("unchecked")
	private static final List<Code<String>> CHARGE_LIST = Arrays.asList(
			new Code<String>("0", "ui.approval.admin.config.form.select"),
			new Code<String>("1", "ui.approval.admin.config.form.allselect"));

	@SuppressWarnings("unchecked")
	private static final List<Code<String>> USAGE_LIST = Arrays.asList(
			new Code<String>("1", "ui.approval.admin.config.form.useY"),
			new Code<String>("0", "ui.approval.admin.config.form.useN"));

	@SuppressWarnings("unchecked")
	private static final List<Code<String>> LINE_VIEW_TYPE_LIST = Arrays.asList(
			new Code<String>("0", "ui.approval.admin.config.form.row"),
			new Code<String>("1", "ui.approval.admin.config.form.col"));

	public String getConfigId() {
		return configId;
	}

	public void setConfigId(String configId) {
		this.configId = configId;
	}

	public String getIsReference() {
		return isReference;
	}

	public void setIsReference(String isReference) {
		this.isReference = isReference;
	}

	public String getIsReservation() {
		return isReservation;
	}

	public void setIsReservation(String isReservation) {
		this.isReservation = isReservation;
	}

	public String getIsArbitraryDecision() {
		return isArbitraryDecision;
	}

	public void setIsArbitraryDecision(String isArbitraryDecision) {
		this.isArbitraryDecision = isArbitraryDecision;
	}

	public String getIsPassword() {
		return isPassword;
	}

	public void setIsPassword(String isPassword) {
		this.isPassword = isPassword;
	}

	public String getIsOverall() {
		return isOverall;
	}

	public void setIsOverall(String isOverall) {
		this.isOverall = isOverall;
	}

	public String getIsExam() {
		return isExam;
	}

	public void setIsExam(String isExam) {
		this.isExam = isExam;
	}

	public String getIsRefMsg() {
		return isRefMsg;
	}

	public void setIsRefMsg(String isRefMsg) {
		this.isRefMsg = isRefMsg;
	}

	public String getIsReceive() {
		return isReceive;
	}

	public void setIsReceive(String isReceive) {
		this.isReceive = isReceive;
	}

	public String getIsUseSign() {
		return isUseSign;
	}

	public void setIsUseSign(String isUseSign) {
		this.isUseSign = isUseSign;
	}

	public String getIsUpdateLine() {
		return isUpdateLine;
	}

	public void setIsUpdateLine(String isUpdateLine) {
		this.isUpdateLine = isUpdateLine;
	}

	public String getIsUpdateDoc() {
		return isUpdateDoc;
	}

	public void setIsUpdateDoc(String isUpdateDoc) {
		this.isUpdateDoc = isUpdateDoc;
	}

	public String getIsUpdateRead() {
		return isUpdateRead;
	}

	public void setIsUpdateRead(String isUpdateRead) {
		this.isUpdateRead = isUpdateRead;
	}

	public String getIsCancel() {
		return isCancel;
	}

	public void setIsCancel(String isCancel) {
		this.isCancel = isCancel;
	}

	public String getReceptionType() {
		return receptionType;
	}

	public void setReceptionType(String receptionType) {
		this.receptionType = receptionType;
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

	public String getUpdaterId() {
		return updaterId;
	}

	public void setUpdaterId(String updaterId) {
		this.updaterId = updaterId;
	}

	public String getUpdaterName() {
		return updaterName;
	}

	public void setUpdaterName(String updaterName) {
		this.updaterName = updaterName;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getIsPostApproval() {
		return isPostApproval;
	}

	public void setIsPostApproval(String isPostApproval) {
		this.isPostApproval = isPostApproval;
	}

	public String getIsPreView() {
		return isPreView;
	}

	public void setIsPreView(String isPreView) {
		this.isPreView = isPreView;
	}

	public String getIsMessageOpen() {
		return isMessageOpen;
	}

	public void setIsMessageOpen(String isMessageOpen) {
		this.isMessageOpen = isMessageOpen;
	}

	public String getDocNoMethod() {
		return docNoMethod;
	}

	public void setDocNoMethod(String docNoMethod) {
		this.docNoMethod = docNoMethod;
	}

	public static List<Code<String>> getChargeList() {
		return CHARGE_LIST;
	}

	public static List<Code<String>> getUsageList() {
		return USAGE_LIST;
	}

	public static List<Code<String>> getMessageOpenList() {
		return MESSAGE_OPEN_LIST;
	}

	public static List<Code<String>> getDocNoMethodList() {
		return DOC_NO_METHOD_LIST;
	}

	public String getPortalId() {
		return portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}

	public String getIsReadAll() {
		return isReadAll;
	}

	public void setIsReadAll(String isReadAll) {
		this.isReadAll = isReadAll;
	}

	public List<String> getUserIdList() {
		return userIdList;
	}

	public void setUserIdList(List<String> userIdList) {
		this.userIdList = userIdList;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLineViewType() {
		return lineViewType;
	}

	public void setLineViewType(String lineViewType) {
		this.lineViewType = lineViewType;
	}

	public static List<Code<String>> getLineViewTypeList() {
		return LINE_VIEW_TYPE_LIST;
	}

}
