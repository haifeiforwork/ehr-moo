package com.lgcns.ikep4.supportpack.fileupload.dao;

import static org.junit.Assert.assertNull;

import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lgcns.ikep4.support.fileupload.dao.FileDao;
import com.lgcns.ikep4.support.fileupload.dao.ThumbnailDao;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.model.Thumbnail;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:/configuration/spring/context-dao.xml",
		"classpath:/configuration/spring/context-service.xml" })
public class ThumbnailDaoTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private ThumbnailDao thumbnailDao;

	@Autowired
	private IdgenService idgenService;

	private Thumbnail thumbnail;

	private String thumbnailId;
	
	@Autowired
	private FileDao fileDao;

	private FileData file;

	private String fileId;

	@Before
	public void setUp() {
		
		file = new FileData();

		fileId = idgenService.getNextId();
		file.setFileId(fileId);
		file.setFileName("11");
		file.setFileRealName("11");
		file.setFileExtension("11");
		file.setFileContentsType("11");
		file.setFilePath("11");
		file.setRegisterId("11");
		file.setRegisterName("11");
		file.setUpdaterId("11");
		file.setUpdaterName("11");
		
		fileDao.create(file);

		thumbnail = new Thumbnail();

		thumbnailId = idgenService.getNextId();
		thumbnail.setThumbnailId(thumbnailId);
		thumbnail.setParentFileId(fileId);
		thumbnail.setThumbnailName("11");
		thumbnail.setThumbnailRealName("11");
		thumbnail.setThumbnailExtension("11");
		thumbnail.setThumbnailContentsType("11");
		thumbnail.setThumbnailPath("11");
		thumbnail.setRegisterId("11");
		thumbnail.setRegisterName("11");
		thumbnail.setUpdaterId("11");
		thumbnail.setUpdaterName("11");

		thumbnailDao.create(thumbnail);

	}

	@Test
	public void testGet() {
		Thumbnail result = thumbnailDao.get(fileId);
		Assert.assertNotNull(result);
	}

	@Test
	public void testCreate() {
		Thumbnail result = thumbnailDao.get(fileId);

		Assert.assertNotNull(result);

	}

	public void testUpdate() {}

	@Test
	public void testPhysicalDelete() {
		thumbnailDao.remove(fileId);

		Thumbnail result = thumbnailDao.get(fileId);

	}

	public void testExists() {


	}

	public void testLogicalDelete() {
		// TODO Auto-generated method stub

	}
	
	@Test
	public void getItemThumbnail() {
		List<Thumbnail> result = thumbnailDao.getItemThumbnail(fileId);
	}

}
