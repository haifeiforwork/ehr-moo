/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.user.code.service;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.support.user.code.model.I18nMessage;
import com.lgcns.ikep4.support.user.code.model.LocaleCode;


/**
 * 메시지 관리 서비스
 * 
 * @author 양새로훈(yang.sae@gmail.com)
 * @version $Id: I18nMessageService.java 17355 2012-03-05 01:38:00Z unshj $
 */
public interface I18nMessageService extends GenericService<I18nMessage, String> {

	/**
	 * 삭제예정 메세지 목록 조회 messageParam 목록 조회 정보(itemType,itemId,fieldName)
	 * 
	 * @param localeCode
	 * @deprecated
	 * @see listMessagesByItemId
	 */
	public List<I18nMessage> get(I18nMessage i18nMessage);

	/**
	 * 삭제예정 메세지 목록 조회 messageParam 목록 조회 정보(itemType,itemId,fieldName)
	 * 
	 * @param localeCode
	 * @deprecated
	 * @see listMessagesByItemId
	 */
	public List<I18nMessage> list(Map<String, String> messageParam);

	/**
	 * 삭제예정 메세지 단건 삭제
	 * 
	 * @param messageId
	 */
	public void remove(String messageId);

	/**
	 * 삭제예정
	 * 
	 * @deprecated
	 * @see #create(List<I18nMessage> i18nMessageList)
	 */
	public void create(Map<Integer, I18nMessage> i18nMessageMap);

	/**
	 * 삭제예정
	 * 
	 * @deprecated
	 * @see #update(List<I18nMessage> i18nMessageList)
	 */
	public void update(Map<Integer, I18nMessage> i18nMessageMap);

	/**
	 * 삭제예정
	 * 
	 * @deprecated
	 * @see #deleteMessagesByItemId
	 */
	public void remove(Map<Integer, String> i18nMessageMap);

	/**
	 * Locale Code 목록을 가져옴
	 * 
	 * @return
	 */
	public List<LocaleCode> selectLocaleAll();

	/**
	 * LocaleCode 삭제시 해당되는 메시지 전부 삭제
	 * 
	 * @param localeCode 삭제할 로케일 코드
	 */
	public void removeLocaleCode(String localeCode);

	/**
	 * 사용하는 로케일 정보를 기준으로 메세지리스트 목록을 반환한다
	 * 
	 * @param fieldNameToken "field1,field2" , 구분자로 다국어로 저장할 필드명
	 * @return locale 정보를 field별로 List형태로 리턴.
	 */
	public List<I18nMessage> makeInitLocaleList(String fieldNameToken);

	/**
	 * 사용하는 로케일 정보를 기준으로 디비에서 기등록된 메세지리스트 목록을 반환한다
	 * 
	 * @param itemTypeCode 아이템 타입 코드
	 * @param itemId 다국어를 사용할 게시물의 아이디
	 * @param fieldNameToken "field1,field2" , 구분자로 다국어로 저장할 필드명
	 * @return 디비에서 기등록된 메세지리스트 목록을 반환
	 */
	public List<I18nMessage> makeExistLocaleList(String itemTypeCode, String itemId, String fieldNameToken);

	/**
	 * 다국어 메세지 목록을 저장한다.
	 * 
	 * @param i18nMessageList 다국어 메세지 목록
	 */
	public void create(List<I18nMessage> i18nMessageList);

	/**
	 * 다국어 메세지 목록을 업데이트.
	 * 
	 * @param i18nMessageList 다국어 메세지 목록
	 */
	public void update(List<I18nMessage> i18nMessageList);
	
	/**
	 * 다국어 메세지 목록을 업데이트(메뉴)
	 * 
	 * @param i18nMessageList 다국어 메세지 목록
	 */
	public void updateMenuMessage(List<I18nMessage> i18nMessageList);

	/**
	 * 등록 수정을 위해 리스트에 비어있는 필수정보를 담는다 (필수정보:itemTypeCode,itemId,사용자정보)
	 * 
	 * @param i18nMessageList 다국어 메세지 목록
	 * @param itemTypeCode 아이템 타입코드
	 * @param itemId 아이템 아이디
	 * @return 메세지 등록을 위해 필요한 필수 정보를 채운 메세지 목록을 반환한다
	 */
	public List<I18nMessage> fillMandatoryField(List<I18nMessage> i18nMessageList, String itemTypeCode, String itemId);

	/**
	 * 등록 수정을 위해 리스트에 메세지와,비어있는 필수정보를 담는다 (필수정보:itemTypeCode,itemId,사용자정보)
	 * 
	 * @param i18nMessageList 다국어 메세지 목록
	 * @param itemTypeCode 아이템 타입코드
	 * @param itemId 아이템 아이디
	 * @param itemMessage 메세지
	 * @return 메세지를 포함한, 메세지 등록을 위해 필요한 필수 정보를 채운 List<I18nMessage> 를 반환한다
	 */
	public List<I18nMessage> fillMandatoryFieldWithMessage(List<I18nMessage> i18nMessageList, String itemTypeCode,
			String itemId, String itemMessage);

	/**
	 * 한 게시물에 속한 다국어 메세지 정보를 모두 삭제한다.
	 * 
	 * @param itemTypeCode 아이템 타입코드
	 * @param itemId 아이템 아이디
	 */
	public void deleteMessagesByItemId(String itemTypeCode, String itemId);

	/**
	 * 한 게시물에 속한 다국어 메세지 정보를 모두 조회한다.
	 * 
	 * @param messageParam itemTypeCode 아이템 타입코드,itemId 아이템 아이디
	 * @return 메세지 목록
	 */
	public List<I18nMessage> listMessagesByItemId(Map<String, String> messageParam);

	/**
	 * 다국어 메세지 ID정보를 조회한다.
	 * 
	 * @param itemTypeCode 아이템 타입코드
	 * @param itemId 아이템 아이디
	 * @param fieldName 필드 이름
	 * @param localeCode 로케일 코드
	 */
	public String readMessagesId(String itemTypeCode, String itemId, String fieldName, String localeCode);
}
