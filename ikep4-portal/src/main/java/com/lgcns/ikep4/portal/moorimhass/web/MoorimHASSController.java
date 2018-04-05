package com.lgcns.ikep4.portal.moorimhass.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.comomon.Constant;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.portal.login.model.UserAccount;
import com.lgcns.ikep4.portal.login.service.LoginUserDetailsService;
import com.lgcns.ikep4.portal.moorimmss.model.MssTeamTreeSpecial;
import com.lgcns.ikep4.portal.moorimmss.service.MssTeamTreeSpecialService;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.group.model.Group;
import com.lgcns.ikep4.support.user.group.service.GroupService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.service.UserService;
import com.lgcns.ikep4.util.encrypt.EncryptUtil;
import com.lgcns.ikep4.util.jco.WebESSRcvRFC;
import com.lgcns.ikep4.util.lang.DateUtil;
import com.lgcns.ikep4.util.lang.StringUtil;

@Controller
@RequestMapping(value="/portal/moorimhass")
public class MoorimHASSController extends BaseController {

	/** The acl service. */
	@Autowired
	private ACLService aclService;
	
	@Autowired
	private LoginUserDetailsService loginUserDetailsService;
	
	@Autowired
	private GroupService groupService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MssTeamTreeSpecialService mssTeamTreeSpecialService;
	
	@Autowired
	private WebESSRcvRFC webEssRcv;
	
