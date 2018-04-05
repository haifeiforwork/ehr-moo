package com.lgcns.ikep4.servicepack.survey.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * 
 * 설문 BaseObject
 *
 * @author ihko11
 * @version $Id: SurveyBaseObject.java 16244 2011-08-18 04:11:42Z giljae $
 */
public class SurveyBaseObject extends BaseObject {
	
    /**
	 *
	 */
	private static final long serialVersionUID = 4258732142699099654L;

	
	private String surveyId;
	private String url;
	/**
     * 설문 등록자 ID
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
    
    private String oldQuestionGroupSeq;
    
    
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
	private String empNo;
	
	
	private String title;
	

	private String jobTitleName;
	private String jobTitleEnglishName;
	
	 private String portalId;
	 
    
	public String getOldQuestionGroupSeq() {
		return oldQuestionGroupSeq;
	}

	public void setOldQuestionGroupSeq(String oldQuestionGroupSeq) {
		this.oldQuestionGroupSeq = oldQuestionGroupSeq;
	}

	public String getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(String surveyId) {
		this.surveyId = surveyId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	public String getEmpNo() {
		return empNo;
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

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}



	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getJobTitleName() {
		return jobTitleName;
	}

	public String getJobTitleEnglishName() {
		return jobTitleEnglishName;
	}

	public void setJobTitleName(String jobTitleName) {
		this.jobTitleName = jobTitleName;
	}

	public void setJobTitleEnglishName(String jobTitleEnglishName) {
		this.jobTitleEnglishName = jobTitleEnglishName;
	}

	public String getPortalId() {
		return portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}
}