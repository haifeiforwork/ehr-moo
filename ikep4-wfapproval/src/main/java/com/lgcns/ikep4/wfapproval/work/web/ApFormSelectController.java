/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.wfapproval.work.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.wfapproval.admin.model.ApCode;
import com.lgcns.ikep4.wfapproval.admin.model.ApForm;
import com.lgcns.ikep4.wfapproval.admin.search.ApFormSearchCondition;
import com.lgcns.ikep4.wfapproval.admin.service.ApCodeService;
import com.lgcns.ikep4.wfapproval.common.model.CommonCode;
import com.lgcns.ikep4.wfapproval.work.service.ApFormSelectService;


/**
 * 결재 양식 선택 <br/>
 * 다음 사항 포함
 * 
 * <pre>
 * <li>양식 목록 조회</li>
 * <li>양식 선택</li>
 * </pre>
 * 
 * @author 박희진(neoheejin@naver.com)
 * @version $Id: ApFormSelectController.java 16234 2011-08-18 02:44:36Z giljae $
 */
@Controller
@RequestMapping(value = "/wfapproval/work/apform")
@SessionAttributes("apform")
public class ApFormSelectController extends BaseController {

	@Autowired
	private ApFormSelectService apFormSelectService;

	@Autowired
	private ApCodeService apCodeService;

	@RequestMapping(value = "/listApFormSelect.do")
	public ModelAndView listApFormSelect(@RequestParam(value = "linkType", required = false) String linkType,
			ApFormSearchCondition apFormSearchCondition, BindingResult result, SessionStatus status) {

		SearchResult<ApForm> searchResult = this.apFormSelectService.listApFormSelect(apFormSearchCondition);
		
		User user = (User) getRequestAttribute(CommonCode.IKEP_USER);
		
		String localeCode = user.getLocaleCode();
		
		//양식유형 목록
		List<ApCode> listFormClassCd = this.apCodeService.listGroupApCodeByValue(CommonCode.FORM_CLASS_CD, localeCode);
		//양식구분 목록
		List<ApCode> listFormTypeCd = this.apCodeService.listGroupApCodeByValue(CommonCode.FORM_TYPE_CD, localeCode);
		
		ModelAndView mav = new ModelAndView("/wfapproval/work/apform/readApFormListSelect");
		
		if(linkType != null && linkType.equals("mywork")){
			mav.setViewName("/wfapproval/work/apform/readMyWorkApFormListSelect");
		}
		
		mav.addObject("apFormListSelect", searchResult);
		mav.addObject("searchCondition", searchResult.getSearchCondition());
		mav.addObject("commonCode", new CommonCode());
		mav.addObject("listFormClassCd", listFormClassCd);
		mav.addObject("listFormTypeCd", listFormTypeCd);
		mav.addObject("linkType", linkType);

		return mav;
	}
}
