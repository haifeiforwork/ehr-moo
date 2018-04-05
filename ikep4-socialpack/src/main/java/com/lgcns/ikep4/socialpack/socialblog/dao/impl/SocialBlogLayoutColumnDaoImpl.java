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
import com.lgcns.ikep4.socialpack.socialblog.dao.SocialBlogLayoutColumnDao;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBlogLayoutColumn;

/**
 * 레이아웃 컬럼 정보 DAO Impl Class
 *
 * @author 이형운
 * @version $Id: SocialBlogLayoutColumnDaoImpl.java 16246 2011-08-18 04:48:28Z giljae $
 */
@Repository("socialBlogLayoutColumnDao")
public class SocialBlogLayoutColumnDaoImpl extends GenericDaoSqlmap<SocialBlogLayoutColumn, String> implements
		SocialBlogLayoutColumnDao {

	/**
	 * SOCIAL_BLOG_LAYOUT_COLUMN_NAME_SPACE
	 */
	private static final String SOCIAL_BLOG_LAYOUT_COLUMN_NAME_SPACE = "socialpack.socialblog.dao.SocialBlogLayoutColumn.";
	
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.dao.SocialBlogLayoutColumnDao#get(com.lgcns.ikep4.socialpack.socialblog.model.SocialBlogLayoutColumn)
	 */
	public SocialBlogLayoutColumn get(SocialBlogLayoutColumn socialBlogLayoutColumn) {
		return (SocialBlogLayoutColumn)sqlSelectForObject(SOCIAL_BLOG_LAYOUT_COLUMN_NAME_SPACE + "get", socialBlogLayoutColumn);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.dao.SocialBlogLayoutColumnDao#exists(com.lgcns.ikep4.socialpack.socialblog.model.SocialBlogLayoutColumn)
	 */
	public boolean exists(SocialBlogLayoutColumn SocialBlogLayoutColumn) {
		Integer count = (Integer) sqlSelectForObject(SOCIAL_BLOG_LAYOUT_COLUMN_NAME_SPACE + "exists", SocialBlogLayoutColumn);
		return count > 0;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public String create(SocialBlogLayoutColumn socialBlogLayoutColumn) {
		sqlInsert(SOCIAL_BLOG_LAYOUT_COLUMN_NAME_SPACE + "create", socialBlogLayoutColumn);
		return socialBlogLayoutColumn.getLayoutId();
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(SocialBlogLayoutColumn socialBlogLayoutColumn) {
		sqlUpdate(SOCIAL_BLOG_LAYOUT_COLUMN_NAME_SPACE + "update", socialBlogLayoutColumn);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.dao.SocialBlogLayoutColumnDao#listByLayoutId(java.lang.String)
	 */
	public List<SocialBlogLayoutColumn> listByLayoutId(String layoutId) {
		return sqlSelectForList(SOCIAL_BLOG_LAYOUT_COLUMN_NAME_SPACE + "listByLayoutId", layoutId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.dao.SocialBlogLayoutColumnDao#countByLayoutId(java.lang.String)
	 */
	public Integer countByLayoutId(String layoutId) {
		return (Integer)sqlSelectForObject(SOCIAL_BLOG_LAYOUT_COLUMN_NAME_SPACE + "countByLayoutId", layoutId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.dao.SocialBlogLayoutColumnDao#getFixedLayoutColumn(java.lang.String)
	 */
	public Integer getFixedLayoutColumn(String layoutId) {
		return (Integer)sqlSelectForObject(SOCIAL_BLOG_LAYOUT_COLUMN_NAME_SPACE + "getFixedLayoutColumn", layoutId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.dao.SocialBlogLayoutColumnDao#physicalDelete(com.lgcns.ikep4.socialpack.socialblog.model.SocialBlogLayoutColumn)
	 */
	public void physicalDelete(SocialBlogLayoutColumn SocialBlogLayoutColumn) {
		sqlDelete(SOCIAL_BLOG_LAYOUT_COLUMN_NAME_SPACE + "physicalDelete", SocialBlogLayoutColumn);
	}
	
	
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	@Deprecated
	public SocialBlogLayoutColumn get(String id) {
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
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable)
	 */
	@Deprecated
	public void remove(String id) {
	}

}
