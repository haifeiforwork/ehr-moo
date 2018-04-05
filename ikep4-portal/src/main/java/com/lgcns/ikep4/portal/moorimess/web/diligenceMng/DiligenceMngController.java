
package com.lgcns.ikep4.portal.moorimess.web.diligenceMng;

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
import com.lgcns.ikep4.util.lang.DateUtil;

@Controller
@RequestMapping(value="/portal/moorimess/diligenceMng")
public class DiligenceMngController extends BaseController {

	@Autowired
	private WebESSRcvRFC webEssRcv;

	@RequestMapping(value = "/holidayWorkList.do")
	public ModelAndView holidayWorkList(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/webess/diligenceMng/holidayWorkList");

		User user = (User) getRequestAttribute("ikep.user");

		String thisYear = DateUtil.getToday("yyyy");

		if(!params.containsKey("imYear")){
			params.put("imYear", thisYear);
		}

		if(!params.containsKey("imMonth")){
			params.put("imMonth", "99");
		}

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> rfcParamList = new HashMap<String, Object>();

		rfcParam.put("IM_YEAR" , params.get("imYear"));
		rfcParam.put("IM_MONTH", params.get("imMonth"));
		rfcParam.put("IM_PERNR", user.getEmpNo());

		Map<?, ?> resultMapCode = webEssRcv.callRfcFunction("ZHR_RFC_PT_003_GET_LISTBOX", rfcParam, null, request);

		rfcParamList.put("IT_ASTAT", resultMapCode.get("ET_ASTAT"));
		rfcParamList.put("IT_PREAS", resultMapCode.get("ET_PREAS"));
		//RFC PARAMETER SETTING END

		Map<?, ?> resultMapList = webEssRcv.callRfcFunction("ZHR_RFC_PT_003_GET_DATA", rfcParam, rfcParamList, request);

		mav.addObject("resultList", resultMapList.get("ET_LIST"));//목록 리스트
		mav.addObject("yearList"  , resultMapCode.get("ET_YEAR"));//연도 코드
		mav.addObject("monthList" , resultMapCode.get("ET_MONTH"));//달 코드
		mav.addObject("keySet"    , Constant.holidayWorkListKeySet);//리스트 키셋
		mav.addObject("params"    , params);

		return mav;
	}

	@RequestMapping(value = "checkedRowValidationPT003.do")
	public @ResponseBody Map<?, ?> checkedRowValidationPT003(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> paramRow = new HashMap<String, Object>();

		rfcParam.put("IM_PERNR", user.getEmpNo());
		rfcParam.put("IM_EVENT_ID", params.get("eventId"));

		String keySet[] = Constant.holidayWorkListKeySet;

		for(String key : keySet){
			paramRow.put(key, ((String)params.get(key)).trim());
		}
		rfcParam.put("IM_LIST", paramRow);
		//RFC PARAMETER SETTING END

		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_PT_003_CHECK_SEL_LINE", rfcParam, null, request);

		return result;
	}

	@RequestMapping(value = "/holidayWorkView.do")
	public ModelAndView holidayWorkView(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		String jspUrl = "/portal/webess/diligenceMng/holidayWorkRegView";
		String page = "CREATE";

		if(params.containsKey("jspType") && "view".equals((String)params.get("jspType"))){
			jspUrl = "/portal/webess/diligenceMng/holidayWorkView";
			page = "DISPLAY";
		}else{
			if(params.containsKey("dataLoad") && "Y".equals((String)params.get("dataLoad"))){
				page = "CHANGE";
			}
		}

		ModelAndView mav = new ModelAndView(jspUrl);

		User user = (User) getRequestAttribute("ikep.user");

		//exFlag로 데이터 구분을 해야한다.
		//exFlag == "" 이면 작성이나, 수정으로 들어온 화면
		//exFlag == "CR" 이면 변경 요청으로 들어온 화면
		//exFlag == "D" 이면 삭제로 들어온 화면
		//exFlag == "C" 이면 취소신청으로 들어온 화면
		//param.get("dataLoad")의 유무로 (작성)인지 (수정, 변경요청)인지 분기

		//RFC PARAMETER SETTING START

		String keySet[] = Constant.holidayWorkListKeySet;

		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> rfcParamRow = new HashMap<String, Object>();

		rfcParam.put("IM_INFTY", "2005");
		rfcParam.put("IM_PERNR", user.getEmpNo());
		rfcParam.put("IM_PAGE" , page);

		if(!"".equals(page)){
			for(String key : keySet){
				rfcParamRow.put(key, params.get(key));
			}
			rfcParam.put("IM_LIST", rfcParamRow);
		}else{
			rfcParam.put("IM_LIST", null);
		}
		//RFC PARAMETER SETTING END

		if(!"CREATE".equals(page)){
			for(String key : keySet){
				params.put("SEL_"+key, params.get(key));
			}
		}

		Map<?, ?> resultMapCode = webEssRcv.callRfcFunction("ZHR_RFC_PT_003_GET_LISTBOX", rfcParam, null, request);

		Map<?, ?> resultMapList = webEssRcv.callRfcFunction("ZHR_RFC_PT_003_GET_APPLINE", rfcParam, null, request);

		String astat = (String) params.get("ASTAT");

		if("05".equals(astat) || "16".equals(astat) || "13".equals(astat)){

			rfcParam.put("IM_APPID", params.get("APPID"));

			Map<?, ?> resultRefuse = webEssRcv.callRfcFunction("ZHR_RFC_READ_REFUSE_REASON", rfcParam, null, request);
			mav.addObject("resultRefuse", resultRefuse);
		}

		Map<?, ?> exList = (Map<?, ?>) resultMapList.get("EX_LIST");

		if(!"CREATE".equals(page)){
			for(String key : keySet){
				params.put(key, exList.get(key));
			}
		}

		mav.addObject("resultMapList", resultMapList);//결재자 리스트
		mav.addObject("reasonList"   , resultMapCode.get("ET_PREAS"));//사유 코드
		mav.addObject("timeList"     , resultMapCode.get("ET_TIME"));//시간 코드
		mav.addObject("keySet"       , keySet);//리스트 선택 키셋
		mav.addObject("apprKeySet"   , Constant.apprKeySet);//결재자 리스트 키셋
		mav.addObject("params"       , params);

		return mav;
	}

