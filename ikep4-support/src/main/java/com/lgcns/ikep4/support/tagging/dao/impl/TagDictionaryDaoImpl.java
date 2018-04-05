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
import com.lgcns.ikep4.support.tagging.dao.TagDictionaryDao;
import com.lgcns.ikep4.support.tagging.model.Tag;

/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: TagDictionaryDaoImpl.java 16316 2011-08-22 00:26:53Z giljae $
 */
@Repository("tagDictionaryDao")
public class TagDictionaryDaoImpl extends GenericDaoSqlmap<Tag, String> implements TagDictionaryDao {
	

	
	public Tag readByTagName(String tagName, String portalId) {
		
		Tag tag = new Tag();
		tag.setTagName(tagName);
		tag.setPortalId(portalId);
		
		return (Tag)sqlSelectForObject("support.tagging.dao.TagDictionary.selectByTagName", tag);
	}


	public List<Tag> listByPortalId(String portalId) {
		return sqlSelectForList("support.tagging.dao.TagDictionary.selectListByPortalId", portalId);
	}


	public String create(Tag tag) {
		return (String) sqlInsert("support.tagging.dao.TagDictionary.insert", tag);
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
		sqlUpdate("support.tagging.dao.TagDictionary.update", tag);
	}
	
	public void remove(String tagId) {
		sqlDelete("support.tagging.dao.TagDictionary.delete", tagId);
	}
	
	
}
