package com.lgcns.ikep4.support.personal.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchCondition;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.favorite.util.FavoriteConstant;
import com.lgcns.ikep4.support.personal.model.Personal;
import com.lgcns.ikep4.support.personal.model.PersonalSearchCondition;
import com.lgcns.ikep4.support.personal.service.PersonalService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.lang.StringUtil;


/**
 * Personal 처리 Controller
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: PersonalController.java 17659 2012-03-22 05:03:57Z yu_hs $
 */
@Controller
@RequestMapping(value = "/support/personal")
public class PersonalController extends BaseController {
	
	/**
	 * Personal 서비스
	 */
	@Autowired
	private PersonalService personalService;
	
	/**
	 * 리스트 화면 공통
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getListForm.do")
	public ModelAndView getListForDocument(String viewMode, String userId, String userLocale) {

		ModelAndView mav = new ModelAndView("support/personal/listForm");

		PersonalSearchCondition searchCondition = new PersonalSearchCondition();

		if (!StringUtil.isEmpty(userId)) {
			searchCondition.setUserLocaleCode(userLocale);
			searchCondition.setRegisterId(userId);
		} else {
			User user = (User) getRequestAttribute("ikep.user");
			searchCondition.setUserLocaleCode(user.getLocaleCode());
			searchCondition.setRegisterId(user.getUserId());
		}

		mav.addObject("searchCondition", searchCondition);
		mav.addObject("viewMode", StringUtil.isEmpty(viewMode) ? "document" : viewMode);

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
	public ModelAndView getSubListForDocument(PersonalSearchCondition searchCondition) {
		ModelAndView mav = new ModelAndView("/support/personal/subListForDocument");

		try {
			searchCondition.setPagePerRecord(FavoriteConstant.LIST_PAGE_SIZE);
	
			//검색할 게시물의 유형이 선택되지 않는 경우 검색하지 않는다
			if(searchCondition.getModuleCodeList() != null)
			{
				SearchResult<Personal> searchResult = personalService.getAllForDocument(searchCondition);
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
			throw new IKEP4AjaxException("", ex);
		}
		
		return mav;
	}

	/**
	 * 파일 리스트 검색
	 * 
	 * @param searchCondition
	 * @param channelId
	 * @return
	 */
	@RequestMapping(value = "/getSubListForFile.do")
	public ModelAndView getSubListForFile(PersonalSearchCondition searchCondition) {
		ModelAndView mav = null;
		if (searchCondition.getFileType().equals("image")) {
			mav = new ModelAndView("/support/personal/subListForImage");
		} else if (searchCondition.getFileType().equals("video")) {
			mav = new ModelAndView("/support/personal/subListForVideo");
		} else {
			mav = new ModelAndView("/support/personal/subListForFile");
		}

		try {
			searchCondition.setPagePerRecord(FavoriteConstant.LIST_PAGE_SIZE);
			
			//검색할 게시물의 유형이 선택되지 않는 경우 검색하지 않는다
			if(searchCondition.getModuleCodeList() != null)
			{
				SearchResult<Personal> searchResult = personalService.getAllForFile(searchCondition);
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
			throw new IKEP4AjaxException("", ex);
		}
		
		return mav;
	}

	/**
	 * 코멘트 리스트 검색
	 * 
	 * @param searchCondition
	 * @param channelId
	 * @return
	 */
	@RequestMapping(value = "/getSubListForComment.do")
	public ModelAndView getSubListForComment(PersonalSearchCondition searchCondition) {
		ModelAndView mav = null;
		if (searchCondition.getCommentType().equals("comment")) {
			mav = new ModelAndView("/support/personal/subListForComment");
		} else {
			mav = new ModelAndView("/support/personal/subListForFeedback");
		}

		try {
			searchCondition.setPagePerRecord(FavoriteConstant.LIST_PAGE_SIZE);

			SearchResult<Personal> searchResult = personalService.getAllForComment(searchCondition);

			mav.addObject("searchResult", searchResult);
			mav.addObject("searchCondition", searchResult.getSearchCondition());

		} catch (Exception ex) {
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
	public ModelAndView getShortcut(PersonalSearchCondition searchCondition) {

		ModelAndView mav = new ModelAndView("support/personal/shortcut");
		
		return mav;
	}

}
