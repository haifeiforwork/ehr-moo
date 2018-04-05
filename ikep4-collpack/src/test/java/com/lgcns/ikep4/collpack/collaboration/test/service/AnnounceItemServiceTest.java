package com.lgcns.ikep4.collpack.collaboration.test.service;




import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
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

import com.lgcns.ikep4.collpack.collaboration.board.announce.model.AnnounceItem;
import com.lgcns.ikep4.collpack.collaboration.board.announce.search.AnnounceItemSearchCondition;
import com.lgcns.ikep4.collpack.collaboration.board.announce.service.AnnounceItemService;
import com.lgcns.ikep4.collpack.collaboration.workspace.model.Workspace;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.user.member.model.User;

public class AnnounceItemServiceTest extends BaseServiceTestCase{

	@Autowired
	AnnounceItemService announceItemService;
	
	private AnnounceItem announceItem;
	private AnnounceItem announceItemSet;

	private String pk;
	private User user;
	private String lowRankWorkspaceId = "100000728575";
	private String oWorkspaceId = "100000730275";
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
		
		
		
		announceItem = new AnnounceItem();
		announceItem.setTitle("공지사항 일반 제목 입력");
		announceItem.setContents("공지사항 일반 내용입력");
		announceItem.setTitle("공지사항 일반 입력");
		announceItem.setIsOwner("0");
		announceItem.setWorkspaceId(oWorkspaceId);
		announceItem.setRegisterId(registerId);
		announceItem.setRegisterName(registerName);
		announceItem.setUpdaterId(updaterId);
		announceItem.setUpdaterName(updaterName);
		
		
		
		
		pk = announceItemService.createAnnounceItem(announceItem, user);
		
		announceItemSet = this.announceItem;
	}

	@Test
	public void testReadannounceItem(){
		String itemId = this.announceItemSet.getItemId();
		AnnounceItem result = announceItemService.readAnnounceItem(announceItemSet.getRegisterId(), itemId, oWorkspaceId);
		
		assertNotNull(result);
	}
	
	@Test
	public void testUpdateAnnounceItem(){
		
		announceItemSet.setTitle("수정_공지");
		announceItemSet.setContents("수정_공지");
		announceItemSet.setItemDisplay(1);
		
		announceItemService.updateAnnounceItem(announceItemSet, user);
		
		AnnounceItem tempSet = new AnnounceItem();
		tempSet = announceItemService.readAnnounceItem(user.getUserId(), announceItemSet.getItemId(),announceItemSet.getWorkspaceId());
		
		assertEquals(tempSet.getTitle(),announceItemSet.getTitle());
		assertEquals(tempSet.getContents(),announceItemSet.getContents());
		assertEquals(tempSet.getItemDisplay(),announceItemSet.getItemDisplay());
	}
	
	@Test
	public void testAdminDeleteAnnounceItem(){
		announceItemService.adminDeleteAnnounceItem(announceItemSet);
		AnnounceItem result = announceItemService.readAnnounceItem(announceItemSet.getRegisterId(), announceItemSet.getItemId(), oWorkspaceId);
		assertNull(result);
	}
	
	@Test
	public void testAdminMultiDeleteAnnounceItem(){
		String pk2 = announceItemService.createAnnounceItem(announceItem, user);
		List<String> tempId = new ArrayList<String>();
		tempId.add(pk);
		tempId.add(pk2);
		
		announceItemService.adminMultiDeleteAnnounceItem(tempId,oWorkspaceId,portalId);
		
		AnnounceItem result = announceItemService.readAnnounceItem(registerId, pk, oWorkspaceId);
		AnnounceItem result2 = announceItemService.readAnnounceItem(registerId, pk2, oWorkspaceId);
		
		assertNull(result);
		assertNull(result2);
	}
	
	@Test
	public void testListChildWorkspaceInfoBySearchCondition(){
		AnnounceItemSearchCondition searchCondition = new AnnounceItemSearchCondition();
		searchCondition.setWorkspaceId(oWorkspaceId);
		searchCondition.setItemId(pk);
		searchCondition.setPortalId(portalId);
		
		SearchResult<AnnounceItem> result = announceItemService.listChildWorkspaceInfoBySearchCondition(searchCondition);
		assertNotNull(result);
	
	}
	
	@Test
	public void testCreateAnnounceLinkItem(){
		List<String> tempWorkspaceId = new ArrayList<String>();
		//tempWorkspaceId.add(oWorkspaceId);
		tempWorkspaceId.add(lowRankWorkspaceId);
		
		announceItemService.createAnnounceLinkItem(pk, tempWorkspaceId);
		
		AnnounceItem result = announceItemService.readAnnounceItem(registerId, pk, oWorkspaceId);
		AnnounceItem result2 = announceItemService.readAnnounceItem(registerId, pk, lowRankWorkspaceId);
		
		assertEquals(result.getIsOwner(),"1");
		assertEquals(result2.getIsOwner(),"0");
	}
	@Test
	public void testGetWorkspaceInfo(){
		Workspace workspaceInfo = announceItemService.getWorkspaceInfo(portalId, oWorkspaceId);
		assertEquals(announceItemSet.getWorkspaceId(),workspaceInfo.getWorkspaceId());
	}
	
	@Test
	public void listAnnounceItemByPortlet(){
		Map<String,String> map = new HashMap<String,String>();
		map.put("workspaceId", oWorkspaceId);
		map.put("limitSize", "10");
		List<AnnounceItem> result = announceItemService.listAnnounceItemByPortlet(map);
		assertNotNull(result);
	}
}
