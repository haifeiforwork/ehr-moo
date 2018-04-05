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
import com.lgcns.ikep4.socialpack.socialblog.dao.SocialBlogPortletDao;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBlogPortlet;
import com.lgcns.ikep4.socialpack.socialblog.service.SocialBlogPortletService;

/**
 * 블로그에 등록된 포틀릿 정보를 제공하는 Service Implement Class
 *
 * @author 이형운
 * @version $Id: SocialBlogPortletServiceImpl.java 16246 2011-08-18 04:48:28Z giljae $
 */
@Service("socialBlogPortletService")
public class SocialBlogPortletServiceImpl extends GenericServiceImpl<SocialBlogPortlet, String> implements
		SocialBlogPortletService {

	/**
	 * 블로그 포틀릿 정보 컨트롤 Dao
	 */
	@Autowired
	public SocialBlogPortletDao socialBlogPortletDao;
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.service.SocialBlogPortletService#listAllSocialBlogPortlet()
	 */
	public List<SocialBlogPortlet> listAllSocialBlogPortlet() {
		SocialBlogPortlet socialBlogPortlet = new SocialBlogPortlet();
		socialBlogPortlet.setIsDefault(-1);
		return socialBlogPortletDao.listAllPortlet(socialBlogPortlet);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.service.SocialBlogPortletService#listDefaultSocialBlogPortlet()
	 */
	public List<SocialBlogPortlet> listDefaultSocialBlogPortlet() {
		SocialBlogPortlet socialBlogPortlet = new SocialBlogPortlet();
		socialBlogPortlet.setIsDefault(1);
		return socialBlogPortletDao.listAllPortlet(socialBlogPortlet);
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#read(java.io.Serializable)
	 */
	public SocialBlogPortlet read(String portletId) {
		return socialBlogPortletDao.get(portletId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#exists(java.io.Serializable)
	 */
	@Deprecated
	public boolean exists(String id) {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#create(java.lang.Object)
	 */
	@Deprecated
	public String create(SocialBlogPortlet object) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#update(java.lang.Object)
	 */
	@Deprecated
	public void update(SocialBlogPortlet object) {
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#delete(java.io.Serializable)
	 */
	@Deprecated
	public void delete(String id) {
	}

}
