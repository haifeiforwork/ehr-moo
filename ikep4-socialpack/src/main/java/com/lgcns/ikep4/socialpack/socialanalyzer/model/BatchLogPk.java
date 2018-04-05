/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.socialanalyzer.model;

import java.util.Date;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * TODO Javadoc주석작성
 *
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: BatchLogPk.java 16246 2011-08-18 04:48:28Z giljae $
 */

public class BatchLogPk extends BaseObject {
	private static final long serialVersionUID = 6603014418271849542L;
	
	/**
	 * 배치종류
	 */
	private String batchType;

	/**
	 * 배치 프로그램 실행 시작일시
	 */
	private Date startDate;
	
	/**
	 * @return the batchType
	 */
	public String getBatchType() {
		return batchType;
	}
	/**
	 * @param batchType the batchType to set
	 */
	public void setBatchType(String batchType) {
		this.batchType = batchType;
	}
	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
}
