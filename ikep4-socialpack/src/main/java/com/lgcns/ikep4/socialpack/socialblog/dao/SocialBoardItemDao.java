/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.socialblog.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBoardItem;
import com.lgcns.ikep4.socialpack.socialblog.search.SocialBoardItemSearchCondition;

/**
 * 소셜블로그 게시글 관련 DAO Interface
 *
 * @author 이형운
 * @version $Id: SocialBoardItemDao.java 16246 2011-08-18 04:48:28Z giljae $
 */
public interface SocialBoardItemDao extends GenericDao<SocialBoardItem, String> {
	
	/**
	 * 검색 조건별 소셜 블로그 게시글 리스트 조회
	 * @param socialBoardItemSearchCondition 블로그 게시글 조회용 정보
	 * @return 블로그 게시글 목록
	 */
	public List<SocialBoardItem> listBySearchCondition(SocialBoardItemSearchCondition socialBoardItemSearchCondition);
	
	/**
	 * 검색 조건별 소셜 블로그 게시글 리스트 조회 수
	 * @param socialBoardItemSearchCondition 블로그 게시글 조회용 정보
	 * @return 블로그 게시글 수 
	 */
	public Integer countBySearchCondition(SocialBoardItemSearchCondition socialBoardItemSearchCondition);
	
	/**
	 * 블로그 포스팅 글 작성 
	 * @param socialBoardItem 블로그 게시글 정보
	 */
	public String create(SocialBoardItem socialBoardItem);

	/**
	 * 소셜블로그 게시글 물리적 삭제
	 * @param itemId 블로그 게시글 ID
	 */
	public void physicalDelete(String itemId);
    
    /**
     * 소셜블로그 게시글 추천 수 조회
     * @param itemId 블로그 게시글 ID
     */
	public void updateRecommendCount(String itemId);
	
	/**
	 * 소셜블로그 게시글 댓글 수 조회
	 * @param itemId 블로그 게시글 ID
	 */
	public void updateLinereplyCount(String itemId);
	
	/**
	 * 소셜블로그 게시글 메일 발송 수 조회
	 * @param itemId 블로그 게시글 ID
	 */
	public void updateMailCount(String itemId);
	
	/**
	 * 소셜블로그 게시글 마이크로블로그 게시 수 조회
	 * @param itemId 블로그 게시글 ID
	 */
	public void updateMBlogCount(String itemId);
	
	/**
	 * 소셜블로그 게시글 조회 수 조회
	 * @param itemId 블로그 게시글 ID
	 */
	public void updateHitCount(String itemId);
	
	/**
	 * 소셜블로그 게시글 단건 조회
	 * @param socialBoardItemSearchCondition
	 * @return
	 */
	public SocialBoardItem get(SocialBoardItem socialBoardItem);
	
	/**
	 * 소셜 블로그의 포스팅된 날짜 목록 반환 (yyyy.MM.dd)
	 * @param socialBoardItemSearchCondition 블로그 게시글 조회용 정보
	 * @return 포스팅된 블로그 날짜 리스트
	 */
	public List<String> selectPostingDate(SocialBoardItemSearchCondition socialBoardItemSearchCondition);


}
