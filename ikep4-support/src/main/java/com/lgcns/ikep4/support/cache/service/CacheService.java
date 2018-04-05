package com.lgcns.ikep4.support.cache.service;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.support.cache.model.CacheData;

/**
 * 캐시 Service
 * @author 임종상
 *
 */
@Transactional
public interface CacheService extends GenericService<CacheData, String> {
	
	/**
	 * 캐시에 데이터가 있는지 체크
	 * @param cacheFlag
	 * @return
	 */
	public Object cacheCheck(String cacheFlag);
	
	/**
	 * 캐시 Element 저장
	 * @param cacheFlag
	 * 
	 */
	public void addCacheElement(String cacheFlag, Object objValue);
	
	/**
	 * 캐시에 데이터가 있는지 체크(포틀릿 contents) 
	 * @param portletId
	 * @param portletConfigId
	 * @return
	 */
	public Object cacheCheckPortletContent(String portletId, String portletConfigId);
	
	
	/**
	 * 캐시 Element 저장(포틀릿 contents)
	 * @param portletId
	 * @param portletConfigId
	 * @param objValue
	 */
	public void addCacheElementPortletContent(String portletId, String portletConfigId, Object objValue);
	
	/**
	 * 캐시 삭제
	 * @param portletId
	 */
	public void removeCachePortletContent(String portletId);
	
	/**
	 * 캐시 Element 전체 삭제  
	 * @param cacheFlag
	 */
	public void removeCacheElementAll(String cacheFlag);
	
	/**
	 * 캐시 Element 전체 삭제  (포틀릿 contents)
	 * @param cacheName
	 */
	public void removeCacheElementPortletContentAll(String cacheNameStr);
	
	/**
	 * 캐시 Element 삭제 
	 * @param cacheFlag
	 */
	public void removeCacheElement(String cacheFlag);
	
	/**
	 * 캐시 Element 삭제 (포틀릿 contents)
	 * @param portletId
	 * @param portletConfigId
	 */
	public void removeCacheElementPortletContent(String portletId, String portletConfigId);
	
	/**
	 * 캐시 Element 삭제 (포틀릿 contents)
	 * @param cacheName
	 * @param cacheMode
	 * @param elementKey
	 * @param userId
	 */
	public void removeCacheElementPortletContent(String cacheNameStr, String cacheModeStr, String elementKeyStr, String userId);
	
	/**
	 *	포탈별 캐시 전체 삭제 
	 */
	public void removeCacheAllPortal();
}