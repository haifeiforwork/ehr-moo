/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.user.code.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.support.user.code.dao.I18nMessageDao;
import com.lgcns.ikep4.support.user.code.model.I18nMessage;
import com.lgcns.ikep4.support.user.code.model.ItemType;
import com.lgcns.ikep4.support.user.code.model.LocaleCode;
import com.lgcns.ikep4.support.user.code.service.I18nMessageService;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * 다국어메시지 관리 서비스 구현체
 * 
 * @author 양새로훈(yang.sae@gmail.com)
 * @version $Id: I18nMessageServiceImpl.java 17355 2012-03-05 01:38:00Z unshj $
 */
@Service("i18nMessageService")
@Transactional
public class I18nMessageServiceImpl extends GenericServiceImpl<I18nMessage, String> implements I18nMessageService {

	@Autowired
	private I18nMessageDao i18nMessageDao;
	
	@Autowired
	private IdgenService idGenService;

	/**
	 * 삭제 예정
     * @deprecated
     * @see #create(List<I18nMessage> i18nMessageList)
     */
	public void create(Map<Integer, I18nMessage> i18nMessageMap) {
		
		Collection<I18nMessage> i18nMessageCollection = i18nMessageMap.values();
		Iterator<I18nMessage> i18nMessageItr = i18nMessageCollection.iterator();
		I18nMessage i18nMessage = new I18nMessage();
		
		while(i18nMessageItr.hasNext()) {
			i18nMessage = i18nMessageItr.next();
			i18nMessage.setMessageId(idGenService.getNextId());
			i18nMessageDao.create(i18nMessage);	
		}
	}
	
	/**
	 * 삭제 예정
     * @deprecated
     * @see #deleteMessagesByItemId
     */
	public void remove(Map<Integer, String> i18nMessageMap) {

		Collection<String> i18nMessageCollection = i18nMessageMap.values();
		Iterator<String> i18nMessageItr = i18nMessageCollection.iterator();
		String i18nMessageId = "";
		boolean exists = false;
		
		while(i18nMessageItr.hasNext()) {
			i18nMessageId = i18nMessageItr.next();
			exists = i18nMessageDao.exists(i18nMessageId);
			if(exists) {
				i18nMessageDao.remove(i18nMessageId);
			} 
		}
	}
	
	/**
	 * 삭제 예정
     * @deprecated
     * @see #update(List<I18nMessage> i18nMessageList)
     */
	public void update(Map<Integer, I18nMessage> i18nMessageMap) {

		Collection<I18nMessage> i18nMessageCollection = i18nMessageMap.values();
		Iterator<I18nMessage> i18nMessageItr = i18nMessageCollection.iterator();
		I18nMessage i18nMessage = new I18nMessage();
		
		while(i18nMessageItr.hasNext()) {
			i18nMessage = i18nMessageItr.next();
			boolean isIdExists = i18nMessageDao.exists(i18nMessage.getMessageId());
			if(isIdExists) {
				i18nMessageDao.update(i18nMessage);
			} else {
				i18nMessage.setMessageId(idGenService.getNextId());
				i18nMessageDao.create(i18nMessage);
			}
		}
	}
	
