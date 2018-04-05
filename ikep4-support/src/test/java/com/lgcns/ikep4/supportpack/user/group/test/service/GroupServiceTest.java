package com.lgcns.ikep4.supportpack.user.group.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

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
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.user.code.model.ItemType;
import com.lgcns.ikep4.support.user.code.service.ItemTypeService;
import com.lgcns.ikep4.support.user.group.model.Group;
import com.lgcns.ikep4.support.user.group.model.GroupType;
import com.lgcns.ikep4.support.user.group.service.GroupService;
import com.lgcns.ikep4.support.user.group.service.GroupTypeService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.model.UserSearchCondition;
import com.lgcns.ikep4.support.user.member.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/configuration/spring/context-dao.xml", "classpath:/configuration/spring/context-service.xml"})
public class GroupServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private GroupService groupService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private GroupTypeService groupTypeService;	
	
	@Autowired
	private ItemTypeService itemTypeService;
	
	/**
	 * Log variable for all child classes. Uses LogFactory.getLog(getClass())
	 * from Commons Logging
	 */
	protected final Log log = LogFactory.getLog(getClass());
	
	private ItemType testItemType;

	private UserSearchCondition searchCondition;
	
	private Group testGroupA;
	private Group testGroupB;
	private GroupType testGroupTypeA;
	
	private List<User> testUserList;
	
	@Before
	public void setMethod() {
		// testGroup_A 세팅
		testGroupA = new Group();
		testGroupA.setGroupId("temp");
		testGroupA.setGroupName("Test Group A");
		testGroupA.setGroupEnglishName("Test Group A");
		testGroupA.setChildGroupCount("0");
		testGroupA.setSortOrder("0000000001000");
		testGroupA.setRegisterId("admin");
		testGroupA.setRegisterName("관리자");
		testGroupA.setUpdaterId("admin");
		testGroupA.setUpdaterName("관리자");

		testGroupB = new Group();
		testGroupB.setGroupId("temp");
		testGroupB.setGroupName("Test Group B");
		testGroupB.setChildGroupCount("0");
		testGroupB.setSortOrder("0000000001001");
		testGroupB.setRegisterId("admin");
		testGroupB.setRegisterName("관리자");
		testGroupB.setUpdaterId("admin");
		testGroupB.setUpdaterName("관리자");
		
		testUserList = new ArrayList<User>();
		// testUser_01 ~ 10 세팅
		for(int i=1; i<=10; i++) {
			User user = new User();
			user.setUserId("testUserId"+i);
			user.setUserPassword("testUserPW");
			user.setUserStatus("C");
			user.setUserName("사용자 이름"+i);
			user.setUserEnglishName("username"+i);
			user.setGroupId("");
			user.setTeamName("Test Group A");
			user.setLeader("0");
			user.setRegisterId("adminTest");
			user.setRegisterName("관리자테스트");
			user.setUpdaterId("adminTest");
			user.setUpdaterName("관리자테스트");
			
			testUserList.add(user);
		}
		
		//Item Type 세팅
		testItemType = new ItemType();
		testItemType.setItemTypeCode("testItemType");
		testItemType.setItemTypeName("ItemTypeForTest");
		testItemType.setRegisterId("admin");
		testItemType.setRegisterName("관리자");
		testItemType.setUpdaterId("admin");
		testItemType.setUpdaterName("관리자");
		
		testGroupTypeA = new GroupType();
		testGroupTypeA.setGroupTypeId("testGroupTypeA");
		testGroupTypeA.setGroupTypeName("테스트그룹타입A");
		testGroupTypeA.setRegisterId("admin");
		testGroupTypeA.setRegisterName("관리자");
		testGroupTypeA.setUpdaterId("admin");
		testGroupTypeA.setUpdaterName("관리자");
		
	}
	
	@Test
	public void createGroupTest() {
		// Group을 생성하면 생성할 때 사용한 PK(GroupId)를 리턴
		String groupIdA = groupService.create(testGroupA);
		
		// testUser 생성
		for (User user : testUserList) {
			List<Group> tempGroupList = new ArrayList<Group>();
			for(int i=5; i<7; i++) {
				String id = "GD000001"+i;
				Group group = groupService.read(id);
				tempGroupList.add(group);
			}
			
			tempGroupList.add(groupService.read(groupIdA));
			
			user.setGroupList(tempGroupList);
			user.setGroupId("GD0000187");
			user.setIsRepresentGroup("1");
			userService.create(user);
		}
		
		// 생성시에 사용한 GroupId를 이용하여 Group을 읽어온다.
		Group tempGroupA = groupService.read(groupIdA);
		
		// 생성한 그룹에서 userlist를 뽑아와서 새 list 구성
		List<User> tempUsers = tempGroupA.getUserList();
		List<String> preUserNames = new ArrayList<String>();
		for (int i=0; i<10; i++) {
			tempUsers.get(i).setRegisterId("admin");
			tempUsers.get(i).setRegisterName("관리자");
			tempUsers.get(i).setUpdaterId("admin");
			tempUsers.get(i).setUpdaterName("관리자");
			preUserNames.add(tempUsers.get(i).getUserName());
		}
		
		testGroupB.setUserList(tempUsers);
		String groupIdB = groupService.create(testGroupB);
		
		Group tempGroupB = groupService.read(groupIdB);
		List<User> userListB = tempGroupB.getUserList();
		List<String> postUserNames = new ArrayList<String>();
		for (int i=0; i<10; i++) {
			postUserNames.add(userListB.get(i).getUserName());
		}
		
		assertNotNull(tempGroupB);
		assertEquals(preUserNames, postUserNames);
	}
	
	@Test
	public void existsTest() {
		// Group을 생성하면 생성할 때 사용한 PK(GroupId)를 리턴
		String groupIdA = groupService.create(testGroupA);
		
		// 위에서 읽어온 GroupID를 이용하여 exists 체크
		// 이미 생성된 ID 이므로 true 리턴
		boolean isExists = groupService.exists(groupIdA);
		
		assertTrue(isExists);
	}
	
	@Test
	public void readTest() {
		// Group을 생성하면 생성할 때 사용한 PK(GroupId)를 리턴
		String groupIdA = groupService.create(testGroupA);
		
		// testUser 생성
		for (User user : testUserList) {
			List<Group> tempGroupList = new ArrayList<Group>();
			for(int i=5; i<7; i++) {
				String id = "GD000001"+i;
				Group group = groupService.read(id);
				tempGroupList.add(group);
			}
			
			tempGroupList.add(groupService.read(groupIdA));
			
			user.setGroupList(tempGroupList);
			user.setGroupId("GD0000187");
			user.setIsRepresentGroup("1");
			userService.create(user);
		}
		
		// 생성시에 사용한 GroupId를 이용하여 Group을 읽어온다.
		Group tempGroupA = groupService.read(groupIdA);
		
		// 생성한 그룹에서 userlist를 뽑아와서 새 list 구성
		List<User> tempUsers = tempGroupA.getUserList();
		List<String> preUserNames = new ArrayList<String>();
		for (int i=0; i<10; i++) {
			tempUsers.get(i).setRegisterId("admin");
			tempUsers.get(i).setRegisterName("관리자");
			tempUsers.get(i).setUpdaterId("admin");
			tempUsers.get(i).setUpdaterName("관리자");
			preUserNames.add(tempUsers.get(i).getUserName());
		}
		
		testGroupB.setUserList(tempUsers);
		String groupIdB = groupService.create(testGroupB);
		
		Group tempGroupB = groupService.read(groupIdB);
		List<User> userListB = tempGroupB.getUserList();
		
		// 생성한 그룹이 null이 아니면 true
		assertNotNull(tempGroupB);
		// 그룹 내의 사용자 리스트가 null이 아니면 true
		assertNotNull(userListB);
	}
	
	@Test
	public void updateTest() {
		// ItemType 생성
		itemTypeService.create(testItemType);
		
		// GroupType 생성
		groupTypeService.create(testGroupTypeA);
		
		// Group을 생성하면 생성할 때 사용한 PK(GroupId)를 리턴
		String groupIdA = groupService.create(testGroupA);
		
		// testUser 생성
		for (User user : testUserList) {
			List<Group> tempGroupList = new ArrayList<Group>();
			for(int i=5; i<7; i++) {
				String id = "GD000001"+i;
				Group group = groupService.read(id);
				tempGroupList.add(group);
			}
			
			tempGroupList.add(groupService.read(groupIdA));
			
			user.setGroupList(tempGroupList);
			user.setGroupId("GD0000187");
			user.setIsRepresentGroup("1");
			userService.create(user);
		}
		
		// 생성시에 사용한 GroupId를 이용하여 Group을 읽어온다.
		Group tempGroupA = groupService.read(groupIdA);
		
		// 생성한 그룹에서 userlist를 뽑아와서 새 list 구성
		List<User> tempUsers = tempGroupA.getUserList();
		for (int i=0; i<10; i++) {
			tempUsers.get(i).setRegisterId("admin");
			tempUsers.get(i).setRegisterName("관리자");
			tempUsers.get(i).setUpdaterId("admin");
			tempUsers.get(i).setUpdaterName("관리자");
		}
		
		testGroupB.setUserList(tempUsers);
		String groupIdB = groupService.create(testGroupB);
		
		Group tempGroupB = groupService.read(groupIdB);
		List<User> userListB = tempGroupB.getUserList();
		List<String> postUserNames = new ArrayList<String>();
		for (int i=0; i<10; i++) {
			postUserNames.add(userListB.get(i).getUserName());
		}
		
		List<User> updatedUserList = new ArrayList<User>();
		for (int i=0; i<10; i++) {
			if(i%2!=0) { // 짝수만 들어가도록
				User user = userListB.get(i);
				user.setRegisterId("updater");
				user.setRegisterName("수정자");
				user.setUpdaterId("updater");
				user.setUpdaterName("수정자");
				
				updatedUserList.add(user);
			}
		}
		
		tempGroupB.setGroupName("updatedName");
		tempGroupB.setGroupTypeId("testGroupTypeA");
		tempGroupB.setUpdaterId("adminB");
		tempGroupB.setUpdaterName("관리자B");
		tempGroupB.setUserList(updatedUserList);
		
		groupService.update(tempGroupB);
		
		// 수정하기 전의 그룹 id를 이용하여 그룹 정보를 읽어와서 비교한다.
		Group updatedGroup = groupService.read(groupIdB);
		
		assertNotNull(updatedGroup);
		assertEquals("updatedName", updatedGroup.getGroupName());
		assertEquals("testGroupTypeA", updatedGroup.getGroupTypeId());
		assertNotNull(updatedGroup.getUserList());
		assertSame(5, updatedGroup.getUserList().size());
	}
	
	@Test
	public void removeTest() {
		// Group을 생성하면 생성할 때 사용한 PK(GroupId)를 리턴
		String groupIdA = groupService.create(testGroupA);
		
		// testUser 생성
		for (User user : testUserList) {
			List<Group> tempGroupList = new ArrayList<Group>();
			for(int i=5; i<7; i++) {
				String id = "GD000001"+i;
				Group group = groupService.read(id);
				tempGroupList.add(group);
			}
			
			tempGroupList.add(groupService.read(groupIdA));
			
			user.setGroupList(tempGroupList);
			user.setGroupId("GD0000187");
			user.setIsRepresentGroup("1");
			userService.create(user);
		}
		
		// 생성시에 사용한 GroupId를 이용하여 Group을 읽어온다.
		Group tempGroupA = groupService.read(groupIdA);
		
		// 생성한 그룹에서 userlist를 뽑아와서 새 list 구성
		List<User> tempUsers = tempGroupA.getUserList();
		List<String> preUserNames = new ArrayList<String>();
		for (int i=0; i<10; i++) {
			tempUsers.get(i).setRegisterId("admin");
			tempUsers.get(i).setRegisterName("관리자");
			tempUsers.get(i).setUpdaterId("admin");
			tempUsers.get(i).setUpdaterName("관리자");
			preUserNames.add(tempUsers.get(i).getUserName());
		}
		
		testGroupB.setUserList(tempUsers);
		String groupIdB = groupService.create(testGroupB);
		
		Group tempGroupB = groupService.read(groupIdB);
		List<User> userListB = tempGroupB.getUserList();
		List<String> postUserNames = new ArrayList<String>();
		for (int i=0; i<10; i++) {
			postUserNames.add(userListB.get(i).getUserName());
		}
		
		groupService.delete(tempGroupB);
		
		assertNull(groupService.read(groupIdB));
	}
	
	@Test
	public void listTest() {
		// ItemType 생성
		itemTypeService.create(testItemType);
		
		// GroupType 생성
		groupTypeService.create(testGroupTypeA);
		
		// Group을 생성하면 생성할 때 사용한 PK(GroupId)를 리턴
		testGroupA.setGroupTypeId(testGroupTypeA.getGroupTypeId());
		String groupIdA = groupService.create(testGroupA);
		
		// testUser 생성
		for (User user : testUserList) {
			List<Group> tempGroupList = new ArrayList<Group>();
			for(int i=5; i<7; i++) {
				String id = "GD000001"+i;
				Group group = groupService.read(id);
				tempGroupList.add(group);
			}
			
			tempGroupList.add(groupService.read(groupIdA));
			
			user.setGroupList(tempGroupList);
			user.setGroupId("GD0000187");
			user.setIsRepresentGroup("1");
			userService.create(user);
		}
		
		// 생성시에 사용한 GroupId를 이용하여 Group을 읽어온다.
		Group tempGroupA = groupService.read(groupIdA);
		
		// 생성한 그룹에서 userlist를 뽑아와서 새 list 구성
		List<User> tempUsers = tempGroupA.getUserList();
		for (int i=0; i<10; i++) {
			tempUsers.get(i).setRegisterId("admin");
			tempUsers.get(i).setRegisterName("관리자");
			tempUsers.get(i).setUpdaterId("admin");
			tempUsers.get(i).setUpdaterName("관리자");
		}
		
		testGroupB.setUserList(tempUsers);
		testGroupB.setGroupTypeId(testGroupTypeA.getGroupTypeId());
		groupService.create(testGroupB);
		
		searchCondition = new UserSearchCondition();
		searchCondition.setGroupTypeId(testGroupTypeA.getGroupTypeId());
		searchCondition.setUserLocaleCode("ko");
		
		SearchResult<Group> tempGroupList = groupService.list(searchCondition);
		
		// Grouptype ID로 testGroupTypeA을 사용하여 두 개의 그룹을 생성했으므로 결과가 2여야 함
		assertEquals(2, tempGroupList.getEntity().size());
		
		UserSearchCondition newSearchCondition = new UserSearchCondition();
		newSearchCondition.setSearchColumn("title");
		newSearchCondition.setSearchWord("huijojiojo");
		
		SearchResult<Group> tempGroupList2 = groupService.list(newSearchCondition);
		
		assertNull(tempGroupList2.getEntity());
	}
	
	@Test
	public void getMaxSortOrderTest() {
		
		String id = groupService.create(testGroupA);
		
		int sortOrder = Integer.parseInt(groupService.read(id).getSortOrder()); 
		
		int maxSortOrder = Integer.parseInt(groupService.getMaxSortOrder());
		
		assertEquals(sortOrder+1, maxSortOrder);
	}
	
	@Test
	public void selectOrgGroupByGroupTypeIdTest() {
		
		groupTypeService.create(testGroupTypeA);
		
		testGroupA.setGroupTypeId("G0001");
		
		String groupId = groupService.create(testGroupA);
		
		Group group = groupService.read(groupId);
		
		List<Group> result = groupService.selectOrgGroupByGroupTypeId(group);
		
		assertNotNull(result);
	}
	
	@Test
	public void selectOrgGroupTest() {
		MockHttpServletRequest req; 
		
		req = new MockHttpServletRequest(); // HttpServletRequest Mock객체를 생성합니다.
		MockHttpSession session = new MockHttpSession(); // HttpSession Mock객체를 생성합니다.
		session.setAttribute("ikep.portalId", "P000001"); // User 모델을 ikep.user라는 키로 세션에 저장합니다.
		req.setSession(session);

	        // RequestContextHolder에 위에서 작업한 request 객체를 저장합니다.
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(req));
		
		testGroupA.setGroupTypeId("G0001");
		String groupId = groupService.create(testGroupA);
		
		Group group = groupService.read(groupId);
		
		List<Group> result = groupService.selectOrgGroup(group);
		
		assertNotNull(result);
	}
	
	@Test
	public void selectCollaborationGroup() {
		
		testGroupA.setGroupTypeId("G0002");
		groupService.create(testGroupA);
		
		// selectCollaborationGroup 쿼리 이상함(where 절에 
		// group_type_id G0001인 것만 가져오는 조건이 있는데 G0001은 collaboration 타입이 아님
		List<Group> result = groupService.selectCollaborationGroup("100019261642");
		
		assertNotNull(result);
	}
	
	@Test
	public void selectGroupSearchTest() {
		
		testGroupA.setGroupTypeId("G0001");
		testGroupA.setGroupName("테스트그룹A");
		
		groupService.create(testGroupA);
		
		List<Group> result = groupService.selectGroupSearch("ko", "테스트그룹A");
		
		assertEquals(1, result.size());
	}
	
	@Test
	public void selectUserRootGroupTest() {
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", "user10");
		param.put("localeCode", "ko");
		
		Group result = groupService.selectUserRootGroup(param);
		
		assertEquals("GD0000001", result.getRootGroupId());
	}
	
	@Test
	public void getGroupFullPathTest() {
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", "user3");
		param.put("portalId", "P000001");
		
		String result = groupService.getGroupFullPath(param);
		
		assertEquals(",GD0000001,GD0000006,GD0000307,GD0000404", result);
	}
	
	@Test
	public void selectUserGroupWithHierarchyTest() {
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId",  "user3");
		param.put("isRoot", "false");
		param.put("localeCode","ko");
		
	  List<Group> result = groupService.selectUserGroupWithHierarchy(param);
		
		assertNotNull(result);
	}
	
	@Test
	public void selectUserGroupOtherTest() {
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId",  "user3");
		param.put("isRoot", "false");
		param.put("localeCode","ko");
		
	  List<Group> result = groupService.selectUserGroupOther(param);
		
		assertNotNull(result);
	}
	
	
	
	
	
}
