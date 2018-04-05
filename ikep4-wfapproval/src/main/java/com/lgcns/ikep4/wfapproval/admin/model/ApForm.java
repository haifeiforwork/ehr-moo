/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.wfapproval.admin.model;

import java.util.Date;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * 
 * 양식 마스터정보
 *
 * @author 박희진(neoheejin@naver.com)
 * @version $Id: ApForm.java 16234 2011-08-18 02:44:36Z giljae $
 */
public class ApForm extends BaseObject {

	private static final long serialVersionUID = -1130109513493565971L;

	/**
	 * 양식 템플릿 VO
	 */
	private ApFormTpl apFormTpl;

	/**
	 *	양식ID
	 */
	private String 	formId;
	
	/**
	 *	양식명
	 */
	@NotEmpty
	private String 	formName;
	
	/**
	 *	양식유형 코드
	 */
	private String 	formClassCd;
	
	/**
	 *	양식구분 코드
	 */
	private String 	formTypeCd;
	
	/**
	 *	양식유형명
	 */
	private String 	formClassName;
	
	/**
	 *	양식구분명
	 */
	private String 	formTypeName;
	
	/**
	 *	양식설명
	 */
	@Size(min=0, max=50)
	private String 	formDesc;
	
	/**
	 *	양식 사용유무
	 */
	private String 	isUse;
	
	/**
	 *	양식 등록자
	 */
	private String 	registUserId;
	
	/**
	 *	양식 등록일자
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd hh:mm")
	private Date 	registDate;
	
	/**
	 *	양식 수정자
	 */
	private String 	modifyUserId;
	
	/**
	 *	양식 수정일자
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd hh:mm")
	private Date 	modifyDate;
	
	/**
	 * 페이지 번호
	 */
	private Integer pageNum;
	
	/**
	 * 양식 템플릿 VO
	 * @return the apFormTpl
	 */
	public ApFormTpl getApFormTpl() {
		return apFormTpl;
	}

	/**
	 * 양식 템플릿 VO
	 * @param apFormTpl the apFormTpl to set
	 */
	public void setApFormTpl(ApFormTpl apFormTpl) {
		this.apFormTpl = apFormTpl;
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
	 * @return the formClassName
	 */
	public String getFormClassName() {
		return formClassName;
	}

	/**
	 * @param formClassName the formClassName to set
	 */
	public void setFormClassName(String formClassName) {
		this.formClassName = formClassName;
	}

	/**
	 * @return the formTypeName
	 */
	public String getFormTypeName() {
		return formTypeName;
	}

	/**
	 * @param formTypeName the formTypeName to set
	 */
	public void setFormTypeName(String formTypeName) {
		this.formTypeName = formTypeName;
	}

	/**
	 * @return the formDesc
	 */
	public String getFormDesc() {
		return formDesc;
	}

	/**
	 * @param formDesc the formDesc to set
	 */
	public void setFormDesc(String formDesc) {
		this.formDesc = formDesc;
	}

	/**
	 * @return the isUse
	 */
	public String getIsUse() {
		return isUse;
	}

	/**
	 * @param isUse the isUse to set
	 */
	public void setIsUse(String isUse) {
		this.isUse = isUse;
	}

	/**
	 * @return the registUserId
	 */
	public String getRegistUserId() {
		return registUserId;
	}

	/**
	 * @param registUserId the registUserId to set
	 */
	public void setRegistUserId(String registUserId) {
		this.registUserId = registUserId;
	}

	/**
	 * @return the registDate
	 */
	public Date getRegistDate() {
		return registDate;
	}

	/**
	 * @param registDate the registDate to set
	 */
	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}

	/**
	 * @return the modifyUserId
	 */
	public String getModifyUserId() {
		return modifyUserId;
	}

	/**
	 * @param modifyUserId the modifyUserId to set
	 */
	public void setModifyUserId(String modifyUserId) {
		this.modifyUserId = modifyUserId;
	}

	/**
	 * @return the modifyDate
	 */
	public Date getModifyDate() {
		return modifyDate;
	}

	/**
	 * @param modifyDate the modifyDate to set
	 */
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	/**
	 * 페이지 번호
	 * @return the pageNum
	 */
	public Integer getPageNum() {
		return pageNum;
	}

	/**
	 * 페이지 번호
	 * @param pageNum the pageNum to set
	 */
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

}
