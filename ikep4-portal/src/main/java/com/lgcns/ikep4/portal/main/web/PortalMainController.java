/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.main.web;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AuthorizedException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.admin.screen.model.PortalMenu;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPopup;
import com.lgcns.ikep4.portal.admin.screen.model.PortalQuickMenu;
import com.lgcns.ikep4.portal.admin.screen.model.PortalSystem;
import com.lgcns.ikep4.portal.admin.screen.service.PortalMenuService;
import com.lgcns.ikep4.portal.admin.screen.service.PortalPageLayoutService;
import com.lgcns.ikep4.portal.admin.screen.service.PortalPageService;
import com.lgcns.ikep4.portal.admin.screen.service.PortalPopupService;
import com.lgcns.ikep4.portal.admin.screen.service.PortalQuickMenuService;
import com.lgcns.ikep4.portal.admin.screen.service.PortalService;
import com.lgcns.ikep4.portal.admin.screen.service.PortalSystemService;
import com.lgcns.ikep4.portal.login.model.UserAccount;
import com.lgcns.ikep4.portal.login.service.LoginUserDetailsService;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.portal.main.model.PortalCode;
import com.lgcns.ikep4.portal.main.model.PortalMain;
import com.lgcns.ikep4.portal.main.model.PortalMymenuConfig;
import com.lgcns.ikep4.portal.main.service.PortalMainService;
import com.lgcns.ikep4.portal.main.service.PortalMymenuConfigService;
import com.lgcns.ikep4.portal.usagetracker.model.PortalLoginRank;
import com.lgcns.ikep4.portal.usagetracker.service.PortalLoginRankService;
import com.lgcns.ikep4.portal.util.BrowserCheck;
import com.lgcns.ikep4.support.abusereporting.model.ArAbuseHistory;
import com.lgcns.ikep4.support.base.constant.CommonConstant;
import com.lgcns.ikep4.support.cache.service.CacheService;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.security.acl.annotations.AccessType;
import com.lgcns.ikep4.support.security.acl.annotations.Attribute;
import com.lgcns.ikep4.support.security.acl.annotations.IsAccess;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.security.acl.validation.AccessingResult;
import com.lgcns.ikep4.support.timezone.service.TimeZoneSupportService;
import com.lgcns.ikep4.support.user.code.service.LocaleCodeService;
import com.lgcns.ikep4.support.user.group.model.Group;
import com.lgcns.ikep4.support.user.group.service.GroupService;
import com.lgcns.ikep4.support.user.member.dao.UserDao;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.model.UserSearchCondition;
import com.lgcns.ikep4.support.user.member.service.UserService;
import com.lgcns.ikep4.support.user.userTheme.model.UserTheme;
import com.lgcns.ikep4.support.user.userTheme.service.UserThemeService;
import com.lgcns.ikep4.support.userconfig.model.UserConfig;
import com.lgcns.ikep4.support.userconfig.service.UserConfigService;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.encrypt.EncryptUtil;
import com.lgcns.ikep4.util.jco.EpGetHRWFCountRFC;
import com.lgcns.ikep4.util.jco.EpGetStatementCountRFC;
import com.lgcns.ikep4.util.lang.DateUtil;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;
import com.lgcns.ikep4.support.user.code.model.JobTitle;
import com.lgcns.ikep4.support.user.code.service.JobTitleService;
import com.lgcns.ikep4.support.user.code.model.JobDuty;
import com.lgcns.ikep4.support.user.code.service.JobDutyService;
/**
 * 포탈 메인 Controller
 * 
 * @author 임종상(malboru80@naver.com)
 * @version $Id: PortalMainController.java 745 2011-02-17 06:20:10Z limjongsang
 *          $
 */
@Controller
@RequestMapping(value = "/portal/main")
public class PortalMainController extends BaseController {

	@Autowired
	private PortalService portalService;

	@Autowired
	private PortalMymenuConfigService portalMymenuConfigService;

	@Autowired
	private PortalSystemService portalSystemService;

	@Autowired
	private PortalPageService portalPageService;

	@Autowired
	private PortalPageLayoutService portalPageLayoutService;

	@Autowired
	private PortalMenuService portalMenuService;

	@Autowired
	private PortalMainService portalMainService;

	@Autowired
	private PortalQuickMenuService portalQuickMenuService;

	@Autowired
	private UserService userService;

	@Autowired
	private FileService fileService;

	@Autowired
	private UserThemeService userThemeService;

	@Autowired
	private TimeZoneSupportService timeZoneSupportService;

	@Autowired
	private JobTitleService jobTitleService;

	@Autowired
	private JobDutyService jobDutyService;	
	
	/**
	 * 사용자 Group 정보 컨트롤용 Service
	 */
	@Autowired
	private GroupService groupService;
	
	@Autowired
	private PortalPopupService portalPopupService;
	
	@Autowired
	private UserConfigService userConfigService;
	
	@Autowired
    ACLService aclService;
	
	@Autowired
	private CacheService cacheService;
	
	@Autowired
	private LoginUserDetailsService loginUserDetailsService;
	
	@Autowired
    private UserDao userDao;
	
	@Autowired
	private PortalLoginRankService portalLoginRankService;
	
	
	/*
	 * 지식광장의 경우 관계사에게 별도의 로그인아이디를 주어 로그인 후 지식광장 페이지로 redirect 시킴
	 * kms-except-login-id.properties 에 별도관리해야할 아이디와 redirectUrl을 관리한다.
	 * */
	private String[] kmsLoginIds;
	private String kmsRedirectUrl;
	private Properties kmsProperty;
	
	private String[] gateLoginIds;
	private String gateRedirectUrl;
	private Properties gateProperty;


	/**
	 * 포탈 메인
	 * 
	 * @param mainFrameUrl iframeURL
	 * @param model 모델객체
	 * @return 포워딩 정보
	 */
	@RequestMapping(value = "/portalMain.do")
	//public String portalMain(Model model, @RequestParam(value = "mainFrameUrl", required = false) String mainFrameUrl   , @RequestParam(value = "initUser", required = false) String initUser ) {
	public String portalMain(Model model, @RequestParam(value = "mainFrameUrl", required = false) String mainFrameUrl   ) {

		String portalId = (String) getRequestAttribute("ikep.portalId");
		User user = (User) getRequestAttribute("ikep.user");
		

		
		PortalMymenuConfig portalMymenuConfig = portalMymenuConfigService.readPortalMymenuConfig(user.getUserId());

		int openOption = 0;
		if (portalMymenuConfig != null) {
			openOption = portalMymenuConfig.getOpenOption();
		}

		String tempMainFrameUrl = mainFrameUrl;

		if (StringUtil.isEmpty(mainFrameUrl)) {
			String pageId = portalPageService.readPageId(portalId);
			tempMainFrameUrl = "/portal/main/body.do?pageId=" + pageId;
		}
		/*
		if (!StringUtil.isEmpty(initUser)) {
			model.addAttribute("initUser", initUser);
		}
		*/
		
		model.addAttribute("openOption", openOption);
		model.addAttribute("mainFrameUrl", tempMainFrameUrl);
		
		if(checkKmsExceptionId(user.getUserId())){
			return kmsRedirectUrl;
		}
		if(checkGateExceptionId(user.getUserId())){
			return gateRedirectUrl;
		}

		return "portal/main/portalMain";
	}
	
	@RequestMapping(value = "/kmsMain.do")
	public String kmsMain(Model model, @RequestParam(value = "mainFrameUrl", required = false) String mainFrameUrl   ) {

		String portalId = (String) getRequestAttribute("ikep.portalId");
		User user = (User) getRequestAttribute("ikep.user");
		

		
		PortalMymenuConfig portalMymenuConfig = portalMymenuConfigService.readPortalMymenuConfig(user.getUserId());

		int openOption = 0;
		if (portalMymenuConfig != null) {
			openOption = portalMymenuConfig.getOpenOption();
		}

		String tempMainFrameUrl = mainFrameUrl;

		if (StringUtil.isEmpty(mainFrameUrl)) {
			String pageId = portalPageService.readPageId(portalId);
			tempMainFrameUrl = "/collpack/kms/main/knowledgeShare.do?pageId=" + pageId;
		}
		
		model.addAttribute("openOption", openOption);
		model.addAttribute("mainFrameUrl", tempMainFrameUrl);
		
		if(checkKmsExceptionId(user.getUserId())){
			return kmsRedirectUrl;
		}
		if(checkGateExceptionId(user.getUserId())){
			return gateRedirectUrl;
		}

		return "portal/main/portalMain";
	}
	
