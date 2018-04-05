/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.profile.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.profile.service.ProfileFileUploadService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.web.BaseController;

/**
 * 프로파일용 메인 사진 이미지 등록을 위한 Controller
 *
 * @author 이형운 (dewey94@wooin.info)
 * @version $Id: ProfileFileUploadController.java 16316 2011-08-22 00:26:53Z giljae $
 */
@Controller
@RequestMapping(value = "/support/profile")
@SessionAttributes("profile")
public class ProfileFileUploadController extends BaseController {

	@Autowired
	FileService fileService;
	@Autowired
	ProfileFileUploadService profileFileUploadService;	

	/**
	 * 프로 파일 메인 사진 업로드용 
	 * @param targetUserId 프로파일 대상자 ID
	 * @param fileId 저장할 프로파일 사진 File ID
	 * @return 저장된 파일 아이템 Map
	 */
	@RequestMapping("/uploadPicture.do")
	public @ResponseBody Map<String, Object> uploadPicture(@RequestParam("targetUserId") String targetUserId
														, @RequestParam("fileId") String fileId) {

		Map<String, Object> fileItem = new HashMap<String, Object>();
		fileItem.put("fileId", fileId);
		User user = (User) getRequestAttribute("ikep.user");

		try {
			profileFileUploadService.savePictureId(fileId, user.getUserId(), user.getUserName(), IKepConstant.ITEM_TYPE_CODE_PROFILE, user);

	    } catch(Exception ex) {
	        throw new IKEP4AjaxException("code", ex);
	    }

		return fileItem;
	}
	
	/**
	 * 프로 파일 메인 인사 사진 업로드용 
	 * @param targetUserId 프로파일 대상자 ID
	 * @param fileId 저장할 프로파일 사진 File ID
	 * @return 저장된 파일 아이템 Map
	 */
	@RequestMapping("/uploadProfilePicture.do")
	public @ResponseBody Map<String, Object> uploadProfilePicture(@RequestParam("targetUserId") String targetUserId
																, @RequestParam("fileId") String fileId) {

		Map<String, Object> fileItem = new HashMap<String, Object>();
		fileItem.put("fileId", fileId);
		User user = (User) getRequestAttribute("ikep.user");
		
		try {

			profileFileUploadService.saveProfilePictureId(fileId, user.getUserId(), user.getUserName(), IKepConstant.ITEM_TYPE_CODE_PROFILE, user);

		} catch (Exception e) {
			throw new IKEP4AjaxException("", e);
		}

		return fileItem;
	}
	
}
