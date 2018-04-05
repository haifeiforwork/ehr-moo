package com.lgcns.ikep4.supportpack.searchpreprocessor.dao;

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

import com.lgcns.ikep4.support.searchpreprocessor.dao.DictionaryDAO;
import com.lgcns.ikep4.support.searchpreprocessor.model.Dictionary;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
 
@RunWith(SpringJUnit4ClassRunner.class)
//@TransactionConfiguration(transactionManager="transactionManager",defaultRollback=false)
@ContextConfiguration({"classpath:/configuration/spring/context-dao.xml", "classpath:/configuration/spring/context-service.xml"})
public class DictionaryDaoTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	@Autowired
	private IdgenService idgenService;
	
	@Autowired
	DictionaryDAO dictionaryDao;
	
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
	public void testExists() {
		String searchKeywordId = dictionaryDao.create(dictionary);
		boolean exists = dictionaryDao.exists( searchKeywordId );
		Assert.assertTrue(exists);
	}
	
	@Test 
	public void testCreate() { 
		String searchKeywordId = dictionaryDao.create(dictionary);
		Assert.assertNotNull(searchKeywordId);
		
	}
	
	@Test
	public void testGet() { 
		String searchKeywordId = dictionaryDao.create(dictionary);
		Dictionary result = dictionaryDao.get(searchKeywordId); 
		Assert.assertNotNull(result);
	}
	
	
	@Test 
	public void testUpdate() {
		
		String searchKeywordId = dictionaryDao.create(dictionary);
		
		dictionary.setFrequencyCount(1);
		dictionary.setUpdateDate(new Date() );
		dictionary.setUpdaterId(userId);
		dictionary.setUpdaterName(userName);
		
		dictionaryDao.update(dictionary);
		
		Dictionary result = dictionaryDao.get(searchKeywordId); 
		
		Assert.assertNotNull(result);
	}
	
	@Test 
	public void testRemove() {
		String searchKeywordId = dictionaryDao.create(dictionary);
		dictionaryDao.remove(searchKeywordId);
		Dictionary result = dictionaryDao.get(searchKeywordId);
		Assert.assertNull(result); 
	}
	
	
	@Test 
	public void testGetList() {
		
		dictionaryDao.create(dictionary);
		
		List<Dictionary> list= dictionaryDao.getList(dictionary);
		
		Assert.assertNotNull(list);
	}
	
	@Test 
	public void testgetRankList() {
		
		dictionaryDao.create(dictionary);
		
		List<Dictionary> list= dictionaryDao.getRankList(dictionary);
		
		Assert.assertNotNull(list);
	}
	
	
	public void testgetBySearchKeyword() {
		
		String searchKeywordId = dictionaryDao.create(dictionary);
		Dictionary dic = new Dictionary();
		dic.setSearchKeyword(searchKeywordId);
		dic.setPortalId("P000001");
		
		Dictionary list= dictionaryDao.getBySearchKeyword(dic);
		
		Assert.assertNotNull(list);
	}
}
