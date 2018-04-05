package com.lgcns.ikep4.portal.evaluation.constant;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum EvaluationExport {

	E_MODIF, E_PRTBT, E_ACTBT, E_SKIPPV,
	EV_MODIF, EV_APRSER, EV_QUNAM1, EV_QUNAM2, EV_RVSTS, EV_RVSTSX, EV_MREVW, EV_AYEAR,
	ES_RETURN, ES_HEADER, ES_CRJOB, ES_IDPLST, ES_MAPPR, ES_RSLST,
	ET_DETAIL, ET_DETAIL1, ET_DETAIL2, ET_SCORE, ET_LIST, ET_REQUAL, ET_HPQUAL, ET_CHAPRJ, F4_QUALI, ET_OUTLINE, ET_DUTY, ET_AQUAL, ET_KQUAL, ET_KPI, ET_LEGCERT, ET_RECCERT, ET_MOVJOB, ET_DFTXT, ET_BHIDX, ET_ORGEH, ET_HEADER, ET_FOOTER,
	T_MAIN, T_IDPAE, T_RSPER;

	public String toCamelCase() {
		Pattern p = Pattern.compile("_(.)");
	    Matcher m = p.matcher(name().toLowerCase());
	    StringBuffer sb = new StringBuffer();

	    while (m.find()) {
	        m.appendReplacement(sb, m.group(1).toUpperCase());
		}

		return m.appendTail(sb).toString();
	}

}
