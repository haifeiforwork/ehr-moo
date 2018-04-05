/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.rss.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.rss.model.ChannelCategory;
import com.lgcns.ikep4.support.rss.model.ChannelSearchCondition;

/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: IdCategoryService.java 3326 2011-03-18 05:34:30Z loverfairy $
 */
@Transactional
public interface ChannelCategoryService extends GenericService<ChannelCategory, String>  {
	
	
	/**
	 * 카테고리  리스트 검색
	 */
	public SearchResult<ChannelCategory> getAll(ChannelSearchCondition searchCondition);
	
	/**
	 * 카테고리  리스트 검색 과 아이템 가져오기
	 */
	public SearchResult<ChannelCategory> getAllforRSSnews(ChannelSearchCondition searchCondition,int listSize);
	
	/**
	 * 카테고리  리스트 검색 과 아이템 가져오기
	 */
	public SearchResult<ChannelCategory> getAllforRSSnewsPortlet(ChannelSearchCondition searchCondition,int listSize);
	
	/**
	 * 카테고리  리스트 검색
	 */
	public List<ChannelCategory> getlistforRSSnewsPortletConfig(String userId);
	
	/**
	 * 카테고리 리스트
	 * @param portalId
	 * @return
	 */
	public List<ChannelCategory> list(String userId);


	/**
	 * PK에 해당하는 테이블의 row를 삭제
	 * @param categoryId 삭제하고자하는 row의 PK 값
	 */
	public void delete(String categoryId);
	
	/**
	 * 카테고리  order update
	 * @param categoryId 자기자신 카테고리 ID
	 * @param nextCategoryId 순서 바뀔 카테고리 ID
	 * @param orderType 바꿀 순서 타입 (up - 자기자신의 order값이 적어짐, down - 자기자신 order값이 늘어남)
	 */
	public void applyOrderCategory(String categoryId, String nextCategoryId, String orderType);
	
	/**
	 * 카테고리 order update - 카테고리 id들을 순서대로 받음.
	 * @param categoryIdes
	 */
	public void updateCategoryOrder(String categoryIdes);
	
}
