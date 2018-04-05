package com.lgcns.ikep4.util.ical.model;

import net.fortuna.ical4j.model.DateTime;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * IDurVO
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: ITimeZone.java 16247 2011-08-18 04:54:29Z giljae $
 */
public class ITimeZone extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = 8486139182290915052L;

	/**
	 * TimeZone ID
	 */
	private String tzId;

	/**
	 * TimeZone Name
	 */
	private String tzName;

	/**
	 * OffsetFrom
	 */
	private long tzOffsetFrom;

	/**
	 * OffsetTo
	 */
	private long tzOffsetTo;

	/**
	 * 시작일
	 */
	private DateTime dtstart;

	public String getTzId() {
		return tzId;
	}

	public void setTzId(String tzId) {
		this.tzId = tzId;
	}

	public String getTzName() {
		return tzName;
	}

	public void setTzName(String tzName) {
		this.tzName = tzName;
	}

	public long getTzOffsetFrom() {
		return tzOffsetFrom;
	}

	public void setTzOffsetFrom(long tzOffsetFrom) {
		this.tzOffsetFrom = tzOffsetFrom;
	}

	public long getTzOffsetTo() {
		return tzOffsetTo;
	}

	public void setTzOffsetTo(long tzOffsetTo) {
		this.tzOffsetTo = tzOffsetTo;
	}

	public DateTime getDtstart() {
		return dtstart;
	}

	public void setDtstart(DateTime dtstart) {
		this.dtstart = dtstart;
	}

}
