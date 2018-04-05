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
import com.lgcns.ikep4.socialpack.socialblog.dao.SocialCategoryDao;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialCategory;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.StringUtil;

/**
 * 소셜블로그 게시글 카테고리 정보 Service Implement Class
 *
 * @author 이형운
 * @version $Id: SocialCategoryServiceImpl.java 16246 2011-08-18 04:48:28Z giljae $
 */
@Service("socialCategoryService")
public class SocialCategoryServiceImpl extends GenericServiceImpl<SocialCategory, String> implements
		com.lgcns.ikep4.socialpack.socialblog.service.SocialCategoryService {

	/**
	 * 블로그 카테고리 컨트롤용 Dao
	 */
	@Autowired
	private SocialCategoryDao socialCategoryDao;
	
	/**
	 * 키값 생성 을 위한 컨트롤용 Service
	 */
	@Autowired
	private IdgenService idgenService;
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.service.SocialCategoryService#listByBlogId(java.lang.String)
	 */
	public List<SocialCategory> listByBlogId(String blogId) {
		return socialCategoryDao.listByBlogId(blogId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.service.SocialCategoryService#countBlogId(java.lang.String)
	 */
	public Integer countBlogId(String blogId) {
		return socialCategoryDao.countBlogId(blogId);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.service.SocialCategoryService#getMaxSortOrder(java.lang.String)
	 */
	public Integer getMaxSortOrder(String blogId) {
		return socialCategoryDao.getMaxSortOrder(blogId);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#read(java.io.Serializable)
	 */
	public SocialCategory read(String categoryId) {
		return socialCategoryDao.get(categoryId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#exists(java.io.Serializable)
	 */
	public boolean exists(String categoryId) {
		return socialCategoryDao.exists(categoryId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#create(java.lang.Object)
	 */
	public String create(SocialCategory socialCategory) {
		
		socialCategory.setCategoryId(idgenService.getNextId());
		socialCategory.setSortOrder(socialCategoryDao.getMaxSortOrder(socialCategory.getBlogId()));
		return socialCategoryDao.create(socialCategory);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#update(java.lang.Object)
	 */
	public void update(SocialCategory socialCategory) {
		socialCategoryDao.update(socialCategory);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.service.SocialCategoryService#physicalDelete(java.lang.String)
	 */
	public void physicalDelete(String categoryId) {
		socialCategoryDao.physicalDelete(categoryId);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.service.SocialCategoryService#updateCategoryOrder(java.lang.String)
	 */
	public void updateCategoryOrder(String categoryIdes) {
		
		List<String> categoryList = StringUtil.getTokens(categoryIdes, ",");
		for(int i=0, count = categoryList.size() ; i < count; i++){
			SocialCategory socialCategory = socialCategoryDao.get(categoryList.get(i));
			socialCategory.setSortOrder(i+1);
			
			socialCategoryDao.update(socialCategory);
		}
		
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#delete(java.io.Serializable)
	 */
	@Deprecated
	public void delete(String id) {
	}

}
