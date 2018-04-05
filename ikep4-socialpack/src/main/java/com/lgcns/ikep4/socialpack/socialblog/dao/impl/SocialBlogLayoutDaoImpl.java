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
import com.lgcns.ikep4.socialpack.socialblog.dao.SocialBlogLayoutDao;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBlogLayout;

/**
 * 포틀릿_레이아웃 DAO Impl Class
 *
 * @author 이형운
 * @version $Id: SocialBlogLayoutDaoImpl.java 16246 2011-08-18 04:48:28Z giljae $
 */
@Repository("socialBlogLayoutDao")
public class SocialBlogLayoutDaoImpl extends GenericDaoSqlmap<SocialBlogLayout, String> implements SocialBlogLayoutDao {

	/**
	 * SOCIAL_BLOG_LAYOUT_NAME_SPACE
	 */
	private static final String SOCIAL_BLOG_LAYOUT_NAME_SPACE = "socialpack.socialblog.dao.SocialBlogLayout.";
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public SocialBlogLayout get(String layoutId) {
		return (SocialBlogLayout)sqlSelectForObject(SOCIAL_BLOG_LAYOUT_NAME_SPACE + "get", layoutId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable)
	 */
	public boolean exists(String layoutId) {
		Integer count = (Integer) sqlSelectForObject(SOCIAL_BLOG_LAYOUT_NAME_SPACE + "exists", layoutId);
		return count > 0;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public String create(SocialBlogLayout socialBlogLayout) {
		sqlInsert(SOCIAL_BLOG_LAYOUT_NAME_SPACE + "create", socialBlogLayout);
		return socialBlogLayout.getLayoutId();
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(SocialBlogLayout socialBlogLayout) {
		sqlUpdate(SOCIAL_BLOG_LAYOUT_NAME_SPACE + "update", socialBlogLayout);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#physicalDelete(java.io.Serializable)
	 */
	public void physicalDelete(String layoutId) {
		sqlDelete(SOCIAL_BLOG_LAYOUT_NAME_SPACE + "physicalDelete", layoutId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.dao.SocialBlogLayoutDao#listSocialBlogLayout()
	 */
	public List<SocialBlogLayout> listSocialBlogLayout() {
		return sqlSelectForList(SOCIAL_BLOG_LAYOUT_NAME_SPACE + "listSocialBlogLayout");
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.dao.SocialBlogLayoutDao#countSocialBlogLayout()
	 */
	public Integer countSocialBlogLayout() {
		return (Integer)sqlSelectForObject(SOCIAL_BLOG_LAYOUT_NAME_SPACE + "countSocialBlogLayout");
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.dao.SocialBlogLayoutDao#getDefaultLayout()
	 */
	public SocialBlogLayout getDefaultLayout() {
		return (SocialBlogLayout)sqlSelectForObject(SOCIAL_BLOG_LAYOUT_NAME_SPACE + "getDefaultLayout");
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.dao.SocialBlogLayoutDao#isDefaultLayout(java.lang.String)
	 */
	public boolean isDefaultLayout(String layoutId) {
		Integer count = (Integer)sqlSelectForObject(SOCIAL_BLOG_LAYOUT_NAME_SPACE + "isDefaultLayout");
		return count > 0;
	}


	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable)
	 */
	@Deprecated
	public void remove(String id) {
	}

}
