/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.collaboration.main.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.collpack.collaboration.admin.model.WorkspaceType;
import com.lgcns.ikep4.collpack.collaboration.admin.service.WorkspaceTypeService;
import com.lgcns.ikep4.collpack.collaboration.alliance.model.Alliance;
import com.lgcns.ikep4.collpack.collaboration.alliance.service.AllianceService;
import com.lgcns.ikep4.collpack.collaboration.board.announce.model.AnnounceItem;
import com.lgcns.ikep4.collpack.collaboration.board.announce.service.AnnounceItemService;
import com.lgcns.ikep4.collpack.collaboration.board.board.dao.BoardDao;
import com.lgcns.ikep4.collpack.collaboration.board.board.model.Board;
import com.lgcns.ikep4.collpack.collaboration.board.board.model.BoardItem;
import com.lgcns.ikep4.collpack.collaboration.board.board.search.BoardItemSearchCondition;
import com.lgcns.ikep4.collpack.collaboration.board.board.service.BoardAdminService;
import com.lgcns.ikep4.collpack.collaboration.board.board.service.BoardItemService;
import com.lgcns.ikep4.collpack.collaboration.member.model.Member;
import com.lgcns.ikep4.collpack.collaboration.member.service.MemberService;
import com.lgcns.ikep4.collpack.collaboration.workspace.model.Congratulations;
import com.lgcns.ikep4.collpack.collaboration.workspace.model.Workspace;
import com.lgcns.ikep4.collpack.collaboration.workspace.model.WorkspaceCode;
import com.lgcns.ikep4.collpack.collaboration.workspace.model.WorkspacePortletLayout;
import com.lgcns.ikep4.collpack.collaboration.workspace.search.WorkspaceSearchCondition;
import com.lgcns.ikep4.collpack.collaboration.workspace.service.CongratulationsService;
import com.lgcns.ikep4.collpack.collaboration.workspace.service.WorkspacePortletLayoutService;
import com.lgcns.ikep4.collpack.collaboration.workspace.service.WorkspaceService;
import com.lgcns.ikep4.collpack.collaboration.workspaceMap.model.WorkspaceMap;
import com.lgcns.ikep4.collpack.collaboration.workspaceMap.service.WorkspaceMapAdminService;
import com.lgcns.ikep4.collpack.collaboration.workspaceMap.tree.TreeNodeAttrWorkspaceMap;
import com.lgcns.ikep4.collpack.common.tree.TreeManager;
import com.lgcns.ikep4.collpack.common.tree.TreeNode;
import com.lgcns.ikep4.collpack.common.tree.TreeNodeData;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.activitystream.model.ActivityCode;
import com.lgcns.ikep4.support.activitystream.model.ActivityStream;
import com.lgcns.ikep4.support.activitystream.model.ActivityStreamSearchCondition;
import com.lgcns.ikep4.support.activitystream.service.ActivityCodeService;
import com.lgcns.ikep4.support.activitystream.service.ActivityStreamService;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.tagging.constants.TagConstants;
import com.lgcns.ikep4.support.tagging.model.Tag;
import com.lgcns.ikep4.support.tagging.service.TagService;
import com.lgcns.ikep4.support.timezone.service.TimeZoneSupportService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.lang.DateUtil;
import com.lgcns.ikep4.util.lang.StringUtil;


/**
 * Collaboration Main Controller
 * 
 * @author 김종철(happyi1018@nate.com)
 * @version $Id: CollaborationController.java 15616 2011-06-27 05:33:17Z
 *          loverfairy $
 */

@Controller
@RequestMapping(value = "/collpack/collaboration/main")
@SessionAttributes("collaboration")
public class CollaborationController extends BaseController {

	@Autowired
	private WorkspaceTypeService workspaceTypeService;

	@Autowired
	private WorkspaceService workspaceService;

	@Autowired
	private CongratulationsService congratulationsService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private BoardItemService boardItemService;

	@Autowired
	private BoardAdminService boardAdminService;

	@Autowired
	private BoardDao boardDao;

	@Autowired
	private AllianceService allianceService;

	@Autowired
	private WorkspaceMapAdminService workspaceMapAdminService;

	private static final String DEFAULT_BOARD_ROOT_ID = "0";

	@Autowired
	private WorkspacePortletLayoutService workspacePortletLayoutService;

	@Autowired
	private ActivityStreamService activityStreamService;

	@Autowired
	private ActivityCodeService activityCodeService;

	@Autowired
	private AnnounceItemService announceItemService;

	@Autowired
	private TagService tagService;

	@Autowired
	private ACLService aclService;

	@Autowired
	private TimeZoneSupportService timeZoneSupportService;

	private static final String COLLABORATION_MANAGER = "Collaboration";

	/**
	 * Coll 전체 관리자 여부 체크
	 * 
	 * @param userId
	 * @return
	 */
	public boolean isSystemAdmin(String userId) {

		boolean isSystemAdmin = aclService.isSystemAdmin(COLLABORATION_MANAGER, userId);

		return isSystemAdmin;
	}

	/**
	 * 시샵 이상 여부
	 * 
	 * @param workspaceId
	 * @param userId
	 * @return
	 */
	public boolean isWorkspaceAdmin(String workspaceId, String userId) {

		// 권한 정보 조회 (isPermission : Manager:관리자 )

		boolean isAdmin = false;

		// 시스템,Collaboration관리자 체크
		Boolean isSystemAdmin = isSystemAdmin(userId);

		if (isSystemAdmin) {
			isAdmin = true; // Collaboration 서비스 관리자
		} else {

			// 시샵, 운영진 체크
			Member member = new Member();
			member.setWorkspaceId(workspaceId);
			member.setMemberId(userId);

			member = memberService.read(member);

			// 시샵 또는 운영진
			if (member != null && (member.getMemberLevel().equals("1"))) {
				isAdmin = true;
			}
		}

		return isAdmin;
	}

