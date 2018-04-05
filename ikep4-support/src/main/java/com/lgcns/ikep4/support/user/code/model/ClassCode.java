/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.user.code.model;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * 클래스 코드 모델
 * 
 * @author 양새로훈(yang.sae@gmail.com)
 * @version $Id: ClassCode.java 16258 2011-08-18 05:37:22Z giljae $
 */
public class ClassCode extends BaseObject {

	/**
	 * Generated Serial Version UID
	 */
	private static final long serialVersionUID = 3168617966687318538L;

	/**
	 * 다국어 메시지용 리스트
	 */
	@Valid
	private List<I18nMessage> i18nMessageList;

	/**
	 * 순번
	 */
	private String num;

	/**
	 * 클래스 ID
	 */
	@NotNull
	@Size(min=0,max=20)
	private String classId;

	/**
	 * 클래스 이름
	 */
	@NotNull
	@Size(min=0,max=100)
	private String className;

	/**
	 * 클래스 설명
	 */
	@NotNull
	@Size(min = 0, max = 150)
	private String description;

	/**
	 * 등록자 ID
	 */
	private String registerId;

	/**
	 * 등록자 이름
	 */
	private String registerName;

	/**
	 * 등록일
	 */
	private String registDate;

	/**
	 * 수정자 ID
	 */
	private String updaterId;

	/**
	 * 수정자 이름
	 */
	private String updaterName;

	/**
	 * 수정일
	 */
	private String updateDate;

	/**
	 * 코드 중복체크용 변수
	 */
	private String codeCheck;

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRegisterId() {
		return registerId;
	}

	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}

	public String getRegisterName() {
		return registerName;
	}

	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}

	public String getRegistDate() {
		return registDate;
	}

	public void setRegistDate(String registDate) {
		this.registDate = registDate;
	}

	public String getUpdaterId() {
		return updaterId;
	}

	public void setUpdaterId(String updaterId) {
		this.updaterId = updaterId;
	}

	public String getUpdaterName() {
		return updaterName;
	}

	public void setUpdaterName(String updaterName) {
		this.updaterName = updaterName;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getCodeCheck() {
		return codeCheck;
	}

	public void setCodeCheck(String codeCheck) {
		this.codeCheck = codeCheck;
	}

	public List<I18nMessage> getI18nMessageList() {
		return i18nMessageList;
	}

	public void setI18nMessageList(List<I18nMessage> i18nMessageList) {
		this.i18nMessageList = i18nMessageList;
	}
}
