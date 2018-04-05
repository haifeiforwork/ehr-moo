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
 * TODO Javadoc주석작성
 *
 * @author jeehye(jjang2g79@naver.com)
 * @version $Id: ApprEntrust.java 16234 2011-08-18 02:44:36Z giljae $
 */
public class ApprEntrust extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8900711193377442735L;

	private String seqId;
	
	/**
	 * 위임 ID
	 */
	private String entrustId;
	
	/**
	 * 포탈 ID
	 */
	private String portalId;
	
	/**
	 * 위임자
	 */
	private String userId;
	
	/**
	 * 위임자
	 */
	private String userName;
	
	/**
	 * 수임자
	 */
	private String signUserId;
	
	/**
	 * 수임자 이름
	 */
	private String signUserName;
	
	/**
	 * 사용유무(0:사용안함,1:사용)
	 */
	private String usage;
	
	/**
	 * 위임기간 유효성 여부
	 */
	private String isValidDate;
	
	/**
	 * 위임시작일
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd hh:mm")
	private Date startDate;
	
	private String 	startDateStr;
	
	private String 	endDateStr;
	
	/**
	 * 위임종료일
	 */	
	@DateTimeFormat(pattern="yyyy-MM-dd hh:mm")
	private Date endDate;
	
	/**
	 * 위임사유
	 */
	@Size(min=0, max=500)
	private String reason;
	
	/**
	 * 등록일자
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd hh:mm")
	private Date registDate;
	
	/**
	 * 수정일자
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd hh:mm")
	private Date updateDate;
	
	
	@SuppressWarnings("unchecked")
	private static final List<Code<String>> ENTRUST_CLASS_LIST = Arrays.asList(
			new Code<String>("1",  "ui.workflow.workplace.personal.setting"),
			new Code<String>("0",  "ui.workflow.workplace.personal.unsetting")
	);


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getSignUserId() {
		return signUserId;
	}

	public void setSignUserId(String signUserId) {
		this.signUserId = signUserId;
	}


	public String getUsage() {
		return usage;
	}

	public Date getStartDate() {
		return startDate;
	}


	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}


	public Date getEndDate() {
		return endDate;
	}


	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


	public void setUsage(String usage) {
		this.usage = usage;
	}


	
	public String getReason() {
		return reason;
	}


	public void setReason(String reason) {
		this.reason = reason;
	}


	public Date getRegistDate() {
		return registDate;
	}


	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}


	public Date getUpdateDate() {
		return updateDate;
	}


	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public static List<Code<String>> getEntrustClassList() {
		return ENTRUST_CLASS_LIST;
	}


	public String getSignUserName() {
		return signUserName;
	}


	public void setSignUserName(String signUserName) {
		this.signUserName = signUserName;
	}


	public String getStartDateStr() {
		return startDateStr;
	}


	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}


	public String getEndDateStr() {
		return endDateStr;
	}


	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}


	public String getSeqId() {
		return seqId;
	}


	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}


	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getPortalId() {
		return portalId;
	}


	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}


	public String getEntrustId() {
		return entrustId;
	}


	public void setEntrustId(String entrustId) {
		this.entrustId = entrustId;
	}


	public String getIsValidDate() {
		return isValidDate;
	}


	public void setIsValidDate(String isValidDate) {
		this.isValidDate = isValidDate;
	}
	
}
