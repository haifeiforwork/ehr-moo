package com.lgcns.ikep4.portal.moorimess.web.personalMng;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
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
@RequestMapping(value="/portal/moorimess/personalMng")
public class PersonalMngController extends BaseController {

	@Autowired
	private WebESSRcvRFC webEssRcv;

	protected final Log log = LogFactory.getLog(this.getClass());
	
	@Autowired
	private RfcCacheService rfcCacheService;

	@RequestMapping(value = "/addressList.do")
	public ModelAndView addressList(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/webess/personalMng/addressList");

		User user = (User) getRequestAttribute("ikep.user");

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		rfcParam.put("IM_PERNR", user.getEmpNo());
		//RFC PARAMETER SETTING END

		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_PA_008_GET_DATA", rfcParam, null, request);

		mav.addObject("result", result);
		mav.addObject("keySet", Constant.addressListKeySet);
		mav.addObject("params", params);

		return mav;
	}

	@RequestMapping(value = "/addressView.do")
	public ModelAndView addressView(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/webess/personalMng/addressRegView");

		Map<?, ?> resultCode = webEssRcv.callRfcFunction("ZHR_RFC_PA_008_GET_LISTBOX", null, null, request);

		mav.addObject("resultCode", resultCode);
		mav.addObject("keySet"    , Constant.addressListKeySet);
		mav.addObject("params"    , params);

		return mav;
	}


	@RequestMapping(value = "/addressProcess.do")
	public @ResponseBody Map<?, ?> addressProcess(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> rfcParamList = new HashMap<String, Object>();

		rfcParam.put("IM_PERNR"   , user.getEmpNo());
		rfcParam.put("IM_PAGE"    , params.get("page"));
		rfcParam.put("IM_EVENT_ID", params.get("eventId"));

		HashMap<String, Object> paramRow = new HashMap<String, Object>();//IM_LIST

		//리스트에서 선택한 데이터 추출, 입력한 데이터 추출
		String keySet[] = Constant.addressListKeySet;

		for(String key : keySet){
			if("SUBTY".equals(key)){
				paramRow.put(key, params.get("type"));
			}else if("ZTYPE".equals(key)){
				paramRow.put(key, params.get("typeName"));
			}else if("STATE".equals(key)){
				paramRow.put(key, params.get("state"));
			}else if("ZSTATE".equals(key)){
				paramRow.put(key, params.get("stateName"));
			}else if("PSTLZ".equals(key)){
				paramRow.put(key, params.get("zipCode"));
			}else if("ORT01".equals(key)){
				paramRow.put(key, params.get("stateText"));
			}else if("ORT02".equals(key)){
				paramRow.put(key, params.containsKey("town") ? params.get("town") : "");
			}else if("STRAS".equals(key)){
				paramRow.put(key, params.get("address"));
			}else if("LOCAT".equals(key)){
				paramRow.put(key, params.get("addressDetail"));
			}else{
				paramRow.put(key, params.containsKey(key) ? params.get(key) : "");
			}
		}

		rfcParam.put("IM_LIST", paramRow);

		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_PA_008_PROCESS", rfcParam, rfcParamList, request);

		return result;
	}

	@RequestMapping(value = "/zipCodePop.do")
	public ModelAndView zipCodePop(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/webess/personalMng/zipCodePop");

		String initFlag = params.containsKey("initFlag") ? (String) params.get("initFlag") : "N";

		if(!params.containsKey("searchType")){
			params.put("searchType", "ROAD");
		}

		if("N".equals(initFlag)){
			//RFC PARAMETER SETTING START
			HashMap<String, Object> rfcParam = new HashMap<String, Object>();

			rfcParam.put("IM_SRCHTXT"  , params.get("keyword"));
			rfcParam.put("IM_SELECTION", params.get("searchType"));
			rfcParam.put("IM_EVENT_ID" , params.get("eventId"));
			//RFC PARAMETER SETTING END

			Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_PA_008_SEARCH_ZIPCODE", rfcParam, null, request);

			mav.addObject("result", result);
		}

		mav.addObject("params", params);

		return mav;
	}

	@RequestMapping(value = "/setZipCode.do")
	public @ResponseBody Map<?, ?> setZipCode(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> paramRow = new HashMap<String, Object>();

		rfcParam.put("IM_EVENT_ID", params.get("eventId"));
		rfcParam.put("IM_FLAG"    , params.get("imFlag"));

		paramRow.put("ZIPNO"   , params.get("ZIPNO"));
		paramRow.put("LNMADRES", params.get("LNMADRES"));
		paramRow.put("RNADRES" , params.get("RNADRES"));

		rfcParam.put("IM_LIST", paramRow);
		//RFC PARAMETER SETTING END

		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_PA_008_SEARCH_ZIPCODE", rfcParam, null, request);

		return result;
	}

	@RequestMapping(value = "/educationList.do")
	public ModelAndView educationList(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/webess/personalMng/educationList");

		User user = (User) getRequestAttribute("ikep.user");

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		rfcParam.put("IM_PERNR", user.getEmpNo());
		//RFC PARAMETER SETTING END

		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_PA_012_GET_DATA", rfcParam, null, request);

		mav.addObject("result", result);
		mav.addObject("params", params);

		return mav;
	}

	@RequestMapping(value = "/familyList.do")
	public ModelAndView familyList(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/webess/personalMng/familyList");

		User user = (User) getRequestAttribute("ikep.user");

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		rfcParam.put("IM_PERNR", user.getEmpNo());
		//RFC PARAMETER SETTING END

		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_PA_009_GET_DATA", rfcParam, null, request);

		mav.addObject("result", result);
		mav.addObject("params", params);

		return mav;
	}

	@RequestMapping(value = "/prizeDisciplineList.do")
	public ModelAndView prizeDisciplineList(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/webess/personalMng/prizeDisciplineList");

		User user = (User) getRequestAttribute("ikep.user");

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		rfcParam.put("IM_PERNR", user.getEmpNo());
		//RFC PARAMETER SETTING END

		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_PA_011_GET_DATA", rfcParam, null, request);

		mav.addObject("result", result);
		mav.addObject("params", params);

		return mav;
	}

	@RequestMapping(value = "/appointList.do")
	public ModelAndView appointList(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/webess/personalMng/appointList");

		User user = (User) getRequestAttribute("ikep.user");

		if(!params.containsKey("appointType")){
			params.put("appointType", "00");
		}

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		rfcParam.put("IM_PERNR", user.getEmpNo());
		rfcParam.put("IM_MASSN", params.get("appointType"));
		//RFC PARAMETER SETTING END

		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_PA_002_GET_DATA", rfcParam, null, request);

		mav.addObject("result", result);
		mav.addObject("params", params);

		return mav;
	}

	@RequestMapping(value = "/foreignLanguageList.do")
	public ModelAndView foreignLanguageList(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/webess/personalMng/foreignLanguageList");

		User user = (User) getRequestAttribute("ikep.user");

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		rfcParam.put("IM_PERNR", user.getEmpNo());
		//RFC PARAMETER SETTING END

		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_PA_007_GET_DATA", rfcParam, null, request);

		mav.addObject("result", result);
		mav.addObject("params", params);

		return mav;
	}

	@RequestMapping(value = "/qualificationList.do")
	public ModelAndView qualificationList(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/webess/personalMng/qualificationList");

		User user = (User) getRequestAttribute("ikep.user");

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		rfcParam.put("IM_PERNR", user.getEmpNo());
		//RFC PARAMETER SETTING END

		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_PA_010_GET_DATA", rfcParam, null, request);

		mav.addObject("result", result);
		mav.addObject("params", params);

		return mav;
	}

	@RequestMapping(value = "/personalView.do")
	public ModelAndView personalView(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		String viewName = "/portal/webess/personalMng/personalView";

		User user = (User) getRequestAttribute("ikep.user");

		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		rfcParam.put("IM_PERNR", user.getEmpNo());

		ModelAndView mav = new ModelAndView();

		String headerKeySet[] = Constant.personalHeaderKeySet;
		String lst1KeySet[]   = Constant.personalLst1KeySet;
		String lst2KeySet[]   = Constant.personalLst2KeySet;
		String lst3KeySet[]   = Constant.personalLst3KeySet;

		//속도 개선을 위해 RFC 호출을 최소화 하도록 작업
		if(params.containsKey("jspType") && "regView".equals((String)params.get("jspType"))){

			viewName = "/portal/webess/personalMng/personalRegView";

			HashMap<String, Object> resultHeader = new HashMap<String, Object>();
			HashMap<String, Object> headerParam = new HashMap<String, Object>();

			HashMap<String, Object> result = new HashMap<String, Object>();
			HashMap<String, Object> lst1Param = new HashMap<String, Object>();
			HashMap<String, Object> lst2Param = new HashMap<String, Object>();
			HashMap<String, Object> lst3Param = new HashMap<String, Object>();

			for(String key : headerKeySet){
				headerParam.put(key, params.get("HEADER_"+key));
			}
			resultHeader.put("EX_HEADER", headerParam);

			for(String key : lst1KeySet){
				lst1Param.put(key, params.get("LST1_"+key));
			}
			result.put("EX_LST1", lst1Param);

			for(String key : lst2KeySet){
				lst2Param.put(key, params.get("LST2_"+key));
			}
			result.put("EX_LST2", lst2Param);

			for(String key : lst3KeySet){
				lst3Param.put(key, params.get("LST3_"+key));
			}
			result.put("EX_LST3", lst3Param);

			mav.addObject("resultHeader", resultHeader);
			mav.addObject("result"      , result);

		}else{

			Map<?, ?> resultHeader = webEssRcv.callRfcFunction("ZHR_RFC_PA_001_GET_DATA", rfcParam, null, request);

			Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_PA_021_GET_DATA", rfcParam, null, request);

			mav.addObject("resultHeader", resultHeader);
			mav.addObject("result"      , result);
		}

		mav.setViewName(viewName);

		Map<?, ?> resultCode = webEssRcv.callRfcFunction("ZHR_RFC_PA_021_GET_LISTBOX", rfcParam, null, request);

		HashMap<String, String[]> keySet = new HashMap<String, String[]>();

		keySet.put("headerKeySet", headerKeySet);
		keySet.put("lst1KeySet"  , lst1KeySet);
		keySet.put("lst2KeySet"  , lst2KeySet);
		keySet.put("lst3KeySet"  , lst3KeySet);

		mav.addObject("resultCode", resultCode);
		mav.addObject("params"    , params);
		mav.addObject("keySet"    , keySet);

		return mav;
	}

