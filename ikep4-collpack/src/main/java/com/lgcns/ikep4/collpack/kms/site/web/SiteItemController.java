package com.lgcns.ikep4.collpack.kms.site.web;

import java.io.IOException;
import java.util.List;
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

import com.lgcns.ikep4.collpack.kms.board.model.BoardPermission;
import com.lgcns.ikep4.collpack.kms.site.model.SiteCode;
import com.lgcns.ikep4.collpack.kms.site.model.SiteItem;
import com.lgcns.ikep4.collpack.kms.site.search.SiteItemSearchCondition;
import com.lgcns.ikep4.collpack.kms.site.service.SiteItemService;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxValidationException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AuthorizedException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.util.model.ModelBeanUtil;


@Controller("kmsSiteItemController")
@RequestMapping(value = "/collpack/kms/site")
@SessionAttributes("siteItem")
public class SiteItemController extends BaseController {

//	private static final String COLLABORATION_MANAGER = "Collaboration";
	private static final String KMS_MANAGER = "Kms";

	@Autowired
	private SiteItemService siteItemService;

//	@Autowired
//	private MemberService memberService;

	@Autowired
	private ACLService aclService;

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
	@RequestMapping(value = "/listSiteItemView")
//	public ModelAndView listSiteItemView(String workspaceId, SiteItemSearchCondition searchCondition,
	public ModelAndView listSiteItemView(SiteItemSearchCondition searchCondition,
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
		SearchResult<SiteItem> searchResult = null;

		searchResult = this.siteItemService.listSiteItemBySearchCondition(searchCondition);

		ModelAndView modelAndView = new ModelAndView(viewName).addObject("siteCode", new SiteCode())
				.addObject("user", user)
//				.addObject("workspaceId", workspaceId)
//				.addObject("sitePermission", getSitePermission(user, workspaceId))
				.addObject("sitePermission", getSitePermission(user))
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
	@RequestMapping(value = "/readSiteItemView")
//	public ModelAndView readSiteItemView(@RequestParam("itemId") String itemId,
//			@RequestParam(value = "layoutType", defaultValue = "layoutNormal") String layoutType,
//			@RequestParam(value = "docPopup", defaultValue = "false") Boolean docPopup,
//			@RequestParam(value = "workspaceId", required = false) String workspaceId) throws JsonGenerationException,
//			JsonMappingException, IOException {
	public ModelAndView readSiteItemView(@RequestParam("itemId") String itemId,
			@RequestParam(value = "layoutType", defaultValue = "layoutNormal") String layoutType,
			@RequestParam(value = "docPopup", defaultValue = "false") Boolean docPopup) throws JsonGenerationException,
			JsonMappingException, IOException {		

		User user = readUser();
		String viewName = "";

		Boolean popup = false;

		int sitePermission = 0;
//		sitePermission = getSitePermission(user, workspaceId);
		sitePermission = getSitePermission(user);

		if (sitePermission == 0) {
			throw new IKEP4AuthorizedException();
		}


		// 게시글 정보를 가져온다. 파일정보와 관련 글 정보가 포함되어 있다.
//		SiteItem siteItem = this.siteItemService.readSiteItem(user.getUserId(), itemId, workspaceId);
		SiteItem siteItem = this.siteItemService.readSiteItem(user.getUserId(), itemId);

		if (siteItem == null) {
			throw new IKEP4AuthorizedException(messageSource, "message.collpack.kms.common.boardItem.deletedItem");
		}

		if ("layoutHorizental".equals(layoutType)) {
			viewName = "collpack/kms/site/readSiteItemFrameView";

		} else if ("layoutVertical".equals(layoutType)) {
			viewName = "collpack/kms/site/readSiteItemFrameView";

		} else if ("layoutNormal".equals(layoutType)) {
			viewName = "collpack/kms/site/readSiteItemView";
		}

//		if (StringUtil.isEmpty(workspaceId)) {
//			popup = true;
//		} else {
			popup = docPopup;
//		}

		if (popup) {
			viewName = "collpack/kms/site/readSiteItemPopupView";
		}
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		String portalId = portal.getPortalId();

//		Workspace workspace = new Workspace();
//		workspace = siteItemService.getWorkspaceInfo(portalId, workspaceId);

		int isOrganization = 0;
//		if (workspace != null) {
//			isOrganization = workspace.getIsOrganization();
//		}

		ObjectMapper mapper = new ObjectMapper();

		// 파일 목록을 JSON으로 변환한다.
		String fileDataListJson = null;

		if (siteItem.getFileDataList() != null) {
			fileDataListJson = mapper.writeValueAsString(siteItem.getFileDataList());
		}

		ModelAndView modelAndView = new ModelAndView(viewName).addObject("siteItem", siteItem)
				.addObject("layoutType", layoutType).addObject("docPopup", popup)
				.addObject("isOrganization", isOrganization).addObject("sitePermission", sitePermission)
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
	@RequestMapping(value = "/readSiteItemPopupView")
	public ModelAndView readSiteItemPopupView(@RequestParam("itemId") String itemId,
			@RequestParam(value = "layoutType", defaultValue = "layoutNormal") String layoutType,
			@RequestParam(value = "docPopup", defaultValue = "false") Boolean docPopup,
			@RequestParam(value = "workspaceId", required = false) String workspaceId) throws JsonGenerationException,
			JsonMappingException, IOException {

		User user = readUser();
		String viewName = "";

		Boolean popup = false;

		int sitePermission = 0;
//		sitePermission = getSitePermission(user, workspaceId);
		sitePermission = getSitePermission(user);

		if (sitePermission == 0) {
			throw new IKEP4AuthorizedException();
		}

		// 게시글 정보를 가져온다. 파일정보와 관련 글 정보가 포함되어 있다.
//		SiteItem siteItem = this.siteItemService.readSiteItem(user.getUserId(), itemId, workspaceId);
		SiteItem siteItem = this.siteItemService.readSiteItem(user.getUserId(), itemId);

		if (siteItem == null) {
			throw new IKEP4AuthorizedException(messageSource, "message.collpack.kms.common.boardItem.deletedItem");
		}

		if ("layoutHorizental".equals(layoutType)) {
			viewName = "collpack/kms/site/readSiteItemFrameView";

		} else if ("layoutVertical".equals(layoutType)) {
			viewName = "collpack/kms/site/readSiteItemFrameView";

		} else if ("layoutNormal".equals(layoutType)) {
			viewName = "collpack/kms/site/readSiteItemView";
		}

		if (StringUtil.isEmpty(workspaceId)) {
			popup = true;
		} else {
			popup = docPopup;
		}

		if (popup) {
			viewName = "collpack/kms/site/readSiteItemPopupView";
		}
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		String portalId = portal.getPortalId();

//		Workspace workspace = new Workspace();
//		workspace = siteItemService.getWorkspaceInfo(portalId, workspaceId);

		int isOrganization = 0;
//		if (workspace != null) {
//			isOrganization = workspace.getIsOrganization();
//		}

		ObjectMapper mapper = new ObjectMapper();

		// 파일 목록을 JSON으로 변환한다.
		String fileDataListJson = null;

		if (siteItem.getFileDataList() != null) {
			fileDataListJson = mapper.writeValueAsString(siteItem.getFileDataList());
		}

		ModelAndView modelAndView = new ModelAndView(viewName).addObject("siteItem", siteItem)
				.addObject("layoutType", layoutType).addObject("docPopup", popup)
				.addObject("isOrganization", isOrganization).addObject("sitePermission", sitePermission)
				.addObject("user", user).addObject("fileDataListJson", fileDataListJson);

		return modelAndView;
	}

	/**
	 * ANNOUNCE 게시물 등록 화면
	 * 
	 * @param workspaceId
	 * @return
	 */
	@RequestMapping(value = "/createSiteItemView")
	public ModelAndView createSiteItemView() {

		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		String portalId = portal.getPortalId();
//		Workspace workspace = new Workspace();
		SiteItem siteItem = new SiteItem();

//		workspace = siteItemService.getWorkspaceInfo(portalId, workspaceId);

//		siteItem.setWorkspaceId(workspaceId);
//		siteItem.setWorkspaceName(workspace.getWorkspaceName());

		User user = readUser();

		// 권한체크 0:비회원,1:회원,2:운영진,3:시스템/Coll관리자
		// 권한 없을때 Forwarding 정책필요
		// int sitePermission = getSitePermission(user, workspaceId);

		// ActiveX editor 사용여부 확인
		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		String useActiveX = "N";
		useActiveX = commonprop.getProperty("ikep4.activex.editor.use");

		return new ModelAndView().addObject("siteItem", siteItem).addObject("user", user)
				.addObject("useActiveX", useActiveX);
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
	@RequestMapping(value = "/updateSiteItemView")
	public ModelAndView updateSiteItemView(@RequestParam("itemId") String itemId) throws JsonGenerationException, JsonMappingException,
			IOException {

		// 권한체크 0:비회원,1:회원,2:운영진,3:시스템/Coll관리자
		// 권한 없을때 Forwarding 정책필요
		// int sitePermission = getSitePermission(user, workspaceId);
		User user = readUser();

//		SiteItem siteItem = this.siteItemService.readSiteItem(user.getUserId(), itemId, workspaceId);
		SiteItem siteItem = this.siteItemService.readSiteItem(user.getUserId(), itemId);

		ObjectMapper mapper = new ObjectMapper();

		String fileDataListJson = mapper.writeValueAsString(siteItem.getFileDataList());
		String tagListJson = mapper.writeValueAsString(siteItem.getTagList());

		// ActiveX editor 사용여부 확인
		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		String useActiveX = "N";
		useActiveX = commonprop.getProperty("ikep4.activex.editor.use");

		return new ModelAndView().addObject("siteItem", siteItem).addObject("user", user)
				.addObject("tagListJson", tagListJson).addObject("fileDataListJson", fileDataListJson)
				.addObject("useActiveX", useActiveX);
	}

	/**
	 * 관리자에 의한 ANNOUNCE 게시물 등록
	 * 
	 * @param siteItem
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/createSiteItem")
	public ModelAndView createSiteItem(@Valid SiteItem siteItem,
			SiteItemSearchCondition searchCondition, BindingResult result, SessionStatus status) {

		// 권한체크 0:비회원,1:회원,2:운영진,3:시스템/Coll관리자
		// 권한 없을때 Forwarding 정책필요
		// int sitePermission = getSitePermission(user, workspaceId);
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
		// log.debug(siteItem.getEndDate());
		// }
		User user = (User) getRequestAttribute("ikep.user");
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		siteItem.setPortalId(portal.getPortalId());

		ModelBeanUtil.bindRegisterInfo(siteItem, user.getUserId(), user.getUserName());

		String itemId = this.siteItemService.createSiteItem(siteItem, user);

		// SiteItemSearchCondition searchCondition =
		// (SiteItemSearchCondition)
		// this.getRequestAttribute("searchCondition");

		status.setComplete();

		// this.setRequestAttribute("searchCondition", searchCondition);

		return new ModelAndView("redirect:/collpack/kms/site/readSiteItemView.do")
				.addObject("itemId", itemId).addObject("searchCondition", searchCondition)
//				.addObject("workspaceId", siteItem.getWorkspaceId());
				.addObject("workspaceId", "");
	}

	/**
	 * ANNOUNCE 게시물 수정모듈
	 * 
	 * @param siteItem
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/updateSiteItem")
	public ModelAndView updateSiteItem(@Valid SiteItem siteItem, BindingResult result, SessionStatus status) {

		// 권한체크 0:비회원,1:회원,2:운영진,3:시스템/Coll관리자
		// 권한 없을때 Forwarding 정책필요
		// int sitePermission = getSitePermission(user, workspaceId);

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

		ModelBeanUtil.bindRegisterInfo(siteItem, user.getUserId(), user.getUserName());
		siteItem.setPortalId(portal.getPortalId());

		this.siteItemService.updateSiteItem(siteItem, user);

		status.setComplete();

		return new ModelAndView("redirect:/collpack/kms/site/readSiteItemView.do").addObject(
				"itemId", siteItem.getItemId());
//				.addObject("workspaceId", siteItem.getWorkspaceId());
		
	}

	/**
	 * 관리자에 의한 ANNOUNCE 게시물 물리적 삭제 (다중삭제시)
	 * 
	 * @param itemId
	 * @param workspaceId
	 * @return
	 */
	@RequestMapping(value = "/adminMultiDeleteSiteItem")
	public @ResponseBody
	void adminMultiDeleteAnnouncItem(@RequestParam("siteItemIds") List<String> siteItemIds) {

		try {
			// 권한체크 0:비회원,1:회원,2:운영진,3:시스템/Coll관리자
			// 권한 없을때 Forwarding 정책필요
			// int sitePermission = getSitePermission(user,
			// workspaceId);
			Portal portal = (Portal) getRequestAttribute("ikep.portal");
//			this.siteItemService.adminMultiDeleteSiteItem(siteItemIds, workspaceId, portal.getPortalId());
			this.siteItemService.adminMultiDeleteSiteItem(siteItemIds, portal.getPortalId());
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
	@RequestMapping(value = "/adminDeleteSiteItem")
	public ModelAndView adminDeleteSiteItem(@RequestParam("itemId") String itemId) {
		// 권한체크 0:비회원,1:회원,2:운영진,3:시스템/Coll관리자
		// 권한 없을때 Forwarding 정책필요
		// int sitePermission = getSitePermission(user, workspaceId);
		User user = readUser();
//		SiteItem anounceItem = siteItemService.readSiteItem(user.getUserId(), itemId, workspaceId);
		SiteItem anounceItem = siteItemService.readSiteItem(user.getUserId(), itemId);

		this.siteItemService.adminDeleteSiteItem(anounceItem);

		return new ModelAndView("redirect:/collpack/kms/site/listSiteItemView.do?workspaceId="
//				+ anounceItem.getWorkspaceId());
				+ "");
	}

//	/**
//	 * // * 하위부서중 개설된 WORKSPACE 리스트
//	 * 
//	 * @param siteItemId
//	 * @param workspaceId
//	 * @param searchCondition
//	 * @param result
//	 * @param status
//	 * @return
//	 */
//	@RequestMapping(value = "/shareSiteItemPop")
//	public ModelAndView shareSiteItemPop(@RequestParam("itemId") String siteItemId,
//			@RequestParam("workspaceId") String workspaceId, SiteItemSearchCondition searchCondition,
//			BindingResult result, SessionStatus status) {
//		try {
//			// 권한체크 0:비회원,1:회원,2:운영진,3:시스템/Coll관리자
//			// 권한 없을때 Forwarding 정책필요
//			// int sitePermission = getSitePermission(user,
//			// workspaceId);
//
//			Portal portal = (Portal) getRequestAttribute("ikep.portal");
//
//			SearchResult<SiteItem> searchResult = null;
//			searchCondition.setWorkspaceId(workspaceId);
//			searchCondition.setItemId(siteItemId);
//			searchCondition.setPortalId(portal.getPortalId());
//			searchResult = siteItemService.listChildWorkspaceInfoBySearchCondition(searchCondition);
//
//			return new ModelAndView("collpack/kms/site/shareSiteItemPop")
//					.addObject("workspaceId", workspaceId)
//					.addObject("searchCondition", searchResult.getSearchCondition())
//					.addObject("searchResult", searchResult).addObject("itemId", siteItemId);
//
//		} catch (Exception exception) {
//			throw new IKEP4AjaxException("code", exception);
//		}
//	}

//	/**
//	 * 선택된 공지사항 게시물 공유
//	 * 
//	 * @param siteItemId
//	 * @param workspaceIds
//	 * @param workspaceId
//	 */
//	@RequestMapping(value = "/shareCollSiteItem")
//	public @ResponseBody
//	void shareCollSiteItem(@RequestParam("itemId") String siteItemId,
//			@RequestParam(value = "workspaceIds", required = false) List<String> workspaceIds,
//			@RequestParam("workspaceId") String workspaceId) {
//		if (!StringUtil.isEmpty(siteItemId)) {
//			siteItemService.createSiteLinkItem(siteItemId, workspaceIds);
//		}
//	}

	/**
	 * Workspace Site 게시판 권한 가져오기 //권한체크
	 * 0:비회원,준회원,1:정회원,2:운영진,시샵,3:시스템,Coll관리자
	 * 
	 * @param userId
	 * @param workspaceId
	 * @return
	 */
//	public int getSitePermission(User user, String workspaceId) {
	public int getSitePermission(User user) {
		
		boolean isSystemAdmin = isSystemAdmin(user);

//		int sitePermission = 0;
		int sitePermission = 0;
		if (isSystemAdmin) {
			sitePermission = 3;
		} 
		else {
		
			/** 사무직일때 **/
			String jobClassCode = user.getJobClassCode();
			if("11".equals(jobClassCode) || "12".equals(jobClassCode) || "21".equals(jobClassCode) || "22".equals(jobClassCode)|| "24".equals(jobClassCode)|| "31".equals(jobClassCode)){
				sitePermission = 1;
			}
		
		
//			Member member = new Member();
//			member.setMemberId(user.getUserId());
//			member.setWorkspaceId(workspaceId);
//			member = memberService.read(member);
//
//			if (member != null) {
//				if ("1".equals(member.getMemberLevel()) || "2".equals(member.getMemberLevel())) {
//					sitePermission = 2;
//				} else if ("3".equals(member.getMemberLevel())) {
//					sitePermission = 1;
//				} else {
//					sitePermission = 0;
//				}
//			}
		}

		return sitePermission;
	}

}
