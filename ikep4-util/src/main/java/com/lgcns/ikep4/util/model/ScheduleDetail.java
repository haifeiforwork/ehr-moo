package com.lgcns.ikep4.util.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;
import java.util.Date;

public class ScheduleDetail extends BaseObject {

	private String pernr;
	
	private String begda;

	private String endda;
	
	private String atext;
	
	private String requestReason;
	
	private String beguz;
	
	private String enduz;
	
	private String whole;
	
	private String awart;
	
	public String getPernr() {
		return pernr;
	}

	public void setPernr(String pernr) {
		this.pernr = pernr;
	}


	public String getAtext() {
		return atext;
	}

	public void setAtext(String atext) {
		this.atext = atext;
	}

	public String getRequestReason() {
		return requestReason;
	}

	public void setRequestReason(String requestReason) {
		this.requestReason = requestReason;
	}

	public String getBeguz() {
		return beguz;
	}

	public void setBeguz(String beguz) {
		this.beguz = beguz;
	}

	public String getEnduz() {
		return enduz;
	}

	public void setEnduz(String enduz) {
		this.enduz = enduz;
	}

	public String getBegda() {
		return begda;
	}

	public void setBegda(String begda) {
		this.begda = begda;
	}

	public String getEndda() {
		return endda;
	}

	public void setEndda(String endda) {
		this.endda = endda;
	}

	public String getWhole() {
		return whole;
	}

	public void setWhole(String whole) {
		this.whole = whole;
	}

	public String getAwart() {
		return awart;
	}

	public void setAwart(String awart) {
		this.awart = awart;
	}

}
