package com.lgcns.ikep4.servicepack.usagetracker.model;

import java.util.Date;


public class UtResTimeLog extends UtBaseObject {

	private static final long serialVersionUID = -4782094755226974304L;
	
	/**
	 * 응답시간 URL 접속 이력 ID
	 */
	private String resTimeAccessId;
	
	/**
	 * 응답시간 URL ID
	 */
	private String resTimeUrlId;
	
	/**
	 * 응답시간 URL 이름
	 */
	private String resTimeUrlName;
	
	/**
	 * 응답시간(단위: ms)
	 */
	private Long resTime; 
	
	/**
	 * 접속 일시
	 */
	private Date accessDate;

	public String getResTimeAccessId() {
		return resTimeAccessId;
	}

	public void setResTimeAccessId(String resTimeAccessId) {
		this.resTimeAccessId = resTimeAccessId;
	}

	public String getResTimeUrlId() {
		return resTimeUrlId;
	}

	public void setResTimeUrlId(String resTimeUrlId) {
		this.resTimeUrlId = resTimeUrlId;
	}
	
	public String getResTimeUrlName() {
		return resTimeUrlName;
	}

	public void setResTimeUrlName(String resTimeUrlName) {
		this.resTimeUrlName = resTimeUrlName;
	}

	public Long getResTime() {
		return resTime;
	}

	public void setResTime(Long resTime) {
		this.resTime = resTime;
	}

	public Date getAccessDate() {
		return accessDate;
	}

	public void setAccessDate(Date accessDate) {
		this.accessDate = accessDate;
	}
}