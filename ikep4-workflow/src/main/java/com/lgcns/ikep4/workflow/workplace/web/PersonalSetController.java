/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.workplace.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.support.timezone.service.TimeZoneSupportService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.workflow.engine.model.DelegateBean;
import com.lgcns.ikep4.workflow.workplace.model.WorkplaceCode;
import com.lgcns.ikep4.workflow.workplace.search.WorkplaceSearchCondition;
import com.lgcns.ikep4.workflow.workplace.service.PersonalSetService;

/**
 * WorkPlace 컨트롤러
 * 
 * @author 이재경
 * @version $Id: PersonalSetController.java 16245 2011-08-18 04:28:59Z giljae $
 */
@Controller
@RequestMapping(value="/workflow/workplace/personal")
public class PersonalSetController extends BaseController {

	@Autowired
	private PersonalSetService personalSetService;
	
	@Autowired
	private IdgenService idgenService;
	
	@Autowired
    private TimeZoneSupportService timeZoneSupportService;
	
	/**
	 * Workplace 개인설정 위임 설정
	 * @return
	 */
	@RequestMapping(value = "/delegateSetDetail.do")
	public ModelAndView workPlacePersonalDelegateSetDetail(DelegateBean delegateBeanParam, @RequestParam(value="saveResult", required=false) String saveResult){
		
		ModelAndView mav = new ModelAndView("/workflow/workplace/personal/delegateSet");
		
		if( delegateBeanParam == null ){ delegateBeanParam = new DelegateBean();}
		
		//세션의 user id를 가져와서 bean에 셋팅한다.
		User user = (User)getRequestAttribute("ikep.user");
		delegateBeanParam.setUserId(user.getUserId());
		
		DelegateBean delegateBean = personalSetService.delegateDetail(delegateBeanParam);
		
		//최초에 유저에 셋팅된 값이 없으면 null로 넘어온다.
		if( delegateBean == null ){ delegateBean = new DelegateBean();}
		
		mav.addObject("delegateBean", delegateBean);
		mav.addObject("workplaceCode", new WorkplaceCode());
		mav.addObject("saveResult", saveResult == null ? "ui.workflow.workplace.common.saveresult.none" : saveResult);
		
		return mav;
	}
	
	/**
	 * Workplace 개인설정 위임설정 저장
	 * @return
	 */
	@RequestMapping(value = "/delegateSetSave.do")
	public ModelAndView workPlacePersonalDelegateSetSave(DelegateBean delegateBean){
		
		ModelAndView mav = new ModelAndView("redirect:/workflow/workplace/personal/delegateSetDetail.do");
		
		//세션의 user id를 가져와서 bean에 셋팅한다.
		User user = (User)getRequestAttribute("ikep.user");
		delegateBean.setUserId(user.getUserId());

		//날짜 문자열을 데이트 형태로 변환하여 저장
		delegateBean.setStartDate(timeZoneSupportService.convertServerTimeZone(delegateBean.getStartDate()));
		delegateBean.setEndDate(timeZoneSupportService.convertServerTimeZone(delegateBean.getEndDate()));
		
		//시퀀스 id가 없으면 생성, 있으면 수정 서비스를 호출한다.
		String seqId = delegateBean.getSeqId();
		int result = 0;
		if( "".equals(seqId) || seqId == null ){	
			delegateBean.setSeqId(idgenService.getNextId());
			result = personalSetService.delegateCreate(delegateBean);
		}
		else{										
			result = personalSetService.delegateUpdate(delegateBean);
		}
		
		String saveResult = "";
		//저장 서비스 결과 값을 추가한다.
		if( result > 0 ){ 	saveResult = "ui.workflow.workplace.common.saveresult.success";}
		else{				saveResult = "ui.workflow.workplace.common.saveresult.fail";}
		
		return mav.addObject("saveResult", saveResult);
	}
	
	/**
	 * Workplace 개인설정 개인 휴무일 설정
	 * @return
	 */
	@RequestMapping(value = "/holidaySet.do")
	public ModelAndView workPlacePersonalHolidaySet(WorkplaceSearchCondition workplaceSearchCondition){
		
		ModelAndView mav = new ModelAndView("/workflow/workplace/personal/holidaySet");
		
		mav.addObject("workplaceSearchCondition", workplaceSearchCondition);
		return mav;
	}
}