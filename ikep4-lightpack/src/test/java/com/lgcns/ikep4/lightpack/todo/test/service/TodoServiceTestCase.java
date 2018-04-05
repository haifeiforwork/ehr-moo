package com.lgcns.ikep4.lightpack.todo.test.service;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.lightpack.todo.model.Todo;
import com.lgcns.ikep4.lightpack.todo.model.TodoPk;
import com.lgcns.ikep4.lightpack.todo.search.TodoSearchCondition;
import com.lgcns.ikep4.lightpack.todo.service.TodoService;

/**
 * Service 정의
 *
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: TodoServiceTestCase.java 16302 2011-08-19 08:43:50Z giljae $
 */
public class TodoServiceTestCase extends BaseServiceTestCase {
	@Autowired
	private TodoService todoService;
	
	private Todo todo;
	
	private TodoPk todoPk;
	
	@Before
	public void setUp() {  
		this.todo = new Todo();
		this.todo.setSystemCode("IKEP");
		this.todo.setSubworkCode("TASK");
		this.todo.setTaskKey("9999999999");
		this.todo.setWorkerId("workerId");
		this.todo.setTitle("Title");   
		this.todo.setDirectorId("directorId");  
		this.todo.setStatusName("StatusName"); 
		this.todo.setDueDate(new Date()); 
		this.todo.setIsComplete(0);
		this.todo.setUrl("/ikep4-webapp/lightpack/todo/detailTodo.do");
		this.todo.setSystemName("SystemName"); 
		this.todo.setSubworkName("SubworkName");
		this.todo.setTodoStatusName("TodoStatusName"); 
		this.todo.setEtcName("EtcName"); 
		
		this.todoPk = todoService.create(this.todo);
	}
	
	@Test
	public void testCreate() {
		Todo result = todoService.read(this.todoPk);
		
		Assert.assertNotNull(result);
	}
	
	@Test
	public void testExists() {
		boolean exists = todoService.exists(this.todoPk); 
		
		Assert.assertTrue(exists);
	}
	
	@Test
	public void testRead() {
		Todo result = todoService.read(this.todoPk);
		
		Assert.assertNotNull(result);
	}
	
	@Test
	public void testDelete() {
		todoService.delete(this.todoPk);
		Todo result = todoService.read(this.todoPk);
		
		Assert.assertNull(result);
	}
	
	@Test
	public void testUpdate() {
		this.todo.setTitle("new Title");
		todoService.update(this.todo);
		
		Todo result = todoService.read(this.todoPk);
		
		Assert.assertEquals(this.todo.getTitle(), result.getTitle());
	}
	
	/////////////////////////////////////////////	
	@Test
	@Ignore
	public void testTodoAssign(){
	}
	
	@Test
	@Ignore
	public void testTodoComplete(){
	}
	
	@Test
	@Ignore
	public void testTodoDelete(){
	}

	@Test
	public void testCountMyTodo() {
		int rerult = todoService.countMyTodo(this.todo.getWorkerId());
		
		Assert.assertEquals(1, rerult);
	}
	
	@Test
	@Ignore
	public void testListMyTodo() {
		List<Todo> resultList = todoService.listMyTodo(this.todo.getWorkerId(), 10);
		boolean exists = false;
		if(resultList.size() > 0)
			exists = true;
		
		Assert.assertTrue(exists);
	}

	@Test
	@Ignore
	public void testListMyTodoSearch() {
		TodoSearchCondition todoSearchCondition = new TodoSearchCondition();
		todoSearchCondition.setWorkerId(this.todo.getWorkerId());
		todoSearchCondition.setTodoStatus(Integer.toString(this.todo.getIsComplete()));
		todoSearchCondition.setFromDay(new Date());
		todoSearchCondition.setToDay(new Date());
		todoSearchCondition.setSearchType("A");	
		
		SearchResult<Todo> resultList = todoService.listMyTodoSearch(todoSearchCondition);
		boolean exists = false;
		if(resultList.getEntity().size() > 0)
			exists = true;
		
		Assert.assertTrue(exists);
	}

	@Test
	@Ignore
	public void testProcessTodoAutoComplete(){
	}
	
	@Test
	@Ignore
	public void testDeleteFromTask(){
	}
}