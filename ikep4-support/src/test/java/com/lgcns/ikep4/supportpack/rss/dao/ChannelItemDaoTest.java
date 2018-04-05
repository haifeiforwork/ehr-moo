package com.lgcns.ikep4.supportpack.rss.dao;

import static org.junit.Assert.assertNull;

import java.util.Date;
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

import com.lgcns.ikep4.support.rss.dao.ChannelItemDao;
import com.lgcns.ikep4.support.rss.model.ChannelItem;
import com.lgcns.ikep4.support.rss.model.ChannelSearchCondition;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:/configuration/spring/context-dao.xml",
		"classpath:/configuration/spring/context-service.xml" })
public class ChannelItemDaoTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private ChannelItemDao channelItemDao;

	@Autowired
	private IdgenService idgenService;

	private ChannelItem channelItem;

	private String channelItemId;

	@Before
	public void setUp() {

		channelItem = new ChannelItem();

		channelItemId = idgenService.getNextId();
		channelItem.setItemId(channelItemId);
		channelItem.setChannelId("11");
		channelItem.setItemTitle("11");
		channelItem.setItemUrl("11");
		channelItem.setItemDescription("11");
		channelItem.setItemRegister("11");
		channelItem.setItemPublishDate(new Date());

		channelItemDao.create(channelItem);

	}

	@Test
	public void testGet() {
		ChannelItem result = channelItemDao.get(channelItemId);
		Assert.assertNotNull(result);
	}

	@Test
	public void testCreate() {
		ChannelItem result = channelItemDao.get(channelItemId);

		Assert.assertNotNull(result);

	}

	public void testUpdate() {
		// TODO Auto-generated method stub
	}

	@Test
	public void testPhysicalDelete() {
		channelItemDao.remove(channelItemId);

		ChannelItem result = channelItemDao.get(channelItemId);

		assertNull(result);
	}

	@Test
	public void testExists() {
		Map map = new HashMap();
		boolean result = channelItemDao.checkItem(map);
	}

	public void testLogicalDelete() {
		// TODO Auto-generated method stub

	}

	@Test
	public void getAll() {

		ChannelSearchCondition searchCondition = new ChannelSearchCondition();
		List<ChannelItem> result = channelItemDao.getAll(searchCondition);
		Assert.assertNotNull(result);
	}

	@Test
	public void countBySearchCondition() {

		ChannelSearchCondition searchCondition = new ChannelSearchCondition();
		Integer result = channelItemDao.countBySearchCondition(searchCondition);
		Assert.assertNotNull(result);
	}

	@Test
	public void getBoardItemList() {

		channelItemDao.getBoardItemList(new HashMap());
	}

	@Test
	public void getQnaItemList() {

		channelItemDao.getQnaItemList(new HashMap());
	}
	
	@Test
	public void getWorkspaceItemList() {

		channelItemDao.getWorkspaceItemList(new HashMap());
	}
	
	@Test
	public void getBlogItemList() {

		channelItemDao.getBlogItemList(new HashMap());
	}

}