	@RequestMapping(value = "/holidayWorkProcess.do")
	public @ResponseBody Map<?, ?> holidayWorkProcess(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> rfcParamList = new HashMap<String, Object>();

		rfcParam.put("IM_PERNR"   , user.getEmpNo());
		rfcParam.put("IM_EVENT_ID", params.get("eventId"));
		rfcParam.put("IM_PAGE"    , params.get("page"));
		rfcParam.put("IM_FLAG"    , params.get("exFlag"));
		rfcParam.put("IM_INFTY"   , "2005");

		HashMap<String, Object> paramRow = new HashMap<String, Object>();//IM_LIST
		HashMap<String, Object> selParamRow = new HashMap<String, Object>();//IM_LIST_SEL

		//리스트에서 선택한 데이터 추출, 입력한 데이터 추출
		String keySet[] = Constant.holidayWorkListKeySet;

		for(String key : keySet){
			selParamRow.put(key, params.containsKey("SEL_"+key) ? params.get("SEL_"+key) : "");

			if("BEGDA".equals(key) || "ENDDA".equals(key)){
				paramRow.put(key, ((String)params.get("holidayWorkDate")).replaceAll("-", ""));
			}else if("PREAS".equals(key)){
				paramRow.put(key, params.get("reason"));
			}else if("STDAZ".equals(key) || "STIME".equals(key)){
				paramRow.put(key, params.get("time"));
			}else if("DESC1".equals(key)){
				paramRow.put(key, params.get("desc1"));
			}else if("DESC2".equals(key)){
				paramRow.put(key, params.get("desc2"));

			}else{
				paramRow.put(key, params.containsKey("SEL_"+key) ? params.get("SEL_"+key) : "");
			}
		}

		//결재라인 추출
		ArrayList<HashMap<String, Object>> apprList = new ArrayList<HashMap<String, Object>>();//IT_LINE
		HashMap<String, Object> apprRow = null;

		String apprKeySet[] = Constant.apprKeySet;
		int apprCnt = Integer.parseInt(((String)params.get("apprCnt")));

		if(apprCnt == 1){
			apprRow = new HashMap<String, Object>();//IT_LINE(row)
			for(String apprKey : apprKeySet){
				apprRow.put(apprKey, params.containsKey(apprKey) ? params.get(apprKey) : "");
			}
			apprList.add(apprRow);
		}else if(apprCnt > 1){
			for(int i=0 ; i<apprCnt ; i++){
				apprRow = new HashMap<String, Object>();//IT_LINE(row)
				for(String apprKey : apprKeySet){
					apprRow.put(apprKey, request.getParameterValues(apprKey)[i]);
				}
				apprList.add(apprRow);
			}
		}

		rfcParam.put("IM_LIST"    , paramRow);
		rfcParam.put("IM_LIST_SEL", selParamRow);

		//코드값 추출
		Map<?, ?> resultMapCode = webEssRcv.callRfcFunction("ZHR_RFC_PT_003_GET_LISTBOX", rfcParam, null, request);

		Map<?, ?> resultMapAppline = webEssRcv.callRfcFunction("ZHR_RFC_PT_003_GET_APPLINE", rfcParam, null, request);

		rfcParamList.put("IT_LINE" , apprList);
		rfcParamList.put("IT_APPNR", resultMapAppline.get("ET_APPNR"));

		rfcParamList.put("IT_PREAS", resultMapCode.get("ET_PREAS"));
		rfcParamList.put("IT_ASTAT", resultMapCode.get("ET_ASTAT"));
		//RFC PARAMETER SETTING END

		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_PT_003_PROCESS", rfcParam, rfcParamList, request);

		return result;
	}

	@RequestMapping(value = "/apprLinePop.do")
	public ModelAndView apprLinePop(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/webess/diligenceMng/apprLinePop");

		User user = (User) getRequestAttribute("ikep.user");

		/*
		String functionName = "";
		String keySet[] = {};
		String imInfty = "";

		if("PT002".equals((String)params.get("menuType"))){
			functionName = "ZHR_RFC_PT_002_GET_APPLINE";
			keySet = Constant.holidayListKeySet;
			imInfty = "2001";
		}else if("PT008".equals((String)params.get("menuType"))){
			functionName = "ZHR_RFC_PT_008_GET_APPLINE";
			keySet = Constant.businessTripListKeySet;
			imInfty = "2002";
		}else if("PT003".equals((String)params.get("menuType"))){
			functionName = "ZHR_RFC_PT_003_GET_APPLINE";
			keySet = Constant.holidayWorkListKeySet;
			imInfty = "2005";
		}else if("PT033".equals((String)params.get("menuType"))){
			functionName = "ZHR_RFC_PT_033_GET_APPLINE";
			keySet = Constant.leaveReinstatementKeySet;
			imInfty = "9208";
		}else if("BE004".equals((String)params.get("menuType"))){
			functionName = "ZHR_RFC_BE_004_GET_APPLINE";
			keySet = Constant.congratulateListKeySet;
			imInfty = "9502";
		}else if("PA006".equals((String)params.get("menuType"))){
			functionName = "ZHR_RFC_PT_006_GET_APPLINE";
			keySet = Constant.expenseListKeySet;
			imInfty = "9999";
		}

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> paramRow = new HashMap<String, Object>();

		for(String key : keySet){
			paramRow.put(key, params.get(key));
		}

		rfcParam.put("IM_INFTY", imInfty);
		rfcParam.put("IM_PERNR", user.getEmpNo());
		rfcParam.put("IM_LIST" , paramRow);
		rfcParam.put("IM_PAGE" , params.get("page"));
		//RFC PARAMETER SETTING END

		Map<?, ?> resultMapList = webEssRcv.callRfcFunction(functionName, rfcParam, null, request);
		*/
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		rfcParam.put("IM_PERNR", user.getEmpNo());

		Map<?, ?> resultMapList = webEssRcv.callRfcFunction("ZHR_RFC_GET_APPNR", rfcParam, null, request);

		mav.addObject("apprAbleKeySet", Constant.apprAblePopKeySet);//결재 가능자 리스트 키셋
		mav.addObject("apprAbleList"  , resultMapList.get("EX_APPNR"));//결재가능자 리스트
		mav.addObject("params"        , params);

		return mav;
	}

	@RequestMapping(value = "/holidayList.do")
	public ModelAndView holidayList(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/webess/diligenceMng/holidayList");

		User user = (User) getRequestAttribute("ikep.user");

		String thisYear = DateUtil.getToday("yyyy");

		if(!params.containsKey("imYear")){
			params.put("imYear", thisYear);
		}

		if(!params.containsKey("imMonth")){
			params.put("imMonth", "99");
		}

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> rfcParamList = new HashMap<String, Object>();

		rfcParam.put("IM_YEAR" , params.get("imYear"));
		rfcParam.put("IM_MONTH", params.get("imMonth"));
		rfcParam.put("IM_PERNR", user.getEmpNo());
		rfcParam.put("IM_FLAG" , "");
		//RFC PARAMETER SETTING END

		Map<?, ?> resultMapCode = webEssRcv.callRfcFunction("ZHR_RFC_PT_002_GET_LISTBOX", rfcParam, null, request);

		rfcParam.put("IM_MOABW", resultMapCode.get("EX_MOABW"));

		rfcParamList.put("IT_ASTAT", resultMapCode.get("ET_ASTAT"));
		rfcParamList.put("IT_AWART", resultMapCode.get("ET_AWART"));

		Map<?, ?> resultMapList = webEssRcv.callRfcFunction("ZHR_RFC_PT_002_GET_DATA", rfcParam, rfcParamList, request);

		params.put("EX_BTRTL", resultMapList.get("EX_BTRTL"));

		mav.addObject("resultList", resultMapList.get("ET_LIST"));//목록 리스트
		mav.addObject("yearList"  , resultMapCode.get("ET_YEAR"));//연도 코드
		mav.addObject("monthList" , resultMapCode.get("ET_MONTH"));//달 코드
		mav.addObject("keySet"    , Constant.holidayListKeySet);//리스트 키셋
		mav.addObject("params"    , params);

		return mav;
	}

	@RequestMapping(value = "checkedRowValidationPT002.do")
	public @ResponseBody Map<?, ?> checkedRowValidationPT002(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		rfcParam.put("IM_PERNR", user.getEmpNo());
		rfcParam.put("IM_EVENT_ID", params.get("eventId"));

		HashMap<String, Object> paramRow = new HashMap<String, Object>();

		String keySet[] = Constant.holidayListKeySet;

		for(String key : keySet){
			paramRow.put(key, ((String)params.get(key)).trim());
		}

		rfcParam.put("IM_LIST", paramRow);
		//RFC PARAMETER SETTING END

		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_PT_002_CHECK_SEL_LINE", rfcParam, null, request);

		return result;
	}

