package com.lgcns.ikep4.servicepack.survey.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.lgcns.ikep4.servicepack.survey.util.NumberUtils;

public class Response extends ResponseKey {

	/**
	 *
	 */
	private static final long serialVersionUID = -5271362650249413461L;
	/**
	 * 설문 ID
	 */
	private String surveyId;
	/**
	 * 응답 여부
	[ 0 : 응답, 1 : 거부(설문불참) ]
	 */
	private Integer response;
	/**
	 * 설문 응답자 메일 주소
	 */
	private String responseUserMail;
	/**
	 * 설문 응답 거부 사유(선택형)
	 */
	private Integer surveyRejectReason1;
	/**
	 * 설문 응답 거부 사유(직접입력)
	 */
	private String surveyRejectReason2;
	/**
	 * 설문 응답 일시
	 */
	@DateTimeFormat(pattern="yyyy.MM.dd")
	private Date responseDate;
	
	private Integer responseCnt;
	private Integer totalSendLog;
	
	
	
	private int day;
	
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public Integer getResponseCnt() {
		return responseCnt;
	}
	public void setResponseCnt(Integer responseCnt) {
		this.responseCnt = responseCnt;
	}
	private List<ResponseDetail> responseDetail = new ArrayList<ResponseDetail>();
	
	
	
	public List<ResponseDetail> getResponseDetail() {
		return responseDetail;
	}
	public void setResponseDetail(List<ResponseDetail> responseDetail) {
		this.responseDetail = responseDetail;
	}
	public String getSurveyId() {
		return surveyId;
	}
	public void setSurveyId(String surveyId) {
		this.surveyId = surveyId;
	}
	public Integer getResponse() {
		return response;
	}
	public void setResponse(Integer response) {
		this.response = response;
	}
	public String getResponseUserMail() {
		return responseUserMail;
	}
	public void setResponseUserMail(String responseUserMail) {
		this.responseUserMail = responseUserMail;
	}
	public Integer getSurveyRejectReason1() {
		return surveyRejectReason1;
	}
	public void setSurveyRejectReason1(Integer surveyRejectReason1) {
		this.surveyRejectReason1 = surveyRejectReason1;
	}
	public String getSurveyRejectReason2() {
		return surveyRejectReason2;
	}
	public void setSurveyRejectReason2(String surveyRejectReason2) {
		this.surveyRejectReason2 = surveyRejectReason2;
	}
	public Date getResponseDate() {
		return responseDate;
	}
	public void setResponseDate(Date responseDate) {
		this.responseDate = responseDate;
	}
	public double getResponseAvg() {
		return NumberUtils.round2(responseCnt, totalSendLog);
	}
	
	public Integer getTotalSendLog() {
		return totalSendLog;
	}
	public void setTotalSendLog(Integer totalSendLog) {
		this.totalSendLog = totalSendLog;
	}
}