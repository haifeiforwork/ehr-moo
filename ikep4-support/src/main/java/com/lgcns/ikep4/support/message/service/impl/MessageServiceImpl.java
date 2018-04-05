/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.message.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.support.activitystream.service.ActivityStreamService;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.message.dao.MessageDao;
import com.lgcns.ikep4.support.message.model.Message;
import com.lgcns.ikep4.support.message.model.MessageAlarm;
import com.lgcns.ikep4.support.message.model.MessageReceive;
import com.lgcns.ikep4.support.message.search.MessageSearchCondition;
import com.lgcns.ikep4.support.message.service.MessageService;
import com.lgcns.ikep4.support.message.util.MessageConstance;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.StringUtil;

/**
 * TODO Javadoc주석작성
 *
 * @author 손정환(samsonic@dbays.com)
 * @version $Id: MessageServiceImpl.java 17307 2012-02-06 04:42:08Z yruyo $
 */
@Service
@Transactional
public class MessageServiceImpl extends GenericServiceImpl<Message, String> implements MessageService {
	protected final Log log = LogFactory.getLog(getClass());
	
	
	private MessageDao messageDao;
	
	@Autowired
	private IdgenService idgenService;
	
	@Autowired
	public MessageServiceImpl(MessageDao dao) {
		super(dao);
		this.messageDao = dao;
	}
	
	@Autowired
	private ActivityStreamService activityStreamService;
	
	@Autowired
	private FileService fileService; 
 
	@Override
	public Message read(String messageId) {
		Message message = new Message();
		message = this.dao.get(messageId);
		
		message.setFileDataList(this.fileService.getItemFile(messageId, "N"));
		return message;
	}
	
	public boolean readCheck(MessageSearchCondition messageSearchCondition) {
		return this.messageDao.readCheck(messageSearchCondition);
	}
	
	public SearchResult<Message> getSendMessageList(MessageSearchCondition messageSearchCondition) {
		Integer count = this.messageDao.countSendMessageList(messageSearchCondition);
		messageSearchCondition.terminateSearchCondition(count);
		SearchResult<Message> searchResult = null;
		
		if(messageSearchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<Message>(messageSearchCondition); 
		} else {
			List<Message> messageList = this.messageDao.getSendMessageList(messageSearchCondition); 
			
			searchResult = new SearchResult<Message>(messageList, messageSearchCondition); 
		}
		
		return searchResult;
	}

	public SearchResult<Message> getReceiveMessageList(MessageSearchCondition messageSearchCondition) {
		Integer count = this.messageDao.countReceiveMessageList(messageSearchCondition);
		messageSearchCondition.terminateSearchCondition(count);
		SearchResult<Message> searchResult = null;
		
		if(messageSearchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<Message>(messageSearchCondition);
		} else {
			List<Message> messageList = this.messageDao.getReceiveMessageList(messageSearchCondition); 
				
			searchResult = new SearchResult<Message>(messageList, messageSearchCondition); 
		}
		return searchResult;
	}
	
	public SearchResult<Message> getStoreMessageList(MessageSearchCondition messageSearchCondition) {
		Integer count = this.messageDao.countStoreMessageList(messageSearchCondition);
		messageSearchCondition.terminateSearchCondition(count);
		SearchResult<Message> searchResult = null;
		
		if(messageSearchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<Message>(messageSearchCondition); 
		} else {
			List<Message> messageList = this.messageDao.getStoreMessageList(messageSearchCondition); 
			
			searchResult = new SearchResult<Message>(messageList, messageSearchCondition); 
		}
		
		return searchResult;
	}
	
	public SearchResult<Message> getGroupMessageList(MessageSearchCondition messageSearchCondition) {
		Integer count = this.messageDao.countGroupMessageList(messageSearchCondition);
		messageSearchCondition.terminateSearchCondition(count);
		SearchResult<Message> searchResult = null;
		
		if(messageSearchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<Message>(messageSearchCondition);
		} else {
			List<Message> messageList = this.messageDao.getGroupMessageList(messageSearchCondition); 
				
			searchResult = new SearchResult<Message>(messageList, messageSearchCondition); 
		}
		return searchResult;
	}
	
	public Integer countNewMessage(String userId) {
		return this.messageDao.countNewMessage(userId);		
	}
	
	public void insertIsRead(String messageId, String userId){
		Message message = new Message();
		message.setMessageId(messageId);
		message.setUserId(userId);
		this.messageDao.insertIsRead(message);
	}


	public void deleteSendMessage(List<Message> messageList) {
		for (int i = 0; i < messageList.size(); i++) {
			this.messageDao.physicalDeleteSend(messageList.get(i));
		}
	}


	public void deleteReceiveMessage(List<Message> messageList) {
		for (int i = 0; i < messageList.size(); i++) {
			this.messageDao.logicalDeleteReceive(messageList.get(i));
		}
	}


