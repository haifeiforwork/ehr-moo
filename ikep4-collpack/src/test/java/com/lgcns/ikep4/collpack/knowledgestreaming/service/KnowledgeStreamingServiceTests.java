/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.knowledgestreaming.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

/**
 * TODO Javadoc주석작성
 *
 * @author  신용환(combinet@gmail.com)
 * @version $Id: KnowledgeStreamingServiceTests.java 16627 2011-09-23 07:33:53Z giljae $
 */
@ContextConfiguration(locations = { "classpath*:/configuration/spring/context-dao.xml", 
		"classpath*:/configuration/spring/context-service.xml" })
public class KnowledgeStreamingServiceTests extends AbstractJUnit4SpringContextTests {

	private final static String portalId = "P000001";
	
	@Autowired
	KnowledgeStreamingService kss;
	
}
