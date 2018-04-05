package com.lgcns.ikep4.collpack.collaboration.board.announce.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.lgcns.ikep4.collpack.collaboration.board.announce.model.AnnounceCode;
import com.lgcns.ikep4.collpack.collaboration.board.announce.model.AnnounceItem;
import com.lgcns.ikep4.collpack.collaboration.board.announce.search.AnnounceItemSearchCondition;
import com.lgcns.ikep4.collpack.collaboration.board.announce.service.AnnounceItemService;
import com.lgcns.ikep4.collpack.collaboration.board.board.service.BoardBatchItemService;
import com.lgcns.ikep4.collpack.collaboration.member.model.Member;
import com.lgcns.ikep4.collpack.collaboration.member.search.MemberSearchCondition;
import com.lgcns.ikep4.collpack.collaboration.member.service.MemberService;
import com.lgcns.ikep4.collpack.collaboration.workspace.model.Workspace;
import com.lgcns.ikep4.collpack.collaboration.workspace.service.WorkspaceService;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxValidationException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AuthorizedException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.admin.screen.service.PortalService;
import com.lgcns.ikep4.portal.login.service.LoginUserDetailsService;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.portal.util.BrowserCheck;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.member.dao.UserDao;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.service.UserService;
import com.lgcns.ikep4.support.user.userTheme.model.UserTheme;
import com.lgcns.ikep4.support.user.userTheme.service.UserThemeService;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.util.model.ModelBeanUtil;


@Controller
@RequestMapping(value = "/collpack/collaboration/board/announce")
@SessionAttributes("announceItem")
public class AnnounceItemController extends BaseController {

	private static final String COLLABORATION_MANAGER = "Collaboration";

	@Autowired
	private AnnounceItemService announceItemService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private ACLService aclService;
	
	@Autowired
	private WorkspaceService workspaceService;
	
	@Autowired
	private BoardBatchItemService boardBatchItemService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PortalService portalService;

	@Autowired
	private LoginUserDetailsService loginUserDetailsService;
	
	@Autowired
	private UserThemeService userThemeService;
	
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
	 * ANNOUNCE 게시물 리스트
	 * 
	 * @param workspaceId
	 * @param searchCondition
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/listAnnounceItemView")
	public ModelAndView listAnnounceItemView(String workspaceId, AnnounceItemSearchCondition searchCondition,
			BindingResult result, SessionStatus status) {
		User user = readUser();

		if (searchCondition.getWorkspaceId() == null) {
			searchCondition.setWorkspaceId(workspaceId);
		}
		if (searchCondition.getViewMode() == null) {
			searchCondition.setViewMode("0");
		}
		if (searchCondition.getPagePerRecord() == null) {
			searchCondition.setPagePerRecord(10);
		}

		String viewName = null;
		SearchResult<AnnounceItem> searchResult = null;

		searchResult = this.announceItemService.listAnnounceItemBySearchCondition(searchCondition);

		ModelAndView modelAndView = new ModelAndView(viewName).addObject("announceCode", new AnnounceCode())
				.addObject("user", user).addObject("workspaceId", workspaceId)
				.addObject("announcePermission", getAnnouncePermission(user, workspaceId))
				.addObject("searchCondition", searchResult.getSearchCondition())
				.addObject("searchResult", searchResult);

		return modelAndView;
	}

