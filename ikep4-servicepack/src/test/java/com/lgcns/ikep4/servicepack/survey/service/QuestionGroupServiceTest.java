package com.lgcns.ikep4.servicepack.survey.service;

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

import com.lgcns.ikep4.servicepack.survey.model.QuestionGroup;
import com.lgcns.ikep4.servicepack.survey.model.Survey;
import com.lgcns.ikep4.servicepack.survey.model.SurveyStatus;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
 
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/configuration/spring/context-dao.xml", "classpath:/configuration/spring/context-service.xml","classpath:/configuration/spring/context-schedule.xml"})
public class QuestionGroupServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	@Autowired
	private IdgenService idgenService;
	
	@Autowired
	private SurveyService surveyService; 
	
	@Autowired
	private QuestionGroupService questionGroupService; 
	
	private  QuestionGroup questionGroup;
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
		
		surveyService.create(survey);
		
		questionGroup = new QuestionGroup();
		questionGroup.setSurveyId(surveyId);
		questionGroup.setTitle("문항1");
		questionGroup.setContents("문항1");

		
		questionGroup.setRegistDate(new Date());
		questionGroup.setRegisterId("admin");
		questionGroup.setRegisterName("고인호");
		questionGroup.setUpdateDate(new Date());
		questionGroup.setUpdaterId("admin");
		questionGroup.setUpdaterName("고인호");
		questionGroup.setQuestionGroupId(idgenService.getNextId());
		
		
	}
	
	
	@Test 
	public void exists() {
		String pk = questionGroupService.create(questionGroup);
		boolean exists = questionGroupService.exists(pk); 
		Assert.assertTrue(exists);
	}
	
	@Test 
	public void create() { 
		String pk = questionGroupService.create(questionGroup);
		QuestionGroup result = questionGroupService.read(pk); 
		Assert.assertNotNull(result);
		
	}
	
	@Test
	public void read() { 
		String pk = questionGroupService.create(questionGroup);
		QuestionGroup result = questionGroupService.read(pk); 
		Assert.assertNotNull(result);
	}
	
	
	@Test 
	public void update() {
		String pk = questionGroupService.create(questionGroup);
		questionGroup.setUpdateDate(new Date());
		questionGroup.setUpdaterId("system");
		questionGroup.setUpdaterName("관리자");
		
		questionGroupService.update(questionGroup);
		
		QuestionGroup result = questionGroupService.read(pk); 
		
		Assert.assertNotNull(result); 
	}
	
	@Test 
	public void delete() {
		String pk = questionGroupService.create(questionGroup);
		questionGroupService.delete(pk);
		boolean exists = questionGroupService.exists(pk); 
		Assert.assertFalse(exists);
	}
	
	@Test 
	public void getAllBySurveyId(){
		questionGroupService.create(questionGroup);
		List<QuestionGroup> groupList = questionGroupService.getAllBySurveyId(questionGroup.getSurveyId());
		Assert.assertNotNull(groupList);
	}
	
}
