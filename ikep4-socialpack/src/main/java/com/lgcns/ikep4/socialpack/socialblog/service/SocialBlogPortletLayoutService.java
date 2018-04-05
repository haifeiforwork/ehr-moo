/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.socialblog.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBlog;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBlogPortletLayout;

/**
 * 내 블로그에 선택된 Portlet 값을 제공 하는 Service Interface
 *
 * @author 이형운
 * @version $Id: SocialBlogPortletLayoutService.java 16246 2011-08-18 04:48:28Z giljae $
 */
@Transactional
public interface SocialBlogPortletLayoutService extends GenericService<SocialBlogPortletLayout, String> {
	
	/**
	 * 해당 블로그에 속한 Portlet 리스트를 가지고 온다.
	 * @param ownerId 블로그 소유자 ID
	 * @return 해당 블로그에 속한 포틀릿 리스트
	 */
	public List<SocialBlogPortletLayout> listSocialBlogPortletLayoutByBlogId(SocialBlog socialBlog);

	/**
	 * 처음 블로그 방문시 Default Portlet List 를 가지고 기본 Portlet Layout을 저장 한다. 
	 * @param socialBlog 블로그 기본정보 객체
	 */
	public void saveDefaultSocialBlogPortletLayout(SocialBlog socialBlog);
	
	/**
	 * 입력 받은 블로그 Portlet Layout을 저장한다.
	 * @param socialBlog 블로그 기본정보 객체
	 * @param socialBlogPortletLayoutList 블로그 에 속한 포틀릿 레이아웃 정보 객체
	 */
	public void saveSocialBlogPortletLayout(SocialBlog socialBlog, List<SocialBlogPortletLayout> socialBlogPortletLayoutList);
	
}
