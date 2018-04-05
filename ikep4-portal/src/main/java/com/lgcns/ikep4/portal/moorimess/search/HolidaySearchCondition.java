package com.lgcns.ikep4.portal.moorimess.search;

import com.lgcns.ikep4.framework.web.SearchCondition;


/**
 * 
 * 전표결제 검색조건
 *
 * @author 
 * @version $Id$
 */
public class HolidaySearchCondition extends SearchCondition {
	
	private String iyear;
	private String imonth;
	private String ipernr;
	public String getIyear() {
		return iyear;
	}
	public void setIyear(String iyear) {
		this.iyear = iyear;
	}
	public String getImonth() {
		return imonth;
	}
	public void setImonth(String imonth) {
		this.imonth = imonth;
	}
	public String getIpernr() {
		return ipernr;
	}
	public void setIpernr(String ipernr) {
		this.ipernr = ipernr;
	}
}
