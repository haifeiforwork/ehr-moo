/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.login.web;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.admin.screen.service.PortalService;
import com.lgcns.ikep4.portal.login.model.UserAccount;
import com.lgcns.ikep4.portal.login.service.LoginUserDetailsService;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.portal.usagetracker.model.PortalLoginRank;
import com.lgcns.ikep4.portal.usagetracker.service.PortalLoginRankService;
import com.lgcns.ikep4.portal.util.BrowserCheck;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.sms.constants.SmsConstants;
import com.lgcns.ikep4.support.sms.model.Sms;
import com.lgcns.ikep4.support.sms.model.SmsConfig;
import com.lgcns.ikep4.support.sms.search.SmsReceiverSearchCondition;
import com.lgcns.ikep4.support.sms.service.SmsConfigService;
import com.lgcns.ikep4.support.sms.service.SmsService;
import com.lgcns.ikep4.support.user.member.dao.UserDao;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.service.UserService;
import com.lgcns.ikep4.support.user.userTheme.model.UserTheme;
import com.lgcns.ikep4.support.user.userTheme.service.UserThemeService;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.encrypt.EncryptUtil;
import com.lgcns.ikep4.util.jco.PasswordModifyRFC;
import com.lgcns.ikep4.util.lang.DateUtil;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.util.model.StatementDetail;

/**
 * 스프링 시큐리티를 이용한 로그인/로그아웃
 * 
 * @author 임종상(malboru80@naver.com)
 * @version $Id: LoginController.java 17495 2012-03-13 08:45:02Z yruyo $
 */
@Controller
public class LoginController extends BaseController {

	@Autowired
	private PortalService portalService;

	@Autowired
	private FileService fileService;

	@Autowired
	private LoginUserDetailsService loginUserDetailsService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserThemeService userThemeService;
	
	@Autowired
	private PortalLoginRankService portalLoginRankService;
	
	@Autowired
	private SmsService smsService;
	
	@Autowired
	private SmsConfigService smsConfigService;
	
