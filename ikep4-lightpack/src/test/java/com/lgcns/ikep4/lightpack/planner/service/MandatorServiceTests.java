package com.lgcns.ikep4.lightpack.planner.service;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.lightpack.BaseDaoTestCase;
import com.lgcns.ikep4.lightpack.planner.model.Mandator;
import com.lgcns.ikep4.lightpack.planner.service.CalendarService;
import com.lgcns.ikep4.support.user.member.model.User;

public class MandatorServiceTests extends BaseDaoTestCase {
	@Autowired
	private CalendarService service;
	
	private static String userId = "user300";
	private static String userName = "홍길동";
	private static String trusteeId = "user1004";
	private String mandatorId = userId;
	
	private final static String portalId = "P000001";
	
	@Before
	public void setUp() {
		User user = new User();
		user.setUserId(userId);
		user.setUserName(userName);
		
		Mandator mandator = service.createMandator(user, portalId, trusteeId);
	}
	
	@Test
	public void getTrustee() {
		Mandator trustee = service.getTrustee(userId);
		assertEquals(trusteeId, trustee.getTrusteeId());
		assertNotNull(trustee.getTrusteeName());
	}
	
	@Test
	public void deleteMandator() {
		service.deleteMandator(mandatorId);
		Mandator trustee = service.getTrustee(userId);
		assertEquals(trustee, null);
	}
	
	@Test
	public void getMyMandators() {
		User user = new User();
		user.setUserId(userId+"1");
		user.setUserName(userName+"a");
		
		service.createMandator(user, portalId, trusteeId);
		
		List<Map> list = service.getMyMandators(trusteeId);
		assertTrue(list.size() >= 2);
	}
}
