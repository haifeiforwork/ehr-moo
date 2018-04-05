/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.socialblog.base;

/**
 * 소셜 블로그용 Constant 
 *
 * @author 이형운
 * @version $Id: Constant.java 16246 2011-08-18 04:48:28Z giljae $
 */
public final class Constant {
	
	/**
	 * 기본 생성자
	 */
	private Constant(){}
	
	/**
	 * 소셜 블로그  Default 포스팅 조회 용 Row Count
	 */
	public static final Integer SOCIAL_BLOG_DEFAULT_POSTING_ROW_COUNT				= 3;
	
	/**
	 * 소셜 블로그 업로드시 사용 하는 File Buffer Size
	 */
	public static final long SOCIAL_BLOG_FILE_BUFFER_SIZE							= 1024;
	
	/**
	 * 소셜 블로그 업로드 가능한 이미지 파일 사이즈
	 */
	public static final long SOCIAL_BLOG_IMAGE_FILE_SIZE							= 1000000;
	
	/**
	 * 소셜 블로그 업로드 가능한 파일 사이즈
	 */
	public static final long SOCIAL_BLOG_FILE_SIZE									= 1000000;
	
	/**
	 * 소셜 블로그 업로드 불가능 파일 타입 설정
	 */
	public static final String SOCICAL_BLOG_RESTRICTION_TYPE 						= "exe^bat^code";
	
	/**
	 * 소셜 블로그 시스템 사용자 ID 체크 값
	 */
	public static final String SOCIAL_BLOG_SYSTEM_ID 								= "SB";
	
	/**
	 * 소셜 블로그 시스템 사용자 ID 체크 값
	 */
	public static final Integer SOCIAL_BLOG_TAG_CLOUD_COUNT 						= 10;
	
	/**
	 * 블로그 최근 Comment 가져 오는  Default Value
	 */
	public static final Integer SOCIAL_BLOG_RECENT_COMMENT_COUNT 					= 5;
	
	
	/**
	 * 소셜 블로그 방문자 차트 조회시 기준 WEEK DATE 수
	 */
	public static final int SOCIAL_BLOG_BASE_WEEK_DATE 								= 2;
	
	/**
	 * 소셜 블로그 방문자 차트 조회시 기준일 관련 전날 조회용 DATE 일수
	 */
	public static final int SOCIAL_BLOG_BASE_REALATIVE_DATE 						= 6;

}
