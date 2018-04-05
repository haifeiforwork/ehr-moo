package com.lgcns.ikep4.servicepack.survey.model;


public class ResponseDetailKey extends SurveyBaseObject{
	

	
    /**
	 *
	 */
	private static final long serialVersionUID = 6048346610802188039L;

	/**
     * 설문문항별 선택답변 ID
     */
    private String answerId;

    /**
     * 설문 문항 질문 ID
     */
    private String questionId;

    /**
     * 설문 응답 ID
     */
    private String responseId;
    private String questionType;
    
    
	public ResponseDetailKey() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ResponseDetailKey(String answerId, String questionId, String responseId) {
		super();
		this.answerId = answerId;
		this.questionId = questionId;
		this.responseId = responseId;
	}

	
	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	public String getAnswerId() {
		return answerId;
	}

	public void setAnswerId(String answerId) {
		this.answerId = answerId;
	}

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public String getResponseId() {
		return responseId;
	}

	public void setResponseId(String responseId) {
		this.responseId = responseId;
	}

  
}