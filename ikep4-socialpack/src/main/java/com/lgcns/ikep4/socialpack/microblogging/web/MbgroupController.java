/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.microblogging.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxValidationException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.socialpack.microblogging.base.Constant;
import com.lgcns.ikep4.socialpack.microblogging.model.Mbgroup;
import com.lgcns.ikep4.socialpack.microblogging.model.MbgroupMember;
import com.lgcns.ikep4.socialpack.microblogging.service.MbgroupMemberService;
import com.lgcns.ikep4.socialpack.microblogging.service.MbgroupService;


/**
 * 
 * 마이크로블로깅 그룹 관리
 *
 * @author 최성우(csw9737@hanmail.net)
 * @version $Id: MbgroupController.java 16246 2011-08-18 04:48:28Z giljae $
 */
@Controller
@RequestMapping(value = "/socialpack/microblogging/mbgroup")
public class MbgroupController extends BaseController {

	@Autowired
	private MbgroupService mbgroupService;

	@Autowired
	private MbgroupMemberService mbgroupMemberService;
	
	@Autowired
	private FileService fileService;

	/**
	 * Group을 등록한다.
	 *
	 * @param mbgroup Mbgroup 정보
	 * @param result 바인딩결과
	 * @param request HttpServletRequest
	 * @param status 세션상태
	 * @return 결과메세지
	 */
	@RequestMapping("/createMbgroup.do")
	public ModelAndView onSubmit(@Valid Mbgroup mbgroup, BindingResult result, HttpServletRequest request, SessionStatus status) {
		if(result.hasErrors()) {
			// 에러가 존재할 경우, IKEP4AjaxValidationException을 발생 시킨다.
			throw new IKEP4AjaxValidationException(result, messageSource); //BindingResult와 BaseController의 MessageSource를 parameter로 전달해야 합니다.
		}

		if (log.isDebugEnabled()) {
			log.debug("createMbgroup mbgroup:"+mbgroup);
		}

		//세션 객체 가지고 오기
		User user = (User) getRequestAttribute("ikep.user");
		
		if (log.isDebugEnabled()) {
			log.debug("user.getUserId():"+user.getUserId());
		}
		
		mbgroup.setRegisterId(user.getUserId());
		mbgroup.setRegisterName(user.getUserName());
		mbgroup.setUpdaterId(user.getUserId());
		mbgroup.setUpdaterName(user.getUserName());

		ModelAndView mav = new ModelAndView("/socialpack/microblogging/messagePage");
		
		try {
			MultipartRequest multipartRequest = (MultipartRequest) request;
			

			if (log.isDebugEnabled()) {
				log.debug("createMbgroup file:"+multipartRequest.getFiles("file"));
			}
			List<MultipartFile> fileList = multipartRequest.getFiles("file");
			
			// 첨부이미지파일 id
			String imagefileId = "";
			
			// 넘어온 파일이 있을 때만 업로드.
			if(null != fileList && 0 < fileList.size() && !fileList.get(0).isEmpty()){
				List<FileData> uploadList = fileService.uploadFile(fileList, "", "", user);
	
				if (log.isDebugEnabled()) {
					log.debug("createMbgroup uploadList:"+uploadList);
				}
				
				imagefileId = ((FileData) uploadList.get(0)).getFileId();
				
				mbgroup.setImagefileId(imagefileId);
				
				mbgroupService.create(mbgroup);
				
				List<String> fileIdList = new ArrayList<String>();
				fileIdList.add(imagefileId);
	
				//IKEP4_DM_FILE_LINK 테이블 에서 입력한다.
				fileService.createFileLink(fileIdList, mbgroup.getMbgroupId(), Constant.FILE_LINK_ITEM_TYPE_CODE, user);

				// 그룹 생성자 그룹 멤버 등록
				MbgroupMember mbgroupMember = new MbgroupMember();
				mbgroupMember.setMbgroupId		(mbgroup.getMbgroupId());
				mbgroupMember.setMemberId		(mbgroup.getRegisterId());
				mbgroupMember.setStatus			(Constant.MBGROUP_MEMBER_STATUS_NORMAL);
				mbgroupMember.setRegisterId		(mbgroup.getRegisterId());
				mbgroupMember.setRegisterName	(mbgroup.getRegisterName());

				mbgroupMemberService.create(mbgroupMember);
			}
			else
			{
				mbgroupService.create(mbgroup);

				// 그룹 생성자 그룹 멤버 등록
				MbgroupMember mbgroupMember = new MbgroupMember();
				mbgroupMember.setMbgroupId		(mbgroup.getMbgroupId());
				mbgroupMember.setMemberId		(mbgroup.getRegisterId());
				mbgroupMember.setStatus			(Constant.MBGROUP_MEMBER_STATUS_NORMAL);
				mbgroupMember.setRegisterId		(mbgroup.getRegisterId());
				mbgroupMember.setRegisterName	(mbgroup.getRegisterName());

				mbgroupMemberService.create(mbgroupMember);
			}
			
		} catch (Exception e) {
			//e.printStackTrace();
			//return messageSource.getMessage("ui.socialpack.microblogging.message.fail", null, new Locale(user.getLocaleCode()));
			mav.addObject("reStr", messageSource.getMessage("ui.socialpack.microblogging.message.fail", null, new Locale(user.getLocaleCode())));
			return mav;
		}

		// 세션 상태를 complete하여 이중 전송 방지
		status.setComplete();
		//return messageSource.getMessage("ui.socialpack.microblogging.message.success", null, new Locale(user.getLocaleCode()));
		mav.addObject("reStr", messageSource.getMessage("ui.socialpack.microblogging.message.success", null, new Locale(user.getLocaleCode())));
		return mav;
	}

