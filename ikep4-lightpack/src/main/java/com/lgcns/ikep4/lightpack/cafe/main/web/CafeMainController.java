/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.cafe.main.web;

import java.util.ArrayList;
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

import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.lightpack.cafe.board.model.Board;
import com.lgcns.ikep4.lightpack.cafe.board.model.BoardItem;
import com.lgcns.ikep4.lightpack.cafe.board.search.BoardItemSearchCondition;
import com.lgcns.ikep4.lightpack.cafe.board.service.BoardAdminService;
import com.lgcns.ikep4.lightpack.cafe.board.service.BoardItemService;
import com.lgcns.ikep4.lightpack.cafe.cafe.model.Cafe;
import com.lgcns.ikep4.lightpack.cafe.cafe.model.CafeCode;
import com.lgcns.ikep4.lightpack.cafe.cafe.model.CafePortletLayout;
import com.lgcns.ikep4.lightpack.cafe.cafe.search.CafeSearchCondition;
import com.lgcns.ikep4.lightpack.cafe.cafe.service.CafePortletLayoutService;
import com.lgcns.ikep4.lightpack.cafe.cafe.service.CafeService;
import com.lgcns.ikep4.lightpack.cafe.category.model.Category;
import com.lgcns.ikep4.lightpack.cafe.category.service.CategoryService;
import com.lgcns.ikep4.lightpack.cafe.member.model.Member;
import com.lgcns.ikep4.lightpack.cafe.member.service.MemberService;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.lang.StringUtil;


/**
 * Cafe Main Controller
 * 
 * @author 김종철(happyi1018@nate.com)
 * @version $Id: CafeMainController.java 17315 2012-02-08 04:56:13Z yruyo $
 */

@Controller
@RequestMapping(value = "/lightpack/cafe/main")
@SessionAttributes("cafe")
public class CafeMainController extends BaseController {

	@Autowired
	private CafeService cafeService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private CafePortletLayoutService cafePortletLayoutService;

	@Autowired
	private ACLService aclService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private BoardItemService boardItemService;

	@Autowired
	private BoardAdminService boardAdminService;

	private static final String CAFE_MANAGER = "Cafe";

	/**
	 * 관리자 여부 체크
	 * 
	 * @param userId
	 * @return
	 */
	public boolean isSystemAdmin(String userId) {

		boolean isSystemAdmin = aclService.isSystemAdmin(CAFE_MANAGER, userId);

		return isSystemAdmin;
	}

