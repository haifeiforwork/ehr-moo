package com.lgcns.ikep4.lightpack.planner.search;

import java.util.Date;

import com.lgcns.ikep4.framework.web.SearchCondition;

public class ScheduleSearchCondition extends SearchCondition {

	/**
	 *
	 */
	private static final long serialVersionUID = -5183905232234479168L;
	
	private String keyword;
	private Date startDate;
	private Date endDate;
	private String[] searchFields;
	private String targetId;
	private String targetType;
	private boolean isDefaultPortalLocale = true;

	private String searchNation;
	private String localeCode;
	
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String[] getSearchFields() {
		return searchFields;
	}
	public void setSearchFields(String[] searchFields) {
		this.searchFields = searchFields;
	}
	public String getTargetId() {
		return targetId;
	}
	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}
	public String getTargetType() {
		return targetType;
	}
	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}
	public boolean isDefaultPortalLocale() {
		return isDefaultPortalLocale;
	}
	public void setDefaultPortalLocale(boolean isDefaultPortalLocale) {
		this.isDefaultPortalLocale = isDefaultPortalLocale;
	}
	public String getSearchNation() {
		return searchNation;
	}
	public void setSearchNation(String searchNation) {
		this.searchNation = searchNation;
	}
	public String getLocaleCode() {
		return localeCode;
	}
	public void setLocaleCode(String localeCode) {
		this.localeCode = localeCode;
	}
}
