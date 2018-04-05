package com.lgcns.ikep4.portal.evaluation.constant;

public enum UserRole {

	APRSE("APE"),
	APRSR("APR"),
	APRSR1("APR1"),
	REVWR("REV"),
	EMPTY("");

	private String code;

	UserRole(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

}
