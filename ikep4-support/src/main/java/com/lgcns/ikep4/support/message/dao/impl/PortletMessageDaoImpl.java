package com.lgcns.ikep4.support.message.dao.impl;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.message.dao.PortletMessageDao;
import com.lgcns.ikep4.support.message.model.PortletMessage;

@Repository
public class PortletMessageDaoImpl extends GenericDaoSqlmap<PortletMessage, String> implements PortletMessageDao{

	private String nameSpace = "support.message.portletMessage.";
	
	public PortletMessage get(String registerId) {
		return (PortletMessage) sqlSelectForObject(nameSpace+"get", registerId);
	}

	public boolean exists(String registerId) {
		Integer count = (Integer) sqlSelectForObject(nameSpace+"exists", registerId);
		if (count > 0) { return true; }
		return false;
	}

	public String create(PortletMessage portletMessage) {
		String registerId = portletMessage.getRegisterId();
		this.sqlInsert(nameSpace+"create",portletMessage);
		return registerId;
	}

	public void update(PortletMessage portletMessage) {
		this.sqlUpdate(nameSpace+"update",portletMessage);
	}

	public void remove(String registerId) {
		this.sqlUpdate(nameSpace+"remove", registerId);
	}

}
