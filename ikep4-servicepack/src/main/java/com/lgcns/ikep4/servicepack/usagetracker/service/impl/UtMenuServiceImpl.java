/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.servicepack.usagetracker.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.servicepack.usagetracker.dao.UtMenuDao;
import com.lgcns.ikep4.servicepack.usagetracker.model.UtMenu;
import com.lgcns.ikep4.servicepack.usagetracker.search.UtSearchCondition;
import com.lgcns.ikep4.servicepack.usagetracker.service.UtMenuService;
import com.lgcns.ikep4.support.user.code.dao.I18nMessageDao;
import com.lgcns.ikep4.support.user.code.model.I18nMessage;
import com.lgcns.ikep4.support.user.code.service.I18nMessageService;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.StringUtil;

/**
 * 사용자현황통계
 *
 * @author ihko11
 * @version $Id: UtMenuServiceImpl.java 17353 2012-03-05 01:17:40Z unshj $
 */
@Service
@Transactional
public class UtMenuServiceImpl extends GenericServiceImpl<UtMenu, String> implements UtMenuService {
	protected final Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private UtMenuDao utMenuDao;
	

	
	@Autowired
	private I18nMessageService i18nMessageService;
	
	@Autowired
	public UtMenuServiceImpl(UtMenuDao dao) {
		super(dao);
		this.utMenuDao = dao;
	}
	
	/**
	 * 삭제
	 */
	@Override
	public void delete(String id) {
		utMenuDao.remove(id);
		i18nMessageService.deleteMessagesByItemId("UT",id);
	}

	
	/**
	 * 상세
	 */
	@Override
	public UtMenu read(String id) {
		return utMenuDao.get(id);
	}
	
	
	/**
	 * 메뉴 intercepter
	 * @param utMenu
	 * @return
	 */
	public UtMenu getMenu(UtMenu utMenu){
		return utMenuDao.getMenu(utMenu);
	}
	
	
	/**
	 * 메뉴 intercepter
	 * @param utMenu
	 * @return
	 */
	public List<UtMenu> getMenuList(UtMenu utMenu){
		return utMenuDao.getMenuList(utMenu);
	}
	
	
	@Override
	public boolean exists(String id) {
		return utMenuDao.exists(id);
	}

	/**
	 * 생성
	 */
	@Override
	public String create(UtMenu utMenu) {
		return utMenuDao.create(utMenu);
	}

	/**
	 * 수정
	 */
	@Override
	public void update(UtMenu utMenu) {
		utMenuDao.update(utMenu);
	}
	
	/**
	 * 모든값저장
	 * @param utMenuList
	 */
	public void saveOrUpdate(List<UtMenu> utMenuList) {
	}
	
	
	/**
	 * 선택된값 삭제
	 * @param menuIds
	 */
	public void removeCheckedAll(String[] menuIds) {
	
		for (int i = 0; i < menuIds.length; i++) {
			utMenuDao.remove(menuIds[i]);
			i18nMessageService.deleteMessagesByItemId("UT",menuIds[i]);
		}
		
	}
	
	/**
	 * 메뉴 수정 및 메시지 수정 처리 
	 */
	public void updateAndMessaging(UtMenu utMenu){
		try{
			//메뉴 수정 처리
			this.update(utMenu);
			
			//메시지 수정처리
			
			//메시징 처리 (메시지 리스트 만들어!!)
	    	List<I18nMessage> i18nMessageList = utMenu.getI18nMessageList();
		
	    	List<I18nMessage> i18nMessageListForUpdate = new ArrayList<I18nMessage>();
	    	List<I18nMessage> i18nMessageListForCreate = new ArrayList<I18nMessage>();
	    	for(int i=0; i< i18nMessageList.size(); i++){
	    	
	    		I18nMessage i18nMessage = new I18nMessage();
	    		
	    		i18nMessage = i18nMessageList.get(i);
	    		
	    		i18nMessage.setItemId(utMenu.getMenuId());
	    		i18nMessage.setUpdaterId(utMenu.getRegisterId());
	    		i18nMessage.setUpdaterName(utMenu.getRegisterName());
	    		
	    		if(i18nMessage.getMessageId().equals("")){
	    			i18nMessage.setItemTypeCode("UT");
	    			i18nMessage.setRegisterId(utMenu.getRegisterId());
	    			i18nMessage.setRegisterName(utMenu.getRegisterName());
	    			i18nMessageListForCreate.add(i18nMessage);
	    		}else{
	    			
	    			i18nMessageListForUpdate.add(i18nMessage);
	    		}	
	    	}

	    	i18nMessageService.create(i18nMessageListForCreate);
	    	i18nMessageService.updateMenuMessage(i18nMessageListForUpdate);
	    	
			
		}catch(Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}
	}
	
