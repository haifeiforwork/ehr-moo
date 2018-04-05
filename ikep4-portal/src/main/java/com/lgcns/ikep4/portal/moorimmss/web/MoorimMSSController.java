package com.lgcns.ikep4.portal.moorimmss.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AuthorizedException;
import com.lgcns.ikep4.framework.core.code.BoardCode;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.login.model.UserAccount;
import com.lgcns.ikep4.portal.login.service.LoginUserDetailsService;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.portal.main.model.PortalCode;
import com.lgcns.ikep4.portal.moorimmss.model.MssTeamTreeSpecial;
import com.lgcns.ikep4.portal.moorimmss.service.MssTeamTreeSpecialService;
import com.lgcns.ikep4.support.security.acl.annotations.AccessType;
import com.lgcns.ikep4.support.security.acl.annotations.Attribute;
import com.lgcns.ikep4.support.security.acl.annotations.IsAccess;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.security.acl.validation.AccessingResult;
import com.lgcns.ikep4.support.user.group.model.Group;
import com.lgcns.ikep4.support.user.group.service.GroupService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.model.UserSearchCondition;
import com.lgcns.ikep4.support.user.member.service.UserService;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.encrypt.EncryptUtil;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.util.model.ModelBeanUtil;

@Controller
@RequestMapping(value="/portal/moorimmss")
public class MoorimMSSController extends BaseController {

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
	
	
	public boolean isSystemAdmin(User user) {
		
		return this.aclService.isSystemAdmin("MoorimMss", user.getUserId());
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
	
	@RequestMapping(value = "/initMssMain.do")
	public ModelAndView initMssMain(HttpServletRequest request, @RequestParam(value = "whereLink", required = false) String whereLink) {

		ModelAndView mav = new ModelAndView( "/portal/moorimmss/initMssMain");
		
		User user = (User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isSystemAdmin(user);
		String portalId = (String) getRequestAttribute("ikep.portalId");

		mav.addObject("isSystemAdmin", isSystemAdmin);
		mav.addObject("Bigmenu", "initMssMain");
		mav.addObject("sapId", user.getSapId());
		
		UserAccount userAccount = loginUserDetailsService.loadUserByUsername(user.getUserId(), portalId);
		//
		//System.out.println("@@@@@@@@@@@@@@@@@@@@@@ sapId:"+user.getSapId());
		//System.out.println("@@@@@@@@@@@@@@@@@@@@@@ sapPwd:"+EncryptUtil.decryptText(userAccount.getPassword()));
		
		mav.addObject("sapPwd", EncryptUtil.decryptText(userAccount.getPassword()));

		mav.addObject("serverLinkUrl", getLinkPath(request));
		if(StringUtil.isEmpty(whereLink)){
			whereLink="zhr_es_002";
		}
		mav.addObject("whereLink", whereLink);
		return mav;
	}
	
	@RequestMapping(value = "/mssMain.do")
	public ModelAndView mssMain(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView( "/portal/moorimmss/mssMain");
		
		User user = (User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isSystemAdmin(user);
		String portalId = (String) getRequestAttribute("ikep.portalId");

		mav.addObject("isSystemAdmin", isSystemAdmin);
		mav.addObject("Bigmenu", "mssMain");
		mav.addObject("sapId", user.getSapId());
		
		UserAccount userAccount = loginUserDetailsService.loadUserByUsername(user.getUserId(), portalId);
		
		//System.out.println("@@@@@@@@@@@@@@@@@@@@@@ sapId:"+user.getSapId());
		//System.out.println("@@@@@@@@@@@@@@@@@@@@@@ sapPwd:"+EncryptUtil.decryptText(userAccount.getPassword()));
		
		mav.addObject("sapPwd", EncryptUtil.decryptText(userAccount.getPassword()));

		mav.addObject("serverLinkUrl", getLinkPath(request));
		mav.addObject("whereLink", "zhr_es_002");
		return mav;
	}
	
	@RequestMapping(value = "/personalMng.do")
	public ModelAndView pesonalMng(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView( "/portal/moorimmss/personalMng");
		
		User user = (User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isSystemAdmin(user);
		
		mav.addObject("isSystemAdmin", isSystemAdmin);
		mav.addObject("Bigmenu", "personalMng");
		mav.addObject("serverLinkUrl", getLinkPath(request));
		
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
		return mav;
	}
	
	@RequestMapping(value = "/webPersonalMng.do")
	public ModelAndView webPesonalMng(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView( "/portal/moorimmss/webPersonalMng");
		
		User user = (User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isSystemAdmin(user);
		
		mav.addObject("isSystemAdmin", isSystemAdmin);
		mav.addObject("Bigmenu", "personalMng");
		mav.addObject("serverLinkUrl", getLinkPath(request));
		
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
		return mav;
	}
	
	@RequestMapping(value = "/webLoanList.do")
	public ModelAndView webLoanList(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView( "/portal/moorimmss/webLoanList");
		
		User user = (User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isSystemAdmin(user);
		
		mav.addObject("isSystemAdmin", isSystemAdmin);
		
		String searchRootGroupId="";
		List<Map<String, Object>> list = null;
		searchRootGroupId="";//전체인원
		List<MssTeamTreeSpecial> specialList = mssTeamTreeSpecialService.getList(user.getUserId());
		if(specialList!=null && specialList.size()>0){
			list = getOrgGroupAndUser3(specialList, "", "USER");//팀뷰어 예외자
		}else{
			list = getOrgGroupAndUser(searchRootGroupId, "", "USER");//내 부서 인원만.
		}
		
		try {
		ObjectMapper objectMapper = new ObjectMapper();

		mav.addObject("deptItems", objectMapper.writeValueAsString(list));

		} catch (Exception e) {
			throw new IKEP4AjaxException("", e);
		}
		return mav;
	}
	
	@RequestMapping(value = "/teamViewMng.do")
	public ModelAndView teamViewMng(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView( "/portal/moorimmss/teamViewMng");
		
		User user = (User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isSystemAdmin(user);
		
		mav.addObject("isSystemAdmin", isSystemAdmin);

		return mav;
	}
	
	@RequestMapping(value = "/teamViewSpecialList.do")
	public ModelAndView teamViewSpecialList(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView( "/portal/moorimmss/teamViewSpecialList");
		
		User user = (User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isSystemAdmin(user);
		String portalId = (String) getRequestAttribute("ikep.portalId");

		List<MssTeamTreeSpecial> list = mssTeamTreeSpecialService.getList();
		mav.addObject("teamViewSpecialList", list);
		return mav;
	}
	
	@RequestMapping(value = "/userList.do")
	public @ResponseBody SearchResult<User> userList(UserSearchCondition searchCondition) {
		
		User sessionuser = (User) getRequestAttribute("ikep.user");
		String portalId = (String) getRequestAttribute("ikep.portalId");

		if (StringUtil.isEmpty(searchCondition.getSortType())) {
			searchCondition.setSortType("ASC");
		}

		PortalCode portalCode = new PortalCode();
		if(sessionuser.getMssAuthCode().equals("M8")){
			searchCondition.setIsAllUser("Y");
		}else if(sessionuser.getMssAuthCode().equals("M9")){
			searchCondition.setIsAllUser("Y");
			searchCondition.setMssAuthCode("M9");
		}
		searchCondition.setPagePerRecord(15);
		searchCondition.setFieldName("jobTitleName");
		searchCondition.setUserLocaleCode(sessionuser.getLocaleCode());
		searchCondition.setPortalId(portalId);
		
		SearchResult<User> searchResult;
		
		Boolean isSystemAdmin = this.isSystemAdmin(sessionuser);
		
		if(isSystemAdmin){
			searchCondition.setGroupId("");	
		}else{
			searchCondition.setGroupId(sessionuser.getGroupId());	
		}
		
		try {
			
			//searchResult = userService.list(searchCondition);
			searchResult = userService.treelist(searchCondition, sessionuser);

		} catch (Exception ex) {
			
			throw new IKEP4AjaxException("", ex);
		}
		
		return searchResult;
	}
	
	
	
	@RequestMapping(value = "/teamViewSpecialAdd.do")
	public @ResponseBody
	Map<String, Object> teamViewSpecialAdd(@RequestParam("userId") String userId,
			@RequestParam("groupId") String groupId) {

		Map<String, Object> result=new HashMap<String, Object>();

		try {			
			MssTeamTreeSpecial mssTeamTreeSpecial =new MssTeamTreeSpecial();
			mssTeamTreeSpecial.setGroupId(groupId);
			mssTeamTreeSpecial.setUserId(userId);
			
			String itemId= mssTeamTreeSpecialService.create(mssTeamTreeSpecial);

			result.put("success", "success");
		} catch (Exception ex) {
			result.put("success", "fail");
	
		}

		return result;
	}
	
	
	@RequestMapping(value = "/teamViewSpecialDelete.do")
	public @ResponseBody
	String teamViewSpecialDelete(String[] itemIds) {

		
		try {

			mssTeamTreeSpecialService.deleteMssTeamTreeSpecialList(itemIds);

		} catch (Exception ex) {
			
			throw new IKEP4AjaxException("", ex);
		}

		return "ok";
	}
	
	
	@RequestMapping(value = "/organMng.do")
	public ModelAndView organMng(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView( "/portal/moorimmss/organMng");
		
		User user = (User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isSystemAdmin(user);
		
		mav.addObject("isSystemAdmin", isSystemAdmin);
		mav.addObject("Bigmenu", "organMng");
		mav.addObject("serverLinkUrl", getLinkPath(request));
		return mav;
	}
	
	@RequestMapping(value = "/webOrganMng.do")
	public ModelAndView webOrganMng(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView( "/portal/moorimmss/webOrganMng");
		
		User user = (User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isSystemAdmin(user);
		
		mav.addObject("isSystemAdmin", isSystemAdmin);
		mav.addObject("Bigmenu", "organMng");
		mav.addObject("serverLinkUrl", getLinkPath(request));
		return mav;
	}
	
	@RequestMapping(value = "/diligencePayMng.do")
	public ModelAndView diligencePayMng(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView( "/portal/moorimmss/diligencePayMng");
		
		User user = (User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isSystemAdmin(user);
		
		mav.addObject("isSystemAdmin", isSystemAdmin);
		mav.addObject("Bigmenu", "diligencePayMng");
		mav.addObject("serverLinkUrl", getLinkPath(request));
		
		
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
		return mav;
	}
	
	@RequestMapping(value = "/webDiligencePayMng.do")
	public ModelAndView webDiligencePayMng(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView( "/portal/moorimmss/webDiligencePayMng");
		
		User user = (User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isSystemAdmin(user);
		
		mav.addObject("isSystemAdmin", isSystemAdmin);
		mav.addObject("Bigmenu", "diligencePayMng");
		mav.addObject("serverLinkUrl", getLinkPath(request));
		
		
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
		return mav;
	}
	
	@RequestMapping(value = "/manPowerMng.do")
	public ModelAndView manPowerMng(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView( "/portal/moorimmss/manPowerMng");
		
		User user = (User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isSystemAdmin(user);
		
		mav.addObject("isSystemAdmin", isSystemAdmin);
		mav.addObject("Bigmenu", "manPowerMng");
		mav.addObject("serverLinkUrl", getLinkPath(request));
		

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
		return mav;
	}
	
	@RequestMapping(value = "/webManPowerMng.do")
	public ModelAndView webManPowerMng(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView( "/portal/moorimmss/webManPowerMng");
		
		User user = (User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isSystemAdmin(user);
		
		mav.addObject("isSystemAdmin", isSystemAdmin);
		mav.addObject("Bigmenu", "manPowerMng");
		mav.addObject("serverLinkUrl", getLinkPath(request));
		

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
		return mav;
	}
	
	 
	@RequestMapping(value = "/evaluationMng.do")
	public ModelAndView evaluationMng(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView( "/portal/moorimmss/evaluationMng");
		
		User user = (User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isSystemAdmin(user);
		
		mav.addObject("isSystemAdmin", isSystemAdmin);
		mav.addObject("Bigmenu", "evaluationMng");
		mav.addObject("serverLinkUrl", getLinkPath(request));
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
		return mav;
	}
	
	@RequestMapping(value = "/webEvaluationMng.do")
	public ModelAndView webEvaluationMng(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView( "/portal/moorimmss/webEvaluationMng");
		
		User user = (User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isSystemAdmin(user);
		
		mav.addObject("isSystemAdmin", isSystemAdmin);
		mav.addObject("Bigmenu", "evaluationMng");
		mav.addObject("serverLinkUrl", getLinkPath(request));
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
		return mav;
	}
	
	@RequestMapping(value = "laborMng.do")
	public ModelAndView laborMng(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView( "/portal/moorimmss/laborMng");
		
		User user = (User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isSystemAdmin(user);
		
		mav.addObject("isSystemAdmin", isSystemAdmin);
		mav.addObject("Bigmenu", "laborMng");
		mav.addObject("serverLinkUrl", getLinkPath(request));
		return mav;
	}
	
	
	@RequestMapping(value = "/webLaborMng.do")
	public ModelAndView webLaborMng(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView( "/portal/moorimmss/webLaborMng");
		
		User user = (User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isSystemAdmin(user);
		
		mav.addObject("isSystemAdmin", isSystemAdmin);
		mav.addObject("Bigmenu", "laborMng");
		mav.addObject("serverLinkUrl", getLinkPath(request));
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

}
