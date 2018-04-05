package com.lgcns.ikep4.portal.evaluation.web.idp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
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
import com.lgcns.ikep4.portal.evaluation.constant.Idp;
import com.lgcns.ikep4.portal.evaluation.constant.IdpKeySet;
import com.lgcns.ikep4.portal.evaluation.constant.IdpRFC;
import com.lgcns.ikep4.portal.evaluation.constant.Tree;
import com.lgcns.ikep4.portal.evaluation.model.IdpCommon;
import com.lgcns.ikep4.portal.evaluation.model.IdpJob;
import com.lgcns.ikep4.portal.evaluation.model.IdpPerformance;
import com.lgcns.ikep4.portal.moorimmss.model.MssTeamTreeSpecial;
import com.lgcns.ikep4.portal.moorimmss.service.MssTeamTreeSpecialService;
import com.lgcns.ikep4.portal.util.PagingUtils;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.group.model.Group;
import com.lgcns.ikep4.support.user.group.service.GroupService;
import com.lgcns.ikep4.support.user.member.dao.UserDao;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.service.UserService;
import com.lgcns.ikep4.util.jco.WebESSRcvRFC;
import com.lgcns.ikep4.util.lang.DateUtil;
import com.lgcns.ikep4.util.lang.StringUtil;

@Controller
@RequestMapping(value="/portal/evaluation/idp")
public class IdpController extends BaseController {

	@Autowired
	private ACLService aclService;

	@Autowired
	private GroupService groupService;

	@Autowired
	private UserService userService;

	@Autowired
	private MssTeamTreeSpecialService mssTeamTreeSpecialService;

	@Autowired
	private WebESSRcvRFC webEssRcv;

	@Autowired
	private UserDao userDao;

	//Log
	protected final Log logger = LogFactory.getLog(this.getClass());

	public boolean isSystemAdmin(User user) {

		return this.aclService.isSystemAdmin("MoorimMss", user.getUserId());

	}

