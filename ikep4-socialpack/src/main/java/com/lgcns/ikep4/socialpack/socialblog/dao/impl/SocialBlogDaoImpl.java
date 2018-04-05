/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.socialblog.dao.impl;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.socialpack.socialblog.dao.SocialBlogDao;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBlog;

/**
 * 소셜블로그 기본 정보용 DAO Impl
 *
 * @author 이형운
 * @version $Id: SocialBlogDaoImpl.java 16246 2011-08-18 04:48:28Z giljae $
 */
@Repository("socialBlogDao")
public class SocialBlogDaoImpl extends GenericDaoSqlmap<SocialBlog, String> implements SocialBlogDao {

	/**
	 * SOCIAL_BLOG_NAME_SPACE
	 */
	private static final String SOCIAL_BLOG_NAME_SPACE = "socialpack.socialblog.dao.SocialBlog.";
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.dao.SocialBlogDao#select(java.lang.Object)
	 */
	public SocialBlog select(SocialBlog socialBlog) {
		return (SocialBlog) sqlSelectForObject(SOCIAL_BLOG_NAME_SPACE + "select", socialBlog);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.dao.SocialBlogDao#exists(java.lang.Object)
	 */
	public boolean exists(SocialBlog socialBlog) {
		Integer count = (Integer) sqlSelectForObject(SOCIAL_BLOG_NAME_SPACE + "exists", socialBlog);
		return count > 0;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public String create(SocialBlog socialBlog) {
		return (String) sqlInsert(SOCIAL_BLOG_NAME_SPACE + "create", socialBlog);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.dao.SocialBlogDao#updateBlogBgImage(java.lang.Object)
	 */
	public void updateBlogBgImage(SocialBlog socialBlog) {
		sqlUpdate(SOCIAL_BLOG_NAME_SPACE + "updateBlogBgImage", socialBlog);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.dao.SocialBlogDao#updateIntroduction(java.lang.Object)
	 */
	public void updateIntroduction(SocialBlog socialBlog) {
		sqlUpdate(SOCIAL_BLOG_NAME_SPACE + "updateIntroduction", socialBlog);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.dao.SocialBlogDao#updateLayoutId(java.lang.Object)
	 */
	public void updateLayoutId(SocialBlog socialBlog) {
		sqlUpdate(SOCIAL_BLOG_NAME_SPACE + "updateLayoutId", socialBlog);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	@Deprecated
	public SocialBlog get(String id) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable)
	 */
	@Deprecated
	public boolean exists(String id) {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	@Deprecated
	public void update(SocialBlog object) {
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable)
	 */
	@Deprecated
	public void remove(String id) {
	}

}
