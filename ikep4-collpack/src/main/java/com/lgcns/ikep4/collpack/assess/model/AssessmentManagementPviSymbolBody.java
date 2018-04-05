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
 * Assessment Management AssessmentManagementPviSymbolBody model
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: AssessmentManagementPviSymbolBody.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class AssessmentManagementPviSymbolBody extends AssessmentManagementPviSymbolPK { 

	/**
	 *
	 */
	private static final long serialVersionUID = -4283105894853742760L;

	/**
	 * PVI 심볼 단계(1~10단계)
	 */
	private int step;

	/**
	 * PVI 심볼 단계별 구간적용값(단위 %)
	 */
	private int sectionValue;

	/**
	 * 구간 시작 PVI 점수
	 */
	private int sectionStartScore;

	/**
	 * 구간 종료 PVI 점수
	 */
	private int sectionEndScore;

	/**
	 * 포탈 ID
	 */
	private String portalId;

	/**
	 * 등록자 ID
	 */
	private String registerId;

	/**
	 * 등록일시
	 */
	private Date registDate;

	/**
	 * @return the step
	 */
	public int getStep() {
		return step;
	}

	/**
	 * @param step the step to set
	 */
	public void setStep(int step) {
		this.step = step;
	}

	/**
	 * @return the sectionValue
	 */
	public int getSectionValue() {
		return sectionValue;
	}

	/**
	 * @param sectionValue the sectionValue to set
	 */
	public void setSectionValue(int sectionValue) {
		this.sectionValue = sectionValue;
	}

	/**
	 * @return the sectionStartScore
	 */
	public int getSectionStartScore() {
		return sectionStartScore;
	}

	/**
	 * @param sectionStartScore the sectionStartScore to set
	 */
	public void setSectionStartScore(int sectionStartScore) {
		this.sectionStartScore = sectionStartScore;
	}

	/**
	 * @return the sectionEndScore
	 */
	public int getSectionEndScore() {
		return sectionEndScore;
	}

	/**
	 * @param sectionEndScore the sectionEndScore to set
	 */
	public void setSectionEndScore(int sectionEndScore) {
		this.sectionEndScore = sectionEndScore;
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

}
