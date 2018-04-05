package com.lgcns.ikep4.portal.admin.code.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxValidationException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AuthorizedException;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.core.code.BoardCode;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.addressbook.model.Category;
import com.lgcns.ikep4.support.addressbook.service.CategoryService;
import com.lgcns.ikep4.support.security.acl.annotations.AccessType;
import com.lgcns.ikep4.support.security.acl.annotations.Attribute;
import com.lgcns.ikep4.support.security.acl.annotations.IsAccess;
import com.lgcns.ikep4.support.security.acl.validation.AccessingResult;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;
import com.lgcns.ikep4.support.user.code.model.I18nMessage;
import com.lgcns.ikep4.support.user.code.model.Nation;
import com.lgcns.ikep4.support.user.code.service.I18nMessageService;
import com.lgcns.ikep4.support.user.code.service.NationService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.lang.StringUtil;


/**
 * 국가 코드 관리 컨트롤러
 * 
 * @author 양새로훈(yang.sae@gmail.com)
 * @version $Id: NationController.java 16243 2011-08-18 04:10:43Z giljae $
 */
@Controller
@RequestMapping(value = "/portal/admin/code/category")
@SessionAttributes("nation")
public class AddressCategoryController extends BaseController {

	@Autowired
	private NationService nationService;

	@Autowired
	private I18nMessageService i18nMessageService;
	
	@Autowired
	private CategoryService categoryService;