	/**
	 * IDP - 목록 공통
	 *
	 * @param params
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/idpList.do")
	public ModelAndView idpList(@RequestParam HashMap<String, Object> params, HttpServletRequest request) throws Exception {

		String action = (String) params.get("action");
		Idp idp = Idp.lookup(action.toUpperCase());

		ModelAndView mav = new ModelAndView(idp.getView());

		User user = (User) getRequestAttribute("ikep.user");

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		switch(idp) {
			case IDP_SETTING :
				rfcParam.put(EvaluationImport.I_IDPAE.name(), user.getEmpNo());
				break;
			case IDP_AGREEMENT :
				rfcParam.put(EvaluationImport.I_IDPAR.name(), user.getEmpNo());
				break;
			default :
				break;
		}
		//RFC PARAMETER SETTING END

		Map<?, ?> export = webEssRcv.callRfcFunction(idp.getIdpRFC().getRfc(), rfcParam, null, request);

		Map<?, ?> esReturn = (Map<?, ?>) export.get(EvaluationExport.ES_RETURN.name());
		ArrayList<Map<?, ?>> tempList = (ArrayList<Map<?, ?>>) export.get(EvaluationExport.ET_LIST.name());

		String currentYear = DateUtil.getToday("yyyy");
		ArrayList<Map<?, ?>> etList = PagingUtils.setPagingParam(tempList, params);

		mav.addObject("currentYear", currentYear);
		mav.addObject("esReturn", esReturn);
		mav.addObject("etList", etList);
		mav.addObject("params", params);

		return mav;

	}

	/**
	 * IDP - 상세체크 공통
	 *
	 * @param params
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/idpCheckView.do")
	public @ResponseBody Map<?, ?> idpCheckView(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		Idp idp = Idp.lookup((String)params.get("action"));

		Map<?, ?> result = null;
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> apprMap = new HashMap<String, Object>();

		User user = (User) getRequestAttribute("ikep.user");

		try {

			//RFC PARAMETER SETTING START
			for(String apprKey : IdpKeySet.COMMON.getKeys()){
				apprMap.put(apprKey, params.get(apprKey));
			}
			rfcParam.put(EvaluationImport.IS_IDPLST.name(), apprMap);

			switch(idp) {
				case IDP_SETTING_VIEW :
					rfcParam.put(EvaluationImport.I_IDPAE.name(), user.getEmpNo());
					break;
				case IDP_AGREEMENT_VIEW :
					rfcParam.put(EvaluationImport.I_IDPAR.name(), user.getEmpNo());
					break;
				case IDP_HSS_VIEW :
					rfcParam.put(EvaluationImport.I_HISTO.name(), "X");
					rfcParam.put(EvaluationImport.I_IDPAE.name(), params.get("IDPAE"));
					break;
				default :
					break;
			}
			//RFC PARAMETER SETTING END

			Map<?, ?> export = webEssRcv.callRfcFunction(idp.getIdpRFC().getRfc(), rfcParam, null, request);

			result = (Map<?, ?>) export.get(EvaluationExport.ES_RETURN.name());

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new IKEP4AjaxException("", ex);
		}

		return result;

	}

	/**
	 * IDP - 수립 상세
	 *
	 * @param params
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/idpSettingView.do")
	public ModelAndView idpSettingView(@RequestParam HashMap<String, Object> params, HttpServletRequest request) throws Exception {

		String action = (String) params.get("action");
		Idp idp = Idp.lookup(action.toUpperCase());

		ModelAndView mav = new ModelAndView(idp.getView());

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> apprMap = new HashMap<String, Object>();

		User user = (User) getRequestAttribute("ikep.user");

		for(String apprKey : IdpKeySet.COMMON.getKeys()){
			apprMap.put(apprKey, params.get(apprKey));
		}
		rfcParam.put(EvaluationImport.IS_IDPLST.name(), apprMap);
		rfcParam.put(EvaluationImport.I_IDPAE.name(), user.getEmpNo());
		//RFC PARAMETER SETTING END

		Map<?, ?> export = webEssRcv.callRfcFunction(idp.getIdpRFC().getRfc(), rfcParam, null, request);

		String eModif = (String) export.get(EvaluationExport.E_MODIF.name());
		String ePrtbt = (String) export.get(EvaluationExport.E_PRTBT.name());
		String eActbt = (String) export.get(EvaluationExport.E_ACTBT.name());
		Map<?, ?> crjob = (Map<?, ?>)export.get(EvaluationExport.ES_CRJOB.name());
		Map<?, ?> idplst = (Map<?, ?>)export.get(EvaluationExport.ES_IDPLST.name());
		ArrayList<Map<?, ?>> requalList = (ArrayList<Map<?, ?>>)export.get(EvaluationExport.ET_REQUAL.name());
		ArrayList<Map<?, ?>> hpqualList = (ArrayList<Map<?, ?>>)export.get(EvaluationExport.ET_HPQUAL.name());

		mav.addObject("eModif", eModif);
		mav.addObject("ePrtbt", ePrtbt);
		mav.addObject("eActbt", eActbt);
		mav.addObject("crjob", crjob);
		mav.addObject("idplst", idplst);
		mav.addObject("requalList", requalList);
		mav.addObject("hpqualList", hpqualList);
		mav.addObject("params", params);

		return mav;

	}

	/**
	 * IDP - 수립 상세 출력
	 *
	 * @param params
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/idpSettingViewPrint.do")
	public ModelAndView idpSettingViewPrint(@RequestParam HashMap<String, Object> params, HttpServletRequest request) throws Exception {

		Idp idp = Idp.IDP_SETTING_VIEW_PRINT;

		ModelAndView mav = new ModelAndView(idp.getView());

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> apprMap = new HashMap<String, Object>();

		User user = (User) getRequestAttribute("ikep.user");

		for(String apprKey : IdpKeySet.COMMON.getKeys()){
			apprMap.put(apprKey, params.get(apprKey));
		}
		rfcParam.put(EvaluationImport.I_IDPAE.name(), user.getEmpNo());
		rfcParam.put(EvaluationImport.I_PRINT.name(), "X");
		rfcParam.put(EvaluationImport.IS_IDPLST.name(), apprMap);
		//RFC PARAMETER SETTING END

		Map<?, ?> export = webEssRcv.callRfcFunction(idp.getIdpRFC().getRfc(), rfcParam, null, request);

		Map<String, Object> esCrjob = (Map<String, Object>) export.get(EvaluationExport.ES_CRJOB.name());
		Map<String, Object> esIdplst = (Map<String, Object>) export.get(EvaluationExport.ES_IDPLST.name());
		ArrayList<Map<String, Object>> etRequal = (ArrayList<Map<String, Object>>) export.get(EvaluationExport.ET_REQUAL.name());
		ArrayList<Map<String, Object>> etHpqual = (ArrayList<Map<String, Object>>) export.get(EvaluationExport.ET_HPQUAL.name());

		ArrayList<Map<String, Object>> printList = new ArrayList<Map<String, Object>>();
		Map<String, Object> print = new HashMap<String, Object>();

		print.put("esCrjob", esCrjob);
		print.put("esIdplst", esIdplst);
		print.put("etRequal", etRequal);
		print.put("etHpqual", etHpqual);
		printList.add(print);

		mav.addObject("printList", printList);

		return mav;

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/print.do")
	public ModelAndView print(@RequestParam(value="encKey", required=true) String encKey, HttpServletRequest request) throws Exception {

		Idp idp = Idp.IDP_SETTING_VIEW_PRINT;
		ModelAndView mav = new ModelAndView(idp.getView());

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		rfcParam.put(EvaluationImport.I_ECRGUB.name(), "3");
		rfcParam.put(EvaluationImport.I_ECRKEY.name(), encKey);
		//RFC PARAMETER SETTING END

		Map<?, ?> export = webEssRcv.callRfcFunction(IdpRFC.PRINT.getRfc(), rfcParam, null, request);

		ArrayList<Map<?, ?>> tIdpaeList = (ArrayList<Map<?, ?>>) export.get(EvaluationExport.T_IDPAE.name());

		ArrayList<Map<String, Object>> printList = new ArrayList<Map<String, Object>>();
		Map<String, Object> print = null;

		ArrayList<Map<String, Object>> etRequal = null;
		ArrayList<Map<String, Object>> etHpqual = null;
		Map<String, Object> esCrjob = null;
		Map<String, Object> esIdplst = null;

		//RFC PARAMETER SETTING START
		HashMap<String, Object> apprMap = null;

		for(Map<?, ?> tIdpae : tIdpaeList) {
			apprMap = new HashMap<String, Object>();
			apprMap.put("AYEAR", tIdpae.get("AYEAR"));
			apprMap.put("IDPAE", tIdpae.get("IDPAE"));
			apprMap.put("IDPAENM", tIdpae.get("IDPAENM"));

			rfcParam = new HashMap<String, Object>();
			rfcParam.put(EvaluationImport.I_PRINT.name(), "X");
			rfcParam.put(EvaluationImport.I_IDPAE.name(), tIdpae.get("IDPAE"));
			rfcParam.put(EvaluationImport.IS_IDPLST.name(), apprMap);

			export = webEssRcv.callRfcFunction(idp.getIdpRFC().getRfc(), rfcParam, null, request);

			esCrjob = (Map<String, Object>) export.get(EvaluationExport.ES_CRJOB.name());
			esIdplst = (Map<String, Object>) export.get(EvaluationExport.ES_IDPLST.name());
			etRequal = (ArrayList<Map<String, Object>>) export.get(EvaluationExport.ET_REQUAL.name());
			etHpqual = (ArrayList<Map<String, Object>>) export.get(EvaluationExport.ET_HPQUAL.name());

			print = new HashMap<String, Object>();
			print.put("esCrjob", esCrjob);
			print.put("esIdplst", esIdplst);
			print.put("etRequal", etRequal);
			print.put("etHpqual", etHpqual);

			printList.add(print);
		}
		//RFC PARAMETER SETTING END

		mav.addObject("printList", printList);

		return mav;

	}

	/**
	 * IDP - 트리 조회
	 *
	 * @param gubun
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/idpTree.do")
	public @ResponseBody Map<?, ?> idpTree(@RequestParam(value="gubun", required=true) String gubun, HttpServletRequest request) {

		Tree tree = Tree.valueOf(gubun.toUpperCase());

		Map<?, ?> export = null;
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		try {

			//RFC PARAMETER SETTING START
			rfcParam.put(EvaluationImport.I_OTYPE.name(), tree.code());
			//RFC PARAMETER SETTING END

			export = webEssRcv.callRfcFunction(IdpRFC.TREE.getRfc(), rfcParam, null, request);

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new IKEP4AjaxException("", ex);
		}

		return export;

	}

	/**
	 * IDP - 수립 저장
	 *
	 * @param idpCommon
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/idpSettingProc.do")
	public @ResponseBody Map<?, ?> idpSettingProc(IdpCommon idpCommon, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");

		Map<?, ?> result = null;
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		HashMap<String, Object> idplstMap = new HashMap<String, Object>();
		HashMap<String, Object> crjobMap = new HashMap<String, Object>();
		List<HashMap<String, Object>> requalList = new ArrayList<HashMap<String, Object>>();
		List<HashMap<String, Object>> hpqualList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> qual = null;

		try {

			//RFC PARAMETER SETTING START
			String methodName = "";

			for(String apprKey : IdpKeySet.COMMON.getKeys()) {
				methodName = apprKey.replace(apprKey.substring(1), apprKey.substring(1).toLowerCase());
				idplstMap.put(apprKey, idpCommon.getClass().getDeclaredMethod("get" + methodName).invoke(idpCommon));
			}

			IdpJob idpJob = idpCommon.getJobs().get(0);
			for(String apprKey : IdpKeySet.JOB.getKeys()) {
				methodName = apprKey.replace(apprKey.substring(1), apprKey.substring(1).toLowerCase());
				crjobMap.put(apprKey, idpJob.getClass().getDeclaredMethod("get" + methodName).invoke(idpJob));
			}

			for(IdpPerformance idpPerformance : idpCommon.getRequals()) {
				qual = new HashMap<String, Object>();
				for(String apprKey : IdpKeySet.QUALITY.getKeys()) {
					methodName = apprKey.replace(apprKey.substring(1), apprKey.substring(1).toLowerCase());
					qual.put(apprKey, idpPerformance.getClass().getDeclaredMethod("get" + methodName).invoke(idpPerformance));
				}
				requalList.add(qual);
			}

			for(IdpPerformance idpPerformance : idpCommon.getHpquals()) {
				qual = new HashMap<String, Object>();
				for(String apprKey : IdpKeySet.QUALITY.getKeys()) {
					methodName = apprKey.replace(apprKey.substring(1), apprKey.substring(1).toLowerCase());
					qual.put(apprKey, idpPerformance.getClass().getDeclaredMethod("get" + methodName).invoke(idpPerformance));
				}
				hpqualList.add(qual);
			}

			rfcParam.put(EvaluationImport.IS_IDPLST.name(), idplstMap);
			rfcParam.put(EvaluationImport.IS_CRJOB.name(), crjobMap);
			rfcParam.put(EvaluationImport.IT_REQUAL.name(), requalList);
			rfcParam.put(EvaluationImport.IT_HPQUAL.name(), hpqualList);
 			rfcParam.put(EvaluationImport.I_BUTTON.name(), idpCommon.getButton());
 			rfcParam.put(EvaluationImport.I_LOGPER.name(), user.getEmpNo());
			//RFC PARAMETER SETTING END

			result = webEssRcv.callRfcFunction(IdpRFC.PROCESS.getRfc(), rfcParam, null, request);

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new IKEP4AjaxException("", ex);
		}

		return result;

	}

	/**
	 * IDP - 합의 상세
	 *
	 * @param params
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/idpAgreementView.do")
	public ModelAndView idpAgreementView(@RequestParam HashMap<String, Object> params, HttpServletRequest request) throws Exception {

		String action = (String) params.get("action");
		Idp idp = Idp.lookup(action.toUpperCase());

		ModelAndView mav = new ModelAndView(idp.getView());

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> apprMap = new HashMap<String, Object>();

		User user = (User) getRequestAttribute("ikep.user");

		for(String apprKey : IdpKeySet.COMMON.getKeys()){
			apprMap.put(apprKey, params.get(apprKey));
		}
		rfcParam.put(EvaluationImport.IS_IDPLST.name(), apprMap);
		rfcParam.put(EvaluationImport.I_IDPAR.name(), user.getEmpNo());
		//RFC PARAMETER SETTING END

		Map<?, ?> export = webEssRcv.callRfcFunction(idp.getIdpRFC().getRfc(), rfcParam, null, request);

		String eModif = (String) export.get(EvaluationExport.E_MODIF.name());
		String ePrtbt = (String) export.get(EvaluationExport.E_PRTBT.name());
		String eActbt = (String) export.get(EvaluationExport.E_ACTBT.name());
		Map<?, ?> crjob = (Map<?, ?>) export.get(EvaluationExport.ES_CRJOB.name());
		Map<?, ?> idplst = (Map<?, ?>) export.get(EvaluationExport.ES_IDPLST.name());
		ArrayList<Map<?, ?>> requalList = (ArrayList<Map<?, ?>>) export.get(EvaluationExport.ET_REQUAL.name());
		ArrayList<Map<?, ?>> hpqualList = (ArrayList<Map<?, ?>>) export.get(EvaluationExport.ET_HPQUAL.name());

		mav.addObject("eModif", eModif);
		mav.addObject("ePrtbt", ePrtbt);
		mav.addObject("eActbt", eActbt);
		mav.addObject("crjob", crjob);
		mav.addObject("idplst", idplst);
		mav.addObject("requalList", requalList);
		mav.addObject("hpqualList", hpqualList);
		mav.addObject("params", params);

		return mav;

	}

	/**
	 * IDP - 합의 저장
	 *
	 * @param idpCommon
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/idpAgreementProc.do")
	public @ResponseBody Map<?, ?> idpAgreementProc(IdpCommon idpCommon, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");

		Map<?, ?> result = null;
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		HashMap<String, Object> idplstMap = new HashMap<String, Object>();

		try {

			//RFC PARAMETER SETTING START
			String methodName = "";

			for(String apprKey : IdpKeySet.COMMON.getKeys()){
				methodName = apprKey.replace(apprKey.substring(1), apprKey.substring(1).toLowerCase());
				idplstMap.put(apprKey, idpCommon.getClass().getDeclaredMethod("get" + methodName).invoke(idpCommon));
			}

			rfcParam.put(EvaluationImport.IS_IDPLST.name(), idplstMap);
			rfcParam.put(EvaluationImport.I_BUTTON.name(), idpCommon.getButton());
			rfcParam.put(EvaluationImport.I_COMMENT.name(), idpCommon.getComment());
			rfcParam.put(EvaluationImport.I_LOGPER.name(), user.getEmpNo());
			//RFC PARAMETER SETTING END

			result = webEssRcv.callRfcFunction(IdpRFC.PROCESS.getRfc(), rfcParam, null, request);

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new IKEP4AjaxException("", ex);
		}

		return result;

	}

	/**
	 * IDP - 조회(HSS)
	 *
	 * @param params
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/idpHSS.do")
	public ModelAndView idpHSS(@RequestParam HashMap<String, Object> params, HttpServletRequest request) throws Exception {

		Idp idp = Idp.IDP_HSS;
		ModelAndView mav = new ModelAndView(idp.getView());

		User user = (User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isSystemAdmin(user);

		/***********************************
		 * Teamview
		 **********************************/
		String searchRootGroupId="";
		List<Map<String, Object>> list = null;

