package com.lgcns.ikep4.portal.moorimess.web;

import java.util.ArrayList;
import java.util.Arrays;
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

import com.lgcns.ikep4.comomon.Constant;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.portal.login.model.UserAccount;
import com.lgcns.ikep4.portal.login.service.LoginUserDetailsService;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.role.service.RoleService;
import com.lgcns.ikep4.util.encrypt.EncryptUtil;
import com.lgcns.ikep4.util.jco.WebESSRcvRFC;
import com.lgcns.ikep4.util.lang.DateUtil;
import com.lgcns.ikep4.util.lang.StringUtil;

@Controller
@RequestMapping(value="/portal/moorimess")
public class MoorimESSController extends BaseController {

	/** The acl service. */
	@Autowired
	private ACLService aclService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private LoginUserDetailsService loginUserDetailsService;

	@Autowired
	private WebESSRcvRFC webEssRcv;

	//Log
	protected final Log log = LogFactory.getLog(this.getClass());

	public boolean isSystemAdmin(User user) {

		return this.aclService.isSystemAdmin("MoorimEss", user.getUserId());
	}

	public boolean isBwSystemAdmin(User user) {

		return this.aclService.isSystemAdmin("BwLink", user.getUserId());
	}

	public String getLinkPath(HttpServletRequest request) {
		String serverLinkUrl = "";

		if((request.getServerName().toLowerCase()).indexOf("eptest.moorim.co.kr")>-1) {
			serverLinkUrl ="http://smrmeqas.moorim.co.kr:8000/sap/bc/bsp/sap/";
		} else {
			serverLinkUrl ="http://smrmep02.moorim.co.kr:8000/sap/bc/bsp/sap/";
		}
		return serverLinkUrl;
	}

	public String getBwLinkPath(HttpServletRequest request) {
		String serverLinkUrl = "";

		if((request.getServerName().toLowerCase()).indexOf("eptest.moorim.co.kr")>-1) {
			serverLinkUrl ="http://smrmbdev.moorim.co.kr:8000/sap/";
		} else {
			serverLinkUrl ="http://smrmbp01.moorim.co.kr:8000/sap/";
		}
		return serverLinkUrl;
	}


	public String getPassword(User user, String portalId){

		UserAccount userAccount = loginUserDetailsService.loadUserByUsername(user.getUserId(), portalId);


		String passwordStr ="";
		if("moorim12".equals( user.getSapId().toLowerCase())){//아이디는 대문자로 들어오고
			passwordStr = user.getSapId().toLowerCase();//패스워드는 소문자로 넘긴다.
		}else{
			passwordStr = EncryptUtil.decryptText(userAccount.getPassword());
		}

		return passwordStr;
	}
	public String getBWPassword(User user, String portalId){

		UserAccount userAccount = loginUserDetailsService.loadUserByUsername(user.getUserId(), portalId);


		String passwordStr ="";
		if(user.getBwId().toLowerCase().contains("epbwif")){//아이디는 대문자로 들어오고 EPBWIF01~...
			passwordStr = user.getBwId().toLowerCase();//패스워드는 소문자로 넘긴다.
		}else{
			passwordStr = EncryptUtil.decryptText(userAccount.getPassword());
		}

		return passwordStr;
	}

