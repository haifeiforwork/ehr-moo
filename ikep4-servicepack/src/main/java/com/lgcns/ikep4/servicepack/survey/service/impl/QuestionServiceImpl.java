/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.servicepack.survey.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.servicepack.survey.dao.AnswerDao;
import com.lgcns.ikep4.servicepack.survey.dao.QuestionDao;
import com.lgcns.ikep4.servicepack.survey.model.Answer;
import com.lgcns.ikep4.servicepack.survey.model.Question;
import com.lgcns.ikep4.servicepack.survey.service.QuestionService;

/**
 * 설문조사 문항 dao
 *
 * @author ihko11
 * @version $Id: QuestionServiceImpl.java 16244 2011-08-18 04:11:42Z giljae $
 */
@Service
@Transactional
public class QuestionServiceImpl extends GenericServiceImpl<Question,String> implements QuestionService {
	protected final Log log = LogFactory.getLog(getClass());
	
	private QuestionDao questionDao;
	
	//답변
	@Autowired
	private AnswerDao answerDao;
	
	//파일서비스
	@Autowired
	private FileService fileService;
	
	@Autowired
	public QuestionServiceImpl(QuestionDao dao) {
		super(dao);
		this.questionDao = dao;
	}
	
	/**
	 * 설문 문항 삭제
	 */
	@Override
	public void delete(String id) {
		Question question = questionDao.get(id);
		
		//답변삭제
		answerDao.removeAllByQuestionId(id);
		
		//문항삭제
		questionDao.remove(id);
		
		List<Question> result = questionDao.getAllByQuestionGroupId(question.getQuestionGroupId());
		int maxSeq =  0;
		
		for (Question seqInfo : result) {
			seqInfo.setQuestionSeq(  ++ maxSeq);
			questionDao.update(seqInfo);
		}
		
	}

	/**
	 * 설문 문항 생성
	 */
	@Override
	public String create(Question question) {
		getMaxQuestionSeq(question);
		questionDao.create(question);
		
		for (Answer answer : question.getAnswer() ) 
		{
			getMaxAnswerSeq(answer);
			answerDao.create(answer );
		}
		
		List<Question> result = questionDao.getSeqAllByQuestionGroupId(question);
		int maxSeq =  question.getQuestionSeq();
		
		for (Question seqInfo : result) {
			seqInfo.setQuestionSeq( ++maxSeq);
			questionDao.update(seqInfo);
		}
		
		
		List<Question> result1 = questionDao.getAllByQuestionGroupId(question.getQuestionGroupId());
		maxSeq =  0;
		
		for (Question seqInfo : result1) {
			seqInfo.setQuestionSeq(  ++ maxSeq);
			questionDao.update(seqInfo);
		}
		
		return question.getQuestionId();
	}

/**
 * 설문 문항 수정
 */
	/*@Override
	public void update(Question question) {
		
		
		//답변은 모두삭제후 다시입력
		answerDao.removeAllByQuestionId(question.getQuestionId());
		
		getMaxQuestionSeq(question);
		questionDao.update(question);
		
		for (Answer answer : question.getAnswer() ) {
			getMaxAnswerSeq(answer);
			answerDao.create(answer );
		}
		
		List<Question> result = questionDao.getSeqAllByQuestionGroupId(question);
		int maxSeq =  question.getQuestionSeq();
		
		for (Question seqInfo : result) {
			seqInfo.setQuestionSeq( ++maxSeq );
			questionDao.update(seqInfo);
		}
		
		List<Question> result1 = questionDao.getAllByQuestionGroupId(question.getQuestionGroupId());
		maxSeq =  0;
		
		for (Question seqInfo : result1) {
			seqInfo.setQuestionSeq(  ++ maxSeq);
			questionDao.update(seqInfo);
		}
		
	}*/
	/**
	 * 설문 문항 수정
	 */
		@Override
		public void update(Question question) {
			
			List<Answer> answerList =  answerDao.getAllByQuestionId(question.getQuestionId());

			//답변은 모두삭제후 다시입력
			answerDao.removeAllByQuestionId(question.getQuestionId());
			
			getMaxQuestionSeq(question);
			questionDao.update(question);
			int i=0;

			for (Answer answer : question.getAnswer() ) {
				
				//Answer hasAnswer = answerDao.get(answerList.get(i).getAnswerId());
				// 이전에 등록한 이미지를 해당 Row 의 answerId 로 수정하기
				if(i<answerList.size() && ( question.getQuestionType().equals("A1") || question.getQuestionType().equals("A5") ) ) {
					
					// 이전 첨부파일 정보
					String hasAnswerId  = answerList.get(i).getAnswerId();
					List<FileData> fileList = fileService.getItemFile( hasAnswerId , "");
					
					if(answer.getImg() == null || answer.getImg().equals("")) {
						if( fileList.size() > 0 ){
							answer.setImg( fileList.get(0).getFileId() );

							List<String> fileIdList = new ArrayList<String>();
							fileIdList.add(answer.getImg());
							
							String itemId = answer.getAnswerId();
	
							User user = new User();
							user.setUserId(question.getRegisterId());
							user.setUserName(question.getRegisterName());
	
							fileService.copyForTransfer(fileIdList, itemId, IKepConstant.ITEM_TYPE_CODE_SURVEY, user);						
							List<FileData> fileList1 = fileService.getItemFile( itemId , "");
							if( fileList1.size() > 0 ){
								answer.setImg( fileList1.get(0).getFileId() );
							}			
							fileService.removeItemFile(hasAnswerId);
						}
					}
					
					/*	
					List<String> fileIdList = new ArrayList<String>();
					fileIdList.add(answer.getImg());
					
					String itemId = answer.getAnswerId();

					User user = new User();
					user.setUserId(question.getRegisterId());
					user.setUserName(question.getRegisterName());

					fileService.copyForTransfer(fileIdList, itemId, IKepConstant.ITEM_TYPE_CODE_SURVEY, user);
					
					List<FileData> fileList1 = fileService.getItemFile( itemId , "");
					if( fileList1.size() > 0 ){
						answer.setImg( fileList1.get(0).getFileId() );
					}					
					fileService.removeItemFile(hasAnswerId);
					*/
				}
				
				getMaxAnswerSeq(answer);
				answerDao.create(answer);					
				i++;
			}
			
			List<Question> result = questionDao.getSeqAllByQuestionGroupId(question);
			int maxSeq =  question.getQuestionSeq();
			
			for (Question seqInfo : result) {
				seqInfo.setQuestionSeq( ++maxSeq );
				questionDao.update(seqInfo);
			}
			
			List<Question> result1 = questionDao.getAllByQuestionGroupId(question.getQuestionGroupId());
			maxSeq =  0;
			
			for (Question seqInfo : result1) {
				seqInfo.setQuestionSeq(  ++ maxSeq);
				questionDao.update(seqInfo);
			}
			
		}
/**
 * 설문 문항 max seq
 * @param question
 */
	private void getMaxQuestionSeq(Question question) {
		Integer maxQuestionSeq = Integer.valueOf(0);
		
		if( question.getQuestionSeq()==null )
		{
			maxQuestionSeq = questionDao.maxQuestionSeq( question.getQuestionGroupId() );
		
			int order = maxQuestionSeq.intValue();
			order++;
			
			question.setQuestionSeq(order);
		}
	}
	
