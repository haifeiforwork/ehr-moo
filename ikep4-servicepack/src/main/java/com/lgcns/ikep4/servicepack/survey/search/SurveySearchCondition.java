package com.lgcns.ikep4.servicepack.survey.search;

import com.lgcns.ikep4.framework.web.SearchCondition;

/**
 * 
 * 설문 리스트 검색 
 *
 * @author 고인호(ihko11@daum.net)
 * @version $Id: SurveySearchCondition.java 16244 2011-08-18 04:11:42Z giljae $
 */
public class SurveySearchCondition extends SearchCondition {
	private static final long serialVersionUID = 1L;

	private String id;
	
	private String searchColumn;
	
	private String searchWord;

	private String viewMode;
	
	private String layoutType; 
	
	private boolean init = Boolean.TRUE; 
	private String approverId;
	
	private Integer surveyStatus;
	
	private String redirect;
	
	private String responseUserId;
	
	private String registerId;
	
	private String portalId;
	
	private boolean admin;
	
	public boolean getAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public String getResponseUserId() {
		return responseUserId;
	}

	public void setResponseUserId(String responseUserId) {
		this.responseUserId = responseUserId;
	}

	public String getRedirect() {
		return redirect;
	}

	public void setRedirect(String redirect) {
		this.redirect = redirect;
	}

	public Integer getSurveyStatus() {
		return surveyStatus;
	}

	public void setSurveyStatus(Integer surveyStatus) {
		this.surveyStatus = surveyStatus;
	}

	public String getApproverId() {
		return approverId;
	}

	public void setApproverId(String approverId) {
		this.approverId = approverId;
	}

	public boolean isInit() {
		return init;
	}

	public void setInit(boolean init) {
		this.init = init;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSearchColumn() {
		return searchColumn;
	}

	public void setSearchColumn(String searchColumn) {
		this.searchColumn = searchColumn;
	}

	public String getSearchWord() {
		return searchWord;
	}

	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}

	public String getViewMode() {
		return viewMode;
	}

	public void setViewMode(String viewMode) {
		this.viewMode = viewMode;
	}

	public String getLayoutType() {
		return layoutType;
	}

	public void setLayoutType(String layoutType) {
		this.layoutType = layoutType;
	}

	public String getRegisterId() {
		return registerId;
	}

	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}

	public String getPortalId() {
		return portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId;
	} 
	
	
	
}