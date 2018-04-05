package com.lgcns.ikep4.collpack.collaboration.board.weekly.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.validation.Valid;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.collpack.collaboration.board.weekly.model.WeeklyCode;
import com.lgcns.ikep4.collpack.collaboration.board.weekly.model.WeeklyItem;
import com.lgcns.ikep4.collpack.collaboration.board.weekly.search.WeeklyItemSearchCondition;
import com.lgcns.ikep4.collpack.collaboration.board.weekly.service.WeeklyItemService;
import com.lgcns.ikep4.collpack.collaboration.member.model.Member;
import com.lgcns.ikep4.collpack.collaboration.member.service.MemberService;
import com.lgcns.ikep4.collpack.collaboration.workspace.model.Workspace;
import com.lgcns.ikep4.collpack.collaboration.workspace.service.WorkspaceService;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxValidationException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AuthorizedException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.mail.MailConstant;
import com.lgcns.ikep4.support.mail.model.Mail;
import com.lgcns.ikep4.support.mail.service.MailSendService;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.sms.model.Sms;
import com.lgcns.ikep4.support.sms.service.SmsService;
import com.lgcns.ikep4.support.user.member.dao.UserDao;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.service.UserService;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.lang.DateUtil;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.util.model.ModelBeanUtil;


@Controller
@RequestMapping(value = "/collpack/collaboration/board/weekly")
@SessionAttributes("WeeklyItem")
public class WeeklyItemController extends BaseController {

	private static final String COLLABORATION_MANAGER = "Collaboration";

	@Autowired
	private WeeklyItemService weeklyItemService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private ACLService aclService;

	@Autowired
	private SmsService smsService;

	@Autowired
	private UserService userService;

	@Autowired
	private MailSendService mailSendService;

	@Autowired
	private WorkspaceService workspaceService;
	
	@Autowired
    private UserDao userDao;

	/**
	 * Workspace 시스템 관리자 여부
	 * 
	 * @param user
	 * @return
	 */
	public boolean isSystemAdmin(User user) {
		return aclService.isSystemAdmin(COLLABORATION_MANAGER, user.getUserId());
	}

	/**
	 * 세션 사용자 정보
	 * 
	 * @return
	 */
	private User readUser() {
		return (User) getRequestAttribute("ikep.user");
	}

	/**
	 * 주간보고 게시판 목록 View
	 * 
	 * @param workspaceId
	 * @param searchCondition
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/listWeeklyItemView")
	public ModelAndView listWeeklyItemView(@RequestParam(value = "workspaceId") String workspaceId,
			WeeklyItemSearchCondition searchCondition, BindingResult result, SessionStatus status) {

		if (searchCondition.getWorkspaceId() == null) {
			searchCondition.setWorkspaceId(workspaceId);
		}

		if (searchCondition.getPagePerRecord() == null) {
			searchCondition.setPagePerRecord(10);
		}

		if (searchCondition.getWeeklyTerm() == null) {
			searchCondition.setWeeklyTerm(makeDefaultWeeklyTermString());
		}

		String viewName = null;
		SearchResult<WeeklyItem> searchResult = null;

		searchResult = this.weeklyItemService.listWeeklyItemBySearchCondition(searchCondition);

		User user = readUser();
		ModelAndView modelAndView = new ModelAndView(viewName).addObject("weeklyCode", new WeeklyCode())
				.addObject("searchCondition", searchResult.getSearchCondition()).addObject("workspaceId", workspaceId)
				.addObject("weeklyPermission", getWeeklyPermission(user, workspaceId))
				.addObject("searchResult", searchResult);

		return modelAndView;
	}

	/**
	 * 주간 보고 검색
	 * 
	 * @param workspaceId
	 * @param weeklyTerm
	 * @param dayFlag
	 * @return
	 */
	@RequestMapping(value = "/searchTermWeeklyItemView")
	public ModelAndView searchTermListWeeklyItemView(@RequestParam(value = "workspaceId") String workspaceId,
			@RequestParam(value = "weeklyTerm") String weeklyTerm, String dayFlag) {

		String searchWeeklyTerm = null;
		if (!StringUtil.isEmpty(weeklyTerm) && !StringUtil.isEmpty(dayFlag)) {
			String[] standardString = weeklyTerm.split("~");
			String startDateString = standardString[0].trim().replace(".", "");

			if (dayFlag.equals("prev") || "prev".equals(dayFlag)) {

				String prevStartDateString = DateUtil.getPrevWeekDate(startDateString, 1, "yyyyMMdd");
				String prevEndDateString = DateUtil.getRelativeDateString(DateUtil.toDate(prevStartDateString), 0, 0,
						6, "yyyyMMdd");

				prevStartDateString = DateUtil.getFmtDateString(prevStartDateString, "yyyy.MM.dd");
				prevEndDateString = DateUtil.getFmtDateString(prevEndDateString, "yyyy.MM.dd");

				searchWeeklyTerm = prevStartDateString + " ~ " + prevEndDateString;
			} else if (dayFlag.equals("next") || "next".equals(dayFlag)) {

				String nextStartDateString = DateUtil.getNextWeekDate(startDateString, 1, "yyyyMMdd");
				String nextEndDateString = DateUtil.getRelativeDateString(DateUtil.toDate(nextStartDateString), 0, 0,
						6, "yyyyMMdd");

				nextStartDateString = DateUtil.getFmtDateString(nextStartDateString, "yyyy.MM.dd");
				nextEndDateString = DateUtil.getFmtDateString(nextEndDateString, "yyyy.MM.dd");

				searchWeeklyTerm = nextStartDateString + " ~ " + nextEndDateString;
			}
		}
		return new ModelAndView("redirect:/collpack/collaboration/board/weekly/listWeeklyItemView.do").addObject(
				"weeklyTerm", searchWeeklyTerm).addObject("workspaceId", workspaceId);
	}