	/**
	 * ANNOUNCE 게시물 읽기
	 * 
	 * @param itemId
	 * @param layoutType
	 * @param docPopup
	 * @param workspaceId
	 * @return
	 */
	@RequestMapping(value = "/readAnnounceItemView")
	public ModelAndView readAnnounceItemView(@RequestParam("itemId") String itemId,
			@RequestParam(value = "layoutType", defaultValue = "layoutNormal") String layoutType,
			@RequestParam(value = "docPopup", defaultValue = "false") Boolean docPopup,
			@RequestParam(value = "workspaceId", required = false) String workspaceId) throws JsonGenerationException,
			JsonMappingException, IOException {

		User user = readUser();
		String viewName = "";

		Boolean popup = false;

		int announcePermission = 0;
		announcePermission = getAnnouncePermission(user, workspaceId);

		if (announcePermission == 0) {
			throw new IKEP4AuthorizedException();
		}

		// 게시글 정보를 가져온다. 파일정보와 관련 글 정보가 포함되어 있다.
		AnnounceItem announceItem = this.announceItemService.readAnnounceItem(user.getUserId(), itemId, workspaceId);

		if (announceItem == null) {
			throw new IKEP4AuthorizedException(messageSource, "message.collpack.collaboration.common.boardItem.deletedItem");
		}

		if ("layoutHorizental".equals(layoutType)) {
			viewName = "collpack/collaboration/board/announce/readAnnounceItemFrameView";

		} else if ("layoutVertical".equals(layoutType)) {
			viewName = "collpack/collaboration/board/announce/readAnnounceItemFrameView";

		} else if ("layoutNormal".equals(layoutType)) {
			viewName = "collpack/collaboration/board/announce/readAnnounceItemView";
		}

		if (StringUtil.isEmpty(workspaceId)) {
			popup = true;
		} else {
			popup = docPopup;
		}

		if (popup) {
			viewName = "collpack/collaboration/board/announce/readAnnounceItemPopupView";
		}
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		String portalId = portal.getPortalId();

		Workspace workspace = new Workspace();
		workspace = announceItemService.getWorkspaceInfo(portalId, workspaceId);

		int isOrganization = 0;
		if (workspace != null) {
			isOrganization = workspace.getIsOrganization();
		}

		ObjectMapper mapper = new ObjectMapper();

		// 파일 목록을 JSON으로 변환한다.
		String fileDataListJson = null;

		if (announceItem.getFileDataList() != null) {
			fileDataListJson = mapper.writeValueAsString(announceItem.getFileDataList());
		}
		
		boolean ecmrole = false;
		Map<String, String> emap = new HashMap<String, String>();
		emap.put("userId", user.getUserId());
		emap.put("roleName", "ECM");
		int ecmRole = userDao.getUserRoleCheck(emap);
		if(ecmRole > 0){
			ecmrole = true;
		}

		ModelAndView modelAndView = new ModelAndView(viewName).addObject("announceItem", announceItem)
				.addObject("layoutType", layoutType).addObject("docPopup", popup)
				.addObject("isOrganization", isOrganization).addObject("announcePermission", announcePermission)
				.addObject("user", user).addObject("fileDataListJson", fileDataListJson)
				.addObject("ecmrole", ecmrole);

		return modelAndView;
	}
	
