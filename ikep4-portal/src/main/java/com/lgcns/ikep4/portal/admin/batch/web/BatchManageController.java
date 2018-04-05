/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.admin.batch.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxValidationException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AuthorizedException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.main.model.PortalCode;
import com.lgcns.ikep4.support.quartz.model.BatchCronJobDetail;
import com.lgcns.ikep4.support.quartz.model.BatchSearchCondition;
import com.lgcns.ikep4.support.quartz.model.BatchSimpleJobDetail;
import com.lgcns.ikep4.support.quartz.model.BatchTrigger;
import com.lgcns.ikep4.support.quartz.model.QuartzLog;
import com.lgcns.ikep4.support.quartz.service.BatchManageService;
import com.lgcns.ikep4.support.quartz.service.QuartzJobService;
import com.lgcns.ikep4.support.security.acl.annotations.AccessType;
import com.lgcns.ikep4.support.security.acl.annotations.Attribute;
import com.lgcns.ikep4.support.security.acl.annotations.IsAccess;
import com.lgcns.ikep4.support.security.acl.validation.AccessingResult;


/**
 * 배치 관리 컨트롤러
 * 
 * @author 주길재
 * @version $Id: BatchManageController.java 16489 2011-09-06 01:41:09Z giljae $
 */
@Controller
@RequestMapping(value = "/portal/admin/batch")
public class BatchManageController extends BaseController {
	@Autowired
	private BatchManageService batchManageService;

	@Autowired
	private QuartzJobService quartzJobService;

