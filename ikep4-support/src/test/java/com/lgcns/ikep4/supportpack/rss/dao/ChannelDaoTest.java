package com.lgcns.ikep4.supportpack.rss.dao;

import static org.junit.Assert.assertNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lgcns.ikep4.support.rss.dao.ChannelDao;
import com.lgcns.ikep4.support.rss.model.Channel;
import com.lgcns.ikep4.support.rss.model.ChannelSearchCondition;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:/configuration/spring/context-dao.xml",
		"classpath:/configuration/spring/context-service.xml" })
public class ChannelDaoTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private ChannelDao channelDao;

	@Autowired
	private IdgenService idgenService;

	private Channel channel;

	private String channelId;

	@Before
	public void setUp() {

		channel = new Channel();

		channelId = idgenService.getNextId();
		channel.setChannelId(channelId);
		channel.setChannelTitle("11");
		channel.setChannelUrl("11");
		channel.setChannelDescription("11");
		channel.setChannelType("11");
		channel.setOwnerId("11");
		channel.setRegisterId("11");
		channel.setRegisterName("11");
		channel.setUpdaterId("11");
		channel.setUpdaterName("11");
		channel.setSortOrder("11");

		channelDao.create(channel);

	}

	@Test
	public void testGet() {
		Channel result = channelDao.get(channelId);
		Assert.assertNotNull(result);
	}

	@Test
	public void testCreate() {
		Channel result = channelDao.get(channelId);

		Assert.assertNotNull(result);

	}

	@Test
	public void testUpdate() {
		channel.setChannelTitle("22");

		channelDao.update(channel);

		Channel result = channelDao.get(channelId);

		Assert.assertEquals(channel.getChannelTitle(), result.getChannelTitle());
	}

	@Test
	public void testPhysicalDelete() {
		channelDao.remove(channelId);

		Channel result = channelDao.get(channelId);

		assertNull(result);
	}

	public void testExists() {
		// TODO Auto-generated method stub

	}

	public void testLogicalDelete() {
		// TODO Auto-generated method stub

	}

	@Test
	public void getAll() {

		ChannelSearchCondition searchCondition = new ChannelSearchCondition();
		List<Channel> result = channelDao.getAll(searchCondition);
		Assert.assertNotNull(result);
	}

	@Test
	public void countBySearchCondition() {

		ChannelSearchCondition searchCondition = new ChannelSearchCondition();
		Integer result = channelDao.countBySearchCondition(searchCondition);
		Assert.assertNotNull(result);
	}

	@Test
	public void selectSortTarget() {
		Map map = new HashMap();
		Channel result = channelDao.selectSortTarget(map);
		
	}
	
	
	@Test
	public void getSortOrder() {
		Map map = new HashMap();
		String result = channelDao.getSortOrder(map);
		
	}
	
	@Test
	public void checkChannel() {
		Map map = new HashMap();
		boolean result = channelDao.checkChannel(map);
		
	}
	
	@Test
	public void updateSortOrder() {
		channelDao.updateSortOrder(channel);
		
	}
	
	@Test
	public void getBoard() {
		channelDao.getBoard("11");
		
	}
	
	@Test
	public void getQna() {
		channelDao.getQna("11");
		
	}
	
	@Test
	public void getWorkspace() {
		channelDao.getWorkspace("11");
		
	}
	
	@Test
	public void getBlog() {
		channelDao.getBlog("11");
		
	}


}
