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
import com.lgcns.ikep4.collpack.qna.model.Qna;
import com.lgcns.ikep4.collpack.qna.model.QnaPolicy;
import com.lgcns.ikep4.collpack.qna.service.QnaPolicyService;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


/**
 * BoardService 구현 클래스
 * 
 * @author 이주열(jooyoul.lee@lgcns.com)
 * @version $Id: QnaPolicyServiceImpl.java 16236 2011-08-18 02:48:22Z giljae $
 */
@Service("qnaPolicyService")
public class QnaPolicyServiceImpl extends GenericServiceImpl<QnaPolicy, String> implements QnaPolicyService {


	@Autowired
	private QnaPolicyDao qnaPolicyDao;
	
	
	@Autowired
	private QnaDao qnaDao;
	
	@Autowired
	private IdgenService idgenService;
	

	public String create(QnaPolicy qnaPolicy) {
		
		String id = idgenService.getNextId();
		
		qnaPolicy.setPolicyId(id);
		
		qnaPolicyDao.create(qnaPolicy);
		
		
		return id;
	}
	

	public List<QnaPolicy> listPolicy(String portalId) {
		return qnaPolicyDao.listPolicy(portalId);
	}
	
	
	public int readCount(String portalId) {
		return qnaPolicyDao.readCount(portalId);
	}


	public void update(QnaPolicy qnaPolicy) {
		qnaPolicyDao.update(qnaPolicy);
	}


	public void delete(String policyId) {
		qnaPolicyDao.remove(policyId);
		
	}


	public void applyBestQnaAndSaveScore() {
		
		List<String> potalIdList = qnaPolicyDao.listPotalId();
		
		for(String portalId : potalIdList){
			Qna qnaSearch = new Qna();
			
			qnaSearch.setPortalId(portalId);
			qnaSearch.setEndRowIndex(1000);
			qnaSearch.setStartRowIndex(Integer.parseInt(QnaConstants.BASE_NO));
			qnaSearch.setItemDelete(QnaConstants.ITEM_DELETE_NO);
			qnaSearch.setOrderType(QnaConstants.ORDER_TYPE_ANSWER);
			
			List<QnaPolicy> list = qnaPolicyDao.listPolicy(portalId);
		
			if(list.size() == 2){	//질문, 답글 정책이 다 있을때
				
				int qnaHitWeight 			= list.get(0).getHitWeight();			//질문 조회수
				int qnaLinereplyWeight 		= list.get(0).getLinereplyWeight();		//질문 댓글수
				int qnaAnswerWeight 		= list.get(0).getAnswerWeight();		//질문 답변수
				int qnaFavoriteWeight 		= list.get(0).getFavoriteWeight();		//질문 favorite 수
				int qnaAnswerSumWeight 		= list.get(0).getAnswerSumWeight();		//답변 점수 합
				int replyLinereplyWeight 	= list.get(1).getAnswerLinereplyWeight();//답변 댓글수
				int replyRecommendWeight 	= list.get(1).getAnswerRecommendWeight();	//답변 추천수
				int replyAnswerAdoptWeight 	= list.get(1).getAnswerAdoptWeight();	//답변 채택수
				
				qnaDao.updateScoreInit();		//score 초기화
				
				List<Qna> listQna = qnaDao.listAll(qnaSearch);
				
				int answerSum = 0;
				for(Qna qna : listQna){
					
					int score = 0;
					if(qna.getQnaType() == 0){	//질문 이면
						
						if(log.isDebugEnabled()){
							log.debug("hit : "+qnaHitWeight+":"+qna.getHitCount());
							log.debug("LinereplyCount : "+qnaLinereplyWeight+":"+qna.getLinereplyCount());
							log.debug("ReplyCount : "+qnaAnswerWeight+":"+qna.getReplyCount());
							log.debug("FavoriteCount : "+qnaFavoriteWeight+":"+qna.getFavoriteCount());
							log.debug("AnswerSumWeight : "+qnaAnswerSumWeight+":"+answerSum);
						}
						
						score = qna.getHitCount()*qnaHitWeight +qna.getLinereplyCount()*qnaLinereplyWeight + qna.getReplyCount()*qnaAnswerWeight + qna.getFavoriteCount()*qnaFavoriteWeight + answerSum*qnaAnswerSumWeight;
						answerSum = 0;
						if(log.isDebugEnabled()){
							log.debug("qnaId- qna : "+ qna.getQnaId()+", sum : "+score);
						}
					} else {
						
						if(log.isDebugEnabled()){
							log.debug("LinereplyCount : "+replyLinereplyWeight+":"+qna.getLinereplyCount());
							log.debug("RecommendWeight : "+replyRecommendWeight+":"+qna.getRecommendCount());
							log.debug("AnswerAdoptWeight : "+replyAnswerAdoptWeight+":"+ qna.getAnswerAdopt());
						}
						
						score = qna.getLinereplyCount()*replyLinereplyWeight + qna.getRecommendCount()*replyRecommendWeight + qna.getAnswerAdopt()*replyAnswerAdoptWeight;
						
						answerSum += score;
						if(log.isDebugEnabled()){
							log.debug("qnaId - answer : "+ qna.getQnaId()+", sum : "+score);
						}
					}
					
					qnaDao.updateScore(qna.getQnaId(), score);
				}
				
				//질문, 답글 베스트 글 선정
				qnaDao.updateBestFlag(QnaConstants.IS_QNA, list.get(0).getbestQnaBasisPoint(), portalId);
				qnaDao.updateBestFlag(QnaConstants.IS_REPLY, list.get(1).getbestAnswerBasisPoint(), portalId);
			}
		}
	}

}
