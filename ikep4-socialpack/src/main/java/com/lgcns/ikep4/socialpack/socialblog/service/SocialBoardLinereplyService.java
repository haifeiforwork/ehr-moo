/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.socialblog.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBoardLinereply;
import com.lgcns.ikep4.socialpack.socialblog.search.SocialBoardLinereplySearchCondition;

/**
 * 소셜 블로그 댓글 관련 Service Interface
 *
 * @author 이형운
 * @version $Id: SocialBoardLinereplyService.java 16246 2011-08-18 04:48:28Z giljae $
 */
@Transactional
public interface SocialBoardLinereplyService extends GenericService<SocialBoardLinereply, String> {

	
	/**
	 * 소셜블로그 댓글 목록 조회
	 * @param socialBoardLinereplySearchCondition 블로그 댓글 조회용 객체
	 * @return 블로그 댓글 리스트
	 */
	public SearchResult<SocialBoardLinereply> listSocialBoardLinereplyBySearchCondition(SocialBoardLinereplySearchCondition searchCondition); 
	
	/**
	 * 소셜블로그 댓글 내용 조회
	 * @param linereplyId 블로그 댓글 ID
	 * @return 블로그 댓글 객체
	 */
	public SocialBoardLinereply readSocialBoardLinereply(String linereplyId);
	
	/**
	 * 소셜블로그 댓글 내용 작성
	 * @param socialBoardLinereply 블로그 댓글 객체
	 * @return 저장 결과 값
	 */
	public String createSocialBoardLinereply(SocialBoardLinereply socialBoardLinereply);
	
	/**
	 * 소셜 블로그 댓글에 댓글 내용 작성 
	 * @param socialBoardLinereply 블로그 댓글 객체
	 * @return 저장 결과 값
	 */
	public String createReplySocialBoardLinereply(SocialBoardLinereply socialBoardLinereply); 
	
	/**
	 *  소셜 블로그 댓글 수정
	 * @param socialBoardLinereply 블로그 댓글 객체
	 */
	public void updateSocialBoardLinereply(SocialBoardLinereply socialBoardLinereply);
	
	/**
	 * 소셜 블로그 관리자 삭제
	 * @param itemId 블로그 아이템 ID
	 * @param linereplyId 블로그 댓글 ID
	 */
	public void adminDeleteSocialBoardLinereply(String itemId, String linereplyId); 
	  
	/**
	 * 소셜 블로그 사용자 글 삭제
	 * @param socialBoardLinereply 블로그 댓글 객체
	 */
	public void userDeleteSocialBoardLinereply(SocialBoardLinereply socialBoardLinereply);
	
	/**
	 * 최근에 작성된 댓글 리스트 가져 온다.
	 * @param topXList 최근 작성된 댓글 목록 조건 blogOwnerId 대상 블로그 ID, rowTopXCount 가져올 건수
	 * @return 최근 작성된 댓글 리스트
	 */
	public List<SocialBoardLinereply> listTopXLinereply(Map<String, Object> topXList);
	
}
