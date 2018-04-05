/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.wfapproval.admin.web;

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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.wfapproval.admin.model.ApCode;
import com.lgcns.ikep4.wfapproval.admin.service.ApCodeService;


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
 * @version $Id: ApCodeController.java 16234 2011-08-18 02:44:36Z giljae $
 */
@Controller
@RequestMapping(value = "/wfapproval/admin/apcode")
@SessionAttributes("apCode")
public class ApCodeController extends BaseController {

	@Autowired
	private ApCodeService apCodeService;

	/**
	 * 코드목록 조회
	 * 
	 * @return
	 */
	@RequestMapping(value = "/listApCode", method = RequestMethod.GET)
	public ModelAndView readApCodeList() {

		ModelAndView mav = new ModelAndView("/wfapproval/admin/apcode/readApCodeList");

		// ===============================================================
		// 루트코드 하위목록을 JSON 변환처리
		// 초기 트리목록
		// ===============================================================
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = this.getJSONArrayByDataList(this.apCodeService.listRootApCode("2"));

		jsonObject.put("items", jsonArray);

		mav.addObject("codeItems", jsonObject.toString());

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
	@RequestMapping(value = "/ajaxReadApCodeView", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public String ajaxReadApCodeView(@RequestParam(value = "codeId", required = false) String codeId,
			@RequestParam(value = "parentCodeId", required = false) String parentCodeId, Model model) {

		ApCode apCode = null;

		try {

			if (codeId != null) {
				apCode = this.apCodeService.readApCode(codeId);
			} else if (parentCodeId != null) {
				apCode = new ApCode();

				apCode.setParentCodeId(parentCodeId);
			}

			// Master
			model.addAttribute("apCode", apCode);

		} catch (Exception ex) {
			throw new IKEP4AjaxException("readApCodeView", ex);
		}

		return "/wfapproval/admin/apcode/updateApCodeForm";
	}

	/**
	 * 코드 저장
	 * 
	 * @param apCode
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/ajaxApplyApCode", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	ApCode ajaxApplyApCode(@Valid ApCode apCode, BindingResult result, SessionStatus status) {

		try {
			if (result.hasErrors()) {
				throw new IKEP4AjaxException("", new Exception("Code Update Valid-Error"));
			}

			if (StringUtil.isEmpty(apCode.getCodeId()))
				this.apCodeService.createApCode(apCode);
			else
				this.apCodeService.updateApCode(apCode);

			// 세션 상태를 complete
			status.setComplete();

		} catch (Exception ex) {
			throw new IKEP4AjaxException("applyApCode", ex);
		}

		return apCode;
	}

	/**
	 * 코드삭제
	 * 
	 * @param apCodeIds
	 * @param parentCodeId
	 * @param status
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/ajaxDeleteApCode", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	ApCode ajaxDeleteApCode(@RequestParam("apCodeIds") List<String> apCodeIds,
			@RequestParam(value = "parentCodeId", required = false) String parentCodeId, SessionStatus status,
			Model model) {

		ApCode apCode = new ApCode();

		try {
			String deleteCodeId;

			if (!apCodeIds.isEmpty()) {
				for (int i = 0; i < apCodeIds.size(); i++) {

					deleteCodeId = apCodeIds.get(i);

					if (this.apCodeService.existsChildApCode(deleteCodeId)) {
						throw new IKEP4AjaxException("ajaxDeleteApCode", new Exception("[" + deleteCodeId
								+ "] 하위코드 목록이 있습니다."));
					} else {
						this.apCodeService.deleteApCode(deleteCodeId);
					}
				}
			}

			apCode.setParentCodeId(parentCodeId);

			// 세션 상태를 complete
			status.setComplete();

		} catch (Exception ex) {
			throw new IKEP4AjaxException("applyApCode", ex);
		}

		return apCode;
	}

	/**
	 * 코드정렬 저장
	 * 
	 * @param apCodeIds
	 * @param parentCodeId
	 * @param status
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/ajaxSaveOrderApCode", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	ApCode ajaxSaveOrderApCode(@RequestParam("apCodeOrder") List<String> apCodeOrder,
			@RequestParam(value = "parentCodeId", required = false) String parentCodeId, SessionStatus status,
			Model model) {

		ApCode apCode = new ApCode();

		try {

			if (!apCodeOrder.isEmpty()) {
				for (int i = 0; i < apCodeOrder.size(); i++) {

					apCode.setCodeId(apCodeOrder.get(i)); // Code ID
					apCode.setCodeOrder(String.valueOf(i)); // Code 순번

					this.apCodeService.updateApCode(apCode);
				}
			}

			apCode.setParentCodeId(parentCodeId);

			// 세션 상태를 complete
			status.setComplete();

		} catch (Exception ex) {
			throw new IKEP4AjaxException("ajaxSaveOrderApCode", ex);
		}

		return apCode;
	}

	/**
	 * 코드 하위목록 가져오기
	 * 
	 * @param codeId 부모키값
	 * @param model
	 * @return List<ApCode>
	 */
	@RequestMapping(value = "/ajaxListCodeChild", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public String ajaxListCodeChild(@RequestParam(value = "parentCodeId", required = false) String parentCodeId,
			Model model) {

		List<ApCode> listApCode = new ArrayList<ApCode>();

		try {
			if (parentCodeId != null) {
				listApCode = this.apCodeService.listGroupApCode(parentCodeId);
			}

			model.addAttribute("apCodeChild", listApCode);
			model.addAttribute("parentCodeId", parentCodeId);
		} catch (Exception ex) {
			throw new IKEP4AjaxException("ajaxListCodeChild", ex);
		}

		return "/wfapproval/admin/apcode/readApCodeChildList";
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

			listMap = this.getJSONObjectByDataList(this.apCodeService.listGroupApCode(codeId));

			/*
			 * JSONObject jsonObject = new JSONObject(); JSONArray jsonArray =
			 * this
			 * .getJSONArrayByDataList(this.apCodeService.listGroupApCode(codeId
			 * )); jsonObject.put("items", jsonArray);
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
	private List<Map<String, Object>> getJSONObjectByDataList(List<ApCode> listRoot) {

		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();

		for (ApCode apCode : listRoot) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("type", "group");
			map.put("name", apCode.getKrName());
			map.put("code", apCode.getCodeId());
			map.put("krName", apCode.getKrName());
			map.put("parent", apCode.getParentCodeId());
			map.put("hasChild", apCode.getChildCount());
			listMap.add(map);
		}

		return listMap;
	}

	/**
	 * 트리노드 목록을 JSONArray 변환작업
	 * 
	 * @return JSONArray
	 */
	private JSONArray getJSONArrayByDataList(List<ApCode> listRoot) {

		JSONArray jsonArray = new JSONArray();
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();

		for (ApCode apCode : listRoot) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("type", "group");
			map.put("name", apCode.getKrName());
			map.put("code", apCode.getCodeId());
			map.put("krName", apCode.getKrName());
			map.put("parent", apCode.getParentCodeId());
			map.put("hasChild", apCode.getChildCount());
			listMap.add(map);
		}

		for (Map<String, Object> jsonItem : listMap) {
			jsonArray.add(jsonItem);
		}

		return jsonArray;
	}
}
