package com.lgcns.ikep4.util.ical.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * IDurVO
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: IDur.java 16247 2011-08-18 04:54:29Z giljae $
 */
public class IDur extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = 3475467942344805299L;

	/**
	 * 초
	 */
	private int seconds;

	/**
	 * 분
	 */
	private int minutes;

	/**
	 * 일
	 */
	private int days;

	/**
	 * 시
	 */
	private int hours;

	/**
	 * 요일
	 */
	private int weeks;

	public int getSeconds() {
		return seconds;
	}

	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	public int getWeeks() {
		return weeks;
	}

	public void setWeeks(int weeks) {
		this.weeks = weeks;
	}
	
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
