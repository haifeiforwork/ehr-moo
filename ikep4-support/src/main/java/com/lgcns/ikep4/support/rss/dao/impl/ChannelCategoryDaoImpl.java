/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.rss.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.rss.dao.ChannelCategoryDao;
import com.lgcns.ikep4.support.rss.model.ChannelCategory;


/**
 *
 * @author 최재영 (FracisChoi@lgcns.com)
 * @version $Id: ChannelCategoryDaoImpl.java 12459 2011-05-20 09:48:02Z loverfairy $
 */
@Repository("channelCategoryDao")
public class ChannelCategoryDaoImpl extends GenericDaoSqlmap<ChannelCategory, String> implements ChannelCategoryDao {
	

	public List<ChannelCategory> listAll(String ownerId) {
		
		return sqlSelectForList("support.rss.dao.channelCategory.selectAll", ownerId);
	}
	
	public List<ChannelCategory> listForPortlet(String ownerId) {
		
		return sqlSelectForList("support.rss.dao.channelCategory.selectForPortlet", ownerId);
	}
	
	public List<ChannelCategory> listForPortletConfig(String ownerId)
	{
		return sqlSelectForList("support.rss.dao.channelCategory.selectForPortletConfig", ownerId);
	}


	public String create(ChannelCategory obj) {
		return (String) sqlInsert("support.rss.dao.channelCategory.insert", obj);
	}

	@Deprecated
	public boolean exists(String id) {
		return false;
	}

	public ChannelCategory get(String id) {
		return (ChannelCategory) sqlSelectForObject("support.rss.dao.channelCategory.select", id);
	}


	public String selectCategoryIdByCategoryName(String categoryName, String userId) {
		
		ChannelCategory channelCategory = new ChannelCategory();
		channelCategory.setCategoryName(categoryName);
		channelCategory.setOwnerId(userId);
		
		return (String)sqlSelectForObject("support.rss.dao.channelCategory.selectCategoryIdByCategoryName",channelCategory);
	}

	public void update(ChannelCategory obj) {
		sqlUpdate("support.rss.dao.channelCategory.update", obj);
	}
	
	
	
	public void updateOrder(String categoryId, int categoryOrder) {
		
		ChannelCategory idCategory = new ChannelCategory();
		idCategory.setCategoryId(categoryId);
		idCategory.setCategoryOrder(categoryOrder);
		
		sqlUpdate("support.rss.dao.channelCategory.updateOrder", idCategory);
		
	}
	
	
	public void updateCategoryOrder(String categoryId, int categoryOrder) {
		
		ChannelCategory idCategory = new ChannelCategory();
		idCategory.setCategoryId(categoryId);
		idCategory.setCategoryOrder(categoryOrder);
		
		sqlUpdate("support.rss.dao.channelCategory.updateCategoryOrder", idCategory);
		
	}


	public void remove(String idId) {
		sqlDelete("support.rss.dao.channelCategory.delete", idId);
	}
	
	
	
	
}
