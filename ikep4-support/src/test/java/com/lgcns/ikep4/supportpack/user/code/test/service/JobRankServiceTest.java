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
import com.lgcns.ikep4.support.user.code.model.JobRank;
import com.lgcns.ikep4.support.user.code.service.JobRankService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/configuration/spring/context-dao.xml", "classpath:/configuration/spring/context-service.xml"}) 
public class JobRankServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	@Autowired
	private JobRankService jobRankService;

	/**
	 * Log variable for all child classes. Uses LogFactory.getLog(getClass())
	 * from Commons Logging
	 */
	protected final Log log = LogFactory.getLog(getClass());

	private JobRank jobRankCreate;
	private JobRank jobRankUpdate;

	private AdminSearchCondition searchCondition;
	
	@Before
	public void setMethod() {

		jobRankCreate = new JobRank();
		jobRankCreate.setCodeCheck("success");
		jobRankCreate.setJobRankCode("testJobRankCode");
		jobRankCreate.setJobRankName("테스트");
		jobRankCreate.setPortalId("P000001");
		jobRankCreate.setSortOrder("000100");
		jobRankCreate.setRegisterId("admin");
		jobRankCreate.setRegisterName("관리자");
		jobRankCreate.setUpdaterId("admin");
		jobRankCreate.setUpdaterName("관리자");
		
		jobRankUpdate = new JobRank();
		jobRankUpdate.setCodeCheck("modify");
		jobRankUpdate.setJobRankCode("testJobRankCode");
		jobRankUpdate.setJobRankName("테스트Update");
		jobRankUpdate.setPortalId("P000001");
		jobRankUpdate.setSortOrder("000101");
		jobRankUpdate.setRegisterId("admin");
		jobRankUpdate.setRegisterName("관리자");
		jobRankUpdate.setUpdaterId("admin");
		jobRankUpdate.setUpdaterName("관리자");
		
		searchCondition = new AdminSearchCondition();
		searchCondition.setFieldName("jobRankName");
		searchCondition.setStartRowIndex(0);
		searchCondition.setEndRowIndex(10);
	}
	
	@Test
	public void createJobRank() {
		
		//직급코드를 생성한 뒤에 생성한 직급코드의 jobRankCode로 read를 수행하여 Null이 아니면 True
		jobRankService.create(jobRankCreate);
		assertNotNull(jobRankService.read(jobRankCreate.getJobRankCode()));
	}
	
	@Test
	public void exists() {
		
		//직급코드를 생성한 뒤에 생성한 직급코드가 존재하는지 확인. 존재하면 True
		jobRankService.create(jobRankCreate);
		assertTrue(jobRankService.exists(jobRankCreate.getJobRankCode()));
	}
	
	@Test
	public void read() {
		
		// 테스트 jobRank를 생성한 후 해당 jobRank를 읽어오는 조건으로 조회한 뒤 결과가 null이 아니면 True
		jobRankService.create(jobRankCreate);
		JobRank tempCreate = jobRankService.read(jobRankCreate.getJobRankCode());
		
		assertNotNull(tempCreate);
	}
	
	@Test
	public void delete() {
		
		//i18nMessage에 해당 객체가 없으면 True, jobRank에 해당 객체가 없으면 True
		jobRankService.create(jobRankCreate);
		jobRankService.delete(jobRankCreate.getJobRankCode());
		
		assertNull(jobRankService.read(jobRankCreate.getJobRankCode()));
	}
	
	@Test
	public void update() {

		// 특정한 조건으로 tempCreate을 생성하고 다른 조건으로 update 한 후 
		// 각각의 JobRank 객체를 비교하여 같지 않으면 True
		jobRankService.create(jobRankCreate);
		JobRank tempCreate = jobRankService.read(jobRankCreate.getJobRankCode());
		
		jobRankService.update(jobRankUpdate);
		JobRank tempUpdate = jobRankService.read(jobRankUpdate.getJobRankCode());
		
		assertNotSame(tempCreate.getJobRankName(), tempUpdate.getJobRankName());
	}
	
	@Test
	public void list() {
		
		// 목록을 조회한 뒤에 null이 아니면 True
		jobRankService.create(jobRankCreate);
		
		assertNotNull(jobRankService.list(searchCondition));
	}
	
	@Test
	public void getMaxSortOrder() {
		
		// sortOrder 최대값을 조회해서 null이 아니면 True
		jobRankService.create(jobRankCreate);
		
		assertEquals("000101", jobRankService.getMaxSortOrder());
	}
	
	@Test
	public void goUp() {
		
		// 특정 조건으로 jobRank를 생성하고 sortOrder를 하나 줄이는 조건으로 goUp을 실행한다. 
		// sortOrder의 차이가 1이고 이전 sortOrder가 현재 sortOrder보다 크면 True
		String tempSortOrder = jobRankService.getMaxSortOrder();
		jobRankCreate.setSortOrder(jobRankService.getMaxSortOrder());
		jobRankService.create(jobRankCreate);
		int preSortOrder = Integer.parseInt(jobRankService.read(jobRankCreate.getJobRankCode()).getSortOrder());
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("jobRankCode", "testJobRankCode");
		map.put("sortOrder", tempSortOrder);
		
		jobRankService.goUp(map);
		
		int currentSortOrder = Integer.parseInt(jobRankService.read(jobRankCreate.getJobRankCode()).getSortOrder());
		
		assertTrue((preSortOrder > currentSortOrder) && (Math.abs(preSortOrder-currentSortOrder)==1));
	}
	
	@Test
	public void goDown() {
		
		// 특정 조건으로 jobRank를 생성하고 sortOrder를 하나 줄이는 조건으로 goDown을 실행한다. 
		// sortOrder의 차이가 1이고 이전 sortOrder가 현재 sortOrder보다 작으면 True
		String tempPreSortOrder = jobRankService.getMaxSortOrder();
		jobRankCreate.setSortOrder(tempPreSortOrder);
		jobRankService.create(jobRankCreate);
		int preSortOrder = Integer.parseInt(jobRankService.read(jobRankCreate.getJobRankCode()).getSortOrder());
		
		String tempPostSortOrder = jobRankService.getMaxSortOrder();
		jobRankUpdate.setJobRankCode("updateTestCode");
		jobRankUpdate.setJobRankName("updateTestName");
		jobRankUpdate.setSortOrder(tempPostSortOrder);
		jobRankService.create(jobRankUpdate);
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("jobRankCode", "testJobRankCode");
		map.put("sortOrder", tempPreSortOrder);
		
		jobRankService.goDown(map);
		
		int currentSortOrder = Integer.parseInt(jobRankService.read(jobRankCreate.getJobRankCode()).getSortOrder());
		
		assertTrue((preSortOrder < currentSortOrder) && (Math.abs(preSortOrder-currentSortOrder)==1));
	}
}
