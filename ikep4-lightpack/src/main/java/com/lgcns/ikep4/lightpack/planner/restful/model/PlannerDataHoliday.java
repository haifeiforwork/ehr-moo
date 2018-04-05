package com.lgcns.ikep4.lightpack.planner.restful.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="holiday")
public class PlannerDataHoliday {

	private String holidayName;
	private String holidayDate;
	
	public PlannerDataHoliday(String holidayName, String holidayDate) {
		super();
		this.holidayDate = holidayDate;
		this.holidayName = holidayName;
	}

	public String getHolidayName() {
		return holidayName;
	}
	
	public String getHolidayDate() {
		return holidayDate;
	}

	public void setHolidayName(String holidayName) {
		this.holidayName = holidayName;
	}

	public void setHolidayDate(String holidayDate) {
		this.holidayDate = holidayDate;
	}

}