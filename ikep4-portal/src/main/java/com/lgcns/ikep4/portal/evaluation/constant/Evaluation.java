package com.lgcns.ikep4.portal.evaluation.constant;

import com.lgcns.ikep4.portal.util.LookupUtil;

public enum Evaluation {

	OBJECTIVE_SETTING(
			UserRole.APRSE,
			EvaluationRFC.OBJECTIVE_LIST,
			"/portal/evaluation/performance/objectiveSettingList"),
	OBJECTIVE_SETTING_VIEW(
			UserRole.APRSE,
			EvaluationRFC.OBJECTIVE_GOAL_DETAIL,
			"/portal/evaluation/performance/objectiveSettingView"),
	OBJECTIVE_SETTING_VIEW_PRINT(
			UserRole.EMPTY,
			EvaluationRFC.OBJECTIVE_GOAL_PRINT,
			"/portal/evaluation/performance/objectiveSettingViewPrint"),
	OBJECTIVE_AGREEMENT(
			UserRole.APRSR1,
			EvaluationRFC.OBJECTIVE_LIST,
			"/portal/evaluation/performance/objectiveAgreementList"),
	OBJECTIVE_AGREEMENT_VIEW(
			UserRole.APRSR1,
			EvaluationRFC.OBJECTIVE_AGREE_DETAIL,
			"/portal/evaluation/performance/objectiveAgreementView"),
	OBJECTIVE_MIDDLECHECK(
			UserRole.APRSE,
			EvaluationRFC.OBJECTIVE_LIST,
			"/portal/evaluation/performance/objectiveMiddlecheckList"),
	OBJECTIVE_MIDDLECHECK_VIEW(
			UserRole.APRSE,
			EvaluationRFC.OBJECTIVE_CHECK_DETAIL,
			"/portal/evaluation/performance/objectiveMiddlecheckView"),
	OBJECTIVE_FEEDBACK(
			UserRole.APRSR1,
			EvaluationRFC.OBJECTIVE_LIST,
			"/portal/evaluation/performance/objectiveFeedbackList"),
	OBJECTIVE_FEEDBACK_VIEW(
			UserRole.APRSR1,
			EvaluationRFC.OBJECTIVE_FEEDBACK_DETAIL,
			"/portal/evaluation/performance/objectiveFeedbackView"),
	OBJECTIVE_PERFORMANCE(
			UserRole.APRSE,
			EvaluationRFC.OBJECTIVE_LIST,
			"/portal/evaluation/performance/objectivePerformanceList"),
	OBJECTIVE_PERFORMANCE_VIEW(
			UserRole.APRSE,
			EvaluationRFC.OBJECTIVE_PERFORMANCE_DETAIL,
			"/portal/evaluation/performance/objectivePerformanceView"),
	OBJECTIVE_EVALUATION(
			UserRole.APRSR,
			EvaluationRFC.OBJECTIVE_LIST,
			"/portal/evaluation/performance/objectiveEvaluationList"),
	OBJECTIVE_EVALUATION1_VIEW(
			UserRole.APRSR,
			EvaluationRFC.OBJECTIVE_EVALUATION_DETAIL,
			"/portal/evaluation/performance/objectivePrimaryView"),
	OBJECTIVE_EVALUATION2_VIEW(
			UserRole.APRSR,
			EvaluationRFC.OBJECTIVE_EVALUATION_DETAIL,
			"/portal/evaluation/performance/objectiveSecondaryView"),
	OBJECTIVE_REVIEW(
			UserRole.REVWR,
			EvaluationRFC.OBJECTIVE_REVIEW_LIST,
			"/portal/evaluation/performance/objectiveReviewList"),
	OBJECTIVE_REVIEW2_LIST(
			UserRole.REVWR,
			EvaluationRFC.OBJECTIVE_REVIEW2_LIST,
			"/portal/evaluation/performance/objectiveReview2List"),
	OBJECTIVE_REVIEW2(
			UserRole.REVWR,
			EvaluationRFC.OBJECTIVE_REVIEW_LIST,
			"/portal/evaluation/performance/objectiveReview2View"),
	OBJECTIVE_INDIVIDUAL_EVALUATION(
			UserRole.APRSE,
			EvaluationRFC.OBJECTIVE_LIST,
			"/portal/evaluation/performance/popPerformanceIndividualEvaluationList"),
	OBJECTIVE_INDIVIDUAL_EVALUATION_VIEW(
			UserRole.APRSE,
			EvaluationRFC.OBJECTIVE_EVALUATION_DETAIL,
			"/portal/evaluation/performance/popPerformanceIndividualEvaluationView"),
	OBJECTIVE_INDIVIDUAL_EVALUATION_NO_SECONDARY_VIEW(
			UserRole.APRSE,
			EvaluationRFC.OBJECTIVE_EVALUATION_DETAIL,
			"/portal/evaluation/performance/popPerformanceIndividualEvaluationNoSecondaryView"),
	OBJECTIVE_HSS(
			UserRole.EMPTY,
			EvaluationRFC.OBJECTIVE_HSS_LIST,
			"/portal/evaluation/performance/objectiveHSSList"),
	OBJECTIVE_HSS_VIEW(
			UserRole.EMPTY,
			EvaluationRFC.OBJECTIVE_HSS_DETAIL,
			"/portal/evaluation/performance/objectiveHSSView"),
	OBJECTIVE_HSS_VIEW_NO_SECONDARY_VIEW(
			UserRole.EMPTY,
			EvaluationRFC.OBJECTIVE_HSS_DETAIL,
			"/portal/evaluation/performance/objectiveHSSNoSecondaryView"),
	COMPETENCE_SETUP(
			UserRole.APRSE,
			EvaluationRFC.COMPETENCE_LIST,
			"/portal/evaluation/competence/competenceSetupList"),
	COMPETENCE_SETUP_VIEW(
			UserRole.APRSE,
			EvaluationRFC.COMPETENCE_SETUP_DETAIL,
			"/portal/evaluation/competence/competenceSetupView"),
	COMPETENCE_SETUP_VIEW_PRINT(
			UserRole.EMPTY,
			EvaluationRFC.COMPETENCE_SETUP_DETAIL,
			"/portal/evaluation/competence/competenceSetupViewPrint"),
	COMPETENCE_AGREEMENT(
			UserRole.APRSR1,
			EvaluationRFC.COMPETENCE_LIST,
			"/portal/evaluation/competence/competenceAgreementList"),
	COMPETENCE_AGREEMENT_VIEW(
			UserRole.APRSR1,
			EvaluationRFC.COMPETENCE_AGREE_DETAIL,
			"/portal/evaluation/competence/competenceAgreementView"),
	COMPETENCE_EVALUATION(
			UserRole.APRSR,
			EvaluationRFC.COMPETENCE_LIST,
			"/portal/evaluation/competence/competenceEvaluationList"),
	COMPETENCE_EVALUATION1_VIEW(
			UserRole.APRSR,
			EvaluationRFC.COMPETENCE_EVALUATION_DETAIL,
			"/portal/evaluation/competence/competenceEvaluation1View"),
	COMPETENCE_EVALUATION2_VIEW(
			UserRole.APRSR,
			EvaluationRFC.COMPETENCE_EVALUATION_DETAIL,
			"/portal/evaluation/competence/competenceEvaluation2View"),
	COMPETENCE_REVIEW(
			UserRole.REVWR,
			EvaluationRFC.COMPETENCE_REVIEW_LIST,
			"/portal/evaluation/competence/competenceReviewList"),
	COMPETENCE_REVIEW2_LIST(
			UserRole.REVWR,
			EvaluationRFC.COMPETENCE_REVIEW2_LIST,
			"/portal/evaluation/competence/competenceReview2List"),
	COMPETENCE_REVIEW2(
			UserRole.REVWR,
			EvaluationRFC.COMPETENCE_REVIEW_LIST,
			"/portal/evaluation/competence/competenceReview2View"),
	COMPETENCE_INDIVIDUAL_EVALUATION(
			UserRole.APRSE,
			EvaluationRFC.COMPETENCE_LIST,
			"/portal/evaluation/competence/popCompetenceindividualEvaluationList"),
	COMPETENCE_INDIVIDUAL_EVALUATION_VIEW(
			UserRole.APRSE,
			EvaluationRFC.COMPETENCE_EVALUATION_DETAIL,
			"/portal/evaluation/competence/popCompetenceindividualEvaluationView"),
	COMPETENCE_HSS(
			UserRole.EMPTY,
			EvaluationRFC.COMPETENCE_HSS_LIST,
			"/portal/evaluation/competence/competenceHSSList"),
	COMPETENCE_HSS_VIEW(
			UserRole.EMPTY,
			EvaluationRFC.COMPETENCE_EVALUATION_DETAIL,
			"/portal/evaluation/competence/competenceHSSView"),
	COMPETENCE_FEEDBACK_MSS(
			UserRole.EMPTY,
			EvaluationRFC.COMPETENCE_FEEDBACK_LIST,
			"/portal/evaluation/competence/competenceFeedbackMSSList"),
	COMPETENCE_FEEDBACK_MSS_VIEW(
			UserRole.EMPTY,
			EvaluationRFC.COMPETENCE_FEEDBACK_DETAIL,
			"/portal/evaluation/competence/competenceFeedbackMSSView"),
	COMPETENCE_FEEDBACK_ESS(
			UserRole.EMPTY,
			EvaluationRFC.COMPETENCE_FEEDBACK_LIST,
			"/portal/evaluation/competence/competenceFeedbackESSList"),
	COMPETENCE_FEEDBACK_ESS_VIEW(
			UserRole.EMPTY,
			EvaluationRFC.COMPETENCE_FEEDBACK_DETAIL,
			"/portal/evaluation/competence/competenceFeedbackESSView"),
	EMTPY(
			UserRole.EMPTY,
			EvaluationRFC.EMPTY,
			"");

	private String view;
	private UserRole userRole;
	private EvaluationRFC evaluationRFC;

	Evaluation(UserRole userRole, EvaluationRFC evaluationRFC, String view) {
		this.userRole = userRole;
		this.evaluationRFC = evaluationRFC;
		this.view = view;
	}

	static public Evaluation lookup(String id) {
		return LookupUtil.lookup(Evaluation.class, id);
	}

	public String getView() {
		return this.view;
	}

	public UserRole getUserRole() {
		return this.userRole;
	}

	public EvaluationRFC getEvaluationRFC() {
		return this.evaluationRFC;
	}

}
