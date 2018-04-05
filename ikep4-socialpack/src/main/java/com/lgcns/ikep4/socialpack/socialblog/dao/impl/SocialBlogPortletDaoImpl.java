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
import com.lgcns.ikep4.socialpack.socialblog.dao.SocialBlogPortletDao;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBlogPortlet;

/**
 * 포틀릿_정보 DAO Impl Class
 *
 * @author 이형운
 * @version $Id: SocialBlogPortletDaoImpl.java 16246 2011-08-18 04:48:28Z giljae $
 */
@Repository("socialBlogPortletDao")
public class SocialBlogPortletDaoImpl extends GenericDaoSqlmap<SocialBlogPortlet, String> implements
		SocialBlogPortletDao {

	/**
	 * SOCIAL_BLOG_PORTLET_NAME_SPACE
	 */
	private static final String SOCIAL_BLOG_PORTLET_NAME_SPACE = "socialpack.socialblog.dao.SocialBlogPortlet.";
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public SocialBlogPortlet get(String portletId) {
		return (SocialBlogPortlet)sqlSelectForObject(SOCIAL_BLOG_PORTLET_NAME_SPACE + "get", portletId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable)
	 */
	public boolean exists(String portletId) {
		Integer count = (Integer) sqlSelectForObject(SOCIAL_BLOG_PORTLET_NAME_SPACE + "exists", portletId);
		return count > 0;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public String create(SocialBlogPortlet socialBlogPortlet) {
		sqlInsert(SOCIAL_BLOG_PORTLET_NAME_SPACE + "create", socialBlogPortlet);
		return socialBlogPortlet.getPortletId();
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(SocialBlogPortlet socialBlogPortlet) {
		sqlUpdate(SOCIAL_BLOG_PORTLET_NAME_SPACE + "update", socialBlogPortlet);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.dao.SocialBlogPortletDao#listAllPortlet()
	 */
	public List<SocialBlogPortlet> listAllPortlet(SocialBlogPortlet socialBlogPortlet) {
		return sqlSelectForList(SOCIAL_BLOG_PORTLET_NAME_SPACE + "listAllPortlet", socialBlogPortlet);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.dao.SocialBlogPortletDao#countAllPortlet()
	 */
	public Integer countAllPortlet(SocialBlogPortlet socialBlogPortlet) {
		return (Integer)sqlSelectForObject(SOCIAL_BLOG_PORTLET_NAME_SPACE + "countAllPortlet", socialBlogPortlet);
	}
	
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable)
	 */
	@Deprecated
	public void remove(String id) {
	}

}
