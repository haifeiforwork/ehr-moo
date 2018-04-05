package com.lgcns.ikep4.portal.evaluation.constant;

import com.lgcns.ikep4.portal.util.LookupUtil;

public enum Technical {

	EVALUATION(
			TechnicalRFC.LIST,
			"/portal/evaluation/technical/technicalList"),
	EVALUATION_VIEW1(
			TechnicalRFC.DETAIL,
			"/portal/evaluation/technical/technicalView1"),
	EVALUATION_VIEW2(
			TechnicalRFC.DETAIL,
			"/portal/evaluation/technical/technicalView2"),
	REVIEW(
			TechnicalRFC.REVIEW,
			"/portal/evaluation/technical/technicalReview"),
	INDIVIDUAL_EVALUATION(
			TechnicalRFC.LIST,
			"/portal/evaluation/technical/popTechnicalIndividualEvaluationList"),
	INDIVIDUAL_EVALUATION_VIEW(
			TechnicalRFC.DETAIL,
			"/portal/evaluation/technical/popTechnicalIndividualEvaluationView"),
	HSS(
			TechnicalRFC.HSS,
			"/portal/evaluation/technical/technicalHSSList"),
	HSS_VIEW(
			TechnicalRFC.DETAIL,
			"/portal/evaluation/technical/technicalHSSView"),
	EMPTY(
			TechnicalRFC.EMPTY,
			"");

	private String view;
	private TechnicalRFC technicalRFC;

	Technical(TechnicalRFC technicalRFC, String view) {
		this.technicalRFC = technicalRFC;
		this.view = view;
	}

	static public Technical lookup(String id) {
		return LookupUtil.lookup(Technical.class, id);
	}

	public String getView() {
		return this.view;
	}

	public TechnicalRFC getTechnicalRFC() {
		return this.technicalRFC;
	}

}
