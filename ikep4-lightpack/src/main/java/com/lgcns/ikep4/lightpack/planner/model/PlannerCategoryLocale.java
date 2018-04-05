/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.planner.model;

/**
 * 범주 locale
 *
 * @author  신용환(combinet@gmail.com)
 * @version $Id: PlannerCategoryLocale.java 16240 2011-08-18 04:08:15Z giljae $
 */
public class PlannerCategoryLocale {
	// 일정 범주(카테고리) ID
	private String categoryId;
	
	// 로케일 코드 (ko, en, jp 등)  코드값을 사용함
	private String localeCode;
	
	// 로케일별별 카테고리 명 	(중요, 업무, 개인, 휴가, 필수참석, 출장, 생일, 기념일 등)
	private String categoryName;
	
	// 로케일 이름
	private String localeName;
	
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getLocaleCode() {
		return localeCode;
	}
	public void setLocaleCode(String localeCode) {
		this.localeCode = localeCode;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getLocaleName() {
		return localeName;
	}
	public void setLocaleName(String localeName) {
		this.localeName = localeName;
	}
}
