package com.lgcns.ikep4.supportpack.searchpreprocessor.service;

import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lgcns.ikep4.support.searchpreprocessor.model.Dictionary;
import com.lgcns.ikep4.support.searchpreprocessor.service.DictionaryService;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
 
@RunWith(SpringJUnit4ClassRunner.class)
//@TransactionConfiguration(transactionManager="transactionManager",defaultRollback=false)
@ContextConfiguration({"classpath:/configuration/spring/context-dao.xml", "classpath:/configuration/spring/context-service.xml"})
public class DictionaryServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	@Autowired
	private IdgenService idgenService;
	
	@Autowired
	DictionaryService dictionaryService;
	
	private String userId="ihko11";
	private String userName="고인호";
	
	private  Dictionary dictionary = new Dictionary();
	
	@Before
	public void setUpBefore() {
		
		String searchKeywordId = idgenService.getNextId();
		
		dictionary.setPortalId(userId);
		dictionary.setSearchKeywordId(searchKeywordId);
		dictionary.setSearchKeyword(searchKeywordId);
		dictionary.setFrequencyCount(1);
		dictionary.setPortalId(userId);
		
		dictionary.setRegistDate(new Date() );
		dictionary.setRegisterId(userId);
		dictionary.setRegisterName(userName);
		dictionary.setUpdateDate(new Date() );
		dictionary.setUpdaterId(userId);
		dictionary.setUpdaterName(userName);
	}
	
	
	@Test
	public void exists() {
		String searchKeywordId = dictionaryService.create(dictionary);
		boolean exists = dictionaryService.exists( searchKeywordId );
		Assert.assertTrue(exists);
	}
	
	@Test 
	public void create() { 
		String searchKeywordId = dictionaryService.create(dictionary);
		Assert.assertNotNull(searchKeywordId);
		
	}
	
	@Test
	public void read() { 
		String searchKeywordId = dictionaryService.create(dictionary);
		Dictionary result = dictionaryService.read(searchKeywordId); 
		Assert.assertNotNull(result);
	}
	
	
	@Test 
	public void update() {
		
		String searchKeywordId = dictionaryService.create(dictionary);
		
		dictionary.setFrequencyCount(1);
		dictionary.setUpdateDate(new Date() );
		dictionary.setUpdaterId(userId);
		dictionary.setUpdaterName(userName);
		
		dictionaryService.update(dictionary);
		
		Dictionary result = dictionaryService.read(searchKeywordId); 
		
		Assert.assertNotNull(result);
	}
	
	@Test 
	public void delete() {
		String searchKeywordId = dictionaryService.create(dictionary);
		dictionaryService.delete(searchKeywordId);
		boolean exists = dictionaryService.exists( searchKeywordId );
		Assert.assertFalse(exists);
	}
	
	
	@Test 
	public void getList() {
		
		dictionaryService.create(dictionary);
		
		List<Dictionary> list= dictionaryService.getList(dictionary);
		
		Assert.assertNotNull(list);
	}
	
	@Test 
	public void getRankList() {
		
		dictionaryService.create(dictionary);
		
		List<Dictionary> list= dictionaryService.getRankList(dictionary);
		
		Assert.assertNotNull(list);
	}
	
}
