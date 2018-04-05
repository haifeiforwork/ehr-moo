/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.user.code.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.support.user.code.model.I18nMessage;


/**
 * 메시지 ID 관리 DAO 정의
 * 
 * @author 양새로훈(yang.sae@gmail.com)
 * @version $Id: I18nMessageDao.java 17355 2012-03-05 01:38:00Z unshj $
 */
public interface I18nMessageDao extends GenericDao<I18nMessage, String> {

	
	/**
	 * 삭제예정 : 특정 itemTypeCode ,itemId ,field 로 메세지 목록 의 정보를 가져옴
	 * @param i18nMessage
	 * @deprecated
	 * @see list
	 * @return
	 */
	public List<I18nMessage> get(I18nMessage i18nMessage);

	/**
	 * 삭제예정
	 * ItemType Code 목록을 가져옴
	 * @deprecated
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List selectItemTypeAll();
	
	/*
	 * menu_message 업데이트 
	 * 
	 */
	public void updateMenuMessage(I18nMessage i18nMessage);
	
	/**
	 * itemType,itemId,fieldName 으로  메시지 조회
	 * 
	 * @param messageParam 메시지 조회 조건
	 * @return
	 */
	public List<I18nMessage> selectMessagesByItemIdFieldName(Map<String,String> messageParam);
	
	
	/**
	 * itemType,itemId 로  메시지 조회
	 * 
	 * @param messageParam 메시지 조회 조건
	 * @return
	 */
	public List<I18nMessage> selectMessageByItemId(Map<String,String> messageParam);

	/**
	 * 중복되는 메시지 ID를 확인
	 * 
	 * @param id 중복 확인을 위한 메시지 ID
	 * @return
	 */
	public String checkId(String id);

	/**
	 * Locale Code 목록을 가져옴
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List selectLocaleAll();
	
	/**
	 * LocaleCode 삭제시 해당되는 메시지 전부 삭제
	 * 
	 * @param localeCode 삭제할 로케일 코드
	 */
	public void removeLocaleCode(String localeCode);
	
	/**
	 * Item에 속한 message 모두 삭제
	 * 
	 * @param messageParam 삭제 조건
	 */
	public void removeMessagesByItemId(Map<String,String> messageParam);

	/**
	 * 메시지 ID를 조회
	 * 
	 * @param itemTypeCode 메시지가 속한 아이템 타입 코드
	 * @param itemId 아이템 ID
	 * @param fieldName 메시지가 들어가는 필드명
	 * @param localeCode 메시지의 로케일 코드
	 * @return String
	 */
	public String getMessagesId(String itemTypeCode, String itemId, String fieldName, String localeCode);
}
