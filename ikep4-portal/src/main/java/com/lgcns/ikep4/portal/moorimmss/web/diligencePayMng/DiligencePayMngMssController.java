package com.lgcns.ikep4.portal.moorimmss.web.diligencePayMng;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.comomon.Constant;
import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.portal.moorimess.web.rfcCache.service.RfcCacheService;
import com.lgcns.ikep4.portal.util.CacheRefreshUtil;
import com.lgcns.ikep4.portal.util.PagingUtils;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.jco.WebESSRcvRFC;
import com.lgcns.ikep4.util.lang.DateUtil;

@Controller
@RequestMapping(value="/portal/moorimmss/diligencePayMng")
public class DiligencePayMngMssController extends BaseController {

	@Autowired
	private WebESSRcvRFC webEssRcv;

	@Autowired
	private RfcCacheService rfcCacheService;

	@RequestMapping(value = "/personInfoDetailPop.do")
	public ModelAndView personInfoDetail(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {
		
		ModelAndView mav = new ModelAndView("/portal/webmss/diligencePayMng/personInfoDetailPop");
		
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		
		rfcParam.put("IM_PERNR", params.get("selEmpNo"));
		
		Map<?, ?> resultHeader = webEssRcv.callRfcFunction("ZHR_RFC_PA_001_GET_DATA", rfcParam, null, request);

		Map<?, ?> resultCode = webEssRcv.callRfcFunction("ZHR_RFC_PA_021_GET_LISTBOX", rfcParam, null, request);

		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_PA_021_GET_DATA", rfcParam, null, request);

		mav.addObject("resultHeader", resultHeader);
		mav.addObject("resultCode"  , resultCode);
		mav.addObject("result"      , result);

		return mav;
	}

	@RequestMapping(value = "/monthDilegenceView.do")
	public ModelAndView monthDilegenceView(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {
		
		ModelAndView mav = new ModelAndView("/portal/webmss/diligencePayMng/monthDilegenceView");
		
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		
		if(!params.containsKey("imYear")){
			params.put("imYear", DateUtil.getToday("yyyy"));
		}
		
		if(!params.containsKey("imMonth")){
			params.put("imMonth", DateUtil.getToday("MM"));
		}
		
		rfcParam.put("IM_PERNR", params.get("selEmpNo"));
		rfcParam.put("IM_YEAR" , params.get("imYear"));
		rfcParam.put("IM_MONTH", params.get("imMonth"));
		
		Map<?, ?> resultHeader = webEssRcv.callRfcFunction("ZHR_RFC_PA_001_GET_DATA", rfcParam, null, request);

		Map<?, ?> resultMapCode = webEssRcv.callRfcFunction("ZHR_RFC_PT_011_GET_LISTBOX", null, null, request);
		
		Map<?, ?> resultMapList = webEssRcv.callRfcFunction("ZHR_RFC_PT_011_GET_DATA", rfcParam, null, request);
		
		mav.addObject("resultHeader" , resultHeader);
		mav.addObject("resultMapCode", resultMapCode);
		mav.addObject("resultMapList", resultMapList);
		mav.addObject("params"       , params);
		
		return mav;
	}
	
	@RequestMapping(value = "/yearMonthPaidHolidayList.do")
	public ModelAndView yearMonthPaidHolidayList(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {
		
		ModelAndView mav = new ModelAndView("/portal/webmss/diligencePayMng/yearMonthPaidHolidayList");
		
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		
		if(!params.containsKey("imYear")){
			params.put("imYear", DateUtil.getToday("yyyy"));
		}
		
		rfcParam.put("IM_PERNR", params.get("selEmpNo"));
		rfcParam.put("IM_YEAR" , params.get("imYear"));
		
		Map<?, ?> resultHeader = webEssRcv.callRfcFunction("ZHR_RFC_PA_001_GET_DATA", rfcParam, null, request);

		Map<?, ?> resultMapList = webEssRcv.callRfcFunction("ZHR_RFC_PT_012_ONINIT", rfcParam, null, request);
		
		rfcParam.put("IM_BTRTL"  , resultMapList.get("EX_BTRTL"));
		rfcParam.put("IM_YEAR_GB", resultMapList.get("EX_YEAR_GB"));
		
		Map<?, ?> resultMapCode = webEssRcv.callRfcFunction("ZHR_RFC_PT_010_GET_LISTBOX", rfcParam, null, request);
		
		mav.addObject("resultHeader" , resultHeader);
		mav.addObject("resultMapList", resultMapList);
		mav.addObject("resultMapCode", resultMapCode);
		mav.addObject("params"       , params);
		
		return mav;
	}
	
	@RequestMapping(value = "/payEarningList.do")
	public ModelAndView payEarningList(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {
		
		ModelAndView mav = new ModelAndView("/portal/webmss/diligencePayMng/payEarningList");
		
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		
		if(!params.containsKey("imYear")){
			params.put("imYear", DateUtil.getToday("yyyy"));
		}
		
		rfcParam.put("IM_PERNR", params.get("selEmpNo"));
		rfcParam.put("IM_YEAR" , params.get("imYear"));
		
		Map<?, ?> resultHeader = webEssRcv.callRfcFunction("ZHR_RFC_PA_001_GET_DATA", rfcParam, null, request);

		Map<?, ?> resultMapList = webEssRcv.callRfcFunction("ZHR_RFC_PY_004_GET_DATA", rfcParam, null, request);
		
		Map<?, ?> resultMapCode = webEssRcv.callRfcFunction("ZHR_RFC_PY_004_GET_LISTBOX", rfcParam, null, request);
		
		mav.addObject("resultHeader" , resultHeader);
		mav.addObject("resultMapList", resultMapList);
		mav.addObject("resultMapCode", resultMapCode);
		mav.addObject("params"       , params);
		
		return mav;
	}
	
	@RequestMapping(value = "/holidayUseList.do")
	public ModelAndView holidayUseList(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {
		
		ModelAndView mav = new ModelAndView("/portal/webmss/diligencePayMng/holidayUseList");
		
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		
		if(!params.containsKey("imYear")){
			params.put("imYear", DateUtil.getToday("yyyy"));
		}
		
		if(!params.containsKey("viewType")){
			params.put("viewType", "A");
		}
		
		rfcParam.put("IM_YEAR" , params.get("imYear"));
		rfcParam.put("IM_PERNR", params.get("selEmpNo"));
		rfcParam.put("IM_ENAME", params.get("imEname"));
		rfcParam.put("IM_TEAM" , params.get("imTeam"));
		
		Map<?, ?> resultMap = webEssRcv.callRfcFunction("ZHR_RFC_PT_013_GET_DATA", rfcParam, null, request);
		
		mav.addObject("resultMap", resultMap);
		mav.addObject("params"   , params);
		
		return mav;
	}

	@RequestMapping(value = "/deadlineList.do")
	public ModelAndView deadlineList(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {
		
		ModelAndView mav = new ModelAndView("/portal/webmss/diligencePayMng/deadlineList");

		User user = (User) getRequestAttribute("ikep.user");
		
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		
		if(!params.containsKey("imDatum")){
			params.put("imDatum", DateUtil.getToday("yyyy-MM-dd"));
		}

		rfcParam.put("IM_ADMIN", user.getEmpNo());
		rfcParam.put("IM_DATUM", params.get("imDatum"));
		rfcParam.put("IM_FIRST", params.get("imFirst"));

		Map<?, ?> resultMap = webEssRcv.callRfcFunction("ZHR_RFC_PT_016_ONINIT", rfcParam, null, request);
		
		mav.addObject("resultMap", resultMap);
		mav.addObject("keySet"   , Constant.deadlineKeySet);
		mav.addObject("params"   , params);
		
		return mav;
	}

	@RequestMapping(value = "/deadlineProcess.do")
	public @ResponseBody Map<?, ?> deadlineProcess(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {
		
		String[] keySet = Constant.deadlineKeySet;
		
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> rfcParamList = new HashMap<String, Object>();
		
		ArrayList<HashMap<String, Object>> itList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> itRow = null;
		
		rfcParam.put("IM_DATUM", params.get("imDatum"));
		
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
		
		rfcParamList.put("IT_LIST", itList);

		Map<?, ?> resultMap = webEssRcv.callRfcFunction("ZHR_RFC_PT_016_PROCESS_CLOSE", rfcParam, rfcParamList, request);
		
		return resultMap;
	}
	
	@RequestMapping(value = "/assessmentsList.do")
	public ModelAndView assessmentsList(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {
		
		ModelAndView mav = new ModelAndView("/portal/webmss/diligencePayMng/assessmentsList");
	
		User user = (User) getRequestAttribute("ikep.user");
		
		String[] keySet = Constant.assessmentsKeySet;
		
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> rfcParamList = new HashMap<String, Object>();
		
		if(!params.containsKey("startDate")){
			params.put("startDate", DateUtil.getToday("yyyy-MM-dd"));
		}
		
		int etMessageCnt = params.containsKey("etMessageCnt") ? Integer.parseInt((String)params.get("etMessageCnt")) : 0;
		
		ArrayList<HashMap<String, Object>> itMessageList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> itMessageRow = null;
		
		if(etMessageCnt == 1){
			itMessageRow = new HashMap<String, Object>();
			for(String key : keySet){
				itMessageRow.put(key, params.get("etMessage_"+key));
			}
			itMessageList.add(itMessageRow);
		}else if(etMessageCnt > 1){
			for(int i = 0 ; i < etMessageCnt ; i++){
				itMessageRow = new HashMap<String, Object>();
				for(String key : keySet){
					itMessageRow.put(key, request.getParameterValues("etMessage_"+key)[i]);
				}
				itMessageList.add(itMessageRow);
			}
		}
		
		if("true".equals((String)params.get("setEmpFlag"))){
			params.put("imPernr", params.get("selEmpNo"));
		}
		
		rfcParam.put("IM_BEGDA", params.get("startDate"));
		rfcParam.put("IM_FIRST", params.get("imFirst"));
		rfcParam.put("IM_ADMIN", user.getEmpNo());
		rfcParam.put("IM_PERNR", params.get("imPernr"));
		
		rfcParamList.put("IT_MESSAGE", itMessageList);

		Map<?, ?> resultMap = webEssRcv.callRfcFunction("ZHR_RFC_PT_025_ONINIT", rfcParam, rfcParamList, request);
		
		mav.addObject("resultMap", resultMap);
		mav.addObject("keySet"   , keySet);
		mav.addObject("params"   , params);
		
		return mav;
	}

	@RequestMapping(value = "/assessmentsProcess.do")
	public @ResponseBody Map<?, ?> assessmentsProcess(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {
		
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> rfcParamList = new HashMap<String, Object>();
		
		ArrayList<HashMap<String, Object>> itList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> itRow = null;
		
		int rowInfoCnt = Integer.parseInt((String) params.get("rowInfoCnt"));
		
		String[] keySet = Constant.assessmentsKeySet;
		
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
		
		rfcParam.put("IM_BEGDA", params.get("startDate"));
		rfcParam.put("IM_ENDDA", params.get("endDate"));
		
		rfcParamList.put("IT_LIST", itList);

		Map<?, ?> resultMap = webEssRcv.callRfcFunction("ZHR_RFC_PT_025_PROCESS_DILIGEN", rfcParam, rfcParamList, request);
		
		return resultMap;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/workHistoryList.do")
	public ModelAndView workHistoryList(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {
		
		ModelAndView mav = new ModelAndView("/portal/webmss/diligencePayMng/workHistoryList");

		User user = (User) getRequestAttribute("ikep.user");
		
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		
		String toDay = DateUtil.getToday("yyyy-MM-dd");
		
		if(!params.containsKey("startDate")){
			params.put("startDate", toDay);
		}
		
		if(!params.containsKey("endDate")){
			params.put("endDate", toDay);
		}
		
		if(!params.containsKey("viewType")){
			params.put("viewType", "BASIS");
		}
		
		params.put("imAdmin", user.getEmpNo());
		
		if("true".equals((String)params.get("setEmpFlag"))){
			params.put("imAdmin", params.get("selEmpNo"));
		}
		
		rfcParam.put("IM_ADMIN", params.get("imAdmin"));
		rfcParam.put("IM_BEGDA", params.get("startDate"));
		rfcParam.put("IM_ENDDA", params.get("endDate"));
		rfcParam.put("IM_ZEITY", params.get("imZeity"));
		rfcParam.put("IM_ORGEH", params.get("imOrgeh"));
		rfcParam.put("IM_SCHKZ", params.get("imSchkz"));
		rfcParam.put("IM_FIRST", params.get("imFirst"));

		Map<?, ?> resultMap = webEssRcv.callRfcFunction("ZHR_RFC_PT_027_ONINIT", rfcParam, null, request);
		
		ArrayList<HashMap<String, Object>> etPernr = (ArrayList<HashMap<String, Object>>) resultMap.get("ET_PERNR");
		HashMap<String, Object> pernrRow;
		
		ArrayList<HashMap<String, Object>> etCalendar = (ArrayList<HashMap<String, Object>>) resultMap.get("ET_CALENDAR");
		HashMap<String, Object> calendarRow;
		
		ArrayList<HashMap<String, Object>> etList = (ArrayList<HashMap<String, Object>>) resultMap.get("ET_LIST");
		HashMap<String, Object> listRow;
		
		ArrayList<HashMap<String, Object>> mergeList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> mergeRow;
		
		String aPernr = "";  //etPernr.PERNR
		String bPernr = "";  //etList.PERNR
		
		String aDate = "";  //etCalendar.DATE
		String bDate = "";  //etList.BEGDA
		
		for(int i = 0 ; i < etPernr.size() ; i++){
			mergeRow = new HashMap<String, Object>();
			
			pernrRow = etPernr.get(i);
			
			mergeRow.put("ENAME", pernrRow.get("ENAME"));
			mergeRow.put("ORGTX", pernrRow.get("ORGTX"));
			mergeRow.put("PLSTX", pernrRow.get("PLSTX"));
			mergeRow.put("RTEXT", pernrRow.get("RTEXT"));
			
			aPernr = (String)etPernr.get(i).get("PERNR");
			
			for(int j = 0 ; j < etCalendar.size() ; j++){
				
				calendarRow = etCalendar.get(j);
				
				aDate = (String)calendarRow.get("DATE");
				
				for(int x = 0 ; x < etList.size() ; x++){
					
					listRow = etList.get(x);
					
					bPernr = (String)listRow.get("PERNR");
					bDate = (String)listRow.get("BEGDA");
					
					if(aPernr.equals(bPernr) && aDate.equals(bDate)){
						mergeRow.put("TTEXT"+j, listRow.get("TTEXT")); //근무일정
						mergeRow.put("WKTIM"+j, listRow.get("WKTIM")); //기본근로
						mergeRow.put("OVTM"+j , listRow.get("OVTM")); //연장근로
						mergeRow.put("TOTAL"+j, listRow.get("TOTAL")); //계(기본+연장)
						
						if("DETAIL".equals((String)params.get("viewType"))){
							mergeRow.put("ATEXT"+j, listRow.get("ATEXT")); //휴가-유형
							mergeRow.put("TATX1"+j, listRow.get("TATX1")); //휴가-대근자1
							mergeRow.put("TATX2"+j, listRow.get("TATX2")); //휴가-대근자2
							mergeRow.put("TATX3"+j, listRow.get("TATX3")); //휴가-대근자3
							mergeRow.put("TATX4"+j, listRow.get("TATX4")); //휴가-대근자4
							
							mergeRow.put("VA_ENAME"+j, listRow.get("VA_ENAME")); //대근O/T - 휴가자
							mergeRow.put("VATM"+j, listRow.get("VATM")); //대근O/T - 대근시간
						}
					}
				}
			}
			
			mergeList.add(mergeRow);
		}
		
		mav.addObject("resultMap", resultMap);
		mav.addObject("mergeList", mergeList);
		mav.addObject("params"   , params);
		
		return mav;
	}
	
	@RequestMapping(value = "retrieveEtSchkz.do")
	public @ResponseBody Map<?, ?> retrieveEtSchkz(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");
		
		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		rfcParam.put("IM_ADMIN", user.getEmpNo());
		rfcParam.put("IM_BEGDA", params.get("startDate"));
		rfcParam.put("IM_ENDDA", params.get("endDate"));
		rfcParam.put("IM_ZEITY", params.get("imZeity"));
		//RFC PARAMETER SETTING END
		
		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_PT_027_GET_LISTBOX", rfcParam, null, request);
		
		return result;
	}
	
	@SuppressWarnings({ "unchecked", "static-access" })
	@RequestMapping(value = "/monthDiligenceReportList.do")
	public ModelAndView monthDiligenceReportList(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {
		
		ModelAndView mav = new ModelAndView("/portal/webmss/diligencePayMng/monthDiligenceReportList");
		
		User user = (User) getRequestAttribute("ikep.user");
		
		String toDay = DateUtil.getToday("yyyy-MM");
		
		if(!params.containsKey("hidStartDate")){
			params.put("hidStartDate", toDay+"-01");
		}
		
		if(!params.containsKey("hidEndDate")){
			
			String[] dateArray = ((String) params.get("hidStartDate")).split("-");
			Calendar cal = Calendar.getInstance();
			cal.set(Integer.parseInt(dateArray[0]), Integer.parseInt(dateArray[1]) - 1, 1);
			
			params.put("hidEndDate", toDay+"-"+String.format("%02d", cal.getActualMaximum(cal.DAY_OF_MONTH)));
		}
		
		if(params.containsKey("clearCache") && "Y".equals(params.get("clearCache"))){
			CacheRefreshUtil.refreshCache("rfcCache");
		}
		
		if("true".equals((String)params.get("setEmpFlag"))){
			params.put("hidImPernr", params.get("selEmpNo"));
		}
		
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		
		rfcParam.put("IM_ADMIN", user.getEmpNo());
		rfcParam.put("IM_PERNR", params.get("hidImPernr"));
		rfcParam.put("IM_BEGDA", params.get("hidStartDate"));
		rfcParam.put("IM_ENDDA", params.get("hidEndDate"));
		rfcParam.put("IM_TOTAL", params.get("hidImTotal"));
		rfcParam.put("IM_FIRST", params.get("imFirst"));
		rfcParam.put("IM_VRC"  , params.get("imVrc"));
		
		Map<?, ?> resultMap = rfcCacheService.callRfcFunctionSetCache("ZHR_RFC_PT_017_ONINIT", rfcParam, null);
		
		ArrayList<Map<?, ?>> tempList = (ArrayList<Map<?, ?>>)resultMap.get("ET_LIST");
		
		ArrayList<Map<?, ?>> etList = PagingUtils.setPagingParam(tempList, params);
		
		mav.addObject("resultMap", resultMap);
		mav.addObject("etList"   , etList);
		mav.addObject("params"   , params);
		
		return mav;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/monthDiligencePrintPop.do")
	public ModelAndView monthDiligencePrintPop(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {
		
		ModelAndView mav = new ModelAndView("/portal/webmss/diligencePayMng/monthDiligencePrintPop");
		
		User user = (User) getRequestAttribute("ikep.user");
				
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		
		rfcParam.put("IM_ADMIN", user.getEmpNo());
		rfcParam.put("IM_PERNR", params.get("hidImPernr"));
		rfcParam.put("IM_BEGDA", params.get("hidStartDate"));
		rfcParam.put("IM_ENDDA", params.get("hidEndDate"));
		rfcParam.put("IM_TOTAL", params.get("hidImTotal"));
		rfcParam.put("IM_FIRST", params.get("imFirst"));
		rfcParam.put("IM_VRC"  , params.get("imVrc"));
		
		Map<?, ?> resultMap = webEssRcv.callRfcFunction("ZHR_RFC_PT_017_ONINIT", rfcParam, null, request);
		
		ArrayList<HashMap<String, Object>> etPernr = (ArrayList<HashMap<String, Object>>) resultMap.get("ET_PERNR");
		HashMap<String, Object> etPernrRow;
		ArrayList<HashMap<String, Object>> etList = (ArrayList<HashMap<String, Object>>) resultMap.get("ET_LIST");
		
		String imTotal = (String) params.get("hidImTotal");
		String imPernr = (String) params.get("hidImPernr");
		
		if(!"X".equals(imTotal) && !"".equals(imPernr)){
			for(int i = etPernr.size() - 1 ; i > -1 ; i--){
				etPernrRow = etPernr.get(i);
				if(!imPernr.equals((String)etPernrRow.get("PERNR"))){
					etPernr.remove(i);
				}
			}
		}
		
		mav.addObject("etPernr", etPernr);
		mav.addObject("etList" , etList);
		mav.addObject("params" , params);
		
		return mav;
	}
	
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/downloadMonthDiligenceReport.do")
	public void downloadMonthDiligenceReport(@RequestParam HashMap<String, Object> params, HttpServletRequest request, HttpServletResponse response) {
		
		User user = (User) getRequestAttribute("ikep.user");
		
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		
		rfcParam.put("IM_ADMIN", user.getEmpNo());
		rfcParam.put("IM_PERNR", params.get("hidImPernr"));
		rfcParam.put("IM_BEGDA", params.get("hidStartDate"));
		rfcParam.put("IM_ENDDA", params.get("hidEndDate"));
		rfcParam.put("IM_TOTAL", params.get("hidImTotal"));
		rfcParam.put("IM_FIRST", params.get("imFirst"));
		rfcParam.put("IM_VRC"  , params.get("imVrc"));
		
		Map<?, ?> resultMap = webEssRcv.callRfcFunction("ZHR_RFC_PT_017_ONINIT", rfcParam, null, request);
		
		String fileName = "monthlyReport.xlsx";
		
		if("X".equals((String)params.get("hidImTotal"))){
			this.saveExcelTotal(resultMap, params, fileName, response, 0, "monthlyReport");
		}else{
			this.saveExcel(resultMap, params, fileName, response, 0, "monthlyReport");
		}
		
	}
	
	@RequestMapping(value = "/overtimeWorkTotalView.do")
	public ModelAndView overtimeWorkTotalView(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {
		
		ModelAndView mav = new ModelAndView("/portal/webmss/diligencePayMng/overtimeWorkTotalView");
		
		User user = (User) getRequestAttribute("ikep.user");
		
		if(!params.containsKey("imYyyymm")){
			params.put("imYyyymm", DateUtil.getToday("yyyyMM"));
		}
		
		params.put("limit", DateUtil.getPrevMonthDate(DateUtil.getToday("yyyyMMdd"), 6, "yyyyMM"));
		
		if("true".equals((String)params.get("setEmpFlag"))){
			params.put("imPernr", params.get("selEmpNo"));
		}
		
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		
		rfcParam.put("IM_ADMIN" , user.getEmpNo());
		rfcParam.put("IM_PERNR" , params.get("imPernr"));
		rfcParam.put("IM_YYYYMM", params.get("imYyyymm"));
		
		Map<?, ?> resultMap = webEssRcv.callRfcFunction("ZHR_RFC_PT_015_ONINIT", rfcParam, null, request);
		
		mav.addObject("resultMap", resultMap);
		mav.addObject("params"   , params);
		
		return mav;
	}
	
	@RequestMapping(value = "/overtimeWorkTotalGetPDF.do")
	public void overtimeWorkTotalGetPDF(@RequestParam HashMap<String, Object> params, HttpServletRequest request, HttpServletResponse response) {

		User user = (User) getRequestAttribute("ikep.user");
		
		if(!params.containsKey("imYyyymm")){
			params.put("imYyyymm", DateUtil.getToday("yyyyMM"));
		}
		
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		
		rfcParam.put("IM_ADMIN" , user.getEmpNo());
		rfcParam.put("IM_PERNR" , params.get("imPernr"));
		rfcParam.put("IM_YYYYMM", params.get("imYyyymm"));
		rfcParam.put("IM_FIRST" , params.get("imFirst"));
		
		byte[] byteArray = webEssRcv.callRfcFunctionGetPDF("ZHR_RFC_PT_015_ONINIT", rfcParam, null, request);

		String charSet = "UTF-8";
		String fileName = "overtimeWorkTotal.pdf";

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
			log.error("overtimeWorkTotalGetPDF() IOException.");
			log.debug("e.getMessage():"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings({ "unchecked", "deprecation", "unused" })
	private static String saveExcel(Map<?, ?> resultMap, HashMap<String, Object> params, String fileName, HttpServletResponse response, int sheetIndex, String sheetTitle) {
		
		String[] titleSet = {"일자", "근무형태", "근무상태", "근무시간", "시간외I", "시간외II", "야간근로", "야간초과", "주휴(일요)", "주휴(비번)", "공휴근로", "휴가근로", "시간외수당"};
		String[] titleKeySet = {"DATUM", "TTEXT", "ATEXT", "WKTIM", "OVTM1", "OVTM2", "NIGHT", "OVNIG", "WKSUN", "WKOFF", "HOLWK", "VACWK", "OVTIM"};
		
		try {
			// WorkBook 초기화
			Workbook[] wbs = new Workbook[] { new XSSFWorkbook() };
			
			for (int i = 0; i < wbs.length; i++) {
				// Sheet 초기화
				Workbook workbook = wbs[i];
				
				Sheet sheet = null;
				if(sheetIndex == 0 && sheetTitle != null){
					sheet = workbook.createSheet(sheetTitle);
				} else {
					for (int sIndex = 0; sIndex < sheetIndex + 1; sIndex++) {
						sheet = workbook.createSheet();
					}
				}
				
				DataFormat format = workbook.createDataFormat();
				DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);
				
				//기본 스타일 설정
				CellStyle cellStyle = workbook.createCellStyle();
				cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
				
				//타이틀 스타일 설정
				CellStyle titleCellStyle = workbook.createCellStyle();
				
				Font titleFont = workbook.createFont();
				
				titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
				
				titleCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
				titleCellStyle.setFont(titleFont);
				
				//헤더정보 날짜 스타일 설정
				CellStyle headerDateStyle = workbook.createCellStyle();
				headerDateStyle.setDataFormat(format.getFormat("yyyy"));
				headerDateStyle.setAlignment(CellStyle.ALIGN_CENTER);
				
				//리스트 근무시간수 스타일 설정
				CellStyle doubleStyle = workbook.createCellStyle();
				doubleStyle.setAlignment(CellStyle.ALIGN_CENTER);
				doubleStyle.setDataFormat(format.getFormat("0.00"));
				doubleStyle.setBorderTop(CellStyle.BORDER_THIN);
				doubleStyle.setBorderLeft(CellStyle.BORDER_THIN);
				doubleStyle.setBorderRight(CellStyle.BORDER_THIN);
				doubleStyle.setBorderBottom(CellStyle.BORDER_THIN);
				
				//리스트 날짜 스타일 설정
				CellStyle dateStyle = workbook.createCellStyle();
				dateStyle.setDataFormat(format.getFormat("yyyy.mm.dd"));
				dateStyle.setAlignment(CellStyle.ALIGN_CENTER);
				dateStyle.setBorderTop(CellStyle.BORDER_THIN);
				dateStyle.setBorderLeft(CellStyle.BORDER_THIN);
				dateStyle.setBorderRight(CellStyle.BORDER_THIN);
				dateStyle.setBorderBottom(CellStyle.BORDER_THIN);
				
				//리스트 일반 스타일
				CellStyle listStyle = workbook.createCellStyle();
				listStyle.setAlignment(CellStyle.ALIGN_CENTER);
				listStyle.setBorderTop(CellStyle.BORDER_THIN);
				listStyle.setBorderLeft(CellStyle.BORDER_THIN);
				listStyle.setBorderRight(CellStyle.BORDER_THIN);
				listStyle.setBorderBottom(CellStyle.BORDER_THIN);
				
				Row row;
				Cell cell;
				
				int rownum = 0;
				int colnum = 0;
				
				// 타이틀 정보 저장
				row = sheet.createRow(rownum);
				cell = row.createCell(colnum);
				cell.setCellStyle(titleCellStyle);
				cell.setCellValue("월근태보고서");
				
				rownum = rownum + 2;
				
				ArrayList<HashMap<String, Object>> etPernr = (ArrayList<HashMap<String, Object>>) resultMap.get("ET_PERNR");
				
				ArrayList<HashMap<String, Object>> etList = (ArrayList<HashMap<String, Object>>) resultMap.get("ET_LIST");
				
				HashMap<String, Object> etPernrRow;
				HashMap<String, Object> etListRow;
				
				String pernr = "";
				double totWktim = 0.0;
				double totOvtm1 = 0.0;
				double totOvtm2 = 0.0;
				double totNight = 0.0;
				double totOvnig = 0.0;
				double totWksun = 0.0;
				double totWkoff = 0.0;
				double totHolwk = 0.0;
				double totVacwk = 0.0;
				int totOvtim = 0;
				
				double totSumWktim = 0.0;
				double totSumOvtm1 = 0.0;
				double totSumOvtm2 = 0.0;
				double totSumNight = 0.0;
				double totSumOvnig = 0.0;
				double totSumWksun = 0.0;
				double totSumWkoff = 0.0;
				double totSumHolwk = 0.0;
				double totSumVacwk = 0.0;
				int totSumOvtim = 0;
				
				String selPernr = (String)params.get("hidImPernr");
				
				if(!"".equals(selPernr)){
					for(int etPernrIndex = etPernr.size() - 1 ; etPernrIndex > -1 ; etPernrIndex--){
						etPernrRow = etPernr.get(etPernrIndex);
						if(!selPernr.equals((String) etPernrRow.get("PERNR"))){
							etPernr.remove(etPernrIndex);
						}
					}
				}
				
				for(int etPernrIndex = 0 ; etPernrIndex < etPernr.size() ; etPernrIndex++){
				
					etPernrRow = etPernr.get(etPernrIndex);
					pernr = (String) etPernrRow.get("PERNR");
					
					for(int etListIndex = 0 ; etListIndex < etList.size() ; etListIndex++){
						etListRow = etList.get(etListIndex);
						
						if(pernr.equals((String)etListRow.get("PERNR")) && ((String)etListRow.get("DATUM")).equals(((String)params.get("hidEndDate")).replaceAll("-", ""))){
							
							row = sheet.createRow(rownum);
							cell = row.createCell(colnum);
							cell.setCellStyle(cellStyle);
							cell.setCellValue("성명");
							colnum++;
							
							cell = row.createCell(colnum);
							cell.setCellStyle(cellStyle);
							cell.setCellValue((String)etListRow.get("ENAME"));
							colnum = colnum + 2;
							
							cell = row.createCell(colnum);
							cell.setCellStyle(cellStyle);
							cell.setCellValue("부서");
							colnum++;
							
							cell = row.createCell(colnum);
							cell.setCellStyle(cellStyle);
							cell.setCellValue((String)etListRow.get("ORGTX"));
							colnum = colnum + 2;
							
							cell = row.createCell(colnum);
							cell.setCellStyle(cellStyle);
							cell.setCellValue("연도");
							colnum++;
							
							cell = row.createCell(colnum);
							cell.setCellStyle(headerDateStyle);
							cell.setCellValue(dateFormat.parse((String)etListRow.get("DATUM")));
							colnum = colnum + 2;
							
							cell = row.createCell(colnum);
							cell.setCellStyle(cellStyle);
							cell.setCellValue("관리자");
							colnum++;
							
							cell = row.createCell(colnum);
							cell.setCellStyle(cellStyle);
							cell.setCellValue((String)etListRow.get("ADMIN"));
							
							colnum = 0;
						}
					}
					
					rownum++;
					
					row = sheet.createRow(rownum);
					
					for(String title : titleSet){
						cell = row.createCell(colnum);
						cell.setCellStyle(listStyle);
						cell.setCellValue(title);
						colnum++;
					}
					
					colnum = 0;
					rownum++;
					
					for(int etListIndex = 0 ; etListIndex < etList.size() ; etListIndex++){
						etListRow = etList.get(etListIndex);
						if(pernr.equals((String)etListRow.get("PERNR"))){
							row = sheet.createRow(rownum);
							
							for(String key : titleKeySet){
								cell = row.createCell(colnum);
								
								if("DATUM".equals(key)){
									cell.setCellStyle(dateStyle);
									cell.setCellValue(dateFormat.parse((String) etListRow.get(key)));
								}else{
									cell.setCellStyle(listStyle);
									cell.setCellValue((String) etListRow.get(key));
								}
								colnum++;
								
								if("WKTIM".equals(key)){
									totWktim = totWktim + Double.parseDouble((String) etListRow.get(key));
									totSumWktim = totSumWktim + Double.parseDouble((String) etListRow.get(key));
								}else if("OVTM1".equals(key)){
									totOvtm1 = totOvtm1 + Double.parseDouble((String) etListRow.get(key));
									totSumOvtm1 = totSumOvtm1 + Double.parseDouble((String) etListRow.get(key));
								}else if("OVTM2".equals(key)){
									totOvtm2 = totOvtm2 + Double.parseDouble((String) etListRow.get(key));
									totSumOvtm2 = totSumOvtm2 + Double.parseDouble((String) etListRow.get(key));
								}else if("NIGHT".equals(key)){
									totNight = totNight + Double.parseDouble((String) etListRow.get(key));
									totSumNight = totSumNight + Double.parseDouble((String) etListRow.get(key));
								}else if("OVNIG".equals(key)){
									totOvnig = totOvnig + Double.parseDouble((String) etListRow.get(key));
									totSumOvnig = totSumOvnig + Double.parseDouble((String) etListRow.get(key));
								}else if("WKSUN".equals(key)){
									totWksun = totWksun + Double.parseDouble((String) etListRow.get(key));
									totSumWksun = totSumWksun + Double.parseDouble((String) etListRow.get(key));
								}else if("WKOFF".equals(key)){
									totWkoff = totWkoff + Double.parseDouble((String) etListRow.get(key));
									totSumWkoff = totSumWkoff + Double.parseDouble((String) etListRow.get(key));
								}else if("HOLWK".equals(key)){
									totHolwk = totHolwk + Double.parseDouble((String) etListRow.get(key));
									totSumHolwk = totSumHolwk + Double.parseDouble((String) etListRow.get(key));
								}else if("VACWK".equals(key)){
									totVacwk = totVacwk + Double.parseDouble((String) etListRow.get(key));
									totSumVacwk = totSumVacwk + Double.parseDouble((String) etListRow.get(key));
								}else if("OVTIM".equals(key)){
									totOvtim = totOvtim + Integer.parseInt((String) etListRow.get(key));
									totSumOvtim = totSumOvtim + Integer.parseInt((String) etListRow.get(key));
								}
							}
							colnum = 0;
							rownum++;
							
							if(((String)etListRow.get("DATUM")).equals(((String)params.get("hidEndDate")).replaceAll("-", ""))){
								
								row = sheet.createRow(rownum);
								cell = row.createCell(colnum);
								
								cell.setCellStyle(listStyle);
								cell.setCellValue("인 별 총 계");
								colnum++;
								
								cell = row.createCell(colnum);
								cell.setCellStyle(listStyle);
								colnum++;
								
								cell = row.createCell(colnum);
								cell.setCellStyle(listStyle);
								colnum++;
								
								sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, 0, 2));
								
								cell = row.createCell(colnum);
								cell.setCellStyle(doubleStyle);
								cell.setCellValue(totWktim);
								colnum++;
								
								cell = row.createCell(colnum);
								cell.setCellStyle(doubleStyle);
								cell.setCellValue(totOvtm1);
								colnum++;
								
								cell = row.createCell(colnum);
								cell.setCellStyle(doubleStyle);
								cell.setCellValue(totOvtm2);
								colnum++;
								
								cell = row.createCell(colnum);
								cell.setCellStyle(doubleStyle);
								cell.setCellValue(totNight);
								colnum++;
								
								cell = row.createCell(colnum);
								cell.setCellStyle(doubleStyle);
								cell.setCellValue(totOvnig);
								colnum++;
								
								cell = row.createCell(colnum);
								cell.setCellStyle(doubleStyle);
								cell.setCellValue(totWksun);
								colnum++;
								
								cell = row.createCell(colnum);
								cell.setCellStyle(doubleStyle);
								cell.setCellValue(totWkoff);
								colnum++;
								
								cell = row.createCell(colnum);
								cell.setCellStyle(doubleStyle);
								cell.setCellValue(totHolwk);
								colnum++;
								
								cell = row.createCell(colnum);
								cell.setCellStyle(doubleStyle);
								cell.setCellValue(totVacwk);
								colnum++;
								
								cell = row.createCell(colnum);
								cell.setCellStyle(listStyle);
								cell.setCellValue(totOvtim);
								
								colnum = 0;
								rownum++;
								
								totWktim = 0.0;
								totOvtm1 = 0.0;
								totOvtm2 = 0.0;
								totNight = 0.0;
								totOvnig = 0.0;
								totWksun = 0.0;
								totWkoff = 0.0;
								totHolwk = 0.0;
								totVacwk = 0.0;
								totOvtim = 0;
								
								if(etPernrIndex == etPernr.size() - 1){
									row = sheet.createRow(rownum);
									cell = row.createCell(colnum);
									
									cell.setCellStyle(listStyle);
									cell.setCellValue("총 계");
									colnum++;
									
									cell = row.createCell(colnum);
									cell.setCellStyle(listStyle);
									colnum++;
									
									cell = row.createCell(colnum);
									cell.setCellStyle(listStyle);
									colnum++;
									
									sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, 0, 2));
									
									cell = row.createCell(colnum);
									cell.setCellStyle(doubleStyle);
									cell.setCellValue(totSumWktim);
									colnum++;
									
									cell = row.createCell(colnum);
									cell.setCellStyle(doubleStyle);
									cell.setCellValue(totSumOvtm1);
									colnum++;
									
									cell = row.createCell(colnum);
									cell.setCellStyle(doubleStyle);
									cell.setCellValue(totSumOvtm2);
									colnum++;
									
									cell = row.createCell(colnum);
									cell.setCellStyle(doubleStyle);
									cell.setCellValue(totSumNight);
									colnum++;
									
									cell = row.createCell(colnum);
									cell.setCellStyle(doubleStyle);
									cell.setCellValue(totSumOvnig);
									colnum++;
									
									cell = row.createCell(colnum);
									cell.setCellStyle(doubleStyle);
									cell.setCellValue(totSumWksun);
									colnum++;
									
									cell = row.createCell(colnum);
									cell.setCellStyle(doubleStyle);
									cell.setCellValue(totSumWkoff);
									colnum++;
									
									cell = row.createCell(colnum);
									cell.setCellStyle(doubleStyle);
									cell.setCellValue(totSumHolwk);
									colnum++;
									
									cell = row.createCell(colnum);
									cell.setCellStyle(doubleStyle);
									cell.setCellValue(totSumVacwk);
									colnum++;
									
									cell = row.createCell(colnum);
									cell.setCellStyle(listStyle);
									cell.setCellValue(totSumOvtim);
									
									colnum = 0;
									rownum++;
								}
							}
						}
					}
					rownum = rownum + 2;
				}
				
				// CellSize 자동 맞춤
				colnum = 0;
				for (String key : titleKeySet) {
					sheet.setColumnWidth(colnum, sheet.getColumnWidth(colnum) + 540);
					colnum++;
				}
				
				// Excel 파일 저장
				fileName = new String(fileName.getBytes("euc-kr"), "8859_1");
				BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
				
				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
				response.setHeader("Pragma", "no-cache;");
				response.setHeader("Expires", "-1;");
				
				workbook.write(out);
				
				out.flush();
				out.close();
			}
		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}
		return null;
	}
	
	@SuppressWarnings({ "unchecked", "deprecation", "unused" })
	private static String saveExcelTotal(Map<?, ?> resultMap, HashMap<String, Object> params, String fileName, HttpServletResponse response, int sheetIndex, String sheetTitle) {
		String[] titleSet = {"사원번호", "성명", "부서명", "근무시간", "시간외I", "시간외II", "야간근로", "야간초과", "주휴(일요)", "주휴(비번)", "공휴근로", "휴가근로", "시간외수당"};
		String[] titleKeySet = {"PERNR", "ENAME", "ORGTX", "WKTIM", "OVTM1", "OVTM2", "NIGHT", "OVNIG", "WKSUN", "WKOFF", "HOLWK", "VACWK", "OVTIM"};
		
		try {
			// WorkBook 초기화
			Workbook[] wbs = new Workbook[] { new XSSFWorkbook() };
			
			for (int i = 0; i < wbs.length; i++) {
				// Sheet 초기화
				Workbook workbook = wbs[i];
				
				Sheet sheet = null;
				if(sheetIndex == 0 && sheetTitle != null){
					sheet = workbook.createSheet(sheetTitle);
				} else {
					for (int sIndex = 0; sIndex < sheetIndex + 1; sIndex++) {
						sheet = workbook.createSheet();
					}
				}
				
				DataFormat format = workbook.createDataFormat();
				
				//기본 스타일 설정
				CellStyle cellStyle = workbook.createCellStyle();
				cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
				
				//타이틀 스타일 설정
				CellStyle titleCellStyle = workbook.createCellStyle();
				
				Font titleFont = workbook.createFont();
				
				titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
				
				titleCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
				titleCellStyle.setFont(titleFont);
				
				//리스트 근무시간수 스타일 설정
				CellStyle doubleStyle = workbook.createCellStyle();
				doubleStyle.setAlignment(CellStyle.ALIGN_CENTER);
				doubleStyle.setDataFormat(format.getFormat("0.00"));
				doubleStyle.setBorderTop(CellStyle.BORDER_THIN);
				doubleStyle.setBorderLeft(CellStyle.BORDER_THIN);
				doubleStyle.setBorderRight(CellStyle.BORDER_THIN);
				doubleStyle.setBorderBottom(CellStyle.BORDER_THIN);
				
				//리스트 일반 스타일
				CellStyle listStyle = workbook.createCellStyle();
				listStyle.setAlignment(CellStyle.ALIGN_CENTER);
				listStyle.setBorderTop(CellStyle.BORDER_THIN);
				listStyle.setBorderLeft(CellStyle.BORDER_THIN);
				listStyle.setBorderRight(CellStyle.BORDER_THIN);
				listStyle.setBorderBottom(CellStyle.BORDER_THIN);
				
				Row row;
				Cell cell;
				
				int rownum = 0;
				int colnum = 0;
				
				// 타이틀 정보 저장
				row = sheet.createRow(rownum);
				cell = row.createCell(colnum);
				cell.setCellStyle(titleCellStyle);
				cell.setCellValue("월근태보고서");
				
				rownum = rownum + 2;
				
				ArrayList<HashMap<String, Object>> etList = (ArrayList<HashMap<String, Object>>) resultMap.get("ET_LIST");
				HashMap<String, Object> etListRow;
				
				double totWktim = 0.0;
				double totOvtm1 = 0.0;
				double totOvtm2 = 0.0;
				double totNight = 0.0;
				double totOvnig = 0.0;
				double totWksun = 0.0;
				double totWkoff = 0.0;
				double totHolwk = 0.0;
				double totVacwk = 0.0;
				int totOvtim = 0;
				
				for(int etListIndex = 0 ; etListIndex < etList.size() ; etListIndex++){
					etListRow = etList.get(etListIndex);
					
					if(etListIndex == 0){
						row = sheet.createRow(rownum);
						for(String key : titleSet){
							cell = row.createCell(colnum);
							cell.setCellStyle(listStyle);
							cell.setCellValue(key);
							
							colnum++;
						}
						colnum = 0;
						rownum++;
					}
					
					row = sheet.createRow(rownum);
					
					for(String key : titleKeySet){
						cell = row.createCell(colnum);
						if("PERNR".equals(key) || "ENAME".equals(key) || "ORGTX".equals(key)){
							cell.setCellStyle(listStyle);
							cell.setCellValue((String) etListRow.get(key));
						}else{
							cell.setCellStyle(doubleStyle);
							cell.setCellValue((String) etListRow.get(key));
						}
						
						colnum++;
						
						if("WKTIM".equals(key)){
							totWktim = totWktim + Double.parseDouble((String) etListRow.get(key));
						}else if("OVTM1".equals(key)){
							totOvtm1 = totOvtm1 + Double.parseDouble((String) etListRow.get(key));
						}else if("OVTM2".equals(key)){
							totOvtm2 = totOvtm2 + Double.parseDouble((String) etListRow.get(key));
						}else if("NIGHT".equals(key)){
							totNight = totNight + Double.parseDouble((String) etListRow.get(key));
						}else if("OVNIG".equals(key)){
							totOvnig = totOvnig + Double.parseDouble((String) etListRow.get(key));
						}else if("WKSUN".equals(key)){
							totWksun = totWksun + Double.parseDouble((String) etListRow.get(key));
						}else if("WKOFF".equals(key)){
							totWkoff = totWkoff + Double.parseDouble((String) etListRow.get(key));
						}else if("HOLWK".equals(key)){
							totHolwk = totHolwk + Double.parseDouble((String) etListRow.get(key));
						}else if("VACWK".equals(key)){
							totVacwk = totVacwk + Double.parseDouble((String) etListRow.get(key));
						}else if("OVTIM".equals(key)){
							totOvtim = totOvtim + Integer.parseInt((String) etListRow.get(key));
						}
					}
					colnum = 0;
					rownum++;
					
					if(etListIndex == etList.size() - 1){
						
						row = sheet.createRow(rownum);
						cell = row.createCell(colnum);
						
						cell.setCellStyle(listStyle);
						cell.setCellValue("총 계");
						colnum++;
						
						cell = row.createCell(colnum);
						cell.setCellStyle(listStyle);
						colnum++;
						
						cell = row.createCell(colnum);
						cell.setCellStyle(listStyle);
						colnum++;
						
						sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, 0, 2));
						
						cell = row.createCell(colnum);
						cell.setCellStyle(doubleStyle);
						cell.setCellValue(totWktim);
						colnum++;
						
						cell = row.createCell(colnum);
						cell.setCellStyle(doubleStyle);
						cell.setCellValue(totOvtm1);
						colnum++;
						
						cell = row.createCell(colnum);
						cell.setCellStyle(doubleStyle);
						cell.setCellValue(totOvtm2);
						colnum++;
						
						cell = row.createCell(colnum);
						cell.setCellStyle(doubleStyle);
						cell.setCellValue(totNight);
						colnum++;
						
						cell = row.createCell(colnum);
						cell.setCellStyle(doubleStyle);
						cell.setCellValue(totOvnig);
						colnum++;
						
						cell = row.createCell(colnum);
						cell.setCellStyle(doubleStyle);
						cell.setCellValue(totWksun);
						colnum++;
						
						cell = row.createCell(colnum);
						cell.setCellStyle(doubleStyle);
						cell.setCellValue(totWkoff);
						colnum++;
						
						cell = row.createCell(colnum);
						cell.setCellStyle(doubleStyle);
						cell.setCellValue(totHolwk);
						colnum++;
						
						cell = row.createCell(colnum);
						cell.setCellStyle(doubleStyle);
						cell.setCellValue(totVacwk);
						colnum++;
						
						cell = row.createCell(colnum);
						cell.setCellStyle(listStyle);
						cell.setCellValue(totOvtim);
						
					}
				}
				
				// CellSize 자동 맞춤
				colnum = 0;
				for (String key : titleKeySet) {
					sheet.setColumnWidth(colnum, sheet.getColumnWidth(colnum) + 540);
					colnum++;
				}
				
				// Excel 파일 저장
				fileName = new String(fileName.getBytes("euc-kr"), "8859_1");
				BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
				
				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
				response.setHeader("Pragma", "no-cache;");
				response.setHeader("Expires", "-1;");
				
				workbook.write(out);
				
				out.flush();
				out.close();
			}
		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}
		return null;
	}
	
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/dayOffList.do")
	public ModelAndView dayOffList(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {
		
		ModelAndView mav = new ModelAndView("/portal/webmss/diligencePayMng/dayOffList");
			
		User user = (User) getRequestAttribute("ikep.user");
		
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		
		String toDay = DateUtil.getToday("yyyy-MM");
		
		if(!params.containsKey("startDate")){
			params.put("startDate", toDay+"-01");
		}
		
		if(!params.containsKey("endDate")){
			String[] dateArray = ((String) params.get("startDate")).split("-");
			Calendar cal = Calendar.getInstance();
			cal.set(Integer.parseInt(dateArray[0]), Integer.parseInt(dateArray[1]) - 1, 1);
			
			params.put("endDate", toDay+"-"+String.format("%02d", cal.getActualMaximum(cal.DAY_OF_MONTH)));
		}
		
		if("true".equals((String)params.get("setEmpFlag"))){
			params.put("imPernr", params.get("selEmpNo"));
		}
		
		rfcParam.put("IM_ADMIN", user.getEmpNo());
		rfcParam.put("IM_PERNR", params.get("imPernr"));
		rfcParam.put("IM_BEGDA", params.get("startDate"));
		rfcParam.put("IM_ENDDA", params.get("endDate"));
		
		Map<?, ?> resultMap = webEssRcv.callRfcFunction("ZHR_RFC_PT_018_ONINIT", rfcParam, null, request);
		
		mav.addObject("resultMap", resultMap);
		mav.addObject("keySet"   , Constant.dayOffListKeySet);
		mav.addObject("params"   , params);
		
		return mav;
	}
	
	@RequestMapping(value = "/dayOffView.do")
	public ModelAndView dayOffView(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {
		
		ModelAndView mav = new ModelAndView();
		
		String viewName = "/portal/webmss/diligencePayMng/dayOffRegView";
		
		String imFlag = (String) params.get("imFlag");
		
		if("DEL".equals(imFlag) || "DIS".equals(imFlag)){
			viewName = "/portal/webmss/diligencePayMng/dayOffView";
		}
		
		User user = (User) getRequestAttribute("ikep.user");
		
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		
		rfcParam.put("IM_PERNR", user.getEmpNo());
		rfcParam.put("IM_FLAG" , imFlag);
		rfcParam.put("IM_BEGDA", params.get("imBegda"));
		rfcParam.put("IM_ENDDA", params.get("imEndda"));
		
		String[] keySet = Constant.dayOffListKeySet;
		HashMap<String, Object> paramRow = new HashMap<String, Object>();
		
		if("CRE".equals(imFlag)){
			paramRow.put("PERNR", params.get("imPernr"));
			rfcParam.put("IM_LIST", paramRow);
		}else{
			for(String key : keySet){
				paramRow.put(key, params.get(key));
				params.put("SEL_"+key, params.get(key));
				params.remove(key);
			}
			
			rfcParam.put("IM_LIST", paramRow);
		}
		
		int etBanwonCnt = Integer.parseInt((String) params.get("etBanwonCnt"));
		
		ArrayList<HashMap<String, Object>> etBanwonList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> etBanwonRow;
		
		if(etBanwonCnt == 1){
			etBanwonRow = new HashMap<String, Object>();
			
			etBanwonRow.put("KEY", params.get("etBanwonKey"));
			etBanwonRow.put("VALUE", params.get("etBanwonValue"));
			
			etBanwonList.add(etBanwonRow);
		}else if(etBanwonCnt > 1){
			for(int i = 0 ; i < etBanwonCnt ; i++){
				etBanwonRow = new HashMap<String, Object>();
				
				etBanwonRow.put("KEY", request.getParameterValues("etBanwonKey")[i]);
				etBanwonRow.put("VALUE", request.getParameterValues("etBanwonValue")[i]);
				
				etBanwonList.add(etBanwonRow);
			}
		}
		
		Map<?, ?> resultMap = webEssRcv.callRfcFunction("ZHR_RFC_PT_018_ONINIT_DETAIL", rfcParam, null, request);
		
		Map<?, ?> exList = (Map<?, ?>) resultMap.get("EX_LIST");
		
		for(String key : keySet){
			params.put(key, exList.get(key));
		}
		
		mav.setViewName(viewName);
		
		mav.addObject("resultMap", resultMap);
		mav.addObject("keySet"   , keySet);
		mav.addObject("params"   , params);
		mav.addObject("etBanwon" , etBanwonList);
		
		return mav;
	}
	
	@RequestMapping(value = "callPT018OnInputDetail.do")
	public @ResponseBody Map<?, ?> callPT018OnInputDetail(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");
		
		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> paramRow = new HashMap<String, Object>();
		
		rfcParam.put("IM_PERNR", user.getEmpNo());
		rfcParam.put("IM_FLAG" , params.get("imFlag"));

		paramRow.put("PERNR", params.get("name"));
		paramRow.put("BEGDA", params.get("startDate"));
		paramRow.put("ENDDA", params.get("endDate"));
		paramRow.put("AWART", params.get("dayOffType"));
		
		rfcParam.put("IM_LIST", paramRow);

		//RFC PARAMETER SETTING END
		
		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_PT_018_ONINPUT_DETAIL", rfcParam, null, request);
		
		return result;
	}
	
	@RequestMapping(value = "dayOffProcess.do")
	public @ResponseBody Map<?, ?> dayOffProcess(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");
		
		String[] keySet = Constant.dayOffListKeySet;
		
		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> paramRow = new HashMap<String, Object>();
		HashMap<String, Object> selParamRow = new HashMap<String, Object>();
		
		rfcParam.put("IM_PERNR"   , user.getEmpNo());
		rfcParam.put("IM_FLAG"    , params.get("imFlag"));
		rfcParam.put("IM_BTRTL"   , params.get("imBtrtl"));
		rfcParam.put("IM_ORGEH"   , params.get("imOrgeh"));
		rfcParam.put("IM_EVENT_ID", params.get("imEventId"));
		rfcParam.put("IM_TA_FLAG" , params.get("imTaFlag"));
		rfcParam.put("IM_TA_COUNT", params.get("imTaCount"));
		rfcParam.put("IM_YES"     , params.get("imYes"));

		String keyPoint = "0";
		
		for(String key : keySet){
			if("PERNR".equals(key)){
				paramRow.put(key, params.get("name"));
			}else if("BEGDA".equals(key)){
				paramRow.put(key, params.get("startDate"));
			}else if("ENDDA".equals(key)){
				paramRow.put(key, params.get("endDate"));
			}else if("AWART".equals(key)){
				paramRow.put(key, params.get("dayOffType"));
			}else if("BEGUZ".equals(key)){
				paramRow.put(key, params.get("startTime"));
			}else if("ENDUZ".equals(key)){
				paramRow.put(key, params.get("endTime"));
			}else if("STDAZ".equals(key)){
				paramRow.put(key, params.get("dayOffTime"));
			}else if("ALLDF".equals(key)){
				paramRow.put(key, params.get("allDay"));
			}else if("ABWTG".equals(key)){
				paramRow.put(key, params.get("dayOffCnt"));
			}else if("KALTG".equals(key)){
				paramRow.put(key, params.get("calendarCnt"));
			}else if("ABRTG".equals(key)){
				paramRow.put(key, params.get("useQuarter"));
			}else{
				if(key.indexOf("TA_PERNR") > -1){
					keyPoint = key.replaceAll("TA_PERNR", "");
					if(params.containsKey("taPernr"+keyPoint) && !"".equals((String)params.get("taPernr"+keyPoint))){
						paramRow.put(key, params.get("taPernr"+keyPoint));
						paramRow.put("TATM"+keyPoint, params.get("taPernrTime"+keyPoint));
					}
				}else if(key.indexOf("TATM") > -1){
					keyPoint = key.replaceAll("TATM", "");
					if(params.containsKey("taPernr"+keyPoint) && !"".equals((String)params.get("taPernr"+keyPoint))){
						paramRow.put(key, params.get("taPernrTime"+keyPoint));
					}
				}else if(key.indexOf("TA_DATE") > -1){
					keyPoint = key.replaceAll("TA_DATE", "");
					if(params.containsKey("taPernr"+keyPoint)){
						paramRow.put(key, params.get("taDate"+keyPoint));
					}
				}else if(key.indexOf("TA_INDEX") > -1){
					keyPoint = key.replaceAll("TA_INDEX", "");
					if(params.containsKey("taPernr"+keyPoint) && !"".equals((String)params.get("taPernr"+keyPoint))){
						paramRow.put(key, keyPoint);
					}
				}else{
					paramRow.put(key, params.get(key));
				}
			}
			
			selParamRow.put(key, params.get("SEL_"+key));
		}
		
		rfcParam.put("IM_LIST"    , paramRow);
		rfcParam.put("IM_LIST_SEL", selParamRow);
		//RFC PARAMETER SETTING END
		
		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_PT_018_PROCESS", rfcParam, null, request);
		return result;
	}
	
	@RequestMapping(value = "/workList.do")
	public ModelAndView workList(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {
		
		ModelAndView mav = new ModelAndView("/portal/webmss/diligencePayMng/workList");
			
		User user = (User) getRequestAttribute("ikep.user");
		
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		
		if(!params.containsKey("startDate")){
			params.put("startDate", DateUtil.getToday("yyyy-MM")+"-01");
		}
		
		if(!params.containsKey("endDate")){
			params.put("endDate", DateUtil.getToday("yyyy-MM-dd"));
		}
		
		if("true".equals((String)params.get("setEmpFlag"))){
			params.put("imPernr", params.get("selEmpNo"));
		}
		
		rfcParam.put("IM_ADMIN", user.getEmpNo());
		rfcParam.put("IM_PERNR", params.get("imPernr"));
		rfcParam.put("IM_BEGDA", params.get("startDate"));
		rfcParam.put("IM_ENDDA", params.get("endDate"));
		rfcParam.put("IM_FIRST", params.get("imFirst"));
		
		Map<?, ?> resultMap = webEssRcv.callRfcFunction("ZHR_RFC_PT_019_ONINIT", rfcParam, null, request);
		
		mav.addObject("resultMap", resultMap);
		mav.addObject("keySet"   , Constant.workListKeySet);
		mav.addObject("params"   , params);
		
		return mav;
	}
	
	@RequestMapping(value = "/workView.do")
	public ModelAndView workView(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {
		
		ModelAndView mav = new ModelAndView();
		
		String viewName = "/portal/webmss/diligencePayMng/workRegView";
		
		String imFlag = (String) params.get("imFlag");
		
		if("DEL".equals(imFlag) || "DIS".equals(imFlag)){
			viewName = "/portal/webmss/diligencePayMng/workView";
		}
		
		String[] keySet = Constant.workListKeySet;
		
		if(!"CRE".equals(imFlag)){
			for(String key : keySet){
				params.put("SEL_"+key, params.get(key));
			}
		}
		
		mav.setViewName(viewName);
		
		int etBanwonCnt = Integer.parseInt((String)params.get("etBanwonCnt"));
		int etAwartCnt = Integer.parseInt((String)params.get("etAwartCnt"));
		
		ArrayList<HashMap<String, Object>> etBanwonList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> etBanwonRow;
		
		ArrayList<HashMap<String, Object>> etAwartList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> etAwartRow;
		
		if(etBanwonCnt == 1){
			etBanwonRow = new HashMap<String, Object>();
			
			etBanwonRow.put("KEY", params.get("etBanwonKey"));
			etBanwonRow.put("VALUE", params.get("etBanwonValue"));
			
			etBanwonList.add(etBanwonRow);
		}else if(etBanwonCnt > 1){
			for(int i = 0 ; i < etBanwonCnt ; i++){
				etBanwonRow = new HashMap<String, Object>();
				
				etBanwonRow.put("KEY", request.getParameterValues("etBanwonKey")[i]);
				etBanwonRow.put("VALUE", request.getParameterValues("etBanwonValue")[i]);
				
				etBanwonList.add(etBanwonRow);
			}
		}
		
		if(etAwartCnt == 1){
			etAwartRow = new HashMap<String, Object>();
			
			etAwartRow.put("KEY", params.get("etAwartKey"));
			etAwartRow.put("VALUE", params.get("etAwartValue"));
			
			etAwartList.add(etAwartRow);
		}else if(etAwartCnt > 1){
			for(int i = 0 ; i < etAwartCnt ; i++){
				etAwartRow = new HashMap<String, Object>();
				
				etAwartRow.put("KEY", request.getParameterValues("etAwartKey")[i]);
				etAwartRow.put("VALUE", request.getParameterValues("etAwartValue")[i]);
				
				etAwartList.add(etAwartRow);
			}
		}
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("ET_BANWON", etBanwonList);
		resultMap.put("ET_AWART" , etAwartList);
		
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		
		rfcParam.put("IM_DATUM", params.get("BEGDA"));
		resultMap.put("EX_BEGWK", webEssRcv.callRfcFunction("ZHR_RFC_GET_DAY_OF_WEEK", rfcParam, null, request).get("EX_TEXT"));
		
		rfcParam.put("IM_DATUM", params.get("ENDDA"));
		resultMap.put("EX_ENDWK", webEssRcv.callRfcFunction("ZHR_RFC_GET_DAY_OF_WEEK", rfcParam, null, request).get("EX_TEXT"));
		
		mav.addObject("resultMap", resultMap);
		mav.addObject("keySet"   , keySet);
		mav.addObject("params"   , params);
		
		return mav;
	}
	
	@RequestMapping(value = "callPT019OnInputDetail.do")
	public @ResponseBody Map<?, ?> callPT019OnInputDetail(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {
		
		User user = (User) getRequestAttribute("ikep.user");
		
		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> paramRow = new HashMap<String, Object>();
		
		rfcParam.put("IM_PERNR", user.getEmpNo());
		rfcParam.put("IM_FLAG" , params.get("imFlag"));
		
		paramRow.put("PERNR", params.get("name"));
		paramRow.put("BEGDA", params.get("startDate"));
		paramRow.put("ENDDA", params.get("endDate"));
		paramRow.put("AWART", params.get("workType"));
		
		rfcParam.put("IM_LIST", paramRow);

		//RFC PARAMETER SETTING END
		
		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_PT_019_ONINPUT_DETAIL", rfcParam, null, request);
		
		return result;
	}
	
	@RequestMapping(value = "workProcess.do")
	public @ResponseBody Map<?, ?> workProcess(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");
		
		String[] keySet = Constant.workListKeySet;
		
		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> paramRow = new HashMap<String, Object>();
		HashMap<String, Object> selParamRow = new HashMap<String, Object>();
		
		rfcParam.put("IM_PERNR"   , user.getEmpNo());
		rfcParam.put("IM_FLAG"    , params.get("imFlag"));
		rfcParam.put("IM_EVENT_ID", params.get("imEventId"));
		rfcParam.put("IM_YES"     , params.get("imYes"));
		
		for(String key : keySet){
			if("PERNR".equals(key)){
				paramRow.put(key, params.get("name"));
			}else if("BEGDA".equals(key)){
				paramRow.put(key, params.get("startDate"));
			}else if("ENDDA".equals(key)){
				paramRow.put(key, params.get("endDate"));
			}else if("AWART".equals(key)){
				paramRow.put(key, params.get("workType"));
			}else if("BEGUZ".equals(key)){
				paramRow.put(key, params.get("startTime"));
			}else if("ENDUZ".equals(key)){
				paramRow.put(key, params.get("endTime"));
			}else if("STDAZ".equals(key)){
				paramRow.put(key, params.get("workTime"));
			}else if("ALLDF".equals(key)){
				paramRow.put(key, params.get("allDay"));
			}else if("ABWTG".equals(key)){
				paramRow.put(key, params.get("workCnt"));
			}else if("KALTG".equals(key)){
				paramRow.put(key, params.get("calendarCnt"));
			}else{
				paramRow.put(key, params.get(key));
			}
			
			selParamRow.put(key, params.get("SEL_"+key));
		}
		
		rfcParam.put("IM_LIST"    , paramRow);
		rfcParam.put("IM_LIST_SEL", selParamRow);
		//RFC PARAMETER SETTING END
		
		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_PT_019_PROCESS", rfcParam, null, request);
		return result;
	}
	
	@RequestMapping(value = "/familyEventList.do")
	public ModelAndView familyEventList(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {
		
		ModelAndView mav = new ModelAndView("/portal/webmss/diligencePayMng/familyEventList");
			
		User user = (User) getRequestAttribute("ikep.user");
		
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		
		if(!params.containsKey("startDate")){
			params.put("startDate", DateUtil.getToday("yyyy-MM")+"-01");
		}
		
		if(!params.containsKey("endDate")){
			params.put("endDate", DateUtil.getToday("yyyy-MM-dd"));
		}

		if("true".equals((String)params.get("setEmpFlag"))){
			params.put("imPernr", params.get("selEmpNo"));
		}
		
		rfcParam.put("IM_ADMIN", user.getEmpNo());
		rfcParam.put("IM_PERNR", params.get("imPernr"));
		rfcParam.put("IM_BEGDA", params.get("startDate"));
		rfcParam.put("IM_ENDDA", params.get("endDate"));
		rfcParam.put("IM_FIRST", params.get("imFirst"));
		
		Map<?, ?> resultMap = webEssRcv.callRfcFunction("ZHR_RFC_PT_024_ONINIT", rfcParam, null, request);
		
		mav.addObject("resultMap", resultMap);
		mav.addObject("keySet"   , Constant.familyEventListKeySet);
		mav.addObject("params"   , params);
		
		return mav;
	}
	
	@RequestMapping(value = "/familyEventView.do")
	public ModelAndView familyEventView(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {
		
		ModelAndView mav = new ModelAndView();
		
		User user = (User) getRequestAttribute("ikep.user");
		
		String viewName = "/portal/webmss/diligencePayMng/familyEventRegView";
		
		String imFlag = (String) params.get("imFlag");
		
		if("DEL".equals(imFlag) || "DIS".equals(imFlag)){
			viewName = "/portal/webmss/diligencePayMng/familyEventView";
		}
		
		mav.setViewName(viewName);
		
		int etBanwonCnt = Integer.parseInt((String)params.get("etBanwonCnt"));
		int etCaccdCnt = Integer.parseInt((String)params.get("etCaccdCnt"));
		int etGarldCnt = Integer.parseInt((String)params.get("etGarldCnt"));
		
		ArrayList<HashMap<String, Object>> etBanwonList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> etBanwonRow;
		
		ArrayList<HashMap<String, Object>> etCaccdList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> etCaccdRow;
		
		ArrayList<HashMap<String, Object>> etGarldList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> etGarldRow;
		
		if(etBanwonCnt == 1){
			etBanwonRow = new HashMap<String, Object>();
			
			etBanwonRow.put("KEY", params.get("etBanwonKey"));
			etBanwonRow.put("VALUE", params.get("etBanwonValue"));
			
			etBanwonList.add(etBanwonRow);
		}else if(etBanwonCnt > 1){
			for(int i = 0 ; i < etBanwonCnt ; i++){
				etBanwonRow = new HashMap<String, Object>();
				
				etBanwonRow.put("KEY", request.getParameterValues("etBanwonKey")[i]);
				etBanwonRow.put("VALUE", request.getParameterValues("etBanwonValue")[i]);
				
				etBanwonList.add(etBanwonRow);
			}
		}
		
		if(etCaccdCnt == 1){
			etCaccdRow = new HashMap<String, Object>();
			
			etCaccdRow.put("KEY", params.get("etCaccdKey"));
			etCaccdRow.put("VALUE", params.get("etCaccdValue"));
			
			etCaccdList.add(etCaccdRow);
		}else if(etCaccdCnt > 1){
			for(int i = 0 ; i < etCaccdCnt ; i++){
				etCaccdRow = new HashMap<String, Object>();
				
				etCaccdRow.put("KEY", request.getParameterValues("etCaccdKey")[i]);
				etCaccdRow.put("VALUE", request.getParameterValues("etCaccdValue")[i]);
				
				etCaccdList.add(etCaccdRow);
			}
		}
		
		if(etGarldCnt == 1){
			etGarldRow = new HashMap<String, Object>();
			
			etGarldRow.put("KEY", params.get("etGarldKey"));
			etGarldRow.put("VALUE", params.get("etGarldValue"));
			
			etGarldList.add(etGarldRow);
		}else if(etGarldCnt > 1){
			for(int i = 0 ; i < etGarldCnt ; i++){
				etGarldRow = new HashMap<String, Object>();
				
				etGarldRow.put("KEY", request.getParameterValues("etGarldKey")[i]);
				etGarldRow.put("VALUE", request.getParameterValues("etGarldValue")[i]);
				
				etGarldList.add(etGarldRow);
			}
		}
		
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		
		rfcParam.put("IM_BEGDA", params.get("imBegda"));
		rfcParam.put("IM_ENDDA", params.get("imEndda"));
		rfcParam.put("IM_FLAG" , imFlag);
		rfcParam.put("IM_PERNR", user.getEmpNo());
		
		String[] keySet = Constant.familyEventListKeySet;
		HashMap<String, Object> paramRow = new HashMap<String, Object>();

		if("CRE".equals(imFlag)){
			paramRow.put("PERNR"  , params.get("imPernr"));
			rfcParam.put("IM_LIST", paramRow);
		}else{
			for(String key : keySet){
				paramRow.put(key, params.get(key));
				params.put("SEL_"+key, params.get(key));
			}
			rfcParam.put("IM_LIST", paramRow);
		}
		
		Map<?, ?> resultMap = webEssRcv.callRfcFunction("ZHR_RFC_PT_024_ONINIT_DETAIL", rfcParam, null, request);
		
		HashMap<String, Object> resultMapCode = new HashMap<String, Object>();
		
		resultMapCode.put("ET_BANWON", etBanwonList);
		resultMapCode.put("ET_CACCD" , etCaccdList);
		resultMapCode.put("ET_GARLD" , etGarldList);
		
		mav.addObject("resultMapCode", resultMapCode);
		mav.addObject("resultMap"    , resultMap);
		mav.addObject("keySet"       , keySet);
		mav.addObject("params"       , params);
		
		return mav;
	}
	
	@RequestMapping(value = "retrieveFamilyEventPersonList.do")
	public @ResponseBody Map<?, ?> retrieveFamilyEventPersonList(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");
		
		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		rfcParam.put("IM_PERNR", user.getEmpNo());
		rfcParam.put("IM_CACCD", params.get("imCaccd"));
		//RFC PARAMETER SETTING END
		
		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_PT_024_GET_CACRN_LIST", rfcParam, null, request);
		return result;
	}
	
	@RequestMapping(value = "callPT024OnInputDetail.do")
	public @ResponseBody Map<?, ?> callPT024OnInputDetail(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");
		
		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> paramRow = new HashMap<String, Object>();
		
		rfcParam.put("IM_PERNR", user.getEmpNo());
		rfcParam.put("IM_FLAG" , params.get("imFlag"));

		paramRow.put("PERNR", params.get("name"));
		paramRow.put("BEGDA", params.get("requestDate"));
		paramRow.put("ENDDA", params.get("requestDate"));
		paramRow.put("CACCD", params.get("familyEventType"));
		paramRow.put("CACRN", params.get("familyEventPerson"));
		paramRow.put("BEGDT", params.get("startDate"));
		
		rfcParam.put("IM_LIST", paramRow);
		//RFC PARAMETER SETTING END
		
		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_PT_024_ONINPUT_DETAIL", rfcParam, null, request);
		
		return result;
	}
	
	@RequestMapping(value = "familyEventProcess.do")
	public @ResponseBody Map<?, ?> familyEventProcess(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");
		
		String[] keySet = Constant.familyEventListKeySet;
		
		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> paramRow = new HashMap<String, Object>();
		HashMap<String, Object> selParamRow = new HashMap<String, Object>();
		
		rfcParam.put("IM_PERNR"   , user.getEmpNo());
		rfcParam.put("IM_FLAG"    , params.get("imFlag"));
		rfcParam.put("IM_BTRTL"   , params.get("imBtrtl"));
		rfcParam.put("IM_ORGEH"   , params.get("imOrgeh"));
		rfcParam.put("IM_EVENT_ID", params.get("imEventId"));
		rfcParam.put("IM_TA_FLAG" , params.get("imTaFlag"));
		rfcParam.put("IM_TA_COUNT", params.get("imTaCount"));
		rfcParam.put("IM_YES"     , params.get("imYes"));

		String keyPoint = "0";
		
		for(String key : keySet){
			if("PERNR".equals(key)){
				paramRow.put(key, params.get("name"));
			}else if("BEGDA".equals(key) || "ENDDA".equals(key)){
				paramRow.put(key, params.get("requestDate"));
			}else if("CACCD".equals(key)){
				paramRow.put(key, params.get("familyEventType"));
			}else if("CACRN".equals(key)){
				paramRow.put(key, params.get("familyEventPerson"));
			}else if("ENAME".equals(key)){
				paramRow.put(key, params.get("familyEventPersonName"));
			}else if("BEGDT".equals(key)){
				paramRow.put(key, params.get("startDate"));
			}else if("ENDDT".equals(key)){
				paramRow.put(key, params.get("endDate"));
			}else if("VACDT".equals(key)){
				paramRow.put(key, params.get("vacationDay"));
			}else if("CACMT".equals(key)){
				paramRow.put(key, params.get("familyEventAmount"));
			}else if("GARLD".equals(key)){
				paramRow.put(key, params.get("garland"));
			}else{
				if(key.indexOf("TA_PERNR") > -1){
					keyPoint = key.replaceAll("TA_PERNR", "");
					if(params.containsKey("taPernr"+keyPoint) && !"".equals((String)params.get("taPernr"+keyPoint))){
						paramRow.put(key, params.get("taPernr"+keyPoint));
						paramRow.put("TATM"+keyPoint, params.get("taPernrTime"+keyPoint));
					}
				}else if(key.indexOf("TATM") > -1){
					keyPoint = key.replaceAll("TATM", "");
					if(params.containsKey("taPernr"+keyPoint) && !"".equals((String)params.get("taPernr"+keyPoint))){
						paramRow.put(key, params.get("taPernrTime"+keyPoint));
					}
				}else if(key.indexOf("TA_DATE") > -1){
					keyPoint = key.replaceAll("TA_DATE", "");
					if(params.containsKey("taPernr"+keyPoint)){
						paramRow.put(key, params.get("taDate"+keyPoint));
					}
				}else if(key.indexOf("TA_INDEX") > -1){
					keyPoint = key.replaceAll("TA_INDEX", "");
					if(params.containsKey("taPernr"+keyPoint) && !"".equals((String)params.get("taPernr"+keyPoint))){
						paramRow.put(key, keyPoint);
					}
				}else{
					paramRow.put(key, params.get(key));
				}
			}
			
			selParamRow.put(key, params.get("SEL_"+key));
		}
		
		rfcParam.put("IM_LIST"    , paramRow);
		rfcParam.put("IM_LIST_SEL", selParamRow);
		//RFC PARAMETER SETTING END
		
		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_PT_024_PROCESS", rfcParam, null, request);
		return result;
	}
	
	@RequestMapping(value = "/altWorkIndividualList.do")
	public ModelAndView altWorkIndividualList(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {
		
		ModelAndView mav = new ModelAndView("/portal/webmss/diligencePayMng/altWorkIndividualList");
			
		User user = (User) getRequestAttribute("ikep.user");
		
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		
		if(!params.containsKey("startDate")){
			params.put("startDate", DateUtil.getToday("yyyy-MM")+"-01");
		}
		
		if(!params.containsKey("endDate")){
			params.put("endDate", DateUtil.getToday("yyyy-MM-dd"));
		}
		
		if("true".equals((String)params.get("setEmpFlag"))){
			params.put("imPernr", params.get("selEmpNo"));
		}
		
		rfcParam.put("IM_ADMIN", user.getEmpNo());
		rfcParam.put("IM_PERNR", params.get("imPernr"));
		rfcParam.put("IM_BEGDA", params.get("startDate"));
		rfcParam.put("IM_ENDDA", params.get("endDate"));
		rfcParam.put("IM_FIRST", params.get("imFirst"));
		
		Map<?, ?> resultMap = webEssRcv.callRfcFunction("ZHR_RFC_PT_020_ONINIT", rfcParam, null, request);
		
		mav.addObject("resultMap"    , resultMap);
		mav.addObject("keySet"       , Constant.altWorkIndividualListKeySet);
		mav.addObject("params"       , params);
		
		return mav;
	}
	
	@RequestMapping(value = "/altWorkIndividualView.do")
	public ModelAndView altWorkIndividualView(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {
		
		User user = (User) getRequestAttribute("ikep.user");
		
		ModelAndView mav = new ModelAndView();
		
		String viewName = "/portal/webmss/diligencePayMng/altWorkIndividualRegView";
		
		String imFlag = (String) params.get("imFlag");
		
		if("DEL".equals(imFlag) || "DIS".equals(imFlag)){
			viewName = "/portal/webmss/diligencePayMng/altWorkIndividualView";
		}
		
		int etBanwonCnt = Integer.parseInt((String)params.get("etBanwonCnt"));	
		
		ArrayList<HashMap<String, Object>> etBanwonList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> etBanwonRow;
		
		if(etBanwonCnt == 1){
			etBanwonRow = new HashMap<String, Object>();
			
			etBanwonRow.put("KEY", params.get("etBanwonKey"));
			etBanwonRow.put("VALUE", params.get("etBanwonValue"));
			
			etBanwonList.add(etBanwonRow);
		}else if(etBanwonCnt > 1){
			for(int i = 0 ; i < etBanwonCnt ; i++){
				etBanwonRow = new HashMap<String, Object>();
				
				etBanwonRow.put("KEY", request.getParameterValues("etBanwonKey")[i]);
				etBanwonRow.put("VALUE", request.getParameterValues("etBanwonValue")[i]);
				
				etBanwonList.add(etBanwonRow);
			}
		}
		
		String[] keySet = Constant.altWorkIndividualListKeySet;
		
		if(!"CRE".equals(imFlag)){
			for(String key : keySet){
				params.put("SEL_"+key, params.get(key));
			}
		}
		mav.setViewName(viewName);
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		
		resultMap.put("ET_BANWON", etBanwonList);
		
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		
		rfcParam.put("IM_PERNR", "CRE".equals(imFlag) ? user.getEmpNo() : params.get("PERNR"));

		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_PT_020_GET_LISTBOX", rfcParam, null, request);
		
		resultMap.put("ET_VTART" , result.get("ET_VTART"));
		resultMap.put("ET_TPROG" , result.get("ET_TPROG"));
		resultMap.put("ET_SCHKZ" , result.get("ET_SCHKZ"));
		
		
		rfcParam.put("IM_DATUM", params.get("BEGDA"));
		resultMap.put("EX_BEGWK", webEssRcv.callRfcFunction("ZHR_RFC_GET_DAY_OF_WEEK", rfcParam, null, request).get("EX_TEXT"));
		
		rfcParam.put("IM_DATUM", params.get("ENDDA"));
		resultMap.put("EX_ENDWK", webEssRcv.callRfcFunction("ZHR_RFC_GET_DAY_OF_WEEK", rfcParam, null, request).get("EX_TEXT"));
		
		mav.addObject("resultMap", resultMap);
		mav.addObject("keySet"   , keySet);
		mav.addObject("params"   , params);
		
		return mav;
	}
	
	@RequestMapping(value = "callPT020OnInputDetail.do")
	public @ResponseBody Map<?, ?> callPT020OnInputDetail(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {
		
		User user = (User) getRequestAttribute("ikep.user");
		
		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> paramRow = new HashMap<String, Object>();
		
		rfcParam.put("IM_PERNR", user.getEmpNo());
		rfcParam.put("IM_FLAG" , params.get("imFlag"));
		rfcParam.put("IM_ZEITY", params.get("imZeity"));
		rfcParam.put("IM_MOSID", params.get("imMosid"));
		rfcParam.put("IM_MOFID", params.get("imMofid"));
		
		paramRow.put("PERNR", params.get("name"));
		paramRow.put("BEGDA", params.get("startDate"));
		paramRow.put("ENDDA", params.get("endDate"));
		paramRow.put("TPROG", params.get("workSchedule"));
		paramRow.put("SCHKZ", params.get("workScheduleRule"));
		
		rfcParam.put("IM_LIST", paramRow);
		//RFC PARAMETER SETTING END
		
		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_PT_020_ONINPUT_DETAIL", rfcParam, null, request);
		
		return result;
	}
	
	@RequestMapping(value = "altWorkIndividualProcess.do")
	public @ResponseBody Map<?, ?> altWorkIndividualProcess(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");
		
		String[] keySet = Constant.altWorkIndividualListKeySet;
		
		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> paramRow = new HashMap<String, Object>();
		HashMap<String, Object> selParamRow = new HashMap<String, Object>();
		
		rfcParam.put("IM_PERNR"   , user.getEmpNo());
		rfcParam.put("IM_FLAG"    , params.get("imFlag"));
		rfcParam.put("IM_EVENT_ID", params.get("imEventId"));
		rfcParam.put("IM_YES"     , params.get("imYes"));
		
		for(String key : keySet){
			if("PERNR".equals(key)){
				paramRow.put(key, params.get("name"));
			}else if("BEGDA".equals(key)){
				paramRow.put(key, params.get("startDate"));
			}else if("ENDDA".equals(key)){
				paramRow.put(key, params.get("endDate"));
			}else if("VTART".equals(key)){
				paramRow.put(key, params.get("workType"));
			}else if("BEGUZ".equals(key)){
				paramRow.put(key, params.get("startTime"));
			}else if("ENDUZ".equals(key)){
				paramRow.put(key, params.get("endTime"));
			}else if("STDAZ".equals(key)){
				paramRow.put(key, params.get("workTime"));
			}else if("TPROG".equals(key)){
				paramRow.put(key, params.get("workSchedule"));
			}else if("SCHKZ".equals(key)){
				paramRow.put(key, params.get("workScheduleRule"));
			}else if("MOFID".equals(key)){
				paramRow.put(key, params.get("holidayCalendar"));
			}else if("ZEITY".equals(key)){
				paramRow.put(key, params.get("empGroup"));
			}else if("MOSID".equals(key)){
				paramRow.put(key, params.get("personGroup"));
			}else{
				paramRow.put(key, params.get(key));
			}
			
			selParamRow.put(key, params.get("SEL_"+key));
		}
		
		rfcParam.put("IM_LIST"    , paramRow);
		rfcParam.put("IM_LIST_SEL", selParamRow);
		//RFC PARAMETER SETTING END
		
		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_PT_020_PROCESS", rfcParam, null, request);
		return result;
	}
	
	@RequestMapping(value = "/altWorkBatchList.do")
	public ModelAndView altWorkBatchList(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {
		
		ModelAndView mav = new ModelAndView("/portal/webmss/diligencePayMng/altWorkBatchList");
			
		User user = (User) getRequestAttribute("ikep.user");
		
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> rfcParamList = new HashMap<String, Object>();
		
		if(!params.containsKey("imBegda")){
			params.put("imBegda", DateUtil.getToday("yyyy-MM-dd"));
		}
		
		if(params.containsKey("moveDate")){
			if("prev".equals((String)params.get("moveDate"))){
				params.put("imBegda", DateUtil.getPrevDate(((String)params.get("imBegda")).replaceAll("-", ""), 1, "yyyy-MM-dd"));
			}else if("next".equals((String)params.get("moveDate"))){
				params.put("imBegda", DateUtil.getNextDate(((String)params.get("imBegda")).replaceAll("-", ""), 1, "yyyy-MM-dd"));
			}
		}
		
		rfcParam.put("IM_ADMIN", user.getEmpNo());
		rfcParam.put("IM_BEGDA", params.get("imBegda"));
		rfcParam.put("IM_FIRST", params.get("imFirst"));
		int etMessageCnt = params.containsKey("etMessageCnt") ? Integer.parseInt((String) params.get("etMessageCnt")) : 0 ;
		
		String[] keySet = Constant.altWorkBatchListKeySet;
		
		if(etMessageCnt > 0){
			
			ArrayList<HashMap<String, Object>> etMessageList = new ArrayList<HashMap<String, Object>>();
			HashMap<String, Object> etMessageRow = null;
			
			if(etMessageCnt == 1){
				etMessageRow = new HashMap<String, Object>();
				for(String key : keySet){
					etMessageRow.put(key, params.get("etMessage_"+key));
				}
				etMessageList.add(etMessageRow);
			}else if(etMessageCnt > 1){
				for(int i = 0 ; i < etMessageCnt ; i++){
					etMessageRow = new HashMap<String, Object>();
					for(String key : keySet){
						etMessageRow.put(key, request.getParameterValues("etMessage_"+key)[i]);
					}
					etMessageList.add(etMessageRow);
				}
			}
			
			rfcParamList.put("IT_MESSAGE", etMessageList);
		}
		
		
		Map<?, ?> resultMap = webEssRcv.callRfcFunction("ZHR_RFC_PT_021_ONINIT", rfcParam, rfcParamList, request);
		
		mav.addObject("resultMap", resultMap);
		mav.addObject("keySet"   , keySet);
		mav.addObject("params"   , params);
		
		return mav;
	}
	
	@RequestMapping(value = "retrieveSchkzList.do")
	public @ResponseBody Map<?, ?> retrieveSchkzList(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {
		
		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		
		rfcParam.put("IM_PERNR", params.get("imPernr"));
		rfcParam.put("IM_DATUM", params.get("imDatum"));
		//RFC PARAMETER SETTING END
		
		return webEssRcv.callRfcFunction("ZHR_RFC_PT_021_GET_LISTBOX2", rfcParam, null, request);
	}
	
	@RequestMapping(value = "altWorkBatchProcess.do")
	public @ResponseBody Map<?, ?> altWorkBatchProcess(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {
		
		String[] keySet = Constant.altWorkBatchListKeySet;
		
		int rowCnt = Integer.parseInt((String) params.get("rowCnt"));
		
		ArrayList<HashMap<String, Object>> itList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> itRow;
		
		if(rowCnt == 1){
			itRow = new HashMap<String, Object>();
			for(String key : keySet){
				itRow.put(key, params.get(key));
			}
			itList.add(itRow);
		}else if(rowCnt > 1){
			for(int i = 0 ; i < rowCnt ; i++){
				itRow = new HashMap<String, Object>();
				for(String key : keySet){
					itRow.put(key, request.getParameterValues(key)[i]);
				}
				itList.add(itRow);
			}
		}
		
		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> rfcParamList = new HashMap<String, Object>();
		
		rfcParam.put("IM_BEGDA"   , params.get("imBegda"));
		rfcParam.put("IM_EVENT_ID", params.get("imEventId"));
		rfcParam.put("IM_FLAG"    , params.get("imFlag"));
		
		rfcParamList.put("IT_LIST", itList);
		//RFC PARAMETER SETTING END
		
		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_PT_021_PROCESS", rfcParam, rfcParamList, request);
		
		return result;
	}
	
	@RequestMapping(value = "/overtimeIndividualList.do")
	public ModelAndView overtimeIndividualList(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {
		
		ModelAndView mav = new ModelAndView("/portal/webmss/diligencePayMng/overtimeIndividualList");
			
		User user = (User) getRequestAttribute("ikep.user");
		
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		
		if(!params.containsKey("startDate")){
			params.put("startDate", DateUtil.getToday("yyyy-MM")+"-01");
		}
		
		if(!params.containsKey("endDate")){
			params.put("endDate", DateUtil.getToday("yyyy-MM-dd"));
		}
		
		if("true".equals((String)params.get("setEmpFlag"))){
			params.put("imPernr", params.get("selEmpNo"));
		}
		
		rfcParam.put("IM_ADMIN", user.getEmpNo());
		rfcParam.put("IM_PERNR", params.get("imPernr"));
		rfcParam.put("IM_BEGDA", params.get("startDate"));
		rfcParam.put("IM_ENDDA", params.get("endDate"));
		rfcParam.put("IM_FIRST", params.get("imFirst"));
		
		Map<?, ?> resultMap = webEssRcv.callRfcFunction("ZHR_RFC_PT_022_ONINIT", rfcParam, null, request);
		
		mav.addObject("resultMap"    , resultMap);
		mav.addObject("keySet"       , Constant.overtimeIndividualListKeySet);
		mav.addObject("params"       , params);
		
		return mav;
	}
	
	@RequestMapping(value = "/overtimeIndividualListPopup.do")
	public ModelAndView overtimeIndividualListPopup(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {
		
		ModelAndView mav = new ModelAndView("/portal/webmss/diligencePayMng/overtimeIndividualListPopup");
			
		User user = (User) getRequestAttribute("ikep.user");
		
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		
		if(!params.containsKey("startDate")){
			params.put("startDate", DateUtil.getToday("yyyy-MM")+"-01");
		}
		
		if(!params.containsKey("endDate")){
			params.put("endDate", DateUtil.getToday("yyyy-MM-dd"));
		}
		
		if("true".equals((String)params.get("setEmpFlag"))){
			params.put("imPernr", params.get("selEmpNo"));
		}
		
		rfcParam.put("IM_ADMIN", user.getEmpNo());
		rfcParam.put("IM_PERNR", params.get("imPernr"));
		rfcParam.put("IM_BEGDA", params.get("startDate"));
		rfcParam.put("IM_ENDDA", params.get("endDate"));
		rfcParam.put("IM_FIRST", params.get("imFirst"));
		
		Map<?, ?> resultMap = webEssRcv.callRfcFunction("ZHR_RFC_PT_022_ONINIT", rfcParam, null, request);
		
		mav.addObject("resultMap"    , resultMap);
		mav.addObject("keySet"       , Constant.overtimeIndividualListKeySet);
		mav.addObject("params"       , params);
		
		return mav;
	}
	
	@RequestMapping(value = "/overtimeIndividualView.do")
	public ModelAndView overtimeIndividualView(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {
		
		ModelAndView mav = new ModelAndView();
		
		String viewName = "/portal/webmss/diligencePayMng/overtimeIndividualRegView";
		
		String imFlag = (String) params.get("imFlag");
		
		if("DEL".equals(imFlag) || "DIS".equals(imFlag)){
			viewName = "/portal/webmss/diligencePayMng/overtimeIndividualView";
		}
		
		int etBanwonCnt = Integer.parseInt((String)params.get("etBanwonCnt"));
		int etVerslCnt = Integer.parseInt((String)params.get("etVerslCnt"));
		int etPreasCnt = Integer.parseInt((String)params.get("etPreasCnt"));
		
		ArrayList<HashMap<String, Object>> etBanwonList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> etBanwonRow;
		
		ArrayList<HashMap<String, Object>> etVerslList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> etVerslRow;
		
		ArrayList<HashMap<String, Object>> etPreasList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> etPreasRow;
		
		if(etBanwonCnt == 1){
			etBanwonRow = new HashMap<String, Object>();
			
			etBanwonRow.put("KEY", params.get("etBanwonKey"));
			etBanwonRow.put("VALUE", params.get("etBanwonValue"));
			
			etBanwonList.add(etBanwonRow);
		}else if(etBanwonCnt > 1){
			for(int i = 0 ; i < etBanwonCnt ; i++){
				etBanwonRow = new HashMap<String, Object>();
				
				etBanwonRow.put("KEY", request.getParameterValues("etBanwonKey")[i]);
				etBanwonRow.put("VALUE", request.getParameterValues("etBanwonValue")[i]);
				
				etBanwonList.add(etBanwonRow);
			}
		}
		
		if(etVerslCnt == 1){
			etVerslRow = new HashMap<String, Object>();
			
			etVerslRow.put("KEY", params.get("etVerslKey"));
			etVerslRow.put("VALUE", params.get("etVerslValue"));
			
			etVerslList.add(etVerslRow);
		}else if(etVerslCnt > 1){
			for(int i = 0 ; i < etVerslCnt ; i++){
				etVerslRow = new HashMap<String, Object>();
				
				etVerslRow.put("KEY", request.getParameterValues("etVerslKey")[i]);
				etVerslRow.put("VALUE", request.getParameterValues("etVerslValue")[i]);
				
				etVerslList.add(etVerslRow);
			}
		}
		
		if(etPreasCnt == 1){
			etPreasRow = new HashMap<String, Object>();
			
			etPreasRow.put("KEY", params.get("etPreasKey"));
			etPreasRow.put("VALUE", params.get("etPreasValue"));
			
			etPreasList.add(etPreasRow);
		}else if(etPreasCnt > 1){
			for(int i = 0 ; i < etPreasCnt ; i++){
				etPreasRow = new HashMap<String, Object>();
				
				etPreasRow.put("KEY", request.getParameterValues("etPreasKey")[i]);
				etPreasRow.put("VALUE", request.getParameterValues("etPreasValue")[i]);
				
				etPreasList.add(etPreasRow);
			}
		}
		
		String[] keySet = Constant.overtimeIndividualListKeySet;
		
		if(!"CRE".equals(imFlag)){
			for(String key : keySet){
				params.put("SEL_"+key, params.get(key));
			}
		}
		mav.setViewName(viewName);
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		
		resultMap.put("ET_BANWON", etBanwonList);
		resultMap.put("ET_VERSL" , etVerslList);
		resultMap.put("ET_PREAS" , etPreasList);
		
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		
		rfcParam.put("IM_DATUM", params.get("BEGDA"));
		resultMap.put("EX_BEGWK", webEssRcv.callRfcFunction("ZHR_RFC_GET_DAY_OF_WEEK", rfcParam, null, request).get("EX_TEXT"));
		
		mav.addObject("resultMap", resultMap);
		mav.addObject("keySet"   , keySet);
		mav.addObject("params"   , params);
		
		return mav;
	}
	
	@RequestMapping(value = "callPT022OnInputDetail.do")
	public @ResponseBody Map<?, ?> callPT022OnInputDetail(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");
		
		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> paramRow = new HashMap<String, Object>();
		
		rfcParam.put("IM_PERNR", user.getEmpNo());
		rfcParam.put("IM_FLAG" , params.get("imFlag"));
		
		paramRow.put("PERNR", params.get("name"));
		paramRow.put("BEGDA", params.get("date"));
		paramRow.put("ENDDA", params.get("date"));
		paramRow.put("BEGUZ", params.get("startTime"));
		paramRow.put("ENDUZ", params.get("endTime"));
		paramRow.put("VERSL", params.get("compensationType"));
		paramRow.put("PREAS", params.get("overtimeReason"));
		
		rfcParam.put("IM_LIST", paramRow);

		//RFC PARAMETER SETTING END
		
		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_PT_022_ONINPUT_DETAIL", rfcParam, null, request);
		
		return result;
	}
	
	@RequestMapping(value = "overtimeIndividualProcess.do")
	public @ResponseBody Map<?, ?> overtimeIndividualProcess(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");
		
		String[] keySet = Constant.overtimeIndividualListKeySet;
		
		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> paramRow = new HashMap<String, Object>();
		HashMap<String, Object> selParamRow = new HashMap<String, Object>();
		
		rfcParam.put("IM_PERNR"   , user.getEmpNo());
		rfcParam.put("IM_FLAG"    , params.get("imFlag"));
		rfcParam.put("IM_EVENT_ID", params.get("imEventId"));
		rfcParam.put("IM_YES"     , params.get("imYes"));
		
		for(String key : keySet){
			if("PERNR".equals(key)){
				paramRow.put(key, params.get("name"));
			}else if("BEGDA".equals(key) || "ENDDA".equals(key)){
				paramRow.put(key, params.get("date"));
			}else if("BEGUZ".equals(key)){
				paramRow.put(key, params.get("startTime"));
			}else if("ENDUZ".equals(key)){
				paramRow.put(key, params.get("endTime"));
			}else if("VTKEN".equals(key)){
				paramRow.put(key, params.get("allDay"));
			}else if("VERSL".equals(key)){
				paramRow.put(key, params.get("compensationType"));
			}else if("PREAS".equals(key)){
				paramRow.put(key, params.get("overtimeReason"));
			}else if("PRTXT".equals(key)){
				paramRow.put(key, params.get("reason"));
			}else if("PBEG1".equals(key)){
				paramRow.put(key, params.get("restStartTime1"));
			}else if("PEND1".equals(key)){
				paramRow.put(key, params.get("restEndTime1"));
			}else if("PBEG2".equals(key)){
				paramRow.put(key, params.get("restStartTime2"));
			}else if("PEND2".equals(key)){
				paramRow.put(key, params.get("restEndTime2"));
			}else if("PBEG3".equals(key)){
				paramRow.put(key, params.get("restStartTime3"));
			}else if("PEND3".equals(key)){
				paramRow.put(key, params.get("restEndTime3"));
			}else if("PBEG4".equals(key)){
				paramRow.put(key, params.get("restStartTime4"));
			}else if("PEND4".equals(key)){
				paramRow.put(key, params.get("restEndTime4"));
			}else{
				paramRow.put(key, params.get(key));
			}
			
			selParamRow.put(key, params.get("SEL_"+key));
		}
		
		rfcParam.put("IM_LIST"    , paramRow);
		rfcParam.put("IM_LIST_SEL", selParamRow);
		//RFC PARAMETER SETTING END
		
		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_PT_022_PROCESS", rfcParam, null, request);
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/workHistoryPop.do")
	public ModelAndView workHistoryPop(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {
		
		ModelAndView mav = new ModelAndView("/portal/webmss/diligencePayMng/workHistoryPop");

		User user = (User) getRequestAttribute("ikep.user");
		
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		
		rfcParam.put("IM_ADMIN", user.getEmpNo());
		rfcParam.put("IM_BEGDA", params.get("startDate"));
		rfcParam.put("IM_ENDDA", params.get("endDate"));
		rfcParam.put("IM_FIRST", "X");

		Map<?, ?> resultMap = webEssRcv.callRfcFunction("ZHR_RFC_PT_027_ONINIT", rfcParam, null, request);
		
		ArrayList<HashMap<String, Object>> etPernr = (ArrayList<HashMap<String, Object>>) resultMap.get("ET_PERNR");
		HashMap<String, Object> pernrRow;
		
		ArrayList<HashMap<String, Object>> etCalendar = (ArrayList<HashMap<String, Object>>) resultMap.get("ET_CALENDAR");
		HashMap<String, Object> calendarRow;
		
		ArrayList<HashMap<String, Object>> etList = (ArrayList<HashMap<String, Object>>) resultMap.get("ET_LIST");
		HashMap<String, Object> listRow;
		
		ArrayList<HashMap<String, Object>> mergeList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> mergeRow;
		
		String aPernr = "";  //etPernr.PERNR
		String bPernr = "";  //etList.PERNR
		
		String aDate = "";  //etCalendar.DATE
		String bDate = "";  //etList.BEGDA
		
		for(int i = 0 ; i < etPernr.size() ; i++){
			mergeRow = new HashMap<String, Object>();
			
			pernrRow = etPernr.get(i);
			
			mergeRow.put("ENAME", pernrRow.get("ENAME"));
			mergeRow.put("ORGTX", pernrRow.get("ORGTX"));
			mergeRow.put("PLSTX", pernrRow.get("PLSTX"));
			mergeRow.put("RTEXT", pernrRow.get("RTEXT"));
			
			aPernr = (String)etPernr.get(i).get("PERNR");
			
			for(int j = 0 ; j < etCalendar.size() ; j++){
				
				calendarRow = etCalendar.get(j);
				
				aDate = (String)calendarRow.get("DATE");
				
				for(int x = 0 ; x < etList.size() ; x++){
					
					listRow = etList.get(x);
					
					bPernr = (String)listRow.get("PERNR");
					bDate = (String)listRow.get("BEGDA");
					
					if(aPernr.equals(bPernr) && aDate.equals(bDate)){
						mergeRow.put("TTEXT"+j, listRow.get("TTEXT")); //근무일정
						mergeRow.put("WKTIM"+j, listRow.get("WKTIM")); //기본근로
						mergeRow.put("OVTM"+j , listRow.get("OVTM")); //연장근로
						mergeRow.put("TOTAL"+j, listRow.get("TOTAL")); //계(기본+연장)
						
						if("DETAIL".equals((String)params.get("viewType"))){
							mergeRow.put("ATEXT"+j, listRow.get("ATEXT")); //휴가-유형
							mergeRow.put("TATX1"+j, listRow.get("TATX1")); //휴가-대근자1
							mergeRow.put("TATX2"+j, listRow.get("TATX2")); //휴가-대근자2
							mergeRow.put("TATX3"+j, listRow.get("TATX3")); //휴가-대근자3
							mergeRow.put("TATX4"+j, listRow.get("TATX4")); //휴가-대근자4
							
							mergeRow.put("VA_ENAME"+j, listRow.get("VA_ENAME")); //대근O/T - 휴가자
							mergeRow.put("VATM"+j, listRow.get("VATM")); //대근O/T - 대근시간
						}
					}
				}
			}
			
			mergeList.add(mergeRow);
		}
		
		mav.addObject("resultMap", resultMap);
		mav.addObject("mergeList", mergeList);
		mav.addObject("params"   , params);
		
		return mav;
	}
	
	@RequestMapping(value = "/overtimeBatchList.do")
	public ModelAndView overtimeBatchList(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {
		
		ModelAndView mav = new ModelAndView("/portal/webmss/diligencePayMng/overtimeBatchList");
			
		User user = (User) getRequestAttribute("ikep.user");
		
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> rfcParamList = new HashMap<String, Object>();
		
		if(!params.containsKey("imBegda")){
			params.put("imBegda", DateUtil.getToday("yyyy-MM-dd"));
		}
		
		if(params.containsKey("moveDate")){
			if("prev".equals((String)params.get("moveDate"))){
				params.put("imBegda", DateUtil.getPrevDate(((String)params.get("imBegda")).replaceAll("-", ""), 1, "yyyy-MM-dd"));
			}else if("next".equals((String)params.get("moveDate"))){
				params.put("imBegda", DateUtil.getNextDate(((String)params.get("imBegda")).replaceAll("-", ""), 1, "yyyy-MM-dd"));
			}
		}
		
		rfcParam.put("IM_ADMIN", user.getEmpNo());
		rfcParam.put("IM_BEGDA", params.get("imBegda"));
		rfcParam.put("IM_FIRST", params.get("imFirst"));
		
		int etMessageCnt = params.containsKey("etMessageCnt") ? Integer.parseInt((String) params.get("etMessageCnt")) : 0 ;
		
		String[] keySet = Constant.overtimeBatchListKeySet;
		
		if(etMessageCnt > 0){
			
			ArrayList<HashMap<String, Object>> etMessageList = new ArrayList<HashMap<String, Object>>();
			HashMap<String, Object> etMessageRow = null;
			
			if(etMessageCnt == 1){
				etMessageRow = new HashMap<String, Object>();
				for(String key : keySet){
					etMessageRow.put(key, params.get("etMessage_"+key));
				}
				etMessageList.add(etMessageRow);
			}else if(etMessageCnt > 1){
				for(int i = 0 ; i < etMessageCnt ; i++){
					etMessageRow = new HashMap<String, Object>();
					for(String key : keySet){
						etMessageRow.put(key, request.getParameterValues("etMessage_"+key)[i]);
					}
					etMessageList.add(etMessageRow);
				}
			}
			rfcParamList.put("IT_MESSAGE", etMessageList);
		}
		
		Map<?, ?> resultMap = webEssRcv.callRfcFunction("ZHR_RFC_PT_023_ONINIT", rfcParam, rfcParamList, request);
		
		mav.addObject("resultMap", resultMap);
		mav.addObject("keySet"   , keySet);
		mav.addObject("params"   , params);
		
		return mav;
	}
	
	@RequestMapping(value = "overtimeBatchProcess.do")
	public @ResponseBody Map<?, ?> overtimeBatchProcess(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {
		
		String[] keySet = Constant.overtimeBatchListKeySet;
		
		User user = (User) getRequestAttribute("ikep.user");
		
		int rowCnt = Integer.parseInt((String) params.get("rowCnt"));
		
		ArrayList<HashMap<String, Object>> itList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> itRow;
		
		HashMap<String, Object> inputRow = new HashMap<String, Object>();
		
		if(rowCnt == 1){
			itRow = new HashMap<String, Object>();
			for(String key : keySet){
				itRow.put(key, params.get(key));
			}
			itList.add(itRow);
		}else if(rowCnt > 1){
			for(int i = 0 ; i < rowCnt ; i++){
				itRow = new HashMap<String, Object>();
				for(String key : keySet){
					itRow.put(key, request.getParameterValues(key)[i]);
				}
				itList.add(itRow);
			}
		}
		
		for(String key : keySet){
			if("BEGUZ".equals(key)){
				inputRow.put(key, params.get("startTime"));
			}else if("ENDUZ".equals(key)){
				inputRow.put(key, params.get("endTime"));
			}else if("VTKEN".equals(key)){
				inputRow.put(key, params.get("allDay"));
			}else if("VERSL".equals(key)){
				inputRow.put(key, params.get("compensationType"));
			}else if("VTEXT".equals(key)){
				inputRow.put(key, params.get("compensationTypeText"));
			}else if("PREAS".equals(key)){
				inputRow.put(key, params.get("overtimeReason"));
			}else if("PRETX".equals(key)){
				inputRow.put(key, params.get("overtimeReasonText"));
			}else if("PRTXT".equals(key)){
				inputRow.put(key, params.get("reason"));
			}else if("PBEG1".equals(key)){
				inputRow.put(key, params.get("restStartTime1"));
			}else if("PEND1".equals(key)){
				inputRow.put(key, params.get("restEndTime1"));
			}else if("PBEG2".equals(key)){
				inputRow.put(key, params.get("restStartTime2"));
			}else if("PEND2".equals(key)){
				inputRow.put(key, params.get("restEndTime2"));
			}else if("PBEG3".equals(key)){
				inputRow.put(key, params.get("restStartTime3"));
			}else if("PEND3".equals(key)){
				inputRow.put(key, params.get("restEndTime3"));
			}else if("PBEG4".equals(key)){
				inputRow.put(key, params.get("restStartTime4"));
			}else if("PEND4".equals(key)){
				inputRow.put(key, params.get("restEndTime4"));
			}else{
				inputRow.put(key, params.get(key));
			}
		}
		
		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> rfcParamList = new HashMap<String, Object>();
		
		rfcParam.put("IM_BEGDA"   , params.get("imBegda"));
		rfcParam.put("IM_EVENT_ID", params.get("imEventId"));
		rfcParam.put("IM_FLAG"    , params.get("imFlag"));
		rfcParam.put("IM_PERNR"   , user.getEmpNo());
		rfcParam.put("IM_INPUT"   , inputRow);
		
		
		rfcParamList.put("IT_LIST", itList);
		//RFC PARAMETER SETTING END
		
		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_PT_023_PROCESS", rfcParam, rfcParamList, request);
		
		return result;
	}
	
	@RequestMapping(value = "/settlementLoginForm.do")
	public ModelAndView settlementLoginForm(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/portal/webmss/diligencePayMng/settlementLoginForm");
		
		if(params.containsKey("resultMap")){
			mav.addObject("resultMap", params.get("resultMap"));
		}
		
		return mav;
	}
	
	@RequestMapping(value = "/settlementLogin.do")
	public ModelAndView settlementLogin(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {
		
		ModelAndView mav = new ModelAndView();
		
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		
		rfcParam.put("IM_PERNR"  , params.get("imPernr"));
		rfcParam.put("IM_PASSWD" , params.get("imPasswd"));
		rfcParam.put("IM_ADMINID", params.get("imAdminid"));
		
		Map<?, ?> resultMap = webEssRcv.callRfcFunction("ZHR_RFC_PY_003_LOGIN99", rfcParam, null, request);
		
		String exResult = (String)resultMap.get("EX_RESULT");
		
		if("S".equals(exResult)){
			
			HttpSession session = request.getSession(false);
			
			if(session != null){
				session.invalidate();
			}
			
			session = request.getSession(true);
			
			session.setAttribute("imPernr", params.get("imPernr"));
			session.setAttribute("imPass" , resultMap.get("EX_PASSWD"));
			
			return yearEndSettlementRegView(params, request);
		}else{
			
			mav.setViewName("/portal/webmss/diligencePayMng/settlementLoginForm");
		
			mav.addObject("resultMap", resultMap);
			mav.addObject("params"   , params);
			
			return mav;
		}
	}
	
	@RequestMapping(value = "/borrowPop.do")
	public ModelAndView borrowPop(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/webmss/diligencePayMng/borrowPop");

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
	
	@RequestMapping(value = "/callPY003OninputPop18.do")
	public @ResponseBody Map<?, ?> callPY003OninputPop18(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		HttpSession session = request.getSession(false);
		
		if(session.getAttribute("imPernr") == null || session.getAttribute("imPass") == null){
			try{
				throw new Exception("Disconnect Session..");
			}catch(Exception e){
				e.printStackTrace();
			}
		}

		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> rfcParamList = new HashMap<String, Object>();

		String pensionKeySet[] = Constant.rentKeySet;

		rfcParam.put("IM_PERNR"   , session.getAttribute("imPernr"));
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
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/yearEndSettlementRegView.do")
	public ModelAndView yearEndSettlementRegView(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {
		
		HttpSession session = request.getSession(false);
		
		if(session.getAttribute("imPernr") == null || session.getAttribute("imPass") == null){
			
			HashMap<String, Object> resultMap = new HashMap<String, Object>();
			
			resultMap.put("EX_RESULT" , "E");
			resultMap.put("EX_MESSAGE", "세션 연결이 끊어졌습니다. 다시 로그인 해 주세요.");
			
			params.put("resultMap", resultMap);
			
			return settlementLoginForm(params, request);
		}

		ModelAndView mav = new ModelAndView("/portal/webmss/diligencePayMng/yearEndSettlementRegView");
		
		if(!params.containsKey("tabIndex")){
			params.put("tabIndex", "0");
		}
		
		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		rfcParam.put("IM_PERNR", session.getAttribute("imPernr"));
		rfcParam.put("IM_YEAR" , DateUtil.getToday("yyyy"));
		rfcParam.put("IM_PASS" , session.getAttribute("imPass"));
		//RFC PARAMETER SETTING END

		Map<?, ?> resultMap = webEssRcv.callRfcFunction("ZHR_RFC_PY_003_ONINIT", rfcParam, null, request);
		
		ArrayList<HashMap<String, Object>> etPerFunc = (ArrayList<HashMap<String, Object>>) resultMap.get("ET_PERSON_FUNCTION");
		
		ArrayList<Map<?, ?>> exLayoutList = new ArrayList<Map<?, ?>>();
		ArrayList<Map<?, ?>> exPerFuncList = new ArrayList<Map<?, ?>>();
		
		HashMap<String, Object> layoutParam = new HashMap<String, Object>();
		
		layoutParam.put("IM_YEAR"    , resultMap.get("EX_YEAR"));
		layoutParam.put("IM_PERNR"   , session.getAttribute("imPernr"));
		layoutParam.put("IM_PASS"    , session.getAttribute("imPass"));
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

		HttpSession session = request.getSession(false);
		
		if(session.getAttribute("imPernr") == null || session.getAttribute("imPass") == null){
			try{
				throw new Exception("Disconnect Session..");
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		String[] perFuncKeySet = Constant.perFuncKeySet;

		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		
		rfcParam.put("IM_YEAR"    , params.get("EX_YEAR"));
		rfcParam.put("IM_PERNR"   , session.getAttribute("imPernr"));
		rfcParam.put("IM_PASS"    , session.getAttribute("imPass"));
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

		ModelAndView mav = new ModelAndView("/portal/webmss/diligencePayMng/addressRegViewPop");
		
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

		HttpSession session = request.getSession(false);
		
		if(session.getAttribute("imPernr") == null || session.getAttribute("imPass") == null){
			try{
				throw new Exception("Disconnect Session..");
			}catch(Exception e){
				e.printStackTrace();
			}
		}

		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		
		String[] addressKeySet = Constant.addressListKeySet;
		
		rfcParam.put("IM_PERNR"   , session.getAttribute("imPernr"));
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

		ModelAndView mav = new ModelAndView("/portal/webmss/diligencePayMng/familyAddPop");
		
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

		HttpSession session = request.getSession(false);
		
		if(session.getAttribute("imPernr") == null || session.getAttribute("imPass") == null){
			try{
				throw new Exception("Disconnect Session..");
			}catch(Exception e){
				e.printStackTrace();
			}
		}

		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		
		rfcParam.put("IM_PERNR"   , session.getAttribute("imPernr"));
		rfcParam.put("IM_ZYEAR"   , params.get("imZyear"));
		rfcParam.put("IM_EVENT_ID", "SAVE");
		
		HashMap<String, Object> paramRow = new HashMap<String, Object>();
		
		paramRow.put("ZYEAR" , params.get("imZyear"));
		paramRow.put("PERNR" , session.getAttribute("imPernr"));
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

		ModelAndView mav = new ModelAndView("/portal/webmss/diligencePayMng/familyDelPop");
		
		HttpSession session = request.getSession(false);
		
		if(session.getAttribute("imPernr") == null || session.getAttribute("imPass") == null){
			HashMap<String, Object> resultMap = new HashMap<String, Object>();
			
			resultMap.put("EX_RESULT" , "E");
			resultMap.put("EX_MESSAGE", "세션 연결이 끊어졌습니다. 다시 로그인 해 주세요.");
			
			params.put("resultMap", resultMap);
			
			return settlementLoginForm(params, request);
		}
		
		String[] perFuncKeySet = Constant.perFuncKeySet;
		
		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		rfcParam.put("IM_PERNR", session.getAttribute("imPernr"));
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

		HttpSession session = request.getSession(false);
		
		if(session.getAttribute("imPernr") == null || session.getAttribute("imPass") == null){
			try{
				throw new Exception("Disconnect Session..");
			}catch(Exception e){
				e.printStackTrace();
			}
		}

		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		
		rfcParam.put("IM_PERNR"   , session.getAttribute("imPernr"));
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

		ModelAndView mav = new ModelAndView("/portal/webmss/diligencePayMng/workPlacePop");
		
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

		HttpSession session = request.getSession(false);
		
		if(session.getAttribute("imPernr") == null || session.getAttribute("imPass") == null){
			try{
				throw new Exception("Disconnect Session..");
			}catch(Exception e){
				e.printStackTrace();
			}
		}

		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> rfcParamList = new HashMap<String, Object>();
		
		rfcParam.put("IM_PERNR"   , session.getAttribute("imPernr"));
		rfcParam.put("IM_EVENT_ID", params.get("imEventId"));
		
		int paramCnt = Integer.parseInt((String)params.get("paramCnt"));
		ArrayList<HashMap<String, Object>> itLastList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> itLastRow;
		
		if(paramCnt == 1){
			itLastRow = new HashMap<String, Object>();
			
			itLastRow.put("PERNR", session.getAttribute("imPernr"));
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
				
				itLastRow.put("PERNR", session.getAttribute("imPernr"));
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

		ModelAndView mav = new ModelAndView("/portal/webmss/diligencePayMng/interestPop");
		
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

		HttpSession session = request.getSession(false);
		
		if(session.getAttribute("imPernr") == null || session.getAttribute("imPass") == null){
			try{
				throw new Exception("Disconnect Session..");
			}catch(Exception e){
				e.printStackTrace();
			}
		}

		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> rfcParamList = new HashMap<String, Object>();
		
		rfcParam.put("IM_PERNR"   , session.getAttribute("imPernr"));
		rfcParam.put("IM_EVENT_ID", params.get("imEventId"));
		rfcParam.put("IM_ZYEAR"   , params.get("imZyear"));
		rfcParam.put("IM_REGNO"   , params.get("imRegno"));
		
		int paramCnt = Integer.parseInt((String)params.get("paramCnt"));
		ArrayList<HashMap<String, Object>> itRepayList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> itRepayRow;
		
		if(paramCnt == 1){
			itRepayRow = new HashMap<String, Object>();
			
			itRepayRow.put("ZYEAR"   , params.get("imZyear"));
			itRepayRow.put("PERNR"   , session.getAttribute("imPernr"));
			itRepayRow.put("ETNAM"   , params.get("etnam"));
			itRepayRow.put("ET_RCBEG", params.get("etRcbeg"));
			itRepayRow.put("ET_RCEND", params.get("etRcend"));
			itRepayRow.put("ET_FIXRT", params.get("etFixrt"));
			itRepayRow.put("ET_NODEF", params.get("etNodef"));
			itRepayRow.put("ET_LNPRD", params.get("etLnprd"));
			itRepayList.add(itRepayRow);
		}else if(paramCnt > 1){
			for(int i = 0 ; i < paramCnt ; i++){
				itRepayRow = new HashMap<String, Object>();
				
				itRepayRow.put("ZYEAR"   , params.get("imZyear"));
				itRepayRow.put("PERNR"   , session.getAttribute("imPernr"));
				itRepayRow.put("ETNAM"   , request.getParameterValues("etnam")[i]);
				itRepayRow.put("ET_RCBEG", request.getParameterValues("etRcbeg")[i]);
				itRepayRow.put("ET_RCEND", request.getParameterValues("etRcend")[i]);
				itRepayRow.put("ET_FIXRT", request.getParameterValues("etFixrt")[i]);
				itRepayRow.put("ET_NODEF", request.getParameterValues("etNodef")[i]);
				itRepayRow.put("ET_LNPRD", request.getParameterValues("etLnprd")[i]);
				
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

		ModelAndView mav = new ModelAndView("/portal/webmss/diligencePayMng/creditPop");
		
		HttpSession session = request.getSession(false);
		
		if(session.getAttribute("imPernr") == null || session.getAttribute("imPass") == null){
			HashMap<String, Object> resultMap = new HashMap<String, Object>();
			
			resultMap.put("EX_RESULT" , "E");
			resultMap.put("EX_MESSAGE", "세션 연결이 끊어졌습니다. 다시 로그인 해 주세요.");
			
			params.put("resultMap", resultMap);
			
			return settlementLoginForm(params, request);
		}
		
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
		layoutParam.put("IM_PERNR"   , session.getAttribute("imPernr"));
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
		
		rfcParam.put("IM_PERNR", session.getAttribute("imPernr"));
		rfcParam.put("IM_ZYEAR", params.get("EX_YEAR"));
		
		Map<? ,?> resultCode = webEssRcv.callRfcFunction("ZHR_RFC_PY_003_GET_LISTBOX6", rfcParam, null, request);
				
		ArrayList<HashMap<String, Object>> etKdsvh = (ArrayList<HashMap<String, Object>>)resultCode.get("ET_KDSVH");
		
		HashMap<String, Object> tempParam = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> tempList;
		HashMap<String, Object> tempRow;
		
		ArrayList<HashMap<String, Object>> etFamList = new ArrayList<HashMap<String, Object>>();
		
		for(int i = 0 ; i < etKdsvh.size() ; i++){
			tempParam.put("IM_PERNR", session.getAttribute("imPernr"));
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

		HttpSession session = request.getSession(false);
		
		if(session.getAttribute("imPernr") == null || session.getAttribute("imPass") == null){
			try{
				throw new Exception("Disconnect Session..");
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		String[] creditKeySet = Constant.creditKeySet;

		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		
		rfcParam.put("IM_YEAR"    , params.get("imYear"));
		rfcParam.put("IM_PERNR"   , session.getAttribute("imPernr"));
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

		HttpSession session = request.getSession(false);
		
		if(session.getAttribute("imPernr") == null || session.getAttribute("imPass") == null){
			try{
				throw new Exception("Disconnect Session..");
			}catch(Exception e){
				e.printStackTrace();
			}
		}

		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> rfcParamList = new HashMap<String, Object>();
		
		String creditKeySet[] = Constant.creditKeySet;
		
		rfcParam.put("IM_PERNR"   , session.getAttribute("imPernr"));
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

		ModelAndView mav = new ModelAndView("/portal/webmss/diligencePayMng/annuityPop");
	
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

		HttpSession session = request.getSession(false);
		
		if(session.getAttribute("imPernr") == null || session.getAttribute("imPass") == null){
			try{
				throw new Exception("Disconnect Session..");
			}catch(Exception e){
				e.printStackTrace();
			}
		}

		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> rfcParamList = new HashMap<String, Object>();
		
		String annuityKeySet[] = Constant.annuityKeySet;
		
		rfcParam.put("IM_PERNR"   , session.getAttribute("imPernr"));
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

		ModelAndView mav = new ModelAndView("/portal/webmss/diligencePayMng/housePop");
	
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

		HttpSession session = request.getSession(false);
		
		if(session.getAttribute("imPernr") == null || session.getAttribute("imPass") == null){
			try{
				throw new Exception("Disconnect Session..");
			}catch(Exception e){
				e.printStackTrace();
			}
		}

		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> rfcParamList = new HashMap<String, Object>();
		
		String houseKeySet[] = Constant.houseKeySet;
		
		rfcParam.put("IM_PERNR"   , session.getAttribute("imPernr"));
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

		ModelAndView mav = new ModelAndView("/portal/webmss/diligencePayMng/paymentPop");
	
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

		HttpSession session = request.getSession(false);
		
		if(session.getAttribute("imPernr") == null || session.getAttribute("imPass") == null){
			try{
				throw new Exception("Disconnect Session..");
			}catch(Exception e){
				e.printStackTrace();
			}
		}

		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> rfcParamList = new HashMap<String, Object>();
		
		String paymentKeySet[] = Constant.paymentKeySet;
		
		rfcParam.put("IM_PERNR"   , session.getAttribute("imPernr"));
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

		ModelAndView mav = new ModelAndView("/portal/webmss/diligencePayMng/premiumPop");

		HttpSession session = request.getSession(false);
		
		if(session.getAttribute("imPernr") == null || session.getAttribute("imPass") == null){
			HashMap<String, Object> resultMap = new HashMap<String, Object>();
			
			resultMap.put("EX_RESULT" , "E");
			resultMap.put("EX_MESSAGE", "세션 연결이 끊어졌습니다. 다시 로그인 해 주세요.");
			
			params.put("resultMap", resultMap);
			
			return settlementLoginForm(params, request);
		}

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
		layoutParam.put("IM_PERNR"   , session.getAttribute("imPernr"));
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

		rfcParam.put("IM_PERNR", session.getAttribute("imPernr"));
		rfcParam.put("IM_ZYEAR", params.get("EX_YEAR"));

		Map<? ,?> resultCode = webEssRcv.callRfcFunction("ZHR_RFC_PY_003_GET_LISTBOX5", rfcParam, null, request);
				
		ArrayList<HashMap<String, Object>> etKdsvh = (ArrayList<HashMap<String, Object>>)resultCode.get("ET_KDSVH");

		HashMap<String, Object> tempParam = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> tempList;
		HashMap<String, Object> tempRow;

		ArrayList<HashMap<String, Object>> etFamList = new ArrayList<HashMap<String, Object>>();

		for(int i = 0 ; i < etKdsvh.size() ; i++){
			tempParam.put("IM_PERNR", session.getAttribute("imPernr"));
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

		HttpSession session = request.getSession(false);
		
		if(session.getAttribute("imPernr") == null || session.getAttribute("imPass") == null){
			try{
				throw new Exception("Disconnect Session..");
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		String[] premiumKeySet = Constant.premiumKeySet;

		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		
		rfcParam.put("IM_YEAR"    , params.get("imYear"));
		rfcParam.put("IM_PERNR"   , session.getAttribute("imPernr"));
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

		HttpSession session = request.getSession(false);
		
		if(session.getAttribute("imPernr") == null || session.getAttribute("imPass") == null){
			try{
				throw new Exception("Disconnect Session..");
			}catch(Exception e){
				e.printStackTrace();
			}
		}

		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> rfcParamList = new HashMap<String, Object>();
		
		String premiumKeySet[] = Constant.premiumKeySet;
		
		rfcParam.put("IM_PERNR"   , session.getAttribute("imPernr"));
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

		ModelAndView mav = new ModelAndView("/portal/webmss/diligencePayMng/medicalPop");

		HttpSession session = request.getSession(false);
		
		if(session.getAttribute("imPernr") == null || session.getAttribute("imPass") == null){
			HashMap<String, Object> resultMap = new HashMap<String, Object>();
			
			resultMap.put("EX_RESULT" , "E");
			resultMap.put("EX_MESSAGE", "세션 연결이 끊어졌습니다. 다시 로그인 해 주세요.");
			
			params.put("resultMap", resultMap);
			
			return settlementLoginForm(params, request);
		}

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
		layoutParam.put("IM_PERNR"   , session.getAttribute("imPernr"));
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

		rfcParam.put("IM_PERNR", session.getAttribute("imPernr"));
		rfcParam.put("IM_ZYEAR", params.get("EX_YEAR"));

		Map<? ,?> resultCode = webEssRcv.callRfcFunction("ZHR_RFC_PY_003_GET_LISTBOX2", rfcParam, null, request);
				
		ArrayList<HashMap<String, Object>> etKdsvh = (ArrayList<HashMap<String, Object>>)resultCode.get("ET_KDSVH");

		HashMap<String, Object> tempParam = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> tempList;
		HashMap<String, Object> tempRow;

		ArrayList<HashMap<String, Object>> etFamList = new ArrayList<HashMap<String, Object>>();

		for(int i = 0 ; i < etKdsvh.size() ; i++){
			tempParam.put("IM_PERNR", session.getAttribute("imPernr"));
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

		HttpSession session = request.getSession(false);
		
		if(session.getAttribute("imPernr") == null || session.getAttribute("imPass") == null){
			try{
				throw new Exception("Disconnect Session..");
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		String[] medicalKeySet = Constant.medicalKeySet;

		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		
		rfcParam.put("IM_YEAR"    , params.get("imYear"));
		rfcParam.put("IM_PERNR"   , session.getAttribute("imPernr"));
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

		HttpSession session = request.getSession(false);
		
		if(session.getAttribute("imPernr") == null || session.getAttribute("imPass") == null){
			try{
				throw new Exception("Disconnect Session..");
			}catch(Exception e){
				e.printStackTrace();
			}
		}

		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> rfcParamList = new HashMap<String, Object>();
		
		String medicalKeySet[] = Constant.medicalKeySet;
		
		rfcParam.put("IM_PERNR"   , session.getAttribute("imPernr"));
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

		ModelAndView mav = new ModelAndView("/portal/webmss/diligencePayMng/educationPop");

		HttpSession session = request.getSession(false);
		
		if(session.getAttribute("imPernr") == null || session.getAttribute("imPass") == null){
			HashMap<String, Object> resultMap = new HashMap<String, Object>();
			
			resultMap.put("EX_RESULT" , "E");
			resultMap.put("EX_MESSAGE", "세션 연결이 끊어졌습니다. 다시 로그인 해 주세요.");
			
			params.put("resultMap", resultMap);
			
			return settlementLoginForm(params, request);
		}

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
		layoutParam.put("IM_PERNR"   , session.getAttribute("imPernr"));
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

		rfcParam.put("IM_PERNR", session.getAttribute("imPernr"));
		rfcParam.put("IM_ZYEAR", params.get("EX_YEAR"));

		Map<? ,?> resultCode = webEssRcv.callRfcFunction("ZHR_RFC_PY_003_GET_LISTBOX3", rfcParam, null, request);
				
		ArrayList<HashMap<String, Object>> etKdsvh = (ArrayList<HashMap<String, Object>>)resultCode.get("ET_KDSVH");

		HashMap<String, Object> tempParam = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> tempList;
		HashMap<String, Object> tempRow;

		ArrayList<HashMap<String, Object>> etFamList = new ArrayList<HashMap<String, Object>>();

		for(int i = 0 ; i < etKdsvh.size() ; i++){
			tempParam.put("IM_PERNR", session.getAttribute("imPernr"));
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

		HttpSession session = request.getSession(false);
		
		if(session.getAttribute("imPernr") == null || session.getAttribute("imPass") == null){
			try{
				throw new Exception("Disconnect Session..");
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		String[] educationKeySet = Constant.educationKeySet;

		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		
		rfcParam.put("IM_YEAR"    , params.get("imYear"));
		rfcParam.put("IM_PERNR"   , session.getAttribute("imPernr"));
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

		HttpSession session = request.getSession(false);
		
		if(session.getAttribute("imPernr") == null || session.getAttribute("imPass") == null){
			try{
				throw new Exception("Disconnect Session..");
			}catch(Exception e){
				e.printStackTrace();
			}
		}

		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> rfcParamList = new HashMap<String, Object>();
		
		String educationKeySet[] = Constant.educationKeySet;
		
		rfcParam.put("IM_PERNR"   , session.getAttribute("imPernr"));
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

		ModelAndView mav = new ModelAndView("/portal/webmss/diligencePayMng/donationPop");

		HttpSession session = request.getSession(false);
		
		if(session.getAttribute("imPernr") == null || session.getAttribute("imPass") == null){
			HashMap<String, Object> resultMap = new HashMap<String, Object>();
			
			resultMap.put("EX_RESULT" , "E");
			resultMap.put("EX_MESSAGE", "세션 연결이 끊어졌습니다. 다시 로그인 해 주세요.");
			
			params.put("resultMap", resultMap);
			
			return settlementLoginForm(params, request);
		}

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
		layoutParam.put("IM_PERNR"   , session.getAttribute("imPernr"));
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

		rfcParam.put("IM_PERNR", session.getAttribute("imPernr"));
		rfcParam.put("IM_ZYEAR", params.get("EX_YEAR"));

		Map<? ,?> resultCode = webEssRcv.callRfcFunction("ZHR_RFC_PY_003_GET_LISTBOX4", rfcParam, null, request);
				
		ArrayList<HashMap<String, Object>> etKdsvh = (ArrayList<HashMap<String, Object>>)resultCode.get("ET_KDSVH");

		HashMap<String, Object> tempParam = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> tempList;
		HashMap<String, Object> tempRow;

		ArrayList<HashMap<String, Object>> etFamList = new ArrayList<HashMap<String, Object>>();

		for(int i = 0 ; i < etKdsvh.size() ; i++){
			tempParam.put("IM_PERNR", session.getAttribute("imPernr"));
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

		HttpSession session = request.getSession(false);
		
		if(session.getAttribute("imPernr") == null || session.getAttribute("imPass") == null){
			try{
				throw new Exception("Disconnect Session..");
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		String[] donationKeySet = Constant.donationKeySet;

		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		
		rfcParam.put("IM_YEAR"    , params.get("imYear"));
		rfcParam.put("IM_PERNR"   , session.getAttribute("imPernr"));
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

		HttpSession session = request.getSession(false);
		
		if(session.getAttribute("imPernr") == null || session.getAttribute("imPass") == null){
			try{
				throw new Exception("Disconnect Session..");
			}catch(Exception e){
				e.printStackTrace();
			}
		}

		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> rfcParamList = new HashMap<String, Object>();
		
		String donationKeySet[] = Constant.donationKeySet;
		
		rfcParam.put("IM_PERNR"   , session.getAttribute("imPernr"));
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

		ModelAndView mav = new ModelAndView("/portal/webmss/diligencePayMng/pensionPop");

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

		HttpSession session = request.getSession(false);
		
		if(session.getAttribute("imPernr") == null || session.getAttribute("imPass") == null){
			try{
				throw new Exception("Disconnect Session..");
			}catch(Exception e){
				e.printStackTrace();
			}
		}

		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> rfcParamList = new HashMap<String, Object>();
		
		String pensionKeySet[] = Constant.pensionKeySet;
		
		rfcParam.put("IM_PERNR"   , session.getAttribute("imPernr"));
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

		ModelAndView mav = new ModelAndView("/portal/webmss/diligencePayMng/monthlyRentPop");

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
	
	@RequestMapping(value = "callPY003OninputPop11.do")
	public @ResponseBody Map<?, ?> callPY003OninputPop11(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		HttpSession session = request.getSession(false);
		
		if(session.getAttribute("imPernr") == null || session.getAttribute("imPass") == null){
			try{
				throw new Exception("Disconnect Session..");
			}catch(Exception e){
				e.printStackTrace();
			}
		}

		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> rfcParamList = new HashMap<String, Object>();

		String pensionKeySet[] = Constant.rentKeySet;

		rfcParam.put("IM_PERNR"   , session.getAttribute("imPernr"));
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
	
	@RequestMapping(value = "callPY003Oninput.do")
	public @ResponseBody Map<?, ?> callPY003Oninput(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		HttpSession session = request.getSession(false);
		
		if(session.getAttribute("imPernr") == null || session.getAttribute("imPass") == null){
			try{
				throw new Exception("Disconnect Session..");
			}catch(Exception e){
				e.printStackTrace();
			}
		}

		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> rfcParamList = new HashMap<String, Object>();

		rfcParam.put("IM_PERNR"    , session.getAttribute("imPernr"));
		rfcParam.put("IM_YEAR"     , params.get("imYear"));
		rfcParam.put("IM_PASS"     , session.getAttribute("imPass"));
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
	
	@RequestMapping(value = "settlementDocumentView.do")
	public ModelAndView settlementDocumentView(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/webmss/diligencePayMng/settlementDocumentView");
		
		HttpSession session = request.getSession(false);
		
		if(session.getAttribute("imPernr") == null || session.getAttribute("imPass") == null){
			HashMap<String, Object> resultMap = new HashMap<String, Object>();
			
			resultMap.put("EX_RESULT" , "E");
			resultMap.put("EX_MESSAGE", "세션 연결이 끊어졌습니다. 다시 로그인 해 주세요.");
			
			params.put("resultMap", resultMap);
			
			return settlementLoginForm(params, request);
		}
		
		params.put("toDay", DateUtil.getToday("yyyyMMdd"));
		
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		
		rfcParam.put("IM_PERNR", session.getAttribute("imPernr"));
		
		mav.addObject("resultMap", webEssRcv.callRfcFunction("ZHR_RFC_PY_006_ONINIT", rfcParam, null, request));
		mav.addObject("params"   , params);

		return mav;
	}
	
	@RequestMapping(value = "loanList.do")
	public ModelAndView loanList(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/webmss/diligencePayMng/loanList");

		User user = (User) getRequestAttribute("ikep.user");

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		rfcParam.put("IM_PERNR", params.get("selEmpNo"));
		rfcParam.put("IM_YEAR" , DateUtil.getToday("yyyy"));
		rfcParam.put("IM_MONTH", DateUtil.getToday("MM"));
		//RFC PARAMETER SETTING END

		Map<?, ?> resultMapList = webEssRcv.callRfcFunction("ZHR_RFC_PY_001_GET_DATA", rfcParam, null, request);

		mav.addObject("resultMapList", resultMapList);
		mav.addObject("keySet"       , Constant.loanListKeySet);
		mav.addObject("params"       , params);

		return mav;
	}

	@RequestMapping(value = "loanView.do")
	public ModelAndView loanView(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/webmss/diligencePayMng/loanView");

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

		rfcParam.put("IM_PERNR", params.get("PERNR"));
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
}
