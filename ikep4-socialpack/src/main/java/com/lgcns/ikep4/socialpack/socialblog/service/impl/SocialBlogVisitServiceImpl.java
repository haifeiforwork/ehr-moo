/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.socialblog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.socialpack.socialblog.dao.SocialBlogVisitDao;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBlogVisit;
import com.lgcns.ikep4.socialpack.socialblog.service.SocialBlogVisitService;
import com.lgcns.ikep4.support.cache.service.CacheService;

/**
 * 소셜 블로그 방문자 정보용 Service Implement Class
 *
 * @author 이형운
 * @version $Id: SocialBlogVisitServiceImpl.java 19512 2012-06-26 09:36:14Z malboru80 $
 */
@Service("socialBlogVisitService")
public class SocialBlogVisitServiceImpl extends GenericServiceImpl<SocialBlogVisit, String> implements
		SocialBlogVisitService {

	/**
	 * 블로그 방문자 정보 컨트롤용 Dao
	 */
	@Autowired
	private SocialBlogVisitDao socialBlogVisitDao;
	
	@Autowired
	private CacheService cacheService;
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.service.SocialBlogVisitService#selectAllByblogId(com.lgcns.ikep4.socialpack.socialblog.model.SocialBlogVisit)
	 */
	public List<SocialBlogVisit> selectAllByblogId(SocialBlogVisit socialBlogVisit) {
		return socialBlogVisitDao.selectAllByblogId(socialBlogVisit);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.service.SocialBlogVisitService#selectAllCountByblogId(com.lgcns.ikep4.socialpack.socialblog.model.SocialBlogVisit)
	 */
	public Integer selectAllCountByblogId(SocialBlogVisit socialBlogVisit) {
		return socialBlogVisitDao.selectAllCountByblogId(socialBlogVisit);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#create(java.lang.Object)
	 */
	public String create(SocialBlogVisit socialBlogVisit) {
		return socialBlogVisitDao.create(socialBlogVisit);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.service.SocialBlogVisitService#get(com.lgcns.ikep4.socialpack.socialblog.model.SocialBlogVisit)
	 */
	public SocialBlogVisit get(SocialBlogVisit socialBlogVisit) {
		return socialBlogVisitDao.get(socialBlogVisit);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.service.SocialBlogVisitService#physicalDelete(com.lgcns.ikep4.socialpack.socialblog.model.SocialBlogVisit)
	 */
	public void physicalDelete(SocialBlogVisit socialBlogVisit) {
		socialBlogVisitDao.physicalDelete(socialBlogVisit);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.service.SocialBlogVisitService#physicalDeleteThreadByBlogId(com.lgcns.ikep4.socialpack.socialblog.model.SocialBlogVisit)
	 */
	public void physicalDeleteThreadByBlogId(SocialBlogVisit socialBlogVisit) {
		socialBlogVisitDao.physicalDeleteThreadByBlogId(socialBlogVisit);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#recordSocialBlogVisitByDay(java.lang.Object)
	 */
	public void recordSocialBlogVisitByDay(SocialBlogVisit socialBlogVisit) {
		
		SocialBlogVisit todaySocialBlogVisit = socialBlogVisitDao.get(socialBlogVisit);
		// Null 인 경우에만 입력
		if(todaySocialBlogVisit == null){
			socialBlogVisitDao.create(socialBlogVisit);
			
			//최근 블로그 방문자 포틀릿 contents 캐시 Element 삭제
			cacheService.removeCacheElementPortletContent("Cachename-recentVisitor-portlet", "Cachemode-recentVisitor-portlet", "Elementkey-recentVisitor-portlet", socialBlogVisit.getBlogOwnerId());
		}
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.service.SocialBlogVisitService#getVisitorManageO(com.lgcns.ikep4.socialpack.socialblog.model.SocialBlogVisit)
	 */
	public List<SocialBlogVisit> getVisitorManage(SocialBlogVisit socialBlogVisit) {
		return socialBlogVisitDao.getVisitorManage(socialBlogVisit);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#read(java.io.Serializable)
	 */
	@Deprecated
	public SocialBlogVisit read(String id) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#exists(java.io.Serializable)
	 */
	@Deprecated
	public boolean exists(String id) {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#update(java.lang.Object)
	 */
	@Deprecated
	public void update(SocialBlogVisit object) {
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#delete(java.io.Serializable)
	 */
	@Deprecated
	public void delete(String id) {
	}

}
