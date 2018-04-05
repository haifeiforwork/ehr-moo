package com.lgcns.ikep4.portal.moorimess.web.evaluationMng;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.jco.WebESSRcvRFC;
import com.lgcns.ikep4.util.lang.DateUtil;

@Controller
@RequestMapping(value="/portal/webess/evaluationMng")
public class EvaluationMngController extends BaseController {

	@Autowired
	private WebESSRcvRFC webEssRcv;	
	
	@RequestMapping(value = "/holidayList.do")
	public ModelAndView holidayList(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {
		
		ModelAndView mav = new ModelAndView("/portal/webess/evaluationMng/");
		
		User user = (User) getRequestAttribute("ikep.user");
		
		String thisYear = DateUtil.getToday("yyyy");
		String thisMonth = DateUtil.getToday("MM").toString();

		if(!params.containsKey("iYear")){
			params.put("iYear", thisYear);
		}
		
		if(!params.containsKey("iMonth")){
			params.put("iMonth", thisMonth);
		}		
		
		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		
		rfcParam.put("I_YEAR" , params.get("iYear"));
		rfcParam.put("I_MONTH", params.get("iMonth"));
		rfcParam.put("I_PERNR", user.getEmpNo());
		//RFC PARAMETER SETTING END
		
		mav.addObject("holidayList", webEssRcv.callRfcFunction("ZHR_PT002_01", rfcParam, null, request).get("T_LIST"));
		mav.addObject("thisYear", thisYear);
		mav.addObject("params", params);
		
		return mav;
	}
}
