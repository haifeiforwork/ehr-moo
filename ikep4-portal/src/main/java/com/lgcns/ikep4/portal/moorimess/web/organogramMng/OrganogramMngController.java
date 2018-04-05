package com.lgcns.ikep4.portal.moorimess.web.organogramMng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.portal.moorimess.web.rfcCache.service.RfcCacheService;
import com.lgcns.ikep4.portal.util.CacheRefreshUtil;
import com.lgcns.ikep4.portal.util.PagingUtils;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.jco.WebESSRcvRFC;
import com.lgcns.ikep4.util.lang.DateUtil;

@Controller
@RequestMapping(value="/portal/moorimess/organogramMng")
public class OrganogramMngController extends BaseController {

	@Autowired
	private WebESSRcvRFC webEssRcv;
	
	@Autowired
	private RfcCacheService rfcCacheService;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/organizationChartList.do")
	public ModelAndView organizationChartList(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/webess/organogramMng/organizationChartList");

		User user = (User) getRequestAttribute("ikep.user");

		if(params.containsKey("imFirst")){
			params.put("imYear" , DateUtil.getToday("yyyy"));
			params.put("imDoumi", "07");
		}

		if(!params.containsKey("hidImOrg")){
			params.put("hidImOrg", "1");
		}
		
		if(params.containsKey("clearCache") && "Y".equals(params.get("clearCache"))){
			CacheRefreshUtil.refreshCache("rfcCache");
		}

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		rfcParam.put("IM_PERNR", user.getEmpNo());
		rfcParam.put("IM_YEAR" , params.get("imYear"));
		rfcParam.put("IM_DOUMI", params.get("imDoumi"));
		rfcParam.put("IM_LOCAT", "");
		rfcParam.put("IM_ORG"  , params.get("hidImOrg"));
		rfcParam.put("IM_FIRST", params.get("imFirst"));

		//RFC PARAMETER SETTING END
		Map<?, ?> resultMap = rfcCacheService.callRfcFunctionSetCache("ZHR_RFC_PA_044_ONINIT", rfcParam, null);
		
		ArrayList<Map<?, ?>> tempList = (ArrayList<Map<?, ?>>)resultMap.get("ET_LIST");
		
		ArrayList<Map<?, ?>> etList = PagingUtils.setPagingParam(tempList, params);

		mav.addObject("resultMap", resultMap);
		mav.addObject("etList"   , etList);
		mav.addObject("params"   , params);

		return mav;
	}
}
