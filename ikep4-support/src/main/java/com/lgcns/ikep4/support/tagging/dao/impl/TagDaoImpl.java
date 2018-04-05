/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.tagging.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.tagging.dao.TagDao;
import com.lgcns.ikep4.support.tagging.model.Tag;

/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: TagDaoImpl.java 17315 2012-02-08 04:56:13Z yruyo $
 */
@Repository("tagDao")
public class TagDaoImpl extends GenericDaoSqlmap<Tag, String> implements TagDao {
	

	public List<Tag> listCloud(Tag tag) {
		return sqlSelectForList("support.tagging.dao.Tag.listCloud", tag);
	}
	
	

	public List<Tag> listByItem(String tagItemId, String tagItemType) {
		
		Tag tag = new Tag();
		tag.setTagItemId(tagItemId);
		tag.setTagItemType(tagItemType);
		
		return sqlSelectForList("support.tagging.dao.Tag.listByItem", tag);
	}



	public List<Tag> listSearch(Tag tag) {
		return sqlSelectForList("support.tagging.dao.Tag.listSearch", tag);
	}



	public int readCount(Tag tag) {
		return (Integer)sqlSelectForObject("support.tagging.dao.Tag.selectCount", tag);
	}
	
	
	public int getCountSearch(Tag tag) {
		return (Integer)sqlSelectForObject("support.tagging.dao.Tag.readCountSearch", tag);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<String> listItemId(Tag tag) {
		return (List)sqlSelectForListOfObject("support.tagging.dao.Tag.listItemId", tag);
	}
	
	public int getCountListItemId(Tag tag) {
		return (Integer)sqlSelectForObject("support.tagging.dao.Tag.getCountListItemId", tag);
	}

	public List<Tag> listTeamType(String portalId) {
		return sqlSelectForList("support.tagging.dao.Tag.listTeamType", portalId);
	}
	
	
	public List<Tag> listItemCount(String tagItemId, String tagItemType) {
		
		Tag tag = new Tag();
		tag.setTagItemId(tagItemId);
		tag.setTagItemType(tagItemType);
		
		return sqlSelectForList("support.tagging.dao.Tag.listItemCount", tag);
	}

	
	public List<Tag> listItemAsUserByTagId(Tag tag) {
		return sqlSelectForList("support.tagging.dao.Tag.listItemAsUserByTagId", tag);
	}
	
	public int getCountListItemAsUserByTagId(Tag tag) {
		return (Integer)sqlSelectForObject("support.tagging.dao.Tag.getCountListItemAsUserByTagId", tag);
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<String> listItemIdAsRandom(Tag tag) {
		return (List)sqlSelectForListOfObject("support.tagging.dao.Tag.listItemIdAsRandom", tag);
	}



	@Deprecated
	public String create(Tag tag) {
		return null;
	}

	@Deprecated
	public boolean exists(String id) {
		return false;
	}

	@Deprecated
	public Tag get(String id) {
		return null;
	}
	
	@Deprecated
	public void update(Tag tag) {
	}
	
	@Deprecated
	public void remove(String tagId) {
	}


}