	@RequestMapping(value = "/boardFaq.do")
	public String boardFaq(Model model, @RequestParam(value = "mainFrameUrl", required = false) String mainFrameUrl   ) {

		String portalId = (String) getRequestAttribute("ikep.portalId");
		User user = (User) getRequestAttribute("ikep.user");
		

		
		PortalMymenuConfig portalMymenuConfig = portalMymenuConfigService.readPortalMymenuConfig(user.getUserId());

		int openOption = 0;
		if (portalMymenuConfig != null) {
			openOption = portalMymenuConfig.getOpenOption();
		}

		String tempMainFrameUrl = mainFrameUrl;

		if (StringUtil.isEmpty(mainFrameUrl)) {
			String pageId = portalPageService.readPageId(portalId);
			tempMainFrameUrl = "/lightpack/board/boardCommon/boardView.do?boardRootId=0&boardId=100010083357";
		}
		
		model.addAttribute("openOption", openOption);
		model.addAttribute("mainFrameUrl", tempMainFrameUrl);
		
		if(checkKmsExceptionId(user.getUserId())){
			return kmsRedirectUrl;
		}
		if(checkGateExceptionId(user.getUserId())){
			return gateRedirectUrl;
		}

		return "portal/main/portalMain";
	}
	
	@RequestMapping(value = "/messengerKmsMain.do")
	public String messengerKmsMain(Model model, @RequestParam(value = "mainFrameUrl", required = false) String mainFrameUrl,
			@RequestParam(value = "j_username", required = true) String username,
			@RequestParam(value = "j_password", required = true) String password, HttpServletRequest request) {

		Portal portal = null;


		// 사용자가 접속한 도메인
		String domain = request.getServerName();

		// 멀티 포탈 구분 플래그(ContextPath : C, domain : D)
		if (!StringUtil.isEmpty(domain)) {
			// 도메인
			portal = portalService.readPortalDomain(domain);
		} else {
			portal = portalService.readPortalDefault();
		}

		

		UserAccount userAccount = loginUserDetailsService.loadUserByUsername(username, portal.getPortalId());


		// User가 존재하지 않을 경우
		if (userAccount == null) {
			return "redirect:/loginForm.do?error=1";
		}
		// authentication check
		if (!password.equals(userAccount.getPassword())) {
			// password가 올바르지 않을 경우, loginForm으로 redirection 한다.
			return "redirect:/loginForm.do?error=1";
		}
		// 로그인이 성공한후의 작업 (세션정보 저장)을 수행한다.
		
		
		

		User user =   userService.read(username);
		
		String workType="";
		if (password.equals( EncryptUtil.encryptText("a1234567"))) {//초기 패스워드이면서 

			if( (!StringUtil.isEmpty(user.getSapId())) && (!"moorim12".equals(user.getSapId().toLowerCase()))){//sap 아이디가 있으면서 공용아이디(moorim12)가 아니면
				if( (!StringUtil.isEmpty(user.getBwId())) && (!user.getBwId().toLowerCase().contains("epbwif"))){//bw 아이디가 있으면서 공용아이디가 아니면
					workType="initall";//sap,bw변경후 ep변경
				}else{
					workType="initsap";//sap변경후 ep변경
				}
			}else{
				if( (!StringUtil.isEmpty(user.getBwId())) && (!user.getBwId().toLowerCase().contains("epbwif"))){//bw 아이디가 있으면서 공용아이디가 아니면
					workType="initbw";//bw변경후 ep변경
				}else{
					workType="init";// ep변경
				}
			}
			return "redirect:/portal/main/newSapPassword.do?workType="+workType+"&userId="+username;
		}else{

			if(!StringUtil.isEmpty(user.getPasswordChangeYn())){
				//패스워드 변경 예외처리자들은 변경주기 확인 안함
			}else{
				String updatePWDateStr = userAccount.getUpdatePasswordDate();
				if (StringUtil.isEmpty(updatePWDateStr)) {
					updatePWDateStr = DateUtil.getToday("yyyyMMdd");
				}
				
				if(DateUtil.getTwoDatesDifference(updatePWDateStr , DateUtil.getToday("yyyyMMdd"))>180){
					if( (!StringUtil.isEmpty(user.getSapId())) && (!"moorim12".equals(user.getSapId().toLowerCase()))){//sap 아이디가 있으면서 공용아이디(moorim12)가 아니면
						if( (!StringUtil.isEmpty(user.getBwId())) && (!user.getBwId().toLowerCase().contains("epbwif"))){//bw 아이디가 있으면서 공용아이디가 아니면
							workType="oldall";//sap,bw변경후 ep변경
						}else{
							workType="oldsap";//sap변경후 ep변경
						}
					
					}else{
						if( (!StringUtil.isEmpty(user.getBwId())) &&  (!user.getBwId().toLowerCase().contains("epbwif"))){//bw 아이디가 있으면서 공용아이디가 아니면
							workType="oldbw";//bw변경후 ep변경
						}else{
							workType="old";// ep변경
						}
					}
					return "redirect:/portal/main/newSapPassword.do?workType="+workType+"&userId="+username;
				}
			}

		}
		
		HttpSession session = request.getSession();
		
		
		setRequestAttribute("ikep.portal", portal);
		setRequestAttribute("ikep.portalId", portal.getPortalId());
		setRequestAttribute("ikep.defaultLocaleCode", portal.getDefaultLocaleCode());
		
		
		authenticationSuccess(request, username);
		

		
		PortalMymenuConfig portalMymenuConfig = portalMymenuConfigService.readPortalMymenuConfig(user.getUserId());

		int openOption = 0;
		if (portalMymenuConfig != null) {
			openOption = portalMymenuConfig.getOpenOption();
		}

		String tempMainFrameUrl = mainFrameUrl;

		if (StringUtil.isEmpty(mainFrameUrl)) {
			String pageId = portalPageService.readPageId(portal.getPortalId());
			tempMainFrameUrl = "/collpack/kms/main/knowledgeShare.do?pageId=" + pageId;
		}
		
		model.addAttribute("openOption", openOption);
		model.addAttribute("mainFrameUrl", tempMainFrameUrl);
		
		if(checkKmsExceptionId(user.getUserId())){
			return kmsRedirectUrl;
		}
		if(checkGateExceptionId(user.getUserId())){
			return gateRedirectUrl;
		}

		return "portal/main/portalMain";
	}
	
