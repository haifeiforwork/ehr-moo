/**
 * LG CNS IKEP4 
*/
package com.lgcns.ikep4.servicepack.usagetracker.model;

import java.util.Date;
/**
 * 
 * 사용량통계 로그인 히스토리
 *
 * @author 고인호(ihko11@daum.net)
 * @version $Id: UtLoginLog.java 16244 2011-08-18 04:11:42Z giljae $
 */
public class UtLoginLog extends UtBaseObject {
    /**
	 *
	 */
	private static final long serialVersionUID = -6574094473267001840L;

	/**
	 * LOGIN_HISTORY_ID[로그인 히스토리 ID]
	 */
	private String loginHistoryId;

	/**
	 * USER_ID[로그인 사용자 ID]
	 */
	private String userId;

	/**
	 * IP_ADDRESS[로그인 사용자 IP ADDRESS]
	 */
	private String ipAddress;

	/**
	 * USER_AGENT[USER-AGENT 명]
	 */
	private String userAgent;

	/**
	 * BROWSER[사용자 브라우저 이름]
	 */
	private String browser;
	
	private String workspace;
	
	private String teamName;
	
	private String userName;
	
	private String phoneNum;
	
	private String cmpUsrId;
	
	private String sendCnt;
	
	private String smsCountSum;
	
	private String mmsCountSum;
	
	private String allCountSum;

	/**
	 * LOGIN_DATE[사용자 로그인 일시]
	 */
	private Date loginDate;

	public String getLoginHistoryId() {
		return loginHistoryId;
	}

	public void setLoginHistoryId(String loginHistoryId) {
		this.loginHistoryId = loginHistoryId == null ? null : loginHistoryId.trim();
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId == null ? null : userId.trim();
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress == null ? null : ipAddress.trim();
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent == null ? null : userAgent.trim();
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser == null ? null : browser.trim();
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public String getWorkspace() {
		return workspace;
	}

	public void setWorkspace(String workspace) {
		this.workspace = workspace;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getSmsCountSum() {
		return smsCountSum;
	}

	public void setSmsCountSum(String smsCountSum) {
		this.smsCountSum = smsCountSum;
	}

	public String getMmsCountSum() {
		return mmsCountSum;
	}

	public void setMmsCountSum(String mmsCountSum) {
		this.mmsCountSum = mmsCountSum;
	}

	public String getAllCountSum() {
		return allCountSum;
	}

	public void setAllCountSum(String allCountSum) {
		this.allCountSum = allCountSum;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getCmpUsrId() {
		return cmpUsrId;
	}

	public void setCmpUsrId(String cmpUsrId) {
		this.cmpUsrId = cmpUsrId;
	}

	public String getSendCnt() {
		return sendCnt;
	}

	public void setSendCnt(String sendCnt) {
		this.sendCnt = sendCnt;
	}
}