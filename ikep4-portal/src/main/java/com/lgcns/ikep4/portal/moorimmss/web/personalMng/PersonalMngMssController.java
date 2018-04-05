package com.lgcns.ikep4.portal.moorimmss.web.personalMng;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.util.jco.WebESSRcvRFC;

@Controller
@RequestMapping(value="/portal/moorimmss/personalMng")
public class PersonalMngMssController extends BaseController {

	@Autowired
	private WebESSRcvRFC webEssRcv;
	
	@RequestMapping(value = "/personalInfoView.do")
	public ModelAndView personalInfoView(@RequestParam HashMap<String, Object> params, HttpServletRequest request){
		ModelAndView mav = new ModelAndView("/portal/webmss/personalMng/personalInfoView");
		
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		
		rfcParam.put("IM_PERNR", params.get("selEmpNo"));
		
		Map<?, ?> resultHeader = webEssRcv.callRfcFunction("ZHR_RFC_PA_001_GET_DATA", rfcParam, null, request);
		
		if(!params.containsKey("appointType")){
			params.put("appointType", "00");
		}
		
		rfcParam.put("IM_MASSN", params.get("appointType"));
		
		Map<?, ?> appointResult = webEssRcv.callRfcFunction("ZHR_RFC_PA_002_GET_DATA", rfcParam, null, request);
		mav.addObject("appointResult", appointResult);
		
		String STAT1 = (String)((Map<?, ?>)resultHeader.get("EX_HEADER")).get("STAT1");
		
		if(!"0".equals(STAT1) && !"3".equals(STAT1)){
			Map<?, ?> educationResult = webEssRcv.callRfcFunction("ZHR_RFC_PA_012_GET_DATA", rfcParam, null, request);
			mav.addObject("educationResult", educationResult);
			
			Map<?, ?> addressResult = webEssRcv.callRfcFunction("ZHR_RFC_PA_008_GET_DATA", rfcParam, null, request);
			mav.addObject("addressResult", addressResult);
			
			Map<?, ?> prizeDisciplineResult = webEssRcv.callRfcFunction("ZHR_RFC_PA_011_GET_DATA", rfcParam, null, request);
			mav.addObject("prizeDisciplineResult", prizeDisciplineResult);
			
			Map<?, ?> qualificationResult = webEssRcv.callRfcFunction("ZHR_RFC_PA_010_GET_DATA", rfcParam, null, request);
			mav.addObject("qualificationResult", qualificationResult);
			
			Map<?, ?> foreignLanguageResult = webEssRcv.callRfcFunction("ZHR_RFC_PA_007_GET_DATA", rfcParam, null, request);
			mav.addObject("foreignLanguageResult", foreignLanguageResult);
			
			Map<?, ?> familyResult = webEssRcv.callRfcFunction("ZHR_RFC_PA_009_GET_DATA", rfcParam, null, request);
			mav.addObject("familyResult", familyResult);
			
			Map<?, ?> careerResult = webEssRcv.callRfcFunction("ZHR_RFC_PA_003_GET_DATA", rfcParam, null, request);
			mav.addObject("careerResult", careerResult);
		}
		
		mav.addObject("resultHeader", resultHeader);
		mav.addObject("params", params);
		
		return mav;
	}
}