	@RequestMapping(value = "/directReadAnnounceItemView")
	public ModelAndView directReadAnnounceItemView(@RequestParam("itemId") String itemId,
			@RequestParam(value = "layoutType", defaultValue = "layoutNormal") String layoutType,
			@RequestParam(value = "docPopup", defaultValue = "false") Boolean docPopup,
			@RequestParam(value = "workspaceId", required = false) String workspaceId,
			@RequestParam(value = "suserId", defaultValue = "false", required = false) String suserId,
			HttpServletRequest request) throws JsonGenerationException,
			JsonMappingException, IOException {

		Portal portal = null;
		
		portal = portalService.readPortalDefault();
		String portalId = portal.getPortalId();
		
		User user =   userService.read(suserId);
		
		HttpSession session = request.getSession();
		
		setRequestAttribute("ikep.portal", portal);
		setRequestAttribute("ikep.portalId", portal.getPortalId());
		setRequestAttribute("ikep.defaultLocaleCode", portal.getDefaultLocaleCode());
		
		
		authenticationSuccess(request, suserId);
		String viewName = "";

		Boolean popup = false;

		int announcePermission = 0;
		announcePermission = getAnnouncePermission(user, workspaceId);

		if (announcePermission == 0) {
			throw new IKEP4AuthorizedException();
		}

		// 게시글 정보를 가져온다. 파일정보와 관련 글 정보가 포함되어 있다.
		AnnounceItem announceItem = this.announceItemService.readAnnounceItem(user.getUserId(), itemId, workspaceId);

		if (announceItem == null) {
			throw new IKEP4AuthorizedException(messageSource, "message.collpack.collaboration.common.boardItem.deletedItem");
		}

		if ("layoutHorizental".equals(layoutType)) {
			viewName = "collpack/collaboration/board/announce/readAnnounceItemFrameView";

		} else if ("layoutVertical".equals(layoutType)) {
			viewName = "collpack/collaboration/board/announce/readAnnounceItemFrameView";

		} else if ("layoutNormal".equals(layoutType)) {
			viewName = "collpack/collaboration/board/announce/readAnnounceItemView";
		}

		if (StringUtil.isEmpty(workspaceId)) {
			popup = true;
		} else {
			popup = docPopup;
		}

		if (popup) {
			viewName = "collpack/collaboration/board/announce/readAnnounceItemPopupView";
		}
		

		Workspace workspace = new Workspace();
		workspace = announceItemService.getWorkspaceInfo(portalId, workspaceId);

		int isOrganization = 0;
		if (workspace != null) {
			isOrganization = workspace.getIsOrganization();
		}

		ObjectMapper mapper = new ObjectMapper();

		// 파일 목록을 JSON으로 변환한다.
		String fileDataListJson = null;

		if (announceItem.getFileDataList() != null) {
			fileDataListJson = mapper.writeValueAsString(announceItem.getFileDataList());
		}

		ModelAndView modelAndView = new ModelAndView(viewName).addObject("announceItem", announceItem)
				.addObject("layoutType", layoutType).addObject("docPopup", popup)
				.addObject("isOrganization", isOrganization).addObject("announcePermission", announcePermission)
				.addObject("user", user).addObject("fileDataListJson", fileDataListJson);

		return modelAndView;
	}

	/**
	 * ANNOUNCE 게시물 읽기
	 * 
	 * @param itemId
	 * @param layoutType
	 * @param docPopup
	 * @param workspaceId
	 * @return
	 */
	@RequestMapping(value = "/readAnnounceItemPopupView")
	public ModelAndView readAnnounceItemPopupView(@RequestParam("itemId") String itemId,
			@RequestParam(value = "layoutType", defaultValue = "layoutNormal") String layoutType,
			@RequestParam(value = "docPopup", defaultValue = "false") Boolean docPopup,
			@RequestParam(value = "workspaceId", required = false) String workspaceId) throws JsonGenerationException,
			JsonMappingException, IOException {

		User user = readUser();
		String viewName = "";

		Boolean popup = false;

		int announcePermission = 0;
		announcePermission = getAnnouncePermission(user, workspaceId);

		if (announcePermission == 0) {
			throw new IKEP4AuthorizedException();
		}

		// 게시글 정보를 가져온다. 파일정보와 관련 글 정보가 포함되어 있다.
		AnnounceItem announceItem = this.announceItemService.readAnnounceItem(user.getUserId(), itemId, workspaceId);

		if (announceItem == null) {
			throw new IKEP4AuthorizedException(messageSource, "message.collpack.collaboration.common.boardItem.deletedItem");
		}

		if ("layoutHorizental".equals(layoutType)) {
			viewName = "collpack/collaboration/board/announce/readAnnounceItemFrameView";

		} else if ("layoutVertical".equals(layoutType)) {
			viewName = "collpack/collaboration/board/announce/readAnnounceItemFrameView";

		} else if ("layoutNormal".equals(layoutType)) {
			viewName = "collpack/collaboration/board/announce/readAnnounceItemView";
		}

		if (StringUtil.isEmpty(workspaceId)) {
			popup = true;
		} else {
			popup = docPopup;
		}

		if (popup) {
			viewName = "collpack/collaboration/board/announce/readAnnounceItemPopupView";
		}
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		String portalId = portal.getPortalId();

		Workspace workspace = new Workspace();
		workspace = announceItemService.getWorkspaceInfo(portalId, workspaceId);

		int isOrganization = 0;
		if (workspace != null) {
			isOrganization = workspace.getIsOrganization();
		}

		ObjectMapper mapper = new ObjectMapper();

		// 파일 목록을 JSON으로 변환한다.
		String fileDataListJson = null;

		if (announceItem.getFileDataList() != null) {
			fileDataListJson = mapper.writeValueAsString(announceItem.getFileDataList());
		}

		ModelAndView modelAndView = new ModelAndView(viewName).addObject("announceItem", announceItem)
				.addObject("layoutType", layoutType).addObject("docPopup", popup)
				.addObject("isOrganization", isOrganization).addObject("announcePermission", announcePermission)
				.addObject("user", user).addObject("fileDataListJson", fileDataListJson);

		return modelAndView;
	}

