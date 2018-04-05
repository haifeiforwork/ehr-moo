/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.qna.test.service;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.collpack.qna.model.QnaPolicy;
import com.lgcns.ikep4.collpack.qna.service.QnaPolicyService;

/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: QnaPolicyServiceTest.java 16289 2011-08-19 06:52:01Z giljae $
 */
public class QnaPolicyServiceTest extends BaseServiceTestCase {

	@Autowired
	private QnaPolicyService qnaPolicyService;
	
	private QnaPolicy qnaPolicy;
	
	private String pk;

	@Before
	public void setUp() {
		
		qnaPolicy = new QnaPolicy();
		
		qnaPolicy.setPolicyId("1");
		qnaPolicy.setQnaType("0");
		qnaPolicy.setHitWeight(2);
		qnaPolicy.setLinereplyWeight(2);
		qnaPolicy.setAnswerWeight(2);
		qnaPolicy.setFavoriteWeight(2);
		qnaPolicy.setbestQnaBasisPoint(20);
		qnaPolicy.setAnswerLinereplyWeight(2);
		qnaPolicy.setAnswerRecommendWeight(2);
		qnaPolicy.setAnswerAdoptWeight(2);
		qnaPolicy.setbestAnswerBasisPoint(20);
		qnaPolicy.setRegisterId("lee");
		qnaPolicy.setRegisterName("이동희");
		qnaPolicy.setPortalId("p1");
		
		pk = qnaPolicyService.create(qnaPolicy);
	}

	@Test
	//@Rollback(false)
	public void testList() {
		
		List<QnaPolicy> result = qnaPolicyService.listPolicy("p1");
		
		assertNotNull(result);
	}
	
	@Test
	public void testReadCount() {
		
		int result = qnaPolicyService.readCount("p1");
		
		assertNotNull(result);
	}

	@Test
	public void testUpdate() {
		
		
        qnaPolicy.setPolicyId(pk);
        qnaPolicy.setQnaType("0");
		qnaPolicy.setHitWeight(2);
		qnaPolicy.setLinereplyWeight(5);
		qnaPolicy.setAnswerWeight(5);
		qnaPolicy.setFavoriteWeight(2);
		qnaPolicy.setRegisterId("lee");
		qnaPolicy.setRegisterName("dong");
		
		qnaPolicyService.update(qnaPolicy);
		
		List<QnaPolicy> result = qnaPolicyService.listPolicy("p1");
		
		assertNotNull(result);
	}

	@Test
	public void testDelete() {
		
		qnaPolicyService.delete(pk);
		List<QnaPolicy> result = qnaPolicyService.listPolicy("p1");
		assertNotNull(result);
		
	}
	

}
