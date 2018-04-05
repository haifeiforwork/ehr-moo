package com.lgcns.ikep4.collpack.kms.perform.search;

import com.ibm.icu.util.Calendar;
import com.lgcns.ikep4.framework.web.SearchCondition;
import com.lgcns.ikep4.util.lang.StringUtil;

public class PerformanceSearchCondition extends SearchCondition {	
	
	private static final long serialVersionUID = 1L;	

	private String workPlaceName;
	private String teamCode;
	private String teamName;
	
	private String registerName;
	private String userId;
	private String userName;
	
	private String searchPeriod;
	private String searchYear;
	private String searchMonth;
	private String startDate;
	private String endDate;
	private String monthCnt;
	private String expertName;
	private String searchIsknowhow;
	
	private String syear;
	private String smonth;
	private String category;
	
		
	
	private String isPerson = "Y";	
	
	//좌측메뉴에서 나의 활동점수 
	private String fromLeft;
	
	
	public String getIsPerson() {
		return isPerson;
	}
	public void setIsPerson(String isPerson) {
		this.isPerson = StringUtil.nvl(isPerson, "Y");
	}
	public String getMonthCnt() {
		if(!StringUtil.isEmpty(monthCnt)){
			return monthCnt;
			
		}else{
			
			monthCnt = StringUtil.isEmpty(searchMonth) ? "12" : "1";
						
			return monthCnt;
		}
	}
	public void setMonthCnt(String monthCnt) {
		this.monthCnt = monthCnt;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getSearchYear() {
		return searchYear;
	}
	public void setSearchYear(String searchYear) {
		this.searchYear = searchYear;
	}
	
	public String getWorkPlaceName() {
		return workPlaceName;
	}
	public void setWorkPlaceName(String workPlaceName) {
		this.workPlaceName = workPlaceName;
	}
	public String getTeamCode() {
		return teamCode;
	}
	public void setTeamCode(String teamCode) {
		this.teamCode = teamCode;
	}
	public String getRegisterName() {
		return registerName;
	}
	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}
	public String getSearchMonth() {
		return searchMonth;
	}
	public void setSearchMonth(String searchMonth) {		
		this.searchMonth = searchMonth;				
	}
	
	public String getStartDate() {
		
		String postFix = " 00:00:00";
		
		if(startDate != null && !startDate.equals("")){
			if(startDate.indexOf(".") > -1){
				startDate = startDate.replace(".", "-");
			}
			
			if(startDate.length() > 10 && startDate.contains(postFix))
				return startDate;
			
			return startDate + postFix;
			
		}else{
			
			String tmpSearchMonth = "0" + searchMonth;
			boolean isEmptySearchMonth = false;
			if(StringUtil.isEmpty(searchMonth)){
				tmpSearchMonth = "12";
				isEmptySearchMonth = true;
				return makeStartDate(searchYear, tmpSearchMonth, postFix, isEmptySearchMonth);
			}							
			
			tmpSearchMonth = tmpSearchMonth.substring(tmpSearchMonth.length()-2, tmpSearchMonth.length());
			return makeStartDate(searchYear, tmpSearchMonth, postFix, isEmptySearchMonth);
		}
	}
	
	private String makeStartDate(String searchYear, String tmpSearchMonth, String postFix, boolean isEmptySearchMonth) {
		String tmpSearchYear = searchYear;
		if(isEmptySearchMonth)
			tmpSearchYear = String.valueOf((Integer.parseInt(searchYear) -1)) ;
		
		return tmpSearchYear + "-" + tmpSearchMonth + "-01" + postFix;
		
	}
	
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	
	public String getEndDate() {
		String postFix = " 23:59:59";
		
		if(endDate != null && !endDate.equals("")){
			
			if(endDate.indexOf(".")> -1){
				endDate = endDate.replace(".", "-");
			}
			
			if(endDate.length() > 10 && endDate.contains(postFix))
				return endDate;
			
			return endDate + postFix;
		}else{
						
			if(StringUtil.isEmpty(searchMonth)){				
				return searchYear + "-11-30" + postFix;
			}else{				
								
				Calendar cal = Calendar.getInstance();
				cal.set(Integer.parseInt(searchYear), Integer.parseInt(searchMonth)-1, 1);
				endDate = String.valueOf(cal.getActualMaximum(Calendar.DAY_OF_MONTH));
								
				String tmpSearchMonth = "0" + searchMonth;				
				tmpSearchMonth = tmpSearchMonth.substring(tmpSearchMonth.length()-2, tmpSearchMonth.length());
				
				endDate = searchYear + "-" + tmpSearchMonth + "-"  + endDate;
				return endDate + postFix;
			}		
		}
	}
	
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	public String getExpertName() {
		return expertName;
	}
	public void setExpertName(String expertName) {
		this.expertName = expertName;
	}
	
	public String getFromLeft() {
		return fromLeft;
	}
	public void setFromLeft(String fromLeft) {
		this.fromLeft = fromLeft;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getSearchIsknowhow() {
		return searchIsknowhow;
	}
	public void setSearchIsknowhow(String searchIsknowhow) {
		this.searchIsknowhow = searchIsknowhow;
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
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getSearchPeriod() {
		return searchPeriod;
	}
	public void setSearchPeriod(String searchPeriod) {
		this.searchPeriod = searchPeriod;
	}
}
