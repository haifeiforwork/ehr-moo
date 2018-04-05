/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.wfapproval.admin.search;

import com.lgcns.ikep4.framework.web.SearchCondition;

/**
 * TODO Javadoc주석작성
 *
 * @author 박희진
 * @version $Id: ApFormSearchCondition.java 16234 2011-08-18 02:44:36Z giljae $
 */
public class ApFormSearchCondition extends SearchCondition {

	private static final long serialVersionUID = -5116116279546818465L;

	private String formId;
	
	private String formTypeCd;
	
	private String formClassCd;
	
	private String formName;
	
	private String searchColumn;
	
	private String searchWord;
	
	@Override
	public Integer getDefaultPagePerRecord() {
		return 10;
	}

	/**
	 * @return the formId
	 */
	public String getFormId() {
		return formId;
	}

	/**
	 * @param formId the formId to set
	 */
	public void setFormId(String formId) {
		this.formId = formId;
	}
	
	/**
	 * @return the formName
	 */
	public String getFormName() {
		return formName;
	}

	/**
	 * @param formName the formName to set
	 */
	public void setFormName(String formName) {
		this.formName = formName;
	}

	/**
	 * @return the searchColumn
	 */
	public String getSearchColumn() {
		return searchColumn;
	}

	/**
	 * @param searchColumn the searchColumn to set
	 */
	public void setSearchColumn(String searchColumn) {
		this.searchColumn = searchColumn;
	}

	/**
	 * @return the searchWord
	 */
	public String getSearchWord() {
		return searchWord;
	}

	/**
	 * @param searchWord the searchWord to set
	 */
	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	/**
	 * @return the formTypeCd
	 */
	public String getFormTypeCd() {
		return formTypeCd;
	}

	/**
	 * @param formTypeCd the formTypeCd to set
	 */
	public void setFormTypeCd(String formTypeCd) {
		this.formTypeCd = formTypeCd;
	}

	/**
	 * @return the formClassCd
	 */
	public String getFormClassCd() {
		return formClassCd;
	}

	/**
	 * @param formClassCd the formClassCd to set
	 */
	public void setFormClassCd(String formClassCd) {
		this.formClassCd = formClassCd;
	}

}
