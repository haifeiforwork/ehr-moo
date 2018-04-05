package com.lgcns.ikep4.collpack.kms.admin.model;

import java.util.Date;

import com.lgcns.ikep4.framework.core.model.BaseObject;
import com.lgcns.ikep4.util.lang.StringUtil;

public class AdminPermission extends BaseObject {
	
	private static final long serialVersionUID = 2289890139646564801L;
	private String rowNum;
	private String userName;	
	private String jobDutyName;
	private String empNo;
	private String userId;
	private String workPlaceName;
	private String workPlaceCode;
	
	private String teamName;
	private String infoGrade;
	private String myCnt;
	private String teamCnt;
	private String teamCntByLeader;
	
	private String boardId;
	
	private String categoryId;
	private String categoryName;
	private String categorySeqId;
	private String categoryType;
	private String color;
	private String description;
	private String portalId;
	private String registerId;
	private String registerName;
	
	private String categoryUsers;
	
	private String period;
	
	private String recommendCnt1;
	private String recommendCnt2;
	private String replyCnt1;
	private String replyCnt2;
	private String score;
	
	private String contents;
	
	private String viewYn;
	
	private String alarmYn;
	
	private Date registDate;
	/**
	 * CategoryId_LIST
	 */
	private String categoryIdList;
	
	/**
	 * CategoryId_LIST
	 */
	private String delIdList;
	
	/**
	 * CategoryId_LIST
	 */
	private String addNameList;
	
	/**
	 * CategoryId_LIST
	 */
	private String updateNameList;
	
	/**
	 * CategoryId_LIST
	 */
	private String updateIdList;
	
	private String alignList;
	
	private String clickTime;
	private String clickFlg;
	
	
	public String getTeamCntByLeader() {
		return teamCntByLeader;
	}
	public void setTeamCntByLeader(String teamCntByLeader) {
		this.teamCntByLeader = teamCntByLeader;
	}
	public String getRowNum() {
		return rowNum;
	}
	public void setRowNum(String rowNum) {
		this.rowNum = rowNum;
	}		
	
	public String getInfoGrade() {
		return infoGrade;
	}
	public void setInfoGrade(String infoGrade) {
		this.infoGrade =  StringUtil.nvl(infoGrade, "");
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getJobDutyName() {
		return jobDutyName;
	}
	public void setJobDutyName(String jobDutyName) {
		this.jobDutyName = jobDutyName;
	}
	public String getEmpNo() {
		return empNo;
	}
	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getWorkPlaceName() {
		return workPlaceName;
	}
	public void setWorkPlaceName(String workPlaceName) {
		this.workPlaceName = workPlaceName;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	
	public String getWorkPlaceCode() {
		return workPlaceCode;
	}
	public void setWorkPlaceCode(String workPlaceCode) {
		this.workPlaceCode = workPlaceCode;
	}
	public String getObligationCnt() {
		return myCnt;
	}
	public void setObligationCnt(String obligationCnt) {
		this.myCnt = obligationCnt;
	}
	public String getTeamObligationCnt() {
		return teamCnt;
	}
	public void setTeamObligationCnt(String teamObligationCnt) {
		this.teamCnt = teamObligationCnt;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getCategorySeqId() {
		return categorySeqId;
	}
	public void setCategorySeqId(String categorySeqId) {
		this.categorySeqId = categorySeqId;
	}
	public String getCategoryType() {
		return categoryType;
	}
	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPortalId() {
		return portalId;
	}
	public void setPortalId(String portalId) {
		this.portalId = portalId;
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
	public String getBoardId() {
		return boardId;
	}
	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}
	public Date getRegistDate() {
		return registDate;
	}
	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}
	public String getCategoryIdList() {
		return categoryIdList;
	}
	public void setCategoryIdList(String categoryIdList) {
		this.categoryIdList = categoryIdList;
	}
	public String getDelIdList() {
		return delIdList;
	}
	public void setDelIdList(String delIdList) {
		this.delIdList = delIdList;
	}
	public String getAddNameList() {
		return addNameList;
	}
	public void setAddNameList(String addNameList) {
		this.addNameList = addNameList;
	}
	public String getUpdateNameList() {
		return updateNameList;
	}
	public void setUpdateNameList(String updateNameList) {
		this.updateNameList = updateNameList;
	}
	public String getUpdateIdList() {
		return updateIdList;
	}
	public void setUpdateIdList(String updateIdList) {
		this.updateIdList = updateIdList;
	}
	public String getAlignList() {
		return alignList;
	}
	public void setAlignList(String alignList) {
		this.alignList = alignList;
	}
	public String getCategoryUsers() {
		return categoryUsers;
	}
	public void setCategoryUsers(String categoryUsers) {
		this.categoryUsers = categoryUsers;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public String getRecommendCnt1() {
		return recommendCnt1;
	}
	public void setRecommendCnt1(String recommendCnt1) {
		this.recommendCnt1 = recommendCnt1;
	}
	public String getRecommendCnt2() {
		return recommendCnt2;
	}
	public void setRecommendCnt2(String recommendCnt2) {
		this.recommendCnt2 = recommendCnt2;
	}
	public String getReplyCnt1() {
		return replyCnt1;
	}
	public void setReplyCnt1(String replyCnt1) {
		this.replyCnt1 = replyCnt1;
	}
	public String getReplyCnt2() {
		return replyCnt2;
	}
	public void setReplyCnt2(String replyCnt2) {
		this.replyCnt2 = replyCnt2;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getViewYn() {
		return viewYn;
	}
	public void setViewYn(String viewYn) {
		this.viewYn = viewYn;
	}
	public String getAlarmYn() {
		return alarmYn;
	}
	public void setAlarmYn(String alarmYn) {
		this.alarmYn = alarmYn;
	}
	public String getClickTime() {
		return clickTime;
	}
	public void setClickTime(String clickTime) {
		this.clickTime = clickTime;
	}
	public String getClickFlg() {
		return clickFlg;
	}
	public void setClickFlg(String clickFlg) {
		this.clickFlg = clickFlg;
	}
}
