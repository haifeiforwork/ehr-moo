package com.lgcns.ikep4.portal.evaluation.constant;

import com.lgcns.ikep4.portal.util.LookupUtil;

public enum PromotionRFC {

	LIST("ZHR_RFC_RWHTE_LST"),
	DETAIL("ZHR_RFC_RWHTE_DTL"),
	PROCESS("ZHR_RFC_RWHTE_REQ"),
	PRINT("ZHR_RFC_RWHTE_PRT"),
	EMPTY("");

	private String rfc;

	static public PromotionRFC lookup(String id) {
		return LookupUtil.lookup(PromotionRFC.class, id);
	}

	PromotionRFC(String rfc) {
		this.rfc = rfc;
	}

	public String getRfc() {
		return this.rfc;
	}

}
