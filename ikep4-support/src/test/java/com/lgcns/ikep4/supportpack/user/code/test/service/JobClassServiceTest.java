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

import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;
import com.lgcns.ikep4.support.user.code.model.JobClass;
import com.lgcns.ikep4.support.user.code.service.JobClassService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/configuration/spring/context-dao.xml", "classpath:/configuration/spring/context-service.xml"}) 
public class JobClassServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	@Autowired
	private JobClassService jobClassService;

	/**
	 * Log variable for all child classes. Uses LogFactory.getLog(getClass())
	 * from Commons Logging
	 */
	protected final Log log = LogFactory.getLog(getClass());

	private JobClass jobClassCreate;
	private JobClass jobClassUpdate;

	private AdminSearchCondition searchCondition;
	
	@Before
	public void setMethod() {

		jobClassCreate = new JobClass();
		jobClassCreate.setCodeCheck("success");
		jobClassCreate.setJobClassCode("testJobClassCode");
		jobClassCreate.setJobClassName("테스트");
		jobClassCreate.setJobClassEnglishName("TEST");
		jobClassCreate.setPortalId("P000001");
		jobClassCreate.setSortOrder("000100");
		jobClassCreate.setRegisterId("admin");
		jobClassCreate.setRegisterName("관리자");
		jobClassCreate.setUpdaterId("admin");
		jobClassCreate.setUpdaterName("관리자");
		
		jobClassUpdate = new JobClass();
		jobClassUpdate.setCodeCheck("modify");
		jobClassUpdate.setJobClassCode("testJobClassCode");
		jobClassUpdate.setJobClassName("테스트Update");
		jobClassUpdate.setJobClassEnglishName("TESTup");
		jobClassUpdate.setPortalId("P000001");
		jobClassUpdate.setSortOrder("000101");
		jobClassUpdate.setRegisterId("admin");
		jobClassUpdate.setRegisterName("관리자");
		jobClassUpdate.setUpdaterId("admin");
		jobClassUpdate.setUpdaterName("관리자");
		
		searchCondition = new AdminSearchCondition();
		searchCondition.setUserLocaleCode("ko");
		searchCondition.setStartRowIndex(0);
		searchCondition.setEndRowIndex(10);
	}
	
	@Test
	public void createJobClass() {
		
		//직군코드를 생성한 뒤에 생성한 직군코드의 jobClassCode로 read를 수행하여 Null이 아니면 True
		jobClassService.create(jobClassCreate);
		assertNotNull(jobClassService.read(jobClassCreate.getJobClassCode()));
	}
	
	@Test
	public void exists() {
		
		//직군코드를 생성한 뒤에 생성한 직군코드가 존재하는지 확인. 존재하면 True
		jobClassService.create(jobClassCreate);
		assertTrue(jobClassService.exists(jobClassCreate.getJobClassCode()));
	}
	
	@Test
	public void read() {
		
		// 테스트 jobClass를 생성한 후 해당 jobClass를 읽어오는 조건으로 조회한 뒤 결과가 null이 아니면 True
		jobClassService.create(jobClassCreate);
		JobClass tempCreate = jobClassService.read(jobClassCreate.getJobClassCode());
		
		assertNotNull(tempCreate);
	}
	
	@Test
	public void delete() {
		
		//i18nMessage에 해당 객체가 없으면 True, jobClass에 해당 객체가 없으면 True
		jobClassService.create(jobClassCreate);
		jobClassService.delete(jobClassCreate.getJobClassCode());
		
		assertNull(jobClassService.read(jobClassCreate.getJobClassCode()));
	}
	
	@Test
	public void update() {

		// 특정한 조건으로 tempCreate을 생성하고 다른 조건으로 update 한 후 
		// 각각의 JobClass 객체를 비교하여 같지 않으면 True
		jobClassService.create(jobClassCreate);
		JobClass tempCreate = jobClassService.read(jobClassCreate.getJobClassCode());
		
		jobClassService.update(jobClassUpdate);
		JobClass tempUpdate = jobClassService.read(jobClassUpdate.getJobClassCode());
		
		assertNotSame(tempCreate.getJobClassName(), tempUpdate.getJobClassName());
	}
	
	@Test
	public void list() {
		
		// 목록을 조회한 뒤에 null이 아니면 True
		jobClassService.create(jobClassCreate);
		
		SearchResult<JobClass> result = jobClassService.list(searchCondition);
		
		assertNotNull(result);
	}
	
	@Test
	public void getMaxSortOrder() {
		
		// sortOrder 최대값을 조회해서 null이 아니면 True
		jobClassService.create(jobClassCreate);
		
		assertEquals("000101", jobClassService.getMaxSortOrder());
	}
	
	@Test
	public void goUp() {
		
		// 특정 조건으로 jobClass를 생성하고 sortOrder를 하나 줄이는 조건으로 goUp을 실행한다. 
		// sortOrder의 차이가 1이고 이전 sortOrder가 현재 sortOrder보다 크면 True
		String tempSortOrder = jobClassService.getMaxSortOrder();
		jobClassCreate.setSortOrder(jobClassService.getMaxSortOrder());
		jobClassService.create(jobClassCreate);
		int preSortOrder = Integer.parseInt(jobClassService.read(jobClassCreate.getJobClassCode()).getSortOrder());
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("jobClassCode", "testJobClassCode");
		map.put("sortOrder", tempSortOrder);
		
		jobClassService.goUp(map);
		
		int currentSortOrder = Integer.parseInt(jobClassService.read(jobClassCreate.getJobClassCode()).getSortOrder());
		
		assertTrue((preSortOrder > currentSortOrder) && (Math.abs(preSortOrder-currentSortOrder)==1));
	}
	
	@Test
	public void goDown() {
		
		// 특정 조건으로 jobClass를 생성하고 sortOrder를 하나 줄이는 조건으로 goDown을 실행한다. 
		// sortOrder의 차이가 1이고 이전 sortOrder가 현재 sortOrder보다 작으면 True
		String tempPreSortOrder = jobClassService.getMaxSortOrder();
		jobClassCreate.setSortOrder(tempPreSortOrder);
		jobClassService.create(jobClassCreate);
		int preSortOrder = Integer.parseInt(jobClassService.read(jobClassCreate.getJobClassCode()).getSortOrder());
		
		String tempPostSortOrder = jobClassService.getMaxSortOrder();
		jobClassUpdate.setJobClassCode("updateTestCode");
		jobClassUpdate.setJobClassName("updateTestName");
		jobClassUpdate.setSortOrder(tempPostSortOrder);
		jobClassService.create(jobClassUpdate);
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("jobClassCode", "testJobClassCode");
		map.put("sortOrder", tempPreSortOrder);
		
		jobClassService.goDown(map);
		
		int currentSortOrder = Integer.parseInt(jobClassService.read(jobClassCreate.getJobClassCode()).getSortOrder());
		
		assertTrue((preSortOrder < currentSortOrder) && (Math.abs(preSortOrder-currentSortOrder)==1));
	}
}