	/**
	 * searchCondition에 맞춰 상단의 목록을 가져옴
	 * 
	 * @param searchCondition 목록 검색 조건
	 * @param accessResult 사용자 권한체크 결과
	 * @param request HttpServletRequest 객체
	 * @param status SesstionStatus 객체
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/getList.do")
	public ModelAndView getList(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AdminSearchCondition searchCondition,
			AccessingResult accessResult, HttpServletRequest request, SessionStatus status) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		ModelAndView mav = new ModelAndView("portal/admin/code/nation/nationList");

		User sessionUser = (User) getRequestAttribute("ikep.user");
		Portal portal = (Portal) getRequestAttribute("ikep.portal");

		Nation nation = null;

		try {

			if (StringUtil.isEmpty(searchCondition.getSortColumn())) {
				searchCondition.setSortColumn("NATION_CODE");
			}
			if (StringUtil.isEmpty(searchCondition.getSortType())) {
				searchCondition.setSortType("ASC");
			}

			String nationCode = request.getParameter("tempCode");

			String userLocaleCode = sessionUser.getLocaleCode();

			if (nationCode != null && !"".equalsIgnoreCase(nationCode)) {
				nation = nationService.read(nationCode);
				nation.setCodeCheck("modify");
				nation.setI18nMessageList(i18nMessageService.makeExistLocaleList(IKepConstant.ITEM_TYPE_CODE_PORTAL,
						nationCode, "nationName"));

			} else {
				nation = new Nation();
				nation.setI18nMessageList(i18nMessageService.makeInitLocaleList("nationName"));
			}

			searchCondition.setFieldName("nationName");
			searchCondition.setUserLocaleCode(sessionUser.getLocaleCode());
			searchCondition.setPortalId(portal.getPortalId());

			SearchResult<Nation> searchResult = nationService.list(searchCondition);

			BoardCode boardCode = new BoardCode();

			mav.addObject("searchResult", searchResult);
			mav.addObject("searchCondition", searchResult.getSearchCondition());
			mav.addObject("boardCode", boardCode);
			mav.addObject("userLocaleCode", userLocaleCode);
			mav.addObject("nation", nation);
			mav.addObject("localeSize", i18nMessageService.selectLocaleAll().size());
		} catch (Exception e) {
			throw new IKEP4AjaxException("", e);
		}

		return mav;
	}

	/**
	 * 하단의 폼 화면에 들어가는 정보를 가져온다.
	 * 첫 로딩시에는 빈 폼을 보여주고 사용자가 리스트에서 항목을 선택하는 경우 
	 * 해당 항목의 Code를 이용하여 정보를 가져와서 보여준다.
	 * 
	 * @param code 상세정보를 요청받은 국가 Code
	 * @param fieldName 다국어메시지 표현에 필요한 필드네임
	 * @param itemTypeCode 다국어메시지 표현에 필요한 ItemType의 Code
	 * @param model Model 객체
	 * @param accessResult 사용자 권한체크 결과
	 * @return String 상세정보화면 URI
	 */
	@RequestMapping(value = "/getForm.do", method = RequestMethod.GET)
	public String getForm(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) String code,
			String fieldName, String itemTypeCode, Model model, AccessingResult accessResult) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		Nation nation = null;

		User sessionUser = (User) getRequestAttribute("ikep.user");
		String userLocaleCode = sessionUser.getLocaleCode();

		if (code != null && !"".equalsIgnoreCase(code)) {
			nation = nationService.read(code);
			nation.setCodeCheck("modify");
			nation.setI18nMessageList(i18nMessageService.makeExistLocaleList(IKepConstant.ITEM_TYPE_CODE_PORTAL, code,
					"nationName"));
		} else {
			nation = new Nation();
			nation.setCodeCheck("new");
			nation.setI18nMessageList(i18nMessageService.makeInitLocaleList("nationName"));
		}

		model.addAttribute("userLocaleCode", userLocaleCode);
		model.addAttribute("nation", nation);
		model.addAttribute("localeSize", i18nMessageService.selectLocaleAll().size());

		return "portal/admin/code/nation/form";
	}

	/**
	 * 등록/수정시에 해당 Code의 중복을 체크하여
	 * 중복된 경우 "duplicated" 중복이 아닌 경우 "success"라는 문자열을 반환한다.
	 * 
	 * @param code 중복을 체크할 국가 Code
	 * @param accessResult 사용자 권한체크 결과
	 * @return String 중복 유무를 알려주는 문자열 Flag
	 */
	@RequestMapping(value = "/checkCode.do")
	public @ResponseBody
	String checkCode(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) String code,
			AccessingResult accessResult) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		boolean result = nationService.exists(code);

		if (result) {
			return "duplicated";
		} else {
			return "success";
		}
	}

	/**
	 * 타임존을 신규로 등록하거나 수정한다.
	 * Code가 중복되는 경우 수정, 중복되지 않는 경우 생성 프로세스를 진행한다.
	 * 생성, 수정이 끝난 후에는 해당 국가 Code를 반환하여 form을 불러오는데 사용한다.
	 * 
	 * @param nation 신규/수정 등록하고자 하는 국가(Nation) 객체
	 * @param accessResult 사용자 권한체크 결과
	 * @param result BindingResult 객체
	 * @param status SessionStatus 객체
	 * @param request HttpServletRequest 객체
	 * @return code 최종 등록한 타임존의 상세 정보를 가져오기 위한 ID
	 */
	@RequestMapping(value = "/createNation.do", method = RequestMethod.POST)
	public @ResponseBody
	String onSubmit(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) @Valid Nation nation,
			AccessingResult accessResult, BindingResult result, SessionStatus status, HttpServletRequest request) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		if (result.hasErrors()) {
			throw new IKEP4AjaxValidationException(result, messageSource);
		}

		String code = nation.getNationCode();
		boolean isCodeExist = nationService.exists(code);

		// nation가 이미 존재하는 경우, 기존의 코드를 수정하는 프로세스
		if (isCodeExist) {
			List<I18nMessage> i18nMessageList = i18nMessageService.fillMandatoryField(nation.getI18nMessageList(),
					IKepConstant.ITEM_TYPE_CODE_PORTAL, nation.getNationCode());
			nation.setUpdaterId("admin");
			nation.setUpdaterName("관리자");
			nation.setI18nMessageList(i18nMessageList);

			nationService.update(nation);
		} else {

			nation.setRegisterId("admin");
			nation.setRegisterName("관리자");
			nation.setUpdaterId("admin");
			nation.setUpdaterName("관리자");

			nationService.create(nation);
		}

		status.setComplete();

		return code;
	}

	/**
	 * 해당 국가 Code를 삭제한다.
	 * 
	 * @param code 삭제할 국가 Code
	 * @param accessResult 사용자 권한체크 결과
	 * @param request HttpServletRequest 객체
	 * @return String redirect 되는 URI
	 */
	@RequestMapping(value = "/deleteNation.do")
	public String delete(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) @RequestParam("nationCode") String code,
			AccessingResult accessResult, HttpServletRequest request) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		nationService.delete(code);

		return "redirect:/portal/admin/code/nation/getList.do";
	}
	
	@RequestMapping(value = "/editCategory")
	public ModelAndView editCategory() {

		Category categoryBoardId = new Category();
		categoryBoardId.setBoardId("100000000001");

		List<Category> categoryList = null;
		categoryList = this.categoryService.listCategory(categoryBoardId);		
		
		ModelAndView model = new ModelAndView();
		
		model.addObject("categoryList", categoryList);
		model.addObject("categoryBoardId", categoryBoardId);
		return model;
	}
	
	@RequestMapping(value = "/createCategoryBoard")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String createCategoryBoard(Category category ,@RequestParam(value = "boardId") String boardId) {	
		User user = (User) getRequestAttribute("ikep.user");
		
		String addNameList = category.getAddNameList();
		String delIdList = category.getDelIdList();
		String updateNameList = category.getUpdateNameList();
		String updateIdList = category.getUpdateIdList();

		Category receiveCategoryNmList = new Category();
		receiveCategoryNmList.setAddNameList(addNameList);
		receiveCategoryNmList.setDelIdList(delIdList);
		receiveCategoryNmList.setUpdateIdList(updateIdList);
		receiveCategoryNmList.setUpdateNameList(updateNameList);
		receiveCategoryNmList.setBoardId(boardId);
		
		receiveCategoryNmList.setRegisterId(user.getUserId());
		receiveCategoryNmList.setRegisterName(user.getUserName());
		receiveCategoryNmList.setAlignList(category.getAlignList());

		List<Category> list = new ArrayList<Category>();
		list.add(receiveCategoryNmList);
		this.categoryService.insertCategoryNm(list);
		
		return "success";
	}

}
