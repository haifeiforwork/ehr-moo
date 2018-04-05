/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.cafe.cafe.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.lightpack.cafe.cafe.model.Cafe;
import com.lgcns.ikep4.lightpack.cafe.cafe.model.Cafe.CreateCafe;
import com.lgcns.ikep4.lightpack.cafe.cafe.model.Cafe.UpdateCafe;
import com.lgcns.ikep4.lightpack.cafe.cafe.model.Cafe.UpdateCafeIntro;
import com.lgcns.ikep4.lightpack.cafe.cafe.model.CafeCode;
import com.lgcns.ikep4.lightpack.cafe.cafe.model.CafeConstants;
import com.lgcns.ikep4.lightpack.cafe.cafe.search.CafeSearchCondition;
import com.lgcns.ikep4.lightpack.cafe.cafe.service.CafeService;
import com.lgcns.ikep4.lightpack.cafe.category.model.Category;
import com.lgcns.ikep4.lightpack.cafe.category.service.CategoryService;
import com.lgcns.ikep4.lightpack.cafe.member.model.Member;
import com.lgcns.ikep4.lightpack.cafe.member.service.MemberService;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxValidationException;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.validator.annotation.ValidEx;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.base.exception.JsonException;
import com.lgcns.ikep4.support.cache.service.CacheService;
import com.lgcns.ikep4.support.tagging.model.Tag;
import com.lgcns.ikep4.support.tagging.service.TagService;
import com.lgcns.ikep4.support.timezone.service.TimeZoneSupportService;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * 워크스페이스 Controller
 * 
 * @author 김종철(happyi1018@nate.com)
 * @version $Id: CafeController.java 19561 2012-06-29 04:17:04Z malboru80 $
 */

@Controller
@RequestMapping(value = "/lightpack/cafe/cafe")
@SessionAttributes("cafe")
public class CafeController extends BaseController {

	@Autowired
	private CafeService cafeService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private ACLService aclService;

	@Autowired
	private TagService tagService;

	@Autowired
	private FileService fileService;

	@Autowired
	private TimeZoneSupportService timeZoneSupportService;
	
	@Autowired
	private CacheService cacheService;

	/**
	 * 폐쇄된 Coll. 목록
	 * 
	 * @param searchCondition
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/listCloseCafeView.do")
	public ModelAndView listCloseCafeView(CafeSearchCondition searchCondition, BindingResult result,
			SessionStatus status) {

		Portal portal = (Portal) getRequestAttribute("ikep.portal");

		searchCondition.setPortalId(portal.getPortalId());
		searchCondition.setListUrl("listCloseCafeView.do");

		ModelAndView modelAndView = new ModelAndView();
		CafeCode cafeCode = new CafeCode();

		modelAndView.setViewName("lightpack/cafe/cafe/listCloseCafeView");
		searchCondition.setCafeStatus(CafeConstants.CAFE_STATUS_CLOSE);

		SearchResult<Cafe> searchResult = this.cafeService.listBySearchCondition(searchCondition);

		modelAndView.addObject("searchResult", searchResult);
		modelAndView.addObject("searchCondition", searchResult.getSearchCondition());
		modelAndView.addObject("cafeCode", cafeCode);

		return modelAndView;
	}

	/**
	 * cafe 개설신청 폼 ##
	 * 
	 * @param searchCondition
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/createCafeView.do", method = RequestMethod.GET)
	public String createCafeView(CafeSearchCondition searchCondition, Model model) {

		Cafe cafe = new Cafe();

		if (searchCondition == null) {
			searchCondition = new CafeSearchCondition();
		}
		model.addAttribute("toDay", timeZoneSupportService.convertTimeZone());
		model.addAttribute("cafe", cafe);
		model.addAttribute("searchCondition", searchCondition);

		return "lightpack/cafe/cafe/createCafeView";
	}

	/**
	 * cafeName 중복체크 ##
	 * 
	 * @param cafeName
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/checkCafeName.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	boolean checkCafeName(@RequestParam("cafeName") String cafeName, Model model) {

		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		boolean checkName = false;

		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("portalId", portal.getPortalId());
			map.put("cafeName", cafeName);

			checkName = cafeService.checkCafeName(map);
		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}
		return checkName;

	}

	/**
	 * cafe 등록내용 저장 ##
	 * 
	 * @param cafe
	 * @param searchCondition
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/createCafe.do", method = RequestMethod.POST)
	public @ResponseBody
	String createCafe(Model model, @ValidEx(groups = { CreateCafe.class }) Cafe cafe,
			CafeSearchCondition searchCondition, BindingResult result, SessionStatus status) {

		if (result.hasErrors()) {
			// 에러가 존재할 경우, IKEP4AjaxValidationException을 발생 시킨다.
			throw new IKEP4AjaxValidationException(result, messageSource); // BindingResult와
																			// BaseController의
																			// MessageSource를
																			// parameter로
																			// 전달해야
																			// 합니다.
		}

		// 세션 객체 가지고 오기
		User user = (User) getRequestAttribute("ikep.user");
		Portal portal = (Portal) getRequestAttribute("ikep.portal");

		String cafeId = cafeService.createCafe(portal.getPortalId(), user, cafe);
		
		//My Cage  포틀릿 contents 캐시 Element 삭제
		cacheService.removeCacheElementPortletContent("Cachename-myCafe-portlet", "Cachemode-myCafe-portlet", "Elementkey-myCafe-portlet", user.getUserId());

		status.setComplete();

		return cafeId;
	}

	/**
	 * Cafe Category 별 cafe목록
	 * 
	 * @param searchCondition
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/listCafeCategoryView.do")
	public ModelAndView listWorkspaceTypeView(CafeSearchCondition searchCondition, BindingResult result,
			SessionStatus status) {

		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		searchCondition.setPortalId(portal.getPortalId());
		searchCondition.setCategoryId(searchCondition.getCategoryId());

		/**
		 * 타입 카테고리/조직별 워크스페이스 수
		 */
		Map<String, String> map = new HashMap<String, String>();
		map.put("portalId", portal.getPortalId());
		map.put("categoryId", searchCondition.getCategoryId());

