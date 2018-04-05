/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.planner.model;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * 일정 되풀이(반복 일정) model
 * 
 * @author 신용환(combinet@gmail.com)
 * @version $Id: Recurrences.java 16240 2011-08-18 04:08:15Z giljae $
 */
public class Recurrences extends BaseObject {

	private static final long serialVersionUID = -3052942581250566670L;

	/**
	 * 반복 일정 유형 반복주기 아님 : 1 매일 : 2 매주 : 3 매월 : 4 매년
	 */
	private String repeatType;

	/**
	 * 반복 주기
	 */
	private String repeatPeriod;

	/**
	 * 반복 타입에 따른 반복 옵션
	 * 매일 : null
	 * 매주 : 1(일), 2(월), 3(화), 4(수), 5(목), 6(금), 7(토)
	 * 매월(날짜기준) : a, 날짜
	 * 매월(요일기준) : b, 포지션, 요일
	 * 매년(날짜기준) : a, 날짜, 월
	 * 매년(요일기준) : b, 포지션, 요일, 월
	 */
	private String repeatPeriodOption;

	/**
	 * 반복 시작일
	 */
	private Date startDate;

	/**
	 * 반복 종료일
	 */
	private Date endDate;

	/**
	 * 일정 시작 일자
	 */
	private Date sdStartDate;

	/**
	 * 일정 종료 일자
	 */
	private Date sdEndDate;

	private String scheduleId;

	// /////////////////////////////////////////////////////////////////////////////////

	public Recurrences() {}

	public Recurrences(String repeatType, String repeatPeriod, String repeatPeriodOption, Date startDate, Date endDate,
			Date sdStartDate, Date sdEndDate) {
		if (sdStartDate == null || sdEndDate == null) {
			throw new IllegalArgumentException("일정 시간은 필수.");
		}
		if (sdStartDate.compareTo(sdEndDate) > 0) {
			throw new IllegalArgumentException("일정 종료시간은 시작 시간보다 전일 수 없음.");
		}
		this.repeatType = repeatType;
		this.repeatPeriod = repeatPeriod;
		this.repeatPeriodOption = repeatPeriodOption;

		this.startDate = DateUtils.truncate(startDate, Calendar.DATE);
		this.endDate = DateUtils.truncate(endDate, Calendar.DATE);

		this.sdStartDate = sdStartDate;
		this.sdEndDate = sdEndDate;
	}

	public Recurrences(String scheduleId, String repeatType, String repeatPeriod, String repeatPeriodOption,
			Date startDate, Date endDate, Date sdStartDate, Date sdEndDate) {
		this(repeatType, repeatPeriod, repeatPeriodOption, startDate, endDate, sdStartDate, sdEndDate);
		this.scheduleId = scheduleId;
	}

	// /////////////////////////////////////////////////////////////////////////

	/**
	 * @return the repeatType
	 */
	public String getRepeatType() {
		return repeatType;
	}

	/**
	 * @param repeatType the repeatType to set
	 */
	public void setRepeatType(String repeatType) {
		this.repeatType = repeatType;
	}

	/**
	 * @return the repeatPeriod
	 */
	public String getRepeatPeriod() {
		return repeatPeriod;
	}

	/**
	 * @param repeatPeriod the repeatPeriod to set
	 */
	public void setRepeatPeriod(String repeatPeriod) {
		this.repeatPeriod = repeatPeriod;
	}

	/**
	 * @return the repeatPeriodOption
	 */
	public String getRepeatPeriodOption() {
		return repeatPeriodOption;
	}

	/**
	 * @param repeatPeriodOption the repeatPeriodOption to set
	 */
	public void setRepeatPeriodOption(String repeatPeriodOption) {
		this.repeatPeriodOption = repeatPeriodOption;
	}

	/**
	 * @return the scheduleId
	 */
	public String getScheduleId() {
		return scheduleId;
	}

	/**
	 * @param scheduleId the scheduleId to set
	 */
	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set TODO: Jackson json deserialize 시
	 *            발생되는 특이사항 보완 한후 원복할 것.
	 */
	public void setStartDate(Date startDate) {
		this.startDate = DateUtils.truncate(startDate, Calendar.DATE);
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set TODO: Jackson json deserialize 시 발생되는
	 *            특이사항 보완 한후 원복할 것.
	 */
	public void setEndDate(Date endDate) {
		this.endDate = DateUtils.truncate(endDate, Calendar.DATE);
	}

	/**
	 * @return the sdStartDate
	 */
	public Date getSdStartDate() {
		return sdStartDate;
	}

	/**
	 * @param sdStartDate the sdStartDate to set
	 */
	public void setSdStartDate(Date sdStartDate) {
		this.sdStartDate = sdStartDate;
	}

	/**
	 * @return the sdEndDate
	 */
	public Date getSdEndDate() {
		return sdEndDate;
	}

	/**
	 * @param sdEndDate the sdEndDate to set
	 */
	public void setSdEndDate(Date sdEndDate) {
		this.sdEndDate = sdEndDate;
	}
}
