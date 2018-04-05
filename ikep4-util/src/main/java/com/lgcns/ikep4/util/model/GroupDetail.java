package com.lgcns.ikep4.util.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * 
 * 부서 정보  model
 *
 * @author 
 * @version
 */
public class GroupDetail extends BaseObject {

	private static final long serialVersionUID = -2119043197179568881L;

	/**
	 * 조직 코드                    
	 */
	private String groupId ; 
	
	/**
	 * 조직 한글명                  
	 */	
	private String groupName; 
	
	/**
	 * 상위 조직 코드               
	 */	
	private String parentGroupId; 
	/**
	 * 조직 영문명                  
	 */	
	private String groupEnglishName; 
	
	/**
	 * 조직 팀장 ID                 
	 */
	private String leaderId; 	

	/**
	 * 조직정렬순서                 
	 */
	private String sortOrder;
	
	/**
	 * 조직유효종료일  
	 */
	private String groupEdate; 
	
	/**
	 * 사용중인지 여부
	 */
	private String isUse;
	
	public String getGroupId() {
		return groupId;
	}
	
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
	public String getGroupName() {
		return groupName;
	}
	
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	public String getParentGroupId() {
		return parentGroupId;
	}
	
	public void setParentGroupId(String parentGroupId) {
		if(parentGroupId.isEmpty())
		{
			this.parentGroupId = null;
		}
		else
		{
			this.parentGroupId = parentGroupId;
		}
	}
	
	public String getGroupEnglishName() {
		return groupEnglishName;
	}
	
	public void setGroupEnglishName(String groupEnglishName) {
		this.groupEnglishName = groupEnglishName;
	}
	
	public String getLeaderId() {
		return leaderId;
	}
	
	public void setLeaderId(String leaderId) {
		this.leaderId = leaderId.toLowerCase();
	}	
	
	public String getSortOrder() {
		return sortOrder;
	}
	
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	public String getGroupEdate() {
		return groupEdate;
	}
	
	public void setGroupEdate(String groupEdate) {
		this.groupEdate = groupEdate;
	}
	
	public String getIsUse() {
		return isUse;
	}

	public void setIsUse(String isUse) {
		this.isUse = isUse;
	}
}
