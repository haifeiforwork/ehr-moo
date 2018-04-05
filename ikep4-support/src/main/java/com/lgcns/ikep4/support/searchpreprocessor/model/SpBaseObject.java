package com.lgcns.ikep4.support.searchpreprocessor.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * 
 * 설문 BaseObject
 *
 * @author ihko11
 * @version $Id: SpBaseObject.java 16277 2011-08-18 07:11:05Z giljae $
 */
public class SpBaseObject extends BaseObject {
	
	/**
	 *
	 */
	private static final long serialVersionUID = 1038075093030378367L;

	/*
     *  등록자 ID
     */
    private String registerId;

    /**
     * 등록자 이름
     */
    private String registerName;

    /**
     * 등록일
     */
    @DateTimeFormat(pattern="yyyy.MM.dd")
    private Date registDate;

    /**
     * 수정자 ID
     */
    private String updaterId;

    /**
     *수정자 이름
     */
    private String updaterName;
    
    /**
     * 수정일
     */
    @DateTimeFormat(pattern="yyyy.MM.dd")
    private Date updateDate;
    
    /**
	 * PORTAL_ID[포탈 ID]
	 */
	private String portalId;
	
	 /**
	 * 사용자의 이름
	 */
	private String userName;

	/**
	 * 사용자의 영문 이름
	 */
	private String userEnglishName;

	/**
	 * 이 사용자가 해당된 부서(그룹) 이름
	 */
	private String teamName;
	
	/**
	 * 이 사용자가 해당된 부서(그룹) 영어 이름
	 */
	private String teamEnglishName;

/**
	 * 사용자의 사원번호
	 */
	private String jobTitleName;
	private String jobTitleEnglishName;
	
	
	
	
	public String getRegisterId() {
		return registerId;
	}

	public String getRegisterName() {
		return registerName;
	}

	public Date getRegistDate() {
		return registDate;
	}

	public String getUpdaterId() {
		return updaterId;
	}

	public String getUpdaterName() {
		return updaterName;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}

	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}

	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}

	public void setUpdaterId(String updaterId) {
		this.updaterId = updaterId;
	}

	public void setUpdaterName(String updaterName) {
		this.updaterName = updaterName;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	public String getPortalId() {
		return portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId == null ? null : portalId.trim();
	}

	public String getUserName() {
		return userName;
	}

	public String getUserEnglishName() {
		return userEnglishName;
	}

	public String getTeamName() {
		return teamName;
	}

	public String getTeamEnglishName() {
		return teamEnglishName;
	}

	public String getJobTitleName() {
		return jobTitleName;
	}

	public String getJobTitleEnglishName() {
		return jobTitleEnglishName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setUserEnglishName(String userEnglishName) {
		this.userEnglishName = userEnglishName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public void setTeamEnglishName(String teamEnglishName) {
		this.teamEnglishName = teamEnglishName;
	}

	public void setJobTitleName(String jobTitleName) {
		this.jobTitleName = jobTitleName;
	}

	public void setJobTitleEnglishName(String jobTitleEnglishName) {
		this.jobTitleEnglishName = jobTitleEnglishName;
	}
}