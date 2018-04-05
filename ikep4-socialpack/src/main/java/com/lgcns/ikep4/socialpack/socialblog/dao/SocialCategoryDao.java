/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.socialblog.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialCategory;

/**
 * 소셜블로그 게시글 카테고리 정보 DAO Interface
 *
 * @author 이형운
 * @version $Id: SocialCategoryDao.java 16246 2011-08-18 04:48:28Z giljae $
 */
public interface SocialCategoryDao extends GenericDao<SocialCategory, String> {

	/**
	 * 소셜블로그의 블로그 ID 기준으로  카테고리 리스트 조회
	 * @param blogId 블로그 ID
	 * @return 블로그 카테고리 리스트
	 */
	public List<SocialCategory> listByBlogId(String blogId); 
	
	/**
	 * 소셜블로그의 블로그 ID를 기준으로 카테고리 리스트 수 조회
	 * @param blogId 블로그 ID
	 * @return 블로그 카테고리 수
	 */
	public Integer countBlogId(String blogId); 
	
	/**
	 * 소셜블로그 카테고리 정렬 최대 값 조회
	 * @param blogId 블로그 ID
	 * @return 블로그 카테고리의 정렬 최대값
	 */
	public Integer getMaxSortOrder(String blogId); 
	
	/**
	 * 소셜블로그 카테고리  삭제
	 * @param categoryId 카테고리 ID
	 */
	public void physicalDelete(String categoryId);
	
}

