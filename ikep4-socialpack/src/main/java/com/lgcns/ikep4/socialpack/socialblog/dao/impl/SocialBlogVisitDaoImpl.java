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
import com.lgcns.ikep4.socialpack.socialblog.dao.SocialBlogVisitDao;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBlogVisit;

/**
 * 소셜 블로그 방문자 정보용 DAO Impl
 *
 * @author 이형운
 * @version $Id: SocialBlogVisitDaoImpl.java 16246 2011-08-18 04:48:28Z giljae $
 */
@Repository("socialBlogVisitDao")
public class SocialBlogVisitDaoImpl extends GenericDaoSqlmap<SocialBlogVisit, String> implements SocialBlogVisitDao {

	/**
	 * SOCIAL_BLOG_VISIT_NAME_SPACE
	 */
	private static final String SOCIAL_BLOG_VISIT_NAME_SPACE = "socialpack.socialblog.dao.SocialBlogVisit.";
	
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.dao.SocialBlogVisit#selectAllByblogId(java.lang.Object)
	 */
	public List<SocialBlogVisit> selectAllByblogId(SocialBlogVisit socialBlogVisit) {
		return sqlSelectForList(SOCIAL_BLOG_VISIT_NAME_SPACE + "selectAllByblogId", socialBlogVisit);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.dao.SocialBlogVisit#selectAllCountByblogId(java.lang.Object)
	 */
	public Integer selectAllCountByblogId(SocialBlogVisit socialBlogVisit) {
		return (Integer) sqlSelectForObject(SOCIAL_BLOG_VISIT_NAME_SPACE + "selectAllCountByblogId", socialBlogVisit);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public String create(SocialBlogVisit socialBlogVisit) {
		return (String) sqlInsert(SOCIAL_BLOG_VISIT_NAME_SPACE + "create", socialBlogVisit);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.dao.SocialBlogVisit#selectAllCountByblogId(java.lang.Object)
	 */
	public void physicalDelete(SocialBlogVisit socialBlogVisit) {
		sqlDelete(SOCIAL_BLOG_VISIT_NAME_SPACE + "physicalDelete", socialBlogVisit);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.dao.SocialBlogVisit#selectAllCountByblogId(java.lang.Object)
	 */
	public void physicalDeleteThreadByBlogId(SocialBlogVisit socialBlogVisit) {
		sqlDelete(SOCIAL_BLOG_VISIT_NAME_SPACE + "physicalDeleteThreadByBlogId", socialBlogVisit);
	}

	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public SocialBlogVisit get(SocialBlogVisit socialBlogVisit) {
		return (SocialBlogVisit) sqlSelectForObject(SOCIAL_BLOG_VISIT_NAME_SPACE + "get", socialBlogVisit);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.dao.SocialBlogVisit#getVisitorManage(java.lang.Object)
	 */
	public List<SocialBlogVisit> getVisitorManage(SocialBlogVisit socialBlogVisit) {
		return sqlSelectForList(SOCIAL_BLOG_VISIT_NAME_SPACE + "getVisitorManage", socialBlogVisit);
	}

	/* (non-Javadoc)o
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
	public void update(SocialBlogVisit object) {
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable)
	 */
	@Deprecated
	public void remove(String id) {
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	@Deprecated
	public SocialBlogVisit get(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
