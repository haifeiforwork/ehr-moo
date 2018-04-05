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
import com.lgcns.ikep4.collpack.ideation.model.IdRecommend;

/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: IdRecommendService.java 7008 2011-04-21 02:07:44Z loverfairy $
 */
@Transactional
public interface IdRecommendService extends GenericService<IdRecommend, String>  {
	
	/**
	 * 추천 등록
	 * @param linereplyId
	 * @param registerId
	 * @return
	 */
	public boolean create(String linereplyId, String registerId) ;
	
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
	public List<IdRecommend> list(String linereplyId);
	
	
	/**
	 * 사용자 추천 삭제
	 * @param linereplyId
	 * @param registerId
	 */
	public void remove(String linereplyId, String registerId);
	
	
	
}
