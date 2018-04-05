/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.servicepack.survey.portlet.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.servicepack.survey.dao.AnswerDao;
import com.lgcns.ikep4.servicepack.survey.dao.QuestionDao;
import com.lgcns.ikep4.servicepack.survey.dao.QuestionGroupDao;
import com.lgcns.ikep4.servicepack.survey.dao.RequestLogDao;
import com.lgcns.ikep4.servicepack.survey.dao.ResponseDao;
import com.lgcns.ikep4.servicepack.survey.dao.ResponseDetailDao;
import com.lgcns.ikep4.servicepack.survey.dao.SendLogDao;
import com.lgcns.ikep4.servicepack.survey.dao.SurveyDao;
import com.lgcns.ikep4.servicepack.survey.dao.TargetDao;
import com.lgcns.ikep4.servicepack.survey.model.Answer;
import com.lgcns.ikep4.servicepack.survey.model.Question;
import com.lgcns.ikep4.servicepack.survey.model.QuestionGroup;
import com.lgcns.ikep4.servicepack.survey.model.Survey;
import com.lgcns.ikep4.servicepack.survey.model.Target;
import com.lgcns.ikep4.servicepack.survey.model.TargetKey;
import com.lgcns.ikep4.servicepack.survey.portlet.services.PortletSurveyService;
import com.lgcns.ikep4.servicepack.survey.search.SurveySearchCondition;
import com.lgcns.ikep4.servicepack.survey.util.SurveyConstance;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


/**
 * 설문조사 dao
 * 
 * @author ihko11
 * @version $Id: PortletSurveyServiceImpl.java 16244 2011-08-18 04:11:42Z giljae $
 */
@Service
public class PortletSurveyServiceImpl extends GenericServiceImpl<Survey, String> implements PortletSurveyService {
	protected final Log log = LogFactory.getLog(getClass());

	/*
	 * private int SURVEY_DEFAULT_TARGET_TYPE=0; private int
	 * SURVEY_DEFAULT_TARGET_USER=0; private int SURVEY_DEFAULT_TARGET_GROUP=1;
	 */

	@Autowired
	private IdgenService idgenService;

	// 설문
	@Autowired
	private SurveyDao surveyDao;

	// 설문대상
	@Autowired
	private TargetDao targetDao;

	// 문항그룹
	@Autowired
	private QuestionGroupDao questionGroupDao;

	// 문항
	@Autowired
	private QuestionDao questionDao;

	// 답변
	@Autowired
	private AnswerDao answerDao;

	@Autowired
	private SendLogDao sendLogDao;

	@Autowired
	private ResponseDao responseDao;

	@Autowired
	private ResponseDetailDao responseDetailDao;

	@Autowired
	private FileService fileService;

	@Autowired
	private RequestLogDao requestLogDao;
	
	/*
	@Autowired
	public PortletSurveyServiceImpl(SurveyDao dao) {
		super();
		this.surveyDao = dao;
	}
	*/

	/**
	 * 삭제
	 */
	public void delete(String id) {
		
		/**
		 * 설문 참가로그 삭제
		 */
		requestLogDao.removeAllBySurveyId(id);
		
		// 고객답변상세삭제
		responseDetailDao.removeAllBySurveyId(id);
		// 고객답변삭제
		responseDao.removeAllBySurveyId(id);

		// 문항답변삭제
		answerDao.removeAllBySurveyId(id);

		// 문항질문삭제
		questionDao.removeAllBySurveyId(id);

		// 문항그룹삭제
		questionGroupDao.removeAllBySurveyId(id);

		// 메일대상자로그 삭제
		sendLogDao.removeAllBySurveyId(id);

		// 대상자 전체 삭제
		targetDao.removeAllBySurveyId(id);

		// 설문삭제
		surveyDao.remove(id);
	}

	/**
	 * 설문 상세
	 */
	public Survey read(String id) {
		Survey survey = surveyDao.get(id);

		if (survey != null && survey.getSurveyTarget() != SurveyConstance.SURVEY_DEFAULT_TARGET_TYPE) {
			List<Target> tmpTargetList = targetDao.getAllBySurveyId(id);
			List<Target> targetList = new ArrayList<Target>();
			List<Target> targetGroupList = new ArrayList<Target>();

			for (Target target : tmpTargetList) {
				int targetType = target.getTargetKey().getTargetType();

				if (targetType == SurveyConstance.SURVEY_DEFAULT_TARGET_USER) {
					targetList.add(target);
				} else if (targetType == SurveyConstance.SURVEY_DEFAULT_TARGET_GROUP) {
					targetGroupList.add(target);
				}
			}

			survey.setTargetList(targetList);
			survey.setTargetGroupList(targetGroupList);
		}

		return survey;
	}

	/**
	 * 설문 생성
	 */
	public String create(Survey survey) {
		// 설문 추가
		surveyDao.create(survey);

		insertTarget(survey);

		return survey.getSurveyId();
	}