	@RequestMapping(value = "/messengerBoardFaq.do")
	public String messengerBoardFaq(Model model, @RequestParam(value = "mainFrameUrl", required = false) String mainFrameUrl,
			@RequestParam(value = "j_username", required = true) String username,
			@RequestParam(value = "j_password", required = true) String password, HttpServletRequest request) {

		Portal portal = null;


		// 사용자가 접속한 도메인
		String domain = request.getServerName();

		// 멀티 포탈 구분 플래그(ContextPath : C, domain : D)
		if (!StringUtil.isEmpty(domain)) {
			// 도메인
			portal = portalService.readPortalDomain(domain);
		} else {
			portal = portalService.readPortalDefault();
		}

		

		UserAccount userAccount = loginUserDetailsService.loadUserByUsername(username, portal.getPortalId());


		// User가 존재하지 않을 경우
		if (userAccount == null) {
			return "redirect:/loginForm.do?error=1";
		}
		// authentication check
		if (!password.equals(userAccount.getPassword())) {
			// password가 올바르지 않을 경우, loginForm으로 redirection 한다.
			return "redirect:/loginForm.do?error=1";
		}
		// 로그인이 성공한후의 작업 (세션정보 저장)을 수행한다.
		
		
		

		User user =   userService.read(username);
		
		String workType="";
		if (password.equals( EncryptUtil.encryptText("a1234567"))) {//초기 패스워드이면서 

			if( (!StringUtil.isEmpty(user.getSapId())) && (!"moorim12".equals(user.getSapId().toLowerCase()))){//sap 아이디가 있으면서 공용아이디(moorim12)가 아니면
				if( (!StringUtil.isEmpty(user.getBwId())) && (!user.getBwId().toLowerCase().contains("epbwif"))){//bw 아이디가 있으면서 공용아이디가 아니면
					workType="initall";//sap,bw변경후 ep변경
				}else{
					workType="initsap";//sap변경후 ep변경
				}
			}else{
				if( (!StringUtil.isEmpty(user.getBwId())) && (!user.getBwId().toLowerCase().contains("epbwif"))){//bw 아이디가 있으면서 공용아이디가 아니면
					workType="initbw";//bw변경후 ep변경
				}else{
					workType="init";// ep변경
				}
			}
			return "redirect:/portal/main/newSapPassword.do?workType="+workType+"&userId="+username;
		}else{

			if(!StringUtil.isEmpty(user.getPasswordChangeYn())){
				//패스워드 변경 예외처리자들은 변경주기 확인 안함
			}else{
				String updatePWDateStr = userAccount.getUpdatePasswordDate();
				if (StringUtil.isEmpty(updatePWDateStr)) {
					updatePWDateStr = DateUtil.getToday("yyyyMMdd");
				}
				
				if(DateUtil.getTwoDatesDifference(updatePWDateStr , DateUtil.getToday("yyyyMMdd"))>180){
					if( (!StringUtil.isEmpty(user.getSapId())) && (!"moorim12".equals(user.getSapId().toLowerCase()))){//sap 아이디가 있으면서 공용아이디(moorim12)가 아니면
						if( (!StringUtil.isEmpty(user.getBwId())) && (!user.getBwId().toLowerCase().contains("epbwif"))){//bw 아이디가 있으면서 공용아이디가 아니면
							workType="oldall";//sap,bw변경후 ep변경
						}else{
							workType="oldsap";//sap변경후 ep변경
						}
					
					}else{
						if( (!StringUtil.isEmpty(user.getBwId())) &&  (!user.getBwId().toLowerCase().contains("epbwif"))){//bw 아이디가 있으면서 공용아이디가 아니면
							workType="oldbw";//bw변경후 ep변경
						}else{
							workType="old";// ep변경
						}
					}
					return "redirect:/portal/main/newSapPassword.do?workType="+workType+"&userId="+username;
				}
			}

		}
		
		HttpSession session = request.getSession();
		
		
		setRequestAttribute("ikep.portal", portal);
		setRequestAttribute("ikep.portalId", portal.getPortalId());
		setRequestAttribute("ikep.defaultLocaleCode", portal.getDefaultLocaleCode());
		
		
		authenticationSuccess(request, username);
		

		
		PortalMymenuConfig portalMymenuConfig = portalMymenuConfigService.readPortalMymenuConfig(user.getUserId());

		int openOption = 0;
		if (portalMymenuConfig != null) {
			openOption = portalMymenuConfig.getOpenOption();
		}

		String tempMainFrameUrl = mainFrameUrl;

		if (StringUtil.isEmpty(mainFrameUrl)) {
			String pageId = portalPageService.readPageId(portal.getPortalId());
			tempMainFrameUrl = "/lightpack/board/boardCommon/boardView.do?boardRootId=0&boardId=100010083357";
		}
		
		model.addAttribute("openOption", openOption);
		model.addAttribute("mainFrameUrl", tempMainFrameUrl);
		
		if(checkKmsExceptionId(user.getUserId())){
			return kmsRedirectUrl;
		}
		if(checkGateExceptionId(user.getUserId())){
			return gateRedirectUrl;
		}

		return "portal/main/portalMain";
	}
	
	@RequestMapping(value = "/messengerKmsMainOld.do")
	public String messengerKmsMainOld(Model model, @RequestParam(value = "mainFrameUrl", required = false) String mainFrameUrl,
			@RequestParam(value = "j_username", required = true) String username,
			@RequestParam(value = "j_password", required = true) String password, HttpServletRequest request) {

		Portal portal = null;


		// 사용자가 접속한 도메인
		String domain = request.getServerName();

		// 멀티 포탈 구분 플래그(ContextPath : C, domain : D)
		if (!StringUtil.isEmpty(domain)) {
			// 도메인
			portal = portalService.readPortalDomain(domain);
		} else {
			portal = portalService.readPortalDefault();
		}

		

		UserAccount userAccount = loginUserDetailsService.loadUserByUsername(username, portal.getPortalId());


		// User가 존재하지 않을 경우
		if (userAccount == null) {
			return "redirect:/loginForm.do?error=1";
		}
		// authentication check
		if (!password.equals(userAccount.getPassword())) {
			// password가 올바르지 않을 경우, loginForm으로 redirection 한다.
			return "redirect:/loginForm.do?error=1";
		}
		// 로그인이 성공한후의 작업 (세션정보 저장)을 수행한다.
		
		
		

		User user =   userService.read(username);
		
		String workType="";
		if (password.equals( EncryptUtil.encryptText("a1234567"))) {//초기 패스워드이면서 

			if( (!StringUtil.isEmpty(user.getSapId())) && (!"moorim12".equals(user.getSapId().toLowerCase()))){//sap 아이디가 있으면서 공용아이디(moorim12)가 아니면
				if( (!StringUtil.isEmpty(user.getBwId())) && (!user.getBwId().toLowerCase().contains("epbwif"))){//bw 아이디가 있으면서 공용아이디가 아니면
					workType="initall";//sap,bw변경후 ep변경
				}else{
					workType="initsap";//sap변경후 ep변경
				}
			}else{
				if( (!StringUtil.isEmpty(user.getBwId())) && (!user.getBwId().toLowerCase().contains("epbwif"))){//bw 아이디가 있으면서 공용아이디가 아니면
					workType="initbw";//bw변경후 ep변경
				}else{
					workType="init";// ep변경
				}
			}
			return "redirect:/portal/main/newSapPassword.do?workType="+workType+"&userId="+username;
		}else{

			if(!StringUtil.isEmpty(user.getPasswordChangeYn())){
				//패스워드 변경 예외처리자들은 변경주기 확인 안함
			}else{
				String updatePWDateStr = userAccount.getUpdatePasswordDate();
				if (StringUtil.isEmpty(updatePWDateStr)) {
					updatePWDateStr = DateUtil.getToday("yyyyMMdd");
				}
				
				if(DateUtil.getTwoDatesDifference(updatePWDateStr , DateUtil.getToday("yyyyMMdd"))>180){
					if( (!StringUtil.isEmpty(user.getSapId())) && (!"moorim12".equals(user.getSapId().toLowerCase()))){//sap 아이디가 있으면서 공용아이디(moorim12)가 아니면
						if( (!StringUtil.isEmpty(user.getBwId())) && (!user.getBwId().toLowerCase().contains("epbwif"))){//bw 아이디가 있으면서 공용아이디가 아니면
							workType="oldall";//sap,bw변경후 ep변경
						}else{
							workType="oldsap";//sap변경후 ep변경
						}
					
					}else{
						if( (!StringUtil.isEmpty(user.getBwId())) &&  (!user.getBwId().toLowerCase().contains("epbwif"))){//bw 아이디가 있으면서 공용아이디가 아니면
							workType="oldbw";//bw변경후 ep변경
						}else{
							workType="old";// ep변경
						}
					}
					return "redirect:/portal/main/newSapPassword.do?workType="+workType+"&userId="+username;
				}
			}

		}
		
		HttpSession session = request.getSession();
		
		
		setRequestAttribute("ikep.portal", portal);
		setRequestAttribute("ikep.portalId", portal.getPortalId());
		setRequestAttribute("ikep.defaultLocaleCode", portal.getDefaultLocaleCode());
		
		
		authenticationSuccess(request, username);
		

		
		PortalMymenuConfig portalMymenuConfig = portalMymenuConfigService.readPortalMymenuConfig(user.getUserId());

		int openOption = 0;
		if (portalMymenuConfig != null) {
			openOption = portalMymenuConfig.getOpenOption();
		}

		String tempMainFrameUrl = mainFrameUrl;

		if (StringUtil.isEmpty(mainFrameUrl)) {
			String pageId = portalPageService.readPageId(portal.getPortalId());
			tempMainFrameUrl = "/collpack/kms/main/Kms.do?pageId=" + pageId;
		}
		
		model.addAttribute("openOption", openOption);
		model.addAttribute("mainFrameUrl", tempMainFrameUrl);
		
		if(checkKmsExceptionId(user.getUserId())){
			return kmsRedirectUrl;
		}
		if(checkGateExceptionId(user.getUserId())){
			return gateRedirectUrl;
		}

		return "portal/main/portalMain";
	}
	
