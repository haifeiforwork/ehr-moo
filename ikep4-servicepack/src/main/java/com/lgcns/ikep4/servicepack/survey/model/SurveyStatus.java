package com.lgcns.ikep4.servicepack.survey.model;

public enum SurveyStatus {
	/**
	 * /ikep4-servicepack/src/main/resources/i18n/ui/survey/ui-survey_ko.properties
	 */
	 WRITING(0,"ui.servicepack.survey.list.surveyStatus.writing"),
	 WAIT(1,"ui.servicepack.survey.list.surveyStatus.wait"),
	 REJECT(2,"ui.servicepack.survey.list.surveyStatus.reject"),
	 ANSERING(3,"ui.servicepack.survey.list.surveyStatus.ansering"),
	 END(4,"ui.servicepack.survey.list.surveyStatus.end"),
	 APPROVE(5,"ui.servicepack.survey.list.surveyStatus.approve");

	 private Integer code;
	 private String status;
	 
	 SurveyStatus(Integer code,String status){
		 this.code = code;
		 this.status = status;
	 }
	 
	 String getStatus(){
		 return status;
	 }

	public Integer getCode() {
		return code;
	}
	
	
}