	/**
	 * 설문 수정
	 */
	public void update(Survey survey) {
		// 설문 수정
		surveyDao.update(survey);

		// 대상자 전체 삭제
		targetDao.removeAllBySurveyId(survey.getSurveyId());

		insertTarget(survey);
	}

	/**
	 * 설문 대상자 입력
	 * 
	 * @param survey
	 */
	public void insertTarget(Survey survey) {
		// 대상자유저
		for (Target target : survey.getTargetList()) {
			targetDao.create(target);
		}

		// 대상그룹
		for (Target target : survey.getTargetGroupList()) {
			targetDao.create(target);
		}
	}

	/**
	 * 설문 승인
	 */
	public void approve(Survey survey) {
		surveyDao.approve(survey);
	}

	/**
	 * 설문 반려
	 */
	public void reject(Survey survey) {
		surveyDao.reject(survey);
	}

	/**
	 * 설문 기간 지난거 자동업데이터
	 */
	public void exipireDateUpdate() {
		surveyDao.exipireDateUpdate();
	}

	/**
	 * 설문 작성 리스트
	 */
	public SearchResult<Survey> listBySearchCondition(SurveySearchCondition surveySearchCondition) {
		SearchResult<Survey> searchResult = null;

		Integer count = this.surveyDao.countBySearchCondition(surveySearchCondition);
		surveySearchCondition.terminateSearchCondition(count);

		if (surveySearchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<Survey>(surveySearchCondition);
		} else {
			List<Survey> surveyItemList = surveyDao.listBySearchCondition(surveySearchCondition);
			searchResult = new SearchResult<Survey>(surveyItemList, surveySearchCondition);
		}

		return searchResult;
	}

	/**
	 * 설문 승인 리스트
	 */
	public SearchResult<Survey> approveListBySearchCondition(SurveySearchCondition surveySearchCondition) {
		SearchResult<Survey> searchResult = null;

		Integer count = this.surveyDao.approveCountBySearchCondition(surveySearchCondition);
		surveySearchCondition.terminateSearchCondition(count);

		if (surveySearchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<Survey>(surveySearchCondition);
		} else {
			List<Survey> surveyItemList = surveyDao.approveListBySearchCondition(surveySearchCondition);
			searchResult = new SearchResult<Survey>(surveyItemList, surveySearchCondition);
		}

		return searchResult;
	}

	/**
	 * 설문 진행 리스트
	 */
	public SearchResult<Survey> ingListBySearchCondition(SurveySearchCondition surveySearchCondition) {
		SearchResult<Survey> searchResult = null;

		Integer count = this.surveyDao.ingCountBySearchCondition(surveySearchCondition);
		surveySearchCondition.terminateSearchCondition(count);

		if (surveySearchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<Survey>(surveySearchCondition);
		} else {
			List<Survey> surveyItemList = surveyDao.ingListBySearchCondition(surveySearchCondition);
			for (Survey survey : surveyItemList) {
				survey.setSendLogCnt(targetDao.getTotalCountBySurveyId(survey.getSurveyId()));
			}

			searchResult = new SearchResult<Survey>(surveyItemList, surveySearchCondition);
		}

		return searchResult;
	}

	/**
	 * 설문 종료 리스트
	 */
	public SearchResult<Survey> endListBySearchCondition(SurveySearchCondition surveySearchCondition) {
		SearchResult<Survey> searchResult = null;

		Integer count = this.surveyDao.endCountBySearchCondition(surveySearchCondition);
		surveySearchCondition.terminateSearchCondition(count);

		if (surveySearchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<Survey>(surveySearchCondition);
		} else {
			List<Survey> surveyItemList = surveyDao.endListBySearchCondition(surveySearchCondition);
			for (Survey survey : surveyItemList) {
				survey.setSendLogCnt(targetDao.getTotalCountBySurveyId(survey.getSurveyId()));
			}
			searchResult = new SearchResult<Survey>(surveyItemList, surveySearchCondition);
		}

		return searchResult;
	}

	/**
	 * 설문 복사
	 */
	public void copy(Survey survey) {
		String oldSurveyId = survey.getSurveyId();

		// 설문 복사 신규생성
		String surveyId = idgenService.getNextId();
		survey.setSurveyId(surveyId);

		try {
			copyImag(survey);
		} catch (Exception e) {
			// 파일처리주에러면 그냥 타입을 text로변경
			survey.setContentsType(0);
		}

		surveyDao.create(survey);

		// 대상자 복사
		List<Target> tmpTargetList = targetDao.getAllBySurveyId(oldSurveyId);

		for (Target target : tmpTargetList) {
			TargetKey targetKey = target.getTargetKey();
			targetKey.setSurveyId(surveyId);

			target.setTargetKey(targetKey);
			target.setSurveyId(surveyId);
			target.setRegistDate(new Date());
			target.setRegisterId(survey.getRegisterId());
			target.setRegisterName(survey.getRegisterName());
			target.setUpdateDate(new Date());
			target.setUpdaterId(survey.getRegisterId());
			target.setUpdaterName(survey.getRegisterName());
			targetDao.create(target);
		}

		// 문항그룹
		List<QuestionGroup> questionGroupList = questionGroupDao.getAllBySurveyId(oldSurveyId);

		for (QuestionGroup group : questionGroupList) {
			makeGroup(survey, surveyId, group);

		}
	}

