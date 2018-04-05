package com.lgcns.ikep4.portal.usagetracker.model;

import java.util.Date;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * 
 * 로그인 통계  Model
 *
 * @author 임종상
 * @version $Id: PortalLoginRank.java 16243 2011-08-18 04:10:43Z giljae $
 */
public class PortalLoginRank extends BaseObject{

	private static final long serialVersionUID = 2009513165808344821L;

	/**
	 * 로그인 히스토리 ID
	 */
	private String loginHistoryId;
	
	/**
	 * 포탈 ID
	 */
	private String portalId;
	
	/**
	 * 로그인 사용자 ID
	 */
	private String userId;
	
	/**
	 * 로그인 사용자 IP ADDRESS
	 */
	private String ipAddress;
	
	/**
	 * USER-AGENT 명
	 */
	private String userAgent;
	
	/**
	 * 사용자 브라우저 이름
	 */
	private String browser;
	
	/**
	 * 사용자 로그인 일시
	 */
	private Date loginDate;
	
	public String getLoginHistoryId() {
		return loginHistoryId;
	}
	public void setLoginHistoryId(String loginHistoryId) {
		this.loginHistoryId = loginHistoryId;
	}
	public String getPortalId() {
		return portalId;
	}
	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getUserAgent() {
		return userAgent;
	}
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
	public String getBrowser() {
		return browser;
	}
	public void setBrowser(String browser) {
		this.browser = browser;
	}
	public Date getLoginDate() {
		return loginDate;
	}
	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}
}
