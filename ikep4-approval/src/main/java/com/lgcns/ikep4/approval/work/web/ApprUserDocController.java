/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.work.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.approval.admin.model.ApprAdminConfig;
import com.lgcns.ikep4.approval.admin.model.CommonCode;
import com.lgcns.ikep4.approval.admin.service.ApprAdminConfigService;
import com.lgcns.ikep4.approval.work.model.ApprList;
import com.lgcns.ikep4.approval.work.model.ApprUserDocFolder;
import com.lgcns.ikep4.approval.work.search.ApprListSearchCondition;
import com.lgcns.ikep4.approval.work.service.ApprListService;
import com.lgcns.ikep4.approval.work.service.ApprUserDocService;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.security.acl.validation.AccessingResult;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.util.web.TreeMaker;

/**
 * 개인보관함
 * 
 * @author 서지혜
 * @version $Id$
 */
@Controller
@RequestMapping(value = "/approval/work/userdoc")
@SessionAttributes("entrust")
public class ApprUserDocController extends BaseController {

	@Autowired
	private ApprUserDocService apprUserDocService;

	@Autowired
	private ApprListService apprListService;

	@Autowired
	ApprAdminConfigService apprAdminConfigService;

	@Autowired
	private ACLService aclService;

	/**
	 * 로그인 사용자가 전자결재의 시스템 관리자인지 체크한다.
	 * 
	 * @param user
	 *            로그인 사용자 모델 객체
	 * @return 시스템 관리자 여부
	 */
	public boolean isSystemAdmin(User user) {
		return this.aclService.isSystemAdmin("Approval", user.getUserId());
	}

	/**
	 * 환경 설정에 정의된 값을 조회한다
	 * 
	 * @param portalId
	 * @return
	 */
	public boolean isReadAll(String portalId) {

		boolean isRead = false;
		ApprAdminConfig apprAdminConfig = apprAdminConfigService
				.adminConfigDetail(portalId);
		if (apprAdminConfig.getIsReadAll().equals("1")) {
			// IKEP4_APPR_READ_ALL에 존재하는지 확인
			User user = (User) getRequestAttribute(CommonCode.IKEP_USER);
			isRead = apprAdminConfigService.existReadAllAuth(user.getUserId());
		}
		return isRead;
	}

