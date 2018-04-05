package com.lgcns.ikep4.portal.moorimess.web.payMng;

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
@RequestMapping(value="/portal/moorimess/payMng")
public class PayMngController extends BaseController {

	@Autowired
	private WebESSRcvRFC webEssRcv;

	@RequestMapping(value = "/paystubView.do")
	public ModelAndView paystubView(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/webess/payMng/paystubView");

		User user = (User) getRequestAttribute("ikep.user");

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> rfcParamList = new HashMap<String, Object>();

		rfcParam.put("IM_PERNR" , user.getEmpNo());
		rfcParam.put("IM_SEARCH", params.get("imSearch"));
		rfcParam.put("IM_FLAG"  , params.get("imFlag"));

		if(params.containsKey("imSearch")){

			ArrayList<HashMap<String, Object>> searchList = new ArrayList<HashMap<String, Object>>();//IT_LINE
			HashMap<String, Object> searchRow = null;

			int searchCnt = Integer.parseInt((String)params.get("searchCnt"));

			if(searchCnt == 1){
				searchRow = new HashMap<String, Object>();

				searchRow.put("KEY"  , params.containsKey("KEY") ? params.get("KEY") : "");
				searchRow.put("VALUE", params.containsKey("VALUE") ? params.get("VALUE") : "");

				searchList.add(searchRow);
			}else if(searchCnt > 1){
				for(int i=0 ; i<searchCnt ; i++){
					searchRow = new HashMap<String, Object>();

					searchRow.put("KEY"  , request.getParameterValues("KEY")[i]);
					searchRow.put("VALUE", request.getParameterValues("VALUE")[i]);

					searchList.add(searchRow);
				}
			}

			rfcParamList.put("ET_SEARCH", searchList);
		}
		//RFC PARAMETER SETTING END

		Map<?, ?> resultMap = webEssRcv.callRfcFunction("ZHR_RFC_PY_002_ONINIT", rfcParam, rfcParamList, request);

		mav.addObject("resultMap", resultMap);
		mav.addObject("params"   , params);

		return mav;
	}

	@RequestMapping(value = "/payEarningView.do")
	public ModelAndView payEarningView(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/webess/payMng/payEarningView");

		User user = (User) getRequestAttribute("ikep.user");

		if(!params.containsKey("imYear")){
			params.put("imYear", DateUtil.getToday("yyyy"));
		}

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		rfcParam.put("IM_PERNR", user.getEmpNo());
		rfcParam.put("IM_YEAR" , params.get("imYear"));
		//RFC PARAMETER SETTING END

		Map<?, ?> resultMapCode = webEssRcv.callRfcFunction("ZHR_RFC_PY_004_GET_LISTBOX", rfcParam, null, request);

		Map<?, ?> resultMapList = webEssRcv.callRfcFunction("ZHR_RFC_PY_004_GET_DATA", rfcParam, null, request);

		mav.addObject("resultMapList", resultMapList);
		mav.addObject("yearList"     , resultMapCode.get("ET_YEAR"));
		mav.addObject("params"       , params);

		return mav;
	}

	@RequestMapping(value = "/loanList.do")
	public ModelAndView loanList(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/webess/payMng/loanList");

		User user = (User) getRequestAttribute("ikep.user");

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		rfcParam.put("IM_PERNR", user.getEmpNo());
		rfcParam.put("IM_YEAR" , DateUtil.getToday("yyyy"));
		rfcParam.put("IM_MONTH", DateUtil.getToday("MM"));
		//RFC PARAMETER SETTING END

		Map<?, ?> resultMapList = webEssRcv.callRfcFunction("ZHR_RFC_PY_001_GET_DATA", rfcParam, null, request);

		mav.addObject("resultMapList", resultMapList);
		mav.addObject("keySet"       , Constant.loanListKeySet);
		mav.addObject("params"       , params);

		return mav;
	}

