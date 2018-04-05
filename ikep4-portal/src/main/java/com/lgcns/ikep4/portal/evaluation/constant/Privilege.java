package com.lgcns.ikep4.portal.evaluation.constant;

import com.lgcns.ikep4.portal.util.LookupUtil;

public enum Privilege {

	EVALUATION(
			PrivilegeRFC.LIST,
			"/portal/evaluation/privilege/privilegeList"),
	EVALUATION_VIEW(
			PrivilegeRFC.DETAIL,
			"/portal/evaluation/privilege/privilegeView"),
	HSS(
			PrivilegeRFC.HSS,
			"/portal/evaluation/privilege/privilegeHSSList"),
	HSS_VIEW(
			PrivilegeRFC.DETAIL,
			"/portal/evaluation/privilege/privilegeHSSView"),
	EMPTY(
			PrivilegeRFC.EMPTY,
			"");

	private String view;
	private PrivilegeRFC privilegeRFC;

	Privilege(PrivilegeRFC privilegeRFC, String view) {
		this.privilegeRFC = privilegeRFC;
		this.view = view;
	}

	static public Privilege lookup(String id) {
		return LookupUtil.lookup(Privilege.class, id);
	}

	public String getView() {
		return this.view;
	}

	public PrivilegeRFC getPrivilegeRFC() {
		return this.privilegeRFC;
	}

}
