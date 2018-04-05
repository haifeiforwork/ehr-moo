/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.ideation.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.collpack.ideation.constants.IdeationConstants;
import com.lgcns.ikep4.collpack.ideation.dao.IdPolicyDao;
import com.lgcns.ikep4.collpack.ideation.dao.IdUserActivitiesDao;
import com.lgcns.ikep4.collpack.ideation.model.IdPolicy;
import com.lgcns.ikep4.collpack.ideation.model.IdSearch;
import com.lgcns.ikep4.collpack.ideation.model.IdUserActivities;
import com.lgcns.ikep4.collpack.ideation.service.IdIdeaService;
import com.lgcns.ikep4.collpack.ideation.service.IdUserActivitiesService;


/**
 * BoardService 구현 클래스
 * 
 * @author 이주열(jooyoul.lee@lgcns.com)
 * @version $Id: IdUserActivitiesServiceImpl.java 12460 2011-05-20 09:48:52Z loverfairy $
 */
@Service("idUserActivitiesService")
public class IdUserActivitiesServiceImpl extends GenericServiceImpl<IdUserActivities, String> implements IdUserActivitiesService {


	@Autowired
	private IdUserActivitiesDao idUserActivitiesDao;
	
	@Autowired
	private IdPolicyDao idPolicyDao;
	
	@Autowired
	private IdIdeaService idIdeaService;
	

	public String create(IdUserActivities idUserActivities) {
		
		idUserActivitiesDao.create(idUserActivities);
		
		return null;
	}
	
	
	
	public IdUserActivities get(String userId) {
		return idUserActivitiesDao.get(userId);
	}





	public List<IdUserActivities> list(IdSearch idSearch) {
		return idUserActivitiesDao.list(idSearch);
	}



	public void remove(String userId) {
		
		idUserActivitiesDao.remove(userId);
		
	}


	public void updateSuggestionScore(){
		updateSuggestionScoreAction(IdeationConstants.POLICY_SUGGESTION);
	}
	
	public void updateContributionScore(){
		updateSuggestionScoreAction(IdeationConstants.POLICY_CONTRIBUTION);
	}

	public void updateSuggestionScoreAction(String policyType) {
		
		
		//정책
		List<IdPolicy> policyList = idPolicyDao.list(policyType);
		
		//스코어 업데이트
		for(IdPolicy idPolicy : policyList){
			
			//회원정보
			IdSearch scSearch = new IdSearch();
			scSearch.setPortalId(idPolicy.getPortalId());
			List<IdUserActivities> activitylist = idUserActivitiesDao.list(scSearch);
			
			for(IdUserActivities idUserActivities : activitylist){
				
				IdUserActivities idUserActivitiesScore = new IdUserActivities();
				
				idUserActivitiesScore.setUserId(idUserActivities.getUserId());
				
				int score = 0;
				
				
				if(policyType.equals(IdeationConstants.POLICY_SUGGESTION)){		//제안활동점수
					
					score = idPolicy.getSuggestionWeight()*idUserActivities.getItemCount()			//제안건수
							+ idPolicy.getRecommendWeight()*idUserActivities.getRecommendItemCount()//추천건수
							+ idPolicy.getAdoptWeight()*idUserActivities.getAdoptItemCount()		//채택건수
							+ idPolicy.getBestWeight()*idUserActivities.getBestItemCount()			//우수건수
							+ idPolicy.getBusinessWeight()*idUserActivities.getBusinessItemCount();	//사업화 건수
					
					idUserActivitiesScore.setSuggestionScore(score);
				} else {	//기여활동점수
					
					
					//내가 즐겨찾기한 개수
					int favoriteCount = idIdeaService.getFavorite(idUserActivities.getUserId(), IKepConstant.ITEM_TYPE_CODE_IDEATION);
					
					score = idPolicy.getRecommendWeight()*idUserActivities.getRecommendCount()		//추천수
							+ idPolicy.getAdoptWeight()*idUserActivities.getAdoptCount()			//채택수
							+ idPolicy.getLinereplyWeight()*idUserActivities.getLinereplyCount()	//댓글수
							+ idPolicy.getFavoriteWeight()*favoriteCount;		//즐겨찾기한 건수
			
					idUserActivitiesScore.setContributionScore(score);
				}
				
				if(log.isDebugEnabled()){
					log.debug("userId; "+idUserActivities.getUserId()+", score: "+score);
				}
				
				idUserActivitiesDao.update(idUserActivitiesScore);
				
			}
			
			
			//랭킹 업데이트
			IdSearch idSearch = new IdSearch();
			idSearch.setPortalId(idPolicy.getPortalId());
			if(policyType.equals(IdeationConstants.POLICY_SUGGESTION)){		//제안활동점수
				idSearch.setIsSuggestion(IdeationConstants.IS_SUGGESTION);
			} else {//기여활동점수
				idSearch.setIsContribution(IdeationConstants.IS_CONTRIBUTION);
			}
			
			
			List<IdUserActivities> rankActivityList = idUserActivitiesDao.list(idSearch);
			
			int index = 0;
			for(IdUserActivities idUserActivities : rankActivityList){
				
				IdUserActivities idUserActivitiesRank = new IdUserActivities();
				idUserActivitiesRank.setUserId(idUserActivities.getUserId());
				
				if(policyType.equals(IdeationConstants.POLICY_SUGGESTION)){		//제안활동점수
					idUserActivitiesRank.setSuggestionRank(++index);
				
				} else {//기여활동점수
					idUserActivitiesRank.setContributionRank(++index);
				}
				
				idUserActivitiesDao.update(idUserActivitiesRank);
			}
			
		}
		
	}



	public IdUserActivities getUserInfo(String userId) {
		return idUserActivitiesDao.getUserInfo(userId);
	}
	
	
}
