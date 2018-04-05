package com.lgcns.ikep4.util.ical.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * ICalendarVO
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: ICalendar.java 16247 2011-08-18 04:54:29Z giljae $
 */
public class ICalendar extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = -3949330475120428865L;

	/**
	 * 버젼
	 */
	private String version;

	/**
	 * id
	 */
	private String prodid;

	/**
	 * 달력종류(GREGORIAN)
	 */
	private String calscale;

	/**
	 *
	 */
	private String method;

	/**
	 *
	 */
	private String xprop;

	private ITimeZone iTimeZone;

	private List<IEvent> ieventList;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getProdid() {
		return prodid;
	}

	public void setProdid(String prodid) {
		this.prodid = prodid;
	}

	public String getCalscale() {
		return calscale;
	}

	public void setCalscale(String calscale) {
		this.calscale = calscale;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getXprop() {
		return xprop;
	}

	public void setXprop(String xprop) {
		this.xprop = xprop;
	}

	public List<IEvent> getIeventList() {
		return ieventList;
	}

	public void setIeventList(List<IEvent> ieventList) {
		this.ieventList = ieventList;
	}

	public void addIEventList(IEvent ieventVO) {

		if (ieventList == null) {
			ieventList = new ArrayList<IEvent>();
		}

		ieventList.add(ieventVO);
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	public ITimeZone getiTimeZone() {
		return iTimeZone;
	}

	public void setiTimeZone(ITimeZone iTimeZone) {
		this.iTimeZone = iTimeZone;
	}

}