	@RequestMapping(value = "/personalProcess.do")
	public @ResponseBody Map<?, ?> personalProcess(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");

		String lst1KeySet[] = Constant.personalLst1KeySet;
		String lst2KeySet[] = Constant.personalLst2KeySet;
		String lst3KeySet[] = Constant.personalLst3KeySet;

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		rfcParam.put("IM_PERNR", user.getEmpNo());

		HashMap<String, Object> lst1Param = new HashMap<String, Object>();
		HashMap<String, Object> lst2Param = new HashMap<String, Object>();
		HashMap<String, Object> lst3Param = new HashMap<String, Object>();

		for(String key : lst1KeySet){
			if("TELNR".equals(key)){
				lst1Param.put(key, params.get("homeTelNo"));
			}else if("ZCPTEL".equals(key)){
				lst1Param.put(key, params.get("officeTelNo"));
			}else if("ZHPNO".equals(key)){
				lst1Param.put(key, params.get("mobileNo"));
			}else if("ZZHOB".equals(key)){
				lst1Param.put(key, params.get("hobby"));
			}else if("CARNO".equals(key)){
				lst1Param.put(key, params.get("myCarNo"));
			}else if("ZEMAIL2".equals(key)){
				lst1Param.put(key, params.get("privateEmail"));
			}else if("HOUSE".equals(key)){
				lst1Param.put(key, params.get("dwellingType"));
			}else if("KONFE".equals(key)){
				lst1Param.put(key, params.get("religion"));
			}else if("MYCAR".equals(key)){
				lst1Param.put(key, params.get("myCar"));
			}else{
				lst1Param.put(key, params.get("LST1_"+key));
			}
		}

		for(String key : lst2KeySet){
			lst2Param.put(key, params.get("LST2_"+key));
		}

		for(String key : lst3KeySet){
			lst3Param.put(key, params.get("LST3_"+key));
		}

		rfcParam.put("IM_LIST1", lst1Param);
		rfcParam.put("IM_LIST2", lst2Param);
		rfcParam.put("IM_LIST3", lst3Param);

		//RFC PARAMETER SETTING END

		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_PA_021_REQUEST", rfcParam, null, request);

		return result;
	}

	@RequestMapping(value = "/careerList.do")
	public ModelAndView careerList(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/webess/personalMng/careerList");

		User user = (User) getRequestAttribute("ikep.user");

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		rfcParam.put("IM_PERNR", user.getEmpNo());
		//RFC PARAMETER SETTING END

		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_PA_003_GET_DATA", rfcParam, null, request);

		mav.addObject("result", result);
		mav.addObject("params", params);

		return mav;
	}

	@RequestMapping(value = "/congratulateList.do")
	public ModelAndView congratulateList(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/webess/personalMng/congratulateList");

		User user = (User) getRequestAttribute("ikep.user");

		String thisYear = DateUtil.getToday("yyyy");

		if(!params.containsKey("imYear")){
			params.put("imYear", thisYear);
		}

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		rfcParam.put("IM_YEAR" , params.get("imYear"));
		//rfcParam.put("IM_MONTH", params.get("imMonth"));
		rfcParam.put("IM_PERNR", user.getEmpNo());
		//RFC PARAMETER SETTING END

		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_BE_004_GET_DATA", rfcParam, null, request);

		Map<?, ?> resultCode = webEssRcv.callRfcFunction("ZHR_RFC_BE_004_GET_LISTBOX", null, null, request);

		mav.addObject("resultList", result.get("ET_LIST"));
		mav.addObject("yearList"  , resultCode.get("ET_YEAR"));
		mav.addObject("keySet"    , Constant.congratulateListKeySet);//리스트 키셋
		mav.addObject("params"    , params);

		return mav;
	}

	@RequestMapping(value = "checkedRowValidationBE004.do")
	public @ResponseBody Map<?, ?> checkedRowValidationBE004(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		rfcParam.put("IM_PERNR", user.getEmpNo());
		rfcParam.put("IM_EVENT_ID", params.get("eventId"));

		HashMap<String, Object> paramRow = new HashMap<String, Object>();

		String keySet[] = Constant.congratulateListKeySet;

		for(String key : keySet){
			paramRow.put(key, ((String)params.get(key)).trim());
		}

		rfcParam.put("IM_LIST", paramRow);
		//RFC PARAMETER SETTING END

		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_BE_004_CHECK_SEL_LINE", rfcParam, null, request);

		return result;
	}

	@RequestMapping(value = "/congratulateView.do")
	public ModelAndView congratulateView(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		String jspUrl = "/portal/webess/personalMng/congratulateRegView";
		String page = "CREATE";

		if(params.containsKey("jspType") && "view".equals((String)params.get("jspType"))){
			jspUrl = "/portal/webess/personalMng/congratulateView";
			page = "DISPLAY";
		}else{
			if(params.containsKey("dataLoad") && "Y".equals((String)params.get("dataLoad"))){
				page = "CHANGE";
			}
		}

		ModelAndView mav = new ModelAndView(jspUrl);

		User user = (User) getRequestAttribute("ikep.user");

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> paramRow = new HashMap<String, Object>();

		rfcParam.put("IM_PERNR", user.getEmpNo());
		rfcParam.put("IM_FLAG" , params.get("exFlag"));
		rfcParam.put("IM_PAGE" , page);
		rfcParam.put("IM_INFTY", "9502");

		String keySet[] = Constant.congratulateListKeySet;

		for(String key : keySet){
			paramRow.put(key, params.get(key));
		}

		rfcParam.put("IM_LIST", paramRow);
		//RFC PARAMETER SETTING END

		if(!"CREATE".equals(page)){
			for(String key : keySet){
				params.put("SEL_"+key, params.get(key));
			}
		}else if("CREATE".equals(page)){
			params.put("toDay", DateUtil.getToday("yyyy-MM-dd"));
		}

		Map<?, ?> resultMapCode = webEssRcv.callRfcFunction("ZHR_RFC_BE_004_GET_LISTBOX", rfcParam, null, request);

		Map<?, ?> resultMapList = webEssRcv.callRfcFunction("ZHR_RFC_BE_004_GET_APPLINE", rfcParam, null, request);

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

		mav.addObject("resultMapList", resultMapList);//결재자 리스트
		mav.addObject("typeList"     , resultMapCode.get("ET_CACCD"));//경조사 유형
		mav.addObject("garlandList"  , resultMapCode.get("ET_GARLD"));//화환(조화)
		mav.addObject("keySet"       , Constant.congratulateListKeySet);//리스트 선택 키셋
		mav.addObject("apprKeySet"   , Constant.apprKeySet);//결재자 리스트 키셋
		mav.addObject("params"       , params);

		return mav;
	}

