package com.lgcns.ikep4.portal.evaluation.constant;

import com.lgcns.ikep4.portal.util.LookupUtil;

public enum IdpRFC {

	LIST("ZHR_RFC_MRIDP_LST"),
	DETAIL("ZHR_RFC_MRIDP_DTL"),
	PROCESS("ZHR_RFC_MRIDP_REQ"),
	PRINT("ZHR_RFC_MRIDP_PRT"),
	TREE("ZHR_RFC_OBJECT_TREE"),
	EMPTY("");

	private String rfc;

	static public IdpRFC lookup(String id) {
		return LookupUtil.lookup(IdpRFC.class, id);
	}

	IdpRFC(String rfc) {
		this.rfc = rfc;
	}

	public String getRfc() {
		return this.rfc;
	}

}
