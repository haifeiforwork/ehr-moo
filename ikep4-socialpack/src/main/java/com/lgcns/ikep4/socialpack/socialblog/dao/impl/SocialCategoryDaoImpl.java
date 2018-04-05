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
import com.lgcns.ikep4.socialpack.socialblog.dao.SocialCategoryDao;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialCategory;

/**
 * 소셜블로그 게시글 카테고리 정보 DAO Impl
 *
 * @author 이형운
 * @version $Id: SocialCategoryDaoImpl.java 16246 2011-08-18 04:48:28Z giljae $
 */
@Repository("socialCategoryDao")
public class SocialCategoryDaoImpl extends GenericDaoSqlmap<SocialCategory, String> implements SocialCategoryDao {

	/**
	 * SOCIAL_CATEGORY_NAME_SPACE
	 */
	private static final String SOCIAL_CATEGORY_NAME_SPACE = "socialpack.socialblog.dao.SocialCategory.";
	
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.dao.SocialCategoryDao#listByPortalId(java.lang.String)
	 */
	public List<SocialCategory> listByBlogId(String categoryId) {
		return sqlSelectForList(SOCIAL_CATEGORY_NAME_SPACE + "listByBlogId", categoryId);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.dao.SocialCategoryDao#countBlogId(java.lang.String)
	 */
	public Integer countBlogId(String blogId) {
		return (Integer)sqlSelectForObject(SOCIAL_CATEGORY_NAME_SPACE + "countBlogId", blogId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.dao.SocialCategoryDao#getMaxSortOrder(java.lang.String)
	 */
	public Integer getMaxSortOrder(String blogId) {
		return (Integer)sqlSelectForObject(SOCIAL_CATEGORY_NAME_SPACE + "getMaxSortOrder", blogId);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public SocialCategory get(String categoryId) {
		return (SocialCategory) sqlSelectForObject(SOCIAL_CATEGORY_NAME_SPACE + "get", categoryId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable)
	 */
	public boolean exists(String categoryId) {
		Integer count = (Integer) sqlSelectForObject(SOCIAL_CATEGORY_NAME_SPACE + "exists", categoryId);
		return count > 0;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public String create(SocialCategory socialCategory) {
		sqlInsert(SOCIAL_CATEGORY_NAME_SPACE + "create", socialCategory);
		return socialCategory.getCategoryId();
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(SocialCategory socialCategory) {
		sqlUpdate(SOCIAL_CATEGORY_NAME_SPACE + "update", socialCategory);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.dao.SocialCategoryDao#physicalDelete(java.lang.String)
	 */
	public void physicalDelete(String categoryId) {
		sqlDelete(SOCIAL_CATEGORY_NAME_SPACE + "physicalDelete", categoryId);
	}
	
	
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable)
	 */
	@Deprecated
	public void remove(String id) {
	}

}
