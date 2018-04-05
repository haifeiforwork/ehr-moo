package com.lgcns.ikep4.servicepack.seamlessmessage.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.lgcns.ikep4.servicepack.seamless.model.ContacUser;
import com.lgcns.ikep4.servicepack.seamless.model.MessageBox;
import com.lgcns.ikep4.servicepack.seamless.model.SeamlessmessageUserSeting;
import com.lgcns.ikep4.servicepack.seamless.search.SeamlessmessageSearchCondition;
import com.lgcns.ikep4.servicepack.seamless.service.SeamlessMessageService;
import com.lgcns.ikep4.servicepack.seamless.util.SeamlessMessageConstance;
import com.lgcns.ikep4.servicepack.usagetracker.service.UtStatisticsService;

public class SeamlessMessageServiceTest extends BaseServiceTestCase {

	@Autowired
	private SeamlessMessageService seamlessMessageService;
	
	@Autowired
	UtStatisticsService utStatisticsService;
	
	@Before
	public void setUp() throws Exception {}
	
	@Test
	public void testReceiveList() {
		SeamlessmessageSearchCondition searchCondition = new SeamlessmessageSearchCondition();
		searchCondition.setUserId("user1");
		List<MessageBox> result = this.seamlessMessageService.receiveList(searchCondition);
		assertNotNull(result);
	}
	
	@Test
	public void testGetReceiveBox() {
		SeamlessmessageSearchCondition searchCondition = new SeamlessmessageSearchCondition();
		searchCondition.setUserId("user20");
		searchCondition.setMessageType("Mail");
		List<MessageBox> receiveList = this.seamlessMessageService.receiveList(searchCondition);
		String messageId = receiveList.get(0).getMessageId();
		searchCondition.setMessageId(messageId);
		MessageBox result = this.seamlessMessageService.getReceiveBox(searchCondition);
		assertNotNull(result);
	}

	@Test
	public void testRemoveReceiveBox() {
		SeamlessmessageSearchCondition searchCondition = new SeamlessmessageSearchCondition();
		searchCondition.setUserId("user20");
		searchCondition.setMessageType("Mail");
		List<MessageBox> receiveList = this.seamlessMessageService.receiveList(searchCondition);
		searchCondition.setMessageId(receiveList.get(0).getMessageId());
		this.seamlessMessageService.removeReceiveBox(searchCondition);
		MessageBox result = this.seamlessMessageService.getReceiveBox(searchCondition);
		assertNull(result);
	}

	@Test
	public void testSendList() {
		SeamlessmessageSearchCondition searchCondition = new SeamlessmessageSearchCondition();
		searchCondition.setUserId("user1");
		List<MessageBox> result = this.seamlessMessageService.sendList(searchCondition);
		assertNotNull(result);
	}
	
	@Test
	public void testGetSendBox() {
		SeamlessmessageSearchCondition searchCondition = new SeamlessmessageSearchCondition();
		searchCondition.setUserId("user20");
		searchCondition.setMessageType("Mail");
		List<MessageBox> sendList = this.seamlessMessageService.sendList(searchCondition);
		searchCondition.setMessageId(sendList.get(0).getMessageId());
		MessageBox result = this.seamlessMessageService.getSendBox(searchCondition);
		assertNotNull(result);
	}

	@Test
	public void testRemoveSendBox() {
		SeamlessmessageSearchCondition searchCondition = new SeamlessmessageSearchCondition();
		searchCondition.setUserId("user20");
		searchCondition.setMessageType("Mail");
		List<MessageBox> sendList = this.seamlessMessageService.sendList(searchCondition);
		searchCondition.setMessageId(sendList.get(0).getMessageId());
		this.seamlessMessageService.removeSendBox(searchCondition);
		MessageBox result = this.seamlessMessageService.getSendBox(searchCondition);
		assertNull(result);
	}

	@Test
	public void testContactHistoryList() {
		SeamlessmessageSearchCondition searchCondition = new SeamlessmessageSearchCondition();
		searchCondition.setUserId("user20");
		List<MessageBox> receiveList = this.seamlessMessageService.receiveList(searchCondition);
		searchCondition.setMessageId(receiveList.get(0).getMessageId());
		searchCondition.setGuestId(receiveList.get(0).getSenderId());
		searchCondition.setRowNum(1);
		List<MessageBox> result = this.seamlessMessageService.contactHistoryList(searchCondition);
		assertNotNull(result);
	}

	@Test
	public void testLastContactDate() {
		SeamlessmessageSearchCondition searchCondition = new SeamlessmessageSearchCondition();
		searchCondition.setUserId("user20");
		List<MessageBox> receiveList = this.seamlessMessageService.receiveList(searchCondition);
		searchCondition.setGuestId(receiveList.get(0).getSenderId());
		try{
			Date result = this.seamlessMessageService.lastContactDate(searchCondition);
			assertTrue(true);
		} catch(Exception e){
			assertTrue(false);
		}
		
	}

	@Test
	public void testContactUserList() {
		SeamlessmessageSearchCondition searchCondition = new SeamlessmessageSearchCondition();
		searchCondition.setUserId("user20");
		searchCondition.setUserIdR("user20");
		searchCondition.setUserIdS("user20");
		searchCondition.setStartRnum(10);
		searchCondition.setEndRnum(SeamlessMessageConstance.contactViewCount);
		List<ContacUser> result = this.seamlessMessageService.contactUserList(searchCondition);
		assertNotNull(result);
	}

	@Ignore
	public void testContackImapMail() {
		fail("Not yet implemented"); // TODO
	}

	@Ignore
	public void testCreateMail() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testGetUserSeting() {
		SeamlessmessageUserSeting result = this.seamlessMessageService.getUserSeting("user20");
		assertNotNull(result);
	}

	@Test
	public void testUpdateUserSeting() {
		SeamlessmessageUserSeting seamlessmessageUserSeting = new SeamlessmessageUserSeting();
		seamlessmessageUserSeting.setRegisterId("user20");
		seamlessmessageUserSeting.setIsSourceDelete(0);
		seamlessmessageUserSeting.setAutoConnect(0);
		seamlessmessageUserSeting.setUpdaterId("user20");
		this.seamlessMessageService.updateUserSeting(seamlessmessageUserSeting);
		SeamlessmessageUserSeting result = this.seamlessMessageService.getUserSeting("user20");
		assertTrue(0 == result.getIsSourceDelete());
	}

	
	@Test
	//@Ignore
	@Rollback(false)
	public void testUtStatisticsBatch() {
		utStatisticsService.utStatisticsBatch();
	}
}
