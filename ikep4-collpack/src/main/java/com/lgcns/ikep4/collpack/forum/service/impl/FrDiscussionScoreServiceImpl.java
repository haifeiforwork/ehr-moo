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
import com.lgcns.ikep4.collpack.forum.dao.FrDiscussionScoreDao;
import com.lgcns.ikep4.collpack.forum.dao.FrItemDao;
import com.lgcns.ikep4.collpack.forum.dao.FrLinereplyDao;
import com.lgcns.ikep4.collpack.forum.dao.FrPolicyDao;
import com.lgcns.ikep4.collpack.forum.model.FrDiscussion;
import com.lgcns.ikep4.collpack.forum.model.FrDiscussionScore;
import com.lgcns.ikep4.collpack.forum.model.FrItem;
import com.lgcns.ikep4.collpack.forum.model.FrLinereply;
import com.lgcns.ikep4.collpack.forum.model.FrPolicy;
import com.lgcns.ikep4.collpack.forum.model.FrSearch;
import com.lgcns.ikep4.collpack.forum.service.FrDiscussionScoreService;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;


/**
 * BoardService 구현 클래스
 * 
 * @author 이주열(jooyoul.lee@lgcns.com)
 * @version $Id: FrDiscussionScoreServiceImpl.java 16236 2011-08-18 02:48:22Z giljae $
 */
@Service("frDiscussionScoreService")
public class FrDiscussionScoreServiceImpl extends GenericServiceImpl<FrDiscussionScore, String> implements FrDiscussionScoreService {


	@Autowired
	private FrDiscussionScoreDao frDiscussionScoreDao;
	
	@Autowired
	private FrPolicyDao frPolicyDao;
	
	@Autowired
	private FrItemDao frItemDao;
	
	@Autowired
	private FrLinereplyDao frLinereplyDao;
	
	@Autowired
	private FrDiscussionDao frDiscussionDao;
	
	@Override
	public String create(FrDiscussionScore object) {
		
		frDiscussionScoreDao.create(object);
		
		return null;
	}


	public void removeAll() {
		frDiscussionScoreDao.removeAll();
		
	}


	public void updateScore() {
		
		frDiscussionScoreDao.removeAll();
		
		//정책
		List<FrPolicy> policyList = frPolicyDao.list(ForumConstants.POLICY_POPULAR_DISCUSSION);
		
		//스코어 업데이트
		for(FrPolicy frPolicy : policyList){
			
			FrSearch frSearch = new FrSearch();
			frSearch.setPortalId(frPolicy.getPortalId());
			
			List<FrDiscussion> discussionList = frDiscussionDao.list(frSearch);
			
			for(FrDiscussion frDiscussion : discussionList){
				FrDiscussionScore frDiscussionScore = new FrDiscussionScore();
				frDiscussionScore.setDiscussionId(frDiscussion.getDiscussionId());
				
				FrSearch itemSearch = new FrSearch();
				itemSearch.setDiscussionId(frDiscussion.getDiscussionId());
				FrItem itemCountes = frItemDao.getCountes(itemSearch);
				
				FrLinereply lineCountes = frLinereplyDao.getCountes(itemSearch);
				
				int discussionScore = 0;
				if(itemCountes.getCount() > 0){
					discussionScore = frPolicy.getItemWeight()*itemCountes.getCount()
									+ frPolicy.getAgreementWeight()*(itemCountes.getAgreementCount()+itemCountes.getOppositionCount())
									+ frPolicy.getFavoriteWeight()*itemCountes.getFavoriteCount();
				}
				
				if(lineCountes.getCount() > 0){
					
					discussionScore += frPolicy.getRecommendWeight()*lineCountes.getRecommendCount()
									+ frPolicy.getLinereplyWeight()*lineCountes.getCount();
					
				}
										
				frDiscussionScore.setDiscussionScore(discussionScore);
				
				frDiscussionScoreDao.create(frDiscussionScore);
				
			}
			
			frDiscussionScoreDao.removeCount(frPolicy.getMaxCount());
		}
		
		
	}
	
	


}
