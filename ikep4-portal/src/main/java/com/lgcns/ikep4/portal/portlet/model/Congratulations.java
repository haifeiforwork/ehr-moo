package com.lgcns.ikep4.portal.portlet.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;
import com.lgcns.ikep4.util.lang.StringUtil;

/**
 * 
 * 축하합니다 PORTLET 모델
 *
 * @author 박철종
 * @version $Id: Congratulations.java 16243 2011-08-18 04:10:43Z giljae $
 */
public class Congratulations extends BaseObject{

	/**
	 *
	 */
	private static final long serialVersionUID = 957822910216975243L;
	
	/**
	 * 리스트 타입
	 */
	private String type;
	
	/**
	 * 포틀릿 설정 아이디
	 */
	private String portletConfigId;
	
	/**
	 * 설정 이름
	 */
	private String propertyName;
	
	/**
	 * 설정값
	 */
	private String propertyValue;
	
	/**
	 * 수정인 아이디
	 */
	private String updaterId;
	
	/**
	 * 수정인 이름
	 */
	private String updaterName;
	
	/**
	 * 등록인 아이디
	 */
	private String registerId;
	
	/**
	 * 등록인 이름
	 */
	private String registerName;

	/**
	 * 사용자 아이디
	 */
	private String userId;
	
	/**
	 * 사용자 이름
	 */
	private String userName;
	
	/**
	 * 사용자 영문 이름
	 */
	private String userEnglishName;
	
	/**
	 * 직책 이름
	 */
	private String jobPositionName;
	
	/**
	 * 직책 영문 이름
	 */
	private String jobPositionEnglishName;
	
	/**
	 * 조직 이름
	 */
	private String groupName;
	
	/**
	 * 조직 영문 이름
	 */
	private String groupEnglishName;
	
	/**
	 * 생일
	 */
	private String birthday;
	
	/**
	 * 생일
	 */
	private String solarBirthday;
	
	/**
	 * 생일
	 */
	private String lunisolarBirthday;
		
	
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the portletConfigId
	 */
	public String getPortletConfigId() {
		return portletConfigId;
	}

	/**
	 * @param portletConfigId the portletConfigId to set
	 */
	public void setPortletConfigId(String portletConfigId) {
		this.portletConfigId = portletConfigId;
	}

	/**
	 * @return the propertyName
	 */
	public String getPropertyName() {
		return propertyName;
	}

	/**
	 * @param propertyName the propertyName to set
	 */
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	/**
	 * @return the propertyValue
	 */
	public String getPropertyValue() {
		return propertyValue;
	}

	/**
	 * @param propertyValue the propertyValue to set
	 */
	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}

	/**
	 * @return the updaterId
	 */
	public String getUpdaterId() {
		return updaterId;
	}

	/**
	 * @param updaterId the updaterId to set
	 */
	public void setUpdaterId(String updaterId) {
		this.updaterId = updaterId;
	}

	/**
	 * @return the updaterName
	 */
	public String getUpdaterName() {
		return updaterName;
	}

	/**
	 * @param updaterName the updaterName to set
	 */
	public void setUpdaterName(String updaterName) {
		this.updaterName = updaterName;
	}

	/**
	 * @return the registerId
	 */
	public String getRegisterId() {
		return registerId;
	}

	/**
	 * @param registerId the registerId to set
	 */
	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}

	/**
	 * @return the registerName
	 */
	public String getRegisterName() {
		return registerName;
	}

	/**
	 * @param registerName the registerName to set
	 */
	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the userEnglishName
	 */
	public String getUserEnglishName() {
		return userEnglishName;
	}

	/**
	 * @param userEnglishName the userEnglishName to set
	 */
	public void setUserEnglishName(String userEnglishName) {
		this.userEnglishName = userEnglishName;
	}

	/**
	 * @return the jobPositionName
	 */
	public String getJobPositionName() {
		return jobPositionName;
	}

	/**
	 * @param jobPositionName the jobPositionName to set
	 */
	public void setJobPositionName(String jobPositionName) {
		this.jobPositionName = jobPositionName;
	}

	/**
	 * @return the jobPositionEnglishName
	 */
	public String getJobPositionEnglishName() {
		return jobPositionEnglishName;
	}

	/**
	 * @param jobPositionEnglishName the jobPositionEnglishName to set
	 */
	public void setJobPositionEnglishName(String jobPositionEnglishName) {
		this.jobPositionEnglishName = jobPositionEnglishName;
	}

	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * @param groupName the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	/**
	 * @return the groupEnglishName
	 */
	public String getGroupEnglishName() {
		return groupEnglishName;
	}

	/**
	 * @param groupEnglishName the groupEnglishName to set
	 */
	public void setGroupEnglishName(String groupEnglishName) {
		this.groupEnglishName = groupEnglishName;
	}

	/**
	 * @return the birthday
	 */
	public String getBirthday() {
		return birthday;
	}

	/**
	 * @param birthday the birthday to set
	 */
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getSolarBirthday() {
		return solarBirthday;
	}

	public void setSolarBirthday(String solarBirthday) {
		this.solarBirthday = solarBirthday;
	}

	public String getLunisolarBirthday() {
		return lunisolarBirthday;
	}

	public void setLunisolarBirthday(String lunisolarBirthday) {
		this.lunisolarBirthday = lunisolarBirthday;
	}
	
}