	/**
	 * 운영진 이상 여부
	 * 
	 * @param workspaceId
	 * @param userId
	 * @return
	 */
	public boolean isWorkspaceManager(String workspaceId, String userId) {

		// 권한 정보 조회 (isPermission : Manager:관리자 )

		boolean isAdmin = false;

		// 시스템,Collaboration관리자 체크
		Boolean isSystemAdmin = isSystemAdmin(userId);

		if (isSystemAdmin) {
			isAdmin = true; // Collaboration 서비스 관리자
		} else {

			// 시샵, 운영진 체크
			Member member = new Member();
			member.setWorkspaceId(workspaceId);
			member.setMemberId(userId);

			member = memberService.read(member);

			// 시샵 또는 운영진
			if (member != null && (member.getMemberLevel().equals("1") || member.getMemberLevel().equals("2"))) {
				isAdmin = true;
			}
		}

		return isAdmin;
	}

	/**
	 * Collaboration 메인
	 * 
	 * @return
	 */
	@RequestMapping(value = "/Collaboration.do", method = RequestMethod.GET)
	public ModelAndView collaboration() {
		ModelAndView modelAndView = new ModelAndView("collpack/collaboration/main/Collaboration");

		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");

		Workspace workspace = new Workspace();
		/*
		 * 랜덤 WS 4건 가져오기
		 */
		List<Workspace> workspaceList = workspaceService.randomWorkspace(portal.getPortalId());
		/*
		 * Ws Type 별 Count
		 */
		List<WorkspaceType> workspaceTypeList = workspaceTypeService.countWorkspaceByType(user.getPortalId());

		Map<String, String> map = new HashMap<String, String>();
		map.put("portalId", portal.getPortalId());
		map.put("memberId", user.getUserId());

		/*
		 * My Collaboration
		 */
		List<Workspace> myWorkspaceList = null;
		myWorkspaceList = workspaceService.listMyCollaboration(map);

		/*
		 * New Collaboration
		 */
		List<Workspace> workspaceNewList = null;
		workspaceNewList = workspaceService.listNewCollaboration(map);

		/*
		 * 나의 Coll 중 최근 게시물 목록
		 */
		BoardItemSearchCondition searchCondition = new BoardItemSearchCondition();
		SearchResult<BoardItem> searchResult = null;
		searchCondition.setUserId(user.getUserId());

		searchResult = this.boardItemService.listMyCollBoardItemBySearchCondition(searchCondition, user);

		/*
		 * Coll 관리자 여부
		 */
		Boolean isSystemAdmin = isSystemAdmin(user.getUserId());

		/*
		 * 태그정보
		 */
		/**
		 * Tag tag = new Tag();
		 * tag.setTagItemType(TagConstants.ITEM_TYPE_WORKSPACE); //itemType
		 * TagConstants에서 모듈에 맞게 사용-team coll은 type ID를 넣으셔야 합니다.
		 * tag.setTagOrder(TagConstants.ORDER_TYPE_FREQUENCY); //정렬순
		 * TagConstants에 정의되어 있음. - 넣지않으면 인기순으로 가져옴.
		 * tag.setPortalId(portal.getPortalId()); //portalID
		 * tag.setEndRowIndex(20); //태그 가져올 개수
		 * tag.setIsNotSubType(TagConstants.IS_NOT_SUB_TYPE); List<Tag> tagList
		 * = tagService.list(tag);
		 **/

		modelAndView.addObject("myWorkspaceList", myWorkspaceList);
		modelAndView.addObject("workspaceNewList", workspaceNewList);
		modelAndView.addObject("workspace", workspace);
		modelAndView.addObject("boardItem", searchResult.getEntity());
		modelAndView.addObject("isSystemAdmin", isSystemAdmin);
		modelAndView.addObject("workspaceList", workspaceList);
		modelAndView.addObject("workspaceListCnt", workspaceList.size() > 4 ? 4 : workspaceList.size());
		modelAndView.addObject("workspaceTypeList", workspaceTypeList);
		// modelAndView.addObject("tagList",tagList);
		modelAndView.addObject("tagItemType", TagConstants.ITEM_TYPE_WORKSPACE);

		return modelAndView;
	}

	@RequestMapping(value = "/getSubListForMyWorkspace.do")
	public ModelAndView getSubListForMyWorkspace() {

		ModelAndView modelAndView = new ModelAndView("collpack/collaboration/main/subListForMyWorkspace");

		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");

		Map<String, String> map = new HashMap<String, String>();
		map.put("portalId", portal.getPortalId());
		map.put("memberId", user.getUserId());

		List<Workspace> myWorkspaceList = null;
		myWorkspaceList = workspaceService.listMyCollaboration(map);

		modelAndView.addObject("myWorkspaceList", myWorkspaceList);

		return modelAndView;

	}

	@RequestMapping("/tag.do")
	public ModelAndView tag() {
		Portal portal = (Portal) getRequestAttribute("ikep.portal");

		Tag tag = new Tag();

		// itemType TagConstants에서 모듈에 맞게 사용-team coll은 type ID를 넣으셔야 합니다.
		tag.setTagItemType(TagConstants.ITEM_TYPE_WORKSPACE);
		// 정렬순 TagConstants에 정의되어 있음. - 넣지않으면 인기순으로 가져옴.
		tag.setTagOrder(TagConstants.ORDER_TYPE_FREQUENCY);

		tag.setPortalId(portal.getPortalId()); // portalID
		tag.setEndRowIndex(20); // 태그 가져올 개수
		tag.setIsNotSubType(TagConstants.IS_NOT_SUB_TYPE);
		List<Tag> tagList = tagService.list(tag);

		// 아래 소스대로 그대로 사용하면됨.
		return new ModelAndView("support/tagging/tagXml").addObject("tagList", tagList) // 태그
																								// 리스트
				.addObject("itemType", tag.getTagItemType()) // item type
				.addObject("subItemType", tag.getTagItemSubType()); // sub item
																	// type

	}

