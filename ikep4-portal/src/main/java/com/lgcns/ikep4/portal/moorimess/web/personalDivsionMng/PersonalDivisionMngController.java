package com.lgcns.ikep4.portal.moorimess.web.personalDivsionMng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.comomon.Constant;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.jco.WebESSRcvRFC;

@Controller
@RequestMapping(value="/portal/moorimess/personalDivisionMng")
public class PersonalDivisionMngController extends BaseController {

	@Autowired
	private WebESSRcvRFC webEssRcv;

	@RequestMapping(value = "/personalDivisionMngList.do")
	public ModelAndView personalDivisionMngList(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/webess/personalDivisionMng/personalDivisionList");

		User user = (User) getRequestAttribute("ikep.user");

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		rfcParam.put("IM_PERNR", user.getEmpNo());
		rfcParam.put("IM_FLAG" , "P");
		//RFC PARAMETER SETTING END

		Map<?, ?> resultMap = webEssRcv.callRfcFunction("ZHR_RFC_PD_033_GET_DATA", rfcParam, null, request);

		mav.addObject("resultMap", resultMap);
		mav.addObject("keySet"   , Constant.personalDivisionListKeySet);
		mav.addObject("params"   , params);

		return mav;
	}

	@RequestMapping(value = "checkedPD033OnInput.do")
	public @ResponseBody Map<?, ?> checkedPD033OnInput(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");

		String eventId = (String) params.get("eventId");

		//RFC PARAMETER SETTING START

		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> rfcParamList = new HashMap<String, Object>();

		String[] keySet = Constant.personalDivisionListKeySet;

		if("CREATE".equals(eventId)){
			ArrayList<HashMap<?, ?>> etList = new ArrayList<HashMap<?, ?>>();
			HashMap<String, Object> etListRow = null;

			int etListCnt = Integer.parseInt(((String) params.get("etListCnt")));


			if(etListCnt == 1){
				etListRow = new HashMap<String, Object>();
				for(String key : keySet){
					etListRow.put(key, params.containsKey("etList_"+key) ? params.get("etList_"+key) : "");
				}
				etList.add(etListRow);
			}else if(etListCnt > 1){
				for(int i=0 ; i<etListCnt ; i++){
					etListRow = new HashMap<String, Object>();
					for(String key : keySet){
						etListRow.put(key, request.getParameterValues("etList_"+key)[i]);
					}
					etList.add(etListRow);
				}
			}

			rfcParamList.put("IT_LIST", etList);

		}else if("DISPLAY".equals(eventId)){
			HashMap<String, Object> paramRow = new HashMap<String, Object>();

			for(String key : keySet){
				paramRow.put(key, params.containsKey(key) ? params.get(key) : "");
			}

			rfcParam.put("IM_LIST", paramRow);
		}

		rfcParam.put("IM_PERNR"   , user.getEmpNo());
		rfcParam.put("IM_EVENT_ID", eventId);
		//RFC PARAMETER SETTING END

		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_PD_033_ONINPUT", rfcParam, rfcParamList, request);

		return result;
	}


