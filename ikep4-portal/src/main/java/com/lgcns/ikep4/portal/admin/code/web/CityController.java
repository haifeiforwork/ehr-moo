package com.lgcns.ikep4.portal.admin.code.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxValidationException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AuthorizedException;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.core.code.BoardCode;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.security.acl.annotations.AccessType;
import com.lgcns.ikep4.support.security.acl.annotations.Attribute;
import com.lgcns.ikep4.support.security.acl.annotations.IsAccess;
import com.lgcns.ikep4.support.security.acl.validation.AccessingResult;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;
import com.lgcns.ikep4.support.user.code.model.City;
import com.lgcns.ikep4.support.user.code.model.I18nMessage;
import com.lgcns.ikep4.support.user.code.service.CityService;
import com.lgcns.ikep4.support.user.code.service.I18nMessageService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.lang.StringUtil;

/**
 * 도시 관리 컨트롤러 
 * 
 * @author 송희정
 *
 */
@Controller
@RequestMapping(value="/portal/admin/code/city")
public class CityController extends BaseController{
	
	
	@Autowired
	private CityService cityService;
	
	@Autowired
	private I18nMessageService i18nMessageService;
	
	
	/**
	 * 도시 목록 조회
	 * @param accessResult
	 * @param searchCondition
	 * @param request
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/getCityList.do")
	public ModelAndView getList(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			AdminSearchCondition searchCondition, HttpServletRequest request, SessionStatus status) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		ModelAndView mav = new ModelAndView("portal/admin/code/city/cityList");

		User sessionUser = (User) getRequestAttribute("ikep.user");
		Portal portal = (Portal) getRequestAttribute("ikep.portal");

		if (StringUtil.isEmpty(searchCondition.getSortColumn())) {
			searchCondition.setSortColumn("NATION_CODE");
		}
		if (StringUtil.isEmpty(searchCondition.getSortType())) {
			searchCondition.setSortType("ASC");
		}
		
		searchCondition.setFieldName("cityName");
		searchCondition.setUserLocaleCode(sessionUser.getLocaleCode());
		searchCondition.setPortalId(portal.getPortalId());
		searchCondition.setNationCode(searchCondition.getNationCode());
		SearchResult<City> searchResult = cityService.list(searchCondition);

		BoardCode boardCode = new BoardCode();

		mav.addObject("searchResult", searchResult);
		mav.addObject("searchCondition", searchResult.getSearchCondition());
		mav.addObject("boardCode", boardCode);
		mav.addObject("nationCode", searchCondition.getNationCode());
		
		return mav;
	}
	
	
	
	/**
	 * 도시 등록폼
	 * @param accessResult
	 * @param model
	 * @param cityId
	 * @return
	 */
	@RequestMapping(value = "/getForm.do", method = RequestMethod.GET)
	public String getForm(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			Model model, String cityId, String nationCode) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		City city = null;

		User sessionUser = (User) getRequestAttribute("ikep.user");
		String userLocaleCode = sessionUser.getLocaleCode();

		if (!StringUtil.isEmpty(cityId)) {
			city = cityService.read(cityId);
			city.setI18nMessageList(i18nMessageService.makeExistLocaleList(IKepConstant.ITEM_TYPE_CODE_PORTAL, cityId, "cityName"));
		} else {
			city = new City();
			city.setI18nMessageList(i18nMessageService.makeInitLocaleList("cityName"));
		}

		model.addAttribute("city", city);
		model.addAttribute("nationCode", nationCode);
		model.addAttribute("userLocaleCode", userLocaleCode);
		model.addAttribute("localeSize", i18nMessageService.selectLocaleAll().size());

		return "portal/admin/code/city/form";
	}
	


	
	/**
	 * 도시 등록
	 * @param accessResult
	 * @param city
	 * @param result
	 * @param status
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/createCity.do", method = RequestMethod.POST)
	public @ResponseBody String onSubmit(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			@Valid City city, BindingResult result, SessionStatus status, HttpServletRequest request) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		if (result.hasErrors()) {
			throw new IKEP4AjaxValidationException(result, messageSource);
		}

		User sessionUser = (User) getRequestAttribute("ikep.user");
		boolean isCodeExist = cityService.exists(city.getCityId());

		// nation가 이미 존재하는 경우, 기존의 코드를 수정하는 프로세스
		if (isCodeExist) {
			List<I18nMessage> i18nMessageList = i18nMessageService.fillMandatoryField(city.getI18nMessageList(), IKepConstant.ITEM_TYPE_CODE_PORTAL, city.getCityId());
			city.setUpdaterId(sessionUser.getUserId());
			city.setUpdaterName(sessionUser.getUserName());
			city.setI18nMessageList(i18nMessageList);

			cityService.update(city);
		} else {
			city.setRegisterId(sessionUser.getUserId());
			city.setRegisterName(sessionUser.getUserName());
			city.setUpdaterId(sessionUser.getUserId());
			city.setUpdaterName(sessionUser.getUserName());

			cityService.create(city);
		}

		status.setComplete();

		return city.getCityId();
	}

	
	
	/**
	 * 도시 삭제
	 * @param accessResult
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/deleteCity.do")
	public @ResponseBody String delete(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			@RequestParam("cityId") String cityId) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		cityService.delete(cityId);

		return cityId;
	}
	
	
	
	
	

}
