package com.lgcns.ikep4.collpack.collaboration.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lgcns.ikep4.collpack.collaboration.board.weekly.model.WeeklyItem;
import com.lgcns.ikep4.collpack.collaboration.board.weekly.search.WeeklyItemSearchCondition;
import com.lgcns.ikep4.collpack.collaboration.board.weekly.service.WeeklyItemService;
import com.lgcns.ikep4.collpack.collaboration.workspace.model.Workspace;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.user.member.model.User;



public class WeeklyItemServiceTest extends BaseServiceTestCase{
	@Autowired
	WeeklyItemService weeklyItemService;
	
	private WeeklyItem weeklyItem;
	private WeeklyItem weeklyItemSet;
	
	private String pk;
	private User user;
	private String highRankWorkspaceId = "100000730275";
	private String oWorkspaceId = "100000728575";
	
	@Before
	public void setUp() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		MockHttpSession session = new MockHttpSession();

		user = new User();
		user.setUserId(registerId);
		user.setUserName(registerName);
		user.setLocaleCode("ko");
		
		session.setAttribute("ikep.user", user);
		request.setSession(session);
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		
		weeklyItem = new WeeklyItem();
		weeklyItem.setTitle("주간보고 일반 제목 입력");
		weeklyItem.setContents("주간보고 일반 내용입력");
		weeklyItem.setTitle("주간보고 일반 입력");
		weeklyItem.setIsSummary(0);
		weeklyItem.setWeeklyTerm("2011.05.09 ~ 2011.05.15");
		weeklyItem.setWorkspaceId(oWorkspaceId);
		weeklyItem.setRegisterId(registerId);
		weeklyItem.setRegisterName(registerName);
		weeklyItem.setUpdaterId(updaterId);
		weeklyItem.setUpdaterName(updaterName);
		
		pk = weeklyItemService.createWeeklyItem(weeklyItem, user);
		
		weeklyItemSet = this.weeklyItem;
	}
	
	@Test
	public void testListWeeklyItemBySearchCondition(){

		WeeklyItemSearchCondition searchCondition = new WeeklyItemSearchCondition();
		searchCondition.setWorkspaceId(oWorkspaceId);
		searchCondition.setPagePerRecord(10);
		searchCondition.setWeeklyTerm(weeklyItem.getWeeklyTerm());
		
		SearchResult<WeeklyItem> result = weeklyItemService.listWeeklyItemBySearchCondition(searchCondition);
		assertNotNull(result.getEntity());
		
	}
	
	@Test
	public void testReadWeeklyItem(){
		WeeklyItem result = weeklyItemService.readWeeklyItem(user.getUserId(),weeklyItem.getItemId(),weeklyItem.getWorkspaceId());
		assertNotNull(result);
	}
	
	@Test
	public void testDeleteWeeklyItem(){
		weeklyItemService.deleteWeeklyItem(weeklyItemSet);
		WeeklyItem result = weeklyItemService.readWeeklyItem(user.getUserId(),weeklyItem.getItemId(),weeklyItem.getWorkspaceId());
		assertNull(result);
	}
	
	@Test
	public void testUpdateWeeklyItem(){
		weeklyItemSet.setTitle("Modify");
		weeklyItemSet.setContents("contents");
		weeklyItemSet.setWeeklyTerm("2011.05.02 ~ 2011.05.08");
		weeklyItemService.updateWeeklyItem(weeklyItemSet, user);
		WeeklyItem result = weeklyItemService.readWeeklyItem(user.getUserId(),weeklyItem.getItemId(),weeklyItem.getWorkspaceId());
		
		assertEquals(weeklyItemSet.getTitle(),result.getTitle());
		assertEquals(weeklyItemSet.getContents(),result.getContents());
		assertEquals(weeklyItemSet.getWeeklyTerm(),result.getWeeklyTerm());
		
	}
	
	@Test
	public void testReadWeeklyItemContents(){
		List<WeeklyItem> result = weeklyItemService.readWeeklyItemContents(weeklyItemSet);
		assertNotNull(result);
	}
	
	@Test
	public void testListLowRankWeeklyItemBySearchCondition(){
		WeeklyItem temp = this.weeklyItemSet;
		
		temp.setWorkspaceId(oWorkspaceId);
		temp.setContents("Contents");
		temp.setTitle("Title");
		temp.setIsSummary(1);
		
		String pk2 = weeklyItemService.createWeeklyItem(temp, user);
		
		String teamId = this.weeklyItemService.getWorkspaceInfo(portalId,highRankWorkspaceId).getTeamId();
    	
		WeeklyItemSearchCondition searchCondition = new WeeklyItemSearchCondition();
		searchCondition.setWorkspaceId(highRankWorkspaceId);
		searchCondition.setPagePerRecord(10);
		searchCondition.setTeamId(teamId);
		
		SearchResult<WeeklyItem> result = weeklyItemService.listLowRankWeeklyItemBySearchCondition(searchCondition);
		assertNotNull(result);
	}
	
	@Test
	public void testGetWorkspaceInfo(){
		Workspace result = weeklyItemService.getWorkspaceInfo(portalId,oWorkspaceId);
		assertNotNull(result);
	}
	
	
	@Test
	public void testCheckWeeklyTerm(){
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("workspaceId", oWorkspaceId);
		map.put("weeklyTerm", weeklyItem.getWeeklyTerm());
		map.put("registerId", user.getUserId());
		
		String itemId = weeklyItemService.checkWeeklyTerm(map);
		assertEquals(pk,itemId);
	}
	
}
