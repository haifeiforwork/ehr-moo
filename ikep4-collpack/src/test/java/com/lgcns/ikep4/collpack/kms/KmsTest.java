/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.kms;

import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lgcns.ikep4.collpack.collaboration.test.dao.BaseDaoTestCase;
import com.lgcns.ikep4.collpack.kms.batch.dao.KmsBatchDao;
import com.lgcns.ikep4.collpack.kms.perform.dao.PerformanceDao;
import com.lgcns.ikep4.collpack.kms.perform.model.Performance;
import com.lgcns.ikep4.collpack.kms.perform.search.PerformanceSearchCondition;
import com.lgcns.ikep4.collpack.kms.perform.service.PerformanceService;

/**
 * TODO Javadoc주석작성
 *
 * @author 김종철
 * @version $Id: AllianceDaoTest.java 16289 2011-08-19 06:52:01Z giljae $
 */
public class KmsTest extends BaseKmsTestCase{
	@Autowired
	private PerformanceDao performanceDao;
	
	@Autowired
	private PerformanceService performanceService;
	
	@Autowired
	private SqlMapClient sqlMapClientInfo;
	
	@Autowired
	private KmsBatchDao kmsBatchDao;


	//@Before
	public void setUp() {
	
	}

	@Test
	@Rollback(false)
	public void testCreate() {		
		
		
		/*연간 누적*/
		PerformanceSearchCondition searchCondition = new PerformanceSearchCondition();
		searchCondition.setUserId("dwlee");
		searchCondition.setSearchYear("2012");
		searchCondition.setFromLeft("Y");
		
		Performance performance = performanceService.getPrivatePerformance(searchCondition);
		System.out.println("Year:" + performance.getRegSum() + ", " + performance.getMarkSum() + ", " + performance.getConversionMark());
		
		/*월별 점수*/
		searchCondition = new PerformanceSearchCondition();
		searchCondition.setUserId("dwlee");
		searchCondition.setSearchYear("2012");
		searchCondition.setSearchMonth("10");
		searchCondition.setFromLeft("Y");
		
		
		performance = performanceService.getPrivatePerformance(searchCondition);
		System.out.println("Month:" + performance.getRegSum() + ", " + performance.getMarkSum() + ", " + performance.getConversionMark());
		
		
		
	}
	
	@Test
	@Rollback(false)
	public void information(){
		
		
		kmsBatchDao.setSqlMapClientInfo(sqlMapClientInfo);
		String employeeId = (String)kmsBatchDao.get("");
		
		System.out.println("employeeId:" + employeeId);
		
		
		
	}
	
}
