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

import com.lgcns.ikep4.servicepack.survey.model.Question;
import com.lgcns.ikep4.servicepack.survey.model.QuestionGroup;
import com.lgcns.ikep4.servicepack.survey.model.QuestionType;
import com.lgcns.ikep4.servicepack.survey.model.Survey;
import com.lgcns.ikep4.servicepack.survey.model.SurveyStatus;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
 
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/configuration/spring/context-dao.xml", "classpath:/configuration/spring/context-service.xml","classpath:/configuration/spring/context-schedule.xml"})
public class QuestionServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	@Autowired
	private IdgenService idgenService;
	
	@Autowired
	private SurveyService surveyService; 
	
	@Autowired
	private QuestionGroupService questionGroupService; 
	
	@Autowired
	private QuestionService questionService; 
	
	private  QuestionGroup questionGroup;
	private  Survey survey;
	private  Question question;
	
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
		
		String questionGroupId = idgenService.getNextId();
		
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
		questionGroup.setQuestionGroupId( questionGroupId );
		
		questionGroupService.create(questionGroup );
		
		String questionId = idgenService.getNextId();
		
		question = new Question();
		question.setQuestionId(questionId);
		question.setDisplayType(1); //수직,수평,컬럼
		question.setColumnCount(1);//한행에 나올 답변수
		question.setNeedAbility( new Long(1) );//요구 역량 ?무엇인가?
		//question.setQuestionId(String);//답변ID
		question.setQuestionType(QuestionType.A0.name());
		/**
		 * 설문문항 질문형식
			( A0 : 단일선택 기본형,
			A1 : 단일선택 이미지,
			A2 : 단일선택 TextBox 추가,
			A3 : 단일선택 TextArea 추가,
			A4 : 다중선택 기본형
			A5 : 다중선택 이미지,
			A6 : 다중선택 TextBox 추가,
			A7 : 다중선택 TextArea 추가,
			B0: 단일 TextBox,
			B0: TextBox Form,
			B0: 다중TextBox,
			B0: TextArea,
			C0 : 순위
			D0 : 척도 3점,
			D0 : 척도 5점,
			D0 : 척도 6점,
			D0 : 척도 7점,
			D0 : 척도 OX + 5점,
			D0 : 척도 OX + 7점,
			D0 : 척도 N/A + 7점,
			D0 : 수준 )
		 */
		question.setRequiredAnswer(0); //설문 문항 필수 답변 여부( 0 : 필수아님, 1 : 필수)
		question.setScale1("낮다");//척도 1
		question.setScale2("보통");//척도 1
		question.setScale3("높다");//척도 1
		question.setSurveyId(surveyId);
		question.setTitle("문항1");
		question.setQuestionGroupId(questionGroupId);

		question.setRegistDate(new Date());
		question.setRegisterId("admin");
		question.setRegisterName("고인호");
		question.setUpdateDate(new Date());
		question.setUpdaterId("admin");
		question.setUpdaterName("고인호");
		
	}
	
	@Test 
	public void exists() {
		String pk = questionService.create(question);
		boolean exists = questionService.exists(pk); 
		Assert.assertTrue(exists);
	}
	
	@Test 
	public void create() {
		String pk = questionService.create(question);
		Question info = questionService.read(pk); 
		Assert.assertNotNull(info);
	}
	
	@Test 
	public void update() {
		String pk = questionService.create(question);
		questionService.update(question);
		Question info = questionService.read(pk); 
		Assert.assertNotNull(info);
	}
	
	@Test 
	public void delete() {
		String pk = questionService.create(question);
		questionService.delete(pk);
		boolean exists = questionService.exists(pk); 
		Assert.assertFalse(exists);
	}
	
	
	
	@Test 
	public void getAllByQuestionGroupId() {
		questionService.create(question);
		List<Question> list = questionService.getAllByQuestionGroupId(question.getQuestionGroupId()); 
		Assert.assertNotNull(list);
	}
	
	@Test 
	public void getReportAllBySurveyId() {
		questionService.create(question);
		List<Question> list = questionService.getReportAllBySurveyId(survey.getSurveyId()); 
		Assert.assertNotNull(list);
	}
}