	/**
	 * 삭제 예정
     * @deprecated
     * @see #listMessagesByItemId
     */
	public List<I18nMessage> get(I18nMessage i18nMessage) {
		return i18nMessageDao.get(i18nMessage);
	}
	
	
	/**
	 * 삭제 예정
     * @deprecated
     * @see #listMessagesByItemId
     */
	public List<I18nMessage> list(Map<String, String> messageParam) {
		return  i18nMessageDao.selectMessageByItemId(messageParam);
	}

	
	/**
	 * 삭제 예정
     * @deprecated
     * 
     */
	@SuppressWarnings("unchecked")
	public List<ItemType> selectItemTypeAll() {

		return i18nMessageDao.selectItemTypeAll();
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#exists(java.io.Serializable)
	 */
	public boolean exists(String code) {

		return i18nMessageDao.exists(code);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.code.service.I18nMessageService#removeLocaleCode(java.lang.String)
	 */
	public void removeLocaleCode(String localeCode) {
		
		i18nMessageDao.removeLocaleCode(localeCode);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.code.service.I18nMessageService#selectLocaleAll()
	 */
	@SuppressWarnings("unchecked")
	public List<LocaleCode> selectLocaleAll() {

		return i18nMessageDao.selectLocaleAll();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.code.service.I18nMessageService#create(java.util.List)
	 */
	public void create(List<I18nMessage> i18nMessageList) {
		for(I18nMessage i18nMessage : i18nMessageList) {
			i18nMessage.setMessageId(idGenService.getNextId());
			//i18nMessageDao.create(i18nMessage);	
		}
		
		for(I18nMessage i18nMessage : i18nMessageList) {
			//i18nMessage.setMessageId(idGenService.getNextId());
			i18nMessageDao.create(i18nMessage);	
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.code.service.I18nMessageService#update(java.util.List)
	 */
	public void update(List<I18nMessage> i18nMessageList) {

		for(I18nMessage i18nMessage : i18nMessageList) {
			boolean isIdExists = i18nMessageDao.exists(i18nMessage.getMessageId());
			if(isIdExists) {
				i18nMessageDao.update(i18nMessage);
			} else {
				i18nMessage.setMessageId(idGenService.getNextId());
				i18nMessageDao.create(i18nMessage);	
			}
		}
	}
	
	public void updateMenuMessage(List<I18nMessage> i18nMessageList) {

		for(I18nMessage i18nMessage : i18nMessageList) {
				i18nMessageDao.updateMenuMessage(i18nMessage);
			
		}
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.code.service.I18nMessageService#makeInitLocaleList(java.lang.String)
	 */
	public List<I18nMessage> makeInitLocaleList(String fieldNameToken)
	{
		//ex) fieldNameToken = "portletName,portletDescription"
		List<LocaleCode> localeCodeList = this.selectLocaleAll();
		I18nMessage i18nMessage;
		List<I18nMessage> i18nMessageList = new ArrayList<I18nMessage>();

		for (String fieldName : Arrays.asList(fieldNameToken.split(","))) {
			for (LocaleCode localeCode : localeCodeList) {
				i18nMessage = new I18nMessage();
				i18nMessage.setIndex(Integer.parseInt(localeCode.getSortOrder()));
				i18nMessage.setLocaleCode(localeCode.getLocaleCode());
				i18nMessage.setFieldName(fieldName);
				i18nMessageList.add(i18nMessage);
			}
		}
		
		return i18nMessageList;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.code.service.I18nMessageService#fillMandatoryField(java.util.List, java.lang.String, java.lang.String)
	 */
	public List<I18nMessage> fillMandatoryField(List<I18nMessage> i18nMessageList,String itemTypeCode,String  itemId )
	{
		User user = (User) this.getRequestAttribute("ikep.user");
		
		for(I18nMessage i18nMessage : i18nMessageList) {
			i18nMessage.setItemId(itemId);
			i18nMessage.setItemTypeCode(itemTypeCode);
			i18nMessage.setRegisterId(user.getUserId());
			i18nMessage.setRegisterName(user.getUserName());
			i18nMessage.setUpdaterId(user.getUserId());
			i18nMessage.setUpdaterName(user.getUserName());
		}
		return i18nMessageList;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.code.service.I18nMessageService#fillMandatoryFieldWithMessage(java.util.List, java.lang.String, java.lang.String, java.lang.String)
	 */
	public List<I18nMessage> fillMandatoryFieldWithMessage(List<I18nMessage> i18nMessageList,String itemTypeCode,String  itemId,String itemMessage )
	{
		User user = (User) this.getRequestAttribute("ikep.user");
		
		for(I18nMessage i18nMessage : i18nMessageList) {
			i18nMessage.setItemId(itemId);
			i18nMessage.setItemTypeCode(itemTypeCode);
			i18nMessage.setRegisterId(user.getUserId());
			i18nMessage.setRegisterName(user.getUserName());
			i18nMessage.setUpdaterId(user.getUserId());
			i18nMessage.setUpdaterName(user.getUserName());
			
			i18nMessage.setItemMessage(itemMessage);
		}
		return i18nMessageList;
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.code.service.I18nMessageService#makeExistLocaleList(java.lang.String, java.lang.String, java.lang.String)
	 */
	public List<I18nMessage> makeExistLocaleList(String itemTypeCode, String itemId, String fieldNameToken)
	{
		//ex) fieldNameToken = "portletName,portletDescription"
		
		Map<String,String> messageParam = new HashMap <String,String>();
		messageParam.put("itemTypeCode", itemTypeCode);
		messageParam.put("itemId", itemId);
		
		List<LocaleCode> localeCodeList = this.selectLocaleAll();
		
		List<I18nMessage> i18nMessageList = i18nMessageDao.selectMessageByItemId(messageParam);

		for (String fieldName : Arrays.asList(fieldNameToken.split(","))) {

			for (LocaleCode localeCode : localeCodeList) {
				boolean hasLocale = false;
				for (I18nMessage i18nMessage : i18nMessageList) {
					if(fieldName.equals(i18nMessage.getFieldName()) && localeCode.getLocaleCode().equals(i18nMessage.getLocaleCode()))
					{
						i18nMessage.setIndex(Integer.parseInt(localeCode.getSortOrder()));
						hasLocale = true;
						break;
					}
				}
				if(!hasLocale)
				{
					I18nMessage i18nMessage = new I18nMessage();
					i18nMessage.setIndex(Integer.parseInt(localeCode.getSortOrder()));
					i18nMessage.setLocaleCode(localeCode.getLocaleCode());
					i18nMessage.setFieldName(fieldName);
					i18nMessageList.add(i18nMessage);
				}
			}
		}
		return i18nMessageList;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.code.service.I18nMessageService#deleteMessagesByItemId(java.lang.String, java.lang.String)
	 */
	public void deleteMessagesByItemId(String itemTypeCode, String  itemId ){
		Map<String,String> messageParam = new HashMap <String,String>();
		messageParam.put("itemTypeCode", itemTypeCode);
		messageParam.put("itemId", itemId);
		
		i18nMessageDao.removeMessagesByItemId(messageParam);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.code.service.I18nMessageService#listMessagesByItemId(java.util.Map)
	 */
	public List<I18nMessage> listMessagesByItemId(Map<String,String> messageParam) {

		return  i18nMessageDao.selectMessageByItemId(messageParam);
	}
	
	/*
	 * 
	 */
	public List<I18nMessage> listMessagesByItemIdFieldName(Map<String, String> messageParam) {

		return i18nMessageDao.selectMessagesByItemIdFieldName(messageParam);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.code.service.I18nMessageService#readMessagesId(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public String readMessagesId(String itemTypeCode, String itemId, String fieldName, String localeCode) {
		
		String messagesId = i18nMessageDao.getMessagesId(itemTypeCode, itemId, fieldName, localeCode);
		
		if(messagesId == null) {
			messagesId = "";
		}
		
		return messagesId;
	}

	public Object getRequestAttribute(String name) {
		return RequestContextHolder.currentRequestAttributes().getAttribute(name, RequestAttributes.SCOPE_SESSION);
	}

	public void remove(String messageId) {
		// TODO Auto-generated method stub
		
	}
}
