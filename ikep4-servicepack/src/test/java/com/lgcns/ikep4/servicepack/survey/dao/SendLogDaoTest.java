package com.lgcns.ikep4.servicepack.survey.dao;

import java.util.ArrayList;
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

import com.lgcns.ikep4.servicepack.survey.model.SendLog;
import com.lgcns.ikep4.servicepack.survey.model.SendLogKey;
import com.lgcns.ikep4.servicepack.survey.model.Survey;
import com.lgcns.ikep4.servicepack.survey.model.SurveyStatus;
import com.lgcns.ikep4.support.user.member.dao.UserDao;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
 
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/configuration/spring/context-dao.xml", "classpath:/configuration/spring/context-service.xml","classpath:/configuration/spring/context-schedule.xml"})
public class SendLogDaoTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	@Autowired
	private IdgenService idgenService;
	
	@Autowired
	private SurveyDao surveyDao; 

	@Autowired
	private UserDao userDao; 
	
	
	
	@Autowired
	private SendLogDao SendLogDao; 
	private  final int MAX_TARGET_USER=2;
	private  Survey survey;
	private  List<SendLog> sendLogList= new ArrayList<SendLog>();

	
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
			SendLog log = new SendLog();
			
			log.setReceiverName("고인호");
			log.setReceiverMail("admin@daum.net");
			log.setSendCount(1);
			log.setSendDate( new Date() );
			log.setSendSeq(1);
			log.setSurveyId(surveyId);
			sendLogList.add(log);
		}
	}
	
	@Test 
	public void testCreate() { 
		SendLogKey result=null;
		
		for (SendLog sendLog : sendLogList) {
			String receiverId = idgenService.getNextId();
			sendLog.setReceiverId(receiverId);
			result = SendLogDao.create( sendLog );
		}
		
		Assert.assertNotNull(result);
		
	}
	
	@Test 
	public void testUpdate() { 
		boolean result=true;
		
		for (SendLog sendLog : sendLogList) {
			String receiverId = idgenService.getNextId();
			sendLog.setReceiverId(receiverId);
			SendLogDao.create( sendLog );
		}
		
		for (SendLog sendLog : sendLogList) {
			SendLogKey key = new SendLogKey(sendLog.getSurveyId(),sendLog.getReceiverId());
			
			result = SendLogDao.exists( key );
			sendLog.setSendCount(1);
			SendLogDao.update( sendLog );
		}
		
		Assert.assertTrue(result);
		
	}
	
	
	@Test
	public void getSendMailListAllBySurveyId() { 
		
		for (SendLog sendLog : sendLogList) {
			String receiverId = idgenService.getNextId();
			sendLog.setReceiverId(receiverId);
			SendLogDao.create( sendLog );
		}
		
		List<SendLog> sendLogList = SendLogDao.getSendMailListAllBySurveyId(survey.getSurveyId());
		
		Assert.assertNotNull(sendLogList);
	}
	
	@Test
	public void getSendMailListAllByUser() { 
		
		for (SendLog sendLog : sendLogList) {
			String receiverId = idgenService.getNextId();
			sendLog.setReceiverId(receiverId);
			SendLogDao.create( sendLog );
		}
		
		User user = (User)userDao.get("admin");
		
		SendLog searchSendLog = new SendLog();
		searchSendLog.setSurveyId(survey.getSurveyId());
		searchSendLog.setPortalId(user.getPortalId());
		
		List<SendLog> sendLogList = SendLogDao.getSendMailListAllByUser(searchSendLog);
		
		Assert.assertNotNull(sendLogList);
	}
	
	@Test
	public void getSendNotMail() { 
		
		for (SendLog sendLog : sendLogList) {
			String receiverId = idgenService.getNextId();
			sendLog.setReceiverId(receiverId);
			SendLogDao.create( sendLog );
		}
		
		List<SendLog> sendLogList = SendLogDao.getSendNotMail(survey.getSurveyId());
		
		Assert.assertNotNull(sendLogList);
	}
	
	@Test
	public void getTotalSendLog() { 
		
		for (SendLog sendLog : sendLogList) {
			String receiverId = idgenService.getNextId();
			sendLog.setReceiverId(receiverId);
			SendLogDao.create( sendLog );
		}
		
		Integer count = SendLogDao.getTotalSendLog(survey.getSurveyId());
		
		Assert.assertNotSame(0, count);
	}
	
	@Test 
	public void removeAllBySurveyId() {
		for (SendLog sendLog : sendLogList) {
			String receiverId = idgenService.getNextId();
			sendLog.setReceiverId(receiverId);
			SendLogDao.create( sendLog );
		}
		
		SendLogDao.removeAllBySurveyId( survey.getSurveyId() );
		
		Integer count = SendLogDao.getTotalSendLog(survey.getSurveyId());
		
		Assert.assertNotSame(0, count);
	}
	
}
