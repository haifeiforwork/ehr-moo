/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.socialblog.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.socialpack.socialblog.dao.SocialBlogPortletLayoutDao;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBlogPortletLayout;

/**
 * 포틀릿_배치_정보  DAO Impl Class
 *
 * @author 이형운
 * @version $Id: SocialBlogPortletLayoutDaoImpl.java 16246 2011-08-18 04:48:28Z giljae $
 */
@Repository("socialBlogPortletLayoutDao")
public class SocialBlogPortletLayoutDaoImpl extends GenericDaoSqlmap<SocialBlogPortletLayout, String> implements
		SocialBlogPortletLayoutDao {

	/**
	 * SOCIAL_BLOG_PORTLET_LAYOUT_NAME_SPACE
	 */
	private static final String SOCIAL_BLOG_PORTLET_LAYOUT_NAME_SPACE = "socialpack.socialblog.dao.SocialBlogPortletLayout.";
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public SocialBlogPortletLayout get(String portletLayoutId) {
		return (SocialBlogPortletLayout)sqlSelectForObject(SOCIAL_BLOG_PORTLET_LAYOUT_NAME_SPACE + "get", portletLayoutId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable)
	 */
	public boolean exists(String portletLayoutId) {
		Integer count = (Integer) sqlSelectForObject(SOCIAL_BLOG_PORTLET_LAYOUT_NAME_SPACE + "exists", portletLayoutId);
		return count > 0;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public String create(SocialBlogPortletLayout socialBlogPortletLayout) {
		sqlInsert(SOCIAL_BLOG_PORTLET_LAYOUT_NAME_SPACE + "create", socialBlogPortletLayout);
		return socialBlogPortletLayout.getPortletLayoutId();
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(SocialBlogPortletLayout socialBlogPortletLayout) {
		sqlUpdate(SOCIAL_BLOG_PORTLET_LAYOUT_NAME_SPACE + "update", socialBlogPortletLayout);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.dao.SocialBlogPortletLayoutDao#listByBlogId(java.lang.String)
	 */
	public List<SocialBlogPortletLayout> listByBlogId(String blogId) {
		return sqlSelectForList(SOCIAL_BLOG_PORTLET_LAYOUT_NAME_SPACE + "listByBlogId", blogId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.dao.SocialBlogPortletLayoutDao#countByBlogId(java.lang.String)
	 */
	public Integer countByBlogId(String blogId) {
		return (Integer)sqlSelectForObject(SOCIAL_BLOG_PORTLET_LAYOUT_NAME_SPACE + "countByBlogId", blogId);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.dao.SocialBlogPortletLayoutDao#physicalDelete(java.lang.String)
	 */
	public void physicalDelete(String portletLayoutId) {
		sqlDelete(SOCIAL_BLOG_PORTLET_LAYOUT_NAME_SPACE + "physicalDelete", portletLayoutId); 
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.dao.SocialBlogPortletLayoutDao#physicalDeleteThreadByBlogId(java.lang.String)
	 */
	public void physicalDeleteThreadByBlogId(String blogId) {
		sqlDelete(SOCIAL_BLOG_PORTLET_LAYOUT_NAME_SPACE + "physicalDeleteThreadByBlogId", blogId); 
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable)
	 */
	@Deprecated
	public void remove(String id) {
	}

}
