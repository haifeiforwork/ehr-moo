package com.lgcns.ikep4.support.cache.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.cache.dao.CacheDao;
import com.lgcns.ikep4.support.cache.model.CacheData;

/**
 * 캐시 Dao 구현 객체
 * @author 임종상
 *
 */
@Repository("cacheDao")
public class CacheDaoImpl extends GenericDaoSqlmap<CacheData, String> implements CacheDao {

	/**
	 * 포틀릿 조회
	 * @param portletId
	 * @return
	 */
	public CacheData getPortlet(String portletId) {
		return (CacheData) sqlSelectForObject("support.cache.getPortlet", portletId);
	}
	
	/**
	 * 포탈 조회
	 * @param portalId
	 * @return
	 */
	public CacheData getPortal(String portalId) {
		return (CacheData) sqlSelectForObject("support.cache.getPortal", portalId);
	}
	
	public List<CacheData> listPortletConfigId(String portalId, String userId, String cacheNameStr) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("portalId", portalId);
		map.put("userId", userId);
		map.put("cacheNameStr",cacheNameStr);
		
		return sqlSelectForList("support.cache.listPortletConfigId", map);
	}
	
	@Deprecated
	public String create(CacheData arg0) {
		return null;
	}

	@Deprecated
	public boolean exists(String arg0) {
		return false;
	}

	@Deprecated
	public CacheData get(String arg0) {
		return null;
	}

	@Deprecated
	public void remove(String arg0) {
		
	}

	@Deprecated
	public void update(CacheData arg0) {
		
	}
}