	/**
	 * 개인보관함 트리 조회
	 * 
	 * @return
	 */
	@RequestMapping(value = "/apprUserDocMain.do")
	public ModelAndView userDocMain() {

		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");

		Map<String, String> map = new HashMap<String, String>();
		map.put("portalId", portal.getPortalId());
		map.put("userId", user.getUserId());
		map.put("useMenu", "");

		ApprUserDocFolder apprUserDocFolder = new ApprUserDocFolder();

		// category tree json 생성
		String categoryTreeJson = "";

		try {
			// if(!"".equals(folderParentId)){
			List<ApprUserDocFolder> apprUserDocFolderList = apprUserDocService
					.treeList(map);
			categoryTreeJson = TreeMaker
					.init(apprUserDocFolderList, "folderId", "folderParentId",
							"folderName").create().toJsonString();
			// }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new ModelAndView("/approval/work/userdoc/apprUserDocMain")
				.addObject("isSystemAdmin", this.isSystemAdmin(user))
				.addObject("isReadAll", this.isReadAll(user.getPortalId()))
				.addObject("categoryTreeJson", categoryTreeJson)
				.addObject("apprUserDocFolder", apprUserDocFolder)
				.addObject("boardRootId", user.getUserId());
	}

	/**
	 * 트리 목록
	 * 
	 */

	@RequestMapping(value = "/apprUserDocMenu")
	public ModelAndView apprUserDocMenu(
			@RequestParam(value = "usePop", required = false) String usePop,
			@RequestParam(value = "apprId", required = false) String apprId) {

		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");

		Map<String, String> map = new HashMap<String, String>();
		map.put("portalId", portal.getPortalId());
		map.put("userId", user.getUserId());
		map.put("useMenu", "Y");

		ApprUserDocFolder apprUserDocFolder = new ApprUserDocFolder();

		// category tree json 생성
		String categoryTreeJson = "";

		try {
			// if(!"".equals(folderParentId)){
			List<ApprUserDocFolder> apprUserDocFolderList = apprUserDocService
					.treeList(map);
			categoryTreeJson = TreeMaker
					.init(apprUserDocFolderList, "folderId", "folderParentId",
							"folderName").create().toJsonString();
			// }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new ModelAndView("/approval/work/userdoc/apprUserDocMenu")
				.addObject("categoryTreeJson", categoryTreeJson)
				.addObject("usePop", usePop)
				.addObject("apprId", apprId);
	}
	
	@RequestMapping(value = "/apprUserDocLeftMenu")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String apprUserDocLeftMenu(SessionStatus status) {

		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");

		Map<String, String> map = new HashMap<String, String>();
		map.put("portalId", portal.getPortalId());
		map.put("userId", user.getUserId());
		map.put("useMenu", "Y");

		// category tree json 생성
		String categoryTreeJson = "";

		try {
			// if(!"".equals(folderParentId)){
			List<ApprUserDocFolder> apprUserDocFolderList = apprUserDocService
					.treeList(map);
			categoryTreeJson = TreeMaker
					.init(apprUserDocFolderList, "folderId", "folderParentId",
							"folderName").create().toJsonString();
			// }
			status.setComplete();	
		} catch (Exception ex) {
			throw new IKEP4AjaxException("apprUserDocLeftMenu", ex);
		}

		return categoryTreeJson;
	}
	
	@RequestMapping(value = "/checkExist")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody boolean checkExist(SessionStatus status,
			@RequestParam(value = "apprId", required = false) String apprId,
			@RequestParam(value = "folderId", required = false) String folderId) {

		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");

		Map<String, String> map = new HashMap<String, String>();
		map.put("apprId", apprId);
		map.put("folderId", folderId);

		boolean existDoc = false;
		try {
			
			existDoc = apprUserDocService.checkExist(map);
			
			status.setComplete();	
		} catch (Exception ex) {
			throw new IKEP4AjaxException("checkExist", ex);
		}

		return existDoc;
	}
	
	@RequestMapping(value = "/existFolderName")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody boolean existFolderName(SessionStatus status,
			@RequestParam(value = "folderName", required = false) String folderName) {

		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");

		Map<String, String> map = new HashMap<String, String>();
		map.put("folderName", folderName);

		boolean existDoc = false;
		try {
			
			existDoc = apprUserDocService.existFolderName(map);
			
			status.setComplete();	
		} catch (Exception ex) {
			throw new IKEP4AjaxException("existFolderName", ex);
		}

		return existDoc;
	}

	/**
	 * 게시판 생성 화면 컨트롤 메서드
	 * 
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/createFolder")
	public ModelAndView createFolder(
			ApprUserDocFolder apprUserDocFolder,
			@RequestParam(value = "folderParentId", required = false) String folderParentId,
			@RequestParam(value = "folderId", required = false) String folderId) {

		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");

		apprUserDocFolder.setPortalId(portal.getPortalId());
		apprUserDocFolder.setUserId(user.getUserId());
		apprUserDocFolder.setRegisterId(user.getUserId());
		apprUserDocFolder.setRegisterName(user.getUserName());

		if (StringUtil.isEmpty(folderId)) {
			apprUserDocService.createFolder(apprUserDocFolder);
		} else {
			apprUserDocService.updateFolder(apprUserDocFolder);
		}

		return new ModelAndView(
				"redirect:/approval/work/userdoc/apprUserDocMain.do")
				.addObject("isSystemAdmin", this.isSystemAdmin(user))
				.addObject("isReadAll", this.isReadAll(user.getPortalId()));
	}

	/**
	 * 완료 > 전체문서
	 * 
	 * @param apprListSearchCondition
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/listApprUserDoc.do")
	public ModelAndView listApprUserDoc(
			ApprListSearchCondition apprListSearchCondition,
			@RequestParam(value = "folderId", required = false) String folderId)
			throws ServletException, IOException {

		ModelAndView mav = new ModelAndView(
				"/approval/work/userdoc/listApprUserDoc");

		if (apprListSearchCondition == null)
			apprListSearchCondition = new ApprListSearchCondition();

		User user = (User) getRequestAttribute(CommonCode.IKEP_USER);
		String portalId = (String) getRequestAttribute("ikep.portalId");
		String userId = user.getUserId();

		// 개인보관함 listType
		String listType = "listApprUserDoc";

		apprListSearchCondition.setUserId(userId);
		apprListSearchCondition.setListType(listType);
		apprListSearchCondition.setGroupId(user.getGroupId());
		apprListSearchCondition.setPortalId(portalId);
		apprListSearchCondition.setFolderId(folderId);

		// Default 검색 조건
		if (apprListSearchCondition.getSortColumn() == null
				|| apprListSearchCondition.getSortColumn().equals("")) {
			apprListSearchCondition.setSortColumn("apprEndDate");
			apprListSearchCondition.setSortType("DESC");
		}

		ApprUserDocFolder apprUserDocFolder = new ApprUserDocFolder();
		apprUserDocFolder = apprUserDocService.getFolderInfo(folderId);

		SearchResult<ApprList> searchResult = null;
		searchResult = this.apprListService
				.listBySearchConditionUserDoc(apprListSearchCondition);

		mav.addObject("searchResult", searchResult);
		mav.addObject("searchCondition", searchResult.getSearchCondition());
		mav.addObject("apprUserDocFolder", apprUserDocFolder);
		mav.addObject("commonCode", ApprList.getPageNumList());
		mav.addObject("isSystemAdmin", this.isSystemAdmin(user));
		mav.addObject("isReadAll", this.isReadAll(portalId));

		return mav;
	}
	
	/**
	 * 공람지정 저장
	 * 
	 * @param apprId
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/createApprUserDoc.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	String createApprDisplay(@RequestParam(value = "apprId", required = false) String apprId, 
			@RequestParam(value = "folderId", required = false) String folderId,
			SessionStatus status) {

		String rtnValue = "";
		try {
			User user = (User) getRequestAttribute("ikep.user");
			
			ApprUserDocFolder apprUserDocFolder = new ApprUserDocFolder();
			
			apprUserDocFolder.setFolderId(folderId);
			apprUserDocFolder.setApprId(apprId);
			apprUserDocFolder.setRegisterId(user.getUserId());
			apprUserDocFolder.setRegisterName(user.getUserName());
			
			apprUserDocService.createApprUserDoc(apprUserDocFolder); 
			
			// 세션 상태를 complete
			status.setComplete();
			rtnValue = "OK";

		} catch (Exception ex) {

			throw new IKEP4AjaxException("createApprUserDoc", ex);
		}

		return rtnValue;

	}
	
	/**
	 * 자장한 문서 삭제
	 * 
	 * @param apprIds
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/deleteApprUserDocList.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	String deleteApprUserDocList(@RequestParam(value = "apprIds", required = false) List<String> apprIds,
			@RequestParam(value = "folderId", required = false) String folderId,
			SessionStatus status) {

		String returnValue = "";

		try {
			if(StringUtil.isEmpty(folderId)) {
				for (int i = 0; i < apprIds.size(); i++) {

					String apprId = apprIds.get(i);
					
					Map<String, String> map = new HashMap<String, String>();
					map.put("apprId", apprId);
					apprUserDocService.deleteApprUserDoc(map);
				}
				status.setComplete();
				returnValue = "OK";
			}else {
				
				//개인보관함 문서 삭제
				Map<String, String> map = new HashMap<String, String>();
				map.put("folderId", folderId);
				apprUserDocService.deleteApprUserDoc(map);
				
				// 폴더 삭제
				apprUserDocService.deleteApprDocFolder(folderId);
			}
			

		} catch (Exception ex) {
			throw new IKEP4AjaxException("deleteApprUserDocList", ex);
		}

		return returnValue;
	}
	
	/**
	 * 폴더 위치 이동 처리 비동기 컨트롤 메서드
	 * 
	 * @param boardId 이동 게시판 ID
	 * @param boardParentId 이동 게시판의 부모 ID
	 * @param sortOrder 이동 위치
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/updateMoveBoard")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	void updateMoveBoard(AccessingResult accessResult,
			@RequestParam(value = "folderId") String folderId,
			@RequestParam(value = "folderParentId", required = false) String folderParentId,
			@RequestParam(value = "sortOrder") Integer sortOrder) {

		
		ApprUserDocFolder apprUserDocFolder = new ApprUserDocFolder();
		
		apprUserDocFolder.setFolderId(folderId);
		apprUserDocFolder.setFolderParentId(folderParentId);
		apprUserDocFolder.setSortOrder(sortOrder);
		
		this.apprUserDocService.updateBoardMove(apprUserDocFolder);

	}
	

}
