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

import com.lgcns.ikep4.servicepack.survey.model.Answer;
import com.lgcns.ikep4.servicepack.survey.model.Question;
import com.lgcns.ikep4.servicepack.survey.model.QuestionGroup;
import com.lgcns.ikep4.servicepack.survey.model.QuestionType;
import com.lgcns.ikep4.servicepack.survey.model.Response;
import com.lgcns.ikep4.servicepack.survey.model.ResponseDetail;
import com.lgcns.ikep4.servicepack.survey.model.ResponseDetailKey;
import com.lgcns.ikep4.servicepack.survey.model.ResponseType;
import com.lgcns.ikep4.servicepack.survey.model.Survey;
import com.lgcns.ikep4.servicepack.survey.model.SurveyStatus;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
 
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/configuration/spring/context-dao.xml", "classpath:/configuration/spring/context-service.xml","classpath:/configuration/spring/context-schedule.xml"})
public class ResponseDetailDaoTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	@Autowired
	private IdgenService idgenService;
	
	@Autowired
	private ResponseDetailDao responseDetailDao; 
	
	@Autowired
	private SurveyDao surveyDao; 
	
	@Autowired
	private QuestionDao questionDao; 
	
	@Autowired
	private ResponseDao responseDao;
	
	@Autowired
	private AnswerDao answerDao; 
	
	@Autowired
	private QuestionGroupDao questionGroupDao; 
	
	private  ResponseDetail responseDetail;
	
	private  QuestionGroup questionGroup;
	private  Survey survey;
	private  Question question;
	private  Response response;
	private  Answer answer;
	
	
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
		
		
		String questionGroupId = idgenService.getNextId();
		
		questionGroup = new QuestionGroup();
		questionGroup.setSurveyId(surveyId);
		questionGroup.setQuestionGroupSeq( questionGroupDao.maxQuestionGroupSeq(surveyId) );
		questionGroup.setTitle("문항1");
		questionGroup.setContents("문항1");

		
		questionGroup.setRegistDate(new Date());
		questionGroup.setRegisterId("admin");
		questionGroup.setRegisterName("고인호");
		questionGroup.setUpdateDate(new Date());
		questionGroup.setUpdaterId("admin");
		questionGroup.setUpdaterName("고인호");
		questionGroup.setQuestionGroupId(questionGroupId);
		
		questionGroupDao.create( questionGroup );
		
		
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
		question.setQuestionSeq(questionDao.maxQuestionSeq(questionGroupId));//설문 문항 질문일련번호
		question.setSurveyId(surveyId);
		question.setTitle("문항1");
		question.setQuestionGroupId(questionGroupId);

		question.setRegistDate(new Date());
		question.setRegisterId("admin");
		question.setRegisterName("고인호");
		question.setUpdateDate(new Date());
		question.setUpdaterId("admin");
		question.setUpdaterName("고인호");
		
		questionDao.create(question);
		
		String answerId = idgenService.getNextId();
		
		answer = new Answer();
		answer.setQuestionId(questionId);
		answer.setAnswerSeq(answerDao.maxAnswerSeq(questionId));
		answer.setAnswerId(answerId);
		answer.setTitle("문항1");

		
		answer.setRegistDate(new Date());
		answer.setRegisterId("admin");
		answer.setRegisterName("고인호");
		answer.setUpdateDate(new Date());
		answer.setUpdaterId("admin");
		answer.setUpdaterName("고인호");
		
		answerDao.create(answer);
		
		
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
		
		responseDao.create(response);
		
		responseDetail = new ResponseDetail();
		responseDetail.setResponseId(responseId);
		responseDetail.setQuestionId(questionId);
		responseDetail.setAnswerId(answerId);
		responseDetail.setResponse1("1");
		responseDetail.setResponse2("2");
		responseDetail.setResponseSeq(responseDetailDao.maxResponseSeq(responseId));
		responseDetail.setResponseType( ResponseType.ACHOOSE.getCode() );
		responseDetail.setQuestionType("A0");
	}
	
	
	@Test 
	public void testExists() {
		ResponseDetailKey pk =responseDetailDao.create(responseDetail);
		boolean exists = responseDetailDao.exists(pk); 
		Assert.assertTrue(exists);
	}
	
	@Test 
	public void testCreate() { 
		ResponseDetailKey pk =responseDetailDao.create(responseDetail);
		ResponseDetail result = responseDetailDao.get(pk); 
		Assert.assertNotNull(result);
		
	}
	
	@Test
	public void testGet() { 
		ResponseDetailKey pk =responseDetailDao.create(responseDetail);
		ResponseDetail result = responseDetailDao.get(pk); 
		Assert.assertNotNull(result);
	}
	
	
	@Test 
	public void testUpdate() {
		ResponseDetailKey pk =responseDetailDao.create(responseDetail);
		responseDetailDao.update(responseDetail);
		ResponseDetail result = responseDetailDao.get(pk); 
		Assert.assertNotNull(result); 
	}
	
	@Test 
	public void testRemove() {
		ResponseDetailKey pk =responseDetailDao.create(responseDetail);
		responseDetailDao.remove(pk);
		ResponseDetail result = responseDetailDao.get(pk);
		Assert.assertNull(result); 
	}
	
	
	@Test 
	public void removeAllByResponseId() {
		ResponseDetailKey pk =responseDetailDao.create(responseDetail);
		responseDetailDao.removeAllByResponseId(response.getResponseId());
		ResponseDetail result = responseDetailDao.get(pk);
		Assert.assertNull(result); 
	}
	
	
	@Test 
	public void removeAllBySurveyId() {
		ResponseDetailKey pk =responseDetailDao.create(responseDetail);
		responseDetailDao.removeAllBySurveyId(survey.getSurveyId());
		ResponseDetail result = responseDetailDao.get(pk);
		Assert.assertNull(result); 
	}
	
	
	@Test
	public void getAllByAnswerId() {
		responseDetailDao.create(responseDetail);
		List<ResponseDetail> surveyItemList = responseDetailDao.getAllByAnswerId(answer.getAnswerId());
		assertNotNull(surveyItemList);
	}
	
	@Test
	public void getReportDetailAllBySurveyId() {
		responseDetailDao.create(responseDetail);
		List<ResponseDetail> surveyItemList = responseDetailDao.getReportDetailAllBySurveyId(survey.getSurveyId());
		assertNotNull(surveyItemList);
	}

}
