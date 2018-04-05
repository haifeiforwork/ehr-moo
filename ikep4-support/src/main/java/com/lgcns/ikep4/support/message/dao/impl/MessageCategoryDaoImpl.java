/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.message.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.message.dao.MessageCategoryDao;
import com.lgcns.ikep4.support.message.model.MessageCategory;

/**
 * TODO Javadoc주석작성
 *
 * @author 손정환(samsonic@dbays.com)
 * @version $Id: MessageCategoryDaoImpl.java 16316 2011-08-22 00:26:53Z giljae $
 */
@Repository
public class MessageCategoryDaoImpl extends GenericDaoSqlmap<MessageCategory,String> implements MessageCategoryDao {
	
	private String nameSpace = "support.message.";
	
	@Deprecated
	public MessageCategory get(String id) {
		return null;
	}

	@Deprecated
	public boolean exists(String id) {
		return false;
	}

	@Deprecated
	public String create(MessageCategory object) {
		return null;
	}

	@Deprecated
	public void update(MessageCategory object) {
	}

	@Deprecated
	public void remove(String id) {
	}
	
	public List<MessageCategory> getCategoryList() {
		return (List<MessageCategory>) sqlSelectForList(nameSpace+"MessageCategory.getCategory");
	}
}
