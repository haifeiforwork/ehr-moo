package com.lgcns.ikep4.supportpack.rss.service;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.rss.model.Channel;
import com.lgcns.ikep4.support.rss.model.ChannelSearchCondition;
import com.lgcns.ikep4.support.rss.service.ChannelService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:/configuration/spring/context-dao.xml",
		"classpath:/configuration/spring/context-service.xml" })
public class ChannelServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private ChannelService channelService;

	@Autowired
	private IdgenService idgenService;

	@Autowired
	protected MessageSource messageSource;

	private Channel channel;

	private String channelId;

	MockHttpServletRequest request;

	MockHttpServletResponse response;

	MockHttpSession session;

	User user;

	@Before
	public void setUp() {

		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		MockHttpSession session = new MockHttpSession();

		user = new User();
		user.setUserId("user1");
		user.setUserName("사용자1");
		user.setLocaleCode("ko");
		
		session.setAttribute("ikep.user", user);
		request.setSession(session);
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

		channel = new Channel();

		channelId = idgenService.getNextId();

		channel.setChannelId(channelId);
		channel.setChannelTitle("test");
		channel.setChannelUrl("http:www.daum.net");
		channel.setChannelDescription("ddd");
		channel.setOwnerId(user.getUserId());
		channel.setRegisterId(user.getUserId());
		channel.setRegisterName(user.getUserName());
		channel.setUpdaterId(user.getUserId());
		channel.setUpdaterName(user.getUserName());

		channelService.create(channel);
	}

	@Test
	public void update() {

		channelService.update(channel);
	}

	@Test
	public void delete() {

		channelService.delete(channelId);
	}

	@Test
	public void read() {

		channelService.read(channelId);
	}

	@Test
	public void changeSort() {

		ChannelSearchCondition searchCondition = new ChannelSearchCondition();
		searchCondition.setOwnerId(user.getUserId());

		SearchResult<Channel> result = channelService.getAllChannel(searchCondition);

		channelService.changeSort(result.getEntity(), "11", 1, user.getUserId());
	}

	@Test
	public void getAll() {

		ChannelSearchCondition searchCondition = new ChannelSearchCondition();
		searchCondition.setOwnerId(user.getUserId());

		SearchResult<Channel> result = channelService.getAllChannel(searchCondition);
	}

	@Test
	public void checkChannel() {

		Map map = new HashMap();
		map.put("channelUrl", channel.getChannelUrl());
		map.put("ownerId", channel.getOwnerId());

		channelService.checkChannel(map);
	}

	@Test
	public void readChannel() {

		ChannelSearchCondition searchCondition = new ChannelSearchCondition();
		searchCondition.setOwnerId(user.getUserId());

		try {
			channelService.readChannel(searchCondition, request);
		} catch (Exception e) {
		}
	}

	@Test
	public void sendChannel() {

		try {
			channelService.sendChannel(messageSource, "rss", "BD", "20000000", request, user);
		} catch (Exception e) {
		}
	}
	
	@Test
	public void getMetaTitle() {

		try {
			channelService.getMetaTitle(messageSource, "BD", "20000000");
		} catch (Exception e) {
		}
	}
	
	@Test
	public void readChannelForPortlet() {

		try {
			channelService.readChannelForPortlet(channel.getChannelUrl());
		} catch (Exception e) {
		}
	}

}
