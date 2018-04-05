/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.collaboration.board.announce.test.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.collpack.collaboration.board.announce.dao.AnnounceItemDao;
import com.lgcns.ikep4.collpack.collaboration.board.announce.model.AnnounceItem;
import com.lgcns.ikep4.collpack.collaboration.board.announce.search.AnnounceItemSearchCondition;
import com.lgcns.ikep4.util.idgen.service.IdgenService;

/**
 * TODO Javadoc주석작성
 *
 * @author 김종철
 * @version $Id: AnnounceItemDaoTest.java 16289 2011-08-19 06:52:01Z giljae $
 */
public class AnnounceItemDaoTest extends BaseDaoTestCase{
	@Autowired
	private AnnounceItemDao announceItemDao;


	@Autowired
	private IdgenService idgenService;
	
	private AnnounceItem announceItem;
	
	private AnnounceItem announceItemSet;

	private String pk;

	@Before
	public void setUp() {
		announceItem = new AnnounceItem();
		Date today = new Date();
		announceItem.setItemId(itemId);
		announceItem.setItemSeqId(itemSeqId);
		announceItem.setItemDisplay(itemDisplay);
		announceItem.setTitle(title);
		announceItem.setContents(contents);
		announceItem.setHitCount(hitCount);
		announceItem.setAttachFileCount(attachFileCount);
		announceItem.setItemDelete(itemDelete);
		announceItem.setRegisterId(registerId);
		announceItem.setRegisterName(registerName);
		announceItem.setUpdaterId(updaterId);
		announceItem.setUpdaterName(updaterName);
		announceItem.setWorkspaceId(workspaceId);
		announceItem.setIsOwner(isOwner);
		announceItem.setEndDate(today);
		
		announceItemDao.create(announceItem);
		announceItemDao.createLinkAnnounce(announceItem);

		announceItemSet = announceItem;
	}

	@Test
	public void testCreate() {
		AnnounceItem result = announceItemDao.getAnnounce(itemId, workspaceId);
		assertNotNull(result);
	}
	
	@Test
	public void testUpdate(){
		this.announceItem = announceItemDao.getAnnounce(itemId, workspaceId);
		this.announceItem.setTitle("modified title");

		announceItemDao.update(this.announceItem);
		
		AnnounceItem result = announceItemDao.getAnnounce(itemId, workspaceId);
		assertEquals(this.announceItem.getTitle(), result.getTitle());
	}
	
	@Test
	public void testLinkDelete() {
		announceItemDao.removeAnnounceLink(itemId,workspaceId);
		announceItemDao.removeAnnounceItem(itemId);
		
		AnnounceItem result = announceItemDao.getAnnounce(itemId, workspaceId);
		assertNull(result);
	}
	
	@Test
	public void testListBySearchCondition() {
		AnnounceItemSearchCondition announceItemSearchCondition = new AnnounceItemSearchCondition();
		announceItemSearchCondition.setWorkspaceId(workspaceId);
		List<AnnounceItem> result = announceItemDao.listBySearchCondition(announceItemSearchCondition);
		assertNotNull(result);
	}
	
	@Test
	public void testCountBySearchCondition() {
		
		AnnounceItemSearchCondition announceItemSearchCondition = new AnnounceItemSearchCondition();
		
		announceItemSearchCondition.setWorkspaceId(workspaceId);		
		
		int count = announceItemDao.countBySearchCondition(announceItemSearchCondition);
		assertNotNull(count);
	}
	
	@Test
	public void testAnnounceShareLinkDelete() {
		announceItemDao.removeAnnounceShareLink(itemId);
		
		AnnounceItem result = announceItemDao.getAnnounce(itemId, workspaceId);
		assertNotNull(result);
	}
	
	@Test
	public void testAnnounceReferenceDelete() {
		announceItemDao.removeAnnounceReference(itemId);
		int count = announceItemDao.countAnnounceReference(registerId, itemId);
		assertEquals(0, count);
	}
	
	
	
	@Test
	public void testListChildWorkspaceBySearchCondition() {
		AnnounceItemSearchCondition announceItemSearchCondition = new AnnounceItemSearchCondition();
		announceItemSearchCondition.setWorkspaceId(workspaceId);
		List<AnnounceItem> result = announceItemDao.listChildWorkspaceBySearchCondition(announceItemSearchCondition);
		assertNotNull(result);
	}
	
	@Test
	public void testCountChildWorkspaceBySearchCondition() {
		
		AnnounceItemSearchCondition announceItemSearchCondition = new AnnounceItemSearchCondition();
		
		announceItemSearchCondition.setWorkspaceId(workspaceId);		
		
		int count = announceItemDao.countChildWorkspaceBySearchCondition(announceItemSearchCondition);
		assertNotNull(count);
	}
}
