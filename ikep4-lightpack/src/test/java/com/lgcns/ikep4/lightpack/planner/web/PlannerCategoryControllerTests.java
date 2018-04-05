/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.planner.web;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.lightpack.planner.web.PlannerCategoryController;


/**
 * 범주 서비스 테스트
 * 
 * @author 신용환(combinet@gmail.com)
 * @version $Id: PlannerCategoryControllerTests.java 16302 2011-08-19 08:43:50Z giljae $
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/context-test.xml" })
public class PlannerCategoryControllerTests {

	@Autowired
	PlannerCategoryController controller;

	@Autowired
	private ApplicationContext applicationContext;

	private MockHttpServletRequest request;

	private MockHttpServletResponse response;

	private HandlerAdapter handlerAdapter;

	@Before
	public void setUp() {
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		handlerAdapter = applicationContext.getBean(HandlerAdapter.class);
	}
	@Ignore
	@Test
	public void list() throws Exception {
		request.setMethod("GET");
		request.setRequestURI("/lightpack/planner/category/list.do");
		ModelAndView mav = handlerAdapter.handle(request, response, controller);
		ModelAndViewAssert.assertViewName(mav, "lightpack/planner/category/list");
	}
	@Ignore
	@Test
	public void listjson() throws Exception {
		List list = controller.listjson(response);	
		assertTrue(list.size() > 0);
	}
}