	/**
	 * Group을 수정한다.
	 *
	 * @param mbgroup Mbgroup 정보
	 * @param result 바인딩결과
	 * @param request HttpServletRequest
	 * @param status 세션상태
	 * @return 결과메세지
	 */
	@RequestMapping("/updateMbgroup.do")
	public @ResponseBody
	String updateMbgroup(@Valid Mbgroup mbgroup, BindingResult result, HttpServletRequest request, SessionStatus status) {
		if(result.hasErrors()) {
			// 에러가 존재할 경우, IKEP4AjaxValidationException을 발생 시킨다.
			throw new IKEP4AjaxValidationException(result, messageSource); //BindingResult와 BaseController의 MessageSource를 parameter로 전달해야 합니다.
		}
		
		if (log.isDebugEnabled()) {
			log.debug("updateMbgroup mbgroup:"+mbgroup);
		}

		//세션 객체 가지고 오기
		User user = (User) getRequestAttribute("ikep.user");
		
		if (log.isDebugEnabled()) {
			log.debug("user.getUserId():"+user.getUserId());
		}
		
		mbgroup.setRegisterId(user.getUserId());
		mbgroup.setRegisterName(user.getUserName());
		mbgroup.setUpdaterId(user.getUserId());
		mbgroup.setUpdaterName(user.getUserName());
		
		try {
			MultipartRequest multipartRequest = (MultipartRequest) request;
			

			if (log.isDebugEnabled()) {
				log.debug("createMbgroup file:"+multipartRequest.getFiles("file"));
			}
			List<MultipartFile> fileList = multipartRequest.getFiles("file");
			
			// 첨부이미지파일 id
			String imagefileId = "";
			
			// 넘어온 파일이 있을 때만 업로드.
			if(null != fileList && 0 < fileList.size() && !fileList.get(0).isEmpty()){
				List<FileData> uploadList = fileService.uploadFile(fileList, "", "", user);
	
				if (log.isDebugEnabled()) {
					log.debug("createMbgroup uploadList:"+uploadList);
				}
				
				imagefileId = ((FileData) uploadList.get(0)).getFileId();
				
				mbgroup.setImagefileId(imagefileId);
				
				mbgroupService.update(mbgroup);
				
				List<String> fileIdList = new ArrayList<String>();
				fileIdList.add(imagefileId);
	
				//IKEP4_DM_FILE_LINK 테이블 에 입력한다.
				fileService.createFileLink(fileIdList, mbgroup.getMbgroupId(), Constant.FILE_LINK_ITEM_TYPE_CODE, user);
			}
			else
			{
				mbgroupService.update(mbgroup);
			}
			
		} catch (Exception e) {
			//e.printStackTrace();
			return messageSource.getMessage("ui.socialpack.microblogging.message.fail", null, new Locale(user.getLocaleCode()));
			//return mav;
		}

		// 세션 상태를 complete하여 이중 전송 방지
		status.setComplete();
		return messageSource.getMessage("ui.socialpack.microblogging.message.success", null, new Locale(user.getLocaleCode()));
		//return mav;
	}

