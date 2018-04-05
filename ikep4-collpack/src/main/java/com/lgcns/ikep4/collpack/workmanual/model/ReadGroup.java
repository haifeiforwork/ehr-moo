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
 * @version $Id: ReadGroup.java 16236 2011-08-18 02:48:22Z giljae $
 */

public class ReadGroup extends BaseObject {
	private static final long serialVersionUID = 7204378011224899757L;
	
	/**
	 * 문서조회 조직(부서)ID 
	 */
	private String readGroupId;
	
	/**
	 * 카테고리 ID 
	 */
	private String categoryId;
	
	/**
	 * 포탈ID 
	 */
	private String portalId;
	
	/**
	 * 조직(부서)명 
	 */
	private String readGroupName;
	
	/**
	 * 조직(부서)명(영어) 
	 */
	private String readGroupEnglishName;
	
	/**
	 * @return the readGroupId
	 */
	public String getReadGroupId() {
		return readGroupId;
	}
	/**
	 * @param readGroupId the readGroupId to set
	 */
	public void setReadGroupId(String readGroupId) {
		this.readGroupId = readGroupId;
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
	 * @return the portalId
	 */
	public String getPortalId() {
		return portalId;
	}
	/**
	 * @param portalId the portalId to set
	 */
	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}
	/**
	 * @return the readGroupName
	 */
	public String getReadGroupName() {
		return readGroupName;
	}
	/**
	 * @param readGroupName the readGroupName to set
	 */
	public void setReadGroupName(String readGroupName) {
		this.readGroupName = readGroupName;
	}
	/**
	 * @return the readGroupEnglishName
	 */
	public String getReadGroupEnglishName() {
		return readGroupEnglishName;
	}
	/**
	 * @param readGroupEnglishName the readGroupEnglishName to set
	 */
	public void setReadGroupEnglishName(String readGroupEnglishName) {
		this.readGroupEnglishName = readGroupEnglishName;
	}

}