	@RequestMapping(value = "/holidayView.do")
	public ModelAndView holidayView(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		String jspUrl = "/portal/webess/diligenceMng/holidayRegView";
		String page = "CREATE";

		if(params.containsKey("jspType") && "view".equals((String)params.get("jspType"))){
			jspUrl = "/portal/webess/diligenceMng/holidayView";
			page = "DISPLAY";
		}else{
			if(params.containsKey("dataLoad") && "Y".equals((String)params.get("dataLoad"))){
				page = "CHANGE";
			}
		}

		ModelAndView mav = new ModelAndView(jspUrl);

		User user = (User) getRequestAttribute("ikep.user");

		//exFlag로 데이터 구분을 해야한다.
		//exFlag == "" 이면 작성이나, 수정으로 들어온 화면
		//exFlag == "CR" 이면 변경 요청으로 들어온 화면
		//exFlag == "D" 이면 삭제로 들어온 화면
		//exFlag == "C" 이면 취소신청으로 들어온 화면

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> paramRow = new HashMap<String, Object>();

		rfcParam.put("IM_PERNR", user.getEmpNo());
		rfcParam.put("IM_FLAG" , params.get("exFlag"));
		rfcParam.put("IM_PAGE" , page);
		rfcParam.put("IM_INFTY", "2001");
		rfcParam.put("IM_BTRTL", params.get("exBtrtl"));

		String keySet[] = Constant.holidayListKeySet;

		for(String key : keySet){
			paramRow.put(key, params.get(key));
		}

		rfcParam.put("IM_LIST", paramRow);
		//RFC PARAMETER SETTING END

		if(!"CREATE".equals(page)){
			for(String key : keySet){
				params.put("SEL_"+key, params.get(key));
			}
		}

		Map<?, ?> resultMapCode = webEssRcv.callRfcFunction("ZHR_RFC_PT_002_GET_LISTBOX", rfcParam, null, request);

		Map<?, ?> resultMapList = webEssRcv.callRfcFunction("ZHR_RFC_PT_002_GET_APPLINE", rfcParam, null, request);

		String astat = (String) params.get("ASTAT");

		if("05".equals(astat) || "16".equals(astat) || "13".equals(astat)){

			rfcParam.put("IM_APPID", params.get("APPID"));

			Map<?, ?> resultRefuse = webEssRcv.callRfcFunction("ZHR_RFC_READ_REFUSE_REASON", rfcParam, null, request);
			mav.addObject("resultRefuse", resultRefuse);
		}

		Map<?, ?> exList = (Map<?, ?>)resultMapList.get("EX_LIST");

		if(!"CREATE".equals(page)){
			for(String key : keySet){
				params.put(key, exList.get(key));
			}
		}

		Map<?, ?> resultMapQuarter = webEssRcv.callRfcFunction("ZHR_RFC_PT_002_GET_QUOTA", rfcParam, null, request);

		mav.addObject("resultMapList"  , resultMapList);//결재자 리스트
		mav.addObject("holidayTypeList", resultMapCode.get("ET_AWART"));//휴가 유형 리스트
		mav.addObject("quarterList"    , resultMapQuarter.get("ET_QUOTA"));//휴가 쿼터 리스트
		mav.addObject("keySet"         , Constant.holidayListKeySet);//리스트 선택 키셋
		mav.addObject("apprKeySet"     , Constant.apprKeySet);//결재자 리스트 키셋
		mav.addObject("params"         , params);

		return mav;
	}

	@RequestMapping(value = "/holidayProcess.do")
	public @ResponseBody Map<?, ?> holidayProcess(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> rfcParamList = new HashMap<String, Object>();

		rfcParam.put("IM_PERNR"   , user.getEmpNo());
		rfcParam.put("IM_EVENT_ID", params.get("eventId"));
		rfcParam.put("IM_PAGE"    , params.get("page"));
		rfcParam.put("IM_FLAG"    , params.get("exFlag"));
		rfcParam.put("IM_INFTY"   , "2001");

		HashMap<String, Object> paramRow = new HashMap<String, Object>();//IM_LIST
		HashMap<String, Object> selParamRow = new HashMap<String, Object>();//IM_LIST_SEL

		//리스트에서 선택한 데이터 추출, 입력한 데이터 추출
		String keySet[] = Constant.holidayListKeySet;

		for(String key : keySet){
			selParamRow.put(key, params.containsKey("SEL_"+key) ? params.get("SEL_"+key) : "");

			if("AWART".equals(key)){
				paramRow.put(key, params.get("holidayType"));
			}else if("ATEXT".equals(key)){
				paramRow.put(key, params.get("holidayTypeNm"));
			}else if("BEGDA".equals(key)){
				paramRow.put(key, params.get("startDate"));
			}else if("ENDDA".equals(key)){
				paramRow.put(key, params.get("endDate"));
			}else if("DESC1".equals(key)){
				paramRow.put(key, params.get("desc1"));
			}else if("DESC2".equals(key)){
				paramRow.put(key, params.get("desc2"));
			}else if("ABWTGT".equals(key) || "ABWTG".equals(key)){
				paramRow.put(key, params.get("exAbwtg"));
			}else{
				paramRow.put(key, params.containsKey("SEL_"+key) ? params.get("SEL_"+key) : "");
			}
		}

		//결재라인 추출
		ArrayList<HashMap<String, Object>> apprList = new ArrayList<HashMap<String, Object>>();//IT_LINE
		HashMap<String, Object> apprRow = null;

		String apprKeySet[] = Constant.apprKeySet;
		int apprCnt = Integer.parseInt(((String)params.get("apprCnt")));

		if(apprCnt == 1){
			apprRow = new HashMap<String, Object>();//IT_LINE(row)
			for(String apprKey : apprKeySet){
				apprRow.put(apprKey, params.containsKey(apprKey) ? params.get(apprKey) : "");
			}
			apprList.add(apprRow);
		}else if(apprCnt > 1){
			for(int i=0 ; i<apprCnt ; i++){
				apprRow = new HashMap<String, Object>();//IT_LINE(row)
				for(String apprKey : apprKeySet){
					apprRow.put(apprKey, request.getParameterValues(apprKey)[i]);
				}
				apprList.add(apprRow);
			}
		}

		rfcParam.put("IM_LIST"    , paramRow);
		rfcParam.put("IM_LIST_SEL", selParamRow);

		//코드값 추출
		Map<?, ?> resultMapCode = webEssRcv.callRfcFunction("ZHR_RFC_PT_002_GET_LISTBOX", rfcParam, null, request);

		Map<?, ?> resultMapAppline = webEssRcv.callRfcFunction("ZHR_RFC_PT_002_GET_APPLINE", rfcParam, null, request);

		rfcParamList.put("IT_LINE" , apprList);
		rfcParamList.put("IT_APPNR", resultMapAppline.get("ET_APPNR"));

		rfcParamList.put("IT_AWART", resultMapCode.get("ET_AWART"));
		rfcParamList.put("IT_ASTAT", resultMapCode.get("ET_ASTAT"));
		//RFC PARAMETER SETTING END

		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_PT_002_PROCESS", rfcParam, rfcParamList, request);

		return result;
	}

	@RequestMapping(value = "retrieveWeekForDay.do")
	public @ResponseBody Map<?, ?> retrieveWeekForDay(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		rfcParam.put("IM_DATUM", params.get("date"));
		//RFC PARAMETER SETTING END

		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_GET_WEEK_FOR_DAY", rfcParam, null, request);

		return result;
	}

	@RequestMapping(value = "retrieveCalculatorHoliday.do")
	public @ResponseBody Map<?, ?> retrieveCalculatorHoliday(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		rfcParam.put("IM_PERNR", user.getEmpNo());
		rfcParam.put("IM_BEGDA", params.get("startDate"));
		rfcParam.put("IM_ENDDA", params.get("endDate"));
		rfcParam.put("IM_AWART", params.get("holidayType"));

		//RFC PARAMETER SETTING END

		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_PT_002_CAL_VOCATION_DA", rfcParam, null, request);

		return result;
	}

