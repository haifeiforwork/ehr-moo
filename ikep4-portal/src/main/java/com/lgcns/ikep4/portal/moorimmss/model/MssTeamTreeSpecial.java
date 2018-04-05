package com.lgcns.ikep4.portal.moorimmss.model;



import org.hibernate.validator.constraints.NotEmpty;

import com.lgcns.ikep4.framework.core.model.BaseObject;

public class MssTeamTreeSpecial extends BaseObject{



	static final long serialVersionUID = -6934705375404904747L;

	private String itemId;
	
	private String userId;

	private String groupId;
	
	private int childGroupCnt;

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public int getChildGroupCnt() {
		return childGroupCnt;
	}

	public void setChildGroupCnt(int childGroupCnt) {
		this.childGroupCnt = childGroupCnt;
	}
	
}
