package com.lgcns.ikep4.collpack.kms.announce.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

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

import com.lgcns.ikep4.collpack.kms.announce.model.AnnounceCode;
import com.lgcns.ikep4.collpack.kms.announce.model.AnnounceItem;
import com.lgcns.ikep4.collpack.kms.announce.search.AnnounceItemSearchCondition;
import com.lgcns.ikep4.collpack.kms.announce.service.AnnounceItemService;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxValidationException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AuthorizedException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.member.dao.UserDao;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.util.model.ModelBeanUtil;


@Controller("kmsAnnounceItemController")
@RequestMapping(value = "/collpack/kms/announce")
@SessionAttributes("announceItem")
public class AnnounceItemController extends BaseController {

//	private static final String COLLABORATION_MANAGER = "Collaboration";
	private static final String KMS_MANAGER = "Kms";

	@Autowired
	private AnnounceItemService announceItemService;

//	@Autowired
//	private MemberService memberService;

	@Autowired
	private ACLService aclService;
	
	@Autowired
    private UserDao userDao;

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
	 * ANNOUNCE 게시물 리스트
	 * 
	 * @param workspaceId
	 * @param searchCondition
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/listAnnounceItemView")
//	public ModelAndView listAnnounceItemView(String workspaceId, AnnounceItemSearchCondition searchCondition,
	public ModelAndView listAnnounceItemView(AnnounceItemSearchCondition searchCondition,
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

		String viewName = null;
		SearchResult<AnnounceItem> searchResult = null;

		searchResult = this.announceItemService.listAnnounceItemBySearchCondition(searchCondition);

		ModelAndView modelAndView = new ModelAndView(viewName).addObject("announceCode", new AnnounceCode())
				.addObject("user", user)
//				.addObject("workspaceId", workspaceId)
//				.addObject("announcePermission", getAnnouncePermission(user, workspaceId))
				.addObject("announcePermission", getAnnouncePermission(user))
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
//	public ModelAndView readAnnounceItemView(@RequestParam("itemId") String itemId,
//			@RequestParam(value = "layoutType", defaultValue = "layoutNormal") String layoutType,
//			@RequestParam(value = "docPopup", defaultValue = "false") Boolean docPopup,
//			@RequestParam(value = "workspaceId", required = false) String workspaceId) throws JsonGenerationException,
//			JsonMappingException, IOException {
	public ModelAndView readAnnounceItemView(@RequestParam("itemId") String itemId,
			@RequestParam(value = "layoutType", defaultValue = "layoutNormal") String layoutType,
			@RequestParam(value = "docPopup", defaultValue = "false") Boolean docPopup) throws JsonGenerationException,
			JsonMappingException, IOException {		

		User user = readUser();
		String viewName = "";

		Boolean popup = false;

		int announcePermission = 0;
//		announcePermission = getAnnouncePermission(user, workspaceId);
		announcePermission = getAnnouncePermission(user);

		if (announcePermission == 0) {
//			throw new IKEP4AuthorizedException();
		}

		// 게시글 정보를 가져온다. 파일정보와 관련 글 정보가 포함되어 있다.
//		AnnounceItem announceItem = this.announceItemService.readAnnounceItem(user.getUserId(), itemId, workspaceId);
		AnnounceItem announceItem = this.announceItemService.readAnnounceItem(user.getUserId(), itemId);

		if (announceItem == null) {
			throw new IKEP4AuthorizedException(messageSource, "message.collpack.kms.common.boardItem.deletedItem");
		}

		if ("layoutHorizental".equals(layoutType)) {
			viewName = "collpack/kms/announce/readAnnounceItemFrameView";

		} else if ("layoutVertical".equals(layoutType)) {
			viewName = "collpack/kms/announce/readAnnounceItemFrameView";

		} else if ("layoutNormal".equals(layoutType)) {
			viewName = "collpack/kms/announce/readAnnounceItemView";
		}

//		if (StringUtil.isEmpty(workspaceId)) {
//			popup = true;
//		} else {
			popup = docPopup;
//		}

		if (popup) {
			viewName = "collpack/kms/announce/readAnnounceItemPopupView";
		}
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		String portalId = portal.getPortalId();

//		Workspace workspace = new Workspace();
//		workspace = announceItemService.getWorkspaceInfo(portalId, workspaceId);

		int isOrganization = 0;
//		if (workspace != null) {
//			isOrganization = workspace.getIsOrganization();
//		}

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
//		announcePermission = getAnnouncePermission(user, workspaceId);
		announcePermission = getAnnouncePermission(user);

		if (announcePermission == 0) {
			throw new IKEP4AuthorizedException();
		}

		// 게시글 정보를 가져온다. 파일정보와 관련 글 정보가 포함되어 있다.
//		AnnounceItem announceItem = this.announceItemService.readAnnounceItem(user.getUserId(), itemId, workspaceId);
		AnnounceItem announceItem = this.announceItemService.readAnnounceItem(user.getUserId(), itemId);

		if (announceItem == null) {
			throw new IKEP4AuthorizedException(messageSource, "message.collpack.kms.common.boardItem.deletedItem");
		}

		if ("layoutHorizental".equals(layoutType)) {
			viewName = "collpack/kms/announce/readAnnounceItemFrameView";

		} else if ("layoutVertical".equals(layoutType)) {
			viewName = "collpack/kms/announce/readAnnounceItemFrameView";

		} else if ("layoutNormal".equals(layoutType)) {
			viewName = "collpack/kms/announce/readAnnounceItemView";
		}

		if (StringUtil.isEmpty(workspaceId)) {
			popup = true;
		} else {
			popup = docPopup;
		}

		if (popup) {
			viewName = "collpack/kms/announce/readAnnounceItemPopupView";
		}
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		String portalId = portal.getPortalId();

//		Workspace workspace = new Workspace();
//		workspace = announceItemService.getWorkspaceInfo(portalId, workspaceId);

		int isOrganization = 0;
//		if (workspace != null) {
//			isOrganization = workspace.getIsOrganization();
//		}

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
				.addObject("user", user).addObject("fileDataListJson", fileDataListJson).addObject("ecmrole", ecmrole);

		return modelAndView;
	}

	/**
	 * ANNOUNCE 게시물 등록 화면
	 * 
	 * @param workspaceId
	 * @return
	 */
	@RequestMapping(value = "/createAnnounceItemView")
	public ModelAndView createAnnounceItemView() {

		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		String portalId = portal.getPortalId();
//		Workspace workspace = new Workspace();
		AnnounceItem announceItem = new AnnounceItem();

//		workspace = announceItemService.getWorkspaceInfo(portalId, workspaceId);

//		announceItem.setWorkspaceId(workspaceId);
//		announceItem.setWorkspaceName(workspace.getWorkspaceName());

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
				.addObject("useActiveX", useActiveX).addObject("ecmrole", ecmrole);
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
	public ModelAndView updateAnnounceItemView(@RequestParam("itemId") String itemId) throws JsonGenerationException, JsonMappingException,
			IOException {

		// 권한체크 0:비회원,1:회원,2:운영진,3:시스템/Coll관리자
		// 권한 없을때 Forwarding 정책필요
		// int announcePermission = getAnnouncePermission(user, workspaceId);
		User user = readUser();

//		AnnounceItem announceItem = this.announceItemService.readAnnounceItem(user.getUserId(), itemId, workspaceId);
		AnnounceItem announceItem = this.announceItemService.readAnnounceItem(user.getUserId(), itemId);

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
				.addObject("useActiveX", useActiveX).addObject("ecmrole", ecmrole);
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

		status.setComplete();

		// this.setRequestAttribute("searchCondition", searchCondition);

		return new ModelAndView("redirect:/collpack/kms/announce/readAnnounceItemView.do")
				.addObject("itemId", itemId).addObject("searchCondition", searchCondition)
//				.addObject("workspaceId", announceItem.getWorkspaceId());
				.addObject("workspaceId", "");
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

		return new ModelAndView("redirect:/collpack/kms/announce/readAnnounceItemView.do").addObject(
				"itemId", announceItem.getItemId());
//				.addObject("workspaceId", announceItem.getWorkspaceId());
		
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
	void adminMultiDeleteAnnouncItem(@RequestParam("announceItemIds") List<String> announceItemIds) {

		try {
			// 권한체크 0:비회원,1:회원,2:운영진,3:시스템/Coll관리자
			// 권한 없을때 Forwarding 정책필요
			// int announcePermission = getAnnouncePermission(user,
			// workspaceId);
			Portal portal = (Portal) getRequestAttribute("ikep.portal");
//			this.announceItemService.adminMultiDeleteAnnounceItem(announceItemIds, workspaceId, portal.getPortalId());
			this.announceItemService.adminMultiDeleteAnnounceItem(announceItemIds, portal.getPortalId());
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
	public ModelAndView adminDeleteAnnounceItem(@RequestParam("itemId") String itemId) {
		// 권한체크 0:비회원,1:회원,2:운영진,3:시스템/Coll관리자
		// 권한 없을때 Forwarding 정책필요
		// int announcePermission = getAnnouncePermission(user, workspaceId);
		User user = readUser();
//		AnnounceItem anounceItem = announceItemService.readAnnounceItem(user.getUserId(), itemId, workspaceId);
		AnnounceItem anounceItem = announceItemService.readAnnounceItem(user.getUserId(), itemId);

		this.announceItemService.adminDeleteAnnounceItem(anounceItem);

		return new ModelAndView("redirect:/collpack/kms/announce/listAnnounceItemView.do?workspaceId="
//				+ anounceItem.getWorkspaceId());
				+ "");
	}

//	/**
//	 * // * 하위부서중 개설된 WORKSPACE 리스트
//	 * 
//	 * @param announceItemId
//	 * @param workspaceId
//	 * @param searchCondition
//	 * @param result
//	 * @param status
//	 * @return
//	 */
//	@RequestMapping(value = "/shareAnnounceItemPop")
//	public ModelAndView shareAnnounceItemPop(@RequestParam("itemId") String announceItemId,
//			@RequestParam("workspaceId") String workspaceId, AnnounceItemSearchCondition searchCondition,
//			BindingResult result, SessionStatus status) {
//		try {
//			// 권한체크 0:비회원,1:회원,2:운영진,3:시스템/Coll관리자
//			// 권한 없을때 Forwarding 정책필요
//			// int announcePermission = getAnnouncePermission(user,
//			// workspaceId);
//
//			Portal portal = (Portal) getRequestAttribute("ikep.portal");
//
//			SearchResult<AnnounceItem> searchResult = null;
//			searchCondition.setWorkspaceId(workspaceId);
//			searchCondition.setItemId(announceItemId);
//			searchCondition.setPortalId(portal.getPortalId());
//			searchResult = announceItemService.listChildWorkspaceInfoBySearchCondition(searchCondition);
//
//			return new ModelAndView("collpack/kms/announce/shareAnnounceItemPop")
//					.addObject("workspaceId", workspaceId)
//					.addObject("searchCondition", searchResult.getSearchCondition())
//					.addObject("searchResult", searchResult).addObject("itemId", announceItemId);
//
//		} catch (Exception exception) {
//			throw new IKEP4AjaxException("code", exception);
//		}
//	}

//	/**
//	 * 선택된 공지사항 게시물 공유
//	 * 
//	 * @param announceItemId
//	 * @param workspaceIds
//	 * @param workspaceId
//	 */
//	@RequestMapping(value = "/shareCollAnnounceItem")
//	public @ResponseBody
//	void shareCollAnnounceItem(@RequestParam("itemId") String announceItemId,
//			@RequestParam(value = "workspaceIds", required = false) List<String> workspaceIds,
//			@RequestParam("workspaceId") String workspaceId) {
//		if (!StringUtil.isEmpty(announceItemId)) {
//			announceItemService.createAnnounceLinkItem(announceItemId, workspaceIds);
//		}
//	}

	/**
	 * Workspace Announce 게시판 권한 가져오기 //권한체크
	 * 0:비회원,준회원,1:정회원,2:운영진,시샵,3:시스템,Coll관리자
	 * 
	 * @param userId
	 * @param workspaceId
	 * @return
	 */
//	public int getAnnouncePermission(User user, String workspaceId) {
	public int getAnnouncePermission(User user) {
		
		boolean isSystemAdmin = isSystemAdmin(user);

//		int announcePermission = 0;
		int announcePermission = 1;
		if (isSystemAdmin) {
			announcePermission = 3;
		} 
//		else {
//			Member member = new Member();
//			member.setMemberId(user.getUserId());
//			member.setWorkspaceId(workspaceId);
//			member = memberService.read(member);
//
//			if (member != null) {
//				if ("1".equals(member.getMemberLevel()) || "2".equals(member.getMemberLevel())) {
//					announcePermission = 2;
//				} else if ("3".equals(member.getMemberLevel())) {
//					announcePermission = 1;
//				} else {
//					announcePermission = 0;
//				}
//			}
//		}

		return announcePermission;
	}

}
