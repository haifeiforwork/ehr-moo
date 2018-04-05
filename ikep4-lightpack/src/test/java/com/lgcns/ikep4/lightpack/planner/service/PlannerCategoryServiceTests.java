/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.planner.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.lgcns.ikep4.lightpack.BaseDaoTestCase;
import com.lgcns.ikep4.lightpack.planner.model.PlannerCategory;
import com.lgcns.ikep4.lightpack.planner.model.PlannerCategoryLocale;
import com.lgcns.ikep4.lightpack.planner.service.PlannerCategoryService;

/**
 * 범주 서비스 테스트
 *
 * @author 신용환(combinet@gmail.com)
 * @version $Id: PlannerCategoryServiceTests.java 16302 2011-08-19 08:43:50Z giljae $
 */
@ContextConfiguration({ "classpath:/configuration/spring/context-dao.xml",
"classpath:/configuration/spring/context-service.xml" })
public class PlannerCategoryServiceTests extends AbstractTransactionalJUnit4SpringContextTests {
	
	private PlannerCategory category;
	private List<PlannerCategoryLocale> localeList;
	
	@Autowired
	PlannerCategoryService service;
	
	
	@Before
	public void init() {
		if(category == null) {
			Date now = new Date();
			
			category = new PlannerCategory();

			category.setPortalId("P000001");
			category.setSeqId("100");
			category.setColor("#902900");
			category.setCategoryType("1");
			category.setDescription("업무 미팅");
			category.setRegisterId("29091029");
			category.setRegisterName("홍길동");
			category.setRegistDate(now);
			
			PlannerCategoryLocale plocale = new PlannerCategoryLocale();
			plocale.setCategoryId("1");
			plocale.setCategoryName("업무1");
			plocale.setLocaleCode("ko");
			plocale.setLocaleName("Korea");
			
			localeList = new ArrayList<PlannerCategoryLocale>();
			localeList.add(plocale);
			
			PlannerCategoryLocale plocale2 = new PlannerCategoryLocale();
			plocale2.setCategoryId("1");
			plocale2.setCategoryName("job1");
			plocale2.setLocaleCode("en");
			plocale2.setLocaleName("English");
			localeList.add(plocale2);
			
			category.setPlannerCategoryLocaleList(localeList);
			service.create(category);
		}
	}
	
	@Test
	public void get() {
		PlannerCategory c = service.read(category.getCategoryId());
		assertEquals(c.getCategoryId(), category.getCategoryId());
		assertEquals(2, c.getPlannerCategoryLocaleList().size());
	}
	
	@Test
	public void update() throws Exception {
		PlannerCategory uc = new PlannerCategory();
		BeanUtils.copyProperties(uc, category);
		
		PlannerCategoryLocale plocale = new PlannerCategoryLocale();
		plocale.setCategoryId("1");
		plocale.setCategoryName("개인");
		plocale.setLocaleCode("ko");
		plocale.setLocaleName("Korea");
		uc.getPlannerCategoryLocaleList().clear();
		uc.getPlannerCategoryLocaleList().add(plocale);
		
		service.update(uc);
		
		PlannerCategory c = service.read(category.getCategoryId());
		assertEquals(1, c.getPlannerCategoryLocaleList().size());
	}
	
	@Test
	public void getList() {
		List<PlannerCategory> list = service.getList();
		assertTrue(list.size() > 0);
	}
	
	@Test
	public void remove() {
		String id = category.getCategoryId();
		service.delete(id);
		
		PlannerCategory c = service.read(id);
		assertNull(c);
	}
	
	@Test
	public void deleteList() {
		String id = category.getCategoryId();
		String[] cid = {id};
		service.delete(cid);
		
		PlannerCategory c = service.read(id);
		assertNull(c);
	}
	
	@Test
	public void deleteCategory() {
		String id = category.getCategoryId();
		String[] cid = {id};
		service.deleteCategory(cid);
		
		PlannerCategory c = service.read(id);
		assertNull(c);
	}
	
	@Test
	public void readWithLocale() {
		PlannerCategory c = service.readWithLocale(category.getCategoryId());
		List list = service.getLocaleList();
		assertEquals(c.getPlannerCategoryLocaleList().size(), list.size());
		
		c = service.readWithLocale("");
		assertEquals(c.getPlannerCategoryLocaleList().size(), list.size());
		
		c = service.readWithLocale(null);
		assertEquals(c.getPlannerCategoryLocaleList().size(), list.size());
	}
	
	@Test 
	public void getListByLocale() {
		List<PlannerCategory> list = service.getList("ko");
		assertTrue(list.size() > 0);
	}
}