	/**
	 * Collaboration 메인의 최근 게시물 목록
	 * 
	 * @param searchCondition
	 * @return
	 */
	@RequestMapping(value = "/mainBoardItemList.do", method = RequestMethod.POST)
	public ModelAndView mainBoardItemList(BoardItemSearchCondition searchCondition) {
		ModelAndView modelAndView = new ModelAndView("collpack/collaboration/main/mainBoardItemList");

		User user = (User) getRequestAttribute("ikep.user");

		/*
		 * 나의 Coll 중 최근 게시물 목록
		 */
		SearchResult<BoardItem> searchResult = null;
		searchCondition.setUserId(user.getUserId());

		searchResult = this.boardItemService.listMyCollBoardItemBySearchCondition(searchCondition, user);

		modelAndView.addObject("boardItem", searchResult.getEntity());
		modelAndView.addObject("searchCondition", searchCondition);
		return modelAndView;
	}

	/**
	 * Collaboration 검색 목록
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "/findCollaboration.do")
	public ModelAndView findCollaboration(@RequestParam("name") String name, WorkspaceSearchCondition searchCondition) {

		ModelAndView modelAndView = new ModelAndView();

		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");

		// List<Workspace> myWorkspaceList = null;

		// List<Workspace> workspaceNewList = null;
		WorkspaceCode workspaceCode = new WorkspaceCode();

		Map<String, String> map = new HashMap<String, String>();
		map.put("portalId", portal.getPortalId());
		map.put("memberId", user.getUserId());

		/**
		 * 나의 Workspace 목록
		 */
		// myWorkspaceList = workspaceService.listMyCollaboration(map);

		/**
		 * 신규 Workspace 목록
		 */
		// workspaceNewList = workspaceService.listNewCollaboration(map);

		// WorkspaceSearchCondition searchCondition = new
		// WorkspaceSearchCondition();
		searchCondition.setSearchColumn("WORKSPACE_NAME");
		searchCondition.setListType("find");
		searchCondition.setSearchWord(name);
		searchCondition.setPortalId(portal.getPortalId());

		if (StringUtil.isEmpty(searchCondition.getSortColumn())) {
			searchCondition.setSortColumn("openDate");
			searchCondition.setSortType("DESC");
		}
		/**
		 * else { if(searchCondition.getSortColumn().equals("workspaceName")) {
		 * searchCondition.setSortType("ASC"); } else {
		 * searchCondition.setSortType("DESC"); } }
		 **/
		// searchCondition.setSortColumn("workspaceName");
		// searchCondition.setSortType("ASC");

		/**
		 * workspace 목록 검색
		 */
		SearchResult<Workspace> searchResult = this.workspaceService.listBySearchCondition(searchCondition);

		modelAndView.setViewName("collpack/collaboration/main/findCollaboration");

		modelAndView.addObject("workspaceList", searchResult.getEntity());
		modelAndView.addObject("searchResult", searchResult);
		modelAndView.addObject("searchCondition", searchResult.getSearchCondition());

		modelAndView.addObject("workspaceCode", workspaceCode);

		/**
		 * Coll 관리자 여부
		 */
		// Boolean isSystemAdmin = isSystemAdmin(user.getUserId());

		/*
		 * 태그정보
		 */
		/*
		 * Tag tag = new Tag();
		 * tag.setTagItemType(TagConstants.ITEM_TYPE_WORKSPACE); //itemType
		 * TagConstants에서 모듈에 맞게 사용-team coll은 type ID를 넣으셔야 합니다.
		 * tag.setTagOrder(TagConstants.ORDER_TYPE_FREQUENCY); //정렬순
		 * TagConstants에 정의되어 있음. - 넣지않으면 인기순으로 가져옴.
		 * tag.setPortalId(portal.getPortalId()); //portalID
		 * tag.setEndRowIndex(20); //태그 가져올 개수
		 * tag.setIsNotSubType(TagConstants.IS_NOT_SUB_TYPE); List<Tag> tagList
		 * = tagService.list(tag);
		 */

		// modelAndView.addObject("tagList", tagList);
		modelAndView.addObject("tagItemType", TagConstants.ITEM_TYPE_WORKSPACE);
		// modelAndView.addObject("isSystemAdmin", isSystemAdmin);
		// modelAndView.addObject("myWorkspaceList", myWorkspaceList);
		// modelAndView.addObject("workspaceNewList", workspaceNewList);
		modelAndView.addObject("name", name);

