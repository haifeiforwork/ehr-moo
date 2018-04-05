package com.lgcns.ikep4.servicepack.usagetracker.util;

/**
 * 
 * 서베이 컨트롤러 타입
 *
 * @author 고인호(ihko11@daum.net)
 * @version $Id: UsageTrackerConstance.java 16244 2011-08-18 04:11:42Z giljae $
 */
public final class UsageTrackerConstance {
	
	private UsageTrackerConstance() {
	}
	
	public final static String ITEM_TYPE_CODE="Tracker";
	

	
	//LOG_TARGET[로그  적용 대상 ( 0 : 사용자로그인, 1 : 메뉴, 2 : 포틀릿)]
	public final static int LOG_TARGET_COUNT=3;
	public final static int LOG_TARGET_LOGIN=0;
	public final static int LOG_TARGET_MENU=1;
	public final static int LOG_TARGET_PORTLET=2;
	       	
	       	
   	//USAGE[로그 사용 여부 ( 0 : 사용, 1 : 미사용)]
   	public final static int UT_CONFIG_LOGIN_USAGE_USE=0;
   	public final static int UT_CONFIG_LOGIN_USAGE_UNUSE=1;
   	
   	public final static int UT_CONFIG_MENU_USAGE_USE=0;
   	public final static int UT_CONFIG_MENU_USAGE_UNUSE=1;
   	
   	public final static int UT_CONFIG_PORTLET_USAGE_USE=0;
   	public final static int UT_CONFIG_PORTLET_USAGE_UNUSE=1;
	
   	//empty string
   	public final static String WI_STRING="";
   	
   	//MODULE_TYPE IS '모듈 타입( 0 : 로그인, 1 : 메뉴, 2 : 브라우저, 3 : 포틀릿, 4 : SMS)';
   	public final static int MODULE_TYPE_LOGIN=0;
   	public final static int MODULE_TYPE_MENU=1;
   	public final static int MODULE_TYPE_BROWSE=2;
   	public final static int MODULE_TYPE_PORTLET=3;
   	public final static int MODULE_TYPE_SMS=4;
	
}