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
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBlogPortletLayout;

/**
 * 블로그에서 사용하는 포틀릿_배치_정보  DAO Interface
 *
 * @author 이형운
 * @version $Id: SocialBlogPortletLayoutDao.java 16246 2011-08-18 04:48:28Z giljae $
 */
public interface SocialBlogPortletLayoutDao extends GenericDao<SocialBlogPortletLayout, String> {
	
	/**
	 * 블로그에 설정한 기본 포틀릿 레이아웃  전체 리스트 조회
	 * @param blogId 블로그 ID
	 * @return 블로그 포틀릿 레이아웃 리스트
	 */
	public List<SocialBlogPortletLayout> listByBlogId(String blogId);
	
	/**
	 *  블로그에 설정한 기본 포틀릿 레이아웃  전체 건수 조회
	 * @param blogId 블로그 ID
	 * @return 블로그 포틀릿 레이아웃 건수
	 */
	public Integer countByBlogId(String blogId);
	
	/**
	 * 해당 블로그의 포틀릿 레이아웃 ID 에 해당 되는 값 삭제 
	 * @param portletLayoutId 삭제하고자 하는 포틀릿 레이아웃 ID
	 */
	public void physicalDelete(String portletLayoutId);
	
	/**
	 * 해당 블로그의 전체 포틀릿 레이아웃을 비운다.
	 * @param blogId 블로그 ID
	 */
	public void physicalDeleteThreadByBlogId(String blogId);
	

}
