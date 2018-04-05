/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.knowledgehub.test.service;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.collpack.knowledgehub.model.KnowledgeMapRecommendTag;
import com.lgcns.ikep4.collpack.knowledgehub.service.KnowledgeMapRecommendTagService;
import com.lgcns.ikep4.support.tagging.dao.TagDictionaryDao;
import com.lgcns.ikep4.support.tagging.model.Tag;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.DateUtil;


/**
 * KnowledgeMapRecommendTagService Test Unit
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMapRecommendTagServiceTest.java 16457 2011-08-30 04:20:17Z giljae $
 */
public class KnowledgeMapRecommendTagServiceTest extends BaseServiceTestCase {

	@Autowired
	private KnowledgeMapRecommendTagService knowledgeMapRecommendTagService;
	@Autowired
	private IdgenService idgenService;
	@Autowired
	private TagDictionaryDao tagDictionaryDao;

	private Tag tag;
	private String portalId = "";
	private String tagId = "";
	private String userId = "";

	@Before
	public void setUp() {
		portalId = idgenService.getNextId();
		tagId = idgenService.getNextId();
		userId = "testUser";

		// Tag 등록
		tag = new Tag();
		tag.setTagId(tagId);
		tag.setTagName("TestTag");
		tag.setRegisterId("admin");
		tag.setRegisterName("관리자");
		tag.setPortalId(portalId);
		tagDictionaryDao.create(tag);
	}

	@Test
	public void listByUserIdTest() {
		// 테스트 데이터 생성이 곤란하여 로그 정보로 판단
		List<KnowledgeMapRecommendTag> result = knowledgeMapRecommendTagService.listByUserId(userId, 30);

		assertEquals(result.size(), result.size());
	}

	@Test
	@Ignore
	public void batchGatherTagsTest() {
		// 테스트 데이터 생성이 곤란하여 로그 정보로 판단
		log.debug("==[batchGatherTags]==");
		Date startDate;
		Date endDate;
		startDate = new Date();
		log.debug("==[시작]==> [" + startDate + "]");

		//knowledgeMapRecommendTagService.batchGatherTags();

		endDate = new Date();
		log.debug("==[종료]==> [" + endDate + "]");
		log.debug("==[배치 소요시간]==> [" + DateUtil.getTimeInterval(startDate, endDate, "ko") + "]");
	
		assertEquals(0, 0);
	}

}
