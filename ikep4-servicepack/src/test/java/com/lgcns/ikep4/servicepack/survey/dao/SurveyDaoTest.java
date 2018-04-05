package com.lgcns.ikep4.servicepack.survey.dao;

import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lgcns.ikep4.servicepack.survey.model.Survey;
import com.lgcns.ikep4.servicepack.survey.model.SurveyStatus;
import com.lgcns.ikep4.servicepack.survey.search.SurveySearchCondition;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
 
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/configuration/spring/context-dao.xml", "classpath:/configuration/spring/context-service.xml","classpath:/configuration/spring/context-schedule.xml"})
public class SurveyDaoTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	@Autowired
	private IdgenService idgenService;
	
	@Autowired
	private SurveyDao surveyDao; 
	
	private  Survey survey;

	private SurveySearchCondition surveySearchCondition;
		
	
	@Before
	public  void setUpBeforeClass() {
		survey = new Survey();
		survey.setAnonymous(0); //익명 지원 여부( 0 : 실명, 1 : 익명)
		//survey.setApproverId("admin"); //승인자 아이디 ? NULL허용
		survey.setContentsType(0); // 설문 내용(머릿말) 타입( 0 : 텍스트, 1 : 이미지)
		survey.setContents("머릿말입니다.");   //머릿말
		survey.setEndDate(new Date());      //종료일
		survey.setOppositionReason(null); 
		survey.setPortalId("admin");
		survey.setRejectButton(0); //설문 거부 버튼 표시 여부( 0 : 비표시, 1 :  표시)
		survey.setResultOpen(0);//설문 공개 여부( 0 : 공개, 1: 비공개)
		survey.setStartDate(new Date());
		survey.setSurveyStatus( SurveyStatus.WRITING.getCode() ); //설문상태( 0 : 작성중, 1 : 승인대기, 1 : 반려3 : 진행중, 4 : 종료)
		survey.setTitle("설문조사처음작성");
	    survey.setSurveyTarget(0); //( 0 : 전사, 1 : 개별설정)
		
		survey.setRegistDate(new Date());
		survey.setRegisterId("admin");
		survey.setRegisterName("고인호");
		survey.setUpdateDate(new Date());
		survey.setUpdaterId("admin");
		survey.setUpdaterName("고인호");
		survey.setSurveyId( idgenService.getNextId() );
		
		
		surveySearchCondition = new SurveySearchCondition();
		surveySearchCondition.setStartRowIndex(1);
		surveySearchCondition.setEndRowIndex(10);
	}
	
	@Test 
	public void testExists() {
		String pk = surveyDao.create(survey);
		boolean exists = surveyDao.exists(pk);
		System.out.println(exists);
		
		Assert.assertTrue(exists);
	}
	
	@Test 
	public void testCreate() { 
		String pk = surveyDao.create(survey);
		Survey result = surveyDao.get(pk); 
		
		Assert.assertNotNull(result);
		
	}
	
	@Test
	public void testGet() { 
		String pk = surveyDao.create(survey);
		Survey result = surveyDao.get(pk); 
		Assert.assertNotNull(result);
	}
	
	
	@Test 
	public void testUpdate() {
		
		String pk = surveyDao.create(survey);
		
		survey.setContents("머릿말입니다.-업데이터 들어갑니다.");   //머릿말
		survey.setUpdateDate(new Date());
		survey.setUpdaterId("system");
		survey.setUpdaterName("관리자");
		
		surveyDao.update(survey);
		
		Survey result = surveyDao.get(pk); 
		
		Assert.assertNotNull(result); 
	}
	
	@Test
	public void countBySearchConditionTest() {
		Integer count = surveyDao.countBySearchCondition(surveySearchCondition);
		assertNotNull(count);
	}
	
	@Test
	public void listBySearchConditionTest() {
		List<Survey> surveyItemList = surveyDao.listBySearchCondition(surveySearchCondition);
		assertNotNull(surveyItemList);
	}
	
	
	@Test
	public void approveCountBySearchCondition() {
		Integer count = surveyDao.approveCountBySearchCondition(surveySearchCondition);
		assertNotNull(count);
	}
	
	@Test
	public void approveListBySearchCondition() {
		List<Survey> surveyItemList = surveyDao.approveListBySearchCondition(surveySearchCondition);
		assertNotNull(surveyItemList);
	}
	
	@Test
	public void ingCountBySearchCondition() {
		Integer count = surveyDao.ingCountBySearchCondition(surveySearchCondition);
		assertNotNull(count);
	}
	
	@Test
	public void ingListBySearchCondition() {
		List<Survey> surveyItemList = surveyDao.ingListBySearchCondition(surveySearchCondition);
		assertNotNull(surveyItemList);
	}
	
	
	@Test
	public void endCountBySearchCondition() {
		Integer count = surveyDao.endCountBySearchCondition(surveySearchCondition);
		assertNotNull(count);
	}
	
	@Test
	public void endListBySearchCondition() {
		List<Survey> surveyItemList = surveyDao.endListBySearchCondition(surveySearchCondition);
		assertNotNull(surveyItemList);
	}

	
	@Test 
	public void testRemove() {
		String pk = surveyDao.create(survey);
		surveyDao.remove(pk);
		Survey result = surveyDao.get(pk);
		Assert.assertNull(result); 
	}
	
	@Test 
	public void approve() {
		
		String pk = surveyDao.create(survey);
		
		survey.setContents("머릿말입니다.-업데이터 들어갑니다.");   //머릿말
		survey.setUpdateDate(new Date());
		survey.setUpdaterId("system");
		survey.setUpdaterName("관리자");
		survey.setSurveyStatus(SurveyStatus.APPROVE.getCode());
		
		surveyDao.update(survey);
		
		Survey result = surveyDao.get(pk); 
		
		Assert.assertNotNull(result); 
	}
	
	@Test 
	public void reject() {
		
		String pk = surveyDao.create(survey);
		
		survey.setContents("머릿말입니다.-업데이터 들어갑니다.");   //머릿말
		survey.setUpdateDate(new Date());
		survey.setUpdaterId("system");
		survey.setUpdaterName("관리자");
		survey.setSurveyStatus(SurveyStatus.REJECT.getCode());
		
		surveyDao.update(survey);
		
		Survey result = surveyDao.get(pk); 
		
		Assert.assertNotNull(result); 
	}
	
	
}
