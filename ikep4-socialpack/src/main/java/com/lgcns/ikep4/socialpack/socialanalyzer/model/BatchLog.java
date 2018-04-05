/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.socialanalyzer.model;

import java.util.Date;


/**
 * TODO Javadoc주석작성
 *
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: BatchLog.java 16246 2011-08-18 04:48:28Z giljae $
 */

public class BatchLog extends BatchLogPk {
	private static final long serialVersionUID = 5522607362001226499L;
	
	/**
	 * 배치 프로그램 실행 종료일시 
	 */
	private Date endDate;
	
	/**
	 * 배치 프로그램 실행 결과( 0 : 성공, 1 : 실패) 
	 */
	private String isSuccess;
	
	/**
	 * 배치 프로그램 실행 결과 설명 
	 */
	private String description;
	
	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	/**
	 * @return the isSuccess
	 */
	public String getIsSuccess() {
		return isSuccess;
	}
	/**
	 * @param isSuccess the isSuccess to set
	 */
	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
}
