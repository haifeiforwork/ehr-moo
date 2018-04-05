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
import com.lgcns.ikep4.support.user.code.model.JobDuty;
import com.lgcns.ikep4.support.user.code.service.JobDutyService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/configuration/spring/context-dao.xml", "classpath:/configuration/spring/context-service.xml"}) 
public class JobDutyServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	@Autowired
	private JobDutyService jobDutyService;

	/**
	 * Log variable for all child classes. Uses LogFactory.getLog(getClass())
	 * from Commons Logging
	 */
	protected final Log log = LogFactory.getLog(getClass());

	private JobDuty jobDutyCreate;
	private JobDuty jobDutyUpdate;

	private AdminSearchCondition searchCondition;
	
	@Before
	public void setMethod() {

		jobDutyCreate = new JobDuty();
		jobDutyCreate.setCodeCheck("success");
		jobDutyCreate.setJobDutyCode("testJobDutyCode");
		jobDutyCreate.setJobDutyName("테스트");
		jobDutyCreate.setPortalId("P000001");
		jobDutyCreate.setSortOrder("000100");
		jobDutyCreate.setRegisterId("admin");
		jobDutyCreate.setRegisterName("관리자");
		jobDutyCreate.setUpdaterId("admin");
		jobDutyCreate.setUpdaterName("관리자");
		
		jobDutyUpdate = new JobDuty();
		jobDutyUpdate.setCodeCheck("modify");
		jobDutyUpdate.setJobDutyCode("testJobDutyCode");
		jobDutyUpdate.setJobDutyName("테스트Update");
		jobDutyUpdate.setPortalId("P000001");
		jobDutyUpdate.setSortOrder("000101");
		jobDutyUpdate.setRegisterId("admin");
		jobDutyUpdate.setRegisterName("관리자");
		jobDutyUpdate.setUpdaterId("admin");
		jobDutyUpdate.setUpdaterName("관리자");
		
		searchCondition = new AdminSearchCondition();
		searchCondition.setUserLocaleCode("ko");
		searchCondition.setStartRowIndex(0);
		searchCondition.setEndRowIndex(10);
	}
	
	@Test
	public void createJobDuty() {
		
		//직책코드를 생성한 뒤에 생성한 직책코드의 jobDutyCode로 read를 수행하여 Null이 아니면 True
		jobDutyService.create(jobDutyCreate);
		assertNotNull(jobDutyService.read(jobDutyCreate.getJobDutyCode()));
	}
	
	@Test
	public void exists() {
		
		//직책코드를 생성한 뒤에 생성한 직책코드가 존재하는지 확인. 존재하면 True
		jobDutyService.create(jobDutyCreate);
		assertTrue(jobDutyService.exists(jobDutyCreate.getJobDutyCode()));
	}
	
	@Test
	public void read() {
		
		// 테스트 jobDuty를 생성한 후 해당 jobDuty를 읽어오는 조건으로 조회한 뒤 결과가 null이 아니면 True
		jobDutyService.create(jobDutyCreate);
		JobDuty tempCreate = jobDutyService.read(jobDutyCreate.getJobDutyCode());
		
		assertNotNull(tempCreate);
	}
	
	@Test
	public void delete() {
		
		//i18nMessage에 해당 객체가 없으면 True, jobDuty에 해당 객체가 없으면 True
		jobDutyService.create(jobDutyCreate);
		jobDutyService.delete(jobDutyCreate.getJobDutyCode());
		
		assertNull(jobDutyService.read(jobDutyCreate.getJobDutyCode()));
	}
	
	@Test
	public void update() {

		// 특정한 조건으로 tempCreate을 생성하고 다른 조건으로 update 한 후 
		// 각각의 JobDuty 객체를 비교하여 같지 않으면 True
		jobDutyService.create(jobDutyCreate);
		JobDuty tempCreate = jobDutyService.read(jobDutyCreate.getJobDutyCode());
		String createName = tempCreate.getJobDutyName();
		
		tempCreate.setJobDutyName("xxxx");
		
		jobDutyService.update(tempCreate);
		JobDuty tempUpdate = jobDutyService.read(jobDutyCreate.getJobDutyCode());
		String updateName = tempUpdate.getJobDutyName();
		
		assertNotSame(createName, updateName);
	}
	
	@Test
	public void list() {
		
		// 목록을 조회한 뒤에 null이 아니면 True
		jobDutyService.create(jobDutyCreate);
		
		assertNotNull(jobDutyService.list(searchCondition));
	}
	
	@Test
	public void getMaxSortOrder() {
		
		// sortOrder 최대값을 조회해서 null이 아니면 True
		jobDutyService.create(jobDutyCreate);
		
		assertEquals("000101", jobDutyService.getMaxSortOrder());
	}
	
	@Test
	public void goUp() {
		
		// 특정 조건으로 jobDuty를 생성하고 sortOrder를 하나 줄이는 조건으로 goUp을 실행한다. 
		// sortOrder의 차이가 1이고 이전 sortOrder가 현재 sortOrder보다 크면 True
		String tempSortOrder = jobDutyService.getMaxSortOrder();
		jobDutyCreate.setSortOrder(jobDutyService.getMaxSortOrder());
		jobDutyService.create(jobDutyCreate);
		int preSortOrder = Integer.parseInt(jobDutyService.read(jobDutyCreate.getJobDutyCode()).getSortOrder());
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("jobDutyCode", "testJobDutyCode");
		map.put("sortOrder", tempSortOrder);
		
		jobDutyService.goUp(map);
		
		int currentSortOrder = Integer.parseInt(jobDutyService.read(jobDutyCreate.getJobDutyCode()).getSortOrder());
		
		assertTrue((preSortOrder > currentSortOrder) && (Math.abs(preSortOrder-currentSortOrder)==1));
	}
	
	@Test
	public void goDown() {
		
		// 특정 조건으로 jobDuty를 생성하고 sortOrder를 하나 줄이는 조건으로 goDown을 실행한다. 
		// sortOrder의 차이가 1이고 이전 sortOrder가 현재 sortOrder보다 작으면 True
		String tempPreSortOrder = jobDutyService.getMaxSortOrder();
		jobDutyCreate.setSortOrder(tempPreSortOrder);
		jobDutyService.create(jobDutyCreate);
		int preSortOrder = Integer.parseInt(jobDutyService.read(jobDutyCreate.getJobDutyCode()).getSortOrder());
		
		String tempPostSortOrder = jobDutyService.getMaxSortOrder();
		jobDutyUpdate.setJobDutyCode("updateTestCode");
		jobDutyUpdate.setJobDutyName("updateTestName");
		jobDutyUpdate.setSortOrder(tempPostSortOrder);
		jobDutyService.create(jobDutyUpdate);
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("jobDutyCode", "testJobDutyCode");
		map.put("sortOrder", tempPreSortOrder);
		
		jobDutyService.goDown(map);
		
		int currentSortOrder = Integer.parseInt(jobDutyService.read(jobDutyCreate.getJobDutyCode()).getSortOrder());
		
		assertTrue((preSortOrder < currentSortOrder) && (Math.abs(preSortOrder-currentSortOrder)==1));
	}
}
