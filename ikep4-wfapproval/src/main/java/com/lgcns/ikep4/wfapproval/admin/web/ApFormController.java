/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.wfapproval.admin.web;

import java.util.List;

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
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.wfapproval.admin.model.ApCode;
import com.lgcns.ikep4.wfapproval.admin.model.ApForm;
import com.lgcns.ikep4.wfapproval.admin.model.ApFormTpl;
import com.lgcns.ikep4.wfapproval.admin.search.ApFormSearchCondition;
import com.lgcns.ikep4.wfapproval.admin.service.ApCodeService;
import com.lgcns.ikep4.wfapproval.admin.service.ApFormService;
import com.lgcns.ikep4.wfapproval.common.model.CommonCode;
import com.lgcns.ikep4.workflow.engine.model.ProcessBean;
import com.lgcns.ikep4.workflow.workplace.model.WorkplaceCode;
import com.lgcns.ikep4.workflow.workplace.search.WorkplaceSearchCondition;
import com.lgcns.ikep4.workflow.workplace.service.WorkplaceListService;


/**
 * 결재 양식관리 <br/>
 * 다음 사항 포함
 * 
 * <pre>
 * <li>양식관리 목록 조회</li>
 * <li>양식정보 조회</li>
 * <li>양식등록</li>
 * </pre>
 * 
 * @author 박희진(neoheejin@naver.com)
 * @version $Id: ApFormController.java 16234 2011-08-18 02:44:36Z giljae $
 */
@Controller
@RequestMapping(value = "/wfapproval/admin/apform")
@SessionAttributes("apform")
public class ApFormController extends BaseController {

	@Autowired
	private ApFormService apFormService;

	@Autowired
	private ApCodeService apCodeService;

	@Autowired
	private WorkplaceListService workplaceListService;

	/**
	 * 양식목록 정보
	 * 
	 * @param apFormSearchCondition
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/listApForm")
	public ModelAndView listApForm(ApFormSearchCondition apFormSearchCondition, BindingResult result,
			SessionStatus status) {
		/*
		ApFormSearchCondition searchCondition = (ApFormSearchCondition)getRequestAttribute("searchApForm");
		
		if(searchCondition != null){
			apFormSearchCondition = searchCondition;
		}*/
		
		SearchResult<ApForm> searchResult = this.apFormService.listApFormAll(apFormSearchCondition);

		User user = (User) getRequestAttribute(CommonCode.IKEP_USER);

		String localeCode = user.getLocaleCode();

		// 양식유형 목록
		List<ApCode> listFormClassCd = this.apCodeService.listGroupApCodeByValue(CommonCode.FORM_CLASS_CD, localeCode);
		// 양식구분 목록
		List<ApCode> listFormTypeCd = this.apCodeService.listGroupApCodeByValue(CommonCode.FORM_TYPE_CD, localeCode);

		ModelAndView mav = new ModelAndView("/wfapproval/admin/apform/readApFormList");
		
		/*setRequestAttribute("searchApForm", apFormSearchCondition);*/

		mav.addObject("apFormList", searchResult);
		mav.addObject("searchCondition", searchResult.getSearchCondition());
		mav.addObject("commonCode", new CommonCode());
		mav.addObject("listFormClassCd", listFormClassCd);
		mav.addObject("listFormTypeCd", listFormTypeCd);

