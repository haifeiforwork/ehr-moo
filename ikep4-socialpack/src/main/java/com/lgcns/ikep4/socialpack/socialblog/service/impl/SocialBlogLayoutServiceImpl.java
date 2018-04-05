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
import com.lgcns.ikep4.socialpack.socialblog.dao.SocialBlogLayoutDao;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBlog;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBlogLayout;
import com.lgcns.ikep4.socialpack.socialblog.service.SocialBlogLayoutService;

/**
 * 블로그의 레이아웃 정보를 제공하는 Service Implement Class
 *
 * @author 이형운
 * @version $Id: SocialBlogLayoutServiceImpl.java 16246 2011-08-18 04:48:28Z giljae $
 */
@Service("socialBlogLayoutService")
public class SocialBlogLayoutServiceImpl extends GenericServiceImpl<SocialBlogLayout, String> implements
		SocialBlogLayoutService {

	/**
	 * 블로그 레이아웃 정보 컨트롤용 Dao
	 */
	@Autowired
	public SocialBlogLayoutDao socialBlogLayoutDao;
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.service.SocialBlogLayoutService#socialBlogLayoutByOwnerId(com.lgcns.ikep4.socialpack.socialblog.model.SocialBlog)
	 */
	public SocialBlogLayout socialBlogLayoutByOwnerId(SocialBlog socialBlog) {
		return socialBlogLayoutDao.get(socialBlog.getLayoutId());
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.service.SocialBlogLayoutService#defaultSocialBlogLayout()
	 */
	public SocialBlogLayout defaultSocialBlogLayout() {
		return socialBlogLayoutDao.getDefaultLayout();
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.service.SocialBlogLayoutService#listSocialBlogLayoutAll()
	 */
	public List<SocialBlogLayout> listSocialBlogLayoutAll() {
		return socialBlogLayoutDao.listSocialBlogLayout();
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#read(java.io.Serializable)
	 */
	@Deprecated
	public SocialBlogLayout read(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#exists(java.io.Serializable)
	 */
	@Deprecated
	public boolean exists(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#create(java.lang.Object)
	 */
	@Deprecated
	public String create(SocialBlogLayout object) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#update(java.lang.Object)
	 */
	@Deprecated
	public void update(SocialBlogLayout object) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#delete(java.io.Serializable)
	 */
	@Deprecated
	public void delete(String id) {
		// TODO Auto-generated method stub

	}

}
