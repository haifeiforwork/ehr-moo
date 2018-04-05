package com.lgcns.ikep4.portal.evaluation.web.performance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.portal.evaluation.constant.Evaluation;
import com.lgcns.ikep4.portal.evaluation.constant.EvaluationExport;
import com.lgcns.ikep4.portal.evaluation.constant.EvaluationImport;
import com.lgcns.ikep4.portal.evaluation.constant.EvaluationKeySet;
import com.lgcns.ikep4.portal.evaluation.constant.EvaluationRFC;
import com.lgcns.ikep4.portal.evaluation.model.CompetenceFeedback;
import com.lgcns.ikep4.portal.evaluation.model.CompetenceFeedbackFooter;
import com.lgcns.ikep4.portal.evaluation.model.CompetenceGoal;
import com.lgcns.ikep4.portal.evaluation.model.CompetenceReview;
import com.lgcns.ikep4.portal.moorimmss.model.MssTeamTreeSpecial;
import com.lgcns.ikep4.portal.moorimmss.service.MssTeamTreeSpecialService;
import com.lgcns.ikep4.portal.util.PagingUtils;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.group.model.Group;
import com.lgcns.ikep4.support.user.group.service.GroupService;
import com.lgcns.ikep4.support.user.member.dao.UserDao;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.service.UserService;
import com.lgcns.ikep4.util.jco.WebESSRcvRFC;
import com.lgcns.ikep4.util.lang.StringUtil;

@Controller
@RequestMapping(value="/portal/evaluation/competence")
public class CompetenceEvaluationController extends BaseController {

	@Autowired
	private ACLService aclService;

	@Autowired
	private GroupService groupService;

	@Autowired
	private UserService userService;

	@Autowired
	private MssTeamTreeSpecialService mssTeamTreeSpecialService;

	@Autowired
	private WebESSRcvRFC webEssRcv;

	@Autowired
	private UserDao userDao;

	//Log
	protected final Log logger = LogFactory.getLog(this.getClass());

	public boolean isSystemAdmin(User user) {

		return this.aclService.isSystemAdmin("MoorimMss", user.getUserId());

	}

