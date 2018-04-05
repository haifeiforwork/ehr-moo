/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.admin.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.approval.admin.model.ApprAdminConfig;
import com.lgcns.ikep4.approval.admin.model.ApprCode;
import com.lgcns.ikep4.approval.admin.model.CommonCode;
import com.lgcns.ikep4.approval.admin.service.ApprAdminConfigService;
import com.lgcns.ikep4.approval.admin.service.ApprCodeService;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.lang.StringUtil;


/**
 * 결재 코드관리 <br/>
 * 다음 사항 포함
 * 
 * <pre>
 * <li>코드관리 목록 조회</li>
 * <li>코드정보 조회</li>
 * <li>코드등록/수정</li>
 * </pre>
 * 
 * @author 박희진(neoheejin@naver.com)
 * @version $Id: ApprCodeController.java 16234 2011-08-18 02:44:36Z giljae $
 */
@Controller
@RequestMapping(value = "/approval/admin/apprCode")
public class ApprCodeController extends BaseController {

	@Autowired
	private ApprCodeService apprCodeService;

	@Autowired
	ApprAdminConfigService apprAdminConfigService;

	@Autowired
	private ACLService aclService;

	/**
	 * 로그인 사용자가 전자결재의 시스템 관리자인지 체크한다.
	 * 
	 * @param user 로그인 사용자 모델 객체
	 * @return 시스템 관리자 여부
	 */
	public boolean isSystemAdmin(User user) {
		return this.aclService.isSystemAdmin("Approval", user.getUserId());
	}

	/**
	 * 환경 설정에 정의된 값을 조회한다
	 * 
	 * @param portalId
	 * @return
	 */
	public boolean isReadAll(String portalId) {

		boolean isRead = false;
		ApprAdminConfig apprAdminConfig = apprAdminConfigService.adminConfigDetail(portalId);
		if (apprAdminConfig.getIsReadAll().equals("1")) {
			// IKEP4_APPR_READ_ALL에 존재하는지 확인
			User user = (User) getRequestAttribute(CommonCode.IKEP_USER);
			isRead = apprAdminConfigService.existReadAllAuth(user.getUserId());
		}
		return isRead;
	}

	/**
	 * 코드목록 조회
	 * 
	 * @return
	 */
	@RequestMapping(value = "/listApprCode", method = RequestMethod.GET)
	public ModelAndView readApprCodeList() {

		// session
		User user = (User) getRequestAttribute("ikep.user");

		ModelAndView mav = new ModelAndView("/approval/admin/apprCode/readApprCodeList");

		// ===============================================================
		// 루트코드 하위목록을 JSON 변환처리
		// 초기 트리목록
		// ===============================================================
		Map<String, String> map = new HashMap<String, String>();
		map.put("portalId", user.getPortalId());
		map.put("level", "2");

		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = this.getJSONArrayByDataList(this.apprCodeService.listRootApprCode(map));

		jsonObject.put("items", jsonArray);

		mav.addObject("codeItems", jsonObject.toString());
		mav.addObject("isSystemAdmin", this.isSystemAdmin(user));
		mav.addObject("isReadAll", this.isReadAll(user.getPortalId()));

		return mav;
	}

