package com.lgcns.ikep4.portal.moorimess.web.manPowerMng;

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

import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.jco.WebESSRcvRFC;

@Controller
@RequestMapping(value="/portal/moorimess/manPowerMng")
public class ManPowerMngController extends BaseController {

	@Autowired
	private WebESSRcvRFC webEssRcv;

	@RequestMapping(value = "/jobProfileView.do")
	public ModelAndView jobProfileView(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/webess/manPowerMng/jobProfileView");

		Map<?, ?> resultMap = webEssRcv.callRfcFunction("ZHR_RFC_PD_002_GET_TREE", null, null, request);

		mav.addObject("resultMap", resultMap);
		mav.addObject("params"   , params);

		return mav;
	}

	@RequestMapping(value = "/retrieveJobProfileInfo.do")
	public @ResponseBody Map<?, ?> retrieveJobProfileInfo(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		rfcParam.put("IM_OBJID", params.get("imObjid"));
		rfcParam.put("IM_OTYPE", params.get("imOtype"));
		//RFC PARAMETER SETTING END

		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_PD_002_GET_DATA", rfcParam, null, request);

		return result;
	}

	@RequestMapping(value = "/capaCatalogView.do")
	public ModelAndView capaCatalogView(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/webess/manPowerMng/capaCatalogView");

		Map<?, ?> resultMap = webEssRcv.callRfcFunction("ZHR_RFC_PD_001_GET_TREE", null, null, request);

		mav.addObject("resultMap", resultMap);
		mav.addObject("params"   , params);

		return mav;
	}

	@RequestMapping(value = "/retrieveCapaCatalogInfo.do")
	public @ResponseBody Map<?, ?> retrieveCapaCatalogInfo(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		rfcParam.put("IM_OBJID", params.get("imObjid"));
		rfcParam.put("IM_OTYPE", params.get("imOtype"));
		//RFC PARAMETER SETTING END

		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_PD_001_GET_DATA", rfcParam, null, request);

		return result;
	}
	
	@RequestMapping(value = "/capaCatalogViewPopup.do")
	public ModelAndView capaCatalogViewPopup(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {
		
		ModelAndView mav = new ModelAndView("/portal/webess/manPowerMng/capaCatalogViewPopup");
		
		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		rfcParam.put("IM_OBJID", params.get("imObjid"));
		rfcParam.put("IM_OTYPE", params.get("imOtype"));
		//RFC PARAMETER SETTING END
		
		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_PD_001_GET_DATA", rfcParam, null, request);
		String tmpExdescr = (String) result.get("EX_DESCR");
		tmpExdescr = tmpExdescr.replaceAll("-.", "<br>-.");
		tmpExdescr = tmpExdescr.substring(4);
		mav.addObject("result", result);
		mav.addObject("EX_DESCR", tmpExdescr);
		
		return mav;
	}

	@RequestMapping(value = "/statusPointList.do")
	public ModelAndView statusPointList(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/webess/manPowerMng/statusPointList");

		User user = (User) getRequestAttribute("ikep.user");

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		rfcParam.put("IM_PERNR", user.getEmpNo());
		//RFC PARAMETER SETTING END

		Map<?, ?> resultMap = webEssRcv.callRfcFunction("ZHR_RFC_PA_036_GET_DATA", rfcParam, null, request);

		mav.addObject("resultMap", resultMap);
		mav.addObject("params"   , params);

		return mav;
	}
}
