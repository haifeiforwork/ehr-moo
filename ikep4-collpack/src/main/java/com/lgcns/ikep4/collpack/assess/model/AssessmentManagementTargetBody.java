/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.assess.model;

import java.util.Date;

/**
 * Assessment Management AssessmentManagementTargetBody model
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: AssessmentManagementTargetBody.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class AssessmentManagementTargetBody extends AssessmentManagementTargetPK {

	/**
	 *
	 */
	private static final long serialVersionUID = -2332832461802317178L;

	/**
	 * 사용자 PVI 산정 대상 모듈 이름(Profile, Microblogging, Blog, Team Collaboration, Cafe,   Work Manual, Q&A, BBS, Forum, Idea, Corporate Vocabulary)
	 */
	private String moduleName;

	/**
	 * 관리 대상 필수 여부 ( 0 : 필수, 1 : 필수 아님) Profile, Microblogging, Blog는 디폴트로 필수임
	 */
	private int required;

	/**
	 * 사용가능여부(0:사용가능, 1:사용불가) - Knowledge Monitor에서 관리대상으로 선택된 모듈(ikep4_kn_module)이 사용가능 모듈임
	 */
	private int available;

	/**
	 * 관리 대상 여부 ( 0 : 대상, 1 : 대상 아님)
	 */
	private int selection;

	/**
	 * 등록자 ID
	 */
	private String registerId;

	/**
	 * 등록일시
	 */
	private Date registDate;

	/**
	 * @return the moduleName
	 */
	public String getModuleName() {
		return moduleName;
	}

	/**
	 * @param moduleName the moduleName to set
	 */
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	/**
	 * @return the required
	 */
	public int getRequired() {
		return required;
	}

	/**
	 * @param required the required to set
	 */
	public void setRequired(int required) {
		this.required = required;
	}

	/**
	 * @return the selection
	 */
	public int getSelection() {
		return selection;
	}

	/**
	 * @param selection the selection to set
	 */
	public void setSelection(int selection) {
		this.selection = selection;
	}

	/**
	 * @return the registerId
	 */
	public String getRegisterId() {
		return registerId;
	}

	/**
	 * @param registerId the registerId to set
	 */
	public void setRegisterId(String registerId) {
		this.registerId = registerId;
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
	 * @return the available
	 */
	public int getAvailable() {
		return available;
	}

	/**
	 * @param available the available to set
	 */
	public void setAvailable(int available) {
		this.available = available;
	}

}
