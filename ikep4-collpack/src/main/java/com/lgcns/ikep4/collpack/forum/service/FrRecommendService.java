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

import com.lgcns.ikep4.collpack.forum.model.FrRecommend;
import com.lgcns.ikep4.framework.core.service.GenericService;

/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: FrRecommendService.java 16236 2011-08-18 02:48:22Z giljae $
 */
@Transactional
public interface FrRecommendService extends GenericService<FrRecommend, String>  {
	
	/**
	 * 추천 등록
	 * @param linereplyId
	 * @param registerId
	 * @param discussionId
	 * @return
	 */
	public boolean create(String linereplyId, String registerId, String discussionId) ;
	
	/**
	 * 존재 여부
	 * @param linereplyId
	 * @param registerId
	 * @return
	 */
	public boolean exists(String linereplyId, String registerId);
	
	/**
	 * 의견 추천 리스트
	 * @param linereplyId
	 * @return
	 */
	public List<FrRecommend> list(String linereplyId);
	
	
	/**
	 * 사용자 추천 삭제
	 * @param linereplyId
	 * @param registerId
	 */
	public void remove(String linereplyId, String registerId, String discussionId);
	
	
	
}
