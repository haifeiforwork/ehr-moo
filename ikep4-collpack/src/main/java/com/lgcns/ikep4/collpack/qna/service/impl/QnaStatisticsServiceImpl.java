/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.qna.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.collpack.qna.constants.QnaConstants;
import com.lgcns.ikep4.collpack.qna.dao.QnaDao;
import com.lgcns.ikep4.collpack.qna.dao.QnaPolicyDao;
import com.lgcns.ikep4.collpack.qna.dao.QnaStatisticsDao;
import com.lgcns.ikep4.collpack.qna.model.QnaStatistics;
import com.lgcns.ikep4.collpack.qna.service.QnaStatisticsService;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;


/**
 * BoardService 구현 클래스
 * 
 * @author 이주열(jooyoul.lee@lgcns.com)
 * @version $Id: QnaStatisticsServiceImpl.java 16236 2011-08-18 02:48:22Z giljae $
 */
@Service("qnaStatisticsService")
public class QnaStatisticsServiceImpl extends GenericServiceImpl<QnaStatistics, String> implements QnaStatisticsService {


	@Autowired
	private QnaStatisticsDao qnaStatisticsDao;
	
	@Autowired
	private QnaPolicyDao qnaPolicyDao;
	
	
	@Autowired
	private QnaDao qnaDao;
	

	public QnaStatistics read(String portalId) {
		return qnaStatisticsDao.get(portalId);
	}

	public void create() {
		
		List<String> potalIdList = qnaPolicyDao.listPotalId();
		
		for(String portalId : potalIdList){
			
			qnaStatisticsDao.remove(portalId);
			
			QnaStatistics qnaStatistics = new QnaStatistics();
			
			
			double sumAnswerNecessaryTime = qnaDao.getSumAnswerNecessaryTime(portalId);
			int countAnswerNecessaryTime = qnaDao.getCountAnswerNecessaryTime(portalId);
			
			int countQna = qnaDao.getCountQna(portalId);
			int countQnaHasAnswer = qnaDao.getCountQnaHasAnswer(portalId);
			
			double averageAnswerTime = sumAnswerNecessaryTime/ countAnswerNecessaryTime;	//평균답변시간 = 전체답변 시간합 / 답변시간이 있는 질문수
			double answerRatio = Double.parseDouble(String.valueOf(countQnaHasAnswer)) / countQna;	//평균답변수 = 전체 답변 있는 질문수/전체 질문 수
			
			double average = Double.parseDouble(String.format("%.1f", averageAnswerTime));
			int answer = Integer.valueOf(String.valueOf(Math.round(answerRatio*QnaConstants.ROUND_PERCENT)));
			
			if(answer != 0){
				qnaStatistics.setAverageAnswerTime(average);
				qnaStatistics.setAnswerRatio(answer);
				qnaStatistics.setPortalId(portalId);
				
				qnaStatisticsDao.create(qnaStatistics);
			}
		}
		
	}

	

}
