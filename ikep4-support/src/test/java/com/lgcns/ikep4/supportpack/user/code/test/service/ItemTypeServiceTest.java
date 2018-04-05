package com.lgcns.ikep4.supportpack.user.code.test.service;

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
import com.lgcns.ikep4.support.user.code.model.ItemType;
import com.lgcns.ikep4.support.user.code.service.ItemTypeService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:/configuration/spring/context-dao.xml",
		"classpath:/configuration/spring/context-service.xml" })
public class ItemTypeServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private ItemTypeService itemTypeService;

	/**
	 * Log variable for all child classes. Uses LogFactory.getLog(getClass())
	 * from Commons Logging
	 */
	protected final Log log = LogFactory.getLog(getClass());

	private ItemType itemTypeCreate;

	private ItemType itemTypeUpdate;

	private AdminSearchCondition searchCondition;

	@Before
	public void setMethod() {
		// 테스트에 사용할 객체들을 세팅
		itemTypeCreate = new ItemType();
		itemTypeCreate.setCodeCheck("success");
		itemTypeCreate.setItemTypeCode("testItemTypeCode");
		itemTypeCreate.setItemTypeName("테스트");
		itemTypeCreate.setRegisterId("admin");
		itemTypeCreate.setRegisterName("관리자");
		itemTypeCreate.setUpdaterId("admin");
		itemTypeCreate.setUpdaterName("관리자");

		itemTypeUpdate = new ItemType();
		itemTypeUpdate.setCodeCheck("modify");
		itemTypeUpdate.setItemTypeCode("testItemTypeCode");
		itemTypeUpdate.setItemTypeName("테스트Update");
		itemTypeUpdate.setRegisterId("admin");
		itemTypeUpdate.setRegisterName("관리자");
		itemTypeUpdate.setUpdaterId("admin");
		itemTypeUpdate.setUpdaterName("관리자");

		searchCondition = new AdminSearchCondition();
		searchCondition.setFieldName("itemTypeName");
		searchCondition.setUserLocaleCode("ko");
		searchCondition.setStartRowIndex(0);
		searchCondition.setEndRowIndex(10);
	}

	@Test
	public void create() {
		// 아이템타입을 생성한 뒤에 생성한 아이템타입으로 read를 수행하여 Null이 아니면 True
		itemTypeService.create(itemTypeCreate);
		assertNotNull(itemTypeService.read(itemTypeCreate.getItemTypeCode()));
	}

	@Test
	public void exists() {
		// 아이템타입을 생성한 뒤에 생성한 아이템타입이 존재하는지 확인. 존재하면 True
		itemTypeService.create(itemTypeCreate);
		assertTrue(itemTypeService.exists(itemTypeCreate.getItemTypeCode()));
	}

	@Test
	public void read() {
		// 테스트 itemType를 생성한 후 해당 itemType를 읽어오는 조건으로 조회한 뒤 결과가 null이 아니면 True
		itemTypeService.create(itemTypeCreate);
		assertNotNull(itemTypeService.read(itemTypeCreate.getItemTypeCode()));
	}

	@Test
	public void update() {
		// 특정한 조건으로 tempCreate을 생성하고 다른 조건으로 update 한 후
		// 각각의 ItemType 객체를 비교하여 같지 않으면 True
		itemTypeService.create(itemTypeCreate);
		ItemType tempCreate = itemTypeService.read(itemTypeCreate.getItemTypeCode());
		itemTypeService.update(itemTypeUpdate);
		ItemType tempUpdate = itemTypeService.read(itemTypeCreate.getItemTypeCode());

		assertNotSame(tempCreate, tempUpdate);
	}

	@Test
	public void list() {
		// 목록을 조회한 뒤에 null이 아니면 True
		assertNotNull(itemTypeService.list(searchCondition));
	}

}
