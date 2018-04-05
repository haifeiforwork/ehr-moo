package com.lgcns.ikep4.supportpack.user.group.test.service;

import static org.junit.Assert.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;
import com.lgcns.ikep4.support.user.group.model.GroupType;
import com.lgcns.ikep4.support.user.group.service.GroupTypeService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/configuration/spring/context-dao.xml", "classpath:/configuration/spring/context-service.xml"})
public class GroupTypeServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private GroupTypeService groupTypeService;

	/**
	 * Log variable for all child classes. Uses LogFactory.getLog(getClass())
	 * from Commons Logging
	 */
	protected final Log log = LogFactory.getLog(getClass());

	private GroupType groupTypeCreate;
	private GroupType groupTypeUpdate;

	private AdminSearchCondition searchCondition;
	
	@Before
	public void setMethod() {

		groupTypeCreate = new GroupType();
		groupTypeCreate.setCodeCheck("success");
		groupTypeCreate.setGroupTypeId("testGroupTypeId");
		groupTypeCreate.setGroupTypeName("테스트");
		groupTypeCreate.setGroupTypeEnglishName("TEST");
		groupTypeCreate.setPortalId("P000001");
		groupTypeCreate.setRegisterId("admin");
		groupTypeCreate.setRegisterName("관리자");
		groupTypeCreate.setUpdaterId("admin");
		groupTypeCreate.setUpdaterName("관리자");
		
		groupTypeUpdate = new GroupType();
		groupTypeUpdate.setCodeCheck("modify");
		groupTypeUpdate.setGroupTypeId("testGroupTypeId");
		groupTypeUpdate.setGroupTypeName("테스트Update");
		groupTypeUpdate.setGroupTypeEnglishName("TESTup");
		groupTypeUpdate.setPortalId("P000001");
		groupTypeUpdate.setRegisterId("admin");
		groupTypeUpdate.setRegisterName("관리자");
		groupTypeUpdate.setUpdaterId("admin");
		groupTypeUpdate.setUpdaterName("관리자");
		
		searchCondition = new AdminSearchCondition();
		searchCondition.setFieldName("groupTypeName");
		searchCondition.setStartRowIndex(0);
		searchCondition.setEndRowIndex(10);
	}
	
	@Test
	public void createGroupType() {
		
		//그룹타입ID를 생성한 뒤에 생성한 그룹타입ID의 groupTypeId로 read를 수행하여 Null이 아니면 True
		groupTypeService.create(groupTypeCreate);
		assertNotNull(groupTypeService.read(groupTypeCreate.getGroupTypeId()));
	}
	
	@Test
	public void exists() {
		
		//그룹타입ID를 생성한 뒤에 생성한 그룹타입ID가 존재하는지 확인. 존재하면 True
		groupTypeService.create(groupTypeCreate);
		assertTrue(groupTypeService.exists(groupTypeCreate.getGroupTypeId()));
	}
	
	@Test
	public void read() {
		
		// 테스트 groupType를 생성한 후 해당 groupType를 읽어오는 조건으로 조회한 뒤 결과가 null이 아니면 True
		groupTypeService.create(groupTypeCreate);
		GroupType tempCreate = groupTypeService.read(groupTypeCreate.getGroupTypeId());
		
		assertNotNull(tempCreate);
	}
	
	@Test
	public void delete() {
		
		//i18nMessage에 해당 객체가 없으면 True, groupType에 해당 객체가 없으면 True
		groupTypeService.create(groupTypeCreate);
		groupTypeService.delete(groupTypeCreate.getGroupTypeId());
		
		assertNull(groupTypeService.read(groupTypeCreate.getGroupTypeId()));
	}
	
	@Test
	public void update() {

		// 특정한 조건으로 tempCreate을 생성하고 다른 조건으로 update 한 후 
		// 각각의 GroupType 객체를 비교하여 같지 않으면 True
		groupTypeService.create(groupTypeCreate);
		GroupType tempCreate = groupTypeService.read(groupTypeCreate.getGroupTypeId());
		
		groupTypeService.update(groupTypeUpdate);
		GroupType tempUpdate = groupTypeService.read(groupTypeUpdate.getGroupTypeId());
		
		assertNotSame(tempCreate.getGroupTypeName(), tempUpdate.getGroupTypeName());
	}
	
	@Test
	public void list() {
		
		// 목록을 조회한 뒤에 null이 아니면 True
		groupTypeService.create(groupTypeCreate);
		
		assertNotNull(groupTypeService.list(searchCondition));
	}
	
}
