/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.kms.main.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.lgcns.ikep4.collpack.kms.admin.dao.AdminWinnerManageDao;
import com.lgcns.ikep4.collpack.kms.admin.model.AdminWinner;
import com.lgcns.ikep4.collpack.kms.admin.model.Board;
import com.lgcns.ikep4.collpack.kms.admin.model.CompulsionTime;
import com.lgcns.ikep4.collpack.kms.admin.search.KmsAdminSearchCondition;
import com.lgcns.ikep4.collpack.kms.admin.service.AdminPermissionService;
import com.lgcns.ikep4.collpack.kms.admin.service.AdminWinnerManageService;
import com.lgcns.ikep4.collpack.kms.announce.dao.AnnounceItemDao;
import com.lgcns.ikep4.collpack.kms.announce.model.AnnounceItem;
import com.lgcns.ikep4.collpack.kms.announce.service.AnnounceItemService;
import com.lgcns.ikep4.collpack.kms.board.model.BoardItem;
import com.lgcns.ikep4.collpack.kms.board.search.BoardItemSearchCondition;
import com.lgcns.ikep4.collpack.kms.board.service.BoardItemService;
import com.lgcns.ikep4.collpack.kms.main.model.KmsPortletLayout;
import com.lgcns.ikep4.collpack.kms.main.service.KmsPortletLayoutService;
import com.lgcns.ikep4.collpack.kms.main.service.KmsService;
import com.lgcns.ikep4.collpack.kms.perform.model.Performance;
import com.lgcns.ikep4.collpack.kms.perform.search.PerformanceSearchCondition;
import com.lgcns.ikep4.collpack.kms.perform.service.PerformanceService;
import com.lgcns.ikep4.collpack.qna.constants.QnaConstants;
import com.lgcns.ikep4.collpack.qna.model.Qna;
import com.lgcns.ikep4.collpack.qna.service.QnaService;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.admin.screen.service.PortalService;
import com.lgcns.ikep4.portal.login.model.UserAccount;
import com.lgcns.ikep4.portal.login.service.LoginUserDetailsService;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.portal.usagetracker.model.PortalLoginRank;
import com.lgcns.ikep4.portal.usagetracker.service.PortalLoginRankService;
import com.lgcns.ikep4.portal.util.BrowserCheck;
import com.lgcns.ikep4.support.cache.service.CacheService;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.timezone.service.TimeZoneSupportService;
import com.lgcns.ikep4.support.user.member.dao.UserDao;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.service.UserService;
import com.lgcns.ikep4.support.user.userTheme.model.UserTheme;
import com.lgcns.ikep4.support.user.userTheme.service.UserThemeService;
import com.lgcns.ikep4.support.userconfig.model.UserConfig;
import com.lgcns.ikep4.support.userconfig.service.UserConfigService;
import com.lgcns.ikep4.util.encrypt.EncryptUtil;
import com.lgcns.ikep4.util.lang.DateUtil;
import com.lgcns.ikep4.util.lang.StringUtil;


/**
 * kms Main Controller
 * 
 * @author 
 * @version $Id: CollaborationController.java 15616 2011-06-27 05:33:17Z
 *          loverfairy $
 */

@Controller
@RequestMapping(value = "/collpack/kms/main")
public class KmsController extends BaseController {
	
	private static final String USER_CONFIG_MODULE_ID = "KMS";
	private static final String USER_CONFIG_KEYINFO_MODULE_ID = "KEYINFO";
	private static final int PAGE_PER_RECORD = 5;

	@Autowired
	private TimeZoneSupportService timeZoneSupportService;
	
	@Autowired
	private BoardItemService boardItemService;

	
	@Autowired
	private KmsPortletLayoutService kmsPortletLayoutService;
	
	@Autowired
	private AdminWinnerManageService adminWinnerManageService;
	
	@Autowired
	private AdminWinnerManageDao adminWinnerManageDao;
	
	@Autowired
	private KmsService kmsService;
	
	
	@Autowired
	private QnaService qnaService;
	

	@Autowired
	private AnnounceItemService announceItemService;

	
	@Autowired
	private ACLService aclService;

	@Autowired
	private PerformanceService performanceService;
	
	@Autowired
	private CacheService cacheService;
	
	@Autowired
	private AnnounceItemDao announceItemDao;
	
	@Autowired
	private UserConfigService userConfigService;	
	
	@Autowired
	private AdminPermissionService adminPermissionService;
	
	@Autowired
    private UserDao userDao;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserThemeService userThemeService;
	
	@Autowired
	private PortalLoginRankService portalLoginRankService;
	
	@Autowired
	private PortalService portalService;
	
	@Autowired
	private LoginUserDetailsService loginUserDetailsService;

	/**
	 * kms 전문가 여부 체크
	 * 
	 * @param userId
	 * @return
	 */
	public boolean isSystemAdmin(String userId) {

		boolean isSystemAdmin = aclService.isSystemAdmin(Board.KMS_MANAGER, userId);

		return isSystemAdmin;
	}
	
	/**
	 * kms 전체 관리자 여부 체크
	 * 
	 * @param userId
	 * @return
	 */
	public boolean isAssessor(String userId) {

		boolean isSystemAdmin = boardItemService.isAssessor(userId);

		return isSystemAdmin;
	}
	
	public boolean isKeyInfoAssessor(String userId) {

		boolean isAssessor = boardItemService.isKeyInfoAssessor(userId);

		return isAssessor;
	}


	/**
	 * Kms 메인
	 * 
	 * @param boardId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/Kms.do", method = RequestMethod.GET)
	public String workspace(@RequestParam(value = "boardId", required = false) String boardId,@RequestParam(value = "pageName", required = false) String pageName, Model model) {

		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");	
		
		boolean isSystemAdmin 	= this.isSystemAdmin(user.getUserId());
		boolean isAssessor		= this.isAssessor(user.getUserId()); 
		boolean isKeyInfoAssessor		= this.isKeyInfoAssessor(user.getUserId());
		
		/*연간 누적*/
		PerformanceSearchCondition searchCondition = new PerformanceSearchCondition();
		searchCondition.setUserId(user.getUserId());
		if(this.getNowMonth().equals("12")){
			searchCondition.setSearchYear(Integer.toString((Integer.parseInt(this.getNowYear())+1)));
		}else{
			searchCondition.setSearchYear(this.getNowYear());
		}
		searchCondition.setFromLeft("Y");
		