	/**
	 * Trigger 목록을 가져온다.
	 * 
	 * @param searchCondition
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/listTrigger.do")
	public String listTrigger(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) BatchSearchCondition searchCondition,
			AccessingResult accessResult, Model model) {
		// 권한 체크
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		PortalCode portalCode = new PortalCode();

		SearchResult<BatchTrigger> searchResult = this.batchManageService.listTrigger(searchCondition);

		model.addAttribute("searchResult", searchResult);
		model.addAttribute("searchCondition", searchResult.getSearchCondition());
		model.addAttribute("portalCode", portalCode);

		return "portal/admin/batch/listTrigger";
	}

	/**
	 * Batch Log 목록을 가져온다.
	 * 
	 * @param searchCondition
	 * @param accessResult
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/listBatchLog.do")
	public String listBatchLog(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) BatchSearchCondition searchCondition,
			AccessingResult accessResult, Model model) {
		// 권한 체크
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		PortalCode portalCode = new PortalCode();

		SearchResult<QuartzLog> searchResult = this.batchManageService.listBatchLog(searchCondition);

		model.addAttribute("searchResult", searchResult);
		model.addAttribute("searchCondition", searchResult.getSearchCondition());
		model.addAttribute("portalCode", portalCode);

		return "portal/admin/batch/listBatchLog";
	}

	/**
	 * Cron Job 정보를 읽어온다.
	 * 
	 * @param triggerName
	 * @param jobName
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/readCronJob.do", method = RequestMethod.GET)
	public String readCronJob(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) @RequestParam String triggerName,
			@RequestParam String jobName, AccessingResult accessResult, Model model) {
		// 권한 체크
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		BatchCronJobDetail cronJobDetail = this.quartzJobService.getCronJobDetail(triggerName, jobName);
		model.addAttribute("cronJobDetail", cronJobDetail);

		return "portal/admin/batch/readCronJob";
	}

	/**
	 * Update Cron Job Form
	 * 
	 * @param triggerName
	 * @param jobName
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/updateCronJobForm.do", method = RequestMethod.GET)
	public String updateCronJobForm(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) @RequestParam String triggerName,
			@RequestParam String jobName, AccessingResult accessResult, Model model) {
		// 권한 체크
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		BatchCronJobDetail cronJobDetail = this.quartzJobService.getCronJobDetail(triggerName, jobName);
		model.addAttribute("cronJobDetail", cronJobDetail);

		return "portal/admin/batch/updateCronJobForm";
	}

	/**
	 * Cron Job 정보를 수정한다.
	 * 
	 * @param cronJobDetail
	 * @return
	 */
	@RequestMapping(value = "/updateCronJob.do", method = RequestMethod.POST)
	public ModelAndView updateCronJob(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) @Valid BatchCronJobDetail cronJobDetail,
			BindingResult result, AccessingResult accessResult, String oldJobName) {
		// 권한 체크
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		if (result.hasErrors()) {
			throw new IKEP4AjaxValidationException(result, messageSource);
		}

		this.quartzJobService.deleteJob(oldJobName);
		this.quartzJobService.createJobCronTrigger(cronJobDetail);
		this.quartzJobService.updateJobCondition(cronJobDetail);

		ModelAndView mav = new ModelAndView("portal/admin/batch/readCronJob");
		cronJobDetail = this.quartzJobService.getCronJobDetail(cronJobDetail.getTriggerName(),
				cronJobDetail.getJobName());
		mav.addObject("cronJobDetail", cronJobDetail);

		return mav;
	}

	/**
	 * Simple Job 수정폼
	 * 
	 * @param triggerName
	 * @param jobName
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/updateSimpleJobForm.do", method = RequestMethod.GET)
	public String updateSimpleJobForm(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) @RequestParam String triggerName,
			@RequestParam String jobName, AccessingResult accessResult, Model model) {
		// 권한 체크
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		BatchSimpleJobDetail simpleJobDetail = this.quartzJobService.getSimpleJobDetail(triggerName, jobName);
		model.addAttribute("simpleJobDetail", simpleJobDetail);

		return "portal/admin/batch/updateSimpleJobForm";
	}

	/**
	 * Simple Job 정보를 수정한다.
	 * 
	 * @param simpleJobDetail
	 * @return
	 */
	@RequestMapping(value = "/updateSimpleJob.do", method = RequestMethod.POST)
	public ModelAndView updateSimpleJob(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) @Valid BatchSimpleJobDetail simpleJobDetail,
			BindingResult result, AccessingResult accessResult, String oldJobName) {
		// 권한 체크
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		if (result.hasErrors()) {
			throw new IKEP4AjaxValidationException(result, messageSource);
		}

		this.quartzJobService.deleteJob(oldJobName);
		this.quartzJobService.createJobSimpleTrigger(simpleJobDetail);
		this.quartzJobService.updateJobConditionSimple(simpleJobDetail);

		ModelAndView mav = new ModelAndView("portal/admin/batch/readSimpleJob");

		simpleJobDetail = this.quartzJobService.getSimpleJobDetail(simpleJobDetail.getTriggerName(),
				simpleJobDetail.getJobName());

		mav.addObject("simpleJobDetail", simpleJobDetail);

		return mav;
	}

	/**
	 * Job을 삭제한다.
	 * 
	 * @param jobName
	 * @return
	 */
	@RequestMapping(value = "/removeJob.do", method = RequestMethod.POST)
	public String removeJob(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) @RequestParam String jobName,
			AccessingResult accessResult) {
		// 권한 체크
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		this.quartzJobService.deleteJob(jobName);

		return "redirect:/portal/admin/batch/listTrigger.do";
	}

	/**
	 * Simple Job 정보를 읽어온다.
	 * 
	 * @param triggerName
	 * @param jobName
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/readSimpleJob.do", method = RequestMethod.GET)
	public String readSimpleJob(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) @RequestParam String triggerName,
			@RequestParam String jobName, AccessingResult accessResult, Model model) {
		// 권한 체크
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		BatchSimpleJobDetail simpleJobDetail = this.quartzJobService.getSimpleJobDetail(triggerName, jobName);
		model.addAttribute("simpleJobDetail", simpleJobDetail);

		return "portal/admin/batch/readSimpleJob";
	}

	/**
	 * Job 생성 폼
	 * 
	 * @return
	 */
	@RequestMapping(value = "/createJobForm.do", method = RequestMethod.GET)
	public String createJobForm(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult) {
		// 권한 체크
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		return "portal/admin/batch/createJobForm";
	}

	/**
	 * Cron Job 생성
	 * 
	 * @param cronJobDetail
	 * @return
	 */
	@RequestMapping(value = "/createCronJob.do", method = RequestMethod.POST)
	public String createCronJob(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) @Valid BatchCronJobDetail cronJobDetail,
			BindingResult result, AccessingResult accessResult) {
		// 권한 체크
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		if (result.hasErrors()) {
			throw new IKEP4AjaxValidationException(result, messageSource);
		}

		// Cron Job 생성
		this.quartzJobService.createJobCronTrigger(cronJobDetail);
		this.quartzJobService.updateJobCondition(cronJobDetail);

		return "redirect:/portal/admin/batch/readCronJob.do?triggerName=" + cronJobDetail.getTriggerName()
				+ "&jobName=" + cronJobDetail.getJobName();
	}

	/**
	 * Simple Job 생성
	 * 
	 * @param simpleJobDetail
	 * @return
	 */
	@RequestMapping(value = "/createSimpleJob.do", method = RequestMethod.POST)
	public String createSimpleJob(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) @Valid BatchSimpleJobDetail simpleJobDetail,
			BindingResult result, AccessingResult accessResult) {
		// 권한 체크
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		if (result.hasErrors()) {
			throw new IKEP4AjaxValidationException(result, messageSource);
		}

		this.quartzJobService.createJobSimpleTrigger(simpleJobDetail);
		
		this.quartzJobService.updateJobConditionSimple(simpleJobDetail);

		return "redirect:/portal/admin/batch/readSimpleJob.do?triggerName=" + simpleJobDetail.getTriggerName()
				+ "&jobName=" + simpleJobDetail.getJobName();
	}

}
