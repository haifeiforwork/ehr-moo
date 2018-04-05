/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.socialblog.service;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBoardItemRecommend;

/**
 * 소셜블로그 블로깅 추천 정보 Service Interface
 *
 * @author 이형운
 * @version $Id: SocialBoardItemRecommendService.java 16246 2011-08-18 04:48:28Z giljae $
 */
@Transactional
public interface SocialBoardItemRecommendService extends GenericService<SocialBoardItemRecommend, String> {

	/**
	 * 소셜 블로그 블로깅 추천정보 기록 
	 * 하루에 한번만 기록함
	 * @param socialBoardItemRecommend  블로그 블로깅 추천정보 객체
	 */
	public void registerRecommend(SocialBoardItemRecommend socialBoardItemRecommend);
	
	/**
	 * 소셜블로그 블로깅 추천 정보 존재 여부 확인
	 * @param socialBoardItemRecommend 블로그 블로깅 추천정보 객체
	 * @return 추천 정보 존재 유무
	 */
	public boolean exists(SocialBoardItemRecommend socialBoardItemRecommend);
	
	/**
	 * 소셜블로그 블로깅 추천 정보  삭제
	 * @param socialBoardItemRecommend 블로그 블로깅 추천정보 객체
	 */
	public void physicalDelete(SocialBoardItemRecommend socialBoardItemRecommend);
	
	/**
	 * 블로그 글에 추천 정보 확인
	 * @param socialBoardItemRecommend 블로그 블로깅 추천정보 객체
	 * @return 블로그 블로깅 추천정보 객체
	 */
	public SocialBoardItemRecommend get(SocialBoardItemRecommend socialBoardItemRecommend);
	
}
