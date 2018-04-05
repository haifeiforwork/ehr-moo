package com.lgcns.ikep4.portal.evaluation.constant;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum PositionKeySet {

	COMMON(Arrays.asList(
			"ORGEH",
			"ORGTX",
			"PERNR",
			"ENAME",
			"PERSG",
			"PERSK",
			"BTRTL",
			"ZZJIK",
			"COTXT",
			"TRFGR",
			"STELL",
			"STLTX",
			"PLANS",
			"PLSTX",
			"CPLSTX",
			"SRTKY",
			"MODIF",
			"MSGTY",
			"MSGTX",
			"NUMBR"
	)),
	EMPTY(Collections.<String> emptyList());

	private List<String> keys;

	PositionKeySet(List<String> keys) {
		this.keys = keys;
	}

	public List<String> getKeys() {
		return this.keys;
	}

}