	@Autowired
    private UserDao userDao;
	
	
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
	 * 로그인 폼
	 * 
	 * @param request Request 객체
	 * @param model 모델객체
	 * @return 포워딩 정보
	 */
	@RequestMapping(value = "/loginForm.do", method = RequestMethod.GET)
	public String loginForm(HttpServletRequest request, Model model) {

		User user = (User) getRequestAttribute("ikep.user");		
		if (user != null) {
			try {
				
				if(checkKmsExceptionId(user.getUserId())){
					return kmsRedirectUrl;
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			return "redirect:/portal/main/portalMain.do";
		}

		Portal portal = null;

		/*
		 * String contextPath = request.getContextPath(); if
		 * (!StringUtil.isEmpty(contextPath)) { //contextPath portal =
		 * portalService.readPortalContextPath(contextPath); } else { portal =
		 * portalService.readPortalDefault(); }
		 */

		// 사용자가 접속한 도메인
		String domain = request.getServerName();

		// 멀티 포탈 구분 플래그(ContextPath : C, domain : D)
		if (!StringUtil.isEmpty(domain)) {
			// 도메인
			portal = portalService.readPortalDomain(domain);
		} else {
			portal = portalService.readPortalDefault();
		}

		if (portal == null) {
			// 사용하는 포탈이 없을시 에러 페이지 이동
			throw new IKEP4ApplicationException("사용하지 않는 포탈 입니다.", new Exception());
		}

		// 로그인 이미지 체크 없으면 디폴트 이미지 보여준다.
		String loginImageYn = "N";

		if (portal.getLoginImageId() != null && !"".equals(portal.getLoginImageId())) {
			FileData fileData = fileService.read(portal.getLoginImageId());

			if (fileData != null) {
				File file = new File(fileData.getFilePhysicalPath());

				if (file.exists()) {
					loginImageYn = "Y";
				}
			}
		}

		setRequestAttribute("ikep.portal", portal);
		setRequestAttribute("ikep.portalId", portal.getPortalId());
		setRequestAttribute("ikep.defaultLocaleCode", portal.getDefaultLocaleCode());

		model.addAttribute("loginImageYn", loginImageYn);

		// request.setAttribute("ikep.preRedirectUri", StringUtil.nvl(
		// request.getParameter("ikep.preRedirectUri"), ""));

		return "portal/login/loginForm";
	}
	
	@RequestMapping(value = "/passwordSMS.do")
	public String passwordSMS(@RequestParam("uid")String uid,@RequestParam("empNo")String empNo,@RequestParam("mobileNo")String mobileNo){
		
		String redirectUrl ="redirect:/passwordSearchForm.do";
		String portalId = (String) getRequestAttribute("ikep.portalId");
		
		//UserAccount userAccount = loginUserDetailsService.loadUserByUsername(uid, portalId);메신저호출위한 하드코딩...
		UserAccount userAccount = loginUserDetailsService.loadUserByUsername(uid, "P000001");
		if(userAccount==null){
			return redirectUrl+"?error=1";
		}
		String decryptPassword = EncryptUtil.decryptText(userAccount.getPassword());
		User tmpUser =   userService.read(uid);
		if(tmpUser.getUserId().equals(uid)){
			if (StringUtil.isEmpty(tmpUser.getEmpNo())) {
				if (StringUtil.isEmpty(tmpUser.getMobile())) {
					return redirectUrl+"?error=4";
				}else{
					String mobilePhoneNo = tmpUser.getMobile().replaceAll("-", "");
					if(mobilePhoneNo.equals(mobileNo)){
						Sms sms = new Sms();
						sms.setRegisterId("admin");
						sms.setRegisterName("관리자");
						sms.setReceiverPhonenos(mobilePhoneNo.split("[-]"));
						sms.setReceiverIds(tmpUser.getUserId().split("[-]"));
						sms.setSenderPhoneno("****");
						sms.setContents(StringUtil.replaceSQLString(tmpUser.getUserName()+"의 비밀번호는 "+decryptPassword+"입니다."));
						smsService.create(sms); 
						return redirectUrl+"?error=0";
					}else{
						return redirectUrl+"?error=3";
					}
				}
			}else{
				if(tmpUser.getEmpNo().equals(empNo)){
					if (StringUtil.isEmpty(tmpUser.getMobile())) {
						return redirectUrl+"?error=4";
					}else{
						String mobilePhoneNo = tmpUser.getMobile().replaceAll("-", "");
						if(mobilePhoneNo.equals(mobileNo)){
							Sms sms = new Sms();
							sms.setRegisterId("admin");
							sms.setRegisterName("관리자");
							sms.setReceiverPhonenos(mobilePhoneNo.split("[-]"));
							sms.setReceiverIds(tmpUser.getUserId().split("[-]"));
							sms.setSenderPhoneno("****");
							sms.setContents(StringUtil.replaceSQLString(tmpUser.getUserName()+"의 비밀번호는 "+decryptPassword+"입니다."));
							smsService.create(sms); 	
							return redirectUrl+"?error=0";
						}else{
							return redirectUrl+"?error=3";
						}
					}
				}else{
					return redirectUrl+"?error=2";
				}
			}
		}else{
			return redirectUrl+"?error=1";
		}
	}
	
	@RequestMapping(value = "/passwordSearchForm.do")
	public String passwordSearchForm(){
		return "portal/login/passwordSearchForm";
	}
	
	@RequestMapping(value = "/identityAuthForm.do")
	public ModelAndView identityAuthForm(String uid){
		ModelAndView mav = new ModelAndView("/portal/login/identityAuthForm");
		User user = userService.read(uid);
		mav.addObject("uid", user.getUserId());
		mav.addObject("uName", user.getUserName());
		mav.addObject("uMobile", user.getMobile().replaceAll("-", "").replaceAll(" ", ""));
		return mav;
	}
	
	@RequestMapping(value = "/sapUnlockingForm.do")
	public ModelAndView sapUnlockingForm(String uid){
		ModelAndView mav = new ModelAndView("/portal/login/sapUnlockingForm");
		mav.addObject("uid", uid);
		return mav;
	}
	
	@RequestMapping(value = "/passwordUpdateForm.do")
	public ModelAndView passwordUpdateForm(String uid,String ucd){
		ModelAndView mav = new ModelAndView("/portal/login/passwordUpdateForm");
		mav.addObject("uid", uid);
		mav.addObject("ucd", ucd);
		return mav;
	}
	
	@RequestMapping(value = "/passwordUpdate.do")
	public @ResponseBody String passwordUpdate(@RequestParam(value = "uid", required = true) String userId,@RequestParam(value = "ucd", required = true) String userCode, @RequestParam(value = "npw", required = true) String newPassword,
			HttpServletRequest request) {

		User tempUser = new User();
		tempUser.setUserId(userId);
		tempUser.setTempCode(userCode);
		
		int userCheck =   userService.requestCertificationCheck(tempUser);
		if(userCheck < 1){
			return "error7";
		}
		User user = userService.read(userId);
		String portalId = "P000001";

		
		// 입력된 예전password를 encrypt한다.
		//String encryptPassword = EncryptUtil.encryptSha(user.getUserId()+password);
		//String encryptPassword = EncryptUtil.encryptText(password);
		//System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@encryptPassword:"+encryptPassword);

		UserAccount userAccount = loginUserDetailsService.loadUserByUsername(userId, portalId);
		
		int resultInt =0;
		int bwresultInt =0;
		int tempresultInt =0;
		int aporesultInt =0;
		String newencryptPassword = EncryptUtil.encryptText(newPassword);//무림제지에서는 복호화 가능하게 해서 넣는다.2012.08.31
		//System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@newencryptPassword:"+newencryptPassword);
		
		PasswordModifyRFC passwordModifyRFC=new PasswordModifyRFC();
		if( ((!StringUtil.isEmpty(user.getEssAuthCode())) && (!"E99".equals(user.getEssAuthCode()))) || ((!StringUtil.isEmpty(user.getMssAuthCode())) && (!"M99".equals(user.getMssAuthCode())))){
			if( (!StringUtil.isEmpty(user.getBwId())) && (!user.getBwId().toLowerCase().contains("epbwif"))){//bw 아이디가 있으면서 공용아이디가 아니면
				tempresultInt = passwordModifyRFC.bwPasswordReset(user.getUserId().toUpperCase(), request);
				bwresultInt = passwordModifyRFC.bwPasswordModify(user.getUserId() , "a1234567", newPassword ,request);
			}
			
			if( (!StringUtil.isEmpty(user.getSapId())) && (!"moorim12".equals(user.getSapId().toLowerCase()))){//sap 아이디가 있으면서 공용아이디(moorim12)가 아니면
				tempresultInt = passwordModifyRFC.sapPasswordReset(user.getUserId().toUpperCase(), request);
				resultInt = passwordModifyRFC.sapPasswordModify(user.getUserId() , "a1234567", newPassword ,request);
			}
		}
		
		tempresultInt = passwordModifyRFC.apoPasswordReset(userId.toUpperCase(), request);
		aporesultInt = passwordModifyRFC.apoPasswordModify(userId, "a1234567", newPassword ,request);
		
		if(resultInt==1){//CHANGE_NOT_ALLOWED
			return "?error3";
			
		}else if(resultInt==4){//PASSWORD_NOT_ALLOWED
			return "?error4";
			
		}else{//resultInt==0
			
			if(bwresultInt==1){//CHANGE_NOT_ALLOWED
				return "?error5";
				
			}else if(bwresultInt==4){//PASSWORD_NOT_ALLOWED
				return "error6";
				
			}else{//resultInt==0
				
			
				List<User> userlist =new ArrayList<User>();
				User passUser = new User();
				passUser.setUserId(user.getUserId());
				passUser.setUserPassword(newencryptPassword);
				userlist.add(passUser);
				userService.updateForPassword(userlist);
				userService.updateMsgForPassword(userlist);
				return "error0";
			}
		}
	}
	
	@RequestMapping(value = "/requestCertification.do")
	public @ResponseBody int requestCertification(String searchId, String searchName, String searchPhoneNo){
		User tempUser = new User();
		tempUser.setUserId(searchId);
		tempUser.setUserName(searchName);
		tempUser.setMobile(searchPhoneNo);
		int userCheck =   userService.requestCertification(tempUser);
		
		
		return userCheck;
	}
	
	@RequestMapping(value = "/requestCertificationCheck.do")
	public @ResponseBody int requestCertificationCheck(String searchId, String code){
		User tempUser = new User();
		tempUser.setUserId(searchId);
		tempUser.setTempCode(code);
		int userCheck =   userService.requestCertificationCheck(tempUser);
		
		
		return userCheck;
	}
	
	@RequestMapping(value = "/identityAuthCheck.do")
	public @ResponseBody String identityAuthCheck(String searchId){
		User tmpUser =   userService.read(searchId);
		String uid = "";
		if(tmpUser != null){
			uid = tmpUser.getUserId();
		}else{
			uid = "error";
		}
		return uid;
	}
	
	@RequestMapping(value = "/sapUnlocking.do")
	public @ResponseBody String sapUnlocking(String searchId, String category,HttpServletRequest request){
		User user =   userService.read(searchId);
		String uid = "";
		if(user != null){
			uid = user.getUserId();
		}else{
			uid = "error";
		}
		int resultInt =0;
		int bwresultInt =0;
		int tempresultInt =0;
		int aporesultInt =0;
		PasswordModifyRFC passwordModifyRFC=new PasswordModifyRFC();
		String decryptPassword = EncryptUtil.decryptText(user.getUserPassword());
		if(category.equals("SAP")){
			if( (!StringUtil.isEmpty(user.getSapId())) && (!"moorim12".equals(user.getSapId().toLowerCase()))){//sap 아이디가 있으면서 공용아이디(moorim12)가 아니면
				tempresultInt = passwordModifyRFC.sapPasswordReset(user.getUserId().toUpperCase(), request);
				resultInt = passwordModifyRFC.sapPasswordModify(user.getUserId() , "a1234567", decryptPassword ,request);
			}else{
				if(StringUtil.isEmpty(user.getSapId())){
					uid = "계정이 존재하지 않습니다.";
				}else if("moorim12".equals(user.getSapId().toLowerCase())){
					uid = "공용계정 사용자는 잠금해제를 할수 없습니다.";
				}
			}
		}else if(category.equals("BW")){
			if( (!StringUtil.isEmpty(user.getBwId())) && (!user.getBwId().toLowerCase().contains("epbwif"))){//bw 아이디가 있으면서 공용아이디가 아니면
				tempresultInt = passwordModifyRFC.bwPasswordReset(user.getUserId().toUpperCase(), request);
				bwresultInt = passwordModifyRFC.bwPasswordModify(user.getUserId() , "a1234567", decryptPassword ,request);
			}else{
				if(StringUtil.isEmpty(user.getBwId())){
					uid = "계정이 존재하지 않습니다.";
				}else if(user.getBwId().toLowerCase().contains("epbwif")){
					uid = "공용계정 사용자는 잠금해제를 할수 없습니다.";
				}
			}
		}else if(category.equals("APO")){
			tempresultInt = passwordModifyRFC.apoPasswordReset(user.getUserId().toUpperCase(), request);
			aporesultInt = passwordModifyRFC.apoPasswordModify(user.getUserId(), "a1234567", decryptPassword ,request);
		}
		return uid;
	}

	private boolean checkKmsExceptionId(String userId) {
		
		try {
			
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
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
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

	/**
	 * 로그아웃
	 * 
	 * @return 포워딩 정보
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/logout.do")
	public String logout(HttpSession session) {
		// Session에서 정보 제거
		session.invalidate();

		// loginForm으로 redirection
		return "redirect:/loginForm.do";
	}

	/**
	 * 롤 권한 없을시 보여지는 페이지
	 * 
	 * @return 포워딩 정보
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/accessdenied.do")
	public String accessdenied() {
		return "portal/login/accessdenied";
	}
	
	@RequestMapping(value = "/externalSms.do")
	public ModelAndView externalSms(
			//@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "SMS", operationName = { "MANAGE" }, resourceName = "SMS")) AccessingResult accessResult,
			SmsReceiverSearchCondition searchCondition, BindingResult result, 
			@RequestParam(value = "euserid", required = false) String euserid,
			@RequestParam(value = "gubun", required = false) String gubun,@RequestParam(value = "receiverId", required = false) String receiverId,
			@RequestParam(value = "sendTarget", required = false) String sendTarget,
			SessionStatus status,
			HttpServletRequest request) {
		
		Portal portal = null;
		
		portal = portalService.readPortalDefault();
		User user =   userService.read(euserid);
		String tempSendTarget = sendTarget.replaceAll("-", "");
		
		
		HttpSession session = request.getSession();
		setRequestAttribute("ikep.portal", portal);
		setRequestAttribute("ikep.portalId", portal.getPortalId());
		setRequestAttribute("ikep.defaultLocaleCode", portal.getDefaultLocaleCode());
		
		
		authenticationSuccess(request, euserid);

		searchCondition.setRegisterId(user.getUserId());
		searchCondition.setPagePerRecord(SmsConstants.RECENT_SMS_PAGE_PER_RECORD); //최근방송내역(6)
		searchCondition.setGubun(gubun); 		   //파라미터로 넘어온 인자 gubun(내부사용자일경우 1, 외부사용자일 경우 2)
		searchCondition.setReceiverId(receiverId); //파라미터로 넘어온 인자 receiverId(내부사용자일경우 userId, 외부사용자일 경우 phoneNum)
		ModelAndView mav = new ModelAndView("/support/sms/externalsms");

		SearchResult<Sms> searchResult = smsService.listSmsReceiverBySearchCondition(searchCondition);

		List<Sms> listRecentBottom = smsService.list(searchCondition);
		int totalRecentBottomCount = smsService.listRecentBottomCount(searchCondition);
		
		int smsCount = 0;  
		SmsConfig smsConfig = smsConfigService.readSmsConfig(user.getUserId());
		
		if(smsConfig == null) {
			SmsConfig smsDefConfig = smsConfigService.readSmsConfig("sms_def_count");
			
			if(smsDefConfig == null) {
				smsCount = 50;
			} else {
				smsCount = smsDefConfig.getSmsCount();
			}
		} else {
			smsCount = smsConfig.getSmsCount();
		}
		
		mav.addObject("smsCount", smsCount);
		//mav.addObject("smsAdmin", accessResult.isAccess());
		mav.addObject("smsRecentBottom", listRecentBottom);	
		mav.addObject("searchResult", searchResult);
		mav.addObject("smsRecentBottomCount", totalRecentBottomCount);	
		mav.addObject("searchCondition", searchResult.getSearchCondition());
		mav.addObject("sendTarget", tempSendTarget);	
		return mav;
	}
	
	
	@RequestMapping(value = "/messengerLogin.do")
	public String messengerLogin(@RequestParam(value = "j_username", required = true) String username,
			@RequestParam(value = "j_password", required = true) String password, 
			@RequestParam(value = "gubun", required = false) String gubun,
			@RequestParam(value = "receiverId", required = false) String receiverId, 
			@RequestParam(value = "itemId", required = false) String itemId, HttpServletRequest request) {
		
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
		
		if(!StringUtil.isEmpty(user.getEmpNo())){
			userService.authCheck(user);
		}
		
		//loginLogInput(user.getUserId());
		
		String preRedirectURI   = "/portal/main/portalMain.do";
		
		if("2".equals(gubun)&&!StringUtil.isEmpty(itemId)){
			preRedirectURI ="/lightpack/board/boardItem/readBoardItemLinkView.do?popupYn=true&itemId="+itemId;
		}else if(!StringUtil.isEmpty(gubun)&&!StringUtil.isEmpty(receiverId)){
			preRedirectURI ="/support/sms/messengerSms.do?gubun="+gubun+"&receiverId="+receiverId;
		}

		if(checkKmsExceptionId(user.getUserId())){
			return kmsRedirectUrl;
		}
		
		if(checkGateExceptionId(user.getUserId())){
			return gateRedirectUrl;
		}
		
		return "redirect:" + preRedirectURI;
	}
	
	@RequestMapping(value = "/messengerKmsMain.do")
	public String messengerKmsMain(@RequestParam(value = "j_username", required = true) String username,
			@RequestParam(value = "j_password", required = true) String password, 
			@RequestParam(value = "gubun", required = false) String gubun,
			@RequestParam(value = "receiverId", required = false) String receiverId, 
			@RequestParam(value = "itemId", required = false) String itemId, HttpServletRequest request) {
		
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
		
		//loginLogInput(user.getUserId());
		
		String preRedirectURI   = "/portal/main/kmsMain.do";
		
		if("2".equals(gubun)&&!StringUtil.isEmpty(itemId)){
			preRedirectURI ="/lightpack/board/boardItem/readBoardItemLinkView.do?popupYn=true&itemId="+itemId;
		}else if(!StringUtil.isEmpty(gubun)&&!StringUtil.isEmpty(receiverId)){
			preRedirectURI ="/support/sms/messengerSms.do?gubun="+gubun+"&receiverId="+receiverId;
		}

		if(checkKmsExceptionId(user.getUserId())){
			return kmsRedirectUrl;
		}
		
		if(checkGateExceptionId(user.getUserId())){
			return gateRedirectUrl;
		}
		
		return "redirect:" + preRedirectURI;
	}
	
	
	@RequestMapping(value = "/messengerBoardFaq.do")
	public String messengerBoardFaq(@RequestParam(value = "j_username", required = true) String username,
			@RequestParam(value = "j_password", required = true) String password, 
			@RequestParam(value = "gubun", required = false) String gubun,
			@RequestParam(value = "receiverId", required = false) String receiverId, 
			@RequestParam(value = "itemId", required = false) String itemId, HttpServletRequest request) {
		
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
		
		//loginLogInput(user.getUserId());
		
		String preRedirectURI   = "/portal/main/boardFaq.do";
		
		if("2".equals(gubun)&&!StringUtil.isEmpty(itemId)){
			preRedirectURI ="/lightpack/board/boardItem/readBoardItemLinkView.do?popupYn=true&itemId="+itemId;
		}else if(!StringUtil.isEmpty(gubun)&&!StringUtil.isEmpty(receiverId)){
			preRedirectURI ="/support/sms/messengerSms.do?gubun="+gubun+"&receiverId="+receiverId;
		}

		if(checkKmsExceptionId(user.getUserId())){
			return kmsRedirectUrl;
		}
		
		if(checkGateExceptionId(user.getUserId())){
			return gateRedirectUrl;
		}
		
		return "redirect:" + preRedirectURI;
	}
	
	/**
	 * 로그인 체크 함수
	 * 
	 * @param username
	 * @param password
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/login.do")
	public String login(@RequestParam(value = "j_username", required = true) String username,
			@RequestParam(value = "j_password", required = true) String password, HttpServletRequest request) {

		HttpSession session = request.getSession();

		// 접속자가 입력한 uri를 세션에서 읽어온다.
		String preRedirectURI = (String) session.getAttribute("ikep.preRedirectUri");

		// 접속자가 입력한 uri 존재하지 않을 경우, main으로 세팅한다.
		if (preRedirectURI == null) {	
			
			if(checkKmsExceptionId(username)){
				preRedirectURI =  kmsRedirectUrl.substring(kmsRedirectUrl.indexOf(":")+1);
			}else{
				preRedirectURI = "/portal/main/portalMain.do";
			}
		}

		// session에서 portalId를 읽어온다.
		String portalId = (String) getRequestAttribute("ikep.portalId");

		if (portalId == null) {
			return "redirect:" + preRedirectURI;
		}

		// 입력된 password를 encrypt한다.
		//String encryptPassword = EncryptUtil.encryptSha(username + password);// ikep4 원래소스
		String encryptPassword = EncryptUtil.encryptText(password);//무림제지 복호화 SAP연동을 위해 가능하게 함. 2012.08.31
		

		UserAccount userAccount = loginUserDetailsService.loadUserByUsername(username, portalId);

		// User가 존재하지 않을 경우
		if (userAccount == null) {
			return "redirect:/loginForm.do?error=1";
		}

		// authentication check
		if (!encryptPassword.equals(userAccount.getPassword())) {
			// password가 올바르지 않을 경우, loginForm으로 redirection 한다.
			return "redirect:/loginForm.do?error=1";
		}

		// 로그인이 성공한후의 작업 (세션정보 저장)을 수행한다.
		
		User user =   userService.read(username);
		
		
		String workType="";
		if (encryptPassword.equals( EncryptUtil.encryptText("a1234567"))) {//초기 패스워드이면서 

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
		authenticationSuccess(request, username);
		
		if(!StringUtil.isEmpty(user.getEmpNo())){
			userService.authCheck(user);
		}
		
		
		
		//loginLogInput(user.getUserId());
		//loginUserDetailsService.catchPassword(username, password,encryptPassword);
		return "redirect:" + preRedirectURI;
	}
	
	@RequestMapping(value = "/sapPasswordModify.do")
	public String sapPasswordModify(@RequestParam(value = "userId", required = true) String userId, @RequestParam(value = "npw", required = true) String newPassword,
			@RequestParam(value = "opw", required = true) String password, @RequestParam(value = "workType", required = true) String workType,  HttpServletRequest request) {



		User user = userService.read(userId);
		String portalId = "P000001";

		
		// 입력된 예전password를 encrypt한다.
		//String encryptPassword = EncryptUtil.encryptSha(user.getUserId()+password);
		String encryptPassword = EncryptUtil.encryptText(password);
		//System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@encryptPassword:"+encryptPassword);

		UserAccount userAccount = loginUserDetailsService.loadUserByUsername(userId, portalId);
		
		String redirectUrl ="redirect:/portal/main/newSapPassword.do";
	

		// User가 존재하지 않을 경우
		if (userAccount == null) {
			return redirectUrl+"?error=2&workType="+workType;
		}else{

			boolean authCheck = true;
			List<String> workTypeList = new ArrayList<String>();
			String[] works = "initsap.initbw.initall".split(".");
			workTypeList =Arrays.asList(works);
			
			if(!workTypeList.contains(workType)){//초기값이어서 sap,bw변경후 ep변경을 하는 경우는 제외하고 모든경우에 이전패스워드와 동일한지 체크한다.
				authCheck = encryptPassword.equals(userAccount.getPassword());
			}
			//System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@authCheck:"+authCheck);
			// authentication check
			if (!authCheck) {
				// password가 올바르지 않을 경우  redirection 한다.
				return redirectUrl+"?error=1&workType="+workType+"&userId="+userId;
			}else{
				// 입력된 새password를 encrypt한다.
				//String newencryptPassword = EncryptUtil.encryptSha(user.getUserId()+newPassword);
				//String newencryptPassword = EncryptUtil.encryptSha(newPassword);
				int resultInt =0;
				int bwresultInt =0;
				int tempresultInt =0;
				String newencryptPassword = EncryptUtil.encryptText(newPassword);//무림제지에서는 복호화 가능하게 해서 넣는다.2012.08.31
				//System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@newencryptPassword:"+newencryptPassword);
				
				PasswordModifyRFC passwordModifyRFC=new PasswordModifyRFC();
				if( ((!StringUtil.isEmpty(user.getEssAuthCode())) && (!"E99".equals(user.getEssAuthCode()))) || ((!StringUtil.isEmpty(user.getMssAuthCode())) && (!"M99".equals(user.getMssAuthCode())))){
					if( (!StringUtil.isEmpty(user.getBwId())) && (!user.getBwId().toLowerCase().contains("epbwif"))){//bw 아이디가 있으면서 공용아이디가 아니면
						tempresultInt = passwordModifyRFC.bwPasswordReset(user.getUserId().toUpperCase(), request);
						bwresultInt = passwordModifyRFC.bwPasswordModify(user.getUserId() , "a1234567", newPassword ,request);
					}
					
					if( (!StringUtil.isEmpty(user.getSapId())) && (!"moorim12".equals(user.getSapId().toLowerCase()))){//sap 아이디가 있으면서 공용아이디(moorim12)가 아니면
						tempresultInt = passwordModifyRFC.sapPasswordReset(user.getUserId().toUpperCase(), request);
						resultInt = passwordModifyRFC.sapPasswordModify(user.getUserId() , "a1234567", newPassword ,request);
					}
				}
				int aporesultInt =0;
				tempresultInt = passwordModifyRFC.apoPasswordReset(userId.toUpperCase(), request);
				aporesultInt = passwordModifyRFC.apoPasswordModify(userId, "a1234567", newPassword ,request);
				
				if(resultInt==1){//CHANGE_NOT_ALLOWED
					return redirectUrl+"?error=3&workType="+workType+"&userId="+userId;
					
				}else if(resultInt==4){//PASSWORD_NOT_ALLOWED
					return redirectUrl+"?error=4&workType="+workType+"&userId="+userId;
					
				}else{//resultInt==0
					
					if(bwresultInt==1){//CHANGE_NOT_ALLOWED
						return redirectUrl+"?error=5&workType="+workType+"&userId="+userId;
						
					}else if(bwresultInt==4){//PASSWORD_NOT_ALLOWED
						return redirectUrl+"?error=6&workType="+workType+"&userId="+userId;
						
					}else{//resultInt==0
						
					
						List<User> userlist =new ArrayList<User>();
						User passUser = new User();
						passUser.setUserId(user.getUserId());
						passUser.setUserPassword(newencryptPassword);
						userlist.add(passUser);
						userService.updateForPassword(userlist);
						userService.updateMsgForPassword(userlist);
						return redirectUrl+"?error=0&workType="+workType+"&userId="+userId;
					}
				}



			}
		}

	}
	
	@RequestMapping(value ="/passwordModify.do")
	public String passwordModify(@RequestParam(value = "npw", required = true) String newPassword,
			@RequestParam(value = "opw", required = true) String password, @RequestParam(value = "workType", required = true) String workType,  HttpServletRequest request) {

		HttpSession session = request.getSession();

		User user = (User) getRequestAttribute("ikep.user");
		String portalId = (String) getRequestAttribute("ikep.portalId");


		// 입력된 예전password를 encrypt한다.
		//String encryptPassword = EncryptUtil.encryptSha(user.getUserId()+password);
		String encryptPassword = EncryptUtil.encryptText(password);

		UserAccount userAccount = loginUserDetailsService.loadUserByUsername(user.getUserId(), portalId);
		
		String redirectUrl ="redirect:/portal/main/newPassword.do";
		if(!"userself".equals(workType)){
			redirectUrl ="redirect:/portal/main/newSapPassword.do";
		}

		// User가 존재하지 않을 경우
		if (userAccount == null) {
			return redirectUrl+"error=2&workType="+workType;
		}else{

			boolean authCheck = true;
			
			if(!"initsap.initbw.initall".contains(workType)){//초기값이어서 sap,bw변경후 ep변경을 하는 경우는 제외하고 모든경우에 이전패스워드와 동일한지 체크한다.
				authCheck = encryptPassword.equals(userAccount.getPassword());
			}
			// authentication check
			if (!authCheck) {
				// password가 올바르지 않을 경우  redirection 한다.
				return redirectUrl+"?error=1&workType="+workType;
			}else{
				// 입력된 새password를 encrypt한다.
				//String newencryptPassword = EncryptUtil.encryptSha(user.getUserId()+newPassword);
				//String newencryptPassword = EncryptUtil.encryptSha(newPassword);
				int resultInt =0;
				int bwresultInt =0;
				int tempresultInt =0;
				String newencryptPassword = EncryptUtil.encryptText(newPassword);//무림제지에서는 복호화 가능하게 해서 넣는다.2012.08.31
				
				PasswordModifyRFC passwordModifyRFC=new PasswordModifyRFC();
				
				if( ((!StringUtil.isEmpty(user.getEssAuthCode())) && (!"E99".equals(user.getEssAuthCode()))) || ((!StringUtil.isEmpty(user.getMssAuthCode())) && (!"M99".equals(user.getMssAuthCode())))){
					if( (!StringUtil.isEmpty(user.getBwId())) && (!user.getBwId().toLowerCase().contains("epbwif"))){//bw 아이디가 있으면서 공용아이디가 아니면
						tempresultInt = passwordModifyRFC.bwPasswordReset(user.getUserId().toUpperCase(), request);
						bwresultInt = passwordModifyRFC.bwPasswordModify(user.getUserId() , "a1234567", newPassword ,request);
					}
					
					if( (!StringUtil.isEmpty(user.getSapId())) && (!"moorim12".equals(user.getSapId().toLowerCase()))){//sap 아이디가 있으면서 공용아이디(moorim12)가 아니면
						tempresultInt = passwordModifyRFC.sapPasswordReset(user.getUserId().toUpperCase(), request);
						resultInt = passwordModifyRFC.sapPasswordModify(user.getUserId() , "a1234567", newPassword ,request);
					}
				}
				
				int aporesultInt =0;
				tempresultInt = passwordModifyRFC.apoPasswordReset(user.getUserId().toUpperCase(), request);
				aporesultInt = passwordModifyRFC.apoPasswordModify(user.getUserId(), "a1234567", newPassword ,request);
			
				if(bwresultInt==1){//CHANGE_NOT_ALLOWED
					return redirectUrl+"?error=5&workType="+workType;
					
				}else if(bwresultInt==4){//PASSWORD_NOT_ALLOWED
					return redirectUrl+"?error=6&workType="+workType;
					
				}else{//resultInt==0
				
				
					if(resultInt==1){//CHANGE_NOT_ALLOWED
						return redirectUrl+"?error=3&workType="+workType;
						
					}else if(resultInt==4){//PASSWORD_NOT_ALLOWED
						return redirectUrl+"?error=4&workType="+workType;
						
					}else{//resultInt==0
					

						List<User> userlist =new ArrayList<User>();
						User passUser = new User();
						passUser.setUserId(user.getUserId());
						passUser.setUserPassword(newencryptPassword);
						userlist.add(passUser);
						userService.updateForPassword(userlist);
						userService.updateMsgForPassword(userlist);
						return redirectUrl+"?error=0&workType="+workType;

					}
				}


			}
		}

	}

	/**
	 * SSO Login 예시
	 * @param username
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/ssoLogin.do", method = RequestMethod.GET)
	public String ssoLogin(@RequestParam(required = true) String userId, HttpServletRequest request,
			HttpServletResponse response) {
		// SSO를 찔러서 login이 되어있는지 확인하는 루틴 구현 필요
		boolean ssoFlag = true;

		User user = (User) getRequestAttribute("ikep.user");

		if (user != null) {
			
			if(checkKmsExceptionId(user.getUserId())){
				return kmsRedirectUrl;
			}
			
			return "redirect:/portal/main/portalMain.do";
		}

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

		if (portal == null) {
			// 사용하는 포탈이 없을시 에러 페이지 이동
			throw new IKEP4ApplicationException("사용하지 않는 포탈 입니다.", new Exception());
		}

		setRequestAttribute("ikep.portal", portal);
		setRequestAttribute("ikep.portalId", portal.getPortalId());
		setRequestAttribute("ikep.defaultLocaleCode", portal.getDefaultLocaleCode());

		if (ssoFlag) {
			UserAccount userAccount = loginUserDetailsService.loadUserByUsername(userId, portal.getPortalId());
			
			// User가 존재하지 않을 경우
			if (userAccount == null) {
				return "redirect:/loginForm.do?error=1";
			}

			authenticationSuccess(request, userId);
		}
		
		if(checkKmsExceptionId(user.getUserId())){
			return kmsRedirectUrl;
		}

		return "redirect:/portal/main/portalMain.do";
	}
	
	/**
	 * 로그인 인증이 성공한 경우 수행할 작업
	 * @param request
	 * @param username
	 */
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
	
	private void loginLogInput(String userId) {

		userService.loginLogInput(userId);
	}

}