	/**
	 * 하위 Coll. 취합 주간보고 게시물 리스트 화면
	 * 
	 * @param workspaceId
	 * @param searchCondition
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/listLowRankWeeklyItemView")
	public ModelAndView listLowRankWeeklyItemView(@RequestParam(value = "workspaceId") String workspaceId,
			WeeklyItemSearchCondition searchCondition, BindingResult result, SessionStatus status) {
		String viewName = null;
		SearchResult<WeeklyItem> searchResult = null;
		User user = readUser();
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		String portalId = portal.getPortalId();
		String teamId = this.weeklyItemService.getWorkspaceInfo(portalId, workspaceId).getTeamId();

		if (searchCondition.getWorkspaceId() == null) {
			searchCondition.setWorkspaceId(workspaceId);
		}

		if (searchCondition.getPagePerRecord() == null) {
			searchCondition.setPagePerRecord(10);
		}

		if (searchCondition.getTeamId() == null) {
			searchCondition.setTeamId(teamId);
		}

		if (searchCondition.getWeeklyTerm() == null) {
			searchCondition.setWeeklyTerm(makeDefaultWeeklyTermString());
		}

		searchResult = this.weeklyItemService.listLowRankWeeklyItemBySearchCondition(searchCondition);

		ModelAndView modelAndView = new ModelAndView(viewName).addObject("weeklyCode", new WeeklyCode())
				.addObject("searchCondition", searchResult.getSearchCondition()).addObject("workspaceId", workspaceId)
				.addObject("weeklyPermission", getWeeklyPermission(user, workspaceId))
				.addObject("searchResult", searchResult);

		return modelAndView;
	}

	/**
	 * 주간보고 상세조회 서브 리스트
	 * 
	 * @param workspaceId
	 * @param searchCondition
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/subListWeeklyItemView")
	public ModelAndView subListWeeklyItemView(@RequestParam(value = "workspaceId") String workspaceId,
			WeeklyItemSearchCondition searchCondition, BindingResult result, SessionStatus status) {

		String viewName = null;
		SearchResult<WeeklyItem> searchResult = null;

		if (searchCondition.getWorkspaceId() == null) {
			searchCondition.setWorkspaceId(workspaceId);
		}

		if (searchCondition.getPagePerRecord() == null) {
			searchCondition.setPagePerRecord(10);
		}

		if (searchCondition.getWeeklyTerm() == null) {
			searchCondition.setWeeklyTerm(makeDefaultWeeklyTermString());
		}

		searchResult = this.weeklyItemService.listWeeklyItemBySearchCondition(searchCondition);

		User user = readUser();
		ModelAndView modelAndView = new ModelAndView(viewName).addObject("weeklyCode", new WeeklyCode())
				.addObject("searchCondition", searchResult.getSearchCondition()).addObject("workspaceId", workspaceId)
				.addObject("weeklyPermission", getWeeklyPermission(user, workspaceId))
				.addObject("searchResult", searchResult);

		return modelAndView;
	}

	/**
	 * 주간보고 등록 화면
	 * 
	 * @param workspaceId
	 * @return
	 */
	@RequestMapping(value = "/createWeeklyItemView")
	public ModelAndView createWeeklyItemView(@RequestParam(value = "workspaceId") String workspaceId,
			@RequestParam(value = "isSummary") Integer isSummary, @RequestParam(value = "weeklyTerm") String weeklyTerm) {
		User user = readUser();

		WeeklyItem weeklyItem = new WeeklyItem();

		weeklyItem.setWorkspaceId(workspaceId);
		weeklyItem.setIsSummary(isSummary);
		weeklyItem.setWeeklyTerm(weeklyTerm);

		if (isSummary == 1) {
			List<WeeklyItem> contentsInfo = new ArrayList<WeeklyItem>();
			contentsInfo = weeklyItemService.readWeeklyItemContents(weeklyItem);
			if (contentsInfo != null) {
				String tempContents = makeHtmlTempContents(contentsInfo);
				weeklyItem.setContents(tempContents);
			}
		}

		// Title Make
		String titleString = "";
		titleString = makeTitleString(weeklyItem);
		weeklyItem.setTitle(titleString);

		// ActiveX editor 사용여부 확인
		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		String useActiveX = "N";
		useActiveX = commonprop.getProperty("ikep4.activex.editor.use");
		
		boolean ecmrole = false;
		Map<String, String> emap = new HashMap<String, String>();
		emap.put("userId", user.getUserId());
		emap.put("roleName", "ECM");
		int ecmRole = userDao.getUserRoleCheck(emap);
		if(ecmRole > 0){
			ecmrole = true;
		}

		ModelAndView modelAndView = new ModelAndView().addObject("user", user).addObject("weeklyItem", weeklyItem)
				.addObject("workspaceId", workspaceId).addObject("useActiveX", useActiveX).addObject("ecmrole", ecmrole);

		return modelAndView;
	}

