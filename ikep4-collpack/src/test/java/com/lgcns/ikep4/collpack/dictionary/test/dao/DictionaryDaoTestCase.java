/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.dictionary.test.dao;

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

import com.lgcns.ikep4.collpack.dictionary.dao.DictionaryDao;
import com.lgcns.ikep4.collpack.dictionary.model.Dictionary;
import com.lgcns.ikep4.collpack.dictionary.search.DictionarySearchCondition;


/**
 * TODO Javadoc주석작성
 *
 * @author 서혜숙(shs0420@nate.com)
 * @version $Id: DictionaryDaoTestCase.java 16289 2011-08-19 06:52:01Z giljae $
 */
//@ContextConfiguration(locations = { "classpath:/context-test.xml" })
//public class DictionaryDaoTestCase extends AbstractJUnit4SpringContextTests {
public class DictionaryDaoTestCase extends BaseDaoTestCase   {
	
	@Autowired
	private DictionaryDao dictionaryDao;

	private Dictionary dictionary;

	private String pk = "90031";
	private String pk2 = "90032";
	private String pk3 = "90033";
	private String pk4 = "90034";
	
	@Before
	public void setUp() {
		dictionary = new Dictionary();
		
		dictionary.setWordId(this.pk);
		dictionary.setWordGroupId(this.pk2);
		dictionary.setDictionaryId(this.pk4);
		dictionary.setWordName("테스트");
		dictionary.setWordEnglishName("test");
		dictionary.setContents("내용테스트");
		dictionary.setHitCount(0);
		dictionary.setVersion(1.0);
		dictionary.setPortalId("p1");
		dictionary.setRegisterId("rgt");
		dictionary.setRegisterName("rgt");
		dictionary.setUpdaterId("upd");
		dictionary.setUpdaterName("upd");
		dictionary.setUpdateReason("상세변경");
		
		dictionary.setSortOrder("1");
		dictionary.setDictionaryName("범용사전");
		
		dictionary.setReferenceId(this.pk3);
		
		String version = dictionaryDao.selectVersion(dictionary);
		assertNotNull(version);
		dictionary.setVersion(Double.parseDouble(version));

		dictionaryDao.insertDictionary(dictionary);
		dictionaryDao.create(dictionary);
		dictionaryDao.insertHit(dictionary);
	
	}

	
	@Test
	public void testCreate() {		
		Dictionary result = dictionaryDao.get(pk);
		assertNotNull(result);
	}
	
	@Test
	public void testSelectDictionary() {
		Dictionary result = dictionaryDao.selectDictionary(pk4);
		assertNotNull(result);
	}	
	
	@Test
	public void testUpdate() {
		dictionary.setWordName("용어");
		dictionary.setWordEnglishName("Word");
		dictionary.setContents("내용수정");
		dictionary.setUpdaterId("바꿈ID");
		dictionary.setUpdaterName("바꿈이름");
		dictionaryDao.update(dictionary);
		Dictionary result = dictionaryDao.get(pk);
		assertEquals(dictionary.getWordName(), result.getWordName());
	}	
	
	@Test
	public void testUpdateDictionaryName() {
		dictionary.setDictionaryName("회계사전");
		dictionaryDao.updateDictionaryName(dictionary);
		Dictionary result = dictionaryDao.selectDictionary(pk4);
		assertEquals(dictionary.getDictionaryName(), result.getDictionaryName());
	}
	
	@Test
	public void testUpdateDictionarySortOrder() {
		dictionary.setSortOrder("2");
		dictionaryDao.updateDictionarySortOrder(dictionary);
		Dictionary result = dictionaryDao.selectDictionary(pk4);
		assertEquals(dictionary.getSortOrder(), result.getSortOrder());
	}
	
	@Test
	public void testListDictionaryBySearchCondition() {
		DictionarySearchCondition dictionarySearchCondition = new DictionarySearchCondition();
		dictionarySearchCondition.setPortalId(dictionary.getPortalId());

		List<Dictionary> result = dictionaryDao.listDictionaryBySearchCondition(dictionarySearchCondition);
		assertNotNull(result);
	}

	@Test
	public void testSelectDictionarys() {
		List<Dictionary> result = dictionaryDao.selectDictionarys();
		assertNotNull(result);
	}

	@Test
	public void testSelectDictionaryId() {
		
		DictionarySearchCondition dictionarySearchCondition = new DictionarySearchCondition();
		dictionarySearchCondition.setPortalId(dictionary.getPortalId());
		
		String dic = dictionaryDao.selectDictionaryId(dictionarySearchCondition);
		assertNotNull(dic);
	}
	
	
	@Test
	public void testSelectWordHistoryList() {
		List<Dictionary> dic = dictionaryDao.selectWordHistoryList(pk2);
		assertNotNull(dic);
	}
	
	@Test
	public void testCountDictionaryBySearchCondition() {
		DictionarySearchCondition dictionarySearchCondition = new DictionarySearchCondition();
		dictionarySearchCondition.setPortalId(dictionary.getPortalId());

		int count = dictionaryDao.countDictionaryBySearchCondition(dictionarySearchCondition);
		assertNotNull(count);
	}	
	
	@Test
	public void testDelete() {
		dictionaryDao.deleteReferenceByWordId(pk);
		Dictionary dictionary = new Dictionary();
		dictionary.setRegisterId("rgt");
		dictionary.setWordId(pk);
		dictionary.setDictionaryId(pk4);
		int count = dictionaryDao.checkAlreadyRead(dictionary);
		assertEquals(count,0);
		
		dictionaryDao.remove(pk);
		Dictionary result = dictionaryDao.get(pk);
		assertNull(result);
	}
	
	@Test
	public void testDeleteDictionary() {
		dictionaryDao.deleteReferenceByDictionaryId(pk4);
		Dictionary dictionary = new Dictionary();
		dictionary.setRegisterId("rgt");
		dictionary.setWordId(pk);
		dictionary.setDictionaryId(pk4);
		int count = dictionaryDao.checkAlreadyRead(dictionary);
		assertEquals(count,0);
		
		dictionaryDao.remove(pk);
		Dictionary result = dictionaryDao.get(pk);
		assertNull(result);
		
		dictionaryDao.deleteDictionary(pk4);
		Dictionary dicResult = dictionaryDao.selectDictionary(pk4);
		assertNull(dicResult);		
	}

	@Test
	public void testSelectDictionaryGroup() {
		DictionarySearchCondition dictionarySearchCondition = new DictionarySearchCondition();

		List<Dictionary> result = dictionaryDao.selectDictionaryGroup(dictionarySearchCondition);
		assertNotNull(result);
	}
	
}
