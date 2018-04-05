package com.lgcns.ikep4.portal.evaluation.constant;

import com.lgcns.ikep4.portal.util.LookupUtil;

public enum Promotion {

	GOAL_SETTING_LIST(
			PromotionRFC.LIST,
			"/portal/evaluation/promotion/goalSettingList"),
	GOAL_SETTING_VIEW(
			PromotionRFC.DETAIL,
			"/portal/evaluation/promotion/goalSettingView"),
	GOAL_SETTING_VIEW_PRINT(
			PromotionRFC.DETAIL,
			"/portal/evaluation/promotion/goalSettingViewPrint"),
	EMPTY(
			PromotionRFC.EMPTY,
			"");

	private String view;
	private PromotionRFC promotionRFC;

	Promotion(PromotionRFC promotionRFC, String view) {
		this.promotionRFC = promotionRFC;
		this.view = view;
	}

	static public Promotion lookup(String id) {
		return LookupUtil.lookup(Promotion.class, id);
	}

	public String getView() {
		return this.view;
	}

	public PromotionRFC getPromotionRFC() {
		return this.promotionRFC;
	}

}
