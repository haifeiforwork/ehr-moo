/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.planner.model;

/**
 * 휴무일 지역화
 *
 * @author  신용환(combinet@gmail.com)
 * @version $Id: HolidayLocale.java 16240 2011-08-18 04:08:15Z giljae $
 */
public class HolidayLocale {
	// 휴무일 ID
	private String holidayId;
	
	// 로케일 코드 (ko, en, jp 등)  코드값을 사용함
	private String localeCode;
	
	// 로케일 이름
	private String localeName;
	
	// 휴무일 이름
	private String holidayName;
	
	public String getHolidayId() {
		return holidayId;
	}
	public void setHolidayId(String holidayId) {
		this.holidayId = holidayId;
	}
	public String getLocaleCode() {
		return localeCode;
	}
	public void setLocaleCode(String localeCode) {
		this.localeCode = localeCode;
	}
	public String getLocaleName() {
		return localeName;
	}
	public void setLocaleName(String localeName) {
		this.localeName = localeName;
	}
	public String getHolidayName() {
		return holidayName;
	}
	public void setHolidayName(String holidayName) {
		this.holidayName = holidayName;
	}
}
