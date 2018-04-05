package com.lgcns.ikep4.support.message.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.support.message.dao.MessageDao;
import com.lgcns.ikep4.support.message.dao.PortletMessageDao;
import com.lgcns.ikep4.support.message.model.Message;
import com.lgcns.ikep4.support.message.model.PortletMessage;
import com.lgcns.ikep4.support.message.search.MessageSearchCondition;
import com.lgcns.ikep4.support.message.service.PortletMessageService;

@Service
public class PortletMessageServiceImpl extends GenericServiceImpl<PortletMessage, String> implements PortletMessageService{

	protected final Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private PortletMessageDao portletMessageDao;
	
	@Autowired
	private MessageDao messageDao;
	
	@Autowired
	public PortletMessageServiceImpl(PortletMessageDao dao) {
		super(dao);
		this.portletMessageDao = dao;
	}
	
	@Override
	public String create(PortletMessage portletMessage) {
		String existCheck = "C";
		if (this.portletMessageDao.exists(portletMessage.getRegisterId()) ) {
			this.portletMessageDao.update(portletMessage);
			existCheck = "U";
		} else {
			this.portletMessageDao.create(portletMessage);
		}
		return existCheck;
	}
	
	public List<Message> unReadReceiveMessageList(MessageSearchCondition messageSearchCondition) {
		messageSearchCondition.setIsRead(0);
		messageSearchCondition.setStartRowIndex(0);
		return this.messageDao.getReceiveMessageList(messageSearchCondition);
	}
	
}
