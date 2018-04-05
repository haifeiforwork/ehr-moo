package com.lgcns.ikep4.support.recent.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.base.constant.CommonConstant;
import com.lgcns.ikep4.support.favorite.util.FavoriteConstant;
import com.lgcns.ikep4.support.recent.model.Recent;
import com.lgcns.ikep4.support.recent.model.RecentSearchCondition;
import com.lgcns.ikep4.support.recent.service.RecentService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.lang.StringUtil;


/**
 * Recent 처리 Controller
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: RecentController.java 17694 2012-03-26 06:12:31Z yu_hs $
 */
@Controller
@RequestMapping(value = "/support/recent")
public class RecentController extends BaseController {

	/**
	 * Recent 서비스
	 */
	@Autowired
	private RecentService recentService;
	
	/**
	 * 도큐먼트 리스트 화면
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getListForDocument.do")
	public ModelAndView getListForDocument(String userId, String userLocale) {

		ModelAndView mav = new ModelAndView("support/recent/listForDocument");

		RecentSearchCondition searchCondition = new RecentSearchCondition();

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
	public ModelAndView getSubListForDocument(RecentSearchCondition searchCondition) {

		ModelAndView mav = new ModelAndView("/support/recent/subListForDocument");

		try {
			// searchCondition.setUserLocaleCode(user.getLocaleCode());
			// searchCondition.setRegisterId(user.getUserId());

			searchCondition.setPagePerRecord(FavoriteConstant.LIST_PAGE_SIZE);
			
			if(searchCondition.getModuleCodeList() != null)
			{
				SearchResult<Recent> searchResult = recentService.getAllForDocument(searchCondition);

				mav.addObject("searchResult", searchResult);
				mav.addObject("searchCondition", searchResult.getSearchCondition());
			}
			else
			{
				searchCondition.setCurrentCount(0);
				searchCondition.setRecordCount(0);
				mav.addObject("searchResult", null);
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

		ModelAndView mav = new ModelAndView("support/recent/listForPeople");

		RecentSearchCondition searchCondition = new RecentSearchCondition();

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
	public ModelAndView getSubListForPeople(RecentSearchCondition searchCondition) {

		ModelAndView mav = new ModelAndView("/support/recent/subListForPeople");

		try {
			// searchCondition.setFieldName("jobTitleName");
			// searchCondition.setUserLocaleCode(user.getLocaleCode());
			// searchCondition.setRegisterId(user.getUserId());

			searchCondition.setPagePerRecord(FavoriteConstant.LIST_PAGE_SIZE);

			SearchResult<Recent> searchResult = recentService.getAllForPeople(searchCondition);

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

		ModelAndView mav = new ModelAndView("support/recent/shortcut");

		try {
			User user = (User) getRequestAttribute("ikep.user");

			RecentSearchCondition searchCondition = new RecentSearchCondition();
			searchCondition.setFieldName("jobTitleName");
			searchCondition.setUserLocaleCode(user.getLocaleCode());
			searchCondition.setRegisterId(user.getUserId());
			searchCondition.setPagePerRecord(FavoriteConstant.SUMMARY_PAGE_SIZE);
			searchCondition.setEndRowIndex(FavoriteConstant.SUMMARY_PAGE_SIZE);

			if(CommonConstant.PACKAGE_VERSION.equals(CommonConstant.IKEP_VERSION_FULL) || CommonConstant.PACKAGE_VERSION.equals(CommonConstant.IKEP_VERSION_LIGHT)) {
				SearchResult<Recent> searchResultForDocument = recentService.getSummaryForDocument(searchCondition);
				mav.addObject("searchResultForDocument", searchResultForDocument);
			}

			SearchResult<Recent> searchResultForPeople = recentService.getSummaryForPeople(searchCondition);
			mav.addObject("searchResultForPeople", searchResultForPeople);
			mav.addObject("searchCondition", searchResultForPeople.getSearchCondition());

		} catch (Exception ex) {
			// ex.printStackTrace();
			throw new IKEP4AjaxException("", ex);
		}
		return mav;
	}

	/**
	 * My Space 리스트 검색
	 * 
	 * @param searchCondition
	 * @param channelId
	 * @return
	 */
	@RequestMapping(value = "/getMySpace.do")
	public ModelAndView getMySpace() {

		ModelAndView mav = new ModelAndView("portal/main/mySpace");

		try {
			User user = (User) getRequestAttribute("ikep.user");

			RecentSearchCondition searchCondition = new RecentSearchCondition();
			searchCondition.setFieldName("jobTitleName");
			searchCondition.setUserLocaleCode(user.getLocaleCode());
			searchCondition.setRegisterId(user.getUserId());
			searchCondition.setPagePerRecord(FavoriteConstant.SUMMARY_PAGE_SIZE);
			searchCondition.setEndRowIndex(FavoriteConstant.SUMMARY_PAGE_SIZE);

			RecentSearchCondition searchCondition2 = new RecentSearchCondition();
			searchCondition2.setFieldName("jobTitleName");
			searchCondition2.setUserLocaleCode(user.getLocaleCode());
			searchCondition2.setRegisterId(user.getUserId());
			searchCondition2.setPagePerRecord(FavoriteConstant.SUMMARY_PAGE_SIZE);
			searchCondition2.setEndRowIndex(FavoriteConstant.SUMMARY_PAGE_SIZE);

			SearchResult<Recent> searchResultForPeople = recentService.getSummaryForPeople(searchCondition2);

			Map<String, String> map = new HashMap<String, String>();
			map.put("userId", user.getUserId());

			
			mav.addObject("searchResultForPeople", searchResultForPeople);
			mav.addObject("searchCondition", searchResultForPeople.getSearchCondition());
			
			if(CommonConstant.PACKAGE_VERSION.equals(CommonConstant.IKEP_VERSION_FULL)) {
				SearchResult<Recent> searchResultForDocument = recentService.getSummaryForDocument(searchCondition);
				mav.addObject("searchResultForDocument", searchResultForDocument);
				
				List<Recent> cafeList = recentService.selectCafe(map);
				mav.addObject("cafeList", cafeList);
				
				List<Recent> collaborationList = recentService.selectCollaboration(map);
				List<Recent> mblogList = recentService.selectMicroblog(map);
				mav.addObject("collaborationList", collaborationList);
				mav.addObject("mblogList", mblogList);
			} else if(CommonConstant.PACKAGE_VERSION.equals(CommonConstant.IKEP_VERSION_LIGHT)) {
				SearchResult<Recent> searchResultForDocument = recentService.getSummaryForDocument(searchCondition);
				mav.addObject("searchResultForDocument", searchResultForDocument);
				
				List<Recent> cafeList = recentService.selectCafe(map);
				mav.addObject("cafeList", cafeList);
			}
		} catch (Exception ex) {
			// ex.printStackTrace();
			throw new IKEP4AjaxException("", ex);
		}
		return mav;
	}

}
