/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.supportpack.message.test.dao;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.support.message.dao.MessageDao;
import com.lgcns.ikep4.support.message.model.Message;
import com.lgcns.ikep4.support.message.search.MessageSearchCondition;
import com.lgcns.ikep4.util.idgen.service.IdgenService;

/**
 * TODO Javadoc주석작성
 *
 * @author 손정환(samsonic@dbays.com)
 * @version $Id: MessageDaoTest.java 16258 2011-08-18 05:37:22Z giljae $
 */
public class MessageDaoTest extends BaseDaoTestCase {
	
	@Autowired
	private MessageDao messageDao ;
	
	@Autowired
	private IdgenService idgenService;
	
	private String messageId;
	
	@Before
	public void setUp() throws Exception {
		Message message = new Message();
		this.messageId = idgenService.getNextId();
		
		message.setMessageId(this.messageId);
		message.setCategoryId("0");
		message.setContents(this.messageId+"Dao테스트입니다.");
		message.setAttachSize(0);
		message.setReceiverCount(1);
		message.setReceiverList("user2");
		message.setIsUrgent(0);
		message.setIsStored(0);
		message.setIsComplete(0);
		message.setSenderId("user1");
		message.setSenderName("사용자1");		
		this.messageDao.create(message);
		
		message.setUserId("user1");
		message.setMessageId(this.messageId);
		this.messageDao.createSend(message);
		
		message.setUserId("user2");
		message.setMessageId(this.messageId);
		this.messageDao.createReceive(message);
	}
	
	@Test
	public void testGetSendMessageList() {
		MessageSearchCondition messageSearchCondition =new MessageSearchCondition();
		messageSearchCondition.setUserId("user1");
		messageSearchCondition.setStartRowIndex(0);
		messageSearchCondition.setEndRowIndex(10);
		List<Message> result = this.messageDao.getSendMessageList(messageSearchCondition);
		assertFalse(result.isEmpty());
	}
	
	@Test
	public void testGetReceiveMessageList() {
		MessageSearchCondition messageSearchCondition =new MessageSearchCondition();
		messageSearchCondition.setUserId("user2");
		messageSearchCondition.setStartRowIndex(0);
		messageSearchCondition.setEndRowIndex(10);
		List<Message> result = this.messageDao.getReceiveMessageList(messageSearchCondition);
		assertFalse(result.isEmpty());
	}
	
	@Test
	public void testCountSendMessageList() {
		MessageSearchCondition messageSearchCondition =new MessageSearchCondition();
		messageSearchCondition.setUserId("user1");
		messageSearchCondition.setStartRowIndex(0);
		messageSearchCondition.setEndRowIndex(10);
		Integer result = this.messageDao.countSendMessageList(messageSearchCondition);
		assertNotNull(result);
	}
	
	@Test
	public void testCountReceiveMessageList() {
		MessageSearchCondition messageSearchCondition =new MessageSearchCondition();
		messageSearchCondition.setUserId("user2");
		messageSearchCondition.setStartRowIndex(0);
		messageSearchCondition.setEndRowIndex(10);
		Integer result = this.messageDao.countReceiveMessageList(messageSearchCondition);
		assertNotNull(result);
	}
		
	@Ignore
	public void testCreateSend() {
		//Message message = new Message();
		//message.setUserId("user1");
		//message.setMessageId(this.messageId);
		//this.messageDao.createSend(message);
		MessageSearchCondition messageSearchCondition =new MessageSearchCondition();
		messageSearchCondition.setUserId("user1");
		messageSearchCondition.setStartRowIndex(0);
		messageSearchCondition.setEndRowIndex(10);
		List<Message> result = this.messageDao.getSendMessageList(messageSearchCondition);
		assertFalse(result.isEmpty());
	}
	
	@Ignore
	public void testCreateReceive() {
		Message message = new Message();
		message.setUserId("user5");
		message.setMessageId(this.messageId);
		this.messageDao.createReceive(message);
		MessageSearchCondition messageSearchCondition =new MessageSearchCondition();
		messageSearchCondition.setUserId("user5");
		messageSearchCondition.setStartRowIndex(0);
		messageSearchCondition.setEndRowIndex(10);
		List<Message> result = this.messageDao.getReceiveMessageList(messageSearchCondition);
		assertFalse(result.isEmpty());
	}
	
	@Ignore
	public void testCreateStore() {
		Message message = new Message();
		message.setUserId("user1");
		message.setMessageId(this.messageId);
		this.messageDao.createStore(message);
	}
	
	@Ignore
	public void testUpdateIsRead() {
		Message message = new Message();
		message.setUserId("user2");
		message.setMessageId(this.messageId);
		this.messageDao.insertIsRead(message);
	}
	
	@Test
	public void testPhysicalDelete() {
		Message message = new Message();
		message.setMessageId(this.messageId);
		this.messageDao.physicalDeleteSend(message);
		this.messageDao.physicalDeleteReceive(message);
		this.messageDao.physicalDeleteStore(message);
		this.messageDao.physicalDelete(this.messageId);
		Message result = this.messageDao.get(this.messageId);
		assertNull(result);
	}
	
	@Test
	public void testPhysicalDeleteSend() {
		Message message = new Message();
		message.setUserId("user1");
		message.setMessageId(this.messageId);
		this.messageDao.physicalDeleteSend(message);
		
		MessageSearchCondition messageSearchCondition =new MessageSearchCondition();
		messageSearchCondition.setUserId("user1");
		messageSearchCondition.setSearchColumn("contents");
		messageSearchCondition.setSearchWord(this.messageId+"Dao테스트입니다.");
		messageSearchCondition.setStartRowIndex(0);
		messageSearchCondition.setEndRowIndex(10);
		List<Message> result = this.messageDao.getSendMessageList(messageSearchCondition);
		assertTrue(result.isEmpty());
	}
	
	@Test
	public void testPhysicalDeleteReceiver() {
		Message message = new Message();
		message.setUserId("user2");
		message.setMessageId(this.messageId);
		this.messageDao.physicalDeleteReceive(message);
		
		MessageSearchCondition messageSearchCondition =new MessageSearchCondition();
		messageSearchCondition.setUserId("user2");
		messageSearchCondition.setSearchColumn("contents");
		messageSearchCondition.setSearchWord(this.messageId+"Dao테스트입니다.");
		messageSearchCondition.setStartRowIndex(0);
		messageSearchCondition.setEndRowIndex(10);
		List<Message> result = this.messageDao.getReceiveMessageList(messageSearchCondition);
		assertTrue(result.isEmpty());
	}
	
	@Ignore
	public void testPhysicalDeleteStore() {
		Message message = new Message();
		message.setUserId("user1");
		message.setMessageId(this.messageId);
		this.messageDao.physicalDeleteStore(message);
	}
	
	@Test
	public void testCountReadMessage() {
		Integer result = this.messageDao.countReadMessage(this.messageId);
		assertNotNull(result);
	}
	
	@Test
	public void testCountNewMessage() {
		Integer result = this.messageDao.countReadMessage(this.messageId);
		assertNotNull(result);
	}
}
