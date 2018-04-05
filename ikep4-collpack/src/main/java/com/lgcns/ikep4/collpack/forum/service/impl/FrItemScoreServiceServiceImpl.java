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
import com.lgcns.ikep4.collpack.forum.dao.FrItemDao;
import com.lgcns.ikep4.collpack.forum.dao.FrItemScoreDao;
import com.lgcns.ikep4.collpack.forum.dao.FrPolicyDao;
import com.lgcns.ikep4.collpack.forum.model.FrItem;
import com.lgcns.ikep4.collpack.forum.model.FrItemScore;
import com.lgcns.ikep4.collpack.forum.model.FrPolicy;
import com.lgcns.ikep4.collpack.forum.model.FrSearch;
import com.lgcns.ikep4.collpack.forum.service.FrItemScoreService;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;


/**
 * BoardService 구현 클래스
 * 
 * @author 이주열(jooyoul.lee@lgcns.com)
 * @version $Id: FrItemScoreServiceServiceImpl.java 16236 2011-08-18 02:48:22Z giljae $
 */
@Service("frItemScoreService")
public class FrItemScoreServiceServiceImpl extends GenericServiceImpl<FrItemScore, String> implements FrItemScoreService {


	@Autowired
	private FrItemScoreDao frItemScoreDao;
	
	@Autowired
	private FrPolicyDao frPolicyDao;
	
	@Autowired
	private FrItemDao frItemDao;
	
	

	public String create(FrItemScore FrItemScore) {
		
			
		frItemScoreDao.create(FrItemScore);
		
		return null;
	}



	public void removeAll() {
		frItemScoreDao.removeAll();
	}
	
	public void updateScore() {
		
		frItemScoreDao.removeAll();
		
		//정책
		List<FrPolicy> policyList = frPolicyDao.list(ForumConstants.POLICY_POPULAR_ITEM);

		frItemDao.updateBestItemInit();	//베스트 토론글 초기화
		//스코어 업데이트
		for(FrPolicy frPolicy : policyList){
			
			FrSearch frSearch = new FrSearch();
			frSearch.setPortalId(frPolicy.getPortalId());
			
			List<FrItem> itemList = frItemDao.list(frSearch);
			
			//우수 토론 정책
			FrPolicy bestPolicy = frPolicyDao.get(ForumConstants.POLICY_BEST , frPolicy.getPortalId());
			
			for(FrItem frItem : itemList){
				
				//토론글 스토어 저장
				FrItemScore frItemScore = new FrItemScore();
				frItemScore.setItemId(frItem.getItemId());
				
				int itemScore = frPolicy.getLinereplyWeight()*frItem.getLinereplyCount()
									+ frPolicy.getAgreementWeight()*(frItem.getAgreementCount()+frItem.getOppositionCount())
									+ frPolicy.getFavoriteWeight()*frItem.getFavoriteCount();
				
				frItemScore.setItemScore(itemScore);
				
				frItemScoreDao.create(frItemScore);
				
				
				//우수 토론글 선정
				if(frItem.getAgreementCount() >= bestPolicy.getAgreementWeight()){
					frItemDao.updateBestItem(frItem.getItemId());
				}
				
			}
			
			frItemScoreDao.removeCount(frPolicy.getMaxCount());
		}
		
		
		
	}
	

}
