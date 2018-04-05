package com.lgcns.ikep4.portal.evaluation.constant;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum EvaluationImport {

	I_DATUM, I_SPRSL, I_AYEAR, I_MAPPR, I_MAPPE, I_BUTTON, I_LOGPER, I_IDPAE, I_IDPAR, I_PRINT, I_ECRGUB, I_ECRKEY, I_NODEL, I_COMMENT, I_RSPER, I_OTYPE, I_OBJID, I_HISTO, I_PERNR, I_ORGEH, I_ACDAT,
	IV_BUTTON, IV_RJTEXT, IV_PERNR, IV_PERFM, IV_REVWR, IV_REVIW, IV_DATUM, IV_SPRSL, IV_AYEAR, IV_APRSR, IV_APRSE, IV_PPERNR, IV_ZYEAR, IV_GUBUN, IT_FOOTER, IV_REVWR2,
	IS_APPR, IS_MAPPR, IS_IDPLST, IS_CRJOB, IS_RSLST, IS_MAPPE,
	IT_DETAIL, IT_DETAIL1, IT_DETAIL2, IT_LIST, IT_REQUAL, IT_HPQUAL, IT_CHAPRJ;

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
