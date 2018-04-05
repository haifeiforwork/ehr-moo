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
import com.lgcns.ikep4.support.message.dao.MessageSpecialUserDao;
import com.lgcns.ikep4.support.message.model.MessageSpecialUser;
import com.lgcns.ikep4.support.message.search.MessageMonitorSearchCondition;
import com.lgcns.ikep4.support.message.service.MessageSpecialUserService;

/**
 * TODO Javadoc주석작성
 *
 * @author 손정환(samsonic@dbays.com)
 * @version $Id: MessageSpecialUserServiceImpl.java 16258 2011-08-18 05:37:22Z giljae $
 */
@Service
public class MessageSpecialUserServiceImpl extends GenericServiceImpl<MessageSpecialUser, String> implements MessageSpecialUserService {
	protected final Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private MessageSpecialUserDao messageSpecialUserDao;
	
	@Autowired
	public MessageSpecialUserServiceImpl(MessageSpecialUserDao dao) {
		super(dao);
		this.messageSpecialUserDao = dao;
	}
	
	@Override
	public String create(MessageSpecialUser messageSpecialUser) {
		String existCheck = "C";
		if (this.messageSpecialUserDao.exists(messageSpecialUser.getUserId()) ) {
			this.messageSpecialUserDao.update(messageSpecialUser);
			existCheck = "U";
		} else {
			this.messageSpecialUserDao.create(messageSpecialUser);
		}
		return existCheck;
	}
	
	public SearchResult<MessageSpecialUser> getSpecialUserList(MessageMonitorSearchCondition messageMonitorSearchCondition) {
		Integer count = this.messageSpecialUserDao.countSpecialUserList(messageMonitorSearchCondition);
		messageMonitorSearchCondition.terminateSearchCondition(count);
		SearchResult<MessageSpecialUser> searchResult = null;
		
		if(messageMonitorSearchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<MessageSpecialUser>(messageMonitorSearchCondition); 
		} else {
			List<MessageSpecialUser> messageSpecialUser = this.messageSpecialUserDao.getSpecialUserList(messageMonitorSearchCondition);
			
			searchResult = new SearchResult<MessageSpecialUser>(messageSpecialUser, messageMonitorSearchCondition); 
		}
		return searchResult;
	}
	
}
