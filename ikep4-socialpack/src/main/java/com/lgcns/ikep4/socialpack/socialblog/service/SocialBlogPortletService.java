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
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBlogPortlet;

/**
 * 블로그에 등록된 포틀릿 정보를 제공하는 Service Interface
 *
 * @author 이형운
 * @version $Id: SocialBlogPortletService.java 16246 2011-08-18 04:48:28Z giljae $
 */
@Transactional
public interface SocialBlogPortletService extends GenericService<SocialBlogPortlet, String> {
	
	/**
	 * 블로그에 등록된 포틀릿의 리스트를 가지고 온다.
	 * @return 등록된 포틀릿 리스트
	 */
	public List<SocialBlogPortlet> listAllSocialBlogPortlet();
	
	/**
	 * 블로그에 등록된 포틀릿 중에 디폴트 값으로 설정된 포틀릿의 리스트를 가지고 온다.
	 * 처음 블로그 생성 될때 입력될 포틀릿 종류를 결정
	 * @return 디폴트 포틀릿 리스트
	 */
	public List<SocialBlogPortlet> listDefaultSocialBlogPortlet();
	

}
