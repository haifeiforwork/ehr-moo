package com.lgcns.ikep4.socialpack.microblogging.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lgcns.ikep4.socialpack.microblogging.model.Mblog;
import com.lgcns.ikep4.socialpack.microblogging.model.QueryReturn;
import com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


/**
 * 
 * MblogService 테스트 케이스
 *
 * @author 최성우
 * @version $Id: MblogServiceTest.java 16246 2011-08-18 04:48:28Z giljae $
 */
public class MblogServiceTest extends BaseServiceTestCase {

	MockHttpServletRequest request;

	MockHttpServletResponse response;

	MockHttpSession session;

	User user;

	@Autowired
	private IdgenService idgenService;
	
	@Autowired
	private MblogService mblogService;
	
	private Mblog mblog;

	String pk = "";
	String mbGroupId = "";

	@Before
	public void setUp() {

		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		session = new MockHttpSession();

		user = new User();
		user.setUserId("user100");
		user.setUserName("사용자100");
		user.setPortalId("P000001");
		session.setAttribute("ikep.user", user);
		
		request.setSession(session);
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

		mblog = new Mblog();

		pk = idgenService.getNextId();
		
		mblog.setMblogId(pk);
		mblog.setMblogType("0");
		mblog.setContents("Unit Test~!");
		mblog.setIsRetweetAllowed("1");
		mblog.setRegisterId(user.getUserId());
		mblog.setRegisterName(user.getUserName());

		mblogService.create(mblog, user);
	}

	@Test
	public void create() {
		Mblog returnMblog = mblogService.read(this.pk);
		assertNotNull(returnMblog);	
	}
	
	@Test
	public void exists() {
		boolean exist = mblogService.exists(this.pk);
		assertTrue(exist);	
	}

	@Test
	public void read() {
		Mblog mblog = mblogService.read(this.pk);
		assertNotNull(mblog);	
	}

	@Test
	public void delete() {
		mblogService.delete(this.pk);
		Mblog mblog = this.mblogService.read(this.pk);
		assertNull(mblog);
	}

	@Test
	public void timelineList() {
		MblogSearchCondition mblogSearchCondition = new MblogSearchCondition();
		mblogSearchCondition.setOwnerId(user.getUserId());
		mblogSearchCondition.setLoginId(user.getUserId());
		
		List<Mblog> list = this.mblogService.timelineList(mblogSearchCondition);
		assertNotNull(list);
	}

	@Test
	public void timelineListUserOnly() {
		MblogSearchCondition mblogSearchCondition = new MblogSearchCondition();
		mblogSearchCondition.setOwnerId(user.getUserId());
		mblogSearchCondition.setLoginId(user.getUserId());
		
		List<Mblog> list = this.mblogService.timelineListUserOnly(mblogSearchCondition);
		assertNotNull(list);
	}
	
	@Test
	public void threadList() {
		MblogSearchCondition mblogSearchCondition = new MblogSearchCondition();
		mblogSearchCondition.setOwnerId(user.getUserId());
		mblogSearchCondition.setLoginId(user.getUserId());
		
		List<Mblog> list = this.mblogService.threadList(mblogSearchCondition);
		assertNotNull(list);
	}

	@Test
	public void timelineAllList() {
		MblogSearchCondition mblogSearchCondition = new MblogSearchCondition();
		mblogSearchCondition.setOwnerId(user.getUserId());
		mblogSearchCondition.setLoginId(user.getUserId());
		mblogSearchCondition.setStandardType("pre");
		
		List<Mblog> list = this.mblogService.timelineAllList(mblogSearchCondition);
		assertNotNull(list);
	}

	@Test
	public void threadAllList() {
		MblogSearchCondition mblogSearchCondition = new MblogSearchCondition();
		mblogSearchCondition.setOwnerId(user.getUserId());
		mblogSearchCondition.setLoginId(user.getUserId());
		mblogSearchCondition.setStandardType("pre");
		
		List<Mblog> list = this.mblogService.threadAllList(mblogSearchCondition);
		assertNotNull(list);
	}

