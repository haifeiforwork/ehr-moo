/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.tagging.dao.impl;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.tagging.dao.TagItemDao;
import com.lgcns.ikep4.support.tagging.model.Tag;

/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: TagItemDaoImpl.java 16316 2011-08-22 00:26:53Z giljae $
 */
@Repository("tagItemDao")
public class TagItemDaoImpl extends GenericDaoSqlmap<Tag, String> implements TagItemDao {
	

	
	public Tag read(String tagItemId, String tagItemType) {
		
		Tag tag = new Tag();
		tag.setTagItemId(tagItemId);
		tag.setTagItemType(tagItemType);
		
		return (Tag)sqlSelectForObject("support.tagging.dao.TagItem.select", tag);
	}


	public String create(Tag tag) {
		return (String) sqlInsert("support.tagging.dao.TagItem.insert", tag);
	}

	@Deprecated
	public boolean exists(String id) {
		return false;
	}

	@Deprecated
	public Tag get(String id) {
		return null;
	}
	

	public void update(Tag tag) {
		sqlUpdate("support.tagging.dao.TagItem.update", tag);
	}
	
	@Deprecated
	public void remove(String tagId) {
	}


	public void remove(String tagItemId, String tagItemType) {
		
		Tag tag = new Tag();
		tag.setTagItemId(tagItemId);
		tag.setTagItemType(tagItemType);
		
		sqlDelete("support.tagging.dao.TagItem.delete", tag);
	}
	
	
}
