package com.lgcns.ikep4.lightpack.todo.test.service;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.lightpack.todo.model.Task;
import com.lgcns.ikep4.lightpack.todo.model.TaskUser;
import com.lgcns.ikep4.lightpack.todo.model.TaskUserPk;
import com.lgcns.ikep4.lightpack.todo.service.TaskService;
import com.lgcns.ikep4.lightpack.todo.service.TaskUserService;

/**
 * Service 정의
 *
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: TaskUserServiceTestCase.java 16302 2011-08-19 08:43:50Z giljae $
 */
public class TaskUserServiceTestCase extends BaseServiceTestCase {
	@Autowired
	private TaskUserService taskUserService;
	@Autowired
	private TaskService taskService;
	
	private Task task;	
	
	private TaskUser taskUser;

	private TaskUserPk taskUserPk;
	
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
		
		this.taskService.create(task);
		
		this.taskUser = new TaskUser();
		this.taskUser.setWorkerId("WorkerId");
		this.taskUser.setTaskId("9999999999");   
		this.taskUser.setUserContents("UserContents");
		this.taskUser.setUserAttachCount(0); 
		this.taskUser.setUserCompleteDate(new Date()); 	
		this.taskUser.setUserStatus("A"); 	
		this.taskUser.setRegisterId("registerId");
		this.taskUser.setRegisterName("registerName");  
		this.taskUser.setUpdaterId("registerId");
		this.taskUser.setUpdaterName("registerName"); 
		this.taskUser.setWorkerName("WorkerName"); 
		this.taskUser.setWorkerTeamName("WorkerTeamName"); 
		this.taskUser.setWorkerJobRankName("WorkerJobRankName");
		
		this.taskUserPk = this.taskUserService.create(taskUser);
		
		Assert.assertNotNull("테스트 픽스쳐 초기화", this.taskUserPk);
	}
	
	@Test
	public void testCreate() {
		TaskUser result = taskUserService.read(this.taskUserPk);
		
		Assert.assertNotNull(result);
	}
	
	@Test
	public void testExists() {
		boolean exists = taskUserService.exists(this.taskUserPk); 
		
		Assert.assertTrue(exists);
	}
	
	@Test
	public void testRead() {
		TaskUser result = taskUserService.read(this.taskUserPk);
		
		Assert.assertNotNull(result);
	}
	
	@Test
	public void testDelete() {
		taskUserService.delete(this.taskUserPk);
		TaskUser result = taskUserService.read(this.taskUserPk);
		
		Assert.assertNull(result);
	}
	
	@Test
	@Ignore
	public void testUpdate() {
		this.taskUser.setUserContents("new Contents");
		taskUserService.update(this.taskUser);
		
		TaskUser result = taskUserService.read(this.taskUserPk);
		
		Assert.assertEquals(this.taskUser.getUserContents(), result.getUserContents());
	}
	
	/////////////////////////////////////////////	
	@Test
	@Ignore
	public void testGetTaskUser() {
	}	

	@Test
	@Ignore
	public void testListByTaskId() {
	}
}
