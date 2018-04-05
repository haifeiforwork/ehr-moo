/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.knowledgemonitor.model;

import java.util.Date;

/**
 * Knowledge Monitor KnowledgeMonitorStatisticsBody model
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMonitorStatisticsBody.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class KnowledgeMonitorStatisticsBody extends KnowledgeMonitorStatisticsPK {

	/**
	 *
	 */
	private static final long serialVersionUID = -2736038975324285384L;

	/**
	 * 공개 게시물 수
	 */
	private int publicDocCount;

	/**
	 * 비공개 게시물 수
	 */
	private int closedDocCount;

	/**
	 * 연도(YYYY)
	 */
	private String year;

	/**
	 * 월(MM)
	 */
	private String month;

	/**
	 * 해당일이 속하는 주의 첫번째일 (일요일))
	 */
	private Date weekStartDate;

	/**
	 * @return the publicDocCount
	 */
	public int getPublicDocCount() {
		return publicDocCount;
	}

	/**
	 * @param publicDocCount the publicDocCount to set
	 */
	public void setPublicDocCount(int publicDocCount) {
		this.publicDocCount = publicDocCount;
	}

	/**
	 * @return the closedDocCount
	 */
	public int getClosedDocCount() {
		return closedDocCount;
	}

	/**
	 * @param closedDocCount the closedDocCount to set
	 */
	public void setClosedDocCount(int closedDocCount) {
		this.closedDocCount = closedDocCount;
	}

	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}

	/**
	 * @return the month
	 */
	public String getMonth() {
		return month;
	}

	/**
	 * @param month the month to set
	 */
	public void setMonth(String month) {
		this.month = month;
	}

	/**
	 * @return the weekStartDate
	 */
	public Date getWeekStartDate() {
		return weekStartDate;
	}

	/**
	 * @param weekStartDate the weekStartDate to set
	 */
	public void setWeekStartDate(Date weekStartDate) {
		this.weekStartDate = weekStartDate;
	}

}
