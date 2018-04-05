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
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 소셜블로그 기본 정보용 Service Interface
 *
 * @author 이형운
 * @version $Id: SocialBlogService.java 16246 2011-08-18 04:48:28Z giljae $
 */
@Transactional
public interface SocialBlogService extends GenericService<SocialBlog, String> {


	/**
	 * 해당 블로그에 대한 정보를 가지고 온다.
	 * @param socialBlog 블로그 기본정보 객체
	 * @param sessLocaleCode 요청 사용자 Locale Code 값
	 * @return 블로그 기본정보 객체
	 */
	public SocialBlog select(SocialBlog socialBlog, String sessLocaleCode);
	
	/**
	 * 해당 블로그의 배경 이미지 관련 정보를 업데이트 한다.
	 * @param socialBlog 블로그 기본정보 객체
	 * @param fileIdList 업로드할 배경이미지 파일 리스트
	 * @param sessionuser 등록한 세션의 User 정보 객체
	 */
	public void updateBlogBgImage(SocialBlog socialBlog, List<String> fileIdList, User sessionuser);
	
	/**
	 * 해당 블로그의 소개 글을 관련 정보를 업데이트 한다.
	 * @param socialBlog 블로그 기본정보 객체
	 */
	public void updateIntroduction(SocialBlog socialBlog);
	
	/**
	 * 해당 블로그의 Layout 정보를 업데이트 한다.
	 * @param socialBlog 블로그 기본정보 객체
	 */
	public void updateLayoutId(SocialBlog socialBlog);
	
	/**
	 * 해당 블로그 개설 유무를 확인한다.
	 * @param socialBlog
	 */
	public boolean exists(SocialBlog socialBlog);
	
}
