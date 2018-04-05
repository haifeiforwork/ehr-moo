/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.forum.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.collpack.forum.model.FrSearch;
import com.lgcns.ikep4.collpack.forum.model.FrUserActivities;
import com.lgcns.ikep4.framework.core.service.GenericService;

/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: FrUserActivitiesService.java 16236 2011-08-18 02:48:22Z giljae $
 */
@Transactional
public interface FrUserActivitiesService extends GenericService<FrUserActivities, String>  {
	
	
	public FrUserActivities get(String userId);
	
	/**
	 * 활동 사용자 목록
	 * @param frSearch
	 * @return
	 */
	public List<FrUserActivities> list(FrSearch frSearch);
	
	/**
	 * 삭제
	 * @param userId
	 */
	public void remove(String userId);
	
	/**
	 * 사용자 활동 점수 계산하여 등록
	 *
	 */
	public void updateActivityScore();
	
	/**
	 * user 정보
	 * @param userId
	 * @return
	 */
	public FrUserActivities getUserInfo(String userId);
	
}
