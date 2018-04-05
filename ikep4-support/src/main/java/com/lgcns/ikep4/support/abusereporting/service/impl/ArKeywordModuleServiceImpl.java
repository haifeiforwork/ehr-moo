/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.abusereporting.service.impl;

import java.util.Iterator;
import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.support.abusereporting.base.ArConstant;
import com.lgcns.ikep4.support.abusereporting.dao.ArKeywordModuleDao;
import com.lgcns.ikep4.support.abusereporting.dao.ArModuleDao;
import com.lgcns.ikep4.support.abusereporting.model.ArKeywordModule;
import com.lgcns.ikep4.support.abusereporting.model.ArModule;
import com.lgcns.ikep4.support.abusereporting.service.ArKeywordModuleService;


/**
 * 
 * ArKeywordModuleService 구현 클래스
 *
 * @author 최성우
 * @version $Id: ArKeywordModuleServiceImpl.java 17315 2012-02-08 04:56:13Z yruyo $
 */
@Service
public class ArKeywordModuleServiceImpl extends GenericServiceImpl<ArKeywordModule, ArKeywordModule> implements ArKeywordModuleService {

	@Autowired
	private ArKeywordModuleDao arKeywordModuleDao;

	@Autowired
	private ArModuleDao arModuleDao;

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.abusereporting.service.ArKeywordModuleService#listByKeyword(com.lgcns.ikep4.support.abusereporting.model.ArKeywordModule)
	 */
	public List<ArModule> listByKeyword(ArKeywordModule arKeywordModule) {
		return arKeywordModuleDao.listByKeyword(arKeywordModule);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.abusereporting.service.ArKeywordModuleService#listByModuleCode(com.lgcns.ikep4.support.abusereporting.model.ArKeywordModule)
	 */
	public List<String> listByModuleCode(ArKeywordModule arKeywordModule) {
		return arKeywordModuleDao.listByModuleCode(arKeywordModule);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.abusereporting.service.ArKeywordModuleService#prohibitWordCacheReload(java.lang.String)
	 */
	@SuppressWarnings("rawtypes")
	public void prohibitWordCacheReload(String portalId){
		if (log.isDebugEnabled()) {
			log.debug("prohibitWordCacheReload Start~");
			log.debug("portalId:" + portalId);
		}
		
		// CacheManager를 이용하여 prohibitWordCache를 조회한다.
		CacheManager cacheManager = CacheManager.getInstance();
		
		Cache cache = cacheManager.getCache("prohibitWordCache");
		
		// prohibitWordCache가 없으면 새로 캐쉬설정을 한다.
		if(null != cache){
			cache.removeAll();
		}else{
			cache = new Cache("prohibitWordCache", ArConstant.MAX_ELEMENTS_IN_MEMORY, false, false, ArConstant.TIME_TO_LIVE_SEECONDS, ArConstant.TIME_TO_IDLE_SECONDS);
			cacheManager.addCache(cache);
		}
		
		// 전체 모듈 리스트 조회
		List<ArModule> arModuleList = arModuleDao.list();
		
		Iterator it = arModuleList.iterator();
		
		// 모듈별 금지어 리스트 조회후 캐쉬에 등록
		while(it.hasNext()){
			ArModule arModule = (ArModule) it.next();
			String moduleCode = arModule.getModuleCode();
			
			ArKeywordModule arKeywordModule = new ArKeywordModule();
			arKeywordModule.setModuleCode(moduleCode);
			arKeywordModule.setPortalId(portalId);
			
			List<String> keywordList = arKeywordModuleDao.listByModuleCode(arKeywordModule);

	        StringBuffer sb = new StringBuffer();
	        
			Iterator<String> itKeywordList = keywordList.iterator();		
			while(itKeywordList.hasNext()){
				String keyword = (String) itKeywordList.next();
				if (log.isDebugEnabled()) {
					log.debug("keywordList keyword:"+keyword);
				}
	        	if(0 != sb.length()){
	        		sb.append("|");
	        	}
				sb.append(keyword);
			}

			if (log.isDebugEnabled()) {
				log.debug(moduleCode + portalId+ " keywordList sb:"+sb.toString());
			}

			// 금지어 리스트를 moduleCode + portalId 변수에 담는다.
			Element newElementBD = new Element(moduleCode + portalId			, sb.toString());
			cache.put(newElementBD);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.abusereporting.service.ArKeywordModuleService#getProhibitWordCacheByModuleCode(com.lgcns.ikep4.support.abusereporting.model.ArKeywordModule)
	 */
	public String getProhibitWordCacheByModuleCode(ArKeywordModule arKeywordModule) {

		String keywordList = "";
		
		CacheManager cacheManager = CacheManager.getInstance();		
		Cache cache = cacheManager.getCache("prohibitWordCache");
		
		// key : moduleCode + portalId
		String cacheEl = arKeywordModule.getModuleCode() + arKeywordModule.getPortalId();
		
		// 처음 cache를 호출하거나, 시간이 지나서 그 안의 Element가 없어졌을 경우 다시 조회해서 cache에 저장한다.
		if(null == cache || null == cache.get(cacheEl))
		{
			prohibitWordCacheReload(arKeywordModule.getPortalId());

			cache = cacheManager.getCache("prohibitWordCache");
		}

		Element element = cache.get(cacheEl);
		keywordList = (String) element.getValue();		
		
		return keywordList;
	}
	
}
