/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.servicepack.usagetracker.web;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.servicepack.usagetracker.model.UtMenu;
import com.lgcns.ikep4.servicepack.usagetracker.search.UtSearchCondition;
import com.lgcns.ikep4.servicepack.usagetracker.service.UtMenuService;
import com.lgcns.ikep4.servicepack.usagetracker.util.UsageTrackerConstance;
import com.lgcns.ikep4.support.user.code.model.Nation;
import com.lgcns.ikep4.support.user.code.service.I18nMessageService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


/**
 * 메뉴 설정
 * 
 * @author 고인호(ihko11@daum.net)
 * @version $Id: UtMenuController.java 17353 2012-03-05 01:17:40Z unshj $
 */
@Controller
@RequestMapping(value = "/servicepack/usagetracker/menu")
public class UtMenuController extends BaseUsageTrackerController {

	// 메뉴
	@Autowired
	private UtMenuService utMenuService;

	@Autowired
	private IdgenService idgenService;

	
	/**
	 * 다국어 관리 service
	 */
	@Autowired
	private I18nMessageService i18nMessageService;
	
	/**
	 * 읽기
	 * 
	 * @return
	 */
	@RequestMapping(value = "/open", method = RequestMethod.GET)
	public ModelAndView read(@RequestParam(value = "menuId", required = true) String menuId) {

		ModelAndView mav = new ModelAndView("/servicepack/usagetracker/menu/open");

		try {

			//List<UtMenu> utMenu = utMenuService.readMenuMessageWithLocale(menuId);
			UtMenu utMenu = utMenuService.read(menuId);
			utMenu.setI18nMessageList(i18nMessageService.makeExistLocaleList(IKepConstant.ITEM_TYPE_CODE_TRACKER, menuId, "menuUrlName"));
			
			mav.addObject("utMenu", utMenu);
	
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return mav;
	}

	/**
	 * 생성초기화면
	 * 
	 * @param utMenu
	 * @return
	 */
	@RequestMapping(value = "/createMenu", method = RequestMethod.GET)
	public ModelAndView createMenu(UtMenu utMenu) {

		ModelAndView mav = new ModelAndView("/servicepack/usagetracker/menu/open");
		User user = (User) getRequestAttribute("ikep.user");

		try {
			UtMenu utMenuT = new UtMenu(UsageTrackerConstance.WI_STRING, UsageTrackerConstance.WI_STRING,
					UsageTrackerConstance.WI_STRING, UsageTrackerConstance.WI_STRING,
					UsageTrackerConstance.UT_CONFIG_LOGIN_USAGE_USE, user.getUserId(), user.getUserName(), new Date());
			
			utMenuT.setI18nMessageList(i18nMessageService.makeInitLocaleList("menuUrlName"));
			
			mav.addObject("utMenu", utMenuT);

		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return mav;
	}

	/**
	 * 수정
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/createOrUpdateAjax", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Map createOrUpdateAjax(UtMenu utMenu) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		User user = (User) getRequestAttribute("ikep.user");
		
		String menuUrl = utMenu.getMenuUrl();
		int resultFlag = 0;
		
		try {

			utMenu.setPortalId(user.getPortalId());

			if (StringUtils.hasText(utMenu.getMenuId())) {//수정모드
				if(utMenuService.existsURL(utMenu)){
					//저장할 url이 기존에 저장된 것일때 
					resultFlag = 1;
				}else{
					utMenu.setRegisterId(user.getUserId());
					utMenu.setRegisterName(user.getUserName());
					utMenu.setMenuName(utMenu.getI18nMessageList().get(0).getItemMessage());
					utMenu.setI18nMessageList(utMenu.getI18nMessageList());
					utMenuService.updateAndMessaging(utMenu);	
				}
				
			} else {//저장모드
				if(utMenuService.existsURL(utMenu)){
					//저장할 url이 기존에 저장된 것일때
					resultFlag = 1;
				}else{
					//기존에 저장된 menuURL이 없을 때 
				utMenu.setMenuId(idgenService.getNextId());
				utMenu.setRegistDate(new Date());
				utMenu.setMenuName(utMenu.getI18nMessageList().get(0).getItemMessage());
				utMenu.setI18nMessageList(utMenu.getI18nMessageList());
				utMenu.setRegisterId(user.getUserId());
				utMenu.setRegisterName(user.getUserName());
				
				utMenuService.createAndMessaging(utMenu);
				}
			}
			
			result.put("utMenu", utMenu);
			result.put("resultFlag", resultFlag);
			
		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}

		return result;
	}

	/**
	 * 선택된 메뉴 삭제
	 * 
	 * @param menuIds
	 * @return
	 */
	@RequestMapping(value = "/deleteAll", method = RequestMethod.POST)
	public ModelAndView deleteAll(@RequestParam(value = "menuId", required = true) String[] menuIds) {

		try {
			utMenuService.removeCheckedAll(menuIds);

		} catch (Exception e) {
			log.error(e.getMessage());
		}

		return new ModelAndView("redirect:/servicepack/usagetracker/menu/list.do");

	}

	/**
	 * 팝업 개별 삭제
	 * 
	 * @param menuId
	 * @return
	 */
	@RequestMapping(value = "/deleteAjax", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	String deleteAjax(@RequestParam(value = "menuId", required = true) String menuId) {

		try {

			utMenuService.delete(menuId);

		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}

		return menuId;
	}

	/**
	 * 메뉴 리스트
	 * 
	 * @param searchCondition
	 * @param result
	 * @param status
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/list")
	public ModelAndView getList(UtSearchCondition searchCondition, BindingResult result, SessionStatus status,
			ModelMap model, HttpServletRequest request) {
		User user = (User) getRequestAttribute("ikep.user");

		searchCondition.setPortalId(user.getPortalId());

		SearchResult<UtMenu> searchResult = utMenuService.listBySearchCondition(searchCondition);

		ModelAndView mav = new ModelAndView("/servicepack/usagetracker/menu/list");
		mav.addObject("searchResult", searchResult);
		mav.addObject("searchCondition", searchResult.getSearchCondition());

		return mav;
	}

}
