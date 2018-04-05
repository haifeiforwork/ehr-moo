/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.forum.test.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.collpack.forum.dao.FrItemDao;
import com.lgcns.ikep4.collpack.forum.dao.FrLinereplyDao;
import com.lgcns.ikep4.collpack.forum.dao.FrReferenceDao;
import com.lgcns.ikep4.collpack.forum.model.FrItem;
import com.lgcns.ikep4.collpack.forum.model.FrLinereply;
import com.lgcns.ikep4.collpack.forum.model.FrReference;


/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: FrReferenceDaoTestCase.java 16289 2011-08-19 06:52:01Z giljae $
 */
public class FrReferenceDaoTestCase extends BaseDaoTestCase  {
	
	@Autowired
	private FrReferenceDao frReferenceDao;
	
	@Autowired
	private FrItemDao frItemDao;
	

	private FrReference frReference;

	private String pk;

	@Before
	public void setUp() {
		
		
		FrItem	frItem = new FrItem();
		
		pk = "20";
		
		frItem.setItemId(pk);
		frItem.setDiscussionId(null);
		frItem.setTitle("title");
		frItem.setContents("contents");
		frItem.setUpdaterId("lee");
		frItem.setUpdaterName("dong");
		frItem.setRegisterId("user1");
		frItem.setRegisterName("동");
		frItem.setPortalId("p1");
		
		frItemDao.create(frItem);
		
		
		frReference = new FrReference();
		
		frReference.setItemId(pk);
		frReference.setRegisterId("user1");
		
		frReferenceDao.create(frReference);
		
	}

	@Test
	public void testCreate() {
		
		boolean result = frReferenceDao.exists(frReference.getItemId(), frReference.getRegisterId());
		assertTrue(result);
	}
	
	
	@Test
	public void testList() {
		
		List<FrReference> result = frReferenceDao.list(frReference.getItemId());
		assertFalse(result.isEmpty());
	}

	
	@Test
	public void testListUserId() {
		
		List<String> result = frReferenceDao.listUserId(frReference.getItemId());
		assertFalse(result.isEmpty());
	}
	
	@Test
	public void testListUserIdByDiscussionId() {
		
		try{
			List<String> result = frReferenceDao.listUserIdByDiscussionId("22");
			assertTrue(true);
		} catch(Exception e){
			assertTrue(false);
		}
		
	}

	
	@Test
	public void testRemove() {
		
		frReferenceDao.remove(frReference.getItemId(), frReference.getRegisterId());
		
		boolean result = frReferenceDao.exists(frReference.getItemId(), frReference.getRegisterId());
		assertFalse(result);
		
	}
	
	
	@Test
	public void testRemoveByItemId() {
		
		frReferenceDao.removebyItemId(frReference.getItemId());
		
		boolean result = frReferenceDao.exists(frReference.getItemId(), frReference.getRegisterId());
		assertFalse(result);
		
	}
	
	@Test
	public void testRemoveByDiscussionId() {
		
		try{
			frReferenceDao.removeByDiscussionId("2");
			assertTrue(true);
		} catch(Exception e){
			assertTrue(false);
		}
		
		
	}

}
