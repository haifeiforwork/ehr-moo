package com.lgcns.ikep4.portal.evaluation.constant;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum PrivilegeKeySet {

	COMMON(Arrays.asList(
			"LOCAT",
			"LOCATX",
			"ORGEH",
			"ORGTX",
			"PERNR",
			"APRSENM",
			"ZZJIK",
			"COTXT",
			"PERSK",
			"PKTXT",
			"TRFGR",
			"PPERNR",
			"APRSRNM",
			"ZZJIK1",
			"COTXT1",
			"PORGEH",
			"PORGTX",
			"REVIEWER",
			"REVWRNM",
			"ZZJIK2",
			"COTXT2",
			"REASN",
			"REASNX",
			"LRATING",
			"MARK",
			"APSTS",
			"APSTSX",
			"ZYEAR",
			"EXFLG",
			"SRTKY",
			"MODIF",
			"ORGEH2",
			"ORGTX2"
	)),
	EVALUATION(Arrays.asList(
			"ZYEAR",
			"PERNR",
			"OTYPE",
			"OBJID",
			"STEXT",
			"DFTXT",
			"BHIDX",
			"SEQNR",
			"NXGRD",
			"MARK"
	)),
	EMPTY(Collections.<String> emptyList());

	private List<String> keys;

	PrivilegeKeySet(List<String> keys) {
		this.keys = keys;
	}

	public List<String> getKeys() {
		return this.keys;
	}

}
