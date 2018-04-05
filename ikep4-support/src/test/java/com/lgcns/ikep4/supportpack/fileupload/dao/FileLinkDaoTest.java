package com.lgcns.ikep4.supportpack.fileupload.dao;

import static org.junit.Assert.assertNull;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lgcns.ikep4.support.fileupload.dao.FileDao;
import com.lgcns.ikep4.support.fileupload.dao.FileLinkDao;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.model.FileLink;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:/configuration/spring/context-dao.xml",
		"classpath:/configuration/spring/context-service.xml" })
public class FileLinkDaoTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	@Autowired
	private FileDao fileDao;
	
	@Autowired
	private FileLinkDao fileLinkDao;

	@Autowired
	private IdgenService idgenService;

	private FileLink fileLink;
	
	private FileData file;
	
	private String fileId;

	private String fileLinkId;

	@Before
	@Ignore
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

		fileLink = new FileLink();

		fileLinkId = idgenService.getNextId();
		fileLink.setFileLinkId(fileLinkId);
		fileLink.setFileId(fileId);
		fileLink.setItemId("11");
		fileLink.setItemTypeCode("11");
		fileLink.setRegisterId("11");
		fileLink.setRegisterName("11");
		fileLink.setUpdaterId("11");
		fileLink.setUpdaterName("11");

		fileLinkDao.create(fileLink);

	}

	public void testGet() {
	}

	@Test
	@Ignore
	public void testCreate() {
		FileLink result = fileLinkDao.get(fileId);

		Assert.assertNotNull(result);

	}

	public void testUpdate() {}

	@Test
	@Ignore
	public void testPhysicalDelete() {
		fileLinkDao.remove(fileId);

		FileLink result = fileLinkDao.get(fileId);

		assertNull(result);
	}

	public void testExists() {


	}

	public void testLogicalDelete() {
		// TODO Auto-generated method stub

	}

}