	@Test
	public void addonListByAddonType() {
		MblogSearchCondition mblogSearchCondition = new MblogSearchCondition();
		mblogSearchCondition.setOwnerId(user.getUserId());
		mblogSearchCondition.setLoginId(user.getUserId());
		
		List<Mblog> list = this.mblogService.addonListByAddonType(mblogSearchCondition);
		//assertNotNull(list);
	}

	@Test
	public void retwitByMeList() {
		MblogSearchCondition mblogSearchCondition = new MblogSearchCondition();
		mblogSearchCondition.setOwnerId(user.getUserId());
		mblogSearchCondition.setLoginId(user.getUserId());
		
		List<Mblog> list = this.mblogService.retwitByMeList(mblogSearchCondition);
		//assertNotNull(list);
	}

	@Test
	public void retwitByOtherList() {
		MblogSearchCondition mblogSearchCondition = new MblogSearchCondition();
		mblogSearchCondition.setOwnerId(user.getUserId());
		mblogSearchCondition.setLoginId(user.getUserId());
		
		List<Mblog> list = this.mblogService.retwitByOtherList(mblogSearchCondition);
		//assertNotNull(list);
	}

	@Test
	public void myMentionList() {
		MblogSearchCondition mblogSearchCondition = new MblogSearchCondition();
		mblogSearchCondition.setOwnerId(user.getUserId());
		mblogSearchCondition.setLoginId(user.getUserId());
		
		List<Mblog> list = this.mblogService.myMentionList(mblogSearchCondition);
		//assertNotNull(list);
	}

	@Test
	public void favoriteList() {
		MblogSearchCondition mblogSearchCondition = new MblogSearchCondition();
		mblogSearchCondition.setOwnerId(user.getUserId());
		mblogSearchCondition.setLoginId(user.getUserId());
		
		List<Mblog> list = this.mblogService.favoriteList(mblogSearchCondition);
		//assertNotNull(list);
	}

	@Test
	public void tweetListByTag() {
		MblogSearchCondition mblogSearchCondition = new MblogSearchCondition();
		mblogSearchCondition.setOwnerId(user.getUserId());
		mblogSearchCondition.setLoginId(user.getUserId());
		mblogSearchCondition.setPortalId(user.getPortalId());
		
		List<Mblog> list = this.mblogService.tweetListByTag(mblogSearchCondition);
		//assertNotNull(list);
	}

	@Test
	public void userListByHashTag() {
		MblogSearchCondition mblogSearchCondition = new MblogSearchCondition();
		mblogSearchCondition.setOwnerId(user.getUserId());
		mblogSearchCondition.setLoginId(user.getUserId());
		mblogSearchCondition.setPortalId(user.getPortalId());
		
		List<User> list = this.mblogService.userListByHashTag(mblogSearchCondition);
		//assertNotNull(list);
	}

	@Test
	public void userListByMentionTag() {
		MblogSearchCondition mblogSearchCondition = new MblogSearchCondition();
		mblogSearchCondition.setOwnerId(user.getUserId());
		mblogSearchCondition.setLoginId(user.getUserId());
		mblogSearchCondition.setPortalId(user.getPortalId());
		mblogSearchCondition.setSearchWord("");
		
		List<User> list = this.mblogService.userListByMentionTag(mblogSearchCondition);
		//assertNotNull(list);
	}

	@Test
	public void tweetListByGroupTag() {
		MblogSearchCondition mblogSearchCondition = new MblogSearchCondition();
		mblogSearchCondition.setOwnerId(user.getUserId());
		mblogSearchCondition.setLoginId(user.getUserId());
		mblogSearchCondition.setPortalId(user.getPortalId());
		
		List<Mblog> list = this.mblogService.tweetListByGroupTag(mblogSearchCondition);
		//assertNotNull(list);
	}

