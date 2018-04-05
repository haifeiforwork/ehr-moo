/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.wfapproval.work.web;

import java.util.Date;

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
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.support.timezone.service.TimeZoneSupportService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.DateUtil;
import com.lgcns.ikep4.wfapproval.common.model.CommonCode;
import com.lgcns.ikep4.workflow.engine.model.DelegateBean;
import com.lgcns.ikep4.workflow.engine.service.InstanceService;
import com.lgcns.ikep4.workflow.workplace.model.WorkplaceCode;
import com.lgcns.ikep4.workflow.workplace.service.PersonalSetService;


/**
 * 위임자 설정
 * 
 * @author 박희진(neoheejin@naver.com)
 * @version $Id: ApDelegateController.java 16234 2011-08-18 02:44:36Z giljae $
 */
@Controller
@RequestMapping(value = "/wfapproval/work/delegate")
@SessionAttributes("apDelegate")
public class ApDelegateController extends BaseController {

	@Autowired
	private PersonalSetService personalSetService;
	
	@Autowired
	private IdgenService idgenService;
	
	@Autowired
	private InstanceService instanceService;

	@Autowired
    private TimeZoneSupportService timeZoneSupportService;
	
	/**
	 * Workplace 개인설정 위임 설정
	 * @return
	 */
	@RequestMapping(value = "/updateDelegateForm")
	public ModelAndView workPlacePersonalDelegateSetDetail(DelegateBean delegateBeanParam, @RequestParam(value="saveResult", required=false) String saveResult){
		
		ModelAndView mav = new ModelAndView("/wfapproval/work/delegate/updateDelegateForm");
		
		if( delegateBeanParam == null ){ delegateBeanParam = new DelegateBean();}
		
		//세션의 user id를 가져와서 bean에 셋팅한다.
		User user = (User)getRequestAttribute(CommonCode.IKEP_USER);
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
		
		ModelAndView mav = new ModelAndView("redirect:/wfapproval/work/delegate/updateDelegateForm.do");
		
		//세션의 user id를 가져와서 bean에 셋팅한다.
		User user = (User)getRequestAttribute(CommonCode.IKEP_USER);
		delegateBean.setUserId(user.getUserId());
		
		String startYear 	= delegateBean.getStartDateStr().substring(0, 4);
		String startMonth 	= delegateBean.getStartDateStr().substring(5, 7);
		String startDay 	= delegateBean.getStartDateStr().substring(8, 10);
		
		if(startMonth.startsWith("0")) {
			startMonth		= startMonth.substring(1, 2);
		}
		if(startDay.startsWith("0")) {
			startDay		= startDay.substring(1, 2);
		}
		
		Date startDate = new Date();
		startDate.setYear(Integer.parseInt(startYear)-1900);
		startDate.setMonth(Integer.parseInt(startMonth)-1);
		startDate.setDate(Integer.parseInt(startDay));
		
		String endYear 		= delegateBean.getEndDateStr().substring(0, 4);
		String endMonth 	= delegateBean.getEndDateStr().substring(5, 7);
		String endDay 		= delegateBean.getEndDateStr().substring(8, 10);
		
		if(endMonth.startsWith("0")) {
			endMonth		= endMonth.substring(1, 2);
		}
		if(endDay.startsWith("0")) {
			endDay			= endDay.substring(1, 2);
		}		
		
		Date endDate = new Date();
		endDate.setYear(Integer.parseInt(endYear)-1900);
		endDate.setMonth(Integer.parseInt(endMonth)-1);
		endDate.setDate(Integer.parseInt(endDay));
		
		
		delegateBean.setStartDate(startDate);
		delegateBean.setEndDate(endDate);
		
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
	 * ApDoc 결재단위 위임 설정
	 * @return
	 */
	@RequestMapping(value = "/updateUnitDelegateForm", method = RequestMethod.GET)
	public ModelAndView updateUnitDelegateForm(Model model){
		
		DelegateBean delegateBean = new DelegateBean();

		//사용자 정보
		User user = (User)getRequestAttribute(CommonCode.IKEP_USER);
		delegateBean.setUserId(user.getUserId());
		
		ModelAndView mav = new ModelAndView("/wfapproval/work/delegate/updateUnitDelegateForm");
		
		mav.addObject("delegateBean", delegateBean);
		
		return mav;
	}
	
	/**
	 *  ApDoc 결재단위 위임 설정 저장
	 * @return
	 */
	@RequestMapping(value = "/ajaxApplyUnitDelegate.do", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody DelegateBean ajaxApplyUnitDelegate(@Valid DelegateBean delegateBean, BindingResult result, SessionStatus status){
		
		try {
			if (result.hasErrors()) {
				throw new IKEP4AjaxException("", new Exception("Delegate Info Valid-Error"));
			}

			//사용자 정보
			User user = (User)getRequestAttribute(CommonCode.IKEP_USER);
			delegateBean.setUserId(user.getUserId());
			
			/**
			 * TODO Javadoc주석작성
			 * @param instanceLogId : 인스턴스로그ID
			 * @param userId	: 위임하는 사람
			 * @param delegatorId	: 위임을 받는 사람
			 * @param message	: 위임 메시지
			 * @return		: true/false
			 */
			if(instanceService.setDelegate(delegateBean.getInstanceLogId(), delegateBean.getUserId(), delegateBean.getMandatorId(), delegateBean.getReasonComment()))
			{
				// 세션 상태를 complete
				status.setComplete();
			}else{
				throw new IKEP4AjaxException("", new Exception("Delegate Set-Error"));
			}

		} catch (Exception ex) {
			throw new IKEP4AjaxException("ajaxApplyUnitDelegate", ex);
		}
		
		//return new ModelAndView("redirect:/wfapproval/work/aplist/listApTodo.do");
		return delegateBean;
	}
	
	
}