		if(isSystemAdmin){
			searchRootGroupId="";//전체인원
			list = getOrgGroupAndUser(searchRootGroupId, "", "USER");//내 부서 인원만.
		}else{
			List<MssTeamTreeSpecial> specialList = mssTeamTreeSpecialService.getList(user.getUserId());
			if(specialList!=null && specialList.size()>0){
				list = getOrgGroupAndUser3(specialList, "", "USER");//팀뷰어 예외자
			}else{
				searchRootGroupId=user.getGroupId();
				if("22".equals(user.getJobDutyCode())){//반장이면
					list = getOrgGroupAndUser2(searchRootGroupId, "", "USER");//소속 인원만.
				}else{
					list = getOrgGroupAndUser(searchRootGroupId, "", "USER");//내 부서 인원만.
				}
			}
		}

		try {

			ObjectMapper objectMapper = new ObjectMapper();
			mav.addObject("deptItems", objectMapper.writeValueAsString(list));

		} catch (Exception e) {
			throw new IKEP4AjaxException("", e);
		}

		mav.addObject("params", params);

		return mav;

	}

	/**
	 * IDP - 조회(HSS) - 목록
	 *
	 * @param params
	 * @param request
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/idpHSSList.do")
	public @ResponseBody Map<?, ?> idpHSSList(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		Map<?, ?> result = null;
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		try {

			String empNo = (String) params.get("emp_no");

			//RFC PARAMETER SETTING START
			rfcParam.put(EvaluationImport.I_IDPAE.name(), empNo);
			//RFC PARAMETER SETTING END

			result = webEssRcv.callRfcFunction(IdpRFC.LIST.getRfc(), rfcParam, null, request);

			Map userInfo = (HashMap<String, String>) this.userDao.empNoToUserId(empNo);
			result.putAll(userInfo);

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new IKEP4AjaxException("", ex);
		}

		return result;

	}

	/**
	 * IDP - 조회(HSS) - 상세
	 *
	 * @param params
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/idpHSSView.do")
	public ModelAndView idpHSSView(@RequestParam HashMap<String, Object> params, HttpServletRequest request) throws Exception {

		Idp idp = Idp.IDP_HSS_VIEW;
		ModelAndView mav = new ModelAndView(idp.getView());

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> apprMap = new HashMap<String, Object>();

		for(String apprKey : IdpKeySet.COMMON.getKeys()){
			apprMap.put(apprKey, params.get(apprKey));
		}
		rfcParam.put(EvaluationImport.IS_IDPLST.name(), apprMap);
		rfcParam.put(EvaluationImport.I_IDPAE.name(), params.get("IDPAE"));
		rfcParam.put(EvaluationImport.I_HISTO.name(), "X");
		//RFC PARAMETER SETTING END

		Map<?, ?> export = webEssRcv.callRfcFunction(idp.getIdpRFC().getRfc(), rfcParam, null, request);

		Map<?, ?> crjob = (Map<?, ?>)export.get(EvaluationExport.ES_CRJOB.name());
		Map<?, ?> idplst = (Map<?, ?>)export.get(EvaluationExport.ES_IDPLST.name());
		ArrayList<Map<?, ?>> requalList = (ArrayList<Map<?, ?>>)export.get(EvaluationExport.ET_REQUAL.name());
		ArrayList<Map<?, ?>> hpqualList = (ArrayList<Map<?, ?>>)export.get(EvaluationExport.ET_HPQUAL.name());

		mav.addObject("crjob", crjob);
		mav.addObject("idplst", idplst);
		mav.addObject("requalList", requalList);
		mav.addObject("hpqualList", hpqualList);
		mav.addObject("params", params);

		return mav;

	}

	@RequestMapping(value = "/idpRemote.do")
	public ModelAndView idpRemote(@RequestParam(value="empno", required=true) String empno, HttpServletRequest request) throws Exception {

		Idp idp = Idp.IDP_REMOTE;
		ModelAndView mav = new ModelAndView(idp.getView());

		mav.addObject("empno", empno);

		return mav;

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/idpRemoteView.do")
	public ModelAndView idpRemoteView(@RequestParam HashMap<String, Object> params, HttpServletRequest request) throws Exception {

		Idp idp = Idp.IDP_REMOTE_VIEW;
		ModelAndView mav = new ModelAndView(idp.getView());

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> apprMap = new HashMap<String, Object>();

		for(String apprKey : IdpKeySet.COMMON.getKeys()){
			apprMap.put(apprKey, params.get(apprKey));
		}
		rfcParam.put(EvaluationImport.IS_IDPLST.name(), apprMap);
		rfcParam.put(EvaluationImport.I_IDPAE.name(), params.get("IDPAE"));
		//RFC PARAMETER SETTING END

		Map<?, ?> export = webEssRcv.callRfcFunction(idp.getIdpRFC().getRfc(), rfcParam, null, request);

		Map<?, ?> crjob = (Map<?, ?>)export.get(EvaluationExport.ES_CRJOB.name());
		Map<?, ?> idplst = (Map<?, ?>)export.get(EvaluationExport.ES_IDPLST.name());
		ArrayList<Map<?, ?>> requalList = (ArrayList<Map<?, ?>>)export.get(EvaluationExport.ET_REQUAL.name());
		ArrayList<Map<?, ?>> hpqualList = (ArrayList<Map<?, ?>>)export.get(EvaluationExport.ET_HPQUAL.name());

		mav.addObject("crjob", crjob);
		mav.addObject("idplst", idplst);
		mav.addObject("requalList", requalList);
		mav.addObject("hpqualList", hpqualList);
		mav.addObject("params", params);

		return mav;

	}

	/**
	 * 조직도와 사용자 조회를 하기 위한 메서드
	 *
	 * @param groupId 조직도에서 조회하고자 하는 그룹 ID
	 * @param selectType GROUP : 조직도만 조회, USER : 사용자 조회, ALL : 전체 조회
	 * @return 조회된 그룹과 사용자 리스트 Map
	 */
	private List<Map<String, Object>> getOrgGroupAndUser(String groupId, String userId, String selectType) {

		User sessionuser = (User) getRequestAttribute("ikep.user");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		String searchUserId = "";
		if (StringUtil.isEmpty(userId)) {
			searchUserId = sessionuser.getUserId();
		} else {
			searchUserId = userId;
		}

		Group searchgroup = new Group();
		searchgroup.setGroupId(groupId);
		searchgroup.setRegisterId(searchUserId);

		// 부서
		List<Group> groupList = groupService.selectOrgGroup(searchgroup);

		if(!"".equals(groupId)){
			Group sessionUserGroup = groupService.read(groupId);

			Map<String, Object> smap = new HashMap<String, Object>();
			smap.put("type", "group");
			smap.put("name", sessionUserGroup.getGroupName());
			smap.put("code", sessionUserGroup.getGroupId());
			smap.put("groupTypeId", sessionUserGroup.getGroupTypeId());
			smap.put("parent", sessionUserGroup.getParentGroupId());
			smap.put("hasChild", sessionUserGroup.getChildGroupCount());
			list.add(smap);
		}

		for (Group group : groupList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("type", "group");
			if (sessionuser.getLocaleCode().equals("ko")) {
				map.put("name", group.getGroupName());
			} else {
				map.put("name", group.getGroupEnglishName());
			}
			map.put("code", group.getGroupId());
			map.put("groupTypeId", group.getGroupTypeId());
			map.put("parent", group.getParentGroupId());
			map.put("hasChild", group.getChildGroupCount());
			list.add(map);
		}

		// 사용자추가
		if (!(selectType.equals("GROUP"))) {

			List<User> userList = userService.selectAllForTree(sessionuser.getLocaleCode(), groupId, searchUserId);
			for (User user : userList) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("type", "user");
				String strJobTitle = user.getJobTitleName();
				if(!StringUtil.isEmpty(user.getJobDutyName()))
				{
					strJobTitle = user.getJobDutyName();
				}

				map.put("name", user.getUserName() + " " + strJobTitle);
				map.put("jobTitle", strJobTitle);
				map.put("teamName", user.getTeamName());
				map.put("code", "");
				map.put("parent", user.getGroupId());
				map.put("id", user.getUserId());
				map.put("empNo", user.getEmpNo());
				map.put("email", user.getMail());
				map.put("mobile", user.getMobile());
				map.put("leader", user.getLeader());
				list.add(map);
			}
		}

		return list;
	}

	/**
	 * @param groupId
	 * @param userId
	 * @param selectType
	 * @return
	 */
	private List<Map<String, Object>> getOrgGroupAndUser2(String groupId, String userId, String selectType) {

		User sessionuser = (User) getRequestAttribute("ikep.user");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		String searchUserId = "";
		if (StringUtil.isEmpty(userId)) {
			searchUserId = sessionuser.getUserId();
		} else {
			searchUserId = userId;
		}

		Group searchgroup = new Group();
		searchgroup.setGroupId(groupId);
		searchgroup.setRegisterId(searchUserId);

		// 부서
		List<Group> groupList = groupService.selectOrgGroup(searchgroup);

		if(!"".equals(groupId)){
			Group sessionUserGroup = groupService.read(groupId);

			Map<String, Object> smap = new HashMap<String, Object>();
			smap.put("type", "group");
			smap.put("name", sessionUserGroup.getGroupName());
			smap.put("code", sessionUserGroup.getGroupId());
			smap.put("groupTypeId", sessionUserGroup.getGroupTypeId());
			smap.put("parent", sessionUserGroup.getParentGroupId());
			smap.put("hasChild", sessionUserGroup.getChildGroupCount());
			list.add(smap);
		}

		for (Group group : groupList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("type", "group");
			if (sessionuser.getLocaleCode().equals("ko")) {
				map.put("name", group.getGroupName());
			} else {
				map.put("name", group.getGroupEnglishName());
			}
			map.put("code", group.getGroupId());
			map.put("groupTypeId", group.getGroupTypeId());
			map.put("parent", group.getParentGroupId());
			map.put("hasChild", group.getChildGroupCount());
			list.add(map);
		}
		//System.out.println("@@@@@@@@@@@@@@@@@@@@@@ sessionuser.getScheduleManager():"+sessionuser.getScheduleManager()+"@@");
		// 사용자추가
		if (!(selectType.equals("GROUP"))) {

			List<User> userList = userService.selectAllForTree(sessionuser.getLocaleCode(), groupId, searchUserId);
			for (User user : userList) {
				//System.out.println("@@@@@@@@@@@@@@@@@@@@@@ user.getScheduleManager():"+user.getScheduleManager()+"@@");

				if((sessionuser.getScheduleManager()).equals(user.getScheduleManager())){//동일한 스케줄 인원이면 더한다. 즉 같은 반장 소속이면.
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("type", "user");


					String strJobTitle = user.getJobTitleName();
					if(!StringUtil.isEmpty(user.getJobDutyName()))
					{
						strJobTitle = user.getJobDutyName();
					}

					map.put("name", user.getUserName() + " " + strJobTitle);
					map.put("jobTitle", strJobTitle);
					map.put("teamName", user.getTeamName());

					map.put("code", "");
					map.put("parent", user.getGroupId());
					map.put("id", user.getUserId());
					map.put("empNo", user.getEmpNo());
					map.put("email", user.getMail());
					map.put("mobile", user.getMobile());
					map.put("leader", user.getLeader());
					list.add(map);
				}
			}
		}

		return list;
	}

	/**
	 * @param specialGroupList
	 * @param userId
	 * @param selectType
	 * @return
	 */
	private List<Map<String, Object>> getOrgGroupAndUser3(List<MssTeamTreeSpecial> specialGroupList, String userId, String selectType) {

		User sessionuser = (User) getRequestAttribute("ikep.user");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		for (MssTeamTreeSpecial specialGroup : specialGroupList) {
			Group searchgroup = new Group();
			searchgroup.setGroupId(specialGroup.getGroupId());
			searchgroup.setRegisterId(specialGroup.getUserId());


			Group specialRootGroup = groupService.read(specialGroup.getGroupId());

			Map<String, Object> smap = new HashMap<String, Object>();


			// 부서
			if(specialGroup.getChildGroupCnt() < 1){


				List<Group> groupList = groupService.selectOrgGroup(searchgroup);

				if(groupList.size() == 0){
					groupList = groupService.selectOrgGroupSp(searchgroup);
				}

				for (Group group : groupList) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("type", "group");
					if (sessionuser.getLocaleCode().equals("ko")) {
						map.put("name", group.getGroupName());
					} else {
						map.put("name", group.getGroupEnglishName());
					}
					map.put("code", group.getGroupId());
					map.put("groupTypeId", group.getGroupTypeId());
					map.put("parent", group.getParentGroupId());
					map.put("hasChild", group.getChildGroupCount());
					list.add(map);
				}
			}else{
				smap.put("type", "group");
				smap.put("name", specialRootGroup.getGroupName());
				smap.put("code", specialRootGroup.getGroupId());
				smap.put("groupTypeId", specialRootGroup.getGroupTypeId());
				smap.put("parent", specialRootGroup.getParentGroupId());
				smap.put("hasChild", specialRootGroup.getChildGroupCount());
				list.add(smap);

			}
		}

		return list;

	}

}