	@RequestMapping(value = "/personalDivisionView.do")
	public ModelAndView personalDivisionView(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		String jspUrl = "/portal/webess/personalDivisionMng/personalDivisionRegView";

		String exDisabled = (String) params.get("exDisabled");
		String keySet[] = Constant.personalDivisionListKeySet;

		if(!"FALSE".equals(exDisabled)){
			jspUrl = "/portal/webess/personalDivisionMng/personalDivisionView";
		}

		ModelAndView mav = new ModelAndView(jspUrl);

		User user = (User) getRequestAttribute("ikep.user");

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> rfcParamList = new HashMap<String, Object>();

		rfcParam.put("IM_PERNR"   , user.getEmpNo());
		rfcParam.put("IM_DISABLED", exDisabled);

		HashMap<String, Object> paramRow = new HashMap<String, Object>();

		for(String key : keySet){
			paramRow.put(key, params.containsKey(key) ? params.get(key) : "");
			params.remove(key);
		}

		rfcParam.put("IM_LIST", paramRow);

		int etRagPernrCnt = Integer.parseInt((String) params.get("etRagPernrCnt"));

		ArrayList<HashMap<?, ?>> etRagPernrList = new ArrayList<HashMap<?, ?>>();
		HashMap<String, Object> etRagPernrRow = null;

		if(etRagPernrCnt == 1){
			etRagPernrRow = new HashMap<String, Object>();

			etRagPernrRow.put("SIGN"  , params.containsKey("etRagPernr_SIGN")   ? params.get("etRagPernr_SIGN") : "");
			etRagPernrRow.put("OPTION", params.containsKey("etRagPernr_OPTION") ? params.get("etRagPernr_OPTION") : "");
			etRagPernrRow.put("LOW"   , params.containsKey("etRagPernr_LOW")    ? params.get("etRagPernr_LOW") : "");
			etRagPernrRow.put("HIGH"  , params.containsKey("etRagPernr_HIGH")   ? params.get("etRagPernr_HIGH") : "");

			etRagPernrList.add(etRagPernrRow);
		}else if(etRagPernrCnt > 1){
			for(int i=0 ; i<etRagPernrCnt ; i++){
				etRagPernrRow = new HashMap<String, Object>();

				etRagPernrRow.put("SIGN"  , request.getParameterValues("etRagPernr_SIGN")[i]);
				etRagPernrRow.put("OPTION", request.getParameterValues("etRagPernr_OPTION")[i]);
				etRagPernrRow.put("LOW"   , request.getParameterValues("etRagPernr_LOW")[i]);
				etRagPernrRow.put("HIGH"  , request.getParameterValues("etRagPernr_HIGH")[i]);

				etRagPernrList.add(etRagPernrRow);
			}
		}

		rfcParamList.put("IT_RAG_PERNR", etRagPernrList);
		//RFC PARAMETER SETTING END

		Map<?, ?> resultMap = webEssRcv.callRfcFunction("ZHR_RFC_PD_033_ONINIT_DETAIL", rfcParam, rfcParamList, request);

		mav.addObject("resultMap"     , resultMap);
		mav.addObject("keySet"        , keySet);
		mav.addObject("dpResultKeySet", Constant.dpResultListKeySet);
		mav.addObject("etRagPernrList", etRagPernrList);
		mav.addObject("params"        , params);

		return mav;
	}

	@RequestMapping(value = "/jobLinePop.do")
	public ModelAndView apprLinePop(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/webess/personalDivisionMng/jobLinePop");

		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		rfcParam.put("IM_SEARCH_D", params.get("imSearch"));

		Map<?, ?> resultMap = webEssRcv.callRfcFunction("ZHR_RFC_SEARCH_JIKMU_ZICGUN", rfcParam, null, request);

		mav.addObject("resultMap", resultMap);
		mav.addObject("params"   , params);
		mav.addObject("keySet"   , Constant.jobKeySet);

		return mav;
	}

	@RequestMapping(value = "retriveTaskList.do")
	public @ResponseBody Map<?, ?> retriveTaskList(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		rfcParam.put("IM_STELL", params.get("imStell"));
		//RFC PARAMETER SETTING END

		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_SEARCH_TASK", rfcParam, null, request);

		return result;
	}

	@RequestMapping(value = "personalDivisionProcess.do")
	public @ResponseBody Map<?, ?> personalDivisionProcess(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");

		String keySet[] = Constant.personalDivisionListKeySet;
		String dpResultKeySet[] = Constant.dpResultListKeySet;

		int dpResultCnt = Integer.parseInt((String) params.get("dpResultCnt"));
		ArrayList<HashMap<String, Object>> dpResult = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> dpResultRow = null;

		if(dpResultCnt == 1){

			dpResultRow = new HashMap<String, Object>();

			for(String key : dpResultKeySet){
				dpResultRow.put(key, params.containsKey(key) ? params.get(key) : "");
			}

			dpResult.add(dpResultRow);

		}else if(dpResultCnt > 1){
			for(int i=0 ; i<dpResultCnt ; i++){
				dpResultRow = new HashMap<String, Object>();
				for(String key : dpResultKeySet){
					dpResultRow.put(key, request.getParameterValues(key)[i]);
				}
				dpResult.add(dpResultRow);
			}
		}

		int ragPernrCnt = Integer.parseInt((String) params.get("ragPernrCnt"));
		ArrayList<HashMap<String, Object>> ragPernr = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> ragPernrRow = null;

		if(ragPernrCnt == 1){

			ragPernrRow = new HashMap<String, Object>();

			ragPernrRow.put("SIGN"  , params.containsKey("itRag_SIGN")   ? params.get("itRag_SIGN") : "");
			ragPernrRow.put("OPTION", params.containsKey("itRag_OPTION") ? params.get("itRag_OPTION") : "");
			ragPernrRow.put("LOW"   , params.containsKey("itRag_LOW")    ? params.get("itRag_LOW") : "");
			ragPernrRow.put("HIGH"  , params.containsKey("itRag_HIGH")   ? params.get("itRag_HIGH") : "");

			ragPernr.add(ragPernrRow);

		}else if(ragPernrCnt > 1){
			for(int i=0 ; i<ragPernrCnt ; i++){
				ragPernrRow = new HashMap<String, Object>();

				ragPernrRow.put("SIGN"  , request.getParameterValues("itRag_SIGN")[i]);
				ragPernrRow.put("OPTION", request.getParameterValues("itRag_OPTION")[i]);
				ragPernrRow.put("LOW"   , request.getParameterValues("itRag_LOW")[i]);
				ragPernrRow.put("HIGH"  , request.getParameterValues("itRag_HIGH")[i]);

				ragPernr.add(ragPernrRow);
			}
		}

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> rfcParamList = new HashMap<String, Object>();

		rfcParam.put("IM_PERNR"   , user.getEmpNo());
		rfcParam.put("IM_EVENT_ID", params.get("imEventId"));
		rfcParam.put("IM_JOBTEXT" , params.get("imJobtext"));
		rfcParam.put("IM_OPERA"   , params.get("imOpera"));

		HashMap<String, Object> paramRow = new HashMap<String, Object>();

		for(String key : keySet){
			paramRow.put(key, params.containsKey("SEL_"+key) ? params.get("SEL_"+key) : "");
		}

		rfcParam.put("IM_LIST", paramRow);

		rfcParamList.put("IT_DP_RESULT", dpResult);
		rfcParamList.put("IT_RAG_PERNR", ragPernr);

		//RFC PARAMETER SETTING END

		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_PD_033_ONINPUT_DETAIL", rfcParam, rfcParamList, request);

		return result;
	}
	
