package com.lgcns.ikep4.servicepack.survey.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.servicepack.survey.model.Survey;
import com.lgcns.ikep4.servicepack.survey.model.SurveyStatus;
import com.lgcns.ikep4.servicepack.survey.model.Target;
import com.lgcns.ikep4.servicepack.survey.model.TargetKey;
import com.lgcns.ikep4.servicepack.survey.search.SurveySearchCondition;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
 
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/configuration/spring/context-dao.xml", "classpath:/configuration/spring/context-service.xml","classpath:/configuration/spring/context-schedule.xml"})
public class SurveyServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	@Autowired
	private IdgenService idgenService;
	
	@Autowired
	private SurveyService surveyService; 
	
	private  Survey survey;
	private  SurveySearchCondition surveySearchCondition;
		
	
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
		
		List<Target> targetList= new ArrayList<Target>();
		for (int i = 0; i <2; i++) {
			String targetId = StringUtils.leftPad( Integer.toString(i), 10,'0');
			TargetKey key = new TargetKey(survey.getSurveyId(),targetId,0);
			
			Target target = new Target();
			target.setTargetKey(key);
			
			target.setRegistDate(new Date());
			target.setRegisterId("admin");
			target.setRegisterName("고인호");

			targetList.add(target);
			
		}
		
		survey.setTargetList(targetList);
		
		
		
		surveySearchCondition = new SurveySearchCondition();
		surveySearchCondition.setStartRowIndex(1);
		surveySearchCondition.setEndRowIndex(10);
		surveySearchCondition.setPortalId("admin");
	}
	
	
	@Test 
	public void exists() {
		String pk = surveyService.create(survey);
		boolean exists = surveyService.exists(pk); 
		Assert.assertTrue(exists);
	}
	
	@Test 
	public void create() { 
		String pk = surveyService.create(survey);
		Assert.assertNotNull(pk);
	}
	
	@Test
	public void read() { 
		String pk = surveyService.create(survey);
		Survey result = surveyService.read(pk); 
		Assert.assertNotNull(result);
	}
	
	
	@Test 
	public void update() {
		String pk = surveyService.create(survey);
		survey.setContents("머릿말입니다.-업데이터 들어갑니다.");   //머릿말
		survey.setUpdateDate(new Date());
		survey.setUpdaterId("system");
		survey.setUpdaterName("관리자");
		
		surveyService.update(survey);
		
		Assert.assertNotNull(pk); 
	}
	
	@Test
	public void listBySearchConditionTest() {
		SearchResult<Survey> surveyItemList = surveyService.listBySearchCondition(surveySearchCondition);
		Assert.assertFalse( surveyItemList.getRecordCount().intValue() > 0 );
	}

	
	@Test 
	public void delete() {
		String pk = surveyService.create(survey);
		surveyService.delete(pk);
		boolean result = surveyService.exists(pk);
		Assert.assertFalse(result); 
	}
	
	
	@Test 
	public void approve() {
		
		String pk = surveyService.create(survey);
		
		survey.setContents("머릿말입니다.-업데이터 들어갑니다.");   //머릿말
		survey.setUpdateDate(new Date());
		survey.setUpdaterId("system");
		survey.setUpdaterName("관리자");
		survey.setSurveyStatus(SurveyStatus.APPROVE.getCode());
		
		surveyService.update(survey);
		
		Assert.assertNotNull(pk); 
	}
	
	@Test 
	public void reject() {
		
		String pk = surveyService.create(survey);
		
		survey.setContents("머릿말입니다.-업데이터 들어갑니다.");   //머릿말
		survey.setUpdateDate(new Date());
		survey.setUpdaterId("system");
		survey.setUpdaterName("관리자");
		survey.setSurveyStatus(SurveyStatus.REJECT.getCode());
		
		surveyService.update(survey);
		
		Assert.assertNotNull(pk); 
	}
	
	@Test 
	public void copy() {
		String pk = surveyService.create(survey);
		surveyService.copy(survey);
		Assert.assertNotNull(pk); 
	}
	
	
	@Test
	public void endListBySearchCondition() {
		surveyService.create(survey);
		SearchResult<Survey> surveyItemList = surveyService.endListBySearchCondition(surveySearchCondition);
		Assert.assertFalse( surveyItemList.getRecordCount().intValue() > 0 );
	}
	
	
	@Test
	public void approveListBySearchCondition() {
		surveyService.create(survey);
		SearchResult<Survey> surveyItemList = surveyService.approveListBySearchCondition(surveySearchCondition);
		Assert.assertFalse( surveyItemList.getRecordCount().intValue() > 0 );
	}
	
	@Test
	public void ingListBySearchCondition() {
		surveyService.create(survey);
		SearchResult<Survey> surveyItemList = surveyService.ingListBySearchCondition(surveySearchCondition);
		Assert.assertFalse( surveyItemList.getRecordCount().intValue() > 0 );
	}
	
	@Test
	public void existByUserId() { 
		surveyService.create(survey);
		boolean exists = surveyService.existByUserId( survey.getTargetList().get(0).getTargetKey() );
		Assert.assertFalse(exists);
	}
	
	
	
	@Test
	public void getTotalCountBySurveyId() {
		surveyService.create(survey);
		Integer count = surveyService.getTotalCountBySurveyId(survey.getSurveyId());
		Assert.assertNotSame(0, count);
	}
	
	
	@Test
	public void getTotalCountByMember() {
		surveyService.create(survey);
		Integer count = surveyService.getTotalCountByMember(survey.getPortalId());
		Assert.assertNotSame(0, count);
	}
	
}
