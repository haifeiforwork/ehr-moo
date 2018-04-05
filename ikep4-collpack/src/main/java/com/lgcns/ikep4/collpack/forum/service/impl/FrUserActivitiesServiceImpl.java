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
import com.lgcns.ikep4.collpack.forum.dao.FrPolicyDao;
import com.lgcns.ikep4.collpack.forum.dao.FrUserActivitiesDao;
import com.lgcns.ikep4.collpack.forum.model.FrPolicy;
import com.lgcns.ikep4.collpack.forum.model.FrSearch;
import com.lgcns.ikep4.collpack.forum.model.FrUserActivities;
import com.lgcns.ikep4.collpack.forum.service.FrUserActivitiesService;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;


/**
 * BoardService 구현 클래스
 * 
 * @author 이주열(jooyoul.lee@lgcns.com)
 * @version $Id: FrUserActivitiesServiceImpl.java 16236 2011-08-18 02:48:22Z giljae $
 */
@Service("frUserActivitiesService")
public class FrUserActivitiesServiceImpl extends GenericServiceImpl<FrUserActivities, String> implements FrUserActivitiesService {


	@Autowired
	private FrUserActivitiesDao frUserActivitiesDao;
	
	@Autowired
	private FrPolicyDao frPolicyDao;
	

	public String create(FrUserActivities frUserActivities) {
		
		frUserActivitiesDao.create(frUserActivities);
		
		return null;
	}
	
	
	
	public FrUserActivities get(String userId) {
		return frUserActivitiesDao.get(userId);
	}





	public List<FrUserActivities> list(FrSearch frSearch) {
		return frUserActivitiesDao.list(frSearch);
	}



	public void remove(String userId) {
		
		frUserActivitiesDao.remove(userId);
		
	}



	public void updateActivityScore() {
		
		
		//정책
		List<FrPolicy> policyList = frPolicyDao.list(ForumConstants.POLICY_ACTIVITY);
		
		//스코어 업데이트
		for(FrPolicy frPolicy : policyList){
			
			//회원정보
			FrSearch scSearch = new FrSearch();
			scSearch.setPortalId(frPolicy.getPortalId());
			List<FrUserActivities> activitylist = frUserActivitiesDao.list(scSearch);
			
			for(FrUserActivities frUserActivities : activitylist){
				
				FrUserActivities frUserActivitiesScore = new FrUserActivities();
				
				frUserActivitiesScore.setUserId(frUserActivities.getUserId());
				
				int discussionScore = (frPolicy.getDiscussionWeight()*frUserActivities.getDiscussionCount()
										+ frPolicy.getItemWeight()*frUserActivities.getItemCount()
										+ frPolicy.getLinereplyWeight()*frUserActivities.getLinereplyCount()
										+ frPolicy.getBestItemWeight()*frUserActivities.getBestItemCount()
										+ frPolicy.getBestLinereplyWeight()*frUserActivities.getBestLinereplyCount()
										)*frPolicy.getParticipationWeight() +
										(frPolicy.getFavoriteWeight()*frUserActivities.getFavoriteCount()
										+ frPolicy.getAgreementWeight()*frUserActivities.getAgreementCount()
										+ frPolicy.getRecommendWeight()*frUserActivities.getRecommendCount()
										)*frPolicy.getFeedbackWeight();
				
				if(log.isDebugEnabled()){
					log.debug("userId; "+frUserActivities.getUserId()+", score: "+discussionScore);
				}
				
				frUserActivitiesScore.setDiscussionScore(discussionScore);
				
				frUserActivitiesDao.update(frUserActivitiesScore);
				
			}
		}
		
		//랭킹 업데이트
		for(FrPolicy frPolicy : policyList){
			
			FrSearch frSearch = new FrSearch();
			frSearch.setPortalId(frPolicy.getPortalId());
			frSearch.setIsBest(ForumConstants.BEST_TYPE);
			List<FrUserActivities> rankActivityList = frUserActivitiesDao.list(frSearch);
			
			
			int index = 0;
			for(FrUserActivities frUserActivities : rankActivityList){
				
				FrUserActivities frUserActivitiesRank = new FrUserActivities();
				frUserActivitiesRank.setUserId(frUserActivities.getUserId());
				frUserActivitiesRank.setDiscussionRank(++index);
				
				frUserActivitiesDao.update(frUserActivitiesRank);
			}
		}
		
		
	}
	
	public FrUserActivities getUserInfo(String userId) {
		return frUserActivitiesDao.getUserInfo(userId);
	}

}
