package com.lgcns.ikep4.collpack.collaboration.workspace.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;
import com.lgcns.ikep4.util.lang.StringUtil;

/**
 * 
 * 축하합니다 PORTLET 모델
 *
 * @author 박철종
 * @version $Id: Congratulations.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class Congratulations extends BaseObject{

	/**
	 *
	 */
	private static final long serialVersionUID = 957822910216975243L;
	// 사용자 ID
	private String userId;
	// 사용자 이름
	private String userName;
	// 직급정보
	private String jobPositionName;
	// 그룹 정보
	private String groupName;
	// 생일
	private String birthday;
	// 기념일
	private String weddingAnniv;


	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the jobPositionName
	 */
	public String getJobPositionName() {
		return jobPositionName;
	}

	/**
	 * @param jobPositionName the jobPositionName to set
	 */
	public void setJobPositionName(String jobPositionName) {
		this.jobPositionName = jobPositionName;
	}

	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * @param groupName the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	/**
	 * @return the birthday
	 */
	public String getBirthday() {
		return birthday;
	}

	/**
	 * @param birthday the birthday to set
	 */
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	/**
	 * @return the weddingAnniv
	 */
	public String getWeddingAnniv() {
		return weddingAnniv;
	}

	/**
	 * @param weddingAnniv the weddingAnniv to set
	 */
	public void setWeddingAnniv(String weddingAnniv) {
		this.weddingAnniv = weddingAnniv;
	}
	
	/**
	 * @return the birthdayMmDdType
	 */
	public String getBirthdayMmDdType() {
		
		String returnValue = "";
		
		if(!StringUtil.isEmpty(birthday))
		{
			returnValue = birthday.substring(5, 7) + "." + birthday.substring(8, 10);
		}
		
		return returnValue;
		
	}
	
	/**
	 * @return the weddingAnnivMmDdType
	 */
	public String getWeddingAnnivMmDdType() {
		
		String returnValue = "";
		
		if(!StringUtil.isEmpty(weddingAnniv))
		{
			returnValue = weddingAnniv.substring(5, 7) + "." + weddingAnniv.substring(8, 10);
		}
		
		return returnValue;
		
	}
	
}