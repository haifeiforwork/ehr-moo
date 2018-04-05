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
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:/configuration/spring/context-dao.xml",
		"classpath:/configuration/spring/context-service.xml" })
public class FileDaoTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private FileDao fileDao;

	@Autowired
	private IdgenService idgenService;

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
		file.setEditorAttach(0);
		file.setRegisterId("11");
		file.setRegisterName("11");
		file.setUpdaterId("11");
		file.setUpdaterName("11");
		
		fileDao.create(file);

	}

	@Test
	public void testGet() {
		FileData result = fileDao.get(fileId);
		Assert.assertNotNull(result);
	}

	@Test
	public void testCreate() {
		FileData result = fileDao.get(fileId);

		Assert.assertNotNull(result);

	}

	public void testUpdate() {
	}

	@Test
	public void testPhysicalDelete() {
		fileDao.remove(fileId);

		FileData result = fileDao.get(fileId);

		assertNull(result);
	}

	public void testExists() {
		
	}

	public void testLogicalDelete() {
		// TODO Auto-generated method stub

	}
	
	@Test
	public void getItemFile() {
		List<FileData> result = fileDao.getItemFile(fileId, "");
	}
	
	@Test
	public void getAllForThumbnailBatch() {
		List<FileData> result = fileDao.getAllForThumbnailBatch();
	}
	
	@Test
	public void getAllForFileDeleteBatch() {
		List<FileData> result = fileDao.getAllForFileDeleteBatch();
	}

}
