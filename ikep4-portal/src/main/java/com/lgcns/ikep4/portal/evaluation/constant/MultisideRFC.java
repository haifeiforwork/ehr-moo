package com.lgcns.ikep4.portal.evaluation.constant;

import com.lgcns.ikep4.portal.util.LookupUtil;

public enum MultisideRFC {

	MULTISIDE_LIST("ZHR_RFC_APR_MSIDE_LST"),
	MULTISIDE_DETAIL("ZHR_RFC_APR_MSIDE_DTL"),
	MULTISIDE_PROCESS("ZHR_RFC_APR_MSIDE_REQ"),
	FEEDBACK_LIST("ZHR_RFC_APR_MSIDE_FB_LST"),
	FEEDBACK_DETAIL("ZHR_RFC_APR_MSIDE_FB_DTL"),
	EMPTY("");

	private String rfc;

	static public MultisideRFC lookup(String id) {
		return LookupUtil.lookup(MultisideRFC.class, id);
	}

	MultisideRFC(String rfc) {
		this.rfc = rfc;
	}

	public String getRfc() {
		return this.rfc;
	}

}
