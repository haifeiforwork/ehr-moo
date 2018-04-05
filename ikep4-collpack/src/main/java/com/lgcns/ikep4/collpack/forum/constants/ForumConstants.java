/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.forum.constants;

/**
 * TODO Javadoc주석작성
 * 
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: ForumConstants.java 16236 2011-08-18 02:48:22Z giljae $
 */
public final class ForumConstants {

	private ForumConstants() {}
	
	/**
	 * 수 증가
	 */
	public static final int NUM_INCRASE = 1;

	/**
	 * 수 증가
	 */
	public static final int NUM_DECREASE = -1;

	/**
	 * 참여 타입 /** 토론 참여 타입 ( 0 : 토론주제등록 1 : 토론글 등록 2 : 토론의견 등록 3 : 토론글 찬성 등록 4 :
	 * 토론글 반대 등록 5 : 토론의견 등록 6 : 토론의견 추천 등록)
	 */

	public static final String PARTICIPATION_DISCUSSION_CREATE = "0";

	public static final String PARTICIPATION_ITEM_CREATE = "1";

	public static final String PARTICIPATION_LINEREPLAY_CREATE = "2";

	public static final String PARTICIPATION_ITEM_AGREE = "3";

	public static final String PARTICIPATION_ITEM_OPPO = "4";

	public static final String PARTICIPATION_RECOMMEND_CREATE = "5";

	/**
	 * 토론글 피드백 타입( 0 : 찬성, 1 : 반대)
	 */
	public static final String FEEDBACK_AGREEMENT = "0";

	public static final String FEEDBACK_OPPOSITION = "1";

	/**
	 * 삭제된 게시물
	 */
	public static final int ITEM_DELETE_OK = 1;

	/**
	 * 삭제안된 게시물
	 */
	public static final int ITEM_DELETE_NO = 0;

	/**
	 * 토론정책타입 ( 0 : 토론활동 정책 1 : 인기토론주제 정책 2 : 인기토론글 정책 3 : 우수토론 정책 4 : 토론분석 정책)
	 */
	public static final String POLICY_ACTIVITY = "0";

	public static final String POLICY_POPULAR_DISCUSSION = "1";

	public static final String POLICY_POPULAR_ITEM = "2";

	public static final String POLICY_BEST = "3";

	public static final String POLICY_ANALYSIS = "4";

	/**
	 * list type
	 */
	public static final String LIST_TYPE_DISCUSSION = "discussion";

	public static final String LIST_TYPE_ITEM = "item";
	
	public static final String LIST_TYPE_ITEM_DISCUSSION = "itemDiscussion";
	
	public static final String LIST_TYPE_ITEM_LAST = "itemLast";

	public static final String LIST_TYPE_LINEREPLY_ITEM = "linereplyItem";
	
	public static final String LIST_TYPE_LINEREPLY = "linereply";
	
	public static final String LIST_TYPE_DISCUSSION_POPULAR = "discussionPopular";
	
	public static final String LIST_TYPE_ITEM_POPULAR = "itemPopular";


	
	/**
	 * best인지
	 */
	public static final String BEST_TYPE = "1";
	
	/**
	 * POPULAR인지
	 */
	public static final String POPULAR_TYPE = "1";


	/**
	 * 최초라인 수
	 */
	public static final String BASE_NO = "0";

	/**
	 * 최대라인수
	 */
	public static final String END_NO = "100000000";


	
	/**
	 * 탭타입
	 */
	public static final String TAP_TYPE_ITEM = "item";

	public static final String TAP_TYPE_DISCUSSION = "discussion";

	public static final String TAP_TYPE_LINEREPLY = "linereply";
	
	public static final String TAP_TYPE_ITEM_BEST = "itemBest";
	
	public static final String TAP_TYPE_LINEREPLY_BEST = "linereplyBest";

	
	/**
	 * 정렬타입
	 */
	public static final String ORDER_DATE = "date";
	
	public static final String ORDER_HIT = "hit";
	
	public static final String ORDER_BEST = "best";
	
	public static final String ORDER_LINEREPLY = "linereply";
	
	/**
	 * 태그 서브 타입
	 */

	public static final String TAG_TYPE_DISCUSSION = "duscussion";
	
	public static final String TAG_TYPE_ITEM = "item";
	
	/**
	 * 토론글 있는지 
	 */
	public static final String IS_ITEM = "1";
	
	/**
	 * junit 테스트 에러 방지
	 */
	public static final String JUNIT_TEST = "junitTest";
	
	/**
	 * 댓글 indentation 한계 수치
	 */
	public static final int LINEREPLY_INDENTATION_LIMITE = 5;
	
	/**
	 * 업무 코드
	 */
	public static final String ITEM_TYPE_CODE_FORUM = "Forum";
	
	/**
	 * 요약 개수
	 */
	public static final int CONTENT_SUMMAR_COUNT = 500;	
}
