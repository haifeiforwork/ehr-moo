package com.lgcns.ikep4.portal.evaluation.constant;

import com.lgcns.ikep4.portal.util.LookupUtil;

public enum PrivilegeRFC {

	LIST("ZHR_RFC_APR_NEXTA_APR_LST"),
	DETAIL("ZHR_RFC_APR_NEXTA_APR_DTL"),
	PROCESS("ZHR_RFC_APR_NEXTA_APR_REQ"),
	HSS("ZHR_RFC_APR_NEXTA_HSS_LST"),
	EMPTY("");

	private String rfc;

	static public PrivilegeRFC lookup(String id) {
		return LookupUtil.lookup(PrivilegeRFC.class, id);
	}

	PrivilegeRFC(String rfc) {
		this.rfc = rfc;
	}

	public String getRfc() {
		return this.rfc;
	}

}
