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
import com.lgcns.ikep4.support.searchpreprocessor.model.Rank;
import com.lgcns.ikep4.support.searchpreprocessor.service.DictionaryService;
import com.lgcns.ikep4.support.searchpreprocessor.service.RankService;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
 
@RunWith(SpringJUnit4ClassRunner.class)
//@TransactionConfiguration(transactionManager="transactionManager",defaultRollback=false)
@ContextConfiguration({"classpath:/configuration/spring/context-dao.xml", "classpath:/configuration/spring/context-service.xml"})
public class RankServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	@Autowired
	private IdgenService idgenService;
	
	@Autowired
	RankService rankService;
	
	@Autowired
	DictionaryService dictionaryService;
	
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
		dictionaryService.create(dictionary);
		
		rank.setPreSearchRank(1);
		rank.setSearchKeywordId(searchKeywordId);
		rank.setSearchRank(1);
		
	}
	
	
	@Test
	public void exists() {
		String id = rankService.create(rank);
		boolean exists = rankService.exists( id );
		Assert.assertTrue(exists);
	}
	
	@Test 
	public void create() { 
		String id = rankService.create(rank);
		Assert.assertNotNull(id);
		
	}
	
	@Test
	public void read() { 
		String id = rankService.create(rank);
		Rank result = rankService.read(id); 
		Assert.assertNotNull(result);
	}
	
	
	@Test 
	public void update() {
		
		String id = rankService.create(rank);
		
		rank.setUpdateDate(new Date() );
		rank.setUpdaterId(userId);
		rank.setUpdaterName(userName);
		
		rankService.update(rank);
		
		Rank result = rankService.read(id); 
		
		Assert.assertNotNull(result);
	}
	
	@Test 
	public void delete() {
		String id = rankService.create(rank);
		rankService.delete(id);
		boolean exists = rankService.exists( id );
		Assert.assertFalse(exists);
	}
	
	@Test 
	public void getRankList() {
		rankService.create(rank);
		List<Rank> list= rankService.getRankList();
		Assert.assertNotNull(list);
	}
	
	@Test 
	public void removeAll() {
		String id = rankService.create(rank);
		rankService.removeAll();
		boolean exists = rankService.exists( id );
		Assert.assertFalse(exists);
	}
}
