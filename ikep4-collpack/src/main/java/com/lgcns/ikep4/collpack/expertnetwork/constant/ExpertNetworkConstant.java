package com.lgcns.ikep4.collpack.expertnetwork.constant;

/**
 * ExpertNetwork 전역 상수
 * 
 * @author 우동진 (jins02@nate.com)
 * @version $Id: ExpertNetworkConstant.java 16236 2011-08-18 02:48:22Z giljae $
 */
public final class ExpertNetworkConstant {

	/**
	 * 사내인증 전문가 관리자 입력여부 ( 0:관리자직접입력 )
	 */
	public static final int AUTHORIZED_FROM_ADMIN = 0;

	/**
	 * 사내인증 전문가 관리자 입력여부 ( 1: 배치입력 )
	 */
	public static final int AUTHORIZED_FROM_BATCH = 1;

	/**
	 * 사내인증 전문가 관리자 입력여부 ( 2: 관리자해제 )
	 */
	public static final int AUTHORIZED_FROM_ADMIN_DELETE = 2;

	/**
	 * 사내인증 전문가의 점수
	 */
	public static final int MAX_MATCHING_SCORE = 100;

	/**
	 * 메인화면 전문가 목록개수(페이징)
	 */
	public static final int MAIN_COUNF_OF_PAGE = 5;

	/**
	 * 화면상단 인기태그 표시개수
	 */
	public static final int SEARCH_BAR_TAG_COUNT = 5;


	/**
	 * 기본 생성자
	 */
	private ExpertNetworkConstant() {
	}

}
