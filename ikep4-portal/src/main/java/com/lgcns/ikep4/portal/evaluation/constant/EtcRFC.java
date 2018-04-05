package com.lgcns.ikep4.portal.evaluation.constant;

import com.lgcns.ikep4.portal.util.LookupUtil;

public enum EtcRFC {

	JIKMU("ZHR_RFC_JIKMU_PROFILE"),
	QUALI("ZHR_RFC_QUALI_CATALOG"),
	TREE("ZHR_RFC_OBJECT_TREE"),
	EMPTY("");

	private String rfc;

	static public EtcRFC lookup(String id) {
		return LookupUtil.lookup(EtcRFC.class, id);
	}

	EtcRFC(String rfc) {
		this.rfc = rfc;
	}

	public String getRfc() {
		return this.rfc;
	}

}
