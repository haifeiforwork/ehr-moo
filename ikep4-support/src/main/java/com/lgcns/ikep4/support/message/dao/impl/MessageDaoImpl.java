/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.message.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.message.dao.MessageDao;
import com.lgcns.ikep4.support.message.model.Message;
import com.lgcns.ikep4.support.message.model.MessageAlarm;
import com.lgcns.ikep4.support.message.model.MessageReceive;
import com.lgcns.ikep4.support.message.search.MessageSearchCondition;
/**
 * TODO Javadoc주석작성
 *
 * @author 손정환(samsonic@dbays.com)
 * @version $Id: MessageDaoImpl.java 17308 2012-02-06 04:43:19Z yruyo $
 */
@Repository
public class MessageDaoImpl extends GenericDaoSqlmap<Message,String> implements MessageDao {
	
	private String nameSpace = "support.message.message.";
	
	public Message get(String messageId) {
		return (Message) sqlSelectForObject(nameSpace+"get", messageId);
	}
	
	public boolean readCheck(MessageSearchCondition messageSearchCondition) {
		Integer check = (Integer) sqlSelectForObject(nameSpace+"readCheck", messageSearchCondition);
		if (check > 0) { return true; } 
		return false; 
	}

	@Deprecated
	public boolean exists(String messageId) {
		return false;
	}

	public String create(Message message) {
		String messageId = message.getMessageId();
		this.sqlInsert(nameSpace+"create",message);
		return messageId;
	}

	@Deprecated
	public void update(Message object) {
	}

	@Deprecated
	public void remove(String id) {
	}

	public List<Message> getSendMessageList(MessageSearchCondition messageSearchCondition) {
		return (List<Message>) sqlSelectForList(nameSpace+"getSendMessageList", messageSearchCondition);
	}

	public List<Message> getReceiveMessageList(MessageSearchCondition messageSearchCondition) {
		return (List<Message>) sqlSelectForList(nameSpace+"getReceiveMessageList", messageSearchCondition);
	}
	
	public List<Message> getStoreMessageList(MessageSearchCondition messageSearchCondition) {
		return (List<Message>) sqlSelectForList(nameSpace+"getStoreMessageList", messageSearchCondition);
	}
	
	public List<Message> getGroupMessageList(MessageSearchCondition messageSearchCondition) {
		return (List<Message>) sqlSelectForList(nameSpace+"getGroupMessageList", messageSearchCondition);
	}
	
	public Integer countReceiveMessageList(MessageSearchCondition messageSearchCondition) {
		return (Integer) sqlSelectForObject(nameSpace+"countReceiveMessageList", messageSearchCondition);
	}

	public Integer countSendMessageList(MessageSearchCondition messageSearchCondition) {
		return (Integer) sqlSelectForObject(nameSpace+"countSendMessageList", messageSearchCondition);
	}
	
	public Integer countStoreMessageList(MessageSearchCondition messageSearchCondition) {
		return (Integer) sqlSelectForObject(nameSpace+"countStoreMessageList", messageSearchCondition);
	}
	
	public Integer countGroupMessageList(MessageSearchCondition messageSearchCondition) {
		return (Integer) sqlSelectForObject(nameSpace+"countGroupMessageList", messageSearchCondition);
	}

	public void createSend(Message message) {
		this.sqlInsert(nameSpace+"createSend",message);
	}

	public void createReceive(Message message) {
		this.sqlInsert(nameSpace+"createReceive",message);
	}

	public void createStore(Message message) {
		this.sqlInsert(nameSpace+"createStore",message);
	}
	
	public void updateIsComplete(String messageId) {
		this.sqlUpdate(nameSpace+"updateIsComplete",messageId);
	}

	public void insertIsRead(Message message) {
		int ext = (Integer) sqlSelectForObject(nameSpace+"existsIsRead", message); 
		message.setIsDelete(0);
		if (ext == 0) { this.sqlInsert(nameSpace+"insertIsRead",message); }
	}

	public void physicalDelete(String messageId) {
		this.sqlUpdate(nameSpace+"physicalDelete",messageId);
	}

	public void physicalDeleteSend(Message message) {
		this.sqlUpdate(nameSpace+"physicalDeleteSend",message);		
	}

	public void physicalDeleteReceive(Message message) {
		this.sqlUpdate(nameSpace+"physicalDeleteReceive",message);			
	}
	
	public void logicalDeleteReceive(Message message) {
		int ext = (Integer) sqlSelectForObject(nameSpace+"existsIsRead", message); 
		if (ext == 0) { 
			message.setIsDelete(1);
			this.sqlInsert(nameSpace+"insertIsRead",message); 
		} else {
			this.sqlUpdate(nameSpace+"logicalDeleteReceive",message);	
		}
	}

	public void physicalDeleteStore(Message message) {
		this.sqlUpdate(nameSpace+"physicalDeleteStore",message);		
	}

	public Integer countReadMessage(String messageId) {
		return (Integer) sqlSelectForObject(nameSpace+"countReadMessage", messageId);
	}

	public Integer countNewMessage(String userId) {
		return (Integer) sqlSelectForObject(nameSpace+"countNewMessage", userId);
	}
	
	@SuppressWarnings({"unchecked"})
	public List<MessageReceive> messageReceiveList(Map<String, Object> map) {
		return (List<MessageReceive>) this.getSqlMapClientTemplate().queryForList(nameSpace+"messageReceiveList", map);
	}
	
	public MessageAlarm getNewMessageArrived(String userId) {
		return (MessageAlarm) sqlSelectForObject(nameSpace+"getNewMessageArrived", userId);
	}
	
	public void updateMessageAlarm(MessageAlarm messageAlarm) {
		this.sqlUpdate(nameSpace+"updateMessageAlarm",messageAlarm);
	}

}
