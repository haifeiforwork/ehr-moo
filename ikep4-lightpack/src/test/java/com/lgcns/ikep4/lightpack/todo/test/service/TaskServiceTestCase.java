package com.lgcns.ikep4.lightpack.todo.test.service;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.lightpack.todo.model.Task;
import com.lgcns.ikep4.lightpack.todo.service.TaskService;

/**
 * Service 정의
 *
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: TaskServiceTestCase.java 16302 2011-08-19 08:43:50Z giljae $
 */
public class TaskServiceTestCase extends BaseServiceTestCase {
	@Autowired
	private TaskService taskService;
	
	private Task task;
	
	private String pk;
	
	@Before
	public void setUp() {
		this.task = new Task();
		this.task.setTaskId("9999999999");
		this.task.setTitle("Title");   
		this.task.setDirectorId("directorId"); 
		this.task.setStartDate(new Date()); 
		this.task.setDueDate(new Date());
		this.task.setTaskContents("taskContents"); 
		this.task.setTaskAttachCount(0); 
		this.task.setTaskStatus("A");		
		this.task.setRegisterId("registerId");
		this.task.setRegisterName("registerName");  
		this.task.setUpdaterId("registerId");
		this.task.setUpdaterName("registerName");  
		this.task.setEtcName("getEtcName"); 
		this.task.setDirectorName("DirectorName"); 
		this.task.setDirectorTeamName("DirectorTeamName"); 
		this.task.setDirectorJobRankName("DirectorJobRankName");	
		
		this.pk = taskService.create(this.task);
	}
	
	@Test
	public void testCreate() {
		Task result = taskService.read(this.pk);
		
		Assert.assertNotNull(result);
	}
	
	@Test
	public void testExists() {
		boolean exists = taskService.exists(this.pk); 
		
		Assert.assertTrue(exists);
	}
	
	@Test
	public void testRead() {
		Task result = taskService.read(this.pk);
		
		Assert.assertNotNull(result);
	}
	
	@Test
	public void testDelete() {
		taskService.delete(this.pk);
		Task result = taskService.read(this.pk);
		
		Assert.assertNull(result);
	}
	
	@Test
	public void testUpdate() {
		this.task.setTitle("new Title");
		taskService.update(this.task);
		
		Task result = taskService.read(this.pk);
		
		Assert.assertEquals(this.task.getTitle(), result.getTitle());
	}
	
	/////////////////////////////////////////////
	@Test
	@Ignore
	public void testGetTask() {
		Task result = taskService.getTask(this.pk);
		
		Assert.assertNotNull(result);
	}	

	@Test
	@Ignore
	public void testCreateTask() {
	}	
	
	@Test
	@Ignore
	public void testUpdateTask() {
	}	
	
	@Test
	@Ignore
	public void testDeleteTask() {
	}	
}
