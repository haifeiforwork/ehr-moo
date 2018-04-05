/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.qna.test.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.AssertFalse;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.collpack.qna.dao.QnaCategoryDao;
import com.lgcns.ikep4.collpack.qna.dao.QnaDao;
import com.lgcns.ikep4.collpack.qna.dao.QnaExpertDao;
import com.lgcns.ikep4.collpack.qna.dao.QnaPolicyDao;
import com.lgcns.ikep4.collpack.qna.dao.QnaRecommendDao;
import com.lgcns.ikep4.collpack.qna.dao.QnaStatisticsDao;
import com.lgcns.ikep4.collpack.qna.model.Qna;
import com.lgcns.ikep4.collpack.qna.model.QnaCategory;
import com.lgcns.ikep4.collpack.qna.model.QnaExpert;
import com.lgcns.ikep4.collpack.qna.model.QnaPolicy;
import com.lgcns.ikep4.collpack.qna.model.QnaRecommend;
import com.lgcns.ikep4.collpack.qna.model.QnaStatistics;


/**
 * TODO Javadoc주석작성
 * 
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: QnaStatisticsDaoTestCase.java 1529 2011-03-02 10:04:41Z
 *          loverfairy $
 */
public class QnaStatisticsDaoTestCase extends BaseDaoTestCase {

	@Autowired
	private QnaStatisticsDao qnaStatisticsDao;

	private QnaStatistics qnaStatistics;

	@Before
	public void setUp() {

		qnaStatisticsDao.remove("p1");

		qnaStatistics = new QnaStatistics();

		qnaStatistics.setAnswerRatio(3);
		qnaStatistics.setAverageAnswerTime(4);
		qnaStatistics.setPortalId("p1");

		qnaStatisticsDao.create(qnaStatistics);

	}

	@Test
	public void testSelect() {

		QnaStatistics result = qnaStatisticsDao.get("p1");

		assertNotNull(result);
	}

	@Test
	public void testUpdate() {

		QnaStatistics test = qnaStatisticsDao.get("p1");

		test.setAnswerRatio(9);
		test.setAverageAnswerTime(9);

		qnaStatisticsDao.update(test);

		QnaStatistics result = qnaStatisticsDao.get("p1");
		assertEquals(result.getAnswerRatio(), 9);

	}

}