	/**
	 * ANNOUNCE 게시물 등록 화면
	 * 
	 * @param workspaceId
	 * @return
	 */
	@RequestMapping(value = "/createAnnounceItemView")
	public ModelAndView createAnnounceItemView(@RequestParam(value = "workspaceId") String workspaceId) {

		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		String portalId = portal.getPortalId();
		Workspace workspace = new Workspace();
		AnnounceItem announceItem = new AnnounceItem();

		workspace = announceItemService.getWorkspaceInfo(portalId, workspaceId);

		announceItem.setWorkspaceId(workspaceId);
		announceItem.setWorkspaceName(workspace.getWorkspaceName());

		User user = readUser();

		// 권한체크 0:비회원,1:회원,2:운영진,3:시스템/Coll관리자
		// 권한 없을때 Forwarding 정책필요
		// int announcePermission = getAnnouncePermission(user, workspaceId);

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
		return new ModelAndView().addObject("announceItem", announceItem).addObject("user", user)
				.addObject("useActiveX", useActiveX)
				.addObject("ecmrole", ecmrole)
;
	}

	/**
	 * ANNOUNCE 게시물 수정 화면
	 * 
	 * @param itemId
	 * @param workspaceId
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping(value = "/updateAnnounceItemView")
	public ModelAndView updateAnnounceItemView(@RequestParam("itemId") String itemId,
			@RequestParam("workspaceId") String workspaceId) throws JsonGenerationException, JsonMappingException,
			IOException {

		// 권한체크 0:비회원,1:회원,2:운영진,3:시스템/Coll관리자
		// 권한 없을때 Forwarding 정책필요
		// int announcePermission = getAnnouncePermission(user, workspaceId);
		User user = readUser();

		AnnounceItem announceItem = this.announceItemService.readAnnounceItem(user.getUserId(), itemId, workspaceId);

		ObjectMapper mapper = new ObjectMapper();

		String fileDataListJson = mapper.writeValueAsString(announceItem.getFileDataList());
		String tagListJson = mapper.writeValueAsString(announceItem.getTagList());

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

		return new ModelAndView().addObject("announceItem", announceItem).addObject("user", user)
				.addObject("tagListJson", tagListJson).addObject("fileDataListJson", fileDataListJson)
				.addObject("useActiveX", useActiveX)
				.addObject("ecmrole", ecmrole);
	}

	/**
	 * 관리자에 의한 ANNOUNCE 게시물 등록
	 * 
	 * @param announceItem
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/createAnnounceItem")
	public ModelAndView createAnnounceItem(@Valid AnnounceItem announceItem,
			AnnounceItemSearchCondition searchCondition, BindingResult result, SessionStatus status) {

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

		// if (log.isDebugEnabled()) {
		// log.debug(announceItem.getEndDate());
		// }
		User user = (User) getRequestAttribute("ikep.user");
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		announceItem.setPortalId(portal.getPortalId());

		ModelBeanUtil.bindRegisterInfo(announceItem, user.getUserId(), user.getUserName());

		String itemId = this.announceItemService.createAnnounceItem(announceItem, user);

		// AnnounceItemSearchCondition searchCondition =
		// (AnnounceItemSearchCondition)
		// this.getRequestAttribute("searchCondition");
		if(announceItem.getAlarmYn().equals("1")){
			Workspace workspace = new Workspace();
			workspace.setPortalId(portal.getPortalId());
			workspace.setWorkspaceId(announceItem.getWorkspaceId());
			workspace = workspaceService.read(workspace);
			
			MemberSearchCondition memberSearchCondition = new MemberSearchCondition();
			
			memberSearchCondition.setWorkspaceId(announceItem.getWorkspaceId());
			
			SearchResult<Member> searchResult = memberService.memberListBySearchCondition(memberSearchCondition);
			String[] recipientId = new String[searchResult.getEntity().size()];
			for (int i = 0; i < searchResult.getEntity().size(); i++) {
				recipientId[i] = searchResult.getEntity().get(i).getMemberId();
			}
			Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
			String baseUrl =commonprop.getProperty("ikep4.baseUrl");
			String url = baseUrl+"/collpack/collaboration/board/announce/directReadAnnounceItemView.do?itemId="+itemId+"&workspaceId="+announceItem.getWorkspaceId()+"&suserId=";
			String title = announceItem.getRegisterName()+"님이 "+workspace.getWorkspaceName()+"에 새로운 글을 작성했습니다";
			String contents = "[게시판 - 공지사항]<br>[제목 - "+announceItem.getTitle()+"]<br>[작성자  - "+announceItem.getRegisterName()+"]";
			boardBatchItemService.messageSend(user.getUserName(),recipientId,contents.toString(),url,title);
		}

		status.setComplete();

		// this.setRequestAttribute("searchCondition", searchCondition);

		return new ModelAndView("redirect:/collpack/collaboration/board/announce/readAnnounceItemView.do")
				.addObject("itemId", itemId).addObject("searchCondition", searchCondition)
				.addObject("workspaceId", announceItem.getWorkspaceId());
	}

	/**
	 * ANNOUNCE 게시물 수정모듈
	 * 
	 * @param announceItem
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/updateAnnounceItem")
	public ModelAndView updateAnnounceItem(@Valid AnnounceItem announceItem, BindingResult result, SessionStatus status) {

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
		Portal portal = (Portal) getRequestAttribute("ikep.portal");

		ModelBeanUtil.bindRegisterInfo(announceItem, user.getUserId(), user.getUserName());
		announceItem.setPortalId(portal.getPortalId());

		this.announceItemService.updateAnnounceItem(announceItem, user);

		status.setComplete();

		return new ModelAndView("redirect:/collpack/collaboration/board/announce/readAnnounceItemView.do").addObject(
				"itemId", announceItem.getItemId()).addObject("workspaceId", announceItem.getWorkspaceId());
	}

	/**
	 * 관리자에 의한 ANNOUNCE 게시물 물리적 삭제 (다중삭제시)
	 * 
	 * @param itemId
	 * @param workspaceId
	 * @return
	 */
	@RequestMapping(value = "/adminMultiDeleteAnnounceItem")
	public @ResponseBody
	void adminMultiDeleteAnnouncItem(@RequestParam("announceItemIds") List<String> announceItemIds,
			@RequestParam("workspaceId") String workspaceId) {

		try {
			// 권한체크 0:비회원,1:회원,2:운영진,3:시스템/Coll관리자
			// 권한 없을때 Forwarding 정책필요
			// int announcePermission = getAnnouncePermission(user,
			// workspaceId);
			Portal portal = (Portal) getRequestAttribute("ikep.portal");
			this.announceItemService.adminMultiDeleteAnnounceItem(announceItemIds, workspaceId, portal.getPortalId());
		} catch (Exception exception) {
			throw new IKEP4AjaxException("code", exception);
		}
	}