	/**
	 * 메뉴 추가 및 메시징 처리 
	 */
	public void createAndMessaging(UtMenu utMenu){
		
		try{			
			//추가 처리 
				this.create(utMenu);

				
				//메시징 처리 (메시지 리스트 만들어!!)
		    	List<I18nMessage> i18nMessageList = utMenu.getI18nMessageList();
			
		    	
		    	List<I18nMessage> i18nMessageListForCreate = new ArrayList<I18nMessage>();
		    	
		    	for(int i=0; i< i18nMessageList.size(); i++){
		    	
		    		I18nMessage i18nMessage = new I18nMessage();
		    		
		    		i18nMessage = i18nMessageList.get(i);
		    		
		    		i18nMessage.setItemId(utMenu.getMenuId());
		    		i18nMessage.setUpdaterId(utMenu.getRegisterId());
		    		i18nMessage.setUpdaterName(utMenu.getRegisterName());
	    			i18nMessage.setItemTypeCode("UT");
	    			i18nMessage.setRegisterId(utMenu.getRegisterId());
	    			i18nMessage.setRegisterName(utMenu.getRegisterName());
	    			
	    			i18nMessageListForCreate.add(i18nMessage);
		    		
		    	}

		    	i18nMessageService.create(i18nMessageListForCreate);
		    	

					
		}catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}

	}
	
	
	/**
	 * 메뉴리스트
	 */
	public SearchResult<UtMenu> listBySearchCondition(UtSearchCondition searchCondition) {
		SearchResult<UtMenu> searchResult = null;

		Integer count = this.utMenuDao.countBySearchCondition(searchCondition);
		searchCondition.terminateSearchCondition(count);

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<UtMenu>(searchCondition);
		} else {
			List<UtMenu> surveyItemList = utMenuDao.listBySearchCondition(searchCondition);
			searchResult = new SearchResult<UtMenu>(surveyItemList, searchCondition);
		}

		return searchResult;
	}
	
	public List<UtMenu> comboList(UtMenu utMenu){
		return utMenuDao.comboList(utMenu);
	}
	
	public List<UtMenu> mobileComboList(UtMenu utMenu){
		return utMenuDao.mobileComboList(utMenu);
	}
	
	public List<UtMenu> mobileSubComboList(UtMenu utMenu){
		return utMenuDao.mobileSubComboList(utMenu);
	}
	

	
	public boolean existsURL(UtMenu utMenu) {
		//return utMenuDao.existsURL(menuUrl);
		boolean isExistsUrl = false;

		List <UtMenu> dbInfoList = this.getMenuList(utMenu);

		return isExistsUrl(dbInfoList, utMenu.getRequestParameter());
	}
	
	
	private boolean isExistsUrl(List <UtMenu> dbInfoList,String queryString ){
		boolean isExistsUrl = false;
		
		queryString = StringUtil.nvl(queryString,"");

		//조회결과가 없으면 공백리턴.
		if(dbInfoList==null || dbInfoList.size() == 0){
			return isExistsUrl;
		}
		
		
		/*
		 * 사용자 파라미터가 있는경우만 체크 
		 * DB에 조회된 결과 값이 한건이고
		 * DB에 등록된 파라미터정보가 없으면 메뉴아이디 리턴.
		 */ 

			for(int i=0;i < dbInfoList.size();i++){
				UtMenu menu = dbInfoList.get(i);
				int patternCnt = 0;
				String param = menu.getRequestParameter();
				if(!StringUtil.isEmpty(param)){
					String[] menuParam = param.trim().split("&");
					//DB에 등록된 파라미터를 배열로 분리.
					for(int j=0;j < menuParam.length;j++){
						// QueryString으로 들어온  문자에 해당 요소가 있는지 확인 후 Cnt++
						if(queryString.indexOf(menuParam[j]) > -1){
							patternCnt++;
						}
					}
					//신규등록된 파라미터가 DB에 등록된 파라미터와 동일하면 리턴 true
					if(patternCnt==menuParam.length && menuParam.length == queryString.split("&").length){
						isExistsUrl = true;
						break;
					}
				}
			}
		return isExistsUrl;
	}
}