	/**
	 * 주간보고 등록
	 * 
	 * @param weeklyItem
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/createWeeklyItem")
	public ModelAndView createWeeklyItem(@Valid WeeklyItem weeklyItem, WeeklyItemSearchCondition searchCondition,
			BindingResult result, SessionStatus status) {

		// 권한체크 0:비회원,1:회원,2:운영진,3:시스템/Coll관리자
		// 권한 없을때 Forwarding 정책필요
		// int announcePermission = getAnnouncePermission(user, workspaceId);
		if (result.hasErrors()) {
			// 에러가 존재할 경우, IKEP4AjaxValidationException을 발생 시킨다.
			throw new IKEP4AjaxValidationException(result, messageSource); // BindingResult와
																			// BaseController의
																			// MessageSource를
																			// parameter로
																			// 전달해야
																			// 합니다.
		}

		User user = readUser();

		ModelBeanUtil.bindRegisterInfo(weeklyItem, user.getUserId(), user.getUserName());

		String itemId = this.weeklyItemService.createWeeklyItem(weeklyItem, user);

		// WeeklyItemSearchCondition searchCondition =
		// (WeeklyItemSearchCondition)
		// this.getRequestAttribute("searchCondition");

		status.setComplete();

		// this.setRequestAttribute("searchCondition", searchCondition);

		return new ModelAndView("redirect:/collpack/collaboration/board/weekly/readWeeklyItemView.do")
				.addObject("itemId", itemId).addObject("searchCondition", searchCondition)
				.addObject("workspaceId", weeklyItem.getWorkspaceId());
	}

	/**
	 * 주간보고 게시물 읽기화면
	 * 
	 * @param itemId
	 * @param workspaceId
	 * @return
	 */
	@RequestMapping(value = "/readWeeklyItemView")
	public ModelAndView readWeeklyItemView(@RequestParam("itemId") String itemId,
			@RequestParam(value = "docPopup", defaultValue = "false") Boolean docPopup,
			@RequestParam(value = "workspaceId", required = false) String workspaceId,
			WeeklyItemSearchCondition searchCondition) throws JsonGenerationException, JsonMappingException,
			IOException {

		User user = readUser();
		Boolean popup = false;
		if (StringUtil.isEmpty(workspaceId)) {
			popup = true;
		} else {
			popup = docPopup;
		}

		

		// 게시글 정보를 가져온다. 파일정보와 관련 글 정보가 포함되어 있다.
		WeeklyItem weeklyItem = this.weeklyItemService.readWeeklyItem(user.getUserId(), itemId, workspaceId);

		if (weeklyItem == null) {
			throw new IKEP4AuthorizedException(messageSource, "message.collpack.collaboration.common.boardItem.deletedItem");
		}
		
		workspaceId = weeklyItem.getWorkspaceId();
		int weeklyPermission = 0;
		weeklyPermission = getWeeklyPermission(user, workspaceId);

		if (weeklyPermission == 0) {
			throw new IKEP4AuthorizedException();
		}

		String viewName = null;
		if (popup) {
			viewName = "collpack/collaboration/board/weekly/readWeeklyItemPopupView";
		}
		SearchResult<WeeklyItem> searchResult = null;

		if (weeklyItem.getIsSummary() == 1) {

			if (searchCondition.getWorkspaceId() == null) {
				searchCondition.setWorkspaceId(workspaceId);
			}

			if (searchCondition.getPagePerRecord() == null) {
				searchCondition.setPagePerRecord(10);
			}

			searchCondition.setWeeklyTerm(weeklyItem.getWeeklyTerm());

			searchResult = this.weeklyItemService.listWeeklyItemBySearchCondition(searchCondition);

		} else {
			searchResult = new SearchResult<WeeklyItem>(searchCondition);
		}

		ObjectMapper mapper = new ObjectMapper();

		// 파일 목록을 JSON으로 변환한다.
		String fileDataListJson = null;

		if (weeklyItem.getFileDataList() != null) {
			fileDataListJson = mapper.writeValueAsString(weeklyItem.getFileDataList());
		}
		
		boolean ecmrole = false;
		Map<String, String> emap = new HashMap<String, String>();
		emap.put("userId", user.getUserId());
		emap.put("roleName", "ECM");
		int ecmRole = userDao.getUserRoleCheck(emap);
		if(ecmRole > 0){
			ecmrole = true;
		}

		ModelAndView modelAndView = new ModelAndView(viewName).addObject("weeklyItem", weeklyItem)
				.addObject("docPopup", popup).addObject("weeklyPermission", weeklyPermission).addObject("user", user)
				.addObject("weeklyCode", new WeeklyCode())
				.addObject("searchCondition", searchResult.getSearchCondition()).addObject("workspaceId", workspaceId)
				.addObject("searchResult", searchResult).addObject("fileDataListJson", fileDataListJson).addObject("ecmrole", ecmrole);

		return modelAndView;
	}