	/**
	 * 역량 목록 - 공통
	 *
	 * @param params
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/competenceList.do")
	public ModelAndView competenceList(@RequestParam HashMap<String, Object> params, HttpServletRequest request) throws Exception {

		String action = (String) params.get("action");
		Evaluation evaluation = Evaluation.lookup(action.toUpperCase());

		ModelAndView mav = new ModelAndView(evaluation.getView());

		User user = (User) getRequestAttribute("ikep.user");

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		rfcParam.put(EvaluationImport.IV_PERNR.name(), user.getEmpNo());
		rfcParam.put(EvaluationImport.IV_PERFM.name(), evaluation.getUserRole().getCode());
		//RFC PARAMETER SETTING END

		Map<?, ?> export = webEssRcv.callRfcFunction(evaluation.getEvaluationRFC().getRfc(), rfcParam, null, request);

		Map<?, ?> esReturn = (Map<?, ?>) export.get(EvaluationExport.ES_RETURN.name());
		ArrayList<Map<?, ?>> tempList = (ArrayList<Map<?, ?>>) export.get(EvaluationExport.ET_LIST.name());

		ArrayList<Map<?, ?>> etList = PagingUtils.setPagingParam(tempList, params);

		mav.addObject("esReturn", esReturn);
		mav.addObject("etList", etList);
		mav.addObject("params", params);

		return mav;
	}

	/**
	 * 역량 상세 유효성체크 - 공통
	 *
	 * @param params
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/competenceCheckView.do")
	public @ResponseBody Map<?, ?> competenceCheckView(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		Map<?, ?> result = null;
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> apprMap = new HashMap<String, Object>();

		try {

			//RFC PARAMETER SETTING START
			for(String apprKey : EvaluationKeySet.COMPETENCE.getKeys()){
				apprMap.put(apprKey, params.get(apprKey));
			}
			rfcParam.put(EvaluationImport.IS_APPR.name(), apprMap);
			//RFC PARAMETER SETTING END

			EvaluationRFC evaluationRFC = EvaluationRFC.lookup((String)params.get("rfc"));
			Map<?, ?> export = webEssRcv.callRfcFunction(evaluationRFC.getRfc(), rfcParam, null, request);

			result = (Map<?, ?>) export.get(EvaluationExport.ES_RETURN.name());

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new IKEP4AjaxException("", ex);
		}

		return result;

	}

	/**
	 * 역량 - 직무역량 선정 상세
	 *
	 * @param params
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/competenceSetupView.do")
	public ModelAndView competenceSetupView(@RequestParam HashMap<String, Object> params, HttpServletRequest request) throws Exception {

		ModelAndView mav = new ModelAndView(Evaluation.COMPETENCE_SETUP_VIEW.getView());

		User user = (User) getRequestAttribute("ikep.user");

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> apprMap = new HashMap<String, Object>();;

		for(String apprKey : EvaluationKeySet.COMPETENCE.getKeys()){
			apprMap.put(apprKey, params.get(apprKey));
		}

		rfcParam.put(EvaluationImport.IS_APPR.name(), apprMap);
		rfcParam.put(EvaluationImport.IV_PERNR.name(), user.getEmpNo());
		//RFC PARAMETER SETTING END

		Map<?, ?> export = webEssRcv.callRfcFunction(EvaluationRFC.COMPETENCE_SETUP_DETAIL.getRfc(), rfcParam, null, request);

		ArrayList<Map<?, ?>> commonList = (ArrayList<Map<?, ?>>) export.get(EvaluationExport.ET_DETAIL1.name());
		ArrayList<Map<?, ?>> stellList = (ArrayList<Map<?, ?>>) export.get(EvaluationExport.ET_DETAIL2.name());
		ArrayList<Map<?, ?>> qitemList = (ArrayList<Map<?, ?>>) export.get(EvaluationExport.F4_QUALI.name());

		mav.addObject("commonList", commonList);
		mav.addObject("stellList", stellList);
		mav.addObject("qitemList", qitemList);
		mav.addObject("params", params);

		return mav;

	}

	/**
	 * 역량 - 직무역량 선정 상세 출력
	 *
	 * @param params
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/competenceSetupViewPrint.do")
	public ModelAndView competenceSetupViewPrint(@RequestParam HashMap<String, Object> params, HttpServletRequest request) throws Exception {

		Evaluation evaluation = Evaluation.COMPETENCE_SETUP_VIEW_PRINT;
		ModelAndView mav = new ModelAndView(evaluation.getView());

		User user = (User) getRequestAttribute("ikep.user");

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> apprMap = new HashMap<String, Object>();;

		for(String apprKey : EvaluationKeySet.COMPETENCE.getKeys()){
			apprMap.put(apprKey, params.get(apprKey));
		}

		rfcParam.put(EvaluationImport.IV_PERNR.name(), user.getEmpNo());
		rfcParam.put(EvaluationImport.IS_APPR.name(), apprMap);
		//RFC PARAMETER SETTING END

		Map<?, ?> export = webEssRcv.callRfcFunction(evaluation.getEvaluationRFC().getRfc(), rfcParam, null, request);

		Map<?, ?> esHeader = (Map<?, ?>) export.get(EvaluationExport.ES_HEADER.name());
		ArrayList<Map<?, ?>> commonList = (ArrayList<Map<?, ?>>) export.get(EvaluationExport.ET_DETAIL1.name());
		ArrayList<Map<?, ?>> stellList = (ArrayList<Map<?, ?>>) export.get(EvaluationExport.ET_DETAIL2.name());

		mav.addObject("esHeader", esHeader);
		mav.addObject("commonList", commonList);
		mav.addObject("stellList", stellList);
		mav.addObject("params", params);

		return mav;

	}

	/**
	 * 역량 - 직무역량 선정 저장
	 *
	 * @param goals
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/competenceSetupProc.do")
	public @ResponseBody Map<?, ?> competenceSetupProc(CompetenceGoal goals, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");

		Map<?, ?> result = null;
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		List<HashMap<String, Object>> detailList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> detail = null;

		try {

			for(CompetenceGoal goal : goals.getGoals()) {
				detail = new HashMap<String, Object>();
				detail.put("AYEAR", goals.getAyear());
				detail.put("APRSE", goals.getAprse());
				detail.put("SEQNO", goals.getSeqno());
				detail.put("QITEM", goal.getQitem());
				detail.put("STEXT", goal.getStext());
				detail.put("DFTXT", goal.getDftxt());
				detail.put("BHIDX", goal.getBhidx());
				detailList.add(detail);
			}

			rfcParam.put(EvaluationImport.IV_BUTTON.name(), goals.getButton());
			rfcParam.put(EvaluationImport.IV_PERNR.name(), user.getEmpNo());
			rfcParam.put(EvaluationImport.IT_DETAIL.name(), detailList);

			result = webEssRcv.callRfcFunction(EvaluationRFC.COMPETENCE_SETUP_PROCESS.getRfc(), rfcParam, null, request);

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new IKEP4AjaxException("", ex);
		}

		return result;
	}

	/**
	 * 역량 - 직무역량 합의 상세
	 *
	 * @param params
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/competenceAgreementView.do")
	public ModelAndView competenceAgreementView(@RequestParam HashMap<String, Object> params, HttpServletRequest request) throws Exception {

		ModelAndView mav = new ModelAndView(Evaluation.COMPETENCE_AGREEMENT_VIEW.getView());

		User user = (User) getRequestAttribute("ikep.user");

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> apprMap = new HashMap<String, Object>();;

		for(String apprKey : EvaluationKeySet.COMPETENCE.getKeys()){
			apprMap.put(apprKey, params.get(apprKey));
		}

		rfcParam.put(EvaluationImport.IV_PERNR.name(), user.getEmpNo());
		rfcParam.put(EvaluationImport.IS_APPR.name(), apprMap);
		//RFC PARAMETER SETTING END

		Map<?, ?> export = webEssRcv.callRfcFunction(EvaluationRFC.COMPETENCE_AGREE_DETAIL.getRfc(), rfcParam, null, request);

		Map<?, ?> esHeader = (Map<?, ?>) export.get(EvaluationExport.ES_HEADER.name());
		ArrayList<Map<?, ?>> commonList = (ArrayList<Map<?, ?>>) export.get(EvaluationExport.ET_DETAIL1.name());
		ArrayList<Map<?, ?>> stellList = (ArrayList<Map<?, ?>>) export.get(EvaluationExport.ET_DETAIL2.name());

		mav.addObject("esHeader", esHeader);
		mav.addObject("commonList", commonList);
		mav.addObject("stellList", stellList);
		mav.addObject("params", params);

		return mav;

	}

	/**
	 * 역량 - 직무역량 합의 저장
	 *
	 * @param goals
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/competenceAgreementProc.do")
	public @ResponseBody Map<?, ?> competenceAgreementProc(CompetenceGoal goals, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");

		Map<?, ?> result = null;
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		List<HashMap<String, Object>> detailList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> detail = null;

		try {

			for(CompetenceGoal goal : goals.getGoals()) {
				detail = new HashMap<String, Object>();
				detail.put("AYEAR", goals.getAyear());
				detail.put("APRSE", goals.getAprse());
				detail.put("SEQNO", goals.getSeqno());
				detail.put("QITEM", goal.getQitem());
				detail.put("STEXT", goal.getStext());
				detail.put("DFTXT", goal.getDftxt());
				detail.put("BHIDX", goal.getBhidx());
				detailList.add(detail);
			}

			rfcParam.put(EvaluationImport.IV_BUTTON.name(), goals.getButton());
			rfcParam.put(EvaluationImport.IV_RJTEXT.name(), goals.getRjtext());
			rfcParam.put(EvaluationImport.IV_PERNR.name(), user.getEmpNo());
			rfcParam.put(EvaluationImport.IT_DETAIL.name(), detailList);

			result = webEssRcv.callRfcFunction(EvaluationRFC.COMPETENCE_AGREE_PROCESS.getRfc(), rfcParam, null, request);

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new IKEP4AjaxException("", ex);
		}

		return result;
	}

	/**
	 * 역량 - 평가자 평가 상세
	 *
	 * @param params
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/competenceEvaluationView.do")
	public ModelAndView competenceEvaluation1View(@RequestParam HashMap<String, Object> params, HttpServletRequest request) throws Exception {

		ModelAndView mav = new ModelAndView();

		User user = (User) getRequestAttribute("ikep.user");

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> apprMap = new HashMap<String, Object>();;

		for(String apprKey : EvaluationKeySet.COMPETENCE.getKeys()) {
			apprMap.put(apprKey, params.get(apprKey));
		}

		rfcParam.put(EvaluationImport.IV_PERNR.name(), user.getEmpNo());
		rfcParam.put(EvaluationImport.IS_APPR.name(), apprMap);
		//RFC PARAMETER SETTING END

		Map<?, ?> export = webEssRcv.callRfcFunction(EvaluationRFC.COMPETENCE_EVALUATION_DETAIL.getRfc(), rfcParam, null, request);

		String evQunam1 = (String) export.get(EvaluationExport.EV_QUNAM1.name());
		String evQunam2 = (String) export.get(EvaluationExport.EV_QUNAM2.name());
		String evAprser = (String) export.get(EvaluationExport.EV_APRSER.name());
		Map<?, ?> etHeader = (Map<?, ?>) export.get(EvaluationExport.ES_HEADER.name());
		ArrayList<Map<?, ?>> commonList = (ArrayList<Map<?, ?>>) export.get(EvaluationExport.ET_DETAIL1.name());
		ArrayList<Map<?, ?>> stellList = (ArrayList<Map<?, ?>>) export.get(EvaluationExport.ET_DETAIL2.name());
		ArrayList<Map<?, ?>> scoreList = (ArrayList<Map<?, ?>>) export.get(EvaluationExport.ET_SCORE.name());

		mav.addObject("evQunam1", evQunam1);
		mav.addObject("evQunam2", evQunam2);
		mav.addObject("evAprser", evAprser);
		mav.addObject("etHeader", etHeader);
		mav.addObject("commonList", commonList);
		mav.addObject("stellList", stellList);
		mav.addObject("scoreList", scoreList);
		mav.addObject("params", params);

		StringBuffer viewName = new StringBuffer();
		viewName
			.append("COMPETENCE_EVALUATION")
			.append(evAprser)
			.append("_VIEW");

		mav.setViewName(Evaluation.lookup(viewName.toString()).getView());

		return mav;

	}

	/**
	 * 역량 - 평가자 평가 저장
	 *
	 * @param goals
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/competenceEvaluationProc.do")
	public @ResponseBody Map<?, ?> competenceEvaluationProc(CompetenceGoal goals, HttpServletRequest request) {

		Map<?, ?> result = null;
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		User user = (User) getRequestAttribute("ikep.user");

		List<HashMap<String, Object>> detailList1 = new ArrayList<HashMap<String, Object>>();
		List<HashMap<String, Object>> detailList2 = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> detail = null;

		try {

			for(CompetenceGoal goal : goals.getCgoals()) {
				detail = new HashMap<String, Object>();
				detail.put("AYEAR", goals.getAyear());
				detail.put("APRSE", goals.getAprse());
				detail.put("SEQNO", goals.getSeqno());
				detail.put("QITEM", goal.getQitem());
				detail.put("STEXT", goal.getStext());
				detail.put("DFTXT", goal.getDftxt());
				detail.put("BHIDX", goal.getBhidx());
				detail.put("APGRD1", goal.getApgrd1());
				detail.put("APSCO1", goal.getApsco1());
				detail.put("APGRD2", goal.getApgrd2());
				detail.put("APSCO2", goal.getApsco2());
				detail.put("PTSUM", goal.getPtsum());

				detailList1.add(detail);
			}

			for(CompetenceGoal goal : goals.getSgoals()) {
				detail = new HashMap<String, Object>();
				detail.put("AYEAR", goals.getAyear());
				detail.put("APRSE", goals.getAprse());
				detail.put("SEQNO", goals.getSeqno());
				detail.put("QITEM", goal.getQitem());
				detail.put("STEXT", goal.getStext());
				detail.put("DFTXT", goal.getDftxt());
				detail.put("BHIDX", goal.getBhidx());
				detail.put("APGRD1", goal.getApgrd1());
				detail.put("APSCO1", goal.getApsco1());
				detail.put("APGRD2", goal.getApgrd2());
				detail.put("APSCO2", goal.getApsco2());
				detail.put("PTSUM", goal.getPtsum());

				detailList2.add(detail);
			}

			rfcParam.put(EvaluationImport.IV_BUTTON.name(), goals.getButton());
			rfcParam.put(EvaluationImport.IV_PERNR.name(), user.getEmpNo());
			rfcParam.put(EvaluationImport.IT_DETAIL1.name(), detailList1);
			rfcParam.put(EvaluationImport.IT_DETAIL2.name(), detailList2);

			result = webEssRcv.callRfcFunction(EvaluationRFC.COMPETENCE_EVALUATION_PROCESS.getRfc(), rfcParam, null, request);

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new IKEP4AjaxException("", ex);
		}

		return result;
	}

	/**
	 * 역량 - 리뷰 리스트
	 *
	 * @param params
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/competenceReviewList.do")
	public ModelAndView competenceReviewList(@RequestParam HashMap<String, Object> params, HttpServletRequest request) throws Exception {

		Evaluation evaulation = Evaluation.COMPETENCE_REVIEW;
		ModelAndView mav = new ModelAndView(evaulation.getView());

		User user = (User) getRequestAttribute("ikep.user");

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		rfcParam.put(EvaluationImport.IV_REVWR.name(), user.getEmpNo());
		//RFC PARAMETER SETTING END

		Map<?, ?> export = webEssRcv.callRfcFunction(evaulation.getEvaluationRFC().getRfc(), rfcParam, null, request);

		String evRvsts = (String) export.get(EvaluationExport.EV_RVSTS.name());
		String evRvstsx = (String) export.get(EvaluationExport.EV_RVSTSX.name());
		Object evMrevw = export.get(EvaluationExport.EV_MREVW.name());
		Map<?, ?> esReturn = (Map<?, ?>) export.get(EvaluationExport.ES_RETURN.name());
		ArrayList<Map<?, ?>> etList = (ArrayList<Map<?, ?>>) export.get(EvaluationExport.ET_LIST.name());

		mav.addObject("evRvsts", evRvsts);
		mav.addObject("evRvstsx", evRvstsx);
		mav.addObject("evMrevw", evMrevw);
		mav.addObject("esReturn", esReturn);
		mav.addObject("etList", etList);
		mav.addObject("params", params);

		return mav;
	}

	/**
	 * 역량 - 리뷰 저장
	 *
	 * @param reviews
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/competenceReviewProc.do")
	public @ResponseBody Map<?, ?> competenceReviewProc(CompetenceReview reviews, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");

		Map<?, ?> result = null;
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		List<HashMap<String, Object>> detailList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> detail = null;

		try {

			for(CompetenceReview review : reviews.getReviews()) {
				detail = new HashMap<String, Object>();
				detail.put("AYEAR", review.getAyear());
				detail.put("ORGEH", review.getOrgeh());
				detail.put("ORGTX", review.getOrgtx());
				detail.put("APRSE", review.getAprse());
				detail.put("APRSENM", review.getAprsenm());
				detail.put("TRFGR", review.getTrfgr());
				detail.put("ADJSC", review.getAdjsc());
				detail.put("CMQSC", review.getCmqsc());
				detail.put("FNSCO", review.getFnsco());
				detail.put("RVSC1", review.getRvsc1());
				detail.put("RVSC2", review.getRvsc2());
				detail.put("RVWSC", review.getRvwsc());
				detail.put("REVWR", review.getRevwr());
				detail.put("REVWRNM", review.getRevwrnm());
				detail.put("RVSTS", review.getRvsts());
				detail.put("RVSTSX", review.getRvstsx());
				detail.put("SRTKY", review.getSrtky());
				detailList.add(detail);
			}

			rfcParam.put(EvaluationImport.IV_BUTTON.name(), reviews.getButton());
			rfcParam.put(EvaluationImport.IV_PERNR.name(), user.getEmpNo());
			rfcParam.put(EvaluationImport.IT_LIST.name(), detailList);

			result = webEssRcv.callRfcFunction(EvaluationRFC.COMPETENCE_REVIEW_PROCESS.getRfc(), rfcParam, null, request);

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new IKEP4AjaxException("", ex);
		}

		return result;

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/competenceReview2List.do")
	public ModelAndView competenceReview2List(@RequestParam HashMap<String, Object> params, HttpServletRequest request) throws Exception {

		Evaluation evaluation = Evaluation.COMPETENCE_REVIEW2_LIST;
		ModelAndView mav = new ModelAndView(evaluation.getView());

		User user = (User) getRequestAttribute("ikep.user");

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		rfcParam.put(EvaluationImport.IV_REVWR2.name(), user.getEmpNo());
		//RFC PARAMETER SETTING END

		Map<?, ?> export = webEssRcv.callRfcFunction(evaluation.getEvaluationRFC().getRfc(), rfcParam, null, request);

		Map<?, ?> esReturn = (Map<?, ?>) export.get(EvaluationExport.ES_RETURN.name());
		ArrayList<Map<?, ?>> tempList = (ArrayList<Map<?, ?>>) export.get(EvaluationExport.ET_LIST.name());

		ArrayList<Map<?, ?>> etList = PagingUtils.setPagingParam(tempList, params);

		mav.addObject("esReturn", esReturn);
		mav.addObject("etList", etList);
		mav.addObject("params", params);

		return mav;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/competenceReview2View.do")
	public ModelAndView competenceReview2View(@RequestParam HashMap<String, Object> params, HttpServletRequest request) throws Exception {

		Evaluation evaluation = Evaluation.COMPETENCE_REVIEW2;
		ModelAndView mav = new ModelAndView(evaluation.getView());

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		rfcParam.put(EvaluationImport.IV_REVWR.name(), params.get("REVWR"));
		rfcParam.put(EvaluationImport.IV_GUBUN.name(), "2");
		//RFC PARAMETER SETTING END

		Map<?, ?> export = webEssRcv.callRfcFunction(evaluation.getEvaluationRFC().getRfc(), rfcParam, null, request);

		String evRvsts = (String) export.get(EvaluationExport.EV_RVSTS.name());
		String evRvstsx = (String) export.get(EvaluationExport.EV_RVSTSX.name());
		Object evMrevw = export.get(EvaluationExport.EV_MREVW.name());
		Map<?, ?> esReturn = (Map<?, ?>) export.get(EvaluationExport.ES_RETURN.name());
		ArrayList<Map<?, ?>> etList = (ArrayList<Map<?, ?>>) export.get(EvaluationExport.ET_LIST.name());

		mav.addObject("evRvsts", evRvsts);
		mav.addObject("evRvstsx", evRvstsx);
		mav.addObject("evMrevw", evMrevw);
		mav.addObject("esReturn", esReturn);
		mav.addObject("etList", etList);
		mav.addObject("params", params);

		return mav;
	}

	@RequestMapping(value = "/competenceReview2Proc.do")
	public @ResponseBody Map<?, ?> competenceReview2Proc(CompetenceReview reviews, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");

		Map<?, ?> result = null;
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		List<HashMap<String, Object>> detailList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> detail = null;

		try {

			//RFC PARAMETER SETTING START
			String methodName = "";

			for(CompetenceReview competenceReview : reviews.getReviews()) {
				detail = new HashMap<String, Object>();
				for(String apprKey : EvaluationKeySet.COMPETENCE_REVIEW.getKeys()) {
					methodName = apprKey.replace(apprKey.substring(1), apprKey.substring(1).toLowerCase());
					detail.put(apprKey, competenceReview.getClass().getDeclaredMethod("get" + methodName).invoke(competenceReview));
				}
				detailList.add(detail);
			}

			rfcParam.put(EvaluationImport.IV_BUTTON.name(), reviews.getButton());
			rfcParam.put(EvaluationImport.IV_GUBUN.name(), "2");
			rfcParam.put(EvaluationImport.IV_PERNR.name(), user.getEmpNo());
			rfcParam.put(EvaluationImport.IT_LIST.name(), detailList);

			result = webEssRcv.callRfcFunction(EvaluationRFC.COMPETENCE_REVIEW_PROCESS.getRfc(), rfcParam, null, request);

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new IKEP4AjaxException("", ex);
		}

		return result;

	}

	/**
	 * 역량 - 피평가자 평가 목록(POPUP)
	 *
	 * @param empno
	 * @param params
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/popCompetenceindividualEvaluationList.do")
	public ModelAndView popCompetenceindividualEvaluationList(@RequestParam(value="empno", required=true) String empno, @RequestParam HashMap<String, Object> params, HttpServletRequest request) throws Exception {

		Evaluation evaluation = Evaluation.COMPETENCE_INDIVIDUAL_EVALUATION;
		ModelAndView mav = new ModelAndView(evaluation.getView());

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		rfcParam.put(EvaluationImport.IV_PERNR.name(), empno);
		rfcParam.put(EvaluationImport.IV_REVIW.name(), "X");
		rfcParam.put(EvaluationImport.IV_PERFM.name(), evaluation.getUserRole().getCode());
		//RFC PARAMETER SETTING END

		Map<?, ?> export = webEssRcv.callRfcFunction(evaluation.getEvaluationRFC().getRfc(), rfcParam, null, request);

		ArrayList<Map<?, ?>> etList = (ArrayList<Map<?, ?>>) export.get(EvaluationExport.ET_LIST.name());

		mav.addObject("etList", etList);
		mav.addObject("params", params);

		return mav;
	}

	/**
	 * 역량 - 피평가자 평가 상세(POPUP)
	 *
	 * @param params
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/popCompetenceindividualEvaluationView.do")
	public ModelAndView popCompetenceindividualEvaluationView(@RequestParam HashMap<String, Object> params, HttpServletRequest request) throws Exception {

		Evaluation evaulation = Evaluation.COMPETENCE_INDIVIDUAL_EVALUATION_VIEW;
		ModelAndView mav = new ModelAndView(evaulation.getView());

		User user = (User) getRequestAttribute("ikep.user");

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> apprMap = new HashMap<String, Object>();

		for(String apprKey : EvaluationKeySet.COMPETENCE.getKeys()) {
			apprMap.put(apprKey, params.get(apprKey));
		}

		rfcParam.put(EvaluationImport.IS_APPR.name(), apprMap);
		rfcParam.put(EvaluationImport.IV_PERNR.name(), user.getEmpNo());
		//RFC PARAMETER SETTING END

		Map<?, ?> export = webEssRcv.callRfcFunction(evaulation.getEvaluationRFC().getRfc(), rfcParam, null, request);

		String evQunam1 = (String) export.get(EvaluationExport.EV_QUNAM1.name());
		String evQunam2 = (String) export.get(EvaluationExport.EV_QUNAM2.name());
		Map<?, ?> etHeader = (Map<?, ?>) export.get(EvaluationExport.ES_HEADER.name());
		ArrayList<Map<?, ?>> commonList = (ArrayList<Map<?, ?>>) export.get(EvaluationExport.ET_DETAIL1.name());
		ArrayList<Map<?, ?>> stellList = (ArrayList<Map<?, ?>>) export.get(EvaluationExport.ET_DETAIL2.name());

		mav.addObject("evQunam1", evQunam1);
		mav.addObject("evQunam2", evQunam2);
		mav.addObject("etHeader", etHeader);
		mav.addObject("commonList", commonList);
		mav.addObject("stellList", stellList);
		mav.addObject("params", params);

		return mav;

	}

	/**
	 * 역량 - 평가표조회(HSS)
	 *
	 * @param params
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/competenceHSS.do")
	public ModelAndView competenceHSS(@RequestParam HashMap<String, Object> params, HttpServletRequest request) throws Exception {

		Evaluation evaulation = Evaluation.COMPETENCE_HSS;
		ModelAndView mav = new ModelAndView(evaulation.getView());

		User user = (User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isSystemAdmin(user);

		/***********************************
		 * Teamview
		 **********************************/
		String searchRootGroupId="";
		List<Map<String, Object>> list = null;

