package com.lgcns.ikep4.portal.evaluation.constant;

import com.lgcns.ikep4.portal.util.LookupUtil;

public enum EvaluationRFC {

	OBJECTIVE_LIST("ZHR_RFC_APR_OBJTV_LST"),
	OBJECTIVE_GOAL_DETAIL("ZHR_RFC_APR_OBJTV_GOAL_DTL"),
	OBJECTIVE_GOAL_PROCESS("ZHR_RFC_APR_OBJTV_GOAL_REQ"),
	OBJECTIVE_GOAL_PRINT("ZHR_RFC_APR_OBJTV_SHEET_PRT"),
	OBJECTIVE_AGREE_DETAIL("ZHR_RFC_APR_OBJTV_AGRE_DTL"),
	OBJECTIVE_AGREE_PROCESS("ZHR_RFC_APR_OBJTV_AGRE_REQ"),
	OBJECTIVE_CHECK_DETAIL("ZHR_RFC_APR_OBJTV_CHEK_DTL"),
	OBJECTIVE_CHECK_PROCESS("ZHR_RFC_APR_OBJTV_CHEK_REQ"),
	OBJECTIVE_FEEDBACK_DETAIL("ZHR_RFC_APR_OBJTV_FEED_DTL"),
	OBJECTIVE_FEEDBACK_PROCESS("ZHR_RFC_APR_OBJTV_FEED_REQ"),
	OBJECTIVE_PERFORMANCE_DETAIL("ZHR_RFC_APR_OBJTV_SELF_DTL"),
	OBJECTIVE_PERFORMANCE_PROCESS("ZHR_RFC_APR_OBJTV_SELF_REQ"),
	OBJECTIVE_EVALUATION_DETAIL("ZHR_RFC_APR_OBJTV_APR_DTL"),
	OBJECTIVE_EVALUATION_PROCESS("ZHR_RFC_APR_OBJTV_APR_REQ"),
	OBJECTIVE_REVIEW_LIST("ZHR_RFC_APR_OBJTV_REVW_LST"),
	OBJECTIVE_REVIEW_PROCESS("ZHR_RFC_APR_OBJTV_REVW_REQ"),
	OBJECTIVE_REVIEW2_LIST("ZHR_RFC_APR_OBJTV_RVW_LST"),
	OBJECTIVE_HSS_LIST("ZHR_RFC_APR_OBJTV_HSS_LST"),
	OBJECTIVE_HSS_DETAIL("ZHR_RFC_APR_OBJTV_HSS_DTL"),
	COMPETENCE_LIST("ZHR_RFC_APR_QUALI_LST"),
	COMPETENCE_SETUP_DETAIL("ZHR_RFC_APR_QUALI_SETUP_DTL"),
	COMPETENCE_SETUP_PROCESS("ZHR_RFC_APR_QUALI_SETUP_REQ"),
	COMPETENCE_AGREE_DETAIL("ZHR_RFC_APR_QUALI_AGRE_DTL"),
	COMPETENCE_AGREE_PROCESS("ZHR_RFC_APR_QUALI_AGRE_REQ"),
	COMPETENCE_EVALUATION_DETAIL("ZHR_RFC_APR_QUALI_APR_DTL"),
	COMPETENCE_EVALUATION_PROCESS("ZHR_RFC_APR_QUALI_APR_REQ"),
	COMPETENCE_REVIEW_LIST("ZHR_RFC_APR_QUALI_REVW_LST"),
	COMPETENCE_REVIEW_PROCESS("ZHR_RFC_APR_QUALI_REVW_REQ"),
	COMPETENCE_REVIEW2_LIST("ZHR_RFC_APR_QUALI_RVW_LST"),
	COMPETENCE_HSS_LIST("ZHR_RFC_APR_QUALI_HSS_LST"),
	COMPETENCE_FEEDBACK_LIST("ZHR_RFC_APR_QUALI_FDB_LST"),
	COMPETENCE_FEEDBACK_DETAIL("ZHR_RFC_APR_QUALI_FDB_DTL"),
	COMPETENCE_FEEDBACK_PROCESS("ZHR_RFC_APR_QUALI_FDB_REQ"),
	EMPTY("");

	private String rfc;

	static public EvaluationRFC lookup(String id) {
		return LookupUtil.lookup(EvaluationRFC.class, id);
	}

	EvaluationRFC(String rfc) {
		this.rfc = rfc;
	}

	public String getRfc() {
		return this.rfc;
	}

}
