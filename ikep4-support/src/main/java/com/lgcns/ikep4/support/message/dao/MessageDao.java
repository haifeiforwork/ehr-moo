/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.message.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.support.message.model.Message;
import com.lgcns.ikep4.support.message.model.MessageAlarm;
import com.lgcns.ikep4.support.message.model.MessageReceive;
import com.lgcns.ikep4.support.message.search.MessageSearchCondition;

/**
 * TODO Javadoc주석작성
 *
 * @author 손정환(samsonic@dbays.com)
 * @version $Id: MessageDao.java 17308 2012-02-06 04:43:19Z yruyo $
 */
public interface MessageDao extends GenericDao<Message,String> {
	
	/**	
	 * 메세지 읽기 가능 여부 확인
	 * 
	 * @param messageSearchCondition
	 * @return @
	 */
	public boolean readCheck(MessageSearchCondition messageSearchCondition);
	/**	
	 * 보낸 쪽지 리스트 조회
	 * 
	 * @param messageSearchCondition
	 * @return @
	 */
	public List<Message> getSendMessageList(MessageSearchCondition messageSearchCondition);

	/**
	 * 받은 쪽지 리스트 조회
	 * 
	 * @param messageSearchCondition
	 * @return @
	 */
	public List<Message> getReceiveMessageList(MessageSearchCondition messageSearchCondition);
	
	/**
	 * 개인보관함 리스트 조회
	 * 
	 * @param messageSearchCondition
	 * @return @
	 */
	public List<Message> getStoreMessageList(MessageSearchCondition messageSearchCondition);
	
	/**
	 * 그룹 받은 쪽지 리스트 조회
	 * 
	 * @param messageSearchCondition
	 * @return @
	 */
	public List<Message> getGroupMessageList(MessageSearchCondition messageSearchCondition);
	
	/**
	 * 보낸 쪽지 리스트 건수
	 * 
	 * @param messageSearchCondition
	 * @return count@
	 */
	public Integer countSendMessageList(MessageSearchCondition messageSearchCondition); 	
	
	/**
	 * 받은 쪽지 리스트 건수
	 * 
	 * @param messageSearchCondition
	 * @return count@
	 */
	public Integer countReceiveMessageList(MessageSearchCondition messageSearchCondition); 
	
	/**
	 * 개인보관함 리스트 건수
	 * 
	 * @param messageSearchCondition
	 * @return count@
	 */
	public Integer countStoreMessageList(MessageSearchCondition messageSearchCondition); 
	
	/**
	 * 그룹 받은 쪽지 리스트 건수
	 * 
	 * @param messageSearchCondition
	 * @return count@
	 */
	public Integer countGroupMessageList(MessageSearchCondition messageSearchCondition);
	
	/**
	 * 받은 쪽지함생성
	 * 
	 * @param message
	 * @return 
	 */
	public void createSend(Message message);
	
	/**
	 * 보낸 쪽지함생성
	 * 
	 * @param message
	 * @return 
	 */
	public void createReceive(Message message);
	
	/**
	 * 쪽지보관함생성
	 * 
	 * @param message
	 * @return 
	 */
	public void createStore(Message message);
	
	/**
	 * 받은 쪽지함 읽기 여부 수정
	 * 
	 * @param message
	 * @return 
	 */
	public void updateIsComplete(String messageId);
	
	/**
	 * 받은 쪽지함 읽기 여부 수정
	 * 
	 * @param message
	 * @return 
	 */
	public void insertIsRead(Message message);
	
	/**
	 * 쪽지함 삭제
	 * 
	 * @param messageId
	 * @return 
	 */
	public void physicalDelete(String messageId);
	
	/**
	 * 보낸 쪽지함  삭제
	 * 
	 * @param message
	 * @return 
	 */
	public void physicalDeleteSend(Message message);
	
	/**
	 * 받은 쪽지함  삭제
	 * 
	 * @param message
	 * @return 
	 */
	public void physicalDeleteReceive(Message message);
	
	public void logicalDeleteReceive(Message message);
	
	/**
	 * 쪽지보관함  삭제
	 * 
	 * @param message
	 * @return 
	 */
	public void physicalDeleteStore(Message message);
	
	/**
	 * 메세지 읽은수신자 여부 확인
	 * 
	 * @param messageId
	 * @return 
	 */
	public Integer countReadMessage(String messageId);
	
	/**
	 * 신규 메세지 건수
	 * 
	 * @param messageId
	 * @return 
	 */
	public Integer countNewMessage(String userId);
	
	/**
	 * 수신확인 List
	 * 
	 * @param map
	 * @return List
	 */
	public List<MessageReceive> messageReceiveList(Map<String, Object> map);
	
	/**
	 * 신규메세지 알림 건
	 * 
	 * @param userId
	 * @return 신규건수, 최종메세지ID
	 */
	public MessageAlarm getNewMessageArrived(String userId);
	
	/**
	 * 메세지 알림 확인 UPDATE
	 * 
	 * @param messageAlarm
	 * @return 
	 */
	public void updateMessageAlarm(MessageAlarm messageAlarm);

}
