/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.forum.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.collpack.forum.constants.ForumConstants;
import com.lgcns.ikep4.collpack.forum.dao.FrDiscussionDao;
import com.lgcns.ikep4.collpack.forum.dao.FrLinereplyDao;
import com.lgcns.ikep4.collpack.forum.dao.FrParticipantDao;
import com.lgcns.ikep4.collpack.forum.dao.FrRecommendDao;
import com.lgcns.ikep4.collpack.forum.dao.FrUserActivitiesDao;
import com.lgcns.ikep4.collpack.forum.model.FrLinereply;
import com.lgcns.ikep4.collpack.forum.model.FrRecommend;
import com.lgcns.ikep4.collpack.forum.model.FrSearch;
import com.lgcns.ikep4.collpack.forum.service.FrRecommendService;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;


/**
 * BoardService 구현 클래스
 * 
 * @author 이주열(jooyoul.lee@lgcns.com)
 * @version $Id: FrRecommendServiceImpl.java 16236 2011-08-18 02:48:22Z giljae $
 */
@Service("frRecommendService")
public class FrRecommendServiceImpl extends GenericServiceImpl<FrRecommend, String> implements FrRecommendService {


	@Autowired
	private FrRecommendDao frRecommendDao;
	
	@Autowired
	private FrLinereplyDao frLinereplyDao;
	
	@Autowired
	private FrUserActivitiesDao frUserActivitiesDao;
	
	@Autowired
	private FrParticipantDao frParticipantDao;
	
	@Autowired
	private FrDiscussionDao frDiscussionDao;
	
	

	public boolean create(String linereplyId, String registerId, String discussionId) {
		
		boolean result = false;
		boolean exists = frRecommendDao.exists(linereplyId, registerId);
		
		if(!exists){	//존재 하는지 검사
			
			frRecommendDao.create(linereplyId, registerId);
			
			
			//토론의견 추천 업데이트
			frLinereplyDao.updateRecommendCount(linereplyId);
			
			//사용자활동 이력 업데이트
			FrLinereply frLinereply = frLinereplyDao.get(linereplyId);
			frUserActivitiesDao.updateRecommendCount(frLinereply.getRegisterId());
			
			//참가자 등록
			boolean participantExists = frParticipantDao.exists(discussionId, ForumConstants.PARTICIPATION_RECOMMEND_CREATE, registerId);
			
			if(!participantExists){
				frParticipantDao.create(discussionId, ForumConstants.PARTICIPATION_RECOMMEND_CREATE, registerId);
			
				frDiscussionDao.updateParticipationCount(discussionId);
			
			} 
			
			result = true;
		}
		
		return result;
	}
	
	
	public boolean exists(String linereplyId, String registerId) {
		return frRecommendDao.exists(linereplyId, registerId);
	}
	
	
	public List<FrRecommend> list(String linereplyId) {
		return frRecommendDao.list(linereplyId);
	}


	public void remove(String linereplyId, String registerId, String discussionId) {
		
		
		//추천삭제
		frRecommendDao.remove(linereplyId, registerId);
		
		//참가자 이력 삭제
		FrSearch frSearch = new FrSearch();
		frSearch.setDiscussionId(discussionId);
		frSearch.setUserId(discussionId);
		
		int itemCount = frRecommendDao.getCountByUserId(registerId);
		
		if(itemCount == 0){
			frParticipantDao.remove(discussionId, ForumConstants.PARTICIPATION_ITEM_CREATE, registerId);
		
			frDiscussionDao.updateParticipationCount(discussionId);
		}
		
		//사용자활동 이력 업데이트
		frUserActivitiesDao.updateRecommendCount(registerId);
		
	}
	

}
