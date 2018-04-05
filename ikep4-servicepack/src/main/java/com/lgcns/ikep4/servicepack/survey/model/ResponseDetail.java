package com.lgcns.ikep4.servicepack.survey.model;

import java.util.Date;

public class ResponseDetail extends ResponseDetailKey {
	
    /**
	 *
	 */
	private static final long serialVersionUID = 3535384984133198132L;

	/**
     * 설문 응답 유형
	   [1:문항(주관식), 2:문항(객관식), 3:예시(주관식), 4:예시(객관식)]
     */
    private String responseType;

    /**
     * 설문문항 답변 일련번호
     */
    private Integer responseSeq;

    /**
     * 설문 응답 1
	[ 척도OX+5점/7점의 적용기회 여부(0, 1)
	척도NA+7점의 N/A(0, 1)
	척도수준별의 요구역량(1~5)
	그 외 선택답변 ]
     */
    private String  response1;
    
    /**
     * 설문 응답 2
	(척도OX+5점/7점, 척도NA+7점의 답변)
     */
    private String  response2;
    
    
    private Date responseDate;
    
    private String answerSeq;
    
	public String getAnswerSeq() {
		return answerSeq;
	}

	public void setAnswerSeq(String answerSeq) {
		this.answerSeq = answerSeq;
	}

	public String getResponseType() {
		return responseType;
	}

	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}

	public Integer getResponseSeq() {
		return responseSeq;
	}

	public void setResponseSeq(Integer responseSeq) {
		this.responseSeq = responseSeq;
	}

	public String getResponse1() {
		return response1;
	}

	public void setResponse1(String response1) {
		this.response1 = response1;
	}

	public String getResponse2() {
		return response2;
	}

	public void setResponse2(String response2) {
		this.response2 = response2;
	}

	public Date getResponseDate() {
		return responseDate;
	}

	public void setResponseDate(Date responseDate) {
		this.responseDate = responseDate;
	}
    
}