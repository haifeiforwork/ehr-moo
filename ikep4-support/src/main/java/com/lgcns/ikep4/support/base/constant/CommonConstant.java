package com.lgcns.ikep4.support.base.constant;

import com.lgcns.ikep4.util.PropertyLoader;

/**
 * 전역 상수
 * 
 * @author 우동진 (jins02@nate.com)
 * @version $Id: CommonConstant.java 19225 2012-06-12 09:13:40Z yu_hs $
 */
public final class CommonConstant {

	/**
	 * 콤마
	 */
	public static final String COMMA_SEPARATER = ",";

	/**
	 * 날짜 형식
	 */
	public static final String DATE_FORMAT = "yyyy.MM.dd";

	/**
	 * 타임존 설정 형식
	 */
	public static final String TIMEZONE_DATETIME_FORMAT = "yyyy.MM.dd HH:mm:ss.SSS";

	/**
	 * 타임존 설정에 사용하는 시작일자 시간 값
	 */
	public static final String TIMEZONE_FROM_TIME_VALUE = " 00:00:00.000";

	/**
	 * 타임존 설정에 사용하는 종료일자 시간 값
	 */
	public static final String TIMEZONE_TO_TIME_VALUE = " 23:59:59.999";
	
	/**
	 * iKEP4 버전
	 */
	public static enum IKEPVersion { FULL, LIGHT, BASIC	};
	public static final String IKEP_VERSION_FULL = IKEPVersion.FULL.toString();
	public static final String IKEP_VERSION_LIGHT = IKEPVersion.LIGHT.toString();
	public static final String IKEP_VERSION_BASIC = IKEPVersion.BASIC.toString();
	
	/**
	 * iKEP4 패키지 버전
	 */
	public static final String PACKAGE_VERSION = PropertyLoader.loadProperties("/configuration/common-conf.properties")
													.getProperty("ikep4.package.version")
													.toUpperCase();
	
	/**
	 * 프로파일 심플 화면 여부
	 */
	public static final boolean IS_PROFILE_VIEW_SIMPLE = PropertyLoader.loadProperties("/configuration/common-conf.properties")
													.getProperty("ikep4.profile.view.mode.simple")
													.equalsIgnoreCase("Y");
	
	/**
	 * 사용자 포탈 메인 레이아웃 설정 명
	 */
	public static final String PORTAL_MAIN_LAYOUT = "PortalMainPortletLayout";

	/**
	 * 첨부 파일 다운로드 Url
	 */
	public static final String File_DOWNLOAD_BASE_URL = PropertyLoader.loadProperties("/configuration/fileupload-conf.properties")
														.getProperty("ikep4.support.fileupload.downloadurl");

	/**
	 * 서비스 기본 Url : http.../contextRoot
	 */
	public static final String IKEP_BASE_URL = PropertyLoader.loadProperties("/configuration/common-conf.properties")
												.getProperty("ikep4.baseUrl");
	
	/**
	 * 기본 생성자
	 */
	private CommonConstant() {
	}

}
