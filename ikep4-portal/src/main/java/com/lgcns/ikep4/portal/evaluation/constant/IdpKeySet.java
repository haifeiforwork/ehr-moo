package com.lgcns.ikep4.portal.evaluation.constant;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum IdpKeySet {

	COMMON(Arrays.asList(
			"AYEAR",
			"ORGEH",
			"ORGTX",
			"IDPAE",
			"IDPAENM",
			"PERSG",
			"STELL",
			"STLTX",
			"ZZJIK",
			"COTXT",
			"TRFGR",
			"IDPAR",
			"IDPARNM",
			"ZZJIK1",
			"COTXT1",
			"TRFGR1",
			"IDSTS",
			"IDSTSX",
			"IDSTSDL",
			"SRTKY",
			"MODIF"
	)),
	JOB(Arrays.asList(
			"IDCGL",
			"HPJOB1",
			"HPJOB1T",
			"HPORG1",
			"HPORG1T",
			"HPJOB2",
			"HPJOB2T",
			"HPORG2",
			"HPORG2T",
			"HPJOB3",
			"HPJOB3T",
			"HPORG3",
			"HPORG3T"
	)),
	QUALITY(Arrays.asList(
			"IDPGB",
			"IDOTY",
			"IDOBJ",
			"IDOBJT",
			"SEQNR",
			"IDDES",
			"IDEDU",
			"IDEDUT",
			"IDEDUX",
			"IDEDPD",
			"IDEDRT"
	)),
	EMPTY(Collections.<String> emptyList());

	private List<String> keys;

	IdpKeySet(List<String> keys) {
		this.keys = keys;
	}

	public List<String> getKeys() {
		return this.keys;
	}

}