		return modelAndView;
	}

	/**
	 * Collaboration 서브메뉴
	 * 
	 * @param searchCondition
	 * @return
	 */
	@RequestMapping(value = "/listCollaborationMain.do", method = RequestMethod.GET)
	public ModelAndView listCollaborationMain(WorkspaceSearchCondition searchCondition) {

		ModelAndView modelAndView = new ModelAndView("collpack/collaboration/main/listCollaborationMain");
		User user = (User) getRequestAttribute("ikep.user");
		Portal portal = (Portal) getRequestAttribute("ikep.portal");

		List<Workspace> workspaceList = null;

		modelAndView.addObject("searchCondition", searchCondition);

		Map<String, String> map = new HashMap<String, String>();
		map.put("portalId", user.getPortalId());
		map.put("memberId", user.getUserId());

		workspaceList = workspaceService.listMyCollaboration(map);

		/**
		 * 타입 목록
		 */
		List<WorkspaceType> workspaceTypeList = workspaceTypeService.listWorkspaceTypeAll(portal.getPortalId());
		/**
		 * Collaboration 시스템 관리자 여부
		 */
		Boolean isSystemAdmin = isSystemAdmin(user.getUserId());

		/*
		 * 태그정보
		 */
		Tag tag = new Tag();
		tag.setTagItemType(TagConstants.ITEM_TYPE_WORKSPACE); // itemType
																// TagConstants에서
																// 모듈에 맞게
																// 사용-team coll은
																// type ID를 넣으셔야
																// 합니다.
		tag.setTagOrder(TagConstants.ORDER_TYPE_FREQUENCY); // 정렬순 TagConstants에
															// 정의되어 있음. - 넣지않으면
															// 인기순으로 가져옴.
		tag.setPortalId(portal.getPortalId()); // portalID
		tag.setEndRowIndex(20); // 태그 가져올 개수

		tag.setIsNotSubType(TagConstants.IS_NOT_SUB_TYPE);
		List<Tag> tagList = tagService.list(tag);

		modelAndView.addObject("tagList", tagList);
		modelAndView.addObject("tagItemType", TagConstants.ITEM_TYPE_WORKSPACE);
		modelAndView.addObject("isSystemAdmin", isSystemAdmin);
		modelAndView.addObject("workspaceTypeList", workspaceTypeList);
		modelAndView.addObject("workspaceList", workspaceList);
		modelAndView.addObject("searchCondition", searchCondition);

		return modelAndView;
	}

	/**
	 * 개별 Collaboration 서브메뉴
	 * 
	 * @param workspaceId
	 * @param boardId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/Workspace.do", method = RequestMethod.GET)
	public String workspace(@RequestParam(value = "workspaceId", required = false) String workspaceId,
			@RequestParam(value = "boardId", required = false) String boardId, Model model) {

		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");

		Workspace workspace = new Workspace();

		/**
		 * workspaceId 가 없는경우 디폴트 Workspace 확인후 해당 Workspace 이동 ( 1건만 조회후 이동 -
		 * 버그 또는 DB 직접 변경으로 인한 다중 Row 발생으로 인한 오류 방지 ) 디폴트 Workspace 없는경우 해당 팀
		 * Workspace 확인후 이동 ( 1건만 조회후 이동 - 버그 또는 DB 직접 변경으로 인한 다중 Row 발생으로 인한 오류
		 * 방지 ) 해당 정보가 없는 경우는 Collaboration 메인 화면으로 이동
		 */
		if (workspaceId == null || workspaceId.equals("")) {

			// 디폴트 WS 체크
			Map<String, String> wsMap = new HashMap<String, String>();
			wsMap.put("portalId", portal.getPortalId());
			wsMap.put("memberId", user.getUserId());
			workspace = workspaceService.getDefaultWorkspace(wsMap);

			// 개인의 팀 WS 체크
			if (workspace == null) {
				wsMap.put("teamId", user.getGroupId());
				workspace = workspaceService.getDefaultWorkspace(wsMap);
			}
			// 디폴트 Workspace 가 없는 경우 Collaboration 메인 화면으로 이동
			if (workspace == null) {
				return "redirect:/collpack/collaboration/main/Collaboration.do";

			}
		} else {
			workspace.setPortalId(portal.getPortalId());
			workspace.setWorkspaceId(workspaceId);
			workspace = workspaceService.read(workspace);
			// workspaceId 로 조회해서 Workspace 가 없는 경우 Collaboration 메인 화면으로 이동
			if (workspace == null) {
				return "redirect:/collpack/collaboration/main/Collaboration.do";

			}
		}

		/**
		 * 방문기록남기기
		 */
		Map<String, String> visitMap = new HashMap<String, String>();
		visitMap.put("workspaceId", workspace.getWorkspaceId());
		visitMap.put("visitorId", user.getUserId());
		workspaceService.createWorkspaceVisit(visitMap);

		/**
		 * 로그인 사용자의 회원 정보 조회
		 */
		Member member = new Member();
		member.setWorkspaceId(workspace.getWorkspaceId());
		member.setMemberId(user.getUserId());
		member = memberService.read(member);

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
		/**
		 * 맵의 하위 존재여부 확인
		 */
		boolean existLowRankGroup = false;
		existLowRankGroup = workspaceMapAdminService.existLowRankGroup(workspace.getWorkspaceId());

		/**
		 * Root 맵 정보 조회
		 */
		WorkspaceMap rootMap = new WorkspaceMap();
		rootMap = workspaceMapAdminService.readWorkspaceMapRoot(workspace.getWorkspaceId());

		/**
		 * 맵 Tree 생성
		 */
		String mapTreeJSON = null;
		Map<String, String> treeMap = new HashMap<String, String>();
		treeMap.put("workspaceId", workspace.getWorkspaceId());
		treeMap.put("mapParentId", rootMap.getMapParentId());
		List<WorkspaceMap> mapList = workspaceMapAdminService.listByWorkspaceMapRootId(treeMap);
		List<TreeNode> treeNodeList = makeListMapTreeNodes(mapList);
		mapTreeJSON = TreeManager.getJSON(treeNodeList);

		/**
		 * Workspace Alliance List
		 */
		List<Alliance> allianceList = allianceService.listAllianceByWorkspace(workspace.getWorkspaceId());
		/**
		 * Workspace에 공유받은 게시판 목록
		 */
		List<Alliance> allianceBoardList = allianceService.receiveAllianceBoardListByWorkspace(workspace
				.getWorkspaceId());

		/*
		 * My Collaboration
		 */
		Map<String, String> myCollMap = new HashMap<String, String>();
		myCollMap.put("portalId", portal.getPortalId());
		myCollMap.put("memberId", user.getUserId());

		List<Workspace> myWorkspaceList = null;
		myWorkspaceList = workspaceService.listMyCollaboration(myCollMap);
		/**
		 * 운영진 이상여부
		 */
		boolean isWorkspaceManager = isWorkspaceManager(workspace.getWorkspaceId(), user.getUserId());
		/**
		 * 시샵 이상여부
		 */
		boolean isWorkspaceAdmin = isWorkspaceAdmin(workspace.getWorkspaceId(), user.getUserId());

		model.addAttribute("allianceList", allianceList);
		model.addAttribute("allianceBoardList", allianceBoardList);
		model.addAttribute("boardId", boardId);
		model.addAttribute("myWorkspaceList", myWorkspaceList);
		model.addAttribute("workspace", workspace);
		model.addAttribute("member", member);
		// model.addAttribute("boardTreeJson", boardTreeJson);
		model.addAttribute("rootMap", rootMap);
		model.addAttribute("mapTreeJSON", mapTreeJSON);
		model.addAttribute("existLowRankGroup", existLowRankGroup);
		model.addAttribute("isWorkspaceManager", isWorkspaceManager);
		model.addAttribute("isWorkspaceAdmin", isWorkspaceAdmin);

		return "collpack/collaboration/main/Workspace";
	}
	
	
	@RequestMapping(value = "/myWorkspace.do", method = RequestMethod.GET)
	public String myWorkspace(@RequestParam(value = "boardId", required = false) String boardId, Model model) {

		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");

		Workspace workspace = new Workspace();
		
		String workspaceId = workspaceService.myTeamWorkspace(user.getUserId());

		/**
		 * workspaceId 가 없는경우 디폴트 Workspace 확인후 해당 Workspace 이동 ( 1건만 조회후 이동 -
		 * 버그 또는 DB 직접 변경으로 인한 다중 Row 발생으로 인한 오류 방지 ) 디폴트 Workspace 없는경우 해당 팀
		 * Workspace 확인후 이동 ( 1건만 조회후 이동 - 버그 또는 DB 직접 변경으로 인한 다중 Row 발생으로 인한 오류
		 * 방지 ) 해당 정보가 없는 경우는 Collaboration 메인 화면으로 이동
		 */
		if (workspaceId == null || workspaceId.equals("")) {

			// 디폴트 WS 체크
			Map<String, String> wsMap = new HashMap<String, String>();
			wsMap.put("portalId", portal.getPortalId());
			wsMap.put("memberId", user.getUserId());
			workspace = workspaceService.getDefaultWorkspace(wsMap);

			// 개인의 팀 WS 체크
			if (workspace == null) {
				wsMap.put("teamId", user.getGroupId());
				workspace = workspaceService.getDefaultWorkspace(wsMap);
			}
			// 디폴트 Workspace 가 없는 경우 Collaboration 메인 화면으로 이동
			if (workspace == null) {
				return "redirect:/collpack/collaboration/main/Collaboration.do";

			}
		} else {
			workspace.setPortalId(portal.getPortalId());
			workspace.setWorkspaceId(workspaceId);
			workspace = workspaceService.read(workspace);
			// workspaceId 로 조회해서 Workspace 가 없는 경우 Collaboration 메인 화면으로 이동
			if (workspace == null) {
				return "redirect:/collpack/collaboration/main/Collaboration.do";

			}
		}

		/**
		 * 방문기록남기기
		 */
		Map<String, String> visitMap = new HashMap<String, String>();
		visitMap.put("workspaceId", workspace.getWorkspaceId());
		visitMap.put("visitorId", user.getUserId());
		workspaceService.createWorkspaceVisit(visitMap);

		/**
		 * 로그인 사용자의 회원 정보 조회
		 */
		Member member = new Member();
		member.setWorkspaceId(workspace.getWorkspaceId());
		member.setMemberId(user.getUserId());
		member = memberService.read(member);

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
		/**
		 * 맵의 하위 존재여부 확인
		 */
		boolean existLowRankGroup = false;
		existLowRankGroup = workspaceMapAdminService.existLowRankGroup(workspace.getWorkspaceId());

		/**
		 * Root 맵 정보 조회
		 */
		WorkspaceMap rootMap = new WorkspaceMap();
		rootMap = workspaceMapAdminService.readWorkspaceMapRoot(workspace.getWorkspaceId());

		/**
		 * 맵 Tree 생성
		 */
		String mapTreeJSON = null;
		Map<String, String> treeMap = new HashMap<String, String>();
		treeMap.put("workspaceId", workspace.getWorkspaceId());
		treeMap.put("mapParentId", rootMap.getMapParentId());
		List<WorkspaceMap> mapList = workspaceMapAdminService.listByWorkspaceMapRootId(treeMap);
		List<TreeNode> treeNodeList = makeListMapTreeNodes(mapList);
		mapTreeJSON = TreeManager.getJSON(treeNodeList);

		/**
		 * Workspace Alliance List
		 */
		List<Alliance> allianceList = allianceService.listAllianceByWorkspace(workspace.getWorkspaceId());
		/**
		 * Workspace에 공유받은 게시판 목록
		 */
		List<Alliance> allianceBoardList = allianceService.receiveAllianceBoardListByWorkspace(workspace
				.getWorkspaceId());

		/*
		 * My Collaboration
		 */
		Map<String, String> myCollMap = new HashMap<String, String>();
		myCollMap.put("portalId", portal.getPortalId());
		myCollMap.put("memberId", user.getUserId());

		List<Workspace> myWorkspaceList = null;
		myWorkspaceList = workspaceService.listMyCollaboration(myCollMap);
		/**
		 * 운영진 이상여부
		 */
		boolean isWorkspaceManager = isWorkspaceManager(workspace.getWorkspaceId(), user.getUserId());
		/**
		 * 시샵 이상여부
		 */
		boolean isWorkspaceAdmin = isWorkspaceAdmin(workspace.getWorkspaceId(), user.getUserId());

		model.addAttribute("allianceList", allianceList);
		model.addAttribute("allianceBoardList", allianceBoardList);
		model.addAttribute("boardId", boardId);
		model.addAttribute("myWorkspaceList", myWorkspaceList);
		model.addAttribute("workspace", workspace);
		model.addAttribute("member", member);
		// model.addAttribute("boardTreeJson", boardTreeJson);
		model.addAttribute("rootMap", rootMap);
		model.addAttribute("mapTreeJSON", mapTreeJSON);
		model.addAttribute("existLowRankGroup", existLowRankGroup);
		model.addAttribute("isWorkspaceManager", isWorkspaceManager);
		model.addAttribute("isWorkspaceAdmin", isWorkspaceAdmin);

		return "collpack/collaboration/main/Workspace";
	}

	/**
	 * 개별 Collaboration 메인 Body 화면
	 * 
	 * @param workspaceId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/WorkspaceBody.do", method = RequestMethod.GET)
	public String workspaceBody(@RequestParam("workspaceId") String workspaceId, Model model) {

		Portal portal = (Portal) getRequestAttribute("ikep.portal");

		// Workspace 정보
		Workspace workspace = new Workspace();
		workspace.setPortalId(portal.getPortalId());
		workspace.setWorkspaceId(workspaceId);
		workspace = workspaceService.read(workspace);

		/**
		 * 팀 Workspace hierarchy Path
		 */
		List<Workspace> workspaceHierarchyList = new ArrayList<Workspace>();
		if (workspace.getIsOrganization() == 1) {
			workspaceHierarchyList = workspaceService.listGroupHierarchy(workspace.getTeamId());
		}

		// Current Workspace Portlet Layout
		List<WorkspacePortletLayout> workspacePortletLayoutList = workspacePortletLayoutService
				.listLayoutByWorkspace(workspaceId);

		model.addAttribute("workspaceHierarchyList", workspaceHierarchyList);
		model.addAttribute("workspacePortletLayoutList", workspacePortletLayoutList);
		model.addAttribute("workspace", workspace);
		model.addAttribute("workspaceId", workspaceId);

		return "collpack/collaboration/main/WorkspaceBody";
	}

	/**
	 * Tree 노드 생성
	 * 
	 * @param workspaceMap
	 * @return
	 */
	protected TreeNode makeTreeNode(WorkspaceMap workspaceMap) {
		// Attr 생성
		TreeNodeAttrWorkspaceMap tna = new TreeNodeAttrWorkspaceMap();
		tna.setId(workspaceMap.getMapId());
		tna.setPid(workspaceMap.getMapParentId());
		tna.setMapDescription(workspaceMap.getMapDescription());
		tna.setOrder(workspaceMap.getSortOrder());
		tna.setTags(workspaceMap.getTags());
		tna.setWorkspaceId(workspaceMap.getWorkspaceId());
		// Data 생성
		TreeNodeData tnd = new TreeNodeData();
		tnd.setTitle(workspaceMap.getMapName());
		tnd.setIcon("dept");

		// Node 생성
		TreeNode tn = new TreeNode();
		tn.setAttr(tna);
		tn.setData(tnd);

		if (0 < workspaceMap.getChildCount()) {
			tn.setState("closed");
		}

		return tn;
	}

	/**
	 * Map Tree 생성
	 * 
	 * @param workspaceMapList
	 * @return
	 */
	protected List<TreeNode> makeListMapTreeNodes(List<WorkspaceMap> workspaceMapList) {
		List<TreeNode> treeNodeList = new ArrayList<TreeNode>();

		for (WorkspaceMap item : workspaceMapList) {
			treeNodeList.add(makeTreeNode(item));
		}

		return treeNodeList;
	}

	/**
	 * Workspace Activity Stream 포틀릿 정보 조회
	 * 
	 * @param workspaceId
	 * @param portletLayoutId
	 * @return
	 */
	@RequestMapping(value = "/listActivityStreamPortlet.do")
	public ModelAndView listActivityStreamPortlet(@RequestParam("workspaceId") String workspaceId,
			@RequestParam("portletLayoutId") String portletLayoutId) {

		User user = (User) getRequestAttribute("ikep.user");

		ModelAndView model = new ModelAndView("collpack/collaboration/main/portlet/listActivityStreamPortlet");

		ActivityStreamSearchCondition searchCondition = new ActivityStreamSearchCondition();

		searchCondition.setUserLocaleCode(user.getLocaleCode());
		searchCondition.setRegisterId(user.getUserId());

		List<String> groupList = new ArrayList<String>();

		groupList.add(workspaceId);

		searchCondition.setGroupList(groupList);

		// 사용자 코드값이 있는지 체크함
		List<ActivityCode> userActivityCodeList = activityCodeService.select(user);

		boolean isUserCode = false;
		for (ActivityCode activityCode : userActivityCodeList) {
			if (!StringUtil.isEmpty(activityCode.getSubscriptionActivityCode())) {
				isUserCode = true;
			}
		}

		if (!isUserCode) {
			searchCondition.setIsUserCode("");
		} else {
			searchCondition.setIsUserCode("userCode");
		}

		SearchResult<ActivityStream> searchResult = activityStreamService.getAllWorkspace(searchCondition, user);

		model.addObject("activityStream", searchResult.getEntity());

		WorkspacePortletLayout workspacePortletLayout = workspacePortletLayoutService.read(portletLayoutId);
		model.addObject("workspacePortletLayout", workspacePortletLayout);
		return model;
	}

	/**
	 * Workspace 최근게시물 목록 포틀릿 정보 조회
	 * 
	 * @param workspaceId
	 * @param portletLayoutId
	 * @return
	 */
	@RequestMapping(value = "/listRecentBoardItemPortlet.do")
	public ModelAndView listRecentBoardItemPortlet(@RequestParam("workspaceId") String workspaceId,
			@RequestParam("portletLayoutId") String portletLayoutId) {

		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		// User user = (User) getRequestAttribute("ikep.user");

		ModelAndView model = new ModelAndView("collpack/collaboration/main/portlet/listRecentBoardItemPortlet");

		Workspace workspace = new Workspace();
		workspace.setPortalId(portal.getPortalId());
		workspace.setWorkspaceId(workspaceId);
		workspace = workspaceService.read(workspace);

		Map<String, String> map = new HashMap<String, String>();
		map.put("workspaceId", workspaceId);
		map.put("limitSize", "5");

		List<BoardItem> boardItem = boardItemService.listBoardItemByPortlet(map);

		WorkspacePortletLayout workspacePortletLayout = workspacePortletLayoutService.read(portletLayoutId);

		model.addObject("workspacePortletLayout", workspacePortletLayout);

		model.addObject("boardItem", boardItem);
		model.addObject("workspace", workspace);
		return model;
	}

	/**
	 * Workspace 게시판 포틀릿 목록
	 * 
	 * @param workspaceId
	 * @param boardId
	 * @param portletLayoutId
	 * @return
	 */
	@RequestMapping(value = "/listBoardItemPortlet.do")
	public ModelAndView listBoardItemPortlet(@RequestParam("workspaceId") String workspaceId,
			@RequestParam("boardId") String boardId, @RequestParam("portletLayoutId") String portletLayoutId) {

		ModelAndView model = new ModelAndView("collpack/collaboration/main/portlet/listBoardItemPortlet");

		Map<String, String> map = new HashMap<String, String>();
		map.put("workspaceId", workspaceId);
		// map.put("locale", user.getLocaleCode());
		map.put("boardId", boardId);
		map.put("limitSize", "5");

		Board board = boardDao.get(boardId);
		List<BoardItem> boardItem = boardItemService.listBoardItemByPortlet(map);

		WorkspacePortletLayout workspacePortletLayout = workspacePortletLayoutService.read(portletLayoutId);

		model.addObject("workspacePortletLayout", workspacePortletLayout);

		model.addObject("boardItem", boardItem);
		model.addObject("board", board);
		return model;
	}

	/**
	 * Workspace 일정 포틀릿 정보 조회
	 * 
	 * @param workspaceId
	 * @param portletLayoutId
	 * @return
	 */
	@RequestMapping(value = "/listPlannerPortlet.do")
	public ModelAndView listPlannerPortlet(@RequestParam("workspaceId") String workspaceId,
			@RequestParam("portletLayoutId") String portletLayoutId,
			@RequestParam(value = "currentDate", required = false) String currentDate) {
		String currDate = null;
		Portal portal = (Portal) getRequestAttribute("ikep.portal");

		ModelAndView model = new ModelAndView("collpack/collaboration/main/portlet/listPlannerPortlet");

		Workspace workspace = new Workspace();
		workspace.setPortalId(portal.getPortalId());
		workspace.setWorkspaceId(workspaceId);
		workspace = workspaceService.read(workspace);
		if (currentDate == null || currentDate.equals("")) {
			//currDate = DateUtil.getToday("yyyyMMdd");// "20110423"; // 현재 날짜
			
			Date tmpDate = timeZoneSupportService.convertTimeZone();
			SimpleDateFormat sdate = new SimpleDateFormat("yyyyMMdd");
			currDate = sdate.format(tmpDate);
		} else {
			currDate = currentDate;
		}
		/*
		 * Calendar cal; //클래스생성 int year, month, day, day_of_month; cal =
		 * Calendar.getInstance(); Date _date = new Date(); //Date newDate =
		 * DateUtil.shift(new Date(), 0,0,5); cal.setTime(_date);
		 * cal.add(Calendar.YEAR, 0); cal.add(Calendar.MONTH, 0);
		 * cal.add(Calendar.DAY_OF_MONTH, 23); year = cal.get(Calendar.YEAR);
		 * //현재의 년도 month = cal.get(Calendar.MONTH)+1; //현재의 월(월은 0부터 시작하므로 1을
		 * 더해줍니다) day = cal.get(Calendar.DATE); //현재의 일 day_of_month =
		 * cal.get(Calendar.WEEK_OF_MONTH); //현재 월의 주
		 * System.out.println(year+"년"
		 * +month+"월"+day+"일"+day_of_month+" 째 주입니다");
		 */

		int weeklyNum = DateUtil.getWeekDayCount(currDate);

		String[] weeklyDate = DateUtil.getDatesInWeek(DateUtil.getWeekDay(currDate, 1), "yyyyMMdd");

		WorkspacePortletLayout workspacePortletLayout = workspacePortletLayoutService.read(portletLayoutId);

		model.addObject("workspacePortletLayout", workspacePortletLayout);
		model.addObject("workspaceId", workspaceId);
		model.addObject("workspace", workspace);
		model.addObject("currentDate", currDate);
		model.addObject("weeklyDate", weeklyDate);
		model.addObject("weeklyStartDate", DateUtil.getFmtDateString(weeklyDate[0], "yyyy.MM.dd"));
		model.addObject("weeklyEndDate", DateUtil.getFmtDateString(weeklyDate[6], "yyyy.MM.dd"));
		model.addObject("weeklyNum", (weeklyNum - 1));
		return model;
	}

	/**
	 * Workspace 공지사항 포틀릿
	 * 
	 * @param workspaceId
	 * @param portletLayoutId
	 * @return
	 */
	@RequestMapping(value = "/listAnnouncePortlet.do")
	public ModelAndView listAnnouncePortlet(@RequestParam("workspaceId") String workspaceId,
			@RequestParam("portletLayoutId") String portletLayoutId) {

		User user = readUser();
		ModelAndView model = new ModelAndView("collpack/collaboration/main/portlet/listAnnouncePortlet");

		Map<String, String> map = new HashMap<String, String>();
		map.put("workspaceId", workspaceId);
		// map.put("locale", user.getLocaleCode());
		map.put("limitSize", "5");

		List<AnnounceItem> announceItem = announceItemService.listAnnounceItemByPortlet(map);

		WorkspacePortletLayout workspacePortletLayout = workspacePortletLayoutService.read(portletLayoutId);

		model.addObject("workspacePortletLayout", workspacePortletLayout);
		model.addObject("announceItem", announceItem);
		model.addObject("announcePermission", getAnnouncePermission(user, workspaceId));
		return model;
	}

	/**
	 * Workspace 기념일 포틀릿
	 * 
	 * @param workspaceId
	 * @param portletLayoutId
	 * @return
	 */
	@RequestMapping(value = "/listAnniversaryPortlet.do")
	public ModelAndView listAnniversaryPortlet(@RequestParam("workspaceId") String workspaceId,
			@RequestParam("portletLayoutId") String portletLayoutId) {

		// Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");

		ModelAndView model = new ModelAndView("collpack/collaboration/main/portlet/listAnniversaryPortlet");

		WorkspacePortletLayout workspacePortletLayout = workspacePortletLayoutService.read(portletLayoutId);

		String pattern = "MM.dd";
		// 현재 시스템 시각을 사용자 시간대로 변환하고 pattern 형태의 문자열을 리턴함, 예제에서는 yyyy-MM-dd
		String currentDate = timeZoneSupportService.convertTimeZoneToString(pattern);

		List<Congratulations> birthdayList = congratulationsService.listCongratulations(workspaceId, user, "BIRTHDAY");
		// List<Congratulations> weddingAnnivList =
		// congratulationsService.listCongratulations(workspaceId, user,
		// "WEDDING_ANNIV");

		model.addObject("workspacePortletLayout", workspacePortletLayout);
		model.addObject("birthdayList", birthdayList);
		// model.addObject("weddingAnnivList", weddingAnnivList);
		model.addObject("currentDate", currentDate);
		return model;
	}

	/**
	 * Activity Stream 목록
	 * 
	 * @param workspaceId
	 * @return
	 */
	@RequestMapping(value = "/listActivityStream.do")
	public ModelAndView listActivityStream(@RequestParam("workspaceId") String workspaceId) {

		ActivityStreamSearchCondition searchCondition = new ActivityStreamSearchCondition();

		ModelAndView model = new ModelAndView("collpack/collaboration/main/listActivityStream");

		List<String> groupList = new ArrayList<String>();

		model.addObject("groupList", workspaceId);
		model.addObject("searchCondition", searchCondition);
		return model;
	}

	/**
	 * Activity Stream 리스트 검색
	 * 
	 * @param searchCondition
	 * @return
	 */
	@RequestMapping(value = "/listActivityStreamByWorkspace.do")
	public ModelAndView listActivityStreamByWorkspace(ActivityStreamSearchCondition searchCondition) {

		ModelAndView mav = new ModelAndView("collpack/collaboration/main/listActivityStreamByWorkspace");

		try {
			User user = (User) getRequestAttribute("ikep.user");

			searchCondition.setUserLocaleCode(user.getLocaleCode());
			searchCondition.setRegisterId(user.getUserId());

			List<String> groupList = new ArrayList<String>();
			for (String group : searchCondition.getGroupList()) {
				if (!StringUtil.isEmpty(group)) {
					groupList.add(group);
				}
			}
			searchCondition.setGroupList(groupList);

			// 사용자 코드값이 있는지 체크함
			List<ActivityCode> userActivityCodeList = activityCodeService.select(user);

			boolean isUserCode = false;
			for (ActivityCode activityCode : userActivityCodeList) {
				if (!StringUtil.isEmpty(activityCode.getSubscriptionActivityCode())) {
					isUserCode = true;
				}
			}

			if (!isUserCode) {
				searchCondition.setIsUserCode("");
			} else {
				searchCondition.setIsUserCode("userCode");
			}

			SearchResult<ActivityStream> searchResult = activityStreamService.getAllWorkspace(searchCondition, user);

			mav.addObject("searchResult", searchResult);
			mav.addObject("searchCondition", searchResult.getSearchCondition());

		} catch (Exception ex) {
			throw new IKEP4AjaxException("", ex);
		}
		return mav;
	}

	/**
	 * 팀에 Mentions 된 목록
	 * 
	 * @param groupId
	 * @param workspaceId
	 * @return
	 */
	@RequestMapping(value = "/listTeamMention.do")
	public ModelAndView listTeamMention(@RequestParam("groupId") String groupId,
			@RequestParam("workspaceId") String workspaceId) {
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		ActivityStreamSearchCondition searchCondition = new ActivityStreamSearchCondition();

		Workspace workspace = new Workspace();
		workspace.setPortalId(portal.getPortalId());
		workspace.setWorkspaceId(workspaceId);
		workspace = workspaceService.read(workspace);

		ModelAndView model = new ModelAndView("collpack/collaboration/main/listTeamMention");
		model.addObject("groupId", groupId);
		model.addObject("workspace", workspace);
		model.addObject("searchCondition", searchCondition);
		return model;
	}

	/**
	 * 팀에게 발송된 쪽지 목록
	 * 
	 * @param groupId
	 * @param workspaceId
	 * @return
	 */
	@RequestMapping(value = "/listTeamMessage.do")
	public ModelAndView listTeamMessage(@RequestParam("groupId") String groupId,
			@RequestParam("workspaceId") String workspaceId) {
		Portal portal = (Portal) getRequestAttribute("ikep.portal");

		Workspace workspace = new Workspace();
		workspace.setPortalId(portal.getPortalId());
		workspace.setWorkspaceId(workspaceId);
		workspace = workspaceService.read(workspace);

		ModelAndView model = new ModelAndView("collpack/collaboration/main/listTeamMessage");
		model.addObject("groupId", groupId);
		model.addObject("workspace", workspace);

		return model;
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
	 * 이미지 갤러리 검색 포틀릿
	 * 
	 * @param workspaceId
	 * @return
	 */
	@RequestMapping(value = "/listWorkspaceImageFilePortlet.do")
	public ModelAndView listWorkspaceImageFilePortlet(@RequestParam("workspaceId") String workspaceId,
			@RequestParam("portletLayoutId") String portletLayoutId) {

		ModelAndView model = new ModelAndView("collpack/collaboration/main/portlet/listWorkspaceImageFilePortlet");

		WorkspaceSearchCondition workspaceSearchCondition = new WorkspaceSearchCondition();
		workspaceSearchCondition.setWorkspaceId(workspaceId);
		workspaceSearchCondition.setPagePerRecord(5);

		SearchResult<Workspace> searchResult = workspaceService.getWorkspaceImageFile(workspaceSearchCondition);

		WorkspacePortletLayout workspacePortletLayout = workspacePortletLayoutService.read(portletLayoutId);

		model.addObject("workspacePortletLayout", workspacePortletLayout);
		model.addObject("searchCondition", searchResult.getSearchCondition());
		model.addObject("searchResult", searchResult);

		return model;
	}

}
