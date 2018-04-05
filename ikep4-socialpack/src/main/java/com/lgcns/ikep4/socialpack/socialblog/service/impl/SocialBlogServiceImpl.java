/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.socialblog.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.service.UserService;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.socialpack.socialblog.dao.SocialBlogDao;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBlog;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBlogLayout;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBlogPortlet;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBlogPortletLayout;
import com.lgcns.ikep4.socialpack.socialblog.service.SocialBlogLayoutService;
import com.lgcns.ikep4.socialpack.socialblog.service.SocialBlogPortletLayoutService;
import com.lgcns.ikep4.socialpack.socialblog.service.SocialBlogPortletService;
import com.lgcns.ikep4.socialpack.socialblog.service.SocialBlogService;
import com.lgcns.ikep4.util.idgen.service.IdgenService;

/**
 * 소셜블로그 기본 정보용 Service Implement Class
 *
 * @author 이형운
 * @version $Id: SocialBlogServiceImpl.java 16246 2011-08-18 04:48:28Z giljae $
 */
@Service("socialBlogService")
public class SocialBlogServiceImpl extends GenericServiceImpl<SocialBlog, String> implements SocialBlogService {

	/**
	 * 블로그 기본 정보 컨트롤용 Dao
	 */
	@Autowired
	private SocialBlogDao socialBlogDao;
	
	/**
	 * 블로그 포틀릿 컨트롤용 Service
	 */
	@Autowired
	private SocialBlogPortletService socialBlogPortletService;
	
	/**
	 * 블로그 레이아웃 컨트롤용 Service
	 */
	@Autowired
	private SocialBlogLayoutService socialBlogLayoutService;
	
	/**
	 * 블로그 포틀릿 레이아웃 컨트롤용 Service
	 */
	@Autowired
	private SocialBlogPortletLayoutService socialBlogPortletLayoutService;
	
	/**
	 * 키값 생성을 위한 컨트롤용 Service
	 */
	@Autowired
	private IdgenService idgenService;
	
	/**
	 * 파일 처리를 위한 컨트롤용 Service
	 */
	@Autowired
	private FileService fileService;
	
	/**
	 * 사용자 정보 컨트롤용 Service
	 */
	@Autowired
	private UserService userService;
	
	/**
	 * Message Source 
	 */
	@Autowired
	protected MessageSource messageSource = null;
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.service.SocialBlogService#select(com.lgcns.ikep4.socialpack.socialblog.model.SocialBlog,java.lang.String)
	 */
	public SocialBlog select(SocialBlog socialBlog, String sessLocaleCode) {
		
		User blogOwner = userService.read(socialBlog.getOwnerId());
		socialBlog.setOwner( blogOwner );
		
		// 최초 방문시 
        if(!(socialBlogDao.exists(socialBlog))){
        	
        	SocialBlogLayout defaultSocialBlogLayout =  socialBlogLayoutService.defaultSocialBlogLayout();

        	// 기본 Blog 정보 생성
        	SocialBlog createSocialBlog = new SocialBlog();
        	String createBlogId = idgenService.getNextId();
        	createSocialBlog.setBlogId(createBlogId);
        	String sUserNameByLocale = "";
        	if( socialBlog.getOwner().getLocaleCode().equals(sessLocaleCode) ){
        		sUserNameByLocale = socialBlog.getOwner().getUserName();
        	}else{
        		sUserNameByLocale = socialBlog.getOwner().getUserEnglishName();
        	}
        	String[] args = { sUserNameByLocale };
        	createSocialBlog.setIntroduction( messageSource.getMessage("message.socialpack.socialblog.profile.introduction", args, Locale.getDefault()) );
        	createSocialBlog.setOwnerId(socialBlog.getOwnerId());
        	createSocialBlog.setImageFileId("");
        	createSocialBlog.setImageUrl("");
        	createSocialBlog.setIsPrivateImage(0);
        	createSocialBlog.setLayoutId(defaultSocialBlogLayout.getLayoutId());
        	socialBlogDao.create(createSocialBlog);
        	
        	List<SocialBlogPortlet> defaultPortletList = socialBlogPortletService.listDefaultSocialBlogPortlet();

        	List<SocialBlogPortletLayout> socialBlogPortletLayoutList  = new ArrayList<SocialBlogPortletLayout>();
        	for(SocialBlogPortlet socialBlogPortlet : defaultPortletList ){
				SocialBlogPortletLayout socialBlogPortletLayout = new SocialBlogPortletLayout();
				socialBlogPortletLayout.setPortletId(socialBlogPortlet.getPortletId());
				socialBlogPortletLayout.setBlogId(createBlogId);
				socialBlogPortletLayout.setColIndex(1);
				socialBlogPortletLayout.setRowIndex(socialBlogPortlet.getSortOrder());
				socialBlogPortletLayoutList.add(socialBlogPortletLayout);
			}

			socialBlog.setLayoutId(defaultSocialBlogLayout.getLayoutId());
			socialBlogPortletLayoutService.saveSocialBlogPortletLayout(createSocialBlog, socialBlogPortletLayoutList);
        	
        }
        
        SocialBlog selectSocialBlog = socialBlogDao.select(socialBlog);
        selectSocialBlog.setOwner( blogOwner );
        
		return selectSocialBlog;
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.service.SocialBlogService#exists(com.lgcns.ikep4.socialpack.socialblog.model.SocialBlog)
	 */
	@Deprecated
	public boolean exists(SocialBlog socialBlog) {
		return socialBlogDao.exists(socialBlog);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#create(java.lang.Object)
	 */
	@Deprecated
	public String create(SocialBlog socialBlog) {
		return socialBlogDao.create(socialBlog);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.service.SocialBlogService#updateBlogBgImage(com.lgcns.ikep4.socialpack.socialblog.model.SocialBlog,java.util.List<String>,com.lgcns.ikep4.support.user.member.model.User)
	 */
	public void updateBlogBgImage(SocialBlog socialBlog, List<String> fileIdList, User sessionuser) {
		
		if( fileIdList != null && fileIdList.size() > 0 ){
			fileService.createFileLink(fileIdList, sessionuser.getUserId(), IKepConstant.ITEM_TYPE_CODE_SOCIAL_BLOG, sessionuser);
		}
		socialBlogDao.updateBlogBgImage(socialBlog);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.service.SocialBlogService#updateIntroduction(com.lgcns.ikep4.socialpack.socialblog.model.SocialBlog)
	 */
	public void updateIntroduction(SocialBlog socialBlog) {
		socialBlogDao.updateIntroduction(socialBlog);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.service.SocialBlogService#updateLayoutId(com.lgcns.ikep4.socialpack.socialblog.model.SocialBlog)
	 */
	public void updateLayoutId(SocialBlog socialBlog) {
		socialBlogDao.updateLayoutId(socialBlog);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#update(java.lang.Object)
	 */
	@Deprecated
	public void update(SocialBlog object) {
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#delete(java.io.Serializable)
	 */
	@Deprecated
	public void delete(String id) {
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#read(java.io.Serializable)
	 */
	@Deprecated
	public SocialBlog read(String blogId) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#exists(java.io.Serializable)
	 */
	@Deprecated
	public boolean exists(String blogId) {
		return false;
	}
}
