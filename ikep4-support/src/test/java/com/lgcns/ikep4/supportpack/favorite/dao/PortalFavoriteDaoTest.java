package com.lgcns.ikep4.supportpack.favorite.dao;

import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lgcns.ikep4.support.favorite.dao.PortalFavoriteDao;
import com.lgcns.ikep4.support.favorite.model.PortalFavorite;
import com.lgcns.ikep4.support.favorite.model.PortalFavoriteSearchCondition;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:/configuration/spring/context-dao.xml",
		"classpath:/configuration/spring/context-service.xml" })
public class PortalFavoriteDaoTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private PortalFavoriteDao favoriteDao;

	@Autowired
	private IdgenService idgenService;

	private PortalFavorite favorite;

	private String favoriteId;

	@Before
	public void setUp() {

		favorite = new PortalFavorite();

		favoriteId = idgenService.getNextId();
		favorite.setFavoriteId(favoriteId);
		favorite.setType("1");
		favorite.setItemTypeCode("BD");
		favorite.setTargetId("DocPost");
		favorite.setTargetTitle("11");
		favorite.setTargetUrl("11");
		favorite.setRegisterId("11");
		favorite.setRegisterName("11");
		favorite.setUpdaterId("11");
		favorite.setUpdaterName("11");

		favoriteDao.create(favorite);

	}

	@Test
	public void testCreate() {
		PortalFavorite result = favoriteDao.get(favoriteId);
		Assert.assertNotNull(result);

	}

	@Test
	public void testGet() {
		PortalFavorite result = favoriteDao.get(favoriteId);
		Assert.assertNotNull(result);
	}

	@Test
	public void listBySearchConditionForPeople() {
		List<PortalFavorite> result = favoriteDao.listBySearchConditionForPeople(new PortalFavoriteSearchCondition());
	}

	@Test
	public void listBySearchConditionForDocument() {
		List<PortalFavorite> result = favoriteDao.listBySearchConditionForDocument(new PortalFavoriteSearchCondition());
	}

}
