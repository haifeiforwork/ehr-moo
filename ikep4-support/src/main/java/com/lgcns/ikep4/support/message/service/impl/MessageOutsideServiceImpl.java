/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.message.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.support.message.model.Message;
import com.lgcns.ikep4.support.message.service.MessageOutsideService;
import com.lgcns.ikep4.support.message.service.MessageService;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * TODO Javadoc주석작성
 *
 * @author 손정환(samsonic@dbays.com)
 * @version $Id: MessageOutsideServiceImpl.java 16273 2011-08-18 07:07:47Z giljae $
 */
@Service
@Transactional
public class MessageOutsideServiceImpl implements MessageOutsideService {

	@Autowired
	private MessageService messageService;
	
	/** 메세지 보내기 (외부사용)
	 * 필수 param [Message Model type]
	 *           1.SENDER_ID 보낸 사람 ID
	 *           2.SENDER_NAME 보낸사람 NAME
	 *           3.CONTENTS 보내는 내용
	 *           4.RECEIVER_LIST 수신자 LIST [USER_ID/USER_NAME/RECEIVE_TYPE(수신타입: user/group)]
	 *           (ex: userId1/userName1/user,userId2/userName2/user,userId3/userName3/user) List 형태
	 **/
	public void sendMessageOutside(Message message, User user) {
		if(message.getCategoryId() == null) { message.setCategoryId("0");}
		if(message.getAttachSize() == null) { message.setAttachSize(0);}
		if(message.getIsStored() == null) { message.setIsStored(0);}
		if(message.getIsComplete() == null) { message.setIsComplete(0);}
		if(message.getIsUrgent() == null) { message.setIsUrgent(0);}
		
		String receiverList = message.getReceiverList().replaceAll(" ", "");
		receiverList = receiverList.replace("[", "");
		receiverList = receiverList.replace("]", "");
		String[] arrayReceive;
		arrayReceive = receiverList.split(",");
		ArrayList<String> receiverString = new ArrayList<String>();
		for (int i = 0; i < arrayReceive.length; i++) {
			String[] arrayReceiveUserList;
			arrayReceiveUserList = arrayReceive[i].split("/");
			if (arrayReceiveUserList.length > 1) {
				receiverString.add(arrayReceiveUserList[0]+ "/" + arrayReceiveUserList[1]);
			} else {
				receiverString.add(arrayReceiveUserList[0]);
			}
		}
		receiverList = receiverString.toString().replace("[", "").replace("]", "");
		message.setReceiverList(receiverList);
		message.setReceiverCount(arrayReceive.length);		
		String messageId = this.messageService.sendMessage(message, user);
		
		List<Message> list = new ArrayList<Message>();
		for (int i = 0; i < arrayReceive.length; i++) {
			Message receiveMessage = new Message();
			receiveMessage.setSenderId(message.getSenderId());
			receiveMessage.setSenderName(message.getSenderName());
			receiveMessage.setMessageId(messageId);
			receiveMessage.setContents(message.getContents());
			String[] arrayReceiveUser;
			arrayReceiveUser = arrayReceive[i].split("/");
			receiveMessage.setUserId(arrayReceiveUser[0]);
			if (arrayReceiveUser.length > 1) { receiveMessage.setUserName(arrayReceiveUser[1]);}
			if (arrayReceiveUser.length > 2) {
				if(arrayReceiveUser[2].equals("user")) { receiveMessage.setReceiveType("P");}
				if(arrayReceiveUser[2].equals("group")) { receiveMessage.setReceiveType("T");}
			} else {
				receiveMessage.setReceiveType("P");
			}
			list.add(receiveMessage);
		}
		this.messageService.insertReceiveMessage(list);		
	}
 
	
}
