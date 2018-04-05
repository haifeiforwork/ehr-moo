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

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.collpack.ideation.constants.IdeationConstants;
import com.lgcns.ikep4.collpack.ideation.dao.IdIdeaDao;
import com.lgcns.ikep4.collpack.ideation.dao.IdRecommendDao;
import com.lgcns.ikep4.collpack.ideation.dao.IdUserActivitiesDao;
import com.lgcns.ikep4.collpack.ideation.model.IdIdea;
import com.lgcns.ikep4.collpack.ideation.model.IdRecommend;
import com.lgcns.ikep4.collpack.ideation.model.IdUserActivities;
import com.lgcns.ikep4.collpack.ideation.service.IdRecommendService;


/**
 * BoardService 구현 클래스
 * 
 * @author 이주열(jooyoul.lee@lgcns.com)
 * @version $Id: IdRecommendServiceImpl.java 12460 2011-05-20 09:48:52Z loverfairy $
 */
@Service("idRecommendService")
public class IdRecommendServiceImpl extends GenericServiceImpl<IdRecommend, String> implements IdRecommendService {


	@Autowired
	private IdRecommendDao idRecommendDao;
	
	@Autowired
	private IdIdeaDao idIdeaDao;
	
	@Autowired
	private IdUserActivitiesDao idUserActivitiesDao;
	
	

	public boolean create(String itemId, String registerId) {
		
		boolean result = false;
		boolean exists = idRecommendDao.exists(itemId, registerId);
		
		if(!exists){	//존재 하는지 검사
			
			idRecommendDao.create(itemId, registerId);
			
			//토론의견 추천 업데이트
			idIdeaDao.updateRecommendCount(itemId);
			
			//사용자활동 이력 업데이트
			boolean activityExists = idUserActivitiesDao.exists(registerId);
			
			IdUserActivities idUserActivities = new IdUserActivities();
			idUserActivities.setUserId(registerId);
			idUserActivities.setRecommendItemCount(IdeationConstants.NUM_INCRASE);
			
			if(activityExists){
				//추천한 아이디어 개수 수정
				idUserActivitiesDao.updateRecommendCount(registerId);
			} else {
				idUserActivitiesDao.create(idUserActivities);
			}
			
			//추천된 아이디어 개수 수정
			IdIdea idIdea = idIdeaDao.get(itemId);
			idUserActivitiesDao.updateRecommendItemCount(idIdea.getRegisterId());
			
			if(idIdea.getRecommendItem() == 0){ //추천아이디어 지정
				idIdeaDao.updateRecommendItem(itemId, IdeationConstants.NUM_INCRASE);
			}
			
			result = true;
		}
		
		return result;
	}
	
	
	public boolean exists(String itemId, String registerId) {
		return idRecommendDao.exists(itemId, registerId);
	}
	
	
	public List<IdRecommend> list(String itemId) {
		return idRecommendDao.list(itemId);
	}


	public void remove(String itemId, String registerId) {
		
		
		//추천삭제
		idRecommendDao.remove(itemId, registerId);
		
		
		//사용자활동 이력 업데이트
		IdUserActivities idUserActivities = new IdUserActivities();
		idUserActivities.setUserId(registerId);
		idUserActivities.setRecommendItemCount(IdeationConstants.NUM_DECREASE);
		
		idUserActivitiesDao.updateIncrease(idUserActivities);
		
		IdIdea idIdea = idIdeaDao.get(itemId);
		if(idIdea.getRecommendCount() == 0){ //추천아이디어 지정삭제
			idIdeaDao.updateRecommendItem(itemId, IdeationConstants.NUM_DECREASE);
		}
	}
	

}
