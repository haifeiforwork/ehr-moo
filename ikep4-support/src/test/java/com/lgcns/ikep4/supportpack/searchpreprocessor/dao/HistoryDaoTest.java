package com.lgcns.ikep4.supportpack.searchpreprocessor.dao;

import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lgcns.ikep4.support.searchpreprocessor.dao.DictionaryDAO;
import com.lgcns.ikep4.support.searchpreprocessor.dao.HistoryDAO;
import com.lgcns.ikep4.support.searchpreprocessor.model.Dictionary;
import com.lgcns.ikep4.support.searchpreprocessor.model.History;
import com.lgcns.ikep4.support.searchpreprocessor.model.Report;
import com.lgcns.ikep4.support.searchpreprocessor.util.Criteria;
import com.lgcns.ikep4.support.searchpreprocessor.util.HistoryCons;
import com.lgcns.ikep4.support.searchpreprocessor.util.SearchUtil;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
 
@RunWith(SpringJUnit4ClassRunner.class)
//@TransactionConfiguration(transactionManager="transactionManager",defaultRollback=false)
@ContextConfiguration({"classpath:/configuration/spring/context-dao.xml", "classpath:/configuration/spring/context-service.xml"})
public class HistoryDaoTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	@Autowired
	private IdgenService idgenService;
	
	@Autowired
	DictionaryDAO dictionaryDao;
	
	@Autowired
	private HistoryDAO historyDao; 
	
	private String userId="ihko11";
	private String userName="고인호";
	
	private  Dictionary dictionary = new Dictionary();
	private  History history = new History();
	
	
	@Before
	public void setUpBefore() {
		
		String searchKeywordId = idgenService.getNextId();
		
		dictionary.setPortalId(userId);
		dictionary.setSearchKeywordId(searchKeywordId);
		dictionary.setSearchKeyword(searchKeywordId);
		dictionary.setFrequencyCount(1);
		
		dictionary.setRegistDate(new Date() );
		dictionary.setRegisterId(userId);
		dictionary.setRegisterName(userName);
		dictionary.setUpdateDate(new Date() );
		dictionary.setUpdaterId(userId);
		dictionary.setUpdaterName(userName);

		
		String searchHistoryId = idgenService.getNextId();
		
		history.setPortalId(userId);
		history.setSearchHistoryId(searchHistoryId);
		history.setSearchKeywordId(searchKeywordId);
		history.setSearchKeyword(searchKeywordId);
		
		history.setRegistDate(new Date() );
		history.setRegisterId(userId);
		history.setRegisterName(userName);
		history.setUpdateDate(new Date() );
		history.setUpdaterId(userId);
		history.setUpdaterName(userName);
		
	}
	
	
	@Test
	public void testExists() {
		
		Dictionary dic = new Dictionary();
		dic.setPortalId("P000001");
		dic.setSearchKeyword(history.getSearchKeyword());
		
		Dictionary dbInfo = dictionaryDao.getBySearchKeyword( dic );
		
		if( dbInfo != null )
		{
			history.setSearchKeywordId( dbInfo.getSearchKeywordId() );
			
			String searchHistoryId = historyDao.create(history);
			boolean exists = historyDao.exists( searchHistoryId );
			Assert.assertTrue(exists);
		}
		else
		{
			
			dictionary.setSearchKeyword( history.getSearchKeyword() );
			String searchKeywordId = dictionaryDao.create(dictionary);

			history.setSearchKeywordId( searchKeywordId );
			
			String searchHistoryId = historyDao.create(history);
			boolean exists = historyDao.exists( searchHistoryId );
			Assert.assertTrue(exists);
		}
		
	}
	
	@Test 
	public void testCreate() { 
		Dictionary dic = new Dictionary();
		dic.setPortalId("P000001");
		dic.setSearchKeyword(history.getSearchKeyword());
		
		Dictionary dbInfo = dictionaryDao.getBySearchKeyword( dic );
		
		if( dbInfo != null )
		{
			history.setSearchKeywordId( dbInfo.getSearchKeywordId() );
			
			String searchHistoryId = historyDao.create(history);
			Assert.assertNotNull(searchHistoryId);
		}
		else
		{
			
			dictionary.setSearchKeyword( history.getSearchKeyword() );
			String searchKeywordId = dictionaryDao.create(dictionary);

			history.setSearchKeywordId( searchKeywordId );
			
			String searchHistoryId = historyDao.create(history);
			Assert.assertNotNull(searchHistoryId);
		}
		
		
	}
	
	@Test
	public void testGet() { 
		
		Dictionary dic = new Dictionary();
		dic.setPortalId("P000001");
		dic.setSearchKeyword(history.getSearchKeyword());
		
		Dictionary dbInfo = dictionaryDao.getBySearchKeyword( dic );
		
		if( dbInfo != null )
		{
			history.setSearchKeywordId( dbInfo.getSearchKeywordId() );
			
			String searchHistoryId = historyDao.create(history);
			History result = historyDao.get(searchHistoryId); 
			Assert.assertNotNull(result);
		}
		else
		{
			
			dictionary.setSearchKeyword( history.getSearchKeyword() );
			String searchKeywordId = dictionaryDao.create(dictionary);

			history.setSearchKeywordId( searchKeywordId );
			
			String searchHistoryId = historyDao.create(history);
			History result = historyDao.get(searchHistoryId); 
			Assert.assertNotNull(result);
		}
		
	}
	
	@Test
	public void testgetList() { 
		
		Dictionary dic = new Dictionary();
		dic.setPortalId("P000001");
		dic.setSearchKeyword(history.getSearchKeyword());
		
		Dictionary dbInfo = dictionaryDao.getBySearchKeyword( dic );
		
		if( dbInfo != null )
		{
			history.setSearchKeywordId( dbInfo.getSearchKeywordId() );
			
			 historyDao.create(history);
			SearchUtil searchUtil = new SearchUtil();
			searchUtil.setCategory(HistoryCons.SEARCH_PREPROCESSOR_HISTORY);
			searchUtil.setStartIndex(0);
			searchUtil.setEndIndex(10);
			
			List<History> result = historyDao.getList(searchUtil); 
			Assert.assertNotNull(result);
		}
		else
		{
			
			dictionary.setSearchKeyword( history.getSearchKeyword() );
			String searchKeywordId = dictionaryDao.create(dictionary);

			history.setSearchKeywordId( searchKeywordId );
			
			 historyDao.create(history);
			SearchUtil searchUtil = new SearchUtil();
			searchUtil.setCategory(HistoryCons.SEARCH_PREPROCESSOR_HISTORY);
			searchUtil.setStartIndex(0);
			searchUtil.setEndIndex(10);
			
			List<History> result = historyDao.getList(searchUtil); 
			Assert.assertNotNull(result);
		}
		
	}

	@Test
	public void testgetCount() { 
		
		Dictionary dic = new Dictionary();
		dic.setPortalId("P000001");
		dic.setSearchKeyword(history.getSearchKeyword());
		
		Dictionary dbInfo = dictionaryDao.getBySearchKeyword( dic );
		
		if( dbInfo != null )
		{
			history.setSearchKeywordId( dbInfo.getSearchKeywordId() );
			
			 historyDao.create(history);
			SearchUtil searchUtil = new SearchUtil();
			searchUtil.setCategory(HistoryCons.SEARCH_PREPROCESSOR_HISTORY);
			searchUtil.setStartIndex(0);
			searchUtil.setEndIndex(10);
			int result = historyDao.getCount(searchUtil); 
			Assert.assertNotSame(0, result);
		}
		else
		{
			
			dictionary.setSearchKeyword( history.getSearchKeyword() );
			String searchKeywordId = dictionaryDao.create(dictionary);

			history.setSearchKeywordId( searchKeywordId );
			
			historyDao.create(history);
			SearchUtil searchUtil = new SearchUtil();
			searchUtil.setCategory(HistoryCons.SEARCH_PREPROCESSOR_HISTORY);
			searchUtil.setStartIndex(0);
			searchUtil.setEndIndex(10);
			searchUtil.setCategory(HistoryCons.SEARCH_PREPROCESSOR_HISTORY);
			int result = historyDao.getCount(searchUtil); 
			Assert.assertNotSame(0, result);
		}
		
	}
	@Test
	public void testgetRelatedList() { 
		
		Dictionary dic = new Dictionary();
		dic.setPortalId("P000001");
		dic.setSearchKeyword(history.getSearchKeyword());
		
		Dictionary dbInfo = dictionaryDao.getBySearchKeyword( dic );
		
		if( dbInfo != null )
		{
			history.setSearchKeywordId( dbInfo.getSearchKeywordId() );
			
			 historyDao.create(history);
			 SearchUtil searchUtil = new SearchUtil();
			 Criteria criteria = searchUtil.createCriteria();
				
			 criteria.addCriterion("h.SEARCH_KEYWORD like", history.getSearchKeyword(), "searchKeyword");
			 criteria.addCriterion("h.portal_id=", "ihko11", "portalId");
				
			List<History> result = historyDao.getRelatedList(searchUtil); 
			Assert.assertNotNull(result);
		}
		else
		{
			
			dictionary.setSearchKeyword( history.getSearchKeyword() );
			String searchKeywordId = dictionaryDao.create(dictionary);

			history.setSearchKeywordId( searchKeywordId );
			
			SearchUtil searchUtil = new SearchUtil();
			 Criteria criteria = searchUtil.createCriteria();
				
			 criteria.addCriterion("h.SEARCH_KEYWORD like", history.getSearchKeyword(), "searchKeyword");
			 criteria.addCriterion("h.portal_id=", "ihko11", "portalId");
			
			 historyDao.create(history);
			List<History> result = historyDao.getRelatedList(searchUtil); 
			Assert.assertNotNull(result);
		}
		
	}
	@Test
	public void testreportDayHistory() { 
		
		Dictionary dic = new Dictionary();
		dic.setPortalId("P000001");
		dic.setSearchKeyword(history.getSearchKeyword());
		
		Dictionary dbInfo = dictionaryDao.getBySearchKeyword( dic );
		
		if( dbInfo != null )
		{
			history.setSearchKeywordId( dbInfo.getSearchKeywordId() );
			
			 historyDao.create(history);
			SearchUtil searchUtil = new SearchUtil();
			
			List<Report> result = historyDao.reportDayHistory(searchUtil); 
			Assert.assertNotNull(result);
		}
		else
		{
			
			dictionary.setSearchKeyword( history.getSearchKeyword() );
			String searchKeywordId = dictionaryDao.create(dictionary);

			history.setSearchKeywordId( searchKeywordId );
			
			 historyDao.create(history);
			SearchUtil searchUtil = new SearchUtil();
			List<Report> result = historyDao.reportDayHistory(searchUtil); 
			Assert.assertNotNull(result);
		}
		
	}
	
	@Test
	public void testreportMonthHistory() { 
		
		Dictionary dic = new Dictionary();
		dic.setPortalId("P000001");
		dic.setSearchKeyword(history.getSearchKeyword());
		
		Dictionary dbInfo = dictionaryDao.getBySearchKeyword( dic );
		
		if( dbInfo != null )
		{
			history.setSearchKeywordId( dbInfo.getSearchKeywordId() );
			
			 historyDao.create(history);
			SearchUtil searchUtil = new SearchUtil();
			
			List<Report> result = historyDao.reportMonthHistory(searchUtil); 
			Assert.assertNotNull(result);
		}
		else
		{
			
			dictionary.setSearchKeyword( history.getSearchKeyword() );
			String searchKeywordId = dictionaryDao.create(dictionary);

			history.setSearchKeywordId( searchKeywordId );
			
			 historyDao.create(history);
			SearchUtil searchUtil = new SearchUtil();
			List<Report> result = historyDao.reportMonthHistory(searchUtil); 
			Assert.assertNotNull(result);
		}
		
	}
	
	@Test 
	public void testremoveAll() {
		Dictionary dic = new Dictionary();
		dic.setPortalId("P000001");
		dic.setSearchKeyword(history.getSearchKeyword());
		
		Dictionary dbInfo = dictionaryDao.getBySearchKeyword( dic );
		
		if( dbInfo != null )
		{
			history.setSearchKeywordId( dbInfo.getSearchKeywordId() );
			
			String searchHistoryId = historyDao.create(history);
			historyDao.removeAll(new Date()); 
			History result = historyDao.get(searchHistoryId); 
			Assert.assertNull(result);
		}
		else
		{
			
			dictionary.setSearchKeyword( history.getSearchKeyword() );
			String searchKeywordId = dictionaryDao.create(dictionary);

			history.setSearchKeywordId( searchKeywordId );
			
			String searchHistoryId = historyDao.create(history);
			historyDao.removeAll(new Date()); 
			History result = historyDao.get(searchHistoryId); 
			Assert.assertNull(result);
		}
	}
	
}