	private void authenticationSuccess(HttpServletRequest request, String username) {
		HttpSession httpsession = request.getSession(true);
		String portalId = (String) httpsession.getAttribute("ikep.portalId");

		// 사용자 정보 조회
		User user = userService.read(username);

		// 사용자 테마 정보 조회
		UserTheme userThemeCheck = userThemeService.readUserTheme(user.getUserId());
		UserTheme userTheme = null;

		if (userThemeCheck == null) {
			// 사용자 테마 정보 없으면 기본 테마정보 조회
			userTheme = userThemeService.readDefaultTheme(portalId);
		} else {
			userTheme = userThemeService.readTheme(userThemeCheck.getThemeId(), portalId);
		}

		user.setUserTheme(userTheme);
		
		//세션 타임아웃 설정
		long loginTime=System.currentTimeMillis();
		httpsession.setAttribute("ikep.loginTime", loginTime);

		httpsession.setAttribute("ikep.user", user);
		httpsession.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,
				new Locale(user.getLocaleCode()));

		BrowserCheck browserCheck = new BrowserCheck();

		String userAgent = request.getHeader("user-agent");
		String browser = browserCheck.getInternetBrowser(userAgent.toLowerCase());

		// 로그인 통계 로그 저장
		PortalLoginRank portalLoginRank = new PortalLoginRank();
		portalLoginRank.setUserId(user.getUserId());
		portalLoginRank.setPortalId(portalId);
		portalLoginRank.setIpAddress(request.getRemoteAddr());
		portalLoginRank.setUserAgent(userAgent);
		portalLoginRank.setBrowser(browser);

