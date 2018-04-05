package com.lgcns.ikep4.portal.evaluation.web.etc;

import java.util.HashMap;
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
import com.lgcns.ikep4.portal.evaluation.constant.Etc;
import com.lgcns.ikep4.portal.evaluation.constant.EtcRFC;
import com.lgcns.ikep4.portal.evaluation.constant.EvaluationImport;
import com.lgcns.ikep4.portal.evaluation.constant.Tree;
import com.lgcns.ikep4.util.jco.WebESSRcvRFC;

@Controller
@RequestMapping(value="/portal/evaluation/etc")
public class EtcController extends BaseController {

	@Autowired
	private WebESSRcvRFC webEssRcv;

	protected final Log logger = LogFactory.getLog(this.getClass());

	@RequestMapping(value = "/retrieve.do")
	public ModelAndView retrieve(@RequestParam HashMap<String, Object> params, HttpServletRequest request) throws Exception {

		Etc etc = Etc.lookup((String) params.get("action"));
		ModelAndView mav = new ModelAndView(etc.getView());

		mav.addObject("params", params);

		return mav;

	}

	@RequestMapping(value = "/detail.do")
	public @ResponseBody Map<?, ?> detail(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		Etc etc = Etc.lookup((String) params.get("action"));

		Map<?, ?> result = null;
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		try {

			String objId = (String) params.get("object_id");

			//RFC PARAMETER SETTING START
			rfcParam.put(EvaluationImport.I_OTYPE.name(), etc.getObjectType());
			rfcParam.put(EvaluationImport.I_OBJID.name(), objId);
			//RFC PARAMETER SETTING END

			result = webEssRcv.callRfcFunction(etc.getEtcRFC().getRfc(), rfcParam, null, request);

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new IKEP4AjaxException("", ex);
		}

		return result;

	}

	@RequestMapping(value = "/getTree.do")
	public @ResponseBody Map<?, ?> getTree(@RequestParam(value="gubun", required=true) String gubun, HttpServletRequest request) {

		Tree tree = Tree.valueOf(gubun.toUpperCase());

		Map<?, ?> export = null;
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		try {

			//RFC PARAMETER SETTING START
			rfcParam.put(EvaluationImport.I_OTYPE.name(), tree.code());
			//RFC PARAMETER SETTING END

			export = webEssRcv.callRfcFunction(EtcRFC.TREE.getRfc(), rfcParam, null, request);

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new IKEP4AjaxException("", ex);
		}

		return export;

	}

}
