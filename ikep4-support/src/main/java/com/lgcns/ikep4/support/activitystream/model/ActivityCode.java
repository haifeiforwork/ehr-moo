package com.lgcns.ikep4.support.activitystream.model;

import java.util.Date;
import java.util.List;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * ActivityStream
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: ActivityCode.java 16271 2011-08-18 07:06:14Z giljae $
 */
public class ActivityCode extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = -3013340965023651370L;

	/**
	 * id
	 */
	private String subscriptionActivityCode;

	/**
	 * activity 코드
	 */
	private String activityCode;

	/**
	 * activity 명
	 */
	private String activityName;

	/**
	 * activity 설명
	 */
	private String activityDescription;

	/**
	 * activity 영문 설명
	 */
	private String activityEnglishDescription;

	/**
	 * type
	 */
	private String type;

	/**
	 * user id
	 */
	private String userId;

	/**
	 * 등록자
	 */
	private String registerId;

	/**
	 * 등록자명
	 */
	private String registerName;

	/**
	 * 등록일
	 */
	private Date registDate;

	/**
	 * 수정자
	 */
	private String updaterId;

	/**
	 * 수정자명
	 */
	private String updaterName;

	/**
	 * 수정일
	 */
	private Date updateDate;

	/**
	 * activity code list
	 */
	private List<String> activityCodeList;

	/**
	 * config code
	 */
	private String configCode;

	/**
	 * config value
	 */
	private String configValue;
	
	/**
	 * 최대 로그 건수
	 */
	private String maxSaveValue;

	public String getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getActivityDescription() {
		return activityDescription;
	}

	public void setActivityDescription(String activityDescription) {
		this.activityDescription = activityDescription;
	}

	public String getActivityEnglishDescription() {
		return activityEnglishDescription;
	}

	public void setActivityEnglishDescription(String activityEnglishDescription) {
		this.activityEnglishDescription = activityEnglishDescription;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getSubscriptionActivityCode() {
		return subscriptionActivityCode;
	}

	public void setSubscriptionActivityCode(String subscriptionActivityCode) {
		this.subscriptionActivityCode = subscriptionActivityCode;
	}

	public List<String> getActivityCodeList() {
		return activityCodeList;
	}

	public void setActivityCodeList(List<String> activityCodeList) {
		this.activityCodeList = activityCodeList;
	}

	public String getConfigCode() {
		return configCode;
	}

	public void setConfigCode(String configCode) {
		this.configCode = configCode;
	}

	public String getConfigValue() {
		return configValue;
	}

	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}

	public String getMaxSaveValue() {
		return maxSaveValue;
	}

	public void setMaxSaveValue(String maxSaveValue) {
		this.maxSaveValue = maxSaveValue;
	}

}
