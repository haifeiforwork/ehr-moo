package com.lgcns.ikep4.portal.moorimmss.web.laborMng;

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
import com.lgcns.ikep4.portal.moorimess.web.rfcCache.service.RfcCacheService;
import com.lgcns.ikep4.portal.util.CacheRefreshUtil;
import com.lgcns.ikep4.portal.util.PagingUtils;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.jco.WebESSRcvRFC;
import com.lgcns.ikep4.util.lang.DateUtil;

@Controller
@RequestMapping(value="/portal/moorimmss/laborMng")
public class LaborMngMssController extends BaseController {

	@Autowired
	private WebESSRcvRFC webEssRcv;

	@Autowired
	private RfcCacheService rfcCacheService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/laborPositionList.do")
	public ModelAndView laborPositionList(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {
		
		ModelAndView mav = new ModelAndView("/portal/webmss/laborMng/laborPositionList");
		
		User user = (User) getRequestAttribute("ikep.user");
		
		if(!params.containsKey("hidImYear")){
			params.put("hidImYear", DateUtil.getToday("yyyy"));
		}
		
		if(!params.containsKey("hidImMonth")){
			params.put("hidImMonth", DateUtil.getToday("MM"));
		}
		
		if(params.containsKey("clearCache") && "Y".equals(params.get("clearCache"))){
			CacheRefreshUtil.refreshCache("rfcCache");
		}
		
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		
		rfcParam.put("IM_PERNR", user.getEmpNo());
		rfcParam.put("IM_YEAR" , params.get("hidImYear"));
		rfcParam.put("IM_MONTH", params.get("hidImMonth"));
		rfcParam.put("IM_WERKS", params.get("hidImWerks"));
		
		Map<?, ?> resultMapCode = webEssRcv.callRfcFunction("ZHR_RFC_PA_024_GET_LISTBOX", rfcParam, null, request);
		
		Map<?, ?> resultMap = rfcCacheService.callRfcFunctionSetCache("ZHR_RFC_PA_024_GET_DATA", rfcParam, null);
		
		ArrayList<Map<?, ?>> tempList = (ArrayList<Map<?, ?>>)resultMap.get("ET_LIST");
		
		ArrayList<Map<?, ?>> etList = PagingUtils.setPagingParam(tempList, params);
		
		mav.addObject("resultMapCode", resultMapCode);
		mav.addObject("resultMap"    , resultMap);
		mav.addObject("keySet"       , Constant.laborPositionListKeySet);
		mav.addObject("etList"       , etList);
		mav.addObject("params"       , params);
		
		return mav;
	}
	
	@RequestMapping(value = "/laborPositionView.do")
	public ModelAndView laborPositionView(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {
		
		ModelAndView mav = new ModelAndView("/portal/webmss/laborMng/laborPositionView");
		
		User user = (User) getRequestAttribute("ikep.user");
		
		String[] keySet = Constant.laborPositionListKeySet;
		
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> paramRow = new HashMap<String, Object>();
		
		for(String key : keySet){
			paramRow.put(key, params.get(key));
		}
		
		rfcParam.put("IM_PERNR", user.getEmpNo());
		rfcParam.put("IM_YEAR" , params.get("imYear"));
		rfcParam.put("IM_MONTH", params.get("imMonth"));
		rfcParam.put("IM_LIST" , paramRow);
		
		Map<?, ?> resultMap = webEssRcv.callRfcFunction("ZHR_RFC_PA_024_GET_DETAIL_DATA", rfcParam, null, request);
		
		mav.addObject("resultMap", resultMap);
		mav.addObject("params"   , params);
		
		return mav;
	}
	
	@RequestMapping(value = "/laborInterviewList.do")
	public ModelAndView laborInterviewList(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {
		
		ModelAndView mav = new ModelAndView("/portal/webmss/laborMng/laborInterviewList");
		
		User user = (User) getRequestAttribute("ikep.user");
		
		String keySet[] = Constant.laborInterviewListKeySet;
		
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> rfcParamList = new HashMap<String, Object>();
		
		rfcParam.put("IM_ADMIN"     , user.getEmpNo());
		rfcParam.put("IM_PERNR"     , params.get("imPernr"));
		rfcParam.put("IM_DATUM"     , DateUtil.getToday("yyyyMMdd"));
		
		Map<?, ?> resultMap = webEssRcv.callRfcFunction("ZHR_RFC_PD_023_ONINIT", rfcParam, rfcParamList, request);
	
		mav.addObject("resultMap" , resultMap);
		mav.addObject("keySet"    , keySet);
		mav.addObject("userKeySet", Constant.laborInterviewUserInfoKeySet);
		mav.addObject("params"    , params);
		
		return mav;
	}
	
	@RequestMapping(value = "/retrievePd023Oninput.do")
	public @ResponseBody Map<?, ?> retrievePd023Oninput(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		
		rfcParam.put("IM_ADMIN"   , user.getEmpNo());
		rfcParam.put("IM_EVENT_ID", params.get("imEventId"));
		rfcParam.put("IM_PERNR"   , params.get("imPernr"));
		rfcParam.put("IM_WORD"    , params.get("imWord"));
		
		HashMap<String, Object> paramRow = new HashMap<String, Object>();
		
		if("DELETE".equals((String)params.get("imEventId"))){
			String[] keySet = Constant.laborInterviewListKeySet;
			for(String key : keySet){
				paramRow.put(key, params.get(key));
			}
			rfcParam.put("IM_LIST", paramRow);
		}
		//RFC PARAMETER SETTING END

		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_PD_023_ONINPUT", rfcParam, null, request);

		return result;
	}
	
	@RequestMapping(value = "/laborInterviewView.do")
	public ModelAndView laborInterviewView(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {
		
		String jspUrl = "/portal/webmss/laborMng/laborInterviewView";
		String page = (String) params.get("imEventId");
		
		if("CREATE".equals(page) || "MODIFY".equals(page)){
			jspUrl = "/portal/webmss/laborMng/laborInterviewRegView";
		}
		
		if(!params.containsKey("tabIndex")){
			params.put("tabIndex", "0");
		}
		
		ModelAndView mav = new ModelAndView(jspUrl);
		
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		
		rfcParam.put("IM_EVENT_ID", params.get("imEventId"));
		rfcParam.put("IM_PERNR"   , params.get("imPernr"));
		rfcParam.put("IM_BEGDA"   , params.get("imBegda"));
		rfcParam.put("IM_ENDDA"   , params.get("imEndda"));
		rfcParam.put("IM_SEQNR"   , params.get("imSeqnr"));
		
		HashMap<String, Object> paramRow = new HashMap<String, Object>();
		
		String[] userKeySet = Constant.laborInterviewUserInfoKeySet;
		
		for(String key : userKeySet){
			paramRow.put(key, params.get(key));
		}
		
		rfcParam.put("IM_USERINFO", paramRow);
		
		Map<?, ?> resultMap = webEssRcv.callRfcFunction("ZHR_RFC_PD_023_ONINIT_DETAIL", rfcParam, null, request);
		
		Map<?, ?> resultHeader = webEssRcv.callRfcFunction("ZHR_RFC_PA_001_GET_DATA", rfcParam, null, request);
		mav.addObject("resultHeader", resultHeader);
		
		if(!params.containsKey("appointType")){
			params.put("appointType", "00");
		}

		rfcParam.put("IM_MASSN", params.get("appointType"));

		Map<?, ?> appointResult = webEssRcv.callRfcFunction("ZHR_RFC_PA_002_GET_DATA", rfcParam, null, request);
		mav.addObject("appointResult", appointResult);

		Map<?, ?> familyResult = webEssRcv.callRfcFunction("ZHR_RFC_PA_009_GET_DATA", rfcParam, null, request);
		mav.addObject("familyResult", familyResult);

		Map<?, ?> educationResult = webEssRcv.callRfcFunction("ZHR_RFC_PA_012_GET_DATA", rfcParam, null, request);
		mav.addObject("educationResult", educationResult);
	
		mav.addObject("resultMap" , resultMap);
		mav.addObject("keySet"    , Constant.laborInterviewProcessKeySet);
		mav.addObject("userKeySet", userKeySet);
		mav.addObject("params"    , params);
		
		return mav;
	}
	
	@RequestMapping(value = "/laborInterviewProcess.do")
	public @ResponseBody Map<?, ?> laborInterviewProcess(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {
		
		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		
		rfcParam.put("IM_EVENT_ID", params.get("imEventId"));
		rfcParam.put("IM_PERNR"   , params.get("imPernr"));
		
		HashMap<String, Object> paramRow = new HashMap<String, Object>();
		
		String[] keySet = Constant.laborInterviewProcessKeySet;
		for(String key : keySet){
			
			if("ITVDT".equals(key)){
				paramRow.put(key, params.get("date"));
			}else if("ITVLO".equals(key)){
				paramRow.put(key, params.get("place"));
			}else if("OBJ01".equals(key)){
				paramRow.put(key, params.get("private"));
			}else if("OBJ02".equals(key)){
				paramRow.put(key, params.get("business"));
			}else if("OBJ03".equals(key)){
				paramRow.put(key, params.get("suggestion"));
			}else if("OBJ04".equals(key)){
				paramRow.put(key, params.get("interviewOpinion"));
			}else if("OBJ05".equals(key)){
				paramRow.put(key, params.get("companyMeasure"));
			}else{
				paramRow.put(key, params.get("exList_"+key));
			}
		}
		rfcParam.put("IM_LIST", paramRow);
		
		//RFC PARAMETER SETTING END

		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_PD_023_ONINPUT_DETAIL", rfcParam, null, request);

		return result;
	}
}
