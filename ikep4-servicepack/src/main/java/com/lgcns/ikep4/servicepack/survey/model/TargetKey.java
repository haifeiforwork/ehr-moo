package com.lgcns.ikep4.servicepack.survey.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * 설문대상자 정보(KEY)
 *
 * @author 고인호(ihko11@daum.net)
 * @version $Id: TargetKey.java 16244 2011-08-18 04:11:42Z giljae $
 */
public class TargetKey extends BaseObject {
	/**
	 *
	 */
	private static final long serialVersionUID = -1621902682127533317L;

	/**
     * 설문 ID
     */
    private String surveyId;

    /**
     *설문대상자 ID
	(타겟타입이 0 인 경우  사용자 ID, 1인경우 그룹ID)
     */
    private String targetId;

    /**
     * 개별 설문대상자 타입
	( 0 : 개인, 1 : 그룹)
     */
    private Integer targetType;
    
    public TargetKey(){}

	public TargetKey(String surveyId, String targetId, Integer targetType) {
		super();
		this.surveyId = surveyId;
		this.targetId = targetId;
		this.targetType = targetType;
	}

	public String getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(String surveyId) {
		this.surveyId = surveyId;
	}

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public Integer getTargetType() {
		return targetType;
	}

	public void setTargetType(Integer targetType) {
		this.targetType = targetType;
	}
}