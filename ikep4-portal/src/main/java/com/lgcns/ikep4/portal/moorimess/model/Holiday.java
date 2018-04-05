
package com.lgcns.ikep4.portal.moorimess.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;

public class Holiday extends BaseObject {
	private String holidayTypes;
	private String startDate;
	private String endDate;
	private String startTime;
	private String endTime;
	private String reason1;
	private String reason2;
	
	public String getHolidayTypes() {
		return holidayTypes;
	}
	public void setHolidayTypes(String holidayTypes) {
		this.holidayTypes = holidayTypes;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getReason1() {
		return reason1;
	}
	public void setReason1(String reason1) {
		this.reason1 = reason1;
	}
	public String getReason2() {
		return reason2;
	}
	public void setReason2(String reason2) {
		this.reason2 = reason2;
	}
}