		return mav;
	}

	/**
	 * 양식 등록화면 정보
	 * 
	 * @param formId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/createApFormForm", method = RequestMethod.GET)
	public ModelAndView createApFormForm(Model model) {

		User user = (User) getRequestAttribute(CommonCode.IKEP_USER);

		ApForm apForm = new ApForm();

		apForm.setApFormTpl(new ApFormTpl());

		ModelAndView mav = new ModelAndView("/wfapproval/admin/apform/createApFormForm");
		
		// Master
		mav.addObject("apForm", apForm);
		// Template
		mav.addObject("apFormTpl", apForm.getApFormTpl());

		// 공통코드 목록정보 설정하기
		this.setApFormModelByApCodeList(mav);

		return mav;
	}

	/**
	 * 양식 수정화면 정보 
	 * 
	 * @param formId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/updateApFormForm", method = RequestMethod.GET)
	public ModelAndView updateApFormForm(
			@RequestParam(value = "formId", required = false) String formId, 
			@RequestParam(value = "tabSelect", required = false) String tabSelect,
			Model model) {

		ApForm apForm = null;

		if (StringUtil.isEmpty(formId)) {
			apForm = new ApForm();

			if (log.isDebugEnabled()) {
				log.debug("If formId is NULL, update Error");
			}
		} else {
			apForm = this.apFormService.readApForm(formId);
		}
		
		//--------------------------------------------------------------------
		//	결재선 정보
		//--------------------------------------------------------------------
		ProcessBean procBean = new ProcessBean();
		
		if (apForm.getApFormTpl().getProcessId() != null) {

			procBean.setProcessId(apForm.getApFormTpl().getProcessId());

			procBean = this.workplaceListService.readProcInfo(procBean);
			
			if(procBean != null){
				//프로세스명
				apForm.getApFormTpl().setProcessName(procBean.getProcessName());
				
				//프로세스 버젼
				apForm.getApFormTpl().setProcessVersion(procBean.getProcessVer());
			}
		}

		ModelAndView mav = new ModelAndView("/wfapproval/admin/apform/updateApFormForm");

		// Master
		mav.addObject("apForm", apForm);
		// Template
		mav.addObject("apFormTpl", apForm.getApFormTpl());
		// Process Info
		mav.addObject("procBean", procBean);
		mav.addObject("tabSelect", tabSelect);

		// 공통코드 목록정보 설정하기
		this.setApFormModelByApCodeList(mav);

		return mav;
	}

	/**
	 * 양식 마스터 저장처리
	 * 
	 * @param apform
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/applyApForm", method = RequestMethod.POST)
	public ModelAndView applyApForm(@Valid ApForm apform, BindingResult result, SessionStatus status) {

		if (result.hasErrors()) {
			return new ModelAndView("/wfapproval/admin/apform/updateApFormForm");
		}
		
		ModelAndView mav = new ModelAndView("redirect:updateApFormForm.do");

		// 사용자 세션정보
		User user = (User) getRequestAttribute(CommonCode.IKEP_USER);

		String formId = apform.getFormId();

		if (StringUtil.isEmpty(formId)) {
			apform.setRegistUserId(user.getUserId());
			apform.setModifyUserId(user.getUserId());
			formId = this.apFormService.createApForm(apform);
			
			//양식 마스터 등록 후 템플릿 화면으로 전환한다.
			mav.addObject("tabSelect", 1);
		} else {
			apform.setModifyUserId(user.getUserId());
			this.apFormService.updateApForm(apform);
			
			//양식수정시 기본탭 설정.
			mav.addObject("tabSelect", 0);
		}

		// 세션 상태를 complete하여 이중 전송 방지
		status.setComplete();
		
		return mav.addObject("formId", formId);
	}

	/**
	 * 양식 템플릿 저장처리
	 * 
	 * @param apformtpl
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/applyApFormTpl", method = RequestMethod.POST)
	public ModelAndView applyApFormTpl(@Valid ApFormTpl apformtpl, BindingResult result, SessionStatus status) {
		if (result.hasErrors()) {
			return new ModelAndView("/wfapproval/admin/apform/updateApFormForm");
		}

		// 사용자 세션정보
		User user = (User) getRequestAttribute(CommonCode.IKEP_USER);

		String formId = apformtpl.getFormId();

		// 등록된 양식템플릿이 있다면...
		if (this.apFormService.existsApFormTpl(formId)) {
			apformtpl.setModifyUserId(user.getUserId());
			this.apFormService.updateApFormTpl(apformtpl, user);
		} else {
			apformtpl.setRegistUserId(user.getUserId());
			apformtpl.setModifyUserId(user.getUserId());
			formId = this.apFormService.createApFormTpl(apformtpl, user);
		}

		// 세션 상태를 complete하여 이중 전송 방지
		status.setComplete();

		return new ModelAndView("redirect:updateApFormForm.do").addObject("formId", formId).addObject("tabSelect", 1);
	}

	/**
	 * 양식 미리보기화면 정보
	 * 
	 * @param formId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/readApFormPreview", method = RequestMethod.GET)
	public ModelAndView readApFormPreview(@RequestParam(value = "formId", required = false) String formId, Model model) {

		ApForm apForm = null;

		if (StringUtil.isEmpty(formId)) {
			apForm = new ApForm();
		} else {
			apForm = this.apFormService.readApForm(formId);
		}

		ModelAndView mav = new ModelAndView("/wfapproval/admin/apform/readApFormPreview");

		// Master
		mav.addObject("apForm", apForm);
		// Template
		mav.addObject("apFormTpl", apForm.getApFormTpl());

		return mav;
	}

	/**
	 * 양식정보 가져오기
	 * 
	 * @param formId
	 * @return
	 */
	@RequestMapping(value = "/readApForm", method = RequestMethod.GET)
	public ModelAndView readApForm(@RequestParam("formId") String formId) {
		ModelAndView mav = new ModelAndView("/wfapproval/admin/apform/readApFormView");
		if (!StringUtil.isEmpty(formId)) {
			ApForm apForm = apFormService.readApForm(formId);

			// Master
			mav.addObject("apForm", apForm);
			// Template
			mav.addObject("apFormTpl", apForm.getApFormTpl());
		}
		return mav;
	}

	/**
	 * 양식삭제
	 * 
	 * @param apFormIds
	 * @param status
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/ajaxDeleteApForm", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	String ajaxDeleteApCode(@RequestParam("apFormIds") List<String> apFormIds, SessionStatus status, Model model) {

		try {
			String deleteFormId;

			if (!apFormIds.isEmpty()) {
				for (int i = 0; i < apFormIds.size(); i++) {

					deleteFormId = apFormIds.get(i);

					this.apFormService.deleteApForm(deleteFormId);
				}
			}

			// 세션 상태를 complete
			status.setComplete();

		} catch (Exception ex) {
			throw new IKEP4AjaxException("ajaxDeleteApForm", ex);
		}

		return "success";
	}

	/**
	 * 양식 결재선 선택 메인화면
	 * 
	 * @param formId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/searchApFormProcess", method = RequestMethod.GET)
	public ModelAndView readApFormPreview(Model model) {
		return new ModelAndView("/wfapproval/admin/apform/searchApFormProcess");
	}
	
	/**
	 * 양식 결재선 선택화면 - 프로세스 목록
	 */
	@RequestMapping(value = "/ajaxListApFormProcess")
	public ModelAndView workPlaceStartWorkList(WorkplaceSearchCondition workplaceSearchCondition, BindingResult result,
			SessionStatus status) {

		ModelAndView mav = new ModelAndView("/wfapproval/admin/apform/listApFormProcess");

		workplaceSearchCondition.setQueryId("startProcList");

		SearchResult<ProcessBean> searchResult = this.workplaceListService
				.workplaceProcessList(workplaceSearchCondition);

		mav.addObject("searchResult", searchResult);
		mav.addObject("workplaceSearchCondition", searchResult.getSearchCondition());
		mav.addObject("workplaceCode", new WorkplaceCode());

		return mav;
	}

	/**
	 * 문서내 공통코드 목록 설정하기
	 * 
	 * @param model
	 */
	private void setApFormModelByApCodeList(ModelAndView mav) {

		User user = (User) getRequestAttribute(CommonCode.IKEP_USER);

		String localeCode = user.getLocaleCode();

		// 양식유형 목록
		List<ApCode> listFormClassCd = this.apCodeService.listGroupApCodeByValue(CommonCode.FORM_CLASS_CD, localeCode);
		// 양식구분 목록
		List<ApCode> listFormTypeCd = this.apCodeService.listGroupApCodeByValue(CommonCode.FORM_TYPE_CD, localeCode);
		// 결재요청시 통보대상
		List<ApCode> listMailReqCd = this.apCodeService.listGroupApCodeByValue(CommonCode.MAIL_REQ_CD, localeCode);
		// 결재완료시 통보대상
		List<ApCode> listMailEndCd = this.apCodeService.listGroupApCodeByValue(CommonCode.MAIL_END_CD, localeCode);
		// 결재요청시 통보방법
		List<ApCode> listMailReqWayCd = this.apCodeService.listGroupApCodeByValue(CommonCode.MAIL_REQ_WAY_CD, localeCode);
		// 결재완료시 통보방법
		List<ApCode> listMailEndWayCd = this.apCodeService.listGroupApCodeByValue(CommonCode.MAIL_END_WAY_CD, localeCode);
		// 합의
		List<ApCode> listDiscussCd = this.apCodeService.listGroupApCodeByValue(CommonCode.DISCUSS_CD, localeCode);
		// 보존연한
		List<ApCode> listApprPeriodCd = this.apCodeService
				.listGroupApCodeByValue(CommonCode.APPR_PERIOD_CD, localeCode);
		// 결재구분
		List<ApCode> listApprTypeCd = this.apCodeService.listGroupApCodeByValue(CommonCode.APPR_TYPE_CD, localeCode);
		// 결재문서종류
		List<ApCode> listApprDocCd = this.apCodeService.listGroupApCodeByValue(CommonCode.APPR_DOC_CD, localeCode);

		mav.addObject("listFormClassCd", listFormClassCd);
		mav.addObject("listFormTypeCd", listFormTypeCd);
		mav.addObject("listMailReqCd", listMailReqCd);
		mav.addObject("listMailEndCd", listMailEndCd);
		mav.addObject("listMailReqWayCd", listMailReqWayCd);
		mav.addObject("listMailEndWayCd", listMailEndWayCd);
		mav.addObject("listDiscussCd", listDiscussCd);
		mav.addObject("listApprPeriodCd", listApprPeriodCd);
		mav.addObject("listApprTypeCd", listApprTypeCd);
		mav.addObject("listApprDocCd", listApprDocCd);

		mav.addObject("commonCode", new CommonCode());
	}
}
