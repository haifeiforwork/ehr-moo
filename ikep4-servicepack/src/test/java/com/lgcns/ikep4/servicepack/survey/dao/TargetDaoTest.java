package com.lgcns.ikep4.servicepack.survey.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lgcns.ikep4.servicepack.survey.model.SendLog;
import com.lgcns.ikep4.servicepack.survey.model.Survey;
import com.lgcns.ikep4.servicepack.survey.model.SurveyStatus;
import com.lgcns.ikep4.servicepack.survey.model.Target;
import com.lgcns.ikep4.servicepack.survey.model.TargetKey;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
 
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/configuration/spring/context-dao.xml", "classpath:/configuration/spring/context-service.xml","classpath:/configuration/spring/context-schedule.xml"})
public class TargetDaoTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	@Autowired
	private TargetDao TargetDao; 
	
	@Autowired
	private IdgenService idgenService;
	
	@Autowired
	private SurveyDao surveyDao; 
	
	private  final int MAX_TARGET_USER=10;
	private  Integer targetType = 0;//개별 설문대상자 타입( 0 : 개인, 1 : 그룹)
	private  List<Target> targetList= new ArrayList<Target>();
	
	
	
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
		
		for (int i = 0; i <MAX_TARGET_USER; i++) {
			String targetId = StringUtils.leftPad( Integer.toString(i), 10,'0');
			TargetKey key = new TargetKey(surveyId,targetId,targetType);
			
			Target target = new Target();
			target.setTargetKey(key);
			
			target.setRegistDate(new Date());
			target.setRegisterId("admin");
			target.setRegisterName("고인호");

			targetList.add(target);
			
		}
	}
	
	@Test 
	public void testExists() {
		
		for (Target target : targetList) {
			TargetDao.create( target );
		}
		
		boolean result = false;
		
		for (Target target : targetList) {
			result = TargetDao.exists( target.getTargetKey() );
			
			if( result ) break;
		}
		
		Assert.assertTrue(result);
	}
	
	@Test 
	public void testCreate() { 
		TargetKey result = new TargetKey();
		
		for (Target target : targetList) {
			result = TargetDao.create( target );
		}
		
		Assert.assertNotNull(result);
		
	}
	
	@Test
	public void testGetAllBySurveyId() { 
		for (Target target : targetList) {
			TargetDao.create( target );
		}
		
		List<Target> targetList = TargetDao.getAllBySurveyId(survey.getSurveyId());
		
		Assert.assertNotNull(targetList);
	}
	
	@Test 
	public void testRemove() {
		for (Target target : targetList) {
			TargetDao.create( target );
		}
		
		for (Target target : targetList) {
			TargetDao.remove( target.getTargetKey() );
		}
	}
	
	@Test
	public void testGet() { 
		
		for (Target target : targetList) {
			TargetDao.create( target );
		}
		
		
		Target result = new Target();
		
		for (Target target : targetList) {
			result = TargetDao.get( target.getTargetKey() );
		}
		
		Assert.assertNull(result);
	}
	
	@Test
	public void existByUserId() { 
		
		for (Target target : targetList) {
			TargetDao.create( target );
		}
		
		
		boolean result = false;
		
		for (Target target : targetList) {
			result = TargetDao.existByUserId( target.getTargetKey() );
			break;
		}
		
		Assert.assertFalse(result);
	}
	
	@Test 
	public void removeAllBySurveyId() {
		for (Target target : targetList) {
			TargetDao.create( target );
		}
		
		TargetDao.removeAllBySurveyId( survey.getSurveyId() );
	}
	
	@Test
	public void getTotalCountBySurveyId() {
		for (Target target : targetList) {
			TargetDao.create( target );
		}
		Integer count = TargetDao.getTotalCountBySurveyId(survey.getSurveyId());
		Assert.assertNotSame(0, count);
	}
	
	
	@Test
	public void getTotalCountByMember() {
		for (Target target : targetList) {
			TargetDao.create( target );
		}
		
		Integer count = TargetDao.getTotalCountByMember(survey.getSurveyId());
		Assert.assertNotSame(0, count);
	}
	
}
