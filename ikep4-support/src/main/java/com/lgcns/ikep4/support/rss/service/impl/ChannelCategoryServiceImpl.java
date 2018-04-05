/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.rss.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.rss.dao.ChannelCategoryDao;
import com.lgcns.ikep4.support.rss.dao.ChannelDao;
import com.lgcns.ikep4.support.rss.dao.ChannelItemDao;
import com.lgcns.ikep4.support.rss.model.Channel;
import com.lgcns.ikep4.support.rss.model.ChannelCategory;
import com.lgcns.ikep4.support.rss.model.ChannelItem;
import com.lgcns.ikep4.support.rss.model.ChannelSearchCondition;
import com.lgcns.ikep4.support.rss.service.ChannelCategoryService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


/**
 * BoardService 구현 클래스
 * 
 * @author 이주열(jooyoul.lee@lgcns.com)
 * @version $Id: ChannelCategoryServiceImpl.java 15601 2011-06-27 01:53:04Z loverfairy $
 */
@Service
public class ChannelCategoryServiceImpl extends GenericServiceImpl<ChannelCategory, String> implements ChannelCategoryService {


	@Autowired
	private ChannelCategoryDao channelCategoryDao;
	
	/**
	 * 채널 Dao
	 */
	@Autowired
	private ChannelDao channelDao;
	
	@Autowired
	private ChannelItemDao channelItemDao;
	
	@Autowired
	private IdgenService idgenService;
	

	public String create(ChannelCategory channelCategory) {
		
		String id = idgenService.getNextId();
		
		channelCategory.setCategoryId(id);
			
		channelCategoryDao.create(channelCategory);
			
		
		return id;
	}
	
	/**
	 * 채널 리스트 검색
	 */
	public SearchResult<ChannelCategory> getAll(ChannelSearchCondition searchCondition) {

		List<ChannelCategory> catList = channelCategoryDao.listAll(searchCondition.getOwnerId());
				
		SearchResult<ChannelCategory> searchResult = null;
		
		searchCondition.terminateSearchCondition(catList.size());
				
		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<ChannelCategory>(searchCondition);

		} else {
			
			Integer categoryCount = catList.size();
		
			for(int i = 0; i < categoryCount; i++)
			{
				ChannelCategory catItem = catList.get(i);
				
				searchCondition.setCategoryId(catItem.getCategoryId());
				
				List<Channel> channelList = channelDao.getAll(searchCondition);
				
				catList.get(i).setChannel(channelList);
			}			
			
			searchResult = new SearchResult<ChannelCategory>(catList, searchCondition);	
		}
		
		return searchResult;
	}
	
	public SearchResult<ChannelCategory> getAllforRSSnews(ChannelSearchCondition searchCondition,int listSize){

		List<ChannelCategory> catList = channelCategoryDao.listAll(searchCondition.getOwnerId());
				
		SearchResult<ChannelCategory> searchResult = null;
		
		searchCondition.terminateSearchCondition(catList.size());
		
		Integer categoryCount = catList.size();
		
		for(int i = 0; i < categoryCount; i++)
		{
			ChannelCategory catItem = catList.get(i);
				
			searchCondition.setCategoryId(catItem.getCategoryId());
			
			ChannelSearchCondition itemSearchCondition = new ChannelSearchCondition();

			itemSearchCondition.setOwnerId(searchCondition.getOwnerId());
			itemSearchCondition.setCategoryId(catItem.getCategoryId());
			itemSearchCondition.setSortColumn("ITEM_PUBLISH_DATE");
			itemSearchCondition.setSortType("DESC");
			itemSearchCondition.setPagePerRecord(listSize);	
			itemSearchCondition.terminateSearchCondition(listSize);
	
			List<ChannelItem> channelItemList = channelItemDao.getAll(itemSearchCondition);
			
			catList.get(i).setChannelItem(channelItemList);				
		}			
			
		searchResult = new SearchResult<ChannelCategory>(catList, searchCondition);	
		
		return searchResult;
	}

	public SearchResult<ChannelCategory> getAllforRSSnewsPortlet(ChannelSearchCondition searchCondition,int listSize)
	{
		List<ChannelCategory> catList = channelCategoryDao.listForPortlet(searchCondition.getOwnerId());
		
		SearchResult<ChannelCategory> searchResult = null;
		
		searchCondition.terminateSearchCondition(catList.size());
		
		Integer categoryCount = catList.size();
		
		for(int i = 0; i < categoryCount; i++)
		{
			ChannelCategory catItem = catList.get(i);
				
			searchCondition.setCategoryId(catItem.getCategoryId());
			
			ChannelSearchCondition itemSearchCondition = new ChannelSearchCondition();

			itemSearchCondition.setOwnerId(searchCondition.getOwnerId());
			itemSearchCondition.setCategoryId(catItem.getCategoryId());
			itemSearchCondition.setSortColumn("ITEM_PUBLISH_DATE");
			itemSearchCondition.setSortType("DESC");
			itemSearchCondition.setPagePerRecord(listSize);	
			itemSearchCondition.terminateSearchCondition(listSize);
	
			List<ChannelItem> channelItemList = channelItemDao.getAll(itemSearchCondition);
			
			catList.get(i).setChannelItem(channelItemList);				
		}			
			
		searchResult = new SearchResult<ChannelCategory>(catList, searchCondition);	
		
		return searchResult;
		
	}

	public List<ChannelCategory> getlistforRSSnewsPortletConfig(String userId)
	{		
		return channelCategoryDao.listForPortletConfig(userId);
	}
	
	
	public List<ChannelCategory> list(String userId) {
		return channelCategoryDao.listAll(userId);
	}


	public ChannelCategory read(String id) {
		
		return channelCategoryDao.get(id);
	}
	
	public void update(ChannelCategory channelCategory) {
		channelCategoryDao.update(channelCategory);
	}
	
	
	
	public void applyOrderCategory(String categoryId, String nextCategoryId, String orderType) {
		
		int categoryOrder = 0;
		int nextCategoryOrder = 0;
		if(orderType.equals("up")){
			categoryOrder = -1;
			nextCategoryOrder = 1;
		} else {
			categoryOrder = 1;
			nextCategoryOrder = -1;
		}
		
		channelCategoryDao.updateOrder(categoryId, categoryOrder);
		channelCategoryDao.updateOrder(nextCategoryId, nextCategoryOrder);
		
	}
	
	
	public void updateCategoryOrder(String categoryIdes) {
		
		String[] categoryIdList = categoryIdes.split(",");
		
		for(int i=0, count = categoryIdList.length; i < count; i++){
			String categoryId = categoryIdList[i];
			
			channelCategoryDao.updateCategoryOrder(categoryId, (i+1));
		}
		
	}


	public void delete(String categoryId) {
		
		//기존 id 카테고리 값을 null로 수정한다.
		//channelDao.updateCategoryId(categoryId, null);	
		
		channelItemDao.removeByCategoryId(categoryId);
		
		channelDao.removeByCategoryId(categoryId);
		
		channelCategoryDao.remove(categoryId);
		
	}
	

}


