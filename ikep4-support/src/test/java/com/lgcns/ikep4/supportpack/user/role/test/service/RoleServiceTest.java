package com.lgcns.ikep4.supportpack.user.role.test.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
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

import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;
import com.lgcns.ikep4.support.user.group.model.Group;
import com.lgcns.ikep4.support.user.group.service.GroupService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.service.UserService;
import com.lgcns.ikep4.support.user.role.model.Role;
import com.lgcns.ikep4.support.user.role.model.RoleType;
import com.lgcns.ikep4.support.user.role.service.RoleService;
import com.lgcns.ikep4.util.lang.StringUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/configuration/spring/context-dao.xml", "classpath:/configuration/spring/context-service.xml"})
public class RoleServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private GroupService groupService;
	
	@Autowired
	private UserService userService;

	/**
	 * Log variable for all child classes. Uses LogFactory.getLog(getClass())
	 * from Commons Logging
	 */
	protected final Log log = LogFactory.getLog(getClass());
	
	private Role roleCreate;
	
	private List<User> testUserList;
	private List<Group> testGroupList;
	

	private AdminSearchCondition searchCondition;
	

	@Before
	public void setMethod() {
		
		//*********** User 생성 시작 ***********//
		testUserList = new ArrayList<User>();
		//*********** User 생성 끝 ***********//
		
		//*********** Group 생성 시작 ***********//
		testGroupList = new ArrayList<Group>();
		//*********** Group 생성 끝 ***********//
		
		//*********** Role 생성 시작 ***********//
		roleCreate = new Role();
		roleCreate.setCodeCheck("success");
		roleCreate.setRoleId("testRoleId");
		roleCreate.setRoleName("testRoleName");
		roleCreate.setDescription("테스트 설명입니다");
		roleCreate.setPortalId("P000001");
		roleCreate.setRegisterId("admin");
		roleCreate.setRegisterName("관리자");
		roleCreate.setUpdaterId("admin");
		roleCreate.setUpdaterName("관리자");
		//*********** Role 생성 끝 ***********//
		
		//*********** SearchCondition 생성 시작 ***********//
		searchCondition = new AdminSearchCondition();
		searchCondition.setUserLocaleCode("ko");
		searchCondition.setStartRowIndex(0);
		searchCondition.setEndRowIndex(10);
		//*********** SearchCondition 생성 끝 ***********//
	}
	
	@Test
	public void create() {
		
		for(int i=1; i<=5; i++) {
			Group testGroupA = new Group();
			testGroupA.setGroupId("TestGroup_"+i);
			testGroupA.setGroupName("TestGroup_"+i);
			testGroupA.setChildGroupCount("0");
			testGroupA.setSortOrder(StringUtil.lpad(String.valueOf(i), 13, "0"));
			testGroupA.setRegisterId("admin");
			testGroupA.setRegisterName("관리자");
			testGroupA.setUpdaterId("admin");
			testGroupA.setUpdaterName("관리자");
			
			groupService.create(testGroupA);
			
			testGroupList.add(testGroupA);
		}
		
		for (User user : testUserList) {
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
			
			testUserList.add(user);
		}
		
		roleCreate.setUserList(testUserList);
		roleCreate.setGroupList(testGroupList);
		roleCreate.setRoleTypeId("RT00001");
		
		roleService.create(roleCreate);
		
		Role result = roleService.read(roleCreate.getRoleId());
		
		assertNotNull(result);
	}
	
	@Test
	public void update() {
		
		for(int i=1; i<=5; i++) {
			Group testGroupA = new Group();
			testGroupA.setGroupId("TestGroup_"+i);
			testGroupA.setGroupName("TestGroup_"+i);
			testGroupA.setChildGroupCount("0");
			testGroupA.setSortOrder(StringUtil.lpad(String.valueOf(i), 13, "0"));
			testGroupA.setRoleId(roleCreate.getRoleId());
			testGroupA.setRegisterId("admin");
			testGroupA.setRegisterName("관리자");
			testGroupA.setUpdaterId("admin");
			testGroupA.setUpdaterName("관리자");
			
			groupService.create(testGroupA);
			
			testGroupList.add(testGroupA);
		}
		
		for(int i=1; i<=5; i++) {
			
			List<Group> tempGroupList = new ArrayList<Group>();
			
			for(int j=5; j<7; j++) {
				String id = "GD000001"+j;
				Group group = groupService.read(id);
				tempGroupList.add(group);
			}
			
			User user = new User();
			user.setUserId("testcaseUser"+i);
			user.setUserPassword("portal");
			user.setUserStatus("C");
			user.setUserName("테스트케이스사용자"+i);
			user.setUserEnglishName("Test Case User"+i);
			user.setTeamName("테스트팀");
			user.setLeader("0");
			user.setRegisterId("admin");
			user.setRegisterName("관리자");
			user.setUpdaterId("admin");
			user.setUpdaterName("관리자");
			user.setGroupId("temp");
			user.setGroupList(tempGroupList);
			user.setGroupId("GD0000187");
			user.setIsRepresentGroup("1");
			userService.create(user);
			
			testUserList.add(user);
		}
		
		roleCreate.setUserList(testUserList);
		roleCreate.setGroupList(testGroupList);
		roleCreate.setRoleTypeId("RT00001");
		
		String roleId = roleService.create(roleCreate);
		
		Role preRole = roleService.read(roleId);
		List<String> groupIds = new ArrayList<String>();
		
		for(int i=0; i<testGroupList.size(); i++) {
			groupIds.add(testGroupList.get(i).getGroupId());
		}
		
		testGroupList = new ArrayList<Group>();
		
		for(int i=1; i<=3; i++) {
			Group testGroupA = groupService.read(groupIds.get(i));
			testGroupA.setRoleId(roleId);
						
			testGroupList.add(testGroupA);
		}
		
		testUserList = new ArrayList<User>();
		
		for(int i=1; i<=5; i++) {
			User user = userService.read("testcaseUser"+i);
			user.setRoleId(roleId);
			
			testUserList.add(user);
		}
		
		preRole.setGroupList(testGroupList);
		preRole.setUserList(testUserList);
		preRole.setRoleName("수정");
		preRole.setRoleEnglishName("EnglishNameUpdated");
		
		roleService.update(preRole);
		
		Role postRole = roleService.read(roleId);
		
		List<Role> roleGroupList = roleService.selectRoleGroupList(roleId);
		List<Map<String,String>> roleUserList = roleService.selectRoleUserList(roleId);
//		List<Map<String,String>> roleUserList = roleService.selectRoleUserList("R00001");
		
		assertNotNull(postRole);
		assertEquals(3, roleGroupList.size());
		assertEquals(0, roleUserList.size());
		assertEquals("수정", postRole.getRoleName());
	}
	
	@Test
	public void remove() {
		
		for(int i=1; i<=5; i++) {
			Group testGroupA = new Group();
			testGroupA.setGroupId("TestGroup_"+i);
			testGroupA.setGroupName("TestGroup_"+i);
			testGroupA.setChildGroupCount("0");
			testGroupA.setSortOrder(StringUtil.lpad(String.valueOf(i), 13, "0"));
			testGroupA.setRoleId(roleCreate.getRoleId());
			testGroupA.setRegisterId("admin");
			testGroupA.setRegisterName("관리자");
			testGroupA.setUpdaterId("admin");
			testGroupA.setUpdaterName("관리자");
			
			groupService.create(testGroupA);
			
			testGroupList.add(testGroupA);
		}
		
		for (User user : testUserList) {
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
			
			testUserList.add(user);
		}
		
		roleCreate.setUserList(testUserList);
		roleCreate.setGroupList(testGroupList);
		roleCreate.setRoleTypeId("RT00001");
		
		roleService.create(roleCreate);
		
		Role preRole = roleService.read(roleCreate.getRoleId());
		String roleId = preRole.getRoleId();
				
		roleService.remove(preRole);
		
		
		assertNull(roleService.read(preRole.getRoleId()));
		assertEquals(0, roleService.selectRoleGroupList(roleId).size());
		assertEquals(0, roleService.selectRoleUserList(roleId).size());
	}
	
	@Test
	public void exists() {	
		
		for(int i=1; i<=5; i++) {
			Group testGroupA = new Group();
			testGroupA.setGroupId("TestGroup_"+i);
			testGroupA.setGroupName("TestGroup_"+i);
			testGroupA.setChildGroupCount("0");
			testGroupA.setSortOrder(StringUtil.lpad(String.valueOf(i), 13, "0"));
			testGroupA.setRegisterId("admin");
			testGroupA.setRegisterName("관리자");
			testGroupA.setUpdaterId("admin");
			testGroupA.setUpdaterName("관리자");
			
			groupService.create(testGroupA);
			
			testGroupList.add(testGroupA);
		}
		
		for (User user : testUserList) {
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
			
			testUserList.add(user);
		}
		
		roleCreate.setUserList(testUserList);
		roleCreate.setGroupList(testGroupList);
		roleCreate.setRoleTypeId("RT00001");
		
		roleService.create(roleCreate);
		
		Role result = roleService.read(roleCreate.getRoleId());
		
		boolean flag = roleService.exists(result.getRoleId());
		
		assertTrue(flag);
	}
	
	@Test
	public void selectTypeId() {
		
		List<RoleType> result = roleService.selectTypeId("en");
		assertNotNull(result);
	}
	
	@Test
	public void listWithSearchCondition() {
		
		SearchResult<Role> result = roleService.list(searchCondition);
		
		assertNotNull(result);
		
	}
	
}
