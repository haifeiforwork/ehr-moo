/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.admin.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/*
 * 통계
 */
public class ApprStat extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = 6193603775295839562L;

	/**
	 * 포탈 id
	 */
	private String portalId;

	/**
	 * 결재문서 id
	 */
	private String apprId;

	/**
	 * 결재문서 번호
	 */
	private String apprDocNo;

	/**
	 * 결재문서 제목
	 */
	private String apprTitle;

	/**
	 * 결재문서 기안자 id
	 */
	private String registerId;

	/**
	 * /결재문서 기안자 이름
	 */
	private String registerName;

	/**
	 * 기안일자
	 */
	private Date apprReqDate;

	/**
	 * 완료일자
	 */
	private Date apprEndDate;

	/**
	 * 결재문서 처리 시간
	 */
	private int apprLeadTime;

	/**
	 * 결재문서 처리 시간 Str
	 */
	private String apprLeadTimeStr;

	/**
	 * 결재자 ID
	 */
	private String approverId;

	/**
	 * 결재자 명
	 */
	private String approverName;

	/**
	 * 결재 순서
	 */
	private int apprOrder;

	/**
	 * 결재일
	 */
	private Date apprDate;

	/**
	 * 양식사용통계 ID
	 */
	private String formStatsId;

	/**
	 * 양식 ID
	 */
	private String formId;

	/**
	 * 양식 명
	 */
	private String formName;

	/**
	 * 합계그룹핑ID
	 */
	private String gId;

	/**
	 * 부모 양식 ID
	 */
	private String parentformId;

	/**
	 * 결재 시스템 ID
	 */
	private String systemId;

	/**
	 * 결재 완료 문서 개수
	 */
	private int formCnt;

	/**
	 * 통계날짜 - 월기준
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date statsDate;

	/**
	 * 등록 일자
	 */
	private Date registDate;

	/**
	 * 부서 ID
	 */
	private String groupId;

	/**
	 * 부서명
	 */
	private String groupName;

	private int cnt1;

	private int cnt2;

	private int cnt3;

	private int cnt4;

	private int cnt5;

	private int cnt6;

	private int cnt7;

	private int cnt8;

	private int cnt9;

	private int cnt10;

	private int cnt11;

	private int cnt12;

	private float time1;

	private float time2;

	private float time3;

	private float time4;

	private float time5;

	private float time6;

	private float time7;

	private float time8;

	private float time9;

	private float time10;

	private float time11;

	private float time12;

	private int totSum;

	private int cntSum;

	private float timeSum;

	public String getPortalId() {
		return portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}

	public String getApprId() {
		return apprId;
	}

	public void setApprId(String apprId) {
		this.apprId = apprId;
	}

	public String getApprTitle() {
		return apprTitle;
	}

	public void setApprTitle(String apprTitle) {
		this.apprTitle = apprTitle;
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

	public Date getApprReqDate() {
		return apprReqDate;
	}

	public void setApprReqDate(Date apprReqDate) {
		this.apprReqDate = apprReqDate;
	}

	public Date getApprEndDate() {
		return apprEndDate;
	}

	public void setApprEndDate(Date apprEndDate) {
		this.apprEndDate = apprEndDate;
	}

	public int getApprLeadTime() {
		return apprLeadTime;
	}

	public void setApprLeadTime(int apprLeadTime) {
		this.apprLeadTime = apprLeadTime;
	}

	public String getApprDocNo() {
		return apprDocNo;
	}

	public void setApprDocNo(String apprDocNo) {
		this.apprDocNo = apprDocNo;
	}

	public String getApprLeadTimeStr() {
		return apprLeadTimeStr;
	}

	public void setApprLeadTimeStr(String apprLeadTimeStr) {
		this.apprLeadTimeStr = apprLeadTimeStr;
	}

	public String getApproverId() {
		return approverId;
	}

	public void setApproverId(String approverId) {
		this.approverId = approverId;
	}

	public int getApprOrder() {
		return apprOrder;
	}

	public void setApprOrder(int apprOrder) {
		this.apprOrder = apprOrder;
	}

	public Date getApprDate() {
		return apprDate;
	}

	public void setApprDate(Date apprDate) {
		this.apprDate = apprDate;
	}

	public String getApproverName() {
		return approverName;
	}

	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}

	public String getFormStatsId() {
		return formStatsId;
	}

	public void setFormStatsId(String formStatsId) {
		this.formStatsId = formStatsId;
	}

	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	public String getParentformId() {
		return parentformId;
	}

	public void setParentformId(String parentformId) {
		this.parentformId = parentformId;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public int getFormCnt() {
		return formCnt;
	}

	public void setFormCnt(int formCnt) {
		this.formCnt = formCnt;
	}

	public Date getStatsDate() {
		return statsDate;
	}

	public void setStatsDate(Date statsDate) {
		this.statsDate = statsDate;
	}

	public Date getRegistDate() {
		return registDate;
	}

	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public String getgId() {
		return gId;
	}

	public void setgId(String gId) {
		this.gId = gId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public int getCnt1() {
		return cnt1;
	}

	public void setCnt1(int cnt1) {
		this.cnt1 = cnt1;
	}

	public int getCnt2() {
		return cnt2;
	}

	public void setCnt2(int cnt2) {
		this.cnt2 = cnt2;
	}

	public int getCnt3() {
		return cnt3;
	}

	public void setCnt3(int cnt3) {
		this.cnt3 = cnt3;
	}

	public int getCnt4() {
		return cnt4;
	}

	public void setCnt4(int cnt4) {
		this.cnt4 = cnt4;
	}

	public int getCnt5() {
		return cnt5;
	}

	public void setCnt5(int cnt5) {
		this.cnt5 = cnt5;
	}

	public int getCnt6() {
		return cnt6;
	}

	public void setCnt6(int cnt6) {
		this.cnt6 = cnt6;
	}

	public int getCnt7() {
		return cnt7;
	}

	public void setCnt7(int cnt7) {
		this.cnt7 = cnt7;
	}

	public int getCnt8() {
		return cnt8;
	}

	public void setCnt8(int cnt8) {
		this.cnt8 = cnt8;
	}

	public int getCnt9() {
		return cnt9;
	}

	public void setCnt9(int cnt9) {
		this.cnt9 = cnt9;
	}

	public int getCnt10() {
		return cnt10;
	}

	public void setCnt10(int cnt10) {
		this.cnt10 = cnt10;
	}

	public int getCnt11() {
		return cnt11;
	}

	public void setCnt11(int cnt11) {
		this.cnt11 = cnt11;
	}

	public int getCnt12() {
		return cnt12;
	}

	public void setCnt12(int cnt12) {
		this.cnt12 = cnt12;
	}

	public float getTime1() {
		return time1;
	}

	public void setTime1(float time1) {
		this.time1 = time1;
	}

	public float getTime2() {
		return time2;
	}

	public void setTime2(float time2) {
		this.time2 = time2;
	}

	public float getTime3() {
		return time3;
	}

	public void setTime3(float time3) {
		this.time3 = time3;
	}

	public float getTime4() {
		return time4;
	}

	public void setTime4(float time4) {
		this.time4 = time4;
	}

	public float getTime5() {
		return time5;
	}

	public void setTime5(float time5) {
		this.time5 = time5;
	}

	public float getTime6() {
		return time6;
	}

	public void setTime6(float time6) {
		this.time6 = time6;
	}

	public float getTime7() {
		return time7;
	}

	public void setTime7(float time7) {
		this.time7 = time7;
	}

	public float getTime8() {
		return time8;
	}

	public void setTime8(float time8) {
		this.time8 = time8;
	}

	public float getTime9() {
		return time9;
	}

	public void setTime9(float time9) {
		this.time9 = time9;
	}

	public float getTime10() {
		return time10;
	}

	public void setTime10(float time10) {
		this.time10 = time10;
	}

	public float getTime11() {
		return time11;
	}

	public void setTime11(float time11) {
		this.time11 = time11;
	}

	public float getTime12() {
		return time12;
	}

	public void setTime12(float time12) {
		this.time12 = time12;
	}

	public int getTotSum() {
		return totSum;
	}

	public void setTotSum(int totSum) {
		this.totSum = totSum;
	}

	public int getCntSum() {
		return cntSum;
	}

	public void setCntSum(int cntSum) {
		this.cntSum = cntSum;
	}

	public float getTimeSum() {
		return timeSum;
	}

	public void setTimeSum(float timeSum) {
		this.timeSum = timeSum;
	}

}