	@RequestMapping(value = "/businessTripList.do")
	public ModelAndView businessTripList(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/webess/diligenceMng/businessTripList");

		User user = (User) getRequestAttribute("ikep.user");

		String thisYear = DateUtil.getToday("yyyy");

		if(!params.containsKey("imYear")){
			params.put("imYear", thisYear);
		}

		if(!params.containsKey("imMonth")){
			params.put("imMonth", "99");
		}

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		rfcParam.put("IM_PERNR", user.getEmpNo());
		rfcParam.put("IM_YEAR" , params.get("imYear"));
		rfcParam.put("IM_MONTH", params.get("imMonth"));
		//RFC PARAMETER SETTING END

		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_PT_008_GET_DATA", rfcParam, null, request);

		Map<?, ?> resultCode = webEssRcv.callRfcFunction("ZHR_RFC_PT_008_GET_LISTBOX", rfcParam, null, request);

		mav.addObject("resultList", result.get("ET_LIST"));//목록 리스트
		mav.addObject("keySet"    , Constant.businessTripListKeySet);//리스트 키셋
		mav.addObject("yearList"  , resultCode.get("ET_YEAR"));
		mav.addObject("monthList" , resultCode.get("ET_MONTH"));
		mav.addObject("params"    , params);

		return mav;
	}

	@RequestMapping(value = "checkedRowValidationPT008.do")
	public @ResponseBody Map<?, ?> checkedRowValidationPT008(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		rfcParam.put("IM_PERNR", user.getEmpNo());
		rfcParam.put("IM_EVENT_ID", params.get("eventId"));

		HashMap<String, Object> paramRow = new HashMap<String, Object>();

		String keySet[] = Constant.businessTripListKeySet;

		for(String key : keySet){
			paramRow.put(key, ((String)params.get(key)).trim());
		}

		rfcParam.put("IM_LIST", paramRow);
		//RFC PARAMETER SETTING END

		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_PT_008_CHECK_SEL_LINE", rfcParam, null, request);

		return result;
	}

	@RequestMapping(value = "/businessTripView.do")
	public ModelAndView businessTripView(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		String jspUrl = "/portal/webess/diligenceMng/businessTripRegView";
		String page = "CREATE";

		if(params.containsKey("jspType") && "view".equals((String)params.get("jspType"))){
			jspUrl = "/portal/webess/diligenceMng/businessTripView";
			page = "DISPLAY";
		}else{
			if(params.containsKey("dataLoad") && "Y".equals((String)params.get("dataLoad"))){
				page = "CHANGE";
			}
		}

		ModelAndView mav = new ModelAndView(jspUrl);

		User user = (User) getRequestAttribute("ikep.user");

		//exFlag로 데이터 구분을 해야한다.
		//exFlag == "" 이면 작성이나, 수정으로 들어온 화면
		//exFlag == "CR" 이면 변경 요청으로 들어온 화면
		//exFlag == "D" 이면 삭제로 들어온 화면
		//exFlag == "C" 이면 취소신청으로 들어온 화면
		//param.get("dataLoad")의 유무로 (작성)인지 (수정, 변경요청)인지 분기

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> paramRow = new HashMap<String, Object>();

		String keySet[] = Constant.businessTripListKeySet;

		for(String key : keySet){

			//작성일 경우 IM_LIST 초기화
			if("CREATE".equals(page)){
				if("ZCNTYP".equals(key)){
					paramRow.put(key, "1");
				}else if("ZTIME_BEG".equals(key)){
					paramRow.put(key, "09");
				}else if("ZTIME_END".equals(key)){
					paramRow.put(key, "09");
				}else if("COUNTRY_END".equals(key)){
					paramRow.put(key, "KR");
				}else{
					paramRow.put(key, params.get(key));
				}
			}else{
				paramRow.put(key, params.get(key));
			}
		}

		rfcParam.put("IM_LIST", paramRow);

		Map<?, ?> resultMapDetail = null;

		if(!"CREATE".equals(page)){
			resultMapDetail = webEssRcv.callRfcFunction("ZHR_RFC_PT_008_GET_DETAIL_DATA", rfcParam, null, request);
		}

		rfcParam.put("IM_PERNR", user.getEmpNo());
		Map<?, ?> resultMapCode = webEssRcv.callRfcFunction("ZHR_RFC_PT_008_GET_LISTBOX", rfcParam, null, request);

		rfcParam.put("IM_PAGE" , page);
		rfcParam.put("IM_INFTY", "2002");

		Map<?, ?> resultMapList = webEssRcv.callRfcFunction("ZHR_RFC_PT_008_GET_APPLINE", rfcParam, null, request);

		String astat = (String) params.get("ASTAT");

		if("05".equals(astat) || "16".equals(astat) || "13".equals(astat)){

			rfcParam.put("IM_APPID", params.get("APPID"));

			Map<?, ?> resultRefuse = webEssRcv.callRfcFunction("ZHR_RFC_READ_REFUSE_REASON", rfcParam, null, request);
			mav.addObject("resultRefuse", resultRefuse);
		}
		//RFC PARAMETER SETTING END

		Map<?, ?> exList = (Map<?, ?>)resultMapList.get("EX_LIST");

		for(String key : keySet){
			if(!"CREATE".equals(page)){
				params.put("SEL_"+key, params.get(key));
				params.put(key, exList.get(key));
			}else{
				if("ZCNTYP".equals(key)){
					params.put(key, "1");
				}else if("ZTIME_BEG".equals(key)){
					params.put(key, "09");
				}else if("ZTIME_END".equals(key)){
					params.put(key, "09");
				}else if("COUNTRY_END".equals(key)){
					params.put(key, "KR");
				}
			}
		}

		mav.addObject("resultMapList", resultMapList);//결재자 리스트
		mav.addObject("tripDivList"  , resultMapCode.get("ET_COUNTRY"));//출장 구분
		mav.addObject("tripTypeList" , resultMapCode.get("ET_TYPE"));//출장 유형
		mav.addObject("transList"    , resultMapCode.get("ET_TRANS"));//출발 교통
		mav.addObject("gradeAList"   , resultMapCode.get("ET_GRADEA"));//교통 등급
		mav.addObject("hourList"     , resultMapCode.get("ET_TIME"));//시간 리스트
		mav.addObject("minList"      , resultMapCode.get("ET_MINT"));//분 리스트
		mav.addObject("gradeBList"   , resultMapCode.get("ET_GRADEB"));//복귀등급

		if(resultMapDetail != null){
			mav.addObject("exAList", resultMapDetail.get("EX_ALIST"));//A리스트
			mav.addObject("exBList", resultMapDetail.get("EX_BLIST"));//B리스트
			mav.addObject("exEList", resultMapDetail.get("EX_ELIST"));//E리스트
			mav.addObject("exRList", resultMapDetail.get("ET_RLIST"));//R리스트
		}else{
			HashMap<String, Object> exAList = new HashMap<String, Object>();

			exAList.put("DATVS", DateUtil.getToday("yyyyMMdd"));
			exAList.put("WAERS", "KRW");
			exAList.put("KURSV", "1.00000");

			mav.addObject("exAList", exAList);//작성일 경우 A리스트 초기화
		}

		mav.addObject("keySet"     , Constant.businessTripListKeySet);//리스트 선택 키셋
		mav.addObject("apprKeySet" , Constant.apprKeySet);//결재자 리스트 키셋
		mav.addObject("aListKeySet", Constant.aListKeySet);//a 리스트 키셋
		mav.addObject("eListKeySet", Constant.eListKeySet);//e 리스트 키셋
		mav.addObject("bListKeySet", Constant.bListKeySet);//b 리스트 키셋
		mav.addObject("rListKeySet", Constant.rListKeySet);//r 리스트 키셋
		mav.addObject("params"     , params);

		return mav;
	}


