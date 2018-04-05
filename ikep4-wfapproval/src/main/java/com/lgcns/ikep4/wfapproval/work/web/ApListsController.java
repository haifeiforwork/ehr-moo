/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.wfapproval.work.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.wfapproval.admin.model.ApCode;
import com.lgcns.ikep4.wfapproval.admin.service.ApCodeService;
import com.lgcns.ikep4.wfapproval.common.model.CommonCode;
import com.lgcns.ikep4.wfapproval.work.model.ApList;
import com.lgcns.ikep4.wfapproval.work.search.ApListSearchCondition;
import com.lgcns.ikep4.wfapproval.work.service.ApListTempService;
import com.lgcns.ikep4.workflow.engine.model.WorkItemBean;
import com.lgcns.ikep4.workflow.workplace.model.WorkplaceCode;
import com.lgcns.ikep4.workflow.workplace.search.WorkplaceSearchCondition;
import com.lgcns.ikep4.workflow.workplace.service.WorkplaceListService;


/**
 * 결재 양식 선택 <br/>
 * 다음 사항 포함
 * 
 * <pre>
 * <li>양식 목록 조회</li>
 * <li>양식 선택</li>
 * </pre>
 * 
 * @author 장규진(mistpoet@paran.com)
 * @version $Id: ApListsController.java 16234 2011-08-18 02:44:36Z giljae $
 */
@Controller
@RequestMapping(value = "/wfapproval/work/aplist")
@SessionAttributes("aplist")
public class ApListsController extends BaseController {

	@Autowired
	private WorkplaceListService workplaceListService;
	
	@Autowired
	private ApCodeService apCodeService;	
	
	@Autowired
	private ApListTempService apListTempService;

	@RequestMapping(value = "/listApTodo.do")
	public ModelAndView getApTodoList( @RequestParam(value = "linkType", required = false) String linkType,
										WorkplaceSearchCondition workplaceSearchCondition,
			                           HttpServletRequest request,
			                           HttpServletResponse response) throws ServletException, IOException { 

		ModelAndView mav = new ModelAndView("/wfapproval/work/aplist/readApList");
		
		if(linkType != null && linkType.equals("mywork")){
			mav.setViewName("/wfapproval/work/aplist/readMyApList");
		}
		


		//String userId    = request.getParameter("userId") == null ? "test01" : request.getParameter("userId").toString();
		User user = (User) getRequestAttribute(CommonCode.IKEP_USER);
		String localeCode = user.getLocaleCode();
		String userId = user.getUserId();

		String listType  = "todoList";
        
		workplaceSearchCondition.setQueryId(listType);
		workplaceSearchCondition.setUserId(userId);

		SearchResult<WorkItemBean> searchResult = this.workplaceListService.workplaceWorkList(workplaceSearchCondition);	

		// 양식유형 목록
		List<ApCode> listFormTypeCd = this.apCodeService.listGroupApCodeByValue(CommonCode.FORM_TYPE_CD, localeCode);
		
		// 문서상태 목록
		List<ApCode> listApprDocState = this.apCodeService.listGroupApCodeByValue(CommonCode.APPR_DOC_STATE, localeCode);
		
		mav.addObject("apList",          searchResult);
		mav.addObject("searchCondition", searchResult.getSearchCondition());
		mav.addObject("pUserName",       (request.getParameter("userName")==null)?"":request.getParameter("userName"));
		mav.addObject("workplaceCode",   new WorkplaceCode());
		mav.addObject("userId",          userId);
		mav.addObject("listType",        listType);
		//mav.addObject("pageTitle",       "미결함");
		mav.addObject("size",            "10");	
		mav.addObject("totalcount",      searchResult.getRecordCount());
		mav.addObject("commonCode", new CommonCode());
		mav.addObject("listFormTypeCd", listFormTypeCd);
		mav.addObject("listApprDocState", listApprDocState);
		
		return mav;
	}

	@RequestMapping(value = "/listApMyRequest.do")
	public ModelAndView getApMyRequest( @RequestParam(value = "linkType", required = false) String linkType,
										WorkplaceSearchCondition workplaceSearchCondition,
			                            HttpServletRequest request,
			                            HttpServletResponse response) throws ServletException, IOException { 

		ModelAndView mav = new ModelAndView("/wfapproval/work/aplist/readApList");
		
		if(linkType != null && linkType.equals("mywork")){
			mav.setViewName("/wfapproval/work/aplist/readMyApList");
		}		

		//String userId    = request.getParameter("userId") == null ? "test01" : request.getParameter("userId").toString();
		User user = (User) getRequestAttribute(CommonCode.IKEP_USER);
		String localeCode = user.getLocaleCode();
		String userId = user.getUserId();

		String listType  = "myRequestList";

		workplaceSearchCondition.setQueryId(listType);
		workplaceSearchCondition.setUserId(userId);

		SearchResult<WorkItemBean> searchResult = this.workplaceListService.workplaceWorkList(workplaceSearchCondition);		
		
		// 양식유형 목록
		List<ApCode> listFormTypeCd = this.apCodeService.listGroupApCodeByValue(CommonCode.FORM_TYPE_CD, localeCode);
		
		// 문서상태 목록
		List<ApCode> listApprDocState = this.apCodeService.listGroupApCodeByValue(CommonCode.APPR_DOC_STATE, localeCode);
		
		mav.addObject("apList",          searchResult);
		mav.addObject("searchCondition", searchResult.getSearchCondition());
		mav.addObject("workplaceCode",   new WorkplaceCode());
		mav.addObject("userId",          userId);
		mav.addObject("listType",        listType);
		//mav.addObject("pageTitle",       "상신함");
		mav.addObject("size",            "10");	
		mav.addObject("totalcount",      searchResult.getRecordCount());
		mav.addObject("commonCode", new CommonCode());
		mav.addObject("listFormTypeCd", listFormTypeCd);
		mav.addObject("listApprDocState", listApprDocState);
		
		return mav;
	}

