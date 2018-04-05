/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.forum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.collpack.forum.constants.ForumConstants;
import com.lgcns.ikep4.collpack.forum.dao.FrDiscussionDao;
import com.lgcns.ikep4.collpack.forum.dao.FrFeedbackDao;
import com.lgcns.ikep4.collpack.forum.dao.FrItemDao;
import com.lgcns.ikep4.collpack.forum.dao.FrParticipantDao;
import com.lgcns.ikep4.collpack.forum.dao.FrUserActivitiesDao;
import com.lgcns.ikep4.collpack.forum.model.FrFeedback;
import com.lgcns.ikep4.collpack.forum.model.FrItem;
import com.lgcns.ikep4.collpack.forum.model.FrSearch;
import com.lgcns.ikep4.collpack.forum.model.FrUserActivities;
import com.lgcns.ikep4.collpack.forum.service.FrFeedbackService;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;


/**
 * BoardService 구현 클래스
 * 
 * @author 이주열(jooyoul.lee@lgcns.com)
 * @version $Id: FrFeedbackServiceImpl.java 16236 2011-08-18 02:48:22Z giljae $
 */
@Service("frFeedbackService")
public class FrFeedbackServiceImpl extends GenericServiceImpl<FrFeedback, String> implements FrFeedbackService {


	@Autowired
	private FrFeedbackDao frFeedbackDao;
	
	@Autowired
	private FrItemDao frItemDao;
	
	@Autowired
	private FrUserActivitiesDao frUserActivitiesDao;
	
	@Autowired
	private FrParticipantDao frParticipantDao;
	
	@Autowired
	private FrDiscussionDao frDiscussionDao;
	
	
	
	public boolean createAgreement(String itemId, String registerId, String discussionId) {
		
		return create(itemId, ForumConstants.FEEDBACK_AGREEMENT , registerId, discussionId) ;
	}


	public boolean createOpposition(String itemId, String registerId, String discussionId) {
		return create(itemId, ForumConstants.FEEDBACK_OPPOSITION, registerId, discussionId) ;
	}
	

	public void removeAgreement(String itemId, String registerId, String discussionId) {
		remove(itemId, ForumConstants.FEEDBACK_AGREEMENT, registerId, discussionId);
		
	}


	public void removeOpposition(String itemId, String registerId, String discussionId) {
		remove(itemId, ForumConstants.FEEDBACK_OPPOSITION, registerId, discussionId);
	}

	public boolean exists(String itemId, String feedbackType, String registerId) {
		return frFeedbackDao.exists(itemId, feedbackType, registerId);
	}

	
	/**
	 * 찬성, 반대 공통 사용
	 * @param itemId
	 * @param feedbackType
	 * @param registerId
	 * @param discussionId
	 * @return
	 */
	public boolean create(String itemId, String feedbackType , String registerId, String discussionId) {
		
		boolean result = false;
		boolean exists = frFeedbackDao.exists(itemId, feedbackType, registerId);
		
		if(!exists){	//존재 하는지 검사
			FrFeedback frFeedback = new FrFeedback();
			frFeedback.setItemId(itemId);
			frFeedback.setFeedbackType(feedbackType);
			frFeedback.setRegisterId(registerId);
			
			frFeedbackDao.create(frFeedback);
			result = true;
			
			//토론글 피드백 업데이트
			if(feedbackType.equals(ForumConstants.FEEDBACK_AGREEMENT)){
				frItemDao.updateAgreementCount(itemId);
			} else {
				frItemDao.updateOppositionCount(itemId);
			}
			
			//사용자활동 이력 업데이트
			boolean activityExists = frUserActivitiesDao.exists(registerId);
			
			FrUserActivities frUserActivities = new FrUserActivities();
			frUserActivities.setUserId(registerId);
			
			if(feedbackType.equals(ForumConstants.FEEDBACK_AGREEMENT)){
				frUserActivities.setAgreementCount(ForumConstants.NUM_INCRASE);
			} else {
				frUserActivities.setOppositionCount(ForumConstants.NUM_INCRASE);
			}
			
			if(activityExists){
				if(feedbackType.equals(ForumConstants.FEEDBACK_AGREEMENT)){
					frUserActivitiesDao.updateAgreementCount(registerId);
				} else {
					frUserActivitiesDao.updateOppositionCount(registerId);
				}
				
			} else {
				frUserActivitiesDao.create(frUserActivities);
				
			}
			
			//참가자 등록
			String participationType = (feedbackType.equals(ForumConstants.FEEDBACK_AGREEMENT))?  ForumConstants.PARTICIPATION_ITEM_AGREE : ForumConstants.PARTICIPATION_ITEM_OPPO;
			
			boolean participantExists = frParticipantDao.exists(discussionId, participationType, registerId);
			
			if(!participantExists){
				frParticipantDao.create(discussionId, participationType, registerId);
			
				frDiscussionDao.updateParticipationCount(discussionId);
			
			} 
		}
		
		return result;
		
	}


	/**
	 * 삭제 공통
	 * @param itemId
	 * @param feedbackType
	 * @param registerId
	 * @param discussionId
	 */
	public void remove(String itemId, String feedbackType, String registerId, String discussionId) {
		
		
		//토론글  등록 이력 재등록
		FrUserActivities frUserActivities = new FrUserActivities();
		
		//토론주제 같은 사용자가 등록한 토론글 개수
		FrSearch frSearch = new FrSearch();
		frSearch.setUserId(registerId);
		FrItem itemCountes = frItemDao.getCountes(frSearch);
		
		frUserActivities.setItemCount(itemCountes.getCount());
		frUserActivities.setAgreementCount(itemCountes.getAgreementCount());
		frUserActivities.setOppositionCount(itemCountes.getOppositionCount());
		frUserActivities.setBestItemCount(itemCountes.getBestCount());
		frUserActivities.setFavoriteCount(itemCountes.getFavoriteCount());
		
		frUserActivitiesDao.update(frUserActivities);
			
		//토론글 피드 삭제
		frFeedbackDao.remove(itemId, feedbackType, registerId);
		
		
		//참가자 이력 삭제
		int itemCount = frFeedbackDao.getCountByUserIdAsType(feedbackType, registerId);
		
		if(itemCount == 0){
			frParticipantDao.remove(discussionId, ForumConstants.PARTICIPATION_ITEM_CREATE, registerId);
		
			frDiscussionDao.updateParticipationCount(discussionId);
		}
		
	}
	

}
