package com.lgcns.ikep4.lightpack.todo.test.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.lightpack.todo.model.UserSetting;
import com.lgcns.ikep4.lightpack.todo.service.UserSettingService;

/**
 * Service 정의
 *
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: UserSettingServiceTestCase.java 16302 2011-08-19 08:43:50Z giljae $
 */
public class UserSettingServiceTestCase extends BaseServiceTestCase {
	@Autowired
	private UserSettingService userSettingService;
	
	private UserSetting userSetting;
	
	@Before
	public void setUp() {
		this.userSetting = new UserSetting();
		this.userSetting.setUserId("userId");
		this.userSetting.setPageViewNum(10);   
		this.userSetting.setTaskEndNotiType("N");	
		
		userSettingService.create(this.userSetting);
	}
	
	@Test
	public void testCreate() {
		UserSetting result = userSettingService.read(this.userSetting.getUserId());
		
		Assert.assertNotNull(result);
	}
	
	@Test
	@Ignore
	public void testExists() {
	}
	
	@Test
	@Ignore
	public void testRead() {
	}
	
	@Test
	@Ignore
	public void testDelete() {
	}
	
	@Test
	public void testUpdate() {
		this.userSetting.setPageViewNum(20);
		userSettingService.update(this.userSetting);
		
		UserSetting result = userSettingService.read(this.userSetting.getUserId());
		
		Assert.assertEquals(this.userSetting.getPageViewNum(), result.getPageViewNum());
	}
}
