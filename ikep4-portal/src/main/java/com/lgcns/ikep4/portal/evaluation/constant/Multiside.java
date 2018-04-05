package com.lgcns.ikep4.portal.evaluation.constant;

import com.lgcns.ikep4.portal.util.LookupUtil;

public enum Multiside {

	MULTISIDE_DIAGNOSIS(
			MultisideRFC.MULTISIDE_LIST,
			"/portal/evaluation/multiside/multisideDiagnosisList"),
	MULTISIDE_DIAGNOSIS_VIEW(
			MultisideRFC.MULTISIDE_DETAIL,
			"/portal/evaluation/multiside/multisideDiagnosisView"),
	MULTISIDE_RESULT(
			MultisideRFC.MULTISIDE_LIST,
			""),
	FEEDBACK_LIST(
			MultisideRFC.FEEDBACK_LIST,
			"/portal/evaluation/multiside/multisideFeedbackList"),
	FEEDBACK_DETAIL(
			MultisideRFC.FEEDBACK_DETAIL,
			"/portal/evaluation/multiside/multisideFeedbackView"),
	FEEDBACK_PRINT(
			MultisideRFC.FEEDBACK_DETAIL,
			"/portal/evaluation/multiside/multisideFeedbackViewPrint"),
	EMPTY(
			MultisideRFC.EMPTY,
			"");

	private String view;
	private MultisideRFC multisideRFC;

	Multiside(MultisideRFC multisideRFC, String view) {
		this.multisideRFC = multisideRFC;
		this.view = view;
	}

	static public Multiside lookup(String id) {
		return LookupUtil.lookup(Multiside.class, id);
	}

	public String getView() {
		return this.view;
	}

	public MultisideRFC getMultisideRFC() {
		return this.multisideRFC;
	}

}
