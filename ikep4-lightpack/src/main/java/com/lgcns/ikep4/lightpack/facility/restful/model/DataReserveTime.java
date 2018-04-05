package com.lgcns.ikep4.lightpack.facility.restful.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="reserveTime")
public class DataReserveTime {

	private String reserveStartTime;
	private String reserveEndTime;
	
	public DataReserveTime(String reserveStartTime, String reserveEndTime) {
		
		super();
		
		this.reserveStartTime = reserveStartTime;
		this.reserveEndTime = reserveEndTime;
	}

	public String getReserveStartTime() {
		return reserveStartTime;
	}

	public String getReserveEndTime() {
		return reserveEndTime;
	}
	


	public void setReserveStartTime(String reserveStartTime) {
		this.reserveStartTime = reserveStartTime;
	}
	
	public void setReserveEndTime(String reserveEndTime) {
		this.reserveEndTime = reserveEndTime;
	}
}