package com.lgcns.ikep4.portal.evaluation.constant;

import com.lgcns.ikep4.portal.util.LookupUtil;

public enum Idp {

	IDP_SETTING(
			IdpRFC.LIST,
			"/portal/evaluation/idp/idpSettingList"),
	IDP_SETTING_VIEW(
			IdpRFC.DETAIL,
			"/portal/evaluation/idp/idpSettingView"),
	IDP_SETTING_VIEW_PRINT(
			IdpRFC.DETAIL,
			"/portal/evaluation/idp/idpSettingViewPrint"),
	IDP_AGREEMENT(
			IdpRFC.LIST,
			"/portal/evaluation/idp/idpAgreementList"),
	IDP_AGREEMENT_VIEW(
			IdpRFC.DETAIL,
			"/portal/evaluation/idp/idpAgreementView"),
	IDP_HSS(
			IdpRFC.LIST,
			"/portal/evaluation/idp/idpHSSList"),
	IDP_HSS_VIEW(
			IdpRFC.DETAIL,
			"/portal/evaluation/idp/idpHSSView"),
	IDP_REMOTE(
			IdpRFC.LIST,
			"/portal/evaluation/idp/idpRomoteList"),
	IDP_REMOTE_VIEW(
			IdpRFC.DETAIL,
			"/portal/evaluation/idp/idpRomoteView"),
	EMPTY(
			IdpRFC.EMPTY,
			"");

	private String view;
	private IdpRFC idpRFC;

	Idp(IdpRFC idpRFC, String view) {
		this.idpRFC = idpRFC;
		this.view = view;
	}

	static public Idp lookup(String id) {
		return LookupUtil.lookup(Idp.class, id);
	}

	public String getView() {
		return this.view;
	}

	public IdpRFC getIdpRFC() {
		return this.idpRFC;
	}

}
