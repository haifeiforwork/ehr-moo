package com.lgcns.ikep4.support.favorite.web;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.validation.Valid;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.base.constant.CommonConstant;
import com.lgcns.ikep4.support.favorite.model.PortalFavorite;
import com.lgcns.ikep4.support.favorite.model.PortalFavoriteSearchCondition;
import com.lgcns.ikep4.support.favorite.service.PortalFavoriteService;
import com.lgcns.ikep4.support.favorite.util.FavoriteConstant;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.lang.StringUtil;


/**
 * PortalFavorite 처리 Controller
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: PortalFavoriteController.java 5682 2011-04-08 10:19:50Z
 *          handul32 $
 */
@Controller
@RequestMapping(value = "/support/favorite")
public class PortalFavoriteController extends BaseController {

	/**
	 * Favorite 서비스
	 */
	@Autowired
	private PortalFavoriteService favoriteService;
	
	/**
	 * 도큐먼트 리스트 화면
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getListForDocument.do")
	public ModelAndView getListForDocument(String userId, String userLocale) {

		ModelAndView mav = new ModelAndView("support/favorite/listForDocument");

		PortalFavoriteSearchCondition searchCondition = new PortalFavoriteSearchCondition();

		User user = (User) getRequestAttribute("ikep.user");

		if (!StringUtil.isEmpty(userId)) {
			searchCondition.setUserLocaleCode(userLocale);
			searchCondition.setRegisterId(userId);
		} else {
			searchCondition.setUserLocaleCode(user.getLocaleCode());
			searchCondition.setRegisterId(user.getUserId());
		}

		mav.addObject("searchCondition", searchCondition);
		return mav;
	}

	/**
	 * 도큐먼트 리스트 검색
	 * 
	 * @param searchCondition
	 * @param channelId
	 * @return
	 */
	@RequestMapping(value = "/getSubListForDocument.do")
	public ModelAndView getSubListForDocument(PortalFavoriteSearchCondition searchCondition) {

		ModelAndView mav = new ModelAndView("/support/favorite/subListForDocument");

		try {
			// searchCondition.setUserLocaleCode(user.getLocaleCode());
			// searchCondition.setRegisterId(user.getUserId());

			searchCondition.setPagePerRecord(FavoriteConstant.LIST_PAGE_SIZE);
			
			//검색할 게시물의 유형이 선택되지 않는 경우 검색하지 않는다
			if(searchCondition.getModuleCodeList() != null)
			{
				SearchResult<PortalFavorite> searchResult = favoriteService.getAllForDocument(searchCondition);
				mav.addObject("searchResult", searchResult);
				mav.addObject("searchCondition", searchResult.getSearchCondition());
			}
			else
			{
				mav.addObject("searchResult", null);
				
				searchCondition.setCurrentCount(0);
				searchCondition.setRecordCount(0);				
				mav.addObject("searchCondition", searchCondition);
			}

		} catch (Exception ex) {
			// ex.printStackTrace();
			throw new IKEP4AjaxException("", ex);
		}
		return mav;
	}

	/**
	 * 피플 리스트 화면
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getListForPeople.do")
	public ModelAndView getListForPeople(String userId, String userLocale) {

		ModelAndView mav = new ModelAndView("support/favorite/listForPeople");

		PortalFavoriteSearchCondition searchCondition = new PortalFavoriteSearchCondition();

		User user = (User) getRequestAttribute("ikep.user");

		if (!StringUtil.isEmpty(userId)) {
			searchCondition.setUserLocaleCode(userLocale);
			searchCondition.setRegisterId(userId);
		} else {
			searchCondition.setUserLocaleCode(user.getLocaleCode());
			searchCondition.setRegisterId(user.getUserId());
		}

		mav.addObject("searchCondition", searchCondition);

		return mav;
	}

	/**
	 * 피플 리스트 검색
	 * 
	 * @param searchCondition
	 * @param channelId
	 * @return
	 */
	@RequestMapping(value = "/getSubListForPeople.do")
	public ModelAndView getSubListForPeople(PortalFavoriteSearchCondition searchCondition) {

		ModelAndView mav = new ModelAndView("/support/favorite/subListForPeople");

		try {
			// searchCondition.setFieldName("jobTitleName");
			// searchCondition.setUserLocaleCode(user.getLocaleCode());
			// searchCondition.setRegisterId(user.getUserId());

			searchCondition.setPagePerRecord(FavoriteConstant.LIST_PAGE_SIZE);

			SearchResult<PortalFavorite> searchResult = favoriteService.getAllForPeople(searchCondition);

			mav.addObject("searchResult", searchResult);
			mav.addObject("searchCondition", searchResult.getSearchCondition());

		} catch (Exception ex) {
			// ex.printStackTrace();
			throw new IKEP4AjaxException("", ex);
		}
		return mav;
	}

