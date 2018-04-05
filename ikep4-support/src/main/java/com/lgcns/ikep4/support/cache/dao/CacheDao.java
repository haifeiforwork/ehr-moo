package com.lgcns.ikep4.support.cache.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.support.cache.model.CacheData;

/**
 * 캐시 Dao
 * @author 임종상
 *
 */
public interface CacheDao extends GenericDao<CacheData, String> {
	
	/**
	 * 포틀릿 조회
	 * @param portletId
	 * @return
	 */
	public CacheData getPortlet(String portletId);
	
	/**
	 * 포탈 조회
	 * @param portalId
	 * @return
	 */
	public CacheData getPortal(String portalId);
	
	/**
	 * 포탈 portletConfigId 목록 조회
	 * @param portalId
	 * @param userId
	 * @param cacheNameStr
	 * @return
	 */
	public List<CacheData> listPortletConfigId(String portalId, String userId, String cacheNameStr);
}