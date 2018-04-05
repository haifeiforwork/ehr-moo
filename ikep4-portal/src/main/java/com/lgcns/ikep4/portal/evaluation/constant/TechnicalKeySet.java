package com.lgcns.ikep4.portal.evaluation.constant;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum TechnicalKeySet {

	COMMON(Arrays.asList(
			"ORGEH",
			"ORGTX",
			"APRSE",
			"APRSENM",
			"ZZJIK",
			"COTXT",
			"TRFGR",
			"TECGB",
			"TECGBX",
			"APRSR",
			"APRSRNM",
			"ZZJIK1",
			"COTXT1",
			"MTRAT",
			"REVWR",
			"REVWRNM",
			"ZZJIK2",
			"COTXT2",
			"MSDAT",
			"MTSCO",
			"ADJSC",
			"APSTS",
			"APSTSX",
			"QKID1",
			"QKID2",
			"BSID",
			"EXRSN",
			"HIREDATE",
			"PBLVE",
			"LVCNT",
			"AYEAR",
			"ABDED",
			"EXFLG",
			"SEQNO",
			"SRTKY",
			"MODIF"
	)),
	EVALUATION(Arrays.asList(
			"AYEAR",
			"APRSE",
			"OTYPE",
			"OBJID",
			"STEXT",
			"DFTXT",
			"BHIDX",
			"SEQNR",
			"MTAPR",
			"MTSCO"
	)),
	REVIEW(Arrays.asList(
			"AYEAR",
			"ORGEH",
			"ORGTX",
			"APRSE",
			"APRSENM",
			"TRFGR",
			"FNSCO",
			"ADJSC",
			"CMQSC",
			"RVSC1",
			"RVSC2",
			"RVWSC",
			"REVWR",
			"REVWRNM",
			"RVSTS",
			"RVSTSX",
			"SRTKY",
			"MODIF"
	)),
	EMPTY(Collections.<String> emptyList());

	private List<String> keys;

	TechnicalKeySet(List<String> keys) {
		this.keys = keys;
	}

	public List<String> getKeys() {
		return this.keys;
	}

}
