/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.work.web;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lgcns.ikep4.approval.admin.service.ApprReceptionService;
import com.lgcns.ikep4.approval.work.search.ApprDisplaySearchCondition;
import com.lgcns.ikep4.approval.work.search.ApprListSearchCondition;
import com.lgcns.ikep4.approval.work.service.ApprDisplayService;
import com.lgcns.ikep4.approval.work.service.ApprListService;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * 결제 카운트 Controller
 * 
 * @author 서지혜
 * @version $Id$
 */
@Controller
@RequestMapping(value = "/portal/approval/work")
public class ApprCountController extends BaseController {

	@Autowired
	private ApprListService apprListService;

	@Autowired
	private ApprDisplayService apprDisplayService;
	
	@Autowired
	ApprReceptionService apprReceptionService;

	// listType를 parameter로 받아 필요한 개수를 얻어 json 타입으로 return.
	// 메인화면의 퀵메뉴의 미결함 수를 가져오는 메써드
	/**
	 * 결재해야 할 문서 갯수
	 */
	@RequestMapping(value = "/getApprovalTodoCount.do", method = RequestMethod.POST)
	public @ResponseBody
	String getApprovalTodoCount() {

		ApprListSearchCondition apprListSearchCondition = new ApprListSearchCondition();

		User user = (User) this.getRequestAttribute("ikep.user");

		apprListSearchCondition.setPortalId(user.getPortalId());
		apprListSearchCondition.setUserId(user.getUserId());
		apprListSearchCondition.setListType("listApprTodo");

		int count = apprListService.countBySearchCondition(apprListSearchCondition);

		JSONObject jsonObj = new JSONObject();
		jsonObj.put("count", count);

		return jsonObj.toString(); // {"count":개수} 형태의 json 타입으로 return 함.
	}

	/**
	 * 검토해야할 문서 갯수
	 */
	@RequestMapping(value = "/getApprovalReqExamCount.do", method = RequestMethod.POST)
	public @ResponseBody
	String getApprovalReqExamCount() {

		ApprListSearchCondition apprListSearchCondition = new ApprListSearchCondition();

		User user = (User) this.getRequestAttribute("ikep.user");

		apprListSearchCondition.setPortalId(user.getPortalId());
		apprListSearchCondition.setUserId(user.getUserId());
		apprListSearchCondition.setListType("listApprRequestExam");

		int count = apprListService.countBySearchConditionExam(apprListSearchCondition);

		JSONObject jsonObj = new JSONObject();
		jsonObj.put("count", count);

		return jsonObj.toString(); // {"count":개수} 형태의 json 타입으로 return 함.
	}

	/**
	 * 부서수신문서 갯수
	 */
	@RequestMapping(value = "/getApprovalDeptRecCount.do", method = RequestMethod.POST)
	public @ResponseBody
	String getApprovalDeptRecCount() {

		ApprListSearchCondition apprListSearchCondition = new ApprListSearchCondition();

		User user = (User) this.getRequestAttribute("ikep.user");

		apprListSearchCondition.setPortalId(user.getPortalId());
		apprListSearchCondition.setUserId(user.getUserId());
		apprListSearchCondition.setListType("listApprDeptRec");
		
		// 접수 담당자 여부
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", user.getUserId());

		if (apprReceptionService.existReceptionUser(map)) {
			apprListSearchCondition.setReceptionUser(true);
		} else {
			apprListSearchCondition.setReceptionUser(false);
		}

		int count = apprListService.countBySearchConditionDeptRec(apprListSearchCondition);

		JSONObject jsonObj = new JSONObject();
		jsonObj.put("count", count);

		return jsonObj.toString(); // {"count":개수} 형태의 json 타입으로 return 함.
	}

	/**
	 * 개인수신문서 갯수
	 */
	@RequestMapping(value = "/ getApprovalUserRecCount.do", method = RequestMethod.POST)
	public @ResponseBody
	String getApprovalUserRecCount() {

		ApprListSearchCondition apprListSearchCondition = new ApprListSearchCondition();

		User user = (User) this.getRequestAttribute("ikep.user");

		apprListSearchCondition.setPortalId(user.getPortalId());
		apprListSearchCondition.setUserId(user.getUserId());
		apprListSearchCondition.setListType("listApprUserRec");

		int count = apprListService.countBySearchConditionDeptRec(apprListSearchCondition);

		JSONObject jsonObj = new JSONObject();
		jsonObj.put("count", count);

		return jsonObj.toString(); // {"count":개수} 형태의 json 타입으로 return 함.
	}

	/**
	 * 공람대기문서 갯수
	 */
	@RequestMapping(value = "/ getApprovalDisplayCount.do", method = RequestMethod.POST)
	public @ResponseBody
	String getApprovalDisplayCount() {

		ApprDisplaySearchCondition apprDisplaySearchCondition = new ApprDisplaySearchCondition();

		User user = (User) this.getRequestAttribute("ikep.user");

		apprDisplaySearchCondition.setPortalId(user.getPortalId());
		apprDisplaySearchCondition.setUserId(user.getUserId());
		apprDisplaySearchCondition.setListType("listApprDisplayWaiting");

		int count = apprDisplayService.countByDisplaySearchCondition(apprDisplaySearchCondition);

		JSONObject jsonObj = new JSONObject();
		jsonObj.put("count", count);

		return jsonObj.toString(); // {"count":개수} 형태의 json 타입으로 return 함.
	}

}
