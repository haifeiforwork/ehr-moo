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
 * @version $Id: WriteUser.java 16236 2011-08-18 02:48:22Z giljae $
 */

public class WriteUser extends BaseObject {
	private static final long serialVersionUID = -4478091770152758109L;

	/**
	 * 문서담당자ID 
	 */
	private String categoryId;
	
	/**
	 * 카테고리 ID 
	 */
	private String writeUserId;
	
	/**
	 * 담당명 
	 */
	private String writeUserName;
	
	/**
	 * 담당자 부서명 
	 */
	private String writeUserTeamName;
	
	/**
	 * 담당자 직책명 
	 */
	private String writeUserJobTitleName;
	
	/**
	 * 담당명(영어) 
	 */
	private String writeUserEnglishName;
	
	/**
	 * 담당자 부서명(영어) 
	 */
	private String writeUserTeamEnglishName;
	
	/**
	 * 담당자 직책명(영어) 
	 */
	private String writeUserJobTitleEnglishName;
	
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
	 * @return the writeUserId
	 */
	public String getWriteUserId() {
		return writeUserId;
	}
	/**
	 * @param writeUserId the writeUserId to set
	 */
	public void setWriteUserId(String writeUserId) {
		this.writeUserId = writeUserId;
	}
	/**
	 * @return the writeUserName
	 */
	public String getWriteUserName() {
		return writeUserName;
	}
	/**
	 * @param writeUserName the writeUserName to set
	 */
	public void setWriteUserName(String writeUserName) {
		this.writeUserName = writeUserName;
	}
	/**
	 * @return the writeUserTeamName
	 */
	public String getWriteUserTeamName() {
		return writeUserTeamName;
	}
	/**
	 * @param writeUserTeamName the writeUserTeamName to set
	 */
	public void setWriteUserTeamName(String writeUserTeamName) {
		this.writeUserTeamName = writeUserTeamName;
	}
	/**
	 * @return the writeUserJobTitleName
	 */
	public String getWriteUserJobTitleName() {
		return writeUserJobTitleName;
	}
	/**
	 * @param writeUserJobTitleName the writeUserJobTitleName to set
	 */
	public void setWriteUserJobTitleName(String writeUserJobTitleName) {
		this.writeUserJobTitleName = writeUserJobTitleName;
	}
	/**
	 * @return the writeUserEnglishName
	 */
	public String getWriteUserEnglishName() {
		return writeUserEnglishName;
	}
	/**
	 * @param writeUserEnglishName the writeUserEnglishName to set
	 */
	public void setWriteUserEnglishName(String writeUserEnglishName) {
		this.writeUserEnglishName = writeUserEnglishName;
	}
	/**
	 * @return the writeUserTeamEnglishName
	 */
	public String getWriteUserTeamEnglishName() {
		return writeUserTeamEnglishName;
	}
	/**
	 * @param writeUserTeamEnglishName the writeUserTeamEnglishName to set
	 */
	public void setWriteUserTeamEnglishName(String writeUserTeamEnglishName) {
		this.writeUserTeamEnglishName = writeUserTeamEnglishName;
	}
	/**
	 * @return the writeUserJobTitleEnglishName
	 */
	public String getWriteUserJobTitleEnglishName() {
		return writeUserJobTitleEnglishName;
	}
	/**
	 * @param writeUserJobTitleEnglishName the writeUserJobTitleEnglishName to set
	 */
	public void setWriteUserJobTitleEnglishName(String writeUserJobTitleEnglishName) {
		this.writeUserJobTitleEnglishName = writeUserJobTitleEnglishName;
	}
	
	
}