	/**
	 * 요약 리스트 검색
	 * 
	 * @param searchCondition
	 * @param channelId
	 * @return
	 */
	@RequestMapping(value = "/getShortcut.do")
	public ModelAndView getShortcut() {

		ModelAndView mav = new ModelAndView("support/favorite/shortcut");

		try {
			User user = (User) getRequestAttribute("ikep.user");

			PortalFavoriteSearchCondition searchCondition = new PortalFavoriteSearchCondition();
			searchCondition.setFieldName("jobTitleName");
			searchCondition.setUserLocaleCode(user.getLocaleCode());
			searchCondition.setRegisterId(user.getUserId());
			searchCondition.setPagePerRecord(FavoriteConstant.SUMMARY_PAGE_SIZE);
			searchCondition.setEndRowIndex(FavoriteConstant.SUMMARY_PAGE_SIZE);

			if(CommonConstant.PACKAGE_VERSION.equals(CommonConstant.IKEP_VERSION_FULL) || CommonConstant.PACKAGE_VERSION.equals(CommonConstant.IKEP_VERSION_LIGHT)) {
				SearchResult<PortalFavorite> searchResultForDocument = favoriteService.getSummaryForDocument(searchCondition);
				mav.addObject("searchResultForDocument", searchResultForDocument);
			}

			SearchResult<PortalFavorite> searchResultForPeople = favoriteService.getSummaryForPeople(searchCondition);
			mav.addObject("searchResultForPeople", searchResultForPeople);
			mav.addObject("searchCondition", searchResultForPeople.getSearchCondition());

		} catch (Exception ex) {
			// ex.printStackTrace();
			throw new IKEP4AjaxException("", ex);
		}
		return mav;
	}

	/**
	 * Favorite 생성
	 * 
	 * @param favorite
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/createFavorite.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	String createFavorite(@Valid PortalFavorite favorite, BindingResult result, SessionStatus status) {

		Map<String, String> reMap = new HashMap<String, String>();
		ObjectMapper objectMapper = new ObjectMapper();

		try {
			User user = (User) getRequestAttribute("ikep.user");
			if (result.hasErrors()) {
				reMap.put("status", "inputError");
				reMap.put("message",
						messageSource.getMessage("ui.support.favorite.message.inputError", null, new Locale(user.getLocaleCode())));
				return objectMapper.writeValueAsString(reMap);
			}

			favorite.setRegisterId(user.getUserId());
			favorite.setRegisterName(user.getUserName());
			favorite.setUpdaterId(user.getUserId());
			favorite.setUpdaterName(user.getUserName());

			if (favoriteService.exists(favorite)) {
				reMap.put("status", "exists");
				reMap.put("message",
						messageSource.getMessage("ui.support.favorite.message.duplicated", null, new Locale(user.getLocaleCode())));
			} else {
				String favoriteId = favoriteService.create(favorite);
				reMap.put("favoriteId", favoriteId);
				reMap.put("status", "success");
				reMap.put("message",
						messageSource.getMessage("ui.support.favorite.message.success", null, new Locale(user.getLocaleCode())));
			}

			// 세션 상태를 complete하여 이중 전송 방지
			status.setComplete();

			return objectMapper.writeValueAsString(reMap);

		} catch (Exception ex) {
			// ex.printStackTrace();
			throw new IKEP4AjaxException("", ex);
		}
	}

	/**
	 * Favorite 삭제
	 * 
	 * @param favoriteId
	 * @param targetId
	 * @return
	 */
	@RequestMapping(value = "/deleteFavorite.do")
	public @ResponseBody
	String deleteFavorite(String favoriteId, String targetId) {

		Map<String, String> reMap = new HashMap<String, String>();
		ObjectMapper objectMapper = new ObjectMapper();

		try {

			User user = (User) getRequestAttribute("ikep.user");

			PortalFavorite favorite = new PortalFavorite();
			favorite.setFavoriteId(favoriteId);
			favorite.setTargetId(targetId);
			favorite.setRegisterId(user.getUserId());

			favoriteService.delete(favorite);

			reMap.put("status", "success");
			reMap.put("message",
					messageSource.getMessage("ui.support.favorite.message.success", null, new Locale(user.getLocaleCode())));

			return objectMapper.writeValueAsString(reMap);

		} catch (Exception ex) {
			// ex.printStackTrace();
			throw new IKEP4AjaxException("", ex);
		}

	}

	/**
	 * Favorite 등록 체크
	 * 
	 * @param targetId
	 * @return
	 */
	@RequestMapping(value = "/checkFavorite.do")
	public @ResponseBody
	String checkFavorite(String targetId) {

		Map<String, String> reMap = new HashMap<String, String>();
		ObjectMapper objectMapper = new ObjectMapper();

		try {

			User user = (User) getRequestAttribute("ikep.user");

			PortalFavorite favorite = new PortalFavorite();
			favorite.setTargetId(targetId);
			favorite.setRegisterId(user.getUserId());

			if (favoriteService.exists(favorite)) {
				reMap.put("status", "exists");
			} else {
				reMap.put("status", "success");
			}

			return objectMapper.writeValueAsString(reMap);

		} catch (Exception ex) {
			// ex.printStackTrace();
			throw new IKEP4AjaxException("", ex);
		}

	}

	/**
	 * Favorite 테스트
	 * 
	 * @return
	 */
	@RequestMapping("/favoriteTest")
	public String uploadTest() {

		return "support/favorite/favoriteTest";
	}

}