	@RequestMapping(value = "/readWeeklyItemPopupView")
	public ModelAndView readWeeklyItemPopupView(@RequestParam("itemId") String itemId,
			@RequestParam(value = "docPopup", defaultValue = "false") Boolean docPopup,
			@RequestParam(value = "workspaceId", required = false) String workspaceId,
			WeeklyItemSearchCondition searchCondition) throws JsonGenerationException, JsonMappingException,
			IOException {

		User user = readUser();
		Boolean popup = false;
		if (StringUtil.isEmpty(workspaceId)) {
			popup = true;
		} else {
			popup = docPopup;
		}

		int weeklyPermission = 0;
		weeklyPermission = getWeeklyPermission(user, workspaceId);

		if (weeklyPermission == 0) {
			throw new IKEP4AuthorizedException();
		}

		// 게시글 정보를 가져온다. 파일정보와 관련 글 정보가 포함되어 있다.
		WeeklyItem weeklyItem = this.weeklyItemService.readWeeklyItem(user.getUserId(), itemId, workspaceId);

		if (weeklyItem == null) {
			throw new IKEP4AuthorizedException(messageSource, "message.collpack.collaboration.common.boardItem.deletedItem");
		}

		String viewName = null;
		if (popup) {
			viewName = "collpack/collaboration/board/weekly/readWeeklyItemPopupView";
		}
		SearchResult<WeeklyItem> searchResult = null;

		if (weeklyItem.getIsSummary() == 1) {

			if (searchCondition.getWorkspaceId() == null) {
				searchCondition.setWorkspaceId(workspaceId);
			}

			if (searchCondition.getPagePerRecord() == null) {
				searchCondition.setPagePerRecord(10);
			}

			searchCondition.setWeeklyTerm(weeklyItem.getWeeklyTerm());

			searchResult = this.weeklyItemService.listWeeklyItemBySearchCondition(searchCondition);

		} else {
			searchResult = new SearchResult<WeeklyItem>(searchCondition);
		}

		ObjectMapper mapper = new ObjectMapper();

		// 파일 목록을 JSON으로 변환한다.
		String fileDataListJson = null;

		if (weeklyItem.getFileDataList() != null) {
			fileDataListJson = mapper.writeValueAsString(weeklyItem.getFileDataList());
		}

		ModelAndView modelAndView = new ModelAndView(viewName).addObject("weeklyItem", weeklyItem)
				.addObject("docPopup", popup).addObject("weeklyPermission", weeklyPermission).addObject("user", user)
				.addObject("weeklyCode", new WeeklyCode())
				.addObject("searchCondition", searchResult.getSearchCondition()).addObject("workspaceId", workspaceId)
				.addObject("searchResult", searchResult).addObject("fileDataListJson", fileDataListJson);

		return modelAndView;
	}

