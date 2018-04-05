package com.lgcns.ikep4.servicepack.seamless.dao.impl;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.servicepack.seamless.dao.SeamlessmessageAdminDao;
import com.lgcns.ikep4.servicepack.seamless.model.SeamlessmessageAdmin;

@Repository
public class SeamlessmessageAdminDaoImpl extends GenericDaoSqlmap<SeamlessmessageAdmin,String> implements SeamlessmessageAdminDao {

	private String nameSpace = "servicepack.seamlessmessage.seamlessmessageAdmin.";
	
	public SeamlessmessageAdmin get(String portalId) {
		return (SeamlessmessageAdmin) sqlSelectForObject(nameSpace+"get", portalId);
	}

	public boolean exists(String portalId) {
		Integer count = (Integer) sqlSelectForObject(nameSpace+"exists", portalId);
		if (count > 0) { return true; }
		return false;
	}

	public String create(SeamlessmessageAdmin seamlessmessageAdmin) {
		String portalId = seamlessmessageAdmin.getPortalId();
		this.sqlInsert(nameSpace+"create",seamlessmessageAdmin);
		return portalId;
	}

	public void update(SeamlessmessageAdmin seamlessmessageAdmin) {
		this.sqlUpdate(nameSpace+"update",seamlessmessageAdmin);
	}

	public void remove(String portalId) {
		this.sqlUpdate(nameSpace+"remove", portalId);
	}
	
	public void removeBatch() {
		this.sqlUpdate(nameSpace+"deleteReceiveboxRecipientBatch");
		this.sqlUpdate(nameSpace+"deleteReceiveboxAttachBatch");
		this.sqlUpdate(nameSpace+"deleteReceiveboxBatch");
		this.sqlUpdate(nameSpace+"deleteSendboxRecipientBatch");
		this.sqlUpdate(nameSpace+"deleteSendboxAttachBatch");
		this.sqlUpdate(nameSpace+"deleteSendboxBatch");
	}

}
