package com.lgcns.ikep4.servicepack.seamless.dao;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.servicepack.seamless.model.SeamlessmessageUserSeting;

public interface  SeamlessmessageUserSetingDao extends GenericDao<SeamlessmessageUserSeting, String> {
	
	public void updateSyncStart(String folderName, String registerId);
	
	public void updateSyncEnd(String folderName, String registerId);
}