	private void makeGroup(Survey survey, String surveyId, QuestionGroup group) {
		String oldQuestionGroupId = group.getQuestionGroupId();

		String questionGroupId = idgenService.getNextId();
		group.setQuestionGroupId(questionGroupId);
		group.setSurveyId(surveyId);
		group.setRegistDate(new Date());
		group.setRegisterId(survey.getRegisterId());
		group.setRegisterName(survey.getRegisterName());
		group.setUpdateDate(new Date());
		group.setUpdaterId(survey.getRegisterId());
		group.setUpdaterName(survey.getRegisterName());

		questionGroupDao.create(group);

		// 문항
		List<Question> questionList = questionDao.getAllByQuestionGroupId(oldQuestionGroupId);
		for (Question question : questionList) {
			String oldQuestionId = question.getQuestionId();

			makeQuestion(survey, surveyId, questionGroupId, question, oldQuestionId);
		}
	}

	private void makeQuestion(Survey survey, String surveyId, String questionGroupId, Question question,
			String oldQuestionId) {
		String questionId = idgenService.getNextId();
		question.setSurveyId(surveyId);
		question.setQuestionGroupId(questionGroupId);
		question.setQuestionId(questionId);
		question.setRegistDate(new Date());
		question.setRegisterId(survey.getRegisterId());
		question.setRegisterName(survey.getRegisterName());
		question.setUpdateDate(new Date());
		question.setUpdaterId(survey.getRegisterId());
		question.setUpdaterName(survey.getRegisterName());
		questionDao.create(question);

		// 답변
		List<Answer> answerList = answerDao.getAllByQuestionId(oldQuestionId);
		for (Answer answer : answerList) {
			String answerId = idgenService.getNextId();
			answer.setAnswerId(answerId);
			answer.setSurveyId(surveyId);
			answer.setQuestionId(questionId);
			answer.setRegistDate(new Date());
			answer.setRegisterId(survey.getRegisterId());
			answer.setRegisterName(survey.getRegisterName());
			answer.setUpdateDate(new Date());
			answer.setUpdaterId(survey.getRegisterId());
			answer.setUpdaterName(survey.getRegisterName());

			try {
				makeAnswer(survey, question, answer);
			} catch (Exception e) {
				log.error(e.getMessage());
				// 파일처리중 에러가 발생하면
				answer.setImg(null);
			}

			answerDao.create(answer);
		}
	}

	private void makeAnswer(Survey survey, Question question, Answer answer) {
		if ((question.getQuestionType().equals("A1") || question.getQuestionType().equals("A5"))
				&& StringUtils.hasText(answer.getImg())) // 타입이
															// 이미지일경우
		{
			List<String> fileIdList = new ArrayList<String>();
			fileIdList.add(answer.getImg());

			String itemId = answer.getAnswerId();

			User user = new User();
			user.setUserId(survey.getRegisterId());
			user.setUserName(survey.getRegisterName());

			fileService.copyForTransfer(fileIdList, itemId, IKepConstant.ITEM_TYPE_CODE_SURVEY, user);

		}
	}

	private void copyImag(Survey survey) {
		if (survey.getContentsType().intValue() == 1) // 타입이 이미지일경우
		{
			List<String> fileIdList = new ArrayList<String>();
			fileIdList.add(survey.getContents());

			String itemId = survey.getSurveyId();
			User user = new User();
			user.setUserId(survey.getRegisterId());
			user.setUserName(survey.getRegisterName());

			List<FileData> copyList = fileService.copyForTransfer(fileIdList, itemId,
					IKepConstant.ITEM_TYPE_CODE_SURVEY, user);

			if (copyList.size() > 0) {
				survey.setContents(copyList.get(0).getFileId());
			}
		}
	}

	/**
	 * 참여 가능 대상자 인지 체크
	 */
	public boolean existByUserId(TargetKey targetKey) {
		return targetDao.existByUserId(targetKey);
	}

	/**
	 * 참여자 총 카운트
	 */
	public Integer getTotalCountBySurveyId(String surveyId) {
		return targetDao.getTotalCountBySurveyId(surveyId);
	}

	/**
	 * 전사일경우 사용자 총 카운트
	 * 
	 * @return
	 */
	public Integer getTotalCountByMember(String portalId) {
		return targetDao.getTotalCountByMember(portalId);
	}

	public boolean exists(String id) {
		// TODO Auto-generated method stub
		return false;
	}

}