		if(isSystemAdmin){
			searchRootGroupId="";//전체인원
			list = getOrgGroupAndUser(searchRootGroupId, "", "USER");//내 부서 인원만.
		}else{
			List<MssTeamTreeSpecial> specialList = mssTeamTreeSpecialService.getList(user.getUserId());
			if(specialList!=null && specialList.size()>0){
				list = getOrgGroupAndUser3(specialList, "", "USER");//팀뷰어 예외자
			}else{
				searchRootGroupId=user.getGroupId();
				if("22".equals(user.getJobDutyCode())){//반장이면
					list = getOrgGroupAndUser2(searchRootGroupId, "", "USER");//소속 인원만.
				}else{
					list = getOrgGroupAndUser(searchRootGroupId, "", "USER");//내 부서 인원만.
				}
			}
		}

		try {

			ObjectMapper objectMapper = new ObjectMapper();
			mav.addObject("deptItems", objectMapper.writeValueAsString(list));

		} catch (Exception e) {
			throw new IKEP4AjaxException("", e);
		}

		mav.addObject("params", params);

		return mav;

	}

	/**
	 * 역량 - 평가표조회(HSS) - 목록
	 *
	 * @param params
	 * @param request
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/competenceHSSList.do")
	public @ResponseBody Map<?, ?> competenceHSSList(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		Map<?, ?> result = null;
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		try {

			String empNo = (String) params.get("emp_no");

			//RFC PARAMETER SETTING START
			rfcParam.put(EvaluationImport.IV_PERNR.name(), empNo);
			//RFC PARAMETER SETTING END

			result = webEssRcv.callRfcFunction(EvaluationRFC.COMPETENCE_HSS_LIST.getRfc(), rfcParam, null, request);

			Map userInfo = (HashMap<String, String>) this.userDao.empNoToUserId(empNo);
			result.putAll(userInfo);

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new IKEP4AjaxException("", ex);
		}

		return result;

	}

	/**
	 * 역량 - 평가표조회(HSS) - 상세
	 *
	 * @param params
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/competenceHSSView.do")
	public ModelAndView competenceHSSView(@RequestParam HashMap<String, Object> params, HttpServletRequest request) throws Exception {

		Evaluation evaulation = Evaluation.COMPETENCE_HSS_VIEW;
		ModelAndView mav = new ModelAndView(evaulation.getView());

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> apprMap = new HashMap<String, Object>();

		for(String apprKey : EvaluationKeySet.COMPETENCE.getKeys()) {
			apprMap.put(apprKey, params.get(apprKey));
		}

		rfcParam.put(EvaluationImport.IS_APPR.name(), apprMap);
		rfcParam.put(EvaluationImport.IV_PERNR.name(), params.get("APRSR1"));
		//RFC PARAMETER SETTING END

		Map<?, ?> export = webEssRcv.callRfcFunction(evaulation.getEvaluationRFC().getRfc(), rfcParam, null, request);

		String evQunam1 = (String) export.get(EvaluationExport.EV_QUNAM1.name());
		String evQunam2 = (String) export.get(EvaluationExport.EV_QUNAM2.name());
		ArrayList<Map<?, ?>> commonList = (ArrayList<Map<?, ?>>) export.get(EvaluationExport.ET_DETAIL1.name());
		ArrayList<Map<?, ?>> stellList = (ArrayList<Map<?, ?>>) export.get(EvaluationExport.ET_DETAIL2.name());

		mav.addObject("evQunam1", evQunam1);
		mav.addObject("evQunam2", evQunam2);
		mav.addObject("commonList", commonList);
		mav.addObject("stellList", stellList);
		mav.addObject("params", params);

		return mav;

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/competenceFeedBackList.do")
	public ModelAndView competenceFeedBackList(@RequestParam HashMap<String, Object> params, HttpServletRequest request) throws Exception {

		Evaluation evaluation = Evaluation.lookup((String) params.get("action"));

		ModelAndView mav = new ModelAndView(evaluation.getView());

		User user = (User) getRequestAttribute("ikep.user");

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		rfcParam.put(EvaluationImport.IV_PERNR.name(), user.getEmpNo());
		switch (evaluation) {
			case COMPETENCE_FEEDBACK_MSS:
				rfcParam.put(EvaluationImport.IV_GUBUN.name(), "M");
				break;
			case COMPETENCE_FEEDBACK_ESS:
				rfcParam.put(EvaluationImport.IV_GUBUN.name(), "E");
				break;
			default:
				break;
		}
		//RFC PARAMETER SETTING END

		Map<?, ?> export = webEssRcv.callRfcFunction(evaluation.getEvaluationRFC().getRfc(), rfcParam, null, request);

		String evAyear = (String) export.get(EvaluationExport.EV_AYEAR.name());
		Map<?, ?> esReturn = (Map<?, ?>) export.get(EvaluationExport.ES_RETURN.name());
		ArrayList<Map<?, ?>> tempList = (ArrayList<Map<?, ?>>) export.get(EvaluationExport.ET_LIST.name());

		ArrayList<Map<?, ?>> etList = PagingUtils.setPagingParam(tempList, params);

		mav.addObject("evAyear", evAyear);
		mav.addObject("esReturn", esReturn);
		mav.addObject("etList", etList);
		mav.addObject("params", params);

		return mav;
	}

	@RequestMapping(value = "/competenceFeedBackCheckView.do")
	public @ResponseBody Map<?, ?> competenceFeedBackCheckView(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		Map<?, ?> result = null;
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> apprMap = new HashMap<String, Object>();

		try {

			//RFC PARAMETER SETTING START
			for(String apprKey : EvaluationKeySet.COMPETENCE_FEEDBACK.getKeys()){
				apprMap.put(apprKey, params.get(apprKey));
			}
			rfcParam.put(EvaluationImport.IS_APPR.name(), apprMap);
			//RFC PARAMETER SETTING END

			Map<?, ?> export = webEssRcv.callRfcFunction(EvaluationRFC.COMPETENCE_FEEDBACK_DETAIL.getRfc(), rfcParam, null, request);

			result = (Map<?, ?>) export.get(EvaluationExport.ES_RETURN.name());

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new IKEP4AjaxException("", ex);
		}

		return result;

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/competenceFeedBackView.do")
	public ModelAndView competenceFeedBackView(@RequestParam HashMap<String, Object> params, HttpServletRequest request) throws Exception {

		Evaluation evaluation = Evaluation.lookup((String) params.get("action"));
		ModelAndView mav = new ModelAndView(evaluation.getView());

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> apprMap = new HashMap<String, Object>();;

		for(String apprKey : EvaluationKeySet.COMPETENCE_FEEDBACK.getKeys()){
			apprMap.put(apprKey, params.get(apprKey));
		}

		rfcParam.put(EvaluationImport.IS_APPR.name(), apprMap);
		//RFC PARAMETER SETTING END

		Map<?, ?> export = webEssRcv.callRfcFunction(evaluation.getEvaluationRFC().getRfc(), rfcParam, null, request);

		Map<?, ?> esHeader = (Map<?, ?>) export.get(EvaluationExport.ES_HEADER.name());
		ArrayList<Map<?, ?>> etDetail = (ArrayList<Map<?, ?>>) export.get(EvaluationExport.ET_DETAIL.name());
		ArrayList<Map<?, ?>> etFooter = (ArrayList<Map<?, ?>>) export.get(EvaluationExport.ET_FOOTER.name());

		mav.addObject("esHeader", esHeader);
		mav.addObject("etDetail", etDetail);
		mav.addObject("etFooter", etFooter);
		mav.addObject("params", params);

		return mav;

	}

	@RequestMapping(value = "/competenceFeedbackProc.do")
	public @ResponseBody Map<?, ?> competenceFeedbackProc(CompetenceFeedback feedback, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");

		Map<?, ?> result = null;
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		HashMap<String, Object> apprMap = new HashMap<String, Object>();
		List<HashMap<String, Object>> footerList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> foot = null;

		try {

			String methodName = "";

			for(String apprKey : EvaluationKeySet.COMPETENCE_FEEDBACK.getKeys()) {
				methodName = apprKey.replace(apprKey.substring(1), apprKey.substring(1).toLowerCase());
				apprMap.put(apprKey, feedback.getClass().getDeclaredMethod("get" + methodName).invoke(feedback));
			}

			for(CompetenceFeedbackFooter footer : feedback.getFooters()) {
				foot = new HashMap<String, Object>();
				for(String apprKey : EvaluationKeySet.COMPETENCE_FEEDBACK_FOOTER.getKeys()) {
					methodName = apprKey.replace(apprKey.substring(1), apprKey.substring(1).toLowerCase());
					foot.put(apprKey, footer.getClass().getDeclaredMethod("get" + methodName).invoke(footer));
				}
				footerList.add(foot);
			}

			rfcParam.put(EvaluationImport.IV_BUTTON.name(), feedback.getButton());
			rfcParam.put(EvaluationImport.IV_APRSR.name(), user.getEmpNo());
			rfcParam.put(EvaluationImport.IS_APPR.name(), apprMap);
			rfcParam.put(EvaluationImport.IT_FOOTER.name(), footerList);

			result = webEssRcv.callRfcFunction(EvaluationRFC.COMPETENCE_FEEDBACK_PROCESS.getRfc(), rfcParam, null, request);

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new IKEP4AjaxException("", ex);
		}

		return result;
	}

	/**
	 * 조직도와 사용자 조회를 하기 위한 메서드
	 *
	 * @param groupId 조직도에서 조회하고자 하는 그룹 ID
	 * @param selectType GROUP : 조직도만 조회, USER : 사용자 조회, ALL : 전체 조회
	 * @return 조회된 그룹과 사용자 리스트 Map
	 */
	private List<Map<String, Object>> getOrgGroupAndUser(String groupId, String userId, String selectType) {

		User sessionuser = (User) getRequestAttribute("ikep.user");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		String searchUserId = "";
		if (StringUtil.isEmpty(userId)) {
			searchUserId = sessionuser.getUserId();
		} else {
			searchUserId = userId;
		}

		Group searchgroup = new Group();
		searchgroup.setGroupId(groupId);
		searchgroup.setRegisterId(searchUserId);

		// 부서
		List<Group> groupList = groupService.selectOrgGroup(searchgroup);

		if(!"".equals(groupId)){
			Group sessionUserGroup = groupService.read(groupId);

			Map<String, Object> smap = new HashMap<String, Object>();
			smap.put("type", "group");
			smap.put("name", sessionUserGroup.getGroupName());
			smap.put("code", sessionUserGroup.getGroupId());
			smap.put("groupTypeId", sessionUserGroup.getGroupTypeId());
			smap.put("parent", sessionUserGroup.getParentGroupId());
			smap.put("hasChild", sessionUserGroup.getChildGroupCount());
			list.add(smap);
		}

		for (Group group : groupList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("type", "group");
			if (sessionuser.getLocaleCode().equals("ko")) {
				map.put("name", group.getGroupName());
			} else {
				map.put("name", group.getGroupEnglishName());
			}
			map.put("code", group.getGroupId());
			map.put("groupTypeId", group.getGroupTypeId());
			map.put("parent", group.getParentGroupId());
			map.put("hasChild", group.getChildGroupCount());
			list.add(map);
		}

		// 사용자추가
		if (!(selectType.equals("GROUP"))) {

			List<User> userList = userService.selectAllForTree(sessionuser.getLocaleCode(), groupId, searchUserId);
			for (User user : userList) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("type", "user");
				String strJobTitle = user.getJobTitleName();
				if(!StringUtil.isEmpty(user.getJobDutyName()))
				{
					strJobTitle = user.getJobDutyName();
				}

				map.put("name", user.getUserName() + " " + strJobTitle);
				map.put("jobTitle", strJobTitle);
				map.put("teamName", user.getTeamName());
				map.put("code", "");
				map.put("parent", user.getGroupId());
				map.put("id", user.getUserId());
				map.put("empNo", user.getEmpNo());
				map.put("email", user.getMail());
				map.put("mobile", user.getMobile());
				map.put("leader", user.getLeader());
				list.add(map);
			}
		}

		return list;
	}

	/**
	 * @param groupId
	 * @param userId
	 * @param selectType
	 * @return
	 */
	private List<Map<String, Object>> getOrgGroupAndUser2(String groupId, String userId, String selectType) {

		User sessionuser = (User) getRequestAttribute("ikep.user");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		String searchUserId = "";
		if (StringUtil.isEmpty(userId)) {
			searchUserId = sessionuser.getUserId();
		} else {
			searchUserId = userId;
		}

		Group searchgroup = new Group();
		searchgroup.setGroupId(groupId);
		searchgroup.setRegisterId(searchUserId);

		// 부서
		List<Group> groupList = groupService.selectOrgGroup(searchgroup);

		if(!"".equals(groupId)){
			Group sessionUserGroup = groupService.read(groupId);

			Map<String, Object> smap = new HashMap<String, Object>();
			smap.put("type", "group");
			smap.put("name", sessionUserGroup.getGroupName());
			smap.put("code", sessionUserGroup.getGroupId());
			smap.put("groupTypeId", sessionUserGroup.getGroupTypeId());
			smap.put("parent", sessionUserGroup.getParentGroupId());
			smap.put("hasChild", sessionUserGroup.getChildGroupCount());
			list.add(smap);
		}

		for (Group group : groupList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("type", "group");
			if (sessionuser.getLocaleCode().equals("ko")) {
				map.put("name", group.getGroupName());
			} else {
				map.put("name", group.getGroupEnglishName());
			}
			map.put("code", group.getGroupId());
			map.put("groupTypeId", group.getGroupTypeId());
			map.put("parent", group.getParentGroupId());
			map.put("hasChild", group.getChildGroupCount());
			list.add(map);
		}
		//System.out.println("@@@@@@@@@@@@@@@@@@@@@@ sessionuser.getScheduleManager():"+sessionuser.getScheduleManager()+"@@");
		// 사용자추가
		if (!(selectType.equals("GROUP"))) {

			List<User> userList = userService.selectAllForTree(sessionuser.getLocaleCode(), groupId, searchUserId);
			for (User user : userList) {
				//System.out.println("@@@@@@@@@@@@@@@@@@@@@@ user.getScheduleManager():"+user.getScheduleManager()+"@@");

				if((sessionuser.getScheduleManager()).equals(user.getScheduleManager())){//동일한 스케줄 인원이면 더한다. 즉 같은 반장 소속이면.
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("type", "user");


					String strJobTitle = user.getJobTitleName();
					if(!StringUtil.isEmpty(user.getJobDutyName()))
					{
						strJobTitle = user.getJobDutyName();
					}

					map.put("name", user.getUserName() + " " + strJobTitle);
					map.put("jobTitle", strJobTitle);
					map.put("teamName", user.getTeamName());

					map.put("code", "");
					map.put("parent", user.getGroupId());
					map.put("id", user.getUserId());
					map.put("empNo", user.getEmpNo());
					map.put("email", user.getMail());
					map.put("mobile", user.getMobile());
					map.put("leader", user.getLeader());
					list.add(map);
				}
			}
		}

		return list;
	}

	/**
	 * @param specialGroupList
	 * @param userId
	 * @param selectType
	 * @return
	 */
	private List<Map<String, Object>> getOrgGroupAndUser3(List<MssTeamTreeSpecial> specialGroupList, String userId, String selectType) {

		User sessionuser = (User) getRequestAttribute("ikep.user");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		for (MssTeamTreeSpecial specialGroup : specialGroupList) {
			Group searchgroup = new Group();
			searchgroup.setGroupId(specialGroup.getGroupId());
			searchgroup.setRegisterId(specialGroup.getUserId());


			Group specialRootGroup = groupService.read(specialGroup.getGroupId());

			Map<String, Object> smap = new HashMap<String, Object>();


			// 부서
			if(specialGroup.getChildGroupCnt() < 1){


				List<Group> groupList = groupService.selectOrgGroup(searchgroup);

				if(groupList.size() == 0){
					groupList = groupService.selectOrgGroupSp(searchgroup);
				}

				for (Group group : groupList) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("type", "group");
					if (sessionuser.getLocaleCode().equals("ko")) {
						map.put("name", group.getGroupName());
					} else {
						map.put("name", group.getGroupEnglishName());
					}
					map.put("code", group.getGroupId());
					map.put("groupTypeId", group.getGroupTypeId());
					map.put("parent", group.getParentGroupId());
					map.put("hasChild", group.getChildGroupCount());
					list.add(map);
				}
			}else{
				smap.put("type", "group");
				smap.put("name", specialRootGroup.getGroupName());
				smap.put("code", specialRootGroup.getGroupId());
				smap.put("groupTypeId", specialRootGroup.getGroupTypeId());
				smap.put("parent", specialRootGroup.getParentGroupId());
				smap.put("hasChild", specialRootGroup.getChildGroupCount());
				list.add(smap);

			}
		}

		return list;

	}

}