	@RequestMapping(value = "/businessTripProcess.do")
	public @ResponseBody Map<?, ?> businessTripProcess(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> rfcParamList = new HashMap<String, Object>();

		rfcParam.put("IM_PERNR"   , user.getEmpNo());
		rfcParam.put("IM_OPERA"   , "");
		rfcParam.put("IM_EVENT_ID", params.get("eventId"));
		rfcParam.put("IM_PAGE"    , params.get("page"));
		rfcParam.put("IM_FLAG"    , params.get("exFlag"));
		rfcParam.put("IM_INFTY"   , "2002");

		HashMap<String, Object> paramRow = new HashMap<String, Object>();//IM_LIST
		HashMap<String, Object> selParamRow = new HashMap<String, Object>();//IM_LIST_SEL

		HashMap<String, Object> aParamRow = new HashMap<String, Object>();//IM_ALIST
		HashMap<String, Object> eParamRow = new HashMap<String, Object>();//IM_ELIST
		HashMap<String, Object> bParamRow = new HashMap<String, Object>();//IM_BLIST


		//리스트에서 선택한 데이터 추출, 입력한 데이터 추출
		String keySet[] = Constant.businessTripListKeySet;

		String aListKeySet[] = Constant.aListKeySet;
		String eListKeySet[] = Constant.eListKeySet;
		String bListKeySet[] = Constant.bListKeySet;

		for(String key : keySet){
			selParamRow.put(key, params.containsKey("SEL_"+key) ? params.get("SEL_"+key) : "");

			/*
			ZCNTYP			1.국내 2.해외
			DATE_BEG		출장 시작일
			DATE_END		출장 종료일
			ZTIME_BEG		시작시간
			ZMINT_BEG		시작분
			ZTIME_END		종료시간
			ZMINT_END		종료분
			ACTIVITY_TYPE	출장 유형
			LOCATION_END	최초출장지
			REQUEST_REASON	출장 사유
			COUNTRY_END		출장국가 (해외일 경우 입력)
			CNTEXT			출장국가 (해외일 경우 입력)
			*/

			if("ZCNTYP".equals(key)){
				paramRow.put(key, params.get("tripDiv"));
			}else if("DATE_BEG".equals(key)){
				paramRow.put(key, params.get("startDate"));
			}else if("DATE_END".equals(key)){
				paramRow.put(key, params.get("endDate"));
			}else if("ZTIME_BEG".equals(key)){
				paramRow.put(key, params.get("startHour"));
			}else if("ZMINT_BEG".equals(key)){
				paramRow.put(key, params.get("startMin"));
			}else if("ZTIME_END".equals(key)){
				paramRow.put(key, params.get("endHour"));
			}else if("ZMINT_END".equals(key)){
				paramRow.put(key, params.get("endMin"));
			}else if("ACTIVITY_TYPE".equals(key)){
				paramRow.put(key, params.get("tripType"));
			}else if("LOCATION_END".equals(key)){
				paramRow.put(key, params.get("tripPlace"));
			}else if("REQUEST_REASON".equals(key)){
				paramRow.put(key, params.get("tripResason"));
			}else{
				paramRow.put(key, params.containsKey("SEL_"+key) ? params.get("SEL_"+key) : "");
			}
		}

		for(String aListKey : aListKeySet){

			/*
			VORSC	금액
			WAERS	통화 키
			KURSV	환율
			VORHW	환산금액
			DATVS	환율적용일자
			*/

			if("VORSC".equals(aListKey)){
				aParamRow.put(aListKey, ((String)params.get("amount")).replaceAll(",", ""));
			}else if("WAERS".equals(aListKey)){
				aParamRow.put(aListKey, params.get("currency"));
			}else if("KURSV".equals(aListKey)){
				aParamRow.put(aListKey, params.get("exchangeRate"));
			}else if("VORHW".equals(aListKey)){
				aParamRow.put(aListKey, ((String)params.get("exchangeAmount")).replaceAll(",", ""));
			}else if("DATVS".equals(aListKey)){
				aParamRow.put(aListKey, params.get("exchangeApplyDate"));
			}else{
				aParamRow.put(aListKey, params.containsKey("ALIST_"+aListKey) ? params.get("ALIST_"+aListKey) : "");
			}
		}

		for(String eListKey : eListKeySet){

			/*
			LTEXT	주석
			*/

			if("LTEXT".equals(eListKey)){
				eParamRow.put(eListKey, params.get("comment"));
			}else{
				eParamRow.put(eListKey, params.containsKey("ELIST_"+eListKey) ? params.get("ELIST_"+eListKey) : "");
			}
		}

		for(String bListKey : bListKeySet){

			/*
			TRANS_BEG	출발교통편
			TRANSGR_BEG	교통편등급
			TRANS_END	복귀교통편
			TRANSGR_END	교통편등급
			*/

			if("TRANS_BEG".equals(bListKey)){
				bParamRow.put(bListKey, params.get("startTrans"));
			}else if("TRANSGR_BEG".equals(bListKey)){
				bParamRow.put(bListKey, params.get("startGrade"));
			}else if("TRANS_END".equals(bListKey)){
				bParamRow.put(bListKey, params.get("endTrans"));
			}else if("TRANSGR_END".equals(bListKey)){
				bParamRow.put(bListKey, params.get("endGrade"));
			}else{
				bParamRow.put(bListKey, params.containsKey("BLIST_"+bListKey) ? params.get("BLIST_"+bListKey) : "");
			}
		}

		//결재라인 추출
		ArrayList<HashMap<String, Object>> apprList = new ArrayList<HashMap<String, Object>>();//IT_LINE
		HashMap<String, Object> apprRow = null;

		String apprKeySet[] = Constant.apprKeySet;
		int apprCnt = Integer.parseInt(((String)params.get("apprCnt")));

		if(apprCnt == 1){
			apprRow = new HashMap<String, Object>();//IT_LINE(row)
			for(String apprKey : apprKeySet){
				apprRow.put(apprKey, params.containsKey(apprKey) ? params.get(apprKey) : "");
			}
			apprList.add(apprRow);
		}else if(apprCnt > 1){
			for(int i=0 ; i<apprCnt ; i++){
				apprRow = new HashMap<String, Object>();//IT_LINE(row)
				for(String apprKey : apprKeySet){
					apprRow.put(apprKey, request.getParameterValues(apprKey)[i]);
				}
				apprList.add(apprRow);
			}
		}

		//RLIST 추출
		ArrayList<HashMap<String, Object>> tripPlaceList = new ArrayList<HashMap<String, Object>>();//IT_LINE
		HashMap<String, Object> tripPlaceRow = null;

		String rListKeySet[] = Constant.rListKeySet;
		int addTripPlaceCnt = Integer.parseInt(((String)params.get("addTripPlaceCnt")));

		/*
		DATE_BEG		출장 세그먼트 시작일
		LOCATION_END	출장지
		COUNTRY_END		국가 키
		REASON			사유
		ZCNTXT			국가명
		ZTIME_BEG		출발시간
		ZTIME_END		종료시간
		ZMINT_END		종료시간(분)
		ZMINT_BEG		출발시간(분)
		ACTIVITYT		출장유형명
		*/

		if(addTripPlaceCnt == 1){
			tripPlaceRow = new HashMap<String, Object>();//IT_RLIST(row)
			for(String rListKey : rListKeySet){
				tripPlaceRow.put(rListKey, params.containsKey("RLIST_"+rListKey) ? params.get("RLIST_"+rListKey) : "");
			}
			tripPlaceList.add(tripPlaceRow);
		}else if(addTripPlaceCnt > 1){
			for(int i=0 ; i<addTripPlaceCnt ; i++){
				tripPlaceRow = new HashMap<String, Object>();//IT_RLIST(row)
				for(String rListKey : rListKeySet){
					tripPlaceRow.put(rListKey, request.getParameterValues("RLIST_"+rListKey)[i]);
				}
				tripPlaceList.add(tripPlaceRow);
			}
		}

		rfcParam.put("IM_LIST"    , paramRow);
		rfcParam.put("IM_LIST_SEL", selParamRow);
		rfcParam.put("IM_ALIST"   , aParamRow);
		rfcParam.put("IM_ELIST"   , eParamRow);
		rfcParam.put("IM_BLIST"   , bParamRow);

		Map<?, ?> resultMapAppline = webEssRcv.callRfcFunction("ZHR_RFC_PT_008_GET_APPLINE", rfcParam, null, request);

		rfcParamList.put("IT_RLIST", tripPlaceList);
		rfcParamList.put("IT_LINE" , apprList);
		rfcParamList.put("IT_APPNR", resultMapAppline.get("ET_APPNR"));
		//RFC PARAMETER SETTING END

		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_PT_008_PROCESS", rfcParam, rfcParamList, request);

		return result;
	}