	/**
	 * 설문 다변 max seq
	 * @param answer
	 */
	private void getMaxAnswerSeq(Answer answer) {
		Integer maxAnswerSeq = Integer.valueOf(0);
		
		if( answer.getAnswerSeq()==null )
		{
			maxAnswerSeq = answerDao.maxAnswerSeq( answer.getQuestionId() );
		
			int order = maxAnswerSeq.intValue();
			order++;
			
			answer.setAnswerSeq(order);
		}
	}
	
	/**
	 * 설문 문항 그룹별 총 문항 리스트
	 */
	public List<Question> getAllByQuestionGroupId(String questionGroupId){
		List<Question> result = new ArrayList<Question>();
		
		result = questionDao.getAllByQuestionGroupId(questionGroupId);
		
		for (Question question : result) {
			
			List<Answer> answerList =  answerDao.getAllByQuestionId(question.getQuestionId() );
			//파일정보를 읽어온다
			if( question.getQuestionType().equals("A1") || question.getQuestionType().equals("A5") ){
				for (Answer answer : answerList) {
					List<FileData> fileList = fileService.getItemFile( answer.getAnswerId() , "");
					
					if( fileList.size() > 0 ){
						answer.setImg( fileList.get(0).getFileId() );
					}
				}	
			}
			question.setAnswer( answerList );
		}
		
		
		return result;
	}
	
	public List<Question> getReportAllBySurveyId(String surveyId){
		List<Question> result = new ArrayList<Question>();
		result = questionDao.getReportAllBySurveyId(surveyId);
		
		return result;
	}
	public List<Question> getJoinSelectTypeAnswer(String surveyId){
		List<Question> result = new ArrayList<Question>();
		List<Answer> answerResult = answerDao.getJoinSelectTypeAnswer(surveyId);

		Question question = new Question();
		question.setAnswer(answerResult);
		result.add(question);
		
		return result;//한개의 Question에 다 넣어서 보낸다.
	}

	public void updateJoinAnswer(Question question) {

		List<Answer> answerList = question.getAnswer();
		answerDao.updateJoinQuestion(answerList.get(0));
	}
	
	public void removeJoinQuestion(String surveyId) {
		answerDao.removeJoinQuestion(surveyId);
	}
	
	public void updateAnswerTitle(Map<String, String> params) {
		answerDao.updateAnswerTitle(params);
	}
}
