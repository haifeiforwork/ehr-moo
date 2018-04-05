/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.knowledgemonitor.search;

import java.util.Calendar;
import java.util.Date;

import com.lgcns.ikep4.support.base.constant.CommonConstant;
import com.lgcns.ikep4.support.base.pagecondition.BlockPageCondition;
import com.lgcns.ikep4.util.lang.DateUtil;


/**
 * 지식순위정보 페이징
 * 
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeRankBlockPageCondition.java 8635 2011-04-28 09:33:07Z
 *          jins02 $
 */
public class KnowledgeRankBlockPageCondition extends BlockPageCondition {

	/**
	 *
	 */
	private static final long serialVersionUID = -7763981078087891461L;

	/**
	 * 6개월전 계산용 (월)
	 */
	private static final int CALC_BEFORE_SIX_MONTH = -1;

	/**
	 * 6주전 계산용 (일)
	 */
	private static final int CALC_BEFORE_SIX_WEEK = -27;

	/**
	 * yyyy.MM.dd 형식에서 년도 시작위치
	 */
	private static final int POS_YEAR_START = 0;

	/**
	 * yyyy.MM.dd 형식에서 년도 끝위치
	 */
	private static final int POS_YEAR_END = 4;

	/**
	 * yyyy.MM.dd 형식에서 월 시작위치
	 */
	private static final int POS_MONTH_START = 5;

	/**
	 * yyyy.MM.dd 형식에서 월 끝위치
	 */
	private static final int POS_MONTH_END = 7;

	/**
	 * 포털ID
	 */
	private String portalId;

	/**
	 * 조회 모듈 (선택된 항목이 (,)로 분리된 문자열로 지정)
	 */
	private String moduleCodes;

	/**
	 * 표시항목 <br/>
	 * 월별(month) / 주별(week)
	 */
	private String itemType;

	/**
	 * 검색 시작일 (주별)
	 */
	private String fromDate;

	/**
	 * 검색 종료일 (주별)
	 */
	private String toDate;

	/**
	 * 검색 시작년 (월별)
	 */
	private String fromYear;

	/**
	 * 검색 시작월 (월별)
	 */
	private String fromMonth;

	/**
	 * 검색 종료년 (월별)
	 */
	private String toYear;

	/**
	 * 검색 종료월 (월별)
	 */
	private String toMonth;

	/**
	 * 누적여부 (0: 비누적, 1: 누적)
	 */
	private int sumItem;

	/**
	 * 생성자
	 */
	public KnowledgeRankBlockPageCondition() {
		sumItem = 0;
		itemType = "month";

		// 월별 (6개월)
		Date nowDate = new Date();
		Date monthFromDate = DateUtil.getRelativeDate(nowDate, 0, CALC_BEFORE_SIX_MONTH);
		String toDateString = DateUtil.getFmtDateString(nowDate, CommonConstant.DATE_FORMAT);
		String fromDateString = DateUtil.getFmtDateString(monthFromDate, CommonConstant.DATE_FORMAT);

		fromYear = fromDateString.substring(POS_YEAR_START, POS_YEAR_END);
		fromMonth = fromDateString.substring(POS_MONTH_START, POS_MONTH_END);
		toYear = toDateString.substring(POS_YEAR_START, POS_YEAR_END);
		toMonth = toDateString.substring(POS_MONTH_START, POS_MONTH_END);

		// 주별 (6주)
		Date weekToDate = DateUtil.getWeekDay(nowDate, Calendar.SATURDAY); // 금주의
																			// 토요일
		Date weekFromDate = DateUtil.getRelativeDate(weekToDate, CALC_BEFORE_SIX_WEEK);
		toDate = DateUtil.getFmtDateString(weekToDate, CommonConstant.DATE_FORMAT);
		fromDate = DateUtil.getFmtDateString(weekFromDate, CommonConstant.DATE_FORMAT);
	}

	/**
	 * 콤마로 분리된 moduleCode 를 배열로 변환
	 * 
	 * @return
	 */
	public String[] getModuleCodeArray() {
		return moduleCodes.split(CommonConstant.COMMA_SEPARATER);
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
	 * @return the moduleCodes
	 */
	public String getModuleCodes() {
		return moduleCodes;
	}

	/**
	 * @param moduleCodes the moduleCodes to set
	 */
	public void setModuleCodes(String moduleCodes) {
		this.moduleCodes = moduleCodes;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getFromYear() {
		return fromYear;
	}

	public void setFromYear(String fromYear) {
		this.fromYear = fromYear;
	}

	public String getFromMonth() {
		return fromMonth;
	}

	public void setFromMonth(String fromMonth) {
		this.fromMonth = fromMonth;
	}

	public String getToYear() {
		return toYear;
	}

	public void setToYear(String toYear) {
		this.toYear = toYear;
	}

	public String getToMonth() {
		return toMonth;
	}

	public void setToMonth(String toMonth) {
		this.toMonth = toMonth;
	}

	public int getSumItem() {
		return sumItem;
	}

	public void setSumItem(int sumItem) {
		this.sumItem = sumItem;
	}

}
