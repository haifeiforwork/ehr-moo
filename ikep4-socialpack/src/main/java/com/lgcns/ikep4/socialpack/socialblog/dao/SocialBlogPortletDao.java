/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.socialblog.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBlogPortlet;

/**
 * 블로그에 사용될 포틀릿_정보  DAO Interface
 *
 * @author 이형운
 * @version $Id: SocialBlogPortletDao.java 16246 2011-08-18 04:48:28Z giljae $
 */
public interface SocialBlogPortletDao extends GenericDao<SocialBlogPortlet, String> {
	
	/**
	 * 블로그에 등록된 포틀릿 리스트 전체 반환
	 * @param socialBlogPortlet 블로그 포틀릿 정보
	 * @return 등록된 포틀릿 전체 리스트
	 */
	public List<SocialBlogPortlet> listAllPortlet(SocialBlogPortlet socialBlogPortlet);
	
	/**
	 * 등록된 포틀릿 리스트 건수 반환
	 * @param socialBlogPortlet 블로그 포틀릿 정보
	 * @return 등록된 포틀릿 전체 수
	 */
	public Integer countAllPortlet(SocialBlogPortlet socialBlogPortlet);


}