	@RequestMapping(value = "dpLineAdd.do")
	public @ResponseBody Map<?, ?> dpLineAdd(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {
		
		String keySet[] = Constant.personalDivisionListKeySet;
		String dpResultKeySet[] = Constant.dpResultListKeySet;

		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> rfcParamList = new HashMap<String, Object>();
		
		HashMap<String, Object> paramRow = new HashMap<String, Object>();

		for(String key : keySet){
			paramRow.put(key, params.containsKey("SEL_"+key) ? params.get("SEL_"+key) : "");
		}
		
		int dpResultCnt = Integer.parseInt((String) params.get("dpResultCnt"));
		ArrayList<HashMap<String, Object>> dpResult = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> dpResultRow = null;

		if(dpResultCnt == 1){

			dpResultRow = new HashMap<String, Object>();

			for(String key : dpResultKeySet){
				dpResultRow.put(key, params.containsKey(key) ? params.get(key) : "");
			}

			dpResult.add(dpResultRow);

		}else if(dpResultCnt > 1){
			for(int i=0 ; i<dpResultCnt ; i++){
				dpResultRow = new HashMap<String, Object>();
				for(String key : dpResultKeySet){
					dpResultRow.put(key, request.getParameterValues(key)[i]);
				}
				dpResult.add(dpResultRow);
			}
		}

		int ragPernrCnt = Integer.parseInt((String) params.get("ragPernrCnt"));
		ArrayList<HashMap<String, Object>> ragPernr = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> ragPernrRow = null;

		if(ragPernrCnt == 1){

			ragPernrRow = new HashMap<String, Object>();

			ragPernrRow.put("SIGN"  , params.containsKey("itRag_SIGN")   ? params.get("itRag_SIGN") : "");
			ragPernrRow.put("OPTION", params.containsKey("itRag_OPTION") ? params.get("itRag_OPTION") : "");
			ragPernrRow.put("LOW"   , params.containsKey("itRag_LOW")    ? params.get("itRag_LOW") : "");
			ragPernrRow.put("HIGH"  , params.containsKey("itRag_HIGH")   ? params.get("itRag_HIGH") : "");

			ragPernr.add(ragPernrRow);

		}else if(ragPernrCnt > 1){
			for(int i=0 ; i<ragPernrCnt ; i++){
				ragPernrRow = new HashMap<String, Object>();

				ragPernrRow.put("SIGN"  , request.getParameterValues("itRag_SIGN")[i]);
				ragPernrRow.put("OPTION", request.getParameterValues("itRag_OPTION")[i]);
				ragPernrRow.put("LOW"   , request.getParameterValues("itRag_LOW")[i]);
				ragPernrRow.put("HIGH"  , request.getParameterValues("itRag_HIGH")[i]);

				ragPernr.add(ragPernrRow);
			}
		}

		rfcParam.put("IM_LIST", paramRow);
		
		rfcParamList.put("IT_DP_RESULT", dpResult);
		rfcParamList.put("IT_RAG_PERNR", ragPernr);
		
		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_PD_033_APPEND_DP_LINE", rfcParam, rfcParamList, request);
		
		return result;
	}
}
