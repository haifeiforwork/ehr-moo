/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.knowledgehub.search;

import java.util.Date;

import com.lgcns.ikep4.support.base.pagecondition.BlockPageCondition;

/**
 * Knowledge Map 페이징
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMapPopularPageCondition.java 16604 2011-09-22 08:15:14Z giljae $
 */
public class KnowledgeMapPopularPageCondition extends BlockPageCondition {

	/**
	 *
	 */
	private static final long serialVersionUID = -3077046473751788114L;

	/**
	 * 포털 ID
	 */
	private String portalId;

	/**
	 * 검색시작일
	 */
	private String fromDate;

	/**
	 * 검색종료일
	 */
	private String toDate;

	/**
	 * 검색기간 Flag(실제 차이값을 월 단위로 보관)<br/>
	 * 1: 최근 1개월, 3: 최근 3개월, 6: 최근 6개월, 12: 최근 1년<br/>
	 */
	private int searchFlag;

	/**
	 * 타임존 계산된 검색시작일
	 */
	private Date convertFromDate;

	/**
	 * 타임존 계산된 검색종료일
	 */
	private Date convertToDate;
	
	private String isGoodDoc;

	/**
	 * 생성자
	 */
	public KnowledgeMapPopularPageCondition() {
		super();

		searchFlag = -1;
/*
		// 최근 1개월
		Date nowDate = new Date();
		Date monthFromDate = DateUtil.getRelativeDate(nowDate, 0, -1);

		toDate = DateUtil.getFmtDateString(nowDate, CommonConstant.DATE_FORMAT);
		fromDate = DateUtil.getFmtDateString(monthFromDate, CommonConstant.DATE_FORMAT);
*/
	}

	/**
	 * @return the fromDate
	 */
	public String getFromDate() {
		return fromDate;
	}

	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * @return the toDate
	 */
	public String getToDate() {
		return toDate;
	}

	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	/**
	 * @return the searchFlag
	 */
	public int getSearchFlag() {
		return searchFlag;
	}

	/**
	 * @param searchFlag the searchFlag to set
	 */
	public void setSearchFlag(int searchFlag) {
		this.searchFlag = searchFlag;
	}

	/**
	 * @return the portalId
	 */
	public String getPortalId() {
		return portalId;
	}

	/**
	 * @param portalId the portalId to set
	 */
	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}

	/**
	 * @return the convertFromDate
	 */
	public Date getConvertFromDate() {
		return convertFromDate;
	}

	/**
	 * @param convertFromDate the convertFromDate to set
	 */
	public void setConvertFromDate(Date convertFromDate) {
		this.convertFromDate = convertFromDate;
	}

	/**
	 * @return the convertToDate
	 */
	public Date getConvertToDate() {
		return convertToDate;
	}

	/**
	 * @param convertToDate the convertToDate to set
	 */
	public void setConvertToDate(Date convertToDate) {
		this.convertToDate = convertToDate;
	}

	public String getIsGoodDoc() {
		return isGoodDoc;
	}

	public void setIsGoodDoc(String isGoodDoc) {
		this.isGoodDoc = isGoodDoc;
	}

}
