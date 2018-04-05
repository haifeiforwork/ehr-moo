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
import com.lgcns.ikep4.portal.evaluation.model.ObjectiveGoal;
import com.lgcns.ikep4.portal.evaluation.model.ObjectiveReview;
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
@RequestMapping(value="/portal/evaluation/performance")
public class PerformanceEvaluationController extends BaseController {

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
	protected final Log log = LogFactory.getLog(this.getClass());

	public boolean isSystemAdmin(User user) {

		return this.aclService.isSystemAdmin("MoorimMss", user.getUserId());

	}

	/**
	 * 업적 리스트 - 공통
	 *
	 * @param params
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/objectiveList.do")
	public ModelAndView objectiveList(@RequestParam HashMap<String, Object> params, HttpServletRequest request) throws Exception {

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
	 * 업적 상세 유효성체크 - 공통
	 *
	 * @param params
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/objectiveCheckView.do")
	public @ResponseBody Map<?, ?> objectiveCheckView(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		Map<?, ?> result = null;
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> apprMap = new HashMap<String, Object>();

		try {

			//RFC PARAMETER SETTING START
			for(String apprKey : EvaluationKeySet.PERFORMANCE.getKeys()){
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
	 * 업적 상세 - 공통
	 *
	 * @param params
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/objectiveView.do")
	public ModelAndView objectiveView(@RequestParam HashMap<String, Object> params, HttpServletRequest request) throws Exception {

		String action = (String) params.get("action");
		Evaluation evaluation = Evaluation.lookup(action.toUpperCase());

		ModelAndView mav = new ModelAndView(evaluation.getView());

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> apprMap = new HashMap<String, Object>();;

		for(String apprKey : EvaluationKeySet.PERFORMANCE.getKeys()){
			apprMap.put(apprKey, params.get(apprKey));
		}

		rfcParam.put(EvaluationImport.IS_APPR.name(), apprMap);
		//RFC PARAMETER SETTING END

		Map<?, ?> export = webEssRcv.callRfcFunction(evaluation.getEvaluationRFC().getRfc(), rfcParam, null, request);

		ArrayList<Map<?, ?>> tempList = (ArrayList<Map<?, ?>>) export.get(EvaluationExport.ET_DETAIL.name());

		ArrayList<Map<?, ?>> qualList = new ArrayList<Map<?, ?>>();
		ArrayList<Map<?, ?>> quanList = new ArrayList<Map<?, ?>>();

		for(Map temp : tempList) {
			if(temp.get("GUBUN").equals("1")) {
				qualList.add(temp);
			} else {
				quanList.add(temp);
			}
		}

		mav.addObject("qualList", qualList);
		mav.addObject("quanList", quanList);
		mav.addObject("export", export);
		mav.addObject("params", params);

		return mav;

	}

	/**
	 * 업적 상세  - 출력
	 *
	 * @param params
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/objectiveSettingViewPrint.do")
	public ModelAndView objectiveSettingViewPrint(@RequestParam HashMap<String, Object> params, HttpServletRequest request) throws Exception {

		Evaluation evaluation = Evaluation.OBJECTIVE_SETTING_VIEW_PRINT;

		ModelAndView mav = new ModelAndView(evaluation.getView());

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> apprMap = new HashMap<String, Object>();;

		for(String apprKey : EvaluationKeySet.PERFORMANCE.getKeys()){
			apprMap.put(apprKey, params.get(apprKey));
		}

		rfcParam.put(EvaluationImport.IS_APPR.name(), apprMap);
		//RFC PARAMETER SETTING END

		Map<?, ?> export = webEssRcv.callRfcFunction(evaluation.getEvaluationRFC().getRfc(), rfcParam, null, request);

		Map<?, ?> esHeader = (Map<?, ?>) export.get(EvaluationExport.ES_HEADER.name());
		ArrayList<Map<?, ?>> tempList = (ArrayList<Map<?, ?>>) export.get(EvaluationExport.ET_DETAIL.name());

		ArrayList<Map<?, ?>> qualList = new ArrayList<Map<?, ?>>();
		ArrayList<Map<?, ?>> quanList = new ArrayList<Map<?, ?>>();

		for(Map temp : tempList) {
			if(temp.get("GUBUN").equals("1")) {
				qualList.add(temp);
			} else {
				quanList.add(temp);
			}
		}

		mav.addObject("esHeader", esHeader);
		mav.addObject("qualList", qualList);
		mav.addObject("quanList", quanList);
		mav.addObject("params", params);

		return mav;

	}

	/**
	 * 업적 - 목표수립 저장
	 *
	 * @param goals
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/objectiveSettingProc.do")
	public @ResponseBody Map<?, ?> objectiveSettingProc(ObjectiveGoal goals, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");

		Map<?, ?> result = null;
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		List<HashMap<String, Object>> detailList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> detail = null;

		try {

			for(ObjectiveGoal goal : goals.getGoals()) {
				detail = new HashMap<String, Object>();
				detail.put("AYEAR", goals.getAyear());
				detail.put("APRSE", goals.getAprse());
				detail.put("SEQNO", goals.getSeqno());
				detail.put("GUBUN", goal.getGubun());
				detail.put("NUMBR", goal.getNumbr());
				detail.put("ATASK", goal.getAtask());
				detail.put("DTAIL", goal.getDtail());
				detail.put("TRGET", goal.getTrget());
				detail.put("WEIGHT", goal.getWeight());
				detailList.add(detail);
			}

			if(goals.getXgoals() != null) {
				for(ObjectiveGoal xgoal : goals.getXgoals()) {
					detail = new HashMap<String, Object>();
					detail.put("AYEAR", goals.getAyear());
					detail.put("APRSE", goals.getAprse());
					detail.put("SEQNO", goals.getSeqno());
					detail.put("GUBUN", xgoal.getGubun());
					detail.put("NUMBR", xgoal.getNumbr());
					detail.put("DELFG", "X");
					detailList.add(detail);
				}
			}

			rfcParam.put(EvaluationImport.IV_BUTTON.name(), goals.getButton());
			rfcParam.put(EvaluationImport.IV_PERNR.name(), user.getEmpNo());
			rfcParam.put(EvaluationImport.IT_DETAIL.name(), detailList);

			result = webEssRcv.callRfcFunction(EvaluationRFC.OBJECTIVE_GOAL_PROCESS.getRfc(), rfcParam, null, request);

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new IKEP4AjaxException("", ex);
		}

		return result;
	}

	/**
	 * 업적 - 목표합의 저장
	 *
	 * @param goals
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/objectiveAgreementProc.do")
	public @ResponseBody Map<?, ?> objectiveAgreementProc(ObjectiveGoal goals, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");

		Map<?, ?> result = null;
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		List<HashMap<String, Object>> detailList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> detail = null;

		try {

			for(ObjectiveGoal goal : goals.getGoals()) {
				detail = new HashMap<String, Object>();
				detail.put("AYEAR", goals.getAyear());
				detail.put("APRSE", goals.getAprse());
				detail.put("SEQNO", goals.getSeqno());
				detail.put("GUBUN", goal.getGubun());
				detail.put("NUMBR", goal.getNumbr());
				detail.put("ATASK", goal.getAtask());
				detail.put("DTAIL", goal.getDtail());
				detail.put("TRGET", goal.getTrget());
				detail.put("WEIGHT", goal.getWeight());
				detailList.add(detail);
			}

			rfcParam.put(EvaluationImport.IV_BUTTON.name(), goals.getButton());
			rfcParam.put(EvaluationImport.IV_RJTEXT.name(), goals.getRjtext());
			rfcParam.put(EvaluationImport.IV_PERNR.name(), user.getEmpNo());
			rfcParam.put(EvaluationImport.IT_DETAIL.name(), detailList);

			result = webEssRcv.callRfcFunction(EvaluationRFC.OBJECTIVE_AGREE_PROCESS.getRfc(), rfcParam, null, request);

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new IKEP4AjaxException("", ex);
		}

		return result;
	}

	/**
	 * 업적 - 중간점검 저장
	 *
	 * @param goals
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/objectiveMiddlecheckProc.do")
	public @ResponseBody Map<?, ?> objectiveMiddlecheckProc(ObjectiveGoal goals, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");

		Map<?, ?> result = null;
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		List<HashMap<String, Object>> detailList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> detail = null;

		try {

			for(ObjectiveGoal goal : goals.getGoals()) {
				detail = new HashMap<String, Object>();
				detail.put("AYEAR", goals.getAyear());
				detail.put("APRSE", goals.getAprse());
				detail.put("SEQNO", goals.getSeqno());
				detail.put("GUBUN", goal.getGubun());
				detail.put("NUMBR", goal.getNumbr());
				detail.put("ATASK", goal.getAtask());
				detail.put("DTAIL", goal.getDtail());
				detail.put("TRGET", goal.getTrget());
				detail.put("WEIGHT", goal.getWeight());
				detail.put("MDCHK", goal.getMdchk());
				detailList.add(detail);
			}

			rfcParam.put(EvaluationImport.IT_DETAIL.name(), detailList);
			rfcParam.put(EvaluationImport.IV_BUTTON.name(), goals.getButton());
			rfcParam.put(EvaluationImport.IV_PERNR.name(), user.getEmpNo());

			result = webEssRcv.callRfcFunction(EvaluationRFC.OBJECTIVE_CHECK_PROCESS.getRfc(), rfcParam, null, request);

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new IKEP4AjaxException("", ex);
		}

		return result;
	}

	/**
	 * 업적 - 중간점검 FeedBack 저장
	 *
	 * @param goals
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/objectiveFeedbackProc.do")
	public @ResponseBody Map<?, ?> objectiveFeedbackProc(ObjectiveGoal goals, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");

		Map<?, ?> result = null;
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		List<HashMap<String, Object>> detailList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> detail = null;

		try {

			for(ObjectiveGoal goal : goals.getGoals()) {
				detail = new HashMap<String, Object>();
				detail.put("AYEAR", goals.getAyear());
				detail.put("APRSE", goals.getAprse());
				detail.put("SEQNO", goals.getSeqno());
				detail.put("GUBUN", goal.getGubun());
				detail.put("NUMBR", goal.getNumbr());
				detail.put("ATASK", goal.getAtask());
				detail.put("DTAIL", goal.getDtail());
				detail.put("TRGET", goal.getTrget());
				detail.put("WEIGHT", goal.getWeight());
				detail.put("MDCHK", goal.getMdchk());
				detail.put("MCFBK", goal.getMcfbk());
				detailList.add(detail);
			}

			rfcParam.put(EvaluationImport.IT_DETAIL.name(), detailList);
			rfcParam.put(EvaluationImport.IV_BUTTON.name(), goals.getButton());
			rfcParam.put(EvaluationImport.IV_PERNR.name(), user.getEmpNo());

			result = webEssRcv.callRfcFunction(EvaluationRFC.OBJECTIVE_FEEDBACK_PROCESS.getRfc(), rfcParam, null, request);

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new IKEP4AjaxException("", ex);
		}

		return result;
	}

	/**
	 * 업적 - 실적작성 저장
	 *
	 * @param goals
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/objectivePerformanceProc.do")
	public @ResponseBody Map<?, ?> objectivePerformanceProc(ObjectiveGoal goals, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");

		Map<?, ?> result = null;
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		List<HashMap<String, Object>> detailList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> detail = null;

		try {

			for(ObjectiveGoal goal : goals.getGoals()) {
				detail = new HashMap<String, Object>();
				detail.put("AYEAR", goals.getAyear());
				detail.put("APRSE", goals.getAprse());
				detail.put("SEQNO", goals.getSeqno());
				detail.put("GUBUN", goal.getGubun());
				detail.put("NUMBR", goal.getNumbr());
				detail.put("ATASK", goal.getAtask());
				detail.put("DTAIL", goal.getDtail());
				detail.put("TRGET", goal.getTrget());
				detail.put("WEIGHT", goal.getWeight());
				detail.put("MDCHK", goal.getMdchk());
				detail.put("MCFBK", goal.getMcfbk());
				detail.put("OUTPT", goal.getOutpt());
				detailList.add(detail);
			}

			rfcParam.put(EvaluationImport.IV_BUTTON.name(), goals.getButton());
			rfcParam.put(EvaluationImport.IV_PERNR.name(), user.getEmpNo());
			rfcParam.put(EvaluationImport.IT_DETAIL.name(), detailList);

			result = webEssRcv.callRfcFunction(EvaluationRFC.OBJECTIVE_PERFORMANCE_PROCESS.getRfc(), rfcParam, null, request);

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new IKEP4AjaxException("", ex);
		}

		return result;
	}

	/**
	 * 업적 - 평가자 평가 상세
	 *
	 * @param params
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/objectiveEvaluationView.do")
	public ModelAndView objectiveEvaluationView(@RequestParam HashMap<String, Object> params, HttpServletRequest request) throws Exception {

		ModelAndView mav = new ModelAndView();

		User user = (User) getRequestAttribute("ikep.user");

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> apprMap = new HashMap<String, Object>();;

		for(String apprKey : EvaluationKeySet.PERFORMANCE.getKeys()) {
			apprMap.put(apprKey, params.get(apprKey));
		}

		rfcParam.put(EvaluationImport.IS_APPR.name(), apprMap);
		rfcParam.put(EvaluationImport.IV_PERNR.name(), user.getEmpNo());
		//RFC PARAMETER SETTING END

		Map<?, ?> export = webEssRcv.callRfcFunction(EvaluationRFC.OBJECTIVE_EVALUATION_DETAIL.getRfc(), rfcParam, null, request);

		String evAprser = (String) export.get(EvaluationExport.EV_APRSER.name());
		Map<?, ?> esHeader = (Map<?, ?>) export.get(EvaluationExport.ES_HEADER.name());
		ArrayList<Map<?, ?>> tempList = (ArrayList<Map<?, ?>>) export.get(EvaluationExport.ET_DETAIL.name());
		ArrayList<Map<?, ?>> scoreList = (ArrayList<Map<?, ?>>) export.get(EvaluationExport.ET_SCORE.name());

		ArrayList<Map<?, ?>> qualList = new ArrayList<Map<?, ?>>();
		ArrayList<Map<?, ?>> quanList = new ArrayList<Map<?, ?>>();

		for(Map temp : tempList) {
			if(temp.get("GUBUN").equals("1")) {
				qualList.add(temp);
			} else {
				quanList.add(temp);
			}
		}

		mav.addObject("qualList", qualList);
		mav.addObject("quanList", quanList);
		mav.addObject("scoreList", scoreList);
		mav.addObject("esHeader", esHeader);
		mav.addObject("evAprser", evAprser);
		mav.addObject("params", params);

		StringBuffer viewName = new StringBuffer();
		viewName
			.append("OBJECTIVE_EVALUATION")
			.append(evAprser)
			.append("_VIEW");

		mav.setViewName(Evaluation.lookup(viewName.toString()).getView());

		return mav;

	}

	/**
	 * 업적 - 평가자 평가 저장
	 *
	 * @param goals
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/objectiveEvaluationProc.do")
	public @ResponseBody Map<?, ?> objectiveEvaluationProc(ObjectiveGoal goals, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");

		Map<?, ?> result = null;
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		List<HashMap<String, Object>> detailList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> detail = null;

		try {

			for(ObjectiveGoal goal : goals.getGoals()) {
				detail = new HashMap<String, Object>();
				detail.put("AYEAR", goals.getAyear());
				detail.put("APRSE", goals.getAprse());
				detail.put("SEQNO", goals.getSeqno());
				detail.put("GUBUN", goal.getGubun());
				detail.put("NUMBR", goal.getNumbr());
				detail.put("ATASK", goal.getAtask());
				detail.put("DTAIL", goal.getDtail());
				detail.put("TRGET", goal.getTrget());
				detail.put("WEIGHT", goal.getWeight());
				detail.put("MDCHK", goal.getMdchk());
				detail.put("MCFBK", goal.getMcfbk());
				detail.put("OUTPT", goal.getOutpt());
				detail.put("FSOAC", goal.getFsoac());
				detail.put("FSILV", goal.getFsilv());
				detail.put("FSTQT", goal.getFstqt());
				detail.put("SEOPA", goal.getSeopa());
				detail.put("SEILV", goal.getSeilv());
				detail.put("SETQT", goal.getSetqt());
				detail.put("APSCO1", goal.getApsco1());
				detail.put("APSCO2", goal.getApsco2());
				detail.put("PTSUM", goal.getPtsum());
				detailList.add(detail);
			}

			rfcParam.put(EvaluationImport.IV_PERNR.name(), user.getEmpNo());
			rfcParam.put(EvaluationImport.IV_BUTTON.name(), goals.getButton());
			rfcParam.put(EvaluationImport.IT_DETAIL.name(), detailList);

			result = webEssRcv.callRfcFunction(EvaluationRFC.OBJECTIVE_EVALUATION_PROCESS.getRfc(), rfcParam, null, request);

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new IKEP4AjaxException("", ex);
		}

		return result;
	}

	/**
	 * 업적 - Review 목록
	 *
	 * @param params
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/objectiveReviewList.do")
	public ModelAndView objectiveReviewList(@RequestParam HashMap<String, Object> params, HttpServletRequest request) throws Exception {

		Evaluation evaluation = Evaluation.OBJECTIVE_REVIEW;
		ModelAndView mav = new ModelAndView(evaluation.getView());

		User user = (User) getRequestAttribute("ikep.user");

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		rfcParam.put(EvaluationImport.IV_REVWR.name(), user.getEmpNo());
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

	/**
	 * 업적 - Review 저장
	 *
	 * @param reviews
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/objectiveReviewProc.do")
	public @ResponseBody Map<?, ?> objectiveReviewProc(ObjectiveReview reviews, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");

		Map<?, ?> result = null;
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		List<HashMap<String, Object>> detailList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> detail = null;

		try {

			for(ObjectiveReview review : reviews.getReviews()) {
				detail = new HashMap<String, Object>();
				detail.put("AYEAR", review.getAyear());
				detail.put("ORGEH", review.getOrgeh());
				detail.put("ORGTX", review.getOrgtx());
				detail.put("APRSE", review.getAprse());
				detail.put("APRSENM", review.getAprsenm());
				detail.put("TRFGR", review.getTrfgr());
				detail.put("FNSCO", review.getFnsco());
				detail.put("ADJSC", review.getAdjsc());
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

			result = webEssRcv.callRfcFunction(EvaluationRFC.OBJECTIVE_REVIEW_PROCESS.getRfc(), rfcParam, null, request);

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new IKEP4AjaxException("", ex);
		}

		return result;

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/objectiveReview2List.do")
	public ModelAndView objectiveReview2List(@RequestParam HashMap<String, Object> params, HttpServletRequest request) throws Exception {

		Evaluation evaluation = Evaluation.OBJECTIVE_REVIEW2_LIST;
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
	@RequestMapping(value = "/objectiveReview2View.do")
	public ModelAndView objectiveReview2View(@RequestParam HashMap<String, Object> params, HttpServletRequest request) throws Exception {

		Evaluation evaluation = Evaluation.OBJECTIVE_REVIEW2;
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

	@RequestMapping(value = "/objectiveReview2Proc.do")
	public @ResponseBody Map<?, ?> objectiveReview2Proc(ObjectiveReview reviews, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");

		Map<?, ?> result = null;
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		List<HashMap<String, Object>> detailList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> detail = null;

		try {

			//RFC PARAMETER SETTING START
			String methodName = "";

			for(ObjectiveReview objectiveReview : reviews.getReviews()) {
				detail = new HashMap<String, Object>();
				for(String apprKey : EvaluationKeySet.PERFORMANCE_REVIEW.getKeys()) {
					methodName = apprKey.replace(apprKey.substring(1), apprKey.substring(1).toLowerCase());
					detail.put(apprKey, objectiveReview.getClass().getDeclaredMethod("get" + methodName).invoke(objectiveReview));
				}
				detailList.add(detail);
			}

			rfcParam.put(EvaluationImport.IV_BUTTON.name(), reviews.getButton());
			rfcParam.put(EvaluationImport.IV_GUBUN.name(), "2");
			rfcParam.put(EvaluationImport.IV_PERNR.name(), user.getEmpNo());
			rfcParam.put(EvaluationImport.IT_LIST.name(), detailList);

			result = webEssRcv.callRfcFunction(EvaluationRFC.OBJECTIVE_REVIEW_PROCESS.getRfc(), rfcParam, null, request);

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new IKEP4AjaxException("", ex);
		}

		return result;

	}

	/**
	 * @param empno
	 * @param params
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/popPerformanceIndividualEvaluationList.do")
	public ModelAndView popPerformanceIndividualEvaluationList(@RequestParam(value="empno", required=true) String empno, @RequestParam HashMap<String, Object> params, HttpServletRequest request) throws Exception {

		Evaluation evaluation = Evaluation.OBJECTIVE_INDIVIDUAL_EVALUATION;
		ModelAndView mav = new ModelAndView(evaluation.getView());

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		rfcParam.put(EvaluationImport.IV_PERNR.name(), empno);
		rfcParam.put(EvaluationImport.IV_PERFM.name(), evaluation.getUserRole().getCode());
		//RFC PARAMETER SETTING END

		Map<?, ?> export = webEssRcv.callRfcFunction(evaluation.getEvaluationRFC().getRfc(), rfcParam, null, request);

		ArrayList<Map<?, ?>> etList = (ArrayList<Map<?, ?>>) export.get("ET_LIST");

		mav.addObject("etList", etList);
		mav.addObject("params", params);

		return mav;
	}

	/**
	 * @param params
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/popPerformanceIndividualEvaluationView.do")
	public ModelAndView popPerformanceIndividualEvaluationView(@RequestParam HashMap<String, Object> params, HttpServletRequest request) throws Exception {

		Evaluation evaluation = null;
		String aprsr2 = (String) params.get("APRSR2");

		if(aprsr2.equals("00000000")) {
			evaluation = Evaluation.OBJECTIVE_INDIVIDUAL_EVALUATION_NO_SECONDARY_VIEW;
		} else {
			evaluation = Evaluation.OBJECTIVE_INDIVIDUAL_EVALUATION_VIEW;
		}

		ModelAndView mav = new ModelAndView(evaluation.getView());

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> apprMap = new HashMap<String, Object>();

		for(String apprKey : EvaluationKeySet.PERFORMANCE.getKeys()) {
			apprMap.put(apprKey, params.get(apprKey));
		}

		rfcParam.put(EvaluationImport.IV_PERNR.name(), params.get("APRSR1"));
		rfcParam.put(EvaluationImport.IS_APPR.name(), apprMap);
		//RFC PARAMETER SETTING END

		Map<?, ?> export = webEssRcv.callRfcFunction(evaluation.getEvaluationRFC().getRfc(), rfcParam, null, request);

		String evAprser = (String) export.get(EvaluationExport.EV_APRSER.name());
		Map<?, ?> esHeader = (Map<?, ?>) export.get(EvaluationExport.ES_HEADER.name());
		ArrayList<Map<?, ?>> tempList = (ArrayList<Map<?, ?>>) export.get(EvaluationExport.ET_DETAIL.name());
		ArrayList<Map<?, ?>> scoreList = (ArrayList<Map<?, ?>>) export.get(EvaluationExport.ET_SCORE.name());

		ArrayList<Map<?, ?>> qualList = new ArrayList<Map<?, ?>>();
		ArrayList<Map<?, ?>> quanList = new ArrayList<Map<?, ?>>();

		for(Map temp : tempList) {
			if(temp.get("GUBUN").equals("1")) {
				qualList.add(temp);
			} else {
				quanList.add(temp);
			}
		}

		mav.addObject("evAprser", evAprser);
		mav.addObject("esHeader", esHeader);
		mav.addObject("scoreList", scoreList);
		mav.addObject("qualList", qualList);
		mav.addObject("quanList", quanList);
		mav.addObject("params", params);

		return mav;

	}

	/**
	 * 업적 - 평가표조회 목록
	 *
	 * @param params
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/objectiveHSS.do")
	public ModelAndView objectiveHSS(@RequestParam HashMap<String, Object> params, HttpServletRequest request) throws Exception {

		Evaluation evaluation = Evaluation.OBJECTIVE_HSS;
		ModelAndView mav = new ModelAndView(evaluation.getView());

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
	 * 업적 - 평가표조회(HSS) - 목록
	 *
	 * @param params
	 * @param request
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/objectiveHSSList.do")
	public @ResponseBody Map<?, ?> objectiveHSSList(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		Map<?, ?> result = null;
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		try {

			String empNo = (String) params.get("emp_no");

			//RFC PARAMETER SETTING START
			rfcParam.put(EvaluationImport.IV_PERNR.name(), empNo);
			//RFC PARAMETER SETTING END

			result = webEssRcv.callRfcFunction(EvaluationRFC.OBJECTIVE_HSS_LIST.getRfc(), rfcParam, null, request);

			Map userInfo = (HashMap<String, String>) this.userDao.empNoToUserId(empNo);
			result.putAll(userInfo);

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new IKEP4AjaxException("", ex);
		}

		return result;

	}

	/**
	 * 업적 - 평가표조회 상세
	 *
	 * @param params
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/objectiveHSSView.do")
	public ModelAndView objectiveHSSView(@RequestParam HashMap<String, Object> params, HttpServletRequest request) throws Exception {

		Evaluation evaluation = null;
		String aprsr2 = (String) params.get("APRSR2");

		if(aprsr2.equals("00000000")) {
			evaluation = Evaluation.OBJECTIVE_HSS_VIEW_NO_SECONDARY_VIEW;
		} else {
			evaluation = Evaluation.OBJECTIVE_HSS_VIEW;
		}

		ModelAndView mav = new ModelAndView(evaluation.getView());

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> apprMap = new HashMap<String, Object>();;

		for(String apprKey : EvaluationKeySet.PERFORMANCE.getKeys()) {
			apprMap.put(apprKey, params.get(apprKey));
		}

		rfcParam.put(EvaluationImport.IS_APPR.name(), apprMap);
		//RFC PARAMETER SETTING END

		Map<?, ?> export = webEssRcv.callRfcFunction(evaluation.getEvaluationRFC().getRfc(), rfcParam, null, request);

		ArrayList<Map<?, ?>> tempList = (ArrayList<Map<?, ?>>) export.get(EvaluationExport.ET_DETAIL.name());

		ArrayList<Map<?, ?>> qualList = new ArrayList<Map<?, ?>>();
		ArrayList<Map<?, ?>> quanList = new ArrayList<Map<?, ?>>();

		for(Map temp : tempList) {
			if(temp.get("GUBUN").equals("1")) {
				qualList.add(temp);
			} else {
				quanList.add(temp);
			}
		}

		mav.addObject("qualList", qualList);
		mav.addObject("quanList", quanList);
		mav.addObject("params", params);

		return mav;

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