	@RequestMapping(value = "/listApComplete.do")
	public ModelAndView getApCompleteList( WorkplaceSearchCondition workplaceSearchCondition,
			                           	   HttpServletRequest request,
			                               HttpServletResponse response) throws ServletException, IOException { 

		ModelAndView mav = new ModelAndView("/wfapproval/work/aplist/readApList");

		//String userId    = request.getParameter("userId") == null ? "test01" : request.getParameter("userId").toString();
		User user = (User) getRequestAttribute(CommonCode.IKEP_USER);
		String localeCode = user.getLocaleCode();
		String userId = user.getUserId();
		
		String listType  = "completeList";
        
		workplaceSearchCondition.setQueryId(listType);
		workplaceSearchCondition.setUserId(userId);

		SearchResult<WorkItemBean> searchResult = this.workplaceListService.workplaceWorkList(workplaceSearchCondition);		
		
		// 양식유형 목록
		List<ApCode> listFormTypeCd = this.apCodeService.listGroupApCodeByValue(CommonCode.FORM_TYPE_CD, localeCode);
		
		// 문서상태 목록
		List<ApCode> listApprDocState = this.apCodeService.listGroupApCodeByValue(CommonCode.APPR_DOC_STATE, localeCode);

		mav.addObject("apList",          searchResult);
		mav.addObject("searchCondition", searchResult.getSearchCondition());
		mav.addObject("pUserName",       (request.getParameter("userName")==null)?"":request.getParameter("userName"));
		mav.addObject("workplaceCode",   new WorkplaceCode());
		mav.addObject("userId",          userId);
		mav.addObject("listType",        listType);
		//mav.addObject("pageTitle",       "기결함");
		mav.addObject("size",            "10");	
		mav.addObject("totalcount",      searchResult.getRecordCount());
		mav.addObject("commonCode", new CommonCode());
		mav.addObject("listFormTypeCd", listFormTypeCd);
		mav.addObject("listApprDocState", listApprDocState);
		
		return mav;
	}

	@RequestMapping(value = "/listApTemp.do")
	public ModelAndView getApTempList(ApListSearchCondition apListSearchCondition, BindingResult result, SessionStatus status) {

		//페이지 사이즈 설정.
		//apListSearchCondition.setPagePerRecord(10);

		User user = (User) getRequestAttribute(CommonCode.IKEP_USER);
		String localeCode = user.getLocaleCode();
		String userId = user.getUserId();
		apListSearchCondition.setRegistUserId(userId);
		apListSearchCondition.setApprDocState(0);

		SearchResult<ApList> searchResult = this.apListTempService.readApSearchList(apListSearchCondition);
		
		List<ApCode> listFormTypeCd = this.apCodeService.listGroupApCodeByValue(CommonCode.FORM_TYPE_CD, localeCode);

		ModelAndView mav = new ModelAndView("/wfapproval/work/aplist/readApTempList");
		
		mav.addObject("apList", searchResult);
		mav.addObject("searchCondition", searchResult.getSearchCondition());
		//mav.addObject("pageTitle",  "임시보관함");
		mav.addObject("listType",   "tempList");
		mav.addObject("commonCode", new CommonCode());
		mav.addObject("listFormTypeCd", listFormTypeCd);

		return mav;
	}

	@RequestMapping(value = "/listApRef.do")
	public ModelAndView getApRefList(ApListSearchCondition apListSearchCondition, BindingResult result, SessionStatus status, HttpServletRequest request) {

		//페이지 사이즈 설정.
		//apListSearchCondition.setPagePerRecord(10);

		User user = (User) getRequestAttribute(CommonCode.IKEP_USER);
		String localeCode = user.getLocaleCode();
		String refUserId = user.getUserId();
		apListSearchCondition.setRefUserId(refUserId);
		apListSearchCondition.setApprDocState(2);

		SearchResult<ApList> searchResult = this.apListTempService.readApSearchList(apListSearchCondition);
		
		List<ApCode> listFormTypeCd = this.apCodeService.listGroupApCodeByValue(CommonCode.FORM_TYPE_CD, localeCode);

		ModelAndView mav = new ModelAndView("/wfapproval/work/aplist/readApRefList");
		
		mav.addObject("apList", searchResult);
		mav.addObject("searchCondition", searchResult.getSearchCondition());
		mav.addObject("pUserName",       (request.getParameter("userName")==null)?"":request.getParameter("userName"));
		mav.addObject("listType",   "refList");
		mav.addObject("commonCode", new CommonCode());
		mav.addObject("listFormTypeCd", listFormTypeCd);

		return mav;
	}

}