	/**
	 * 하위 Coll 주간보고 취합본 리스트 화면
	 * 
	 * @param itemId
	 * @param workspaceId
	 * @param itemWorkspaceId
	 * @return
	 */
	@RequestMapping(value = "/readLowRankWeeklyItemView")
	public ModelAndView readLowRankWeeklyItemView(@RequestParam("itemId") String itemId,
			@RequestParam("workspaceId") String workspaceId, @RequestParam("itemWorkspaceId") String itemWorkspaceId) {

		User user = readUser();

		String viewName = null;
		// 게시글 정보를 가져온다. 파일정보와 관련 글 정보가 포함되어 있다.
		WeeklyItem weeklyItem = this.weeklyItemService.readWeeklyItem(user.getUserId(), itemId, itemWorkspaceId);

		ModelAndView modelAndView = new ModelAndView(viewName).addObject("weeklyItem", weeklyItem)
				.addObject("workspaceId", workspaceId)
				.addObject("weeklyPermission", getWeeklyPermission(user, workspaceId)).addObject("user", user);

		return modelAndView;
	}

	/**
	 * 주간보고 수정 화면
	 * 
	 * @param itemId
	 * @param workspaceId
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping(value = "/updateWeeklyItemView")
	public ModelAndView updateWeeklyItemView(@RequestParam("itemId") String itemId,
			@RequestParam("workspaceId") String workspaceId) throws JsonGenerationException, JsonMappingException,
			IOException {

		// 권한체크 0:비회원,1:회원,2:운영진,3:시스템/Coll관리자
		// 권한 없을때 Forwarding 정책필요
		// int announcePermission = getAnnouncePermission(user, workspaceId);
		User user = readUser();

		WeeklyItem weeklyItem = this.weeklyItemService.readWeeklyItem(user.getUserId(), itemId, workspaceId);

		ObjectMapper mapper = new ObjectMapper();

		String fileDataListJson = mapper.writeValueAsString(weeklyItem.getFileDataList());

		// ActiveX editor 사용여부 확인
		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		String useActiveX = "N";
		useActiveX = commonprop.getProperty("ikep4.activex.editor.use");

		boolean ecmrole = false;
		Map<String, String> emap = new HashMap<String, String>();
		emap.put("userId", user.getUserId());
		emap.put("roleName", "ECM");
		int ecmRole = userDao.getUserRoleCheck(emap);
		if(ecmRole > 0){
			ecmrole = true;
		}

		return new ModelAndView().addObject("weeklyItem", weeklyItem).addObject("fileDataListJson", fileDataListJson)
				.addObject("useActiveX", useActiveX).addObject("ecmrole", ecmrole);
	}

	/**
	 * 주간보고 수정
	 * 
	 * @param weeklyItem
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/updateWeeklyItem")
	public ModelAndView updateWeeklyItem(@Valid WeeklyItem weeklyItem, BindingResult result, SessionStatus status) {

		// 권한체크 0:비회원,1:회원,2:운영진,3:시스템/Coll관리자
		// 권한 없을때 Forwarding 정책필요
		// int announcePermission = getAnnouncePermission(user, workspaceId);

		if (result.hasErrors()) {
			// 에러가 존재할 경우, IKEP4AjaxValidationException을 발생 시킨다.
			throw new IKEP4AjaxValidationException(result, messageSource); // BindingResult와
																			// BaseController의
																			// MessageSource를
																			// parameter로
																			// 전달해야
																			// 합니다.
		}

		User user = (User) getRequestAttribute("ikep.user");

		ModelBeanUtil.bindRegisterInfo(weeklyItem, user.getUserId(), user.getUserName());

		this.weeklyItemService.updateWeeklyItem(weeklyItem, user);

		status.setComplete();

		return new ModelAndView("redirect:/collpack/collaboration/board/weekly/readWeeklyItemView.do")
				.addObject("itemId", weeklyItem.getItemId()).addObject("weeklyTerm", weeklyItem.getWeeklyTerm())
				.addObject("workspaceId", weeklyItem.getWorkspaceId());
	}

	/**
	 * 주간보고 게시물 삭제
	 * 
	 * @param itemId
	 * @param workspaceId
	 * @return
	 */
	@RequestMapping(value = "/deleteWeeklyItem")
	public ModelAndView adminDeleteAnnounceItem(@RequestParam("itemId") String itemId,
			@RequestParam("workspaceId") String workspaceId) {
		// 권한체크 0:비회원,1:회원,2:운영진,3:시스템/Coll관리자
		// 권한 없을때 Forwarding 정책필요
		// int announcePermission = getAnnouncePermission(user, workspaceId);
		User user = readUser();
		WeeklyItem weeklyItem = weeklyItemService.readWeeklyItem(user.getUserId(), itemId, workspaceId);

		this.weeklyItemService.deleteWeeklyItem(weeklyItem);

		return new ModelAndView("redirect:/collpack/collaboration/board/weekly/listWeeklyItemView.do?workspaceId="
				+ weeklyItem.getWorkspaceId() + "&weeklyItem=" + weeklyItem.getWeeklyTerm());
	}

