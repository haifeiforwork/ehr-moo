package com.lgcns.ikep4.portal.evaluation.constant;

import com.lgcns.ikep4.portal.util.LookupUtil;

public enum Position {

	CHANGE_LIST(
			PositionRFC.LIST,
			"/portal/evaluation/position/positionList"),
	APPOINT_LIST(
			PositionRFC.APPOINT,
			"/portal/evaluation/position/popupAppointList"),
	EMPTY(
			PositionRFC.EMPTY,
			"");

	private String view;
	private PositionRFC positionRFC;

	Position(PositionRFC positionRFC, String view) {
		this.positionRFC = positionRFC;
		this.view = view;
	}

	static public Position lookup(String id) {
		return LookupUtil.lookup(Position.class, id);
	}

	public String getView() {
		return this.view;
	}

	public PositionRFC getPositionRFC() {
		return this.positionRFC;
	}

}
