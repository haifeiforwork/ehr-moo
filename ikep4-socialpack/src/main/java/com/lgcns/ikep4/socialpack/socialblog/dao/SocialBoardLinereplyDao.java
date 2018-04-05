/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.socialblog.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBoardLinereply;
import com.lgcns.ikep4.socialpack.socialblog.search.SocialBoardLinereplySearchCondition;

/**
 * 소셜 블로그 댓글 관련 DAO Interface
 *
 * @author 이형운
 * @version $Id: SocialBoardLinereplyDao.java 16246 2011-08-18 04:48:28Z giljae $
 */
public interface SocialBoardLinereplyDao extends GenericDao<SocialBoardLinereply, String> {
	
	/**
	 * 소셜블로그 게시글의 댓글 목록 조회
	 * @param socialBoardLinereplySearchCondition 블로그 댓글 정보 조회용 정보
	 * @return 블로그 댓글 정보 리스트
	 */
	public List<SocialBoardLinereply> listBySearchCondition(SocialBoardLinereplySearchCondition socialBoardLinereplySearchCondition); 
	
	/**
	 * 소셜블로그 게시글의 댓글 수 조회
	 * @param socialBoardLinereplySearchCondition 블로그 댓글 정보 조회용 정보
	 * @return 블로그 댓글 정보 수
	 */
	public Integer countBySearchCondition(SocialBoardLinereplySearchCondition socialBoardLinereplySearchCondition);
	
	/**
	 * 소셜블로그 게시글 댓글의 하위 댓글의 수
	 * @param linereplyId 댓글 ID
	 * @return 댓글의 하위 댓글 수
	 */
	public Integer countChildren(String linereplyId);
	
	/**
	 * 소셜블로그 게시글의 댓글 작성시 관련  댓글의 Step 업데이트
	 * @param socialBoardLinereply 블로그 댓글 정보 정보
	 */
	public void updateStep (SocialBoardLinereply socialBoardLinereply);
	
	/**
	 * 소셜블로그 게시글의 댓글  논리적 삭제 
	 * @param socialBoardLinereply 블로그 댓글 정보 정보
	 */
	public void logicalDelete(SocialBoardLinereply socialBoardLinereply);

	/**
	 * 소셜블로그 게시글의 댓글  물리적 삭제
	 * @param linereplyId 댓글 ID
	 */
	public void physicalDelete(String linereplyId);
	
	/**
	 * 댓글 ID 기준으로 소셜 블로그 하위 댓글 포함 물리적 삭제 
	 * @param linereplyId 댓글 ID
	 */
	public void physicalDeleteThreadByLinereplyId(String linereplyId);

	/**
	 * 블로그 글 아이템 ID 기준으로 소셜 블로그 하위 댓글 포함 물리적 삭제 
	 * @param itemId 블로그 글 아이템 ID
	 */
	public void physicalDeleteThreadByItemId(String itemId);
	
	/**
	 * 최근에 작성된 댓글 리스트 가져 온다.
	 * @param topXList 최근 작성 조회 용 Map 
	 * @return 최근 작성된 댓글 목록 리스트
	 */
	public List<SocialBoardLinereply> listTopXLinereply(Map<String, Object> topXList);
	
}
