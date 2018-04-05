/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.microblogging.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.socialpack.microblogging.base.Constant;
import com.lgcns.ikep4.socialpack.microblogging.model.Addon;
import com.lgcns.ikep4.socialpack.microblogging.service.AddonService;
import com.lgcns.ikep4.util.lang.StringUtil;

/**
 * 
 * 마이크로블로그 게시글 내 부가정보 관리
 *
 * @author 최성우(csw9737@hanmail.net)
 * @version $Id: AddonController.java 16246 2011-08-18 04:48:28Z giljae $
 */
@Controller
@RequestMapping(value = "/socialpack/microblogging/addon")
public class AddonController extends BaseController {

	@Autowired
	private AddonService addonService;

	/**
	 * Addon 등록 후 displayCode를 리턴한다.
	 *
	 * @param addon Addon 정보
	 * @param result 바인딩결과
	 * @param status 세션상태
	 * @return displayCode
	 */
	@RequestMapping("/createAddon")
	public @ResponseBody String onSubmit(@Valid Addon addon, BindingResult result, SessionStatus status) {

		if (log.isDebugEnabled()) 
		{
			log.debug("addon:"+addon);
		}

		/*
		 * ADDON 테이블에 이미 등록되어 있는지 조회한다. 현재는 shortUrl인 경우..
		 */
		Addon orgAddon = addonService.selectBySourceLink(addon.getSourceLink());

		if (log.isDebugEnabled()) 
		{
			log.debug("orgAddon:"+orgAddon);
		}

		String displayCode = "";
		// 기존에 url이  등록되어 있는 경우
		if(null != orgAddon && !("").equals(orgAddon))
		{
			displayCode = orgAddon.getDisplayCode();
		}
		else
		{
			/*
			 * ADDON_CODE 코드를 공통 util + sequence로 생성한다.
			 */
			String addonCode = StringUtil.randomStr(Constant.ADDON_RANDOMSTR_SIZE) + addonService.getSeq();
			
			//마이크로블로깅 링크 타입 (0 : URL, 1 : 투표, 2: 이미지, 3 :  비디오) 별 displayCode 생성
			if(Constant.ADDON_TYPE_URL.equals(addon.getAddonType()))
			{
				displayCode = Constant.ADDON_LEFTBRACE_URL + addonCode + Constant.ADDON_RIGHTBRACE; // {url:xxx}
			}
			else if(Constant.ADDON_TYPE_POLL.equals(addon.getAddonType()))
			{
				displayCode = Constant.ADDON_LEFTBRACE_POLL + addonCode + Constant.ADDON_RIGHTBRACE; // {poll:xxx}
			}
			else if(Constant.ADDON_TYPE_IMG.equals(addon.getAddonType()))
			{
				displayCode = Constant.ADDON_LEFTBRACE_IMG + addonCode + Constant.ADDON_RIGHTBRACE; // {img:xxx}
			}
			else if(Constant.ADDON_TYPE_FILE.equals(addon.getAddonType()))
			{
				displayCode = Constant.ADDON_LEFTBRACE_FILE + addonCode + Constant.ADDON_RIGHTBRACE; // {file:xxx}
			}
			addon.setAddonCode(addonCode);
			addon.setDisplayCode(displayCode);
			
			addonService.create(addon);

		}
		// 세션 상태를 complete하여 이중 전송 방지
		status.setComplete();
		return displayCode;
	}

}