	public void deleteStoreMessage(List<Message> messageList) {
		for (int i = 0; i < messageList.size(); i++) {
			this.messageDao.physicalDeleteStore(messageList.get(i));
		}
	}


	public void keepSendMessage(List<Message> messageList) {
		for (int i = 0; i < messageList.size(); i++) {
			this.messageDao.physicalDeleteStore(messageList.get(i));
			this.messageDao.createStore(messageList.get(i));
			this.messageDao.physicalDeleteSend(messageList.get(i));
		}
	}


	public void keepReceiveMessage(List<Message> messageList) {
		for (int i = 0; i < messageList.size(); i++) {
			this.messageDao.physicalDeleteStore(messageList.get(i));
			this.messageDao.createStore(messageList.get(i));
			this.messageDao.logicalDeleteReceive(messageList.get(i));
		}
	}
	
	public String withdrawMessage(String messageId) {
		int messageCount = this.messageDao.countReadMessage(messageId);
		String resultCode = null;
		Message message = new Message();
		message.setMessageId(messageId);
		if(messageCount > 0) {
			resultCode = "DONT";
		} else {
			this.messageDao.physicalDeleteStore(message);
			this.messageDao.physicalDeleteReceive(message);
			this.messageDao.physicalDeleteSend(message);
			this.messageDao.physicalDelete(messageId);
			resultCode = "DONE";
		}
		return resultCode;
	}
	
	public String sendMessage(Message message, User user) {
		String messageId = idgenService.getNextId();
		message.setMessageId(messageId);
		//message.setReceiverCount(receiveList.size());
		if(message.getFileLinkList() != null){
			int attachCount = 0;
			int filesize = 0;
			for (int i = 0; i < message.getFileLinkList().size(); i++) {
				if (!message.getFileLinkList().get(i).getFlag().equals("del")) {
					if (message.getFileLinkList().get(i).getFileSize() != null && !(message.getFileLinkList().get(i).getFileSize()).equals("")) {
						filesize = filesize + Integer.parseInt( message.getFileLinkList().get(i).getFileSize());
					}
					attachCount++;
				}
			}
			message.setAttachCount(attachCount);
			message.setAttachSize(Math.round(filesize / MessageConstance.byteChangeNum));
		}
		this.messageDao.create(message);
		message.setUserId(message.getSenderId());
		this.messageDao.createSend(message);
		
		if(message.getFileLinkList() != null) {
	         //파라미터(업로드된파일리스트정보, 아이템ID, 아이템TYPE(IKEP4_EV_ITEM_TYPE 의 코드값 참조))
			fileService.copyByFileLinkList(message.getFileLinkList(), message.getMessageId(), IKepConstant.ITEM_TYPE_CODE_MESSAGING, user);
		}
		
		return messageId;
	}
	
	//@Async
	//@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void insertReceiveMessage(List<Message> receiveList) {
		for (int i = 0; i < receiveList.size(); i++) {
			this.messageDao.createReceive(receiveList.get(i));
		}
		this.messageDao.updateIsComplete(receiveList.get(0).getMessageId());
		
		User user = new User();
		user.setUserId(((Message)receiveList.get(0)).getSenderId());
		user.setUserName(((Message)receiveList.get(0)).getSenderName());
		if (receiveList.size() > 1 ) {
			//SMS, MAIL, MESSAGE, Follow/UnFollow, Mention, Profile Invite/Visit 등등의 ActivityStream 등록(대량건수일 경우)
			activityStreamService.createForMessage(IKepConstant.ITEM_TYPE_CODE_MESSAGING, IKepConstant.ACTIVITY_CODE_MSG_SEND_Multi, receiveList.get(0).getMessageId(), StringUtil.cutString(receiveList.get(0).getContents(),MessageConstance.activityStreamWordNum,null), receiveList.get(0).getUserId(), receiveList.size(), user);
		} else {
			//SMS, MAIL, MESSAGE, Follow/UnFollow, Mention, Profile Invite/Visit 등등의 ActivityStream 등록(한건일 경우)
			activityStreamService.createForMessage(IKepConstant.ITEM_TYPE_CODE_MESSAGING, IKepConstant.ACTIVITY_CODE_MSG_SEND, receiveList.get(0).getMessageId(), StringUtil.cutString(receiveList.get(0).getContents(),MessageConstance.activityStreamWordNum,null), receiveList.get(0).getUserId(), 1, user);
		}
	}
	
	public List<MessageReceive> messageReceiveList(String messageId, int rowNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("messageId", messageId);
		map.put("rowNum", rowNum);
		return messageDao.messageReceiveList(map);
	}

	public MessageAlarm getNewMessageArrived(String userId) {
		return this.messageDao.getNewMessageArrived(userId);
	}

	public void updateMessageAlarm(MessageAlarm messageAlarm) {
		this.messageDao.updateMessageAlarm(messageAlarm);
	}

}
