/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.qna.test.service;

import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.collpack.qna.service.QnaStatisticsService;

/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: QnaStatisticsServiceTest.java 16289 2011-08-19 06:52:01Z giljae $
 */
public class QnaStatisticsServiceTest extends BaseServiceTestCase {

	@Autowired
	private QnaStatisticsService qnaStatisticsService;
	

	@Test
	//@Rollback(false)
	@Ignore
	public void testCreateRead() {
		try {
			qnaStatisticsService.create();
			assertTrue(true);
		} catch (Exception e) {
			assertTrue(false);
		}		
	}
	
	

}
