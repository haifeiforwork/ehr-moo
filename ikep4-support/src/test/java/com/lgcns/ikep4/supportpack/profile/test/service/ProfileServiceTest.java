package com.lgcns.ikep4.supportpack.profile.test.service;

/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lgcns.ikep4.support.user.member.dao.UserDao;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * Profile User Update 테스트 케이스
 * 
 * @author 이형운 (dewey94@wooin.info)
 * @version $Id: ProfileServiceTest.java 16258 2011-08-18 05:37:22Z giljae $
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
                                "classpath*:/configuration/spring/context-dao.xml", 
                                "classpath*:/configuration/spring/context-service.xml" })
public class ProfileServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private UserDao userDao;

	private User user;

	private String userId;

	@Before
	public void setUp() {
		this.user = new User();
		this.userId = "user39";
	}

	@Test
	public void testUpdateProfile() {
		this.user.setUserId("user39");
		this.user.setTimezoneId("GMT000000009");
		this.user.setMobile("01012345678");
		this.user.setOfficePhoneNo("029876543");
		this.user.setOfficeBasicAddress("서울특별시");
		this.user.setOfficeDetailAddress("여의도 동성빌딩 11층");
		this.user.setLocaleCode("en");
		//this.user.setCurrentJob("iKEP4 버젼 업데이트");
		this.user.setUpdaterId("user39-1");
		this.user.setUpdaterName("테스트 User");

		userDao.updateProfile(this.user);
		User result = userDao.get("user39");
		assertEquals(this.user.getTimezoneId(), result.getTimezoneId());
		assertEquals(this.user.getMobile(), result.getMobile());
		assertEquals(this.user.getOfficePhoneNo(), result.getOfficePhoneNo());
		assertEquals(this.user.getOfficeBasicAddress(), result.getOfficeBasicAddress());
		assertEquals(this.user.getOfficeDetailAddress(), result.getOfficeDetailAddress());
		assertEquals(this.user.getLocaleCode(), result.getLocaleCode());
		//assertEquals(this.user.getCurrentJob(), result.getCurrentJob());
		assertEquals(this.user.getUpdaterId(), result.getUpdaterId());
		assertEquals(this.user.getUpdaterName(), result.getUpdaterName());
	}

	@Test
	public void testUpdateProfileStaus() {

		this.user.setUserId("user39");
		this.user.setProfileStatus("############################ 테스트 Status ########################");
		this.user.setUpdaterId("user39-2");
		this.user.setUpdaterName("테스트 User");

		userDao.updateProfileStaus(this.user);
		User result = userDao.get("user39");
		assertEquals(this.user.getProfileStatus(), result.getProfileStatus());

	}

	@Test
	public void testUpdatePictureId() {

		this.user.setUserId("user39");
		this.user.setPictureId("1234567890");
		this.user.setUpdaterId("user39-1");
		this.user.setUpdaterName("테스트 User");

		userDao.updatePictureId(this.user);
		User result = userDao.get("user39");
		assertEquals(this.user.getPictureId(), result.getPictureId());

	}

	@Test
	public void testUpdateProfilePictureId() {

		this.user.setUserId("user39");
		this.user.setProfilePictureId("9876543210");
		this.user.setUpdaterId("user39-1");
		this.user.setUpdaterName("테스트 User");

		userDao.updateProfilePictureId(this.user);
		User result = userDao.get("user39");
		assertEquals(this.user.getProfilePictureId(), result.getProfilePictureId());

	}

	@Test
	public void testUpdateTwitterInfo() {

		this.user.setUserId("user39");
		this.user.setTwitterAccount("test@test.com");
		this.user.setTwitterAuthCode("DVEDGVWV#FF#@Y%^HBE");
		this.user.setUpdaterId("user39-1");
		this.user.setUpdaterName("테스트 User");

		userDao.updateTwitterInfo(this.user);
		User result = userDao.get("user39");
		assertEquals(this.user.getTwitterAccount(), result.getTwitterAccount());
		assertEquals(this.user.getTwitterAuthCode(), result.getTwitterAuthCode());

	}

	@Test
	public void testUpdateFacebookInfo() {

		this.user.setUserId("user39");
		this.user.setFacebookAccount("test@test.com");
		this.user.setFacebookAuthCode("DVEDGVWV#FF#@Y%^HBE");
		this.user.setUpdaterId("user39-1");
		this.user.setUpdaterName("테스트 User");

		userDao.updateFacebookInfo(this.user);
		User result = userDao.get("user39");
		assertEquals(this.user.getFacebookAccount(), result.getFacebookAccount());
		assertEquals(this.user.getFacebookAuthCode(), result.getFacebookAuthCode());

	}

	@Test
	public void testUpdateExportField() {

		this.user.setUserId("user39");
		this.user.setExpertField("############################ 테스트 Export Field ########################");
		this.user.setUpdaterId("user39-1");
		this.user.setUpdaterName("테스트 User");

		userDao.updateExportField(this.user);
		User result = userDao.get("user39");
		assertEquals(this.user.getExpertField(), result.getExpertField());

	}
	
	@Test
	public void testUpdateCurrentJob() {

		this.user.setUserId("user39");
		this.user.setCurrentJob("############################ 테스트 Current Job ########################");
		this.user.setUpdaterId("user39-1");
		this.user.setUpdaterName("테스트 User");

		userDao.updateCurrentJob(this.user);
		User result = userDao.get("user39");
		assertEquals(this.user.getCurrentJob(), result.getCurrentJob());

	}

	@Deprecated
	public void testGet() {
	}

	@Deprecated
	public void testExists() {
	}

	@Deprecated
	public void testCreate() {
	}

	@Deprecated
	public void testUpdate() {
	}

	@Deprecated
	public void testPhysicalDelete() {
	}

	@Deprecated
	public void testLogicalDelete() {
	}

}
