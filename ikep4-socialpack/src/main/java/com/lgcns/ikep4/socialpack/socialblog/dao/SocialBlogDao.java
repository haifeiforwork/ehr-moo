/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.socialblog.dao;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBlog;

/**
 * 소셜블로그 기본 정보용 DAO Interface
 *
 * @author 이형운
 * @version $Id: SocialBlogDao.java 16246 2011-08-18 04:48:28Z giljae $
 */
public interface SocialBlogDao extends GenericDao<SocialBlog, String> {
	
	/**
	 * 해당 블로그에 대한 정보를 가지고 온다.
	 * @return 블로그 정보
	 */
	public SocialBlog select(SocialBlog socialBlog);
	
	/**
	 * 해당 블로그의 배경 이미지 관련 정보를 업데이트 한다.
	 * @param socialBlog 블로그 정보
	 */
	public void updateBlogBgImage(SocialBlog socialBlog);
	
	/**
	 * 해당 블로그의 소개 글을 관련 정보를 업데이트 한다.
	 * @param socialBlog 블로그 정보
	 */
	public void updateIntroduction(SocialBlog socialBlog);
	
	/**
	 * 해당 블로그의 레이아웃을 변경 시켜 준다.
	 * @param socialBlog 블로그 정보
	 */
	public void updateLayoutId(SocialBlog socialBlog);
	
	/**
	 * 해당 블로그 개설 유무를 확인한다.
	 * @param socialBlog 블로그 정보
	 */
	public boolean exists(SocialBlog socialBlog);
	

}