		ModelAndView modelAndView = new ModelAndView();
		CafeCode cafeCode = new CafeCode();
		SearchResult<Cafe> searchResult = null;

		// 1 Level Category List / lowRankCafe Count
		List<Category> defaultCateogryList = categoryService.defaultCategoryList(portal.getPortalId());
		modelAndView.addObject("defaultCateogryList", defaultCateogryList);

		// LowRank CategoryList
		List<Category> lowRankCategoryList = categoryService.lowRankCategoryList(map);
		modelAndView.addObject("lowRankCategoryList", lowRankCategoryList);

		// Category별 Cafe조회
		searchResult = this.cafeService.listBySearchCondition(searchCondition);
		modelAndView.addObject("searchResult", searchResult);
		modelAndView.addObject("searchCondition", searchResult.getSearchCondition());
		modelAndView.addObject("cafeCode", cafeCode);

		modelAndView.setViewName("lightpack/cafe/cafe/listCafeCategoryView");
		return modelAndView;
	}

	/**
	 * cafe 내용조회
	 * 
	 * @param searchCondition
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/readCafeView.do")
	public String readCafeView(CafeSearchCondition searchCondition, Model model) {
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");

		Cafe cafe = new Cafe();
		cafe.setPortalId(portal.getPortalId());
		cafe.setCafeId(searchCondition.getCafeId());

		if (cafe.getCafeId() != null) {
			cafe = cafeService.read(searchCondition.getCafeId());
			model.addAttribute("cafe", cafe);
		}

		Map<String, String> map = new HashMap<String, String>();
		map.put("cafeId", cafe.getCafeId());
		map.put("memberType", "member");

		cafe.setMemberList(memberService.listCafeMember(map));

		List<Tag> tagList = tagService.listTagByItemId(cafe.getCafeId(), IKepConstant.ITEM_TYPE_CODE_CAFE);
		cafe.setTagList(tagList);

		List<FileData> fileDataList = fileService.getItemFile(cafe.getCafeId(), "N");
		cafe.setFileDataList(fileDataList);

		Boolean isSystemAdmin = isSystemAdmin(user.getUserId());
		model.addAttribute("isSystemAdmin", isSystemAdmin);
		model.addAttribute("searchCondition", searchCondition);
		model.addAttribute("cafe", cafe);

		return "lightpack/cafe/cafe/readCafeView";
	}

	/**
	 * cafe 수정폼
	 * 
	 * @param searchCondition
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/updateCafeInfoView.do")
	public String updateCafeInfoView(CafeSearchCondition searchCondition, Model model) {
		/**
		 * 카페 수정시 멤버회원 수정만 시샵은 남김
		 */
		Portal portal = (Portal) getRequestAttribute("ikep.portal");

		Cafe cafe = new Cafe();
		cafe.setPortalId(portal.getPortalId());
		cafe.setCafeId(searchCondition.getCafeId());

		if (cafe.getCafeId() != null) {
			cafe = cafeService.read(searchCondition.getCafeId());
		}

		// searchCondition.setIsOrganization(cafe.getIsOrganization());

		Map<String, String> map = new HashMap<String, String>();
		map.put("cafeId", cafe.getCafeId());
		map.put("memberType", "member");

		cafe.setMemberList(memberService.listCafeMember(map));

		List<Tag> tagList = tagService.listTagByItemId(cafe.getCafeId(), IKepConstant.ITEM_TYPE_CODE_CAFE);
		cafe.setTagList(tagList);

		List<FileData> fileDataList = fileService.getItemFile(cafe.getCafeId(), "N");
		cafe.setFileDataList(fileDataList);

		model.addAttribute("cafe", cafe);
		model.addAttribute("searchCondition", searchCondition);

		return "lightpack/cafe/cafe/updateCafeInfoView";
	}

	/**
	 * cafe 정보수정(멤버 제외)
	 * 
	 * @param cafe
	 * @param searchCondition
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/updateCafeInfo.do", method = RequestMethod.POST)
	public @ResponseBody
	String updateCafeInfo(@ValidEx(groups = { UpdateCafe.class }) Cafe cafe, CafeSearchCondition searchCondition,
			BindingResult result, SessionStatus status) {

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

		cafe.setPortalId(user.getPortalId());

		cafeService.updateCafeInfo(cafe, user);
		if(cafe.getFileId() == null || cafe.getFileId().equals("")) {
			fileService.removeItemFile(cafe.getCafeId());
		}

		status.setComplete();
		return cafe.getCafeId();
	}

	/**
	 * cafe 소개화면 수정폼
	 * 
	 * @param searchCondition
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/updateCafeIntroView.do")
	public String updateCafeIntroView(CafeSearchCondition searchCondition, Model model) {

		Portal portal = (Portal) getRequestAttribute("ikep.portal");

		Cafe cafe = new Cafe();
		cafe.setPortalId(portal.getPortalId());
		cafe.setCafeId(searchCondition.getCafeId());

		if (cafe.getCafeId() != null) {
			cafe = cafeService.read(searchCondition.getCafeId());
		}

		model.addAttribute("cafe", cafe);
		model.addAttribute("searchCondition", searchCondition);

		Map<String, String> map = new HashMap<String, String>();
		map.put("cafeId", cafe.getCafeId());
		map.put("memberType", "member");
		
		// ActiveX editor 사용여부 확인
		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		String useActiveX = "N";
		useActiveX = commonprop.getProperty("ikep4.activex.editor.use");

		model.addAttribute("useActiveX", useActiveX);

		model.addAttribute("cafe", cafe);

		return "lightpack/cafe/cafe/updateCafeIntroView";
	}

	/**
	 * cafe 정보수정(멤버 제외)
	 * 
	 * @param cafe
	 * @param searchCondition
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/updateCafeIntro.do", method = RequestMethod.POST)
	public @ResponseBody
	String updateCafeIntro(@ValidEx(groups = { UpdateCafeIntro.class }) Cafe cafe, CafeSearchCondition searchCondition,
			BindingResult result, SessionStatus status) {
		if (result.hasErrors()) {
			// 에러가 존재할 경우, IKEP4AjaxValidationException을 발생 시킨다.
			throw new IKEP4AjaxValidationException(result, messageSource); // BindingResult와
																			// BaseController의
																			// MessageSource를
																			// parameter로
																			// 전달해야
																			// 합니다.
		}
		if (result.hasErrors()) {
			return "forward:/lightpack/cafe/cafe/updateCafeIntroView.do";
		}
		User user = (User) getRequestAttribute("ikep.user");

		cafe.setPortalId(user.getPortalId());
		cafeService.updateCafeIntro(cafe, user);

		status.setComplete();

		return cafe.getCafeId();

	}

	/**
	 * cafe/부서 개설대기 메인
	 * 
	 * @param searchCondition
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/listWaitingOpenOrgView.do")
	public ModelAndView listWaitingOpenOrgView(CafeSearchCondition searchCondition, BindingResult result,
			SessionStatus status) {

		User user = (User) getRequestAttribute("ikep.user");
		ModelAndView modelAndView = new ModelAndView();

		CafeCode cafeCode = new CafeCode();

		searchCondition.setPortalId(user.getPortalId());
		searchCondition.setListType("WaitingOpenOrg");

		modelAndView.setViewName("lightpack/cafe/cafe/listWaitingOpenOrgView");
		// 트리정보
		String rootString = "";
		List<Map<String, Object>> root = getOrgGroup(null, searchCondition.getListType());
		rootString = getJSONString(root);

		modelAndView.addObject("searchCondition", searchCondition);
		modelAndView.addObject("cafeCode", cafeCode);

		modelAndView.addObject("rootString", rootString);
		return modelAndView;
	}

	/**
	 * Cafe Tree Child
	 * 
	 * @param searchCondition
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/requestGroupChildren.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	Map<String, Object> requestGroupChildren(@RequestParam(value = "groupId", required = false) String groupId,
			String listType, Model model) {
		/** 트리정보 **/
		Map<String, Object> item = new HashMap<String, Object>();

		List<Map<String, Object>> child = getOrgGroup(groupId, listType);
		// String childString = "";
		// childString = getJSONString(child); 한글깨짐 현상
		item.put("items", child);
		return item;
	}

	/**
	 * 해당 그룹의 하위 그룹 조회
	 * 
	 * @param groupId
	 * @param listType
	 * @return
	 */
	private List<Map<String, Object>> getOrgGroup(String groupId, String listType) {

		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<Cafe> groupNode = cafeService.getOrgGroup(groupId);

		for (Cafe cafe : groupNode) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("type", "group");
			if (cafe.getChildGroupCount() > 0) {
				if (portal.getDefaultLocaleCode() != null && portal.getDefaultLocaleCode().equals(user.getLocaleCode()))
					map.put("name",
							cafe.getGroupName() + "(" + cafe.getCountChildCafe() + "/" + cafe.getChildGroupCount()
									+ ")");
				else
					map.put("name",
							cafe.getGroupEnglishName() + "(" + cafe.getCountChildCafe() + "/"
									+ cafe.getChildGroupCount() + ")");
			} else {
				if (portal.getDefaultLocaleCode() != null && portal.getDefaultLocaleCode().equals(user.getLocaleCode()))
					map.put("name", cafe.getGroupName());
				else
					map.put("name", cafe.getGroupEnglishName());
			}
			map.put("code", cafe.getGroupId());
			map.put("groupTypeId", cafe.getGroupTypeId());
			map.put("parent", cafe.getParentGroupId());
			map.put("hasChild", cafe.getChildGroupCount());
			map.put("hasCafe", cafe.getCountCafe());
			if (cafe.getCountCafe() > 0)// 개설시 Tree Icon
				map.put("icon", "person");
			else
				map.put("icon", "dept"); // 미개설시 Tree Icon
			list.add(map);
		}
		return list;
	}

	/**
	 * JSON 자료 생성
	 * 
	 * @param obj
	 * @return
	 */
	public String getJSONString(Object obj) {

		ObjectMapper objectMapper = new ObjectMapper();
		String jsonString = "";

		try {
			jsonString = objectMapper.writeValueAsString(obj);
		} catch (Exception ex) {
			throw new JsonException();
		}

		return jsonString;
	}

	/**
	 * cafe 상태변경내용 저장(개설승인,폐쇄신청,폐쇄 )
	 * 
	 * @param cafeIds
	 * @param cafeStatus
	 * @param searchCondition
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/updateCafeStatus.do", method = RequestMethod.POST)
	public String updateCafeStatus(@RequestParam("cafeIds") List<String> cafeIds,
			@RequestParam("cafeStatus") String cafeStatus, CafeSearchCondition searchCondition, SessionStatus status) {

		User user = (User) getRequestAttribute("ikep.user");

		cafeService.updateCafeStatus(cafeIds, cafeStatus, user);

		status.setComplete();
		return "redirect:/lightpack/cafe/cafe/listCloseCafeView.do?listType=" + searchCondition.getListType()
				+ "&listUrl=" + searchCondition.getListUrl();
	}

	/**
	 * cafe 상태변경내용 저장(개설반려)
	 * 
	 * @param cafeIds
	 * @param cafeStatus
	 * @param searchCondition
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/updateCafeCloseRejectStatus.do", method = RequestMethod.POST)
	public String updateCafeCloseRejectStatus(@RequestParam("cafeIds") List<String> cafeIds,
			@RequestParam("cafeStatus") String cafeStatus, CafeSearchCondition searchCondition, SessionStatus status) {
		// Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");

		cafeService.updateCafeStatus(cafeIds, "Reject", user);

		status.setComplete();
		return "redirect:/lightpack/cafe/cafe/listCloseCafeView.do?listType=" + searchCondition.getListType()
				+ "&listUrl=" + searchCondition.getListUrl();
	}

	/**
	 * cafe 상태변경내용 저장(개설승인,폐쇄신청,폐쇄 )
	 * 
	 * @param cafeIds
	 * @param cafeStatus
	 * @param searchCondition
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/updateCafeStatusAjax.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	void updateCafeStatusAjax(@RequestParam("cafeIds") List<String> cafeIds,
			@RequestParam("cafeStatus") String cafeStatus, CafeSearchCondition searchCondition, SessionStatus status) {

		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");
		try {
			cafeService.updateCafeStatus(portal, user, cafeIds, cafeStatus);
		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}

	}

	/**
	 * cafe 폐쇄신청
	 * 
	 * @param cafeIds
	 * @param cafeStatus
	 * @param searchCondition
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/updateCafeCloseStatusAjax.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	boolean updateCafeCloseStatusAjax(@RequestParam("cafeId") String cafeId, CafeSearchCondition searchCondition,
			SessionStatus status) {
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");
		try {

			Cafe cafe = new Cafe();

			cafe.setPortalId(portal.getPortalId());
			cafe.setCafeId(cafeId);
			cafe.setCafeStatus(CafeConstants.CAFE_STATUS_CLOSE);

			cafe.setUpdaterId(user.getUserId());
			cafe.setUpdaterName(user.getUserName());

			cafeService.updateStatus(cafe);
			
			//My cafe 포틀릿 contents 캐시 Element 전체 삭제
			cacheService.removeCacheElementPortletContentAll("Cachename-myCafe-portlet");
		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}
		return true;
	}

	/**
	 * cafe 부서 Cafe 상태변경내용 저장(ONLY 폐쇄)
	 * 
	 * @param cafeIds
	 * @param cafeStatus
	 * @param searchCondition
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/updateCafeCloseStatus.do", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	void updateOrgCafeStatus(@RequestParam("cafeIds") List<String> cafeIds, CafeSearchCondition searchCondition,
			SessionStatus status) {

		User user = (User) getRequestAttribute("ikep.user");

		cafeService.updateCafeStatus(cafeIds, "C", user);
		
		//My cafe 포틀릿 contents 캐시 Element 전체 삭제
		cacheService.removeCacheElementPortletContentAll("Cachename-myCafe-portlet");

		status.setComplete();
	}

	/**
	 * cafe 영구 삭제처리 ( 플래그값 변경 CAFE_STATUS = ED , 배치 프로그램으로 영구 삭제처리 함)
	 * 
	 * @param cafeIds
	 * @param searchCondition
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/deleteCafe.do", method = RequestMethod.POST)
	public String deleteCafe(@RequestParam("cafeIds") List<String> cafeIds, CafeSearchCondition searchCondition,
			SessionStatus status) {

		User user = (User) getRequestAttribute("ikep.user");

		cafeService.updateCafeStatus(cafeIds, "ED", user);
		status.setComplete();

		return "redirect:/lightpack/cafe/cafe/listCloseCafeView.do?listType=" + searchCondition.getListType()
				+ "&listUrl=" + searchCondition.getListUrl();
	}

	/**
	 * cafe 영구 삭제처리 ( 플래그값 변경 CAFE_STATUS = ED , 배치 프로그램으로 영구 삭제처리 함)
	 * 
	 * @param cafeIds
	 * @param searchCondition
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/deleteCafeAjax.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	void deleteCafeAjax(@RequestParam("cafeIds") List<String> cafeIds, CafeSearchCondition searchCondition,
			SessionStatus status) {

		User user = (User) getRequestAttribute("ikep.user");

		try {
			cafeService.updateCafeStatus(cafeIds, "ED", user);
		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}
	}

	/**
	 * CAFE LOGPAGE
	 * 
	 * @param searchCondition
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/listCafeLogView.do")
	public ModelAndView listCafeLogView(CafeSearchCondition searchCondition, BindingResult result, SessionStatus status) {
		Portal portal = (Portal) getRequestAttribute("ikep.portal");

		searchCondition.setPortalId(portal.getPortalId());
		CafeCode cafeCode = new CafeCode();
		searchCondition.setCafeStatus("LOG");
		searchCondition.setListUrl("listCafeLogView.do");

		if (searchCondition.getStartDate() != null && searchCondition.getEndDate() != null) {
			searchCondition.setStartDate(timeZoneSupportService.convertServerTimeZone(searchCondition.getStartDate()));
			searchCondition.setEndDate(timeZoneSupportService.convertServerTimeZone(searchCondition.getEndDate()));
		}
		SearchResult<Cafe> searchResult = this.cafeService.listBySearchCondition(searchCondition);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("lightpack/cafe/cafe/listCafeLogView");
		modelAndView.addObject("searchResult", searchResult);
		modelAndView.addObject("searchCondition", searchResult.getSearchCondition());
		modelAndView.addObject("cafeCode", cafeCode);

		return modelAndView;

	}

	private static final String CAFE_MANAGER = "Cafe";

	/**
	 * Coll 전체 관리자 여부 체크
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
			isAdmin = true; // Collaboration 서비스 관리자
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

		// 시스템,Collaboration관리자 체크
		Boolean isSystemAdmin = isSystemAdmin(userId);

		if (isSystemAdmin) {
			isAdmin = true; // Collaboration 서비스 관리자
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

	@RequestMapping(value = "/getCafeImage.do")
	public ModelAndView getCafeImage(String cafeId) {

		ModelAndView mav = new ModelAndView("lightpack/cafe/cafe/listCafeImage");

		CafeSearchCondition searchCondition = new CafeSearchCondition();
		searchCondition.setCafeId(cafeId);

		mav.addObject("searchCondition", searchCondition);

		return mav;
	}

	@RequestMapping(value = "/getSubListCafeImage.do")
	public ModelAndView getSubLiatCafeImage(CafeSearchCondition cafeSearchCondition) {

		ModelAndView mav = new ModelAndView("/lightpack/cafe/cafe/subListCafeImage");

		try {

			cafeSearchCondition.setPagePerRecord(10);
			SearchResult<Cafe> searchResult = cafeService.getCafeImageFile(cafeSearchCondition);

			mav.addObject("searchCondition", searchResult.getSearchCondition());
			mav.addObject("searchResult", searchResult);

		} catch (Exception ex) {
			// ex.printStackTrace();
			throw new IKEP4AjaxException("", ex);
		}
		return mav;
	}

	@RequestMapping(value = "/getCafeDateCount.do")
	public ModelAndView getCafeDateCount(String cafeId) {

		ModelAndView mav = new ModelAndView("lightpack/cafe/cafe/listCafeDateCount");

		List<Cafe> cafeDateCountList = cafeService.getCafeDateCount(cafeId);

		mav.addObject("cafeDateCountList", cafeDateCountList);
		mav.addObject("cafeId", cafeId);

		return mav;
	}

}