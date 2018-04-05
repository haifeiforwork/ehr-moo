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
import com.lgcns.ikep4.support.message.dao.MessageSpecialUserDao;
import com.lgcns.ikep4.support.message.model.MessageSpecialUser;
import com.lgcns.ikep4.support.message.search.MessageMonitorSearchCondition;

/**
 * TODO Javadoc주석작성
 *
 * @author 손정환(samsonic@dbays.com)
 * @version $Id: MessageSpecialUserDaoImpl.java 16316 2011-08-22 00:26:53Z giljae $
 */
@Repository
public class MessageSpecialUserDaoImpl extends GenericDaoSqlmap<MessageSpecialUser,String> implements MessageSpecialUserDao {

	private String nameSpace = "support.message.";
	
	public MessageSpecialUser get(String userId) {
		return (MessageSpecialUser) sqlSelectForObject(nameSpace+"messageSpecialUser.get", userId);
	}
	
	public boolean exists(String userId) {
		Integer count = (Integer) sqlSelectForObject(nameSpace+"messageSpecialUser.exists", userId);
		if (count > 0) { return true; }
		return false;
	}

	public String create(MessageSpecialUser messageSpecialUser) {
		String userId = messageSpecialUser.getUserId();
		this.sqlInsert(nameSpace+"messageSpecialUser.create",messageSpecialUser);
		return userId;
	}

	public void update(MessageSpecialUser messageSpecialUser) {
		this.sqlUpdate(nameSpace+"messageSpecialUser.update",messageSpecialUser);
	}

	public void remove(String userId) {
		this.sqlUpdate(nameSpace+"messageSpecialUser.remove", userId);
	}
	
	public List<MessageSpecialUser> getSpecialUserList(MessageMonitorSearchCondition messageMonitorSearchCondition) {
		return (List<MessageSpecialUser>) sqlSelectForList(nameSpace+"messageSpecialUser.getSpecialUserList", messageMonitorSearchCondition);
	}

	public Integer countSpecialUserList(MessageMonitorSearchCondition messageMonitorSearchCondition) {
		return (Integer) sqlSelectForObject(nameSpace+"messageSpecialUser.countSpecialUserList", messageMonitorSearchCondition);
	}
	
	
}
