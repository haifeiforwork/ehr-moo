/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.qna.test.dao;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.collpack.qna.dao.QnaPolicyDao;
import com.lgcns.ikep4.collpack.qna.model.QnaPolicy;


/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: QnaPolicyDaoTestCase.java 16289 2011-08-19 06:52:01Z giljae $
 */
public class QnaPolicyDaoTestCase extends BaseDaoTestCase  {
	
	@Autowired
	private QnaPolicyDao qnaPolicyDao;

	private QnaPolicy qnaPolicy;

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
		
		qnaPolicyDao.create(qnaPolicy);
		
		qnaPolicy.setPolicyId("2");
		qnaPolicy.setQnaType("1");
		qnaPolicy.setHitWeight(23);
		qnaPolicy.setLinereplyWeight(3);
		qnaPolicy.setAnswerWeight(23);
		qnaPolicy.setFavoriteWeight(23);
		qnaPolicy.setbestQnaBasisPoint(23);
		qnaPolicy.setAnswerLinereplyWeight(23);
		qnaPolicy.setAnswerRecommendWeight(23);
		qnaPolicy.setAnswerAdoptWeight(23);
		qnaPolicy.setbestAnswerBasisPoint(23);
		qnaPolicy.setRegisterId("lee");
		qnaPolicy.setRegisterName("이동희");
		qnaPolicy.setPortalId("p1");
		
		qnaPolicyDao.create(qnaPolicy);
		
		
	}

	
	@Test
	public void testListPolicy() {
		
		List<QnaPolicy> result = qnaPolicyDao.listPolicy("p1");
		
		assertNotNull(result);
	}
	
	
	@Test
	public void testReadCount() {
		
		int result = qnaPolicyDao.readCount("p1");
		
		assertNotNull(result);
	}
	
	
	@Test
	public void testUpdate() {
		
		
		qnaPolicy.setPolicyId(qnaPolicy.getPolicyId());
		qnaPolicy.setAnswerWeight(5);
		
		qnaPolicyDao.update(qnaPolicy);
		
		List<QnaPolicy> result = qnaPolicyDao.listPolicy("p1");
		
		assertNotNull(result);
		
	}
	
	
	@Test
	public void testDelete() {
		
		qnaPolicyDao.remove(qnaPolicy.getPolicyId());
		
		int result = qnaPolicyDao.readCount(qnaPolicy.getPolicyId());
		assertFalse(result > 3);
		
	}
	

}
