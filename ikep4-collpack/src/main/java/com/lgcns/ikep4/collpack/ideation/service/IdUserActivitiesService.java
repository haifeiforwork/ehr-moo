/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.ideation.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.collpack.ideation.model.IdSearch;
import com.lgcns.ikep4.collpack.ideation.model.IdUserActivities;

/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: IdUserActivitiesService.java 12238 2011-05-20 01:55:30Z loverfairy $
 */
@Transactional
public interface IdUserActivitiesService extends GenericService<IdUserActivities, String>  {
	
	
	public IdUserActivities get(String userId);
	
	/**
	 * 활동 사용자 목록
	 * @param idSearch
	 * @return
	 */
	public List<IdUserActivities> list(IdSearch idSearch);
	
	/**
	 * 삭제
	 * @param userId
	 */
	public void remove(String userId);
	

	/**
	 * 제안활동 점수 배치
	 *
	 */
	public void updateSuggestionScore();
	
	/**
	 * 기여 활동 점수 배치
	 *
	 */
	public void updateContributionScore();
	
	/**
	 * user 정보 
	 * @param userId
	 * @return
	 */
	public IdUserActivities getUserInfo(String userId);

}
