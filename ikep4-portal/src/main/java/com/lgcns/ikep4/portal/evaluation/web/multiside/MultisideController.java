package com.lgcns.ikep4.portal.evaluation.web.multiside;

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
import com.lgcns.ikep4.portal.evaluation.constant.Multiside;
import com.lgcns.ikep4.portal.evaluation.constant.MultisideGroup;
import com.lgcns.ikep4.portal.evaluation.constant.MultisideKeySet;
import com.lgcns.ikep4.portal.evaluation.constant.MultisideRFC;
import com.lgcns.ikep4.portal.evaluation.model.MultisideDiagnosis;
import com.lgcns.ikep4.portal.evaluation.model.MultisideDiagnosisDetail;
import com.lgcns.ikep4.portal.util.PagingUtils;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.jco.WebESSRcvRFC;

@Controller
@RequestMapping(value="/portal/evaluation/multiside")
public class MultisideController extends BaseController {

	@Autowired
	private WebESSRcvRFC webEssRcv;

	//Log
	protected final Log logger = LogFactory.getLog(this.getClass());

	/**
	 * 다면진단 - 목록
	 *
	 * @param params
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/multisideDiagnosisList.do")
	public ModelAndView multisideDiagnosisList(@RequestParam HashMap<String, Object> params, HttpServletRequest request) throws Exception {

		String action = (String) params.get("action");
		Multiside multiside = Multiside.lookup(action.toUpperCase());

		ModelAndView mav = new ModelAndView(multiside.getView());

		User user = (User) getRequestAttribute("ikep.user");

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		rfcParam.put(EvaluationImport.I_MAPPR.name(), user.getEmpNo());
		//RFC PARAMETER SETTING END

		Map<?, ?> export = webEssRcv.callRfcFunction(multiside.getMultisideRFC().getRfc(), rfcParam, null, request);

		Map<?, ?> esReturn = (Map<?, ?>) export.get(EvaluationExport.ES_RETURN.name());
		ArrayList<Map<?, ?>> tempList = (ArrayList<Map<?, ?>>) export.get(EvaluationExport.ET_LIST.name());

		ArrayList<Map<?, ?>> etList = PagingUtils.setPagingParam(tempList, params);

		mav.addObject("esReturn", esReturn);
		mav.addObject("etList", etList);
		mav.addObject("params", params);

		return mav;

	}

	/**
	 * 다면진단 - 상세체크
	 *
	 * @param params
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/multisideCheckView.do")
	public @ResponseBody Map<?, ?> multisideCheckView(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		Map<?, ?> result = null;
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> apprMap = new HashMap<String, Object>();

		try {

			//RFC PARAMETER SETTING START
			for(String apprKey : MultisideKeySet.DIAGNOSIS.getKeys()){
				apprMap.put(apprKey, params.get(apprKey));
			}
			rfcParam.put(EvaluationImport.IS_MAPPR.name(), apprMap);
			//RFC PARAMETER SETTING END

			MultisideRFC multisideRFC = MultisideRFC.lookup((String)params.get("rfc"));
			Map<?, ?> export = webEssRcv.callRfcFunction(multisideRFC.getRfc(), rfcParam, null, request);

			result = (Map<?, ?>) export.get(EvaluationExport.ES_RETURN.name());

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new IKEP4AjaxException("", ex);
		}

		return result;

	}

	/**
	 * 다면진단 - 상세
	 *
	 * @param params
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/multisideDiagnosisView.do")
	public ModelAndView multisideDiagnosisView(@RequestParam HashMap<String, Object> params, HttpServletRequest request) throws Exception {

		Multiside multiside = Multiside.MULTISIDE_DIAGNOSIS_VIEW;
		ModelAndView mav = new ModelAndView(multiside.getView());

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> apprMap = new HashMap<String, Object>();

		for(String apprKey : MultisideKeySet.DIAGNOSIS.getKeys()){
			apprMap.put(apprKey, params.get(apprKey));
		}
		rfcParam.put(EvaluationImport.IS_MAPPR.name(), apprMap);
		//RFC PARAMETER SETTING END

		Map<?, ?> export = webEssRcv.callRfcFunction(multiside.getMultisideRFC().getRfc(), rfcParam, null, request);

		String eModif = (String) export.get(EvaluationExport.E_MODIF.name());
		Map<?, ?> esMappr = (Map<?, ?>) export.get(EvaluationExport.ES_MAPPR.name());
		ArrayList<Map<?, ?>> etDetail = (ArrayList<Map<?, ?>>) export.get(EvaluationExport.ET_DETAIL.name());

		MultisideGroup multisideGroup = null;
		ArrayList<Map<?, ?>> etDetail1List = new ArrayList<Map<?, ?>>();
		ArrayList<Map<?, ?>> etDetail2List = new ArrayList<Map<?, ?>>();
		ArrayList<Map<?, ?>> etDetail3List = new ArrayList<Map<?, ?>>();
		ArrayList<Map<?, ?>> etDetail4List = new ArrayList<Map<?, ?>>();

		for(Map<?, ?> temp : etDetail) {
			multisideGroup = MultisideGroup.valueOf((String) temp.get("MRGRP"));

			switch(multisideGroup) {
				case EQKCM :
					etDetail1List.add(temp);
					break;
				case EQKL1 : case EQKL2 :
					etDetail2List.add(temp);
					break;
				case FBSG1 :
					etDetail3List.add(temp);
					break;
				case FBSG2 :
					etDetail4List.add(temp);
					break;
				default :
					break;
			}
		}

		mav.addObject("eModif", eModif);
		mav.addObject("esMappr", esMappr);
		mav.addObject("etDetail1List", etDetail1List);
		mav.addObject("etDetail2List", etDetail2List);
		mav.addObject("etDetail3List", etDetail3List);
		mav.addObject("etDetail4List", etDetail4List);
		mav.addObject("params", params);

		return mav;

	}

	/**
	 * 다면진단 - 저장
	 *
	 * @param multisideDiagnosis
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/multisideDiagnosisProc.do")
	public @ResponseBody Map<?, ?> multisideDiagnosisProc(MultisideDiagnosis multisideDiagnosis, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");

		Map<?, ?> result = null;
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		HashMap<String, Object> apprMap = new HashMap<String, Object>();
		List<HashMap<String, Object>> detailList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> detail = null;

		try {

			String methodName = "";

			for(String apprKey : MultisideKeySet.DIAGNOSIS.getKeys()){
				methodName = apprKey.replace(apprKey.substring(1), apprKey.substring(1).toLowerCase());
				apprMap.put(apprKey, multisideDiagnosis.getClass().getDeclaredMethod("get" + methodName).invoke(multisideDiagnosis));
			}

			for(MultisideDiagnosisDetail itDetail : multisideDiagnosis.getDetails()) {
				detail = new HashMap<String, Object>();
				for(String apprKey : MultisideKeySet.DIAGNOSIS_DETAIL.getKeys()){
					methodName = apprKey.replace(apprKey.substring(1), apprKey.substring(1).toLowerCase());
					detail.put(apprKey, itDetail.getClass().getDeclaredMethod("get" + methodName).invoke(itDetail));
				}
				detailList.add(detail);
			}

			rfcParam.put(EvaluationImport.I_BUTTON.name(), multisideDiagnosis.getButton());
			rfcParam.put(EvaluationImport.I_LOGPER.name(), user.getEmpNo());
			rfcParam.put(EvaluationImport.IS_MAPPR.name(), apprMap);
			rfcParam.put(EvaluationImport.IT_DETAIL.name(), detailList);

			result = webEssRcv.callRfcFunction(MultisideRFC.MULTISIDE_PROCESS.getRfc(), rfcParam, null, request);

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new IKEP4AjaxException("", ex);
		}

		return result;

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/multisideFeedbackList.do")
	public ModelAndView multisideFeedbackList(@RequestParam HashMap<String, Object> params, HttpServletRequest request) throws Exception {

		Multiside multiside = Multiside.FEEDBACK_LIST;
		ModelAndView mav = new ModelAndView(multiside.getView());

		User user = (User) getRequestAttribute("ikep.user");

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		rfcParam.put(EvaluationImport.I_MAPPE.name(), user.getEmpNo());
		//RFC PARAMETER SETTING END

		Map<?, ?> export = webEssRcv.callRfcFunction(multiside.getMultisideRFC().getRfc(), rfcParam, null, request);

		Map<?, ?> esReturn = (Map<?, ?>) export.get(EvaluationExport.ES_RETURN.name());
		ArrayList<Map<?, ?>> tempList = (ArrayList<Map<?, ?>>) export.get(EvaluationExport.ET_LIST.name());

		ArrayList<Map<?, ?>> etList = PagingUtils.setPagingParam(tempList, params);

		mav.addObject("esReturn", esReturn);
		mav.addObject("etList", etList);
		mav.addObject("params", params);

		return mav;

	}

	@RequestMapping(value = "/multisideFeedbackCheckView.do")
	public @ResponseBody Map<?, ?> multisideFeedbackCheckView(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		Map<?, ?> result = null;
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> apprMap = new HashMap<String, Object>();

		try {

			//RFC PARAMETER SETTING START
			for(String apprKey : MultisideKeySet.FEEDBACK_DETAIL.getKeys()){
				apprMap.put(apprKey, params.get(apprKey));
			}
			rfcParam.put(EvaluationImport.IS_MAPPE.name(), apprMap);
			//RFC PARAMETER SETTING END

			Map<?, ?> export = webEssRcv.callRfcFunction(MultisideRFC.FEEDBACK_DETAIL.getRfc(), rfcParam, null, request);

			result = (Map<?, ?>) export.get(EvaluationExport.ES_RETURN.name());

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new IKEP4AjaxException("", ex);
		}

		return result;

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/multisideFeedbackView.do")
	public ModelAndView multisideFeedbackView(@RequestParam HashMap<String, Object> params, HttpServletRequest request) throws Exception {

		Multiside multiside = Multiside.lookup((String)params.get("action"));
		ModelAndView mav = new ModelAndView(multiside.getView());

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> apprMap = new HashMap<String, Object>();

		for(String apprKey : MultisideKeySet.FEEDBACK_DETAIL.getKeys()){
			apprMap.put(apprKey, params.get(apprKey));
		}
		rfcParam.put(EvaluationImport.IS_MAPPE.name(), apprMap);
		//RFC PARAMETER SETTING END

		Map<?, ?> export = webEssRcv.callRfcFunction(multiside.getMultisideRFC().getRfc(), rfcParam, null, request);

		String eSkippv = (String) export.get(EvaluationExport.E_SKIPPV.name());
		Map<?, ?> esMappr = (Map<?, ?>) export.get(EvaluationExport.ES_MAPPR.name());
		ArrayList<Map<?, ?>> etHeader = (ArrayList<Map<?, ?>>) export.get(EvaluationExport.ET_HEADER.name());
		ArrayList<Map<?, ?>> etDetail = (ArrayList<Map<?, ?>>) export.get(EvaluationExport.ET_DETAIL.name());

		Map<?, ?> etHeader1 = new HashMap<String, Object>();
		Map<?, ?> etHeader201 = new HashMap<String, Object>();
		Map<?, ?> etHeader202 = new HashMap<String, Object>();
		Map<?, ?> etHeader203 = new HashMap<String, Object>();
		Map<?, ?> etHeader301 = new HashMap<String, Object>();
		Map<?, ?> etHeader302 = new HashMap<String, Object>();
		Map<?, ?> etHeader401 = new HashMap<String, Object>();
		Map<?, ?> etHeader402 = new HashMap<String, Object>();
		Map<?, ?> etHeader403 = new HashMap<String, Object>();
		Map<?, ?> etHeader501 = new HashMap<String, Object>();
		Map<?, ?> etHeader502 = new HashMap<String, Object>();
		Map<?, ?> etHeader503 = new HashMap<String, Object>();
		Map<?, ?> etHeader601 = new HashMap<String, Object>();
		Map<?, ?> etHeader602 = new HashMap<String, Object>();
		Map<?, ?> etHeader603 = new HashMap<String, Object>();
		Map<?, ?> etHeader604 = new HashMap<String, Object>();
		Map<?, ?> etHeader605 = new HashMap<String, Object>();
		Map<?, ?> etHeader606 = new HashMap<String, Object>();
		Map<?, ?> etHeader607 = new HashMap<String, Object>();
		Map<?, ?> etHeader608 = new HashMap<String, Object>();
		Map<?, ?> etHeader609 = new HashMap<String, Object>();
		Map<?, ?> etHeader610 = new HashMap<String, Object>();
		Map<?, ?> etHeader611 = new HashMap<String, Object>();
		Map<?, ?> etHeader612 = new HashMap<String, Object>();
		Map<?, ?> etHeader613 = new HashMap<String, Object>();

		for(Map<?, ?> header : etHeader) {
			String tabGubun = (String) header.get("TABGB");
			String seqnr;

			if(tabGubun.equals("01")) {
				etHeader1 = header;
				continue;
			} else if(tabGubun.equals("03")) {
				seqnr = (String) header.get("SEQNR");

				if(seqnr.equals("01")) {
					etHeader201 = header;
					continue;
				} else if(seqnr.equals("02")) {
					etHeader202 = header;
					continue;
				} else if(seqnr.equals("03")) {
					etHeader203 = header;
					continue;
				} else {
					continue;
				}
			} else if(tabGubun.equals("04")) {
				seqnr = (String) header.get("SEQNR");

				if(seqnr.equals("01")) {
					etHeader401 = header;
					continue;
				} else if(seqnr.equals("02")) {
					etHeader402 = header;
					continue;
				} else if(seqnr.equals("03")) {
					etHeader403 = header;
					continue;
				} else {
					continue;
				}
			} else if(tabGubun.equals("05")) {
				seqnr = (String) header.get("SEQNR");

				if(seqnr.equals("01")) {
					etHeader301 = header;
					continue;
				} else if(seqnr.equals("02")) {
					etHeader302 = header;
					continue;
				} else {
					continue;
				}
			} else if(tabGubun.equals("06")) {
				seqnr = (String) header.get("SEQNR");

				if(seqnr.equals("01")) {
					etHeader501 = header;
					continue;
				} else if(seqnr.equals("02")) {
					etHeader502 = header;
					continue;
				} else if(seqnr.equals("03")) {
					etHeader503 = header;
					continue;
				} else {
					continue;
				}
			} else if(tabGubun.equals("07")) {
				seqnr = (String) header.get("SEQNR");

				if(seqnr.equals("01")) {
					etHeader601 = header;
					continue;
				} else if(seqnr.equals("02")) {
					etHeader602 = header;
					continue;
				} else if(seqnr.equals("03")) {
					etHeader603 = header;
					continue;
				} else if(seqnr.equals("04")) {
					etHeader604 = header;
					continue;
				} else if(seqnr.equals("05")) {
					etHeader605 = header;
					continue;
				} else if(seqnr.equals("06")) {
					etHeader606 = header;
					continue;
				} else if(seqnr.equals("07")) {
					etHeader607 = header;
					continue;
				} else if(seqnr.equals("08")) {
					etHeader608 = header;
					continue;
				} else if(seqnr.equals("09")) {
					etHeader609 = header;
					continue;
				} else if(seqnr.equals("10")) {
					etHeader610 = header;
					continue;
				} else if(seqnr.equals("11")) {
					etHeader611 = header;
					continue;
				} else if(seqnr.equals("12")) {
					etHeader612 = header;
					continue;
				} else if(seqnr.equals("13")) {
					etHeader613 = header;
					continue;
				} else {
					continue;
				}
			} else {
				continue;
			}
		}

		MultisideGroup multisideGroup = null;
		ArrayList<Map<?, ?>> etDetail1List = new ArrayList<Map<?, ?>>();
		ArrayList<Map<?, ?>> etDetail2List = new ArrayList<Map<?, ?>>();
		ArrayList<Map<?, ?>> etDetail3List = new ArrayList<Map<?, ?>>();
		ArrayList<Map<?, ?>> etDetail4List = new ArrayList<Map<?, ?>>();
		ArrayList<Map<?, ?>> etDetail5List = new ArrayList<Map<?, ?>>();

		for(Map<?, ?> temp : etDetail) {
			multisideGroup = MultisideGroup.valueOf((String) temp.get("MRGRP"));

			switch(multisideGroup) {
				case EQKCM :
					etDetail1List.add(temp);
					break;
				case EQKL1 : case EQKL2 :
					etDetail2List.add(temp);
					break;
				case FBSG1 :
					etDetail3List.add(temp);
					break;
				case FBSG2 :
					etDetail4List.add(temp);
					break;
				case ZTOT :
					etDetail5List.add(temp);
					break;
				default :
					break;
			}
		}

		mav.addObject("eSkippv", eSkippv);
		mav.addObject("esMappr", esMappr);
		mav.addObject("etHeader1", etHeader1);
		mav.addObject("etHeader201", etHeader201);
		mav.addObject("etHeader202", etHeader202);
		mav.addObject("etHeader203", etHeader203);
		mav.addObject("etHeader301", etHeader301);
		mav.addObject("etHeader302", etHeader302);
		mav.addObject("etHeader401", etHeader401);
		mav.addObject("etHeader402", etHeader402);
		mav.addObject("etHeader403", etHeader403);
		mav.addObject("etHeader501", etHeader501);
		mav.addObject("etHeader502", etHeader502);
		mav.addObject("etHeader503", etHeader503);
		mav.addObject("etHeader601", etHeader601);
		mav.addObject("etHeader602", etHeader602);
		mav.addObject("etHeader603", etHeader603);
		mav.addObject("etHeader604", etHeader604);
		mav.addObject("etHeader605", etHeader605);
		mav.addObject("etHeader606", etHeader606);
		mav.addObject("etHeader607", etHeader607);
		mav.addObject("etHeader608", etHeader608);
		mav.addObject("etHeader609", etHeader609);
		mav.addObject("etHeader610", etHeader610);
		mav.addObject("etHeader611", etHeader611);
		mav.addObject("etHeader612", etHeader612);
		mav.addObject("etHeader613", etHeader613);
		mav.addObject("etDetail1List", etDetail1List);
		mav.addObject("etDetail2List", etDetail2List);
		mav.addObject("etDetail3List", etDetail3List);
		mav.addObject("etDetail4List", etDetail4List);
		mav.addObject("etDetail5List", etDetail5List);
		mav.addObject("params", params);

		return mav;

	}

}
