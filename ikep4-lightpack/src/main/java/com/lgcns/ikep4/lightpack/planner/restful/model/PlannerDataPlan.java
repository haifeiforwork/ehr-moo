package com.lgcns.ikep4.lightpack.planner.restful.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="plan")
public class PlannerDataPlan {

	protected String itemId;
	protected String isWholeDay;
	protected String isRepeat;
	protected String secretFlag;
	protected String itemColor;
	protected String itemTitle;
	protected String itemPlace;
	protected String itemStartDate;
	protected String itemStartTime;
	protected String itemEndDate;
	protected String itemEndTime;
	protected String itemCategoryId;
	protected String itemCategoryName;
	protected String itemCategoryIcon;
	protected String regUserId;
	protected String workspaceId;
	
	protected String isUnfixedPlan;
	
	public PlannerDataPlan(String itemId, String isWholeDay, String isRepeat, String secretFlag, String itemColor,
			String itemTitle, String itemPlace, String itemStartDate,
			String itemStartTime, String itemEndDate, String itemEndTime, String itemCategoryId, String itemCategoryName, String itemCategoryIcon,
			String regUserId, String workspaceId, String isUnfixedPlan) {
		
		super();
		
		this.itemId = itemId;
		this.isWholeDay = isWholeDay;
		this.isRepeat = isRepeat;
		this.secretFlag = secretFlag;
		this.itemColor = itemColor;
		this.itemTitle = itemTitle;
		this.itemPlace = itemPlace;
		this.itemStartDate = itemStartDate;
		this.itemStartTime = itemStartTime;
		this.itemEndDate = itemEndDate;
		this.itemEndTime = itemEndTime;
		this.itemCategoryId = itemCategoryId;
		this.itemCategoryName = itemCategoryName;
		this.itemCategoryIcon = itemCategoryIcon;
		this.regUserId = regUserId;
		
		this.workspaceId = workspaceId;
		
		this.isUnfixedPlan = isUnfixedPlan;
	}

	public String getItemId() {
		return itemId;
	}

	public String getIsWholeDay() {
		return isWholeDay;
	}
	
	public String getIsRepeat() {
		return isRepeat;
	}

	public String getSecretFlag() {
		return secretFlag;
	}

	public String getItemColor() {
		return itemColor;
	}

	public String getItemTitle() {
		return itemTitle;
	}

	public String getItemPlace() {
		return itemPlace;
	}

	public String getItemStartDate() {
		return itemStartDate;
	}

	public String getItemStartTime() {
		return itemStartTime;
	}

	public String getItemEndDate() {
		return itemEndDate;
	}

	public String getItemEndTime() {
		return itemEndTime;
	}
	
	public String getItemCategoryId() {
		return itemCategoryId;
	}
	
	public String getItemCategoryName() {
		return itemCategoryName;
	}
	
	public String getItemCategoryIcon() {
		return itemCategoryIcon;
	}

	public String getRegUserId() {
		return regUserId;
	}
	
	public String getWorkspaceId() {
		return workspaceId;
	}
	
	public String getIsUnfixedPlan() {
		return isUnfixedPlan;
	}
	
	

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public void setIsWholeDay(String isWholeDay) {
		this.isWholeDay = isWholeDay;
	}
	
	public void setIsRepeat(String isRepeat) {
		this.isRepeat = isRepeat;
	}

	public void setSecretFlag(String secretFlag) {
		this.secretFlag = secretFlag;
	}

	public void setItemColor(String itemColor) {
		this.itemColor = itemColor;
	}

	public void setItemTitle(String itemTitle) {
		this.itemTitle = itemTitle;
	}

	public void setItemPlace(String itemPlace) {
		this.itemPlace = itemPlace;
	}

	public void setItemStartDate(String itemStartDate) {
		this.itemStartDate = itemStartDate;
	}

	public void setItemStartTime(String itemStartTime) {
		this.itemStartTime = itemStartTime;
	}

	public void setItemEndDate(String itemEndDate) {
		this.itemEndDate = itemEndDate;
	}

	public void setItemEndTime(String itemEndTime) {
		this.itemEndTime = itemEndTime;
	}
	
	public void setItemCategoryId(String itemCategoryId) {
		this.itemCategoryId = itemCategoryId;
	}
	
	public void setItemCategoryName(String itemCategoryName) {
		this.itemCategoryName = itemCategoryName;
	}
	
	public void setItemCategoryIcon(String itemCategoryIcon) {
		this.itemCategoryIcon = itemCategoryIcon;
	}

	public void setRegUserId(String regUserId) {
		this.regUserId = regUserId;
	}
	
	public void setWorkspaceId(String workspaceId) {
		this.workspaceId = workspaceId;
	}
	
	public void setIsUnfixedPlan(String isUnfixedPlan) {
		this.isUnfixedPlan = isUnfixedPlan;
	}

	
}