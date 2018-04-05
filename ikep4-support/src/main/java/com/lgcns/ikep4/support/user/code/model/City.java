package com.lgcns.ikep4.support.user.code.model;

import java.util.List;

import javax.validation.Valid;

import com.lgcns.ikep4.framework.core.model.BaseObject;

public class City extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3658062714279646502L;


	/**
	 * 다국어 메시지용 리스트
	 */
	@Valid
	private List<I18nMessage> i18nMessageList;
	
	/**
	 * 순번
	 */
	private String num;
	
	/**
	 * 도시 ID
	 */
	
	private String cityId;
	
	/**
	 * 국가 코드 
	 */
	
	private String nationCode;
	
	/*
	 * 도시 명 
	 */
	private String cityName;
	
	
	/**
	 * 도시 영어명 
	 *
	 */
	private String cityEnglishName;
	
	
	
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
	private String registDate;

	/**
	 * 수정자 ID
	 */
	private String updaterId;

	/**
	 * 수정자 이름
	 */
	private String updaterName;

	/**
	 * 수정일
	 */
	private String updateDate;
	
	
	public List<I18nMessage> getI18nMessageList() {
		return i18nMessageList;
	}

	public void setI18nMessageList(List<I18nMessage> i18nMessageList) {
		this.i18nMessageList = i18nMessageList;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getNationCode() {
		return nationCode;
	}

	public void setNationCode(String nationCode) {
		this.nationCode = nationCode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCityEnglishName() {
		return cityEnglishName;
	}

	public void setCityEnglishName(String cityEnglishName) {
		this.cityEnglishName = cityEnglishName;
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

	public String getRegistDate() {
		return registDate;
	}

	public void setRegistDate(String registDate) {
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

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	
	
	
}
