/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.supportpack.message.test.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.awt.Robot;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.message.model.Message;
import com.lgcns.ikep4.support.message.search.MessageSearchCondition;
import com.lgcns.ikep4.support.message.service.MessageService;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * TODO Javadoc주석작성
 * 
 * @author 손정환(samsonic@dbays.com)
 * @version $Id: MessageServiceTest.java 16258 2011-08-18 05:37:22Z giljae $
 */
public class MessageServiceTest extends BaseServiceTestCase {
	@Autowired
	private MessageService messageService;

	private final Message message = new Message();

	private static String messageIdG;

	private final List<Message> messageList = null;

	@Before
	public void setUp() throws Exception {
		this.message.setCategoryId("0");
		this.message.setContents("message77테스트입니다.");
		this.message.setAttachSize(0);
		this.message.setReceiverCount(1);
		this.message.setReceiverList("user2");
		this.message.setIsUrgent(0);
		this.message.setIsStored(0);
		this.message.setIsComplete(0);
		this.message.setSenderId("user1");
		this.message.setSenderName("사용자1");
	}

	@Test
	@Ignore
	// TODO : 단일 트랜젝션이 아닌 케이스에 대한 방안을 수립되기 전까진 ignore
	public void testSendMessage() {
		User user = new User();
		user.setUserId("user1");
		user.setUserName("사용자1");
		String messageId = this.messageService.sendMessage(this.message, user);
		List<Message> list = new ArrayList<Message>();
		for (int i = 1; i < 11; i++) {
			Message receiveMessage = new Message();
			receiveMessage.setUserId("user" + i);
			receiveMessage.setMessageId(messageId);
			list.add(receiveMessage);
		}

		log.debug("-----start-----");
		this.messageService.insertReceiveMessage(list);
		log.debug("-----end-----");
		messageIdG = messageId;
		assertNotNull(messageId);
	}

	@Test
	public void testGetSendMessageList() {
		MessageSearchCondition messageSearchCondition = new MessageSearchCondition();
		messageSearchCondition.setUserId("user1");
		SearchResult<Message> result = this.messageService.getSendMessageList(messageSearchCondition);
		assertNotNull(result);
	}

	@Test
	public void testGetReceiveMessageList() {
		MessageSearchCondition messageSearchCondition = new MessageSearchCondition();
		messageSearchCondition.setUserId("user2");
		SearchResult<Message> result = this.messageService.getReceiveMessageList(messageSearchCondition);
		assertNotNull(result);
	}

	@Test
	public void testCountNewMessage() {
		Integer result = this.messageService.countNewMessage("user2");
		assertNotNull(result);
	}

	@Ignore
	public void testUpdateIsRead() {

	}

	@Ignore
	public void testWithdrawMessage() {

	}

	@Test
	public void testDeleteSendMessage() {
		List<Message> sendList = new ArrayList<Message>();
		// Message message = new Message();
		this.message.setUserId(message.getSenderId());
		this.message.setMessageId(messageIdG);
		sendList.add(this.message);
		this.messageService.deleteSendMessage(sendList);

		MessageSearchCondition messageSearchCondition = new MessageSearchCondition();
		messageSearchCondition.setUserId(this.message.getUserId());
		messageSearchCondition.setSearchColumn("contents");
		messageSearchCondition.setSearchWord("message77테스트입니다.");
		SearchResult<Message> result = this.messageService.getSendMessageList(messageSearchCondition);
		assertNull(result.getEntity());
	}

	@Test
	@Ignore
	public void testDeleteReceiveMessage() {
		List<Message> receiveList = new ArrayList<Message>();
		// Message message = new Message();
		this.message.setUserId("user2");
		this.message.setMessageId(messageIdG);
		receiveList.add(this.message);
		this.messageService.deleteReceiveMessage(receiveList);

		MessageSearchCondition messageSearchCondition = new MessageSearchCondition();
		messageSearchCondition.setUserId("user2");
		messageSearchCondition.setSearchColumn("contents");
		messageSearchCondition.setSearchWord("message77테스트입니다.");
		SearchResult<Message> result = this.messageService.getReceiveMessageList(messageSearchCondition);
		assertNull(result.getEntity());
	}

	public void testDeleteStoreMessage() {

	}

	public void testKeepSendMessage() {

	}

	public void testKeepReceiveMessage() {

	}
}
