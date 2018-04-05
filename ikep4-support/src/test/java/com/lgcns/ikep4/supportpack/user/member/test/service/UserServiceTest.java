package com.lgcns.ikep4.supportpack.user.member.test.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lgcns.ikep4.support.user.group.model.Group;
import com.lgcns.ikep4.support.user.group.service.GroupService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.model.UserSearchCondition;
import com.lgcns.ikep4.support.user.member.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/configuration/spring/context-dao.xml", "classpath:/configuration/spring/context-service.xml"})
public class UserServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private GroupService groupService;
	
	/**
	 * Log variable for all child classes. Uses LogFactory.getLog(getClass())
	 * from Commons Logging
	 */
	protected final Log log = LogFactory.getLog(getClass());
	
	private User user;
	private Group group;
	private UserSearchCondition searchCondition;
	private String portalId = "";
	
	@Before
	public void setMethod() {
		
		// testGroup_A 세팅
		group = new Group();
		group.setGroupId("temp");
		group.setGroupName("Test Group A");
		group.setChildGroupCount("0");
		group.setSortOrder("0000000001000");
		group.setRegisterId("admin");
		group.setRegisterName("관리자");
		group.setUpdaterId("admin");
		group.setUpdaterName("관리자");
		
		// User 세팅
		user = new User();
		user.setUserId("testcaseUser");
		user.setUserPassword("portal");
		user.setUserStatus("C");
		user.setUserName("테스트케이스사용자");
		user.setUserEnglishName("Test Case User");
		user.setTeamName("테스트팀");
		user.setLeader("0");
		user.setRegisterId("admin");
		user.setRegisterName("관리자");
		user.setUpdaterId("admin");
		user.setUpdaterName("관리자");
		user.setGroupId("temp");
		
		searchCondition = new UserSearchCondition();
		
		portalId = "P000001";
	}
	
	@Test
	public void create() {
		
		List<Group> tempGroupList = new ArrayList<Group>();
		for(int i=5; i<7; i++) {
			String id = "GD000001"+i;
			Group group = groupService.read(id);
			tempGroupList.add(group);
		}
		
		user.setGroupList(tempGroupList);
		user.setGroupId("GD0000187");
		user.setIsRepresentGroup("1");
		userService.create(user);
		
		assertNotNull(userService.read(user.getUserId()));
	}
	
	@Test
	public void createForExcel() {
		
		List<Group> tempGroupList = new ArrayList<Group>();
		for(int i=5; i<7; i++) {
			String id = "GD000001"+i;
			Group group = groupService.read(id);
			tempGroupList.add(group);
		}
		
		user.setGroupList(tempGroupList);
		user.setGroupId("GD0000187");
		user.setIsRepresentGroup("1");
		userService.createForExcel(user);
		
		assertNotNull(userService.read(user.getUserId()));
	}
	
	@Test
	public void exists() {
		
		List<Group> tempGroupList = new ArrayList<Group>();
		for(int i=5; i<7; i++) {
			String id = "GD000001"+i;
			Group group = groupService.read(id);
			tempGroupList.add(group);
		}
		
		user.setGroupList(tempGroupList);
		user.setGroupId("GD0000187");
		user.setIsRepresentGroup("1");
		userService.create(user);
		
		assertTrue(userService.exists(userService.read(user.getUserId()).getUserId()));
	}
	
	@Test
	public void update() {
		
		List<Group> tempGroupList = new ArrayList<Group>();
		for(int i=5; i<7; i++) {
			String id = "GD000001"+i;
			Group group = groupService.read(id);
			tempGroupList.add(group);
		}
		
		user.setGroupList(tempGroupList);
		user.setGroupId("GD0000187");
		user.setIsRepresentGroup("1");
		userService.create(user);
		
		User preUser = userService.read(user.getUserId());
		
		preUser.setUserEnglishName("updatedEngName");
		preUser.setGroupList(tempGroupList);
		preUser.setLeader("1");
		preUser.setGroupId("GD0000187");
		user.setIsRepresentGroup("1");
		userService.update(preUser);
		
		User postUser = userService.read(user.getUserId());
		
		assertEquals(preUser.getUserId(), postUser.getUserId());
		assertNotSame(preUser.getUserEnglishName(), postUser.getUserEnglishName());
		assertEquals("updatedEngName", postUser.getUserEnglishName());
		
	}
	
	@Test
	public void updateForExcel() {
		
		List<Group> tempGroupList = new ArrayList<Group>();
		for(int i=5; i<7; i++) {
			String id = "GD000001"+i;
			Group group = groupService.read(id);
			tempGroupList.add(group);
		}
		
		user.setGroupList(tempGroupList);
		user.setGroupId("GD0000187");
		user.setIsRepresentGroup("1");
		userService.create(user);
		
		User preUser = userService.read(user.getUserId());
		
		preUser.setUserEnglishName("updatedEngName");
		preUser.setGroupList(tempGroupList);
		preUser.setLeader("1");
		preUser.setGroupId("GD0000002");
		user.setIsRepresentGroup("1");
		userService.updateForExcel(preUser);
		
		User postUser = userService.read(user.getUserId());
		
		assertEquals(preUser.getUserId(), postUser.getUserId());
		assertNotSame(preUser.getUserEnglishName(), postUser.getUserEnglishName());
		assertEquals("updatedEngName", postUser.getUserEnglishName());
		
	}
	
	@Test
	public void delete() {
		
		List<Group> tempGroupList = new ArrayList<Group>();
		for(int i=5; i<7; i++) {
			String id = "GD000001"+i;
			Group group = groupService.read(id);
			tempGroupList.add(group);
		}
		
		user.setGroupList(tempGroupList);
		user.setGroupId("GD0000187");
		user.setIsRepresentGroup("1");
		userService.create(user);
		
		User preUser = userService.read(user.getUserId());
		
		userService.delete(preUser);
		
		assertNull(userService.read(user.getUserId()));
	}
	
	@Test
	public void list() {
		searchCondition.isEmptyRecord();
	}
	
	@Test
	public void selectTimezoneAll() {
		
		@SuppressWarnings("rawtypes")
		List result = userService.selectTimezoneAll("en");
		
		assertNotNull(result);
	}
	
	@Test
	public void selectLocaleCodeAll() {
		
		@SuppressWarnings("rawtypes")
		List result = userService.selectLocaleCodeAll("en");
		
		assertNotNull(result);
	}
	
	@Test
	public void selectJobClassAll() {
		
		@SuppressWarnings("rawtypes")
		List result = userService.selectJobClassAll(portalId);
		
		assertNotNull(result);
	}
	
	@Test
	public void selectJobRankAll() {
		
		@SuppressWarnings("rawtypes")
		List result = userService.selectJobRankAll(portalId);
		
		assertNotNull(result);
	}
	
	@Test
	public void selectJobPositionAll() {
		
		@SuppressWarnings("rawtypes")
		List result = userService.selectJobPositionAll(portalId);
		
		assertNotNull(result);
	}
	
	@Test
	public void selectJobTitleAll() {
		
		@SuppressWarnings("rawtypes")
		List result = userService.selectJobTitleAll(portalId);
		
		assertNotNull(result);
	}
	
	@Test
	public void selectJobDutyAll() {
		
		@SuppressWarnings("rawtypes")
		List result = userService.selectJobDutyAll(portalId);
		
		assertNotNull(result);
	}
	
	@Test
	public void selectJobCode() {
		
		List<Group> tempGroupList = new ArrayList<Group>();
		for(int i=5; i<7; i++) {
			String id = "GD000001"+i;
			Group group = groupService.read(id);
			tempGroupList.add(group);
		}
		
		user.setGroupList(tempGroupList);
		user.setGroupId("GD0000187");
		user.setIsRepresentGroup("1");
		userService.create(user);
		
		User preUser = userService.read(user.getUserId());
		preUser.setGroupId("GD0000187");

		Map<String,String> param = new HashMap<String,String>();
		param.put("paramField", "group_name");
		param.put("paramTable", "ikep4_ev_group");
		param.put("paramCondition", "group_id");
		param.put("paramValue", preUser.getGroupId());
		
		String result = userService.selectJobCode(param);
		
		assertNotNull(result);
	}
	
	@Test
	public void selectForPassword() {
		
		List<Group> tempGroupList = new ArrayList<Group>();
		for(int i=5; i<7; i++) {
			String id = "GD000001"+i;
			Group group = groupService.read(id);
			tempGroupList.add(group);
		}
		
		user.setGroupList(tempGroupList);
		user.setGroupId("GD0000187");
		user.setIsRepresentGroup("1");
		userService.create(user);
		
		searchCondition.setUserId("testcaseUser");
		
		List<User> result = userService.selectForPassword(searchCondition);
		
		assertNotNull(result);
	}
	
	@Test
	public void updateForPassword() {
		
		List<Group> tempGroupList = new ArrayList<Group>();
		for(int i=5; i<7; i++) {
			String id = "GD000001"+i;
			Group group = groupService.read(id);
			tempGroupList.add(group);
		}
		
		List<User> param = new ArrayList<User>();
		
		for(int i=0; i<10; i++) {
			user = new User();
			user.setGroupList(tempGroupList);
			user.setGroupId("GD0000187");
			user.setIsRepresentGroup("1");
			user.setUserId("testcaseUser"+i);
			user.setUserPassword("portal");
			user.setUserStatus("C");
			user.setUserName("테스트케이스사용자");
			user.setUserEnglishName("Test Case User");
			user.setTeamName("테스트팀");
			user.setLeader("0");
			user.setRegisterId("admin");
			user.setRegisterName("관리자");
			user.setUpdaterId("admin");
			user.setUpdaterName("관리자");
			
			userService.create(user);
		}
		
		for(int i=0; i<10; i++) {
			user = userService.read("testcaseUser"+i);
			user.setUserPassword("xxxx");
			param.add(user);
		}
		
		userService.updateForPassword(param);
		
		assertEquals("xxxx", userService.read("testcaseUser1").getUserPassword());
	}

}