	@RequestMapping(value = "placeAdd.do")
	public @ResponseBody Map<?, ?> placeAdd(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		HashMap<String, Object> paramRow = new HashMap<String, Object>();//IM_LIST
		String keySet[] = Constant.businessTripListKeySet;

		for(String key : keySet){
			if("ZCNTYP".equals(key)){
				paramRow.put(key, params.get("tripDiv"));
			}else if("DATE_BEG".equals(key)){
				paramRow.put(key, params.get("startDate"));
			}else if("DATE_END".equals(key)){
				paramRow.put(key, params.get("endDate"));
			}else if("ZTIME_BEG".equals(key)){
				paramRow.put(key, params.get("startHour"));
			}else if("ZMINT_BEG".equals(key)){
				paramRow.put(key, params.get("startMin"));
			}else if("ZTIME_END".equals(key)){
				paramRow.put(key, params.get("endHour"));
			}else if("ZMINT_END".equals(key)){
				paramRow.put(key, params.get("endMin"));
			}else if("ACTIVITY_TYPE".equals(key)){
				paramRow.put(key, params.get("tripType"));
			}else if("LOCATION_END".equals(key)){
				paramRow.put(key, params.get("tripPlace"));
			}else if("REQUEST_REASON".equals(key)){
				paramRow.put(key, params.get("tripResason"));
			}else{
				paramRow.put(key, params.containsKey("SEL_"+key) ? params.get("SEL_"+key) : "");
			}
		}

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		rfcParam.put("IM_LIST", paramRow);
		//RFC PARAMETER SETTING END

		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_PT_008_APPEND_LINE", rfcParam, null, request);

		return result;
	}

	@RequestMapping(value = "retrieveTransGrade.do")
	public @ResponseBody Map<?, ?> retrieveTransGrade(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");

		HashMap<String, Object> bParamRow = new HashMap<String, Object>();//BLIST
		String bListKeySet[] = Constant.bListKeySet;

		for(String bListKey : bListKeySet){

			/*
			TRANS_BEG	출발교통편
			TRANSGR_BEG	교통편등급
			TRANS_END	복귀교통편
			TRANSGR_END	교통편등급
			*/

			if("TRANS_BEG".equals(bListKey)){
				bParamRow.put(bListKey, params.get("startTrans"));
			}else if("TRANSGR_BEG".equals(bListKey)){
				bParamRow.put(bListKey, params.get("startGrade"));
			}else if("TRANS_END".equals(bListKey)){
				bParamRow.put(bListKey, params.get("endTrans"));
			}else if("TRANSGR_END".equals(bListKey)){
				bParamRow.put(bListKey, params.get("endGrade"));
			}else{
				bParamRow.put(bListKey, params.containsKey("BLIST_"+bListKey) ? params.get("BLIST_"+bListKey) : "");
			}
		}

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		rfcParam.put("IM_PERNR", user.getEmpNo());
		rfcParam.put("IM_FLAG" , params.get("IM_FLAG"));
		rfcParam.put("IM_BLIST", bParamRow);
		//RFC PARAMETER SETTING END

		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_PT_008_GET_LISTBOX", rfcParam, null, request);

		return result;
	}

	@RequestMapping(value = "/retrieveTripExpenses.do")
	public ModelAndView retrieveTripExpenses(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/webess/diligenceMng/retrieveTripExpenses");

		User user = (User) getRequestAttribute("ikep.user");

		HashMap<String, Object> paramRow = new HashMap<String, Object>();//IM_LIST
		String keySet[] = Constant.businessTripListKeySet;

		for(String key : keySet){
			if(!params.containsKey("baseDate")){
				if("ZCNTYP".equals(key)){
					paramRow.put(key, params.get("tripDiv"));
				}else if("DATE_BEG".equals(key)){
					paramRow.put(key, params.get("startDate"));
				}else if("DATE_END".equals(key)){
					paramRow.put(key, params.get("endDate"));
				}else if("ZTIME_BEG".equals(key)){
					paramRow.put(key, params.get("startHour"));
				}else if("ZMINT_BEG".equals(key)){
					paramRow.put(key, params.get("startMin"));
				}else if("ZTIME_END".equals(key)){
					paramRow.put(key, params.get("endHour"));
				}else if("ZMINT_END".equals(key)){
					paramRow.put(key, params.get("endMin"));
				}else if("ACTIVITY_TYPE".equals(key)){
					paramRow.put(key, params.get("tripType"));
				}else if("LOCATION_END".equals(key)){
					paramRow.put(key, params.get("tripPlace"));
				}else if("REQUEST_REASON".equals(key)){
					paramRow.put(key, params.get("tripResason"));
				}else{
					paramRow.put(key, params.containsKey(key) ? params.get(key) : "");
				}
			}else{
				paramRow.put(key, params.containsKey(key) ? params.get(key) : "");
			}
		}

		String toDate = null;

		if(params.containsKey("baseDate")){
			toDate = (String) params.get("baseDate");
		}else{
			if(!"".equals((String) params.get("startDate"))){
				toDate = (String) params.get("startDate");
			}else{
				toDate = DateUtil.getToday("");
			}
		}

		params.put("IM_DATE", toDate);

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		rfcParam.put("IM_DATE" , toDate);
		rfcParam.put("IM_PERNR", user.getEmpNo());

		rfcParam.put("IM_LIST", paramRow);
		//RFC PARAMETER SETTING END

		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_PT_008_GET_COST", rfcParam, null, request);
		mav.addObject("costResult", result);

		Map<?, ?> codeResult = webEssRcv.callRfcFunction("ZHR_RFC_PT_008_GET_LISTBOX", rfcParam, null, request);
		mav.addObject("tripTypeList", codeResult.get("ET_TYPE"));

		mav.addObject("IM_LIST", paramRow);
		mav.addObject("keySet" , keySet);
		mav.addObject("params" , params);

		return mav;
	}

	@RequestMapping(value = "/retrieveCurrencyList.do")
	public ModelAndView retrieveCurrencyList(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/webess/diligenceMng/retrieveCurrencyList");

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		rfcParam.put("IM_SEARCH_D", params.get("searchText"));
		//RFC PARAMETER SETTING END

		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_SEARCH_WAERS", rfcParam, null, request);

		mav.addObject("params", params);
		mav.addObject("resultList", result.get("EX_TAB"));

		return mav;
	}

	@RequestMapping(value = "retrieveExchangeRate.do")
	public @ResponseBody Map<?, ?> retrieveExchangeRate(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		rfcParam.put("IM_WAERS", params.get("IM_WAERS"));
		rfcParam.put("IM_DATVS", params.get("IM_DATVS"));
		//RFC PARAMETER SETTING END

		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_PT_008_GET_WAERS", rfcParam, null, request);

		return result;
	}