	@RequestMapping(value = "/initEssMain.do")
	public ModelAndView initEssMain(HttpServletRequest request, @RequestParam(value = "whereLink", required = false) String whereLink) {

		ModelAndView mav = new ModelAndView( "/portal/moorimess/initEssMain");

		User user = (User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isSystemAdmin(user);
		String portalId = (String) getRequestAttribute("ikep.portalId");

		mav.addObject("isSystemAdmin", isSystemAdmin);
		mav.addObject("Bigmenu", "initEssMain");
		mav.addObject("sapId", user.getSapId());

		UserAccount userAccount = loginUserDetailsService.loadUserByUsername(user.getUserId(), portalId);

		mav.addObject("sapPwd", getPassword(user, portalId) );

		mav.addObject("serverLinkUrl", getLinkPath(request));

		if(StringUtil.isEmpty(whereLink)){
			whereLink="zhr_es_001";
		}
		mav.addObject("whereLink", whereLink);
		return mav;
	}

	@RequestMapping(value = "/essMain.do")
	public ModelAndView essMain(HttpServletRequest request) {

		ModelAndView mav = null;

		User user = (User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isSystemAdmin(user);
		String portalId = (String) getRequestAttribute("ikep.portalId");



		if("E9".equals(user.getEssAuthCode())){
			mav = new ModelAndView( "/portal/moorimess/payDiligenceMng");
			mav.addObject("Bigmenu", "payDiligenceMng");
		}else{
			mav = new ModelAndView( "/portal/moorimess/essMain");
			mav.addObject("Bigmenu", "essMain");
			mav.addObject("sapId", user.getSapId());

			UserAccount userAccount = loginUserDetailsService.loadUserByUsername(user.getUserId(), portalId);


			mav.addObject("sapPwd", getPassword(user, portalId));
			mav.addObject("whereLink", "zhr_es_001");
		}
		mav.addObject("isSystemAdmin", isSystemAdmin);
		mav.addObject("serverLinkUrl", getLinkPath(request));
		return mav;
	}


	@RequestMapping(value = "/initBwMain.do")
	public ModelAndView initBwMain(HttpServletRequest request, @RequestParam(value = "whereLink", required = false) String whereLink) {

		ModelAndView mav = new ModelAndView( "/portal/moorimess/initBwMain");

		User user = (User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isSystemAdmin(user);
		String portalId = (String) getRequestAttribute("ikep.portalId");

		mav.addObject("isSystemAdmin", isSystemAdmin);
		mav.addObject("Bigmenu", "initBwMain");
		mav.addObject("bwId", user.getBwId());

		UserAccount userAccount = loginUserDetailsService.loadUserByUsername(user.getUserId(), portalId);


		mav.addObject("bwPwd", getBWPassword(user, portalId) );

		mav.addObject("serverLinkUrl", getBwLinkPath(request));

		if(StringUtil.isEmpty(whereLink)){
			whereLink="bw/BEx?sap-language=KO&bsplanguage=KO&CMD=LDOC&TEMPLATE_ID=ZEIS_C01_WT001";
		}
		mav.addObject("whereLink", whereLink);
		return mav;
	}

	@RequestMapping(value = "/bwMain.do")
	public ModelAndView bwMain(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView( "/portal/moorimess/systemLinkBW");

		User user = (User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isSystemAdmin(user);
		String portalId = (String) getRequestAttribute("ikep.portalId");


		mav.addObject("bwId", user.getBwId());

		UserAccount userAccount = loginUserDetailsService.loadUserByUsername(user.getUserId(), portalId);


		mav.addObject("bwPwd", getBWPassword(user, portalId));


		return mav;
	}

	@RequestMapping(value = "/bwNewMain.do")
	public ModelAndView bwNewMain(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView( "/portal/moorimess/systemNewLinkBW");

		User user = (User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isSystemAdmin(user);
		String portalId = (String) getRequestAttribute("ikep.portalId");


		mav.addObject("bwId", user.getBwId());

		UserAccount userAccount = loginUserDetailsService.loadUserByUsername(user.getUserId(), portalId);


		mav.addObject("bwPwd", getBWPassword(user, portalId));


		return mav;
	}

	@RequestMapping(value = "/bwRealtimeReport.do")
	public ModelAndView bwRealtimeReport(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView( "/portal/moorimess/systemLinkRealtimeReport");

		User user = (User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isSystemAdmin(user);
		String portalId = (String) getRequestAttribute("ikep.portalId");


		mav.addObject("bwId", user.getBwId());

		UserAccount userAccount = loginUserDetailsService.loadUserByUsername(user.getUserId(), portalId);


		mav.addObject("bwPwd", getBWPassword(user, portalId));


		return mav;
	}


	@RequestMapping(value = "/bwLink.do")
	public ModelAndView bwLink(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView( "/portal/moorimess/bwLink");

		User user = (User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isBwSystemAdmin(user);

		mav.addObject("isBwLinkSystemAdmin", isSystemAdmin);
		mav.addObject("serverLinkUrl", getBwLinkPath(request));

		List<Map<String, String>> roleUserList = roleService.selectRoleUserList("100000803127");//ACL1 KM_메뉴권한_임원_팀장
		Boolean isAcl=false;
		for (Map<String, String> roleUser : roleUserList) {
			if( (user.getUserId()).equals(roleUser.get("userId")) ){
				isAcl=true;
				break;
			}
		}
		mav.addObject("isAcl", isAcl);

		return mav;
	}


	@RequestMapping(value = "/personalMng.do")
	public ModelAndView pesonalMng(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView( "/portal/moorimess/personalMng");

		User user = (User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isSystemAdmin(user);

		mav.addObject("isSystemAdmin", isSystemAdmin);
		mav.addObject("Bigmenu", "personalMng");
		mav.addObject("serverLinkUrl", getLinkPath(request));

		return mav;
	}
	
	@RequestMapping(value = "/webPersonalMng.do")
	public ModelAndView webPesonalMng(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView( "/portal/moorimess/webPersonalMng");

		User user = (User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isSystemAdmin(user);

		mav.addObject("isSystemAdmin", isSystemAdmin);
		mav.addObject("Bigmenu", "personalMng");
		mav.addObject("serverLinkUrl", getLinkPath(request));

		return mav;
	}



	@RequestMapping(value = "/evaluationMng.do")
	public ModelAndView evaluationMng(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView( "/portal/moorimess/evaluationMng");

		User user = (User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isSystemAdmin(user);

		mav.addObject("isSystemAdmin", isSystemAdmin);
		mav.addObject("Bigmenu", "evaluationMng");
		mav.addObject("serverLinkUrl", getLinkPath(request));
		return mav;
	}
	
	@RequestMapping(value = "/webEvaluationMng.do")
	public ModelAndView webEvaluationMng(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView( "/portal/moorimess/webEvaluationMng");

		User user = (User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isSystemAdmin(user);

		mav.addObject("isSystemAdmin", isSystemAdmin);
		mav.addObject("Bigmenu", "evaluationMng");
		mav.addObject("serverLinkUrl", getLinkPath(request));
		return mav;
	}

	@RequestMapping(value = "/manPowerMng.do")
	public ModelAndView manPowerMng(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView( "/portal/moorimess/manPowerMng");

		User user = (User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isSystemAdmin(user);

		mav.addObject("isSystemAdmin", isSystemAdmin);
		mav.addObject("Bigmenu", "manPowerMng");
		mav.addObject("serverLinkUrl", getLinkPath(request));
		return mav;
	}
	
	@RequestMapping(value = "/webManPowerMng.do")
	public ModelAndView webManPowerMng(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView( "/portal/moorimess/webManPowerMng");

		User user = (User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isSystemAdmin(user);

		mav.addObject("isSystemAdmin", isSystemAdmin);
		mav.addObject("Bigmenu", "manPowerMng");
		mav.addObject("serverLinkUrl", getLinkPath(request));
		return mav;
	}

	@RequestMapping(value = "/diligenceMng.do")
	public ModelAndView diligenceMng(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView( "/portal/moorimess/diligenceMng");

		User user = (User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isSystemAdmin(user);

		mav.addObject("isSystemAdmin", isSystemAdmin);
		mav.addObject("Bigmenu", "diligenceMng");
		mav.addObject("serverLinkUrl", getLinkPath(request));
		return mav;
	}
	
	@RequestMapping(value = "/webDiligenceMng.do")
	public ModelAndView webDiligenceMng(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView( "/portal/moorimess/webDiligenceMng");

		User user = (User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isSystemAdmin(user);

		mav.addObject("isSystemAdmin", isSystemAdmin);
		mav.addObject("Bigmenu", "diligenceMng");
		mav.addObject("serverLinkUrl", getLinkPath(request));
		return mav;
	}

	@RequestMapping(value = "/payMng.do")
	public ModelAndView payMng(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView( "/portal/moorimess/payMng");

		User user = (User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isSystemAdmin(user);

		mav.addObject("isSystemAdmin", isSystemAdmin);
		mav.addObject("Bigmenu", "payMng");
		mav.addObject("serverLinkUrl", getLinkPath(request));
		return mav;
	}
	
	@RequestMapping(value = "/webPayMng.do")
	public ModelAndView webPayMng(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView( "/portal/moorimess/webPayMng");

		User user = (User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isSystemAdmin(user);

		mav.addObject("isSystemAdmin", isSystemAdmin);
		mav.addObject("Bigmenu", "payMng");
		mav.addObject("serverLinkUrl", getLinkPath(request));
		return mav;
	}

	@RequestMapping(value = "/personalDivsionMng.do")
	public ModelAndView personalDivsionMng(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView( "/portal/moorimess/personalDivsionMng");

		User user = (User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isSystemAdmin(user);

		mav.addObject("isSystemAdmin", isSystemAdmin);
		mav.addObject("Bigmenu", "personalDivsionMng");
		mav.addObject("serverLinkUrl", getLinkPath(request));
		return mav;
	}
	
	@RequestMapping(value = "/webPersonalDivsionMng.do")
	public ModelAndView webPersonalDivsionMng(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView( "/portal/moorimess/webPersonalDivsionMng");

		User user = (User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isSystemAdmin(user);

		mav.addObject("isSystemAdmin", isSystemAdmin);
		mav.addObject("Bigmenu", "personalDivsionMng");
		mav.addObject("serverLinkUrl", getLinkPath(request));
		return mav;
	}

	@RequestMapping(value = "/organogramMng.do")
	public ModelAndView organogramMng(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView( "/portal/moorimess/organogramMng");

		User user = (User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isSystemAdmin(user);

		mav.addObject("isSystemAdmin", isSystemAdmin);
		mav.addObject("Bigmenu", "organogramMng");
		mav.addObject("serverLinkUrl", getLinkPath(request));
		return mav;
	}
	
	@RequestMapping(value = "/webOrganogramMng.do")
	public ModelAndView webOrganogramMng(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView( "/portal/moorimess/webOrganogramMng");

		User user = (User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isSystemAdmin(user);

		mav.addObject("isSystemAdmin", isSystemAdmin);
		mav.addObject("Bigmenu", "organogramMng");
		mav.addObject("serverLinkUrl", getLinkPath(request));
		return mav;
	}


	@RequestMapping(value = "/payDiligenceMng.do")
	public ModelAndView payDiligenceMng(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView( "/portal/moorimess/payDiligenceMng");

		User user = (User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isSystemAdmin(user);

		mav.addObject("isSystemAdmin", isSystemAdmin);
		mav.addObject("Bigmenu", "payDiligenceMng");
		mav.addObject("serverLinkUrl", getLinkPath(request));
		return mav;
	}
	
	@RequestMapping(value = "/webPayDiligenceMng.do")
	public ModelAndView webPayDiligenceMng(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView( "/portal/moorimess/webPayDiligenceMng");

		User user = (User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isSystemAdmin(user);

		mav.addObject("isSystemAdmin", isSystemAdmin);
		mav.addObject("Bigmenu", "payDiligenceMng");
		mav.addObject("serverLinkUrl", getLinkPath(request));
		return mav;
	}

	//HR결재 RFC 전환작업
	@RequestMapping(value = "/hrApproval.do")
	public ModelAndView hrApproval(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/moorimess/hrApproval");

		User user = (User) getRequestAttribute("ikep.user");

		String thisYear = DateUtil.getToday("yyyy");
		String thisMonth = DateUtil.getToday("MM").toString();

		if(!params.containsKey("imYear")){
			params.put("imYear", thisYear);
		}

		if(!params.containsKey("imMonth")){
			params.put("imMonth", thisMonth);
		}

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		rfcParam.put("IM_YEAR" , params.get("imYear"));
		rfcParam.put("IM_MONTH", params.get("imMonth"));
		rfcParam.put("IM_PERNR", user.getEmpNo());
		//RFC PARAMETER SETTING END

		Map<?, ?> resultList = webEssRcv.callRfcFunction("ZHR_RFC_ES_003_GET_DATA", rfcParam, null, request);

		Map<?, ?> resultCode = webEssRcv.callRfcFunction("ZHR_RFC_ES_003_GET_LISTBOX", rfcParam, null, request);

		mav.addObject("resultList", resultList);
		mav.addObject("yearList"  , resultCode.get("ET_YEAR"));
		mav.addObject("monthList" , resultCode.get("ET_MONTH"));
		mav.addObject("params"    , params);
		mav.addObject("keySet"    , Constant.hrApprovalListKeySet);

		return mav;
	}

	@RequestMapping(value = "/approvalPop.do")
	public ModelAndView approvalPop(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView();

		this.setViewRfcName(params);

		String rfcName = (String) params.get("rfcName");
		String viewName = (String) params.get("viewName");

		String astat = (String) params.get("ASTAT");

		User user = (User) getRequestAttribute("ikep.user");

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		rfcParam.put("IM_APPID", params.get("APPID"));
		rfcParam.put("IM_APLEV", params.get("APLEV"));
		rfcParam.put("IM_PERNR", user.getEmpNo());
		//RFC PARAMETER SETTING END

		params.put("apprKeySet"    , Constant.apprKeySet);
		params.put("apprAbleKeySet", Constant.apprAbleKeySet);

		Map<?, ?> resultInit = webEssRcv.callRfcFunction(rfcName, rfcParam, null, request);

		if("2002".equals((String) params.get("INFTY"))){
			Map<?, ?> resultCode = webEssRcv.callRfcFunction("ZHR_RFC_WF_011_GET_LISTBOX", null, null, request);
			mav.addObject("resultCode", resultCode);
		}else if("9999".equals((String) params.get("INFTY"))){
			Map<?, ?> resultCode = webEssRcv.callRfcFunction("ZHR_RFC_WF_013_GET_LISTBOX", null, null, request);
			mav.addObject("resultCode", resultCode);
		}else if("9503".equals((String) params.get("INFTY"))){
			Map<?, ?> resultCode = webEssRcv.callRfcFunction("ZHR_RFC_WF_003_GET_LISTBOX", null, null, request);
			mav.addObject("resultCode", resultCode);
		}else if("9970".equals((String) params.get("INFTY"))){
			Map<?, ?> resultCode = webEssRcv.callRfcFunction("ZHR_RFC_WF_022_GET_LISTBOX", null, null, request);
			mav.addObject("resultCode", resultCode);
		}else if("9920".equals((String) params.get("INFTY"))){
			Map<?, ?> resultCode = webEssRcv.callRfcFunction("ZHR_RFC_BE_002_GET_LISTBOX", null, null, request);
			mav.addObject("resultCode", resultCode);
		}

		if("05".equals(astat) || "16".equals(astat) || "13".equals(astat)){
			Map<?, ?> resultRefuse = webEssRcv.callRfcFunction("ZHR_RFC_READ_REFUSE_REASON", rfcParam, null, request);
			mav.addObject("resultRefuse", resultRefuse);
		}

		mav.setViewName(viewName);

		mav.addObject("resultInit", resultInit);
		mav.addObject("params"    , params);

		return mav;
	}

	@RequestMapping(value = "holidayOnInput.do")
	public @ResponseBody Map<?, ?> holidayOnInput(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {
		return this.onInputProcess(Constant.holidayListKeySet, "ZHR_RFC_WF_001_ONINPUT", params, request);
	}

	@RequestMapping(value = "holidayWorkOnInput.do")
	public @ResponseBody Map<?, ?> holidayWorkOnInput(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {
		return this.onInputProcess(Constant.holidayWorkListKeySet, "ZHR_RFC_WF_007_ONINPUT", params, request);
	}

	@RequestMapping(value = "leaveReinstatementOnInput.do")
	public @ResponseBody Map<?, ?> leaveReinstatementOnInput(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {
		return this.onInputProcess(Constant.leaveReinstatementKeySet, "ZHR_RFC_WF_008_ONINPUT", params, request);
	}

	@RequestMapping(value = "businessTripOnInput.do")
	public @ResponseBody Map<?, ?> businessTripOnInput(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {
		return this.onInputProcess(Constant.businessTripListKeySet, "ZHR_RFC_WF_011_ONINPUT", params, request);
	}

	@RequestMapping(value = "congratulateOnInput.do")
	public @ResponseBody Map<?, ?> congratulateOnInput(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {
		return this.onInputProcess(Constant.congratulateListKeySet, "ZHR_RFC_WF_009_ONINPUT", params, request);
	}
	
	@RequestMapping(value = "certificateOnInput.do")
	public @ResponseBody Map<?, ?> certificateOnInput(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {
		return this.onInputProcess(Constant.certificateListKeySet, "ZHR_RFC_WF_014_ONINPUT", params, request);
	}

	@RequestMapping(value = "expenseOnInput.do")
	public @ResponseBody Map<?, ?> expenseOnInput(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {
		return this.onInputProcess(Constant.expenseListKeySet, "ZHR_RFC_WF_013_ONINPUT", params, request);
	}

	@RequestMapping(value = "clubOnInput.do")
	public @ResponseBody Map<?, ?> clubOnInput(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {
		return this.onInputProcess(Constant.clubListKeySet, "ZHR_RFC_WF_003_ONINPUT", params, request);
	}

	@RequestMapping(value = "yearlyEduOnInput.do")
	public @ResponseBody Map<?, ?> yearlyEduOnInput(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {
		return this.onInputProcess(Constant.eduApproveKeySet, Constant.eduDListKeySet, "ZHR_RFC_WF_022_ONINPUT", params, request);
	}

	@RequestMapping(value = "cancelOnInput.do")
	public @ResponseBody Map<?, ?> cancelOnInput(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {
		return this.onInputProcess(Constant.eduApproveKeySet, Constant.eduDListKeySet, "ZHR_RFC_WF_023_ONINPUT", params, request);
	}

	@RequestMapping(value = "frequentEduOnInput.do")
	public @ResponseBody Map<?, ?> frequentEduOnInput(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {
		return this.onInputProcess(Constant.eduApproveKeySet, Constant.eduDListKeySet, "ZHR_RFC_WF_024_ONINPUT", params, request);
	}

	@RequestMapping(value = "reportEduOnInput.do")
	public @ResponseBody Map<?, ?> reportEduOnInput(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {
		return this.onInputProcess(null, Constant.reportEduDlistKeySet, "ZHR_RFC_WF_025_ONINPUT", params, request);
	}

	@RequestMapping(value = "approveProcess.do")
	public @ResponseBody Map<?, ?> approveProcess(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");

		int rowCnt = Integer.parseInt((String) params.get("rowCnt"));


		String keySet[] = Constant.hrApprovalListKeySet;

		ArrayList<HashMap<String, Object>> itList = new ArrayList<HashMap<String, Object>>();

		HashMap<String, Object> paramRow = null;

		if(rowCnt == 1){
			paramRow = new HashMap<String, Object>();
			for(String key : keySet){

				if("CHECKBOX".equals(key)){
					paramRow.put(key, "X");
				}else{
					paramRow.put(key, params.get(key));
				}
			}
			itList.add(paramRow);
		}else{
			for(int i = 0 ; i < rowCnt ; i++){
				paramRow = new HashMap<String, Object>();
				for(String key : keySet){
					if("CHECKBOX".equals(key)){
						paramRow.put(key, "X");
					}else{
						paramRow.put(key, request.getParameterValues(key)[i]);
					}
				}
				itList.add(paramRow);
			}
		}


		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> rfcParamList = new HashMap<String, Object>();

		rfcParam.put("IM_PERNR", user.getEmpNo());

		rfcParamList.put("IT_LIST", itList);
		//RFC PARAMETER SETTING END

		Map<?, ?> result = webEssRcv.callRfcFunction("ZHR_RFC_ES_003_PROCESS", rfcParam, rfcParamList, request);

		return result;
	}

	private Map<?, ?> onInputProcess(String[] keySet, String rfcName, HashMap<String, Object> params, HttpServletRequest request){

		User user = (User) getRequestAttribute("ikep.user");

		String eventId = (String) params.get("eventId");

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> rfcParamList = new HashMap<String, Object>();

		HashMap<String, Object> paramRow = new HashMap<String, Object>();

		rfcParam.put("IM_APPID"      , params.get("APPID"));
		rfcParam.put("IM_APLEV"      , params.get("APLEV"));
		rfcParam.put("IM_PERNR"      , user.getEmpNo());
		rfcParam.put("IM_ASTAT"      , params.get("IM_ASTAT"));
		rfcParam.put("IM_APPNR"      , user.getEmpNo());
		rfcParam.put("IM_OPINION"    , "APPLY".equals(eventId) ? "" : params.get("reason"));
		rfcParam.put("IM_EVENT_ID"   , params.get("eventId")); //승인 APPLY 반려 REFUSE

		for(String key : keySet){
			paramRow.put(key, ((String)params.get("exList_"+key)).trim());
		}
		rfcParam.put("IM_LIST", paramRow);

		ArrayList<HashMap<String, Object>> apprList = new ArrayList<HashMap<String, Object>>();//IT_LINE
		HashMap<String, Object> apprRow = null;

		String apprKeySet[] = Constant.apprKeySet;
		int apprCnt = Integer.parseInt(((String)params.get("etLineCnt")));

		if(apprCnt == 1){
			apprRow = new HashMap<String, Object>();
			for(String apprKey : apprKeySet){
				apprRow.put(apprKey, params.containsKey("etLine_"+apprKey) ? params.get("etLine_"+apprKey) : "");
			}
			apprList.add(apprRow);
		}else if(apprCnt > 1){
			for(int i=0 ; i<apprCnt ; i++){
				apprRow = new HashMap<String, Object>();
				for(String apprKey : apprKeySet){
					apprRow.put(apprKey, request.getParameterValues("etLine_"+apprKey)[i]);
				}
				apprList.add(apprRow);
			}
		}
		rfcParamList.put("IT_LINE" , apprList);

		ArrayList<HashMap<String, Object>> appnrList = new ArrayList<HashMap<String, Object>>();//IT_LINE
		HashMap<String, Object> appnrRow = null;

		String appnrKeySet[] = Constant.apprAbleKeySet;
		int appnrCnt = Integer.parseInt(((String)params.get("etAppnrCnt")));

		if(appnrCnt == 1){
			appnrRow = new HashMap<String, Object>();
			for(String appnrKey : appnrKeySet){
				appnrRow.put(appnrKey, params.containsKey("etAppnr_"+appnrKey) ? params.get("etAppnr_"+appnrKey) : "");
			}
			appnrList.add(apprRow);
		}else if(appnrCnt > 1){
			for(int i=0 ; i<apprCnt ; i++){
				appnrRow = new HashMap<String, Object>();
				for(String appnrKey : appnrKeySet){
					appnrRow.put(appnrKey, request.getParameterValues("etAppnr_"+appnrKey)[i]);
				}
				appnrList.add(apprRow);
			}
		}
		rfcParamList.put("IT_APPNR", appnrList);
		//RFC PARAMETER SETTING END

		Map<?, ?> result = webEssRcv.callRfcFunction(rfcName, rfcParam, rfcParamList, request);

		return result;
	}

	private Map<?, ?> onInputProcess(String[] firKeySet, String[] secKeySet, String rfcName, HashMap<String, Object> params, HttpServletRequest request){

		User user = (User) getRequestAttribute("ikep.user");

		String eventId = (String) params.get("eventId");

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> rfcParamList = new HashMap<String, Object>();

		HashMap<String, Object> paramRow = new HashMap<String, Object>();
		HashMap<String, Object> paramRow2 = new HashMap<String, Object>();

		rfcParam.put("IM_APPID"      , params.get("APPID"));
		rfcParam.put("IM_APLEV"      , params.get("APLEV"));
		rfcParam.put("IM_PERNR"      , user.getEmpNo());
		rfcParam.put("IM_ASTAT"      , params.get("IM_ASTAT"));
		rfcParam.put("IM_APPNR"      , user.getEmpNo());
		rfcParam.put("IM_OPINION"    , "APPLY".equals(eventId) ? "" : params.get("reason"));
		rfcParam.put("IM_EVENT_ID"   , params.get("eventId")); //승인 APPLY 반려 REFUSE

		if(firKeySet != null){
			for(String key : firKeySet){
				paramRow.put(key, ((String)params.get("exList_"+key)).trim());
			}
		}
		rfcParam.put("IM_LIST", paramRow);

		if(secKeySet != null){
			for(String key : secKeySet){
				paramRow2.put(key, ((String)params.get("exDlist_"+key)).trim());
			}
		}
		rfcParam.put("IM_DLIST", paramRow2);

		ArrayList<HashMap<String, Object>> apprList = new ArrayList<HashMap<String, Object>>();//IT_LINE
		HashMap<String, Object> apprRow = null;

		String apprKeySet[] = Constant.apprKeySet;
		int apprCnt = Integer.parseInt(((String)params.get("etLineCnt")));

		if(apprCnt == 1){
			apprRow = new HashMap<String, Object>();
			for(String apprKey : apprKeySet){
				apprRow.put(apprKey, params.containsKey("etLine_"+apprKey) ? params.get("etLine_"+apprKey) : "");
			}
			apprList.add(apprRow);
		}else if(apprCnt > 1){
			for(int i=0 ; i<apprCnt ; i++){
				apprRow = new HashMap<String, Object>();
				for(String apprKey : apprKeySet){
					apprRow.put(apprKey, request.getParameterValues("etLine_"+apprKey)[i]);
				}
				apprList.add(apprRow);
			}
		}
		rfcParamList.put("IT_LINE" , apprList);

		ArrayList<HashMap<String, Object>> appnrList = new ArrayList<HashMap<String, Object>>();//IT_LINE
		HashMap<String, Object> appnrRow = null;

		String appnrKeySet[] = Constant.apprAbleKeySet;
		int appnrCnt = Integer.parseInt(((String)params.get("etAppnrCnt")));

		if(appnrCnt == 1){
			appnrRow = new HashMap<String, Object>();
			for(String appnrKey : appnrKeySet){
				appnrRow.put(appnrKey, params.containsKey("etAppnr_"+appnrKey) ? params.get("etAppnr_"+appnrKey) : "");
			}
			appnrList.add(apprRow);
		}else if(appnrCnt > 1){
			for(int i=0 ; i<apprCnt ; i++){
				appnrRow = new HashMap<String, Object>();
				for(String appnrKey : appnrKeySet){
					appnrRow.put(appnrKey, request.getParameterValues("etAppnr_"+appnrKey)[i]);
				}
				appnrList.add(apprRow);
			}
		}
		rfcParamList.put("IT_APPNR", appnrList);
		//RFC PARAMETER SETTING END

		Map<?, ?> result = webEssRcv.callRfcFunction(rfcName, rfcParam, rfcParamList, request);

		return result;
	}

	private void setViewRfcName(HashMap<String, Object> params){
		/*
		0180	가디언 제도
		0538	중도정산신청
		2001	휴가신청
		2002	출장신청
		2005	휴가근로신청
		9208	휴/복직
		9501	학자금신청
		9502	경조금신청
		9970	연간교육 신청
		9971	연간교육 취소신청
		9972	수시 교육(~ 100만원)
		9973	수시 교육(100 ~ 200만원)
		9974	수시 교육(200만원 이상?)
		9975	결과보고서
		9976	수시교육 취소신청
		9977	연간교육 불참신청
		9978	수시교육 불참
		9994	인력확보요청
		9995	출장정산신청
		9996	교육이수완료신청
		9997	교육불참신청
		9998	교육신청
		9999	이사비신청
		9503	동호회신청
		*/

		String infty = (String) params.get("INFTY");

		if("2001".equals(infty)){
			params.put("viewName", "/portal/moorimess/holidayApprovalPop");
			params.put("rfcName" , "ZHR_RFC_WF_001_ONINIT");
			params.put("keySet"  , Constant.holidayListKeySet);
		}else if("2002".equals(infty)){
			params.put("rfcName", "ZHR_RFC_WF_011_ONINIT");
			params.put("viewName", "/portal/moorimess/businessTripApprovalPop");
			params.put("keySet"  , Constant.businessTripListKeySet);
		}else if("9208".equals(infty)){
			params.put("rfcName", "ZHR_RFC_WF_008_ONINIT");
			params.put("viewName", "/portal/moorimess/leaveReinstatementApprovalPop");
			params.put("keySet"  , Constant.leaveReinstatementKeySet);
		}else if("2005".equals(infty)){
			params.put("rfcName", "ZHR_RFC_WF_007_ONINIT");
			params.put("viewName", "/portal/moorimess/holidayWorkApprovalPop");
			params.put("keySet"  , Constant.holidayWorkListKeySet);
		}else if("9502".equals(infty)){
			params.put("rfcName", "ZHR_RFC_WF_009_ONINIT");
			params.put("viewName", "/portal/moorimess/congratulateApprovalPop");
			params.put("keySet"  , Constant.congratulateListKeySet);
		}else if("9999".equals(infty)){
			params.put("rfcName", "ZHR_RFC_WF_013_ONINIT");
			params.put("viewName", "/portal/moorimess/expenseApprovalPop");
			params.put("keySet"  , Constant.expenseListKeySet);
		}else if("9503".equals(infty)){
			params.put("rfcName", "ZHR_RFC_WF_003_ONINIT");
			params.put("viewName", "/portal/moorimess/clubApprovalPop");
			params.put("keySet"  , Constant.clubListKeySet);
		}else if("9970".equals(infty)){
			params.put("rfcName"    , "ZHR_RFC_WF_022_ONINIT");
			params.put("viewName"   , "/portal/moorimess/yearlyEduApprovalPop");
			params.put("keySet"     , Constant.eduApproveKeySet);
			params.put("dListkeySet", Constant.eduDListKeySet);
		}else if("9971".equals(infty) || "9976".equals(infty) || "9977".equals(infty) || "9978".equals(infty)){
			params.put("rfcName"    , "ZHR_RFC_WF_023_ONINIT");
			params.put("viewName"   , "/portal/moorimess/cancelApprovalPop");
			params.put("keySet"     , Constant.eduApproveKeySet);
			params.put("dListkeySet", Constant.eduDListKeySet);
		}else if("9972".equals(infty) || "9973".equals(infty) || "9974".equals(infty)){
			params.put("rfcName"    , "ZHR_RFC_WF_024_ONINIT");
			params.put("viewName"   , "/portal/moorimess/frequentEduApprovalPop");
			params.put("keySet"     , Constant.eduApproveKeySet);
			params.put("dListkeySet", Constant.eduDListKeySet);
		}else if("9975".equals(infty)){
			params.put("rfcName"    , "ZHR_RFC_WF_025_ONINIT");
			params.put("viewName"   , "/portal/moorimess/reportEduApprovalPop");
			params.put("keySet"     , "");
			params.put("dListkeySet", Constant.reportEduDlistKeySet);
		}else if("9920".equals(infty)){
			params.put("rfcName"    , "ZHR_RFC_WF_014_ONINIT");
			params.put("viewName"   , "/portal/moorimess/certificateApprovalPop");
			params.put("keySet"     , Constant.certificateListKeySet);
		}
	}
}