		portalLoginRankService.createLoginLog(portalLoginRank);
	}
	
	@RequestMapping(value = "/kmsCreateView.do")
	public String kmsCreateView(Model model, @RequestParam(value = "mainFrameUrl", required = false) String mainFrameUrl   ) {

		String portalId = (String) getRequestAttribute("ikep.portalId");
		User user = (User) getRequestAttribute("ikep.user");
		

		
		PortalMymenuConfig portalMymenuConfig = portalMymenuConfigService.readPortalMymenuConfig(user.getUserId());

		int openOption = 0;
		if (portalMymenuConfig != null) {
			openOption = portalMymenuConfig.getOpenOption();
		}

		String tempMainFrameUrl = mainFrameUrl;

		if (StringUtil.isEmpty(mainFrameUrl)) {
			String pageId = portalPageService.readPageId(portalId);
			tempMainFrameUrl = "/collpack/kms/main/Kms.do?pageName=create&pageId=" + pageId;
		}
		
		model.addAttribute("openOption", openOption);
		model.addAttribute("mainFrameUrl", tempMainFrameUrl);
		
		if(checkKmsExceptionId(user.getUserId())){
			return kmsRedirectUrl;
		}
		if(checkGateExceptionId(user.getUserId())){
			return gateRedirectUrl;
		}

		return "portal/main/portalMain";
	}
	
	
	@RequestMapping(value = "/credu.do")
	public @ResponseBody
	String credu() {

		return "success";
	}
	
	@RequestMapping(value = "/mpsOpen.do")
	public @ResponseBody
	String mpsOpen(@RequestParam(value = "mpsId", required = false) String mpsId,
			@RequestParam(value = "mpsNo", required = false) String mpsNo) {
		String result="";
		String encryptMpsId = EncryptUtil.encryptText(mpsId);
		String encryptMpsNo = EncryptUtil.encryptText(mpsNo);
		result=encryptMpsId+"-_-"+encryptMpsNo;
		return result;
	}
	
	@RequestMapping(value = "/recOpen.do")
	public @ResponseBody
	String recOpen(@RequestParam(value = "recId", required = false) String recId,
			@RequestParam(value = "recNo", required = false) String recNo) {
		String result="";
		String encryptRecId = EncryptUtil.encryptText(recId);
		String encryptRecNo = EncryptUtil.encryptText(recNo);
		result=encryptRecId+"-_-"+encryptRecNo;
		return result;
	}
	
	@RequestMapping(value = "/mplOpen.do")
	public @ResponseBody
	String mplOpen(@RequestParam(value = "mplId", required = false) String mplId,
			@RequestParam(value = "mplNo", required = false) String mplNo) {
		String result="";
		String encryptMplId = EncryptUtil.encryptText(mplId);
		String encryptMplNo = EncryptUtil.encryptText(mplNo);
		result=encryptMplId+"-_-"+encryptMplNo;
		return result;
	}
	
	@RequestMapping(value = "/mlcOpen.do")
	public @ResponseBody
	String mlcOpen(@RequestParam(value = "mpsId", required = false) String mpsId,
			@RequestParam(value = "mpsNo", required = false) String mpsNo) {
		String result="";
		String encryptMpsId = EncryptUtil.encryptText(mpsId);
		String encryptMpsNo = EncryptUtil.encryptText(mpsNo);
		result=encryptMpsId+"-_-"+encryptMpsNo;
		return result;
	}

	@RequestMapping(value = "/salesOpen.do")
	public @ResponseBody
	String salesOpen(@RequestParam(value = "mpsId", required = false) String mpsId) {
		String result="";
		String encryptSalesId = EncryptUtil.encryptText("ssoUserId=" + mpsId);
		result=encryptSalesId;
		return result;
	}
	
	@RequestMapping(value = "/portalSchedule.do")
	//public String portalMain(Model model, @RequestParam(value = "mainFrameUrl", required = false) String mainFrameUrl   , @RequestParam(value = "initUser", required = false) String initUser ) {
	public String portalSchedule(Model model, @RequestParam(value = "mainFrameUrl", required = false) String mainFrameUrl   ) {

		String portalId = (String) getRequestAttribute("ikep.portalId");
		User user = (User) getRequestAttribute("ikep.user");
		

		
		PortalMymenuConfig portalMymenuConfig = portalMymenuConfigService.readPortalMymenuConfig(user.getUserId());

		int openOption = 0;
		if (portalMymenuConfig != null) {
			openOption = portalMymenuConfig.getOpenOption();
		}

		String tempMainFrameUrl = mainFrameUrl;

		if (StringUtil.isEmpty(mainFrameUrl)) {
			String pageId = portalPageService.readPageId(portalId);
			tempMainFrameUrl = "/lightpack/planner/calendar/init.do";
		}
		/*
		if (!StringUtil.isEmpty(initUser)) {
			model.addAttribute("initUser", initUser);
		}
		*/
		
		model.addAttribute("openOption", openOption);
		model.addAttribute("mainFrameUrl", tempMainFrameUrl);
		
		if(checkKmsExceptionId(user.getUserId())){
			return kmsRedirectUrl;
		}
		if(checkGateExceptionId(user.getUserId())){
			return gateRedirectUrl;
		}

		return "portal/main/portalMain";
	}

	/**
	 * 메인 헤더
	 * 
	 * @param accessResult 권한체크 결과
	 * @return 포워딩 정보
	 */
	@RequestMapping(value = "/header.do")
	public ModelAndView head(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult) {

		ModelAndView mav = new ModelAndView("/portal/main/header");

		User user = (User) getRequestAttribute("ikep.user");
		Portal portal = (Portal) getRequestAttribute("ikep.portal");

		//메뉴 목록 캐시에서 조회
		List<PortalMenu> portalMenuList = (List<PortalMenu>) cacheService.cacheCheck("menu");
		
		if(portalMenuList == null) {
			Map<String, String> param = new HashMap<String, String>();
			param.put("systemName", "Portal");
			param.put("portalId", portal.getPortalId());
			param.put("operationName", "READ");
			param.put("className", "Portal-Menu");
			param.put("userId", user.getUserId());
			param.put("fieldName", "menuName");
			param.put("localeCode", user.getLocaleCode());

			portalMenuList = portalMenuService.menuList(param);
			
			// 캐시에 저장
			cacheService.addCacheElement("menu", portalMenuList);
		}
		
		Map<String, String> quickMenuParam = new HashMap<String, String>();
		quickMenuParam.put("portalId", portal.getPortalId());
		quickMenuParam.put("operationName", "READ");
		quickMenuParam.put("className", "Portal-QuickMenu");
		quickMenuParam.put("userId", user.getUserId());
		quickMenuParam.put("fieldName", "menuName");
		quickMenuParam.put("localeCode", user.getLocaleCode());

		List<PortalQuickMenu> portalQuickMenuList = portalQuickMenuService.quickMenuListByUserId(quickMenuParam);

		// 로고 이미지 체크 없으면 디폴트 이미지 보여준다.
		String logoImageYn = "N";
		String logoImageId = portalService.readLogoImageId(portal.getPortalId());

		if (!StringUtil.isEmpty(logoImageId)) {
			FileData fileData = fileService.read(logoImageId);

			if (fileData != null) {
				Properties prop = PropertyLoader.loadProperties("/configuration/fileupload-conf.properties");
				String uploadRoot = prop.getProperty("ikep4.support.fileupload.upload_root_image");

				File file = new File(uploadRoot + fileData.getFilePath(), fileData.getFileName());

				if (file.exists()) {
					logoImageYn = "Y";
				}
			}
		}
		
		// 팝업 목록
		List<PortalPopup> popupList = portalPopupService.listPopupUse(portal.getPortalId(), user.getUserId());

		Date today = new Date();

		Calendar cal = Calendar.getInstance();
		cal.setTime(timeZoneSupportService.convertTimeZone());

		String todayM = cal.getTimeInMillis() + "";

		mav.addObject("logoImageId", logoImageId);
		mav.addObject("logoImageYn", logoImageYn);
		mav.addObject("today", today);
		mav.addObject("todayM", todayM);
		mav.addObject("portalMenuList", portalMenuList);
		mav.addObject("portalQuickMenuList", portalQuickMenuList);
		mav.addObject("isAdmin", accessResult.isAccess());
		mav.addObject("popupList", popupList);

		return mav;

	}
	
	
	@RequestMapping(value = "/getMySpaceNew.do")
	public ModelAndView getMySpaceNew() {
		ModelAndView mav = new ModelAndView("portal/main/mySpaceNew");

		User user = (User) getRequestAttribute("ikep.user");
		Portal portal = (Portal) getRequestAttribute("ikep.portal");

		
		Map<String, String> quickMenuParam = new HashMap<String, String>();
		quickMenuParam.put("portalId", portal.getPortalId());
		quickMenuParam.put("operationName", "READ");
		quickMenuParam.put("className", "Portal-QuickMenu");
		quickMenuParam.put("userId", user.getUserId());
		quickMenuParam.put("fieldName", "menuName");
		quickMenuParam.put("localeCode", user.getLocaleCode());

		List<PortalQuickMenu> portalQuickMenuList = portalQuickMenuService.quickMenuListByUserId(quickMenuParam);

		mav.addObject("portalQuickMenuList", portalQuickMenuList);

		return mav;

	}

	/**
	 * 포탈 메인 content(포틀릿 영역)
	 * 
	 * @param accessResult 권한체크 결과
	 * @param pageId 페이지 ID
	 * @return 포워딩 정보
	 */
	@RequestMapping(value = "/body.do")
	public ModelAndView body(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal-Page", operationName = { "READ" }, resourceName = "pageId")) @RequestParam String pageId,
			AccessingResult accessResult) {

		if (!accessResult.isAccess()) { // true : 승인, false : 비승인
			throw new IKEP4AuthorizedException();
		}

		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");

		ModelAndView mav = new ModelAndView("portal/main/body");

		String defPageId = portalPageService.readPageId(portal.getPortalId());

		PortalMain portalMain = portalMainService.readPortletMain(user.getUserId(), user.getLocaleCode(), pageId, user);
		
		UserConfig userConfig = userConfigService.readUserConfigByUserIdAndModuleId(user.getUserId(), CommonConstant.PORTAL_MAIN_LAYOUT, portal.getPortalId());

		mav.addObject("defPageId", defPageId);
		mav.addObject("portalMain", portalMain);
		mav.addObject("portalMainLayout", userConfig != null ? userConfig.getLayout() : "");
		mav.addObject("isAdmin", aclService.isSystemAdmin("Portal", user.getUserId()));
		
		return mav;
	}

	/**
	 * 메인 페이지
	 * 
	 * @param pageId 페이지 ID
	 * @return 포워딩 정보
	 */
	@RequestMapping(value = "/removePortletReset.do")
	public String removePortletReset(@RequestParam String pageId) {

		User user = (User) getRequestAttribute("ikep.user");

		portalPageLayoutService.removePortletReset(pageId, user.getUserId());
		
		// 포탈 사용자 포틀릿 목록 캐시 Element 삭제
		cacheService.removeCacheElement("userPortlet");

		return "redirect:/portal/main/body.do?pageId=" + pageId;
	}
	
	/**
	 * 포틀릿 레이아웃 설정
	 * @param pageId
	 * @param layoutId
	 * @return
	 */
	@RequestMapping(value = "/updateUserPageLayout.do")
	public String updateUserPageLayout(@RequestParam String pageId, @RequestParam String layoutId) {

		User user = (User) getRequestAttribute("ikep.user");

		portalPageLayoutService.updateUserPageLayout(pageId, layoutId, user);
		
		// 포탈 사용자 포틀릿 목록 캐시 Element 삭제
		cacheService.removeCacheElement("userPortlet");
		
		return "redirect:/portal/main/body.do?pageId=" + pageId;
	}

	/**
	 * 유저 테마 업데이트
	 * 
	 * @param pageId 페이지 ID
	 * @param themeId 테마 ID
	 * @return 포워딩 정보
	 */
	@RequestMapping(value = "/updateUserTheme.do")
	public String updateUserTheme(@RequestParam String pageId, @RequestParam String themeId) {

		User user = (User) getRequestAttribute("ikep.user");
		String portalId = (String) getRequestAttribute("ikep.portalId");

		// 유저 테마 조회
		UserTheme userThemeCheck = userThemeService.readUserTheme(user.getUserId());

		UserTheme updateUserTheme = new UserTheme();
		updateUserTheme.setUserId(user.getUserId());
		updateUserTheme.setThemeId(themeId);
		updateUserTheme.setRegisterId(user.getUserId());
		updateUserTheme.setRegisterName(user.getUserName());
		updateUserTheme.setUpdaterId(user.getUserId());
		updateUserTheme.setUpdaterName(user.getUserName());

		// 유저 테마 체크
		if (userThemeCheck == null) {
			// 유저 테마 없으면 인서트
			userThemeService.createUserTheme(updateUserTheme);
		} else {
			// 유저 테마 있으면 업데이트
			userThemeService.updateUserTheme(updateUserTheme);
		}

		// 업데이트 한 테마 정보 조회
		UserTheme userTheme = userThemeService.readTheme(updateUserTheme.getThemeId(), portalId);

		user.setUserTheme(userTheme);

		setRequestAttribute("ikep.user", user);

		return "redirect:/portal/main/portalMain.do?pageId=" + pageId;
	}

	// 시스템 링크
	@RequestMapping(value = "/portalSystemLink.do", method = RequestMethod.POST)
	public ModelAndView portalSystemLink(SessionStatus status, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("portal/main/portalSystemLink");

		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");

		Map<String, String> param = new HashMap<String, String>();
		param.put("portalId", portal.getPortalId());
		param.put("operationName", "READ");
		param.put("className", "Portal-System");
		param.put("userId", user.getUserId());
		param.put("fieldName", "systemName");
		param.put("localeCode", user.getLocaleCode());

		List<PortalSystem> portalSystemLinkList = portalSystemService.portalSystemLinkList(param);

		mav.addObject("portalSystemLinkList", portalSystemLinkList);

		status.setComplete();

		return mav;
	}

	// quickmenu setting
	@RequestMapping("/quickmenuSetting.do")
	public String quickmenuSetting(SessionStatus status, HttpServletRequest request, Model model)
			throws JsonGenerationException, JsonMappingException, IOException {

		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");

		Map<String, String> quickMenuParam = new HashMap<String, String>();
		quickMenuParam.put("portalId", portal.getPortalId());
		quickMenuParam.put("operationName", "READ");
		quickMenuParam.put("className", "Portal-QuickMenu");
		quickMenuParam.put("userId", user.getUserId());
		quickMenuParam.put("fieldName", "menuName");
		quickMenuParam.put("localeCode", user.getLocaleCode());

		List<PortalQuickMenu> userQuickMenuList = portalQuickMenuService.quickMenuListByUserId(quickMenuParam);
		List<PortalQuickMenu> portalQuickMenuList = portalQuickMenuService.quickMenuList(quickMenuParam);

		List<Map<String, Object>> quickMenuList = new ArrayList<Map<String, Object>>();

		for (PortalQuickMenu portalQuickMenu : userQuickMenuList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", portalQuickMenu.getQuickMenuId());
			map.put("icon", portalQuickMenu.getIconId());
			map.put("desc", portalQuickMenu.getQuickMenuName());

			quickMenuList.add(map);
		}

		ObjectMapper mapper = new ObjectMapper();
		String result = mapper.writeValueAsString(quickMenuList);

		model.addAttribute("quickMenuList", result);
		model.addAttribute("portalQuickMenuList", portalQuickMenuList);

		return "portal/main/quickmenuSetting";
	}

	@RequestMapping(value = "/quickMenuSetSave.do", method = RequestMethod.POST)
	public @ResponseBody
	String quickMenuSetSave(@RequestParam String userQuickMenu) {

		User user = (User) getRequestAttribute("ikep.user");

		JSONArray jsonArray = JSONArray.fromObject(JSONSerializer.toJSON(userQuickMenu));

		String[] ids = new String[jsonArray.size()];

		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject obj = (JSONObject) jsonArray.get(i);

			ids[i] = (String) obj.get("id");
		}

		portalQuickMenuService.setUserQuickMenu(ids, user.getUserId(), user.getUserName());

		return "";
	}

	/**
	 * 사용자 리스트 메인
	 * 
	 * @param headerSearchWord 검색어
	 * @param headerSearchColumn 검색 컬럼
	 * @param rightFrameUrl iframe URL
	 * @param model 모델 객체
	 * @return 포워딩 정보
	 */
	@RequestMapping(value = "/listUserMain.do")
	public String listUserMain(@RequestParam(value = "headerSearchWord", required = false) String headerSearchWord,
			@RequestParam(value = "headerSearchColumn", required = false) String headerSearchColumn,
			@RequestParam(value = "rightFrameUrl", required = false) String rightFrameUrl, Model model) {

		try {
			ObjectMapper objectMapper = new ObjectMapper();

			model.addAttribute("headerSearchWord", headerSearchWord);
			model.addAttribute("headerSearchColumn", headerSearchColumn);
			model.addAttribute("rightFrameUrl", rightFrameUrl);
			
			User user = (User) getRequestAttribute("ikep.user");

			List<Map<String, Object>> list = getOrgGroupAndUser("", "", "USER");
			List<Map<String, Object>> list1 = getJobTitleGroupAndUser("", user.getUserId());
			List<Map<String, Object>> list2 = getJobDutyGroupAndUser("", user.getUserId());

			model.addAttribute("deptItems", objectMapper.writeValueAsString(list));
			model.addAttribute("jobTitleItems", objectMapper.writeValueAsString(list1));
			model.addAttribute("jobDutyItems", objectMapper.writeValueAsString(list2));

		} catch (Exception e) {
			throw new IKEP4AjaxException("", e);
		}
		return "portal/main/listUserMain";
	}
	
	@RequestMapping(value = "/listUserAgent.do")
	public ModelAndView listUserAgent() {

		ModelAndView mav = new ModelAndView("/portal/main/listUserAgent");
		List<User> userList = userService.selectAgentUserList("AG1");
		//SearchResult<User> searchResult = userService.selectAgentUserList("AG1");
		/*SearchResult<User> searchResult = null;


			List<User> list = userService.selectAgentUserList("AG1");

			List<User> returnList = new ArrayList<User>();
			for (User user : list) {
				returnList.add(user);
			}

			searchResult = new SearchResult<User>(returnList, null);
		mav.addObject("searchResult", searchResult);*/
		
		mav.addObject("userList", userList);
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
		for (Group group : groupList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("type", "group");
			if ("ko".equals(sessionuser.getLocaleCode())) {
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
	 * 사용자 리스트
	 * 
	 * @param searchCondition 검색조건
	 * @param model 모델 객체
	 * @return 포워딩 정보
	 */
	@RequestMapping(value = "/listUser.do")
	public String listUser(UserSearchCondition searchCondition, Model model) {

		User sessionuser = (User) getRequestAttribute("ikep.user");
		String portalId = (String) getRequestAttribute("ikep.portalId");

		if (StringUtil.isEmpty(searchCondition.getSortType())) {
			searchCondition.setSortType("ASC");
		}

		PortalCode portalCode = new PortalCode();

		searchCondition.setFieldName("jobTitleName");
		searchCondition.setUserLocaleCode(sessionuser.getLocaleCode());
		searchCondition.setPortalId(portalId);
		
		String searchDutyCode = searchCondition.getJobDutyCode();
		
		if(!StringUtil.isEmpty(searchDutyCode) && "10".equals(searchDutyCode))
		{
			searchCondition.setJobDutyCode("11");	
		}

		SearchResult<User> searchResult = userService.list(searchCondition);
				
		String searchUserId = ""; 
		if(searchResult.getEntity() != null && searchResult.getEntity().size() > 0) { 
			List<User> userList = searchResult.getEntity(); 
			searchUserId = userList.get(0).getUserId();
			
			//2012-12-07 최재영
			//회장님을 별도로 보여드리기 위한 노력...
			if(!StringUtil.isEmpty(searchDutyCode) && ("10".equals(searchDutyCode) || "11".equals(searchDutyCode) )){
				
				List<User> filteredList = new ArrayList<User>();
				
				for (User user : userList) {
				
					if("10".equals(searchDutyCode) && "1".equals(user.getJobTitleCode()))
					{	
						filteredList.add(user);
					}
					
					if("11".equals(searchDutyCode) && "2".equals(user.getJobTitleCode()))
					{						
						filteredList.add(user);
					}	
				}	
				searchCondition.terminateSearchCondition(filteredList.size());
				searchResult = new SearchResult<User>(filteredList, searchCondition);
			}
		}
		
		model.addAttribute("searchUserId", searchUserId);
		model.addAttribute("searchResult", searchResult);
		model.addAttribute("searchCondition", searchResult.getSearchCondition());
		model.addAttribute("portalCode", portalCode);

		return "portal/main/listUser";
	}

	/**
	 * 메인 화면 왼쪽 메뉴 설정
	 * 
	 * @param portalMymenuConfig 포탈 좌측 메뉴 설정 모델 객체
	 * @return success 메세지
	 */
	@RequestMapping(value = "/updateOpenOption.do", method = RequestMethod.POST)
	public @ResponseBody
	String updateOpenOption(@ModelAttribute PortalMymenuConfig portalMymenuConfig) {

		User user = (User) getRequestAttribute("ikep.user");

		PortalMymenuConfig readPortalMymenuConfig = portalMymenuConfigService.readPortalMymenuConfig(user.getUserId());

		portalMymenuConfig.setUserId(user.getUserId());
		portalMymenuConfig.setRegisterId(user.getUserId());
		portalMymenuConfig.setRegisterName(user.getUserName());
		portalMymenuConfig.setUpdaterId(user.getUserId());
		portalMymenuConfig.setUpdaterName(user.getUserName());

		if (readPortalMymenuConfig == null) {
			// 데이터 없으면 인서트
			portalMymenuConfigService.createPortalMymenuConfig(portalMymenuConfig);
		} else {
			// 데이터 있으면 업데이트
			portalMymenuConfigService.updateOpenOption(portalMymenuConfig);
		}

		return "success";
	}

	/**
	 * 사이트 맵
	 * 
	 * @return 포워딩 정보
	 */
	@RequestMapping(value = "/portalSitemap.do")
	public String portalSitemap( Model model)  {
		
		User user = (User) getRequestAttribute("ikep.user");
		boolean ptmAuth = aclService.hasSystemPermission("Portal-Menu", "READ", "100000793365", user.getUserId());//PTM메뉴에 대한 권한이 있는지 여부
		boolean  knowledgeAuth = aclService.hasSystemPermission("Portal-Menu", "READ", "100000676474", user.getUserId());//Knowledge메뉴에 대한 권한이 있는지 여부
		
		boolean a1 = false;
		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("userId", user.getUserId());
		map1.put("roleName", "A1");
		int tmpa1 = userDao.getUserRoleCheck(map1);
		if(tmpa1 > 0){
			a1 = true;
		}
		
		boolean a2 = false;
		Map<String, String> map2 = new HashMap<String, String>();
		map2.put("userId", user.getUserId());
		map2.put("roleName", "A2");
		int tmpa2 = userDao.getUserRoleCheck(map2);
		if(tmpa2 > 0){
			a2 = true;
		}
		
		boolean a3 = false;
		Map<String, String> map3 = new HashMap<String, String>();
		map3.put("userId", user.getUserId());
		map3.put("roleName", "A3");
		int tmpa3 = userDao.getUserRoleCheck(map3);
		if(tmpa3 > 0){
			a3 = true;
		}
		
		boolean a4 = false;
		Map<String, String> map4 = new HashMap<String, String>();
		map4.put("userId", user.getUserId());
		map4.put("roleName", "A4");
		int tmpa4 = userDao.getUserRoleCheck(map4);
		if(tmpa4 > 0){
			a4 = true;
		}
		
		boolean z1 = false;
		Map<String, String> mapz1 = new HashMap<String, String>();
		mapz1.put("userId", user.getUserId());
		mapz1.put("roleName", "Z1");
		int tmpz1 = userDao.getUserRoleCheck(mapz1);
		if(tmpz1 > 0){
			z1 = true;
		}
		
		boolean z2 = false;
		Map<String, String> mapz2 = new HashMap<String, String>();
		mapz2.put("userId", user.getUserId());
		mapz2.put("roleName", "Z2");
		int tmpz2 = userDao.getUserRoleCheck(mapz2);
		if(tmpz2 > 0){
			z2 = true;
		}
		
		boolean z3 = false;
		Map<String, String> mapz3 = new HashMap<String, String>();
		mapz3.put("userId", user.getUserId());
		mapz3.put("roleName", "Z3");
		int tmpz3 = userDao.getUserRoleCheck(mapz3);
		if(tmpz3 > 0){
			z3 = true;
		}
		
		boolean z4 = false;
		Map<String, String> mapz4 = new HashMap<String, String>();
		mapz4.put("userId", user.getUserId());
		mapz4.put("roleName", "Z4");
		int tmpz4 = userDao.getUserRoleCheck(mapz4);
		if(tmpz4 > 0){
			z4 = true;
		}
		
		boolean z5 = false;
		Map<String, String> mapz5 = new HashMap<String, String>();
		mapz5.put("userId", user.getUserId());
		mapz5.put("roleName", "Z5");
		int tmpz5 = userDao.getUserRoleCheck(mapz5);
		if(tmpz5 > 0){
			z5 = true;
		}
		boolean acl0 = false;
		Map<String, String> mapacl0 = new HashMap<String, String>();
		mapacl0.put("userId", user.getUserId());
		mapacl0.put("roleName", "acl0");
		int tmpacl0 = userDao.getUserRoleCheck(mapacl0);
		if(tmpacl0 > 0){
			acl0 = true;
		}
		
		boolean acl4 = false;
		Map<String, String> mapacl4 = new HashMap<String, String>();
		mapacl4.put("userId", user.getUserId());
		mapacl4.put("roleName", "acl4");
		int tmpacl4 = userDao.getUserRoleCheck(mapacl4);
		if(tmpacl4 > 0){
			acl4 = true;
		}
		
		boolean acl5 = false;
		Map<String, String> mapacl5 = new HashMap<String, String>();
		mapacl5.put("userId", user.getUserId());
		mapacl5.put("roleName", "acl5");
		int tmpacl5 = userDao.getUserRoleCheck(mapacl5);
		if(tmpacl5 > 0){
			acl5 = true;
		}
		
		boolean acl8 = false;
		Map<String, String> mapacl8 = new HashMap<String, String>();
		mapacl8.put("userId", user.getUserId());
		mapacl8.put("roleName", "acl8");
		int tmpacl8 = userDao.getUserRoleCheck(mapacl8);
		if(tmpacl8 > 0){
			acl8 = true;
		}
		
		model.addAttribute("a1", a1);
		model.addAttribute("a2", a2);
		model.addAttribute("a3", a3);
		model.addAttribute("a4", a4);
		model.addAttribute("z1", z1);
		model.addAttribute("z2", z2);
		model.addAttribute("z3", z3);
		model.addAttribute("z4", z4);
		model.addAttribute("z5", z5);
		model.addAttribute("acl0", acl0);
		model.addAttribute("acl4", acl4);
		model.addAttribute("acl5", acl5);
		model.addAttribute("acl8", acl8);
		model.addAttribute("ptmAuth", ptmAuth);
		model.addAttribute("knowledgeAuth", knowledgeAuth);
		
		return "portal/main/portalSitemap";
	}

	/**
	 * FUTURE planet
	 * 
	 * @return 포워딩 정보
	 */
	@RequestMapping(value = "/portalFuturePlanet.do")
	public String portalFuturePlanet() {
		return "portal/main/portalFuturePlanet";
	}
	
	/**
	 * 사용자 그룹의 전체 경로 리턴
	 * 
	 */
	@RequestMapping("/getDeptPath.do")
	public void getDeptPath(@RequestParam(value="search_string") String userId, HttpServletResponse res) {
		StringBuffer sb = new StringBuffer();

		int cnt = 0;
		String portalId = (String)getRequestAttribute("ikep.portalId");
		
		HashMap<String, Object> userInfo = new HashMap<String, Object>();
		userInfo.put("userId", userId);
		userInfo.put("portalId", portalId);
		
		String strPath = groupService.getGroupFullPath(userInfo);
		if(strPath != null && !strPath.equals("")) {
			String[] strArr = strPath.split(",");
			sb.append("[");
			for(String groupid : strArr) {
				cnt++;
				if(!groupid.equals("")) {
					sb.append("\"" + groupid + "\"");
					if(cnt < strArr.length) sb.append(",");
				}
			}
			sb.append("]");
		}
		
		try {
			res.getWriter().println(sb.toString());
		} catch(Exception e) {}
	}
	
	/**
	 * 사용자 그룹의 전체 경로 리턴
	 * 
	 */
	@RequestMapping("/getDeptPathByGroup.do")
	public void getDeptPathByGroup(@RequestParam(value="search_string") String groupId, HttpServletResponse res) {
		StringBuffer sb = new StringBuffer();

		int cnt = 0;
		String portalId = (String)getRequestAttribute("ikep.portalId");
		
		HashMap<String, Object> groupInfo = new HashMap<String, Object>();
		groupInfo.put("groupId", groupId);
		groupInfo.put("portalId", portalId);
		
		String strPath = groupService.getGroupFullPathByGroup(groupInfo);
		if(strPath != null && !strPath.equals("")) {
			String[] strArr = strPath.split(",");
			sb.append("[");
			for(String groupid : strArr) {
				cnt++;
				if(!groupid.equals("")) {
					sb.append("\"" + groupid + "\"");
					if(cnt < strArr.length) sb.append(",");
				}
			}
			sb.append("]");
		}
		
		try {
			res.getWriter().println(sb.toString());
		} catch(Exception e) {}
	}
	
	/**
	 * 온라인 헬프
	 * 
	 */
	@RequestMapping("/onlineHelp.do")
	public String onlineHelp() {
		return "portal/main/onlineHelp";
	}
	
	
	/**
	 * 패스워드 변경창
	 * 
	 */
	@RequestMapping("/newPassword.do")
	public String newPassword() {
		return "portal/main/newPassword";
	}
	
	
	@RequestMapping(value = "/mailMain.do")
	public String mailMain( Model model) {
		User user = (User) getRequestAttribute("ikep.user");
		model.addAttribute("mailUserId", user.getUserId());
		return "portal/main/mailMain";
	}
	
	
	/**
	 * Sap 패스워드 변경창
	 * 
	 */
	@RequestMapping(value = "/newSapPassword.do")
	public String newSapPassword(String workType, String userId, Model model) {
		model.addAttribute("userId", userId);
		return "portal/main/newSapPassword";
	}
	
	@RequestMapping(value = "/findPassword.do")
	public String findPassword() {
		return "portal/main/findPassword";
	}
	
	@RequestMapping("/getPackageVersion.do")
	public @ResponseBody String getPackageVersion() {
		return CommonConstant.PACKAGE_VERSION;
	}
	
	/**
	 * 메인 화면 왼쪽 메뉴 설정
	 * 
	 * @param portalMymenuConfig 포탈 좌측 메뉴 설정 모델 객체
	 * @return success 메세지
	 */
	@RequestMapping(value = "/updatePortalMainLayout.do", method = RequestMethod.POST)
	public @ResponseBody
	String updatePortalMainLayout(@RequestParam(value = "portalMainLayout") String portalMainLayout) {

		String portalId = (String) getRequestAttribute("ikep.portalId");
		User user = (User) getRequestAttribute("ikep.user");
		
		UserConfig userConfig = new UserConfig();
		userConfig.setUserId(user.getUserId());
		userConfig.setModuleId(CommonConstant.PORTAL_MAIN_LAYOUT);
		userConfig.setLayout(portalMainLayout);
		userConfig.setPortalId(portalId);
		
		userConfigService.createUserConfig(userConfig);
		
		return "success";
	}
	
	@RequestMapping(value = "/portletCheckUrl.do", method = RequestMethod.POST)
	public @ResponseBody String portletCheckUrl(@RequestParam(value = "checkUrl") String checkUrl) {

		int responseCode = 0;
		
		if(checkUrl.length() > 7 && "http://".equals(checkUrl.substring(0, 7))) {
			try{
	            /* set up */

	            URL url = new URL(checkUrl);

	            HttpURLConnection urlConn = (HttpURLConnection)url.openConnection();

	            responseCode = urlConn.getResponseCode();
			}

		    catch (Exception e) {
		        System.out.println("Exception : " + e.toString());
		    }
		} else {
			responseCode = 200;
		}
		
		
		return responseCode+"";
	}	
	
	//HR 결재
	@RequestMapping(value = "/getHRapprCount.do", method = RequestMethod.POST)
	public @ResponseBody
	String getHRapprCount() {
		
		EpGetHRWFCountRFC epCount = new EpGetHRWFCountRFC();
		
		User user = (User) this.getRequestAttribute("ikep.user");
		
		String strResult = "0";
		
		if(!StringUtil.isEmpty(user.getSapId()))
		{		
			strResult = epCount.EpGetHRWFCountRFC(null,user.getSapId());
		}

		JSONObject jsonObj = new JSONObject();
		
		jsonObj.put("count", strResult);

		return jsonObj.toString(); // {"count":개수} 형태의 json 타입으로 return 함.
	}
	
	//전표 결재
	@RequestMapping(value = "/getStatementCount.do", method = RequestMethod.POST)
	public @ResponseBody
	String getStatementCount() {
		
		EpGetStatementCountRFC epCount = new EpGetStatementCountRFC();
		
		User user = (User) this.getRequestAttribute("ikep.user");
		
		String strResult = "0";
		
		if(!StringUtil.isEmpty(user.getSapId()))
		{		
			strResult = epCount.EpGetStatementCountRFC(null,user.getSapId());
		}

		JSONObject jsonObj = new JSONObject();
		
		jsonObj.put("count", strResult);

		return jsonObj.toString(); // {"count":개수} 형태의 json 타입으로 return 함.
	}
	
	//HR 결재
	@RequestMapping(value = "/getHRapprCountTest.do", method = RequestMethod.GET)
	public @ResponseBody
	String getHRapprCountTest() {
		
		EpGetHRWFCountRFC epCount = new EpGetHRWFCountRFC();
		
		User user = (User) this.getRequestAttribute("ikep.user");
		
		String strResult = "0";
		
		if(!StringUtil.isEmpty(user.getSapId()))
		{		
			strResult = epCount.EpGetHRWFCountRFC(null,user.getSapId());
		}

		JSONObject jsonObj = new JSONObject();
		
		jsonObj.put("count", strResult);

		return jsonObj.toString(); // {"count":개수} 형태의 json 타입으로 return 함.
	}



	/**
	 * 직급을 통한 조직도 트리를 그리기 위해 리스트를 조회해옴
	 * 
	 * @param 
	 * @param selectType GROUP : 조직도만 조회, USER : 사용자 조회, ALL : 전체 조회
	 * @return 조회된 그룹과 사용자 리스트 Map
	 */
	private List<Map<String, Object>> getJobTitleGroupAndUser(String jobTitleCode, String userId) {

		User sessionuser = (User) getRequestAttribute("ikep.user");
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		List<JobTitle> jobTitleList = jobTitleService.selectJobTitleList(portal.getPortalId());
		
		for (JobTitle jobTitle : jobTitleList) {
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("type", "group");
			map.put("name", jobTitle.getJobTitleName());
			map.put("code", jobTitle.getJobTitleCode());
			map.put("groupTypeId", "");
			map.put("parent", null);
			map.put("hasChild", jobTitle.getNum());
			list.add(map);
		}

		List<User> userList = userService.selectJobTitleUserForTree(sessionuser.getLocaleCode(), jobTitleCode, userId);
		for (User user : userList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("type", "user");
			
			
			String strJobTitle = user.getJobTitleName();
			if(!StringUtil.isEmpty(user.getJobDutyName()))
			{
				strJobTitle = user.getJobDutyName();
			}
			map.put("name", user.getUserName() + " " + strJobTitle + "/" + user.getTeamName());		
			map.put("jobTitle", strJobTitle);
			map.put("teamName", user.getTeamName());

			map.put("code", "");
			map.put("parent", user.getGroupId());
			map.put("id", user.getUserId());
			map.put("empNo", user.getEmpNo());
			map.put("email", user.getMail());
			map.put("mobile", user.getMobile());
			//System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ user.getLeader():"+ user.getLeader()+"@@@@");
			map.put("leader", user.getLeader());
			list.add(map);
		}
		
		return list;
	}


	/**
	 * 직급을 통한 조직도 트리를 그리기 위해 리스트를 조회해옴
	 * 
	 * @param 
	 * @param selectType GROUP : 조직도만 조회, USER : 사용자 조회, ALL : 전체 조회
	 * @return 조회된 그룹과 사용자 리스트 Map
	 */
	private List<Map<String, Object>> getJobDutyGroupAndUser(String jobDutyCode, String userId) {

		User sessionuser = (User) getRequestAttribute("ikep.user");
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		List<JobDuty> jobDutyList = jobDutyService.selectJobDutyList(portal.getPortalId());
		
		for (JobDuty jobDuty : jobDutyList) {
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("type", "group");
			map.put("name", jobDuty.getJobDutyName());
			map.put("code", jobDuty.getJobDutyCode());
			map.put("groupTypeId", "");
			map.put("parent", null);
			map.put("hasChild", jobDuty.getNum());
			list.add(map);
		}

		List<User> userList = userService.selectJobDutyUserForTree(sessionuser.getLocaleCode(), jobDutyCode, userId);
		for (User user : userList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("type", "user");
			
			String strJobTitle = user.getJobTitleName();
			if(!StringUtil.isEmpty(user.getJobDutyName()))
			{
				strJobTitle = user.getJobDutyName();
			}
			map.put("name", user.getUserName() + " " + strJobTitle + "/" + user.getTeamName());		
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
		
		return list;
	}
	
	private boolean checkKmsExceptionId(String userId) {
		
		if(kmsProperty == null){
			kmsProperty = PropertyLoader.loadProperties("/configuration/kms-except-login-id.properties");			
			//kmsLoginIds = kmsProperty.getProperty("kms.login.id").split(","); 
			kmsRedirectUrl = kmsProperty.getProperty("kms.redirect.url");
		}
		/*
		for(String exceptIds : kmsLoginIds){
			
			if(exceptIds.equals(userId)){
				return true;
			}			
		}*/
		Map<String, String> emap = new HashMap<String, String>();
		emap.put("userId", userId);
		emap.put("roleName", "COKM");
		int cokmRole = userDao.getUserRoleCheck(emap);
		if(cokmRole > 0){
			return true;
		}
		return false;
	}
	
	private boolean checkGateExceptionId(String userId) {
		
		try {
			
			if(gateProperty == null){
				gateProperty = PropertyLoader.loadProperties("/configuration/gate-except-login-id.properties");			
				gateLoginIds = gateProperty.getProperty("gate.login.id").split(",");
				gateRedirectUrl = gateProperty.getProperty("gate.redirect.url");
			}
			
			for(String exceptIds : gateLoginIds){
				
				if(exceptIds.equals(userId)){
					return true;
				}			
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}
}
