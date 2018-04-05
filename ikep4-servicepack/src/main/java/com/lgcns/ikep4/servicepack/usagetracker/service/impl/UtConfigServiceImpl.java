/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.servicepack.usagetracker.service.impl;

import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.servicepack.usagetracker.dao.UtConfigDao;
import com.lgcns.ikep4.servicepack.usagetracker.model.UtConfig;
import com.lgcns.ikep4.servicepack.usagetracker.model.UtConfigKey;
import com.lgcns.ikep4.servicepack.usagetracker.service.UtConfigService;
import com.lgcns.ikep4.servicepack.usagetracker.util.MagicNumUtils;
import com.lgcns.ikep4.servicepack.usagetracker.util.SearchUtil;
import com.lgcns.ikep4.servicepack.usagetracker.util.UtConfigUtil;

/**
 * 사용자현황통계
 *
 * @author ihko11
 * @version $Id: UtConfigServiceImpl.java 19668 2012-07-05 08:08:31Z malboru80 $
 */
@Service
@Transactional
public class UtConfigServiceImpl extends GenericServiceImpl<UtConfig, UtConfigKey> implements UtConfigService {
	protected final Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private UtConfigDao utConfigDao;
	
	@Autowired
	public UtConfigServiceImpl(UtConfigDao dao) {
		super(dao);
		this.utConfigDao = dao;
	}
	
	/**
	 * 삭제
	 */
	@Override
	public void delete(UtConfigKey id) {
		utConfigDao.remove(id);
	}

	
	/**
	 * 상세
	 */
	@Override
	public UtConfig read(UtConfigKey id) {
		return utConfigDao.get(id);
	}
	
	
	@Override
	public boolean exists(UtConfigKey id) {
		return utConfigDao.exists(id);
	}

	/**
	 * 생성
	 */
	@Override
	public UtConfigKey create(UtConfig utConfig) {
		return utConfigDao.create(utConfig);
	}

	/**
	 * 수정
	 */
	@Override
	public void update(UtConfig utConfig) {
		utConfigDao.update(utConfig);
	}
	
	/**
	 * 모든값저장
	 * @param utConfigList
	 */
	public void saveOrUpdate(List<UtConfig> utConfigList, String portalId) {
		
		
		for (UtConfig utConfig2 : utConfigList) {
			boolean exists = exists( new UtConfigKey(utConfig2.getLogTarget(),utConfig2.getPortalId()) );
			
			if( exists ){
				utConfigDao.update( utConfig2 );
			}else{ 
				utConfigDao.create(utConfig2);	
			}
		}
		
		CacheManager cacheManager = CacheManager.getInstance();		
		Cache cache = cacheManager.getCache("logConfigCache"+portalId);
		
		if(cache == null) {
			cache = new Cache("logConfigCache"+portalId, 100, false, false, 0, 0);
			cacheManager.addCache(cache);
		} else {
			cache.removeAll();
		}
		
		SearchUtil searchUtil = new SearchUtil();
		searchUtil.setPortalId(portalId);

		List<UtConfig> utConfigListTemp = selectUtConfigList(searchUtil);
		
		for(int i=0 ; i < utConfigListTemp.size() ; i++){
			//LOG_TARGET[로그  적용 대상 ( 0 : 사용자로그인, 1 : 메뉴, 2 : 포틀릿, 3 : URL 응답시간)]
			Element newElement = new Element("logConfigCache"+utConfigListTemp.get(i).getLogTarget(), utConfigListTemp.get(i).getUsage());
			cache.put(newElement);
		}
	}
	
	
	/**
	 * 사용자별
	 */
	public List<UtConfig> selectUtConfigList(SearchUtil searchUtil){
		List<UtConfig> utConfigList = utConfigDao.selectUtConfigList(searchUtil);
		
		//utconfig list에 portal 값이 없으면
		if( utConfigList.size() < MagicNumUtils.ARRAY_SIZE_3 ){
			return UtConfigUtil.notExistUtConfigMake(searchUtil.getPortalId());
		}
		
		return utConfigList;
		
	}
	
	
	
}
