package com.lgcns.ikep4.portal.evaluation.web.position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.portal.evaluation.constant.EvaluationExport;
import com.lgcns.ikep4.portal.evaluation.constant.EvaluationImport;
import com.lgcns.ikep4.portal.evaluation.constant.Position;
import com.lgcns.ikep4.portal.evaluation.constant.PositionKeySet;
import com.lgcns.ikep4.portal.evaluation.constant.PositionRFC;
import com.lgcns.ikep4.portal.evaluation.model.PositionCommon;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.jco.WebESSRcvRFC;

@Controller
@RequestMapping(value="/portal/evaluation/position")
public class PositionChangeController extends BaseController {

	@Autowired
	private WebESSRcvRFC webEssRcv;

	protected final Log logger = LogFactory.getLog(this.getClass());

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/positionList.do")
	public ModelAndView positionList(@RequestParam HashMap<String, Object> params, HttpServletRequest request) throws Exception {

		Position position = Position.CHANGE_LIST;

		ModelAndView mav = new ModelAndView(position.getView());

		User user = (User) getRequestAttribute("ikep.user");
		String empno = user.getEmpNo();

		String paramsOrgeh = (String) ((params.get("orgeh") == null) ? "" : params.get("orgeh"));

		//RFC PARAMETER SETTING START
		 HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		rfcParam.put(EvaluationImport.I_PERNR.name(), empno);
		rfcParam.put(EvaluationImport.I_ORGEH.name(), paramsOrgeh);
		//RFC PARAMETER SETTING END

		Map<?, ?> export = webEssRcv.callRfcFunction(position.getPositionRFC().getRfc(), rfcParam, null, request);

		Map<?, ?> esReturn = (Map<?, ?>) export.get(EvaluationExport.ES_RETURN.name());
		ArrayList<Map<?, ?>> etList = (ArrayList<Map<?, ?>>) export.get(EvaluationExport.ET_LIST.name());

		rfcParam = new HashMap<String, Object>();
		rfcParam.put(EvaluationImport.I_PERNR.name(), empno);
		Map<?, ?> result = webEssRcv.callRfcFunction(PositionRFC.ORGANIZATION.getRfc(), rfcParam, null, request);
		ArrayList<Map<?, ?>> etOrgeh = (ArrayList<Map<?, ?>>) result.get(EvaluationExport.ET_ORGEH.name());

		if(etOrgeh.size() > 0 && paramsOrgeh.equals("")) paramsOrgeh =  (String) etOrgeh.get(0).get("OBJID");

		mav.addObject("esReturn", esReturn);
		mav.addObject("etOrgeh", etOrgeh);
		mav.addObject("etList", etList);
		mav.addObject("paramsOrgeh", paramsOrgeh);
		mav.addObject("params", params);

		return mav;

	}

	@RequestMapping(value = "/positionProc.do")
	public @ResponseBody Map<?, ?> positionProc(PositionCommon positionCommon, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");
		String empno = user.getEmpNo();

		Map<?, ?> result = null;
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		List<HashMap<String, Object>> itList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> deatil = null;

		try {

			//RFC PARAMETER SETTING START
			String methodName = "";

			for(PositionCommon position : positionCommon.getPositions()) {
				deatil = new HashMap<String, Object>();
				for(String apprKey : PositionKeySet.COMMON.getKeys()) {
					methodName = apprKey.replace(apprKey.substring(1), apprKey.substring(1).toLowerCase());
					deatil.put(apprKey, position.getClass().getDeclaredMethod("get" + methodName).invoke(position));
				}
				itList.add(deatil);
			}

			rfcParam.put(EvaluationImport.I_BUTTON.name(), positionCommon.getButton());
			rfcParam.put(EvaluationImport.I_PERNR.name(), empno);
			rfcParam.put(EvaluationImport.I_LOGPER.name(), empno);
			rfcParam.put(EvaluationImport.I_ORGEH.name(), positionCommon.getiOrgeh());
			rfcParam.put(EvaluationImport.I_ACDAT.name(), positionCommon.getiAcdat());
			rfcParam.put(EvaluationImport.IT_LIST.name(), itList);
			//RFC PARAMETER SETTING END

			result = webEssRcv.callRfcFunction(PositionRFC.PROCESS.getRfc(), rfcParam, null, request);

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new IKEP4AjaxException("", ex);
		}

		return result;

	}

	@RequestMapping(value = "/popAppointList.do")
	public ModelAndView popAppointList(@RequestParam(value="empno", required=true) String empno, @RequestParam HashMap<String, Object> params, HttpServletRequest request) throws Exception {

		Position position = Position.APPOINT_LIST;
		ModelAndView mav = new ModelAndView(position.getView());

		if(!params.containsKey("appointType")){
			params.put("appointType", "00");
		}

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		rfcParam.put("IM_PERNR", empno);
		rfcParam.put("IM_MASSN", params.get("appointType"));
		//RFC PARAMETER SETTING END

		Map<?, ?> result = webEssRcv.callRfcFunction(position.getPositionRFC().getRfc(), rfcParam, null, request);

		mav.addObject("result", result);
		mav.addObject("params", params);

		return mav;
	}

}