	@Test
	public void userListByGroupHashTag() {
		MblogSearchCondition mblogSearchCondition = new MblogSearchCondition();
		mblogSearchCondition.setOwnerId(user.getUserId());
		mblogSearchCondition.setLoginId(user.getUserId());
		mblogSearchCondition.setPortalId(user.getPortalId());
		
		List<User> list = this.mblogService.userListByGroupHashTag(mblogSearchCondition);
		//assertNotNull(list);
	}

	@Test
	public void userListByGroupMentionTag() {
		MblogSearchCondition mblogSearchCondition = new MblogSearchCondition();
		mblogSearchCondition.setOwnerId(user.getUserId());
		mblogSearchCondition.setLoginId(user.getUserId());
		mblogSearchCondition.setPortalId(user.getPortalId());
		
		List<User> list = this.mblogService.userListByGroupMentionTag(mblogSearchCondition);
		//assertNotNull(list);
	}

	@Test
	public void parentNReplyListByMblogId() {		
		MblogSearchCondition mblogSearchCondition = new MblogSearchCondition();
		List<Mblog> list = this.mblogService.parentNReplyListByMblogId(mblogSearchCondition);
		//assertNotNull(list);
	}

	@Test
	public void retweetUserListByMblogId() {
		MblogSearchCondition mblogSearchCondition = new MblogSearchCondition();
		mblogSearchCondition.setOwnerId(user.getUserId());
		mblogSearchCondition.setLoginId(user.getUserId());
		
		List<User> list = this.mblogService.retweetUserListByMblogId(mblogSearchCondition);
		//assertNotNull(list);
	}

	@Test
	public void retweetStatisticsByMblogId() {
		MblogSearchCondition mblogSearchCondition = new MblogSearchCondition();
		mblogSearchCondition.setOwnerId(user.getUserId());
		mblogSearchCondition.setLoginId(user.getUserId());
		
		List<QueryReturn> list = this.mblogService.retweetStatisticsByMblogId(pk);
		//assertNotNull(list);
	}

	@Test
	public void mentionedUserListByMblogId() {
		MblogSearchCondition mblogSearchCondition = new MblogSearchCondition();
		mblogSearchCondition.setOwnerId(user.getUserId());
		mblogSearchCondition.setLoginId(user.getUserId());
		
		List<User> list = this.mblogService.mentionedUserListByMblogId(mblogSearchCondition);
		//assertNotNull(list);
	}

	@Test
	public void userInfoByUserId() {
		MblogSearchCondition mblogSearchCondition = new MblogSearchCondition();
		mblogSearchCondition.setOwnerId(user.getUserId());
		mblogSearchCondition.setLoginId(user.getUserId());
		
		User  user= this.mblogService.userInfoByUserId(mblogSearchCondition);
		//assertNotNull(list);
	}

	@Test
	public void myTweetCount() {		
		int myTweetCount = this.mblogService.myTweetCount(user.getUserId());
		//assertNotNull(list);
	}

	@Test
	public void groupTimelineList() {
		MblogSearchCondition mblogSearchCondition = new MblogSearchCondition();
		mblogSearchCondition.setOwnerId(user.getUserId());
		mblogSearchCondition.setLoginId(user.getUserId());
		
		List<Mblog> list = this.mblogService.groupTimelineList(mblogSearchCondition);
		//assertNotNull(list);
	}

	@Test
	public void groupThreadList() {
		MblogSearchCondition mblogSearchCondition = new MblogSearchCondition();
		mblogSearchCondition.setOwnerId(user.getUserId());
		mblogSearchCondition.setLoginId(user.getUserId());
		
		List<Mblog> list = this.mblogService.groupThreadList(mblogSearchCondition);
		//assertNotNull(list);
	}

	@Test
	public void groupAddonListByAddonType() {
		MblogSearchCondition mblogSearchCondition = new MblogSearchCondition();
		mblogSearchCondition.setOwnerId(user.getUserId());
		mblogSearchCondition.setLoginId(user.getUserId());
		
		List<Mblog> list = this.mblogService.groupAddonListByAddonType(mblogSearchCondition);
		//assertNotNull(list);
	}

