/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.modeler.test.dao;

import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.workflow.modeler.dao.InstanceTrackingDataDao;
import com.lgcns.ikep4.workflow.modeler.model.InstanceTrackingData;
import com.lgcns.ikep4.workflow.modeler.model.ProcessModel;

/**
 * TODO Javadoc주석작성
 *
 * @author 김동후
 * @version $Id: InstanceTrackingDataDaoTest.java 16245 2011-08-18 04:28:59Z giljae $
 */
public class InstanceTrackingDataDaoTest extends BaseDaoTestCase {
	
	@Autowired
	private InstanceTrackingDataDao instanceTrackingDataDao;

	private InstanceTrackingData instanceTrackingData;
	
	private String instanceId;
	
	@Before
	public void setUp() throws SQLException {
		instanceId = "12E7E789AA3100000004065";
	}
	
	/**
	 * Instance Tracking Data 리스트
	 */
	@Test
	public void testInstanceTrackingData() {
		List<InstanceTrackingData> result = instanceTrackingDataDao.listInstanceTrackingData(instanceId);
		assertNotNull(result);
	}
}
