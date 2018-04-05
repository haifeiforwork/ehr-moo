/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.qna.constants;

/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: QnaConstants.java 16236 2011-08-18 02:48:22Z giljae $
 */
public final class QnaConstants {
	
	private QnaConstants() {}
	 
	/**
     * 일반질문 
     */
    public static final int URGENT_NO = 0;
    
    /**
     * 긴급질문
     */
    public static final int URGENT_OK = 1;
    
    /**
     * 삭제된 게시물
     */
    public static final int ITEM_DELETE_OK = 1;
    
    /**
     * 삭제안된 게시물
     */
    public static final int ITEM_DELETE_NO = 0;
    
    /**
     * 채택된 게시물
     */
    public static final int ADOPT_OK = 1;
    
    /**
     * 채택안된 게시물
     */
    public static final int ADOPT_NO = 0;
    
    /**
     * 게시물 채택 디폴트
     */
    public static final int ADOPT_DEFAULT = 9;
    
    
    /**
     * 수 증가
     */
    public static final int NUM_INCRASE = 1;
    
    /**
     * 수 증가
     */
    public static final int NUM_DECREASE = -1;
    
    /**
     * 상태 미답변
     */
    public static final String STATUS_NOREPLY = "0";
    
    /**
     * 상태 미해결
     */
    public static final String STATUS_REPLY = "1";

    /**
     * 상태 해결
     */
    public static final String STATUS_COMPLETE = "2";
    
    /**
     * 질문인지
     */
    public static final int IS_QNA = 0;
    
    /**
     * 답변인지
     */
    public static final int IS_REPLY = 1;
    
    
    /**
     * 채택되지 않은것들
     */
    public static final int IS_NOT_ADOPT = 1;
    
    /**
     * 질문인지
     */
    public static final int BEST_QNA_TYPE = 1;
    
    /**
     * 질문인지
     */
    public static final int BEST_REPLY_TYPE = 2;
    
    
    /**
     * 최초라인 수
     */
    public static final String BASE_NO = "0";
    
    /**
     * 최대라인수
     */
    public static final String END_NO = "100000000";
    
    /**
     * POLICY에서 사용하는 QNA 타입
     */
    public static final String POLICY_QNA_TYPE = "0";
    
    /**
     * POLICY에서 사용하는 REPLY 타입
     */
    public static final String POLICY_REPLY_TYPE = "1";
    
    /**
     * qna list type
     */
    public static final String LIST_TYPE_BEST = "Best";
    
    public static final String LIST_TYPE_URGENT = "Urgent";
    
    public static final String LIST_TYPE_NOTADOPT = "NotAdopt";
    
    public static final String LIST_TYPE_CATEGORY = "Category";
    
    public static final String LIST_TYPE_SEARCH = "Search";
    
    public static final String LIST_TYPE_MAIN = "Main";
    
    public static final String LIST_TYPE_MY = "My";
    
    /**
     * 카테고리 텝타입
     */
    public static final String TAP_TYPE_ALL = "0";
    
    public static final int TAP_TYPE_IS_NOT_ADOPT = 1;
    
    public static final String TAP_TYPE_BEST = "2";
    
    /**
     * qna 타입코드
     */
    public static final String ITEM_TYPE_CODE_QNA = "Q&A"; 
    
    /**
     * TAG 질문, 답변 item sub type
     */
    public static final String ITEM_SUB_TYPE_QNA = "Q"; 
    
    public static final String ITEM_SUB_TYPE_ANSWER = "A"; 
    
    /**
	 * 댓글 indentation 한계 수치
	 */
	public static final int LINEREPLY_INDENTATION_LIMITE = 5;
	
	/**
	 * 외부 문의 채널 - mblog
	 */
	public static final int EXPERT_CHANNEL_MBLOG = 3;
	
	/**
	 * 전문가 정보 배열로 오는 값에 순서
	 */
	public static final int EXPERT_INFO_ID = 0;
	
	public static final int EXPERT_INFO_NAME = 1;
	
	public static final int EXPERT_INFO_TEL = 2;
	
	public static final int EXPERT_INFO_MAI = 3;
	
	
	/**
	 * 평균 답변 소숫점 2자리 계산처리
	 */
	public static final int ROUND_PERCENT = 100;
	
	/**
	 * 요약 개수
	 */
	public static final int CONTENT_SUMMAR_COUNT = 500;
	
	/**
	 * 밀리언 초
	 */
	public static final int MILLION_SECOND = 1000;
	
	/**
	 * 시간 
	 */
	public static final int TIME_SECOND = 60;
	
	/**
	 * 시간 double 타입
	 */
	public static final double TIME_SECOND_LONG = 60.0;
	
	/**
	 * 정렬 타입
	 */
	public static final String ORDER_TYPE_ANSWER = "answer";
	public static final String ORDER_TYPE_BEST = "best";
	
}
