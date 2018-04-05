package com.lgcns.ikep4.collpack.kms.notice.web;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.collpack.kms.notice.model.NoticeCode;
import com.lgcns.ikep4.collpack.kms.notice.model.NoticeConfig;
import com.lgcns.ikep4.collpack.kms.notice.model.NoticeItem;
import com.lgcns.ikep4.collpack.kms.notice.search.NoticeItemSearchCondition;
import com.lgcns.ikep4.collpack.kms.notice.service.NoticeItemService;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxValidationException;
import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AuthorizedException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.member.dao.UserDao;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.util.model.ModelBeanUtil;


@Controller("kmsNoticeItemController")
@RequestMapping(value = "/collpack/kms/notice")
@SessionAttributes("noticeItem")
public class NoticeItemController extends BaseController {

//	private static final String COLLABORATION_MANAGER = "Collaboration";
	private static final String KMS_MANAGER = "Kms";

	@Autowired
	private NoticeItemService noticeItemService;

//	@Autowired
//	private MemberService memberService;

	@Autowired
	private ACLService aclService;
	
	@Autowired
    private UserDao userDao;
	
	@Autowired
	private FileService fileService;

	/**
	 * Workspace 시스템 관리자 여부
	 * 
	 * @param user
	 * @return
	 */
	public boolean isSystemAdmin(User user) {

		return aclService.isSystemAdmin(KMS_MANAGER, user.getUserId());

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
	 * NOTICE 게시물 리스트
	 * 
	 * @param workspaceId
	 * @param searchCondition
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/listNoticeItemView")
//	public ModelAndView listNoticeItemView(String workspaceId, NoticeItemSearchCondition searchCondition,
	public ModelAndView listNoticeItemView(NoticeItemSearchCondition searchCondition,
			BindingResult result, SessionStatus status) {
		User user = readUser();

//		if (searchCondition.getWorkspaceId() == null) {
//			searchCondition.setWorkspaceId(workspaceId);
//		}
		if (searchCondition.getViewMode() == null) {
			searchCondition.setViewMode("0");
		}
		if (searchCondition.getPagePerRecord() == null) {
			searchCondition.setPagePerRecord(10); 
		}
		boolean isSystemAdmin = isSystemAdmin(user);
		String viewName = null;
		SearchResult<NoticeItem> searchResult = null;

		searchResult = this.noticeItemService.listNoticeItemBySearchCondition(searchCondition);

		ModelAndView modelAndView = new ModelAndView(viewName).addObject("noticeCode", new NoticeCode())
				.addObject("user", user)
//				.addObject("workspaceId", workspaceId)
//				.addObject("noticePermission", getNoticePermission(user, workspaceId))
				.addObject("noticePermission", getNoticePermission(user))
				.addObject("searchCondition", searchResult.getSearchCondition())
				.addObject("searchResult", searchResult).addObject("isSystemAdmin", isSystemAdmin);

		return modelAndView;
	}
	
	@RequestMapping(value = "/noticeConfigView")
	public ModelAndView noticeConfigView( String portletConfigId) {
		
		ModelAndView mav = new ModelAndView("collpack/kms/notice/noticeConfigView");
		
		NoticeConfig noticeconfig = noticeItemService.readNoticeconfig();
		
		if(noticeconfig == null) {
			noticeconfig = new NoticeConfig();
		}
		
		mav.addObject("noticeconfig", noticeconfig);
		mav.addObject("portletConfigId", portletConfigId);
		
		return mav;
	}
	
	@RequestMapping(value = "/createNoticeConfig.do")
	public @ResponseBody String createNoticeConfig(@ModelAttribute NoticeConfig noticeConfig, String editorAttach, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");
		
		try {
			MultipartRequest multipartRequest = (MultipartRequest) request;
			List<MultipartFile> fileList = multipartRequest.getFiles("file");
			
			noticeItemService.createNoticeConfig(noticeConfig, fileList, editorAttach, user);
		} catch (Exception e) {
			if(e instanceof IKEP4ApplicationException) {
				throw (IKEP4ApplicationException)e;
			} else {
				throw new IKEP4ApplicationException("", e);
			}
		}
		
		return "success";
	}

	/**
	 * NOTICE 게시물 읽기
	 * 
	 * @param itemId
	 * @param layoutType
	 * @param docPopup
	 * @param workspaceId
	 * @return
	 */
	@RequestMapping(value = "/readNoticeItemView")
//	public ModelAndView readNoticeItemView(@RequestParam("itemId") String itemId,
//			@RequestParam(value = "layoutType", defaultValue = "layoutNormal") String layoutType,
//			@RequestParam(value = "docPopup", defaultValue = "false") Boolean docPopup,
//			@RequestParam(value = "workspaceId", required = false) String workspaceId) throws JsonGenerationException,
//			JsonMappingException, IOException {
	public ModelAndView readNoticeItemView(@RequestParam("itemId") String itemId,
			@RequestParam(value = "layoutType", defaultValue = "layoutNormal") String layoutType,
			@RequestParam(value = "docPopup", defaultValue = "false") Boolean docPopup) throws JsonGenerationException,
			JsonMappingException, IOException {		

		User user = readUser();
		String viewName = "";

		Boolean popup = false;

		int noticePermission = 0;
//		noticePermission = getNoticePermission(user, workspaceId);
		noticePermission = getNoticePermission(user);

		if (noticePermission == 0) {
//			throw new IKEP4AuthorizedException();
		}

		// 게시글 정보를 가져온다. 파일정보와 관련 글 정보가 포함되어 있다.
//		NoticeItem noticeItem = this.noticeItemService.readNoticeItem(user.getUserId(), itemId, workspaceId);
		NoticeItem noticeItem = this.noticeItemService.readNoticeItem(user.getUserId(), itemId);

		if (noticeItem == null) {
			throw new IKEP4AuthorizedException(messageSource, "message.collpack.kms.common.boardItem.deletedItem");
		}

		if ("layoutHorizental".equals(layoutType)) {
			viewName = "collpack/kms/notice/readNoticeItemFrameView";

		} else if ("layoutVertical".equals(layoutType)) {
			viewName = "collpack/kms/notice/readNoticeItemFrameView";

		} else if ("layoutNormal".equals(layoutType)) {
			viewName = "collpack/kms/notice/readNoticeItemView";
		}

//		if (StringUtil.isEmpty(workspaceId)) {
//			popup = true;
//		} else {
			popup = docPopup;
//		}

		if (popup) {
			viewName = "collpack/kms/notice/readNoticeItemPopupView";
		}
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		String portalId = portal.getPortalId();

//		Workspace workspace = new Workspace();
//		workspace = noticeItemService.getWorkspaceInfo(portalId, workspaceId);

		int isOrganization = 0;
//		if (workspace != null) {
//			isOrganization = workspace.getIsOrganization();
//		}

		ObjectMapper mapper = new ObjectMapper();

		// 파일 목록을 JSON으로 변환한다.
		String fileDataListJson = null;

		if (noticeItem.getFileDataList() != null) {
			fileDataListJson = mapper.writeValueAsString(noticeItem.getFileDataList());
		}

		ModelAndView modelAndView = new ModelAndView(viewName).addObject("noticeItem", noticeItem)
				.addObject("layoutType", layoutType).addObject("docPopup", popup)
				.addObject("isOrganization", isOrganization).addObject("noticePermission", noticePermission)
				.addObject("user", user).addObject("fileDataListJson", fileDataListJson);

		return modelAndView;
	}

	/**
	 * NOTICE 게시물 읽기
	 * 
	 * @param itemId
	 * @param layoutType
	 * @param docPopup
	 * @param workspaceId
	 * @return
	 */
	@RequestMapping(value = "/readNoticeItemPopupView")
	public ModelAndView readNoticeItemPopupView(@RequestParam("itemId") String itemId,
			@RequestParam(value = "layoutType", defaultValue = "layoutNormal") String layoutType,
			@RequestParam(value = "docPopup", defaultValue = "false") Boolean docPopup,
			@RequestParam(value = "workspaceId", required = false) String workspaceId) throws JsonGenerationException,
			JsonMappingException, IOException {

		User user = readUser();
		String viewName = "";

		Boolean popup = false;

		int noticePermission = 0;
//		noticePermission = getNoticePermission(user, workspaceId);
		noticePermission = getNoticePermission(user);

		if (noticePermission == 0) {
			throw new IKEP4AuthorizedException();
		}

		// 게시글 정보를 가져온다. 파일정보와 관련 글 정보가 포함되어 있다.
//		NoticeItem noticeItem = this.noticeItemService.readNoticeItem(user.getUserId(), itemId, workspaceId);
		NoticeItem noticeItem = this.noticeItemService.readNoticeItem(user.getUserId(), itemId);

		if (noticeItem == null) {
			throw new IKEP4AuthorizedException(messageSource, "message.collpack.kms.common.boardItem.deletedItem");
		}

		if ("layoutHorizental".equals(layoutType)) {
			viewName = "collpack/kms/notice/readNoticeItemFrameView";

		} else if ("layoutVertical".equals(layoutType)) {
			viewName = "collpack/kms/notice/readNoticeItemFrameView";

		} else if ("layoutNormal".equals(layoutType)) {
			viewName = "collpack/kms/notice/readNoticeItemView";
		}

		if (StringUtil.isEmpty(workspaceId)) {
			popup = true;
		} else {
			popup = docPopup;
		}

		if (popup) {
			viewName = "collpack/kms/notice/readNoticeItemPopupView";
		}
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		String portalId = portal.getPortalId();

//		Workspace workspace = new Workspace();
//		workspace = noticeItemService.getWorkspaceInfo(portalId, workspaceId);

		int isOrganization = 0;
//		if (workspace != null) {
//			isOrganization = workspace.getIsOrganization();
//		}

		ObjectMapper mapper = new ObjectMapper();

		// 파일 목록을 JSON으로 변환한다.
		String fileDataListJson = null;

		if (noticeItem.getFileDataList() != null) {
			fileDataListJson = mapper.writeValueAsString(noticeItem.getFileDataList());
		}
		

		boolean ecmrole = false;
				Map<String, String> emap = new HashMap<String, String>();
				emap.put("userId", user.getUserId());
				emap.put("roleName", "ECM");
				int ecmRole = userDao.getUserRoleCheck(emap);
				if(ecmRole > 0){
					ecmrole = true;
				}

		ModelAndView modelAndView = new ModelAndView(viewName).addObject("noticeItem", noticeItem)
				.addObject("layoutType", layoutType).addObject("docPopup", popup)
				.addObject("isOrganization", isOrganization).addObject("noticePermission", noticePermission)
				.addObject("user", user).addObject("fileDataListJson", fileDataListJson).addObject("ecmrole", ecmrole);

		return modelAndView;
	}

	/**
	 * NOTICE 게시물 등록 화면
	 * 
	 * @param workspaceId
	 * @return
	 */
	@RequestMapping(value = "/createNoticeItemView")
	public ModelAndView createNoticeItemView() {

		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		String portalId = portal.getPortalId();
//		Workspace workspace = new Workspace();
		NoticeItem noticeItem = new NoticeItem();

//		workspace = noticeItemService.getWorkspaceInfo(portalId, workspaceId);

//		noticeItem.setWorkspaceId(workspaceId);
//		noticeItem.setWorkspaceName(workspace.getWorkspaceName());

		User user = readUser();

		// 권한체크 0:비회원,1:회원,2:운영진,3:시스템/Coll관리자
		// 권한 없을때 Forwarding 정책필요
		// int noticePermission = getNoticePermission(user, workspaceId);

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

		return new ModelAndView().addObject("noticeItem", noticeItem).addObject("user", user)
				.addObject("useActiveX", useActiveX).addObject("ecmrole", ecmrole);
	}

	/**
	 * NOTICE 게시물 수정 화면
	 * 
	 * @param itemId
	 * @param workspaceId
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping(value = "/updateNoticeItemView")
	public ModelAndView updateNoticeItemView(@RequestParam("itemId") String itemId) throws JsonGenerationException, JsonMappingException,
			IOException {

		// 권한체크 0:비회원,1:회원,2:운영진,3:시스템/Coll관리자
		// 권한 없을때 Forwarding 정책필요
		// int noticePermission = getNoticePermission(user, workspaceId);
		User user = readUser();

//		NoticeItem noticeItem = this.noticeItemService.readNoticeItem(user.getUserId(), itemId, workspaceId);
		NoticeItem noticeItem = this.noticeItemService.readNoticeItem(user.getUserId(), itemId);

		ObjectMapper mapper = new ObjectMapper();

		String fileDataListJson = mapper.writeValueAsString(noticeItem.getFileDataList());
		String tagListJson = mapper.writeValueAsString(noticeItem.getTagList());

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


		return new ModelAndView().addObject("noticeItem", noticeItem).addObject("user", user)
				.addObject("tagListJson", tagListJson).addObject("fileDataListJson", fileDataListJson)
				.addObject("useActiveX", useActiveX).addObject("ecmrole", ecmrole);
	}

	/**
	 * 관리자에 의한 NOTICE 게시물 등록
	 * 
	 * @param noticeItem
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/createNoticeItem")
	public ModelAndView createNoticeItem(@Valid NoticeItem noticeItem,
			NoticeItemSearchCondition searchCondition, BindingResult result, SessionStatus status) {

		// 권한체크 0:비회원,1:회원,2:운영진,3:시스템/Coll관리자
		// 권한 없을때 Forwarding 정책필요
		// int noticePermission = getNoticePermission(user, workspaceId);
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
		// log.debug(noticeItem.getEndDate());
		// }
		User user = (User) getRequestAttribute("ikep.user");
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		noticeItem.setPortalId(portal.getPortalId());

		ModelBeanUtil.bindRegisterInfo(noticeItem, user.getUserId(), user.getUserName());

		String itemId = this.noticeItemService.createNoticeItem(noticeItem, user);

		// NoticeItemSearchCondition searchCondition =
		// (NoticeItemSearchCondition)
		// this.getRequestAttribute("searchCondition");

		status.setComplete();

		// this.setRequestAttribute("searchCondition", searchCondition);

		return new ModelAndView("redirect:/collpack/kms/notice/readNoticeItemView.do")
				.addObject("itemId", itemId).addObject("searchCondition", searchCondition)
//				.addObject("workspaceId", noticeItem.getWorkspaceId());
				.addObject("workspaceId", "");
	}

	/**
	 * NOTICE 게시물 수정모듈
	 * 
	 * @param noticeItem
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/updateNoticeItem")
	public ModelAndView updateNoticeItem(@Valid NoticeItem noticeItem, BindingResult result, SessionStatus status) {

		// 권한체크 0:비회원,1:회원,2:운영진,3:시스템/Coll관리자
		// 권한 없을때 Forwarding 정책필요
		// int noticePermission = getNoticePermission(user, workspaceId);

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

		ModelBeanUtil.bindRegisterInfo(noticeItem, user.getUserId(), user.getUserName());
		noticeItem.setPortalId(portal.getPortalId());

		this.noticeItemService.updateNoticeItem(noticeItem, user);

		status.setComplete();

		return new ModelAndView("redirect:/collpack/kms/notice/readNoticeItemView.do").addObject(
				"itemId", noticeItem.getItemId());
//				.addObject("workspaceId", noticeItem.getWorkspaceId());
		
	}

	/**
	 * 관리자에 의한 NOTICE 게시물 물리적 삭제 (다중삭제시)
	 * 
	 * @param itemId
	 * @param workspaceId
	 * @return
	 */
	@RequestMapping(value = "/adminMultiDeleteNoticeItem")
	public @ResponseBody
	void adminMultiDeleteAnnouncItem(@RequestParam("noticeItemIds") List<String> noticeItemIds) {

		try {
			// 권한체크 0:비회원,1:회원,2:운영진,3:시스템/Coll관리자
			// 권한 없을때 Forwarding 정책필요
			// int noticePermission = getNoticePermission(user,
			// workspaceId);
			Portal portal = (Portal) getRequestAttribute("ikep.portal");
//			this.noticeItemService.adminMultiDeleteNoticeItem(noticeItemIds, workspaceId, portal.getPortalId());
			this.noticeItemService.adminMultiDeleteNoticeItem(noticeItemIds, portal.getPortalId());
		} catch (Exception exception) {
			throw new IKEP4AjaxException("code", exception);
		}
	}

	/**
	 * 관리자에 의한 NOTICE 게시물 물리적 삭제
	 * 
	 * @param itemId
	 * @param workspaceId
	 * @return
	 */
	@RequestMapping(value = "/adminDeleteNoticeItem")
	public ModelAndView adminDeleteNoticeItem(@RequestParam("itemId") String itemId) {
		// 권한체크 0:비회원,1:회원,2:운영진,3:시스템/Coll관리자
		// 권한 없을때 Forwarding 정책필요
		// int noticePermission = getNoticePermission(user, workspaceId);
		User user = readUser();
//		NoticeItem noticeItem = noticeItemService.readNoticeItem(user.getUserId(), itemId, workspaceId);
		NoticeItem noticeItem = noticeItemService.readNoticeItem(user.getUserId(), itemId);

		this.noticeItemService.adminDeleteNoticeItem(noticeItem);

		return new ModelAndView("redirect:/collpack/kms/notice/listNoticeItemView.do?workspaceId="
//				+ noticeItem.getWorkspaceId());
				+ "");
	}

//	/**
//	 * // * 하위부서중 개설된 WORKSPACE 리스트
//	 * 
//	 * @param noticeItemId
//	 * @param workspaceId
//	 * @param searchCondition
//	 * @param result
//	 * @param status
//	 * @return
//	 */
//	@RequestMapping(value = "/shareNoticeItemPop")
//	public ModelAndView shareNoticeItemPop(@RequestParam("itemId") String noticeItemId,
//			@RequestParam("workspaceId") String workspaceId, NoticeItemSearchCondition searchCondition,
//			BindingResult result, SessionStatus status) {
//		try {
//			// 권한체크 0:비회원,1:회원,2:운영진,3:시스템/Coll관리자
//			// 권한 없을때 Forwarding 정책필요
//			// int noticePermission = getNoticePermission(user,
//			// workspaceId);
//
//			Portal portal = (Portal) getRequestAttribute("ikep.portal");
//
//			SearchResult<NoticeItem> searchResult = null;
//			searchCondition.setWorkspaceId(workspaceId);
//			searchCondition.setItemId(noticeItemId);
//			searchCondition.setPortalId(portal.getPortalId());
//			searchResult = noticeItemService.listChildWorkspaceInfoBySearchCondition(searchCondition);
//
//			return new ModelAndView("collpack/kms/notice/shareNoticeItemPop")
//					.addObject("workspaceId", workspaceId)
//					.addObject("searchCondition", searchResult.getSearchCondition())
//					.addObject("searchResult", searchResult).addObject("itemId", noticeItemId);
//
//		} catch (Exception exception) {
//			throw new IKEP4AjaxException("code", exception);
//		}
//	}

//	/**
//	 * 선택된 공지사항 게시물 공유
//	 * 
//	 * @param noticeItemId
//	 * @param workspaceIds
//	 * @param workspaceId
//	 */
//	@RequestMapping(value = "/shareCollNoticeItem")
//	public @ResponseBody
//	void shareCollNoticeItem(@RequestParam("itemId") String noticeItemId,
//			@RequestParam(value = "workspaceIds", required = false) List<String> workspaceIds,
//			@RequestParam("workspaceId") String workspaceId) {
//		if (!StringUtil.isEmpty(noticeItemId)) {
//			noticeItemService.createNoticeLinkItem(noticeItemId, workspaceIds);
//		}
//	}

	/**
	 * Workspace Notice 게시판 권한 가져오기 //권한체크
	 * 0:비회원,준회원,1:정회원,2:운영진,시샵,3:시스템,Coll관리자
	 * 
	 * @param userId
	 * @param workspaceId
	 * @return
	 */
//	public int getNoticePermission(User user, String workspaceId) {
	public int getNoticePermission(User user) {
		
		boolean isSystemAdmin = isSystemAdmin(user);

//		int noticePermission = 0;
		int noticePermission = 1;
		if (isSystemAdmin) {
			noticePermission = 3;
		} 
//		else {
//			Member member = new Member();
//			member.setMemberId(user.getUserId());
//			member.setWorkspaceId(workspaceId);
//			member = memberService.read(member);
//
//			if (member != null) {
//				if ("1".equals(member.getMemberLevel()) || "2".equals(member.getMemberLevel())) {
//					noticePermission = 2;
//				} else if ("3".equals(member.getMemberLevel())) {
//					noticePermission = 1;
//				} else {
//					noticePermission = 0;
//				}
//			}
//		}

		return noticePermission;
	}
	
	@RequestMapping(value = "/noticeNormalView")
	public ModelAndView noticeNormalView(String portletConfigId) {
		ModelAndView mav = new ModelAndView("collpack/kms/notice/noticeNormalView");
		
		String campaignmessagesImageYn = "N";
		
		//String managementPolicyImageYn = "N";
		
		try{
			
			NoticeConfig noticeconfig = noticeItemService.readNoticeconfig();

			if(noticeconfig != null) {
				
				if(!StringUtil.isEmpty(noticeconfig.getSummary())) {
					
					String summary = noticeconfig.getSummary().replaceAll("\r\n", "<br/>");
				
					noticeconfig.setSummary(summary);
			    }
			
				if(!StringUtil.isEmpty(noticeconfig.getImageFileId())) {
				
					FileData fileData = fileService.read(noticeconfig.getImageFileId());
			
					if(fileData != null) {
						Properties prop = PropertyLoader.loadProperties("/configuration/fileupload-conf.properties");
						String uploadRoot = prop.getProperty("ikep4.support.fileupload.upload_root_image");
				
						File file = new File(uploadRoot + fileData.getFilePath(), fileData.getFileName());
					
						if (file.exists()) {
							campaignmessagesImageYn = "Y"; 
						}
					}
				}
			}
			
			mav.addObject("noticeconfig", noticeconfig);
			mav.addObject("campaignmessagesImageYn", campaignmessagesImageYn);
			
			/*PortletCampaignMessages portletManagementPolicy = portletCampaignMessagesService.readPortletManagementPolicy();

			if(portletManagementPolicy != null) {
				
				if(!StringUtil.isEmpty(portletManagementPolicy.getSummary())) {
					
					String summary = portletManagementPolicy.getSummary().replaceAll("\r\n", "<br/>");
				
					portletManagementPolicy.setSummary(summary);
			    }
			
				if(!StringUtil.isEmpty(portletManagementPolicy.getImageFileId())) {
				
					FileData fileData = fileService.read(portletManagementPolicy.getImageFileId());
			
					if(fileData != null) {
						Properties prop = PropertyLoader.loadProperties("/configuration/fileupload-conf.properties");
						String uploadRoot = prop.getProperty("ikep4.support.fileupload.upload_root_image");
				
						File file = new File(uploadRoot + fileData.getFilePath(), fileData.getFileName());
					
						if (file.exists()) {
							managementPolicyImageYn = "Y"; 
						}
					}
				}
			}
			
			mav.addObject("portletManagementPolicy", portletManagementPolicy);
			mav.addObject("managementPolicyImageYn", managementPolicyImageYn);*/
						
			mav.addObject("portletConfigId", portletConfigId);

		} catch (Exception e) {
			throw new IKEP4AjaxException("", e);
		}
		
		return mav;
	}

}
