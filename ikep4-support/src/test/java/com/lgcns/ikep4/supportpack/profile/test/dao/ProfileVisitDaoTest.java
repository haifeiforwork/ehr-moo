/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.supportpack.profile.test.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lgcns.ikep4.support.profile.dao.ProfileVisitDao;
import com.lgcns.ikep4.support.profile.model.ProfileVisit;

/**
 * 프로 파일 방문자 테스트 JUNIT
 *
 * @author 이형운
 * @version $Id: ProfileVisitDaoTest.java 16259 2011-08-18 05:40:01Z giljae $
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
                                "classpath*:/configuration/spring/context-dao.xml", 
                                "classpath*:/configuration/spring/context-service.xml" })
public class ProfileVisitDaoTest extends AbstractTransactionalJUnit4SpringContextTests {


	@Autowired
	private ProfileVisitDao profileVisitDao;

	private ProfileVisit profileVisit;


	@Before
	public void setUp() {
		
		
		profileVisit = new ProfileVisit();
		profileVisit.setOwnerId("user39");
		profileVisit.setVisitorId("visitorId");
		
		profileVisitDao.create(profileVisit);
		
	}
	
	@Test
	public void testCreate() {
		
		profileVisit = new ProfileVisit();
		profileVisit.setOwnerId("user39");
		profileVisit.setVisitorId("visitorId");
		profileVisit.setVisitFlag("TODAY");
		
		ProfileVisit result = profileVisitDao.get(profileVisit);
		assertNotNull(result);
		
	}

	@Test
	public void testSelectAllByOwnerId() {
		
		profileVisit = new ProfileVisit();
		profileVisit.setOwnerId("user39");
		profileVisit.setVisitFlag("TODAY");
		
		List<ProfileVisit> result = profileVisitDao.selectAllByOwnerId(profileVisit);
		assertFalse(result.isEmpty());
		
	}
	
	@Test
	public void testSelectAllCountByblogId() {
		
		profileVisit = new ProfileVisit();
		profileVisit.setOwnerId("user39");
		profileVisit.setVisitFlag("TODAY");

		Integer count = profileVisitDao.selectAllCountByOwnerId(profileVisit);
		assertNotNull(count);
	}


	@Test
	public void testPhysicalDelete() {
		
		profileVisit = new ProfileVisit();
		profileVisit.setOwnerId("user39");
		profileVisit.setVisitorId("visitorId");
		
		profileVisitDao.physicalDelete(profileVisit);
		ProfileVisit result = profileVisitDao.get(profileVisit);
		assertNull(result);
		
	}
	
	@Test
	public void testPhysicalDeleteThreadByBlogId() {
		
		profileVisit = new ProfileVisit();
		profileVisit.setOwnerId("user39");
		profileVisit.setVisitorId("visitorId");
		
		profileVisitDao.physicalDeleteThreadByOwnerId(profileVisit);
		ProfileVisit result = profileVisitDao.get(profileVisit);
		assertNull(result);
		
	}

}
