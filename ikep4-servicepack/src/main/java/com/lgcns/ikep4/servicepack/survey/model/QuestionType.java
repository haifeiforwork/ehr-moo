package com.lgcns.ikep4.servicepack.survey.model;

public enum QuestionType {
	A0("A0","단일선택 기본형"),
	A1("A1","단일선택 이미지"),
	A2("A2","단일선택 TextBox 추가"),
	A3("A3","단일선택 TextArea 추가"),
	A4("A4","다중선택 기본형"),
	A5("A5","다중선택 이미지"),
	A6("A6","다중선택 TextBox 추가"),
	A7("A7","다중선택 TextArea 추가"),
	B0("B0","단일 TextBox"),
	B1("B1","TextBox Form"),
	B2("B2","다중TextBox"),
	B3("B3","TextArea"),
	C0("C0","순위"),
	D0("D0","척도 3점"),
	D1("D1","척도 5점"),
	D2("D2","척도 6점"),
	D3("D3","척도 7점"),
	D4("D4","척도 OX + 5점"),
	D5("D5","척도 OX + 7점"),
	D6("D6","척도 N/A + 7점"),
	D7("D7","수준");
	 
	 private String code;
	 private String status;
	 
	 QuestionType(String code,String status){
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
