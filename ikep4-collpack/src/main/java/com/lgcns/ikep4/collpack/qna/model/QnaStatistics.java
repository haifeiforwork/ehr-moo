package com.lgcns.ikep4.collpack.qna.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;

public class QnaStatistics extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = -6789217708958021556L;

	/**
	 * 답변소요시간에 대한 평균값( 답변소요시간의 총합 / 답변이 등록된 질문수 * 100)
	 */
	private double averageAnswerTime;

	/**
	 * 질문에 대한 답변이 등록된 비율( 답변이 등록된 질문수 / 총 질문수 * 100)
	 */
    private int answerRatio;
    
    /**
     * 포털 ID
     */
    private String portalId;

	public double getAverageAnswerTime() {
		return averageAnswerTime;
	}

	public void setAverageAnswerTime(double averageAnswerTime) {
		this.averageAnswerTime = averageAnswerTime;
	}

	public int getAnswerRatio() {
		return answerRatio;
	}

	public void setAnswerRatio(int answerRatio) {
		this.answerRatio = answerRatio;
	}

	public String getPortalId() {
		return portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}
    
}