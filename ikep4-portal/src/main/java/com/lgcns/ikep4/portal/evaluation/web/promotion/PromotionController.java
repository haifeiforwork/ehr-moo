package com.lgcns.ikep4.portal.evaluation.web.promotion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.portal.evaluation.constant.EvaluationExport;
import com.lgcns.ikep4.portal.evaluation.constant.EvaluationImport;
import com.lgcns.ikep4.portal.evaluation.constant.Promotion;
import com.lgcns.ikep4.portal.evaluation.constant.PromotionKeySet;
import com.lgcns.ikep4.portal.evaluation.constant.PromotionRFC;
import com.lgcns.ikep4.portal.evaluation.model.PromotionCommon;
import com.lgcns.ikep4.portal.evaluation.model.PromotionSubject;
import com.lgcns.ikep4.portal.util.PagingUtils;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.jco.WebESSRcvRFC;

@Controller
@RequestMapping(value="/portal/evaluation/promotion")
public class PromotionController extends BaseController {

	@Autowired
	private WebESSRcvRFC webEssRcv;

	//Log
	protected final Log logger = LogFactory.getLog(this.getClass());

	/**
	 * 사무기술직승격 - 목록
	 *
	 * @param params
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/promotionList.do")
	public ModelAndView promotionList(@RequestParam HashMap<String, Object> params, HttpServletRequest request) throws Exception {

		String action = (String) params.get("action");
		Promotion promotion = Promotion.lookup(action.toUpperCase());

		ModelAndView mav = new ModelAndView(promotion.getView());

		User user = (User) getRequestAttribute("ikep.user");

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		rfcParam.put(EvaluationImport.I_RSPER.name(), user.getEmpNo());
		//RFC PARAMETER SETTING END

		Map<?, ?> export = webEssRcv.callRfcFunction(promotion.getPromotionRFC().getRfc(), rfcParam, null, request);

		Map<?, ?> esReturn = (Map<?, ?>) export.get(EvaluationExport.ES_RETURN.name());
		ArrayList<Map<?, ?>> tempList = (ArrayList<Map<?, ?>>) export.get(EvaluationExport.ET_LIST.name());

		ArrayList<Map<?, ?>> etList = PagingUtils.setPagingParam(tempList, params);

		mav.addObject("esReturn", esReturn);
		mav.addObject("etList", etList);
		mav.addObject("params", params);

		return mav;

	}

	/**
	 * 사무기술직승격 - 상세체크
	 *
	 * @param params
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/promotionCheckView.do")
	public @ResponseBody Map<?, ?> promotionCheckView(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		Promotion promotion = Promotion.lookup((String)params.get("action"));

		Map<?, ?> result = null;
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> apprMap = new HashMap<String, Object>();

		User user = (User) getRequestAttribute("ikep.user");

		try {

			//RFC PARAMETER SETTING START
			for(String apprKey : PromotionKeySet.COMMON.getKeys()){
				apprMap.put(apprKey, params.get(apprKey));
			}
			rfcParam.put(EvaluationImport.I_RSPER.name(), user.getEmpNo());
			rfcParam.put(EvaluationImport.IS_RSLST.name(), apprMap);
			//RFC PARAMETER SETTING END

			Map<?, ?> export = webEssRcv.callRfcFunction(promotion.getPromotionRFC().getRfc(), rfcParam, null, request);

			result = (Map<?, ?>) export.get(EvaluationExport.ES_RETURN.name());

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new IKEP4AjaxException("", ex);
		}

		return result;

	}

	/**
	 * 사무기술직승격 - 상세
	 *
	 * @param params
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/goalSettingView.do")
	public ModelAndView goalSettingView(@RequestParam HashMap<String, Object> params, HttpServletRequest request) throws Exception {

		String action = (String) params.get("action");
		Promotion promotion = Promotion.lookup(action.toUpperCase());

		ModelAndView mav = new ModelAndView(promotion.getView());

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> apprMap = new HashMap<String, Object>();

		User user = (User) getRequestAttribute("ikep.user");

		for(String apprKey : PromotionKeySet.COMMON.getKeys()){
			apprMap.put(apprKey, params.get(apprKey));
		}
		rfcParam.put(EvaluationImport.I_RSPER.name(), user.getEmpNo());
		rfcParam.put(EvaluationImport.IS_RSLST.name(), apprMap);
		//RFC PARAMETER SETTING END

		Map<?, ?> export = webEssRcv.callRfcFunction(promotion.getPromotionRFC().getRfc(), rfcParam, null, request);

		String eModif = (String) export.get(EvaluationExport.E_MODIF.name());
		Map<?, ?> rslst = (Map<?, ?>) export.get(EvaluationExport.ES_RSLST.name());
		ArrayList<Map<?, ?>> goalList = (ArrayList<Map<?, ?>>) export.get(EvaluationExport.ET_CHAPRJ.name());

		mav.addObject("goalList", goalList);
		mav.addObject("rslst", rslst);
		mav.addObject("eModif", eModif);
		mav.addObject("params", params);

		return mav;

	}

	/**
	 * @param params
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/goalSettingViewPrint.do")
	public ModelAndView goalSettingViewPrint(@RequestParam HashMap<String, Object> params, HttpServletRequest request) throws Exception {

		Promotion promotion = Promotion.GOAL_SETTING_VIEW_PRINT;
		ModelAndView mav = new ModelAndView(promotion.getView());

		User user = (User) getRequestAttribute("ikep.user");

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> apprMap = new HashMap<String, Object>();

		for(String apprKey : PromotionKeySet.COMMON.getKeys()){
			apprMap.put(apprKey, params.get(apprKey));
		}
		rfcParam.put(EvaluationImport.I_RSPER.name(), user.getEmpNo());
		rfcParam.put(EvaluationImport.I_PRINT.name(), "X");
		rfcParam.put(EvaluationImport.IS_RSLST.name(), apprMap);
		//RFC PARAMETER SETTING END

		Map<?, ?> export = webEssRcv.callRfcFunction(promotion.getPromotionRFC().getRfc(), rfcParam, null, request);

		Map<String, Object> esRslst = (Map<String, Object>) export.get(EvaluationExport.ES_RSLST.name());
		ArrayList<Map<String, Object>> etChaprj = (ArrayList<Map<String, Object>>) export.get(EvaluationExport.ET_CHAPRJ.name());

		ArrayList<Map<String, Object>> printList = new ArrayList<Map<String, Object>>();
		Map<String, Object> print = new HashMap<String, Object>();

		print.put("esRslst", esRslst);
		print.put("etChaprj", etChaprj);
		printList.add(print);

		mav.addObject("printList", printList);

		return mav;

	}

	/**
	 * For SAP PRINT
	 *
	 * @param encKey
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/print.do")
	public ModelAndView print(@RequestParam(value="encKey", required=true) String encKey, HttpServletRequest request) throws Exception {

		Promotion promotion = Promotion.GOAL_SETTING_VIEW_PRINT;
		ModelAndView mav = new ModelAndView(promotion.getView());

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		rfcParam.put(EvaluationImport.I_ECRGUB.name(), "3");
		rfcParam.put(EvaluationImport.I_ECRKEY.name(), encKey);
		//RFC PARAMETER SETTING END

		Map<?, ?> export = webEssRcv.callRfcFunction(PromotionRFC.PRINT.getRfc(), rfcParam, null, request);

		ArrayList<Map<?, ?>> rsperList = (ArrayList<Map<?, ?>>) export.get(EvaluationExport.T_RSPER.name());

		ArrayList<Map<String, Object>> printList = new ArrayList<Map<String, Object>>();
		Map<String, Object> print = null;

		ArrayList<Map<String, Object>> etChaprj = null;
		Map<String, Object> esRslst = null;

		//RFC PARAMETER SETTING START
		HashMap<String, Object> apprMap = null;

		for(Map<?, ?> rsper : rsperList) {
			apprMap = new HashMap<String, Object>();
			apprMap.put("AYEAR", rsper.get("AYEAR"));
			apprMap.put("RSPER", rsper.get("RSPER"));
			apprMap.put("RSPERNM", rsper.get("RSPERNM"));

			rfcParam = new HashMap<String, Object>();
			rfcParam.put(EvaluationImport.I_RSPER.name(), rsper.get("RSPER"));
			rfcParam.put(EvaluationImport.I_PRINT.name(), "X");
			rfcParam.put(EvaluationImport.IS_RSLST.name(), apprMap);

			export = webEssRcv.callRfcFunction(promotion.getPromotionRFC().getRfc(), rfcParam, null, request);

			esRslst = (Map<String, Object>) export.get(EvaluationExport.ES_RSLST.name());
			etChaprj = (ArrayList<Map<String, Object>>) export.get(EvaluationExport.ET_CHAPRJ.name());

			print = new HashMap<String, Object>();
			print.put("esRslst", esRslst);
			print.put("etChaprj", etChaprj);

			printList.add(print);
		}
		//RFC PARAMETER SETTING END

		mav.addObject("printList", printList);

		return mav;

	}

	/**
	 * 사무기술직승격 - 저장
	 *
	 * @param promotionCommon
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/goalSettingProc.do")
	public @ResponseBody Map<?, ?> goalSettingProc(PromotionCommon promotionCommon, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");

		Map<?, ?> result = null;
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		HashMap<String, Object> rslstMap = new HashMap<String, Object>();
		List<HashMap<String, Object>> subjectList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> subject = null;

		try {

			//RFC PARAMETER SETTING START
			String methodName = "";

			for(String apprKey : PromotionKeySet.COMMON.getKeys()) {
				methodName = apprKey.replace(apprKey.substring(1), apprKey.substring(1).toLowerCase());
				rslstMap.put(apprKey, promotionCommon.getClass().getDeclaredMethod("get" + methodName).invoke(promotionCommon));
			}

			for(PromotionSubject promotionSubject : promotionCommon.getSubjects()) {
				subject = new HashMap<String, Object>();
				for(String apprKey : PromotionKeySet.SUBJECT.getKeys()) {
					methodName = apprKey.replace(apprKey.substring(1), apprKey.substring(1).toLowerCase());
					subject.put(apprKey, promotionSubject.getClass().getDeclaredMethod("get" + methodName).invoke(promotionSubject));
				}
				subjectList.add(subject);
			}

			rfcParam.put(EvaluationImport.I_BUTTON.name(), promotionCommon.getButton());
			rfcParam.put(EvaluationImport.I_LOGPER.name(), user.getEmpNo());
			rfcParam.put(EvaluationImport.IS_RSLST.name(), rslstMap);
			rfcParam.put(EvaluationImport.IT_CHAPRJ.name(), subjectList);
			//RFC PARAMETER SETTING END

			result = webEssRcv.callRfcFunction(PromotionRFC.PROCESS.getRfc(), rfcParam, null, request);

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new IKEP4AjaxException("", ex);
		}

		return result;

	}

}
