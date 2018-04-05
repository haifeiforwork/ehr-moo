/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.poll.model;

import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * TODO Javadoc주석작성
 * 
 * @author 서혜숙
 * @version $Id: Answer.java 16258 2011-08-18 05:37:22Z giljae $
 */
public class Answer extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = 4384194596792625462L;

	/**
	 * 온라인 투표 ID
	 */
	@NotEmpty
	private String pollId;
	
	/**
	 * 등록자 ID
	 */
	@NotEmpty
	private String registerId;		

	/**
	 * 등록자 이름
	 */
	@NotEmpty
	private String registerName;	

	/**
	 * 등록일시
	 */
	@NotEmpty
	private Date registDate;

	
	/**
	 * 질문에 대한 답변 항목 ID
	 */
	@NotEmpty
	private String answerId;
	
	/**
	 * answer title 들 
	 */
	@NotEmpty
	private String[] answerTitles;	
	
	/**
	 * answer title
	 */
	@NotEmpty
	private String answerTitle;		
	
	/**
	 * 투표 갯수
	 */
	@NotEmpty	
	private int answerTotal;
	
	/**
	 * 총 투표 갯수
	 */
	@NotEmpty	
	private int answerTotalSum;		

	/**
	 * 투표 퍼센트
	 */
	@NotEmpty	
	private int answerPercent;

	/**
	 * @return the registerId
	 */		
	public String getRegisterId() {
		return registerId;
	}

	/**
	 * @param registerId the registerId to set
	 */		
	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}	
	
	/**
	 * @return the registerName
	 */		
	public String getRegisterName() {
		return registerName;
	}

	/**
	 * @param registerName the registerName to set
	 */		
	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}

	/**
	 * @return the registDate
	 */		
	public Date getRegistDate() {
		return registDate;
	}

	/**
	 * @param registDate the registDate to set
	 */		
	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}	
	
	public String getPollId() {
		return pollId;
	}

	public void setPollId(String pollId) {
		this.pollId = pollId;
	}

	public String getAnswerId() {
		return answerId;
	}

	public void setAnswerId(String answerId) {
		this.answerId = answerId;
	}

	public String[] getAnswerTitles() {
		return answerTitles;
	}

	public void setAnswerTitles(String[] answerTitles) {
		if ( answerTitles != null ) {
			this.answerTitles = new String[answerTitles.length];
			System.arraycopy(answerTitles, 0, this.answerTitles, 0, answerTitles.length);
		}		
	}

	
	public String getAnswerTitle() {
		return answerTitle;
	}

	public void setAnswerTitle(String answerTitle) {
		this.answerTitle = answerTitle;
	}

	public int getAnswerTotal() {
		return answerTotal;
	}

	public void setAnswerTotal(int answerTotal) {
		this.answerTotal = answerTotal;
	}		
	
	public int getAnswerTotalSum() {
		return answerTotalSum;
	}

	public void setAnswerTotalSum(int answerTotalSum) {
		this.answerTotalSum = answerTotalSum;
	}
	
	public int getAnswerPercent() {
		return answerPercent;
	}

	public void setAnswerPercent(int answerPercent) {
		this.answerPercent = answerPercent;
	}	

}
