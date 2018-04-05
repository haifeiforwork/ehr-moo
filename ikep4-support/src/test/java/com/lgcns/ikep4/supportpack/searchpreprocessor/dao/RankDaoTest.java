package com.lgcns.ikep4.supportpack.searchpreprocessor.dao;

import java.util.Date;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lgcns.ikep4.support.searchpreprocessor.dao.DictionaryDAO;
import com.lgcns.ikep4.support.searchpreprocessor.dao.RankDAO;
import com.lgcns.ikep4.support.searchpreprocessor.model.Dictionary;
import com.lgcns.ikep4.support.searchpreprocessor.model.Rank;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
 
@RunWith(SpringJUnit4ClassRunner.class)
//@TransactionConfiguration(transactionManager="transactionManager",defaultRollback=false)
@ContextConfiguration({"classpath:/configuration/spring/context-dao.xml", "classpath:/configuration/spring/context-service.xml"})
public class RankDaoTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	@Autowired
	private IdgenService idgenService;
	
	@Autowired
	RankDAO rankDao;
	
	@Autowired
	DictionaryDAO dictionaryDao;
	
	private String userId="ihko11";
	private String userName="고인호";
	
	private  Rank rank = new Rank();
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
		dictionaryDao.create(dictionary);
		
		rank.setPreSearchRank(1);
		rank.setSearchKeywordId(searchKeywordId);
		rank.setSearchRank(1);
		
	}
	
	
	@Test
	public void testExists() {
		String id = rankDao.create(rank);
		boolean exists = rankDao.exists( id );
		Assert.assertTrue(exists);
	}
	
	@Test 
	public void testCreate() { 
		String id = rankDao.create(rank);
		Assert.assertNotNull(id);
		
	}
	
	@Test
	public void testGet() { 
		String id = rankDao.create(rank);
		Rank result = rankDao.get(id); 
		Assert.assertNotNull(result);
	}
	
	
	@Test 
	public void testUpdate() {
		
		String id = rankDao.create(rank);
		
		rank.setUpdateDate(new Date() );
		rank.setUpdaterId(userId);
		rank.setUpdaterName(userName);
		
		rankDao.update(rank);
		
		Rank result = rankDao.get(id); 
		
		Assert.assertNotNull(result);
	}
	
	@Test 
	public void testRemove() {
		String id = rankDao.create(rank);
		rankDao.remove(id);
		Rank result = rankDao.get(id);
		Assert.assertNull(result); 
	}
}
