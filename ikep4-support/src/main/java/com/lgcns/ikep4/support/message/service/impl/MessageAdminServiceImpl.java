/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.message.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.message.dao.MessageAdminDao;
import com.lgcns.ikep4.support.message.model.MessageAdmin;
import com.lgcns.ikep4.support.message.model.MessageChart;
import com.lgcns.ikep4.support.message.model.MessageCode;
import com.lgcns.ikep4.support.message.model.MessageMonitor;
import com.lgcns.ikep4.support.message.search.MessageMonitorSearchCondition;
import com.lgcns.ikep4.support.message.service.MessageAdminService;

/**
 * TODO Javadoc주석작성
 *
 * @author 손정환(samsonic@dbays.com)
 * @version $Id: MessageAdminServiceImpl.java 16258 2011-08-18 05:37:22Z giljae $
 */
@Service
public class MessageAdminServiceImpl extends GenericServiceImpl<MessageAdmin, String> implements MessageAdminService {
	protected final Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private MessageAdminDao messageAdminDao;
	
	@Autowired
	public MessageAdminServiceImpl(MessageAdminDao dao) {
		super(dao);
		this.messageAdminDao = dao;
	}
	
	@Override
	public String create(MessageAdmin messageAdmin) {
		String existCheck = "C";
		MessageCode messageCode = new MessageCode();
		if (this.messageAdminDao.exists(messageAdmin.getPortalId()) ) {
			this.messageAdminDao.update(messageAdmin);
			existCheck = "U";
		} else {
			if(messageAdmin.getMaxMonthFilesize() == null) { messageAdmin.setMaxMonthFilesize(messageCode.getMaxMonthFilesize().get(0).getKey());}
			if(messageAdmin.getMaxStoredFilesize() == null) { messageAdmin.setMaxStoredFilesize(messageCode.getMaxStoredFilesize().get(0).getKey());}
			if(messageAdmin.getMaxAttachFilesize() == null) { messageAdmin.setMaxAttachFilesize(messageCode.getMaxAttachFilesize().get(0).getKey());}
			if(messageAdmin.getMaxReceiverCount() == null) { messageAdmin.setMaxReceiverCount(messageCode.getMaxReceiverCount().get(0).getKey());}
			if(messageAdmin.getKeepDays() == null) { messageAdmin.setKeepDays(messageCode.getKeepDays().get(0).getKey());}
			this.messageAdminDao.create(messageAdmin);
		}
		return existCheck;
	}
	
	public MessageAdmin getUserAdmin(String userId) {
		return this.messageAdminDao.getUserAdmin(userId);
	}
	
	public SearchResult<MessageMonitor> getUserVolumnInfoList(MessageMonitorSearchCondition messageMonitorSearchCondition) {
		Integer count = this.messageAdminDao.countUserVolumnInfoList(messageMonitorSearchCondition);
		messageMonitorSearchCondition.terminateSearchCondition(count);
		SearchResult<MessageMonitor> searchResult = null;
		
		if(messageMonitorSearchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<MessageMonitor>(messageMonitorSearchCondition); 
		} else {
			List<MessageMonitor> messageMonitorList = this.messageAdminDao.getUserVolumnInfoList(messageMonitorSearchCondition);
			
			searchResult = new SearchResult<MessageMonitor>(messageMonitorList, messageMonitorSearchCondition); 
		}
		return searchResult;
	}
	
	public List<MessageChart> messageWeekChartList() {
		return this.messageAdminDao.messageWeekChartList();
	}
	
	public List<MessageChart> messageDayChartList() {
		return this.messageAdminDao.messageDayChartList();
	}
}
