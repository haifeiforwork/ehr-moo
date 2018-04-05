package com.lgcns.ikep4.portal.evaluation.constant;

public enum Tree {

	QULITY("Q"), DUTY("C"), ORGANIZATION("O"), EDUCATION("D");

	private String code;

	Tree(String code) {
		this.code = code;
	}

	public String code() {
		return this.code;
	}

}
