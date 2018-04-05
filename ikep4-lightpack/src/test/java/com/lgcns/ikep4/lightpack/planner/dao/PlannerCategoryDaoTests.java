/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.planner.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.lgcns.ikep4.lightpack.planner.dao.PlannerCategoryDao;
import com.lgcns.ikep4.lightpack.planner.model.PlannerCategory;
import com.lgcns.ikep4.lightpack.planner.model.PlannerCategoryLocale;


/**
 * TODO Javadoc주석작성
 *
 * @author 신용환(combinet@gmail.com)
 * @version $Id: PlannerCategoryDaoTests.java 16302 2011-08-19 08:43:50Z giljae $
 */
@ContextConfiguration({ "classpath:/configuration/spring/context-dao.xml",
"classpath:/configuration/spring/context-service.xml" })
public class PlannerCategoryDaoTests extends AbstractTransactionalJUnit4SpringContextTests { 
	//AbstractTransactionalJUnit4SpringContextTests {
	
	private PlannerCategory category;
	private List<PlannerCategoryLocale> localeList;
	
	@Autowired
	PlannerCategoryDao cdao;
	
	@Before
	public void init() {
		if(category == null) {
			Date now = new Date();
			
			category = new PlannerCategory();
			
			category.setPortalId("P000001");
			category.setCategoryId("23");
			category.setSeqId("2");
			category.setColor("#902900");
			category.setCategoryName("업무");
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
			cdao.create(category);
		}
	}
	
//	@Test
//	public void create() {
//		PlannerCategory c = cdao.get(category.getId());
//		assertEquals(c.getId(), category.getId());
//	}
	
	@Test
	@Ignore // service에서 test 대체
	public void get() {
		PlannerCategory c = cdao.get(category.getCategoryId());
		assertEquals(c.getCategoryId(), category.getCategoryId());
	}
	
	@Test
	@Ignore // service에서 test 대체
	public void update() throws Exception {
		PlannerCategory uc = new PlannerCategory();
		BeanUtils.copyProperties(uc, category);
		uc.setCategoryName("개인");
		
		cdao.update(uc);
		
		PlannerCategory c = cdao.get(category.getCategoryId());
		assertEquals(uc.getCategoryName(), c.getCategoryName());
	}
	
	@Test
	@Ignore // service에서 test 대체
	public void getList() {
		List<PlannerCategory> list = cdao.getList();
		assertTrue(list.size() > 0);
	}
	
	@Test
	public void remove() {
		String id = category.getCategoryId();
		cdao.remove(id);
		
		PlannerCategory c = cdao.get(id);
		assertNull(c);
	}
}
