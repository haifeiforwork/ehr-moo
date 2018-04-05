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

import com.lgcns.ikep4.support.profile.dao.ProfileExpertFellowDao;
import com.lgcns.ikep4.support.profile.model.ProfileExpertFellow;

/**
 * 관련 전문가 영역 조회용
 *
 * @author 이형운
 * @version $Id: ProfileExpertFellowDaoTest.java 16258 2011-08-18 05:37:22Z giljae $
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
                                "classpath*:/configuration/spring/context-dao.xml", 
                                "classpath*:/configuration/spring/context-service.xml" })
public class ProfileExpertFellowDaoTest extends AbstractTransactionalJUnit4SpringContextTests {


	@Autowired
	private ProfileExpertFellowDao profileExpertFellowDao;

	//private ProfileExpertFellow profileExpertFellow;


	@Before
	public void setUp() {
		
	}
	
	@Test
	public void testListProfileFellowExpert() {
		
		List<ProfileExpertFellow> result = profileExpertFellowDao.listProfileFellowExpert("user1");
		assertFalse(result.isEmpty());
		
	}
	
}
