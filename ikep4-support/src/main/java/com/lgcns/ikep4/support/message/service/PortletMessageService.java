package com.lgcns.ikep4.support.message.service;

import java.util.List;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.support.message.model.Message;
import com.lgcns.ikep4.support.message.model.PortletMessage;
import com.lgcns.ikep4.support.message.search.MessageSearchCondition;

public interface PortletMessageService  extends GenericService<PortletMessage, String> {
	public List<Message> unReadReceiveMessageList(MessageSearchCondition messageSearchCondition);
}