	public boolean isSystemAdmin(User user) {
		
		return this.aclService.isSystemAdmin("MoorimMss", user.getUserId());/////////////////// MSS 어드민 송충원을 그대로 쓴다.
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
	
	@RequestMapping(value = "/initHassMain.do")
	public ModelAndView initEssMain(HttpServletRequest request, @RequestParam(value = "whereLink", required = false) String whereLink) {

		ModelAndView mav = new ModelAndView( "/portal/moorimhass/initHassMain");
		
		User user = (User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isSystemAdmin(user);
		String portalId = (String) getRequestAttribute("ikep.portalId");

		mav.addObject("isSystemAdmin", isSystemAdmin);
		mav.addObject("Bigmenu", "initHassMain");
		mav.addObject("sapId", user.getSapId());
		
		UserAccount userAccount = loginUserDetailsService.loadUserByUsername(user.getUserId(), portalId);
		
		//System.out.println("@@@@@@@@@@@@@@@@@@@@@@ sapId:"+user.getSapId());
		//System.out.println("@@@@@@@@@@@@@@@@@@@@@@ sapPwd:"+EncryptUtil.decryptText(userAccount.getPassword()));
		

		mav.addObject("sapPwd", EncryptUtil.decryptText(userAccount.getPassword()));

		
		mav.addObject("serverLinkUrl", getLinkPath(request));
		
		if(StringUtil.isEmpty(whereLink)){
			whereLink="zhr_hass_es_003";
		}
		mav.addObject("whereLink", whereLink);
		return mav;
	}
	
	@RequestMapping(value = "/hassMain.do")
	public ModelAndView essMain(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView( "/portal/moorimhass/hassMain");
		
		User user = (User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isSystemAdmin(user);
		String portalId = (String) getRequestAttribute("ikep.portalId");

		mav.addObject("isSystemAdmin", isSystemAdmin);
		mav.addObject("Bigmenu", "hassMain");
		mav.addObject("sapId", user.getSapId());
		
		UserAccount userAccount = loginUserDetailsService.loadUserByUsername(user.getUserId(), portalId);
		
		//System.out.println("@@@@@@@@@@@@@@@@@@@@@@ sapId:"+user.getSapId());
		//System.out.println("@@@@@@@@@@@@@@@@@@@@@@ sapPwd:"+EncryptUtil.decryptText(userAccount.getPassword()));
		
		mav.addObject("sapPwd", EncryptUtil.decryptText(userAccount.getPassword()));

		mav.addObject("serverLinkUrl", getLinkPath(request));
		mav.addObject("whereLink", "zhr_hass_es_003");
		return mav;
	}
	
	@RequestMapping(value = "/hassLink.do")
	public ModelAndView pesonalMng(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView( "/portal/moorimhass/hassLink");
		
		User user = (User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isSystemAdmin(user);
		
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
				//System.out.println("@@@@@@@@@@@@@@@@@@@@@@ getJobDutyCode:"+user.getJobDutyCode()+"@@");
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
		mav.addObject("isSystemAdmin", isSystemAdmin);
		mav.addObject("Bigmenu", "hassLink");
		mav.addObject("serverLinkUrl", getLinkPath(request));

		return mav;
	}
	
	@RequestMapping(value = "/webHassLink.do")
	public ModelAndView webPesonalMng(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView( "/portal/moorimhass/webHassLink");
		
		User user = (User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isSystemAdmin(user);
		
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
				//System.out.println("@@@@@@@@@@@@@@@@@@@@@@ getJobDutyCode:"+user.getJobDutyCode()+"@@");
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
		mav.addObject("isSystemAdmin", isSystemAdmin);
		mav.addObject("Bigmenu", "hassLink");
		mav.addObject("serverLinkUrl", getLinkPath(request));

		return mav;
	}
	
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
	private List<Map<String, Object>> getOrgGroupAndUser3(List<MssTeamTreeSpecial> specialGroupList, String userId, String selectType) {

		User sessionuser = (User) getRequestAttribute("ikep.user");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		String searchUserId = "";


		
		for (MssTeamTreeSpecial specialGroup : specialGroupList) {
			Group searchgroup = new Group();
			searchgroup.setGroupId(specialGroup.getGroupId());
			searchgroup.setRegisterId(specialGroup.getUserId());
	

			Group specialRootGroup = groupService.read(specialGroup.getGroupId());
			
			Map<String, Object> smap = new HashMap<String, Object>();
			smap.put("type", "group");
			smap.put("name", specialRootGroup.getGroupName());
			smap.put("code", specialRootGroup.getGroupId());
			smap.put("groupTypeId", specialRootGroup.getGroupTypeId());
			smap.put("parent", specialRootGroup.getParentGroupId());
			smap.put("hasChild", specialRootGroup.getChildGroupCount());
			//list.add(smap);
			
			
			// 부서
			
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
		}

		// 사용자추가
		/*
		if (!(selectType.equals("GROUP")) && specialGroupList.size()==1) {
			
			if (StringUtil.isEmpty(userId)) {
				searchUserId = sessionuser.getUserId();
			} else {
				searchUserId = userId;
			}
			
			
			Group searchgroup = new Group();
			searchgroup.setGroupId(sessionuser.getGroupId());
			searchgroup.setRegisterId(userId);
				// 부서

			List<User> userList = userService.selectAllForTree(sessionuser.getLocaleCode(), searchgroup.getGroupId(), searchUserId);
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
		 */	
		return list;

	}
	
	//HR결재 RFC 전환작업
	@RequestMapping(value = "/hrApproval.do")
	public ModelAndView hrApproval(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/moorimhass/hrApproval");

		User user = (User) getRequestAttribute("ikep.user");

		String thisYear = DateUtil.getToday("yyyy");
		String thisMonth = DateUtil.getToday("MM").toString();

		if(!params.containsKey("imYear")){
			params.put("imYear", thisYear);
		}

		if(!params.containsKey("imMonth")){
			params.put("imMonth", thisMonth);
		}
		
		params.put("imPernr", user.getEmpNo());
		
		if("true".equals((String)params.get("setEmpFlag"))){
			params.put("imPernr", params.get("selEmpNo"));
		}

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		rfcParam.put("IM_YEAR" , params.get("imYear"));
		rfcParam.put("IM_MONTH", params.get("imMonth"));
		rfcParam.put("IM_PERNR", params.get("imPernr"));
		//RFC PARAMETER SETTING END
		
		Map<?, ?> resultHeader = webEssRcv.callRfcFunction("ZHR_RFC_PA_001_GET_DATA", rfcParam, null, request);

		Map<?, ?> resultList = webEssRcv.callRfcFunction("ZHR_RFC_ES_003_GET_DATA", rfcParam, null, request);

		Map<?, ?> resultCode = webEssRcv.callRfcFunction("ZHR_RFC_ES_003_GET_LISTBOX", rfcParam, null, request);

		mav.addObject("resultHeader", resultHeader);
		mav.addObject("resultList"  , resultList);
		mav.addObject("yearList"    , resultCode.get("ET_YEAR"));
		mav.addObject("monthList"   , resultCode.get("ET_MONTH"));
		mav.addObject("params"      , params);
		mav.addObject("keySet"      , Constant.hrApprovalListKeySet);

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
		rfcParam.put("IM_PERNR", params.get("imPernr"));
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

		rfcParam.put("IM_PERNR", params.get("imPernr"));

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
		rfcParam.put("IM_PERNR"      , params.get("imPernr"));
		rfcParam.put("IM_ASTAT"      , params.get("IM_ASTAT"));
		rfcParam.put("IM_APPNR"      , params.get("imPernr"));
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
		rfcParam.put("IM_PERNR"      , params.get("imPernr"));
		rfcParam.put("IM_ASTAT"      , params.get("IM_ASTAT"));
		rfcParam.put("IM_APPNR"      , params.get("imPernr"));
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
			params.put("viewName", "/portal/moorimhass/holidayApprovalPop");
			params.put("rfcName" , "ZHR_RFC_WF_001_ONINIT");
			params.put("keySet"  , Constant.holidayListKeySet);
		}else if("2002".equals(infty)){
			params.put("rfcName", "ZHR_RFC_WF_011_ONINIT");
			params.put("viewName", "/portal/moorimhass/businessTripApprovalPop");
			params.put("keySet"  , Constant.businessTripListKeySet);
		}else if("9208".equals(infty)){
			params.put("rfcName", "ZHR_RFC_WF_008_ONINIT");
			params.put("viewName", "/portal/moorimhass/leaveReinstatementApprovalPop");
			params.put("keySet"  , Constant.leaveReinstatementKeySet);
		}else if("2005".equals(infty)){
			params.put("rfcName", "ZHR_RFC_WF_007_ONINIT");
			params.put("viewName", "/portal/moorimhass/holidayWorkApprovalPop");
			params.put("keySet"  , Constant.holidayWorkListKeySet);
		}else if("9502".equals(infty)){
			params.put("rfcName", "ZHR_RFC_WF_009_ONINIT");
			params.put("viewName", "/portal/moorimhass/congratulateApprovalPop");
			params.put("keySet"  , Constant.congratulateListKeySet);
		}else if("9999".equals(infty)){
			params.put("rfcName", "ZHR_RFC_WF_013_ONINIT");
			params.put("viewName", "/portal/moorimhass/expenseApprovalPop");
			params.put("keySet"  , Constant.expenseListKeySet);
		}else if("9503".equals(infty)){
			params.put("rfcName", "ZHR_RFC_WF_003_ONINIT");
			params.put("viewName", "/portal/moorimhass/clubApprovalPop");
			params.put("keySet"  , Constant.clubListKeySet);
		}else if("9970".equals(infty)){
			params.put("rfcName"    , "ZHR_RFC_WF_022_ONINIT");
			params.put("viewName"   , "/portal/moorimhass/yearlyEduApprovalPop");
			params.put("keySet"     , Constant.eduApproveKeySet);
			params.put("dListkeySet", Constant.eduDListKeySet);
		}else if("9971".equals(infty) || "9976".equals(infty) || "9977".equals(infty) || "9978".equals(infty)){
			params.put("rfcName"    , "ZHR_RFC_WF_023_ONINIT");
			params.put("viewName"   , "/portal/moorimhass/cancelApprovalPop");
			params.put("keySet"     , Constant.eduApproveKeySet);
			params.put("dListkeySet", Constant.eduDListKeySet);
		}else if("9972".equals(infty) || "9973".equals(infty) || "9974".equals(infty)){
			params.put("rfcName"    , "ZHR_RFC_WF_024_ONINIT");
			params.put("viewName"   , "/portal/moorimhass/frequentEduApprovalPop");
			params.put("keySet"     , Constant.eduApproveKeySet);
			params.put("dListkeySet", Constant.eduDListKeySet);
		}else if("9975".equals(infty)){
			params.put("rfcName"    , "ZHR_RFC_WF_025_ONINIT");
			params.put("viewName"   , "/portal/moorimhass/reportEduApprovalPop");
			params.put("keySet"     , "");
			params.put("dListkeySet", Constant.reportEduDlistKeySet);
		}
	}
}
