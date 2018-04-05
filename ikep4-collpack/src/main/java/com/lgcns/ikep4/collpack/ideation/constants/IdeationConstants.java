/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.ideation.constants;

/**
 * TODO Javadoc주석작성
 * 
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: IdeationConstants.java 12630 2011-05-23 05:43:35Z loverfairy $
 */
public final class IdeationConstants {

	private IdeationConstants() {}
	
	/**
	 * 수 증가
	 */
	public static final int NUM_INCRASE = 1;

	/**
	 * 수 증가
	 */
	public static final int NUM_DECREASE = -1;


	/**
	 * 삭제된 게시물
	 */
	public static final int ITEM_DELETE_OK = 1;

	/**
	 * 삭제안된 게시물
	 */
	public static final int ITEM_DELETE_NO = 0;

	/**
	 * 정책 타입( 0 : 우수 아이디어 정책, 1 : 아이디어 제안활동 정책, 2 : 아이디어 기여활동 정책)
	 */

	public static final String POLICY_BEST = "0";

	public static final String POLICY_SUGGESTION = "1";
	
	public static final String POLICY_CONTRIBUTION = "2";

	/**
	 * list type
	 */
	public static final String LIST_TYPE_IDEA = "idea";
	
	public static final String LIST_TYPE_IDEA_BEST = "ideaBest";
	
	public static final String LIST_TYPE_IDEA_ADOPT = "adopt";
	
	public static final String LIST_TYPE_IDEA_BUSINESS = "business";
	
	public static final String LIST_TYPE_LINEREPLY = "linereply";
	
	
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
	public static final String TAP_TYPE_SUGGESTION = "suggestion";
	
	public static final String TAP_TYPE_ADOP = "adop";

	
	/**
	 * 정렬타입
	 */
	public static final String ORDER_DATE = "date";
	
	public static final String ORDER_HIT = "hit";
	
	public static final String ORDER_BEST = "best";
	
	public static final String ORDER_LINEREPLY = "linereply";
	
	public static final String ORDER_RECOMMEND = "recommend";
	
	
	/**
	 * 토론글 있는지 
	 */
	public static final String IS_ITEM = "1";
	
	public static final String IS_BEST = "1";
	
	public static final String IS_BUSINESS = "1";
	
	public static final String IS_RECOMMEND = "1";
	
	public static final String IS_MY = "1";
	
	/**
	 * 채택된 아이디어 인지 아닌지
	 */
	public static final String IS_ADOPT = "1";
	
	public static final String IS_NOT_ADOPT = "0";
	
	
	public static final String IS_SUGGESTION = "1";
	
	public static final String IS_CONTRIBUTION = "1";
	
	/**
	 * 사업화 선정 아이템 여부
	 */
	public static final String BUSINESS_TYPE_NONE = "0";
	
	public static final String BUSINESS_TYPE_ABOPT = "1";
	
	public static final String BUSINESS_TYPE_COMPLETE = "2";
	
	
	public static final int LINEREPLY_INDENTATION_LIMITE = 5;
	
	/**
	 * 업무 코드
	 */
	public static final String ITEM_TYPE_CODE_IDEATION = "Ideation";
	
	/**
	 * 요약 개수
	 */
	public static final int CONTENT_SUMMAR_COUNT = 500;
	
}
