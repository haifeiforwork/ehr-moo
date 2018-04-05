/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.message.service;

import java.util.List;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.message.model.Message;
import com.lgcns.ikep4.support.message.model.MessageAlarm;
import com.lgcns.ikep4.support.message.model.MessageReceive;
import com.lgcns.ikep4.support.message.search.MessageSearchCondition;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * TODO Javadoc주석작성
 *
 * @author 손정환(samsonic@dbays.com)
 * @version $Id: MessageService.java 16258 2011-08-18 05:37:22Z giljae $
 */
public interface MessageService extends GenericService<Message, String> {

	public boolean readCheck(MessageSearchCondition messageSearchCondition);
	
	public SearchResult<Message> getSendMessageList(MessageSearchCondition messageSearchCondition) ;
	
	public SearchResult<Message> getReceiveMessageList(MessageSearchCondition messageSearchCondition) ;
	
	public SearchResult<Message> getStoreMessageList(MessageSearchCondition messageSearchCondition) ;
	
	public SearchResult<Message> getGroupMessageList(MessageSearchCondition messageSearchCondition) ;
	
	public Integer countNewMessage(String userId);
	
	public void insertIsRead(String messageId, String userId);
	
	public void deleteSendMessage(List<Message> messageList);
	
	public void deleteReceiveMessage(List<Message> messageList);
	
	public void deleteStoreMessage(List<Message> messageList);
	
	public void keepSendMessage(List<Message> messageList);
	
	public void keepReceiveMessage(List<Message> messageList);
	
	public String withdrawMessage(String messageId);
	
	public String sendMessage(Message message, User user);
	
	public void insertReceiveMessage(List<Message> receiveList) ;
	
	public List<MessageReceive> messageReceiveList(String messageId, int rowNum);
	
	public MessageAlarm getNewMessageArrived(String userId);
	
	public void updateMessageAlarm(MessageAlarm messageAlarm);
}
