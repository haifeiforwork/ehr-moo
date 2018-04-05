package com.lgcns.ikep4.supportpack.message.test.service;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.message.model.MessageAdmin;
import com.lgcns.ikep4.support.message.model.MessageChart;
import com.lgcns.ikep4.support.message.model.MessageMonitor;
import com.lgcns.ikep4.support.message.search.MessageMonitorSearchCondition;
import com.lgcns.ikep4.support.message.service.MessageAdminService;

public class MessageAdminServiceTest extends BaseServiceTestCase {
	
	@Autowired
	private MessageAdminService messageAdminService;
	
	@Before
	public void setUp() throws Exception {
		
	}
	
	@Ignore
	public void testCreate() {
		MessageAdmin messageAdmin = new MessageAdmin();
	}
	
	@Test
	public void testGetUserAdmin() {
		MessageAdmin result = this.messageAdminService.getUserAdmin("user1");
		assertNotNull(result);
	}
	
	@Test
	public void testGetUserVolumnInfoList() {
		MessageMonitorSearchCondition messageMonitorSearchCondition = new MessageMonitorSearchCondition();
		messageMonitorSearchCondition.setPortalId("P000001");
		
		SearchResult<MessageMonitor> result = this.messageAdminService.getUserVolumnInfoList(messageMonitorSearchCondition);
		assertNotNull(result);
	}
	
	@Test
	public void testMessageWeekChartList() {
		List<MessageChart> result = this.messageAdminService.messageWeekChartList();
		assertNotNull(result);
	}
	
	@Test
	public void testMessageDayChartList() {
		List<MessageChart> result = this.messageAdminService.messageDayChartList();
		assertNotNull(result);
	}
}
