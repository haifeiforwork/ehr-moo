package com.lgcns.ikep4.support.cache.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;


public class CacheData extends BaseObject{

	private static final long serialVersionUID = -5376906778373582356L;

	/**
	 * 포틀릿 ID
	 */
	private String portletId;
	
	/**
	 * 포틀릿 컨텐츠 캐시 사용여부
	 */
	private String portletCacheYn;
	
	/**
	 * 포틀릿 컨텐츠 캐시 주기(초단위) 
	 */
	private String cacheLiveSecond;
	
	/**
	 * 포틀릿 컨텐츠 캐시 max 갯수
	 */
	private String cacheMaxCount;
	
	/**
	 * 설정 파일의  캐시 STR
	 */
	private String cacheNameStr;
	
	/**
	 * 설정 파일의 캐시모드 STR
	 */
	private String cacheModeStr;
	
	/**
	 * 설정 파일의 캐시 ELEMENT STR
	 */
	private String elementKeyStr;
	
	/**
	 * 포탈 ID
	 */
	private String portalId;
	
	/**
	 * 포탈 캐시 사용여부
	 */
	private String portalCacheYn;
	
	/**
	 * 포틀릿 ConfigId
	 */
	private String portletConfigId;
	
	
	public String getPortletConfigId() {
		return portletConfigId;
	}

	public void setPortletConfigId(String portletConfigId) {
		this.portletConfigId = portletConfigId;
	}
	
	public String getPortletId() {
		return portletId;
	}

	public void setPortletId(String portletId) {
		this.portletId = portletId;
	}
	
	public String getPortletCacheYn() {
		return portletCacheYn;
	}

	public void setPortletCacheYn(String portletCacheYn) {
		this.portletCacheYn = portletCacheYn;
	}

	public String getCacheLiveSecond() {
		return cacheLiveSecond;
	}

	public void setCacheLiveSecond(String cacheLiveSecond) {
		this.cacheLiveSecond = cacheLiveSecond;
	}

	public String getCacheMaxCount() {
		return cacheMaxCount;
	}

	public void setCacheMaxCount(String cacheMaxCount) {
		this.cacheMaxCount = cacheMaxCount;
	}

	public String getCacheNameStr() {
		return cacheNameStr;
	}

	public void setCacheNameStr(String cacheNameStr) {
		this.cacheNameStr = cacheNameStr;
	}

	public String getCacheModeStr() {
		return cacheModeStr;
	}

	public void setCacheModeStr(String cacheModeStr) {
		this.cacheModeStr = cacheModeStr;
	}

	public String getElementKeyStr() {
		return elementKeyStr;
	}

	public void setElementKeyStr(String elementKeyStr) {
		this.elementKeyStr = elementKeyStr;
	}
	
	public String getPortalId() {
		return portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}

	public String getPortalCacheYn() {
		return portalCacheYn;
	}

	public void setPortalCacheYn(String portalCacheYn) {
		this.portalCacheYn = portalCacheYn;
	}
}