	/**
	 * 해당 기간의 주간 보고 존재 유무 확인
	 * 
	 * @param workspaceName
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/checkWeeklyTerm.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	String checkWorkspaceName(@RequestParam("workspaceId") String workspaceId,
			@RequestParam("weeklyTerm") String weeklyTerm) {

		String itemId = "";
		User user = readUser();
		try {
			Map<String, String> map = new HashMap<String, String>();

			map.put("workspaceId", workspaceId);
			map.put("weeklyTerm", weeklyTerm);
			map.put("registerId", user.getUserId());

			itemId = weeklyItemService.checkWeeklyTerm(map);

			if (StringUtil.isEmpty(itemId)) {
				itemId = "empty";
			}

		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}
		return itemId;
	}

	/**
	 * 해당 itemId 의 중복 주간 보고 삭제
	 * 
	 * @param workspaceName
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/removeDuplicateItem.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	String removeDuplicateItem(@RequestParam("workspaceId") String workspaceId, @RequestParam("itemId") String itemId) {

		User user = readUser();
		try {

			WeeklyItem weeklyItem = weeklyItemService.readWeeklyItem(user.getUserId(), itemId, workspaceId);

			this.weeklyItemService.deleteWeeklyItem(weeklyItem);

		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}
		return itemId;
	}

	/**
	 * Summary TempContents
	 * 
	 * @param contentsInfo
	 * @return
	 */
	public String makeHtmlTempContents(List<WeeklyItem> contentsInfo) {
		StringBuffer summaryContents = new StringBuffer();

		User user = (User) getRequestAttribute("ikep.user");
		Portal portal = (Portal) getRequestAttribute("ikep.portal");

		if (!user.getLocaleCode().equals(portal.getDefaultLocaleCode())) {
			for (WeeklyItem weeklyItem : contentsInfo) {
				summaryContents.append("<hr />");
				summaryContents.append("<br/>Author : " + weeklyItem.getRegisterEnglishName() + " "
						+ weeklyItem.getJobTitleEnglishName() + " | "
						+ DateUtil.getFmtDateString(weeklyItem.getRegistDate(), "yyyy-MM-dd hh:ss"));
				summaryContents.append("<br/>" + weeklyItem.getContents() + "<br/>");
			}
		} else {
			for (WeeklyItem weeklyItem : contentsInfo) {
				summaryContents.append("<hr />");
				summaryContents.append("<br/>작성자 : " + weeklyItem.getRegisterName() + " "
						+ weeklyItem.getJobTitleName() + " | "
						+ DateUtil.getFmtDateString(weeklyItem.getRegistDate(), "yyyy-MM-dd hh:ss"));
				summaryContents.append("<br/>" + weeklyItem.getContents() + "<br/>");
			}
		}

		return summaryContents.toString();
	}

	/**
	 * Default TitleString
	 * 
	 * @param contentsInfo
	 * @return
	 */
	public String makeTitleString(WeeklyItem weeklyItem) {
		StringBuffer titleString = new StringBuffer();

		User user = (User) getRequestAttribute("ikep.user");
		Portal portal = (Portal) getRequestAttribute("ikep.portal");

		String[] standardString = weeklyItem.getWeeklyTerm().split("~");
		String startDateString = standardString[0].trim().replace(".", "");

		Calendar cal; // 클래스생성
		int year, month, dayOfMonth, totalWeeks;

		cal = Calendar.getInstance();
		cal.setTime(DateUtil.toDate(startDateString));

		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH) + 1;
		dayOfMonth = cal.get(Calendar.WEEK_OF_MONTH);
		totalWeeks = DateUtil.getWeekCount(year, month);
		// GET WorkspaceInfo
		Workspace workspace = new Workspace();
		workspace = workspaceService.getWorkspace(weeklyItem.getWorkspaceId());

