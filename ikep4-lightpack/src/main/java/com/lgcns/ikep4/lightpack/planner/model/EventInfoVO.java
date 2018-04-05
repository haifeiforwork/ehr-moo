/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.planner.model;

import java.util.Date;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * javascript와 event data 연동용, parameter로 사용(수정/삭제시)
 *
 * @author  신용환(combinet@gmail.com)
 * @version $Id: EventInfoVO.java 16240 2011-08-18 04:08:15Z giljae $
 */
public class EventInfoVO extends BaseObject {

	private static final long serialVersionUID = 9056003740472272841L;
	
	private String scheduleId;
	private Date startDate;
	private Date endDate;
	private int repeatType;
	private int repeatPeriod;
	private String repeatPeriodOption;
	private Date repeatStartDate;
	private Date repeatEndDate;
	private Date updateStart;
	private Date updateEnd;
	
	private Date prevStart;
	private Date prevEnd;
	private Date nextStart;
	private Date nextEnd;
	private Date start;
	private Date end;
	
	private int wholeday;
	
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public int getRepeatType() {
		return repeatType;
	}
	public void setRepeatType(int repeatType) {
		this.repeatType = repeatType;
	}
	public int getRepeatPeriod() {
		return repeatPeriod;
	}
	public void setRepeatPeriod(int repeatPeriod) {
		this.repeatPeriod = repeatPeriod;
	}
	public String getRepeatPeriodOption() {
		return repeatPeriodOption;
	}
	public void setRepeatPeriodOption(String repeatPeriodOption) {
		this.repeatPeriodOption = repeatPeriodOption;
	}
	public Date getRepeatStartDate() {
		return repeatStartDate;
	}
	public void setRepeatStartDate(Date repeatStartDate) {
		this.repeatStartDate = repeatStartDate;
	}
	public Date getRepeatEndDate() {
		return repeatEndDate;
	}
	public void setRepeatEndDate(Date repeatEndDate) {
		this.repeatEndDate = repeatEndDate;
	}
	public Date getUpdateStart() {
		return updateStart;
	}
	public void setUpdateStart(Date updateStart) {
		this.updateStart = updateStart;
	}
	public Date getUpdateEnd() {
		return updateEnd;
	}
	public void setUpdateEnd(Date updateEnd) {
		this.updateEnd = updateEnd;
	}
	public Date getPrevStart() {
		return prevStart;
	}
	public void setPrevStart(Date prevStart) {
		this.prevStart = prevStart;
	}
	public Date getPrevEnd() {
		return prevEnd;
	}
	public void setPrevEnd(Date prevEnd) {
		this.prevEnd = prevEnd;
	}
	public Date getNextStart() {
		return nextStart;
	}
	public void setNextStart(Date nextStart) {
		this.nextStart = nextStart;
	}
	public Date getNextEnd() {
		return nextEnd;
	}
	public void setNextEnd(Date nextEnd) {
		this.nextEnd = nextEnd;
	}
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	public String getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId;
	}
	public int getWholeday() {
		return wholeday;
	}
	public void setWholeday(int wholeday) {
		this.wholeday = wholeday;
	}
}
