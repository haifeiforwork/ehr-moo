package com.lgcns.ikep4.servicepack.survey.model;



public class Target extends SurveyBaseObject {

	/**
	 *설문대상자 정보
	 */
	private static final long serialVersionUID = -5390265513591535755L;

	private TargetKey targetKey;
	private String targetName;
	private String engTargetName;

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	public TargetKey getTargetKey() {
		return targetKey;
	}

	public void setTargetKey(TargetKey targetKey) {
		this.targetKey = targetKey;
	}

	public String getEngTargetName() {
		return engTargetName;
	}

	public void setEngTargetName(String engTargetName) {
		this.engTargetName = engTargetName;
	}
	



}