	@RequestMapping(value = "/yearMonthPaidHolidayList.do")
	public ModelAndView yearMonthPaidHolidayList(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/webess/diligenceMng/yearMonthPaidHolidayList");

		String thisYear = DateUtil.getToday("yyyy");
		User user = (User) getRequestAttribute("ikep.user");

		if(!params.containsKey("imYear")){
			params.put("imYear", thisYear);
		}

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		rfcParam.put("IM_YEAR" , params.get("imYear"));
		rfcParam.put("IM_PERNR", user.getEmpNo());
		//RFC PARAMETER SETTING END

		Map<?, ?> result = null;

		Map<?, ?> resultOnCreate = webEssRcv.callRfcFunction("ZHR_RFC_PT_010_ONCREATE", rfcParam, null, request);

		String exYearGb = (String)resultOnCreate.get("EX_YEAR_GB");
		String exBtrtl = (String)resultOnCreate.get("EX_BTRTL");

		rfcParam.put("IM_BTRTL"  , exBtrtl);
		rfcParam.put("IM_YEAR_GB", exYearGb);

		params.put("exBtrtl" , exBtrtl);
		params.put("exYearGb", exYearGb);

		Map<?, ?> resultList = webEssRcv.callRfcFunction("ZHR_RFC_PT_010_GET_LISTBOX", rfcParam, null, request);
		rfcParam.put("IM_FLAG" , resultList.get("EX_FLAG"));
		params.put("exFlag"    , resultList.get("EX_FLAG"));

		if("P200".equals(exBtrtl) && "X".equals(exYearGb)){
			result = webEssRcv.callRfcFunction("ZHR_RFC_PT_010_GET_DATA_P200", rfcParam, null, request);

			mav.addObject("ET_LIST_M", result.get("ET_LIST_M_1"));
			mav.addObject("ET_LIST", result.get("ET_LIST_1"));
		}else{
			result = webEssRcv.callRfcFunction("ZHR_RFC_PT_010_GET_DATA", rfcParam, null, request);

			mav.addObject("ET_LIST_M", result.get("ET_LIST_M"));
			mav.addObject("ET_LIST", result.get("ET_LIST"));
		}

		mav.addObject("resultList", resultList);
		mav.addObject("params"    , params);

		return mav;
	}

	@RequestMapping(value = "/retrievePaidHolidayUseList.do")
	public ModelAndView retrievePaidHolidayUseList(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/webess/diligenceMng/retrievePaidHolidayUseList");

		User user = (User) getRequestAttribute("ikep.user");

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		rfcParam.put("IM_YEAR" , params.get("IM_YEAR"));
		rfcParam.put("IM_MONTH", params.get("IM_MONTH"));
		rfcParam.put("IM_PERNR", params.containsKey("selEmpNo") ? params.get("selEmpNo") : user.getEmpNo());
		rfcParam.put("IM_FLAG" , params.get("IM_FLAG"));
		//RFC PARAMETER SETTING END

		Map<?, ?> result = null;

		if("P200".equals((String)params.get("exBtrtl")) && "X".equals((String)params.get("exYearGb"))){
			result = webEssRcv.callRfcFunction("ZHR_RFC_PT_010_GET_ITEM_P200", rfcParam, null, request);
		}else{
			result = webEssRcv.callRfcFunction("ZHR_RFC_PT_010_GET_ITEM", rfcParam, null, request);
		}

		mav.addObject("params", params);
		mav.addObject("resultList", result.get("ET_ITEM"));

		return mav;
	}

	@RequestMapping(value = "/leaveReinstatementList.do")
	public ModelAndView leaveReinstatementList(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/webess/diligenceMng/leaveReinstatementList");

		User user = (User) getRequestAttribute("ikep.user");

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		rfcParam.put("IM_PERNR", user.getEmpNo());
		//RFC PARAMETER SETTING END

		Map<?, ?> resultMapList = webEssRcv.callRfcFunction("ZHR_RFC_PA_033_GET_DATA", rfcParam, null, request);

		mav.addObject("resultList", resultMapList.get("ET_LIST"));//목록 리스트
		mav.addObject("keySet"    , Constant.leaveReinstatementKeySet);//리스트 키셋
		mav.addObject("params"    , params);

		return mav;
	}

	@RequestMapping(value = "/leaveReinstatementView.do")
	public ModelAndView leaveReinstatementView(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		String jspUrl = "/portal/webess/diligenceMng/leaveReinstatementRegView";
		String page = "CREATE";

		if(params.containsKey("jspType") && "view".equals((String)params.get("jspType"))){
			jspUrl = "/portal/webess/diligenceMng/leaveReinstatementView";
			page = "DISPLAY";
		}else{
			if(params.containsKey("dataLoad") && "Y".equals((String)params.get("dataLoad"))){
				page = "CHANGE";
			}
		}

		ModelAndView mav = new ModelAndView(jspUrl);

		User user = (User) getRequestAttribute("ikep.user");

		//exFlag로 데이터 구분을 해야한다.
		//exFlag == "" 이면 작성이나, 수정으로 들어온 화면
		//exFlag == "CR" 이면 변경 요청으로 들어온 화면
		//exFlag == "D" 이면 삭제로 들어온 화면
		//exFlag == "C" 이면 취소신청으로 들어온 화면
		//param.get("dataLoad")의 유무로 (작성)인지 (수정, 변경요청)인지 분기

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> paramRow = new HashMap<String, Object>();

		rfcParam.put("IM_PERNR", user.getEmpNo());
		rfcParam.put("IM_FLAG" , params.get("exFlag"));
		rfcParam.put("IM_PAGE" , page);
		rfcParam.put("IM_INFTY", "9208");

		String keySet[] = Constant.leaveReinstatementKeySet;

		for(String key : keySet){
			paramRow.put(key, params.get(key));
		}

		rfcParam.put("IM_LIST", paramRow);
		//RFC PARAMETER SETTING END

		if(!"CREATE".equals(page)){
			for(String key : keySet){
				params.put("SEL_"+key, params.get(key));
			}
		}

		Map<?, ?> resultMapCode = webEssRcv.callRfcFunction("ZHR_RFC_PA_033_GET_LISTBOX", rfcParam, null, request);

		Map<?, ?> resultMapList = webEssRcv.callRfcFunction("ZHR_RFC_PA_033_GET_APPLINE", rfcParam, null, request);

		String astat = (String) params.get("ASTAT");

		if("05".equals(astat) || "16".equals(astat) || "13".equals(astat)){

			rfcParam.put("IM_APPID", params.get("APPID"));

			Map<?, ?> resultRefuse = webEssRcv.callRfcFunction("ZHR_RFC_READ_REFUSE_REASON", rfcParam, null, request);
			mav.addObject("resultRefuse", resultRefuse);
		}

		Map<?, ?> exList = (Map<?, ?>)resultMapList.get("EX_LIST");

		if(!"CREATE".equals(page)){
			for(String key : keySet){
				params.put(key, exList.get(key));
			}
		}

		mav.addObject("requestDivList", resultMapCode.get("ET_MASSN"));//신청구분 리스트
		mav.addObject("leaveTypeList" , resultMapCode.get("ET_MASSG"));//휴직유형 리스트
		mav.addObject("resultMapList" , resultMapList);//결재자 리스트
		mav.addObject("keySet"        , keySet);//리스트 선택 키셋
		mav.addObject("apprKeySet"    , Constant.apprKeySet);//결재자 리스트 키셋
		mav.addObject("params"        , params);

		return mav;
	}

	@RequestMapping(value = "retrieveCalculatorPeriod.do")
	public @ResponseBody Map<?, ?> retrieveCalculatorPeriod(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		rfcParam.put("IM_BEGDA", params.get("startDate"));
		rfcParam.put("IM_ENDDA", params.get("endDate"));
		//RFC PARAMETER SETTING END

		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_PA_033_GET_CALDAY", rfcParam, null, request);

		return result;
	}

