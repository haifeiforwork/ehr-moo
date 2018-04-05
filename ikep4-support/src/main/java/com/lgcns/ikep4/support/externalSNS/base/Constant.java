package com.lgcns.ikep4.support.externalSNS.base;

//import java.util.Properties;

//import com.lgcns.ikep4.util.PropertyLoader;

/**
 * Twitter 및 Facebook 인증 관련 키값 
 *  Twitter 및 Facebook 인증 계정에 관한 유의 점.
 *  ***** 별도의 메일계정과 핸드폰 번호를 통한 인증 이 필요로 함 *******
 *  
 *  
 *  Twitter 인증 계정의 경우는 리턴 URL 을 트위터 계정 내 어플리케이션 정보에 등록하여 사용하게 됨
 *           따라서 해당 iKEP 시스템을 사용하게 되는 각각의 포탈에서 개별 계정을 등록하여 사용해야 함.
 *           
 *            1. 회원 가입 
 *            어플리케이션이 사용하는 계정 (사용자 계정 아님)을 등록한다.
 *            예) ikep_manager@lgcns.com
 *            2. 어플리케이션 등록
 *            등록된 계정으로 로그인 후 twitter.com 하단 개발자 링크나  http://dev.twitter.com/ 로 이동
 *             메뉴에서 your apps >> Register a new app 선택
 *             
 *            3. 등록 양식 (예제)
 *            Application Name:  iKEP 4.0 for Java 입력
 *            Description: 	iKEP 4.0 for Java 입력
 *            Application Website: http://ikep.lgcns.com 입력
 *            Organization: 	LG CNS 입력
 *            Application Type:  Browser 선택
 *            Callback URL: 	http://ikep.lgcns.com:9999/ikep4-webapp/support/externalSNS/TwitterAuthSuccess.do
 *            *** 중요 실제 인증후 리턴되어 돌아 올 인증 결과 주소값 입력
 *            Default Access type: Read & Write 선택
 *            Application Icon: 
 *            
 *            이후는 프로파일에서 인증이 가능함.
 *            
 *                 
 *  Facebook 인증 계정 생성 및 등록 절차
 *            
 *            1. 회원 가입 
 *            어플리케이션이 사용하는 계정 (사용자 계정 아님 )을 등록한다. 
 *             예) ikep_manager@lgcns.com
 *            2. 내 앱 등록
 *             등록된 계정으로 로그인 후  facebook 하단 개발자 메뉴 또는 http://developers.facebook.com/ 
 *             입력 개발자 사이트로 이동한다. 
 *            3. 내 앱 >> + 새앱 만들기 를 통해 앱 등록을 한다.
 *            인증 계정의 경우는 해당 iKEP 를 어플리케이션으로 등록시에 핸드폰이나 신용카드로 인증된 계정이 필요로 함
 *             인증 용도로 사용 될 수도 있어서 관리자용 메일이나 핸드폰 번호 인증이 필요함 
 *            
 *            4. 웹사이트 등록  
 *            정상적으로 엡을 등록하고 나면 웹사이트 등록 메뉴에서 
 *		              사이트 URL 에 등록할  대상 URL => http://ikep.lgcns.com:9999/ikep4-webapp/
 *            사이트 도메인 => ikep.lgcns.com 등록
 *            
 *            이후는 프로파일에서 인증 이 가능함
 *              
 *  추후 인증되는 포탈에 각각의 계정정보로 인증한 값으로 아래 정보를 대체 해야 함
 *
 * @author 이형운
 * @version $Id: Constant.java 17315 2012-02-08 04:56:13Z yruyo $
 */
public final class Constant {
	
	/**
	 * 기본 생성자
	 */
	private Constant(){}

	
	// 트위터 인증용 키값  - ikep4 계정 인증 값
	//public static String TWITTER_CONSUMER_KEY			= ""; 
	//public static String TWITTER_CONSUMER_SECRET		= "";
	public static final String TWITTER_REQUEST_TOKEN_URL 	= "https://api.twitter.com/oauth/request_token";
	public static final String TWITTER_ACCESS_TOKEN_URL 	= "https://api.twitter.com/oauth/access_token";
	public static final String TWITTER_AUTHORIZE_URL 		= "https://api.twitter.com/oauth/authorize";
	
    //FaceBook 인증용 키값
    //public static String API_KEY = "";
    //public static String APP_SECRET = "";
    //public static final String APP_ID = "";
    
    public static final String FB_AUTH_URL = "http://www.facebook.com/login.php";
    public static final String FB_AUTH_PARAM1 = "?api_key=";
    public static final String FB_AUTH_PARAM2 = "&next=";
    public static final String FB_AUTH_PARAM3 = "&cancel_url=";
    public static final String FB_AUTH_PARAM4 = "&extern=1&fbconnect=1&return_session=1";
    public static final String FB_AUTH_PARAM5 = "&req_perms=email,publish_stream,read_stream,offline_access";

    public static final String REDIRECT_URL = "/support/externalSNS/FacebookAuth.do";
    public static final String API_CONSTANT_FILTER_KEY = "filter_key";
    public static final int    FACEBOOK_LIMIT = 50;
    public static final int    COUNT_FOR_MILLISECOND = 1000;
    public static final int    TWITTER_COUNT_DEFAULT = 20;
    
    
	
    

}

