/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.addressbook.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.addressbook.model.Addrgroup;
import com.lgcns.ikep4.support.addressbook.search.AddrgroupSearchCondition;
import com.lgcns.ikep4.support.addressbook.service.AddrgroupService;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 주소록 메뉴 Controller
 *
 * @author 이형운 (dewey94@wooin.info)
 * @version $Id: AddrbookMenuController.java 16271 2011-08-18 07:06:14Z giljae $
 */
@Controller
@RequestMapping(value = "/support/addressbook")
@SessionAttributes("adressbook")
public class AddrbookMenuController extends BaseController {
	
	/**
	 * 주소록 그룹 컨트롤용 Service
	 */
	@Autowired
	private AddrgroupService addrgroupService;
	
	/**
	 * 해당 모듈 관리 권한 컨트롤용 Service
	 */
	@Autowired
	private ACLService aclService;

	
	/**
	 * 주소록 공통 메뉴 부분 조회용 
	 * @return 공통 메뉴 view 페이지
	 */
	@RequestMapping(value = "/readAddrbookMenu.do")
	public ModelAndView getView() {
		
		User user = (User) getRequestAttribute("ikep.user");
		
		ModelAndView mav = new ModelAndView("/support/addressbook/addressbookmenulist");
		
		mav.addObject("userName", user.getUserName() );
		mav.addObject("userEnglishName", user.getUserEnglishName() );
		
        boolean isSystemAdmin = aclService.isSystemAdmin("Addressbook", user.getUserId());

		mav.addObject("publicManageFlag", String.valueOf(isSystemAdmin));

		return mav;
	}
	
	/**
	 * 주소록 그룹 타입별 목록 조회
	 * @param addrgroupId 선택된 주소록 Id
	 * @param groupType 'P' 개인그룹 'O' 공용그룹
	 * @return 주소록 타입별 목록 View
	 */
	@RequestMapping(value = "/readAddrgroupListByGroupType.do")
	public ModelAndView readAddrgroupListByGroupType( @RequestParam(value = "addrgroupId", required = false) String addrgroupId
													, @RequestParam("groupType") String groupType) {
		
		ModelAndView mav = new ModelAndView("/support/addressbook/addressbookmenugroup");

		User user = (User) getRequestAttribute("ikep.user");
		
		AddrgroupSearchCondition addrgroupSearch = new AddrgroupSearchCondition();
		addrgroupSearch.setRegisterId(user.getUserId());
		addrgroupSearch.setPortalId(user.getPortalId());
		addrgroupSearch.setGroupType(groupType);
		addrgroupService.list(addrgroupSearch);
		
		SearchResult<Addrgroup> addrgroupList = addrgroupService.list(addrgroupSearch);

		mav.addObject("addrgroupId", addrgroupId);
		mav.addObject("addrgroupList", addrgroupList);
		mav.addObject("groupType", groupType);

		mav.addObject("userName", user.getUserName() );
		mav.addObject("userEnglishName", user.getUserEnglishName() );
		
		return mav;
	}
	
}
