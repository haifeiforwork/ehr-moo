package com.lgcns.ikep4.portal.moorimmss.web.evaluationMng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.comomon.Constant;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.jco.WebESSRcvRFC;
import com.lgcns.ikep4.util.lang.DateUtil;

@Controller
@RequestMapping(value="/portal/moorimmss/evaluationMng")
public class EvaluationMngMssController extends BaseController {

	@Autowired
	private WebESSRcvRFC webEssRcv;	
	
	@RequestMapping(value = "/evaluationList.do")
	public ModelAndView evaluationList(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {
		
		ModelAndView mav = new ModelAndView("/portal/webmss/evaluationMng/evaluationList");
		
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		
		rfcParam.put("IM_PERNR", params.get("selEmpNo"));
		rfcParam.put("IM_PERNR1", params.get("selEmpNo"));
		
		Map<?, ?> resultHeader = webEssRcv.callRfcFunction("ZHR_RFC_HAP_010_ONINIT", rfcParam, null, request);
		
		mav.addObject("resultHeader", resultHeader);
		mav.addObject("params", params);
		
		return mav;
	}
	
	@RequestMapping(value = "/raterEvaluationList.do")
	public ModelAndView raterEvaluationList(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {
		
		ModelAndView mav = new ModelAndView("/portal/webmss/evaluationMng/raterEvaluationList");
		
		User user = (User) getRequestAttribute("ikep.user");

		String thisYear = DateUtil.getToday("yyyy");
		
		if(!params.containsKey("imYear")){
			params.put("imYear", thisYear);
		}
		
		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		rfcParam.put("IM_PERNR", user.getEmpNo());
		rfcParam.put("IM_YEAR" , params.get("imYear"));
		//RFC PARAMETER SETTING END

		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_HAP_003_ONINIT", rfcParam, null, request);

		mav.addObject("result", result);
		mav.addObject("keySet1", Constant.raterEvaluationHeaderListKeySet);
		mav.addObject("keySet2", Constant.raterEvaluationSubListKeySet);
		mav.addObject("params", params);

		return mav;
	}
	
	@RequestMapping(value = "/raterEvaluationView.do")
	public ModelAndView raterEvaluationView(@RequestParam HashMap<String, Object> params, HttpServletRequest request, String pernr) {

		ModelAndView mav = new ModelAndView("/portal/webmss/evaluationMng/raterEvaluationView");
		
		
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> rfcParamList = new HashMap<String, Object>();
		HashMap<String, Object> paramRow1 = new HashMap<String, Object>();
		HashMap<String, Object> paramRow2 = null;
		HashMap<String, Object> paramRow3 = null;
		ArrayList<HashMap<String, Object>> tempList1 = new ArrayList<HashMap<String, Object>>();
		ArrayList<HashMap<String, Object>> tempList2 = new ArrayList<HashMap<String, Object>>();
		
		String keySet1[] = Constant.raterEvaluationHeaderListKeySet;

		for(String key1 : keySet1){
			paramRow1.put(key1, params.containsKey(key1+"_"+pernr) ? params.get(key1+"_"+pernr) : "");
		}
		rfcParam.put("IM_LIST", paramRow1);
		
		String keySet2[] = Constant.raterEvaluationSubListKeySet;
		
		int etdocumentCnt = Integer.parseInt(((String)params.get("etdocumentCnt")));

		if(etdocumentCnt == 1){
			paramRow2 = new HashMap<String, Object>();
			for(String key2 : keySet2){
				paramRow2.put(key2, params.containsKey("etdocument_"+key2+"_0") ? params.get("etdocument_"+key2+"_0") : "");
			}
			tempList1.add(paramRow2);
		}else if(etdocumentCnt > 1){
			for(int i=0 ; i<etdocumentCnt ; i++){
				paramRow2 = new HashMap<String, Object>();
				for(String key2 : keySet2){
					paramRow2.put(key2, params.containsKey("etdocument_"+key2+"_"+i) ? params.get("etdocument_"+key2+"_"+i) : "" );
				}
				tempList1.add(paramRow2);
			}
		}
		
		int etmyselfCnt = Integer.parseInt(((String)params.get("etmyselfCnt")));

		if(etmyselfCnt == 1){
			paramRow3 = new HashMap<String, Object>();
			paramRow3.put("SOBID", params.containsKey("etmyself_SOBID_0") ? params.get("etmyself_SOBID_0") : "");
			paramRow3.put("PLVAR", params.containsKey("etmyself_PLVAR_0") ? params.get("etmyself_PLVAR_0") : "");
			paramRow3.put("OTYPE", params.containsKey("etmyself_OTYPE_0") ? params.get("etmyself_OTYPE_0") : "");
			tempList2.add(paramRow3);
		}else if(etmyselfCnt > 1){
			for(int i=0 ; i<etmyselfCnt ; i++){
				paramRow3 = new HashMap<String, Object>();
				paramRow3.put("SOBID", params.containsKey("etmyself_SOBID_"+i)? params.get("etmyself_SOBID_"+i) : "");
				paramRow3.put("PLVAR", params.containsKey("etmyself_PLVAR_"+i)? params.get("etmyself_PLVAR_"+i) : "");
				paramRow3.put("OTYPE", params.containsKey("etmyself_OTYPE_"+i)? params.get("etmyself_OTYPE_"+i) : "");
				tempList2.add(paramRow3);
			}
		}

		rfcParamList.put("IT_DOCUMENTS", tempList1);
		rfcParamList.put("IT_MYSELF", tempList2);

		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_HAP_003_ONINIT_DETAIL", rfcParam, rfcParamList, request);

		mav.addObject("result", result);
		mav.addObject("keySet"    , Constant.raterEvaluationHeaderListKeySet);
		mav.addObject("params"    , params);

		return mav;
	}
}
