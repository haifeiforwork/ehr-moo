/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.message.service;

import java.util.List;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.message.model.MessageAdmin;
import com.lgcns.ikep4.support.message.model.MessageChart;
import com.lgcns.ikep4.support.message.model.MessageMonitor;
import com.lgcns.ikep4.support.message.search.MessageMonitorSearchCondition;

/**
 * TODO Javadoc주석작성
 *
 * @author 손정환(samsonic@dbays.com)
 * @version $Id: MessageAdminService.java 16258 2011-08-18 05:37:22Z giljae $
 */
public interface MessageAdminService extends GenericService<MessageAdmin, String> {
	
	public MessageAdmin getUserAdmin(String userId);
	
	public SearchResult<MessageMonitor> getUserVolumnInfoList(MessageMonitorSearchCondition messageMonitorSearchCondition);
	
	public List<MessageChart> messageWeekChartList();
	
	public List<MessageChart> messageDayChartList();
}