	/**
	 * Group을 조회한다.
	 *
	 * @param mbgroupId mbgroup의 id 
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/getMbgroup.do")
	public ModelAndView getView(@RequestParam("mbgroupId") String mbgroupId) {
		ModelAndView mav = new ModelAndView("/socialpack/microblogging/groupFormPopup");
		if (mbgroupId != null) {
			Mbgroup mbgroup = mbgroupService.read(mbgroupId);
			mav.addObject("mbgroup", mbgroup);
		}
		return mav;
	}

	/**
	 * Group을 삭제하고 로그인사용자의 마이크로블로그 홈으로 이동한다.
	 *
	 * @param id mbgroup의 id 
	 * @return 이동할 로그인사용자의 마이크로블로그 홈
	 */
	@RequestMapping(value = "/removeMbgroup.do")
	public String remove(String id) {

		if (log.isDebugEnabled()) {
			log.debug("removeMbgroup id:"+id);
		}
		//세션 객체 가지고 오기
		User user = (User) getRequestAttribute("ikep.user");
		
		if (log.isDebugEnabled()) {
			log.debug("user.getUserId():"+user.getUserId());
		}

		Mbgroup mbgroup = mbgroupService.read(id);
		
		// 그룹 등록자만 그룹을 삭제할 수 있다.
		if(user.getUserId().equals(mbgroup.getRegisterId()))
		{
			mbgroupService.delete(id);
			
			// 그룹 이미지 삭제. 
			fileService.removeItemFile(id);
		}

		return "redirect:/socialpack/microblogging/privateHome.do?ownerId=" + user.getUserId();
	}

	/**
	 * 사용자의 Group 리스트를 리턴한다.
	 *
	 * @param userId 사용자의 id 
	 * @param showType 사용자가 속한 그룹 리스트의 링크종류나 추가버튼여부 등의 구분. 
	 * @return ModelAndView
	 */
	@RequestMapping("/grouplistByUserId.do")
	public ModelAndView grouplistByUserId(String userId, @RequestParam(value="showType", required=false) String showType) {

		ModelAndView mav = new ModelAndView("/socialpack/microblogging/groupList");
		List<Mbgroup> ownerGroupList = mbgroupService.myGroupList(userId);
		if (log.isDebugEnabled()) {
			log.debug("grouplistByUserId ownerGroupList:"+ownerGroupList);
		}
		mav.addObject("ownerGroupList"		, ownerGroupList);
		mav.addObject("showType"			, showType);

		return mav;
	}

	/**
	 * 사용자의 Group 리스트 자체를 바로 리턴한다.
	 *
	 * @param userId 사용자의 id 
	 * @return Mbgroup 객체 목록
	 */
	@RequestMapping("/grouplistByUserIdForPortlet.do")
	@ResponseBody
	public List<Mbgroup> grouplistByUserIdForPortlet(String userId) {
		if (log.isDebugEnabled()) {
			log.debug("================== grouplistByUserIdForPortlet ==================");
			log.debug("grouplistByUserIdForPortlet userId:"+userId);
		}

		List<Mbgroup> groupList = mbgroupService.myGroupList(userId);

		if (log.isDebugEnabled()) {
			log.debug("grouplistByUserIdForPortlet groupList:"+groupList);
		}

		return groupList;
	}

	/**
	 * Group 등록용 팝업을 리턴한다.
	 *
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/getGroupForm.do")
	public ModelAndView getGroupForm() {
		
		ModelAndView mav = new ModelAndView("/socialpack/microblogging/groupFormPopup");
		
		Mbgroup mbgroup = new Mbgroup();
		mav.addObject("mbgroup"			, mbgroup);
		
		return mav;
	}

	/**
	 * 기존에 등록된 GroupId인지 여부를 리턴한다.
	 *
	 * @param mbgroupId Group의 id 
	 * @return 기존에 등록된 GroupId인지 여부
	 */
	@RequestMapping(value = "/checkDuplMbgroupId.do")
	public @ResponseBody String checkDuplMbgroupId(String mbgroupId) {

		String exist = "N";

		if(mbgroupService.exists(mbgroupId)){
			exist = "Y";
		}
		
		return exist;
	}

}