	@RequestMapping(value = "/congratulateProcess.do")
	public @ResponseBody Map<?, ?> congratulateProcess(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> rfcParamList = new HashMap<String, Object>();

		rfcParam.put("IM_PERNR"   , user.getEmpNo());
		rfcParam.put("IM_EVENT_ID", params.get("eventId"));
		rfcParam.put("IM_PAGE"    , params.get("page"));
		rfcParam.put("IM_FLAG"    , params.get("exFlag"));
		rfcParam.put("IM_INFTY"   , "9502");

		HashMap<String, Object> paramRow = new HashMap<String, Object>();//IM_LIST
		//HashMap<String, Object> selParamRow = new HashMap<String, Object>();//IM_LIST_SEL

		//리스트에서 선택한 데이터 추출, 입력한 데이터 추출
		String keySet[] = Constant.congratulateListKeySet;

		for(String key : keySet){
			//selParamRow.put(key, params.containsKey("SEL_"+key) ? params.get("SEL_"+key) : "");

			if("BEGDA".equals(key) || "ENDDA".equals(key)){
				paramRow.put(key, params.get("requestDate"));
			}else if("CACCD".equals(key)){
				paramRow.put(key, params.get("type"));
			}else if("CACRN".equals(key)){
				paramRow.put(key, params.get("recipientType"));
			}else if("ENAME".equals(key)){
				paramRow.put(key, params.get("recipientName"));
			}else if("BEGDT".equals(key)){
				paramRow.put(key, params.get("startDate"));
			}else if("ENDDT".equals(key)){
				paramRow.put(key, params.get("endDate"));
			}else if("ZVACDT".equals(key)){
				paramRow.put(key, params.get("holiday"));
			}else if("CACMT".equals(key)){
				paramRow.put(key, params.get("amount"));
			}else if("GARLD".equals(key)){
				paramRow.put(key, params.get("garlandType"));
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
		//rfcParam.put("IM_LIST_SEL", selParamRow);

		//코드값 추출
		Map<?, ?> resultMapCode = webEssRcv.callRfcFunction("ZHR_RFC_BE_004_GET_LISTBOX", rfcParam, null, request);

		Map<?, ?> resultMapAppline = webEssRcv.callRfcFunction("ZHR_RFC_BE_004_GET_APPLINE", rfcParam, null, request);

		rfcParamList.put("IT_LINE" , apprList);
		rfcParamList.put("IT_APPNR", resultMapAppline.get("ET_APPNR"));

		rfcParamList.put("IT_ASTAT", resultMapCode.get("ET_ASTAT"));
		//RFC PARAMETER SETTING END

		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_BE_004_PROCESS", rfcParam, rfcParamList, request);

		return result;
	}

	@RequestMapping(value = "retrieveRecipientTypeList.do")
	public @ResponseBody Map<?, ?> retrieveRecipientTypeList(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");

		HashMap<String, Object> paramRow = new HashMap<String, Object>();
		paramRow.put("CACCD", params.containsKey("type") ? params.get("type") : "");

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		rfcParam.put("IM_PERNR", user.getEmpNo());
		rfcParam.put("IM_LIST" , paramRow);
		//RFC PARAMETER SETTING END

		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_BE_004_GET_CACRN_LIST", rfcParam, null, request);

		return result;
	}

	@RequestMapping(value = "retrieveCongratulateInfo.do")
	public @ResponseBody Map<?, ?> retrieveCongratulateInfo(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");

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

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> rfcParamList = new HashMap<String, Object>();

		rfcParam.put("IM_PERNR", user.getEmpNo());
		rfcParam.put("IM_CACCD", params.get("type"));
		rfcParam.put("IM_CACRN", params.get("recipientType"));
		rfcParam.put("IM_FLAG" , params.get("flag"));
		rfcParam.put("IM_BEGDT", params.get("startDate"));

		rfcParamList.put("IT_LINE" , apprList);
		//RFC PARAMETER SETTING END
		
		String page = (String) params.get("page");
		String functionName = "ZHR_RFC_BE_004_GET_TYPE2";
		
		if("DISPLAY".equals(page)){
			functionName = "ZHR_RFC_BE_004_GET_TYPE";
		}
		
		Map<?, ?> result = webEssRcv.callRfcFunction(functionName, rfcParam, rfcParamList, request);

		return result;
	}

	@RequestMapping(value = "/cafeteriaList.do")
	public ModelAndView cafeteriaList(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/webess/personalMng/cafeteriaList");

		User user = (User) getRequestAttribute("ikep.user");

		String thisYear = DateUtil.getToday("yyyy");

		params.put("thisYear", thisYear);

		if(!params.containsKey("imYear")){
			params.put("imYear", thisYear);
		}

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		rfcParam.put("IM_YEAR" , params.get("imYear"));
		rfcParam.put("IM_PERNR", user.getEmpNo());
		//RFC PARAMETER SETTING END

		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_PA_042_GET_DATA", rfcParam, null, request);

		Map<?, ?> resultCode = webEssRcv.callRfcFunction("ZHR_RFC_PA_042_GET_LISTBOX", null, null, request);

		mav.addObject("resultList", result.get("ET_LIST"));
		mav.addObject("yearList"  , resultCode.get("ET_YEAR"));
		mav.addObject("keySet"    , Constant.cafeteriaListKeySet);//리스트 키셋
		mav.addObject("params"    , params);

		return mav;
	}

	@RequestMapping(value = "/cafeteriaRegView.do")
	public ModelAndView cafeteriaView(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/webess/personalMng/cafeteriaRegView");

		User user = (User) getRequestAttribute("ikep.user");
		
		params.put("toDay", DateUtil.getToday("yyyy-MM-dd"));

		String[] keySet = Constant.cafeteriaListKeySet;

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> rfcParamList = new HashMap<String, Object>();
		
		ArrayList<HashMap<String, Object>> itList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> itRow = null;
		
		int rowInfoCnt = Integer.parseInt((String)params.get("rowInfoCnt"));
		
		if(rowInfoCnt == 1){
			
			itRow = new HashMap<String, Object>();
			
			for(String key : keySet){
				itRow.put(key, params.get(key));
			}
			
			itList.add(itRow);
			
		}else if(rowInfoCnt > 1){
			
			for(int i = 0 ; i < rowInfoCnt ; i++){
				itRow = new HashMap<String, Object>();
				
				for(String key : keySet){
					itRow.put(key, request.getParameterValues(key)[i]);
				}
				
				itList.add(itRow);
			}
			
		}
		
		rfcParam.put("IM_PERNR"   , user.getEmpNo());
		rfcParam.put("IM_YEAR"    , params.get("imYear"));
		rfcParam.put("IM_EVENT_ID", "CREATE");
		
		rfcParamList.put("IT_LIST", itList);
		//RFC PARAMETER SETTING END

		Map<?, ?> resultSelLine = webEssRcv.callRfcFunction("ZHR_RFC_PA_042_CHECK_SEL_LINE", rfcParam, rfcParamList, request);

		Map<?, ?> exList = (Map<?, ?>)resultSelLine.get("EX_LIST");

		for(String key : keySet){
			params.remove(key);
			params.put("SEL_"+key, exList.get(key));
		}

		Map<?, ?> resultMapCode = webEssRcv.callRfcFunction("ZHR_RFC_PA_042_GET_LISTBOX", rfcParam, null, request);

		mav.addObject("useItemList", resultMapCode.get("ET_CAFCD"));//사용항목 리스트
		mav.addObject("keySet"     , keySet);//리스트 선택 키셋
		mav.addObject("params"     , params);

		return mav;
	}

	@RequestMapping(value = "/cafeteriaProcess.do")
	public @ResponseBody Map<?, ?> cafeteriaProcess(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> rfcParamList = new HashMap<String, Object>();

		rfcParam.put("IM_PERNR"   , user.getEmpNo());
		rfcParam.put("IM_EVENT_ID", params.get("eventId"));
		rfcParam.put("IM_PAGE"    , params.get("page"));
		rfcParam.put("IM_YEAR"    , params.get("imYear"));

		HashMap<String, Object> paramRow = new HashMap<String, Object>();//IM_LIST

		//리스트에서 선택한 데이터 추출, 입력한 데이터 추출
		String keySet[] = Constant.cafeteriaListKeySet;

		for(String key : keySet){
			if("CAFCD".equals(key)){
				paramRow.put(key, params.get("useItem"));
			}else if("USEDT".equals(key)){
				paramRow.put(key, params.get("useDate"));
			}else if("PROPT".equals(key)){
				paramRow.put(key, params.get("requestPoint"));
			}else if("RENPT".equals(key)){
				paramRow.put(key, params.get("remainPoint"));
			}else{
				paramRow.put(key, params.containsKey("SEL_"+key) ? params.get("SEL_"+key) : "");
			}
		}

		rfcParam.put("IM_LIST", paramRow);

		//코드값 추출
		Map<?, ?> resultMapCode = webEssRcv.callRfcFunction("ZHR_RFC_PA_042_GET_LISTBOX", rfcParam, null, request);

		rfcParamList.put("IT_CAFCD", resultMapCode.get("ET_CAFCD"));
		//RFC PARAMETER SETTING END

		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_PA_042_PROCESS", rfcParam, rfcParamList, request);

		return result;
	}

	@RequestMapping(value = "checkedRowValidationPA042.do")
	public @ResponseBody Map<?, ?> checkedRowValidationPA042(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");

		String[] keySet = Constant.cafeteriaListKeySet;

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> paramRow = new HashMap<String, Object>();

		rfcParam.put("IM_PERNR", user.getEmpNo());

		for(String key : keySet){
			paramRow.put(key, params.get(key));
		}

		rfcParam.put("IM_LIST"    , paramRow);
		rfcParam.put("IM_YEAR"    , params.get("imYear"));
		rfcParam.put("IM_EVENT_ID", params.get("eventId"));
		//RFC PARAMETER SETTING END

		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_PA_042_CHECK_SEL_LINE", rfcParam, null, request);

		return result;
	}

	@RequestMapping(value = "/expenseList.do")
	public ModelAndView expenseList(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/webess/personalMng/expenseList");

		User user = (User) getRequestAttribute("ikep.user");

		String thisYear = DateUtil.getToday("yyyy");
		String thisMonth = DateUtil.getToday("MM");

		if(!params.containsKey("imYear")){
			params.put("imYear", thisYear);
		}

		if(!params.containsKey("imMonth")){
			params.put("imMonth", thisMonth);
		}

		if(!params.containsKey("imMassn")){
			params.put("imMassn", "00");
		}

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		rfcParam.put("IM_YEAR" , params.get("imYear"));
		rfcParam.put("IM_MONTH", params.get("imMonth"));
		rfcParam.put("IM_PERNR", user.getEmpNo());
		rfcParam.put("IM_MASSN", params.get("imMassn"));
		//RFC PARAMETER SETTING END

		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_PT_006_GET_DATA", rfcParam, null, request);

		mav.addObject("resultList", result.get("ET_LIST"));
		mav.addObject("keySet"    , Constant.expenseListKeySet);//리스트 키셋
		mav.addObject("params"    , params);

		return mav;
	}

	@RequestMapping(value = "checkedRowValidationPT006.do")
	public @ResponseBody Map<?, ?> checkedRowValidationPT006(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");

		String[] keySet = Constant.expenseListKeySet;

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> paramRow = new HashMap<String, Object>();

		rfcParam.put("IM_PERNR", user.getEmpNo());
		rfcParam.put("IM_EVENT_ID", params.get("eventId"));

		for(String key : keySet){
			paramRow.put(key, params.get(key));
		}

		rfcParam.put("IM_LIST", paramRow);
		//RFC PARAMETER SETTING END

		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_PT_006_CHECK_SEL_LINE", rfcParam, null, request);

		return result;
	}

	@RequestMapping(value = "/expenseView.do")
	public ModelAndView expenseView(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		String jspUrl = "/portal/webess/personalMng/expenseRegView";
		String page = "CREATE";

		if(params.containsKey("jspType") && "view".equals((String)params.get("jspType"))){
			jspUrl = "/portal/webess/personalMng/expenseView";
			page = "DISPLAY";
		}else{
			if(params.containsKey("dataLoad") && "Y".equals((String)params.get("dataLoad"))){
				page = "CHANGE";
			}
		}

		ModelAndView mav = new ModelAndView(jspUrl);

		User user = (User) getRequestAttribute("ikep.user");

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> paramRow = new HashMap<String, Object>();

		rfcParam.put("IM_PERNR", user.getEmpNo());
		rfcParam.put("IM_FLAG" , params.get("exFlag"));
		rfcParam.put("IM_PAGE" , page);
		rfcParam.put("IM_INFTY", "9999");

		String keySet[] = Constant.expenseListKeySet;

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

		Map<?, ?> resultMapCode = webEssRcv.callRfcFunction("ZHR_RFC_PT_006_GET_LISTBOX", rfcParam, null, request);

		Map<?, ?> resultMapList = webEssRcv.callRfcFunction("ZHR_RFC_PT_006_GET_APPLINE", rfcParam, null, request);

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
		}else if("CREATE".equals(page)){
			params.put("toDay", DateUtil.getToday("yyyy-MM-dd"));
		}

		mav.addObject("resultMapList"   , resultMapList);//결재자 리스트
		mav.addObject("reqTypeList"     , resultMapCode.get("ET_REQGN"));//신청 구분
		mav.addObject("areaTypeList"    , resultMapCode.get("ET_AREA"));//국내외 구분
		mav.addObject("moveTypeList"    , resultMapCode.get("ET_MOVTY"));//이동 구분
		mav.addObject("sleepTypeList"   , resultMapCode.get("ET_SLEEP"));//숙박 구분
		mav.addObject("marriageTypeList", resultMapCode.get("ET_MARRY"));//미혼/기혼 구분
		mav.addObject("keySet"          , Constant.expenseListKeySet);//리스트 선택 키셋
		mav.addObject("apprKeySet"      , Constant.apprKeySet);//결재자 리스트 키셋
		mav.addObject("params"          , params);

		return mav;
	}

	@RequestMapping(value = "/expenseProcess.do")
	public @ResponseBody Map<?, ?> expenseProcess(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> rfcParamList = new HashMap<String, Object>();

		rfcParam.put("IM_PERNR"      , user.getEmpNo());
		rfcParam.put("IM_EVENT_ID"   , params.get("eventId"));
		rfcParam.put("IM_PAGE"       , params.get("page"));
		rfcParam.put("IM_FLAG"       , params.get("exFlag"));
		rfcParam.put("IM_PA_DISABLED", params.get("paDisabled"));
		rfcParam.put("IM_INFTY"      , "9999");

		HashMap<String, Object> paramRow = new HashMap<String, Object>();//IM_LIST
		HashMap<String, Object> selParamRow = new HashMap<String, Object>();//IM_LIST_SEL

		//리스트에서 선택한 데이터 추출, 입력한 데이터 추출
		String keySet[] = Constant.expenseListKeySet;

		String reqType = (String) params.get("reqType");

		for(String key : keySet){
			selParamRow.put(key, params.containsKey("SEL_"+key) ? params.get("SEL_"+key) : "");

			if("REQDT".equals(key)){
				paramRow.put(key, params.get("requestDate"));
			}else if("REQGN".equals(key)){
				paramRow.put(key, params.get("reqType"));
			}else if("ZAREA".equals(key)){
				paramRow.put(key, params.get("areaType"));
			}else if("ZBUDT".equals(key)){
				paramRow.put(key, params.get("dispatchDate"));
			}else if("ZMODT".equals(key)){
				paramRow.put(key, params.get("moveDate"));
			}else if("MOVTY".equals(key)){
				paramRow.put(key, params.get("moveType"));
			}else if("SLEEP".equals(key)){
				paramRow.put(key, params.get("sleepType"));
			}else if("MARRY".equals(key)){
				paramRow.put(key, params.get("marriageType"));
			}else if("MOVMT".equals(key)){
				paramRow.put(key, params.get("amount"));
			}else if("MOVDT".equals(key)){
				if("1".equals(reqType)){
					paramRow.put(key, params.get("moveDate"));
				}else{
					paramRow.put(key, params.get("dispatchDate"));
				}
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
		Map<?, ?> resultMapCode = webEssRcv.callRfcFunction("ZHR_RFC_PT_006_GET_LISTBOX", rfcParam, null, request);

		Map<?, ?> resultMapAppline = webEssRcv.callRfcFunction("ZHR_RFC_PT_006_GET_APPLINE", rfcParam, null, request);

		rfcParamList.put("IT_LINE" , apprList);
		rfcParamList.put("IT_APPNR", resultMapAppline.get("ET_APPNR"));
		rfcParamList.put("IT_ASTAT", resultMapCode.get("ET_ASTAT"));
		rfcParamList.put("IT_REQGN", resultMapCode.get("ET_REQGN"));
		//RFC PARAMETER SETTING END

		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_PT_006_PROCESS", rfcParam, rfcParamList, request);

		return result;
	}

	@RequestMapping(value = "retrieveSetReqgn.do")
	public @ResponseBody Map<?, ?> retrieveSetReqgn(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		rfcParam.put("IM_REQGN"      , params.get("reqType"));
		rfcParam.put("IM_PA_DISABLED", params.get("paDisabled"));
		rfcParam.put("IM_ZMODT"      , params.get("moveDate"));
		rfcParam.put("IM_ZBUDT"      , params.get("dispatchDate"));
		//RFC PARAMETER SETTING END

		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_PT_006_SET_REQGN", rfcParam, null, request);

		return result;
	}

	@RequestMapping(value = "retrieveGetArea.do")
	public @ResponseBody Map<?, ?> retrieveGetArea(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		rfcParam.put("IM_PERNR", user.getEmpNo());
		rfcParam.put("IM_REQDT", params.get("requestDate"));
		rfcParam.put("IM_AREA" , params.get("areaType"));
		//RFC PARAMETER SETTING END

		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_PT_006_GET_AREA", rfcParam, null, request);

		return result;
	}

	@RequestMapping(value = "retrieveGetMovamt.do")
	public @ResponseBody Map<?, ?> retrieveGetMovamt(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		rfcParam.put("IM_PERNR", user.getEmpNo());
		rfcParam.put("IM_MOVTY", params.get("moveType"));
		//RFC PARAMETER SETTING END

		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_PT_006_GET_MOVAMT", rfcParam, null, request);

		return result;
	}

	@RequestMapping(value = "/clubList.do")
	public ModelAndView clubList(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/webess/personalMng/clubList");

		User user = (User) getRequestAttribute("ikep.user");

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> rfcParamList = new HashMap<String, Object>();

		rfcParam.put("IM_PERNR", user.getEmpNo());

		Map<?, ?> resultMapCode = webEssRcv.callRfcFunction("ZHR_RFC_BE_003_GET_LISTBOX", rfcParam, null, request);
		//RFC PARAMETER SETTING END

		rfcParamList.put("IT_ASTAT", resultMapCode.get("ET_ASTAT"));
		rfcParamList.put("IT_INFCD", resultMapCode.get("ET_INFCD"));
		rfcParamList.put("IT_INFGR", resultMapCode.get("ET_INFGR"));

		Map<?, ?> resultMapList = webEssRcv.callRfcFunction("ZHR_RFC_BE_003_GET_DATA", rfcParam, rfcParamList, request);

		mav.addObject("resultList", resultMapList.get("ET_LIST"));
		mav.addObject("keySet"    , Constant.clubListKeySet);//리스트 키셋
		mav.addObject("params"    , params);

		return mav;
	}

	@RequestMapping(value = "/clubView.do")
	public ModelAndView clubView(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		String jspUrl = "/portal/webess/personalMng/clubRegView";
		String page = "CREATE";

		if(params.containsKey("jspType") && "view".equals((String)params.get("jspType"))){
			jspUrl = "/portal/webess/personalMng/clubView";
			page = "DISPLAY";
		}else{
			if(params.containsKey("dataLoad") && "Y".equals((String)params.get("dataLoad"))){
				page = "CHANGE";
			}
		}

		ModelAndView mav = new ModelAndView(jspUrl);

		User user = (User) getRequestAttribute("ikep.user");

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		rfcParam.put("IM_PERNR", user.getEmpNo());
		//RFC PARAMETER SETTING END

		String keySet[] = Constant.clubListKeySet;

		if("CREATE".equals(page)){
			params.put("toDay", DateUtil.getToday("yyyy-MM-dd"));
		}else{
			for(String key : keySet){
				params.put("SEL_"+key, params.get(key));
				params.remove(key);
			}
		}

		Map<?, ?> resultMapCode = webEssRcv.callRfcFunction("ZHR_RFC_BE_003_GET_LISTBOX", rfcParam, null, request);

		mav.addObject("joinTypeList"  , resultMapCode.get("ET_JOIN"));//가입/탈퇴 구분
		mav.addObject("clubList"      , resultMapCode.get("ET_INFCD"));//동호회명
		mav.addObject("deductTypeList", resultMapCode.get("ET_PAYGN"));//공제구분
		mav.addObject("keySet"        , Constant.clubListKeySet);//리스트 선택 키셋
		mav.addObject("infcdKeySet"   , Constant.infcdKeySet);//동호회 정보 키셋
		mav.addObject("apprKeySet"    , Constant.apprKeySet);//결재자 키셋
		mav.addObject("params"        , params);

		return mav;
	}

	@RequestMapping(value = "/clubProcess.do")
	public @ResponseBody Map<?, ?> clubProcess(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> rfcParamList = new HashMap<String, Object>();

		rfcParam.put("IM_PERNR"      , user.getEmpNo());
		rfcParam.put("IM_EVENT_ID"   , params.get("eventId"));
		rfcParam.put("IM_PAGE"       , params.get("page"));
		rfcParam.put("IM_FLAG"       , params.get("exFlag"));
		rfcParam.put("IM_BEGDA"      , params.get("requestDate"));

		HashMap<String, Object> paramRow = new HashMap<String, Object>();//IM_LIST

		//리스트에서 선택한 데이터 추출, 입력한 데이터 추출
		String keySet[] = Constant.clubListKeySet;

		for(String key : keySet){
			if("ZJOIN".equals(key)){
				paramRow.put(key, params.get("joinType"));
			}else if("INFCD".equals(key)){
				paramRow.put(key, params.get("club"));
			}else if("PAYGN".equals(key)){
				paramRow.put(key, params.get("deductType"));
			}else if("MBAMT".equals(key)){
				paramRow.put(key, params.get("dues"));
			}else if("PERNR".equals(key)){
				paramRow.put(key, user.getEmpNo());
			}else{
				paramRow.put(key, params.containsKey("SEL_"+key) ? params.get("SEL_"+key) : "");
			}
		}

		rfcParam.put("IM_LIST", paramRow);

		//결재라인 추출
		ArrayList<HashMap<String, Object>> apprList = new ArrayList<HashMap<String, Object>>();//IT_LINE
		HashMap<String, Object> apprRow = null;

		String apprKeySet[] = Constant.apprKeySet;
		int apprCnt = Integer.parseInt(((String)params.get("apprCnt")));

		if(apprCnt == 1){
			apprRow = new HashMap<String, Object>();//IT_LINE(row)
			for(String apprKey : apprKeySet){
				apprRow.put(apprKey, params.containsKey("ETLINE_"+apprKey) ? params.get("ETLINE_"+apprKey) : "");
			}
			apprList.add(apprRow);
		}else if(apprCnt > 1){
			for(int i=0 ; i<apprCnt ; i++){
				apprRow = new HashMap<String, Object>();//IT_LINE(row)
				for(String apprKey : apprKeySet){
					apprRow.put(apprKey, request.getParameterValues("ETLINE_"+apprKey)[i]);
				}
				apprList.add(apprRow);
			}
		}

		//코드값 추출
		Map<?, ?> resultMapCode = webEssRcv.callRfcFunction("ZHR_RFC_BE_003_GET_LISTBOX", rfcParam, null, request);

		rfcParamList.put("IT_LINE" , apprList);
		rfcParamList.put("IT_ASTAT", resultMapCode.get("ET_ASTAT"));
		//RFC PARAMETER SETTING END

		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_BE_003_PROCESS", rfcParam, rfcParamList, request);

		return result;
	}

	@RequestMapping(value = "/clubDelete.do")
	public @ResponseBody Map<?, ?> clubDelete(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		rfcParam.put("IM_PERNR", user.getEmpNo());
		rfcParam.put("IM_APPID", params.get("SEL_APPID"));
		rfcParam.put("IM_ASTAT", params.get("SEL_ASTAT"));
		//RFC PARAMETER SETTING END

		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_BE_003_DELETE", rfcParam, null, request);

		return result;
	}

	@RequestMapping(value = "/retrieveGetJoinCircle.do")
	public @ResponseBody Map<?, ?> retrieveGetJoinCircle(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");

		String[] infcdKeySet = Constant.infcdKeySet;

		ArrayList<HashMap<String, Object>> infcdList = new ArrayList<HashMap<String, Object>>();//IT_INFCD
		HashMap<String, Object> infcdRow = null;

		int infcdCnt = Integer.parseInt(((String)params.get("infcdCnt")));

		if(infcdCnt == 1){
			infcdRow = new HashMap<String, Object>();
			for(String key : infcdKeySet){
				infcdRow.put(key, params.containsKey("INFCD_"+key) ? params.get("INFCD_"+key) : "");
			}
			infcdList.add(infcdRow);
		}else if(infcdCnt > 1){
			for(int i=0 ; i<infcdCnt ; i++){
				infcdRow = new HashMap<String, Object>();
				for(String key : infcdKeySet){
					infcdRow.put(key, request.getParameterValues("INFCD_"+key)[i]);
				}
				infcdList.add(infcdRow);
			}
		}

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> rfcParamList = new HashMap<String, Object>();

		String joinType = (String) params.get("joinType");

		rfcParam.put("IM_PERNR", user.getEmpNo());
		rfcParam.put("IM_JOIN" , "1".equals(joinType) ? "X" : "J");

		rfcParamList.put("IT_INFCD", infcdList);
		//RFC PARAMETER SETTING END

		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_BE_003_GET_JOIN_CIRCLE", rfcParam, rfcParamList, request);

		return result;
	}

	@RequestMapping(value = "/retrieveGetMbamt.do")
	public @ResponseBody Map<?, ?> retrieveGetMbamt(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		String[] keySet = Constant.clubListKeySet;
		String[] infcdKeySet = Constant.infcdKeySet;

		ArrayList<HashMap<String, Object>> infcdList = new ArrayList<HashMap<String, Object>>();//IT_INFCD
		HashMap<String, Object> infcdRow = null;

		int infcdCnt = Integer.parseInt(((String)params.get("infcdCnt")));

		if(infcdCnt == 1){
			infcdRow = new HashMap<String, Object>();
			for(String key : infcdKeySet){
				infcdRow.put(key, params.containsKey("INFCD_"+key) ? params.get("INFCD_"+key) : "");
			}
			infcdList.add(infcdRow);
		}else if(infcdCnt > 1){
			for(int i=0 ; i<infcdCnt ; i++){
				infcdRow = new HashMap<String, Object>();
				for(String key : infcdKeySet){
					infcdRow.put(key, request.getParameterValues("INFCD_"+key)[i]);
				}
				infcdList.add(infcdRow);
			}
		}

		HashMap<String, Object> paramRow = new HashMap<String, Object>();

		for(String key : keySet){
			if("ZJOIN".equals(key)){
				paramRow.put(key, params.get("joinType"));
			}else if("INFCD".equals(key)){
				paramRow.put(key, params.get("club"));
			}else if("PAYGN".equals(key)){
				paramRow.put(key, params.get("deductType"));
			}else if("MBAMT".equals(key)){
				paramRow.put(key, params.get("dues"));
			}else{
				paramRow.put(key, params.containsKey("SEL_"+key) ? params.get("SEL_"+key) : "");
			}
		}

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> rfcParamList = new HashMap<String, Object>();


		rfcParam.put("IM_LIST", paramRow);

		rfcParamList.put("IT_INFCD", infcdList);
		//RFC PARAMETER SETTING END

		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_BE_003_GET_MBAMT", rfcParam, rfcParamList, request);

		return result;
	}

	@RequestMapping(value = "/certificateEmpList.do")
	public ModelAndView certificateEmpList(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/webess/personalMng/certificateEmpList");

		User user = (User) getRequestAttribute("ikep.user");

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		rfcParam.put("IM_PERNR", user.getEmpNo());
		//RFC PARAMETER SETTING END

		Map<?, ?> resultMapList = webEssRcv.callRfcFunction("ZHR_RFC_BE_002_GET_DATA", rfcParam, null, request);

		mav.addObject("resultList", resultMapList.get("ET_LIST"));
		mav.addObject("keySet"    , Constant.certificateEmpListKeySet);//리스트 키셋
		mav.addObject("params"    , params);

		return mav;
	}
	
	@RequestMapping(value = "/certificateList.do")
	public ModelAndView certificateList(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/webess/personalMng/certificateList");

		User user = (User) getRequestAttribute("ikep.user");

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		rfcParam.put("IM_PERNR", user.getEmpNo());
		//RFC PARAMETER SETTING END

		Map<?, ?> resultMapList = webEssRcv.callRfcFunction("ZHR_RFC_BE_002_GET_DATA", rfcParam, null, request);

		mav.addObject("resultList", resultMapList.get("ET_LIST"));
		mav.addObject("keySet"    , Constant.certificateListKeySet);//리스트 키셋
		mav.addObject("params"    , params);

		return mav;
	}
	
	@RequestMapping(value = "/certificateDelete.do")
	public @ResponseBody Map<?, ?> certificateDelete(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");

		String[] keySet = Constant.certificateListKeySet;

		HashMap<String, Object> paramRow = new HashMap<String, Object>();

		for(String key : keySet){
			paramRow.put(key, params.containsKey(key) ? params.get(key) : "");
		}

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		rfcParam.put("IM_APPID", params.get("APPID"));
		rfcParam.put("IM_PERNR", user.getEmpNo());
		rfcParam.put("IM_LIST" , paramRow);
		//RFC PARAMETER SETTING END

		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_BE_002_DELETE", rfcParam, null, request);

		return result;
	}

	@RequestMapping(value = "/certificateEmpDelete.do")
	public @ResponseBody Map<?, ?> certificateEmpDelete(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");

		String[] keySet = Constant.certificateEmpListKeySet;

		HashMap<String, Object> paramRow = new HashMap<String, Object>();

		for(String key : keySet){
			paramRow.put(key, params.containsKey(key) ? params.get(key) : "");
		}

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		rfcParam.put("IM_PERNR", user.getEmpNo());
		rfcParam.put("IM_LIST" , paramRow);
		//RFC PARAMETER SETTING END

		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_BE_002_DELETE", rfcParam, null, request);

		return result;
	}

	@RequestMapping(value = "/checkedRowBe002SetEntryCheck.do")
	public @ResponseBody Map<?, ?> checkedRowBe002SetEntryCheck(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");

		String[] keySet = Constant.certificateEmpListKeySet;

		HashMap<String, Object> paramRow = new HashMap<String, Object>();

		for(String key : keySet){
			paramRow.put(key, params.containsKey(key) ? params.get(key) : "");
		}

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		rfcParam.put("IM_PERNR", user.getEmpNo());
		rfcParam.put("IM_LIST" , paramRow);
		//RFC PARAMETER SETTING END

		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_BE_002_SET_ENTRY_CHECK", rfcParam, null, request);

		return result;
	}

	@RequestMapping(value = "/certificateEmpRegView.do")
	public ModelAndView certificateEmpRegView(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/webess/personalMng/certificateEmpRegView");

		User user = (User) getRequestAttribute("ikep.user");

		String[] keySet = Constant.certificateEmpListKeySet;

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		rfcParam.put("IM_PERNR", user.getEmpNo());
		//RFC PARAMETER SETTING END

		params.put("toDay", DateUtil.getToday("yyyy-MM-dd"));

		Map<?, ?> resultMap = webEssRcv.callRfcFunction("ZHR_RFC_BE_002_ONCREATE", rfcParam, null, request);

		Map<?, ?> resultMapCode = webEssRcv.callRfcFunction("ZHR_RFC_BE_002_GET_LISTBOX", null, null, request);

		Map<?, ?> exList = (Map<?, ?>)resultMap.get("EX_LIST");

		for(String key : keySet){
			params.put(key, exList.get(key));
		}

		mav.addObject("copyList"  , resultMapCode.get("ET_COPIES"));
		mav.addObject("keySet"    , keySet);//리스트 키셋
		mav.addObject("params"    , params);

		return mav;
	}
	
	@RequestMapping(value = "/certificatePrint.do")
	public ModelAndView certificatePrint(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		

		User user = (User) getRequestAttribute("ikep.user");

		String[] keySet = Constant.certificateListKeySet;

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam1 = new HashMap<String, Object>();

		rfcParam1.put("IM_PERNR", user.getEmpNo());
		rfcParam1.put("IM_ZECERTI", params.get("IM_CERTI"));
		rfcParam1.put("IM_APPID", params.get("IM_APPID"));
		rfcParam1.put("IM_BEGDA", params.get("IM_BEGDA"));
		rfcParam1.put("IM_WERKS", params.get("IM_WERKS"));
		//RFC PARAMETER SETTING END

		params.put("toDay", DateUtil.getToday("yyyy-MM-dd"));

		Map<?, ?> resultMap = webEssRcv.callRfcFunction("ZHR_RFC_BE_002_SELFPRINT", rfcParam1, null, request);

		/*Map<?, ?> exList = (Map<?, ?>)resultMap.get("EX_LIST");

		for(String key : keySet){
			params.put(key, exList.get(key));
		}*/

		//mav.addObject("keySet"    , keySet);//리스트 키셋
		ModelAndView mav = new ModelAndView("/portal/webess/personalMng/certificatePrint");
		mav.addObject("EX_CERTI1"  , resultMap.get("EX_CERTI1"));
		mav.addObject("today", 					DateUtil.getToday("yyyy.MM.dd"));
		mav.addObject("IM_WERKS", 					params.get("IM_WERKS"));
		return mav;
		
	}
	
	@RequestMapping(value = "/certificatePrintPDF.do")
	public ModelAndView certificatePrintPDF(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		

		User user = (User) getRequestAttribute("ikep.user");

		String[] keySet = Constant.certificateListKeySet;

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam1 = new HashMap<String, Object>();

		rfcParam1.put("IM_PERNR", user.getEmpNo());
		rfcParam1.put("IM_ZECERTI", params.get("IM_CERTI"));
		rfcParam1.put("IM_APPID", params.get("IM_APPID"));
		rfcParam1.put("IM_BEGDA", params.get("IM_BEGDA"));
		rfcParam1.put("IM_WERKS", params.get("IM_WERKS"));
		//RFC PARAMETER SETTING END

		params.put("toDay", DateUtil.getToday("yyyy-MM-dd"));

		//Map<?, ?> resultMap = webEssRcv.callRfcFunction("ZHR_RFC_BE_002_SELFPRINT", rfcParam1, null, request);

		/*Map<?, ?> exList = (Map<?, ?>)resultMap.get("EX_LIST");

		for(String key : keySet){
			params.put(key, exList.get(key));
		}*/

		//mav.addObject("keySet"    , keySet);//리스트 키셋
		ModelAndView mav = new ModelAndView("/portal/webess/personalMng/taxWithholdingSimpleView");
		String yearFlg = "0";
		if(!params.containsKey("imYear")){
			params.put("imYear", Integer.parseInt(DateUtil.getToday("yyyy")) - 1);
			yearFlg = "1";
		}

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		rfcParam.put("IM_YEAR" , params.get("imYear"));
		rfcParam.put("IM_PERNR", user.getEmpNo());
		//RFC PARAMETER SETTING END
		Map<?, ?> resultMapCode = webEssRcv.callRfcFunction("ZHR_RFC_PY_007_GET_LISTBOX", rfcParam, null, request);

		List<HashMap<String, String>> tempYearList = (List<HashMap<String, String>>) resultMapCode.get("ET_YEAR");
		String year = "";
		for(HashMap<String, String> tempYear : tempYearList){
			if(year == "" && yearFlg == "1"){
				year = tempYear.values().toString().substring(1, 5);
			
				params.put("imYear", year);
				rfcParam.put("IM_YEAR" , year);
				resultMapCode = webEssRcv.callRfcFunction("ZHR_RFC_PY_007_GET_LISTBOX", rfcParam, null, request);
			}
		}
		
		mav.addObject("yearList", resultMapCode.get("ET_YEAR"));
		mav.addObject("params"  , params);

		return mav;
		
	}
	
	@RequestMapping(value = "/certificateView.do")
	public ModelAndView certificateView(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {
		
		String jspUrl = "/portal/webess/personalMng/certificateRegView";
		String page = "CREATE";
		String page2 = "";

		if(params.containsKey("jspType") && "view".equals((String)params.get("jspType"))){
			jspUrl = "/portal/webess/personalMng/certificateView";
			page = "DISPLAY";
			page2 = "VIEW";
		}else{
			if(params.containsKey("dataLoad") && "Y".equals((String)params.get("dataLoad"))){
				page = "CHANGE";
				page2 = "CHANGE";
			}
		}

		ModelAndView mav = new ModelAndView(jspUrl);

		User user = (User) getRequestAttribute("ikep.user");

		String[] keySet = Constant.certificateListKeySet;

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		

		HashMap<String, Object> paramRow = new HashMap<String, Object>();
		
		rfcParam.put("IM_PERNR", user.getEmpNo());
		rfcParam.put("IM_FLAG" , params.get("exFlag"));
		rfcParam.put("IM_PAGE" , page);
		rfcParam.put("IM_INFTY", "9920");

		for(String key : keySet){
			paramRow.put(key, params.get(key));
		}

		rfcParam.put("IM_LIST", paramRow);

		if(!"CREATE".equals(page)){
			for(String key : keySet){
				params.put("SEL_"+key, params.get(key));
			}
		}else if("CREATE".equals(page)){
			params.put("toDay", DateUtil.getToday("yyyy-MM-dd"));
		}
		//RFC PARAMETER SETTING END
		
		Map<?, ?> resultMapList = webEssRcv.callRfcFunction("ZHR_RFC_BE_002_GET_APPLINE", rfcParam, null, request);

		Map<?, ?> resultMap = webEssRcv.callRfcFunction("ZHR_RFC_BE_002_ONCREATE", rfcParam, null, request);

		Map<?, ?> resultMapCode = webEssRcv.callRfcFunction("ZHR_RFC_BE_002_GET_LISTBOX", null, null, request);
		
		

		Map<?, ?> exList = (Map<?, ?>)resultMap.get("EX_LIST");
		if("".equals(page2)){
			for(String key : keySet){
				params.put(key, exList.get(key));
			}
		}
		
		String astat = (String) params.get("ASTAT");

		if("05".equals(astat) || "16".equals(astat) || "13".equals(astat)){

			rfcParam.put("IM_APPID", params.get("APPID"));

			Map<?, ?> resultRefuse = webEssRcv.callRfcFunction("ZHR_RFC_READ_REFUSE_REASON", rfcParam, null, request);
			mav.addObject("resultRefuse", resultRefuse);
		}
		
		mav.addObject("resultMapList", resultMapList);//결재자 리스트
		mav.addObject("copyList"  , resultMapCode.get("ET_COPIES"));
		mav.addObject("receiveList"  , resultMapCode.get("ET_RECEIVE"));
		mav.addObject("useforList"  , resultMapCode.get("ET_USEFOR"));
		mav.addObject("certiList"  , resultMapCode.get("ET_CERTI"));
		mav.addObject("keySet"    , keySet);//리스트 키셋
		mav.addObject("apprKeySet"   , Constant.apprKeySet);//결재자 리스트 키셋
		mav.addObject("params"    , params);

		return mav;
	}

	@RequestMapping(value = "/certificateEmpProcess.do")
	public @ResponseBody Map<?, ?> certificateEmpProcess(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");

		String[] keySet = Constant.certificateEmpListKeySet;

		HashMap<String, Object> paramRow = new HashMap<String, Object>();

		for(String key : keySet){

			if("BEGDA".equals(key)){
				paramRow.put(key, params.get("requestDate"));
			}else if("COPIES".equals(key)){
				paramRow.put(key, params.get("copy"));
			}else if("SUBMIT".equals(key)){
				paramRow.put(key, params.get("submission"));
			}else if("PURPS".equals(key)){
				paramRow.put(key, params.get("purpose"));
			}else if("DETAIL".equals(key)){
				paramRow.put(key, params.get("specialNote"));
			}else{
				paramRow.put(key, params.containsKey("SEL_"+key) ? params.get("SEL_"+key) : "");
			}
		}

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		rfcParam.put("IM_PERNR", user.getEmpNo());
		rfcParam.put("IM_LIST" , paramRow);
		//RFC PARAMETER SETTING END

		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_BE_002_PROCESS", rfcParam, null, request);

		return result;
	}
	
	@RequestMapping(value = "/certificateProcess.do")
	public @ResponseBody Map<?, ?> certificateProcess(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");

		String[] keySet = Constant.certificateListKeySet;

		HashMap<String, Object> paramRow = new HashMap<String, Object>();

		for(String key : keySet){

			if("BEGDA".equals(key)){
				paramRow.put(key, params.get("requestDate"));
			}else if("COPIES".equals(key)){
				paramRow.put(key, params.get("copy"));
			}else if("SUBMIT".equals(key)){
				paramRow.put(key, params.get("submission"));
			}else if("PURPS".equals(key)){
				paramRow.put(key, params.get("purpose"));
			}else if("DETAIL".equals(key)){
				paramRow.put(key, params.get("specialNote"));
			}else if("RECEIVE".equals(key)){
				paramRow.put(key, params.get("receive"));
			}else if("CPURPS".equals(key)){
				paramRow.put(key, params.get("cpurps"));
			}else if("CERTI".equals(key)){
				paramRow.put(key, params.get("certi"));
			}else{
				paramRow.put(key, params.containsKey("SEL_"+key) ? params.get("SEL_"+key) : "");
			}
		}

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> rfcParamList = new HashMap<String, Object>();
		
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

		rfcParam.put("IM_PERNR", user.getEmpNo());
		//RFC PARAMETER SETTING END
		
		Map<?, ?> resultMapAppline = webEssRcv.callRfcFunction("ZHR_RFC_BE_002_GET_APPLINE", rfcParam, null, request);

		rfcParamList.put("IT_LINE" , apprList);
		rfcParamList.put("IT_APPNR", resultMapAppline.get("ET_APPNR"));

		//Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_BE_002_PROCESS", rfcParam, null, request);
		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_BE_002_PROCESS", rfcParam, rfcParamList, request);

		return result;
	}

	@RequestMapping(value = "/taxWithholdingList.do")
	public ModelAndView taxWithholdingList(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/webess/personalMng/taxWithholdingList");

		User user = (User) getRequestAttribute("ikep.user");

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		rfcParam.put("IM_PERNR", user.getEmpNo());
		//RFC PARAMETER SETTING END

		Map<?, ?> resultMapList = webEssRcv.callRfcFunction("ZHR_RFC_BE_005_GET_DATA", rfcParam, null, request);

		mav.addObject("resultList", resultMapList.get("ET_LIST"));
		mav.addObject("keySet"    , Constant.taxWithholdingListKeySet);//리스트 키셋
		mav.addObject("params"    , params);

		return mav;
	}

	@RequestMapping(value = "/taxWithholdingDelete.do")
	public @ResponseBody Map<?, ?> taxWithholdingDelete(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");

		String[] keySet = Constant.taxWithholdingListKeySet;

		HashMap<String, Object> paramRow = new HashMap<String, Object>();

		for(String key : keySet){
			paramRow.put(key, params.containsKey(key) ? params.get(key) : "");
		}

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		rfcParam.put("IM_PERNR", user.getEmpNo());
		rfcParam.put("IM_LIST" , paramRow);
		//RFC PARAMETER SETTING END

		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_BE_005_DELETE", rfcParam, null, request);

		return result;
	}

	@RequestMapping(value = "/checkedRowBe005SetEntryCheck.do")
	public @ResponseBody Map<?, ?> checkedRowBe005SetEntryCheck(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");

		String[] keySet = Constant.taxWithholdingListKeySet;

		HashMap<String, Object> paramRow = new HashMap<String, Object>();

		for(String key : keySet){
			paramRow.put(key, params.containsKey(key) ? params.get(key) : "");
		}

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		rfcParam.put("IM_PERNR", user.getEmpNo());
		rfcParam.put("IM_LIST" , paramRow);
		//RFC PARAMETER SETTING END

		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_BE_005_SET_ENTRY_CHECK", rfcParam, null, request);

		return result;
	}

	@RequestMapping(value = "/taxWithholdingRegView.do")
	public ModelAndView taxWithholdingRegView(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/webess/personalMng/taxWithholdingRegView");

		User user = (User) getRequestAttribute("ikep.user");

		String[] keySet = Constant.taxWithholdingListKeySet;

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		rfcParam.put("IM_PERNR", user.getEmpNo());
		//RFC PARAMETER SETTING END

		params.put("toDay", DateUtil.getToday("yyyy-MM-dd"));

		Map<?, ?> resultMap = webEssRcv.callRfcFunction("ZHR_RFC_BE_005_ONCREATE", rfcParam, null, request);

		Map<?, ?> resultMapCode = webEssRcv.callRfcFunction("ZHR_RFC_BE_005_GET_LISTBOX", null, null, request);

		Map<?, ?> exList = (Map<?, ?>)resultMap.get("EX_LIST");

		for(String key : keySet){
			params.put(key, exList.get(key));
		}

		mav.addObject("copyList"  , resultMapCode.get("ET_COPIES"));
		mav.addObject("keySet"    , keySet);//리스트 키셋
		mav.addObject("params"    , params);

		return mav;
	}

	@RequestMapping(value = "/taxWithholdingProcess.do")
	public @ResponseBody Map<?, ?> taxWithholdingProcess(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");

		String[] keySet = Constant.taxWithholdingListKeySet;

		HashMap<String, Object> paramRow = new HashMap<String, Object>();

		for(String key : keySet){

			if("BEGDA".equals(key)){
				paramRow.put(key, params.get("requestDate"));
			}else if("COPIES".equals(key)){
				paramRow.put(key, params.get("copy"));
			}else if("SUBMIT".equals(key)){
				paramRow.put(key, params.get("submission"));
			}else if("PURPS".equals(key)){
				paramRow.put(key, params.get("purpose"));
			}else if("DETAIL".equals(key)){
				paramRow.put(key, params.get("specialNote"));
			}else{
				paramRow.put(key, params.containsKey("SEL_"+key) ? params.get("SEL_"+key) : "");
			}
		}

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		rfcParam.put("IM_PERNR", user.getEmpNo());
		rfcParam.put("IM_LIST" , paramRow);
		//RFC PARAMETER SETTING END

		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_BE_005_PROCESS", rfcParam, null, request);

		return result;
	}

	@RequestMapping(value = "/taxWithholdingView.do")
	public ModelAndView taxWithholdingView(@RequestParam HashMap<String, Object> params, HttpServletRequest request, HttpServletResponse response) {

		ModelAndView mav = new ModelAndView("/portal/webess/personalMng/taxWithholdingView");

		User user = (User) getRequestAttribute("ikep.user");
		String yearFlg = "0";
		if(!params.containsKey("imYear")){
			params.put("imYear", Integer.parseInt(DateUtil.getToday("yyyy")) - 1);
			yearFlg = "1";
		}

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		rfcParam.put("IM_YEAR" , params.get("imYear"));
		rfcParam.put("IM_PERNR", user.getEmpNo());
		//RFC PARAMETER SETTING END
		Map<?, ?> resultMapCode = webEssRcv.callRfcFunction("ZHR_RFC_PY_007_GET_LISTBOX", rfcParam, null, request);

		List<HashMap<String, String>> tempYearList = (List<HashMap<String, String>>) resultMapCode.get("ET_YEAR");
		String year = "";
		for(HashMap<String, String> tempYear : tempYearList){
			if(year == "" && yearFlg == "1"){
				year = tempYear.values().toString().substring(1, 5);
			
				params.put("imYear", year);
				rfcParam.put("IM_YEAR" , year);
				resultMapCode = webEssRcv.callRfcFunction("ZHR_RFC_PY_007_GET_LISTBOX", rfcParam, null, request);
			}
		}
		
		mav.addObject("yearList", resultMapCode.get("ET_YEAR"));
		mav.addObject("params"  , params);

		return mav;
	}

	@RequestMapping(value = "/taxWithholdingGetPDF.do")
	public void taxWithholdingGetPDF(@RequestParam HashMap<String, Object> params, HttpServletRequest request, HttpServletResponse response) {

		User user = (User) getRequestAttribute("ikep.user");

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		rfcParam.put("IM_YEAR" , params.get("imYear"));
		rfcParam.put("IM_PERNR", user.getEmpNo());
		//RFC PARAMETER SETTING END

		byte[] byteArray = webEssRcv.callRfcFunctionGetPDF("ZHR_RFC_PY_007_ONINIT", rfcParam, null, request);

		String charSet = "UTF-8";
		String fileName = "taxWithholing.pdf";

		BufferedInputStream in = null;

		 try{

			in = new BufferedInputStream(new ByteArrayInputStream(byteArray));

			response.setHeader("Accept-Ranges", "bytes");

			String agent = request.getHeader("User-Agent");

			if(agent.indexOf("MSIE 5.5") > -1 ) {
				response.setHeader("Content-Disposition", "filename=\"" + charSet + "\"" + fileName + "\";");
			}else{
				response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
			}

			response.setHeader("Content-Transfer-Encoding", "binary;");
			response.setHeader("Expired", "-1");
			response.setHeader("Pragma", "nocache");

			FileCopyUtils.copy(in, response.getOutputStream());
			in.close();

		}catch (IOException e){
			log.error("taxWithholdingGetPDF() IOException.");
			log.debug("e.getMessage():"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	@RequestMapping(value = "/taxWithholdingGetSimplePDF.do")
	public void taxWithholdingGetSimplePDF(@RequestParam HashMap<String, Object> params, HttpServletRequest request, HttpServletResponse response) {

		User user = (User) getRequestAttribute("ikep.user");

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		rfcParam.put("IM_YEAR" , params.get("imYear"));
		rfcParam.put("IM_PERNR", user.getEmpNo());
		rfcParam.put("IM_ZECERTI", params.get("IM_CERTI"));
		rfcParam.put("IM_APPID", params.get("IM_APPID"));
		rfcParam.put("IM_BEGDA", params.get("IM_BEGDA"));
		rfcParam.put("IM_WERKS", params.get("IM_WERKS"));
		//RFC PARAMETER SETTING END
  
		byte[] byteArray = webEssRcv.callRfcFunctionGetPDF("ZHR_RFC_BE_002_SELFPRINT", rfcParam, null, request);

		String charSet = "UTF-8";
		String fileName = "taxWithholing.pdf";

		BufferedInputStream in = null;

		 try{

			in = new BufferedInputStream(new ByteArrayInputStream(byteArray));

			response.setHeader("Accept-Ranges", "bytes");

			String agent = request.getHeader("User-Agent");

			if(agent.indexOf("MSIE 5.5") > -1 ) {
				response.setHeader("Content-Disposition", "filename=\"" + charSet + "\"" + fileName + "\";");
			}else{
				response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
			}

			response.setHeader("Content-Transfer-Encoding", "binary;");
			response.setHeader("Expired", "-1");
			response.setHeader("Pragma", "nocache");

			FileCopyUtils.copy(in, response.getOutputStream());
			in.close();

		}catch (IOException e){
			log.error("taxWithholdingGetPDF() IOException.");
			log.debug("e.getMessage():"+e.getMessage());
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/eduExpenseList.do")
	public ModelAndView eduExpenseList(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/webess/personalMng/eduExpenseList");

		User user = (User) getRequestAttribute("ikep.user");

		if(!params.containsKey("imYear")){
			params.put("imYear", DateUtil.getToday("yyyy"));
		}

		if(!params.containsKey("imMonth")){
			params.put("imMonth", DateUtil.getToday("MM"));
		}

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		rfcParam.put("IM_YEAR" , params.get("imYear"));
		rfcParam.put("IM_MONTH", params.get("imMonth"));
		rfcParam.put("IM_PERNR", user.getEmpNo());
		//RFC PARAMETER SETTING END

		Map<?, ?> resultMap = webEssRcv.callRfcFunction("ZHR_RFC_PT_004_GET_DATA", rfcParam, null, request);

		mav.addObject("resultMap", resultMap);
		mav.addObject("params"   , params);
		mav.addObject("keySet"   , Constant.eduExpenseKeySet);

		return mav;
	}

	@RequestMapping(value = "/eduExpenseView.do")
	public ModelAndView eduExpenseView(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/webess/personalMng/eduExpenseView");

		User user = (User) getRequestAttribute("ikep.user");

		String keySet[] = Constant.eduExpenseKeySet;
		HashMap<String, Object> paramRow = new HashMap<String, Object>();

		for(String key : keySet){
			paramRow.put(key, params.containsKey(key) ? params.get(key) : "");
		}

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		rfcParam.put("IM_PERNR", user.getEmpNo());

		rfcParam.put("IM_LIST", paramRow);
		//RFC PARAMETER SETTING END

		Map<?, ?> resultMap = webEssRcv.callRfcFunction("ZHR_RFC_PT_004_GET_DISPLAY", rfcParam, null, request);

		mav.addObject("resultMap", resultMap);
		mav.addObject("keySet"   , keySet);
		mav.addObject("params"   , params);

		return mav;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/personAppointList.do")
	public ModelAndView personAppointList(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/webess/personalMng/personAppointList");

		User user = (User) getRequestAttribute("ikep.user");


		if(!params.containsKey("hidStartDate")){
			params.put("hidStartDate", DateUtil.getPrevDate(DateUtil.getToday("yyyyMMdd"), 7, "yyyy-MM-dd"));
		}

		if(!params.containsKey("hidEndDate")){
			params.put("hidEndDate", DateUtil.getToday("yyyy-MM-dd"));
		}

		if(!params.containsKey("hidAppointType")){
			params.put("hidAppointType", "00");
		}
		
		if(!params.containsKey("hidName")){
			params.put("hidName", "");
		}
		
		if(params.containsKey("clearCache") && "Y".equals(params.get("clearCache"))){
			CacheRefreshUtil.refreshCache("rfcCache");
		}

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		rfcParam.put("IM_PERNR", user.getEmpNo());
		rfcParam.put("IM_MASSN", params.get("hidAppointType"));
		rfcParam.put("IM_ENAME", params.get("hidName"));
		rfcParam.put("IM_BEGDA", params.get("hidStartDate"));
		rfcParam.put("IM_ENDDA", params.get("hidEndDate"));
		//RFC PARAMETER SETTING END

		Map<?, ?> result = rfcCacheService.callRfcFunctionSetCache("ZHR_RFC_ES_001_GET_DATA", rfcParam, null);
						
		ArrayList<Map<?, ?>> tempList = (ArrayList<Map<?, ?>>)result.get("ET_LIST");
		
		ArrayList<Map<?, ?>> etList = PagingUtils.setPagingParam(tempList, params);
		
		mav.addObject("etList", etList);
		mav.addObject("params", params);

		return mav;
	}
}