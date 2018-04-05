/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.workplace.service;

import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.lgcns.ikep4.support.timezone.service.TimeZoneSupportService;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.workflow.engine.model.DelegateBean;

/**
 * TODO Javadoc주석작성
 *
 * @author 이재경
 * @version $Id: PersonalSetServiceTest.java 16245 2011-08-18 04:28:59Z giljae $
 */
public class PersonalSetServiceTest extends BaseServiceTestCase {
	
	@Autowired
	private PersonalSetService personalSetService;
	
	@Autowired
	private IdgenService idgenService;
	
	private DelegateBean delegateBean;
	
	@Before
	public void setUp(){
		delegateBean = new DelegateBean();
		delegateBean.setIsSetup("Y");
		delegateBean.setUserId("user1");
		Date startDate	= new Date();
		Date endDate	= new Date();
		delegateBean.setStartDate(startDate);
		delegateBean.setEndDate(endDate);
		delegateBean.setMandatorId("user2");
		delegateBean.setReasonComment("test");
	}
	
	@Test
	@Rollback(false)
	public void delegateCreate() {
		
		DelegateBean deleBean = (DelegateBean)personalSetService.delegateDetail(this.delegateBean);
		if( deleBean == null ){
			delegateBean.setSeqId(idgenService.getNextId());
			assertNotNull(personalSetService.delegateCreate(this.delegateBean));
		}
	}
	
	@Test
	@Rollback(false)
	public void delegateDetail() {
		assertNotNull((DelegateBean)personalSetService.delegateDetail(this.delegateBean));
	}
	
	@Test
	@Rollback(false)
	public void delegateUpdate() {
		delegateBean.setIsSetup("N");
		Date startDate	= new Date();
		Date endDate	= new Date();		
		delegateBean.setStartDate(startDate);
		delegateBean.setEndDate(endDate);
		delegateBean.setMandatorId("user5");
		delegateBean.setReasonComment("Modify");
		assertNotNull(personalSetService.delegateUpdate(this.delegateBean));
	}
}