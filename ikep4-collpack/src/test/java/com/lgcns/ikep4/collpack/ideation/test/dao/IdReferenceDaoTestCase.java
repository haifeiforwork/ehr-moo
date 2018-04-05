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
import com.lgcns.ikep4.collpack.ideation.dao.IdReferenceDao;
import com.lgcns.ikep4.collpack.ideation.model.IdIdea;
import com.lgcns.ikep4.collpack.ideation.model.IdReference;



/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: IdReferenceDaoTestCase.java 16289 2011-08-19 06:52:01Z giljae $
 */
public class IdReferenceDaoTestCase extends BaseDaoTestCase  {
	
	@Autowired
	private IdReferenceDao idReferenceDao;
	
	@Autowired
	private IdIdeaDao idIdeaDao;
	

	private IdReference idReference;

	private String pk;

	@Before
	public void setUp() {
		
		
		IdIdea	idIdea = new IdIdea();
		
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
		
		idReference = new IdReference();
		
		idReference.setItemId(pk);
		idReference.setRegisterId("user1");
		
		idReferenceDao.create(pk, "user1");
		
	}

	@Test
	public void testCreate() {
		
		boolean result = idReferenceDao.exists(idReference.getItemId(), idReference.getRegisterId());
		assertTrue(result);
	}
	
	
	@Test
	public void testList() {
		
		List<IdReference> result = idReferenceDao.list(idReference.getItemId());
		assertFalse(result.isEmpty());
	}

	
	@Test
	public void testListUserId() {
		
		List<String> result = idReferenceDao.listUserId(idReference.getItemId());
		assertFalse(result.isEmpty());
	}
	

	
	@Test
	public void testRemove() {
		
		idReferenceDao.remove(idReference.getItemId(), idReference.getRegisterId());
		
		boolean result = idReferenceDao.exists(idReference.getItemId(), idReference.getRegisterId());
		assertFalse(result);
		
	}
	
	
	@Test
	public void testRemoveByItemId() {
		
		idReferenceDao.removebyItemId(idReference.getItemId());
		
		boolean result = idReferenceDao.exists(idReference.getItemId(), idReference.getRegisterId());
		assertFalse(result);
		
	}
	

}