	@RequestMapping(value = "/loanView.do")
	public ModelAndView loanView(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/webess/payMng/loanView");

		User user = (User) getRequestAttribute("ikep.user");

		String keySet[] = Constant.loanListKeySet;
		HashMap<String, Object> paramRow = new HashMap<String, Object>();

		for(String key : keySet){
			paramRow.put(key, params.containsKey(key) ? params.get(key) : "");
		}

		if(!params.containsKey("imYear")){
			params.put("imYear", DateUtil.getToday("yyyy"));
		}
		
		if(!params.containsKey("imMonth")){
			params.put("imMonth", "99");
		}

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		rfcParam.put("IM_PERNR", user.getEmpNo());
		rfcParam.put("IM_YEAR" , params.get("imYear"));
		rfcParam.put("IM_MONTH", params.get("imMonth"));
		rfcParam.put("IM_ALL"  , params.get("imAll"));

		rfcParam.put("IM_LIST", paramRow);
		//RFC PARAMETER SETTING END

		Map<?, ?> resultMap = webEssRcv.callRfcFunction("ZHR_RFC_PY_001_GET_DETAIL", rfcParam, null, request);

		Map<?, ?> resultMapCode = webEssRcv.callRfcFunction("ZHR_RFC_PY_001_GET_LISTBOX", rfcParam, null, request);

		mav.addObject("resultMap"    , resultMap);
		mav.addObject("resultMapCode", resultMapCode);
		mav.addObject("keySet"       , keySet);
		mav.addObject("params"       , params);

		return mav;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/yearEndSettlementRegView.do")
	public ModelAndView yearEndSettlementRegView(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/webess/payMng/yearEndSettlementRegView");

		User user = (User) getRequestAttribute("ikep.user");
		
		if(!params.containsKey("tabIndex")){
			params.put("tabIndex", "0");
		}
		
		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		rfcParam.put("IM_PERNR", user.getEmpNo());
		rfcParam.put("IM_YEAR" , DateUtil.getToday("yyyy"));
		rfcParam.put("IM_PASS" , params.get("imPass"));
		//RFC PARAMETER SETTING END

		Map<?, ?> resultMap = webEssRcv.callRfcFunction("ZHR_RFC_PY_003_ONINIT", rfcParam, null, request);
		
		ArrayList<HashMap<String, Object>> etPerFunc = (ArrayList<HashMap<String, Object>>) resultMap.get("ET_PERSON_FUNCTION");
		
		ArrayList<Map<?, ?>> exLayoutList = new ArrayList<Map<?, ?>>();
		ArrayList<Map<?, ?>> exPerFuncList = new ArrayList<Map<?, ?>>();
		
		HashMap<String, Object> layoutParam = new HashMap<String, Object>();
		
		layoutParam.put("IM_YEAR"    , resultMap.get("EX_YEAR"));
		layoutParam.put("IM_PERNR"   , user.getEmpNo());
		layoutParam.put("IM_EVENT_ID", "");
		layoutParam.put("IM_BEGDA"   , resultMap.get("EX_BEGDA"));
		layoutParam.put("IM_ENDDA"   , resultMap.get("EX_ENDDA"));
		layoutParam.put("IM_FIRST"   , "X");
		
		Map<?, ?> layoutResult;
		
		for(int i = 0 ; i < etPerFunc.size() ; i++){
			layoutParam.put("IM_PERSON_FUNCTION", etPerFunc.get(i));
			layoutResult = (Map<?, ?>) webEssRcv.callRfcFunction("ZHR_RFC_PY_003_FILL_PERSON", layoutParam, null, request);
			
			exLayoutList.add((Map<?, ?>) layoutResult.get("EX_LAYOUT"));
			exPerFuncList.add((Map<?, ?>) layoutResult.get("EX_PERSON_FUNCTION"));
		}
		
		mav.addObject("exLayoutList" , exLayoutList);
		mav.addObject("exPerFuncList", exPerFuncList);
		
		mav.addObject("perFuncKeySet"  , Constant.perFuncKeySet);
		mav.addObject("addressKeySet"  , Constant.addressListKeySet);
		mav.addObject("taxKeySet"      , Constant.taxKeySet);
		mav.addObject("last2KeySet"    , Constant.last2KeySet);
		mav.addObject("repayKeySet"    , Constant.repayKeySet);
		mav.addObject("creditKeySet"   , Constant.creditKeySet);
		mav.addObject("annuityKeySet"  , Constant.annuityKeySet);
		mav.addObject("houseKeySet"    , Constant.houseKeySet);
		mav.addObject("paymentKeySet"  , Constant.paymentKeySet);
		mav.addObject("premiumKeySet"  , Constant.premiumKeySet);
		mav.addObject("medicalKeySet"  , Constant.medicalKeySet);
		mav.addObject("educationKeySet", Constant.educationKeySet);
		mav.addObject("donationKeySet" , Constant.donationKeySet);
		mav.addObject("pensionKeySet"  , Constant.pensionKeySet);
		mav.addObject("rentKeySet"     , Constant.rentKeySet);
		mav.addObject("resultMap", resultMap);
		mav.addObject("params"   , params);

		return mav;
	}
	
	@RequestMapping(value = "callPY003FillPerson.do")
	public @ResponseBody Map<?, ?> callPY003FillPerson(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");
		
		String[] perFuncKeySet = Constant.perFuncKeySet;

		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		
		rfcParam.put("IM_YEAR"    , params.get("EX_YEAR"));
		rfcParam.put("IM_PERNR"   , user.getEmpNo());
		rfcParam.put("IM_EVENT_ID", params.get("imEventId"));
		rfcParam.put("IM_BEGDA"   , params.get("EX_BEGDA"));
		rfcParam.put("IM_ENDDA"   , params.get("EX_ENDDA"));
		
		HashMap<String, Object> paramRow = new HashMap<String, Object>();
		
		for(String key : perFuncKeySet){
			if("DPTID".equals(key)){
				paramRow.put(key, params.get("basicDeduction"));
			}else if("ZSEVENTY".equals(key)){
				paramRow.put(key, params.get("seniors"));
			}else if("ZHNDEE".equals(key)){
				paramRow.put(key, params.get("disabled"));
			}else if("HNDCD".equals(key)){
				paramRow.put(key, params.get("disabledCode"));
			}else if("SIGPR".equals(key)){
				paramRow.put(key, params.get("singleParent"));
			}else if("WOMEE".equals(key)){
				paramRow.put(key, params.get("woman"));
			}else if("ZINSID".equals(key)){
				paramRow.put(key, params.get("premium"));
			}else if("ZMEDID".equals(key)){
				paramRow.put(key, params.get("medical"));
			}else if("ZEDUID".equals(key)){
				paramRow.put(key, params.get("education"));
			}else if("ZCREID".equals(key)){
				paramRow.put(key, params.get("creditCard"));
			}else if("ZCONID".equals(key)){
				paramRow.put(key, params.get("donation"));
			}else{
				paramRow.put(key, params.get(key));
			}
		}
		
		rfcParam.put("IM_PERSON_FUNCTION", paramRow);

		return webEssRcv.callRfcFunction("ZHR_RFC_PY_003_FILL_PERSON", rfcParam, null, request);
	}
	
	@RequestMapping(value = "/addressRegViewPop.do")
	public ModelAndView addressRegViewPop(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/webess/payMng/addressRegViewPop");
		
		int etZstateCnt = Integer.parseInt((String)params.get("etZstateCnt"));
		ArrayList<HashMap<String, Object>> etZstateList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> etZstateRow;
		
		if(etZstateCnt == 1){
			etZstateRow = new HashMap<String, Object>();
			
			etZstateRow.put("KEY", params.get("etZstateKey"));
			etZstateRow.put("VALUE", params.get("etZstateValue"));
			
			etZstateList.add(etZstateRow);
		}else if(etZstateCnt > 1){
			for(int i = 0 ; i < etZstateCnt ; i++){
				etZstateRow = new HashMap<String, Object>();
				
				etZstateRow.put("KEY"  , request.getParameterValues("etZstateKey")[i]);
				etZstateRow.put("VALUE", request.getParameterValues("etZstateValue")[i]);
				
				etZstateList.add(etZstateRow);
			}
		}
		
		int etZtypeCnt = Integer.parseInt((String)params.get("etZtypeCnt"));
		ArrayList<HashMap<String, Object>> etZtypeList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> etZtypeRow;
		
		if(etZtypeCnt == 1){
			etZtypeRow = new HashMap<String, Object>();
			
			etZtypeRow.put("KEY", params.get("etZtypeKey"));
			etZtypeRow.put("VALUE", params.get("etZtypeValue"));
			
			etZtypeList.add(etZtypeRow);
		}else if(etZtypeCnt > 1){
			for(int i = 0 ; i < etZtypeCnt ; i++){
				etZtypeRow = new HashMap<String, Object>();
				
				etZtypeRow.put("KEY"  , request.getParameterValues("etZtypeKey")[i]);
				etZtypeRow.put("VALUE", request.getParameterValues("etZtypeValue")[i]);
				
				etZtypeList.add(etZtypeRow);
			}
		}
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		
		resultMap.put("ET_ZSTATE", etZstateList);
		resultMap.put("ET_ZTYPE" , etZtypeList);
		
		mav.addObject("addressKeySet", Constant.addressListKeySet);
		mav.addObject("resultMap"    , resultMap);
		mav.addObject("params"       , params);

		return mav;
	}
	
	@RequestMapping(value = "callPY003OninputPop14.do")
	public @ResponseBody Map<?, ?> callPY003OninputPop14(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");

		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		
		String[] addressKeySet = Constant.addressListKeySet;
		
		rfcParam.put("IM_PERNR"   , user.getEmpNo());
		rfcParam.put("IM_ZYEAR"   , params.get("imZyear"));
		rfcParam.put("IM_EVENT_ID", "SAVE");
		
		HashMap<String, Object> paramRow = new HashMap<String, Object>();
		
		for(String key : addressKeySet){
			if("SUBTY".equals(key)){
				paramRow.put(key, params.get("type"));
			}else if("STATE".equals(key)){
				paramRow.put(key, params.get("state"));
			}else if("PSTLZ".equals(key)){
				paramRow.put(key, params.get("zipCode"));
			}else if("ORT01".equals(key)){
				paramRow.put(key, params.get("stateText"));
			}else if("ORT02".equals(key)){
				paramRow.put(key, params.get("town"));
			}else if("LOCAT".equals(key)){
				paramRow.put(key, params.get("addAddress"));
			}else if("STRAS".equals(key)){
				paramRow.put(key, params.get("detailAddress"));
			}else if("TELNB".equals(key)){
				paramRow.put(key, params.get("telNumber"));
			}else{
				paramRow.put(key, params.get(key));
			}
		}
		
		rfcParam.put("IM_ADDRESS", paramRow);

		return webEssRcv.callRfcFunction("ZHR_RFC_PY_003_ONINPUT_POP14", rfcParam, null, request);
	}
	
	@RequestMapping(value = "/familyAddPop.do")
	public ModelAndView familyAddPop(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/webess/payMng/familyAddPop");
		
		int etFamCnt = Integer.parseInt((String)params.get("etFamCnt"));
		ArrayList<HashMap<String, Object>> etFamList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> etFamRow;
		
		if(etFamCnt == 1){
			etFamRow = new HashMap<String, Object>();
			
			etFamRow.put("KEY", params.get("etFamKey"));
			etFamRow.put("VALUE", params.get("etFamValue"));
			
			etFamList.add(etFamRow);
		}else if(etFamCnt > 1){
			for(int i = 0 ; i < etFamCnt ; i++){
				etFamRow = new HashMap<String, Object>();
				
				etFamRow.put("KEY"  , request.getParameterValues("etFamKey")[i]);
				etFamRow.put("VALUE", request.getParameterValues("etFamValue")[i]);
				
				etFamList.add(etFamRow);
			}
		}
		int etChiCnt = Integer.parseInt((String)params.get("etChiCnt"));
		ArrayList<HashMap<String, Object>> etChiList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> etChiRow;
		
		if(etChiCnt == 1){
			etChiRow = new HashMap<String, Object>();
			
			etChiRow.put("KEY", params.get("etChiKey"));
			etChiRow.put("VALUE", params.get("etChiValue"));
			
			etChiList.add(etChiRow);
		}else if(etChiCnt > 1){
			for(int i = 0 ; i < etChiCnt ; i++){
				etChiRow = new HashMap<String, Object>();
				
				etChiRow.put("KEY"  , request.getParameterValues("etChiKey")[i]);
				etChiRow.put("VALUE", request.getParameterValues("etChiValue")[i]);
				
				etChiList.add(etChiRow);
			}
		}
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		
		resultMap.put("ET_FAM", etFamList);
		resultMap.put("ET_CHI", etChiList);
		
		mav.addObject("resultMap", resultMap);
		mav.addObject("params"   , params);

		return mav;
	}
	
	@RequestMapping(value = "callPY003OninputPop12.do")
	public @ResponseBody Map<?, ?> callPY003OninputPop12(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");

		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		
		rfcParam.put("IM_PERNR"   , user.getEmpNo());
		rfcParam.put("IM_ZYEAR"   , params.get("imZyear"));
		rfcParam.put("IM_EVENT_ID", "SAVE");
		
		HashMap<String, Object> paramRow = new HashMap<String, Object>();
		
		paramRow.put("ZYEAR" , params.get("imZyear"));
		paramRow.put("PERNR" , user.getEmpNo());
		paramRow.put("FNMHG" , params.get("firstName"));
		paramRow.put("LNMHG" , params.get("lastName"));
		paramRow.put("REGNO1", params.get("regNo1"));
		paramRow.put("REGNO2", params.get("regNo2"));
		paramRow.put("DPTYP" , params.get("relations"));
		paramRow.put("KDBSL" , params.get("childCnt"));
		
		rfcParam.put("IM_FAM", paramRow);

		return webEssRcv.callRfcFunction("ZHR_RFC_PY_003_ONINPUT_POP12", rfcParam, null, request);
	}
	
	@RequestMapping(value = "/familyDelPop.do")
	public ModelAndView familyDelPop(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/webess/payMng/familyDelPop");
		
		User user = (User) getRequestAttribute("ikep.user");
		
		String[] perFuncKeySet = Constant.perFuncKeySet;
		
		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		rfcParam.put("IM_PERNR", user.getEmpNo());
		rfcParam.put("IM_YEAR", params.get("EX_YEAR"));
		//RFC PARAMETER SETTING END
		
		HashMap<String, Object> paramRow = new HashMap<String, Object>();
		
		for(String key : perFuncKeySet){
			paramRow.put(key, params.get(key));
		}
		
		rfcParam.put("IM_PERSON_FUNCTION", paramRow);
		
		Map<?, ?> resultMap = webEssRcv.callRfcFunction("ZHR_RFC_PY_003_ONINIT_POP13", rfcParam, null, request);
		
		int etFamCnt = Integer.parseInt((String)params.get("etFamCnt"));
		ArrayList<HashMap<String, Object>> etFamList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> etFamRow;
		
		if(etFamCnt == 1){
			etFamRow = new HashMap<String, Object>();
			
			etFamRow.put("KEY"  , params.get("etFamKey"));
			etFamRow.put("VALUE", params.get("etFamValue"));
			
			etFamList.add(etFamRow);
		}else if(etFamCnt > 1){
			for(int i = 0 ; i < etFamCnt ; i++){
				etFamRow = new HashMap<String, Object>();
				
				etFamRow.put("KEY"  , request.getParameterValues("etFamKey")[i]);
				etFamRow.put("VALUE", request.getParameterValues("etFamValue")[i]);
				
				etFamList.add(etFamRow);
			}
		}
		
		int etEnddaCnt = Integer.parseInt((String)params.get("etEnddaCnt"));
		ArrayList<HashMap<String, Object>> etEnddaList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> etEnddaRow;
		
		if(etEnddaCnt == 1){
			etEnddaRow = new HashMap<String, Object>();
			
			etEnddaRow.put("KEY"  , params.get("etEnddaKey"));
			etEnddaRow.put("VALUE", params.get("etEnddaValue"));
			
			etEnddaList.add(etEnddaRow);
		}else if(etEnddaCnt > 1){
			for(int i = 0 ; i < etEnddaCnt ; i++){
				etEnddaRow = new HashMap<String, Object>();
				
				etEnddaRow.put("KEY"  , request.getParameterValues("etEnddaKey")[i]);
				etEnddaRow.put("VALUE", request.getParameterValues("etEnddaValue")[i]);
				
				etEnddaList.add(etEnddaRow);
			}
		}
		
		mav.addObject("resultMap"    , resultMap);
		mav.addObject("ET_FAM"       , etFamList);
		mav.addObject("ET_ENDDA"     , etEnddaList);
		mav.addObject("perFuncKeySet", Constant.perFuncKeySet);
		mav.addObject("params"       , params);

		return mav;
	}
	
	@RequestMapping(value = "callPY003OninputPop13.do")
	public @ResponseBody Map<?, ?> callPY003OninputPop13(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");

		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		
		rfcParam.put("IM_PERNR"   , user.getEmpNo());
		rfcParam.put("IM_ZYEAR"   , params.get("imZyear"));
		rfcParam.put("IM_EVENT_ID", "FAM_DELETE");
		
		HashMap<String, Object> paramRow = new HashMap<String, Object>();
		
		paramRow.put("MANDT" , params.get("MANDT"));
		paramRow.put("ZYEAR" , params.get("ZYEAR"));
		paramRow.put("PERNR" , params.get("PERNR"));
		paramRow.put("BEGDA" , params.get("BEGDA"));
		paramRow.put("FNMHG" , params.get("firstName"));
		paramRow.put("LNMHG" , params.get("lastName"));
		paramRow.put("REGNO1", params.get("regNo1"));
		paramRow.put("REGNO2", params.get("regNo2"));
		paramRow.put("DPTYP" , params.get("relations"));
		paramRow.put("ENDDA" , params.get("endDate"));
		
		rfcParam.put("IM_FAM", paramRow);
		
		HashMap<String, Object> paramRow2 = new HashMap<String, Object>();
		
		for(String key : Constant.perFuncKeySet){
			paramRow2.put(key, params.get("perFunc_"+key));
		}
		
		rfcParam.put("IM_PERSON_FUNCTION", paramRow2);

		return webEssRcv.callRfcFunction("ZHR_RFC_PY_003_ONINPUT_POP13", rfcParam, null, request);
	}
	
	@RequestMapping(value = "/workPlacePop.do")
	public ModelAndView workPlacePop(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/webess/payMng/workPlacePop");
		
		int etLastCnt = Integer.parseInt((String)params.get("etLastCnt"));
		ArrayList<HashMap<String, Object>> etLastList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> etLastRow;
		
		if(etLastCnt == 1){
			etLastRow = new HashMap<String, Object>();
			
			etLastRow.put("KEY", params.get("etLastKey"));
			etLastRow.put("VALUE", params.get("etLastValue"));
			
			etLastList.add(etLastRow);
		}else if(etLastCnt > 1){
			for(int i = 0 ; i < etLastCnt ; i++){
				etLastRow = new HashMap<String, Object>();
				
				etLastRow.put("KEY"  , request.getParameterValues("etLastKey")[i]);
				etLastRow.put("VALUE", request.getParameterValues("etLastValue")[i]);
				
				etLastList.add(etLastRow);
			}
		}
		
		int etLast2Cnt = Integer.parseInt((String)params.get("etLast2Cnt"));
		ArrayList<HashMap<String, Object>> etLast2List = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> etLast2Row;

		String[] last2KeySet = Constant.last2KeySet;
		
		if(etLast2Cnt == 1){
			etLast2Row = new HashMap<String, Object>();
			
			for(String key : last2KeySet){
				etLast2Row.put(key, params.get("etLast2_"+key));
			}
			
			etLast2List.add(etLast2Row);
		}else if(etLast2Cnt > 1){
			for(int i = 0 ; i < etLast2Cnt ; i++){
				etLast2Row = new HashMap<String, Object>();
				for(String key : last2KeySet){
					etLast2Row.put(key, request.getParameterValues("etLast2_"+key)[i]);
				}
				etLast2List.add(etLast2Row);
			}
		}
		
		HashMap<String, Object> taxRow = new HashMap<String, Object>();
		
		String[] taxKeySet = Constant.taxKeySet;
		
		for(String key : taxKeySet){
			taxRow.put(key, params.get("exTax_"+key));
		}
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		
		resultMap.put("ET_LAST" , etLastList);
		resultMap.put("ET_LAST2", etLast2List);
		resultMap.put("EX_TAX"  , taxRow);
		
		mav.addObject("resultMap"  , resultMap);
		mav.addObject("last2KeySet", last2KeySet);
		mav.addObject("taxKeySet"  , taxKeySet);
		mav.addObject("params"     , params);

		return mav;
	}
	
	@RequestMapping(value = "callPY003OninputPop1.do")
	public @ResponseBody Map<?, ?> callPY003OninputPop1(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");

		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> rfcParamList = new HashMap<String, Object>();
		
		rfcParam.put("IM_PERNR"   , user.getEmpNo());
		rfcParam.put("IM_EVENT_ID", params.get("imEventId"));
		
		int paramCnt = Integer.parseInt((String)params.get("paramCnt"));
		ArrayList<HashMap<String, Object>> itLastList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> itLastRow;
		
		if(paramCnt == 1){
			itLastRow = new HashMap<String, Object>();
			
			itLastRow.put("PERNR", user.getEmpNo());
			itLastRow.put("BIZNO", params.get("bizno"));
			itLastRow.put("COMNM", params.get("comnm"));
			itLastRow.put("PABEG", params.get("pabeg"));
			itLastRow.put("PAEND", params.get("paend"));
			itLastRow.put("SALM" , params.get("salm"));
			itLastRow.put("BONM" , params.get("bonm"));
			itLastRow.put("INTM" , params.get("intm"));
			itLastRow.put("RETM" , params.get("retm"));
			itLastRow.put("OUTM" , params.get("outm"));
			itLastRow.put("OVRM" , params.get("ovrm"));
			itLastRow.put("OTHM" , params.get("othm"));
			itLastRow.put("MEDM" , params.get("medm"));
			itLastRow.put("NPMM" , params.get("npmm"));
			itLastRow.put("EIMM" , params.get("eimm"));
			itLastRow.put("FLDM" , params.get("fldm"));
			itLastRow.put("FRIM" , params.get("frim"));
			itLastRow.put("FREM" , params.get("frem"));
			itLastRow.put("ABNM" , params.get("abnm"));
			itLastRow.put("SPTM" , params.get("sptm"));
			itLastRow.put("EXBEG", params.get("exbeg"));
			itLastRow.put("EXEND", params.get("exend"));
			
			itLastList.add(itLastRow);
		}else if(paramCnt > 1){
			for(int i = 0 ; i < paramCnt ; i++){
				itLastRow = new HashMap<String, Object>();
				
				itLastRow.put("PERNR", user.getEmpNo());
				itLastRow.put("BIZNO", request.getParameterValues("bizno")[i]);
				itLastRow.put("COMNM", request.getParameterValues("comnm")[i]);
				itLastRow.put("PABEG", request.getParameterValues("pabeg")[i]);
				itLastRow.put("PAEND", request.getParameterValues("paend")[i]);
				itLastRow.put("SALM" , request.getParameterValues("salm")[i]);
				itLastRow.put("BONM" , request.getParameterValues("bonm")[i]);
				itLastRow.put("INTM" , request.getParameterValues("intm")[i]);
				itLastRow.put("RETM" , request.getParameterValues("retm")[i]);
				itLastRow.put("OUTM" , request.getParameterValues("outm")[i]);
				itLastRow.put("OVRM" , request.getParameterValues("ovrm")[i]);
				itLastRow.put("OTHM" , request.getParameterValues("othm")[i]);
				itLastRow.put("MEDM" , request.getParameterValues("medm")[i]);
				itLastRow.put("NPMM" , request.getParameterValues("npmm")[i]);
				itLastRow.put("EIMM" , request.getParameterValues("eimm")[i]);
				itLastRow.put("FLDM" , request.getParameterValues("fldm")[i]);
				itLastRow.put("FRIM" , request.getParameterValues("frim")[i]);
				itLastRow.put("FREM" , request.getParameterValues("frem")[i]);
				itLastRow.put("ABNM" , request.getParameterValues("abnm")[i]);
				itLastRow.put("SPTM" , request.getParameterValues("sptm")[i]);
				itLastRow.put("EXBEG", request.getParameterValues("exbeg")[i]);
				itLastRow.put("EXEND", request.getParameterValues("exend")[i]);
				
				itLastList.add(itLastRow);
			}
		}
		
		rfcParamList.put("IT_LAST", itLastList);
		
		HashMap<String, Object> paramRow = new HashMap<String, Object>();
		
		for(String key : Constant.taxKeySet){
			paramRow.put(key, params.get("exTax_"+key));
		}
		
		rfcParam.put("IM_TAX", paramRow);
		
		if(params.containsKey("itDelLineCnt")){
			int itDelLineCnt = Integer.parseInt((String)params.get("itDelLineCnt"));
			ArrayList<HashMap<String, Object>> itDelLineList = new ArrayList<HashMap<String, Object>>();
			HashMap<String, Object> itDelLineRow;
			
			if(itDelLineCnt == 1){
				itDelLineRow = new HashMap<String, Object>();
				itDelLineRow.put("SEQ", params.get("itDelLine_SEQ"));
				itDelLineList.add(itDelLineRow);
			}else{
				for(int i = 0 ; i < itDelLineCnt ; i++){
					itDelLineRow = new HashMap<String, Object>();
					itDelLineRow.put("SEQ", request.getParameterValues("itDelLine_SEQ")[i]);
					itDelLineList.add(itDelLineRow);
				}
			}
			
			rfcParamList.put("IT_DEL_LINE", itDelLineList);
		}

		return webEssRcv.callRfcFunction("ZHR_RFC_PY_003_ONINPUT_POP1", rfcParam, rfcParamList, request);
	}
	
	@RequestMapping(value = "/interestPop.do")
	public ModelAndView interestPop(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/webess/payMng/interestPop");
		
		int etRepayCnt = Integer.parseInt((String)params.get("etRepayCnt"));
		ArrayList<HashMap<String, Object>> etRepayList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> etRepayRow;

		String[] repayKeySet = Constant.repayKeySet;
		
		if(etRepayCnt == 1){
			etRepayRow = new HashMap<String, Object>();
			
			for(String key : repayKeySet){
				etRepayRow.put(key, params.get("etRepay_"+key));
			}
			
			etRepayList.add(etRepayRow);
		}else if(etRepayCnt > 1){
			for(int i = 0 ; i < etRepayCnt ; i++){
				etRepayRow = new HashMap<String, Object>();
				for(String key : repayKeySet){
					etRepayRow.put(key, request.getParameterValues("etRepay_"+key)[i]);
				}
				etRepayList.add(etRepayRow);
			}
		}
		
		HashMap<String, Object> taxRow = new HashMap<String, Object>();
		
		String[] taxKeySet = Constant.taxKeySet;
		
		for(String key : taxKeySet){
			taxRow.put(key, params.get("exTax_"+key));
		}
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		
		resultMap.put("ET_REPAY", etRepayList);
		resultMap.put("EX_TAX"  , taxRow);
		
		mav.addObject("resultMap"  , resultMap);
		mav.addObject("repayKeySet", repayKeySet);
		mav.addObject("taxKeySet"  , taxKeySet);
		mav.addObject("params"   , params);

		return mav;
	}
	
	@RequestMapping(value = "callPY003OninputPop17.do")
	public @ResponseBody Map<?, ?> callPY003OninputPop17(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");

		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> rfcParamList = new HashMap<String, Object>();
		
		rfcParam.put("IM_PERNR"   , user.getEmpNo());
		rfcParam.put("IM_EVENT_ID", params.get("imEventId"));
		rfcParam.put("IM_ZYEAR"   , params.get("imZyear"));
		rfcParam.put("IM_REGNO"   , params.get("imRegno"));
		
		int paramCnt = Integer.parseInt((String)params.get("paramCnt"));
		ArrayList<HashMap<String, Object>> itRepayList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> itRepayRow;
		
		if(paramCnt == 1){
			itRepayRow = new HashMap<String, Object>();
			
			itRepayRow.put("ZYEAR"   , params.get("imZyear"));
			itRepayRow.put("PERNR"   , user.getEmpNo());
			itRepayRow.put("ETNAM"   , params.get("etnam"));
			itRepayRow.put("ET_RCBEG", params.get("etRcbeg"));
			itRepayRow.put("ET_RCEND", params.get("etRcend"));
			itRepayRow.put("ET_LNPRD", params.get("etLnprd"));
			itRepayRow.put("ET_FIXRT", params.get("etFixrt"));
			itRepayRow.put("ET_NODEF", params.get("etNodef"));
			
			itRepayList.add(itRepayRow);
		}else if(paramCnt > 1){
			for(int i = 0 ; i < paramCnt ; i++){
				itRepayRow = new HashMap<String, Object>();
				
				itRepayRow.put("ZYEAR"   , params.get("imZyear"));
				itRepayRow.put("PERNR"   , user.getEmpNo());
				itRepayRow.put("ETNAM"   , request.getParameterValues("etnam")[i]);
				itRepayRow.put("ET_RCBEG", request.getParameterValues("etRcbeg")[i]);
				itRepayRow.put("ET_RCEND", request.getParameterValues("etRcend")[i]);
				itRepayRow.put("ET_LNPRD", request.getParameterValues("etLnprd")[i]);
				itRepayRow.put("ET_FIXRT", request.getParameterValues("etFixrt")[i]);
				itRepayRow.put("ET_NODEF", request.getParameterValues("etNodef")[i]);
				
				itRepayList.add(itRepayRow);
			}
		}
		
		rfcParamList.put("IT_REPAY", itRepayList);
		
		HashMap<String, Object> paramRow = new HashMap<String, Object>();
		
		for(String key : Constant.taxKeySet){
			paramRow.put(key, params.get("exTax_"+key));
		}
		
		rfcParam.put("IM_TAX", paramRow);
		
		if(params.containsKey("itDelLineCnt")){
			int itDelLineCnt = Integer.parseInt((String)params.get("itDelLineCnt"));
			ArrayList<HashMap<String, Object>> itDelLineList = new ArrayList<HashMap<String, Object>>();
			HashMap<String, Object> itDelLineRow;
			
			if(itDelLineCnt == 1){
				itDelLineRow = new HashMap<String, Object>();
				itDelLineRow.put("SEQ", params.get("itDelLine_SEQ"));
				itDelLineList.add(itDelLineRow);
			}else{
				for(int i = 0 ; i < itDelLineCnt ; i++){
					itDelLineRow = new HashMap<String, Object>();
					itDelLineRow.put("SEQ", request.getParameterValues("itDelLine_SEQ")[i]);
					itDelLineList.add(itDelLineRow);
				}
			}
			
			rfcParamList.put("IT_DEL_LINE", itDelLineList);
		}

		return webEssRcv.callRfcFunction("ZHR_RFC_PY_003_ONINPUT_POP17", rfcParam, rfcParamList, request);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/creditPop.do")
	public ModelAndView creditPop(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/webess/payMng/creditPop");
		
		User user = (User) getRequestAttribute("ikep.user");
		
		int etCredCnt = Integer.parseInt((String)params.get("etCredCnt"));
		ArrayList<HashMap<String, Object>> etCredList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> etCredRow;

		String[] creditKeySet = Constant.creditKeySet;
		
		if(etCredCnt == 1){
			etCredRow = new HashMap<String, Object>();
			
			for(String key : creditKeySet){
				etCredRow.put(key, params.get("etCred_"+key));
			}
			
			etCredList.add(etCredRow);
		}else if(etCredCnt > 1){
			for(int i = 0 ; i < etCredCnt ; i++){
				etCredRow = new HashMap<String, Object>();
				for(String key : creditKeySet){
					etCredRow.put(key, request.getParameterValues("etCred_"+key)[i]);
				}
				etCredList.add(etCredRow);
			}
		}
		
		ArrayList<Map<?, ?>> exLayoutList = new ArrayList<Map<?, ?>>();
		ArrayList<Map<?, ?>> exCredList = new ArrayList<Map<?, ?>>();
		
		HashMap<String, Object> layoutParam = new HashMap<String, Object>();
		Map<?, ?> layoutResult;
		
		layoutParam.put("IM_YEAR"    , params.get("EX_YEAR"));
		layoutParam.put("IM_PERNR"   , user.getEmpNo());
		layoutParam.put("IM_EVENT_ID", "");
		layoutParam.put("IM_FIRST"   , "X");
		
		for(int i = 0 ; i < etCredList.size() ; i++){
			layoutParam.put("IM_CRED", etCredList.get(i));
			
			layoutResult = webEssRcv.callRfcFunction("ZHR_RFC_PY_003_FILL_POP6", layoutParam, null, request);
			
			exLayoutList.add((Map<?, ?>)layoutResult.get("EX_LAYOUT"));
			exCredList.add((Map<?, ?>)layoutResult.get("EX_CRED"));
		}
		
		
		HashMap<String, Object> taxRow = new HashMap<String, Object>();
		
		String[] taxKeySet = Constant.taxKeySet;
		
		for(String key : taxKeySet){
			taxRow.put(key, params.get("exTax_"+key));
		}
		
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		
		rfcParam.put("IM_PERNR", user.getEmpNo());
		rfcParam.put("IM_ZYEAR", params.get("EX_YEAR"));
		
		Map<? ,?> resultCode = webEssRcv.callRfcFunction("ZHR_RFC_PY_003_GET_LISTBOX6", rfcParam, null, request);
				
		ArrayList<HashMap<String, Object>> etKdsvh = (ArrayList<HashMap<String, Object>>)resultCode.get("ET_KDSVH");
		
		HashMap<String, Object> tempParam = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> tempList;
		HashMap<String, Object> tempRow;
		
		ArrayList<HashMap<String, Object>> etFamList = new ArrayList<HashMap<String, Object>>();
		
		for(int i = 0 ; i < etKdsvh.size() ; i++){
			tempParam.put("IM_PERNR", user.getEmpNo());
			tempParam.put("IM_ZYEAR", params.get("EX_YEAR"));
			tempParam.put("IM_SUBTY", etKdsvh.get(i).get("KEY"));
			
			tempList = (ArrayList<HashMap<String, Object>>)webEssRcv.callRfcFunction("ZHR_RFC_PY_003_GET_FAMILY_CRED", tempParam, null, request).get("ET_FAM_LIST");
			
			for(int j = 0 ; j < tempList.size() ; j++){
				tempRow = new HashMap<String, Object>();
				tempRow.put("KEY"  , tempList.get(j).get("KEY"));
				tempRow.put("VALUE", tempList.get(j).get("VALUE"));
				tempRow.put("SUBTY", etKdsvh.get(i).get("KEY"));
				
				etFamList.add(tempRow);
			}
		}
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		
		resultMap.put("ET_CRED"       , etCredList);
		resultMap.put("EX_TAX"        , taxRow);
		resultMap.put("ET_GUBUN"      , resultCode.get("ET_GUBUN"));
		resultMap.put("ET_EXPRD"      , resultCode.get("ET_EXPRD"));
		resultMap.put("ET_KDSVH"      , resultCode.get("ET_KDSVH"));
		resultMap.put("ET_FAM_LIST"   , etFamList);
		resultMap.put("EX_LAYOUT_LIST", exLayoutList);
		resultMap.put("EX_CRED_LIST"  , exCredList);
		
		mav.addObject("resultMap"   , resultMap);
		mav.addObject("creditKeySet", creditKeySet);
		mav.addObject("taxKeySet"   , taxKeySet);
		mav.addObject("params"      , params);

		return mav;
	}
	
	@RequestMapping(value = "callPY003FillPop6.do")
	public @ResponseBody Map<?, ?> callPY003FillPop6(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");
		
		String[] creditKeySet = Constant.creditKeySet;

		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		
		rfcParam.put("IM_YEAR"    , params.get("imYear"));
		rfcParam.put("IM_PERNR"   , user.getEmpNo());
		rfcParam.put("IM_EVENT_ID", params.get("imEventId"));
		
		HashMap<String, Object> paramRow = new HashMap<String, Object>();
		
		for(String key : creditKeySet){
			if("KDSVH".equals(key) || "SUBTY".equals(key)){
				paramRow.put(key, params.get("kdsvh"));
			}else if("DNAME".equals(key) || "REGNO".equals(key)){
				paramRow.put(key, params.get("dname"));
			}else if("REGNOT".equals(key)){
				paramRow.put(key, params.get("regnot"));
			}else if("GUBUN".equals(key)){
				paramRow.put(key, params.get("gubun"));
			}else if("OTNAM".equals(key)){
				paramRow.put(key, params.get("otnam"));
			}else if("OTOAM".equals(key)){
				paramRow.put(key, params.get("otoam"));
			}else if("TRDMK".equals(key)){
				paramRow.put(key, params.get("trdmk"));
			}else if("CCTRA".equals(key)){
				paramRow.put(key, params.get("cctra"));
			}else if("EXPRD".equals(key)){
				paramRow.put(key, params.get("exprd"));
			}else{
				paramRow.put(key, params.get(key));
			}
		}
		
		rfcParam.put("IM_CRED", paramRow);

		return webEssRcv.callRfcFunction("ZHR_RFC_PY_003_FILL_POP6", rfcParam, null, request);
	}
	
	@RequestMapping(value = "callPY003OninputPop6.do")
	public @ResponseBody Map<?, ?> callPY003OninputPop6(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");

		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> rfcParamList = new HashMap<String, Object>();
		
		String creditKeySet[] = Constant.creditKeySet;
		
		rfcParam.put("IM_PERNR"   , user.getEmpNo());
		rfcParam.put("IM_EVENT_ID", params.get("imEventId"));
		rfcParam.put("IM_ZYEAR"   , params.get("imZyear"));
		rfcParam.put("IM_REGNO"   , params.get("imRegno"));
		
		int paramCnt = Integer.parseInt((String)params.get("paramCnt"));
		ArrayList<HashMap<String, Object>> itCredList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> itCredRow;
		
		if(paramCnt == 1){
			itCredRow = new HashMap<String, Object>();
			
			for(String key : creditKeySet){
				if("KDSVH".equals(key) || "SUBTY".equals(key)){
					itCredRow.put(key, params.get("kdsvh"));
				}else if("DNAME".equals(key) || "REGNO".equals(key)){
					itCredRow.put(key, params.get("dname"));
				}else if("REGNOT".equals(key)){
					itCredRow.put(key, params.get("regnot"));
				}else if("GUBUN".equals(key)){
					itCredRow.put(key, params.get("gubun"));
				//}else if("EXPRD".equals(key)){
				//	itCredRow.put(key, params.get("exprd"));
				}else if("OTNAM".equals(key)){
					itCredRow.put(key, params.get("otnam"));
				}else if("OTOAM".equals(key)){
					itCredRow.put(key, params.get("otoam"));
				}else if("TRDMK".equals(key)){
					itCredRow.put(key, params.get("trdmk"));
				}else if("CCTRA".equals(key)){
					itCredRow.put(key, params.get("cctra"));
				}else{
					itCredRow.put(key, params.get(key));
				}
			}
			itCredList.add(itCredRow);
		}else if(paramCnt > 1){
			for(int i = 0 ; i < paramCnt ; i++){
				itCredRow = new HashMap<String, Object>();
				
				for(String key : creditKeySet){
					if("KDSVH".equals(key) || "SUBTY".equals(key)){
						itCredRow.put(key, request.getParameterValues("kdsvh")[i]);
					}else if("DNAME".equals(key) || "REGNO".equals(key)){
						itCredRow.put(key, request.getParameterValues("dname")[i]);
					}else if("REGNOT".equals(key)){
						itCredRow.put(key, request.getParameterValues("regnot")[i]);
					}else if("GUBUN".equals(key)){
						itCredRow.put(key, request.getParameterValues("gubun")[i]);
					//}else if("EXPRD".equals(key)){
					//	itCredRow.put(key, request.getParameterValues("exprd")[i]);
					}else if("OTNAM".equals(key)){
						itCredRow.put(key, request.getParameterValues("otnam")[i]);
					}else if("OTOAM".equals(key)){
						itCredRow.put(key, request.getParameterValues("otoam")[i]);
					}else if("TRDMK".equals(key)){
						itCredRow.put(key, request.getParameterValues("trdmk")[i]);
					}else if("CCTRA".equals(key)){
						itCredRow.put(key, request.getParameterValues("cctra")[i]);
					}else{
						itCredRow.put(key, request.getParameterValues(key)[i]);
					}
				}
				
				itCredList.add(itCredRow);
			}
		}
		
		rfcParamList.put("IT_CRED", itCredList);
		
		HashMap<String, Object> paramRow = new HashMap<String, Object>();
		
		for(String key : Constant.taxKeySet){
			paramRow.put(key, params.get("exTax_"+key));
		}
		
		rfcParam.put("IM_TAX", paramRow);
		
		if(params.containsKey("itDelLineCnt")){
			int itDelLineCnt = Integer.parseInt((String)params.get("itDelLineCnt"));
			ArrayList<HashMap<String, Object>> itDelLineList = new ArrayList<HashMap<String, Object>>();
			HashMap<String, Object> itDelLineRow;
			
			if(itDelLineCnt == 1){
				itDelLineRow = new HashMap<String, Object>();
				itDelLineRow.put("SEQ", params.get("itDelLine_SEQ"));
				itDelLineList.add(itDelLineRow);
			}else{
				for(int i = 0 ; i < itDelLineCnt ; i++){
					itDelLineRow = new HashMap<String, Object>();
					itDelLineRow.put("SEQ", request.getParameterValues("itDelLine_SEQ")[i]);
					itDelLineList.add(itDelLineRow);
				}
			}
			
			rfcParamList.put("IT_DEL_LINE", itDelLineList);
		}

		return webEssRcv.callRfcFunction("ZHR_RFC_PY_003_ONINPUT_POP6", rfcParam, rfcParamList, request);
	}
	
	@RequestMapping(value = "/annuityPop.do")
	public ModelAndView annuityPop(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/webess/payMng/annuityPop");
	
		int etPensaveCnt = Integer.parseInt((String)params.get("etPensaveCnt"));
		ArrayList<HashMap<String, Object>> etPensaveList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> etPensaveRow;

		String[] annuityKeySet = Constant.annuityKeySet;
		
		if(etPensaveCnt == 1){
			etPensaveRow = new HashMap<String, Object>();
			
			for(String key : annuityKeySet){
				etPensaveRow.put(key, params.get("etPensave_"+key));
			}
			etPensaveList.add(etPensaveRow);
		}else if(etPensaveCnt > 1){
			for(int i = 0 ; i < etPensaveCnt ; i++){
				etPensaveRow = new HashMap<String, Object>();
				for(String key : annuityKeySet){
					etPensaveRow.put(key, request.getParameterValues("etPensave_"+key)[i]);
				}
				etPensaveList.add(etPensaveRow);
			}
		}
		
		int etBankCnt = Integer.parseInt((String)params.get("etBankCnt"));
		ArrayList<HashMap<String, Object>> etBankList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> etBankRow;

		if(etBankCnt == 1){
			etBankRow = new HashMap<String, Object>();
			
			etBankRow.put("KEY", params.get("etBankKey"));
			etBankRow.put("VALUE", params.get("etBankValue"));
			
			etBankList.add(etBankRow);
		}else if(etBankCnt > 1){
			for(int i = 0 ; i < etBankCnt ; i++){
				etBankRow = new HashMap<String, Object>();
				
				etBankRow.put("KEY"  , request.getParameterValues("etBankKey")[i]);
				etBankRow.put("VALUE", request.getParameterValues("etBankValue")[i]);
				
				etBankList.add(etBankRow);
			}
		}
		
		HashMap<String, Object> taxRow = new HashMap<String, Object>();
		
		String[] taxKeySet = Constant.taxKeySet;
		
		for(String key : taxKeySet){
			taxRow.put(key, params.get("exTax_"+key));
		}
		
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		
		rfcParam.put("IM_SUBTY", "E2");
		
		Map<? ,?> resultCode = webEssRcv.callRfcFunction("ZHR_RFC_PY_003_GET_LISTBOX7", rfcParam, null, request);
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		
		resultMap.put("ET_PENSAVE", etPensaveList);
		resultMap.put("EX_TAX"    , taxRow);
		resultMap.put("ET_BANK"   , etBankList);
		resultMap.put("ET_SLIST"  , resultCode.get("ET_SLIST"));
		
		mav.addObject("resultMap"    , resultMap);
		mav.addObject("annuityKeySet", annuityKeySet);
		mav.addObject("taxKeySet"    , taxKeySet);
		mav.addObject("params"       , params);

		return mav;
	}
	
	@RequestMapping(value = "callPY003OninputPop7.do")
	public @ResponseBody Map<?, ?> callPY003OninputPop7(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");

		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> rfcParamList = new HashMap<String, Object>();
		
		String annuityKeySet[] = Constant.annuityKeySet;
		
		rfcParam.put("IM_PERNR"   , user.getEmpNo());
		rfcParam.put("IM_EVENT_ID", params.get("imEventId"));
		rfcParam.put("IM_ZYEAR"   , params.get("imZyear"));
		rfcParam.put("IM_REGNO"   , params.get("imRegno"));
		rfcParam.put("IM_BEGDA"   , params.get("imBegda"));
		rfcParam.put("IM_ENDDA"   , params.get("imEndda")); 
		rfcParam.put("IM_TOT"     , params.get("imTot"));
		
		int paramCnt = Integer.parseInt((String)params.get("paramCnt"));
		ArrayList<HashMap<String, Object>> itPensaveList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> itPensaveRow;
		
		if(paramCnt == 1){
			itPensaveRow = new HashMap<String, Object>();
			
			for(String key : annuityKeySet){
				if("ET_PNSTY".equals(key)){
					itPensaveRow.put(key, params.get("etPnsty"));
				}else if("ET_FINCO".equals(key)){
					itPensaveRow.put(key, params.get("etFinco"));
				}else if("ET_ACCNO".equals(key)){
					itPensaveRow.put(key, params.get("etAccno"));
				}else if("ETNAM".equals(key)){
					itPensaveRow.put(key, params.get("etnam"));
				}else{
					itPensaveRow.put(key, params.get(key));
				}
			}
			itPensaveList.add(itPensaveRow);
		}else if(paramCnt > 1){
			for(int i = 0 ; i < paramCnt ; i++){
				itPensaveRow = new HashMap<String, Object>();
				
				for(String key : annuityKeySet){
					if("ET_PNSTY".equals(key)){
						itPensaveRow.put(key, request.getParameterValues("etPnsty")[i]);
					}else if("ET_FINCO".equals(key)){
						itPensaveRow.put(key, request.getParameterValues("etFinco")[i]);
					}else if("ET_ACCNO".equals(key)){
						itPensaveRow.put(key, request.getParameterValues("etAccno")[i]);
					}else if("ETNAM".equals(key)){
						itPensaveRow.put(key, request.getParameterValues("etnam")[i]);
					}else{
						itPensaveRow.put(key, request.getParameterValues(key)[i]);
					}
				}
				itPensaveList.add(itPensaveRow);
			}
		}
		
		rfcParamList.put("IT_PENSAVE", itPensaveList);
		
		HashMap<String, Object> paramRow = new HashMap<String, Object>();
		
		for(String key : Constant.taxKeySet){
			paramRow.put(key, params.get("exTax_"+key));
		}
		
		rfcParam.put("IM_TAX", paramRow);
		
		if(params.containsKey("itDelLineCnt")){
			int itDelLineCnt = Integer.parseInt((String)params.get("itDelLineCnt"));
			ArrayList<HashMap<String, Object>> itDelLineList = new ArrayList<HashMap<String, Object>>();
			HashMap<String, Object> itDelLineRow;
			
			if(itDelLineCnt == 1){
				itDelLineRow = new HashMap<String, Object>();
				itDelLineRow.put("SEQ", params.get("itDelLine_SEQ"));
				itDelLineList.add(itDelLineRow);
			}else{
				for(int i = 0 ; i < itDelLineCnt ; i++){
					itDelLineRow = new HashMap<String, Object>();
					itDelLineRow.put("SEQ", request.getParameterValues("itDelLine_SEQ")[i]);
					itDelLineList.add(itDelLineRow);
				}
			}
			
			rfcParamList.put("IT_DEL_LINE", itDelLineList);
		}

		return webEssRcv.callRfcFunction("ZHR_RFC_PY_003_ONINPUT_POP7", rfcParam, rfcParamList, request);
	}
	
	@RequestMapping(value = "/housePop.do")
	public ModelAndView housePop(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/webess/payMng/housePop");
	
		int etChungCnt = Integer.parseInt((String)params.get("etChungCnt"));
		ArrayList<HashMap<String, Object>> etChungList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> etChungRow;

		String[] houseKeySet = Constant.houseKeySet;

		if(etChungCnt == 1){
			etChungRow = new HashMap<String, Object>();
			
			for(String key : houseKeySet){
				etChungRow.put(key, params.get("etChung_"+key));
			}
			etChungList.add(etChungRow);
		}else if(etChungCnt > 1){
			for(int i = 0 ; i < etChungCnt ; i++){
				etChungRow = new HashMap<String, Object>();
				for(String key : houseKeySet){
					etChungRow.put(key, request.getParameterValues("etChung_"+key)[i]);
				}
				etChungList.add(etChungRow);
			}
		}
		
		int etBankCnt = Integer.parseInt((String)params.get("etBankCnt"));
		ArrayList<HashMap<String, Object>> etBankList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> etBankRow;

		if(etBankCnt == 1){
			etBankRow = new HashMap<String, Object>();
			
			etBankRow.put("KEY", params.get("etBankKey"));
			etBankRow.put("VALUE", params.get("etBankValue"));
			
			etBankList.add(etBankRow);
		}else if(etBankCnt > 1){
			for(int i = 0 ; i < etBankCnt ; i++){
				etBankRow = new HashMap<String, Object>();
				
				etBankRow.put("KEY"  , request.getParameterValues("etBankKey")[i]);
				etBankRow.put("VALUE", request.getParameterValues("etBankValue")[i]);
				
				etBankList.add(etBankRow);
			}
		}
		
		HashMap<String, Object> taxRow = new HashMap<String, Object>();
		
		String[] taxKeySet = Constant.taxKeySet;
		
		for(String key : taxKeySet){
			taxRow.put(key, params.get("exTax_"+key));
		}
		
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		
		rfcParam.put("IM_SUBTY", "E3");
		
		Map<? ,?> resultCode = webEssRcv.callRfcFunction("ZHR_RFC_PY_003_GET_LISTBOX7", rfcParam, null, request);
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		
		resultMap.put("ET_CHUNG", etChungList);
		resultMap.put("EX_TAX"  , taxRow);
		resultMap.put("ET_BANK" , etBankList);
		resultMap.put("ET_SLIST", resultCode.get("ET_SLIST"));
		
		mav.addObject("resultMap"  , resultMap);
		mav.addObject("houseKeySet", houseKeySet);
		mav.addObject("taxKeySet"  , taxKeySet);
		mav.addObject("params"     , params);

		return mav;
	}
	
	@RequestMapping(value = "callPY003OninputPop8.do")
	public @ResponseBody Map<?, ?> callPY003OninputPop8(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");

		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> rfcParamList = new HashMap<String, Object>();
		
		String houseKeySet[] = Constant.houseKeySet;
		
		rfcParam.put("IM_PERNR"   , user.getEmpNo());
		rfcParam.put("IM_EVENT_ID", params.get("imEventId"));
		rfcParam.put("IM_ZYEAR"   , params.get("imZyear"));
		rfcParam.put("IM_REGNO"   , params.get("imRegno"));
		rfcParam.put("IM_BEGDA"   , params.get("imBegda"));
		rfcParam.put("IM_ENDDA"   , params.get("imEndda")); 
		rfcParam.put("IM_TOT"     , params.get("imTot"));
		
		int paramCnt = Integer.parseInt((String)params.get("paramCnt"));
		ArrayList<HashMap<String, Object>> itChungList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> itChungRow;

		if(paramCnt == 1){
			itChungRow = new HashMap<String, Object>();
			
			for(String key : houseKeySet){
				if("ET_PNSTY".equals(key)){
					itChungRow.put(key, params.get("etPnsty"));
				}else if("ET_FINCO".equals(key)){
					itChungRow.put(key, params.get("etFinco"));
				}else if("ET_ACCNO".equals(key)){
					itChungRow.put(key, params.get("etAccno"));
				}else if("ET_RCBEG".equals(key)){
					itChungRow.put(key, params.get("etRcbeg"));
				}else if("ETNAM".equals(key)){
					itChungRow.put(key, params.get("etnam"));
				}else{
					itChungRow.put(key, params.get(key));
				}
			}
			itChungList.add(itChungRow);
		}else if(paramCnt > 1){
			for(int i = 0 ; i < paramCnt ; i++){
				itChungRow = new HashMap<String, Object>();
				
				for(String key : houseKeySet){
					if("ET_PNSTY".equals(key)){
						itChungRow.put(key, request.getParameterValues("etPnsty")[i]);
					}else if("ET_FINCO".equals(key)){
						itChungRow.put(key, request.getParameterValues("etFinco")[i]);
					}else if("ET_ACCNO".equals(key)){
						itChungRow.put(key, request.getParameterValues("etAccno")[i]);
					}else if("ET_RCBEG".equals(key)){
						itChungRow.put(key, request.getParameterValues("etRcbeg")[i]);
					}else if("ETNAM".equals(key)){
						itChungRow.put(key, request.getParameterValues("etnam")[i]);
					}else{
						itChungRow.put(key, request.getParameterValues(key)[i]);
					}
				}
				itChungList.add(itChungRow);
			}
		}

		rfcParamList.put("IT_CHUNG", itChungList);
		
		HashMap<String, Object> paramRow = new HashMap<String, Object>();
		
		for(String key : Constant.taxKeySet){
			paramRow.put(key, params.get("exTax_"+key));
		}
		
		rfcParam.put("IM_TAX", paramRow);
		
		if(params.containsKey("itDelLineCnt")){
			int itDelLineCnt = Integer.parseInt((String)params.get("itDelLineCnt"));
			ArrayList<HashMap<String, Object>> itDelLineList = new ArrayList<HashMap<String, Object>>();
			HashMap<String, Object> itDelLineRow;
			
			if(itDelLineCnt == 1){
				itDelLineRow = new HashMap<String, Object>();
				itDelLineRow.put("SEQ", params.get("itDelLine_SEQ"));
				itDelLineList.add(itDelLineRow);
			}else{
				for(int i = 0 ; i < itDelLineCnt ; i++){
					itDelLineRow = new HashMap<String, Object>();
					itDelLineRow.put("SEQ", request.getParameterValues("itDelLine_SEQ")[i]);
					itDelLineList.add(itDelLineRow);
				}
			}
			
			rfcParamList.put("IT_DEL_LINE", itDelLineList);
		}

		return webEssRcv.callRfcFunction("ZHR_RFC_PY_003_ONINPUT_POP8", rfcParam, rfcParamList, request);
	}
	
	@RequestMapping(value = "/paymentPop.do")
	public ModelAndView paymentPop(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/webess/payMng/paymentPop");
	
		int etLtaisCnt = Integer.parseInt((String)params.get("etLtaisCnt"));
		ArrayList<HashMap<String, Object>> etLtaisList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> etLtaisRow;

		String[] paymentKeySet = Constant.paymentKeySet;

		if(etLtaisCnt == 1){
			etLtaisRow = new HashMap<String, Object>();
			
			for(String key : paymentKeySet){
				etLtaisRow.put(key, params.get("etLtais_"+key));
			}
			etLtaisList.add(etLtaisRow);
		}else if(etLtaisCnt > 1){
			for(int i = 0 ; i < etLtaisCnt ; i++){
				etLtaisRow = new HashMap<String, Object>();
				for(String key : paymentKeySet){
					etLtaisRow.put(key, request.getParameterValues("etLtais_"+key)[i]);
				}
				etLtaisList.add(etLtaisRow);
			}
		}
		
		int etBankCnt = Integer.parseInt((String)params.get("etBankCnt"));
		ArrayList<HashMap<String, Object>> etBankList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> etBankRow;

		if(etBankCnt == 1){
			etBankRow = new HashMap<String, Object>();
			
			etBankRow.put("KEY", params.get("etBankKey"));
			etBankRow.put("VALUE", params.get("etBankValue"));
			
			etBankList.add(etBankRow);
		}else if(etBankCnt > 1){
			for(int i = 0 ; i < etBankCnt ; i++){
				etBankRow = new HashMap<String, Object>();
				
				etBankRow.put("KEY"  , request.getParameterValues("etBankKey")[i]);
				etBankRow.put("VALUE", request.getParameterValues("etBankValue")[i]);
				
				etBankList.add(etBankRow);
			}
		}
		
		HashMap<String, Object> taxRow = new HashMap<String, Object>();
		
		String[] taxKeySet = Constant.taxKeySet;
		
		for(String key : taxKeySet){
			taxRow.put(key, params.get("exTax_"+key));
		}
		
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		
		rfcParam.put("IM_SUBTY", "E7");
		
		Map<? ,?> resultCode = webEssRcv.callRfcFunction("ZHR_RFC_PY_003_GET_LISTBOX7", rfcParam, null, request);
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		
		resultMap.put("ET_LTAIS", etLtaisList);
		resultMap.put("EX_TAX"  , taxRow);
		resultMap.put("ET_BANK" , etBankList);
		resultMap.put("ET_SLIST", resultCode.get("ET_SLIST"));
		
		mav.addObject("resultMap"    , resultMap);
		mav.addObject("paymentKeySet", paymentKeySet);
		mav.addObject("taxKeySet"    , taxKeySet);
		mav.addObject("params"       , params);

		return mav;
	}
	
	@RequestMapping(value = "callPY003OninputPop16.do")
	public @ResponseBody Map<?, ?> callPY003OninputPop16(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");

		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> rfcParamList = new HashMap<String, Object>();
		
		String paymentKeySet[] = Constant.paymentKeySet;
		
		rfcParam.put("IM_PERNR"   , user.getEmpNo());
		rfcParam.put("IM_EVENT_ID", params.get("imEventId"));
		rfcParam.put("IM_ZYEAR"   , params.get("imZyear"));
		rfcParam.put("IM_REGNO"   , params.get("imRegno"));
		rfcParam.put("IM_BEGDA"   , params.get("imBegda"));
		rfcParam.put("IM_ENDDA"   , params.get("imEndda"));
		
		int paramCnt = Integer.parseInt((String)params.get("paramCnt"));
		ArrayList<HashMap<String, Object>> itLtAisList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> itLtAisRow;

		if(paramCnt == 1){
			itLtAisRow = new HashMap<String, Object>();
			
			for(String key : paymentKeySet){
				if("ET_FINCO".equals(key)){
					itLtAisRow.put(key, params.get("etFinco"));
				}else if("ET_ACCNO".equals(key)){
					itLtAisRow.put(key, params.get("etAccno"));
				}else if("ETNAM".equals(key)){
					itLtAisRow.put(key, params.get("etnam"));
				}else{
					itLtAisRow.put(key, params.get(key));
				}
			}
			itLtAisList.add(itLtAisRow);
		}else if(paramCnt > 1){
			for(int i = 0 ; i < paramCnt ; i++){
				itLtAisRow = new HashMap<String, Object>();
				
				for(String key : paymentKeySet){
					if("ET_FINCO".equals(key)){
						itLtAisRow.put(key, request.getParameterValues("etFinco")[i]);
					}else if("ET_ACCNO".equals(key)){
						itLtAisRow.put(key, request.getParameterValues("etAccno")[i]);
					}else if("ETNAM".equals(key)){
						itLtAisRow.put(key, request.getParameterValues("etnam")[i]);
					}else{
						itLtAisRow.put(key, request.getParameterValues(key)[i]);
					}
				}
				itLtAisList.add(itLtAisRow);
			}
		}

		rfcParamList.put("IT_LTAIS", itLtAisList);
		
		HashMap<String, Object> paramRow = new HashMap<String, Object>();
		
		for(String key : Constant.taxKeySet){
			paramRow.put(key, params.get("exTax_"+key));
		}
		
		rfcParam.put("IM_TAX", paramRow);
		
		if(params.containsKey("itDelLineCnt")){
			int itDelLineCnt = Integer.parseInt((String)params.get("itDelLineCnt"));
			ArrayList<HashMap<String, Object>> itDelLineList = new ArrayList<HashMap<String, Object>>();
			HashMap<String, Object> itDelLineRow;
			
			if(itDelLineCnt == 1){
				itDelLineRow = new HashMap<String, Object>();
				itDelLineRow.put("SEQ", params.get("itDelLine_SEQ"));
				itDelLineList.add(itDelLineRow);
			}else{
				for(int i = 0 ; i < itDelLineCnt ; i++){
					itDelLineRow = new HashMap<String, Object>();
					itDelLineRow.put("SEQ", request.getParameterValues("itDelLine_SEQ")[i]);
					itDelLineList.add(itDelLineRow);
				}
			}
			
			rfcParamList.put("IT_DEL_LINE", itDelLineList);
		}

		return webEssRcv.callRfcFunction("ZHR_RFC_PY_003_ONINPUT_POP16", rfcParam, rfcParamList, request);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "premiumPop.do")
	public ModelAndView premiumPop(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/webess/payMng/premiumPop");

		User user = (User) getRequestAttribute("ikep.user");

		int etInsCnt = Integer.parseInt((String)params.get("etInsCnt"));
		ArrayList<HashMap<String, Object>> etInsList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> etInsRow;

		String[] premiumKeySet = Constant.premiumKeySet;

		if(etInsCnt == 1){
			etInsRow = new HashMap<String, Object>();
			
			for(String key : premiumKeySet){
				etInsRow.put(key, params.get("etIns_"+key));
			}
			
			etInsList.add(etInsRow);
		}else if(etInsCnt > 1){
			for(int i = 0 ; i < etInsCnt ; i++){
				etInsRow = new HashMap<String, Object>();
				for(String key : premiumKeySet){
					etInsRow.put(key, request.getParameterValues("etIns_"+key)[i]);
				}
				etInsList.add(etInsRow);
			}
		}

		ArrayList<Map<?, ?>> exLayoutList = new ArrayList<Map<?, ?>>();
		ArrayList<Map<?, ?>> exInsList = new ArrayList<Map<?, ?>>();

		HashMap<String, Object> layoutParam = new HashMap<String, Object>();
		Map<?, ?> layoutResult;

		layoutParam.put("IM_YEAR"    , params.get("EX_YEAR"));
		layoutParam.put("IM_PERNR"   , user.getEmpNo());
		layoutParam.put("IM_EVENT_ID", "");
		layoutParam.put("IM_FIRST"   , "X");

		for(int i = 0 ; i < etInsList.size() ; i++){
			layoutParam.put("IM_INS", etInsList.get(i));
			
			layoutResult = webEssRcv.callRfcFunction("ZHR_RFC_PY_003_FILL_POP5", layoutParam, null, request);
			
			exLayoutList.add((Map<?, ?>)layoutResult.get("EX_LAYOUT"));
			exInsList.add((Map<?, ?>)layoutResult.get("EX_INS"));
		}

		HashMap<String, Object> taxRow = new HashMap<String, Object>();

		String[] taxKeySet = Constant.taxKeySet;

		for(String key : taxKeySet){
			taxRow.put(key, params.get("exTax_"+key));
		}

		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		rfcParam.put("IM_PERNR", user.getEmpNo());
		rfcParam.put("IM_ZYEAR", params.get("EX_YEAR"));

		Map<? ,?> resultCode = webEssRcv.callRfcFunction("ZHR_RFC_PY_003_GET_LISTBOX5", rfcParam, null, request);
				
		ArrayList<HashMap<String, Object>> etKdsvh = (ArrayList<HashMap<String, Object>>)resultCode.get("ET_KDSVH");

		HashMap<String, Object> tempParam = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> tempList;
		HashMap<String, Object> tempRow;

		ArrayList<HashMap<String, Object>> etFamList = new ArrayList<HashMap<String, Object>>();

		for(int i = 0 ; i < etKdsvh.size() ; i++){
			tempParam.put("IM_PERNR", user.getEmpNo());
			tempParam.put("IM_ZYEAR", params.get("EX_YEAR"));
			tempParam.put("IM_SUBTY", etKdsvh.get(i).get("KEY"));
			
			tempList = (ArrayList<HashMap<String, Object>>)webEssRcv.callRfcFunction("ZHR_RFC_PY_003_GET_FAMILY_INSU", tempParam, null, request).get("ET_FAM_LIST");
			
			for(int j = 0 ; j < tempList.size() ; j++){
				tempRow = new HashMap<String, Object>();
				tempRow.put("KEY"  , tempList.get(j).get("KEY"));
				tempRow.put("VALUE", tempList.get(j).get("VALUE"));
				tempRow.put("SUBTY", etKdsvh.get(i).get("KEY"));
				
				etFamList.add(tempRow);
			}
		}

		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		resultMap.put("ET_INS"        , etInsList);
		resultMap.put("EX_TAX"        , taxRow);
		resultMap.put("ET_KDSVH"      , resultCode.get("ET_KDSVH"));
		resultMap.put("ET_FAM_LIST"   , etFamList);
		resultMap.put("EX_LAYOUT_LIST", exLayoutList);
		resultMap.put("EX_INS_LIST"   , exInsList);

		mav.addObject("resultMap"    , resultMap);
		mav.addObject("premiumKeySet", premiumKeySet);
		mav.addObject("taxKeySet"    , taxKeySet);
		mav.addObject("params"       , params);

		return mav;
	}
	
	@RequestMapping(value = "callPY003FillPop5.do")
	public @ResponseBody Map<?, ?> callPY003FillPop5(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");
		
		String[] premiumKeySet = Constant.premiumKeySet;

		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		
		rfcParam.put("IM_YEAR"    , params.get("imYear"));
		rfcParam.put("IM_PERNR"   , user.getEmpNo());
		rfcParam.put("IM_EVENT_ID", params.get("imEventId"));
		
		HashMap<String, Object> paramRow = new HashMap<String, Object>();
		
		for(String key : premiumKeySet){
			if("KDSVH".equals(key) || "SUBTY".equals(key)){
				paramRow.put(key, params.get("kdsvh"));
			}else if("DNAME".equals(key) || "REGNO".equals(key)){
				paramRow.put(key, params.get("dname"));
			}else if("REGNOT".equals(key)){
				paramRow.put(key, params.get("regnot"));
			}else if("OTNAM".equals(key)){
				paramRow.put(key, params.get("otnam"));
			}else if("OTOAM".equals(key)){
				paramRow.put(key, params.get("otoam"));
			}else if("OTHAN".equals(key)){
				paramRow.put(key, params.get("othan"));
			}else{
				paramRow.put(key, params.get(key));
			}
		}
		
		rfcParam.put("IM_INS", paramRow);

		return webEssRcv.callRfcFunction("ZHR_RFC_PY_003_FILL_POP5", rfcParam, null, request);
	}
	
	@RequestMapping(value = "callPY003OninputPop5.do")
	public @ResponseBody Map<?, ?> callPY003OninputPop5(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");

		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> rfcParamList = new HashMap<String, Object>();
		
		String premiumKeySet[] = Constant.premiumKeySet;
		
		rfcParam.put("IM_PERNR"   , user.getEmpNo());
		rfcParam.put("IM_EVENT_ID", params.get("imEventId"));
		rfcParam.put("IM_ZYEAR"   , params.get("imZyear"));
		rfcParam.put("IM_REGNO"   , params.get("imRegno"));
		
		int paramCnt = Integer.parseInt((String)params.get("paramCnt"));
		ArrayList<HashMap<String, Object>> itInsList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> itInsRow;
		
		if(paramCnt == 1){
			itInsRow = new HashMap<String, Object>();
			
			for(String key : premiumKeySet){
				if("KDSVH".equals(key) || "SUBTY".equals(key)){
					itInsRow.put(key, params.get("kdsvh"));
				}else if("DNAME".equals(key) || "REGNO".equals(key)){
					itInsRow.put(key, params.get("dname"));
				}else if("REGNOT".equals(key)){
					itInsRow.put(key, params.get("regnot"));
				}else if("GUBUN".equals(key)){
					itInsRow.put(key, params.get("gubun"));
				}else if("OTNAM".equals(key)){
					itInsRow.put(key, params.get("otnam"));
				}else if("OTOAM".equals(key)){
					itInsRow.put(key, params.get("otoam"));
				}else if("OTHAN".equals(key)){
					itInsRow.put(key, params.get("othan"));
				}else{
					itInsRow.put(key, params.get(key));
				}
			}
			itInsList.add(itInsRow);
		}else if(paramCnt > 1){
			for(int i = 0 ; i < paramCnt ; i++){
				itInsRow = new HashMap<String, Object>();
				
				for(String key : premiumKeySet){
					if("KDSVH".equals(key) || "SUBTY".equals(key)){
						itInsRow.put(key, request.getParameterValues("kdsvh")[i]);
					}else if("DNAME".equals(key) || "REGNO".equals(key)){
						itInsRow.put(key, request.getParameterValues("dname")[i]);
					}else if("REGNOT".equals(key)){
						itInsRow.put(key, request.getParameterValues("regnot")[i]);
					}else if("OTNAM".equals(key)){
						itInsRow.put(key, request.getParameterValues("otnam")[i]);
					}else if("OTOAM".equals(key)){
						itInsRow.put(key, request.getParameterValues("otoam")[i]);
					}else if("OTHAN".equals(key)){
						itInsRow.put(key, request.getParameterValues("othan")[i]);
					}else{
						itInsRow.put(key, request.getParameterValues(key)[i]);
					}
				}
				
				itInsList.add(itInsRow);
			}
		}
		
		rfcParamList.put("IT_INS", itInsList);
		
		HashMap<String, Object> paramRow = new HashMap<String, Object>();
		
		for(String key : Constant.taxKeySet){
			paramRow.put(key, params.get("exTax_"+key));
		}
		
		rfcParam.put("IM_TAX", paramRow);
		
		if(params.containsKey("itDelLineCnt")){
			int itDelLineCnt = Integer.parseInt((String)params.get("itDelLineCnt"));
			ArrayList<HashMap<String, Object>> itDelLineList = new ArrayList<HashMap<String, Object>>();
			HashMap<String, Object> itDelLineRow;
			
			if(itDelLineCnt == 1){
				itDelLineRow = new HashMap<String, Object>();
				itDelLineRow.put("SEQ", params.get("itDelLine_SEQ"));
				itDelLineList.add(itDelLineRow);
			}else{
				for(int i = 0 ; i < itDelLineCnt ; i++){
					itDelLineRow = new HashMap<String, Object>();
					itDelLineRow.put("SEQ", request.getParameterValues("itDelLine_SEQ")[i]);
					itDelLineList.add(itDelLineRow);
				}
			}
			
			rfcParamList.put("IT_DEL_LINE", itDelLineList);
		}

		return webEssRcv.callRfcFunction("ZHR_RFC_PY_003_ONINPUT_POP5", rfcParam, rfcParamList, request);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "medicalPop.do")
	public ModelAndView medicalPop(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/webess/payMng/medicalPop");

		User user = (User) getRequestAttribute("ikep.user");

		int etMediCnt = Integer.parseInt((String)params.get("etMediCnt"));
		ArrayList<HashMap<String, Object>> etMediList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> etMediRow;

		String[] medicalKeySet = Constant.medicalKeySet;

		if(etMediCnt == 1){
			etMediRow = new HashMap<String, Object>();
			
			for(String key : medicalKeySet){
				etMediRow.put(key, params.get("etMedi_"+key));
			}
			
			etMediList.add(etMediRow);
		}else if(etMediCnt > 1){
			for(int i = 0 ; i < etMediCnt ; i++){
				etMediRow = new HashMap<String, Object>();
				for(String key : medicalKeySet){
					etMediRow.put(key, request.getParameterValues("etMedi_"+key)[i]);
				}
				etMediList.add(etMediRow);
			}
		}

		ArrayList<Map<?, ?>> exLayoutList = new ArrayList<Map<?, ?>>();
		ArrayList<Map<?, ?>> exMediList = new ArrayList<Map<?, ?>>();

		HashMap<String, Object> layoutParam = new HashMap<String, Object>();
		Map<?, ?> layoutResult;

		layoutParam.put("IM_YEAR"    , params.get("EX_YEAR"));
		layoutParam.put("IM_PERNR"   , user.getEmpNo());
		layoutParam.put("IM_EVENT_ID", "");
		layoutParam.put("IM_FIRST"   , "X");

		for(int i = 0 ; i < etMediList.size() ; i++){
			layoutParam.put("IM_MEDI", etMediList.get(i));
			
			layoutResult = webEssRcv.callRfcFunction("ZHR_RFC_PY_003_FILL_POP2", layoutParam, null, request);
			
			exLayoutList.add((Map<?, ?>)layoutResult.get("EX_LAYOUT"));
			exMediList.add((Map<?, ?>)layoutResult.get("EX_MEDI"));
		}

		HashMap<String, Object> taxRow = new HashMap<String, Object>();

		String[] taxKeySet = Constant.taxKeySet;

		for(String key : taxKeySet){
			taxRow.put(key, params.get("exTax_"+key));
		}

		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		rfcParam.put("IM_PERNR", user.getEmpNo());
		rfcParam.put("IM_ZYEAR", params.get("EX_YEAR"));

		Map<? ,?> resultCode = webEssRcv.callRfcFunction("ZHR_RFC_PY_003_GET_LISTBOX2", rfcParam, null, request);
				
		ArrayList<HashMap<String, Object>> etKdsvh = (ArrayList<HashMap<String, Object>>)resultCode.get("ET_KDSVH");

		HashMap<String, Object> tempParam = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> tempList;
		HashMap<String, Object> tempRow;

		ArrayList<HashMap<String, Object>> etFamList = new ArrayList<HashMap<String, Object>>();

		for(int i = 0 ; i < etKdsvh.size() ; i++){
			tempParam.put("IM_PERNR", user.getEmpNo());
			tempParam.put("IM_ZYEAR", params.get("EX_YEAR"));
			tempParam.put("IM_SUBTY", etKdsvh.get(i).get("KEY"));
			
			tempList = (ArrayList<HashMap<String, Object>>)webEssRcv.callRfcFunction("ZHR_RFC_PY_003_GET_FAMILY", tempParam, null, request).get("ET_FAM_LIST");
			
			for(int j = 0 ; j < tempList.size() ; j++){
				tempRow = new HashMap<String, Object>();
				tempRow.put("KEY"  , tempList.get(j).get("KEY"));
				tempRow.put("VALUE", tempList.get(j).get("VALUE"));
				tempRow.put("SUBTY", etKdsvh.get(i).get("KEY"));
				
				etFamList.add(tempRow);
			}
		}

		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		resultMap.put("ET_MEDI"       , etMediList);
		resultMap.put("EX_TAX"        , taxRow);
		resultMap.put("ET_KDSVH"      , resultCode.get("ET_KDSVH"));
		resultMap.put("ET_MEPCD"      , resultCode.get("ET_MEPCD"));
		resultMap.put("ET_FAM_LIST"   , etFamList);
		resultMap.put("EX_LAYOUT_LIST", exLayoutList);
		resultMap.put("EX_MEDI_LIST"  , exMediList);

		mav.addObject("resultMap"    , resultMap);
		mav.addObject("medicalKeySet", medicalKeySet);
		mav.addObject("taxKeySet"    , taxKeySet);
		mav.addObject("params"       , params);

		return mav;
	}
	
	@RequestMapping(value = "callPY003FillPop2.do")
	public @ResponseBody Map<?, ?> callPY003FillPop2(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");
		
		String[] medicalKeySet = Constant.medicalKeySet;

		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		
		rfcParam.put("IM_YEAR"    , params.get("imYear"));
		rfcParam.put("IM_PERNR"   , user.getEmpNo());
		rfcParam.put("IM_EVENT_ID", params.get("imEventId"));
		
		HashMap<String, Object> paramRow = new HashMap<String, Object>();
		
		for(String key : medicalKeySet){
			if("KDSVH".equals(key) || "SUBTY".equals(key)){
				paramRow.put(key, params.get("kdsvh"));
			}else if("DNAME".equals(key) || "REGNO".equals(key)){
				paramRow.put(key, params.get("dname"));
			}else if("REGNOT".equals(key)){
				paramRow.put(key, params.get("regnot"));
			}else if("MEPCD".equals(key)){
				paramRow.put(key, params.get("mepcd"));
			}else if("MENUM".equals(key)){
				paramRow.put(key, params.get("menum"));
			}else if("MENAM".equals(key)){
				paramRow.put(key, params.get("menam"));
			}else if("MECDT".equals(key)){
				paramRow.put(key, params.get("mecdt"));
			}else if("MECDA".equals(key)){
				paramRow.put(key, params.get("mecda"));
			}else if("GLCHK".equals(key)){
				paramRow.put(key, params.get("glchk"));
			}else if("SURPG".equals(key)){
				paramRow.put(key, params.get("surpg"));
			}else{
				paramRow.put(key, params.get(key));
			}
		}
		
		rfcParam.put("IM_MEDI", paramRow);

		return webEssRcv.callRfcFunction("ZHR_RFC_PY_003_FILL_POP2", rfcParam, null, request);
	}
	
	@RequestMapping(value = "callPY003OninputPop2.do")
	public @ResponseBody Map<?, ?> callPY003OninputPop2(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");

		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> rfcParamList = new HashMap<String, Object>();
		
		String medicalKeySet[] = Constant.medicalKeySet;
		
		rfcParam.put("IM_PERNR"   , user.getEmpNo());
		rfcParam.put("IM_EVENT_ID", params.get("imEventId"));
		rfcParam.put("IM_ZYEAR"   , params.get("imZyear"));
		rfcParam.put("IM_REGNO"   , params.get("imRegno"));
		
		int paramCnt = Integer.parseInt((String)params.get("paramCnt"));
		ArrayList<HashMap<String, Object>> itMediList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> itMediRow;
		
		if(paramCnt == 1){
			itMediRow = new HashMap<String, Object>();
			
			for(String key : medicalKeySet){
				if("KDSVH".equals(key) || "SUBTY".equals(key)){
					itMediRow.put(key, params.get("kdsvh"));
				}else if("DNAME".equals(key) || "REGNO".equals(key)){
					itMediRow.put(key, params.get("dname"));
				}else if("REGNOT".equals(key)){
					itMediRow.put(key, params.get("regnot"));
				}else if("MEPCD".equals(key)){
					itMediRow.put(key, params.get("mepcd"));
				}else if("MENUM".equals(key)){
					itMediRow.put(key, params.get("menum"));
				}else if("MENAM".equals(key)){
					itMediRow.put(key, params.get("menam"));
				}else if("MECDTT".equals(key)){
					itMediRow.put(key, params.get("mecdtt"));
				}else if("MECDA".equals(key)){
					itMediRow.put(key, params.get("mecda"));
				}else if("GLCHK".equals(key)){
					itMediRow.put(key, params.get("glchk"));
				}else if("SURPG".equals(key)){
					itMediRow.put(key, params.get("surpg"));
				}else{
					itMediRow.put(key, params.get(key));
				}
			}
			itMediList.add(itMediRow);
		}else if(paramCnt > 1){
			for(int i = 0 ; i < paramCnt ; i++){
				itMediRow = new HashMap<String, Object>();
				
				for(String key : medicalKeySet){
					if("KDSVH".equals(key) || "SUBTY".equals(key)){
						itMediRow.put(key, request.getParameterValues("kdsvh")[i]);
					}else if("DNAME".equals(key) || "REGNO".equals(key)){
						itMediRow.put(key, request.getParameterValues("dname")[i]);
					}else if("REGNOT".equals(key)){
						itMediRow.put(key, request.getParameterValues("regnot")[i]);
					}else if("MEPCD".equals(key)){
						itMediRow.put(key, request.getParameterValues("mepcd")[i]);
					}else if("MENUM".equals(key)){
						itMediRow.put(key, request.getParameterValues("menum")[i]);
					}else if("MENAM".equals(key)){
						itMediRow.put(key, request.getParameterValues("menam")[i]);
					}else if("MECDTT".equals(key)){
						itMediRow.put(key, request.getParameterValues("mecdtt")[i]);
					}else if("MECDA".equals(key)){
						itMediRow.put(key, request.getParameterValues("mecda")[i]);
					}else if("GLCHK".equals(key)){
						itMediRow.put(key, request.getParameterValues("glchk")[i]);
					}else if("SURPG".equals(key)){
						itMediRow.put(key, request.getParameterValues("surpg")[i]);
					}else{
						itMediRow.put(key, request.getParameterValues(key)[i]);
					}
				}
				
				itMediList.add(itMediRow);
			}
		}
		
		rfcParamList.put("IT_MEDI", itMediList);
		
		HashMap<String, Object> paramRow = new HashMap<String, Object>();
		
		for(String key : Constant.taxKeySet){
			paramRow.put(key, params.get("exTax_"+key));
		}
		
		rfcParam.put("IM_TAX", paramRow);
		
		if(params.containsKey("itDelLineCnt")){
			int itDelLineCnt = Integer.parseInt((String)params.get("itDelLineCnt"));
			ArrayList<HashMap<String, Object>> itDelLineList = new ArrayList<HashMap<String, Object>>();
			HashMap<String, Object> itDelLineRow;
			
			if(itDelLineCnt == 1){
				itDelLineRow = new HashMap<String, Object>();
				itDelLineRow.put("SEQ", params.get("itDelLine_SEQ"));
				itDelLineList.add(itDelLineRow);
			}else{
				for(int i = 0 ; i < itDelLineCnt ; i++){
					itDelLineRow = new HashMap<String, Object>();
					itDelLineRow.put("SEQ", request.getParameterValues("itDelLine_SEQ")[i]);
					itDelLineList.add(itDelLineRow);
				}
			}
			
			rfcParamList.put("IT_DEL_LINE", itDelLineList);
		}

		return webEssRcv.callRfcFunction("ZHR_RFC_PY_003_ONINPUT_POP2", rfcParam, rfcParamList, request);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "educationPop.do")
	public ModelAndView educationPop(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/webess/payMng/educationPop");

		User user = (User) getRequestAttribute("ikep.user");

		int etEduCnt = Integer.parseInt((String)params.get("etEduCnt"));
		ArrayList<HashMap<String, Object>> etEduList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> etEduRow;

		String[] educationKeySet = Constant.educationKeySet;

		if(etEduCnt == 1){
			etEduRow = new HashMap<String, Object>();
			
			for(String key : educationKeySet){
				etEduRow.put(key, params.get("etEdu_"+key));
			}
			
			etEduList.add(etEduRow);
		}else if(etEduCnt > 1){
			for(int i = 0 ; i < etEduCnt ; i++){
				etEduRow = new HashMap<String, Object>();
				for(String key : educationKeySet){
					etEduRow.put(key, request.getParameterValues("etEdu_"+key)[i]);
				}
				etEduList.add(etEduRow);
			}
		}

		ArrayList<Map<?, ?>> exLayoutList = new ArrayList<Map<?, ?>>();
		ArrayList<Map<?, ?>> exEduList = new ArrayList<Map<?, ?>>();

		HashMap<String, Object> layoutParam = new HashMap<String, Object>();
		Map<?, ?> layoutResult;

		layoutParam.put("IM_YEAR"    , params.get("EX_YEAR"));
		layoutParam.put("IM_PERNR"   , user.getEmpNo());
		layoutParam.put("IM_EVENT_ID", "");
		layoutParam.put("IM_FIRST"   , "X");

		for(int i = 0 ; i < etEduList.size() ; i++){
			layoutParam.put("IM_EDU", etEduList.get(i));
			
			layoutResult = webEssRcv.callRfcFunction("ZHR_RFC_PY_003_FILL_POP3", layoutParam, null, request);
			
			exLayoutList.add((Map<?, ?>)layoutResult.get("EX_LAYOUT"));
			exEduList.add((Map<?, ?>)layoutResult.get("EX_EDU"));
		}

		HashMap<String, Object> taxRow = new HashMap<String, Object>();

		String[] taxKeySet = Constant.taxKeySet;

		for(String key : taxKeySet){
			taxRow.put(key, params.get("exTax_"+key));
		}

		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		rfcParam.put("IM_PERNR", user.getEmpNo());
		rfcParam.put("IM_ZYEAR", params.get("EX_YEAR"));

		Map<? ,?> resultCode = webEssRcv.callRfcFunction("ZHR_RFC_PY_003_GET_LISTBOX3", rfcParam, null, request);
				
		ArrayList<HashMap<String, Object>> etKdsvh = (ArrayList<HashMap<String, Object>>)resultCode.get("ET_KDSVH");

		HashMap<String, Object> tempParam = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> tempList;
		HashMap<String, Object> tempRow;

		ArrayList<HashMap<String, Object>> etFamList = new ArrayList<HashMap<String, Object>>();

		for(int i = 0 ; i < etKdsvh.size() ; i++){
			tempParam.put("IM_PERNR", user.getEmpNo());
			tempParam.put("IM_ZYEAR", params.get("EX_YEAR"));
			tempParam.put("IM_SUBTY", etKdsvh.get(i).get("KEY"));
			
			tempList = (ArrayList<HashMap<String, Object>>)webEssRcv.callRfcFunction("ZHR_RFC_PY_003_GET_FAMILY", tempParam, null, request).get("ET_FAM_LIST");
			
			for(int j = 0 ; j < tempList.size() ; j++){
				tempRow = new HashMap<String, Object>();
				tempRow.put("KEY"  , tempList.get(j).get("KEY"));
				tempRow.put("VALUE", tempList.get(j).get("VALUE"));
				tempRow.put("SUBTY", etKdsvh.get(i).get("KEY"));
				
				etFamList.add(tempRow);
			}
		}

		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		resultMap.put("ET_EDU"        , etEduList);
		resultMap.put("EX_TAX"        , taxRow);
		resultMap.put("ET_KDSVH"      , resultCode.get("ET_KDSVH"));
		resultMap.put("ET_EDULV"      , resultCode.get("ET_EDULV"));
		resultMap.put("ET_FAM_LIST"   , etFamList);
		resultMap.put("EX_LAYOUT_LIST", exLayoutList);
		resultMap.put("EX_EDU_LIST"   , exEduList);

		mav.addObject("resultMap"    , resultMap);
		mav.addObject("educationKeySet", educationKeySet);
		mav.addObject("taxKeySet"    , taxKeySet);
		mav.addObject("params"       , params);

		return mav;
	}
	
	@RequestMapping(value = "callPY003FillPop3.do")
	public @ResponseBody Map<?, ?> callPY003FillPop3(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");
		
		String[] educationKeySet = Constant.educationKeySet;

		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		
		rfcParam.put("IM_YEAR"    , params.get("imYear"));
		rfcParam.put("IM_PERNR"   , user.getEmpNo());
		rfcParam.put("IM_EVENT_ID", params.get("imEventId"));
		
		HashMap<String, Object> paramRow = new HashMap<String, Object>();
		
		for(String key : educationKeySet){
			if("KDSVH".equals(key) || "SUBTY".equals(key)){
				paramRow.put(key, params.get("kdsvh"));
			}else if("DNAME".equals(key) || "REGNO".equals(key)){
				paramRow.put(key, params.get("dname"));
			}else if("REGNOT".equals(key)){
				paramRow.put(key, params.get("regnot"));
			}else if("OTELV".equals(key)){
				paramRow.put(key, params.get("otelv"));
			}else if("OTNAM".equals(key)){
				paramRow.put(key, params.get("otnam"));
			}else if("OTOAM".equals(key)){
				paramRow.put(key, params.get("otoam"));
			}else if("HNDID".equals(key)){
				paramRow.put(key, params.get("hndid"));
			}else if("SUCHK".equals(key)){
				paramRow.put(key, params.get("suchk"));
			}else if("EXCHK".equals(key)){
				paramRow.put(key, params.get("exchk"));
			}else{
				paramRow.put(key, params.get(key));
			}
		}
		
		rfcParam.put("IM_EDU", paramRow);

		return webEssRcv.callRfcFunction("ZHR_RFC_PY_003_FILL_POP3", rfcParam, null, request);
	}
	
	@RequestMapping(value = "callPY003OninputPop3.do")
	public @ResponseBody Map<?, ?> callPY003OninputPop3(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");

		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> rfcParamList = new HashMap<String, Object>();
		
		String educationKeySet[] = Constant.educationKeySet;
		
		rfcParam.put("IM_PERNR"   , user.getEmpNo());
		rfcParam.put("IM_EVENT_ID", params.get("imEventId"));
		rfcParam.put("IM_ZYEAR"   , params.get("imZyear"));
		rfcParam.put("IM_REGNO"   , params.get("imRegno"));
		
		int paramCnt = Integer.parseInt((String)params.get("paramCnt"));
		ArrayList<HashMap<String, Object>> itEduList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> itEduRow;
		
		if(paramCnt == 1){
			itEduRow = new HashMap<String, Object>();
			
			for(String key : educationKeySet){
				if("KDSVH".equals(key) || "SUBTY".equals(key)){
					itEduRow.put(key, params.get("kdsvh"));
				}else if("DNAME".equals(key) || "REGNO".equals(key)){
					itEduRow.put(key, params.get("dname"));
				}else if("REGNOT".equals(key)){
					itEduRow.put(key, params.get("regnot"));
				}else if("OTELV".equals(key)){
					itEduRow.put(key, params.get("otelv"));
				}else if("OTNAM".equals(key)){
					itEduRow.put(key, params.get("otnam"));
				}else if("OTOAM".equals(key)){
					itEduRow.put(key, params.get("otoam"));
				}else if("HNDID".equals(key)){
					itEduRow.put(key, params.get("hndid"));
				}else if("SUCHK".equals(key)){
					itEduRow.put(key, params.get("suchk"));
				}else if("EXCHK".equals(key)){
					itEduRow.put(key, params.get("exchk"));
				}else{
					itEduRow.put(key, params.get(key));
				}
			}
			itEduList.add(itEduRow);
		}else if(paramCnt > 1){
			for(int i = 0 ; i < paramCnt ; i++){
				itEduRow = new HashMap<String, Object>();
				
				for(String key : educationKeySet){
					if("KDSVH".equals(key) || "SUBTY".equals(key)){
						itEduRow.put(key, request.getParameterValues("kdsvh")[i]);
					}else if("DNAME".equals(key) || "REGNO".equals(key)){
						itEduRow.put(key, request.getParameterValues("dname")[i]);
					}else if("REGNOT".equals(key)){
						itEduRow.put(key, request.getParameterValues("regnot")[i]);
					}else if("OTELV".equals(key)){
						itEduRow.put(key, request.getParameterValues("otelv")[i]);
					}else if("OTNAM".equals(key)){
						itEduRow.put(key, request.getParameterValues("otnam")[i]);
					}else if("OTOAM".equals(key)){
						itEduRow.put(key, request.getParameterValues("otoam")[i]);
					}else if("HNDID".equals(key)){
						itEduRow.put(key, request.getParameterValues("hndid")[i]);
					}else if("SUCHK".equals(key)){
						itEduRow.put(key, request.getParameterValues("suchk")[i]);
					}else if("EXCHK".equals(key)){
						itEduRow.put(key, request.getParameterValues("exchk")[i]);
					}else{
						itEduRow.put(key, request.getParameterValues(key)[i]);
					}
				}
				
				itEduList.add(itEduRow);
			}
		}
		
		rfcParamList.put("IT_EDU", itEduList);
		
		HashMap<String, Object> paramRow = new HashMap<String, Object>();
		
		for(String key : Constant.taxKeySet){
			paramRow.put(key, params.get("exTax_"+key));
		}
		
		rfcParam.put("IM_TAX", paramRow);
		
		if(params.containsKey("itDelLineCnt")){
			int itDelLineCnt = Integer.parseInt((String)params.get("itDelLineCnt"));
			ArrayList<HashMap<String, Object>> itDelLineList = new ArrayList<HashMap<String, Object>>();
			HashMap<String, Object> itDelLineRow;
			
			if(itDelLineCnt == 1){
				itDelLineRow = new HashMap<String, Object>();
				itDelLineRow.put("SEQ", params.get("itDelLine_SEQ"));
				itDelLineList.add(itDelLineRow);
			}else{
				for(int i = 0 ; i < itDelLineCnt ; i++){
					itDelLineRow = new HashMap<String, Object>();
					itDelLineRow.put("SEQ", request.getParameterValues("itDelLine_SEQ")[i]);
					itDelLineList.add(itDelLineRow);
				}
			}
			
			rfcParamList.put("IT_DEL_LINE", itDelLineList);
		}

		return webEssRcv.callRfcFunction("ZHR_RFC_PY_003_ONINPUT_POP3", rfcParam, rfcParamList, request);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "donationPop.do")
	public ModelAndView donationPop(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/webess/payMng/donationPop");

		User user = (User) getRequestAttribute("ikep.user");

		int etDonaCnt = Integer.parseInt((String)params.get("etDonaCnt"));
		ArrayList<HashMap<String, Object>> etDonaList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> etDonaRow;

		String[] donationKeySet = Constant.donationKeySet;

		if(etDonaCnt == 1){
			etDonaRow = new HashMap<String, Object>();
			
			for(String key : donationKeySet){
				etDonaRow.put(key, params.get("etDona_"+key));
			}
			
			etDonaList.add(etDonaRow);
		}else if(etDonaCnt > 1){
			for(int i = 0 ; i < etDonaCnt ; i++){
				etDonaRow = new HashMap<String, Object>();
				for(String key : donationKeySet){
					etDonaRow.put(key, request.getParameterValues("etDona_"+key)[i]);
				}
				etDonaList.add(etDonaRow);
			}
		}

		ArrayList<Map<?, ?>> exLayoutList = new ArrayList<Map<?, ?>>();
		ArrayList<Map<?, ?>> exDonaList = new ArrayList<Map<?, ?>>();

		HashMap<String, Object> layoutParam = new HashMap<String, Object>();
		Map<?, ?> layoutResult;

		layoutParam.put("IM_YEAR"    , params.get("EX_YEAR"));
		layoutParam.put("IM_PERNR"   , user.getEmpNo());
		layoutParam.put("IM_EVENT_ID", "");
		layoutParam.put("IM_FIRST"   , "X");

		for(int i = 0 ; i < etDonaList.size() ; i++){
			layoutParam.put("IM_DONA", etDonaList.get(i));
			
			layoutResult = webEssRcv.callRfcFunction("ZHR_RFC_PY_003_FILL_POP4", layoutParam, null, request);
			
			exLayoutList.add((Map<?, ?>)layoutResult.get("EX_LAYOUT"));
			exDonaList.add((Map<?, ?>)layoutResult.get("EX_DONA"));
		}

		HashMap<String, Object> taxRow = new HashMap<String, Object>();

		String[] taxKeySet = Constant.taxKeySet;

		for(String key : taxKeySet){
			taxRow.put(key, params.get("exTax_"+key));
		}

		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		rfcParam.put("IM_PERNR", user.getEmpNo());
		rfcParam.put("IM_ZYEAR", params.get("EX_YEAR"));

		Map<? ,?> resultCode = webEssRcv.callRfcFunction("ZHR_RFC_PY_003_GET_LISTBOX4", rfcParam, null, request);
				
		ArrayList<HashMap<String, Object>> etKdsvh = (ArrayList<HashMap<String, Object>>)resultCode.get("ET_KDSVH");

		HashMap<String, Object> tempParam = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> tempList;
		HashMap<String, Object> tempRow;

		ArrayList<HashMap<String, Object>> etFamList = new ArrayList<HashMap<String, Object>>();

		for(int i = 0 ; i < etKdsvh.size() ; i++){
			tempParam.put("IM_PERNR", user.getEmpNo());
			tempParam.put("IM_ZYEAR", params.get("EX_YEAR"));
			tempParam.put("IM_SUBTY", etKdsvh.get(i).get("KEY"));
			
			tempList = (ArrayList<HashMap<String, Object>>)webEssRcv.callRfcFunction("ZHR_RFC_PY_003_GET_FAMILY", tempParam, null, request).get("ET_FAM_LIST");
			
			for(int j = 0 ; j < tempList.size() ; j++){
				tempRow = new HashMap<String, Object>();
				tempRow.put("KEY"  , tempList.get(j).get("KEY"));
				tempRow.put("VALUE", tempList.get(j).get("VALUE"));
				tempRow.put("SUBTY", etKdsvh.get(i).get("KEY"));
				
				etFamList.add(tempRow);
			}
		}

		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		resultMap.put("ET_DONA"       , etDonaList);
		resultMap.put("EX_TAX"        , taxRow);
		resultMap.put("ET_KDSVH"      , resultCode.get("ET_KDSVH"));
		resultMap.put("ET_CODE"       , resultCode.get("ET_CODE"));
		resultMap.put("ET_FAM_LIST"   , etFamList);
		resultMap.put("EX_LAYOUT_LIST", exLayoutList);
		resultMap.put("EX_DONA_LIST"  , exDonaList);

		mav.addObject("resultMap"     , resultMap);
		mav.addObject("donationKeySet", donationKeySet);
		mav.addObject("taxKeySet"     , taxKeySet);
		mav.addObject("params"        , params);

		return mav;
	}
	
	@RequestMapping(value = "callPY003FillPop4.do")
	public @ResponseBody Map<?, ?> callPY003FillPop4(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");
		
		String[] donationKeySet = Constant.donationKeySet;

		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		
		rfcParam.put("IM_YEAR"    , params.get("imYear"));
		rfcParam.put("IM_PERNR"   , user.getEmpNo());
		rfcParam.put("IM_EVENT_ID", params.get("imEventId"));
		
		HashMap<String, Object> paramRow = new HashMap<String, Object>();
		
		for(String key : donationKeySet){
			if("KDSVH".equals(key) || "SUBTY".equals(key)){
				paramRow.put(key, params.get("kdsvh"));
			}else if("DNAME".equals(key) || "REGNO".equals(key)){
				paramRow.put(key, params.get("dname"));
			}else if("REGNOT".equals(key)){
				paramRow.put(key, params.get("regnot"));
			}else if("DOCOD".equals(key)){
				paramRow.put(key, params.get("docod"));
			}else if("DOCODT".equals(key)){
				paramRow.put(key, params.get("docodt"));
			}else if("DONUM".equals(key)){
				paramRow.put(key, params.get("donum"));
			}else if("DONAM".equals(key)){
				paramRow.put(key, params.get("donam"));
			}else if("DOREC".equals(key)){
				paramRow.put(key, params.get("dorec"));
			}else if("DOCNTT".equals(key)){
				paramRow.put(key, params.get("docntt"));
			}else if("DOAMT".equals(key)){
				paramRow.put(key, params.get("doamt"));
			}else{
				paramRow.put(key, params.get(key));
			}
		}
		
		rfcParam.put("IM_DONA", paramRow);

		return webEssRcv.callRfcFunction("ZHR_RFC_PY_003_FILL_POP4", rfcParam, null, request);
	}
	
	@RequestMapping(value = "callPY003OninputPop4.do")
	public @ResponseBody Map<?, ?> callPY003OninputPop4(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");

		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> rfcParamList = new HashMap<String, Object>();
		
		String donationKeySet[] = Constant.donationKeySet;
		
		rfcParam.put("IM_PERNR"   , user.getEmpNo());
		rfcParam.put("IM_EVENT_ID", params.get("imEventId"));
		rfcParam.put("IM_ZYEAR"   , params.get("imZyear"));
		rfcParam.put("IM_REGNO"   , params.get("imRegno"));
		
		int paramCnt = Integer.parseInt((String)params.get("paramCnt"));
		ArrayList<HashMap<String, Object>> itDonaList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> itDonaRow;
		
		if(paramCnt == 1){
			itDonaRow = new HashMap<String, Object>();
			
			for(String key : donationKeySet){
				if("KDSVH".equals(key) || "SUBTY".equals(key)){
					itDonaRow.put(key, params.get("kdsvh"));
				}else if("DNAME".equals(key) || "REGNO".equals(key)){
					itDonaRow.put(key, params.get("dname"));
				}else if("REGNOT".equals(key)){
					itDonaRow.put(key, params.get("regnot"));
				}else if("DOCOD".equals(key)){
					itDonaRow.put(key, params.get("docod"));
				}else if("DOCODT".equals(key)){
					itDonaRow.put(key, params.get("docodt"));
				}else if("DONUM".equals(key)){
					itDonaRow.put(key, params.get("donum"));
				}else if("DONAM".equals(key)){
					itDonaRow.put(key, params.get("donam"));
				}else if("DOREC".equals(key)){
					itDonaRow.put(key, params.get("dorec"));
				}else if("DOCNTT".equals(key)){
					itDonaRow.put(key, params.get("docntt"));
				}else if("DOAMT".equals(key)){
					itDonaRow.put(key, params.get("doamt"));
				}else{
					itDonaRow.put(key, params.get(key));
				}
			}
			itDonaList.add(itDonaRow);
		}else if(paramCnt > 1){
			for(int i = 0 ; i < paramCnt ; i++){
				itDonaRow = new HashMap<String, Object>();
				
				for(String key : donationKeySet){
					if("KDSVH".equals(key) || "SUBTY".equals(key)){
						itDonaRow.put(key, request.getParameterValues("kdsvh")[i]);
					}else if("DNAME".equals(key) || "REGNO".equals(key)){
						itDonaRow.put(key, request.getParameterValues("dname")[i]);
					}else if("REGNOT".equals(key)){
						itDonaRow.put(key, request.getParameterValues("regnot")[i]);
					}else if("DOCOD".equals(key)){
						itDonaRow.put(key, request.getParameterValues("docod")[i]);
					}else if("DOCODT".equals(key)){
						itDonaRow.put(key, request.getParameterValues("docodt")[i]);
					}else if("DONUM".equals(key)){
						itDonaRow.put(key, request.getParameterValues("donum")[i]);
					}else if("DONAM".equals(key)){
						itDonaRow.put(key, request.getParameterValues("donam")[i]);
					}else if("DOREC".equals(key)){
						itDonaRow.put(key, request.getParameterValues("dorec")[i]);
					}else if("DOCNTT".equals(key)){
						itDonaRow.put(key, request.getParameterValues("docntt")[i]);
					}else if("DOAMT".equals(key)){
						itDonaRow.put(key, request.getParameterValues("doamt")[i]);
					}else{
						itDonaRow.put(key, request.getParameterValues(key)[i]);
					}
				}
				itDonaList.add(itDonaRow);
			}
		}
		
		rfcParamList.put("IT_DONA", itDonaList);
		
		HashMap<String, Object> paramRow = new HashMap<String, Object>();
		
		for(String key : Constant.taxKeySet){
			paramRow.put(key, params.get("exTax_"+key));
		}
		
		rfcParam.put("IM_TAX", paramRow);
		
		if(params.containsKey("itDelLineCnt")){
			int itDelLineCnt = Integer.parseInt((String)params.get("itDelLineCnt"));
			ArrayList<HashMap<String, Object>> itDelLineList = new ArrayList<HashMap<String, Object>>();
			HashMap<String, Object> itDelLineRow;
			
			if(itDelLineCnt == 1){
				itDelLineRow = new HashMap<String, Object>();
				itDelLineRow.put("SEQ", params.get("itDelLine_SEQ"));
				itDelLineList.add(itDelLineRow);
			}else{
				for(int i = 0 ; i < itDelLineCnt ; i++){
					itDelLineRow = new HashMap<String, Object>();
					itDelLineRow.put("SEQ", request.getParameterValues("itDelLine_SEQ")[i]);
					itDelLineList.add(itDelLineRow);
				}
			}
			
			rfcParamList.put("IT_DEL_LINE", itDelLineList);
		}

		return webEssRcv.callRfcFunction("ZHR_RFC_PY_003_ONINPUT_POP4", rfcParam, rfcParamList, request);
	}
	
	@RequestMapping(value = "pensionPop.do")
	public ModelAndView pensionPop(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/webess/payMng/pensionPop");

		int etRetpeCnt = Integer.parseInt((String)params.get("etRetpeCnt"));
		ArrayList<HashMap<String, Object>> etRetpeList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> etRetpeRow;

		String[] pensionKeySet = Constant.pensionKeySet;

		if(etRetpeCnt == 1){
			etRetpeRow = new HashMap<String, Object>();
			
			for(String key : pensionKeySet){
				etRetpeRow.put(key, params.get("etRetpe_"+key));
			}
			
			etRetpeList.add(etRetpeRow);
		}else if(etRetpeCnt > 1){
			for(int i = 0 ; i < etRetpeCnt ; i++){
				etRetpeRow = new HashMap<String, Object>();
				for(String key : pensionKeySet){
					etRetpeRow.put(key, request.getParameterValues("etRetpe_"+key)[i]);
				}
				etRetpeList.add(etRetpeRow);
			}
		}

		int etBankCnt = Integer.parseInt((String)params.get("etBankCnt"));
		ArrayList<HashMap<String, Object>> etBankList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> etBankRow;

		if(etBankCnt == 1){
			etBankRow = new HashMap<String, Object>();
			
			etBankRow.put("KEY", params.get("etBankKey"));
			etBankRow.put("VALUE", params.get("etBankValue"));
			
			etBankList.add(etBankRow);
		}else if(etBankCnt > 1){
			for(int i = 0 ; i < etBankCnt ; i++){
				etBankRow = new HashMap<String, Object>();
				
				etBankRow.put("KEY"  , request.getParameterValues("etBankKey")[i]);
				etBankRow.put("VALUE", request.getParameterValues("etBankValue")[i]);
				
				etBankList.add(etBankRow);
			}
		}

		HashMap<String, Object> taxRow = new HashMap<String, Object>();

		String[] taxKeySet = Constant.taxKeySet;

		for(String key : taxKeySet){
			taxRow.put(key, params.get("exTax_"+key));
		}

		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		rfcParam.put("IM_SUBTY", "E1");

		Map<? ,?> resultCode = webEssRcv.callRfcFunction("ZHR_RFC_PY_003_GET_LISTBOX7", rfcParam, null, request);

		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		resultMap.put("ET_RETPE", etRetpeList);
		resultMap.put("EX_TAX"  , taxRow);
		resultMap.put("ET_SLIST", resultCode.get("ET_SLIST"));
		resultMap.put("ET_BANK" , etBankList);

		mav.addObject("resultMap"    , resultMap);
		mav.addObject("pensionKeySet", pensionKeySet);
		mav.addObject("taxKeySet"    , taxKeySet);
		mav.addObject("params"       , params);

		return mav;
	}
	
	@RequestMapping(value = "callPY003OninputPop10.do")
	public @ResponseBody Map<?, ?> callPY003OninputPop10(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");

		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> rfcParamList = new HashMap<String, Object>();
		
		String pensionKeySet[] = Constant.pensionKeySet;
		
		rfcParam.put("IM_PERNR"   , user.getEmpNo());
		rfcParam.put("IM_EVENT_ID", params.get("imEventId"));
		rfcParam.put("IM_ZYEAR"   , params.get("imZyear"));
		rfcParam.put("IM_REGNO"   , params.get("imRegno"));
		rfcParam.put("IM_BEGDA"   , params.get("imBegda"));
		rfcParam.put("IM_ENDDA"   , params.get("imEndda"));
		
		int paramCnt = Integer.parseInt((String)params.get("paramCnt"));
		ArrayList<HashMap<String, Object>> itRetpeList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> itRetpeRow;
		
		if(paramCnt == 1){
			itRetpeRow = new HashMap<String, Object>();
			for(String key : pensionKeySet){
				if("ET_PNSTY".equals(key)){
					itRetpeRow.put(key, params.get("etPnsty"));
				}else if("ET_FINCO".equals(key)){
					itRetpeRow.put(key, params.get("etFinco"));
				}else if("ET_ACCNO".equals(key)){
					itRetpeRow.put(key, params.get("etAccno"));
				}else if("ETNAM".equals(key)){
					itRetpeRow.put(key, params.get("etnam"));
				}else if("ET_PREIN".equals(key)){
					itRetpeRow.put(key, params.get("etPrein"));
				}else{
					itRetpeRow.put(key, params.get(key));
				}
			}
			itRetpeList.add(itRetpeRow);
		}else if(paramCnt > 1){
			for(int i = 0 ; i < paramCnt ; i++){
				itRetpeRow = new HashMap<String, Object>();
				for(String key : pensionKeySet){
					if("ET_PNSTY".equals(key)){
						itRetpeRow.put(key, request.getParameterValues("etPnsty")[i]);
					}else if("ET_FINCO".equals(key)){
						itRetpeRow.put(key, request.getParameterValues("etFinco")[i]);
					}else if("ET_ACCNO".equals(key)){
						itRetpeRow.put(key, request.getParameterValues("etAccno")[i]);
					}else if("ETNAM".equals(key)){
						itRetpeRow.put(key, request.getParameterValues("etnam")[i]);
					}else if("ET_PREIN".equals(key)){
						itRetpeRow.put(key, request.getParameterValues("etPrein")[i]);
					}else{
						itRetpeRow.put(key, request.getParameterValues(key)[i]);
					}
				}
				itRetpeList.add(itRetpeRow);
			}
		}
		
		rfcParamList.put("IT_RETPE", itRetpeList);
		
		HashMap<String, Object> paramRow = new HashMap<String, Object>();
		
		for(String key : Constant.taxKeySet){
			paramRow.put(key, params.get("exTax_"+key));
		}
		
		rfcParam.put("IM_TAX", paramRow);
		
		if(params.containsKey("itDelLineCnt")){
			int itDelLineCnt = Integer.parseInt((String)params.get("itDelLineCnt"));
			ArrayList<HashMap<String, Object>> itDelLineList = new ArrayList<HashMap<String, Object>>();
			HashMap<String, Object> itDelLineRow;
			
			if(itDelLineCnt == 1){
				itDelLineRow = new HashMap<String, Object>();
				itDelLineRow.put("SEQ", params.get("itDelLine_SEQ"));
				itDelLineList.add(itDelLineRow);
			}else{
				for(int i = 0 ; i < itDelLineCnt ; i++){
					itDelLineRow = new HashMap<String, Object>();
					itDelLineRow.put("SEQ", request.getParameterValues("itDelLine_SEQ")[i]);
					itDelLineList.add(itDelLineRow);
				}
			}
			
			rfcParamList.put("IT_DEL_LINE", itDelLineList);
		}

		return webEssRcv.callRfcFunction("ZHR_RFC_PY_003_ONINPUT_POP10", rfcParam, rfcParamList, request);
	}

	@RequestMapping(value = "monthlyRentPop.do")
	public ModelAndView monthlyRentPop(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/webess/payMng/monthlyRentPop");

		int etRentCnt = Integer.parseInt((String)params.get("etRentCnt"));
		ArrayList<HashMap<String, Object>> etRentList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> etRentRow;

		String[] rentKeySet = Constant.rentKeySet;

		if(etRentCnt == 1){
			etRentRow = new HashMap<String, Object>();
			
			for(String key : rentKeySet){
				etRentRow.put(key, params.get("etRent_"+key));
			}
			
			etRentList.add(etRentRow);
		}else if(etRentCnt > 1){
			for(int i = 0 ; i < etRentCnt ; i++){
				etRentRow = new HashMap<String, Object>();
				for(String key : rentKeySet){
					etRentRow.put(key, request.getParameterValues("etRent_"+key)[i]);
				}
				etRentList.add(etRentRow);
			}
		}

		HashMap<String, Object> taxRow = new HashMap<String, Object>();

		String[] taxKeySet = Constant.taxKeySet;

		for(String key : taxKeySet){
			taxRow.put(key, params.get("exTax_"+key));
		}
		
		int etPnstyCnt = Integer.parseInt((String)params.get("etPnstyCnt"));
		ArrayList<HashMap<String, Object>> etPnstyList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> etPnstyRow;

		if(etPnstyCnt == 1){
			etPnstyRow = new HashMap<String, Object>();
			
			etPnstyRow.put("KEY", params.get("etPnstyKey"));
			etPnstyRow.put("VALUE", params.get("etPnstyValue"));
			
			etPnstyList.add(etPnstyRow);
		}else if(etPnstyCnt > 1){
			for(int i = 0 ; i < etPnstyCnt ; i++){
				etPnstyRow = new HashMap<String, Object>();
				
				etPnstyRow.put("KEY"  , request.getParameterValues("etPnstyKey")[i]);
				etPnstyRow.put("VALUE", request.getParameterValues("etPnstyValue")[i]);
				
				etPnstyList.add(etPnstyRow);
			}
		}
		
		int etHoutyCnt = Integer.parseInt((String)params.get("etHoutyCnt"));
		ArrayList<HashMap<String, Object>> etHoutyList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> etHoutyRow;

		if(etHoutyCnt == 1){
			etHoutyRow = new HashMap<String, Object>();
			
			etHoutyRow.put("KEY", params.get("etHoutyKey"));
			etHoutyRow.put("VALUE", params.get("etHoutyValue"));
			
			etHoutyList.add(etHoutyRow);
		}else if(etHoutyCnt > 1){
			for(int i = 0 ; i < etHoutyCnt ; i++){
				etHoutyRow = new HashMap<String, Object>();
				
				etHoutyRow.put("KEY"  , request.getParameterValues("etHoutyKey")[i]);
				etHoutyRow.put("VALUE", request.getParameterValues("etHoutyValue")[i]);
				
				etHoutyList.add(etHoutyRow);
			}
		}
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		resultMap.put("ET_RENT" , etRentList);
		resultMap.put("EX_TAX"  , taxRow);
		resultMap.put("ET_PNSTY", etPnstyList);
		resultMap.put("ET_HOUTY", etHoutyList);

		mav.addObject("resultMap" , resultMap);
		mav.addObject("rentKeySet", rentKeySet);
		mav.addObject("taxKeySet" , taxKeySet);
		mav.addObject("params"    , params);

		return mav;
	}
	
	@RequestMapping(value = "borrowPop.do")
	public ModelAndView borrowPop(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/webess/payMng/borrowPop");

		int etLoane5Cnt = Integer.parseInt((String)params.get("etLoane5Cnt"));
		int etLoane6Cnt = Integer.parseInt((String)params.get("etLoane6Cnt"));
		ArrayList<HashMap<String, Object>> etLoane5List = new ArrayList<HashMap<String, Object>>();
		ArrayList<HashMap<String, Object>> etLoane6List = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> etLoane5Row;
		HashMap<String, Object> etLoane6Row;

		String[] rentKeySet = Constant.rentKeySet;

		if(etLoane5Cnt == 1){
			etLoane5Row = new HashMap<String, Object>();
			
			for(String key : rentKeySet){
				etLoane5Row.put(key, params.get("etLoane5_"+key));
			}
			
			etLoane5List.add(etLoane5Row);
		}else if(etLoane5Cnt > 1){
			for(int i = 0 ; i < etLoane5Cnt ; i++){
				etLoane5Row = new HashMap<String, Object>();
				for(String key : rentKeySet){
					etLoane5Row.put(key, request.getParameterValues("etLoane5_"+key)[i]);
				}
				etLoane5List.add(etLoane5Row);
			}
		}
		
		if(etLoane6Cnt == 1){
			etLoane6Row = new HashMap<String, Object>();
			
			for(String key : rentKeySet){
				etLoane6Row.put(key, params.get("etLoane6_"+key));
			}
			
			etLoane6List.add(etLoane6Row);
		}else if(etLoane6Cnt > 1){
			for(int i = 0 ; i < etLoane6Cnt ; i++){
				etLoane6Row = new HashMap<String, Object>();
				for(String key : rentKeySet){
					etLoane6Row.put(key, request.getParameterValues("etLoane6_"+key)[i]);
				}
				etLoane6List.add(etLoane6Row);
			}
		}

		HashMap<String, Object> taxRow = new HashMap<String, Object>();

		String[] taxKeySet = Constant.taxKeySet;

		for(String key : taxKeySet){
			taxRow.put(key, params.get("exTax_"+key));
		}
		
		int etPnstyCnt = Integer.parseInt((String)params.get("etPnstyCnt"));
		ArrayList<HashMap<String, Object>> etPnstyList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> etPnstyRow;

		if(etPnstyCnt == 1){
			etPnstyRow = new HashMap<String, Object>();
			
			etPnstyRow.put("KEY", params.get("etPnstyKey"));
			etPnstyRow.put("VALUE", params.get("etPnstyValue"));
			
			etPnstyList.add(etPnstyRow);
		}else if(etPnstyCnt > 1){
			for(int i = 0 ; i < etPnstyCnt ; i++){
				etPnstyRow = new HashMap<String, Object>();
				
				etPnstyRow.put("KEY"  , request.getParameterValues("etPnstyKey")[i]);
				etPnstyRow.put("VALUE", request.getParameterValues("etPnstyValue")[i]);
				
				etPnstyList.add(etPnstyRow);
			}
		}
		
		int etHoutyCnt = Integer.parseInt((String)params.get("etHoutyCnt"));
		ArrayList<HashMap<String, Object>> etHoutyList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> etHoutyRow;

		if(etHoutyCnt == 1){
			etHoutyRow = new HashMap<String, Object>();
			
			etHoutyRow.put("KEY", params.get("etHoutyKey"));
			etHoutyRow.put("VALUE", params.get("etHoutyValue"));
			
			etHoutyList.add(etHoutyRow);
		}else if(etHoutyCnt > 1){
			for(int i = 0 ; i < etHoutyCnt ; i++){
				etHoutyRow = new HashMap<String, Object>();
				
				etHoutyRow.put("KEY"  , request.getParameterValues("etHoutyKey")[i]);
				etHoutyRow.put("VALUE", request.getParameterValues("etHoutyValue")[i]);
				
				etHoutyList.add(etHoutyRow);
			}
		}
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		resultMap.put("ET_LOANE5" , etLoane5List);
		resultMap.put("ET_LOANE6" , etLoane6List);
		resultMap.put("EX_TAX"  , taxRow);
		resultMap.put("ET_PNSTY", etPnstyList);
		resultMap.put("ET_HOUTY", etHoutyList);

		mav.addObject("resultMap" , resultMap);
		mav.addObject("rentKeySet", rentKeySet);
		mav.addObject("taxKeySet" , taxKeySet);
		mav.addObject("params"    , params);

		return mav;
	}
	
	@RequestMapping(value = "callPY003OninputPop11.do")
	public @ResponseBody Map<?, ?> callPY003OninputPop11(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");

		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> rfcParamList = new HashMap<String, Object>();

		String pensionKeySet[] = Constant.rentKeySet;

		rfcParam.put("IM_PERNR"   , user.getEmpNo());
		rfcParam.put("IM_EVENT_ID", params.get("imEventId"));
		rfcParam.put("IM_ZYEAR"   , params.get("imZyear"));
		rfcParam.put("IM_REGNO"   , params.get("imRegno"));
		rfcParam.put("IM_TOT"     , params.get("imTot"));

		int paramCnt = Integer.parseInt((String)params.get("paramCnt"));
		ArrayList<HashMap<String, Object>> itRentList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> itRentRow;

		if(paramCnt == 1){
			itRentRow = new HashMap<String, Object>();
			for(String key : pensionKeySet){
				if("ET_PNSTY".equals(key)){
					itRentRow.put(key, params.get("etPnsty"));
				}else if("ET_LDNAM".equals(key)){
					itRentRow.put(key, params.get("etLdnam"));
				}else if("ET_LDREG".equals(key)){
					itRentRow.put(key, params.get("etLdreg"));
				}else if("ET_ADDRE".equals(key)){
					itRentRow.put(key, params.get("etAddre"));
				}else if("HOUTY".equals(key)){
					itRentRow.put(key, params.get("houty"));
				}else if("HOSTX".equals(key)){
					itRentRow.put(key, params.get("hostx"));
				}else if("ET_RCBEG".equals(key)){
					itRentRow.put(key, params.get("etRcbeg"));
				}else if("ET_RCEND".equals(key)){
					itRentRow.put(key, params.get("etRcend"));
				}else if("ETNAM".equals(key)){
					itRentRow.put(key, params.get("etnam"));
				}else{
					itRentRow.put(key, params.get(key));
				}
			}
			itRentList.add(itRentRow);
		}else if(paramCnt > 1){
			for(int i = 0 ; i < paramCnt ; i++){
				itRentRow = new HashMap<String, Object>();
				for(String key : pensionKeySet){
					if("ET_PNSTY".equals(key)){
						itRentRow.put(key, request.getParameterValues("etPnsty")[i]);
					}else if("ET_LDNAM".equals(key)){
						itRentRow.put(key, request.getParameterValues("etLdnam")[i]);
					}else if("ET_LDREG".equals(key)){
						itRentRow.put(key, request.getParameterValues("etLdreg")[i]);
					}else if("ET_ADDRE".equals(key)){
						itRentRow.put(key, request.getParameterValues("etAddre")[i]);
					}else if("HOUTY".equals(key)){
						itRentRow.put(key, request.getParameterValues("houty")[i]);
					}else if("HOSTX".equals(key)){
						itRentRow.put(key, request.getParameterValues("hostx")[i]);
					}else if("ET_RCBEG".equals(key)){
						itRentRow.put(key, request.getParameterValues("etRcbeg")[i]);
					}else if("ET_RCEND".equals(key)){
						itRentRow.put(key, request.getParameterValues("etRcend")[i]);
					}else if("ETNAM".equals(key)){
						itRentRow.put(key, request.getParameterValues("etnam")[i]);
					}else{
						itRentRow.put(key, request.getParameterValues(key)[i]);
					}
				}
				itRentList.add(itRentRow);
			}
		}

		rfcParamList.put("IT_RENT", itRentList);

		HashMap<String, Object> paramRow = new HashMap<String, Object>();

		for(String key : Constant.taxKeySet){
			paramRow.put(key, params.get("exTax_"+key));
		}

		rfcParam.put("IM_TAX", paramRow);

		if(params.containsKey("itDelLineCnt")){
			int itDelLineCnt = Integer.parseInt((String)params.get("itDelLineCnt"));
			ArrayList<HashMap<String, Object>> itDelLineList = new ArrayList<HashMap<String, Object>>();
			HashMap<String, Object> itDelLineRow;
			
			if(itDelLineCnt == 1){
				itDelLineRow = new HashMap<String, Object>();
				itDelLineRow.put("SEQ", params.get("itDelLine_SEQ"));
				itDelLineList.add(itDelLineRow);
			}else{
				for(int i = 0 ; i < itDelLineCnt ; i++){
					itDelLineRow = new HashMap<String, Object>();
					itDelLineRow.put("SEQ", request.getParameterValues("itDelLine_SEQ")[i]);
					itDelLineList.add(itDelLineRow);
				}
			}
			
			rfcParamList.put("IT_DEL_LINE", itDelLineList);
		}

		return webEssRcv.callRfcFunction("ZHR_RFC_PY_003_ONINPUT_POP11", rfcParam, rfcParamList, request);
	}
	
	@RequestMapping(value = "callPY003OninputPop18.do")
	public @ResponseBody Map<?, ?> callPY003OninputPop18(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");

		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> rfcParamList = new HashMap<String, Object>();

		String pensionKeySet[] = Constant.rentKeySet;

		rfcParam.put("IM_PERNR"   , user.getEmpNo());
		rfcParam.put("IM_EVENT_ID", params.get("imEventId"));
		rfcParam.put("IM_ZYEAR"   , params.get("imZyear"));
		rfcParam.put("IM_REGNO"   , params.get("imRegno"));
		rfcParam.put("IM_TOT"     , params.get("imTot"));

		int paramCnt = Integer.parseInt((String)params.get("paramCnt"));
		ArrayList<HashMap<String, Object>> itRentList1 = new ArrayList<HashMap<String, Object>>();
		ArrayList<HashMap<String, Object>> itRentList2 = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> itRentRow1;
		HashMap<String, Object> itRentRow2;

		if(paramCnt == 1){
			itRentRow1 = new HashMap<String, Object>();
			itRentRow2 = new HashMap<String, Object>();
			for(String key : pensionKeySet){
				if("ET_PNSTY".equals(key)){
					itRentRow1.put(key, params.get("etPnsty5"));
				}else if("ET_LDNAM".equals(key)){
					itRentRow1.put(key, params.get("etLdnam5"));
					itRentRow2.put(key, params.get("etLdnam6"));
				}else if("ET_LDREG".equals(key)){
					itRentRow1.put(key, params.get("etLdreg5"));
					itRentRow2.put(key, params.get("etLdreg6"));
				}else if("ET_ADDRE".equals(key)){
					itRentRow1.put(key, params.get("etAddre5"));
				}else if("HOUTY".equals(key)){
					itRentRow1.put(key, params.get("houty5"));
				}else if("HOSTX".equals(key)){
					itRentRow1.put(key, params.get("hostx5"));
				}else if("ET_RCBEG".equals(key)){
					itRentRow1.put(key, params.get("etRcbeg5"));
					itRentRow2.put(key, params.get("etRcbeg6"));
				}else if("ET_RCEND".equals(key)){
					itRentRow1.put(key, params.get("etRcend5"));
					itRentRow2.put(key, params.get("etRcend6"));
				}else if("ETNAM".equals(key)){
					itRentRow1.put(key, params.get("etnam5"));
					itRentRow2.put(key, params.get("etnam6"));
				}else if("ET_INRAT".equals(key)){
					itRentRow2.put(key, params.get("etInrat6"));
				}else if("ETOAM".equals(key)){
					itRentRow2.put(key, params.get("etoam6"));
				}else{
					itRentRow1.put(key, params.get(key));
					itRentRow2.put(key, params.get(key));
				}
			}
			itRentList1.add(itRentRow1);
			itRentList2.add(itRentRow2);
		}else if(paramCnt > 1){
			for(int i = 0 ; i < paramCnt ; i++){
				itRentRow1 = new HashMap<String, Object>();
				itRentRow2 = new HashMap<String, Object>();
				for(String key : pensionKeySet){
					if("ET_PNSTY".equals(key)){
						itRentRow1.put(key, request.getParameterValues("etPnsty5")[i]);
					}else if("ET_LDNAM".equals(key)){
						itRentRow1.put(key, request.getParameterValues("etLdnam5")[i]);
						itRentRow2.put(key, request.getParameterValues("etLdnam6")[i]);
					}else if("ET_LDREG".equals(key)){
						itRentRow1.put(key, request.getParameterValues("etLdreg5")[i]);
						itRentRow2.put(key, request.getParameterValues("etLdreg6")[i]);
					}else if("ET_ADDRE".equals(key)){
						itRentRow1.put(key, request.getParameterValues("etAddre5")[i]);
					}else if("HOUTY".equals(key)){
						itRentRow1.put(key, request.getParameterValues("houty5")[i]);
					}else if("HOSTX".equals(key)){
						itRentRow1.put(key, request.getParameterValues("hostx5")[i]);
					}else if("ET_RCBEG".equals(key)){
						itRentRow1.put(key, request.getParameterValues("etRcbeg5")[i]);
						itRentRow2.put(key, request.getParameterValues("etRcbeg6")[i]);
					}else if("ET_RCEND".equals(key)){
						itRentRow1.put(key, request.getParameterValues("etRcend5")[i]);
						itRentRow2.put(key, request.getParameterValues("etRcend6")[i]);
					}else if("ETNAM".equals(key)){
						itRentRow1.put(key, request.getParameterValues("etnam5")[i]);
						itRentRow2.put(key, request.getParameterValues("etnam6")[i]);
					}else if("ET_INRAT".equals(key)){
						itRentRow2.put(key, request.getParameterValues("etInrat6")[i]);
					}else if("ETOAM".equals(key)){
						itRentRow2.put(key, request.getParameterValues("etoam6")[i]);
					}else{
						itRentRow1.put(key, request.getParameterValues(key)[i]);
						itRentRow2.put(key, request.getParameterValues(key)[i]);
					}
				}
				itRentList1.add(itRentRow1);
				itRentList2.add(itRentRow2);
			}
		}

		rfcParamList.put("IT_LOANE5", itRentList1);
		rfcParamList.put("IT_LOANE6", itRentList2);

		HashMap<String, Object> paramRow = new HashMap<String, Object>();

		for(String key : Constant.taxKeySet){
			paramRow.put(key, params.get("exTax_"+key));
		}

		rfcParam.put("IM_TAX", paramRow);

		if(params.containsKey("itDelLineCnt")){
			int itDelLineCnt = Integer.parseInt((String)params.get("itDelLineCnt"));
			ArrayList<HashMap<String, Object>> itDelLineList = new ArrayList<HashMap<String, Object>>();
			HashMap<String, Object> itDelLineRow;
			
			if(itDelLineCnt == 1){
				itDelLineRow = new HashMap<String, Object>();
				itDelLineRow.put("SEQ", params.get("itDelLine_SEQ"));
				itDelLineList.add(itDelLineRow);
			}else{
				for(int i = 0 ; i < itDelLineCnt ; i++){
					itDelLineRow = new HashMap<String, Object>();
					itDelLineRow.put("SEQ", request.getParameterValues("itDelLine_SEQ")[i]);
					itDelLineList.add(itDelLineRow);
				}
			}
			
			rfcParamList.put("IT_DEL_LINE", itDelLineList);
		}

		return webEssRcv.callRfcFunction("ZHR_RFC_PY_003_ONINPUT_POP18", rfcParam, rfcParamList, request);
	}
	
	@RequestMapping(value = "callPY003Oninput.do")
	public @ResponseBody Map<?, ?> callPY003Oninput(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");

		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> rfcParamList = new HashMap<String, Object>();

		rfcParam.put("IM_PERNR"    , user.getEmpNo());
		rfcParam.put("IM_YEAR"     , params.get("imYear"));
		rfcParam.put("IM_PASS"     , params.get("imPass"));
		rfcParam.put("IM_EVENT_ID" , params.get("imEventId"));
		rfcParam.put("IM_ZYEAR"    , params.get("imZyear"));
		rfcParam.put("IM_BEGDA"    , params.get("imBegda"));
		rfcParam.put("IM_ENDDA"    , params.get("imEndda"));
		rfcParam.put("IM_DISABLED3", params.get("imDisabled3"));
		
		int perFuncCnt = Integer.parseInt((String)params.get("perFuncCnt"));
		ArrayList<HashMap<String, Object>> itPerFuncList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> itPerFuncRow;
		
		String[] perFuncKeySet = Constant.perFuncKeySet;
		
		if(perFuncCnt == 1){
			itPerFuncRow = new HashMap<String, Object>();
			for(String key : perFuncKeySet){
				if("DPTID".equals(key)){
					itPerFuncRow.put(key, params.get("basicDeduction"));
				}else if("ZSEVENTY".equals(key)){
					itPerFuncRow.put(key, params.get("seniors"));
				}else if("ZHNDEE".equals(key)){
					itPerFuncRow.put(key, params.get("disabled"));
				}else if("HNDCD".equals(key)){
					itPerFuncRow.put(key, params.get("disabledCode"));
				}else if("KDBSL".equals(key)){
					itPerFuncRow.put(key, params.get("childCntCode"));
				}else if("SIGPR".equals(key)){
					itPerFuncRow.put(key, params.get("singleParent"));
				}else if("WOMEE".equals(key)){
					itPerFuncRow.put(key, params.get("woman"));
				}else if("ZINSID".equals(key)){
					itPerFuncRow.put(key, params.get("premium"));
				}else if("ZMEDID".equals(key)){
					itPerFuncRow.put(key, params.get("medical"));
				}else if("ZEDUID".equals(key)){
					itPerFuncRow.put(key, params.get("education"));
				}else if("ZCREID".equals(key)){
					itPerFuncRow.put(key, params.get("creditCard"));
				}else if("ZCONID".equals(key)){
					itPerFuncRow.put(key, params.get("donation"));
				}else{
					itPerFuncRow.put(key, params.get(key));
				}
			}
			itPerFuncList.add(itPerFuncRow);
		}else if(perFuncCnt > 1){
			for(int i = 0 ; i < perFuncCnt ; i++){
				itPerFuncRow = new HashMap<String, Object>();
				for(String key : perFuncKeySet){
					if("DPTID".equals(key)){
						itPerFuncRow.put(key, request.getParameterValues("basicDeduction")[i]);
					}else if("ZSEVENTY".equals(key)){
						itPerFuncRow.put(key, request.getParameterValues("seniors")[i]);
					}else if("ZHNDEE".equals(key)){
						itPerFuncRow.put(key, request.getParameterValues("disabled")[i]);
					}else if("HNDCD".equals(key)){
						itPerFuncRow.put(key, request.getParameterValues("disabledCode")[i]);
					}else if("KDBSL".equals(key)){
						itPerFuncRow.put(key, request.getParameterValues("childCntCode")[i]);
					}else if("SIGPR".equals(key)){
						itPerFuncRow.put(key, request.getParameterValues("singleParent")[i]);
					}else if("WOMEE".equals(key)){
						itPerFuncRow.put(key, request.getParameterValues("woman")[i]);
					}else if("ZINSID".equals(key)){
						itPerFuncRow.put(key, request.getParameterValues("premium")[i]);
					}else if("ZMEDID".equals(key)){
						itPerFuncRow.put(key, request.getParameterValues("medical")[i]);
					}else if("ZEDUID".equals(key)){
						itPerFuncRow.put(key, request.getParameterValues("education")[i]);
					}else if("ZCREID".equals(key)){
						itPerFuncRow.put(key, request.getParameterValues("creditCard")[i]);
					}else if("ZCONID".equals(key)){
						itPerFuncRow.put(key, request.getParameterValues("donation")[i]);
					}else{
						itPerFuncRow.put(key, request.getParameterValues(key)[i]);
					}
				}
				itPerFuncList.add(itPerFuncRow);
			}
		}
		
		rfcParamList.put("IT_PERSON_FUNCTION", itPerFuncList);
		
		HashMap<String, Object> paramRow = new HashMap<String, Object>();

		for(String key : Constant.taxKeySet){
			if("HSHLD".equals(key) || "REPAY".equals(key) || "INDPL".equals(key) || "COMST".equals(key) || "SMBFI".equals(key) || "INDIF".equals(key)){
				paramRow.put(key, params.get("taxParam_"+key.toLowerCase()));
			}else{
				paramRow.put(key, params.get("exTax_"+key));
			}
		}

		rfcParam.put("IM_TAX", paramRow);

		return webEssRcv.callRfcFunction("ZHR_RFC_PY_003_ONINPUT", rfcParam, rfcParamList, request);
	}
	
	@RequestMapping(value = "writingTipPop.do")
	public ModelAndView writingTipPop(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/webess/payMng/writingTipPop");
		mav.addObject("params", params);

		return mav;
	}
	
	
	@RequestMapping(value = "settlementDocumentView.do")
	public ModelAndView settlementDocumentView(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/webess/payMng/settlementDocumentView");
		
		User user = (User) getRequestAttribute("ikep.user");
		
		params.put("toDay", DateUtil.getToday("yyyyMMdd"));
		
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		
		rfcParam.put("IM_PERNR", user.getEmpNo());
		
		mav.addObject("resultMap", webEssRcv.callRfcFunction("ZHR_RFC_PY_006_ONINIT", rfcParam, null, request));
		mav.addObject("params"   , params);

		return mav;
	}
}
