/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.servicepack.survey.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.servicepack.survey.dao.AnswerDao;
import com.lgcns.ikep4.servicepack.survey.dao.QuestionDao;
import com.lgcns.ikep4.servicepack.survey.dao.QuestionGroupDao;
import com.lgcns.ikep4.servicepack.survey.model.QuestionGroup;
import com.lgcns.ikep4.servicepack.survey.service.QuestionGroupService;

/**
 * 설문조사 문항 그룹 dao
 *
 * @author ihko11
 * @version $Id: QuestionGroupServiceImpl.java 16244 2011-08-18 04:11:42Z giljae $
 */
@Service
@Transactional
public class QuestionGroupServiceImpl extends GenericServiceImpl<QuestionGroup,String> implements QuestionGroupService {
	protected final Log log = LogFactory.getLog(getClass());
	
	private QuestionGroupDao questionGroupDao;
	
	//문항
	@Autowired
	private QuestionDao questionDao;
	
	//답변
	@Autowired
	private AnswerDao answerDao;
	
	@Autowired
	public QuestionGroupServiceImpl(QuestionGroupDao dao) {
		super(dao);
		this.questionGroupDao = dao;
	}
	
	/**
	 * 문항그룹 삭제
	 */
	@Override
	public void delete(String id) {
		//답변삭제 
		answerDao.removeAllByGroupId(id);
		//문항삭제
		questionDao.removeAllByGroupId(id);
		//그룹삭제
		questionGroupDao.remove(id);
	}

	/**
	 * 문항그룹 작성
	 */
	@Override
	public String create(QuestionGroup questionGroup) {
		getMaxQuestionGroupSeq(questionGroup);
		return questionGroupDao.create(questionGroup);
	}

/**
 * 문항 그룹 수정
 */
	@Override
	public void update(QuestionGroup questionGroup) {
		getMaxQuestionGroupSeq(questionGroup);
		questionGroupDao.update(questionGroup);
	}

/**
 * 문항 그룹 max seq
 * @param questionGroup
 */
	private void getMaxQuestionGroupSeq(QuestionGroup questionGroup) {
		Integer maxQuestionGroupSeq = Integer.valueOf(0);
		
		if( questionGroup.getQuestionGroupSeq() == null  )
		{
			maxQuestionGroupSeq = questionGroupDao.maxQuestionGroupSeq( questionGroup.getSurveyId() );
		
			int order = maxQuestionGroupSeq.intValue();
			order++;
			
			questionGroup.setQuestionGroupSeq(order);
		}
	}
	
	/**
	 * 설문의 문항 그룹 리스트
	 */
	public List<QuestionGroup> getAllBySurveyId(String surveyId){
		return questionGroupDao.getAllBySurveyId(surveyId);
	}
}
