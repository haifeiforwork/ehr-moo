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

import com.lgcns.ikep4.support.searchpreprocessor.dao.RelationDAO;
import com.lgcns.ikep4.support.searchpreprocessor.model.Relation;
import com.lgcns.ikep4.support.searchpreprocessor.model.RelationKey;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
 
@RunWith(SpringJUnit4ClassRunner.class)
//@TransactionConfiguration(transactionManager="transactionManager",defaultRollback=false)
@ContextConfiguration({"classpath:/configuration/spring/context-dao.xml", "classpath:/configuration/spring/context-service.xml"})
public class RelationDaoTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	@Autowired
	private IdgenService idgenService;
	
	@Autowired
	RelationDAO relationDao;
	
	private String userId="ihko11";
	private String userName="고인호";
	
	private  Relation relation = new Relation();
	
	@Before
	public void setUpBefore() {
		
		String seq = idgenService.getNextId();
		
		relation.setId(seq);
		relation.setPortalId(userId);
		relation.setSearchKeywordId("1");
		relation.setSearchKeyword("1");

		relation.setRelationKeyword("2");
		relation.setRelationKeywordId("2");
		
		relation.setFrequencyCount(1);
		relation.setPortalId(userId);
		
		relation.setRegistDate(new Date() );
		relation.setRegisterId(userId);
		relation.setRegisterName(userName);
		relation.setUpdateDate(new Date() );
		relation.setUpdaterId(userId);
		relation.setUpdaterName(userName);
		
	}
	
	
	@Test
	public void testExists() {
		RelationKey id = relationDao.create(relation);
		boolean exists = relationDao.exists( id );
		Assert.assertTrue(exists);
	}
	
	@Test 
	public void testCreate() { 
		RelationKey id = relationDao.create(relation);
		Assert.assertNotNull(id);
		
	}
	
	@Test
	public void testGet() { 
		RelationKey id = relationDao.create(relation);
		Relation result = relationDao.get(id); 
		Assert.assertNotNull(result);
	}
	
	
	@Test 
	public void testUpdate() {
		
		RelationKey id = relationDao.create(relation);
		
		relation.setFrequencyCount(1);
		relation.setUpdateDate(new Date() );
		relation.setUpdaterId(userId);
		relation.setUpdaterName(userName);
		
		relationDao.update(relation);
		
		Relation result = relationDao.get(id); 
		
		Assert.assertNotNull(result);
	}
	
	@Test 
	public void testRemove() {
		RelationKey id = relationDao.create(relation);
		relationDao.remove(id);
		Relation result = relationDao.get(id);
		Assert.assertNull(result); 
	}
}
