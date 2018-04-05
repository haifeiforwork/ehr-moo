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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.icu.util.StringTokenizer;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.abusereporting.dao.ArAbuseHistoryDao;
import com.lgcns.ikep4.support.abusereporting.dao.ArHistoryKeyDao;
import com.lgcns.ikep4.support.abusereporting.model.ArAbuseHistory;
import com.lgcns.ikep4.support.abusereporting.model.ArAbuseQueryReturn;
import com.lgcns.ikep4.support.abusereporting.model.ArHistoryKey;
import com.lgcns.ikep4.support.abusereporting.model.ArKeywordModule;
import com.lgcns.ikep4.support.abusereporting.model.ArModule;
import com.lgcns.ikep4.support.abusereporting.search.ArAbuseSearchCondition;
import com.lgcns.ikep4.support.abusereporting.service.ArAbuseHistoryService;
import com.lgcns.ikep4.support.abusereporting.service.ArKeywordModuleService;
import com.lgcns.ikep4.support.abusereporting.service.ArModuleService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.StringUtil;


/**
 * 
 * ArAbuseHistoryService 구현 클래스
 *
 * @author 최성우
 * @version $Id: ArAbuseHistoryServiceImpl.java 16316 2011-08-22 00:26:53Z giljae $
 */
@Service
public class ArAbuseHistoryServiceImpl extends GenericServiceImpl<ArAbuseHistory, ArAbuseHistory> implements ArAbuseHistoryService {

	@Autowired
	private ArAbuseHistoryDao arAbuseHistoryDao;

	@Autowired
	private ArHistoryKeyDao arHistoryKeyDao;

	@Autowired
	private ArKeywordModuleService arKeywordModuleService;

	@Autowired
	private ArModuleService arModuleService;

