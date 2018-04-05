package com.lgcns.ikep4.lightpack.planner.restful.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="planDetail")
public class PlannerDataPlanDetail extends PlannerDataPlan {

	private String itemContent;
	private String regUserName;
	private String regUserDept;
	private String regUserTitle;
	
	private String isSendMail;
	private String isAttendance;
	private String isSecret;
	private String schedulePrivate;
	
	private String color;
	
	private String workspaceName;
	private String cartooletcId;
	private String cartooletcName;
	private String meetingRoomId;
		
	public PlannerDataPlanDetail(String itemId, String isWholeDay, String isRepeat, String secretFlag, String itemColor,
			String itemTitle, String itemPlace, String itemStartDate, String itemStartTime, String itemEndDate, String itemEndTime,
			String itemCategoryId, String itemCategoryName, String itemCategoryIcon,
			String itemContent, String regUserId, String regUserName, String regUserDept, String regUserTitle,
			String isSendMail, String isAttendance, String workspaceId, String workspaceName, String isUnfixedPlan, String isSecret, String schedulePrivate, 
			String cartooletcId, String cartooletcName, String meetingRoomId) {
		
		super(itemId, isWholeDay, isRepeat, secretFlag, itemColor, itemTitle, itemPlace,
				itemStartDate, itemStartTime, itemEndDate, itemEndTime, itemCategoryId, itemCategoryName,
				itemCategoryIcon, regUserId, workspaceId, isUnfixedPlan);
		
		this.itemContent = itemContent;
		this.regUserName = regUserName;
		this.regUserDept = regUserDept;
		this.regUserTitle = regUserTitle;
		this.isSendMail = isSendMail;
		this.isAttendance = isAttendance;
		this.isSecret = isSecret;
		this.schedulePrivate = schedulePrivate;
		this.workspaceName = workspaceName;
		this.color = itemColor;
		this.cartooletcId = cartooletcId;
		this.cartooletcName = cartooletcName;
		this.meetingRoomId = meetingRoomId;
	}

	public String getItemContent() {
		return itemContent;
	}

	public String getRegUserName() {
		return regUserName;
	}

	public String getRegUserDept() {
		return regUserDept;
	}

	public String getRegUserTitle() {
		return regUserTitle;
	}
	
	public String getIsSendMail() {
		return isSendMail;
	}

	public String getIsAttendance() {
		return isAttendance;
	}
	
	public String getIsSecret() {
		return isSecret;
	}
	
	public String getWorkspaceName() {
		return workspaceName;
	}



	public void setItemContent(String itemContent) {
		this.itemContent = itemContent;
	}

	public void setRegUserName(String regUserName) {
		this.regUserName = regUserName;
	}

	public void setRegUserDept(String regUserDept) {
		this.regUserDept = regUserDept;
	}

	public void setRegUserTitle(String regUserTitle) {
		this.regUserTitle = regUserTitle;
	}

	public void setIsSendMail(String isSendMail) {
		this.isSendMail = isSendMail;
	}

	public void setIsAttendance(String isAttendance) {
		this.isAttendance = isAttendance;
	}
	
	public void setIsSecret(String isSecret) {
		this.isSecret = isSecret;
	}
	
	public void setWorkspaceName(String workspaceName) {
		this.workspaceName = workspaceName;
	}

	public String getSchedulePrivate() {
		return schedulePrivate;
	}

	public void setSchedulePrivate(String schedulePrivate) {
		this.schedulePrivate = schedulePrivate;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getCartooletcId() {
		return cartooletcId;
	}

	public void setCartooletcId(String cartooletcId) {
		this.cartooletcId = cartooletcId;
	}
	
	public String getCartooletcName() {
		return cartooletcName;
	}

	public void setCartooletcName(String cartooletcName) {
		this.cartooletcName = cartooletcName;
	}

	public String getMeetingRoomId() {
		return meetingRoomId;
	}

	public void setMeetingRoomId(String meetingRoomId) {
		this.meetingRoomId = meetingRoomId;
	}

}