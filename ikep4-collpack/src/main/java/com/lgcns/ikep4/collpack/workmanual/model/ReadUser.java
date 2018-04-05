/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.workmanual.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * TODO Javadoc주석작성
 *
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: ReadUser.java 16236 2011-08-18 02:48:22Z giljae $
 */

public class ReadUser extends BaseObject {
	private static final long serialVersionUID = -920386194320297931L;
	
	/**
	 * 문서조회자 ID 
	 */
	private String readUserId;
	
	/**
	 * 카테고리 ID 
	 */
	private String categoryId;
	
	/**
	 * 담당자명 
	 */
	private String readUserName;
	
	/**
	 * 담당자 부서명 
	 */
	private String readUserTeamName;
	
	/**
	 * 담당자 직책명 
	 */
	private String readUserJobTitleName;
	
	/**
	 * 담당자명(영어) 
	 */
	private String readUserEnglishName;
	
	/**
	 * 담당자 부서명(영어) 
	 */
	private String readUserTeamEnglishName;
	
	/**
	 * 담당자 직책명(영어) 
	 */
	private String readUserJobTitleEnglishName;
	
	/**
	 * @return the readUserId
	 */
	public String getReadUserId() {
		return readUserId;
	}
	/**
	 * @param readUserId the readUserId to set
	 */
	public void setReadUserId(String readUserId) {
		this.readUserId = readUserId;
	}
	/**
	 * @return the categoryId
	 */
	public String getCategoryId() {
		return categoryId;
	}
	/**
	 * @param categoryId the categoryId to set
	 */
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	/**
	 * @return the readUserName
	 */
	public String getReadUserName() {
		return readUserName;
	}
	/**
	 * @param readUserName the readUserName to set
	 */
	public void setReadUserName(String readUserName) {
		this.readUserName = readUserName;
	}
	/**
	 * @return the readUserTeamName
	 */
	public String getReadUserTeamName() {
		return readUserTeamName;
	}
	/**
	 * @param readUserTeamName the readUserTeamName to set
	 */
	public void setReadUserTeamName(String readUserTeamName) {
		this.readUserTeamName = readUserTeamName;
	}
	/**
	 * @return the readUserJobTitleName
	 */
	public String getReadUserJobTitleName() {
		return readUserJobTitleName;
	}
	/**
	 * @param readUserJobTitleName the readUserJobTitleName to set
	 */
	public void setReadUserJobTitleName(String readUserJobTitleName) {
		this.readUserJobTitleName = readUserJobTitleName;
	}
	/**
	 * @return the readUserEnglishName
	 */
	public String getReadUserEnglishName() {
		return readUserEnglishName;
	}
	/**
	 * @param readUserEnglishName the readUserEnglishName to set
	 */
	public void setReadUserEnglishName(String readUserEnglishName) {
		this.readUserEnglishName = readUserEnglishName;
	}
	/**
	 * @return the readUserTeamEnglishName
	 */
	public String getReadUserTeamEnglishName() {
		return readUserTeamEnglishName;
	}
	/**
	 * @param readUserTeamEnglishName the readUserTeamEnglishName to set
	 */
	public void setReadUserTeamEnglishName(String readUserTeamEnglishName) {
		this.readUserTeamEnglishName = readUserTeamEnglishName;
	}
	/**
	 * @return the readUserJobTitleEnglishName
	 */
	public String getReadUserJobTitleEnglishName() {
		return readUserJobTitleEnglishName;
	}
	/**
	 * @param readUserJobTitleEnglishName the readUserJobTitleEnglishName to set
	 */
	public void setReadUserJobTitleEnglishName(String readUserJobTitleEnglishName) {
		this.readUserJobTitleEnglishName = readUserJobTitleEnglishName;
	}

}
