/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.microblogging.base;

public final class Constant {

	private Constant(){}
	
    public static final String ADDON_TYPE_URL					= "0";	//url
    public static final String ADDON_TYPE_POLL					= "1";	//poll
    public static final String ADDON_TYPE_IMG					= "2";	//img
    public static final String ADDON_TYPE_FILE					= "3";	//file
	
    public static final String ADDON_LEFTBRACE_URL				= "{url:";	//url
    public static final String ADDON_LEFTBRACE_POLL				= "{poll:";	//poll
    public static final String ADDON_LEFTBRACE_IMG				= "{img:";	//img
    public static final String ADDON_LEFTBRACE_FILE				= "{file:";	//file

    public static final String ADDON_RIGHTBRACE					= "}";
    

    public static final String TAG_MENTION						= "@";
    public static final String TAG_HASH							= "#";
    
    public static final String TAG_TYPE_MENTION					= "0";
    public static final String TAG_TYPE_HASH					= "1";
    
    public static final String NULL_STRING						= " ";
    
    public static final String FILE_LINK_ITEM_TYPE_CODE			= "MB";
    
    public static final int    ADDON_RANDOMSTR_SIZE				= 4;
    public static final int    THE_NEWEST_TWEET_SIZE			= 3;

    public static final String SEARCH_TYPE_MEMBER				= "MEMBER";
    public static final String SEARCH_TYPE_GROUP				= "GROUP";
    

    public static final String MBGROUP_MEMBER_STATUS_INVITE		= "0";
    public static final String MBGROUP_MEMBER_STATUS_NORMAL		= "1";
    

    public static final int    MAX_FEED_COUNT_DEFAULT			= 200;
    public static final int    MAX_FAVORITE_COUNT_DEFAULT		= 200;
    public static final int    FEEDS_AT_TIME_DEFAULT			= 10;
    public static final int    DAYS_IN_YEAR						= 365;
    

    public static final int 	FETCH_SIZE_8					= 8;	
    public static final int 	FETCH_SIZE_10					= 10;	
    public static final int 	LIST_SIZE_9						= 9;	
}