	/**
	 * 관리자에 의한 ANNOUNCE 게시물 물리적 삭제
	 * 
	 * @param itemId
	 * @param workspaceId
	 * @return
	 */
	@RequestMapping(value = "/adminDeleteAnnounceItem")
	public ModelAndView adminDeleteAnnounceItem(@RequestParam("itemId") String itemId,
			@RequestParam("workspaceId") String workspaceId) {
		// 권한체크 0:비회원,1:회원,2:운영진,3:시스템/Coll관리자
		// 권한 없을때 Forwarding 정책필요
		// int announcePermission = getAnnouncePermission(user, workspaceId);
		User user = readUser();
		AnnounceItem anounceItem = announceItemService.readAnnounceItem(user.getUserId(), itemId, workspaceId);

		this.announceItemService.adminDeleteAnnounceItem(anounceItem);

		return new ModelAndView("redirect:/collpack/collaboration/board/announce/listAnnounceItemView.do?workspaceId="
				+ anounceItem.getWorkspaceId());
	}

	/**
	 * // * 하위부서중 개설된 WORKSPACE 리스트
	 * 
	 * @param announceItemId
	 * @param workspaceId
	 * @param searchCondition
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/shareAnnounceItemPop")
	public ModelAndView shareAnnounceItemPop(@RequestParam("itemId") String announceItemId,
			@RequestParam("workspaceId") String workspaceId, AnnounceItemSearchCondition searchCondition,
			BindingResult result, SessionStatus status) {
		try {
			// 권한체크 0:비회원,1:회원,2:운영진,3:시스템/Coll관리자
			// 권한 없을때 Forwarding 정책필요
			// int announcePermission = getAnnouncePermission(user,
			// workspaceId);

			Portal portal = (Portal) getRequestAttribute("ikep.portal");

			SearchResult<AnnounceItem> searchResult = null;
			searchCondition.setWorkspaceId(workspaceId);
			searchCondition.setItemId(announceItemId);
			searchCondition.setPortalId(portal.getPortalId());
			searchResult = announceItemService.listChildWorkspaceInfoBySearchCondition(searchCondition);

			return new ModelAndView("collpack/collaboration/board/announce/shareAnnounceItemPop")
					.addObject("workspaceId", workspaceId)
					.addObject("searchCondition", searchResult.getSearchCondition())
					.addObject("searchResult", searchResult).addObject("itemId", announceItemId);

		} catch (Exception exception) {
			throw new IKEP4AjaxException("code", exception);
		}
	}

	/**
	 * 선택된 공지사항 게시물 공유
	 * 
	 * @param announceItemId
	 * @param workspaceIds
	 * @param workspaceId
	 */
	@RequestMapping(value = "/shareCollAnnounceItem")
	public @ResponseBody
	void shareCollAnnounceItem(@RequestParam("itemId") String announceItemId,
			@RequestParam(value = "workspaceIds", required = false) List<String> workspaceIds,
			@RequestParam("workspaceId") String workspaceId) {
		if (!StringUtil.isEmpty(announceItemId)) {
			announceItemService.createAnnounceLinkItem(announceItemId, workspaceIds);
		}
	}

	/**
	 * Workspace Announce 게시판 권한 가져오기 //권한체크
	 * 0:비회원,준회원,1:정회원,2:운영진,시샵,3:시스템,Coll관리자
	 * 
	 * @param userId
	 * @param workspaceId
	 * @return
	 */
	public int getAnnouncePermission(User user, String workspaceId) {
		boolean isSystemAdmin = isSystemAdmin(user);

		int announcePermission = 0;
		if (isSystemAdmin) {
			announcePermission = 3;
		} else {
			Member member = new Member();
			member.setMemberId(user.getUserId());
			member.setWorkspaceId(workspaceId);
			member = memberService.read(member);

			if (member != null) {
				if ("1".equals(member.getMemberLevel()) || "2".equals(member.getMemberLevel())) {
					announcePermission = 2;
				} else if ("3".equals(member.getMemberLevel())) {
					announcePermission = 1;
				} else {
					announcePermission = 0;
				}
			}
		}

		return announcePermission;
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

	}

}