		String isSummaryString = "";
		if (weeklyItem.getIsSummary() == 1) {
			if (!user.getLocaleCode().equals(portal.getDefaultLocaleCode())) {
				isSummaryString = "[TakeItem]";
			} else {
				isSummaryString = "[취합본]";
			}
		}

		// 정확한 로케일 적용문구 필요
		// 취합본 경우 별도 표시 필요한지.
		if (!user.getLocaleCode().equals(portal.getDefaultLocaleCode())) {
			// titleString.append(isSummaryString + workspace.getWorkspaceName()
			// + " Workspace " + year + "년 " + month + "월 " + dayOfMonth + "/" +
			// totalWeeks + " Weekly Item");
			titleString.append(isSummaryString + workspace.getWorkspaceName() + " Workspace " + year + "년 " + month
					+ "월 " + dayOfMonth + " Weekly Item");
		} else {
			// titleString.append(isSummaryString + workspace.getWorkspaceName()
			// + " 워크스페이스 " + year + "년 " + month + "월 " + dayOfMonth + "/" +
			// totalWeeks + " 주차 주간보고서");
			titleString.append(isSummaryString + workspace.getWorkspaceName() + " 워크스페이스 " + year + "년 " + month + "월 "
					+ dayOfMonth + " 주차 주간보고서");
		}

