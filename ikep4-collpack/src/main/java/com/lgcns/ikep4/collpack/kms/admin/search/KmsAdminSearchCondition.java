package com.lgcns.ikep4.collpack.kms.admin.search;

import com.lgcns.ikep4.framework.web.SearchCondition;
import com.lgcns.ikep4.util.lang.StringUtil;

public class KmsAdminSearchCondition extends SearchCondition {
	
	
	private static final long serialVersionUID = 1L;	

	private String workPlaceName;
	private String infoGrade;
	private String searchColumn;
	private String searchWord;
	
	private String workPlaceCode;
	private String teamName;
	private String teamCode;
	

	private String userId;
	private String userName;
	private String winYear;
	private String disMode;
	
	private String syear;
	private String smonth;
	
	private String startDate;
	private String endDate;
	
	private String categoryId;
	
	private String clickFlg;
	
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getWinYear() {
		return winYear;
	}

	public void setWinYear(String winYear) {
		this.winYear = winYear;
	}

	public String getWorkPlaceCode() {
		return workPlaceCode;
	}

	public void setWorkPlaceCode(String workPlaceCode) {
		this.workPlaceCode = workPlaceCode;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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
		if(StringUtil.isEmpty(searchWord)){
			this.searchWord = null;
		}else{
			this.searchWord = searchWord;
		}
	}
	
	public String getWorkPlaceName() {
		return workPlaceName;
	}
	
	public void setWorkPlaceName(String workPlaceName) {
		this.workPlaceName = StringUtil.nvl(workPlaceName, "");
		this.workPlaceName = StringUtil.replace(workPlaceName, "ALL", "");
	}

	public String getInfoGrade() {
		return infoGrade;
	}

	public void setInfoGrade(String infoGrade) {
		this.infoGrade = StringUtil.nvl(infoGrade, "");
	}
	
	public String getTeamCode() {
		return teamCode;
	}

	public void setTeamCode(String teamCode) {
		this.teamCode = teamCode;
	}

	public String getDisMode() {
		return disMode;
	}

	public void setDisMode(String disMode) {
		this.disMode = disMode;
	}

	public String getSyear() {
		return syear;
	}

	public void setSyear(String syear) {
		this.syear = syear;
	}

	public String getSmonth() {
		return smonth;
	}

	public void setSmonth(String smonth) {
		this.smonth = smonth;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getClickFlg() {
		return clickFlg;
	}

	public void setClickFlg(String clickFlg) {
		this.clickFlg = clickFlg;
	}

	
}
