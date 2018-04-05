package com.lgcns.ikep4.support.rss.util;

/**
 * RssConstant 클래스
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: RssConstant.java 16274 2011-08-18 07:08:23Z giljae $
 */
public final class RssConstant {
	
	private RssConstant() {
		throw new AssertionError();
	}

	/**
	 * 전사 게시판
	 */
	public static final String META_TYPE_BOARD = "BD";

	/**
	 * Q&A : 긴급 질문
	 */
	public static final String META_TYPE_QNA100 = "QA100";

	/**
	 * Q&A : 미해결질문
	 */
	public static final String META_TYPE_QNA200 = "QA200";

	/**
	 * Q&A : Best Q&A 질문
	 */
	public static final String META_TYPE_QNA300 = "QA300";

	/**
	 * Q&A : Best Q&A 답변
	 */
	public static final String META_TYPE_QNA400 = "QA400";

	/**
	 * Q&A : 카테고리별 Q&A
	 */
	public static final String META_TYPE_QNA500 = "QA500";

	/**
	 * WORKSPACE
	 */
	public static final String META_TYPE_WORKSPACE = "WS";

	/**
	 * Social Blog
	 */
	public static final String META_TYPE_BLOG = "SB";

	/**
	 * Cafe
	 */
	public static final String META_TYPE_CAFE = "CF";

}