	/**
	 * 시샵 이상 여부
	 * 
	 * @param cafeId
	 * @param userId
	 * @return
	 */
	public boolean isCafeAdmin(String cafeId, String userId) {

		// 권한 정보 조회 (isPermission : Manager:관리자 )

		boolean isAdmin = false;

		// 시스템,Cafe관리자 체크
		Boolean isSystemAdmin = isSystemAdmin(userId);

		if (isSystemAdmin) {
			isAdmin = true; // Cafe 서비스 관리자
		} else {

			// 시샵, 운영진 체크
			Member member = new Member();
			member.setCafeId(cafeId);
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
	 * @param cafeId
	 * @param userId
	 * @return
	 */
	public boolean isCafeManager(String cafeId, String userId) {

		// 권한 정보 조회 (isPermission : Manager:관리자 )

		boolean isAdmin = false;

		// 시스템,Cafe관리자 체크
		Boolean isSystemAdmin = isSystemAdmin(userId);

		if (isSystemAdmin) {
			isAdmin = true; // Cafe 서비스 관리자
		} else {

			// 시샵, 운영진 체크
			Member member = new Member();
			member.setCafeId(cafeId);
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
	 * Cafe 메인
	 * 
	 * @return
	 */
	@RequestMapping(value = "/main.do", method = RequestMethod.GET)
	public ModelAndView main() {
		ModelAndView modelAndView = new ModelAndView("lightpack/cafe/main/main");

		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");

		Map<String, String> map = new HashMap<String, String>();
		map.put("portalId", portal.getPortalId());
		map.put("memberId", user.getUserId());

		List<Category> defaultCategoryList = new ArrayList<Category>();
		defaultCategoryList = categoryService.defaultCategoryList(portal.getPortalId());

		List<Category> menuCategoryList = new ArrayList<Category>();
		menuCategoryList = categoryService.listCafeCategoryByPortalId(portal.getPortalId());

		// Coll 관리자 여부
		Boolean isSystemAdmin = isSystemAdmin(user.getUserId());

		modelAndView.addObject("defaultCategoryList", defaultCategoryList);
		modelAndView.addObject("menuCategoryList", menuCategoryList);
		modelAndView.addObject("isSystemAdmin", isSystemAdmin);

		return modelAndView;
	}

	/**
	 * 마이 카페 서브 리스트
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getSubListForMyCafe.do")
	public ModelAndView getSubListForMyCafe() {

		ModelAndView modelAndView = new ModelAndView("lightpack/cafe/main/subListForMyCafe");

		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");

		Map<String, String> map = new HashMap<String, String>();
		map.put("portalId", portal.getPortalId());
		map.put("memberId", user.getUserId());

		List<Cafe> myCafeList = null;
		myCafeList = cafeService.listMyCafe(map, false);

		List<Cafe> closeCafeList = null;
		closeCafeList = cafeService.listCloseCafe(map);

		modelAndView.addObject("myCafeList", myCafeList);
		modelAndView.addObject("closeCafeList", closeCafeList);

		return modelAndView;
	}

	/**
	 * 마이 카페 서브 리스트 - more item
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getSubListForMyCafeItem.do")
	public ModelAndView getSubListForMyCafeItem(BoardItemSearchCondition searchCondition) {

		ModelAndView modelAndView = new ModelAndView("lightpack/cafe/main/subListForMyCafeItem");

		searchCondition.setPagePerRecord(3);
		SearchResult<BoardItem> searchResult = boardItemService.listRecentBoardItemForCafe(searchCondition);

		modelAndView.addObject("boardItemList", searchResult.getEntity());
		modelAndView.addObject("searchCondition", searchCondition);

		return modelAndView;
	}

	/**
	 * 가입대기 카페 서브 리스트
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getSubListForWaitCafe.do")
	public ModelAndView getSubListForWaitCafe() {
		ModelAndView modelAndView = new ModelAndView("lightpack/cafe/main/subListForWaitCafe");

		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");

		Map<String, String> map = new HashMap<String, String>();
		map.put("portalId", portal.getPortalId());
		map.put("memberId", user.getUserId());
		map.put("memberType", "wait");

		List<Cafe> myCafeList = null;
		myCafeList = cafeService.listMyCafe(map, false);

		modelAndView.addObject("myCafeList", myCafeList);

		return modelAndView;
	}

	/**
	 * 신규 카페 서브 리스트
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getSubListForNewCafe.do")
	public ModelAndView getSubListForNewCafe() {
		ModelAndView modelAndView = new ModelAndView("lightpack/cafe/main/subListForNewCafe");

		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");

		Map<String, String> map = new HashMap<String, String>();
		map.put("portalId", portal.getPortalId());
		map.put("memberId", user.getUserId());

		List<Cafe> cafeNewList = null;
		cafeNewList = cafeService.listNewCafe(map);

		modelAndView.addObject("cafeNewList", cafeNewList);

		return modelAndView;
	}

	/**
	 * Cafe 검색 목록
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "/getSubListForCafe.do")
	public ModelAndView getSubListForCafe(CafeSearchCondition searchCondition) {

		ModelAndView modelAndView = new ModelAndView("lightpack/cafe/main/subListForCafe");

		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");

		CafeCode cafeCode = new CafeCode();

		Map<String, String> map = new HashMap<String, String>();
		map.put("portalId", portal.getPortalId());
		map.put("memberId", user.getUserId());

		searchCondition.setSearchColumn("CAFE_NAME");
		searchCondition.setListType("find");
		searchCondition.setPortalId(portal.getPortalId());
		if (StringUtil.isEmpty(searchCondition.getSortColumn())) {
			searchCondition.setSortColumn("openDate");
			searchCondition.setSortType("DESC");
		} 

		Category catetory = null;
		if (!StringUtil.isEmpty(searchCondition.getCategoryId())) {
			catetory = categoryService.read(searchCondition.getCategoryId());
		}

		/**
		 * cafe 목록 검색
		 */
		SearchResult<Cafe> searchResult = this.cafeService.listBySearchCondition(searchCondition);

		modelAndView.addObject("searchResult", searchResult);
		modelAndView.addObject("searchCondition", searchResult.getSearchCondition());
		modelAndView.addObject("cafeCode", cafeCode);
		modelAndView.addObject("catetory", catetory);

		return modelAndView;
	}

	/**
	 * Cafe 관리 메뉴
	 * 
	 * @param searchCondition
	 * @return
	 */
	@RequestMapping(value = "/menu.do", method = RequestMethod.GET)
	public ModelAndView listCafeMain(CafeSearchCondition searchCondition) {

		ModelAndView modelAndView = new ModelAndView("lightpack/cafe/main/menu");
		User user = (User) getRequestAttribute("ikep.user");

		List<Cafe> cafeList = null;

		modelAndView.addObject("searchCondition", searchCondition);

		Map<String, String> map = new HashMap<String, String>();
		map.put("portalId", user.getPortalId());
		map.put("memberId", user.getUserId());

		cafeList = cafeService.listMyCafe(map, false);

		// 카페 권한 셋팅
		boolean isSystemAdmin = isSystemAdmin(user.getUserId());

		modelAndView.addObject("isSystemAdmin", isSystemAdmin);
		modelAndView.addObject("cafeList", cafeList);
		modelAndView.addObject("searchCondition", searchCondition);

		return modelAndView;
	}

	/**
	 * 개별Cafe admin SubMenu
	 * 
	 * @param searchCondition
	 * @return
	 */
	@RequestMapping(value = "/cafeAdmin.do", method = RequestMethod.GET)
	public ModelAndView cafeAdmin(CafeSearchCondition searchCondition) {

		ModelAndView modelAndView = new ModelAndView("lightpack/cafe/main/cafeAdmin");
		User user = (User) getRequestAttribute("ikep.user");
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		Cafe cafe = new Cafe();

		cafe.setPortalId(portal.getPortalId());
		cafe.setCafeId(searchCondition.getCafeId());
		cafe = cafeService.read(searchCondition.getCafeId());

		// 카페 권한 셋팅
		boolean isSystemAdmin = isSystemAdmin(user.getUserId());
		boolean isCafeAdmin = isCafeAdmin(cafe.getCafeId(), user.getUserId());
		boolean isCafeManager = isCafeManager(cafe.getCafeId(), user.getUserId());

		modelAndView.addObject("searchCondition", searchCondition);
		modelAndView.addObject("isSystemAdmin", isSystemAdmin);
		modelAndView.addObject("isCafeAdmin", isCafeAdmin);
		modelAndView.addObject("isCafeManager", isCafeManager);
		modelAndView.addObject("cafe", cafe);
		modelAndView.addObject("searchCondition", searchCondition);

		return modelAndView;
	}

	/**
	 * 개별 Cafe 서브메뉴
	 * 
	 * @param cafeId
	 * @param boardId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/cafe.do", method = RequestMethod.GET)
	public String cafeSubMenu(@RequestParam(value = "cafeId", required = false) String cafeId,
			@RequestParam(value = "boardId", required = false) String boardId, Model model) {

		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");

		Cafe cafe = new Cafe();

		cafe.setPortalId(portal.getPortalId());
		cafe.setCafeId(cafeId);
		cafe = cafeService.read(cafeId);

		/**
		 * 방문기록남기기
		 */
		Map<String, String> visitMap = new HashMap<String, String>();
		visitMap.put("cafeId", cafe.getCafeId());
		visitMap.put("visitorId", user.getUserId());
		cafeService.createCafeVisit(visitMap);

		/**
		 * 로그인 사용자의 회원 정보 조회
		 */
		Member member = new Member();
		member.setCafeId(cafe.getCafeId());
		member.setMemberId(user.getUserId());
		member = memberService.read(member);

		/**
		 * Cafe 게시판 정보
		 */
		Map<String, String> map = new HashMap<String, String>();
		map.put("cafeId", cafe.getCafeId());
		map.put("portalId", portal.getPortalId());
		List<Board> boardList = boardAdminService.listByMenuBoardRootId(map);

		// 카페 권한 셋팅
		boolean isSystemAdmin = isSystemAdmin(user.getUserId());
		boolean isCafeAdmin = isCafeAdmin(cafe.getCafeId(), user.getUserId());
		boolean isCafeManager = isCafeManager(cafe.getCafeId(), user.getUserId());

		model.addAttribute("boardId", boardId);
		model.addAttribute("cafe", cafe);
		model.addAttribute("member", member);
		model.addAttribute("isSystemAdmin", isSystemAdmin);
		model.addAttribute("isCafeAdmin", isCafeAdmin);
		model.addAttribute("isCafeManager", isCafeManager);
		model.addAttribute("boardList", boardList);
		return "lightpack/cafe/main/cafe";
	}

	/**
	 * 개별 Cafe 메인 Body 화면
	 * 
	 * @param cafeId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/cafeBody.do", method = RequestMethod.GET)
	public String cafe(@RequestParam("cafeId") String cafeId, Model model) {

		// Cafe 정보
		Cafe cafe = cafeService.read(cafeId);

		// Current Cafe Portlet Layout
		List<CafePortletLayout> cafePortletLayoutList = cafePortletLayoutService.listLayoutByCafe(cafeId);

		model.addAttribute("cafePortletLayoutList", cafePortletLayoutList);
		model.addAttribute("cafe", cafe);
		model.addAttribute("cafeId", cafeId);

		return "lightpack/cafe/main/cafeBody";
	}

	/**
	 * Cafe 최근게시물 목록 포틀릿 정보 조회
	 * 
	 * @param cafeId
	 * @param portletLayoutId
	 * @return
	 */
	@RequestMapping(value = "/listRecentBoardItemPortlet.do")
	public ModelAndView listRecentBoardItemPortlet(@RequestParam("cafeId") String cafeId,
			@RequestParam("portletLayoutId") String portletLayoutId) {

		// User user = (User) getRequestAttribute("ikep.user");

		ModelAndView model = new ModelAndView("lightpack/cafe/main/portlet/listRecentBoardItemPortlet");

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cafeId", cafeId);
		// map.put("locale", user.getLocaleCode());
		map.put("limitSize", 5);

		List<BoardItem> boardItemList = boardItemService.listBoardItemByPortlet(map);

		CafePortletLayout cafePortletLayout = cafePortletLayoutService.read(portletLayoutId);

		model.addObject("cafePortletLayout", cafePortletLayout);
		model.addObject("boardItemList", boardItemList);
		model.addObject("cafeId", cafeId);
		return model;
	}

	/**
	 * Cafe 게시판 포틀릿 목록
	 * 
	 * @param cafeId
	 * @param boardId
	 * @param portletLayoutId
	 * @return
	 */
	@RequestMapping(value = "/listBoardItemPortlet.do")
	public ModelAndView listBoardItemPortlet(@RequestParam("cafeId") String cafeId,
			@RequestParam("boardId") String boardId, @RequestParam("portletLayoutId") String portletLayoutId) {

		ModelAndView model = new ModelAndView("lightpack/cafe/main/portlet/listBoardItemPortlet");

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cafeId", cafeId);
		// map.put("locale", user.getLocaleCode());
		map.put("boardId", boardId);
		map.put("limitSize", 5);

		Board board = boardAdminService.readBoard(boardId);
		List<BoardItem> boardItemList = boardItemService.listBoardItemByPortlet(map);

		CafePortletLayout cafePortletLayout = cafePortletLayoutService.read(portletLayoutId);

		model.addObject("cafePortletLayout", cafePortletLayout);
		model.addObject("boardItemList", boardItemList);
		model.addObject("board", board);
		model.addObject("cafeId", cafeId);

		return model;
	}

	/**
	 * Cafe 날짜별 방문자 조회수 검색 포틀릿
	 * 
	 * @param cafeId
	 * @return
	 */
	@RequestMapping(value = "/listCafeDateCountPortlet.do")
	public ModelAndView listCafeDateCountPortlet(@RequestParam("cafeId") String cafeId) {

		ModelAndView model = new ModelAndView("lightpack/cafe/main/portlet/listCafeDateCountPortlet");

		List<Cafe> cafeDateCountList = cafeService.getCafeDateCount(cafeId);

		model.addObject("cafeDateCountList", cafeDateCountList);
		model.addObject("cafeId", cafeId);

		return model;
	}

	/**
	 * Cafe 이미지 갤러리 검색 포틀릿
	 * 
	 * @param cafeId
	 * @return
	 */
	@RequestMapping(value = "/listCafeImageFilePortlet.do")
	public ModelAndView listCafeImageFilePortlet(@RequestParam("cafeId") String cafeId) {

		ModelAndView model = new ModelAndView("lightpack/cafe/main/portlet/listCafeImageFilePortlet");

		CafeSearchCondition cafeSearchCondition = new CafeSearchCondition();
		cafeSearchCondition.setCafeId(cafeId);
		cafeSearchCondition.setPagePerRecord(5);

		SearchResult<Cafe> searchResult = cafeService.getCafeImageFile(cafeSearchCondition);

		model.addObject("searchCondition", searchResult.getSearchCondition());
		model.addObject("searchResult", searchResult);

		return model;
	}

}
