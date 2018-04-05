/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.message.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.message.dao.MessageAdminDao;
import com.lgcns.ikep4.support.message.model.MessageAdmin;
import com.lgcns.ikep4.support.message.model.MessageChart;
import com.lgcns.ikep4.support.message.model.MessageMonitor;
import com.lgcns.ikep4.support.message.search.MessageMonitorSearchCondition;

/**
 * TODO Javadoc주석작성
 *
 * @author 손정환(samsonic@dbays.com)
 * @version $Id: MessageAdminDaoImpl.java 16316 2011-08-22 00:26:53Z giljae $
 */
@Repository
public class MessageAdminDaoImpl extends GenericDaoSqlmap<MessageAdmin,String> implements MessageAdminDao {

	private String nameSpace = "support.message.";
	
	public MessageAdmin get(String portalId) {
		return (MessageAdmin) sqlSelectForObject(nameSpace+"messageAdmin.get", portalId);
	}
	
	public MessageAdmin getUserAdmin(String userId) {
		return (MessageAdmin) sqlSelectForObject(nameSpace+"messageAdmin.getUserAdmin", userId);
	}

	public boolean exists(String portalId) {
		Integer count = (Integer) sqlSelectForObject(nameSpace+"messageAdmin.exists", portalId);
		if (count > 0) { return true; }
		return false;
	}

	public String create(MessageAdmin messageAdmin) {
		String portalId = messageAdmin.getPortalId();
		this.sqlInsert(nameSpace+"messageAdmin.create",messageAdmin);
		return portalId;
	}

	public void update(MessageAdmin messageAdmin) {
		this.sqlUpdate(nameSpace+"messageAdmin.update",messageAdmin);
	}

	public void remove(String portalId) {
		this.sqlUpdate(nameSpace+"messageAdmin.remove", portalId);
	}
	
	@SuppressWarnings("unchecked")
	public List<MessageMonitor> getUserVolumnInfoList(MessageMonitorSearchCondition messageMonitorSearchCondition) {
		return (List<MessageMonitor>) this.getSqlMapClientTemplate().queryForList(nameSpace+"messageAdmin.getUserVolumnInfoList",messageMonitorSearchCondition);
	}

	public Integer countUserVolumnInfoList(MessageMonitorSearchCondition messageMonitorSearchCondition) {
		return (Integer) sqlSelectForObject(nameSpace+"messageAdmin.countUserVolumnInfoList", messageMonitorSearchCondition);
	}
	
	@SuppressWarnings("unchecked")
	public List<MessageChart> messageWeekChartList() {
		return (List<MessageChart>) this.getSqlMapClientTemplate().queryForList(nameSpace+"messageAdmin.messageWeekChartList");
	}
	
	@SuppressWarnings("unchecked")
	public List<MessageChart> messageDayChartList() {
		return (List<MessageChart>) this.getSqlMapClientTemplate().queryForList(nameSpace+"messageAdmin.messageDayChartList");
	}
	
	public void removeBatch() {
		this.sqlUpdate(nameSpace+"messageAdmin.deleteReceiveReadBatch");
		this.sqlUpdate(nameSpace+"messageAdmin.deleteReceiveboxBatch");
		this.sqlUpdate(nameSpace+"messageAdmin.deleteSendboxBatch");
		this.sqlUpdate(nameSpace+"messageAdmin.deleteMessageBatch");
		this.sqlUpdate(nameSpace+"messageAdmin.deleteDayFilesizeBatch");
		this.sqlInsert(nameSpace+"messageAdmin.createDayFilesizeBatch");
	}
	
}
