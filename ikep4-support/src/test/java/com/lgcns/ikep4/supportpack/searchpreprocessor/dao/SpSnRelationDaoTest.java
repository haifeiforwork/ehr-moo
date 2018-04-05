package com.lgcns.ikep4.supportpack.searchpreprocessor.dao;

import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lgcns.ikep4.support.searchpreprocessor.dao.SpSnRelationDAO;
import com.lgcns.ikep4.support.searchpreprocessor.util.SearchUtil;
import com.lgcns.ikep4.support.user.member.model.User;
 
@RunWith(SpringJUnit4ClassRunner.class)
//@TransactionConfiguration(transactionManager="transactionManager",defaultRollback=false)
@ContextConfiguration({"classpath:/configuration/spring/context-dao.xml", "classpath:/configuration/spring/context-service.xml"})
public class SpSnRelationDaoTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	@Autowired
	SpSnRelationDAO spSnrelationDao;
	
	@Before
	public void setUpBefore() {
	}
	
	
	@Test
	public void getSnRelationList() {
		SearchUtil searchUtil = new SearchUtil();
		searchUtil.setStartIndex(0);
		searchUtil.setEndIndex(10);
		List<User> list = spSnrelationDao.getSnRelationList(searchUtil);
		
		Assert.assertNotNull(list);
	}
	
	
	@Test
	public void getSnRelationDetailList(){
		SearchUtil searchUtil = new SearchUtil();
		searchUtil.setStartIndex(0);
		searchUtil.setEndIndex(10);
		List<User> list = spSnrelationDao.getSnRelationDetailList(searchUtil);
		
		Assert.assertNotNull(list);
	}
	
	@Test
	public void countSnRelationDetailList(){
		SearchUtil searchUtil = new SearchUtil();
		searchUtil.setStartIndex(0);
		searchUtil.setEndIndex(10);
		Integer count = spSnrelationDao.countSnRelationDetailList(searchUtil);
		
		Assert.assertNotSame(0, count);
		
	}
}