		Performance performance1 = performanceService.getPrivatePerformance(searchCondition);
		
		/*월별 점수*/
		searchCondition = new PerformanceSearchCondition();
		searchCondition.setUserId(user.getUserId());
		searchCondition.setSearchYear(this.getNowYear());
		searchCondition.setSearchMonth(this.getNowMonth());
		searchCondition.setFromLeft("Y");
		
		Performance performance2 = performanceService.getPrivatePerformance(searchCondition);
		
		Date today = new Date();

		Calendar cal = Calendar.getInstance();
		cal.setTime(timeZoneSupportService.convertTimeZone());

		String todayM = cal.getTimeInMillis() + "";
		
		CompulsionTime compulsionTime =  adminPermissionService.selectCompulsionTimeSetting();
		int compulsionStartTimeClick = adminPermissionService.selectCompulsionTimeClickCheck("1",user.getUserId());
		int compulsionEndTimeClick = adminPermissionService.selectCompulsionTimeClickCheck("2",user.getUserId());
		int tmpDay = today.getDay();

		model.addAttribute("todayM", todayM);
		model.addAttribute("isSystemAdmin", isSystemAdmin);
		model.addAttribute("isAssessor", isAssessor);
		model.addAttribute("isKeyInfoAssessor", isKeyInfoAssessor);
		model.addAttribute("performance1", performance1);
		model.addAttribute("performance2", performance2);
		model.addAttribute("compulsionTime", compulsionTime);
		model.addAttribute("compulsionDay", tmpDay);
		model.addAttribute("compulsionStartTimeClick", compulsionStartTimeClick);
		model.addAttribute("compulsionEndTimeClick", compulsionEndTimeClick);
		model.addAttribute("yyyy", this.getNowYear());
		model.addAttribute("yyyy1", this.getNowYear1());
		model.addAttribute("mm", this.getNowMonth());
		model.addAttribute("dd", this.getNowDay());
		model.addAttribute("pageName", pageName);
	
		
		/**
		 * Workspace 게시판 정보 Tree
		 */
		/**
		 * Map<String, String> map = new HashMap<String, String>();
		 * map.put("workspaceId", workspace.getWorkspaceId());
		 * map.put("boardRootId", DEFAULT_BOARD_ROOT_ID); List<Board> boardList
		 * = boardAdminService.listByBoardRootId(map); // List<Board> boardList
		 * = // boardAdminService.listByBoardRootId(DEFAULT_BOARD_ROOT_ID);
		 * String boardTreeJson = TreeMaker.init(boardList, "boardId",
		 * "boardParentId", "boardName").create() .toJsonString();
		 **/
		
		/*
		 * My Collaboration
		 */
		/*
		Map<String, String> myCollMap = new HashMap<String, String>();
		myCollMap.put("portalId", portal.getPortalId());
		myCollMap.put("memberId", user.getUserId());

		List<Workspace> myWorkspaceList = null;
		myWorkspaceList = workspaceService.listMyCollaboration(myCollMap);		

		
		model.addAttribute("boardId", boardId);
		model.addAttribute("myWorkspaceList", myWorkspaceList);
		*/

