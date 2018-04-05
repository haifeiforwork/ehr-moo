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

import com.lgcns.ikep4.servicepack.survey.model.Response;
import com.lgcns.ikep4.servicepack.survey.model.ResponseKey;
import com.lgcns.ikep4.servicepack.survey.model.Survey;
import com.lgcns.ikep4.servicepack.survey.model.SurveyStatus;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
 
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/configuration/spring/context-dao.xml", "classpath:/configuration/spring/context-service.xml","classpath:/configuration/spring/context-schedule.xml"})
public class ResponseDaoTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	@Autowired
	private IdgenService idgenService;
	
	@Autowired
	private ResponseDao responseDao; 
	
	@Autowired
	private SurveyDao surveyDao; 
	
	private  Response response;
	private  Survey survey;
	
	@Before
	public  void setUpBeforeClass() {
		
		String surveyId = idgenService.getNextId();
		
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
		survey.setSurveyId( surveyId );
		
		surveyDao.create(survey);
		
		
		String responseId = idgenService.getNextId();
		response = new Response();
		response.setResponseId(responseId);
		response.setResponseUserId( "admin");
		response.setResponse( 0 );
		response.setResponseDate( new Date() );
		response.setResponseUserMail("admin@daum.net");
		response.setSurveyId(surveyId);
		response.setSurveyRejectReason1(null);
		response.setSurveyRejectReason2(null);
		
	}
	
	@Test 
	public void testExists() {
		ResponseKey pk = responseDao.create(response);
		boolean exists = responseDao.exists(pk); 
		Assert.assertTrue(exists);
	}
	
	@Test 
	public void testCreate() { 
		ResponseKey pk = responseDao.create(response);
		Response result = responseDao.get(pk); 
		Assert.assertNotNull(result);
	}
	
	@Test
	public void testGet() { 
		ResponseKey pk = responseDao.create(response);
		Response result = responseDao.get(pk); 
		Assert.assertNotNull(result);
	}
	
	
	@Test 
	public void testUpdate() {
		ResponseKey pk = responseDao.create(response);
		responseDao.update(response);
		Response result = responseDao.get(pk); 
		Assert.assertNotNull(result); 
	}
	
	@Test 
	public void testRemove() {
		ResponseKey pk = responseDao.create(response);
		responseDao.remove(pk);
		Response result = responseDao.get(pk);
		Assert.assertNull(result); 
	}
	
	@Test 
	public void removeAllBySurveyId() {
		ResponseKey pk = responseDao.create(response);
		responseDao.removeAllBySurveyId(survey.getSurveyId());
		Response result = responseDao.get(pk);
		Assert.assertNull(result); 
	}
	
	
	@Test 
	public void already() {
		boolean exists = responseDao.already(response); 
		Assert.assertFalse(exists);
	}
	
	@Test
	public void getDayResultList() {
		responseDao.create(response);
		List<Response> surveyItemList = responseDao.getDayResultList(survey.getSurveyId());
		assertNotNull(surveyItemList);
	}
}
