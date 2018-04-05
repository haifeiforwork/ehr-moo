package com.lgcns.ikep4.portal.evaluation.constant;

import com.lgcns.ikep4.portal.util.LookupUtil;

public enum Etc {

	JIKMU(
			EtcRFC.JIKMU,
			"C",
			"/portal/evaluation/etc/retrieveJikmu"),
	QUALI(
			EtcRFC.QUALI,
			"Q",
			"/portal/evaluation/etc/retrieveQuali"),
	EMPTY(
			EtcRFC.EMPTY,
			"",
			"");

	private String view;
	private String objectType;
	private EtcRFC etcRFC;

	Etc(EtcRFC etcRFC, String objectType, String view) {
		this.etcRFC = etcRFC;
		this.view = view;
		this.objectType = objectType;
	}

	static public Etc lookup(String id) {
		return LookupUtil.lookup(Etc.class, id);
	}

	public String getView() {
		return this.view;
	}

	public String getObjectType() {
		return this.objectType;
	}

	public EtcRFC getEtcRFC() {
		return this.etcRFC;
	}

}
