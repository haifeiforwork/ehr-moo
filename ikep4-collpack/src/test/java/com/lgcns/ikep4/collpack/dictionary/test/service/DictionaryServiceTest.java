/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.dictionary.test.service;

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
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lgcns.ikep4.collpack.dictionary.model.Dictionary;
import com.lgcns.ikep4.collpack.dictionary.search.DictionarySearchCondition;
import com.lgcns.ikep4.collpack.dictionary.service.DictionaryService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.poll.model.Poll;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * TODO Javadoc주석작성
 *
 * @author 서혜숙(shs0420@nate.com)
 * @version $Id: DictionaryServiceTest.java 16289 2011-08-19 06:52:01Z giljae $
 */
public class DictionaryServiceTest extends BaseServiceTestCase {

	@Autowired
	private DictionaryService dictionaryService;
	
	private MockHttpServletRequest req;

	User user;
	
	private Dictionary dictionary;

	private String pk;
	private String pk2;

	@Before
	public void setUp() {
		req = new MockHttpServletRequest(); // HttpServletRequest Mock객체를 생성
		MockHttpSession session = new MockHttpSession(); // HttpSession Mock객체를 생성
		
		user = new User();
		user.setUserId("user96");
		user.setUserName("사용자96");	
		
		session.setAttribute("ikep.user", user); // User 모델을 ikep.user라는 키로 세션에 저장
		req.setSession(session);

		// RequestContextHolder에 위에서 작업한 request 객체를 저장
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(req));
		
		dictionary = new Dictionary();
		
		dictionary.setDictionaryId("999999");
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
		
		dictionary.setSortOrder("9");
		dictionary.setDictionaryName("범용사전");		

		pk2 = dictionaryService.insertDictionary(dictionary);
		pk = dictionaryService.create(dictionary,user);
	}

	@Test
	public void testCreate() {
		Dictionary result = dictionaryService.read(pk);		
		assertNotNull(result);
	}

	@Test
	public void testInsertDictionary() {
		Dictionary result = dictionaryService.selectDictionary(pk2);		
		assertNotNull(result);
	}
	
	@Test
	public void testUpdate() {
		dictionary.setWordName("용어");
		dictionary.setWordEnglishName("Word");
		dictionary.setContents("내용수정");
		dictionary.setUpdaterId("바꿈ID");
		dictionary.setUpdaterName("바꿈이름");
		dictionaryService.update(dictionary);
		
		Dictionary result = dictionaryService.read(pk);
		
		assertEquals(this.dictionary.getWordName(), result.getWordName());
	}
	

	@Test
	public void testDelete() {
		dictionaryService.delete(dictionary.getWordId());
		Dictionary result = dictionaryService.read(dictionary.getWordId());
		assertNull(result);
	}	
	
	@Test
	public void testListDictionaryBySearchCondition() {		
		DictionarySearchCondition dictionarySearchCondition = new DictionarySearchCondition();
		dictionarySearchCondition.setPortalId(dictionary.getPortalId());
		
		SearchResult<Dictionary> result = dictionaryService.listDictionaryBySearchCondition(dictionarySearchCondition);
		assertNotNull(result);
	}	
	
	@Test
	public void testSelectDictionarys() {
		List<Dictionary> result = dictionaryService.selectDictionarys();
		assertNotNull(result);
	}	

	@Test
	public void testSelectDictionaryId() {
		DictionarySearchCondition dictionarySearchCondition = new DictionarySearchCondition();
		dictionarySearchCondition.setPortalId(dictionary.getPortalId());
		
		String dic = dictionaryService.selectDictionaryId(dictionarySearchCondition);
		assertNotNull(dic);
	}
	
	
	@Test
	public void testSelectWordHistoryList() {
		List<Dictionary> dic = dictionaryService.selectWordHistoryList(dictionary.getWordGroupId());
		assertNotNull(dic);
	}	
	
	@Test
	public void testReadDetail() {
		dictionary.setWordId(pk);
		Dictionary result = dictionaryService.readDetail(dictionary);
		assertEquals(1, result.getHitCount());

	}		
	
	@Test
	public void testDeleteDictionary() {
		dictionaryService.deleteDictionary(dictionary.getDictionaryId());
		Dictionary result = dictionaryService.read(dictionary.getDictionaryId());
		assertNull(result);
	}	
	
	@Test
	public void testUpdateDictionaryName() {
		dictionary.setDictionaryName("테스트사전");
		dictionaryService.updateDictionaryName(dictionary);
		
		Dictionary result = dictionaryService.read(pk);
		
		assertEquals(this.dictionary.getDictionaryName(), result.getDictionaryName());
	}	
	
	@Test
	public void testUpdateDictionarySortOrder() {
		String dictionaryIdes = "999999";
		dictionaryService.updateDictionarySortOrder(dictionaryIdes);
		
		Dictionary result = dictionaryService.selectDictionary(dictionary.getDictionaryId());
		
		assertEquals(this.dictionary.getSortOrder(), result.getSortOrder());
	}	
	
	
	@Test
	public void testIsDuplicateByWordName() {
		DictionarySearchCondition dictionarySearchCondition = new DictionarySearchCondition();
		dictionarySearchCondition.setPortalId(dictionary.getPortalId());
		dictionarySearchCondition.setSearchWord("CO");
		dictionarySearchCondition.setDictionaryId("999999");
		
		try{
			dictionaryService.isDuplicateByWordName(dictionarySearchCondition);
			assertTrue(true);
		} catch(Exception e){
			assertTrue(false);
		}
		
	}
	
}
