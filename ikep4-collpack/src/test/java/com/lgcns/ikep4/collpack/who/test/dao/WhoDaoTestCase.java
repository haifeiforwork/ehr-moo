/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.who.test.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.lgcns.ikep4.collpack.dictionary.model.Dictionary;
import com.lgcns.ikep4.collpack.dictionary.search.DictionarySearchCondition;
import com.lgcns.ikep4.collpack.who.dao.WhoDao;
import com.lgcns.ikep4.collpack.who.model.Pro;
import com.lgcns.ikep4.collpack.who.model.Who;
import com.lgcns.ikep4.collpack.who.search.WhoSearchCondition;


/**
 * TODO Javadoc주석작성
 *
 * @author 서혜숙(shs0420@nate.com)
 * @version $Id: WhoDaoTestCase.java 16289 2011-08-19 06:52:01Z giljae $
 */
//@ContextConfiguration(locations = { "classpath:/context-test.xml" })
//public class WhoDaoTestCase extends AbstractJUnit4SpringContextTests {
public class WhoDaoTestCase extends BaseDaoTestCase   {
	
	@Autowired
	private WhoDao whoDao;

	private Who who;

	private String pk = "90031";
	private String pk2 = "90032";
	private String pk3 = "90033";
	
	@Before
	public void setUp() {
		who = new Who();
		
		who.setProfileId(this.pk);
		who.setProfileGroupId(this.pk2);
		who.setName("홍길동");
		who.setCompanyName("길동주식회사");
		who.setTeamName("팀");
		who.setJobRankName("직급");
		who.setOfficePhoneno("01098761234");
		who.setMobile("01098761234");
		who.setMail("test@abc.com");
		who.setCompanyAddress("서울 개포구 개포동 11");
		who.setMemo("메모테스트");
		who.setHitCount(0);
		who.setVersion(1.0);
		who.setUpdateReason("주소변경");
		who.setPortalId("p1");
		who.setRegisterId("user96");
		who.setRegisterName("rgt");
		who.setUpdaterId("upd");
		who.setUpdaterName("upd");
		
		who.setReferenceId(this.pk3);
		
		String version = whoDao.selectVersion(who);
		assertNotNull(version);
		who.setVersion(Double.parseDouble(version));

		whoDao.create(who);
		whoDao.insertHit(who);
	
	}
	
	@Test
	public void testCreate() {		
		Who result = whoDao.get(pk);
		assertNotNull(result);
	}
	

	@Test
	public void testUpdate() {
		who.setCompanyName("Update길동주식회사");
		who.setTeamName("팀");
		who.setJobRankName("직급");
		who.setOfficePhoneno("01098761234");
		who.setMobile("01098761234");
		who.setMail("test@abc.com");
		who.setCompanyAddress("서울 개포구 개포동 11");
		who.setMemo("메모테스트");
		who.setUpdaterId("바꿈ID");
		who.setUpdaterName("바꿈이름");
		whoDao.update(who);
		Who result = whoDao.get(pk);
		assertEquals(who.getCompanyName(), result.getCompanyName());
	}	
	
	@Test
	public void testListWhoBySearchCondition() {
		WhoSearchCondition whoSearchCondition = new WhoSearchCondition();

		List<Who> result = whoDao.listWhoBySearchCondition(whoSearchCondition);
		assertNotNull(result);
	}
	
	@Test
	public void testSelectProfileHistoryList() {
		List<Who> profileHistoryList = whoDao.selectProfileHistoryList(pk2);
		assertNotNull(profileHistoryList);
	}
	
	@Test
	public void testCountWhoBySearchCondition() {
		WhoSearchCondition whoSearchCondition = new WhoSearchCondition();

		int count = whoDao.countWhoBySearchCondition(whoSearchCondition);
		assertNotNull(count);
	}
	
	@Test
	public void testDelete() {
		whoDao.deleteReferenceByProfileId(pk);
		Who who = new Who();
		who.setRegisterId("rgt");
		who.setProfileId(pk);
		int count = whoDao.checkAlreadyRead(who);
		assertEquals(count,0);
		
		whoDao.remove(pk);
		Who result = whoDao.get(pk);
		assertNull(result);
	}
	@Test
	public void testCheckAlreadyMailExists() {	
		int wordCount = whoDao.checkAlreadyMailExists(who);
		assertNotNull(wordCount);
	}	
	
	
}
