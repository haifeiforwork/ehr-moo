package com.lgcns.ikep4.lightpack.planner.model;

import java.util.Date;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * 휴무일
 *
 * @author  신용환(combinet@gmail.com)
 * @version $Id: Holiday.java 16240 2011-08-18 04:08:15Z giljae $
 */
public class Holiday extends BaseObject {
	private static final long serialVersionUID = 4432027501502325340L;
	
	private String holidayId;
	
	//휴무일 매년 반복 여부( 0 : 반복, 1 : 비반복)
	private int yearRepeat = 0;

	private Date holidayDate;
	private String holidayDateStr;
	
	private String holidayName;
	private String portalId;
	private String registerId;
	private String registerName;
	private Date registDate;
	private String nation;
	
	public Holiday() {}
	
	public Holiday(String holidayName, Date holidayDate) {
		this.holidayName = holidayName;
		this.holidayDate = holidayDate;
	}
	
	public String getHolidayId() {
		return holidayId;
	}
	public void setHolidayId(String holidayId) {
		this.holidayId = holidayId;
	}
	public int getYearRepeat() {
		return yearRepeat;
	}
	public void setYearRepeat(int yearRepeat) {
		this.yearRepeat = yearRepeat;
	}
	public String getHolidayName() {
		return holidayName;
	}
	public void setHolidayName(String holidayName) {
		this.holidayName = holidayName;
	}
	public String getPortalId() {
		return portalId;
	}
	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}
	public String getRegisterId() {
		return registerId;
	}
	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}
	public String getRegisterName() {
		return registerName;
	}
	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}
	public Date getRegistDate() {
		return registDate;
	}
	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}

	public Date getHolidayDate() {
		return holidayDate;
	}

	public void setHolidayDate(Date holidayDate) {
		this.holidayDate = holidayDate;
	}
	
	public String getHolidayDateStr() {
		return holidayDateStr;
	}

	public void setHolidayDateStr(String holidayDateStr) {
		this.holidayDateStr = holidayDateStr;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}
}