		return titleString.toString();
	}

	/**
	 * Default WeeklyTermString
	 * 
	 * @return
	 */
	public String makeDefaultWeeklyTermString() {
		String weeklyTermString = "";
		int weekDay = 2;
		int relativeDate = 6;

		String nowDate = DateUtil.getToday("yyyyMMdd");
		String startDate = DateUtil.getWeekDay(nowDate, weekDay);
		String endDate = DateUtil.getRelativeDateString(DateUtil.toDate(startDate), 0, 0, relativeDate, "yyyyMMdd");

		startDate = DateUtil.getFmtDateString(startDate, "yyyy.MM.dd");
		endDate = DateUtil.getFmtDateString(endDate, "yyyy.MM.dd");

		weeklyTermString = startDate + " ~ " + endDate;

		return weeklyTermString;
	}

	/**
	 * 해당 사용자 의 Workspace 에서 권한 정보 확인
	 * 
	 * @param user
	 * @param workspaceId
	 * @return
	 */
	public int getWeeklyPermission(User user, String workspaceId) {
		boolean isSystemAdmin = isSystemAdmin(user);

		int weeklyPermission = 0;
		if (isSystemAdmin) {
			weeklyPermission = 3;
		} else {
			Member member = new Member();
			member.setMemberId(user.getUserId());
			member.setWorkspaceId(workspaceId);
			member = memberService.read(member);

			if (member != null) {
				if ("1".equals(member.getMemberLevel()) || "2".equals(member.getMemberLevel())) {
					weeklyPermission = 2; // 시샵/운영진
				} else if ("3".equals(member.getMemberLevel())) {
					weeklyPermission = 1; // 정회원
				} else {
					weeklyPermission = 0; // 준회원/미회원
				}
			}
		}

		return weeklyPermission;
	}

	/**
	 * 주간보고 게시물 작성 SMS 발송
	 * 
	 * @param sms
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/createSms.do", method = RequestMethod.GET)
	public @ResponseBody
	void createSms(@RequestParam("urgeIds") List<String> urgeIds, @RequestParam("workspaceId") String workspaceId,
			@RequestParam("weeklyTerm") String weeklyTerm, SessionStatus status) {
		User user = (User) getRequestAttribute("ikep.user");
		Sms sms = new Sms();

		sms.setRegisterId(user.getUserId());
		sms.setRegisterName(user.getUserName());

		List<String> tempReceiverIds = new ArrayList<String>();
		List<String> tempReceiverPhonenos = new ArrayList<String>();

		for (int i = 0; i < urgeIds.size(); i++) {
			User userInfo = new User();
			userInfo = userService.read(urgeIds.get(i));
			if (userInfo.getMobile() != null && !StringUtil.isEmpty(userInfo.getMobile())) {
				tempReceiverIds.add(i, urgeIds.get(i));
				tempReceiverPhonenos.add(i, userInfo.getMobile());
			}
		}

		String[] receiverIds = new String[tempReceiverIds.size()];
		String[] receiverPhonenos = new String[tempReceiverPhonenos.size()];

		if (receiverIds != null) {
			for (int i = 0; i < receiverIds.length; i++) {
				receiverIds[i] = tempReceiverIds.get(i);
				receiverPhonenos[i] = tempReceiverPhonenos.get(i);
			}
		}
		String workspaceName = "Workspace";
		if (!StringUtil.isEmpty(workspaceId)) {
			Workspace workspace = new Workspace();
			workspace = workspaceService.getWorkspace(workspaceId);
			workspaceName = workspace.getWorkspaceName();
		}

		String[] standardString = weeklyTerm.split("~");
		String startDateString = standardString[0].trim().replace(".", "");

		Calendar cal; // 클래스생성
		int year, month, dayOfMonth, totalWeeks;

		cal = Calendar.getInstance();
		cal.setTime(DateUtil.toDate(startDateString));

		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH) + 1;
		dayOfMonth = cal.get(Calendar.WEEK_OF_MONTH);
		totalWeeks = DateUtil.getWeekCount(year, month);

		String contents = "[" + workspaceName + "] " + month + "월 " + dayOfMonth + "/" + totalWeeks
				+ "주차 주간보고서 작성이 미완료 되었습니다.";

		sms.setContents(contents);
		sms.setReceiverIds(receiverIds);
		sms.setReceiverPhonenos(receiverPhonenos);

		if (contents.length() > 0 && receiverIds != null && receiverPhonenos != null) {
			sms.setContents(contents);
			smsService.create(sms);
		}

		// 세션 상태를 complete하여 이중 전송 방지
		status.setComplete();
	}

	/**
	 * 주간보고 메일발송
	 * 
	 * @param urgeIds
	 * @param status
	 */
	@RequestMapping(value = "/createMail.do", method = RequestMethod.GET)
	public @ResponseBody
	String createMail(@RequestParam("urgeIds") List<String> urgeIds, @RequestParam("workspaceId") String workspaceId,
			@RequestParam("weeklyTerm") String weeklyTerm, SessionStatus status) {

		String workspaceName = "Workspace";
		
		Workspace workspace = new Workspace();
		
		if (!StringUtil.isEmpty(workspaceId)) {

			workspace = workspaceService.getWorkspace(workspaceId);
			workspaceName = workspace.getWorkspaceName();
		}

		String[] standardString = weeklyTerm.split("~");
		String startDateString = standardString[0].trim().replace(".", "");

		Calendar cal; // 클래스생성
		int year, month, dayOfMonth, totalWeeks;

		cal = Calendar.getInstance();
		cal.setTime(DateUtil.toDate(startDateString));

		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH) + 1;
		dayOfMonth = cal.get(Calendar.WEEK_OF_MONTH);
		totalWeeks = DateUtil.getWeekCount(year, month);

		String title = "[" + workspaceName + "] 주간보고 독려메일입니다.";
		// String content = "<html><body><b>" + month + "월 " + dayOfMonth + "/"
		// + totalWeeks +
		// "주차 주간보고 미등록 상태입니다.</b><br/>확인 후 작성 해주시기 바랍니다.</body></html>";
		
		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		String linkUrl = commonprop.getProperty("ikep4.baseUrl")+"/collpack/collaboration/main/Workspace.do?workspaceId="+workspace.getWorkspaceId();
		
		String content = "<html><body><b>" + month + "월 " + dayOfMonth
				+ "주차 주간보고 미등록 상태입니다.</b><br/><br/>확인 후 작성 해주시기 바랍니다.<br/><br/><br/><a href='"+linkUrl+"' target='_blank'>[" + workspaceName + "] 바로가기</a></body></html>";


		Mail mail = new Mail();
		Map dataMap = new HashMap();
		User userInfo = new User();
		List<HashMap> list = new ArrayList<HashMap>();

		for (int i = 0; i < urgeIds.size(); i++) {
			userInfo = userService.read(urgeIds.get(i));
			if (!StringUtil.isEmpty(userInfo.getMail()) && !StringUtil.isEmpty(userInfo.getUserName())) {
				// 수신자
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("email", userInfo.getMail());
				map.put("name", userInfo.getUserName());
				list.add(map);
			}
		}

		mail.setToEmailList(list);

		// 제목
		mail.setTitle(title);
		// 내용
		mail.setContent(content);
		// 메일형식(txt,html,template)
		mail.setMailType(MailConstant.MAIL_TYPE_HTML);

		// 메일보내기(발신자,메일서버정도 등을 공통 셋팅하여 메일을 보냄)
		User user = (User) getRequestAttribute("ikep.user");
		return mailSendService.sendMail(mail, dataMap, user);

	}
}
