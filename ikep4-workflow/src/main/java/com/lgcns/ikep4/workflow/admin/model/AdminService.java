/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.admin.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * 워크플로우 - 현황관리 - 프로세스 관리 - 프로세스 상세화면
 *
 * @author 이성훈(smart727@built1.com)
 * @version $Id: AdminService.java 16245 2011-08-18 04:28:59Z giljae $
 */
public class AdminService extends BaseObject{
	
	private static final long serialVersionUID = 5911006115938818166L;
	
	private String serviceId;     //서비스 아이디
	private String baseCheck;     //기본체크
	private String createDate;    //생성일자
	private String applyStartDate;//적용시작일자
	private String applyEndDate;  //적용종료일자
	private String updateDate;    //수정일자
	private String processId;     //프로세스 아이디
	private String processVer;    //프로세스 버전
	//추가
	private String processName;   //프로세스명
	private String author;        //작성자
	private String description;   //설명
	
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getBaseCheck() {
		return baseCheck;
	}
	public void setBaseCheck(String baseCheck) {
		this.baseCheck = baseCheck;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public String getProcessId() {
		return processId;
	}
	public void setProcessId(String processId) {
		this.processId = processId;
	}
	public String getProcessVer() {
		return processVer;
	}
	public void setProcessVer(String processVer) {
		this.processVer = processVer;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getApplyStartDate() {
		return applyStartDate;
	}
	public void setApplyStartDate(String applyStartDate) {
		this.applyStartDate = applyStartDate;
	}
	public String getApplyEndDate() {
		return applyEndDate;
	}
	public void setApplyEndDate(String applyEndDate) {
		this.applyEndDate = applyEndDate;
	}
}
