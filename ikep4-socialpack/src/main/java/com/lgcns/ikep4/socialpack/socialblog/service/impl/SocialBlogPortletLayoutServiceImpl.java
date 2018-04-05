/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.socialblog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.socialpack.socialblog.dao.SocialBlogPortletLayoutDao;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBlog;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBlogPortlet;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBlogPortletLayout;
import com.lgcns.ikep4.socialpack.socialblog.service.SocialBlogPortletLayoutService;
import com.lgcns.ikep4.socialpack.socialblog.service.SocialBlogPortletService;
import com.lgcns.ikep4.socialpack.socialblog.service.SocialBlogService;
import com.lgcns.ikep4.util.idgen.service.IdgenService;

/**
 * 내 블로그에 선택된 Portlet 값을 제공 하는 Service Implement Class
 *
 * @author 이형운
 * @version $Id: SocialBlogPortletLayoutServiceImpl.java 16246 2011-08-18 04:48:28Z giljae $
 */
@Service("socialBlogPortletLayoutService")
public class SocialBlogPortletLayoutServiceImpl extends GenericServiceImpl<SocialBlogPortletLayout, String> implements
		SocialBlogPortletLayoutService {

	/**
	 * 블로그 포틀릿 레이아웃 컨트롤용 Dao
	 */
	@Autowired
	public SocialBlogPortletLayoutDao socialBlogPortletLayoutDao;
	
	/**
	 * 블로그 포틀릿 컨트롤용 Service
	 */
	@Autowired
	public SocialBlogPortletService socialBlogPortletService;
	
	/**
	 * 블로그 기본 정보 컨트롤용 Service
	 */
	@Autowired
	public SocialBlogService socialBlogService;
	
	/** 
	 * 키값 생성 컨트롤용 Service
	 */
	@Autowired
	private IdgenService idgenService;
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.service.SocialBlogPortletLayoutService#listSocialBlogPortletLayoutByBlogId(com.lgcns.ikep4.socialpack.socialblog.model.SocialBlog)
	 */
	public List<SocialBlogPortletLayout> listSocialBlogPortletLayoutByBlogId(SocialBlog socialBlog) {
		
		List<SocialBlogPortletLayout> socialBlogPortletLayoutList = socialBlogPortletLayoutDao.listByBlogId(socialBlog.getBlogId());
		
		for( SocialBlogPortletLayout socialBlogPortletLayout : socialBlogPortletLayoutList){
			socialBlogPortletLayout.setSocialBlogPortlet(socialBlogPortletService.read(socialBlogPortletLayout.getPortletId()));
		}

		return socialBlogPortletLayoutList;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.service.SocialBlogPortletLayoutService#saveDefaultSocialBlogPortletLayout(com.lgcns.ikep4.socialpack.socialblog.model.SocialBlog)
	 */
	public void saveDefaultSocialBlogPortletLayout(SocialBlog socialBlog) {
		
		List<SocialBlogPortlet> socialBlogPortletList = socialBlogPortletService.listDefaultSocialBlogPortlet();
		int i = 1;
		for(SocialBlogPortlet socialBlogPortlet : socialBlogPortletList) {
			
			SocialBlogPortletLayout socialBlogPortletLayout = new SocialBlogPortletLayout();
			socialBlogPortletLayout.setPortletLayoutId(idgenService.getNextId());
			socialBlogPortletLayout.setBlogId(socialBlog.getBlogId());
			socialBlogPortletLayout.setPortletId(socialBlogPortlet.getPortletId());
			socialBlogPortletLayout.setColIndex(1);
			socialBlogPortletLayout.setRowIndex(i);
			
			this.create(socialBlogPortletLayout);
			i++;
		}
		
	}

	/* (non-Javadoc) 
	 * @see com.lgcns.ikep4.socialpack.socialblog.service.SocialBlogPortletLayoutService#saveSocialBlogPortletLayout(com.lgcns.ikep4.socialpack.socialblog.model.SocialBlog,java.util.List<SocialBlogPortletLayout>)
	 */
	public void saveSocialBlogPortletLayout(SocialBlog socialBlog, List<SocialBlogPortletLayout> socialBlogPortletLayoutList) {
		
		//기존 데이타 삭제
		socialBlogPortletLayoutDao.physicalDeleteThreadByBlogId(socialBlog.getBlogId());
		
		//신규 데이타입력
		for(SocialBlogPortletLayout socialBlogPortletLayout : socialBlogPortletLayoutList) {
			
			socialBlogPortletLayout.setPortletLayoutId(idgenService.getNextId());
			socialBlogPortletLayout.setBlogId(socialBlog.getBlogId());			
			this.create(socialBlogPortletLayout);
		}
		socialBlogService.updateLayoutId(socialBlog);
		
	}
	
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#read(java.lang.String)
	 */
	public SocialBlogPortletLayout read(String portletLayoutId) {
		return socialBlogPortletLayoutDao.get(portletLayoutId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#exists(java.lang.String)
	 */
	public boolean exists(String portletLayoutId) {
		return socialBlogPortletLayoutDao.exists(portletLayoutId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#create(com.lgcns.ikep4.socialpack.socialblog.model.SocialBlogPortletLayout)
	 */
	public String create(SocialBlogPortletLayout socialBlogPortletLayout) {
		return socialBlogPortletLayoutDao.create(socialBlogPortletLayout);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#update(com.lgcns.ikep4.socialpack.socialblog.model.SocialBlogPortletLayout)
	 */
	public void update(SocialBlogPortletLayout socialBlogPortletLayout) {
		socialBlogPortletLayoutDao.update(socialBlogPortletLayout);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#delete(java.lang.String)
	 */
	@Deprecated
	public void delete(String portletLayoutId) {
	}



}
