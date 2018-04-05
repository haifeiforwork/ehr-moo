/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.addressbook.base;

/**
 * 주소록 용 Constant 파일
 *
 * @author 이형운
 * @version $Id: Constant.java 16258 2011-08-18 05:37:22Z giljae $
 */
public final class Constant {
	
	/**
	 * 기본 생성자
	 */
	private Constant(){}

	
	/**
	 * Connection History 1회 Patch Size
	 */
	public static final Integer DEFAULT_CONN_HIST_FETCH_SIZE			= 10;
	
	/**
	 * 엑셀 다운로드 용으로 페이징 구분 없이 전체 데이타를 가지고 올때 사용하는 Fetch Size
	 */
	public static final Integer DEFAULT_ADDRGROUP_PERSON_FETCH_SIZE		= 10000;
	
	/**
	 * 아웃륵용 이름 Index
	 */
	
	public static final int OUTLOOK_FIRST_NAME_INDEX					= 1 ;
	/**
	 * 아웃륵용 성 Index
	 */
	public static final int OUTLOOK_LAST_NAME_INDEX 					= 3 ;
	
	/**
	 * 아웃륵용 회사명 Index
	 */
	public static final int OUTLOOK_COMPANY_INDEX 						= 5 ;

	/**
	 * 아웃륵용 팀명 Index
	 */
	public static final int OUTLOOK_TEAM_NAME_INDEX 					= 6 ;

	/**
	 * 아웃륵용 직함 Index
	 */
	public static final int OUTLOOK_JOB_RANK_NAME_INDEX 				= 7 ;

	/**
	 * 아웃륵용 사무실 전화번호 Index
	 */
	public static final int OUTLOOK_OFFICE_PHONE_INDEX 					= 31 ;

	/**
	 * 아웃륵용 모바일 폰번호  Index
	 */
	public static final int OUTLOOK_MOBILE_PHONE_INDEX 					= 40 ;

	/**
	 * 아웃륵용 메모  Index
	 */
	public static final int OUTLOOK_MEMO_INDEX 							= 57 ;

	/**
	 * 아웃륵용 메일 주소 Index
	 */
	public static final int OUTLOOK_MAIL_ADDRESS_INDEX 					= 76 ;

	/**
	 * 아웃륵 익스프레스 용 이름 Index
	 */
	public static final int EXP_OUTLOOK_FULL_NAME_INDEX					= 3 ;

	/**
	 * 아웃륵 익스프레스용 메일 주소 Index
	 */
	public static final int EXP_OUTLOOK_MAIL_ADDRESS_INDEX 				= 5 ;

	/**
	 * 아웃륵 익스프레스용 모바일 폰번호 Index
	 */
	public static final int EXP_OUTLOOK_MOBILE_PHONE_INDEX 				= 13 ;

	/**
	 * 아웃륵 익스프레스용 사무실 폰번호 Index
	 */
	public static final int EXP_OUTLOOK_OFFICE_PHONE_INDEX 				= 21 ;

	/**
	 * 아웃륵 익스프레스용 회사명 Index
	 */
	public static final int EXP_OUTLOOK_COMPANY_INDEX 					= 24 ;

	/**
	 * 아웃륵 익스프레스용 직함명 Index
	 */
	public static final int EXP_OUTLOOK_JOB_RANK_NAME_INDEX 			= 25 ;

	/**
	 * 아웃륵 익스프레스용 팀명 Index
	 */
	public static final int EXP_OUTLOOK_TEAM_NAME_INDEX 				= 26 ;

	/**
	 * 아웃륵 익스프레스용 메모 Index
	 */
	public static final int EXP_OUTLOOK_MEMO_INDEX 						= 28 ;
	
	/**
	 * 조직도 팝업 Tab의 Display 시 사용하는 Flag 값
	 */
	public static final String ADDRE_TAB_DISP_FLAG 						= "1" ;
	
	/**
	 * 조직도 팝업에서 Public Tab 의 인덱스 값
	 */
	public static final int ADDRE_PUB_TAB_INDEX 						= 0 ; 
	
	/**
	 * 조직도 팝업에서 Private Tab 의 인덱스 값
	 */
	public static final int ADDRE_PRI_TAB_INDEX 						= 1 ;
	
	/**
	 * 조직도 팝업에서 Collaboration Tab 의 인덱스 값
	 */
	public static final int ADDRE_COL_TAB_INDEX 						= 2 ;
	
	/**
	 * 조직도 팝업에서 SNS Tab 의 인덱스 값
	 */
	public static final int ADDRE_SNS_TAB_INDEX 						= 3 ;
	
	
	/**
	 * 조직도 팝업에서 부서 Tab 의 인덱스 값
	 */
	public static final int ADDRE_ORG_TAB_INDEX 						= 4 ;
	
	/**
	 * 조직도 팝업에서 검색 Tab 의 인덱스 값
	 */
	public static final int ADDRE_SCH_TAB_INDEX 						= 5 ;
	
	
	/**
	 * 조직 팝업에서 조회시 가져 오는 디폴트 Fetch Size
	 */
	public static final Integer DEFAULT_ADDR_POPUP_FETCH_SIZE			= 10000;
	
	/**
	 * 조직 팝업에서 조회시 가져 오는 디폴트 Group Fetch Size
	 */
	public static final Integer DEFAULT_ADDR_POPUP_GROUP_FETCH_SIZE			= 50;

}