	@RequestMapping(value = "/leaveReinstatementProcess.do")
	public @ResponseBody Map<?, ?> leaveReinstatementProcess(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> rfcParamList = new HashMap<String, Object>();

		rfcParam.put("IM_PERNR"   , user.getEmpNo());
		rfcParam.put("IM_EVENT_ID", params.get("eventId"));
		rfcParam.put("IM_PAGE"    , params.get("page"));
		rfcParam.put("IM_FLAG"    , params.get("exFlag"));
		rfcParam.put("IM_INFTY"   , "9208");

		HashMap<String, Object> paramRow = new HashMap<String, Object>();//IM_LIST

		//리스트에서 선택한 데이터 추출, 입력한 데이터 추출
		String keySet[] = Constant.leaveReinstatementKeySet;

		for(String key : keySet){
			if("MASSN".equals(key)){
				paramRow.put(key, params.get("requestDiv"));
			}else if("MASSG".equals(key)){
				paramRow.put(key, params.get("leaveType"));
			}else if("BEGDA".equals(key)){
				paramRow.put(key, params.get("startDate"));
			}else if("ENDDA".equals(key)){
				paramRow.put(key, params.get("endDate"));
			}else if("RETDT".equals(key)){
				paramRow.put(key, params.get("reinstateDate"));
			}else if("ZMON".equals(key)){
				paramRow.put(key, params.get("exZmon"));
			}else if("ZDAY".equals(key)){
				paramRow.put(key, params.get("exZday"));
			}else if("COMET".equals(key)){
				paramRow.put(key, params.get("comment"));
			}else{
				paramRow.put(key, params.containsKey("SEL_"+key) ? params.get("SEL_"+key) : "");
			}
		}

		//결재라인 추출
		ArrayList<HashMap<String, Object>> apprList = new ArrayList<HashMap<String, Object>>();//IT_LINE
		HashMap<String, Object> apprRow = null;

		String apprKeySet[] = Constant.apprKeySet;
		int apprCnt = Integer.parseInt(((String)params.get("apprCnt")));

		if(apprCnt == 1){
			apprRow = new HashMap<String, Object>();//IT_LINE(row)
			for(String apprKey : apprKeySet){
				apprRow.put(apprKey, params.containsKey(apprKey) ? params.get(apprKey) : "");
			}
			apprList.add(apprRow);
		}else if(apprCnt > 1){
			for(int i=0 ; i<apprCnt ; i++){
				apprRow = new HashMap<String, Object>();//IT_LINE(row)
				for(String apprKey : apprKeySet){
					apprRow.put(apprKey, request.getParameterValues(apprKey)[i]);
				}
				apprList.add(apprRow);
			}
		}

		rfcParam.put("IM_LIST", paramRow);

		//코드값 추출
		Map<?, ?> resultMapCode = webEssRcv.callRfcFunction("ZHR_RFC_PA_033_GET_LISTBOX", rfcParam, null, request);

		Map<?, ?> resultMapAppline = webEssRcv.callRfcFunction("ZHR_RFC_PA_033_GET_APPLINE", rfcParam, null, request);

		rfcParamList.put("IT_LINE" , apprList);
		rfcParamList.put("IT_APPNR", resultMapAppline.get("ET_APPNR"));
		rfcParamList.put("IT_ASTAT", resultMapCode.get("ET_ASTAT"));
		//RFC PARAMETER SETTING END

		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_PA_033_PROCESS", rfcParam, rfcParamList, request);

		return result;
	}

	@RequestMapping(value = "checkedRowValidationPA033.do")
	public @ResponseBody Map<?, ?> checkedRowValidationPA033(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> paramRow = new HashMap<String, Object>();

		rfcParam.put("IM_PERNR", user.getEmpNo());
		rfcParam.put("IM_EVENT_ID", params.get("eventId"));

		String keySet[] = Constant.leaveReinstatementKeySet;

		for(String key : keySet){
			paramRow.put(key, ((String)params.get(key)).trim());
		}
		rfcParam.put("IM_LIST", paramRow);
		//RFC PARAMETER SETTING END

		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_PA_033_CHECK_SEL_LINE", rfcParam, null, request);

		return result;
	}

	@RequestMapping(value = "evaluationList.do")
	public ModelAndView evaluationList(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/webess/diligenceMng/evaluationList");

		User user = (User) getRequestAttribute("ikep.user");

		String thisYear = DateUtil.getToday("yyyy");
		String thisMonth = DateUtil.getToday("MM");

		if(!params.containsKey("imYear")){
			params.put("imYear", thisYear);
		}

		if(!params.containsKey("imMonth")){
			params.put("imMonth", thisMonth);
		}

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		rfcParam.put("IM_YEAR" , params.get("imYear"));
		rfcParam.put("IM_MONTH", params.get("imMonth"));
		rfcParam.put("IM_PERNR", user.getEmpNo());

		Map<?, ?> resultMapCode = webEssRcv.callRfcFunction("ZHR_RFC_PT_007_GET_LISTBOX", rfcParam, null, request);
		//RFC PARAMETER SETTING END

		rfcParam.put("IM_IDATE1", resultMapCode.get("EX_IDATE1"));
		rfcParam.put("IM_IDATE2", resultMapCode.get("EX_IDATE2"));

		Map<?, ?> resultMapList = webEssRcv.callRfcFunction("ZHR_RFC_PT_007_GET_DATA", rfcParam, null, request);

		params.put("EX_RESULT", resultMapList.get("EX_RESULT"));
		params.put("EX_MESSAGE", resultMapList.get("EX_MESSAGE"));

		mav.addObject("resultList", resultMapList.get("ET_LIST"));//목록 리스트
		mav.addObject("resultSum" , resultMapList.get("ET_SUM"));//목록 리스트
		mav.addObject("yearList"  , resultMapCode.get("ET_YEAR"));//연도 코드
		mav.addObject("monthList" , resultMapCode.get("ET_MONTH"));//달 코드
		mav.addObject("params"    , params);

		return mav;
	}

	@RequestMapping(value = "holidayAmassRegView.do")
	public ModelAndView holidayAmassRegView(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/webess/diligenceMng/holidayAmassRegView");

		User user = (User) getRequestAttribute("ikep.user");

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> rfcParamList = new HashMap<String, Object>();
		rfcParam.put("IM_PERNR", user.getEmpNo());


		Map<?, ?> resultOnCreate = webEssRcv.callRfcFunction("ZHR_RFC_PT_005_ONCREATE", rfcParam, null, request);

		String imDate = DateUtil.getToday("yyyyMMdd");

		rfcParam.put("IM_DATE", imDate);

		Map<?, ?> resultData = webEssRcv.callRfcFunction("ZHR_RFC_PT_005_GET_DATA", rfcParam, null, request);

		rfcParam.put("IM_FLAG", resultOnCreate.get("EX_FLAG"));
		rfcParam.put("IM_YEAR", resultData.get("EX_YEAR"));
		rfcParam.put("IM_CHK" , resultData.get("EX_CHK"));

		Map<?, ?> resultScr = webEssRcv.callRfcFunction("ZHR_RFC_PT_005_CHECK_SCR", rfcParam, null, request);

		String scrResult = (String) resultScr.get("EX_RESULT");
		String scrMessage = (String) resultScr.get("EX_MESSAGE");

		params.put("scrResult", scrResult);
		params.put("scrMessage", scrMessage);

		if("S".equals(scrResult)){
			rfcParam.put("IM_ANZHI_J", resultData.get("EX_ANZHI_J"));
			rfcParamList.put("IT_QUONUM", resultData.get("ET_QUONUM"));

			Map<?, ?> resultAfterData = webEssRcv.callRfcFunction("ZHR_RFC_PT_005_AFTER_GET_DATA", rfcParam, rfcParamList, request);

			mav.addObject("resultAfterData", resultAfterData);
		}
		//RFC PARAMETER SETTING END

		mav.addObject("resultOnCreate", resultOnCreate);
		mav.addObject("resultData", resultData);
		mav.addObject("params", params);

		return mav;
	}

	@RequestMapping(value = "/holidayAmassProcess.do")
	public @ResponseBody Map<?, ?> holidayAmassProcess(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		rfcParam.put("IM_PERNR"   , user.getEmpNo());
		rfcParam.put("IM_ENDDA"   , params.get("imEndda"));
		rfcParam.put("IM_BEGDA"   , params.get("imBegda"));
		rfcParam.put("IM_ANZHL_I" , params.get("anzhlI"));
		rfcParam.put("IM_EVENT_ID", params.get("eventId"));

		//RFC PARAMETER SETTING END

		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_PT_005_PROCESS", rfcParam, null, request);

		return result;
	}
}
