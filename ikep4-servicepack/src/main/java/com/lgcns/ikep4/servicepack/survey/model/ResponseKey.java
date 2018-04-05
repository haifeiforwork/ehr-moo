package com.lgcns.ikep4.servicepack.survey.model;


public class ResponseKey extends SurveyBaseObject{

	/**
	 *
	 */
	private static final long serialVersionUID = -6136924764360863681L;
	/**
	 * 설문 응답 ID
	 */
	private String responseId;
	/**
	 * 설문 응답자  ID
	 */
	private String responseUserId;
	
	
	
	public ResponseKey() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ResponseKey(String responseId, String responseUserId) {
		super();
		this.responseId = responseId;
		this.responseUserId = responseUserId;
	}
	
	public String getResponseId() {
		return responseId;
	}
	public void setResponseId(String responseId) {
		this.responseId = responseId;
	}
	public String getResponseUserId() {
		return responseUserId;
	}
	public void setResponseUserId(String responseUserId) {
		this.responseUserId = responseUserId;
	}

}