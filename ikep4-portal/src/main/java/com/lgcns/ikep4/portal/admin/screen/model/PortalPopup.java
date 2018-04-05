package com.lgcns.ikep4.portal.admin.screen.model;

import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

import com.lgcns.ikep4.framework.core.model.BaseObject;

public class PortalPopup extends BaseObject{

	private static final long serialVersionUID = -8561071349552525773L;
	
	/**
	 * 팝업 ID
	 */
	private String popupId;
	
	/**
	 * 팝업 이름
	 */
	@NotEmpty
	private String popupName;
	
	/**
	 * 팝업 URL
	 */
	private String popupUrl;
	
	/**
	 * 포탈 ID
	 */
	private String portalId;
	
	/**
	 * 가로 길이
	 */
	private int popupWidth;
	
	/**
	 * 세로 길이
	 */
	private int popupHeight;
	
	/**
	 * 팝업 시작 시간
	 */
	@NotEmpty
	private String popupStartDate;
	
	/**
	 * 팝업 종료 시간
	 */
	@NotEmpty
	private String popupEndDate;
	
	/**
	 * 사용 여부
	 */
	private int popupActive;
	
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
	 * 내용
	 */
	private String contents;

	/**
	 * 팝업방식
	 */
	private String windowYn;

	/**
	 * 배경이미지 테마
	 */
	private String popupTheme;
	
	/**
	 * 배경이미지 테마URL
	 */
	private String popupThemeUrl;
	
	/**
	 * 팝업타입
	 */
	private String popupType;
		
	private PortalSecurity security;
	
	public PortalSecurity getSecurity() {
		return security;
	}

	public void setSecurity(PortalSecurity security) {
		this.security = security;
	}

	public String getPopupType() {
		return popupType;
	}

	public void setPopupType(String popupType) {
		this.popupType = popupType;
	}

	public String getPopupId() {
		return popupId;
	}
	
	public void setPopupId(String popupId) {
		this.popupId = popupId;
	}

	public String getPopupName() {
		return popupName;
	}

	public void setPopupName(String popupName) {
		this.popupName = popupName;
	}

	public String getPopupUrl() {
		return popupUrl;
	}

	public void setPopupUrl(String popupUrl) {
		this.popupUrl = popupUrl;
	}

	public String getPortalId() {
		return portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}

	public int getPopupWidth() {
		return popupWidth;
	}

	public void setPopupWidth(int popupWidth) {
		this.popupWidth = popupWidth;
	}

	public int getPopupHeight() {
		return popupHeight;
	}

	public void setPopupHeight(int popupHeight) {
		this.popupHeight = popupHeight;
	}

	public String getPopupStartDate() {
		return popupStartDate;
	}

	public void setPopupStartDate(String popupStartDate) {
		this.popupStartDate = popupStartDate;
	}

	public String getPopupEndDate() {
		return popupEndDate;
	}

	public void setPopupEndDate(String popupEndDate) {
		this.popupEndDate = popupEndDate;
	}

	public int getPopupActive() {
		return popupActive;
	}

	public void setPopupActive(int popupActive) {
		this.popupActive = popupActive;
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

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getWindowYn() {
		return windowYn;
	}

	public void setWindowYn(String windowYn) {
		this.windowYn = windowYn;
	}

	public String getPopupTheme() {
		return popupTheme;
	}

	public void setPopupTheme(String popupTheme) {
		this.popupTheme = popupTheme;
	}

	public String getPopupThemeUrl() {
		return popupThemeUrl;
	}

	public void setPopupThemeUrl(String popupThemeUrl) {
		this.popupThemeUrl = popupThemeUrl;
	}	
}
