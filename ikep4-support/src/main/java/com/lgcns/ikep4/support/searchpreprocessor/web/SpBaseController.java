/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.searchpreprocessor.web;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lgcns.ikep4.framework.core.code.Code;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.support.searchpreprocessor.model.SpBaseControllerData;
import com.lgcns.ikep4.support.searchpreprocessor.util.PageCons;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 
 * 검색프로세스 base controller
 *
 * @author 고인호(ihko11@daum.net)
 * @version $Id: SpBaseController.java 16316 2011-08-22 00:26:53Z giljae $
 */
@Controller
@RequestMapping(value = "/support/searchpreprocessor")
public class SpBaseController extends BaseController {
	

	
	/**
	 * 권한 관리
	 */
	@Autowired
    ACLService aclService;

	/**
	 * 어드민 유저 인지
	 * @return
	 */
	@ModelAttribute("isAdmin")
	public boolean isAdmin(){
		User user = (User) getRequestAttribute("ikep.user");
		
        String userId = user.getUserId();
        boolean isSystemAdmin = aclService.isSystemAdmin(SpBaseControllerData.ITEM_TYPE_CODE, userId);
        
        return isSystemAdmin;
	}
	
	/**
	 * 사용자정보
	 * @return
	 */
	@ModelAttribute("user")
	public User getUser(){
		return  (User) getRequestAttribute("ikep.user");
	}
	
	/**
	 * 리스트 페이지 코드
	 * @return
	 */
	@ModelAttribute("codeList")
	public List<Code<Integer>> getCodeList(){
		@SuppressWarnings("unchecked")
		List<Code<Integer>> pageNumList = Arrays.asList(
				new Code<Integer>(PageCons.PAGE_PER_ROW_10, "10"),
				new Code<Integer>(PageCons.PAGE_PER_ROW_15, "15"),
				new Code<Integer>(PageCons.PAGE_PER_ROW_20, "20"),
				new Code<Integer>(PageCons.PAGE_PER_ROW_30, "30"),
				new Code<Integer>(PageCons.PAGE_PER_ROW_40, "40"),
				new Code<Integer>(PageCons.PAGE_PER_ROW_50, "50")
		);
		
		return pageNumList;
	}
	
}
