package com.lgcns.ikep4.collpack.kms.admin.model;

import java.util.List;

import com.lgcns.ikep4.support.base.constant.CommonConstant;

public class AdminTeamLeader {
	
	private String rowNum;
	private String teamCode;
	private String teamName;	
	private String teamCodes;
	private String teamCnt;
	private String userId;
	
	
	public String getTeamCnt(){
		return teamCnt;
	}
	
	public void setTeamCnt(String teamCnt){
		this.teamCnt = teamCnt;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String[] getTeamCodes() {
		if(teamCodes != null)
			return teamCodes.split(CommonConstant.COMMA_SEPARATER);
		else
			return null;
	}
	public void setTeamCodes(String teamCodes) {
		this.teamCodes = teamCodes;
	}
	public String getRowNum() {
		return rowNum;
	}
	public void setRowNum(String rowNum) {
		this.rowNum = rowNum; 
	}
	
	public String getTeamCode() {
		return teamCode;
	}
	public void setTeamCode(String teamCode) {
		this.teamCode = teamCode;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	

}
