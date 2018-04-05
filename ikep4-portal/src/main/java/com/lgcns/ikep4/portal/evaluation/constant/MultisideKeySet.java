package com.lgcns.ikep4.portal.evaluation.constant;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum MultisideKeySet {

	DIAGNOSIS(Arrays.asList(
			"ORGEH",
			"ORGTX",
			"MAPPE",
			"MAPENM",
			"PERSG",
			"ZZJIK",
			"COTXT",
			"TRFGR",
			"MAPPR",
			"MAPRNM",
			"ZZJIK1",
			"COTXT1",
			"TRFGR1",
			"MSGRP",
			"MSGRPX",
			"MSGRPDL",
			"MSSTS",
			"MSSTSX",
			"MSSTSDL",
			"AYEAR",
			"SRTKY",
			"MODIF"
	)),
	DIAGNOSIS_DETAIL(Arrays.asList(
			"MRGRP",
			"SEQNR",
			"MROTY",
			"MROBJ",
			"MOTYP",
			"MOBJD",
			"MSTEXT",
			"MDTAIL",
			"MRSLT"
	)),
	FEEDBACK_DETAIL(Arrays.asList(
			"AYEAR",
			"MAPPE",
			"MAPENM",
			"LOCAT",
			"LOCATX",
			"ORGEH",
			"ORGTX",
			"ZZJIK",
			"COTXT",
			"TRFGR",
			"PLANS",
			"PLSTX",
			"PERSG",
			"PGTXT",
			"FBEGDA",
			"FENDDA",
			"FDATUM",
			"SRTKY",
			"MODIF"
			)),
	EMPTY(Collections.<String> emptyList());

	private List<String> keys;

	MultisideKeySet(List<String> keys) {
		this.keys = keys;
	}

	public List<String> getKeys() {
		return this.keys;
	}

}
