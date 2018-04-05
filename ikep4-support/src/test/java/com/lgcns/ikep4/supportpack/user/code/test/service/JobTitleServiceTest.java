package com.lgcns.ikep4.supportpack.user.code.test.service;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;
import com.lgcns.ikep4.support.user.code.model.JobTitle;
import com.lgcns.ikep4.support.user.code.service.JobTitleService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/configuration/spring/context-dao.xml", "classpath:/configuration/spring/context-service.xml"}) 
public class JobTitleServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	@Autowired
	private JobTitleService jobTitleService;

	/**
	 * Log variable for all child classes. Uses LogFactory.getLog(getClass())
	 * from Commons Logging
	 */
	protected final Log log = LogFactory.getLog(getClass());

	private JobTitle jobTitleCreate;
	private JobTitle jobTitleUpdate;

	private AdminSearchCondition searchCondition;
	
	@Before
	public void setMethod() {

		jobTitleCreate = new JobTitle();
		jobTitleCreate.setCodeCheck("success");
		jobTitleCreate.setJobTitleCode("testJobTitleCode");
		jobTitleCreate.setJobTitleName("테스트");
		jobTitleCreate.setPortalId("P000001");
		jobTitleCreate.setSortOrder("000100");
		jobTitleCreate.setRegisterId("admin");
		jobTitleCreate.setRegisterName("관리자");
		jobTitleCreate.setUpdaterId("admin");
		jobTitleCreate.setUpdaterName("관리자");
		
		jobTitleUpdate = new JobTitle();
		jobTitleUpdate.setCodeCheck("modify");
		jobTitleUpdate.setJobTitleCode("testJobTitleCode");
		jobTitleUpdate.setJobTitleName("테스트Update");
		jobTitleUpdate.setPortalId("P000001");
		jobTitleUpdate.setSortOrder("000101");
		jobTitleUpdate.setRegisterId("admin");
		jobTitleUpdate.setRegisterName("관리자");
		jobTitleUpdate.setUpdaterId("admin");
		jobTitleUpdate.setUpdaterName("관리자");
		
		searchCondition = new AdminSearchCondition();
		searchCondition.setFieldName("jobTitleName");
		searchCondition.setStartRowIndex(0);
		searchCondition.setEndRowIndex(10);
	}
	
	@Test
	public void createJobTitle() {
		
		//호칭코드를 생성한 뒤에 생성한 호칭코드의 jobTitleCode로 read를 수행하여 Null이 아니면 True
		jobTitleService.create(jobTitleCreate);
		assertNotNull(jobTitleService.read(jobTitleCreate.getJobTitleCode()));
	}
	
	@Test
	public void exists() {
		
		//호칭코드를 생성한 뒤에 생성한 호칭코드가 존재하는지 확인. 존재하면 True
		jobTitleService.create(jobTitleCreate);
		assertTrue(jobTitleService.exists(jobTitleCreate.getJobTitleCode()));
	}
	
	@Test
	public void read() {
		
		// 테스트 jobTitle를 생성한 후 해당 jobTitle를 읽어오는 조건으로 조회한 뒤 결과가 null이 아니면 True
		jobTitleService.create(jobTitleCreate);
		JobTitle tempCreate = jobTitleService.read(jobTitleCreate.getJobTitleCode());
		
		assertNotNull(tempCreate);
	}
	
	@Test
	public void delete() {
		
		//i18nMessage에 해당 객체가 없으면 True, jobTitle에 해당 객체가 없으면 True
		jobTitleService.create(jobTitleCreate);
		jobTitleService.delete(jobTitleCreate.getJobTitleCode());
		
		assertNull(jobTitleService.read(jobTitleCreate.getJobTitleCode()));
	}
	
	@Test
	public void update() {

		// 특정한 조건으로 tempCreate을 생성하고 다른 조건으로 update 한 후 
		// 각각의 JobTitle 객체를 비교하여 같지 않으면 True
		jobTitleService.create(jobTitleCreate);
		JobTitle tempCreate = jobTitleService.read(jobTitleCreate.getJobTitleCode());
		
		jobTitleService.update(jobTitleUpdate);
		JobTitle tempUpdate = jobTitleService.read(jobTitleUpdate.getJobTitleCode());
		
		assertNotSame(tempCreate.getJobTitleName(), tempUpdate.getJobTitleName());
	}
	
	@Test
	public void list() {
		
		// 목록을 조회한 뒤에 null이 아니면 True
		jobTitleService.create(jobTitleCreate);
		
		assertNotNull(jobTitleService.list(searchCondition));
	}
	
	@Test
	public void getMaxSortOrder() {
		
		// sortOrder 최대값을 조회해서 null이 아니면 True
		jobTitleService.create(jobTitleCreate);
		
		assertEquals("000101", jobTitleService.getMaxSortOrder());
	}
	
	@Test
	public void goUp() {
		
		// 특정 조건으로 jobTitle를 생성하고 sortOrder를 하나 줄이는 조건으로 goUp을 실행한다. 
		// sortOrder의 차이가 1이고 이전 sortOrder가 현재 sortOrder보다 크면 True
		String tempSortOrder = jobTitleService.getMaxSortOrder();
		jobTitleCreate.setSortOrder(jobTitleService.getMaxSortOrder());
		jobTitleService.create(jobTitleCreate);
		int preSortOrder = Integer.parseInt(jobTitleService.read(jobTitleCreate.getJobTitleCode()).getSortOrder());
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("jobTitleCode", "testJobTitleCode");
		map.put("sortOrder", tempSortOrder);
		
		jobTitleService.goUp(map);
		
		int currentSortOrder = Integer.parseInt(jobTitleService.read(jobTitleCreate.getJobTitleCode()).getSortOrder());
		
		assertTrue((preSortOrder > currentSortOrder) && (Math.abs(preSortOrder-currentSortOrder)==1));
	}
	
	@Test
	public void goDown() {
		
		// 특정 조건으로 jobTitle를 생성하고 sortOrder를 하나 줄이는 조건으로 goDown을 실행한다. 
		// sortOrder의 차이가 1이고 이전 sortOrder가 현재 sortOrder보다 작으면 True
		String tempPreSortOrder = jobTitleService.getMaxSortOrder();
		jobTitleCreate.setSortOrder(tempPreSortOrder);
		jobTitleService.create(jobTitleCreate);
		int preSortOrder = Integer.parseInt(jobTitleService.read(jobTitleCreate.getJobTitleCode()).getSortOrder());
		
		String tempPostSortOrder = jobTitleService.getMaxSortOrder();
		jobTitleUpdate.setJobTitleCode("updateTestCode");
		jobTitleUpdate.setJobTitleName("updateTestName");
		jobTitleUpdate.setSortOrder(tempPostSortOrder);
		jobTitleService.create(jobTitleUpdate);
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("jobTitleCode", "testJobTitleCode");
		map.put("sortOrder", tempPreSortOrder);
		
		jobTitleService.goDown(map);
		
		int currentSortOrder = Integer.parseInt(jobTitleService.read(jobTitleCreate.getJobTitleCode()).getSortOrder());
		
		assertTrue((preSortOrder < currentSortOrder) && (Math.abs(preSortOrder-currentSortOrder)==1));
	}
}
