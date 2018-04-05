package com.lgcns.ikep4.supportpack.activitystream.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.activitystream.model.ActivityStream;
import com.lgcns.ikep4.support.activitystream.model.ActivityStreamSearchCondition;
import com.lgcns.ikep4.support.activitystream.service.ActivityStreamService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:/configuration/spring/context-dao.xml",
		"classpath:/configuration/spring/context-service.xml" })
public class ActivityStreamServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private ActivityStreamService activityStreamService;

	@Autowired
	private IdgenService idgenService;

	private ActivityStream activityStream;

	private String activityStreamId;

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

		activityStream = new ActivityStream();

		activityStreamId = idgenService.getNextId();
		activityStream.setActivityStreamId(activityStreamId);
		activityStream.setItemTypeCode(IKepConstant.ITEM_TYPE_CODE_BBS);
		activityStream.setActivityCode(IKepConstant.ACTIVITY_CODE_DOC_EDIT);
		activityStream.setObjectId("11");
		activityStream.setObjectTitle("test");

		activityStreamService.create(activityStream, user, user.getUserId());

	}

	@Test
	public void create1() {

		activityStreamService.create(IKepConstant.ITEM_TYPE_CODE_BBS, IKepConstant.ACTIVITY_CODE_DOC_EDIT, "11", "test");
	}

	@Test
	public void create2() {

		activityStreamService.createForCollavoration(IKepConstant.ITEM_TYPE_CODE_BBS, IKepConstant.ACTIVITY_CODE_DOC_EDIT, "11", "test", "ANNOUNCE", "", "");
	}

	@Test
	public void create3() {

		activityStreamService.createForMessage(IKepConstant.ITEM_TYPE_CODE_BBS, IKepConstant.ACTIVITY_CODE_DOC_EDIT, "11", "test", "22", 1);
	}

	@Test
	public void read() {

		ActivityStream result = activityStreamService.read(activityStreamId);
	}

	@Test
	public void getAll() {

		ActivityStreamSearchCondition searchCondition = new ActivityStreamSearchCondition();

		SearchResult<ActivityStream> result = activityStreamService.getAll(searchCondition, user);
	}

}
