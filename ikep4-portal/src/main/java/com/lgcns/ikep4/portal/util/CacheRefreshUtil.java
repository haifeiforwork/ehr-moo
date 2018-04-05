/**
 * =================================================================================
 * @Project      : 무림 구매포탈 시스템
 * @Source       : CacheRefreshUtil.java
 * @Description  : CacheRefresh Utility
 * @Version      : v1.0
 * 
 * Copyright(c) 2015 GM Solution All rights reserved
 * =================================================================================
 *  No    CSR ID   Req. No.         Req. Date     Author  Description
 * =================================================================================
 *  1.0                             2015/03/05    정병철    1.0 Release
 * =================================================================================
 */


package com.lgcns.ikep4.portal.util;

import com.lgcns.ikep4.portal.util.spring.SpringFactoryUtil;

/**
 * <pre>
 * 기능 : Cache 를 Refresh 하기 위한 클래스
 * 설명 : 전달된 이름의 캐쉬 메모리의 정보를 모두 제거한 후 최초 조회 시 다시 한 번 로드한다.
 * 
 * Special Logic : NONE
 * </pre>
 */
public class CacheRefreshUtil {
	public static void refreshCache() {
		((net.sf.ehcache.CacheManager) SpringFactoryUtil.getBean("ehCacheManager")).getCache("default").flush();
	}

	public static void refreshCache(String cacheName) {
		((net.sf.ehcache.CacheManager) SpringFactoryUtil.getBean("ehCacheManager")).getCache(cacheName).flush();
	}
}
