package com.lgcns.ikep4.servicepack.survey.model;

public enum ResponseType {
	AWRITE("1","문항(주관식)"),
	ACHOOSE("2","문항(객관식)"),
	QWRITE("3","예시(주관식)"),
	QCHOOSE("4","예시(객관식)");
	 
	 private String code;
	 private String status;
	 
	 ResponseType(String code,String status){
		 this.code =code;
		 this.status = status;
	 }
	 
	 String getStatus(){
		 return status;
	 }

	public String getCode() {
		return code;
	}
}
