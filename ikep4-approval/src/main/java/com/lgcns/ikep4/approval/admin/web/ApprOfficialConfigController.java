/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.admin.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.approval.admin.model.ApprAdminConfig;
import com.lgcns.ikep4.approval.admin.model.ApprOfficialConfig;
import com.lgcns.ikep4.approval.admin.model.CommonCode;
import com.lgcns.ikep4.approval.admin.service.ApprAdminConfigService;
import com.lgcns.ikep4.approval.admin.service.ApprOfficialConfigService;
import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.lang.StringUtil;


/**
 * 공문 설정
 * 
 * @author 유승목
 * @version $Id$
 */
@Controller
@RequestMapping(value = "/approval/admin/apprOfficialConfig")
public class ApprOfficialConfigController extends BaseController {

	@Autowired
	private ApprOfficialConfigService apprOfficialConfigService;

	@Autowired
	private ApprAdminConfigService apprAdminConfigService;

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
	 * 공문 설정 화면
	 * 
	 * @return
	 */
	@RequestMapping(value = "/apprOfficialConfigForm.do")
	public ModelAndView apprOfficialConfigForm() {

		ModelAndView mav = new ModelAndView("/approval/admin/apprOfficialConfig/apprOfficialConfigForm");

		try {
			ApprOfficialConfig officialConfig = apprOfficialConfigService.read("");

			User user = (User) getRequestAttribute(CommonCode.IKEP_USER);
			Portal portal = (Portal) getRequestAttribute("ikep.portal");

			mav.addObject("isSystemAdmin", this.isSystemAdmin(user));
			mav.addObject("isReadAll", this.isReadAll(portal.getPortalId()));
			mav.addObject("officialConfig", officialConfig);

		} catch (Exception ex) {
			throw new IKEP4ApplicationException("", ex);
		}

		return mav;
	}

	/**
	 * 공문 설정 저장
	 * 
	 * @param apprOfficialConfig
	 * @return
	 */
	@RequestMapping(value = "/apprOfficialConfigSave.do")
	public @ResponseBody
	String apprOfficialConfigSave(ApprOfficialConfig apprOfficialConfig) {

		try {

			User user = (User) getRequestAttribute("ikep.user");
			apprOfficialConfig.setRegisterId(user.getUserId());
			apprOfficialConfig.setRegisterName(user.getUserName());

			String portalId = (String) getRequestAttribute("ikep.portalId");
			apprOfficialConfig.setPortalId(portalId);

			if (StringUtil.isEmpty(apprOfficialConfig.getConfigId())) {
				apprOfficialConfigService.create(apprOfficialConfig);
			} else {
				apprOfficialConfigService.update(apprOfficialConfig);
			}

		} catch (Exception ex) {
			throw new IKEP4ApplicationException("", ex);
		}

		return "ok";
	}

	/**
	 * 이미지 삭제
	 * 
	 * @param configId
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "/deleteImgFile.do")
	public @ResponseBody
	String deleteImgFile(String configId, String type) {

		try {

			User user = (User) getRequestAttribute("ikep.user");

			Map map = new HashMap();
			map.put("type", type);
			map.put("registerId", user.getUserId());
			map.put("registerName", user.getUserName());
			map.put("configId", configId);

			apprOfficialConfigService.deleteImgFile(map);

		} catch (Exception ex) {
			throw new IKEP4ApplicationException("", ex);
		}

		return "ok";
	}

}