	@Test
	public void followingList() {
		MblogSearchCondition mblogSearchCondition = new MblogSearchCondition();
		mblogSearchCondition.setOwnerId(user.getUserId());
		mblogSearchCondition.setLoginId(user.getUserId());
		
		List<User> list = this.mblogService.followingList(mblogSearchCondition);
		//assertNotNull(list);
	}

	@Test
	public void followerList() {
		MblogSearchCondition mblogSearchCondition = new MblogSearchCondition();
		mblogSearchCondition.setOwnerId(user.getUserId());
		mblogSearchCondition.setLoginId(user.getUserId());
		
		List<User> list = this.mblogService.followerList(mblogSearchCondition);
		//assertNotNull(list);
	}

	@Test
	public void userIdForAutoComplete() {
		MblogSearchCondition mblogSearchCondition = new MblogSearchCondition();
		mblogSearchCondition.setOwnerId(user.getUserId());
		mblogSearchCondition.setLoginId(user.getUserId());
		
		List<String> list = this.mblogService.userIdForAutoComplete(mblogSearchCondition);
		//assertNotNull(list);
	}

	@Test
	public void groupTweetCount() {
		MblogSearchCondition mblogSearchCondition = new MblogSearchCondition();
		mblogSearchCondition.setOwnerId(user.getUserId());
		mblogSearchCondition.setLoginId(user.getUserId());
		
		int groupTweetCount = this.mblogService.groupTweetCount(mbGroupId);
		//assertNotNull(list);
	}

	@Test
	public void retwitFromOutsideList() {
		MblogSearchCondition mblogSearchCondition = new MblogSearchCondition();
		mblogSearchCondition.setOwnerId(user.getUserId());
		mblogSearchCondition.setLoginId(user.getUserId());
		
		List<Mblog> list = this.mblogService.retwitFromOutsideList(mblogSearchCondition);
		//assertNotNull(list);
	}

	@Test
	public void retwitToOutsideList() {
		MblogSearchCondition mblogSearchCondition = new MblogSearchCondition();
		mblogSearchCondition.setOwnerId(user.getUserId());
		mblogSearchCondition.setLoginId(user.getUserId());
		
		List<Mblog> list = this.mblogService.retwitToOutsideList(mblogSearchCondition);
		//assertNotNull(list);
	}

	@Test
	public void recentFileListByAddonType() {
		MblogSearchCondition mblogSearchCondition = new MblogSearchCondition();
		mblogSearchCondition.setOwnerId(user.getUserId());
		mblogSearchCondition.setLoginId(user.getUserId());
		
		List<Mblog> list = this.mblogService.recentFileListByAddonType(mblogSearchCondition);
		//assertNotNull(list);
	}

	@Test
	public void workspaceTimelineList() {
		MblogSearchCondition mblogSearchCondition = new MblogSearchCondition();
		mblogSearchCondition.setOwnerId(user.getUserId());
		mblogSearchCondition.setLoginId(user.getUserId());
		
		List<Mblog> list = this.mblogService.workspaceTimelineList(mblogSearchCondition);
		//assertNotNull(list);
	}

	@Test
	public void teamTimelineList() {
		MblogSearchCondition mblogSearchCondition = new MblogSearchCondition();
		mblogSearchCondition.setOwnerId(user.getUserId());
		mblogSearchCondition.setLoginId(user.getUserId());
		
		List<Mblog> list = this.mblogService.teamTimelineList(mblogSearchCondition);
		//assertNotNull(list);
	}

	@Test
	public void followingTimelineList() {
		MblogSearchCondition mblogSearchCondition = new MblogSearchCondition();
		mblogSearchCondition.setOwnerId(user.getUserId());
		mblogSearchCondition.setLoginId(user.getUserId());
		
		List<Mblog> list = this.mblogService.followingTimelineList(mblogSearchCondition);
		//assertNotNull(list);
	}

}
