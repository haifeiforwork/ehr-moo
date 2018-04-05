/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.abusereporting.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.support.abusereporting.dao.ArKeywordDao;
import com.lgcns.ikep4.support.abusereporting.dao.ArKeywordModuleDao;
import com.lgcns.ikep4.support.abusereporting.model.ArKeyword;
import com.lgcns.ikep4.support.abusereporting.model.ArKeywordModule;
import com.lgcns.ikep4.support.abusereporting.search.ArAbuseSearchCondition;
import com.lgcns.ikep4.support.abusereporting.service.ArKeywordModuleService;
import com.lgcns.ikep4.support.abusereporting.service.ArKeywordService;


/**
 * 
 * ArKeywordService 구현 클래스
 *
 * @author 최성우
 * @version $Id: ArKeywordServiceImpl.java 16316 2011-08-22 00:26:53Z giljae $
 */
@Service
public class ArKeywordServiceImpl extends GenericServiceImpl<ArKeyword, ArKeyword> implements ArKeywordService {

	@Autowired
	private ArKeywordDao arKeywordDao;

	@Autowired
	private ArKeywordModuleDao arKeywordModuleDao;

	@Autowired
	private ArKeywordModuleService arKeywordModuleService;
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#create(java.lang.Object)
	 */
	public ArKeyword create(ArKeyword arKeyword) {

		// 기존에 등록된 키워드인지 체크하여 없으면 insert, 있으면  arKeyword_module 테이블만 allDelete 후 insert
		if(!arKeywordDao.exists(arKeyword))
		{
			arKeywordDao.create(arKeyword);
		}else{
			ArKeywordModule arKeywordModule = new ArKeywordModule();
			arKeywordModule.setKeyword(arKeyword.getKeyword());
			arKeywordModule.setPortalId(arKeyword.getPortalId());
			
			arKeywordModuleDao.deleteByKeyword(arKeywordModule);
		}

		// 2. moduleCode만큼 반복해서 등록한다.
		String[] moduleCodes = arKeyword.getModuleCode().split(",");

		if (log.isDebugEnabled()) {
			log.debug("moduleCodes.length:"+moduleCodes.length);
		}
		
		ArKeywordModule submitArKeywordModule = null;
		for(int i = 0; i < moduleCodes.length; i++)
		{
			if (log.isDebugEnabled()) {
				log.debug("moduleCodes["+i+"]:"+moduleCodes[i]);
			}
			
			submitArKeywordModule = new ArKeywordModule();
			
			submitArKeywordModule.setKeyword	(arKeyword.getKeyword());
			submitArKeywordModule.setModuleCode	(moduleCodes[i]);
			submitArKeywordModule.setPortalId	(arKeyword.getPortalId());

			arKeywordModuleDao.create(submitArKeywordModule);
		}
		
		// 금지어 관련 cache를 삭제 후  Reload한다.
		arKeywordModuleService.prohibitWordCacheReload(arKeyword.getPortalId());
				
		return arKeyword;
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#read(java.io.Serializable)
	 */
	public ArKeyword read(ArKeyword arKeyword) {
		ArKeyword returnArKeyword = arKeywordDao.get(arKeyword);
		
		return returnArKeyword;
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#delete(java.io.Serializable)
	 */
	public void delete(ArKeyword arKeyword) {

		String keywords = arKeyword.getKeywords();
		
		if(null != keywords){
			String[] keyword = keywords.split(",");
	
			if (log.isDebugEnabled()) {
				log.debug("ArKeywordServiceImpl keywords:"+keywords);
				log.debug("keyword.length:"+keyword.length);
			}

			ArKeyword 		delArKeyword 		= new ArKeyword();
			ArKeywordModule delArKeywordModule 	= new ArKeywordModule();
			
			for(int i = 0; i < keyword.length; i++)
			{
				if (log.isDebugEnabled()) {
					log.debug("keyword["+i+"]:"+keyword[i]);
				}

				// 1. 먼저 금지어와 모듈 연결정보를 모두 삭제한다.
				delArKeywordModule = new ArKeywordModule();
				
				delArKeywordModule.setKeyword(keyword[i]);
				delArKeywordModule.setPortalId(arKeyword.getPortalId());
				
				arKeywordModuleDao.deleteByKeyword(delArKeywordModule);
				
				// 2. 금지어를 삭제한다.
				delArKeyword 		= new ArKeyword();
				
				delArKeyword.setKeyword(keyword[i]);
				delArKeyword.setPortalId(arKeyword.getPortalId());
				arKeywordDao.remove(delArKeyword);
			}

			// 금지어 관련 cache를 삭제 후  Reload한다.
			arKeywordModuleService.prohibitWordCacheReload(arKeyword.getPortalId());
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.abusereporting.service.ArKeywordService#list(com.lgcns.ikep4.support.abusereporting.search.ArAbuseSearchCondition)
	 */
	public List<String> list(ArAbuseSearchCondition arAbuseSearchCondition) {
		return arKeywordDao.list(arAbuseSearchCondition);
	}

}
