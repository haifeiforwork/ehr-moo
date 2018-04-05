package com.lgcns.ikep4.lightpack.planner.restful.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="alarm")
public class PlannerDataAlarm {

	private String alarmType;
	private String alarmTime;
	
	public PlannerDataAlarm(String alarmType, String alarmTime) {
		super();
		this.alarmType = alarmType;
		this.alarmTime = alarmTime;
	}

	public String getAlarmType() {
		return alarmType;
	}
	
	public String getAlarmTime() {
		return alarmTime;
	}

	
	
	public void setAlarmType(String alarmType) {
		this.alarmType = alarmType;
	}

	public void setAlarmTime(String alarmTime) {
		this.alarmTime = alarmTime;
	}
}