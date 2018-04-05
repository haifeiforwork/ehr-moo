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
import com.lgcns.ikep4.support.searchpreprocessor.model.History;
import com.lgcns.ikep4.support.searchpreprocessor.model.Report;
import com.lgcns.ikep4.support.searchpreprocessor.model.Result;
import com.lgcns.ikep4.support.searchpreprocessor.service.DictionaryService;
import com.lgcns.ikep4.support.searchpreprocessor.service.HistoryService;
import com.lgcns.ikep4.support.searchpreprocessor.util.Criteria;
import com.lgcns.ikep4.support.searchpreprocessor.util.HistoryCons;
import com.lgcns.ikep4.support.searchpreprocessor.util.SearchUtil;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
 
@RunWith(SpringJUnit4ClassRunner.class)
//@TransactionConfiguration(transactionManager="transactionManager",defaultRollback=false)
@ContextConfiguration({"classpath:/configuration/spring/context-dao.xml", "classpath:/configuration/spring/context-service.xml"})
public class HistoryServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	@Autowired
	private IdgenService idgenService;
	
	@Autowired
	DictionaryService dictionaryService;
	
	@Autowired
	private HistoryService historyService; 
	
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
	public void create() { 
		String historyId = historyService.create(history);
		boolean exists = historyService.exists(historyId);
		Assert.assertTrue(exists);
	}
	
	@Test
	public void read() { 
			String historyId = historyService.create(history);
			History result = historyService.read(historyId); 
			Assert.assertNotNull(result);
	}
	
	@Test
	public void getList() { 
		SearchUtil searchUtil = new SearchUtil();
		searchUtil.setCategory(HistoryCons.SEARCH_PREPROCESSOR_HISTORY);
		searchUtil.setStartIndex(0);
		searchUtil.setEndIndex(10);
		
		historyService.create(history);
		Result result = historyService.getList(searchUtil); 
		Assert.assertTrue(result.getTotalCount() > 0 );
	}

	@Test
	public void getMainList() { 
		SearchUtil searchUtil = new SearchUtil();
		searchUtil.setCategory(HistoryCons.SEARCH_PREPROCESSOR_HISTORY);
		searchUtil.setStartIndex(0);
		searchUtil.setEndIndex(10);
		
		historyService.create(history);
		List<History> result = historyService.getMainList(searchUtil); 
		Assert.assertNotNull(result);
	}
	
	@Test
	public void getRelatedList() { 
		historyService.create(history);
		SearchUtil searchUtil = new SearchUtil();
		 Criteria criteria = searchUtil.createCriteria();
			
		 criteria.addCriterion("h.SEARCH_KEYWORD like", history.getSearchKeyword(), "searchKeyword");
		 criteria.addCriterion("h.portal_id=", "ihko11", "portalId");
		List<History> result = historyService.getRelatedList(searchUtil); 
		Assert.assertNotNull(result);
	}
	
	
	@Test
	public void reportDayHistory() { 
		SearchUtil searchUtil = new SearchUtil();
		searchUtil.setCategory(HistoryCons.SEARCH_PREPROCESSOR_HISTORY);
		searchUtil.setStartIndex(0);
		searchUtil.setEndIndex(10);
		
		historyService.create(history);
		List<Report>  result = historyService.reportDayHistory(searchUtil); 
		Assert.assertNotNull(result);
	}
	
	@Test
	public void reportMonthHistory() { 
		SearchUtil searchUtil = new SearchUtil();
		searchUtil.setCategory(HistoryCons.SEARCH_PREPROCESSOR_HISTORY);
		searchUtil.setStartIndex(0);
		searchUtil.setEndIndex(10);
		
		historyService.create(history);
		List<Report>  result = historyService.reportMonthHistory(searchUtil); 
		Assert.assertNotNull(result);
	}
	
	@Test 
	public void update() { 
		String historyId = historyService.create(history);
		historyService.update(history);
		boolean exists = historyService.exists(historyId);
		Assert.assertTrue(exists);
	}
	
	@Test 
	public void delete() { 
		String historyId = historyService.create(history);
		historyService.delete(historyId);
		boolean exists = historyService.exists(historyId);
		Assert.assertFalse(exists);
	}
	
	@Test 
	public void removeAll() { 
		String historyId = historyService.create(history);
		historyService.removeAll(new Date());
		boolean exists = historyService.exists(historyId);
		Assert.assertFalse(exists);
	}
}
