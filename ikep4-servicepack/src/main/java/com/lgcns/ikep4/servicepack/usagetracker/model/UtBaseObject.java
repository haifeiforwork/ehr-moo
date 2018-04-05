package com.lgcns.ikep4.servicepack.usagetracker.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * 
 * 현황 통계
 *
 * @author ihko11
 * @version $Id: UtBaseObject.java 16244 2011-08-18 04:11:42Z giljae $
 */
public class UtBaseObject extends BaseObject {
	
	/**
	 *
	 */
	private static final long serialVersionUID = 4342922498002922553L;
	
	
	/**
    PORTAL_ID[포탈 ID]
     */
    private String portalId;

    /**
    REGIST_DATE[등록일시]
     */
    @DateTimeFormat(pattern="yyyy.MM.dd")
    private Date registDate;

    
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

	private String jobTitleName;
	private String jobTitleEnglishName;
	
	private String userId;
	
	 
	public String getPortalId() {
		return portalId;
	}

	public Date getRegistDate() {
		return registDate;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}

	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}