	/**
	 * 코드 상세화면 조회
	 * 
	 * @param codeId
	 * @param parentCodeId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/ajaxReadApprCodeView", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public String ajaxReadApprCodeView(@RequestParam(value = "codeId", required = false) String codeId,
			@RequestParam(value = "parentCodeId", required = false) String parentCodeId, Model model) {

		ApprCode apprCode = null;

		try {

			if (codeId != null) {
				apprCode = this.apprCodeService.readApprCode(codeId);
			} else if (parentCodeId != null) {
				apprCode = new ApprCode();

				apprCode.setParentCodeId(parentCodeId);
			}

			// Master
			model.addAttribute("apprCode", apprCode);

		} catch (Exception ex) {
			throw new IKEP4AjaxException("readApprCodeView", ex);
		}

		return "/approval/admin/apprCode/updateApprCodeForm";
	}

	/**
	 * 코드 저장
	 * 
	 * @param apprCode
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/ajaxApplyApprCode", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	ApprCode ajaxApplyApprCode(@Valid ApprCode apprCode, BindingResult result) {

		try {
			if (result.hasErrors()) {
				throw new IKEP4AjaxException("", new Exception("Code Update Valid-Error"));
			}

			// session
			User user = (User) getRequestAttribute("ikep.user");
			apprCode.setPortalId(user.getPortalId());

			if (StringUtil.isEmpty(apprCode.getCodeId()))
				this.apprCodeService.createApprCode(apprCode);
			else
				this.apprCodeService.updateApprCode(apprCode);

		} catch (Exception ex) {
			throw new IKEP4AjaxException("applyApprCode", ex);
		}

		return apprCode;
	}

	/**
	 * 코드삭제
	 * 
	 * @param apprCodeIds
	 * @param parentCodeId
	 * @param status
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/ajaxDeleteApprCode", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	ApprCode ajaxDeleteApprCode(@RequestParam("apprCodeIds") List<String> apprCodeIds,
			@RequestParam(value = "parentCodeId", required = false) String parentCodeId, Model model) {

		ApprCode apprCode = new ApprCode();

		try {
			String deleteCodeId;

			if (!apprCodeIds.isEmpty()) {
				for (int i = 0; i < apprCodeIds.size(); i++) {

					deleteCodeId = apprCodeIds.get(i);

					if (this.apprCodeService.existsChildApprCode(deleteCodeId)) {
						throw new IKEP4AjaxException("ajaxDeleteApprCode", new Exception("[" + deleteCodeId
								+ "] 하위코드 목록이 있습니다."));
					} else {
						this.apprCodeService.deleteApprCode(deleteCodeId);
					}
				}
			}

			apprCode.setParentCodeId(parentCodeId);

		} catch (Exception ex) {
			throw new IKEP4AjaxException("applyApprCode", ex);
		}

		return apprCode;
	}

	/**
	 * 코드정렬 저장
	 * 
	 * @param apprCodeIds
	 * @param parentCodeId
	 * @param status
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/ajaxSaveOrderApprCode", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	ApprCode ajaxSaveOrderApprCode(@RequestParam("apprCodeOrder") List<String> apprCodeOrder,
			@RequestParam(value = "parentCodeId", required = false) String parentCodeId, Model model) {

		ApprCode apprCode = new ApprCode();

		try {

			if (!apprCodeOrder.isEmpty()) {
				for (int i = 0; i < apprCodeOrder.size(); i++) {

					apprCode.setCodeId(apprCodeOrder.get(i)); // Code ID
					apprCode.setCodeOrder(String.valueOf(i)); // Code 순번

					this.apprCodeService.updateApprCode(apprCode);
				}
			}

			apprCode.setParentCodeId(parentCodeId);

		} catch (Exception ex) {
			throw new IKEP4AjaxException("ajaxSaveOrderApprCode", ex);
		}

		return apprCode;
	}

	/**
	 * 코드 하위목록 가져오기
	 * 
	 * @param codeId 부모키값
	 * @param model
	 * @return List<ApprCode>
	 */
	@RequestMapping(value = "/ajaxListCodeChild", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public String ajaxListCodeChild(@RequestParam(value = "parentCodeId", required = false) String parentCodeId,
			Model model) {

		List<ApprCode> listApprCode = new ArrayList<ApprCode>();

		try {
			if (parentCodeId != null) {
				listApprCode = this.apprCodeService.listGroupApprCode(parentCodeId);
			}

			model.addAttribute("apprCodeChild", listApprCode);
			model.addAttribute("parentCodeId", parentCodeId);
		} catch (Exception ex) {
			throw new IKEP4AjaxException("ajaxListCodeChild", ex);
		}

		return "/approval/admin/apprCode/readApprCodeChildList";
	}

	@RequestMapping("/ajaxTreeCodeChild")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	List<Map<String, Object>> ajaxTreeCodeChild(@RequestParam(value = "codeId", required = false) String codeId,
			HttpServletResponse response) {

		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();

		try {
			// ===============================================================
			// 선택코드 하위목록을 JSON 변환처리
			// ===============================================================
			if (StringUtil.isEmpty(codeId))
				codeId = "-1";

			listMap = this.getJSONObjectByDataList(this.apprCodeService.listGroupApprCode(codeId));

			/*
			 * JSONObject jsonObject = new JSONObject(); JSONArray jsonArray =
			 * this
			 * .getJSONArrayByDataList(this.apprCodeService.listGroupApprCode
			 * (codeId )); jsonObject.put("items", jsonArray);
			 * response.setContentType("text/json; charset=utf-8");
			 * response.getWriter(). println(jsonObject.toString());
			 */

		} catch (Exception ex) {
			throw new IKEP4AjaxException("ajaxTreeCodeChild", ex);
		}

		return listMap;
	}

	/**
	 * 트리노드 목록을 Key/Value 변환작업
	 * 
	 * @param listRoot
	 * @return
	 */
	private List<Map<String, Object>> getJSONObjectByDataList(List<ApprCode> listRoot) {

		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();

		for (ApprCode apprCode : listRoot) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("type", "group");
			map.put("name", apprCode.getCodeKrName());
			map.put("code", apprCode.getCodeId());
			map.put("codeKrName", apprCode.getCodeKrName());
			map.put("parent", apprCode.getParentCodeId());
			map.put("hasChild", apprCode.getChildCount());
			listMap.add(map);
		}

		return listMap;
	}

	/**
	 * 트리노드 목록을 JSONArray 변환작업
	 * 
	 * @return JSONArray
	 */
	private JSONArray getJSONArrayByDataList(List<ApprCode> listRoot) {

		JSONArray jsonArray = new JSONArray();
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();

		for (ApprCode apprCode : listRoot) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("type", "group");
			map.put("name", apprCode.getCodeKrName());
			map.put("code", apprCode.getCodeId());
			map.put("codeKrName", apprCode.getCodeKrName());
			map.put("parent", apprCode.getParentCodeId());
			map.put("hasChild", apprCode.getChildCount());
			listMap.add(map);
		}

		for (Map<String, Object> jsonItem : listMap) {
			jsonArray.add(jsonItem);
		}

		return jsonArray;
	}
}
