package com.lgcns.ikep4.servicepack.seamless.dao.impl;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.servicepack.seamless.dao.SeamlessmessageUserSetingDao;
import com.lgcns.ikep4.servicepack.seamless.model.SeamlessmessageUserSeting;
import com.lgcns.ikep4.servicepack.seamless.util.SeamlessMessageConstance;

@Repository
public class SeamlessmessageUserSetingDaoImpl extends GenericDaoSqlmap<SeamlessmessageUserSeting, String> implements SeamlessmessageUserSetingDao{

	private String nameSpace = "servicepack.seamlessmessage.seamlessmessageUserSeting.";
	
	public SeamlessmessageUserSeting get(String registerId) {
		return (SeamlessmessageUserSeting) sqlSelectForObject(nameSpace+"get", registerId);
	}

	public boolean exists(String registerId) {
		Integer count = (Integer) sqlSelectForObject(nameSpace+"exists", registerId);
		if (count > 0) { return true; }
		return false;
	}

	public String create(SeamlessmessageUserSeting seamlessmessageUserSeting) {
		String registerId = seamlessmessageUserSeting.getRegisterId();
		this.sqlInsert(nameSpace+"create",seamlessmessageUserSeting);
		return registerId;
	}

	public void update(SeamlessmessageUserSeting seamlessmessageUserSeting) {
		this.sqlUpdate(nameSpace+"update",seamlessmessageUserSeting);
	}

	public void remove(String registerId) {
		this.sqlUpdate(nameSpace+"remove", registerId);
	}

	public void updateSyncStart(String folderName, String registerId) {
		Integer count = (Integer) sqlSelectForObject(nameSpace+"exists", registerId);
		if (count > 0) {
			if (folderName.equals(SeamlessMessageConstance.imapReceiveBox)) {
				this.sqlUpdate(nameSpace+"updateInboxSyncStart", registerId);
			} else if(folderName.equals(SeamlessMessageConstance.imapsendBox)) {
				this.sqlUpdate(nameSpace+"updateSentSyncStart", registerId);
			}
		} else {
			SeamlessmessageUserSeting seamlessmessageUserSeting = new SeamlessmessageUserSeting();
			seamlessmessageUserSeting.setRegisterId(registerId);
			seamlessmessageUserSeting.setIsSourceDelete(0);
			seamlessmessageUserSeting.setAutoConnect(0);
			seamlessmessageUserSeting.setUpdaterId(registerId);
			this.sqlInsert(nameSpace+"create",seamlessmessageUserSeting);
			
			if (folderName.equals(SeamlessMessageConstance.imapReceiveBox)) {
				this.sqlUpdate(nameSpace+"updateInboxSyncStart", registerId);
			} else if(folderName.equals(SeamlessMessageConstance.imapsendBox)) {
				this.sqlUpdate(nameSpace+"updateSentSyncStart", registerId);
			}
		}
	}

	public void updateSyncEnd(String folderName, String registerId) {
		if (folderName.equals(SeamlessMessageConstance.imapReceiveBox)) {
			this.sqlUpdate(nameSpace+"updateInboxSyncEnd", registerId);
		} else if(folderName.equals(SeamlessMessageConstance.imapsendBox)) {
			this.sqlUpdate(nameSpace+"updateSentSyncEnd", registerId);
		}
	}

}