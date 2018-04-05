package com.lgcns.ikep4.portal.evaluation.constant;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum PromotionKeySet {

	COMMON(Arrays.asList(
			"AYEAR",
			"ORGEH",
			"ORGTX",
			"RSPER",
			"RSPERNM",
			"PERSG",
			"PERSK",
			"STELL",
			"STLTX",
			"ZZJIK",
			"COTXT",
			"TRFGR",
			"RTRFGR",
			"RSDAT",
			"KTCDAT",
			"KPTERM",
			"KPYEAR",
			"RSSTS",
			"RSSTSX",
			"RSSTSDL",
			"BIGO",
			"SRTKY",
			"MODIF"
	)),
	SUBJECT(Arrays.asList(
			"CHAPRNM",
			"CHAPRD1",
			"CHAPRD2",
			"CHAPRD3",
			"CHAPRD4",
			"CHAPRD5"
	)),
	EMPTY(Collections.<String> emptyList());

	private List<String> keys;

	PromotionKeySet(List<String> keys) {
		this.keys = keys;
	}

	public List<String> getKeys() {
		return this.keys;
	}

}
