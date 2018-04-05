package com.lgcns.ikep4.portal.evaluation.constant;

import com.lgcns.ikep4.portal.util.LookupUtil;

public enum TechnicalRFC {

	LIST("ZHR_RFC_APR_MTECH_APR_LST"),
	DETAIL("ZHR_RFC_APR_MTECH_APR_DTL"),
	PROCESS("ZHR_RFC_APR_MTECH_APR_REQ"),
	REVIEW("ZHR_RFC_APR_MTECH_REV_LST"),
	REVIEW_PROCESS("ZHR_RFC_APR_MTECH_REV_REQ"),
	HSS("ZHR_RFC_APR_MTECH_HSS_LST"),
	EMPTY("");

	private String rfc;

	static public TechnicalRFC lookup(String id) {
		return LookupUtil.lookup(TechnicalRFC.class, id);
	}

	TechnicalRFC(String rfc) {
		this.rfc = rfc;
	}

	public String getRfc() {
		return this.rfc;
	}

}
