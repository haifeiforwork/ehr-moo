/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.ideation.test.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.collpack.ideation.dao.IdIdeaDao;
import com.lgcns.ikep4.collpack.ideation.dao.IdRecommendDao;
import com.lgcns.ikep4.collpack.ideation.model.IdIdea;
import com.lgcns.ikep4.collpack.ideation.model.IdLinereply;
import com.lgcns.ikep4.collpack.ideation.model.IdRecommend;



/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: IdRecommendDaoTestCase.java 16289 2011-08-19 06:52:01Z giljae $
 */
public class IdRecommendDaoTestCase extends BaseDaoTestCase  {
	
	@Autowired
	private IdRecommendDao idRecommendDao;
	
	@Autowired
	private IdIdeaDao idIdeaDao;

	private IdRecommend idRecommend;

	private String pk;

	@Before
	public void setUp() {
		
		
		IdIdea idIdea = new IdIdea();
		
		pk = "21";
		
		idIdea.setItemId(pk);
		idIdea.setTitle("title");
		idIdea.setContents("contents");
		idIdea.setUpdaterId("user1");
		idIdea.setUpdaterName("dong");
		idIdea.setRegisterId("user1");
		idIdea.setRegisterName("동");
		idIdea.setPortalId("p1");
		
		idIdeaDao.create(idIdea);
		
		idRecommend = new IdRecommend();
		
		idRecommend.setItemId(idIdea.getItemId());
		idRecommend.setRegisterId("user1");
		
		idRecommendDao.create(idIdea.getItemId(), "user1");
		
	}

	@Test
	public void testCreate() {
		
		boolean result = idRecommendDao.exists(idRecommend.getItemId(), idRecommend.getRegisterId());
		assertTrue(result);
	}
	
	@Test
	public void testCountByUserId() {
		
		int result = idRecommendDao.getCountByUserId(idRecommend.getRegisterId());
		assertNotNull(result);
	}
	
	
	@Test
	public void testListUserIdByItemId() {
		
		boolean result = false;
		try {
			List<String> test = idRecommendDao.listUserIdByItemId("10");
			result = true;
		} catch (Exception e) {
			result = false;
		}
		
		assertTrue(result);
	}
	
	
	
	
	@Test
	public void testList() {
		
		List<IdRecommend> result = idRecommendDao.list(idRecommend.getItemId());
		assertFalse(result.isEmpty());
	}

	
	@Test
	public void testRemove() {
		
		idRecommendDao.remove(idRecommend.getItemId(), idRecommend.getRegisterId());
		
		boolean result = idRecommendDao.exists(idRecommend.getItemId(), idRecommend.getRegisterId());
		assertFalse(result);
		
	}
	
	
	
	@Test
	public void testRemoveByItemId() {
		
		boolean result = false;
		
		try{
			idRecommendDao.removeByItemId("11");
			result  = true;
		} catch(Exception e){
			result  = false;
		}
		
		assertTrue(result);
		
	}
	
	
}