	@Autowired
	private IdgenService idgenService;
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#create(java.lang.Object)
	 */
	public ArAbuseHistory create(ArAbuseHistory arAbuseHistory) {
		
		return arAbuseHistoryDao.create(arAbuseHistory);
	}


	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#read(java.io.Serializable)
	 */
	public ArAbuseHistory read(ArAbuseHistory paramArAbuseHistory) {
		ArAbuseHistory arAbuseHistory = arAbuseHistoryDao.get(paramArAbuseHistory);
		
		return arAbuseHistory;
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.abusereporting.service.ArAbuseHistoryService#checkAndSaveProhibitWord(com.lgcns.ikep4.support.abusereporting.model.ArAbuseHistory, com.lgcns.ikep4.support.user.member.model.User)
	 */
	public void checkAndSaveProhibitWord(ArAbuseHistory arAbuseHistory, User user){
		if (log.isDebugEnabled()) {
			log.debug("checkAndSaveProhibitWord Start~");
			log.debug("arAbuseHistory:"+arAbuseHistory);
		}
				
		// moduleCode에 해당하는 moduleName 조회
		ArModule arModule = arModuleService.read(arAbuseHistory.getModuleCode());
		
		// contents안의 금지어 조회
		String filteredKeyword = this.getCheckProhibitWordList(arAbuseHistory);
		
		// contents안에 금지어 존재여부
		int isDetected = 0;
		if(filteredKeyword.length() > 0){
			isDetected = 1;
		}

		// 금지어가 있거나, 외부연동 모듈의 경우  history에 쌓는다.
		if(1 == isDetected || 1 == arModule.getIsExternal())
		{
			/*
			 * idgen Service에서 id생성하여 넣기
			 */
			String historyId = idgenService.getNextId();
			
			arAbuseHistory.setHistoryId		(historyId);
			arAbuseHistory.setModuleName	(arModule.getModuleName());
			arAbuseHistory.setKeyword		(filteredKeyword);
			arAbuseHistory.setIsDetected	(isDetected);
			arAbuseHistory.setRegisterId	(user.getUserId());
			arAbuseHistory.setRegisterName	(user.getUserName());
			arAbuseHistory.setPortalId		(user.getPortalId());
			
			arAbuseHistoryDao.create(arAbuseHistory);
			
			// IKEP4_AR_HISTORY_KEY에 keyword를  검색된 만큼 저장한다.
			if(1 == isDetected)
			{
				ArHistoryKey arHistoryKey = null;
				
				StringTokenizer token = new StringTokenizer(filteredKeyword,"|");
				
				while(token.hasMoreTokens()) {
					arHistoryKey = new ArHistoryKey();
					
					arHistoryKey.setHistoryId	(historyId);
					arHistoryKey.setKeyword		(token.nextToken());
					
					arHistoryKeyDao.create(arHistoryKey);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.abusereporting.service.ArAbuseHistoryService#listBySearchconditionForReport(com.lgcns.ikep4.support.abusereporting.search.ArAbuseSearchCondition)
	 */
	public SearchResult<ArAbuseHistory> listBySearchconditionForReport(ArAbuseSearchCondition searchCondition) {

		Integer count = arAbuseHistoryDao.countBySearchconditionForReport(searchCondition);

		searchCondition.terminateSearchCondition(count);

		SearchResult<ArAbuseHistory> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<ArAbuseHistory>(searchCondition);

		} else {

			List<ArAbuseHistory> arAbuseHistoryList = arAbuseHistoryDao.listBySearchconditionForReport(searchCondition);
			
			Iterator<ArAbuseHistory> it = arAbuseHistoryList.iterator();
			
			while(it.hasNext()){
				ArAbuseHistory arAbuseHistory = (ArAbuseHistory)it.next();
				
				String changedContents = this.changeContents(arAbuseHistory.getKeyword(), arAbuseHistory.getContents(), searchCondition.getKeyword());
				
				arAbuseHistory.setContents(changedContents);
			}

			searchResult = new SearchResult<ArAbuseHistory>(arAbuseHistoryList, searchCondition);
		}

		return searchResult;
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.abusereporting.service.ArAbuseHistoryService#listBySearchconditionForStatistics(com.lgcns.ikep4.support.abusereporting.search.ArAbuseSearchCondition)
	 */
	public List<ArAbuseQueryReturn> listBySearchconditionForStatistics(ArAbuseSearchCondition arAbuseSearchCondition) {
		return arAbuseHistoryDao.listBySearchconditionForStatistics(arAbuseSearchCondition);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.abusereporting.service.ArAbuseHistoryService#getCheckProhibitWordList(com.lgcns.ikep4.support.abusereporting.model.ArAbuseHistory)
	 */
	public String getCheckProhibitWordList(ArAbuseHistory arAbuseHistory){
		if (log.isDebugEnabled()) {
			log.debug("getCheckedProhibitWordList Start~");
			log.debug("arAbuseHistory:"+arAbuseHistory);
		}
		ArKeywordModule arKeywordModule = new ArKeywordModule();
		arKeywordModule.setModuleCode	(arAbuseHistory.getModuleCode());
		arKeywordModule.setPortalId		(arAbuseHistory.getPortalId());
		
		String keywordList = arKeywordModuleService.getProhibitWordCacheByModuleCode(arKeywordModule);
						
		// contents안의 금지어 조회해서 리턴 
		return filterText(keywordList, arAbuseHistory.getContents());
	}

	/*
	 * *************************************************************************************
	 * private 함수 -Start
	 * *************************************************************************************
	 * 
	 */

	/**
	 * Contents에서 금지어 조회해서 리턴한다.
	 * 
	 * @return String
	 */
    public String filterText(String keywordList, String paramStext) {
    	String sText = paramStext;
		if (log.isDebugEnabled()) {
			log.debug("filterText Start~");
			log.debug("keywordList:"+keywordList);
			log.debug("sText:"+sText);
		}
        Pattern p = Pattern.compile(keywordList, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(sText);

        StringBuffer sb = new StringBuffer();
        while (m.find()) {
        	if(0 != sb.length()){
        		sb.append("|");
        	}
        	sb.append(m.group());
        	sText = sText.replaceAll(m.group(), "");
        	m = p.matcher(sText);
        }

        return sb.toString();
    }

	/**
	 * Contents에서 금지어 조회해서 span tag를 붙힌다.
	 * 
	 * @return String
	 */
    public String changeContents(String keywordList, String sText, String keyword) {
		if (log.isDebugEnabled()) {
			log.debug("changeContents Start~");
			log.debug("keywordList:"+keywordList);
			log.debug("sText:"+sText);
		}
		String returnString = "";
		if(null != keywordList){
	        Pattern p = Pattern.compile(keywordList, Pattern.CASE_INSENSITIVE);
	        Matcher m = p.matcher(sText);
	
	        StringBuffer sb = new StringBuffer();
	        while (m.find()) {
	            //System.out.println(m.group());
	        	m.appendReplacement(sb, "<span class='colorPoint'>"+m.group()+"</span>");
	        }
	        m.appendTail(sb);
	        
	        //System.out.println(sb.toString());
	        returnString = sb.toString();
		}
		else
		{
			returnString = sText;
		}
		
		// 검색어는 파란색 Bold로 표시.
		returnString = StringUtil.replace(returnString, keyword, "<span class='colorBlue'>"+keyword+"</span>");
		
		return returnString;
    }
    
	/*
	 * *************************************************************************************
	 * private 함수 -End
	 * *************************************************************************************
	 */
}