		return "collpack/kms/main/Kms";
	}
	
	@RequestMapping(value = "/knowledgeShare.do", method = RequestMethod.GET)
	public String knowledgeShare(@RequestParam(value = "boardId", required = false) String boardId, Model model) {

		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");	
		
		boolean isSystemAdmin 	= this.isSystemAdmin(user.getUserId());
		boolean isAssessor		= this.isAssessor(user.getUserId()); 
		boolean isKeyInfoAssessor		= this.isKeyInfoAssessor(user.getUserId());
		
		/*연간 누적*/
		PerformanceSearchCondition searchCondition = new PerformanceSearchCondition();
		searchCondition.setUserId(user.getUserId());
		if(this.getNowMonth().equals("12")){
			searchCondition.setSearchYear(Integer.toString((Integer.parseInt(this.getNowYear())+1)));
		}else{
			searchCondition.setSearchYear(this.getNowYear());
		}
		searchCondition.setFromLeft("Y");
		
		Performance performance1 = performanceService.getPrivatePerformance(searchCondition);
		
		/*월별 점수*/
		searchCondition = new PerformanceSearchCondition();
		searchCondition.setUserId(user.getUserId());
		searchCondition.setSearchYear(this.getNowYear());
		searchCondition.setSearchMonth(this.getNowMonth());
		searchCondition.setFromLeft("Y");
		
		Performance performance2 = performanceService.getPrivatePerformance(searchCondition);
		
		Date today = new Date();

		Calendar cal = Calendar.getInstance();
		cal.setTime(timeZoneSupportService.convertTimeZone());

		String todayM = cal.getTimeInMillis() + "";
		
		CompulsionTime compulsionTime =  adminPermissionService.selectCompulsionTimeSetting();
		int compulsionStartTimeClick = adminPermissionService.selectCompulsionTimeClickCheck("1",user.getUserId());
		int compulsionEndTimeClick = adminPermissionService.selectCompulsionTimeClickCheck("2",user.getUserId());
		int tmpDay = today.getDay();

		model.addAttribute("todayM", todayM);
		model.addAttribute("isSystemAdmin", isSystemAdmin);
		model.addAttribute("isAssessor", isAssessor);
		model.addAttribute("isKeyInfoAssessor", isKeyInfoAssessor);
		model.addAttribute("performance1", performance1);
		model.addAttribute("performance2", performance2);
		model.addAttribute("compulsionTime", compulsionTime);
		model.addAttribute("compulsionDay", tmpDay);
		model.addAttribute("compulsionStartTimeClick", compulsionStartTimeClick);
		model.addAttribute("compulsionEndTimeClick", compulsionEndTimeClick);
		model.addAttribute("yyyy", this.getNowYear());
		model.addAttribute("yyyy1", this.getNowYear1());
		model.addAttribute("mm", this.getNowMonth());
		model.addAttribute("dd", this.getNowDay());
	
		
		/**
		 * Workspace 게시판 정보 Tree
		 */
		/**
		 * Map<String, String> map = new HashMap<String, String>();
		 * map.put("workspaceId", workspace.getWorkspaceId());
		 * map.put("boardRootId", DEFAULT_BOARD_ROOT_ID); List<Board> boardList
		 * = boardAdminService.listByBoardRootId(map); // List<Board> boardList
		 * = // boardAdminService.listByBoardRootId(DEFAULT_BOARD_ROOT_ID);
		 * String boardTreeJson = TreeMaker.init(boardList, "boardId",
		 * "boardParentId", "boardName").create() .toJsonString();
		 **/
		
		/*
		 * My Collaboration
		 */
		/*
		Map<String, String> myCollMap = new HashMap<String, String>();
		myCollMap.put("portalId", portal.getPortalId());
		myCollMap.put("memberId", user.getUserId());

		List<Workspace> myWorkspaceList = null;
		myWorkspaceList = workspaceService.listMyCollaboration(myCollMap);		

		
		model.addAttribute("boardId", boardId);
		model.addAttribute("myWorkspaceList", myWorkspaceList);
		*/

		return "collpack/kms/main/knowledgeShare";
	}
	
	@RequestMapping(value = "/compulsionTimeClickSave.do")	
	public @ResponseBody String compulsionTimeClickSave(String compulsionTimeClickFlg) {
		 		
		CompulsionTime compulsionTime = new CompulsionTime();
		User user = (User) getRequestAttribute("ikep.user");	
		compulsionTime.setUserId(user.getUserId());
		compulsionTime.setCompulsionTimeClickFlg(compulsionTimeClickFlg);
		
		adminPermissionService.compulsionTimeClickSave(compulsionTime);
		
		//int compulsionStartTimeClick = adminPermissionService.selectCompulsionTimeClickCheck("1");
		//int compulsionEndTimeClick = adminPermissionService.selectCompulsionTimeClickCheck("2");
		
		return "success";
	}

	/**
	 * Kms 메인 Body 화면 
	 * 
	 * @param model
	 * @return 
	 */
	@RequestMapping(value = "/KmsBody.do", method = RequestMethod.GET)
	public String kmsBody(Model model) {

		List<KmsPortletLayout> kmsPortletLayoutList = kmsPortletLayoutService.listLayout();
		AnnounceItem announceItem = announceItemDao.getTopAnnounceItem();
		model.addAttribute("kmsPortletLayoutList", kmsPortletLayoutList);
		model.addAttribute("announceItem1", announceItem);

		return "collpack/kms/main/KmsBody"; 
	}
	
	@RequestMapping(value = "/messengerKmsView.do")
	public String messengerKmsView(Model model,@RequestParam(value = "j_username", required = true) String username,
			@RequestParam(value = "j_password", required = true) String password, 
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
		

		List<KmsPortletLayout> kmsPortletLayoutList = kmsPortletLayoutService.listLayout();
		AnnounceItem announceItem = announceItemDao.getTopAnnounceItem();
		model.addAttribute("kmsPortletLayoutList", kmsPortletLayoutList);
		model.addAttribute("announceItem1", announceItem);

		return "collpack/kms/main/messengerKmsView"; 
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
	
	@RequestMapping(value = "/knowledgeShareBody.do", method = RequestMethod.GET)
	public String knowledgeShareBody(Model model) {

		List<KmsPortletLayout> kmsPortletLayoutList = kmsPortletLayoutService.listLayoutNew();
		AnnounceItem announceItem = announceItemDao.getTopAnnounceItem();
		model.addAttribute("kmsPortletLayoutList", kmsPortletLayoutList);
		model.addAttribute("announceItem1", announceItem);

		return "collpack/kms/main/knowledgeShareBody"; 
	}

	

	/**
	 * 최근게시물 목록 포틀릿 정보 조회
	 * 
	 * @param workspaceId
	 * @param portletLayoutId
	 * @return
	 */
	@RequestMapping(value = "/portlet/listRecentBoardItemPortlet.do")
	public ModelAndView listRecentBoardItemPortlet(@RequestParam("portletLayoutId") String portletLayoutId) {		

		ModelAndView model = new ModelAndView("collpack/kms/main/portlet/listRecentBoardItemPortlet");
		
		BoardItemSearchCondition searchCondition = new BoardItemSearchCondition();
		searchCondition.setPagePerRecord(5);	
		
		//일반정보
		/*if(StringUtil.nvl(portletLayoutId, "1").equals("1")){
			searchCondition.setIsKnowhow("1");*/
			
		//업무노하우
		/*}else{
			searchCondition.setIsKnowhow("0");
		}*/
		
		User user = readUser();

		//사용자 정보등급세팅
		searchCondition.setInfoGrage(user.getInfoGrade());
		searchCondition.setViewMode("0");
		searchCondition.setUserId(user.getUserId());
		searchCondition.setAdmin(this.isSystemAdmin(user.getUserId()));
		searchCondition.setIsKnowhow("1");
		SearchResult<BoardItem> searchResult1 = boardItemService.latestListBySearchCondition(searchCondition);
		List<BoardItem> boardItem1 = new ArrayList<BoardItem>();
		if(!searchResult1.isEmptyRecord()){
			boardItem1 = searchResult1.getEntity();
		}
		
		searchCondition.setIsKnowhow("0");
		SearchResult<BoardItem> searchResult2 = boardItemService.latestListBySearchCondition(searchCondition);
		List<BoardItem> boardItem2 = new ArrayList<BoardItem>();
		
		
		
		if(!searchResult2.isEmptyRecord()){
			boardItem2 = searchResult2.getEntity();
		}
		
		searchCondition.setIsKnowhow("3");
		SearchResult<BoardItem> searchResult3 = boardItemService.latestListBySearchCondition(searchCondition);
		List<BoardItem> boardItem3 = new ArrayList<BoardItem>();
		
		
		
		if(!searchResult3.isEmptyRecord()){
			boardItem3 = searchResult3.getEntity();
		}
		
		boolean cokmrole = false;
		Map<String, String> emap = new HashMap<String, String>();
		emap.put("userId", user.getUserId());
		emap.put("roleName", "COKM");
		int cokmRole = userDao.getUserRoleCheck(emap);
		if(cokmRole > 0){
			cokmrole = true;
		}
		
		KmsPortletLayout kmsPortletLayout = kmsPortletLayoutService.read(portletLayoutId);
		model.addObject("kmsPortletLayout", kmsPortletLayout);
		model.addObject("boardItem1", boardItem1);
		model.addObject("boardItem2", boardItem2);
		model.addObject("boardItem3", boardItem3);
		model.addObject("cokmrole", cokmrole);
		model.addObject("isSystemAdmin", this.isSystemAdmin(user.getUserId()));

		return model;
	}
	
	@RequestMapping(value = "/portlet/listHotissueBoardItemPortlet.do")
	public ModelAndView listHotissueBoardItemPortlet(@RequestParam("portletLayoutId") String portletLayoutId) {		

		ModelAndView model = new ModelAndView("collpack/kms/main/portlet/listHotissueBoardItemPortlet");
		
		BoardItemSearchCondition searchCondition = new BoardItemSearchCondition();
		searchCondition.setPagePerRecord(5);	
		
		//일반정보
		/*if(StringUtil.nvl(portletLayoutId, "1").equals("1")){
			searchCondition.setIsKnowhow("1");*/
			
		//업무노하우
		/*}else{
			searchCondition.setIsKnowhow("0");
		}*/
		
		User user = readUser();

		//사용자 정보등급세팅
		searchCondition.setInfoGrage(user.getInfoGrade());
		searchCondition.setViewMode("0");
		searchCondition.setUserId(user.getUserId());
		searchCondition.setAdmin(this.isSystemAdmin(user.getUserId()));
		searchCondition.setIsKnowhow("1");
		SearchResult<BoardItem> searchResult1 = boardItemService.hotissueListBySearchCondition(searchCondition);
		List<BoardItem> boardItem1 = new ArrayList<BoardItem>();
		if(!searchResult1.isEmptyRecord()){
			boardItem1 = searchResult1.getEntity();
		}
		/*searchCondition.setIsKnowhow("0");
		SearchResult<BoardItem> searchResult2 = boardItemService.hotissueListBySearchCondition(searchCondition);
		List<BoardItem> boardItem2 = new ArrayList<BoardItem>();*/
		
		
		
		/*if(!searchResult2.isEmptyRecord()){
			boardItem2 = searchResult2.getEntity();
		}*/
		
		boolean cokmrole = false;
		Map<String, String> emap = new HashMap<String, String>();
		emap.put("userId", user.getUserId());
		emap.put("roleName", "COKM");
		int cokmRole = userDao.getUserRoleCheck(emap);
		if(cokmRole > 0){
			cokmrole = true;
		}

		KmsPortletLayout kmsPortletLayout = kmsPortletLayoutService.read(portletLayoutId);
		model.addObject("kmsPortletLayout", kmsPortletLayout);
		model.addObject("boardItem1", boardItem1);
		model.addObject("cokmrole", cokmrole);
		model.addObject("isSystemAdmin", this.isSystemAdmin(user.getUserId()));
		//model.addObject("boardItem2", boardItem2);

		return model;
	}
	
	
	


	/**
	 * 공지사항 포틀릿
	 * 
	 * 
	 * @param portletLayoutId
	 * @return
	 */
	@RequestMapping(value = "/portlet/listAnnouncePortlet.do")
	public ModelAndView listAnnouncePortlet(@RequestParam("portletLayoutId") String portletLayoutId) {

		User user = readUser();
		ModelAndView model = new ModelAndView("collpack/kms/main/portlet/listAnnouncePortlet");

		Map<String, String> map = new HashMap<String, String>();		
		map.put("limitSize", "5");

		List<AnnounceItem> announceItem = announceItemService.listAnnounceItemByPortlet(map);
		KmsPortletLayout kmsPortletLayout = kmsPortletLayoutService.read(portletLayoutId);

		model.addObject("kmsPortletLayout", kmsPortletLayout);
		model.addObject("announceItem", announceItem);
		
		
		return model;
	}
	
	
	/**
	 * 주간우수정보 포틀릿
	 * 
	 * 
	 * @param portletLayoutId
	 * @return
	 */
	@RequestMapping(value = "/portlet/listMarkPortlet.do")
	public ModelAndView listMarkPortlet(@RequestParam("portletLayoutId") String portletLayoutId) {

		
		User user = readUser();
		ModelAndView model = new ModelAndView("collpack/kms/main/portlet/listMarkPortlet");

		BoardItemSearchCondition searchCondition = new BoardItemSearchCondition();
		searchCondition.setPagePerRecord(5);		

		//사용자 정보등급세팅
		searchCondition.setInfoGrage(user.getInfoGrade());
		searchCondition.setViewMode("0");
		searchCondition.setUserId(user.getUserId());
		searchCondition.setAdmin(this.isSystemAdmin(user.getUserId()));
		SearchResult<BoardItem> searchResult = boardItemService.excellenceListBySearchCondition(searchCondition);
		List<BoardItem> markItem = new ArrayList<BoardItem>();
		
		if(!searchResult.isEmptyRecord()){
			markItem = searchResult.getEntity();
		}
		
		boolean cokmrole = false;
		Map<String, String> emap = new HashMap<String, String>();
		emap.put("userId", user.getUserId());
		emap.put("roleName", "COKM");
		int cokmRole = userDao.getUserRoleCheck(emap);
		if(cokmRole > 0){
			cokmrole = true;
		}
		
		KmsPortletLayout kmsPortletLayout = kmsPortletLayoutService.read(portletLayoutId);
		model.addObject("kmsPortletLayout", kmsPortletLayout);
		model.addObject("markItem", markItem);	
		model.addObject("cokmrole", cokmrole);
		model.addObject("isSystemAdmin", this.isSystemAdmin(user.getUserId()));
		
		
		return model;
	}
	
	
	
	/**
	 * QNA 포틀릿
	 * 
	 * 
	 * @param portletLayoutId
	 * @return
	 */
	@RequestMapping(value = "/portlet/listQnaPortlet.do")
	public ModelAndView listQnaPortlet(@RequestParam("portletLayoutId") String portletLayoutId) {
		
		User user = readUser();
		ModelAndView model = new ModelAndView("collpack/kms/main/portlet/listQnaPortlet");
		
		//최신 qna
		Qna newQnaSearch = new Qna();

		newQnaSearch.setEndRowIndex(5);
		newQnaSearch.setStartRowIndex(0);		
		newQnaSearch.setPortalId(user.getPortalId());
		newQnaSearch.setQnaType(QnaConstants.IS_QNA);
		newQnaSearch.setItemDelete(QnaConstants.ITEM_DELETE_NO); // 삭제되지 않은 자료만

		List<Qna> newList = qnaService.list(newQnaSearch);	
		
		KmsPortletLayout kmsPortletLayout = kmsPortletLayoutService.read(portletLayoutId);
		model.addObject("kmsPortletLayout", kmsPortletLayout);
		model.addObject("newList", newList);	
		
		return model;
	}
	
	
	/**
	 * 우수지식인 포틀릿
	 * 
	 * 
	 * @param portletLayoutId
	 * @return
	 */
	@RequestMapping(value = "/portlet/listWinners.do")
	public ModelAndView listWinners(@RequestParam("portletLayoutId") String portletLayoutId) {
		
		User user = readUser();
		ModelAndView model = new ModelAndView("collpack/kms/main/portlet/listWinners");
		
		KmsAdminSearchCondition searchCondition = new KmsAdminSearchCondition();
		searchCondition.setPagePerRecord(5);
		searchCondition.setDisMode("0");
		SearchResult<AdminWinner> searchResult1 = adminWinnerManageService.getWinnerListByPortlet(searchCondition);
		searchCondition.setDisMode("1");
		SearchResult<AdminWinner> searchResult2 = adminWinnerManageService.getWinnerListByPortlet(searchCondition);
		searchCondition.setDisMode("2");
		SearchResult<AdminWinner> searchResult3 = adminWinnerManageService.getWinnerListByPortlet(searchCondition);
		String displayMode = adminWinnerManageDao.getDisplay();
		List<AdminWinner> winnerMonthlyItem = new ArrayList<AdminWinner>();
		List<AdminWinner> winnerQuarterItem = new ArrayList<AdminWinner>();
		List<AdminWinner> winnerYearItem = new ArrayList<AdminWinner>();
		
		String monthlyYear = "";
		String monthly = "";
		String quarterYear = "";
		String quarter = "";
		String year = "";
		
		if(!searchResult1.isEmptyRecord()){
			winnerMonthlyItem = searchResult1.getEntity();
			monthly = searchResult1.getEntity().get(0).getWinGb();
			monthlyYear = searchResult1.getEntity().get(0).getWinYear();
		}
		
		if(!searchResult2.isEmptyRecord()){
			winnerQuarterItem = searchResult2.getEntity();
			quarter = searchResult2.getEntity().get(0).getWinGb();
			quarterYear = searchResult2.getEntity().get(0).getWinYear();
		}
		
		if(!searchResult3.isEmptyRecord()){
			winnerYearItem = searchResult3.getEntity();
			year = searchResult3.getEntity().get(0).getWinYear();
		}
		
		
		KmsPortletLayout kmsPortletLayout = kmsPortletLayoutService.read(portletLayoutId);
		model.addObject("kmsPortletLayout", kmsPortletLayout);
		model.addObject("winnerMonthlyItem", winnerMonthlyItem);
		model.addObject("winnerQuarterItem", winnerQuarterItem);
		model.addObject("winnerYearItem", winnerYearItem);
		model.addObject("displayMode", displayMode);
		model.addObject("monthlyYear", monthlyYear);
		model.addObject("monthly", monthly);
		model.addObject("quarterYear", quarterYear);
		model.addObject("quarter", quarter);
		model.addObject("year", year);
		
		return model;
	}
	
	
	@RequestMapping(value = "/portlet/listHandsome.do")
	public ModelAndView listHandsome(@RequestParam("portletLayoutId") String portletLayoutId) {
		
		User user = readUser();
		ModelAndView model = new ModelAndView("collpack/kms/main/portlet/listHandsome");
		
		KmsAdminSearchCondition searchCondition = new KmsAdminSearchCondition();
		searchCondition.setPagePerRecord(3);
		searchCondition.setDisMode("0");
		SearchResult<AdminWinner> searchResult1 = adminWinnerManageService.getWinnerPeopleListByPortlet(searchCondition);
		searchCondition.setDisMode("1");
		SearchResult<AdminWinner> searchResult2 = adminWinnerManageService.getWinnerTeamListByPortlet(searchCondition);
		searchCondition.setDisMode("2");
		SearchResult<AdminWinner> searchResult3 = adminWinnerManageService.getWinnerAwardListByPortlet(searchCondition);
		String displayMode = adminWinnerManageDao.getDisplay();
		List<AdminWinner> winnerPeopleItem = new ArrayList<AdminWinner>();
		List<AdminWinner> winnerTeamItem = new ArrayList<AdminWinner>();
		List<AdminWinner> winnerAwardItem = new ArrayList<AdminWinner>();
		
		String monthlyYear = "";
		String monthly = "";
		String quarterYear = "";
		String quarter = "";
		String year = "";
		
		if(!searchResult1.isEmptyRecord()){
			winnerPeopleItem = searchResult1.getEntity();
			if(displayMode.equals("0")){
				monthly = searchResult1.getEntity().get(0).getWinGb();
				monthlyYear = searchResult1.getEntity().get(0).getWinYear();
			}else if(displayMode.equals("1")){
				quarter = searchResult1.getEntity().get(0).getWinGb();
				quarterYear = searchResult1.getEntity().get(0).getWinYear();
			}else{
				year = searchResult1.getEntity().get(0).getWinYear();
			}
		}
		
		if(!searchResult2.isEmptyRecord()){
			winnerTeamItem = searchResult2.getEntity();
			if(displayMode.equals("0")){
				if(StringUtils.isEmpty(monthly)) {
				monthly = searchResult2.getEntity().get(0).getWinGb();
				}
				if(StringUtils.isEmpty(monthlyYear)) {
				monthlyYear = searchResult2.getEntity().get(0).getWinYear();
				}
			}else if(displayMode.equals("1")){
				if(StringUtils.isEmpty(quarter)) {
				quarter = searchResult2.getEntity().get(0).getWinGb();
				}
				if(StringUtils.isEmpty(quarterYear)) {
				quarterYear = searchResult2.getEntity().get(0).getWinYear();
				}
			}else{
				if(StringUtils.isEmpty(year)) {
				year = searchResult2.getEntity().get(0).getWinYear();
				}
			}
		}
		
		if(!searchResult3.isEmptyRecord()){
			winnerAwardItem = searchResult3.getEntity();
			if(displayMode.equals("0")){
				if(StringUtils.isEmpty(monthly)) {
				monthly = searchResult3.getEntity().get(0).getWinGb();
				}
				if(StringUtils.isEmpty(monthlyYear)) {
				monthlyYear = searchResult3.getEntity().get(0).getWinYear();
				}
			}else if(displayMode.equals("1")){
				if(StringUtils.isEmpty(quarter)) {
				quarter = searchResult3.getEntity().get(0).getWinGb();
				}
				if(StringUtils.isEmpty(quarterYear)) {
				quarterYear = searchResult3.getEntity().get(0).getWinYear();
				}
			}else{
				if(StringUtils.isEmpty(year)) {
				year = searchResult3.getEntity().get(0).getWinYear();
				}
			}
		}
		
		
		KmsPortletLayout kmsPortletLayout = kmsPortletLayoutService.read(portletLayoutId);
		model.addObject("kmsPortletLayout", kmsPortletLayout);
		model.addObject("winnerPeopleItem", winnerPeopleItem);
		model.addObject("winnerTeamItem", winnerTeamItem);
		model.addObject("winnerAwardItem", winnerAwardItem);
		model.addObject("displayMode", displayMode);
		model.addObject("monthlyYear", monthlyYear);
		model.addObject("monthly", monthly);
		model.addObject("quarterYear", quarterYear);
		model.addObject("quarter", quarter);
		model.addObject("year", year);
		
		return model;
	}
	
	@RequestMapping(value = "/portlet/performance.do")
	public ModelAndView performance(String searchYear, String searchMonth) {
		
		User user = readUser();
		ModelAndView model = new ModelAndView("collpack/kms/main/portlet/performance");
		PerformanceSearchCondition searchCondition = new PerformanceSearchCondition();
		SearchResult<Performance> searchResult = null;
		if(StringUtil.isEmpty(searchYear)){
			searchYear=DateUtil.getToday("yyyy");
		}
		if(StringUtil.isEmpty(searchMonth)){
			searchMonth=DateUtil.getToday("MM");
		}
		
		searchCondition.setSearchPeriod(searchYear+searchMonth);
		searchResult = performanceService.listPerformance(searchCondition);
		HashMap<String, String> totalSumMap = new HashMap<String, String>();
		String[] arrRegSum = null;
		String[] arrTeam = null;
		//if(!searchResult.isEmptyRecord()){
			ArrayList<String[]> resultChartList = getChartData(searchResult, totalSumMap);
			arrRegSum = resultChartList.get(0);
			arrTeam = resultChartList.get(1);	
		//}
		model.addObject("arrRegSum", arrRegSum);
		model.addObject("arrTeam", arrTeam);
		model.addObject("searchYear", searchYear);
		model.addObject("searchMonth", searchMonth);
		model.addObject("nowYear", DateUtil.getToday("yyyy"));
		model.addObject("nowMonth", DateUtil.getToday("MM"));
		return model;
	}
	
	@RequestMapping(value = "/portlet/performanceSub.do")
	public ModelAndView performanceSub(String searchYear, String searchMonth) {
		
		User user = readUser();
		ModelAndView model = new ModelAndView("collpack/kms/main/portlet/performanceSub");
		PerformanceSearchCondition searchCondition = new PerformanceSearchCondition();
		SearchResult<Performance> searchResult = null;
		if(StringUtil.isEmpty(searchYear)){
			searchYear=DateUtil.getToday("yyyy");
		}
		if(StringUtil.isEmpty(searchMonth)){
			searchMonth=DateUtil.getToday("MM");
		}
		
		searchCondition.setSearchPeriod(searchYear+searchMonth);
		searchResult = performanceService.listPerformance(searchCondition);
		HashMap<String, String> totalSumMap = new HashMap<String, String>();
		String[] arrRegSum = null;
		String[] arrTeam = null;
		//if(!searchResult.isEmptyRecord()){
			ArrayList<String[]> resultChartList = getChartData(searchResult, totalSumMap);
			arrRegSum = resultChartList.get(0);
			arrTeam = resultChartList.get(1);	
		//}
		model.addObject("arrRegSum", arrRegSum);
		model.addObject("arrTeam", arrTeam);
		model.addObject("searchYear", searchYear);
		model.addObject("searchMonth", searchMonth);
		model.addObject("nowYear", DateUtil.getToday("yyyy"));
		model.addObject("nowMonth", DateUtil.getToday("MM"));
		return model;
	}
	
	private ArrayList<String[]> getChartData(SearchResult<Performance> searchResult, HashMap<String, String> totalSumMap) {
		List<Performance> listPrivateStatPerson = searchResult.getEntity();
		int size = listPrivateStatPerson.size();
		ArrayList<String> regList = new ArrayList<String>();
		ArrayList<String> teamList = new ArrayList<String>();
		ArrayList<String[]> resultList = new ArrayList();
		
		makeTotSumData(listPrivateStatPerson, regList, teamList, totalSumMap);
		
		resultList.add(0, regList.toArray(new String[size]));
		resultList.add(1, teamList.toArray(new String[size]));
		
		return resultList;
	}
	
	private void makeTotSumData(List<Performance> listPrivateStatPerson, ArrayList<String> regList,
			ArrayList<String> teamList, HashMap<String, String> totalSumMap) {
		
		//int totRegSum = 0;
		
		for(Performance performance : listPrivateStatPerson){
			regList.add(performance.getRegSum());
			teamList.add(performance.getTeamName());
			
			/*try {
				totRegSum += Integer.parseInt(performance.getRegSum());
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}		*/				
		}
		
		//totalSumMap.put("totRegSum", String.valueOf(totRegSum));
		
	}
	
	/**
	 * 나의 관심탭 포틀릿
	 * 
	 * 
	 * @param portletLayoutId
	 * @return
	 */
	@RequestMapping(value = "/portlet/searchTab.do")
	public ModelAndView searchTab(@RequestParam("portletLayoutId") String portletLayoutId) {
		User user = readUser();
		ModelAndView model = new ModelAndView("collpack/kms/main/portlet/searchTab");
		
		List<HashMap<String, String>> keywordList = kmsService.listKeyword(user.getUserId());		
		
		KmsPortletLayout kmsPortletLayout = kmsPortletLayoutService.read(portletLayoutId);
		model.addObject("kmsPortletLayout", kmsPortletLayout);
		model.addObject("keywordList", keywordList);
		model.addObject("isSystemAdmin", this.isSystemAdmin(user.getUserId()));
		if(keywordList != null && keywordList.size() > 0){
			model.addObject("firstKeyword", keywordList.get(0));
		}
		
		return model;
	}
	
	@RequestMapping(value = "/portlet/searchSimpleTab.do")
	public ModelAndView searchSimpleTab(@RequestParam("portletLayoutId") String portletLayoutId) {
		User user = readUser();
		ModelAndView model = new ModelAndView("collpack/kms/main/portlet/searchSimpleTab");
		
		List<HashMap<String, String>> keywordList = kmsService.listKeyword(user.getUserId());		
		
		KmsPortletLayout kmsPortletLayout = kmsPortletLayoutService.read(portletLayoutId);
		model.addObject("kmsPortletLayout", kmsPortletLayout);
		model.addObject("keywordList", keywordList);
		model.addObject("isSystemAdmin", this.isSystemAdmin(user.getUserId()));
		if(keywordList != null && keywordList.size() > 0){
			model.addObject("firstKeyword", keywordList.get(0));
		}
		
		return model;
	}
	
	
	/**
	 * 나의 관심탭 포틀릿 상단 탭
	 * 
	 * 
	 * @param portletLayoutId
	 * @return
	 */
	@RequestMapping(value = "/portlet/searchTabTop.do")
	public ModelAndView searchTabTop() {
		User user = readUser();
		ModelAndView model = new ModelAndView("collpack/kms/main/portlet/searchTabTop");
		List<HashMap<String, String>> keywordList = kmsService.listKeyword(user.getUserId());		
		model.addObject("keywordList", keywordList);	
		
		return model;
	}
	
	@RequestMapping(value = "/portlet/searchSimpleTabTop.do")
	public ModelAndView searchSimpleTabTop() {
		User user = readUser();
		ModelAndView model = new ModelAndView("collpack/kms/main/portlet/searchSimpleTabTop");
		List<HashMap<String, String>> keywordList = kmsService.listKeyword(user.getUserId());		
		model.addObject("keywordList", keywordList);	
		
		return model;
	}
	
	
	/**
	 * 나의 관심탭 포틀릿 탭추가 페이지
	 * 
	 * 
	 * @param portletLayoutId
	 * @return
	 */
	@RequestMapping(value = "/portlet/searchTabAdd.do")
	public ModelAndView searchTabAdd() {
		User user = readUser();
		ModelAndView model = new ModelAndView("collpack/kms/main/portlet/searchTabAdd");
		return model;
	}

		
	
	/**
	 * 키워드 등록처리 비동기 컨트롤 메서드
	 * 
	 * @param keyword
	 * @param userId
	 */	
	
	@RequestMapping(value = "/addKeyword.do")
	public String addKeyword(@RequestParam(value="mode", defaultValue="add") String mode, 
			@RequestParam(value="itemSeq", defaultValue="0") String itemSeq,
			@RequestParam(value="keyword") String keyword, @RequestParam(value="userId") String userId) {

		try {			
			
			Map<String, String> paramMap = new HashMap<String, String>();
			if(mode.equals("del")){
				paramMap.put("itemSeq", itemSeq);
				paramMap.put("userId", userId);
				
				kmsService.deleteKeyword(paramMap);
			}else{				
				paramMap.put("keyword", keyword);
				paramMap.put("userId", userId);
				
				kmsService.insertKeyword(paramMap);
			}
			

		} catch (Exception exception) {
			exception.printStackTrace();
			//throw new IKEP4AjaxException("code", exception);

		}
		return "redirect:/collpack/kms/main/portlet/searchTabTop.do";
	}
	
	/**
	 * 포틀릿 EP 메인의 개인 포틀릿 담당 메소드
	 *
	 * @return the model and view
	 */
	@RequestMapping(value = "/portlet/normalView.do")
	public ModelAndView normalView(@RequestParam String portletConfigId, @RequestParam String portletId) {
		ModelAndView mav = new ModelAndView("collpack/kms/main/portlet/normalView");
		User user = readUser();
		mav.addObject("portletConfigId", portletConfigId);		
		List<BoardItem> normalItemList = getBoardListByMainPortlet("1");
		mav.addObject("normalItemList", normalItemList);
		List<BoardItem> knowHowItemList = getBoardListByMainPortlet("0");
		mav.addObject("knowHowItemList", knowHowItemList);
		List<BoardItem> originItemList = getBoardListByMainPortlet("3");
		mav.addObject("originItemList", originItemList);
		mav.addObject("isSystemAdmin", this.isSystemAdmin(user.getUserId()));

		return mav;
	}
	
	@RequestMapping(value = "/portlet/normalKeyInfoView.do")
	public ModelAndView normalKeyInfoView(@RequestParam String portletConfigId, @RequestParam String portletId) {
		ModelAndView mav = new ModelAndView("collpack/kms/main/portlet/normalKeyInfoView");
		mav.addObject("portletConfigId", portletConfigId);		
		List<BoardItem> normalItemList = getKeyInfoListByMainPortlet();
		mav.addObject("normalItemList", normalItemList);

		return mav;
	}
	
	@RequestMapping(value = "/portlet/normalSearchView.do")
	public ModelAndView normalSearchView(@RequestParam String portletConfigId, @RequestParam String portletId) {
		ModelAndView mav = new ModelAndView("collpack/kms/main/portlet/normalSearchView");
		mav.addObject("portletConfigId", portletConfigId);		

		return mav;
	}

	
	@RequestMapping(value = "/portlet/configView.do")
	public ModelAndView configView() {

		User user = readUser();

		UserConfig userConfig = userConfigService.readUserConfigByUserIdAndModuleId(user.getUserId(), USER_CONFIG_MODULE_ID, user.getPortalId());
		ModelAndView mav =  new ModelAndView("collpack/kms/main/portlet/configView");
		mav.addObject("count", userConfig == null ? PAGE_PER_RECORD : userConfig.getPageCount());

		return mav;
	}
	
	@RequestMapping(value = "/portlet/configKeyInfoView.do")
	public ModelAndView configKeyInfoView() {

		User user = readUser();

		UserConfig userConfig = userConfigService.readUserConfigByUserIdAndModuleId(user.getUserId(), USER_CONFIG_KEYINFO_MODULE_ID, user.getPortalId());
		ModelAndView mav =  new ModelAndView("collpack/kms/main/portlet/configKeyInfoView");
		mav.addObject("count", userConfig == null ? PAGE_PER_RECORD : userConfig.getPageCount());

		return mav;
	}
	
	
	@RequestMapping(value = "/portlet/updatePageCount.do")
	public @ResponseBody void config(@RequestParam("count") Integer count) {
		try {
			User user = readUser();
			UserConfig userConfig = this.userConfigService.readUserConfigByUserIdAndModuleId(user.getUserId(), USER_CONFIG_MODULE_ID, user.getPortalId());


			if(userConfig == null) {
				userConfig = new UserConfig();

				userConfig.setPortalId(user.getPortalId());
				userConfig.setUserId(user.getUserId());
				userConfig.setPageCount((count != null && count > 0) ? count:PAGE_PER_RECORD);
				userConfig.setModuleId(USER_CONFIG_MODULE_ID);

				this.userConfigService.createUserConfig(userConfig);

			} else {
				userConfig.setPageCount(count);
				this.userConfigService.updateUserConfig(userConfig);
			}
		} catch(Exception exception) {
			throw new IKEP4AjaxException("code", exception);
		}

	}
	
	
	private List<BoardItem> getBoardListByMainPortlet(String isKnowHow) {
		User user = readUser();
		
		BoardItemSearchCondition searchCondition = new BoardItemSearchCondition();
		UserConfig userConfig = userConfigService.readUserConfigByUserIdAndModuleId(user.getUserId(), USER_CONFIG_MODULE_ID, user.getPortalId());
		searchCondition.setPagePerRecord(userConfig == null ? PAGE_PER_RECORD : userConfig.getPageCount());	
		searchCondition.setIsKnowhow(isKnowHow);		

		//사용자 정보등급세팅
		searchCondition.setInfoGrage(user.getInfoGrade());
		searchCondition.setViewMode("0");
		searchCondition.setAdmin(this.isSystemAdmin(user.getUserId()));
		SearchResult<BoardItem> searchResult = boardItemService.latestListBySearchCondition(searchCondition);
		List<BoardItem> boardItemList = new ArrayList<BoardItem>();
		
		if(!searchResult.isEmptyRecord()){
			boardItemList = searchResult.getEntity();
		}
		return boardItemList;
	}
	
	private List<BoardItem> getKeyInfoListByMainPortlet() {
		User user = readUser();
		
		BoardItemSearchCondition searchCondition = new BoardItemSearchCondition();
		UserConfig userConfig = userConfigService.readUserConfigByUserIdAndModuleId(user.getUserId(), USER_CONFIG_KEYINFO_MODULE_ID, user.getPortalId());
		searchCondition.setPagePerRecord(userConfig == null ? PAGE_PER_RECORD : userConfig.getPageCount());	

		//사용자 정보등급세팅
		searchCondition.setInfoGrage(user.getInfoGrade());
		searchCondition.setViewMode("0");
		searchCondition.setAdmin(this.isSystemAdmin(user.getUserId()));
		SearchResult<BoardItem> searchResult = boardItemService.keyInfoListBySearchCondition(searchCondition);
		List<BoardItem> boardItemList = new ArrayList<BoardItem>();
		
		if(!searchResult.isEmptyRecord()){
			boardItemList = searchResult.getEntity();
		}
		return boardItemList;
	}
	
	

	/**
	 * 세션 사용자 정보
	 * 
	 * @return
	 */
	private User readUser() {
		return (User) getRequestAttribute("ikep.user");
	}
	  /***
	   * 현재 년도 가져오기
	   * @return String
	   */
	  public String getNowYear() {
		  Calendar calendar = Calendar.getInstance(); 
		  return Integer.toString(calendar.get ( Calendar.YEAR ));
	  }
	  /***
	   * 현재 년도 가져오기
	   * @return String
	   */
	  public String getNowYear1() {
		  Calendar calendar = Calendar.getInstance(); 
		  return Integer.toString(calendar.get ( Calendar.YEAR )-1);
	  }
	  /***
	   * 현재 월 가져오기
	   * @return String
	   */
	  public String getNowMonth() {
		  Calendar calendar = Calendar.getInstance(); 
		  int nMonth = calendar.get ( Calendar.MONTH ) + 1;
		  if ( nMonth < 10 ) 
			  return "0" + Integer.toString(nMonth);
		  else 
			  return Integer.toString(nMonth);
	  }
	  /***
	   * 현재 일 가져오기
	   * @return String
	   */
	  public String getNowDay() {
		  Calendar calendar = Calendar.getInstance(); 
		  int nDay = calendar.get ( Calendar.DAY_OF_MONTH );
		  if ( nDay < 10 ) 
			  return "0" + Integer.toString(nDay);
		  else 
			  return Integer.toString(nDay);
	  }
}
