/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.work.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.approval.admin.test.dao.BaseDaoTestCase;
import com.lgcns.ikep4.wfapproval.work.dao.ApListDao;
import com.lgcns.ikep4.wfapproval.work.model.ApList;
import com.lgcns.ikep4.wfapproval.work.search.ApListSearchCondition;

/**
 * TODO Javadoc주석작성
 *
 * @author 장규진
 * @version $Id: ApListDaoTest.java 16234 2011-08-18 02:44:36Z giljae $
 */
public class ApListDaoTest extends  BaseDaoTestCase {
	@Autowired
	private ApListDao apListDao;
	
	//@Autowired
	//private IdgenService idgenService;
	
	//private ApList apList;
	private String id;

	private ApListSearchCondition apListSearchCondition = new ApListSearchCondition();
	
	/**TODO Javadoc주석작성
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		//apList = new ApList();
		this.id = "user1";
		
		apListSearchCondition.setRegistUserId(this.id);
		apListSearchCondition.setApprTitle("모델18");
		apListSearchCondition.setApprDocState(0);
	}

	/**
	 * Test method for {@link com.lgcns.ikep4.wfapproval.work.dao.ApListDao#getApSearchList(com.lgcns.ikep4.wfapproval.work.search.ApListSearchCondition)}.
	 */
	@Test
	public void testGetApSearchList() {
		//fail("Not yet implemented");
		List<ApList> result = this.apListDao.getApSearchList(apListSearchCondition);
		assertNotNull(result);
	}

	/**
	 * Test method for {@link com.lgcns.ikep4.wfapproval.work.dao.ApListDao#getCountApList(com.lgcns.ikep4.wfapproval.work.search.ApListSearchCondition)}.
	 */
	@Test
	public void testGetCountApList() {
		//fail("Not yet implemented");
		Integer result = this.apListDao.getCountApList(apListSearchCondition);
		assertTrue(result >= 0);
	}

}
