/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.socialblog.dao;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBoardItemRecommend;

/**
 * 소셜블로그 블로깅 추천 정보 DAO Interface
 *
 * @author 이형운
 * @version $Id: SocialBoardItemRecommendDao.java 16246 2011-08-18 04:48:28Z giljae $
 */
public interface SocialBoardItemRecommendDao extends GenericDao<SocialBoardItemRecommend, String> {

	/**
	 * 소셜블로그 블로깅 추천 정보 존재 여부 확인
	 * @param socialBoardItemRecommend 블로그 게시글 추천인 정보
	 * @return 블로그 글 추천 정보 존재 여부
	 */
	public boolean exists(SocialBoardItemRecommend socialBoardItemRecommend);
	
	/**
	 * 소셜블로그 블로깅 추천 정보  삭제
	 * @param socialBoardItemRecommend 블로그 게시글 추천인 정보
	 */
	public void physicalDelete(SocialBoardItemRecommend socialBoardItemRecommend);
	
	/**
	 * 블로그 글에 추천 정보 확인
	 * @param socialBoardItemRecommend 블로그 게시글 추천인 정보
	 * @return 해당 블로그 게시글 추천 정보 
	 */
	public SocialBoardItemRecommend get(SocialBoardItemRecommend socialBoardItemRecommend);
}
