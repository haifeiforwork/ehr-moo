package com.lgcns.ikep4.portal.portlet.model;

import java.util.Date;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * 
 * 세계시간 model
 *
 * @author 
 * @version $Id: WorldClock.java 3775 2011-07-29 04:55:22Z dev07 $
 */
public class WorldClock extends BaseObject {

	private static final long serialVersionUID = -2116043147179568881L;

	/**
	 * 사용자 ID
	 */
	private String userId;
	
	/**
	 * 유형
	 */
	private String type;

	/**
	 * 국가 이름
	 */
	private String countryName;
	
	/**
	 * 도시 이름
	 */
	private String cityName;
	
	/**
	 * 등록자 ID
	 */
	private String registerId;
	
	/**
	 * 등록자 이름
	 */
	private String registerName;
	
	/**
	 * 등록일
	 */
	private Date registDate;
	
	/**
	 * 수정일 ID
	 */
	private String updaterId;
	
	/**
	 * 수정자 이름
	 */
	private String updaterName;
	
	/**
	 * 수정일
	 */
	private Date updateDate;
	
	/**
	 * 국가 영문 이름
	 */
	private String countryEnglishName;
	
	/**
	 * 도시 영문 이름
	 */
	private String cityEnglishName;
	
	public String getCountryEnglishName() {
		return countryEnglishName;
	}

	public void setCountryEnglishName(String countryEnglishName) {
		this.countryEnglishName = countryEnglishName;
	}

	public String getCityEnglishName() {
		return cityEnglishName;
	}

	public void setCityEnglishName(String cityEnglishName) {
		this.cityEnglishName = cityEnglishName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
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

	public Date getRegistDate() {
		return registDate;
	}

	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}

	public String getUpdaterId() {
		return updaterId;
	}

	public void setUpdaterId(String updaterId) {
		this.updaterId = updaterId;
	}

	public String getUpdaterName() {
		return updaterName;
	}

	public void setUpdaterName(String updaterName) {
		this.updaterName = updaterName;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
