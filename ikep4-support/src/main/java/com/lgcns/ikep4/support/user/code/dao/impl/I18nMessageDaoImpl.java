/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.user.code.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.user.code.dao.I18nMessageDao;
import com.lgcns.ikep4.support.user.code.model.I18nMessage;


/**
 * 다국어 메세지 Dao 구현체
 * 
 * @author 양새로훈(yang.sae@gmail.com)
 * @version $Id: I18nMessageDaoImpl.java 17355 2012-03-05 01:38:00Z unshj $
 */
@Repository("i18nMessageDao")
public class I18nMessageDaoImpl extends GenericDaoSqlmap<I18nMessage, String> implements I18nMessageDao {


	/* 삭제예정
	 * @see com.lgcns.ikep4.support.user.code.dao.I18nMessageDao#selectAll()
	 */
	public List<I18nMessage> get(I18nMessage i18nMessage) {
		return null;
	}
	

	/* 삭제예정
	 * @see com.lgcns.ikep4.support.user.code.dao.I18nMessageDao#selectItemTypeAll()
	 */
	@SuppressWarnings("rawtypes")
	public List selectItemTypeAll() {

		return sqlSelectForList("support.user.code.dao.I18nMessage.selectItemTypeAll");
	}
	
	/* 삭제예정
	 * @see com.lgcns.ikep4.support.user.code.dao.I18nMessageDao#selectAll()
	 */
	public List<I18nMessage> selectAll() {
		return null;
	}
	

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.code.dao.I18nMessageDao#selectMessagesByItemIdFieldName(java.util.Map)
	 */
	public List<I18nMessage> selectMessagesByItemIdFieldName(Map<String,String> messageParam) {

		return sqlSelectForList("support.user.code.dao.I18nMessage.selectMessagesByItemIdFieldName", messageParam);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.code.dao.I18nMessageDao#selectMessageByItemId(java.util.Map)
	 */
	public List<I18nMessage> selectMessageByItemId(Map<String,String> messageParam) {

		return sqlSelectForList("support.user.code.dao.I18nMessage.selectMessageByItemId", messageParam);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public String create(I18nMessage i18nMessage) {

		return (String) sqlInsert("support.user.code.dao.I18nMessage.insert", i18nMessage);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable)
	 */
	public boolean exists(String id) {

		String count = (String) sqlSelectForObject("support.user.code.dao.I18nMessage.checkId", id);

		return !count.equals("0");
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.code.dao.I18nMessageDao#checkId(java.lang.String)
	 */
	public String checkId(String id) {

		return (String) sqlSelectForObject("support.user.code.dao.I18nMessage.checkId", id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(I18nMessage i18nMessage) {

		sqlUpdate("support.user.code.dao.I18nMessage.update", i18nMessage);
	}
	
	public void updateMenuMessage(I18nMessage i18nMessage){
		
		sqlUpdate("support.user.code.dao.I18nMessage.updateMenuMessage",i18nMessage);
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.code.dao.I18nMessageDao#removeLocaleCode(java.lang.String)
	 */
	public void removeLocaleCode(String localeCode) {
		
		sqlDelete("support.user.code.dao.I18nMessage.deleteLocaleCode", localeCode);
	}


	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.code.dao.I18nMessageDao#selectLocaleAll()
	 */
	@SuppressWarnings("rawtypes")
	public List selectLocaleAll() {
		
		return sqlSelectForList("support.user.code.dao.I18nMessage.selectLocaleAll");
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.code.dao.I18nMessageDao#removeMessagesByItemId(java.util.Map)
	 */
	public void removeMessagesByItemId(Map<String,String> messageParam){
		sqlDelete("support.user.code.dao.I18nMessage.deleteMessagesByItemId", messageParam);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.code.dao.I18nMessageDao#getMessagesId(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public String getMessagesId(String itemTypeCode, String itemId, String fieldName, String localeCode) {
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("itemTypeCode", itemTypeCode);
		map.put("itemId", itemId);
		map.put("fieldName", fieldName);
		map.put("localeCode", localeCode);
		
		return (String) sqlSelectForObject("support.user.code.dao.I18nMessage.getMessagesId", map);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public I18nMessage get(String id) {
		return null;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable)
	 */
	public void remove(String id) {

	}
}
