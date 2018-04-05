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
import com.lgcns.ikep4.support.user.code.model.JobPosition;
import com.lgcns.ikep4.support.user.code.service.JobPositionService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/configuration/spring/context-dao.xml", "classpath:/configuration/spring/context-service.xml"}) 
public class JobPositionServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	@Autowired
	private JobPositionService jobPositionService;

	/**
	 * Log variable for all child classes. Uses LogFactory.getLog(getClass())
	 * from Commons Logging
	 */
	protected final Log log = LogFactory.getLog(getClass());

	private JobPosition jobPositionCreate;
	private JobPosition jobPositionUpdate;

	private AdminSearchCondition searchCondition;
	
	@Before
	public void setMethod() {

		jobPositionCreate = new JobPosition();
		jobPositionCreate.setCodeCheck("success");
		jobPositionCreate.setJobPositionCode("testJobPositionCode");
		jobPositionCreate.setJobPositionName("테스트");
		jobPositionCreate.setPortalId("P000001");
		jobPositionCreate.setSortOrder("000100");
		jobPositionCreate.setRegisterId("admin");
		jobPositionCreate.setRegisterName("관리자");
		jobPositionCreate.setUpdaterId("admin");
		jobPositionCreate.setUpdaterName("관리자");
		
		jobPositionUpdate = new JobPosition();
		jobPositionUpdate.setCodeCheck("modify");
		jobPositionUpdate.setJobPositionCode("testJobPositionCode");
		jobPositionUpdate.setJobPositionName("테스트Update");
		jobPositionUpdate.setPortalId("P000001");
		jobPositionUpdate.setSortOrder("000101");
		jobPositionUpdate.setRegisterId("admin");
		jobPositionUpdate.setRegisterName("관리자");
		jobPositionUpdate.setUpdaterId("admin");
		jobPositionUpdate.setUpdaterName("관리자");
		
		searchCondition = new AdminSearchCondition();
		searchCondition.setFieldName("jobPositionName");
		searchCondition.setStartRowIndex(0);
		searchCondition.setEndRowIndex(10);
	}
	
	@Test
	public void createJobPosition() {
		
		//직위코드를 생성한 뒤에 생성한 직위코드의 jobPositionCode로 read를 수행하여 Null이 아니면 True
		jobPositionService.create(jobPositionCreate);
		assertNotNull(jobPositionService.read(jobPositionCreate.getJobPositionCode()));
	}
	
	@Test
	public void exists() {
		
		//직위코드를 생성한 뒤에 생성한 직위코드가 존재하는지 확인. 존재하면 True
		jobPositionService.create(jobPositionCreate);
		assertTrue(jobPositionService.exists(jobPositionCreate.getJobPositionCode()));
	}
	
	@Test
	public void read() {
		
		// 테스트 jobPosition를 생성한 후 해당 jobPosition를 읽어오는 조건으로 조회한 뒤 결과가 null이 아니면 True
		jobPositionService.create(jobPositionCreate);
		JobPosition tempCreate = jobPositionService.read(jobPositionCreate.getJobPositionCode());
		
		assertNotNull(tempCreate);
	}
	
	@Test
	public void delete() {
		
		//i18nMessage에 해당 객체가 없으면 True, jobPosition에 해당 객체가 없으면 True
		jobPositionService.create(jobPositionCreate);
		jobPositionService.delete(jobPositionCreate.getJobPositionCode());
		
		assertNull(jobPositionService.read(jobPositionCreate.getJobPositionCode()));
	}
	
	@Test
	public void update() {

		// 특정한 조건으로 tempCreate을 생성하고 다른 조건으로 update 한 후 
		// 각각의 JobPosition 객체를 비교하여 같지 않으면 True
		jobPositionService.create(jobPositionCreate);
		JobPosition tempCreate = jobPositionService.read(jobPositionCreate.getJobPositionCode());
		
		jobPositionService.update(jobPositionUpdate);
		JobPosition tempUpdate = jobPositionService.read(jobPositionUpdate.getJobPositionCode());
		
		assertNotSame(tempCreate.getJobPositionName(), tempUpdate.getJobPositionName());
	}
	
	@Test
	public void list() {
		
		// 목록을 조회한 뒤에 null이 아니면 True
		jobPositionService.create(jobPositionCreate);
		
		assertNotNull(jobPositionService.list(searchCondition));
	}
	
	@Test
	public void getMaxSortOrder() {
		
		// sortOrder 최대값을 조회해서 null이 아니면 True
		jobPositionService.create(jobPositionCreate);
		
		assertEquals("000101", jobPositionService.getMaxSortOrder());
	}
	
	@Test
	public void goUp() {
		
		// 특정 조건으로 jobPosition를 생성하고 sortOrder를 하나 줄이는 조건으로 goUp을 실행한다. 
		// sortOrder의 차이가 1이고 이전 sortOrder가 현재 sortOrder보다 크면 True
		String tempSortOrder = jobPositionService.getMaxSortOrder();
		jobPositionCreate.setSortOrder(jobPositionService.getMaxSortOrder());
		jobPositionService.create(jobPositionCreate);
		int preSortOrder = Integer.parseInt(jobPositionService.read(jobPositionCreate.getJobPositionCode()).getSortOrder());
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("jobPositionCode", "testJobPositionCode");
		map.put("sortOrder", tempSortOrder);
		
		jobPositionService.goUp(map);
		
		int currentSortOrder = Integer.parseInt(jobPositionService.read(jobPositionCreate.getJobPositionCode()).getSortOrder());
		
		assertTrue((preSortOrder > currentSortOrder) && (Math.abs(preSortOrder-currentSortOrder)==1));
	}
	
	@Test
	public void goDown() {
		
		// 특정 조건으로 jobPosition를 생성하고 sortOrder를 하나 줄이는 조건으로 goDown을 실행한다. 
		// sortOrder의 차이가 1이고 이전 sortOrder가 현재 sortOrder보다 작으면 True
		String tempPreSortOrder = jobPositionService.getMaxSortOrder();
		jobPositionCreate.setSortOrder(tempPreSortOrder);
		jobPositionService.create(jobPositionCreate);
		int preSortOrder = Integer.parseInt(jobPositionService.read(jobPositionCreate.getJobPositionCode()).getSortOrder());
		
		String tempPostSortOrder = jobPositionService.getMaxSortOrder();
		jobPositionUpdate.setJobPositionCode("updateTestCode");
		jobPositionUpdate.setJobPositionName("updateTestName");
		jobPositionUpdate.setSortOrder(tempPostSortOrder);
		jobPositionService.create(jobPositionUpdate);
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("jobPositionCode", "testJobPositionCode");
		map.put("sortOrder", tempPreSortOrder);
		
		jobPositionService.goDown(map);
		
		int currentSortOrder = Integer.parseInt(jobPositionService.read(jobPositionCreate.getJobPositionCode()).getSortOrder());
		
		assertTrue((preSortOrder < currentSortOrder) && (Math.abs(preSortOrder-currentSortOrder)==1));
	}
}
