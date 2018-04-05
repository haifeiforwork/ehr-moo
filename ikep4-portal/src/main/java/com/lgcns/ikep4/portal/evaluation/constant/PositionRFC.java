package com.lgcns.ikep4.portal.evaluation.constant;

import com.lgcns.ikep4.portal.util.LookupUtil;

public enum PositionRFC {

	LIST("ZHR_RFC_POSCHG_LST"),
	PROCESS("ZHR_RFC_POSCHG_REQ"),
	ORGANIZATION("ZHR_RFC_POSCHG_ORG"),
	APPOINT("ZHR_RFC_PA_002_GET_DATA"),
	EMPTY("");

	private String rfc;

	static public PositionRFC lookup(String id) {
		return LookupUtil.lookup(PositionRFC.class, id);
	}

	PositionRFC(String rfc) {
		this.rfc = rfc;
	}

	public String getRfc() {
		return this.rfc;
	}

}
