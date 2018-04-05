package com.lgcns.ikep4.servicepack.usagetracker.model;

public class UtResTimeUrl extends UtBaseObject {

	private static final long serialVersionUID = 4402743542009802782L;
	
	/**
	 * RES_TIME_URL_ID[응답시간 URL ID]
	 */
	private String resTimeUrlId;

	/**
	 * RES_TIME_URL_NAME[응답시간 URL 이름]
	 */
	private String resTimeUrlName;
	
	/**
	 * RES_TIME_URL[응답시간 URL]
	 */
	private String resTimeUrl;

	/**
	 * USAGE[사용 여부 ( 0 : 사용, 1 : 미사용)]
	 */
	private Integer usage;

	/**
	 * REGISTER_ID[등록자 ID]
	 */
	private String registerId;

	/**
	 * REGISTER_NAME[등록자 이름]
	 */
	private String registerName;

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

	public String getResTimeUrl() {
		return resTimeUrl;
	}

	public void setResTimeUrl(String resTimeUrl) {
		this.resTimeUrl = resTimeUrl;
	}

	public Integer getUsage() {
		return usage;
	}

	public void setUsage(Integer usage) {
		this.usage = usage;
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
}