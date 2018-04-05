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

import com.lgcns.ikep4.collpack.ideation.dao.IdLinereplyDao;
import com.lgcns.ikep4.collpack.ideation.dao.IdLinereplyRecommendDao;
import com.lgcns.ikep4.collpack.ideation.model.IdLinereply;
import com.lgcns.ikep4.collpack.ideation.model.IdLinereplyRecommend;



/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: IdLinereplyRecommendDaoTestCase.java 16289 2011-08-19 06:52:01Z giljae $
 */
public class IdLinereplyRecommendDaoTestCase extends BaseDaoTestCase  {
	
	@Autowired
	private IdLinereplyRecommendDao idLinereplyRecommendDao;
	
	@Autowired
	private IdLinereplyDao idLinereplyDao;

	private IdLinereplyRecommend idLinereplyRecommend;

	private String pk;

	@Before
	public void setUp() {
		
		
		IdLinereply idLinereply = new IdLinereply();
		
		pk = "20";
		
		idLinereply.setItemId(null);
		idLinereply.setLinereplyId(pk);
		idLinereply.setLinereplyParentId(null);
		idLinereply.setLinereplyGroupId(pk);
		idLinereply.setContents("contents");
		idLinereply.setStep(0);
		idLinereply.setIndentation(0);
		idLinereply.setRegisterId("user1");
		idLinereply.setRegisterName("동");
		idLinereply.setPortalId("p1");
		idLinereply.setUpdaterId("user1");
		idLinereply.setUpdaterName("동");
		idLinereply.setAdoptLinereply(1);
		
		idLinereplyDao.create(idLinereply);
		
		idLinereplyRecommend = new IdLinereplyRecommend();
		
		idLinereplyRecommend.setLinereplyId(idLinereply.getLinereplyId());
		idLinereplyRecommend.setRegisterId("user1");
		
		idLinereplyRecommendDao.create(idLinereply.getLinereplyId(), "user1");
		
	}

	@Test
	public void testCreate() {
		
		boolean result = idLinereplyRecommendDao.exists(idLinereplyRecommend.getLinereplyId(), idLinereplyRecommend.getRegisterId());
		assertTrue(result);
	}
	
	@Test
	public void testCountByUserId() {
		
		int result = idLinereplyRecommendDao.getCountByUserId(idLinereplyRecommend.getRegisterId());
		assertNotNull(result);
	}
	
	@Test
	public void testListUserIdByLinereplyId() {
		
		List<String> result = idLinereplyRecommendDao.listUserIdByLinereplyId(idLinereplyRecommend.getLinereplyId());
		assertNotNull(result);
	}
	
	@Test
	public void testListUserIdByItemId() {
		
		boolean result = false;
		try {
			List<String> test = idLinereplyRecommendDao.listUserIdByItemId("10");
			result = true;
		} catch (Exception e) {
			result = false;
		}
		
		assertTrue(result);
	}
	
	
	
	
	@Test
	public void testList() {
		
		List<IdLinereplyRecommend> result = idLinereplyRecommendDao.list(idLinereplyRecommend.getLinereplyId());
		assertFalse(result.isEmpty());
	}

	
	@Test
	public void testRemove() {
		
		idLinereplyRecommendDao.remove(idLinereplyRecommend.getLinereplyId(), idLinereplyRecommend.getRegisterId());
		
		boolean result = idLinereplyRecommendDao.exists(idLinereplyRecommend.getLinereplyId(), idLinereplyRecommend.getRegisterId());
		assertFalse(result);
		
	}
	
	
	@Test
	public void testRemoveByLinereplyId() {
		
		idLinereplyRecommendDao.removeByLinereplyId(idLinereplyRecommend.getLinereplyId());
		
		boolean result = idLinereplyRecommendDao.exists(idLinereplyRecommend.getLinereplyId(), idLinereplyRecommend.getRegisterId());
		assertFalse(result);
		
	}
	
	
	@Test
	public void testRemoveByItemId() {
		
		boolean result = false;
		
		try{
			idLinereplyRecommendDao.removeByItemId("11");
			result  = true;
		} catch(Exception e){
			result  = false;
		}
		
		assertTrue(result);
		
	}
	
	
}
