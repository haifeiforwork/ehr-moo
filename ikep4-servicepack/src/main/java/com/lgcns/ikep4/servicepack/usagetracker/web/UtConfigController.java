/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.servicepack.usagetracker.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.servicepack.usagetracker.model.UtConfig;
import com.lgcns.ikep4.servicepack.usagetracker.service.UtConfigService;
import com.lgcns.ikep4.servicepack.usagetracker.util.SearchUtil;
import com.lgcns.ikep4.servicepack.usagetracker.util.UtWebUtils;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * 사용량 통계 config settting
 * 
 * @author 고인호(ihko11@daum.net)
 * @version $Id: UtConfigController.java 16244 2011-08-18 04:11:42Z giljae $
 */
@Controller
@RequestMapping(value = "/servicepack/usagetracker/logconfig")
public class UtConfigController extends BaseUsageTrackerController {

	// configure
	@Autowired
	private UtConfigService utConfigService;

	/**
	 * config read
	 * 
	 * @return
	 */
	@RequestMapping(value = "/read", method = RequestMethod.GET)
	public ModelAndView read() {
		ModelAndView mav = new ModelAndView("/servicepack/usagetracker/logconfig/read");
		User user = (User) getRequestAttribute("ikep.user");

		try {

			SearchUtil searchUtil = new SearchUtil();
			searchUtil.setPortalId(user.getPortalId());

			List<UtConfig> utConfigList = utConfigService.selectUtConfigList(searchUtil);

			mav.addObject("logConfigList", utConfigList);

		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return mav;
	}

	/**
	 * 수정
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/update")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	List<UtConfig> update(HttpServletRequest request) {
		List<UtConfig> utConfigList = new ArrayList<UtConfig>();
		User user = (User) getRequestAttribute("ikep.user");
		String portalId = (String) getRequestAttribute("ikep.portalId");

		try {
			utConfigList = getExtraData(request, user);
			utConfigService.saveOrUpdate(utConfigList, portalId);
		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}

		return utConfigList;
	}

	/**
	 * prefix cfg paramter request
	 * 
	 * @param request
	 * @param user
	 * @return
	 */
	private List<UtConfig> getExtraData(HttpServletRequest request, User user) {

		List<UtConfig> utConfigList = new ArrayList<UtConfig>();

		try {

			String prefix = "cfg";

			Map<String, Object> requestParam = UtWebUtils.getParametersStartingWith(request, prefix);
			Collection<Object> collection = requestParam.values();

			for (Iterator<Object> iterator = collection.iterator(); iterator.hasNext();) {
				String[] value = iterator.next().toString().split("\\|");

				UtConfig config = new UtConfig();
				config.setPortalId(user.getPortalId());
				config.setLogTarget(value[0]);
				config.setUsage(Integer.valueOf(value[1]));
				utConfigList.add(config);
			}

		} catch (Exception e) {
			log.error(e.getMessage());
		}

		return utConfigList;
	}

}
