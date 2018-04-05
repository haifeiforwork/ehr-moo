/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.socialblog.service;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBoardItem;
import com.lgcns.ikep4.socialpack.socialblog.search.SocialBoardItemSearchCondition;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 소셜블로그 게시글 관련 Service Interface
 *
 * @author 이형운
 * @version $Id: SocialBoardItemService.java 16246 2011-08-18 04:48:28Z giljae $
 */
@Transactional
public interface SocialBoardItemService extends GenericService<SocialBoardItem, String> {

	/**
	 * 검색 조건별 소셜 블로그 게시글 리스트 조회
	 * @param socialBoardItemSearchCondition 블로그 게시글 조회용 객체
	 * @return 블로그 글 조회 결과 리스트
	 */
	public SearchResult<SocialBoardItem> listSocialBoardItemBySearchCondition(SocialBoardItemSearchCondition socialBoardItemSearchCondition) throws JsonGenerationException, JsonMappingException, IOException;
	
	/**
	 * 검색 조건별 소셜 블로그 게시글 리스트 조회 건수
	 * @param socialBoardItemSearchCondition 블로그 게시글 조회용 객체
	 * @return 블로그 글 조회 건수
	 */
	public Integer countSocialBoardItemBySearchCondition(SocialBoardItemSearchCondition socialBoardItemSearchCondition);
	
	/**
	 * 해당 아이템 아이디에 해당 하는 포스팅 조회
	 * @param itemId 블로그 아이템 Id
	 * @return 블로그 게시글  객체
	 */
	public SocialBoardItem readSocialBoardItem(String itemId, User sessionUser) throws JsonGenerationException, JsonMappingException, IOException;

	/**
	 * 블로그 포스팅 글 작성 
	 * @param socialBoardItem 블로그 게시글  객체
	 */
	public String createSocialBoardItem(SocialBoardItem socialBoardItem, User user) ;

	/**
	 * 블로그 포스팅 글 작성
	 * @param socialBoardItem 블로그 게시글  객체
	 */
	void updateSocialBoardItem(SocialBoardItem socialBoardItem, User user) ;

	/**
	 * 관리 권한 해당 포스팅 삭제
	 * @param socialBoardItem 블로그 게시글  객체
	 */
	void adminDeleteSocialBoardItem(SocialBoardItem socialBoardItem);

	/**
	 * 작성자가 해당 포스팅 삭제
	 * @param socialBoardItem 블로그 게시글  객체
	 */
	void userDeleteSocialBoardItem(SocialBoardItem socialBoardItem);

    /**
     * 소셜블로그 게시글 추천 수 업데이트 
     * @param itemId 블로그 아이템 Id
     */
	public void updateRecommendCount(String itemId);

	/**
	 * 소셜블로그 게시글 댓글 수  업데이트 
	 * @param itemId 블로그 아이템 Id
	 */
	public void updateLinereplyCount(String itemId);

	/**
	 * 소셜블로그 게시글 메일 발송 수 업데이트 
	 * @param itemId 블로그 아이템 Id
	 */
	public void updateMailCount(String itemId);
	
	/**
	 * 소셜블로그 게시글 마이크로블로그 게시 수  업데이트 
	 * @param itemId 블로그 아이템 Id
	 */
	public void updateMBlogCount(String itemId);

	/**
	 * 소셜블로그 게시글 마이크로블로그 조회수 
	 * @param itemId 블로그 아이템 Id
	 */
	public void updateHitCount(String itemId);
	
	/**
	 * 소셜 블로그의 포스팅된 날짜 목록 반환 (yyyy.MM.dd)
	 * @param socialBoardItemSearchCondition 블로그 게시글 조회용 객체
	 * @return 블로그 포스팅 일자 String 리스트
	 */
	public List<String> selectPostingDate(SocialBoardItemSearchCondition socialBoardItemSearchCondition);


}
