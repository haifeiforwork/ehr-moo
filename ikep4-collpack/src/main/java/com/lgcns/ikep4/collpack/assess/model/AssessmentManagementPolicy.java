/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.assess.model;

import org.hibernate.validator.constraints.NotEmpty;



/**
 * Assessment Management AssessmentManagementPolicy model
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: AssessmentManagementPolicy.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class AssessmentManagementPolicy extends AssessmentManagementPolicyBody {

	/**
	 *
	 */
	private static final long serialVersionUID = 1415947116564410970L; 

	/**
	 * PVI 평가점수 산정 시작일 (문자열 yyyy.MM.dd 형식)
	 */
	private String evaluationStartDateString;

	/**
	 * 생성자
	 */
	public AssessmentManagementPolicy() {
		super();
	}

	/**
	 * 생성자
	 * @param portalId
	 */
	public AssessmentManagementPolicy(String portalId) {
		super(portalId);
	}

	/**
	 * @return the evaluationStartDateString
	 */
	public String getEvaluationStartDateString() {
		return evaluationStartDateString;
	}

	/**
	 * @param evaluationStartDateString the evaluationStartDateString to set
	 */
	public void setEvaluationStartDateString(String evaluationStartDateString) {
		this.evaluationStartDateString = evaluationStartDateString;
	}

}
